package app.component.finance.paramset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwJiti;
import app.component.finance.paramset.feign.CwJitiFeign;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwJitiAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 09:31:32 CST 2017
 **/
@Controller
@RequestMapping("/cwJiti")
public class CwJitiController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwJitiFeign cwJitiFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwJitiController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/paramset/CwJiti_List";
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
		CwJiti cwJiti = new CwJiti();
		try {
			cwJiti.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwJiti.setCriteriaList(cwJiti, ajaxData);// 我的筛选
			// cwJiti.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwJiti,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwJiti", cwJiti);
			ipage.setParams(params);
			// 自定义查询Bo方法
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			ipage = cwJitiFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
			getFormValue(formcwjiti0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwjiti0002)) {
				CwJiti cwJiti = new CwJiti();
				setObjValue(formcwjiti0002, cwJiti);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwJitiFeign.insert(cwJiti,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
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
		FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
		getFormValue(formcwjiti0002, getMapByJson(ajaxData));
		CwJiti cwJitiJsp = new CwJiti();
		setObjValue(formcwjiti0002, cwJitiJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwJiti cwJiti = cwJitiFeign.getById(cwJitiJsp,finBooks);
		if (cwJiti != null) {
			try {
				cwJiti = (CwJiti) EntityUtil.reflectionSetVal(cwJiti, cwJitiJsp, getMapByJson(ajaxData));
				cwJitiFeign.update(cwJiti,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
			getFormValue(formcwjiti0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwjiti0002)) {
				CwJiti cwJiti = new CwJiti();
				setObjValue(formcwjiti0002, cwJiti);
				cwJitiFeign.update(cwJiti,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String jtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
		CwJiti cwJiti = new CwJiti();
		cwJiti.setJtId(jtId);
		cwJiti = cwJitiFeign.getById(cwJiti,finBooks);
		getObjValue(formcwjiti0002, cwJiti, formData);
		if (cwJiti != null) {
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
	public Map<String, Object> deleteAjax(String jtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwJiti cwJiti = new CwJiti();
		cwJiti.setJtId(jtId);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwJitiFeign.delete(cwJiti,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			e.printStackTrace();
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
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			dataMap = cwJitiFeign.getJiTiMessage(dataMap,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formcwjiti0002", formcwjiti0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwJiti_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String jtId) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		FormData formcwjiti0003 = formService.getFormData("cwjiti0003");
		getFormValue(formcwjiti0003);
		CwJiti cwJiti = new CwJiti();
		cwJiti.setJtId(jtId);
		cwJiti = cwJitiFeign.getById(cwJiti,finBooks);
		getObjValue(formcwjiti0003, cwJiti);
		model.addAttribute("cwJiti", cwJiti);
		model.addAttribute("formcwjiti0003", formcwjiti0003);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwJiti_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
		getFormValue(formcwjiti0002);
		boolean validateFlag = this.validateFormData(formcwjiti0002);
		model.addAttribute("formcwjiti0002", formcwjiti0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwjiti0002 = formService.getFormData("cwjiti0002");
		getFormValue(formcwjiti0002);
		boolean validateFlag = this.validateFormData(formcwjiti0002);
		model.addAttribute("formcwjiti0002", formcwjiti0002);
		model.addAttribute("query", query);
	}

	@RequestMapping(value = "/getkemuBalByAccnoAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getkemuBalByAccnoAjax(String accNo) throws Exception {
		ActionContext.initialize(request, response);
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String bal = cwJitiFeign.getkemuBalByAccno(accNo,finBooks);
			dataMap.put("bal", bal);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 根据金额和比例计算结果
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-23 上午11:38:26
	 */
	@RequestMapping(value = "/getRusultAmtAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRusultAmtAjax(String resBal, String bili, String upJiTiBal) throws Exception {
		ActionContext.initialize(request, response);
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			 R r = cwJitiFeign.getRestJisuanAmt(resBal, bili, upJiTiBal);
			if(r.isOk()) {
				Map<String, String> balMap =(Map<String, String>) r.getResult();
				dataMap.put("bal", balMap.get("resBal"));
				dataMap.put("chaBal", balMap.get("chaBal"));
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
				
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取合计的金额
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-23 上午11:39:12
	 */
	@RequestMapping(value = "/getHeJiAmtAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getHeJiAmtAjax(String zcResAmt, String gzResAmt, String cjResAmt, String kyResAmt,
			String ssResAmt, String daiBal) throws Exception {
		ActionContext.initialize(request, response);
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> balmap = cwJitiFeign.getHeJiAmt(zcResAmt, gzResAmt, cjResAmt, kyResAmt, ssResAmt,
					daiBal);
			dataMap.put("bal", balmap.get("hejibal"));
			dataMap.put("jitibal", balmap.get("jitibal"));
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	@RequestMapping(value = "/insertJiTiDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertJiTiDataAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			// Map<String, String> formMap =
			// CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwJitiFeign.insertJiTiData(formMap,finBooks);
			if(r.isOk()) {
				if ("0000".equals(r.getResult())) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

}
