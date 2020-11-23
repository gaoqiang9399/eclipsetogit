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

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusAssureOutside;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGoods;
import app.component.cus.feign.MfCusAssureOutsideFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusAssureOutsideAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 28 11:34:05 CST 2017
 **/
@Controller
@RequestMapping("/mfCusAssureOutside")
public class MfCusAssureOutsideController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusAssureOutsideFeign mfCusAssureOutsideFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage( ) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusAssureOutside_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		try {
			mfCusAssureOutside.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAssureOutside.setCriteriaList(mfCusAssureOutside, ajaxData);// 我的筛选
			// mfCusAssureOutside.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusAssureOutside,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusAssureOutside", mfCusAssureOutside));
			ipage = mfCusAssureOutsideFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			getFormValue(formcusAssOutsideBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusAssOutsideBase)) {
				MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
				setObjValue(formcusAssOutsideBase, mfCusAssureOutside);
				mfCusAssureOutsideFeign.insert(mfCusAssureOutside);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusAssureOutside.getCusNo(),
						mfCusAssureOutside.getRelNo());// 更新客户信息完整度
				JsonTableUtil jtu = new JsonTableUtil();
				String cusNo = mfCusAssureOutside.getCusNo();
				String relNo = mfCusAssureOutside.getRelNo();
				Ipage ipage = this.getIpage();
				mfCusAssureOutside = new MfCusAssureOutside();
				mfCusAssureOutside.setCusNo(cusNo);
				mfCusAssureOutside.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusAssureOutside", mfCusAssureOutside));
				String tableHtml = jtu.getJsonStr("tablecusAssOutsideList", "tableTag",
						(List<MfCusGoods>) mfCusAssureOutsideFeign.findByPage(ipage).getResult(),
						null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String cusNo,String pageView,String relNo,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		String formId="";
		String query="";
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = null;
		if ("busView".equals(pageView)) {
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(relNo);
			String kindNo = mfBusApply.getKindNo();
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusAssureOutsideAction");
		} else {
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusAssureOutsideAction");
		}
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
			mfCusAssureOutside.setId(id);
			mfCusAssureOutside = mfCusAssureOutsideFeign.getById(mfCusAssureOutside);
			getObjValue(formcusAssOutsideBase, mfCusAssureOutside, formData);
			String htmlStr = jsonFormUtil.getJsonStr(formcusAssOutsideBase, "propertySeeTag", query);
			if (mfCusAssureOutside != null) {
				dataMap.put("formHtml", htmlStr);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusAssureOutside);
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
	public Map<String, Object> updateAjaxByOne(String formId,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusAssOutsideBase = formService.getFormData(formId);
		getFormValue(formcusAssOutsideBase, getMapByJson(ajaxData));
		MfCusAssureOutside mfCusAssureOutsideJsp = new MfCusAssureOutside();
		setObjValue(formcusAssOutsideBase, mfCusAssureOutsideJsp);
		MfCusAssureOutside mfCusAssureOutside = mfCusAssureOutsideFeign.getById(mfCusAssureOutsideJsp);
		if (mfCusAssureOutside != null) {
			try {
				mfCusAssureOutside = (MfCusAssureOutside) EntityUtil.reflectionSetVal(mfCusAssureOutside,
						mfCusAssureOutsideJsp, getMapByJson(ajaxData));
				mfCusAssureOutsideFeign.update(mfCusAssureOutside);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusAssOutsideBase = formService.getFormData("cusAssOutsideBase");
			getFormValue(formcusAssOutsideBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusAssOutsideBase)) {
				MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
				setObjValue(formcusAssOutsideBase, mfCusAssureOutside);
				mfCusAssureOutsideFeign.update(mfCusAssureOutside);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusAssOutsideBase = formService.getFormData("cusAssOutsideBase");
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		mfCusAssureOutside.setId(id);
		mfCusAssureOutside = mfCusAssureOutsideFeign.getById(mfCusAssureOutside);
		getObjValue(formcusAssOutsideBase, mfCusAssureOutside, formData);
		if (mfCusAssureOutside != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		mfCusAssureOutside.setId(id);
		try {
			mfCusAssureOutsideFeign.delete(mfCusAssureOutside);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	public String input(Model model,String cusNo,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		String formId="";
		mfCusAssureOutside.setCusNo(cusNo);
		mfCusAssureOutside.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusAssureOutsideAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			if (formcusAssOutsideBase.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formcusAssOutsideBase);
				mfCusAssureOutside.setCusName(mfCusCustomer.getCusName());
				mfCusAssureOutside.setCusNo(cusNo);
				mfCusAssureOutside.setOpName(User.getRegName(request));
				mfCusAssureOutside.setOpNo(User.getRegNo(request));
				getObjValue(formcusAssOutsideBase, mfCusAssureOutside);
			}
			model.addAttribute("formcusAssOutsideBase", formcusAssOutsideBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusAssureOutside_Insert";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		String formId="";
		mfCusAssureOutside.setCusNo(cusNo);
		mfCusAssureOutside.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusAssureOutsideAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			if (formcusAssOutsideBase.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusAssOutsideBase);
				mfCusAssureOutside.setCusName(mfCusCustomer.getCusName());
				mfCusAssureOutside.setCusNo(cusNo);
				mfCusAssureOutside.setOpName(User.getRegName(request));
				mfCusAssureOutside.setOpNo(User.getRegNo(request));
				getObjValue(formcusAssOutsideBase, mfCusAssureOutside);
			}
			model.addAttribute("formcusAssOutsideBase", formcusAssOutsideBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusAssureOutside_Insert";
	}

	@RequestMapping(value = "/getMfAssureListAjax")
	@ResponseBody
	public Map<String, Object> getMfAssureListAjax(String cusNo,int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.putParams("cusNo", cusNo);
			ipage = mfCusAssureOutsideFeign.getMfAssureList(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcus000123123 = formService.getFormData("cus000123123");
		getFormValue(formcus000123123);
		MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
		mfCusAssureOutside.setId(id);
		mfCusAssureOutside = mfCusAssureOutsideFeign.getById(mfCusAssureOutside);
		getObjValue(formcus000123123, mfCusAssureOutside);
		model.addAttribute("formcus000123123", formcus000123123);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssureOutside_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusAssOutsideBase = formService.getFormData("cusAssOutsideBase");
		getFormValue(formcusAssOutsideBase);
		boolean validateFlag = this.validateFormData(formcusAssOutsideBase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusAssOutsideBase = formService.getFormData("cusAssOutsideBase");
		getFormValue(formcusAssOutsideBase);
		boolean validateFlag = this.validateFormData(formcusAssOutsideBase);
	}
}
