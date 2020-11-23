package app.component.thirdservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.base.SpringUtil;
import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.paph.entity.ApiReturnRecord;
import app.component.paphinterface.PaphInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.HttpClientUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import config.YmlConfig;


@Controller
@RequestMapping(value = "/mfTongDunReportAuth")
public class MfTongDunReportAuthController extends BaseFormBean{
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PaphInterfaceFeign paphInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	
	/**
	 * 查询同盾的认证报告
	 * @param cusNo 
	 * @param appId 
	 * @param success 
	 * @param ajaxData 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getTDReportAjax")
	public Map<String, Object> getTDReportAjax(String cusNo, String appId, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			//是否需要重新提交查询 0-否 1-是
			String submitFlag = request.getParameter("submitFlag");
			//判断跳转认证报告页面  1:网信; 0:杭州恩义 2:小风策 3:杭州微溪同盾
			String comReportFlag = new CodeUtils().getSingleValByKey("COM_REPORT_FLAG");
			YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
			if("3".equals(comReportFlag)){
				Map<String,Object> dataMapTemp = new HashMap<String,Object>();
				//账号信息
				String traceNo = WaterIdUtil.getWaterId();
				String sn = ymlConfig.getCloud().get("username");
				String password = ymlConfig.getCloud().get("password");
				String ipUrl = ymlConfig.getCloud().get("ip");
				String source = traceNo.concat(sn).concat(password);
				String pwd =  DigestUtils.md5Hex(source).toUpperCase();
				Map<String, String> cloudMap = new HashMap<String, String>();
				cloudMap.put("traceNo", traceNo);
				cloudMap.put("sn", sn);
				cloudMap.put("pwd", pwd);
				dataMapTemp.put("ipUrl", ipUrl);
				dataMapTemp.put("cloudMap", cloudMap);
				//账号信息end
				ApiReturnRecord apiReturnRecordQuery = new ApiReturnRecord();
				apiReturnRecordQuery.setReturnId(cusNo);
				ApiReturnRecord apiReturnRecord = paphInterfaceFeign.getApiReturnRecord(apiReturnRecordQuery);
				//是否存在已有数据
				String dataExist = "0";
				String reportId = null;
				if(apiReturnRecord != null && StringUtil.isNotEmpty(apiReturnRecord.getOrderNo())){
					reportId = apiReturnRecord.getOrderNo();
					dataExist = "1";
				}
				String success = "";
				//重新提交 或则 第一次查询
				if("1".equals(submitFlag) || "0".equals(dataExist)){
					Map<String, String> submitMap = new HashMap<String, String>();
					MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
					MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
					String id_number = mfCusCustomer.getIdNum();
					String name = mfCusCustomer.getCusName();
					String mobile = mfCusCustomer.getCusTel();
					if(mfBusApply != null){
						String loanAmount = String.valueOf(mfBusApply.getAppAmt());
						String loan_term = String.valueOf(mfBusApply.getTerm());
						String loan_term_unit = "MONTH";
						if("2".equals(mfBusApply.getTermType())){
							loan_term_unit = "DAY";
						}
						submitMap.put("loan_amount", loanAmount);
						submitMap.put("loan_term", loan_term);
						submitMap.put("loan_term_unit", loan_term_unit);
					}
					submitMap.put("id_number", id_number);
					submitMap.put("name", name);
					submitMap.put("mobile", mobile);
					submitMap.putAll((Map<String,String>)dataMapTemp.get("cloudMap"));
					dataMapTemp.put("submitMap", submitMap);
					Gson gson = new Gson();
					String url = "http://" + (String)dataMapTemp.get("ipUrl") + "/cloudRegister/withShield/apply.json";
					String jsonStr = HttpClientUtil.sendPost((Map<String,String>)dataMapTemp.get("submitMap"), url);
					Map<String, String> resultMap = gson.fromJson(jsonStr, Map.class);
					success = resultMap.get("errorcode");
					reportId = resultMap.get("data");
				}
				if("11111".equals(success) || StringUtil.isNotEmpty(reportId)){
					dataMapTemp.put("dataExist", dataExist);
					dataMapTemp.put("submitFlag", submitFlag);
					dataMap = applyQuery(dataMapTemp, ajaxData,  cusNo,  reportId, dataMap);
				}
			}else{
				dataMap.put("errorCode", "00000");
			}
		} catch (Exception e) {
//			logger.error("查询同盾的认证报告出错",e);
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", "认证报告出错");
		}
		return dataMap;
	}
	
	
	//贷前审核--报告查询
	@SuppressWarnings("unchecked")
	private Map<String,Object> applyQuery(Map<String,Object> dataMapTemp,String ajaxData, String cusNo, String reportId, Map<String, Object> dataMap) throws Exception{
		Gson gson = new Gson();
		String dataExist = (String)dataMapTemp.get("dataExist");
		Map<String, String> resuleMap = new HashMap<String, String>();
		String success;
		if("1".equals(dataExist) && !"1".equals((String)dataMapTemp.get("submitFlag"))){
			ApiReturnRecord apiReturnRecordQuery = new ApiReturnRecord();
			apiReturnRecordQuery.setReturnId(cusNo);
			ApiReturnRecord apiReturnRecord = paphInterfaceFeign.getApiReturnRecord(apiReturnRecordQuery);
			ajaxData = apiReturnRecord.getReqPacketEncrypt();
			resuleMap = gson.fromJson(ajaxData, Map.class);
			success = resuleMap.get("errorcode");
		}else{
			Map<String,String> map = new HashMap<String,String>();
			map.putAll((Map<String, String>)dataMapTemp.get("cloudMap"));
			map.put("accordNo", reportId);
			String url = "http://" + (String)dataMapTemp.get("ipUrl") + "/cloudRegister/withShield/query.json";
			ajaxData = HttpClientUtil.sendPost(map, url);
			resuleMap = gson.fromJson(ajaxData, Map.class);
			success = resuleMap.get("errorcode");
			if("11111".equals(success)){
				ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
				apiReturnRecord.setOrderNo(reportId);
				apiReturnRecord.setReturnId(cusNo);
				apiReturnRecord.setReqPacketEncrypt(ajaxData);
				Map<String, String> submitMap = (Map<String, String>)dataMapTemp.get("submitMap");
				submitMap.put("accordNo", reportId);
				String sumitInfo = gson.toJson(submitMap);
				apiReturnRecord.setRepUrl(sumitInfo);
				apiReturnRecord.setThreeParty("TONG_DUN");
				apiReturnRecord.setThreePartyName("同盾");
				apiReturnRecord.setReqSts(BizPubParm.YES_NO_Y);
				if("0".equals(dataExist)){
					apiReturnRecord.setTxTime(DateUtil.getDateTime());
					paphInterfaceFeign.insert(apiReturnRecord);
				}else{
					paphInterfaceFeign.update1(apiReturnRecord);
				}
			}
		}
		Map<String, String> oriDataMap = new HashMap<String, String>();
		oriDataMap = gson.fromJson(resuleMap.get("data"), Map.class);
		ajaxData = oriDataMap.get("oriData");
		dataMap.put("data", ajaxData);
		dataMap.put("errorCode", success);
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 添加贷后检测
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @param cusNo 
	 * @param pactNo 
	 * @param scanPeriod 
	 * @date 2018-1-2 下午5:21:44
	 */
	@ResponseBody
	@RequestMapping(value = "/addLoanAfterMonitor")
	public Map<String, String> addLoanAfterMonitor(String cusNo, String pactNo, String scanPeriod) throws Exception{
		//账号信息
		Map<String, String> cloudMap = new HashMap<String, String>();
		YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
		String traceNo = WaterIdUtil.getWaterId();
		String sn = ymlConfig.getCloud().get("username");
		String password = ymlConfig.getCloud().get("password");
		String ipUrl = ymlConfig.getCloud().get("ip");
		String source = traceNo.concat(sn).concat(password);
		String pwd =  DigestUtils.md5Hex(source).toUpperCase();
		cloudMap.put("traceNo", traceNo);
		cloudMap.put("sn", sn);
		cloudMap.put("pwd", pwd);
		ApiReturnRecord apiReturnRecordQuery = new ApiReturnRecord();
		apiReturnRecordQuery.setReturnId(cusNo);
		ApiReturnRecord apiReturnRecord = paphInterfaceFeign.getApiReturnRecord(apiReturnRecordQuery);
		String reportId = apiReturnRecord.getOrderNo();
		MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
		mfBusFincAppQuery.setPactNo(pactNo);
		MfBusFincApp mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincAppQuery);
		cloudMap.put("report_id", reportId);
		cloudMap.put("loan_term", String.valueOf(mfBusFincApp.getTermMonth()));
		cloudMap.put("loan_term_unit", "1".equals(mfBusFincApp.getTermType())?"MONTH":"DAY");
		cloudMap.put("loan_date", mfBusFincApp.getPutoutDate());
		cloudMap.put("loan_amount", String.valueOf(mfBusFincApp.getPactAmt()));
		cloudMap.put("begin_scan_time", mfBusFincApp.getPactBeginDate());
		cloudMap.put("end_scan_time", mfBusFincApp.getPactEndDate());
		cloudMap.put("scan_period", scanPeriod);
		cloudMap.put("operator", User.getRegName(request));
		return cloudMap;
	}
	
	
}
