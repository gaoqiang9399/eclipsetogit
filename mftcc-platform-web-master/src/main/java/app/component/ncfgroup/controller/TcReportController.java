package app.component.ncfgroup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.taglib.JsonTableUtil;

import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.ncfgroup.feign.TcReportFeign;

@Controller
@RequestMapping("/tcReport")
public class TcReportController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TcReportFeign tcReportFeign;

	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 * 跳转认证信息页面获取的所有数据
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getWangxinReportAjax")
	public Map<String, Object> getWangxinReportAjax(String ajaxData, String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
			mfCusFamilyInfo.setCusNo(cusNo);
			List<MfCusFamilyInfo> familyInfoList = cusInterfaceFeign.getFamilyList(mfCusFamilyInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtmlPhoneBook = jtu.getJsonStr("tablecusfam00003", "tableTag", familyInfoList, null, true);

			dataMap = tcReportFeign.getWangxinReport(cusNo);
			dataMap.put("familyInfoList", familyInfoList);
			dataMap.put("tableHtmlPhoneBook", tableHtmlPhoneBook);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			// logger.error("获取认证报告出错：", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getRiskAssessmentReportAjax")
	public Map<String, Object> getRiskAssessmentReportAjax(String ajaxData, String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			/*
			 * MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
			 * mfCusFamilyInfo.setCusNo(cusNo); familyInfoList =
			 * cusInterfaceFeign.getFamilyList(mfCusFamilyInfo); JsonTableUtil jtu = new
			 * JsonTableUtil(); String tableHtmlPhoneBook =
			 * jtu.getJsonStr("tablecusfam00003","tableTag", familyInfoList, null,true);
			 */
			dataMap = tcReportFeign.getRiskAssessmentReport(cusNo);
			/*
			 * dataMap.put("familyInfoList", familyInfoList);
			 * dataMap.put("tableHtmlPhoneBook", tableHtmlPhoneBook);
			 */
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			// logger.error("获取认证报告出错：", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
		}
		return dataMap;
	}

}
