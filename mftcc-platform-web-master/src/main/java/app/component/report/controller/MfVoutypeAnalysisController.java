package app.component.report.controller;

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

import app.component.report.feign.MfPieVouTypeFeign;

@Controller
@RequestMapping("/mfVoutypeAnalysis")
public class MfVoutypeAnalysisController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfPieVouTypeFeign mfPieVouTypeFeign;
	@ResponseBody
	@RequestMapping(value = "/getPieDataSource")
	public Map<String, Object>   getPieDataSource(Model model, String ajaxData)throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		List<Object>datasourceList=mfPieVouTypeFeign.getVouTypeList(null);
		if(datasourceList == null || datasourceList.size() == 0){
			datasourceList = null;
		}
		dataMap.put("datasourceList", datasourceList);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
		
	}
	@ResponseBody
	@RequestMapping(value = "/getTermPieDataSource")
	public Map<String, Object>   getTermPieDataSource(Model model, String ajaxData)throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<Object>datasourceList=mfPieVouTypeFeign.getTermTypeList(null);
			if(datasourceList == null || datasourceList.size() == 0){
				datasourceList = null;
			}
			dataMap.put("datasourceList", datasourceList);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
		
	}
	
}
