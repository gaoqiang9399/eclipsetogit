package app.component.thirdservice.dsdatas.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.thirdservice.dsdatas.feign.TdDsFeign;

@Controller
@RequestMapping("/tdDs")
public class TdDsController extends BaseFormBean {

	// 注入
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
//	@Autowired
	private TdDsFeign tdDsFeign;

	// 返回数据

	// 参数

	/**
	 * 运营商数据采集
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTelecomData")
	@ResponseBody
	public Map<String, Object> getTelecomData(Model model, String jsonParam) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getTelecomData(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 浅橙-黑名单(下架)
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getNewBlackListCheck")
	@ResponseBody
	public Map<String, Object> getNewBlackListCheck(Model model, String jsonParam) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getNewBlackListCheck(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 百融-多次申请规则评分
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrRuleApplyLoan")
	@ResponseBody
	public Map<String, Object> getBrRuleApplyLoan(Model model, String jsonParam) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getBrRuleApplyLoan(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 百融-反欺诈特殊名单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrRuleSpecialList")
	@ResponseBody
	public Map<String, Object> getBrRuleSpecialList(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getBrRuleSpecialList(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 百融-月度收支等级整合报告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrAccountChangeC")
	@ResponseBody
	public Map<String, Object> getBrAccountChangeC(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getBrAccountChangeC(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 圣数-火眼黑名单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFireEyesBlack")
	@ResponseBody
	public Map<String, Object> getFireEyesBlack(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getBrRuleSpecialList(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 贷后邦-反欺诈
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDhbGetSauronC")
	@ResponseBody
	public Map<String, Object> getDhbGetSauronC(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getDhbGetSauronC(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 天行_运营商三要素
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMobileVerifyThree")
	@ResponseBody
	public Map<String, Object> getMobileVerifyThree(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getMobileVerifyThree(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 天行-银行卡四要素
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBankCardVerifyFour")
	@ResponseBody
	public Map<String, Object> getBankCardVerifyFour(Model model, String jsonParam) {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resMap = tdDsFeign.getBankCardVerifyFour(jsonParam);
		dataMap = resMap;
		return dataMap;
	}

	/**
	 * 异步通知 运营商数据采集
	 */
	@RequestMapping(value = "/noticTelecomData")
	public void noticTelecomData(Model model) {
		ActionContext.initialize(request, response);
		HttpServletRequest request = ActionContext.getActionContext().getRequest();
		HttpServletResponse response = ActionContext.getActionContext().getResponse();
		Map<String, String[]> map = null;
		BufferedWriter out = null;
		try {
			// 处理结果
			map = request.getParameterMap();
			tdDsFeign.noticTelecomData(map);
			out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.write("SUCCESS");
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
