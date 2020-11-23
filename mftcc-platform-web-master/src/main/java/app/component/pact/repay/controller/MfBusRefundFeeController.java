package app.component.pact.repay.controller;

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
import com.google.gson.Gson;

import app.component.app.entity.MfBusAppKind;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.repay.entity.MfRefundFeeApply;
import app.component.pact.repay.entity.MfRefundFeeDetail;
import app.component.pact.repay.feign.MfBusRefundFeeFeign;
import app.util.JsonStrHandling;

@Controller
@RequestMapping("/mfBusRefundFee")
public class MfBusRefundFeeController extends BaseFormBean{
	
	private static final long serialVersionUID = 9156454891709532438L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusRefundFeeFeign mfBusRefundFeeFeign;
	
	/**
	 * 列表打开页面请求
	 * @param fincId 
	 * @param pactId 
	 * @param appId 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMfBusRefundFee")
	public String getMfBusRefundFee(Model model,String fincId, String pactId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrefundfeeapplyadd = formService.getFormData("refundfeeapplyadd");
		MfRefundFeeApply mfRefundFeeApply = new MfRefundFeeApply();
		mfRefundFeeApply.setFincId(fincId);
		mfRefundFeeApply.setPactId(pactId);
		mfRefundFeeApply.setAppId(appId);
		Map<String,Object> dataMap=mfBusRefundFeeFeign.getMfRefundFeeInfobyFincInfo(mfRefundFeeApply);
		//费用收取信息明细
		List<MfRefundFeeDetail> mfRefundFeeDetailList = (List<MfRefundFeeDetail>) dataMap.get("mfRefundFeeDetailList");
		mfRefundFeeApply=(MfRefundFeeApply)JsonStrHandling.handlingStrToBean(dataMap.get("mfRefundFeeApply"), MfRefundFeeApply.class);// 参数实体
		String itemNoStr=String.valueOf(dataMap.get("itemNoStr"));
		getObjValue(formrefundfeeapplyadd, mfRefundFeeApply);
		Gson gson = new Gson();
		String ajaxData = gson.toJson(mfRefundFeeDetailList);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formrefundfeeapplyadd", formrefundfeeapplyadd);
		model.addAttribute("query", "");
		model.addAttribute("itemNoStr", itemNoStr);
		return "/component/pact/repay/MfBusRefundFee";
	}
	
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formrefundfeeapplyadd = formService.getFormData("refundfeeapplyadd");
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
//			String formId = map.get("formId").toString();
			FormData formrefundfeeapplyaddAmtINfo = formService.getFormData("refundfeeapplyaddAmtINfo");
			getFormValue(formrefundfeeapplyaddAmtINfo, getMapByJson(ajaxData));
			if(this.validateFormData(formrefundfeeapplyadd)){
				MfRefundFeeApply mfRefundFeeApply = new MfRefundFeeApply();
				setObjValue(formrefundfeeapplyaddAmtINfo, mfRefundFeeApply);				
				dataMap = mfBusRefundFeeFeign.insert(mfRefundFeeApply);
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
}
