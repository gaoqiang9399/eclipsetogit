package app.component.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.report.entity.MfBalAnalysis;
import app.component.report.entity.MfInfoSource;
import app.component.report.feign.MfBalAnalysisFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfBalAnalysis")
public class MfBalAnalysisController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBalAnalysisFeign mfBalAnalysisFeign;
	/**
	 * 
	 * 方法描述：  获取余额汇总表数据
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @param reportId 
	 * @date 2017-7-18 下午3:21:32
	 */
	@RequestMapping(value = "/getBalAnalysisList")
	public String getBalAnalysisList(Model model, String sqlMap, String reportId) throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfBalAnalysis> resultList = null;
		Map<String, String> map =new HashMap<String, String>();
		map.put("sqlMap",sqlMap);
		map.put("reportId",reportId);
		map.put("opNo",opNo);
		try {
			resultList = mfBalAnalysisFeign.getMfBalAnalysisList(map);
			request.setAttribute("reportUrl",PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/balAnalysis";
		
	}
	
	/**
	 * 
	 * 方法描述： 项目来源统计表
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @param reportId 
	 * @param sqlMap 
	 * @date 2017-7-25 上午9:20:23
	 */
	@RequestMapping(value = "/getSourceInfo")
	public String getSourceInfo(Model model, String ajaxData, String reportId, String sqlMap,String uuid,String  reportUrl)throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfInfoSource> resultList = null;
		try {
			resultList = mfBalAnalysisFeign.getSourceList(opNo, reportId,sqlMap);
			String json=new Gson().toJson(resultList);
			//request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", resultList);
			model.addAttribute("json",json);
			model.addAttribute("uuid",uuid);
			model.addAttribute("reportUrl",reportUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/creditQueryListRDP";
		
	}
	
	
}
