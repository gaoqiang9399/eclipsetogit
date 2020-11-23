package app.component.auth.controller;

import java.util.ArrayList;
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

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.entity.MfCusCreditContractHis;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.auth.feign.MfCusCreditContractHisFeign;
import app.component.auth.feign.MfWorkFlowDyFormFeign;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/workFlowDyForm")
public class MfWorkFlowDyFormController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfWorkFlowDyFormFeign mfWorkFlowDyFormFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	
	@Autowired
	private MfCusCreditContractFeign  mfCusCreditContractFeign;
	
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	
	@Autowired
	private MfCusCreditContractHisFeign    mfCusCreditContractHisFeign;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCreditContractHisAppId")
	@ResponseBody
	public Map<String,Object> getCreditContractHisAppId(String creditAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfCusCreditContractHis     mfCusCreditContractHis   =  new   MfCusCreditContractHis();
			mfCusCreditContractHis.setCreditAppId(creditAppId);
			List<MfCusCreditContractHis> creditContractHisList = mfCusCreditContractHisFeign.getCreditContractHisList(mfCusCreditContractHis);
			
			List<MfCusCreditContractHis>  creditContractHisListNew  =  new   ArrayList<>();
			JsonTableUtil jtu = new JsonTableUtil();
			String htmlStr = jtu.getJsonStr("tablecusCreditContractHis", "tableTag", creditContractHisListNew, null, true);
			dataMap.put("tableHtml", htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	

}
