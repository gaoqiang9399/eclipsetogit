package app.component.ncfgroup.appinterface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.interfaces.mobileinterface.entity.VmCifCustomerDTO;
import app.component.ncfgroup.appinterface.feign.WxMfCusPersBaseInfoFeign;

/**
 * Title: MfSysKindAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/wxMfCusPersBaseInfo")
public class WxMfCusPersBaseInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入appMfCusPersBaseInfoBo
	@Autowired
	private WxMfCusPersBaseInfoFeign wxMfCusPersBaseInfoFeign;
	//全局变量
	/**
	 * 用来验证蚂蚁信用的账号类型，身份证类型还是手机类型
	 * 1：身份证类型，2：手机类型，默认1
	 */
	//异步参数
	
	/***
	 * 获取个人客户基本信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getBaseInfoAjax")
	public Map<String, Object> getBaseInfoAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.getBaseInfo(cusNo);
		} catch (Exception e) {
			//logger.error("移动端获取客户基本信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "获取客户基本信息出错");
		}
		return dataMap;
	}
	
	
	/***
	 * 插入或更新个人客户基本信息
	 * @param verifyNum 
	 * @param cifcus 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBaseInfoAjax")
	public Map<String, Object> updateBaseInfoAjax(@RequestBody VmCifCustomerDTO cifcus, String verifyNum) throws Exception {

		
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try {
			dataMap = wxMfCusPersBaseInfoFeign.updateBaseInfo(cifcus, verifyNum);
		} catch (Exception e) {
			//logger.error("移动端 插入或更新客户基本信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 插入或更新客户基本信息出错");
		}
		return dataMap;
	}
	
	/***
	 * 插入或更新个人客户基本信息（汇达贷）
	 * @param cifcus 
	 * @param verifyNum 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBaseInfoNewAjax")
	public Map<String, Object> updateBaseInfoNewAjax(@RequestBody VmCifCustomerDTO cifcus, String verifyNum) throws Exception {
		
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.updateBaseInfoNew(cifcus, verifyNum);
		} catch (Exception e) {
			//logger.error("移动端 插入或更新客户基本信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 插入或更新客户基本信息出错");
		}
		return dataMap;
	}
	
	/***
	 * 插入或更新个人客户基本信息(无三方验证)
	 * @param cifcus 
	 * @param verifyNum 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBaseInfoForNoValidationAjax")
	public Map<String, Object> updateBaseInfoForNoValidationAjax(@RequestBody VmCifCustomerDTO cifcus, String verifyNum) throws Exception {

		
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try {
			dataMap = wxMfCusPersBaseInfoFeign.updateBaseInfoForNoValidation(cifcus, verifyNum);
		} catch (Exception e) {
			//logger.error("移动端 插入或更新客户基本信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 插入或更新客户基本信息出错");
		}
		return dataMap;
	}
	
	/***
	 * 插入或更新个人客户职业信息
	 * @param jobInfo 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateJobInfoAjax")
	public Map<String, Object> updateJobInfoAjax(@RequestBody MfCusPersonJob jobInfo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.updateJobInfo(jobInfo);
		} catch (Exception e) {
			//logger.error("移动端 插入或更新客户职业信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 插入或更新客户职业信息出错");
		}
		return dataMap;
	}
	
	/***
	 * 获取个人客户职业信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getJobInfoAjax")
	public Map<String, Object> getJobInfoAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.getJobInfo(cusNo);
		} catch (Exception e) {
			//logger.error("移动端获取个人客户职业信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "获取客户工作信息出错");
		}
		return dataMap;
	}
	/**
	 * AJAX插入或更新信用卡账户信息（东风贷）
	 * @author zhang_dlei
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCreditCardInfoAjax")
	public Map<String, Object> updateCreditCardInfoAjax(String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try{
			Gson gson =new  Gson();
			Map<String,Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String,String>>(){}.getType());//getMapByJson(ajaxData);
			//插入或更新 银行卡信息
			dataMap = wxMfCusPersBaseInfoFeign.updateCreditCardInfo(map);
		}catch(Exception e){
			//logger.error("插入银行信息错误",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}
	
	/**
	 * AJAX插入或更新信用卡账户信息(汇达贷)
	 * @author zhang_dlei
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCreditCardInfoForHdAjax")
	@ResponseBody
	public Map<String, Object> updateCreditCardInfoForHdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try{
			Gson gson =new  Gson();
			Map<String,Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String,String>>(){}.getType());//getMapByJson(ajaxData);
			//插入或更新 银行卡信息
			dataMap = wxMfCusPersBaseInfoFeign.updateCreditCardInfoForHd(map);
		}catch(Exception e){
			//logger.error("插入银行信息错误",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}
	
	/***
	 *  插入或更新个人客户家庭信息(资产)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAssetsInfoAjax")
	public Map<String, Object> updateAssetsInfoAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {

			dataMap = wxMfCusPersBaseInfoFeign.updateAssetsInfo(ajaxData);
		} catch (Exception e) {
			//logger.error("移动端插入或更新客户家庭信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "插入或更新客户家庭信息出错");
		}
		return dataMap;
	}
	
	/***
	 * 获取个人客户家庭信息(资产)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAssetsInfoAjax")
	public Map<String, Object> getAssetsInfoAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.getAssetsInfo(cusNo);
		} catch (Exception e) {
			//logger.error("移动端获取客户家庭信息出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取客户家庭信息出错");
		}
		return dataMap;
	}
	
		
	
	/***
	 * 获取 个人客户学历信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getEducationInfoAjax")
	public Map<String, Object> getEducationInfoAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.getEducationInfo(cusNo);
		} catch (Exception e) {
			//logger.error("移动端获取 客户学历信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "获取 客户学历信息出错");
		}
		return dataMap;
	}
	
	
	/***
	 *  更新个人客户学历信息
	 * @param education 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEducationInfoAjax")
	public Map<String, Object> updateEducationInfoAjax(@RequestBody MfCusPersBaseInfo education) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.updateEducationInfo(education);
		} catch (Exception e) {
			//logger.error("移动端更新客户学历信息出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", " 更新客户学历信息出错");
		}
		return dataMap;
	}
	
	/***
	 *  验证芝麻信用、运营商、公积金等是否验证通过
	 * @param cusNo 
	 * @param method 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkMfThirdServiceInfoAjax")
	@ResponseBody
	public Map<String, Object> checkMfThirdServiceInfoAjax(String cusNo, String method) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = wxMfCusPersBaseInfoFeign.checkMfThirdServiceInfo(cusNo, method);
		} catch (Exception e) {
		//	logger.error("验证芝麻信用、运营商、公积金等是否验证通过出错",e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "验证芝麻信用、运营商、公积金等是否验证通过出错");
		}
		return dataMap;
	}
	
}
