package app.component.pfs.controller;

import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pfs.entity.CusFinRatioData;
import app.component.pfs.entity.CusFinSubjectBalInfo;
import app.component.pfs.entity.CusFinSubjectData;
import app.component.pfs.feign.CusFinRatioDataFeign;
import app.component.pfs.feign.CusFinSubjectDataFeign;
import cn.mftcc.common.MessageEnum;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: CusFinRatioDataController.java Description:财务指标
 * 
 * @author:LJW
 * @Mon May 09 05:27:05 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinRatioData")
public class CusFinRatioDataController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinCapDataBo
	@Autowired
	private CusFinRatioDataFeign cusFinRatioDataFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 * 更新科目余额表信息
	 * 
	 * @author zhs date 2019-4-24
	 * @param finRptDate
	 * @param ajaxData
	 * @param finRptType
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String cusNo, String finRptDate, String ajaxData, String finRptType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray1 = JSONArray.fromObject(ajaxData);
			List<CusFinRatioData> cusFinRatioDataList = (List<CusFinRatioData>) JSONArray.toList(jsonArray1,
					new CusFinRatioData(), new JsonConfig());
			CusFinRatioData cusFinRatioData = new CusFinRatioData();
			cusFinRatioData.setCusNo(cusNo);
			cusFinRatioData.setFinRptDate(finRptDate);
			cusFinRatioData.setFinRptType(finRptType);
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("cusFinRatioDataList",cusFinRatioDataList);
			parmMap.put("cusFinRatioData",cusFinRatioData);
			cusFinRatioDataFeign.updateFormVal(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
		}
		return dataMap;
	}


	/**
	 * 方法描述：财务指标分析
	 * @param cusNo
	 * @param finRptDate
	 * @param ajaxData
	 * @param finRptType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRatioAnalysisAjax")
	@ResponseBody
	public Map<String, Object> updateRatioAnalysisAjax(String cusNo, String finRptDate, String finRptType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CusFinRatioData cusFinRatioData = new CusFinRatioData();
			cusFinRatioData.setCusNo(cusNo);
			cusFinRatioData.setFinRptDate(finRptDate);
			cusFinRatioData.setFinRptType(finRptType);
			cusFinRatioDataFeign.updateRatioAnalysis(cusFinRatioData);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("指标分析"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("指标分析"));
		}
		return dataMap;
	}


}
