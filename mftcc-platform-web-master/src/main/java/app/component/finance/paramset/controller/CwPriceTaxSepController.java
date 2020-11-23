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
import app.component.finance.paramset.entity.CwPriceTaxSep;
import app.component.finance.paramset.feign.CwPriceTaxSepFeign;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwPriceTaxSepAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 14:41:38 CST 2017
 **/
@Controller
@RequestMapping("/cwPriceTaxSep")
public class CwPriceTaxSepController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwPriceTaxSepFeign cwPriceTaxSepFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwPriceTaxSepController() {
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
		return "/component/finance/paramset/CwPriceTaxSep_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwPriceTaxSep cwPriceTaxSep = new CwPriceTaxSep();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwPriceTaxSep.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwPriceTaxSep.setCriteriaList(cwPriceTaxSep, ajaxData);// 我的筛选
			// cwPriceTaxSep.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwPriceTaxSep,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwPriceTaxSep", cwPriceTaxSep);
			ipage.setParams(params);
			ipage = cwPriceTaxSepFeign.findByPage(ipage,finBooks);
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
			FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
			getFormValue(formpricetaxsep0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpricetaxsep0002)) {
				CwPriceTaxSep cwPriceTaxSep = new CwPriceTaxSep();
				setObjValue(formpricetaxsep0002, cwPriceTaxSep);
				cwPriceTaxSepFeign.insert(cwPriceTaxSep);
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
		FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
		getFormValue(formpricetaxsep0002, getMapByJson(ajaxData));
		CwPriceTaxSep cwPriceTaxSepJsp = new CwPriceTaxSep();
		setObjValue(formpricetaxsep0002, cwPriceTaxSepJsp);
		CwPriceTaxSep cwPriceTaxSep = cwPriceTaxSepFeign.getById(cwPriceTaxSepJsp);
		if (cwPriceTaxSep != null) {
			try {
				cwPriceTaxSep = (CwPriceTaxSep) EntityUtil.reflectionSetVal(cwPriceTaxSep, cwPriceTaxSepJsp,
						getMapByJson(ajaxData));
				cwPriceTaxSepFeign.update(cwPriceTaxSep);
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
			FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
			getFormValue(formpricetaxsep0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpricetaxsep0002)) {
				CwPriceTaxSep cwPriceTaxSep = new CwPriceTaxSep();
				setObjValue(formpricetaxsep0002, cwPriceTaxSep);
				cwPriceTaxSepFeign.update(cwPriceTaxSep);
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
	public Map<String, Object> getByIdAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
		CwPriceTaxSep cwPriceTaxSep = new CwPriceTaxSep();
		cwPriceTaxSep.setUuid(uuid);
		cwPriceTaxSep = cwPriceTaxSepFeign.getById(cwPriceTaxSep);
		getObjValue(formpricetaxsep0002, cwPriceTaxSep, formData);
		if (cwPriceTaxSep != null) {
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
		CwPriceTaxSep cwPriceTaxSep = new CwPriceTaxSep();
		cwPriceTaxSep.setUuid(uuid);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwPriceTaxSepFeign.delete(cwPriceTaxSep,finBooks);
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
		// FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			dataMap = cwPriceTaxSepFeign.getCwMessageData(dataMap,finBooks);

			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/paramset/CwPriceTaxSep_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		// formpricetaxsep0001 = formService.getFormData("pricetaxsep0001");
		// getFormValue(formpricetaxsep0001);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("uuid", uuid);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			dataMap = cwPriceTaxSepFeign.getPriceTaxDataByUid(dataMap,finBooks);

			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
		// getObjValue(formpricetaxsep0001, cwPriceTaxSep);
		return "/component/finance/paramset/CwPriceTaxSep_Detail";
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
		FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
		getFormValue(formpricetaxsep0002);
		boolean validateFlag = this.validateFormData(formpricetaxsep0002);
		model.addAttribute("formpricetaxsep0002", formpricetaxsep0002);
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
		FormData formpricetaxsep0002 = formService.getFormData("pricetaxsep0002");
		getFormValue(formpricetaxsep0002);
		boolean validateFlag = this.validateFormData(formpricetaxsep0002);
		model.addAttribute("formpricetaxsep0002", formpricetaxsep0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 根据科目获取当前科目余额
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-7 下午3:35:26
	 */
	@RequestMapping(value = "/getAccAmtByWeeksAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccAmtByWeeksAjax(String accNo, String weeks) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			dataMap.put("accNo", accNo);
			dataMap.put("weeks", weeks);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			dataMap = cwPriceTaxSepFeign.getAccAmtByWeeks(dataMap,finBooks);
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
	 * 方法描述： 计算金额的总和
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-7 下午8:26:55
	 */
	@RequestMapping(value = "/addCalTotalAmtAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCalTotalAmtAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			dataMap = cwPriceTaxSepFeign.addCalTotalAmt(formMap);
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
	 * 方法描述： 保存计税的功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-8 上午9:56:03
	 */
	@RequestMapping(value = "/insertCwPriceTaxAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertCwPriceTaxAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwPriceTaxSepFeign.insertCwPriceTaxData(formMap,finBooks);
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
	 * 
	 * 方法描述：判断 按月价税管理是否显示
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-12 下午5:05:18
	 */
	@RequestMapping(value = "/getMonPriceTaxShowAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMonPriceTaxShowAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String result = cwPriceTaxSepFeign.getMonPriceTaxShow(finBooks);
			dataMap.put("flag", "success");
			dataMap.put("result", result);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}
