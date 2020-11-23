package  app.component.wkf.controller;
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

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.nmd.entity.ParmDic;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.feign.WkfApprovalOpinionFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: WkfApprovalOpinionAction.java
* Description:
* @author:zhanglei@dhcc.com.cn
* @Fri Feb 22 10:03:00 CST 2013
**/
@Controller
@RequestMapping(value = "/wkfApprovalOpinion")
public class WkfApprovalOpinionController extends BaseFormBean 
{
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WkfApprovalOpinionFeign wkfApprovalOpinionFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	
	
	/**
	 * ��ҳ��ѯ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForLoan")
	public String findByPageForLoan(Model model,String activityName,String execution) throws Exception 
	{
		ActionContext.initialize(request,response);
		WkfApprovalOpinion wkfApprovalOpinion = new WkfApprovalOpinion();
		wkfApprovalOpinion.setActivityName(activityName);
		wkfApprovalOpinion.setExecution(execution);
		wkfApprovalOpinion.setState("completed");
		Ipage ipage = this.getIpage();
		List<WkfApprovalOpinion> wkfApprovalOpinionList = (List<WkfApprovalOpinion>) wkfApprovalOpinionFeign.findByPage(ipage, wkfApprovalOpinion).getResult();
		
		model.addAttribute("wkfApprovalOpinionList", wkfApprovalOpinionList);
		return "/component/wkf/WkfApprovalOpinion_ListForLoan";
	}
	@ResponseBody
	@RequestMapping(value = "/findAllForLoan")
	public String findAllForLoan(Model model,String activityName,String execution) throws Exception 
	{
		ActionContext.initialize(request,response);
		WkfApprovalOpinion wkfApprovalOpinion = new WkfApprovalOpinion();
		wkfApprovalOpinion.setActivityName(activityName);
		wkfApprovalOpinion.setExecution(execution);
		wkfApprovalOpinion.setState("completed");
		List<WkfApprovalOpinion> wkfApprovalOpinionList = (List<WkfApprovalOpinion>) wkfApprovalOpinionFeign.findAllForLoan(wkfApprovalOpinion);
		model.addAttribute("wkfApprovalOpinionList", wkfApprovalOpinionList);
		return "/component/wkf/WkfApprovalOpinion_ListForLoan";
	}
	/**
	 * 业务概况中的审批意见
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplyApprovalOpinionList")
	public Map<String, Object> getApplyApprovalOpinionList(String appNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>)cu.getCacheByKeyName("APPROVAL_RESULT");
		 Object ideaList =  wkfInterfaceFeign.getWkfTaskHisList(appNo);
//		 if(ideaList.size()>1){
//			 ideaList.remove(ideaList.size()-1);
//		 }
		 JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
		 
			for(int i=0;i<zTreeNodes.size();i++){
				/*Boolean optNameFlag = true;
				if("signtask".equals(zTreeNodes.getJSONObject(i).getString("activityType"))){//评审委
					zTreeNodes.getJSONObject(i).put("optName", "");
					optNameFlag = false;
				}
				if("node4968816226".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){//区域评审会秘书汇总评审委意见
					zTreeNodes.getJSONObject(i).put("optName", "");
					optNameFlag = false;
				}
				
				if("node6684788113".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){ //集团评审会秘书汇总评审委意见
					zTreeNodes.getJSONObject(i).put("optName", "");
					optNameFlag = false;
				}
				
				if("node3982769925".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){//董事长
					zTreeNodes.getJSONObject(i).put("optName", "");
					optNameFlag = false;
				}
				*/
				zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
				zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
				zTreeNodes.getJSONObject(i).put("pId", "0");
				//if(optNameFlag){
					for (ParmDic parmDic : pdList) {
						if(parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))){
							zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
							break;
						}
					}
				//}
			}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}
	
	/**
	 * 业务概况中的审批意见(不显示会签的审批意见)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getApprovalOpinionNoSignList")
	public Map<String, Object> getApprovalOpinionNoSignList(String appNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>)cu.getCacheByKeyName("APPROVAL_RESULT");
		 Object ideaList = wkfInterfaceFeign.getWkfTaskHisList(appNo);
		 JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
			for(int i=0;i<zTreeNodes.size();i++){
				Boolean optNameFlag = true;
				zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
/*				if("signtask".equals(zTreeNodes.getJSONObject(i).getString("activityType"))){//评审委
					zTreeNodes.getJSONObject(i).put("approveIdea", "保密");
					zTreeNodes.getJSONObject(i).put("optName", "保密");
					optNameFlag = false;
				}
				if("node4968816226".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){//区域评审会秘书汇总评审委意见
					zTreeNodes.getJSONObject(i).put("approveIdea", "保密");
					zTreeNodes.getJSONObject(i).put("optName", "保密");
					optNameFlag = false;
				}
				
				if("node6684788113".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){ //集团评审会秘书汇总评审委意见
					zTreeNodes.getJSONObject(i).put("approveIdea", "保密");
					zTreeNodes.getJSONObject(i).put("optName", "保密");
					optNameFlag = false;
				}
				
				if("node3982769925".equals(zTreeNodes.getJSONObject(i).getString("activityName"))){//董事长
					zTreeNodes.getJSONObject(i).put("approveIdea", "保密");
					zTreeNodes.getJSONObject(i).put("optName", "保密");
					optNameFlag = false;
				}
				*/
				zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
				zTreeNodes.getJSONObject(i).put("pId", "0");
				/*if(optNameFlag){*/
					for (ParmDic parmDic : pdList) {
						if(parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))){
							zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
							break;
						}
					}
			/*	}*/
				
			}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}
	@ResponseBody
	@RequestMapping(value = "/getListPage")	
	public Map<String, Object> getListPage() throws Exception 
	{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		return dataMap;
	}
	/**
	 * 查看审批意见
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getListPageForWkf")	
	public Map<String, Object> getListPageForWkf() throws Exception 
	{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		return dataMap;
	}
	/**
	 * 查看审批意见 并带有流程图
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getListPageForWkfChart")	
	public Map<String, Object> getListPageForWkfChart() throws Exception 
	{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		return dataMap;
	}
	/**
	 * 审批流程查看带保密功能
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getListPageForWkfChartSecret")	
	public Map<String, Object> getListPageForWkfChartSecret() throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		return dataMap;
	} 
}