package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.interfaces.mobileinterface.feign.AppCalcRepayPlanFeign;


@Controller
@RequestMapping("/appCalcRepayPlan")
public class AppCalcRepayPlanController extends BaseFormBean{
	@Autowired
	private AppCalcRepayPlanFeign appCalcRepayPlanFeign;
	//异步参数
	

	
	/**
	 * 
	* @Title: appSelectAjax  
	* @Description: 通过客户号查询应还总额（多个合同） 
	* @param @return
	* @param @throws Exception    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/appSelectRepayAmtAjax")
	@ResponseBody
	public Map<String, Object> appSelectRepayAmtAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			dataMap = appCalcRepayPlanFeign.getRepayAmtByCusNo(cusNo);
		} catch (Exception e) {
//			logger.error("移动端查询应还总额出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}



}
