package app.component.pfs.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pfs.entity.CusFinCapData;
import app.component.pfs.entity.CusFinSubjectBalInfo;
import app.component.pfs.entity.CusFinSubjectData;
import app.component.pfs.feign.CusFinCapDataFeign;
import app.component.pfs.feign.CusFinSubjectDataFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * Title: CusFinCapDataAction.java Description:资产负债表视图层控制
 * 
 * @author:LJW
 * @Mon May 09 05:27:05 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinSubjectData")
public class CusFinSubjectDataController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinCapDataBo
	@Autowired
	private CusFinSubjectDataFeign cusFinSubjectDataFeign;
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
			List<CusFinSubjectData> cusFinSubjectDataList = (List<CusFinSubjectData>) JSONArray.toList(jsonArray1,
					new CusFinSubjectData(), new JsonConfig());
			CusFinSubjectData cusFinSubjectData = new CusFinSubjectData();
			 MfCusCustomer  mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
			String cusName = mfCusCustomer.getCusName();
			String finRptYear = finRptDate.substring(0, 4);
			cusFinSubjectData.setCusNo(cusNo);
			cusFinSubjectData.setCusName(cusName);
			cusFinSubjectData.setFinRptYear(finRptYear);
			cusFinSubjectData.setFinRptType(finRptType);
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("cusFinSubjectDataList",cusFinSubjectDataList);
			parmMap.put("cusFinSubjectData",cusFinSubjectData);
			cusFinSubjectDataFeign.update(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * 获取客户关注的list
	 * @param tableId
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAttentionListAjax")
	@ResponseBody
	public Map<String, Object> getAttentionListAjax(String tableId,String cusNo) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CusFinSubjectData cusFinSubjectData = new CusFinSubjectData();
			cusFinSubjectData.setCusNo(cusNo);
			List<CusFinSubjectBalInfo> list  = cusFinSubjectDataFeign.getAttentionList(cusFinSubjectData);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, false);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("flag","success");

			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}


	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String cusNo, String finRptDate, String subjectFirstNo) throws Exception {
		ActionContext.initialize(request, response);
		CusFinSubjectData cusFinSubjectData = new CusFinSubjectData();
		cusFinSubjectData.setCusNo(cusNo);
		cusFinSubjectData.setSubjectFirstNo(subjectFirstNo);
		cusFinSubjectData.setFinRptDate(finRptDate);
		List<CusFinSubjectData> lastLevList = cusFinSubjectDataFeign.getLastLevList(cusFinSubjectData);

		model.addAttribute("lastLevList", lastLevList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinSubjectData_Detail";
	}

}
