package app.component.cus.intangibleassets.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.intangibleassets.entity.MfCusIntangibleAssets;
import app.component.cus.intangibleassets.feign.MfCusIntangibleAssetsFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import org.apache.commons.lang.StringUtils;
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

/**
 * 客户模块-无形资产
 */
@Controller
@RequestMapping("/mfCusIntangibleAssets")
public class MfCusIntangibleAssetsController extends BaseFormBean {

	@Autowired
	private MfCusIntangibleAssetsFeign mfCusIntangibleAssetsFeign;

	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;


	/**
	 * 新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusIntangibleAssets mfCusIntangibleAssets = new MfCusIntangibleAssets();
		mfCusIntangibleAssets.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusIntangibleAssetsAction");
		FormData cusIntangibleAssetsBase = formService.getFormData(mfCusFormConfig.getAddModelDef());
		this.changeFormProperty(cusIntangibleAssetsBase, "cusNo", "initValue", mfCusIntangibleAssets.getCusNo());
		model.addAttribute("cusIntangibleAssetsBase", cusIntangibleAssetsBase);
		model.addAttribute("query", "");
		return "/component/cus/intangibleassets/MfCusIntangibleAssets_Insert";
	}

	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusIntangibleAssetsAction").getAddModel();
			}
			FormData cusIntangibleAssetsBase = formService.getFormData(formId);
			getFormValue(cusIntangibleAssetsBase, map);
			if (this.validateFormDataAnchor(cusIntangibleAssetsBase)) {
				MfCusIntangibleAssets mfCusIntangibleAssets = new MfCusIntangibleAssets();
				setObjValue(cusIntangibleAssetsBase, mfCusIntangibleAssets);
				mfCusIntangibleAssets.setIntangibleAssetsId(WaterIdUtil.getWaterId());
				MfCusIntangibleAssets mfCusIntangibleAssetsTemp = mfCusIntangibleAssetsFeign.getByAssetsTypeAndAssetsNo(mfCusIntangibleAssets);
				if(null != mfCusIntangibleAssetsTemp){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此无形资产种类下相同编号信息已存在!");
					return  dataMap;
				}
				mfCusIntangibleAssets = setAssetsNoValue(mfCusIntangibleAssets);
				mfCusIntangibleAssetsFeign.insert(mfCusIntangibleAssets);
				// 更新客户信息完整度
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusIntangibleAssets.getCusNo(),mfCusIntangibleAssets.getCusNo());
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusIntangibleAssets.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusIntangibleAssetsBaseList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusIntangibleAssetsAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				JsonTableUtil jtu = new JsonTableUtil();
				List<MfCusIntangibleAssets> list = mfCusIntangibleAssetsFeign.getByCusNo(mfCusIntangibleAssets.getCusNo());
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailByAjax")
	@ResponseBody
	public Map<String, Object> getDetailByAjax(String intangibleAssetsId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusIntangibleAssetsAction");
		String formId ="cusIntangibleAssetsBaseDetail";
		if(StringUtils.isNotEmpty(mfCusFormConfig.getShowModelDef())){
			formId = mfCusFormConfig.getShowModelDef();
		}
		Map<String, Object> formData = new HashMap<>();
		this.setIfBizManger("3");
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData cusIntangibleAssetsBase = formService.getFormData(formId);
		MfCusIntangibleAssets mfCusIntangibleAssets = mfCusIntangibleAssetsFeign.getById(intangibleAssetsId);
		getObjValue(cusIntangibleAssetsBase, mfCusIntangibleAssets, formData);
		String htmlStr = jsonFormUtil.getJsonStr(cusIntangibleAssetsBase, "propertySeeTag", "");
		dataMap.put("formHtml", htmlStr);
		dataMap.put("flag", "success");
		dataMap.put("formData", mfCusIntangibleAssets);
		return dataMap;
	}


	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		FormData cusIntangibleAssetsBase = formService.getFormData("cusIntangibleAssetsBase");
		getFormValue(cusIntangibleAssetsBase, getMapByJson(ajaxData));
		MfCusIntangibleAssets mfCusIntangibleAssetsTemp = new MfCusIntangibleAssets();
		setObjValue(cusIntangibleAssetsBase, mfCusIntangibleAssetsTemp);
		MfCusIntangibleAssets mfCusIntangibleAssets = mfCusIntangibleAssetsFeign.getById(mfCusIntangibleAssetsTemp.getIntangibleAssetsId());
		if (mfCusIntangibleAssets != null) {
			try {
				mfCusIntangibleAssets = (MfCusIntangibleAssets) EntityUtil.reflectionSetVal(mfCusIntangibleAssets, mfCusIntangibleAssetsTemp,
						getMapByJson(ajaxData));
				MfCusIntangibleAssets temp = mfCusIntangibleAssetsFeign.getByAssetsTypeAndAssetsNoExcludeSelf(mfCusIntangibleAssets);
				if(null != temp){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此无形资产种类下相同编号信息已存在!");
					return  dataMap;
				}
				mfCusIntangibleAssetsFeign.update(mfCusIntangibleAssets);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String intangibleAssetsId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusIntangibleAssets mfCusIntangibleAssets = mfCusIntangibleAssetsFeign.getById(intangibleAssetsId);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusIntangibleAssetsAction");
		String formId="cusIntangibleAssetsBase";
		if(mfCusFormConfig != null){
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusIntangibleAssetsBase = formService.getFormData(formId);
		getFormValue(formcusIntangibleAssetsBase);
		getObjValue(formcusIntangibleAssetsBase, mfCusIntangibleAssets);
		model.addAttribute("formcusIntangibleAssetsBase", formcusIntangibleAssetsBase);
		model.addAttribute("query", "");
		return "/component/cus/intangibleassets/MfCusIntangibleAssets_Edit";
	}

	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<>();
		MfCusIntangibleAssets mfCusIntangibleAssets = new MfCusIntangibleAssets();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			FormData formcusIntangibleAssetsBase = formService.getFormData(formId);
			getFormValue(formcusIntangibleAssetsBase,map);
			if(this.validateFormData(formcusIntangibleAssetsBase)){
				setObjValue(formcusIntangibleAssetsBase, mfCusIntangibleAssets);
				MfCusIntangibleAssets mfCusIntangibleAssetsTemp = mfCusIntangibleAssetsFeign.getByAssetsTypeAndAssetsNoExcludeSelf(mfCusIntangibleAssets);
				if(null != mfCusIntangibleAssetsTemp){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此无形资产种类下相同编号信息已存在!");
					return  dataMap;
				}
				mfCusIntangibleAssets = setAssetsNoValue(mfCusIntangibleAssets);
				mfCusIntangibleAssetsFeign.update(mfCusIntangibleAssets);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusIntangibleAssets.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusIntangibleAssetsBaseList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusIntangibleAssetsAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						mfCusIntangibleAssetsFeign.getByCusNo(mfCusIntangibleAssets.getCusNo()),null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> deleteAjax(String intangibleAssetsId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<>();
		try {
			mfCusIntangibleAssetsFeign.delete(intangibleAssetsId);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 给无形资产编号赋值
	 *
	 * @param mfCusIntangibleAssets 无形资产实体对象
	 * @return
	 */
	private MfCusIntangibleAssets setAssetsNoValue(MfCusIntangibleAssets mfCusIntangibleAssets){
		//1.专利信息 2.证书信息 3.软件著作权
		if("1".equals(mfCusIntangibleAssets.getAssetsType())){
			mfCusIntangibleAssets.setAssetsNo(mfCusIntangibleAssets.getPublicNo());
		}else if("2".equals(mfCusIntangibleAssets.getAssetsType())){
			mfCusIntangibleAssets.setAssetsNo(mfCusIntangibleAssets.getCertificateNo());
		}else if("3".equals(mfCusIntangibleAssets.getAssetsType())){
			mfCusIntangibleAssets.setAssetsNo(mfCusIntangibleAssets.getRegNo());
		}
		return mfCusIntangibleAssets;
	}
}