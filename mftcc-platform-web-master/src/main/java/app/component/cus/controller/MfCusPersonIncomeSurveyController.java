package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonIncomeSurvey;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonIncomeSurveyFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusPersonIncomeSurveyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 28 09:27:50 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonIncomeSurvey")
public class MfCusPersonIncomeSurveyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonIncomeSurveyBo
	@Autowired
	private MfCusPersonIncomeSurveyFeign mfCusPersonIncomeSurveyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;// 获取申请信息中的时间信息
	//全局变量
	private String query = "";//初始化query为空

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonIncomeSurvey_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		try {
			mfCusPersonIncomeSurvey.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonIncomeSurvey.setCriteriaList(mfCusPersonIncomeSurvey, ajaxData);// 我的筛选
			// mfCusPersonIncomeSurvey.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusPersonIncomeSurvey,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonIncomeSurvey", mfCusPersonIncomeSurvey));
			ipage = mfCusPersonIncomeSurveyFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo((String) map.get("cusNo"));
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonIncomeSurveyAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncomeSurveyAction表单信息没有查询到");
		} else {
			try {
				FormData formcusPersonIncomeSurveyBase = formService.getFormData(formId);
				getFormValue(formcusPersonIncomeSurveyBase, getMapByJson(ajaxData));
				if (this.validateFormData(formcusPersonIncomeSurveyBase)) {
					MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
					setObjValue(formcusPersonIncomeSurveyBase, mfCusPersonIncomeSurvey);
					mfCusPersonIncomeSurveyFeign.insert(mfCusPersonIncomeSurvey);
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonIncomeSurvey.getCusNo(),
							mfCusPersonIncomeSurvey.getRelNo());// 更新资料完整度

					String detailFormId = mfCusFormConfigFeign
							.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonIncomeSurveyAction")
							.getShowModelDef();
					if (StringUtil.isEmpty(detailFormId)) {
						// logger.error("MfCusPersonIncomeSurveyAction的detailFormId找不到");
					}
					FormData formcusPersonIncomeSurveyDetail = formService.getFormData(detailFormId);
					getObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurvey);
					this.getHttpRequest().setAttribute("ifBizManger", "3");
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(formcusPersonIncomeSurveyDetail, "propertySeeTag", query);

					dataMap.put("mfCusCustomer", mfCusCustomer);
					dataMap.put("htmlStr", htmlStr);
					dataMap.put("htmlStrFlag", "1");
					dataMap.put("infIntegrity", infIntegrity);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());

					/*
					 * MfCusPersonIncomeSurvey mfCusPersonIncomeSurveyNew = new
					 * MfCusPersonIncomeSurvey();
					 * mfCusPersonIncomeSurveyNew.setCusNo(
					 * mfCusPersonIncomeSurvey.getCusNo());
					 * mfCusPersonIncomeSurveyNew.setRelNo(
					 * mfCusPersonIncomeSurvey.getRelNo()); Ipage ipage =
					 * this.getIpage(); JsonTableUtil jtu = new JsonTableUtil();
					 * String tableHtml =
					 * jtu.getJsonStr("tablecusPersonIncomeSurveyBase",
					 * "tableTag", (List<MfCusPersonIncomeSurvey>)
					 * mfCusPersonIncomeSurveyFeign.findByPage(ipage,
					 * mfCusPersonIncomeSurveyNew).getResult(), null,true);
					 * dataMap.put("mfCusCustomer", mfCusCustomer);
					 * dataMap.put("htmlStr", tableHtml);
					 * dataMap.put("htmlStrFlag","1");
					 * dataMap.put("infIntegrity",infIntegrity);
					 * dataMap.put("flag", "success"); dataMap.put("msg",
					 * MessageEnum.SUCCEED_INSERT.getMessage());
					 */
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
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusPersonIncomeSurveyDetail = formService.getFormData(formId);
		getFormValue(formcusPersonIncomeSurveyDetail, getMapByJson(ajaxData));
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurveyJsp = new MfCusPersonIncomeSurvey();
		setObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurveyJsp);
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign
				.getById(mfCusPersonIncomeSurveyJsp);
		if (mfCusPersonIncomeSurvey != null) {
			try {
				mfCusPersonIncomeSurvey = (MfCusPersonIncomeSurvey) EntityUtil.reflectionSetVal(mfCusPersonIncomeSurvey,
						mfCusPersonIncomeSurveyJsp, getMapByJson(ajaxData));
				mfCusPersonIncomeSurveyFeign.update(mfCusPersonIncomeSurvey);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		try {
			FormData formcusPersonIncomeSurveyDetail = formService.getFormData("cusPersonIncomeSurveyDetail");
			getFormValue(formcusPersonIncomeSurveyDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcusPersonIncomeSurveyDetail)) {
				setObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurvey);
				mfCusPersonIncomeSurveyFeign.update(mfCusPersonIncomeSurvey);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonIncomeSurvey.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				/*MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonIncomeSurveyAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}*/
				getFormValue(formcusPersonIncomeSurveyDetail);
				getObjValue(formcusPersonIncomeSurveyDetail, mfCusCustomer);
				getObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurvey);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusPersonIncomeSurveyDetail,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String surveyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusPersonIncomeSurveyDetail = formService.getFormData("cusPersonIncomeSurveyDetail");
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setSurveyId(surveyId);
		mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign.getById(mfCusPersonIncomeSurvey);
		getObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurvey, formData);
		if (mfCusPersonIncomeSurvey != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看(修改后回调)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByAppIdAjax")
	@ResponseBody
	public Map<String, Object> getByAppIdAjax(String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusPersonIncomeSurveyDetail = formService.getFormData("cusPersonIncomeSurveyDetail");
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setRelNo(relNo);
		mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign.getById(mfCusPersonIncomeSurvey);
		if (mfCusPersonIncomeSurvey != null) {
			dataMap.put("incomeSurvey", mfCusPersonIncomeSurvey);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String surveyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setSurveyId(surveyId);
		try {
			mfCusPersonIncomeSurveyFeign.delete(mfCusPersonIncomeSurvey);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		FormData formcusPersonIncomeSurveyBase = null;
		ActionContext.initialize(request, response);
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setCusNo(cusNo);
		mfCusPersonIncomeSurvey.setRelNo(relNo);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(relNo);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		// String appTime = mfBusApply.getAppTime();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonIncomeSurveyAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncomeSurveyAction表单信息没有查询到");
		} else {
			formcusPersonIncomeSurveyBase = formService.getFormData(formId);
			if (formcusPersonIncomeSurveyBase.getFormId() == null) {
				// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncomeSurveyAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusPersonIncomeSurveyBase);
				mfCusPersonIncomeSurvey.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusPersonIncomeSurveyBase, mfCusPersonIncomeSurvey);
			}
		}
		model.addAttribute("formcusPersonIncomeSurveyBase", formcusPersonIncomeSurveyBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncomeSurvey_Insert";
	}

	/**
	 * 新增页面(业务)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusPersonIncomeSurveyBase = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setCusNo(cusNo);
		mfCusPersonIncomeSurvey.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(relNo);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		String appTime = mfBusApply.getAppTime();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonIncomeSurveyAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("产品类型为"+kindNo+"的MfCusPersonIncomeSurveyAction表单信息没有查询到");
		} else {
			formcusPersonIncomeSurveyBase = formService.getFormData(formId);
			if (formcusPersonIncomeSurveyBase.getFormId() == null) {
				// log.error("产品类型为"+kindNo+"的MfCusPersonIncomeSurveyAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusPersonIncomeSurveyBase);
				mfCusPersonIncomeSurvey.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusPersonIncomeSurveyBase, mfCusPersonIncomeSurvey);
			}
		}
		model.addAttribute("formcusPersonIncomeSurveyBase", formcusPersonIncomeSurveyBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncomeSurvey_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusPersonIncomeSurveyBase = formService.getFormData("cusPersonIncomeSurveyBase");
		getFormValue(formcusPersonIncomeSurveyBase);
		MfCusPersonIncomeSurvey  mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
		mfCusPersonIncomeSurvey.setCusNo(cusNo);
		mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign.getById(mfCusPersonIncomeSurvey);
		getObjValue(formcusPersonIncomeSurveyBase, mfCusPersonIncomeSurvey);
		model.addAttribute("formcusPersonIncomeSurveyBase", formcusPersonIncomeSurveyBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonIncomeSurvey_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusPersonIncomeSurveyDetail = formService.getFormData("cusPersonIncomeSurveyDetail");
		getFormValue(formcusPersonIncomeSurveyDetail);
		boolean validateFlag = this.validateFormData(formcusPersonIncomeSurveyDetail);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusPersonIncomeSurveyDetail = formService.getFormData("cusPersonIncomeSurveyDetail");
		getFormValue(formcusPersonIncomeSurveyDetail);
		boolean validateFlag = this.validateFormData(formcusPersonIncomeSurveyDetail);
	}

	/**
	 * @Description:列表展示详情
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-11-29 上午10:16:01
	 */
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String surveyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonIncomeSurveyAction");
		String formId = "";
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonIncomeSurveyAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			FormData formcusPersonIncomeSurveyDetail = formService.getFormData(formId);
			MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey = new MfCusPersonIncomeSurvey();
			mfCusPersonIncomeSurvey.setSurveyId(surveyId);
			mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign.getById(mfCusPersonIncomeSurvey);
			if (mfCusPersonIncomeSurvey != null) {
				getObjValue(formcusPersonIncomeSurveyDetail, mfCusPersonIncomeSurvey, formData);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStrCorp = jsonFormUtil.getJsonStr(formcusPersonIncomeSurveyDetail, "propertySeeTag", "");
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusPersonIncomeSurvey);
		}
		return dataMap;
	}

}
