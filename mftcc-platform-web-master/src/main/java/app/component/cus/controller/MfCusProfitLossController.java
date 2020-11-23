package app.component.cus.controller;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusProfitLoss;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusProfitLossFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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

/**
 * Title: MfCusProfitLossController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 28 10:58:22 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusProfitLoss")
public class MfCusProfitLossController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusProfitLossFeign mfCusProfitLossFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusProfitLoss_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
		try {
			mfCusProfitLoss.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusProfitLoss.setCriteriaList(mfCusProfitLoss, ajaxData);//我的筛选
			//mfCusProfitLoss.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusProfitLoss,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
			//自定义查询Feign方法
			ipage = mfCusProfitLossFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
			getFormValue(formcusprofitlossDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusprofitlossDetail)){
				MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
				setObjValue(formcusprofitlossDetail, mfCusProfitLoss);
				mfCusProfitLossFeign.insert(mfCusProfitLoss);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
		getFormValue(formcusprofitlossDetail, getMapByJson(ajaxData));
		MfCusProfitLoss mfCusProfitLossJsp = new MfCusProfitLoss();
		setObjValue(formcusprofitlossDetail, mfCusProfitLossJsp);
		MfCusProfitLoss mfCusProfitLoss = mfCusProfitLossFeign.getById(mfCusProfitLossJsp);
		if(mfCusProfitLoss!=null){
			try{
				mfCusProfitLoss = (MfCusProfitLoss)EntityUtil.reflectionSetVal(mfCusProfitLoss, mfCusProfitLossJsp, getMapByJson(ajaxData));
				mfCusProfitLossFeign.update(mfCusProfitLoss);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String , Object> map = getMapByJson(ajaxData);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo((String)map.get("cusNo"));
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusProfitLossAction");
		String formId="";
		if(mfCusFormConfig == null){

		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
			try{
				FormData formcusprofitlossBase = formService.getFormData(formId);
				getFormValue(formcusprofitlossBase, getMapByJson(ajaxData));
				if(this.validateFormData(formcusprofitlossBase)){
					MfCusProfitLoss 	mfCusProfitLoss = new MfCusProfitLoss();
					setObjValue(formcusprofitlossBase, mfCusProfitLoss);
					mfCusProfitLossFeign.update(mfCusProfitLoss);
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusProfitLoss.getCusNo(),mfCusProfitLoss.getCusNo());//更新客户信息完整度
					mfCusProfitLoss.setCusNo(mfCusCustomer.getCusNo());
					Ipage ipage = this.getIpage();
					JsonTableUtil jtu = new JsonTableUtil();
					ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
					String  tableHtml = jtu.getJsonStr("tablecusprofitlossBase","tableTag", (List<MfCusProfitLoss>)mfCusProfitLossFeign.findByPage(ipage).getResult(), null,true);
					dataMap.put("mfCusCustomer", mfCusCustomer);
					dataMap.put("htmlStr", tableHtml);
					dataMap.put("htmlStrFlag","1");
					dataMap.put("infIntegrity",infIntegrity);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
		MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
		mfCusProfitLoss.setId(id);
		mfCusProfitLoss = mfCusProfitLossFeign.getById(mfCusProfitLoss);
		getObjValue(formcusprofitlossDetail, mfCusProfitLoss,formData);
		if(mfCusProfitLoss!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
		mfCusProfitLoss.setId(id);
		try {
			mfCusProfitLossFeign.delete(mfCusProfitLoss);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
		model.addAttribute("formcusprofitlossDetail", formcusprofitlossDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitLoss_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusProfitLoss.setCusNo(cusNo);
		mfCusProfitLoss.setId(id);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusProfitLossAction");
		String formId="";
		FormData  formcusprofitlossBase=null;
		if(mfCusFormConfig == null){

		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
			FormData 	formcusliabilitiesBase = formService.getFormData(formId);
			if(formcusliabilitiesBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单form"+formId+".xml文件不存在");
			}else{
				formcusprofitlossBase = formService.getFormData(formId);
				getFormValue(formcusprofitlossBase);
				mfCusProfitLoss = mfCusProfitLossFeign.getById(mfCusProfitLoss);
				getObjValue(formcusprofitlossBase, mfCusProfitLoss);
			}
		}
		model.addAttribute("formcusprofitlossBase", formcusprofitlossBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitLoss_Detail";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoSaveSunYi")
	@ResponseBody
	public Map<String, Object> autoSaveSunYi(String appId,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusProfitLoss mfCusProfitLossQuery = new MfCusProfitLoss();
			mfCusProfitLossQuery.setAppId(appId);
			mfCusProfitLossQuery.setCusNo(cusNo);
			mfCusProfitLossQuery.setOpNo(User.getRegNo(request));
			mfCusProfitLossFeign.delete(mfCusProfitLossQuery);
			MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();

			mfCusProfitLoss.setCusNo(mfCusCustomer.getCusNo());
			mfCusProfitLoss.setOpNo(User.getRegNo(request));
			mfCusProfitLoss.setCusName(mfCusCustomer.getCusName());
			mfCusProfitLoss.setAppId(appId);

			mfCusProfitLoss = mfCusProfitLossFeign.autoCalc(mfCusProfitLoss);
			mfCusProfitLossFeign.insert(mfCusProfitLoss);
			String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusProfitLoss.getCusNo(),mfCusProfitLoss.getCusNo());//更新客户信息完整度
			mfCusProfitLoss.setCusNo(mfCusCustomer.getCusNo());
			Ipage ipage = this.getIpage();
			JsonTableUtil jtu = new JsonTableUtil();
			ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
			String  tableHtml = jtu.getJsonStr("tablecusprofitlossBase","tableTag", (List<MfCusProfitLoss>)mfCusProfitLossFeign.findByPage(ipage).getResult(), null,true);
			dataMap.put("mfCusCustomer", mfCusCustomer);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("htmlStrFlag","1");
			dataMap.put("infIntegrity",infIntegrity);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
		getFormValue(formcusprofitlossDetail);
		boolean validateFlag = this.validateFormData(formcusprofitlossDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusprofitlossDetail = formService.getFormData("cusprofitlossDetail");
		getFormValue(formcusprofitlossDetail);
		boolean validateFlag = this.validateFormData(formcusprofitlossDetail);
	}
}
