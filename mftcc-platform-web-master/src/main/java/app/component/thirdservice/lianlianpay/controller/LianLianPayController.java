//package app.component.thirdservice.lianlianpay.controller;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//
//
//import app.component.cus.entity.MfCusCustomer;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import app.component.thirdservice.lianlianpay.feign.LianLianPayFeign;
//import app.component.thirdservice.lianlianpay.constant.PaymentConstant;
//import app.component.thirdservice.lianlianpay.constant.PaymentStatusEnum;
//import app.component.thirdservice.lianlianpay.entity.BusinessNoticeBean;
//import app.component.thirdservice.lianlianpay.entity.LLBankCardAgreeBean;
//import app.component.thirdservice.lianlianpay.entity.LLRealTimePayBean;
//import app.component.thirdservice.lianlianpay.entity.NotifyResponseBean;
//import app.component.thirdservice.lianlianpay.entity.OrderQuery;
//import app.component.thirdservice.lianlianpay.util.SignUtil;
//
//import cn.mftcc.util.PropertiesUtil;
//import cn.mftcc.util.StringUtil;
//import cn.mftcc.util.WaterIdUtil;
//
//import com.alibaba.fastjson.JSONObject;
//import com.core.struts.BaseFormBean;
//import com.google.gson.Gson;
//
//
///**
// * 
// * 
// * 
// * @author MaHao
// * @date 2017-9-25 下午4:37:11
// */
//@Controller
//@RequestMapping("/lianLianPay")
//public class LianLianPayController  extends BaseFormBean {
//	
//	@Autowired
//	private HttpServletRequest request;
//	@Autowired
//	private HttpServletResponse response;
//	@Autowired
//	private LianLianPayFeign lianLianPayFeign;
//	
//	private final String RET_CODE = "ret_code";
//	private final String RET_MSG = "ret_msg";
//	
//	//银行卡签约回调
//	//分期付款 银行卡还款扣款回调参数
////	@RequestMapping(value = "/test")
////	public String test(Model model, String ajaxData) throws Exception{
////		InputStream in = request.getInputStream();
////		BufferedReader br = new BufferedReader(new InputStreamReader(in));
////		StringBuffer stringBuffer = new StringBuffer();
////		String str = "";
////		while ((str = br.readLine()) != null) {
////			stringBuffer.append(str);
////		}
////		String info = stringBuffer.toString();
////		
////		if(StringUtil.isNotBlank(info)){
////			
////			Gson gson = new Gson();
////			System.out.println(info);
//////			businessNoticeBean = gson.fromJson(info, BusinessNoticeBean.class);
////		}
//////		logger.error(repayment_plan);
//////		System.out.println(repayment_plan);
////	/*	LLRealTimePayBean llRealTimePayBean = new LLRealTimePayBean();
////		llRealTimePayBean.setMoneyOrder("0.02");
////		llRealTimePayBean.setAcctName("马豪");
////		llRealTimePayBean.setCardNo("6236833019886669");
////		llRealTimePayBean.setCusNo("2020000000274");
////		llRealTimePayBean.setFlagCard("0");
////		llRealTimePayBean.setInfoOrder("测试");
////		llRealTimePayBean.setMemo("测试;是否,鸳鸯返回");
////		llRealTimePayBean.setNoOrder(WaterIdUtil.getWaterId());
////		lianLianPayFeign.realTimePay(llRealTimePayBean);
////		logger.error("测试执行完毕");*/
////		return dataMap;
////	}
////	@RequestMapping(value = "/bangka")
////	public String bangka(Model model, String ajaxData) throws Exception{
////		LLBankCardAgreeBean llBankCardAgreeBean = new LLBankCardAgreeBean();
////		llBankCardAgreeBean.setAcctName("马豪");
////		llBankCardAgreeBean.setCardNo("6236833019886669");
////		llBankCardAgreeBean.setIdNo("41272519880313031X");
////		llBankCardAgreeBean.setIdType("0");
////		llBankCardAgreeBean.setPayType("I");
////		llBankCardAgreeBean.setUserId("2020000000274");
////		MfCusCustomer mfCusCustomer = new MfCusCustomer();
////		String riskItem = getRiskItemW(mfCusCustomer);
////		llBankCardAgreeBean.setRiskItem(riskItem);
////		Map<String, Object> resultMap = lianLianPayFeign.signApply(llBankCardAgreeBean);
////		String req_data = (String) resultMap.get("req_data");
////		model.addAttribute("req_data", req_data);
////		return "bangka";
////	}
//	private String getRiskItemW(MfCusCustomer mfCusCustomer){
//		Map<String,String> map =  new HashMap<String,String>();
//		map.put("frms_ware_category", "2010");
//		map.put("user_info_mercht_userno", "2020000000274");
//		String lianlianTest = PropertiesUtil.getCloudProperty("lianlian.isTest");
//		if("1".equals(lianlianTest)){
//			map.put("user_info_mercht_userno", "test_2020000000274");
//		}
//		String user_info_dt_register = "20170808121212";
//		map.put("user_info_dt_register", user_info_dt_register);
//		map.put("user_info_bind_phone", "13393730589");
//		map.put("user_info_identify_state", "1");//写死已认证
//		map.put("user_info_identify_type", "2");
//		map.put("user_info_full_name", "马豪");
//		map.put("user_info_id_no", "41272519880313031X");
//		return new Gson().toJson(map);
//	}
//
//	/**
//	 * 连连支付实时付款回调
//	 * @return
//	 * @throws Exception
//	 * @author MaHao
//	 * @param businessNoticeBean 
//	 * @date 2017-9-26 上午9:11:44
//	 */
//	@RequestMapping(value = "/receiveNotify")
//	public String receiveNotify(Model model, String ajaxData, BusinessNoticeBean businessNoticeBean) throws Exception{
//		Map<String, Object> dataMap = new HashMap<String, Object>();
////		logger.error("连连实时付款回调--->>开始notify request:" + businessNoticeBean.toString());
//		try {
//			lianLianPayFeign.receiveNotify(businessNoticeBean);
//			dataMap.put(RET_CODE,"0000");
//			dataMap.put(RET_MSG,"交易成功");
//		} catch (Exception e) {
////			logger.error("连连支付实时付款回调发生异常："+e.getMessage(),e);
//			dataMap.put(RET_CODE, "9999");
//			dataMap.put(RET_MSG, "未知异常");
//			throw e;
//		}
//		return "success";
//	}
//	/**
//	 * 银行卡签约回调
//	 * http://payhttp.xiaofubao.com/.../result.htm?status=0000&result={
//		"oid_partner":"201103171000000000",
//		"user_id":"2222222",
//		"agreeno":"12313232313312331",
//		"sign_type":"RSA",
//		"sign":"ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/
//		+p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtX
//		E+vPTfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
//	 * @return
//	 * @throws Exception
//	 * @author MaHao
//	 * @date 2017-9-26 下午1:12:47
//	 */
//	@RequestMapping(value = "/receiveSignApplyNotify")
//	public String receiveSignApplyNotify(Model model, String ajaxData) throws Exception{
//		logger.error("连连分期付款银行卡签约后回调--->>开始 status:" + status +"\n result:"+result);
//		//保存签约协议号  agreeno 这里user_id使用的是银行卡id
//		lianLianPayFeign.receiveSignApplyNotify(status,result);
//		
//		return dataMap;
//	}
//	/**
//	 * 分期付款银行卡扣款 回调
//	 * @return
//	 * @throws Exception
//	 * @author MaHao
//	 * @date 2017-9-26 下午7:25:42
//	 */
//	@RequestMapping(value = "/receiveBankCardRepaymentNotify")
//	public String receiveBankCardRepaymentNotify(Model model, String ajaxData) throws Exception{
//		Map<String, Object> dataMap = new HashMap<String,Object>();
//		InputStream in = request.getInputStream();
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		StringBuffer stringBuffer = new StringBuffer();
//		String str = "";
//		while ((str = br.readLine()) != null) {
//			stringBuffer.append(str);
//		}
//		String info = stringBuffer.toString();
//		
//		if(StringUtil.isNotBlank(info)){
//			
//			Gson gson = new Gson();
//			System.out.println(info);
//			businessNoticeBean = gson.fromJson(info, BusinessNoticeBean.class);
//		}
//		try {
//			lianLianPayFeign.receiveBankCardRepaymentNotify(businessNoticeBean);
//			dataMap.put(RET_CODE, "0000");
//			dataMap.put(RET_MSG, "交易成功");
//		} catch (Exception e) {
//			logger.error("连连支付分期付款回调发生异常："+e.getMessage(),e);
//			dataMap.put(RET_CODE, "9999");
//			dataMap.put(RET_MSG, "未知异常");
//			throw e;
//		}
//		return dataMap;
//	}
//	
//	@RequestMapping(value = "/checkSignPay")
//	public String checkSignPay(Model model, String ajaxData) throws Exception{
//		logger.info("测试认证支付订单查询功能>>>>>start");
//		OrderQuery orderQuery = new OrderQuery();
//		orderQuery.setDt_order("20171012172843");
//		orderQuery.setNo_order("plan17101216294835837W3");
//		orderQuery.setQuery_version("1.0");
//		String cusNo = "2020000000043";
//		OrderQuery resOrderQuery = lianLianPayFeign.checkAuthPay(orderQuery, cusNo);
//		System.out.println("返回结果："+new Gson().toJson(resOrderQuery));
//		return dataMap;
//	}
//	
//}
