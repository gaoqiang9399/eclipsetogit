/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DisposableDealDataAction.java
 * 包名： app.component.tools.disposable.action
 * 说明：
 * @author 沈浩兵
 * @date 2018-1-6 下午5:05:18
 * @version V1.0
 */ 
package app.component.tools.disposable.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.tools.disposable.feign.DisposableDealDataFeign;

/**
 * 类名： DisposableDealDataAction
 * 描述：
 * @author 沈浩兵
 * @date 2018-1-6 下午5:05:18
 *
 *
 */
@Controller
@RequestMapping("/disposableDealData")
public class DisposableDealDataController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DisposableDealDataFeign disposableDealDataFeign;
	/**
	 * 
	 * 方法描述： 处理合同审批历史、放款审批历史表中审批人员信息老数据
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-1-8 上午9:39:57
	 */
	@RequestMapping(value = "/doApproveHisForPactFincAjax")
	@ResponseBody
	public Map<String, Object> doApproveHisForPactFincAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 自定义查询Bo方法
			disposableDealDataFeign.doApproveHisForPactFinc();
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 处理流程历史表中的批复利率
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-8 上午9:59:51
	 */
	@RequestMapping(value = "/doWkfHisTaskRepayFincRateAjax")
	@ResponseBody
	public Map<String, Object> doWkfHisTaskRepayFincRateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 自定义查询Bo方法
			disposableDealDataFeign.doWkfHisTaskRepayFincRate();
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
