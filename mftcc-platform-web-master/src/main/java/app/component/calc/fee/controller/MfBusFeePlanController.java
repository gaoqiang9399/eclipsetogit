package  app.component.calc.fee.controller;
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

import app.component.calc.core.entity.MfBusFeePlan;
import app.component.calc.core.feign.MfBusFeePlanFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfSysFeeItemAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri May 20 18:04:05 CST 2016
 **/
@Controller
@RequestMapping("/mfBusFeePlan")
public class MfBusFeePlanController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入Bo
	//全局变量
	@Autowired
	private MfBusFeePlanFeign mfBusFeePlanFeign;
	@Autowired
	private MfBusAppFeeFeign mfBusAppFeeFeign;
	//异步参数
	//表单变量
	
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReturnListPage")
	public String getReturnListPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		String channelSource=ActionContext.getActionContext().getRequest().getParameter("channelSource");
		String channelSourceNo=ActionContext.getActionContext().getRequest().getParameter("channelSourceNo");
		ActionContext.getActionContext().getRequest().setAttribute("channelSource", channelSource);
		ActionContext.getActionContext().getRequest().setAttribute("channelSourceNo", channelSourceNo);
		return "/component/calc/fee/MfBusReturnFeePlan_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findReturnFeeByPageAjax")
	@ResponseBody
	public Map<String, Object> findReturnFeeByPageAjax(String ajaxData,String pageNo,String appId,String cusNo,String pactId,String fincId,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFeePlan mfBusFeePlan = new MfBusFeePlan();
		try {
			mfBusFeePlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFeePlan.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFeePlan.setCriteriaList(mfBusFeePlan, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			//ipage.setPageSize(pageSize);
			//ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			mfBusFeePlan.setAppId(appId);
			mfBusFeePlan.setCusNo(cusNo);
			mfBusFeePlan.setPactId(pactId);
			mfBusFeePlan.setFincId(fincId);
			ipage = mfBusFeePlanFeign.getReturnFeePlanList(ipage, mfBusFeePlan);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("获取返费计划失败，",e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 返费操作
	 * 
	 */
	@RequestMapping(value = "/returnFeeAjax")
	@ResponseBody
	public Map<String, Object> returnFeeAjax(String ajaxData,String appId,String feePlanId,String itemNos) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("appId", appId);
			map.put("itemType", "2");//退费项
			//String itemNos=calcFeePlanInterfaceFeign.getItemNoByAppId(map);
			Map<String,String> feeParmMap=new HashMap<String,String>();
			feeParmMap.put("feePlanId", feePlanId);
			feeParmMap.put("itemNo", itemNos);
			//calcFeePlanInterfaceFeign.doReturnFeeByInfo(feeParmMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("返费"));
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("返费操作失败，",e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("返费"));
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getMfBusFeePlanListAjax")
	@ResponseBody
	public Map<String, Object> getMfBusFeePlanListAjax(String fincId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFeePlan mfBusFeePlan =new MfBusFeePlan();
		mfBusFeePlan.setFincId(fincId);
		List<MfBusFeePlan> mfBusFeePlanList = mfBusFeePlanFeign.getBusFeePlanList(mfBusFeePlan);
		
		
		//是否存在已还款、部分收费、逾期的费用计划
		Boolean outFlag = true;
		//获得未收取的费用计划
		List<MfBusFeePlan> busFeePlanList = mfBusFeePlanFeign.getFeePlanList(mfBusFeePlan);
		if (busFeePlanList!=null&&busFeePlanList.size()>0) {
			for (int i = 0; i < busFeePlanList.size(); i++) {
				if ("1".equals(busFeePlanList.get(i).getOutFlag())||"2".equals(busFeePlanList.get(i).getOutFlag())
						||"3".equals(busFeePlanList.get(i).getOutFlag())) {
					outFlag = false;
					break;
				}
			}
		}
		// 费用计划历史
		String tableHtml = "";
		if (mfBusFeePlanList!=null&&mfBusFeePlanList.size() > 0) {
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusFeePlanList, null, true);
			dataMap.put("outFlag", outFlag);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 打开完善费用计划信息页面
	 * @param model
	 * @param fincId 借据号
	 * @param appFeeId 费用业务关联编号
	 * @param planId 费用计划编号
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月30日 下午3:03:25
	 */
	@RequestMapping(value = "/perfectBusFeePlan")
	public String perfectBusFeePlan(Model model, String fincId,String appFeeId,String planId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formperfectBusFeePlan = formService.getFormData("perfectBusFeePlan");
		getFormValue(formperfectBusFeePlan);
		MfBusFeePlan mfBusFeePlan = new MfBusFeePlan();
		mfBusFeePlan.setFeePlanId(planId);
		mfBusFeePlan = mfBusFeePlanFeign.getById(mfBusFeePlan);
		
		MfBusAppFee  mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(appFeeId);
		mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
		
		if (StringUtil.isNotEmpty(mfBusFeePlan.getFeeAccountId())) {
			mfBusAppFee.setFeeAccountId(mfBusFeePlan.getFeeAccountId());
		}
		if (StringUtil.isNotEmpty(mfBusFeePlan.getFeeMainNo())) {
			mfBusAppFee.setFeeMainNo(mfBusFeePlan.getFeeMainNo());
			mfBusAppFee.setFeeMainName(mfBusFeePlan.getFeeMainName());
		}
		if (StringUtil.isNotEmpty(mfBusFeePlan.getFeeVoucherNo())) {
			mfBusAppFee.setFeeVoucherNo(mfBusFeePlan.getFeeVoucherNo());
		}
		getObjValue(formperfectBusFeePlan, mfBusFeePlan);
		getObjValue(formperfectBusFeePlan, mfBusAppFee);
		model.addAttribute("formperfectBusFeePlan", formperfectBusFeePlan);
		model.addAttribute("appId", mfBusAppFee.getAppId());
		model.addAttribute("itemNo", mfBusAppFee.getItemNo());
		model.addAttribute("query", "");
		return "/component/calc/fee/perfectBusFeePlan";
	}
	/**
	 * 
	 * 方法描述： 保存完善费用计划信息页面
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月30日 下午3:09:23
	 */
	@RequestMapping(value = "/savePerfectBusFeePlanAjax")
	@ResponseBody
	public Map<String, Object> savePerfectBusFeePlanAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			FormData 	formperfectBusFeePlan = formService.getFormData((String) dataMap.get("formId"));
			getFormValue(formperfectBusFeePlan, getMapByJson(ajaxData));
			if(this.validateFormData(formperfectBusFeePlan)){
				MfBusFeePlan mfBusFeePlan = new MfBusFeePlan();
				setObjValue(formperfectBusFeePlan, mfBusFeePlan);
				mfBusFeePlanFeign.update(mfBusFeePlan);
				dataMap.put("mfBusFeePlan",mfBusFeePlan);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
}
