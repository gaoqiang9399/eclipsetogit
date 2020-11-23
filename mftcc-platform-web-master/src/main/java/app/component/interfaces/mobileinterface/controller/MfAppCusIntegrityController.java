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

import app.component.interfaces.mobileinterface.entity.MfAppCusIntegrity;
import app.component.interfaces.mobileinterface.feign.MfAppCusIntegrityFeign;

/**
 * Title: MfAppCusIntegrityAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Nov 11 10:50:04 CST 2017
 **/
@Controller
@RequestMapping("/mfAppCusIntegrity")
public class MfAppCusIntegrityController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppCusIntegrityBo
	@Autowired
	private MfAppCusIntegrityFeign mfAppCusIntegrityFeign;
	// 全局变量

	/**
	 * 根据客户号获取客户信息的完整信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppCusIntegrityAjxa")
	@ResponseBody
	public Map<String, Object> getAppCusIntegrityAjxa(Model model, String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppCusIntegrityFeign.getAppCusIntegrity(cusNo);
		} catch (Exception e) {
			// //logger.error("移动端插入融资信息出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户号检查客户信息的完整度
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateIntegrityAjxa")
	@ResponseBody
	public Map<String, Object> updateIntegrityAjxa(MfAppCusIntegrity mfAppCusIntegrity) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfAppCusIntegrityFeign.update(mfAppCusIntegrity);
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", "成功");
		} catch (Exception e) {
			// //logger.error("根据客户号检查客户信息的完整度出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户号检查客户信息的完整度(用于新的APP)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCheckIntegrityAjxa")
	@ResponseBody
	public Map<String, Object> doCheckIntegrityAjxa(String cusNo, String cheackItems) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppCusIntegrityFeign.doCheckIntegrity(cusNo, cheackItems);
		} catch (Exception e) {
			//logger.error("新App端：根据客户号检查客户信息的完整度出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户号检查客户信息的完整度(用于新的Paid)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCheckIntegrityForPaidAjxa")
	@ResponseBody
	public Map<String, Object> doCheckIntegrityForPaidAjxa(String cusNo, String cheackItems) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppCusIntegrityFeign.doCheckIntegrityForPaid(cusNo, cheackItems);
		} catch (Exception e) {
			//logger.error("Paid端：根据客户号检查客户信息的完整度出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户号检查客户信息的完整度(用于旧的APP)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCheckIntegrityForOldAjxa")
	@ResponseBody
	public Map<String, Object> doCheckIntegrityForOldAjxa(String cusNo, String cheackItems) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppCusIntegrityFeign.doCheckIntegrityForOld(cusNo, cheackItems);
		} catch (Exception e) {
			//logger.error("旧App端：根据客户号检查客户信息的完整度出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户号检查客户信息的完整度(用于网信)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCheckIntegrityForWxAjxa")
	@ResponseBody
	public Map<String, Object> doCheckIntegrityForWxAjxa(String cusNo, String cheackItems) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppCusIntegrityFeign.doCheckIntegrityForWx(cusNo, cheackItems);
		} catch (Exception e) {
			//logger.error("旧App端：根据客户号检查客户信息的完整度出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

}
