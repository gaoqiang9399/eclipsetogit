package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.hzey.entity.MfBusIcloudManage;
import app.component.interfaces.mobileinterface.feign.AppMfCusPersBaseInfoFeign;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfSysKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/appMfCusPersBaseInfo")
public class AppMfCusPersBaseInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入appMfCusPersBaseInfoBo
	@Autowired
	private AppMfCusPersBaseInfoFeign appMfCusPersBaseInfoFeign;
	// 全局变量
	/**
	 * 用来验证蚂蚁信用的账号类型，身份证类型还是手机类型 1：身份证类型，2：手机类型，默认1
	 */
	// 异步参数

	/***
	 * 查询个人客户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appSelectAjax")
	@ResponseBody
	public Map<String, Object> appSelectAjax(String cusNo, String cusTel) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.getByCusNoOrTel(cusNo, cusTel);
		} catch (Exception e) {
			// logger.error("移动端查询个人客户基本信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "查询个人客户基本信息出错");
		}
		return dataMap;
	}

	/***
	 * 查询个人客户base64图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIdCardPictureAjax")
	@ResponseBody
	public Map<String, Object> getIdCardPictureAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.getIdCardPicture(cusNo);
		} catch (Exception e) {
			// logger.error("查询个人客户base64图片出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "查询个人客户base64图片出错");
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: getIdCardIsOk @Description:
	 *         获取身份证资料是否完善 @param @return @param @throws Exception 参数 @return
	 *         String 返回类型 @throws
	 */
	@RequestMapping(value = "/getIdCardIsOk")
	@ResponseBody
	public Map<String, Object> getIdCardIsOk(Model model, String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.getIdCardIsOk(cusNo);
		} catch (Exception e) {
			// logger.error("获取身份证资料是否完善出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "获取身份证资料是否完善出错");
		}
		return dataMap;
	}

	/***
	 * 查询个人客户基本信息 (微信调用)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appSelectForWxAjax")
	@ResponseBody
	public Map<String, Object> appSelectForWxAjax(String cusNo, String cusTel) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.getByCusNoOrTelForWx(cusNo, cusTel);
		} catch (Exception e) {
			// logger.error("移动端查询个人客户基本信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "查询个人客户基本信息出错");
		}
		return dataMap;
	}

	/***
	 * 更新个人客户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appUpdateAjax")
	@ResponseBody
	public Map<String, Object> appUpdateAjax(MfCusPersBaseInfo cifcus) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.update(cifcus);
		} catch (Exception e) {
			// logger.error("更新个人客户基本信息出错", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "更新个人客户基本信息出错");
		}
		return dataMap;
	}

	/**
	 * 身份证OCR识别
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/completeInfoAjax")
	@ResponseBody
	public Map<String, Object> completeInfoAjax(String paramJson) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			// dataMap =
			// webCusLineRegFeign.completeCustomerInfo(cifcus,ServletActionContext.getRequest());
			String ORCInfo = appMfCusPersBaseInfoFeign.insert(paramJson);
			dataMap.put("errorCode", "00000");
			dataMap.put("data", ORCInfo);
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("移动端OCR调用异常", e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "OCR调用异常");
		}
		return dataMap;
	}

	/**
	 * 保存紧急联系人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCusFamilyInfoAjax")
	@ResponseBody
	public Map<String, Object> saveCusFamilyInfoAjax(MfCusFamilyInfo cusFamilyInfo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			if (StringUtil.isEmpty(cusFamilyInfo.getId())) {
				dataMap = appMfCusPersBaseInfoFeign.insertCusFamilyInfo("mf_cus_family_info", cusFamilyInfo, request);
			} else {
				dataMap = appMfCusPersBaseInfoFeign.updateCusFamilyInfo("mf_cus_family_info", cusFamilyInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("保存紧急联系人信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "保存紧急联系人信息异常");
		}
		return dataMap;
	}

	/**
	 * 获取紧急联系人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusFamilyInfoAjax")
	@ResponseBody
	public Map<String, Object> getCusFamilyInfoAjax(String cusNo, String cusTel) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = appMfCusPersBaseInfoFeign.selectCusFamilyInfo("mf_cus_family_info", cusNo, cusTel);

		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(" 获取紧急联系人信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取紧急联系人信息异常");
		}
		return dataMap;
	}

	/**
	 * 验证紧急联系人信息是否完善
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCusFamilyInfoAjax")
	@ResponseBody
	public Map<String, Object> validateCusFamilyInfoAjax(String cusNo, String cusTel) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = appMfCusPersBaseInfoFeign.validateCusFamilyInfo("mf_cus_family_info", cusNo, cusTel);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(" 获取紧急联系人信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取紧急联系人信息异常");
		}
		return dataMap;
	}

	/**
	 * 获取iCloud账号信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIcloudInfoAjax")
	@ResponseBody
	public Map<String, Object> getIcloudInfoAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = appMfCusPersBaseInfoFeign.getIcloudInfo(cusNo);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("获取iCloud账号信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取iCloud账号信息异常");
		}
		return dataMap;
	}

	/**
	 * 更新iCloud账号信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateIcloudInfoAjax")
	@ResponseBody
	public Map<String, Object> updateIcloudInfoAjax(MfBusIcloudManage icloudManage) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = appMfCusPersBaseInfoFeign.updateIcloudInfo(icloudManage);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(" 更新iCloud账号信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "更新iCloud账号信息异常");
		}
		return dataMap;
	}

	/**
	 * 获取客户芝麻信用是否满足基本分数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getZhimaCreditAjax")
	@ResponseBody
	public Map<String, Object> getZhimaCreditAjax(String cusNo, String accountType) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfCusPersBaseInfoFeign.getZhimaCredit(cusNo, accountType);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("获取蚂蚁信用异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取蚂蚁信用异常");
		}
		return dataMap;
	}

	/**
	 * 获取紧急联系人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusFamilyInfoByIdAjax")
	@ResponseBody
	public Map<String, Object> getCusFamilyInfoByIdAjax(String cusLinkId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo方法获取返回结果
			dataMap = appMfCusPersBaseInfoFeign.getCusFamilyInfoById(cusLinkId);

		} catch (Exception e) {
			e.printStackTrace();
//			logger.error(" 获取紧急联系人信息异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取紧急联系人信息异常");
		}
		return dataMap;
	}

}
