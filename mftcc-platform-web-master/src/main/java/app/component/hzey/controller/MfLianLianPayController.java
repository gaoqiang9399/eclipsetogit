package app.component.hzey.controller;

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

import com.core.struts.BaseFormBean;

import app.component.hzey.feign.MfLianLianPayFeign;
import app.component.hzey.util.HttpClientRedirectUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfLianLianPayAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 19 15:07:00 CST 2017
 **/
@Controller
@RequestMapping("/mfLianLianPay")
public class MfLianLianPayController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfLianLianPayFeign mfLianLianPayFeign;

	/**
	 * 获取连连支持银行卡信息
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-27 下午3:10:35
	 */
	@RequestMapping(value = "/getLianLianPayBanks")
	@ResponseBody
	public Map<String, Object> getLianLianPayBanks(Model model) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<Map<String, String>> data = mfLianLianPayFeign.getLianLianPayBanks();
			dataMap.put("flag", "00000");
			dataMap.put("msg", "成功");
			dataMap.put("data", data);
		} catch (Exception e) {
//			logger.error("APP端获取连连支持签约银行卡种类出错：", e);
			dataMap.put("flag", "11111");
			dataMap.put("msg", "获取数据异常，请检查服务器端");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 签约连连银行卡
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-27 下午5:13:52
	 */
	@RequestMapping(value = "/doSignLianLianBank")
	public void doSignLianLianBank(Model model, String cusNo, String bankCardNo) throws Exception {
		Map<String, Object> data = mfLianLianPayFeign.doSignLianLianBank(cusNo, bankCardNo);
		HttpClientRedirectUtil httpClientRedirectUtil = new HttpClientRedirectUtil(response);
		httpClientRedirectUtil.setParameter("req_data", (String) data.get("req_data"));
		httpClientRedirectUtil.sendByPost((String) data.get("url"));
	}

	/**
	 * 银行卡签约回调 http://payhttp.xiaofubao.com/.../result.htm?status=0000&result={
	 * "oid_partner":"201103171000000000", "user_id":"2222222",
	 * "agreeno":"12313232313312331", "sign_type":"RSA",
	 * "sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/
	 * +p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtX
	 * E+vPTfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 下午1:12:47
	 */
	@RequestMapping(value = "/receiveSignApplyNotify")
	public String receiveSignApplyNotify(Model model, String status, String result, String successFlag)
			throws Exception {
		// logger.error("连连分期付款银行卡签约后回调--->>开始 status:" + status +"\n
		// result:"+result);
		// 保存签约协议号 agreeno 这里user_id使用的是银行卡id
		mfLianLianPayFeign.receiveSignApplyNotify(status, result);
		// 如果状态不是0000 错误信息放到页面
		if (!"0000".equals(status)) {
			successFlag = "2";
			model.addAttribute("successFlag", successFlag);
			return "/component/hzey/receiveAuthpayNotify";
		}
		model.addAttribute("successFlag", successFlag);
		return "/component/hzey/succback";
	}

	/**
	 * 认证支付调用
	 * 
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-9 下午2:36:32
	 */
	@RequestMapping(value = "/doAuthpay")
	public void doAuthpay(Model model, String fincId, String planId, String cusNo) throws Exception {
		HashMap<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("fincId", fincId);
		parmMap.put("planId", planId);
		Map<String, Object> data = mfLianLianPayFeign.doAuthpay(cusNo, parmMap);
		HttpClientRedirectUtil httpClientRedirectUtil = new HttpClientRedirectUtil(response);
		httpClientRedirectUtil.setParameter("req_data", (String) data.get("req_data"));
		httpClientRedirectUtil.sendByPost((String) data.get("url"));
		model.addAttribute("req_data", (String) data.get("req_data"));
		model.addAttribute("url", (String) data.get("url"));
	}

	/**
	 * 支付 成功以 POST 方式访问商户指定地址（url_return）（失败和异常只以 GET
	 * 方式返回指定地址，不返回参数，商户可通过订单结果查询来查 询订单状态）
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-11 下午1:08:40
	 */
	@RequestMapping(value = "/receiveAuthpayNotify")
	public String receiveAuthpayNotify(Model model, String res_data) throws Exception {
		String successFlag = "0";// 默认失败
		// 保存返回数据到第三方表中
		mfLianLianPayFeign.saveReceiveAuthpayNotify(res_data);
		// 出现失败和异常 无返回值
		if (StringUtil.isNotBlank(res_data)) {
			successFlag = "1";
		}
		model.addAttribute("successFlag", successFlag);
		model.addAttribute("query", "");
		return "/component/hzey/receiveAuthpayNotify";
	}

	/**
	 * 查询对应还款计划认证支付的状态
	 * 
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-24 下午4:15:46
	 */
	@RequestMapping(value = "/checkAuthpayStatuAjax")
	@ResponseBody
	public Map<String, Object> checkAuthpayStatuAjax(String planId, String fincId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfLianLianPayFeign.checkAuthpayStatu(planId, fincId);
		} catch (Exception e) {
			dataMap.put("flag", "11111");
			dataMap.put("msg", "获取数据异常，请检查服务器端");
		}
		return dataMap;
	}

}
