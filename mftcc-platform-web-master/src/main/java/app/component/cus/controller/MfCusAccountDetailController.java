package  app.component.cus.controller;
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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusAccountDetail;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAccountDetailFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusAccountDetailAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 17 12:17:45 CST 2018
 **/

@Controller
@RequestMapping("/mfCusAccountDetail")
public class MfCusAccountDetailController extends BaseFormBean{
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusAccountDetailFeign mfCusAccountDetailFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String relNo)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formAccountDetailBase = formService.getFormData("AccountDetailBase");
		MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
		mfCusAccountDetail.setRelNo(relNo);
		mfCusAccountDetail.setCusNo(cusNo);
		getObjValue(formAccountDetailBase, mfCusAccountDetail);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("formAccountDetailBase", formAccountDetailBase);
		return "/component/cus/MfCusAccountDetail_Insert";
	};
	
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formAccountDetailBase = formService.getFormData(formId);
			getFormValue(formAccountDetailBase, getMapByJson(ajaxData));
			if(this.validateFormData(formAccountDetailBase)){
				MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
				setObjValue(formAccountDetailBase, mfCusAccountDetail);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAccountDetail.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusAccountDetail.setCusName(mfCusCustomer.getCusName());
				mfCusAccountDetailFeign.insert(mfCusAccountDetail);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusAccountDetail.getCusNo(),
						mfCusAccountDetail.getRelNo());// 更新客户信息完整度
				String cusNo = mfCusAccountDetail.getCusNo();
				String relNo = mfCusAccountDetail.getRelNo();
				mfCusAccountDetail = new MfCusAccountDetail();
				mfCusAccountDetail.setCusNo(cusNo);
				mfCusAccountDetail.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusAccountDetail", mfCusAccountDetail));
				String tableHtml = jtu.getJsonStr("tableAccountDetailList", "tableTag",
						(List<MfCusAccountDetail>) mfCusAccountDetailFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
				return dataMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"mfCusAccountDetailAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
		
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formAccountDetail = formService.getFormData(formId);
			MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
			mfCusAccountDetail.setId(id);
			mfCusAccountDetail = mfCusAccountDetailFeign.getById(mfCusAccountDetail);
			getObjValue(formAccountDetail, mfCusAccountDetail, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formAccountDetail, "propertySeeTag", "");
			if (mfCusAccountDetail != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusAccountDetail);
		}
		return dataMap;
	}
	
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusjob00003 = formService.getFormData(formId);
		getFormValue(formcusjob00003, getMapByJson(ajaxData));
		MfCusAccountDetail mfCusAccountDetailNew = new MfCusAccountDetail();
		setObjValue(formcusjob00003, mfCusAccountDetailNew);
		mfCusAccountDetail.setId(mfCusAccountDetailNew.getId());
		mfCusAccountDetail = mfCusAccountDetailFeign.getById(mfCusAccountDetail);
		if (mfCusAccountDetail != null) {
			try {
				mfCusAccountDetail = (MfCusAccountDetail) EntityUtil.reflectionSetVal(mfCusAccountDetail, mfCusAccountDetailNew,
						getMapByJson(ajaxData));
				mfCusAccountDetailFeign.update(mfCusAccountDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formAccountDetailDetail = formService.getFormData("AccountDetailBase");
		getFormValue(formAccountDetailDetail);
		MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
		mfCusAccountDetail.setId(id);
		mfCusAccountDetail = mfCusAccountDetailFeign.getById(mfCusAccountDetail);
		getObjValue(formAccountDetailDetail, mfCusAccountDetail);
		model.addAttribute("formAccountDetailBase", formAccountDetailDetail);
		model.addAttribute("mfCusAccountDetail", mfCusAccountDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAccountDetail_Detail";
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
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusAccountDetailAction").getAddModel();
			}
			FormData formAccountDetailDetail = formService.getFormData(formId);
			getFormValue(formAccountDetailDetail, map);
			if (this.validateFormData(formAccountDetailDetail)) {
				MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
				setObjValue(formAccountDetailDetail, mfCusAccountDetail);
				mfCusAccountDetailFeign.update(mfCusAccountDetail);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAccountDetail.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tableAccountDetailList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusAccountDetailAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}

				String cusNo = mfCusAccountDetail.getCusNo();
				mfCusAccountDetail = new MfCusAccountDetail();
				mfCusAccountDetail.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusAccountDetail", mfCusAccountDetail));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusAccountDetail>) mfCusAccountDetailFeign.findByPage(ipage).getResult(), null, true);
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
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
		mfCusAccountDetail.setId(id);
		try {
			mfCusAccountDetailFeign.delete(mfCusAccountDetail);
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


}
