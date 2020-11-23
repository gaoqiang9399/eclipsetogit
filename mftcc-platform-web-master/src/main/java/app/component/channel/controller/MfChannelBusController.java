package app.component.channel.controller;

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
import com.core.struts.taglib.JsonTableUtil;

import app.component.channel.feign.MfChannelBusFeign;
import app.component.channel.fund.entity.MfFundChannelFinc;
import app.component.channel.fund.entity.MfFundChannelRepayPlan;
import app.component.thirdRecord.feign.MfFundChannelRepayPlanFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping(value ="/mfChannelBus")
public class MfChannelBusController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfChannelBusFeign mfChannelBusFeign ;
	@Autowired
	private MfFundChannelRepayPlanFeign mfFundChannelRepayPlanFeign;
	
	@RequestMapping(value = "openUpload")
	public String openUpload(Model model,String pactId,String pactNo,String cusNo,String cusName,String appName,String fincId,String putoutAmt) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("pactId", pactId);
		model.addAttribute("pactNo", pactNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusName", cusName);
		model.addAttribute("appName", appName);
		model.addAttribute("fincId", fincId);
		model.addAttribute("putoutAmt", putoutAmt);
		
		model.addAttribute("query","");
		return "/component/channel/MfChannelBus_UploadRepay";
	}
	
	@RequestMapping(value = "/repay")
	public String repay(Model model,String pactId,String pactNo,String cusNo,String cusName,String appName,String fincId,String putoutAmt) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfFundchannelrepayplan0001 = formService.getFormData("mfFundchannelrepayplan0001");
		model.addAttribute("formmfFundchannelrepayplan0001", formmfFundchannelrepayplan0001);
		model.addAttribute("pactId", pactId);
		model.addAttribute("pactNo", pactNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusName", cusName);
		model.addAttribute("appName", appName);
		model.addAttribute("fincId", fincId);
		model.addAttribute("putoutAmt", putoutAmt);
		
		MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
		mfFundChannelRepayPlan.setPactId(pactId);
		List<MfFundChannelRepayPlan> mfFundChannelUnRepayPlanList = mfFundChannelRepayPlanFeign.getMfFundChannelUnRepayPlanByPactId(mfFundChannelRepayPlan);
		model.addAttribute("mfFundChannelUnRepayPlanList", mfFundChannelUnRepayPlanList);
		if (mfFundChannelUnRepayPlanList!=null && mfFundChannelUnRepayPlanList.size() > 0){
			Double repayPrcp = mfFundChannelUnRepayPlanList.get(0).getRepayPrcp();
			Double repayIntst = mfFundChannelUnRepayPlanList.get(0).getRepayIntst();
			if (repayPrcp == 0){
				this.changeFormProperty(formmfFundchannelrepayplan0001, "realRepayPrcp", "initValue", "0.00");
				this.changeFormProperty(formmfFundchannelrepayplan0001, "realRepayPrcp", "readonly","1");
			}
			if (repayIntst == 0){
				this.changeFormProperty(formmfFundchannelrepayplan0001, "realRepayIntst","initValue","0.00");
				this.changeFormProperty(formmfFundchannelrepayplan0001, "realRepayIntst", "readonly","1");
			}
		}
		model.addAttribute("query","");
		return "/component/channel/MfChannelBus_Repay";
	}
	
	@RequestMapping(value = "/getListPageChannelBusFinc")
	public String getListPageChannelBusFinc(Model model) throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/channel/MfChannelBusFinc_List";
	}
	
	@RequestMapping(value = "/findChannelBusFincByPageAjax")
	@ResponseBody
	public Map<String, Object> findChannelBusFincByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String fincSts) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFundChannelFinc mfFundChannelFinc = new MfFundChannelFinc();
		try{
			mfFundChannelFinc.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFundChannelFinc.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfFundChannelFinc.setCriteriaList(mfFundChannelFinc, ajaxData);// 我的筛选
			mfFundChannelFinc.setFincSts(fincSts);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfFundChannelFinc", mfFundChannelFinc));
			ipage = mfChannelBusFeign.findChannelBusFincByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
		
	}
	
	@RequestMapping("/saveRepayAjax")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,Object> saveRepayAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			String formId = (String)dataMap.get("formId");
			FormData formmfFundchannelrepayplan0001 = formService.getFormData(formId);
			getFormValue(formmfFundchannelrepayplan0001, getMapByJson(ajaxData));
			if(this.validateFormData(formmfFundchannelrepayplan0001)){
				//formmfFundchannelrepayplan0001
				Map<String,Object> formMap = new HashMap<String,Object>();
				setObjValue(formmfFundchannelrepayplan0001, formMap);
				Map<String,String> map = mfFundChannelRepayPlanFeign.saveMfFundChannelRepayplan(formMap);
				
				if("success".equals(map.get("flag"))){
					MfFundChannelRepayPlan mfFundChannelRepayPlan = new MfFundChannelRepayPlan();
					mfFundChannelRepayPlan.setPactId((String)formMap.get("pactId"));
					Ipage ipage = this.getIpage();
					ipage.setParams(this.setIpageParams("mfFundChannelRepayPlan",mfFundChannelRepayPlan));
					List<MfFundChannelRepayPlan> mfFundChannelRepayPlanList = (List<MfFundChannelRepayPlan>)mfFundChannelRepayPlanFeign.findByPageForMfFundChannelRepayPlan(ipage).getResult();
					JsonTableUtil jtu = new JsonTableUtil();
					String htmlStr = jtu.getJsonStr("tablemfFundChannelRepayPlan0001", "tableTag", mfFundChannelRepayPlanList, null, true);
					dataMap.put("tableHtml", htmlStr);
					//控制本金或则利息是否允许填写值
					dataMap.put("repayIntst",map.get("repayIntst"));
					dataMap.put("realRepayPrcp",map.get("realRepayPrcp"));
					dataMap.put("msg", map.get("msg"));
					dataMap.put("flag", "success");
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "保存失败");
				}
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
}
