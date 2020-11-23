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

import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.entity.CwSysParam;
import app.component.finance.paramset.feign.CwSysParamFeign;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwSysParamAction.java Description:财务基础数据参数化设置功能
 * 
 * @author:zhangli@dhcc.com.cn
 * @Fri Dec 30 09:47:39 CST 2016sysParam
 **/
@Controller
@RequestMapping("/cwSysParam")
public class CwSysParamController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwSysParamFeign cwSysParamFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;

	private FormService formService = new FormService();
	private Gson gson = new Gson();

	public CwSysParamController() {
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
		try {
			ActionContext.initialize(request, response);
		} catch (Exception e) {
//			logger.error("跳转到参数化设置列表页面 出错：" + e.getMessage(), e);
			throw e;
		}
		return "/component/finance/paramset/CwSysParam_List";
	}

	/**
	 * 
	 * 方法描述： 跳转到参数设置页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-18 下午4:50:47
	 */
	@RequestMapping(value = "/getListDataPage")
	public String getListDataPage(Model model) throws Exception {
		try {
			ActionContext.initialize(request, response);
			CwSysParam cwSysParam = new CwSysParam();
			List<CwSysParam> beanList = cwSysParamFeign.findListData(cwSysParam);
			String listjson = gson.toJson(beanList);
			model.addAttribute("beanlist", beanList);
			model.addAttribute("beanlistjson", listjson);
		} catch (Exception e) {
//			logger.error("跳转到参数化设置页面 出错：" + e.getMessage(), e);
			throw e;
		}
		return "/component/finance/paramset/CwSysParam_ListDataPage";
	}

	/**
	 * 方法描述： 进入系统参数设置页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-21 上午9:56:48
	 */
	@RequestMapping(value = "/toSysParamSetPage")
	public String toSysParamSetPage(Model model) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ActionContext.initialize(request, response);
			CwSysParam cwSysParam = new CwSysParam();
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<Map<String, Object>> list = cwSysParamFeign.getSysParamSetData(cwSysParam,finBooks);
			dataMap.put("list", list);
			dataMap.put("listJson", gson.toJson(list));
			String initWeek = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.qjqyqj.getNum(),finBooks).getPvalue();
			String cashierWeek = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.cnqyqj.getNum(),finBooks)
					.getPvalue();
			String comType = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.kmbmgs.getNum(),finBooks).getPvalue();
			String jitiType = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.jititype.getNum(),finBooks).getPvalue();
			String xjllfx = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.xjllfx.getNum(),finBooks).getPvalue();// xjllfx现金流量分析
			String pzfskg = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.pzfskg.getNum(),finBooks).getPvalue();// pzfskg凭证负数开关
			String sfkmtz = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.sfkmtz.getNum(),finBooks).getPvalue();// sfkmtz报表是否按其科目
			String yefxjz = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.yefxjz.getNum(),finBooks).getPvalue();// yefxjz报表是否按其余额相反方向
			String comDits = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.yjkmws.getNum(),finBooks).getPvalue();// yefxjz报表是否按其余额相反方向

			dataMap.put("weeks", initWeek.substring(0, 4) + "年" + initWeek.substring(4) + "期");
			dataMap.put("cashierWeek", cashierWeek);
			dataMap.put("comType", comType);
			dataMap.put("jitiType", "0".equals(jitiType) ? "全部" : "五级分类");
			dataMap.put("xjllfx", xjllfx);
			dataMap.put("pzfskg", pzfskg);
			dataMap.put("sfkmtz", sfkmtz);
			dataMap.put("yefxjz", yefxjz);
			dataMap.put("comDits", comDits);
			model.addAttribute("dataMap", dataMap);
		} catch (Exception e) {
//			logger.error("跳转到参数化设置页面 出错：" + e.getMessage(), e);
			throw e;
		}
		return "/component/finance/paramset/CwSysParam_Set";
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
		CwSysParam cwSysParam = new CwSysParam();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwSysParam.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwSysParam.setCriteriaList(cwSysParam, ajaxData);// 我的筛选
			// this.getRoleConditions(cwSysParam,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Feign方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwSysParam", cwSysParam);
			ipage.setParams(params);
			ipage = cwSysParamFeign.findByPage(ipage,finBooks);
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
//			logger.error("findByPageAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存参数化设置
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formparamset0001 = formService.getFormData("paramset0001");
			getFormValue(formparamset0001, getMapByJson(ajaxData));
			if (this.validateFormData(formparamset0001)) {
				CwSysParam cwSysParam = new CwSysParam();
				setObjValue(formparamset0001, cwSysParam);
				cwSysParamFeign.update(cwSysParam,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("updateAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			dataMap.put("flag", "error");
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String pcode) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwSysParam cwSysParam = new CwSysParam();
		cwSysParam.setPcode(pcode);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwSysParam = cwSysParamFeign.getById(cwSysParam,finBooks);
			if (cwSysParam != null) {
				dataMap.put("formData", cwSysParam);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
//			logger.error("getByIdAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 详情(编辑)页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String pcode) throws Exception {
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			ActionContext.initialize(request, response);
			FormData formparamset0001 = formService.getFormData("paramset0001");
			getFormValue(formparamset0001);
			CwSysParam cwSysParam = new CwSysParam();
			cwSysParam.setPcode(pcode);
			cwSysParam = cwSysParamFeign.getById(cwSysParam,finBooks);
			if ("select".equals(cwSysParam.getPtype())) {
				cwSysParam.setParams(cwSysParam.getParams().replaceAll("\"", "'"));
			}
			getObjValue(formparamset0001, cwSysParam);
			model.addAttribute("cwSysParam", cwSysParam);
			model.addAttribute("formparamset0001", formparamset0001);
			model.addAttribute("query", query);
		} catch (Exception e) {
//			logger.error("getById方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
		return "/component/finance/paramset/CwSysParam_Detail";
	}

	/**
	 * 
	 * 方法描述： 修改pvalue的值
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-20 下午3:19:39
	 */
	@RequestMapping(value = "/changePvalueByCodeAjax")
	@ResponseBody
	public Map<String, Object> changePvalueByCodeAjax(String pcode, String pvalue) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwSysParam cwSysParam = new CwSysParam();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwSysParam.setPcode(pcode);
			cwSysParam.setPvalue(pvalue);
			String result = cwSysParamFeign.doChangePvaluebyPcode(cwSysParam,finBooks);
			if ("0000".equals(result)) {

				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", formData);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 设置计提方式
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-21 下午3:26:08
	 */
	@RequestMapping(value = "/getJiTiType")
	public String getJiTiType(Model model) throws Exception {
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			ActionContext.initialize(request, response);
			FormData formcwjiti0001 = formService.getFormData("cwjiti0001");
			getFormValue(formcwjiti0001);
			CwSysParam cwSysParam = new CwSysParam();
			cwSysParam = cwSysParamFeign.getJiTiValue(cwSysParam,finBooks);
			/*
			 * cwSysParam.setPcode(pcode); cwSysParam =
			 * cwSysParamFeign.getById(cwSysParam); if
			 * ("select".equals(cwSysParam.getPtype())) {
			 * cwSysParam.setParams(cwSysParam.getParams().replaceAll("\"",
			 * "'")); }
			 */
			getObjValue(formcwjiti0001, cwSysParam);
			model.addAttribute("cwSysParam", cwSysParam);
			model.addAttribute("formcwjiti0001", formcwjiti0001);
			model.addAttribute("query", query);
		} catch (Exception e) {
//			logger.error("计提方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
		return "/component/finance/paramset/CwSysParam_JiTiType";
	}

	@RequestMapping(value = "/jiTiInsertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> jiTiInsertAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formcwjiti0001 = formService.getFormData("cwjiti0001");
			getFormValue(formcwjiti0001, getMapByJson(ajaxData));
			/* if(this.validateFormData(formcwjiti0001)){ */
			CwSysParam cwSysParam = new CwSysParam();
			setObjValue(formcwjiti0001, cwSysParam);

			R r = cwSysParamFeign.insertJitiType(cwSysParam,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			/*
			 * }else{ dataMap.put("flag", "error");
			 * dataMap.put("msg",this.getFormulavaliErrorMsg()); }
			 */
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增一级科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

}
