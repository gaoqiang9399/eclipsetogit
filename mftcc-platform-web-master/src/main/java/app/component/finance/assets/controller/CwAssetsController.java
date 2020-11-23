package app.component.finance.assets.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.assets.entity.CwAssets;
import app.component.finance.assets.entity.CwAssetsClass;
import app.component.finance.assets.feign.CwAssetsFeign;
import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CwAssetsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 10 16:28:21 CST 2017
 **/
@Controller
@RequestMapping("/cwAssets")
public class CwAssetsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwAssetsFeign cwAssetsFeign;
	@Autowired
	private CwMonthKnotFeign cwMonthKnotFeign;
	

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/assets/CwAssets_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssets cwAssets = new CwAssets();
		try {
			cwAssets.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwAssets.setCriteriaList(cwAssets, ajaxData);// 我的筛选
			// this.getRoleConditions(cwAssets,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwAssets", cwAssets);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			ipage = cwAssetsFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： Ajax 固定资产列表 批量删除
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:31:39
	 */
	@RequestMapping(value = "/deleteBatchsAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchsAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwAssetsFeign.deleteBatchs(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		String query="";
		FormService formService=new FormService();
		FormData formassets0002 = formService.getFormData("assets0002");
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = cwAssetsFeign.getCwAssetsInfo(cwAssets,finBooks);
		Map<String, Object> cwAssetsMap = (Map<String, Object>) dataMap.get("cwAssets");
		BeanUtils.populate(cwAssets, cwAssetsMap);
		//cwAssets = (CwAssets) dataMap.get("cwAssets");

		String buyVchNo = cwAssets.getBuyVchNo();// 生成凭证，为空就是未生成

		String beCloseWeek = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
		String zcflag = "1";// 用来控制资产类别的组件，1可用，0不可用
		if (!beCloseWeek.equals(cwAssets.getWeeks())) {
			// query = "query";
			zcflag = "0";
			formassets0002 = formService.getFormData("assets0001");
		}
		if (StringUtil.isNotEmpty(buyVchNo)) {
			// 已生成凭证，只能修改减值准备
			zcflag = "0";
			formassets0002 = formService.getFormData("assets0001");
		}

		dataMap.put("zcflag", zcflag);
		getObjValue(formassets0002, cwAssets);

		@SuppressWarnings("unchecked")
		List<CwAssetsClass> listObj = (List<CwAssetsClass>) dataMap.get("listObj");
		JSONObject json = new JSONObject();
		if (listObj == null) {
			listObj = new ArrayList<CwAssetsClass>();
		}
		JSONArray roleArray = JSONArray.fromObject(listObj);
		for (int i = 0; i < roleArray.size(); i++) {

			roleArray.getJSONObject(i).put("id", roleArray.getJSONObject(i).getString("classNo"));
			roleArray.getJSONObject(i).put("name", roleArray.getJSONObject(i).getString("className"));
		}
		json.put("classType", roleArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cwAssets", cwAssets);
		model.addAttribute("formassets0002", formassets0002);
		model.addAttribute("query", query);
		return "/component/finance/assets/CwAssets_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String zcflag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			FormService formService=new FormService();
			FormData formassets0002;
			if ("0".equals(zcflag)) {
				formassets0002 = formService.getFormData("assets0001");
			} else {
				formassets0002 = formService.getFormData("assets0002");
			}

			Map<String, String> paramMap = CwPublicUtil.getMapByJson(ajaxData);
			String assetsMethod = paramMap.get("assetsMethod");
			// 设置验证
			if ("1".equals(assetsMethod)) {// 不提折旧
				paramMap.put("comDepre", "0");// 累计折旧科目
				paramMap.put("comCost", "0");// 折旧费用科目
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for (String key : paramMap.keySet()) {
					Map<String, String> newmap = new HashMap<String, String>();
					String val = paramMap.get(key);
					newmap.put("name", key);
					newmap.put("value", val);
					listmap.add(newmap);
				}
				JSONArray cwassetArray = JSONArray.fromObject(listmap);
				ajaxData = cwassetArray.toString();
			}
			getFormValue(formassets0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassets0002)) {
				CwAssets cwAssets = new CwAssets();
				setObjValue(formassets0002, cwAssets);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwAssetsFeign.insert(cwAssets,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					// dataMap.put("msg", "新增成功");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}

			} else {
				dataMap.put("flag", "error");
				System.out.println(this.getFormulavaliErrorMsg());
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 清理资产数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-10 下午2:59:48
	 */
	@RequestMapping(value = "/cleanAssetsAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cleanAssetsAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssets cwAssets = new CwAssets();
		try {
			String week = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
			cwAssets.setUuid(uuid);
			cwAssets.setAssetsSts("1");
			cwAssets.setCleanWeek(week);
			cwAssetsFeign.update(cwAssets,finBooks);
			dataMap.put("flag", "success");
			// dataMap.put("msg", "更新成功");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "更新失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 取消清理资产数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-10 下午2:59:31
	 */
	@RequestMapping(value = "/reCleanAssetsAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCleanAssetsAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssets cwAssets = new CwAssets();
		try {
			cwAssets.setUuid(uuid);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			CwAssets paAssets = cwAssetsFeign.getById(cwAssets,finBooks);
			if (StringUtil.isNotEmpty(paAssets.getCleanVchNo())) {
				//throw new CustomExp(MessageEnum.FIRST_OPERATION.getMessage("删除清理凭证,再取消清理，当前卡片已生成清理凭证"));
				// throw new CustomExp("当前卡片已生成清理凭证，请先删除清理凭证后再取消清理！");
				dataMap.put("flag", "error");
				// dataMap.put("msg", "更新失败");
				dataMap.put("msg",MessageEnum.FIRST_OPERATION.getMessage("删除清理凭证,再取消清理，当前卡片已生成清理凭证"));
				return dataMap;
			} else {
				cwAssets.setAssetsSts("0");
				cwAssets.setCleanWeek("");
				R r = cwAssetsFeign.update(cwAssets,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}

			}
			// dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "更新失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取资产类别列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-27 下午3:29:16
	 */
	@RequestMapping(value = "/getAssetsClassListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssetsClassListAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<CwAssetsClass> list = cwAssetsFeign.getAssetsClassList(new CwAssetsClass(),finBooks);
		dataMap.put("list", list);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 查询
	 * @param query 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uuid, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormData formassets0002 = new FormService().getFormData("assets0001");
		getFormValue(formassets0002);
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssets = cwAssetsFeign.getById(cwAssets,finBooks);
		getObjValue(formassets0002, cwAssets);
		model.addAttribute("cwAssets", cwAssets);
		model.addAttribute("formassets0002", formassets0002);
		model.addAttribute("query", query);
		return "/component/finance/assets/CwAssets_Detail";
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassets0002 = new FormService().getFormData("assets0002");
		getFormValue(formassets0002, getMapByJson(ajaxData));
		CwAssets cwAssetsJsp = new CwAssets();
		setObjValue(formassets0002, cwAssetsJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwAssets cwAssets = cwAssetsFeign.getById(cwAssetsJsp,finBooks);
		if (cwAssets != null) {
			try {
				cwAssets = (CwAssets) EntityUtil.reflectionSetVal(cwAssets, cwAssetsJsp, getMapByJson(ajaxData));
				cwAssetsFeign.update(cwAssets,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "保存成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());

			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				// dataMap.put("msg", "新增失败");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "编号不存在,保存失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formassets0002 = new FormService().getFormData("assets0002");
			getFormValue(formassets0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassets0002)) {
				CwAssets cwAssets = new CwAssets();
				setObjValue(formassets0002, cwAssets);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwAssetsFeign.update(cwAssets,finBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "更新成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());

			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			// dataMap.put("msg", "更新失败");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassets0002 = new FormService().getFormData("assets0002");
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssets = cwAssetsFeign.getById(cwAssets,finBooks);
		getObjValue(formassets0002, cwAssets, formData);
		if (cwAssets != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwAssetsFeign.delete(cwAssets,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "成功");
				dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax异步批量删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBatchAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwAssetsFeign.delete(cwAssets,finBooks);
			dataMap.put("flag", "success");
			// dataMap.put("msg", "成功");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formassets0002 = new FormService().getFormData("assets0002");
		getFormValue(formassets0002);
		CwAssets cwAssets = new CwAssets();
		setObjValue(formassets0002, cwAssets);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsFeign.insert(cwAssets,finBooks);
		getObjValue(formassets0002, cwAssets);
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwAssets", cwAssets);
		ipage.setParams(params);
		List<CwAssets> cwAssetsList = (List<CwAssets>) cwAssetsFeign.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwAssets", cwAssets);
		model.addAttribute("formassets0002", formassets0002);
		model.addAttribute("query", "");
		return "/component/finance/assets/CwAssets_Insert";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		CwAssets cwAssets = new CwAssets();
		cwAssets.setUuid(uuid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwAssetsFeign.delete(cwAssets,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * @param query 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormData formassets0002 = new FormService().getFormData("assets0002");
		getFormValue(formassets0002);
		boolean validateFlag = this.validateFormData(formassets0002);
		model.addAttribute("formassets0002", formassets0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * @param query 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormData formassets0002 = new FormService().getFormData("assets0002");
		getFormValue(formassets0002);
		boolean validateFlag = this.validateFormData(formassets0002);
		model.addAttribute("formassets0002", formassets0002);
		model.addAttribute("query", query);
	}

}
