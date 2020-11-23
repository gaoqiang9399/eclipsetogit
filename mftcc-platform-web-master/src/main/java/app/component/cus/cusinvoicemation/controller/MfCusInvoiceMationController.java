package app.component.cus.cusinvoicemation.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mfCusInvoiceMation")
public class MfCusInvoiceMationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	
	
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/invoiceMation/MfCusInvoiceMation_List";
	}
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		try {
			mfCusInvoiceMation.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusInvoiceMation.setCriteriaList(mfCusInvoiceMation, ajaxData);//我的筛选
			mfCusInvoiceMation.setDelFlag(BizPubParm.YES_NO_N);
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
			//自定义查询Bo方法
			ipage = mfCusInvoiceMationFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByCusNoPageAjax")
	public Map<String, Object> findByCusNoPageAjax(Integer pageNo, Integer pageSize,
			String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		try {
			mfCusInvoiceMation.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusInvoiceMation.setCriteriaList(mfCusInvoiceMation, ajaxData);//我的筛选
			mfCusInvoiceMation.setDelFlag(BizPubParm.YES_NO_N);
			mfCusInvoiceMation.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
			//自定义查询Bo方法
			ipage = mfCusInvoiceMationFeign.findByPage(ipage);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 选择开票弹窗
	 * @author weisd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getList")
	public String getList(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("query", "");
		return "/component/cus/invoicemation/MfCusInvoiceMation_SelectList";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findSelectListByPageAjax")
	public Map<String, Object> findSelectListByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		try {
			mfCusInvoiceMation.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusInvoiceMation.setCriteriaList(mfCusInvoiceMation, ajaxData);//我的筛选
//			mfCusInvoiceMation.setDelFlag(BizPubParm.YES_NO_N);
			mfCusInvoiceMation.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
			//自定义查询Bo方法
			ipage = mfCusInvoiceMationFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @author weisd
	 * @param id
	 * @Description: 根据主键ID查询对象信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
			mfCusInvoiceMation.setId(id);
			mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
			dataMap.put("flag","success");
			dataMap.put("mfCusInvoiceMation",mfCusInvoiceMation);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		mfCusInvoiceMation.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusInvoiceMationAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			if (formcusCourtInfoBase.getFormId() == null) {
			} else {
				getFormValue(formcusCourtInfoBase);
				mfCusInvoiceMation.setCusName(mfCusCustomer.getCusName());
				List<MfCusInvoiceMation>  mfCusInvoiceMationList = mfCusInvoiceMationFeign.getAllList(mfCusInvoiceMation);
				if(mfCusInvoiceMationList!=null&&mfCusInvoiceMationList.size()>0){
				}else{
					mfCusInvoiceMation.setTaxpayerNo(mfCusCustomer.getIdNum());
				}



				getObjValue(formcusCourtInfoBase, mfCusInvoiceMation);
			}
			model.addAttribute("formcusCourtInfoBase", formcusCourtInfoBase);
		}
		model.addAttribute("query", "");
		return "/component/cus/invoicemation/MfCusInvoiceMation_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		mfCusInvoiceMation.setId(id);
		FormData formmfCusInvoiceMationBase = formService.getFormData("cusMeManageBase");
		String flag = "update";
		getFormValue(formmfCusInvoiceMationBase);
		mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
		getObjValue(formmfCusInvoiceMationBase, mfCusInvoiceMation);
		model.addAttribute("formmfCusInvoiceMationBase", formmfCusInvoiceMationBase);
		model.addAttribute("query", "");
		return "/component/cus/invoiceMation/MfCusInvoiceMation_Detail";
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
		try {
			
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusInvoiceMationAction").getAddModel();
			}
			FormData cusCourtInfoBase = formService.getFormData(formId);
			
			getFormValue(cusCourtInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusCourtInfoBase)) {
				MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
				mfCusInvoiceMation.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusCourtInfoBase, mfCusInvoiceMation);
				dataMap = mfCusInvoiceMationFeign.insert(mfCusInvoiceMation);
				String cusNo = mfCusInvoiceMation.getCusNo();
				String relNo = mfCusInvoiceMation.getCusNo();

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusInvoiceMation.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "mfCusInvoiceMation";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusInvoiceMationAction");				

				mfCusInvoiceMation = new MfCusInvoiceMation();
				mfCusInvoiceMation.setCusNo(cusNo);
//				MfCusInvoiceMation.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
				String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
						(List<MfCusInvoiceMation>) mfCusInvoiceMationFeign.findByPage(ipage).getResult(), null, true);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusInvoiceMation", mfCusInvoiceMation);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("DataFullFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = (String) getMapByJson(ajaxData).get("formId");
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusInvoiceMation mfCusInvoiceMationJsp = new MfCusInvoiceMation();
		setObjValue(formcusper00002, mfCusInvoiceMationJsp);
		MfCusInvoiceMation mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMationJsp);
		if(mfCusInvoiceMation!=null){
			try{
				mfCusInvoiceMation = (MfCusInvoiceMation) EntityUtil.reflectionSetVal(mfCusInvoiceMation, mfCusInvoiceMationJsp, getMapByJson(ajaxData));
				mfCusInvoiceMationFeign.update(mfCusInvoiceMation);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusInvoiceMationAction").getAddModel();
			}
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			getFormValue(formcusCourtInfoBase, map);
			if (this.validateFormData(formcusCourtInfoBase)) {

				setObjValue(formcusCourtInfoBase, mfCusInvoiceMation);
				mfCusInvoiceMationFeign.update(mfCusInvoiceMation);

				String cusNo = mfCusInvoiceMation.getCusNo();
				mfCusInvoiceMation.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
				String tableHtml = jtu.getJsonStr("tablecusMeManageBase", "tableTag",
						(List<MfCusInvoiceMation>) mfCusInvoiceMationFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
		MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
		mfCusInvoiceMation.setId(id);
		try {
			mfCusInvoiceMationFeign.delete(mfCusInvoiceMation);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusInvoiceMationAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusMeManage0003 = formService.getFormData(formId);
			MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
			mfCusInvoiceMation.setId(id);
			mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
			getObjValue(formcusMeManage0003, mfCusInvoiceMation, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusMeManage0003, "propertySeeTag", "");
			if (mfCusInvoiceMation != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusInvoiceMation);
		}
		return dataMap;
	}
}
