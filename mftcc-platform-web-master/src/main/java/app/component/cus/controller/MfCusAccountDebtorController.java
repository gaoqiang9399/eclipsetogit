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
import app.component.cus.entity.MfCusAccountDebtor;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAccountDebtorFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusAccountDebtorAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 17 12:15:38 CST 2018
 **/
@Controller
@RequestMapping("/mfCusAccountDebtor")
public class MfCusAccountDebtorController extends BaseFormBean{
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusAccountDebtorFeign mfCusAccountDebtorFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String relNo)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formAccountDebtorBase = formService.getFormData("AccountDebtorBase");
		MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
		mfCusAccountDebtor.setCusNo(cusNo);
		mfCusAccountDebtor.setRelNo(relNo);
		getObjValue(formAccountDebtorBase, mfCusAccountDebtor);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("mfCusAccountDebtor", mfCusAccountDebtor);
		model.addAttribute("formAccountDebtorBase", formAccountDebtorBase);
		return "/component/cus/MfCusAccountDebtor_Insert";
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
			FormData formAccountDebtorBase = formService.getFormData(formId);
			getFormValue(formAccountDebtorBase, getMapByJson(ajaxData));
			if(this.validateFormData(formAccountDebtorBase)){
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				setObjValue(formAccountDebtorBase, mfCusAccountDebtor);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAccountDebtor.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusAccountDebtor.setCusName(mfCusCustomer.getCusName());
				mfCusAccountDebtorFeign.insert(mfCusAccountDebtor);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusAccountDebtor.getCusNo(),
						mfCusAccountDebtor.getRelNo());// 更新客户信息完整度
				String cusNo = mfCusAccountDebtor.getCusNo();
				String relNo = mfCusAccountDebtor.getRelNo();
				mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setCusNo(cusNo);
				mfCusAccountDebtor.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusAccountDebtor", mfCusAccountDebtor));
				String tableHtml = jtu.getJsonStr("tableAccountDebtorList", "tableTag",
						(List<MfCusAccountDebtor>) mfCusAccountDebtorFeign.findByPage(ipage).getResult(), null,
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
					"mfCusAccountDebtorAction");
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
				FormData formAccountDebtorDetail = formService.getFormData(formId);
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setId(id);
				mfCusAccountDebtor = mfCusAccountDebtorFeign.getById(mfCusAccountDebtor);
				getObjValue(formAccountDebtorDetail, mfCusAccountDebtor, formData);
				String htmlStrCorp = jsonFormUtil.getJsonStr(formAccountDebtorDetail, "propertySeeTag", "");
				if (mfCusAccountDebtor != null) {
					dataMap.put("formHtml", htmlStrCorp);
					dataMap.put("flag", "success");
				} else {
					dataMap.put("msg", "获取详情失败");
					dataMap.put("flag", "error");
				}
				dataMap.put("formData", mfCusAccountDebtor);
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
			MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
			Map map = getMapByJson(ajaxData);
			formId = (String) map.get("formId");
			FormData formAccountDebtorBase = formService.getFormData(formId);
			getFormValue(formAccountDebtorBase, getMapByJson(ajaxData));
			MfCusAccountDebtor mfCusAccountDebtorNew = new MfCusAccountDebtor();
			setObjValue(formAccountDebtorBase, mfCusAccountDebtorNew);
			mfCusAccountDebtor.setId(mfCusAccountDebtorNew.getId());
			mfCusAccountDebtor = mfCusAccountDebtorFeign.getById(mfCusAccountDebtor);
			if (mfCusAccountDebtor != null) {
				try {
					mfCusAccountDebtor = (MfCusAccountDebtor) EntityUtil.reflectionSetVal(mfCusAccountDebtor, mfCusAccountDebtorNew,
							getMapByJson(ajaxData));
					mfCusAccountDebtorFeign.update(mfCusAccountDebtor);
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
		
		@RequestMapping(value = "/getAll")
		@ResponseBody
		public Map<String, Object> getAll(String cusNo) throws Exception {
			ActionContext.initialize(request, response);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
			mfCusAccountDebtor.setCusNo(cusNo);
			List<MfCusAccountDebtor> mfCusAccountDebtorList = mfCusAccountDebtorFeign
					.getAll(mfCusAccountDebtor);
			JSONArray array = new JSONArray();
			if (mfCusAccountDebtorList != null && mfCusAccountDebtorList.size() > 0) {
				for (int i = 0; i < mfCusAccountDebtorList.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("id", mfCusAccountDebtorList.get(i).getId());
					obj.put("name", mfCusAccountDebtorList.get(i).getDebtorName());
					array.add(obj);
				}
				dataMap.put("mfCusAssureCompanyList", mfCusAccountDebtorList);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
			dataMap.put("items", array.toString());
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
		FormData formAccountDebtorBase = formService.getFormData("AccountDebtorBase");
		getFormValue(formAccountDebtorBase);
		MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
		mfCusAccountDebtor.setId(id);
		mfCusAccountDebtor = mfCusAccountDebtorFeign.getById(mfCusAccountDebtor);
		getObjValue(formAccountDebtorBase, mfCusAccountDebtor);
		model.addAttribute("formAccountDebtorBase", formAccountDebtorBase);
		model.addAttribute("mfCusAccountDebtor", mfCusAccountDebtor);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAccountDebtor_Detail";
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusAccountDebtorAction").getAddModel();
			}
			FormData formAccountDebtorBase = formService.getFormData(formId);
			getFormValue(formAccountDebtorBase, map);
			if (this.validateFormData(formAccountDebtorBase)) {
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				setObjValue(formAccountDebtorBase, mfCusAccountDebtor);
				mfCusAccountDebtorFeign.update(mfCusAccountDebtor);

				String cusNo = mfCusAccountDebtor.getCusNo();
				mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusAccountDebtor", mfCusAccountDebtor));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tableAccountDebtorList", "tableTag",
						(List<MfCusAccountDebtor>) mfCusAccountDebtorFeign.findByPage(ipage).getResult(), null, true);
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
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
		mfCusAccountDebtor.setId(id);
		try {
			mfCusAccountDebtorFeign.delete(mfCusAccountDebtor);
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
