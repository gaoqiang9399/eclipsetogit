package app.component.interfaces.appinterface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.app.entity.MfBusApply;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.interfaces.appinterface.feign.AppCusCustomerFeign;

/**
 * 客户信息管理的Action类
 * 
 * @author zhang_dlei
 * @date 2017-06-15 下午5:58:30
 */
@Controller
@RequestMapping("/appCusCustomer")
public class AppCusCustomerController extends BaseFormBean {
	// 异步参数

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AppCusCustomerFeign appCusCustomerFeign;

	// 客户号

	/**
	 * 添加客户进件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCusInfoAjax")
	@ResponseBody
	public Map<String, Object> insertCusInfoAjax(MfCusCustomer cusInfo, MfCusPersBaseInfo persInfo,
			MfCusCorpBaseInfo corpInfo, MfBusApply busApply) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层服务
			dataMap = appCusCustomerFeign.insert(cusInfo, persInfo, corpInfo, busApply, request);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "添加客户进件信息异常");
			// logger.error("添加客户进件信息异常",e);
		}
		return dataMap;
	}

	/**
	 * 完善客户基本信息(个人和企业)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/completeBaseInfoAjax")
	@ResponseBody
	public Map<String, Object> completeBaseInfoAjax(MfCusPersBaseInfo persInfo, MfCusCorpBaseInfo corpInfo)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层服务
			if (null != persInfo) {
				dataMap = appCusCustomerFeign.updateForPersBase(persInfo);
			} else if (null != corpInfo) {
				dataMap = appCusCustomerFeign.updateForCorpBase(corpInfo);
			} else {
				dataMap.put("errorCode", "999999");
				dataMap.put("errorMsg", "请求参数为空");
			}
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "完善客户基本信息异常");
			// logger.error("完善客户基本信息异常",e);
		}
		return dataMap;
	}

	/**
	 * 完善客户其他信息(个人和企业)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/compeleteCusOtherInfoAjax")
	@ResponseBody
	public Map<String, Object> compeleteCusOtherInfoAjax(String ajaxData, String tableName) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层服务
			dataMap = appCusCustomerFeign.insertCusOtherInfo(tableName, ajaxData, request);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "完善客户基本信息异常");
//			logger.error("完善客户基本信息异常", e);
		}
		return dataMap;
	}

	/**
	 * 获取上传要件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDocInfoListAjax")
	@ResponseBody
	public Map<String, Object> getDocInfoListAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层服务
			dataMap = appCusCustomerFeign.getDocInfoList(ajaxData);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取上传要件列表异常");
//			logger.error("获取上传要件列表异常", e);
		}
		return dataMap;
	}

	/**
	 * 获取完善基本信息表单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusTableListAjax")
	@ResponseBody
	public Map<String, Object> getCusTableListAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 调用Bo层服务
			dataMap = appCusCustomerFeign.getMfCusTableList(cusNo);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取完善基本信息表单列表异常");
//			logger.error("获取完善基本信息表单列表异常", e);
		}
		return dataMap;
	}

}
