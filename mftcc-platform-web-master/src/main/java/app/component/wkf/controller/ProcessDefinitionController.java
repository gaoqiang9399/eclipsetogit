package app.component.wkf.controller;

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
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.ProcessDefinition;
import com.dhcc.workflow.api.ProcessDefinitionQuery;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.component.wkf.AppConstant;
import app.component.wkf.feign.ProcessDefinitionFeign;
import app.util.toolkit.Ipage;

//public class ProcessDefinitionAction extends BaseAction {
@Controller
@RequestMapping(value = "/processDefinition")
public class ProcessDefinitionController extends BaseFormBean 
{
	@Autowired
	private ProcessDefinitionFeign processDefinitionFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model,String processDefName,String processDefKey,String processDefDesc) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0013 = formService.getFormData("wkf0013");
		int currentPage = 1;
		String pageNo = this.getEadisPage();
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		Ipage ipage = this.getIpage();
		Map<String, Object> map = this.setIpageParams("currentPage", currentPage);
		map.put("processDefName", processDefName);
		map.put("processDefKey", processDefKey);
		map.put("processDefDesc", processDefDesc);
		ipage.setParams(map);
		ipage = processDefinitionFeign.processDefFindByPage(ipage);
		/**page end**/
		model.addAttribute("ipage", ipage);
		model.addAttribute("formwkf0013", formwkf0013);
		model.addAttribute("query", "");
		return "/component/wkf/processDefinitionList";
	}
	@RequestMapping(value = "/delete")
	public String delete(Model model,String processDeploymentId) throws Exception {
		 processDefinitionFeign.delete(processDeploymentId);
		return getListPage();
	}
	@ResponseBody
	@RequestMapping(value = "/deleteMore")
	public Map<String, Object> deleteMore(String processDefId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		if(!(StringUtil.isEmpty(processDefId))){
			String[] defIdArr = processDefId.split(",");
			for(String id : defIdArr){
				processDefinitionFeign.delete(id);
			}
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/activate")
	public String activate() throws Exception{
		String id = request.getParameter(WF.PARAM_PROCESSDEF_ID);
		processDefinitionFeign.activate(id);
		return getListPage();
	}
	@RequestMapping(value = "/suspend")
	public String suspend() throws Exception{
		String id = getHttpRequest().getParameter(WF.PARAM_PROCESSDEF_ID);
		processDefinitionFeign.suspend(id);
		return getListPage();
	}
	@RequestMapping(value = "/selectProcess")
	public String selectProcess() throws Exception {
		ActionContext.initialize(request, response);
		List<ProcessDefinition> pocessDefinitionList = processDefinitionFeign.selectProcess();
		return "selectprocess";
	}
	@RequestMapping(value = "/selectprocessPop")
	public String selectprocessPop() 
	{
		/**page start**/
		HttpServletRequest request = getHttpRequest();
		String processName = request.getParameter(AppConstant.PARAM_PROCESSDEF_NAME);
		String processKey = request.getParameter(AppConstant.PARAM_PROCESSDEF_KEY);
		String processDesc = request.getParameter("processDefDesc");
		
		int currentPage = 1;
		String pageNo = this.getEadisPage();
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		ProcessDefinitionQuery query = WFUtil.getRepositoryService().createProcessDefinitionQuery().activated();
		if( !(StringUtil.isEmpty(processName)) ){
			query.processDefinitionNameLike(processName);
		}  
		if( !(StringUtil.isEmpty(processKey)) ) {
			query.processDefinitionKey(processKey);
		}
		if( !(StringUtil.isEmpty(processDesc)) ) {
			query.processDefinitionDescLike(processDesc);
		}
		
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<ProcessDefinition> list = query.page(firstResult, ipage.getPageSize())
										.orderAsc(ProcessDefinitionQuery.PROPERTY_DEPLOYMENT_TIMESTAMP)
										.list();
		ipage.setResult(list);
		/**page end**/
		
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/selectProcess";
	}
/***新系统 begin****/
	@RequestMapping(value = "/getListPage")
	public String getListPage()  throws Exception {
		ActionContext.initialize(request,response);
		
		return "/component/wkf/ProcessDefinition_ListPage";
	}
	
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String processDefName,String processDefKey,String processDefDesc,int pageNo,String tableId,String tableType,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageNo(pageNo);
			Map<String, Object> map = this.setIpageParams("ajaxData", ajaxData);
			map.put("processDefName", processDefName);
			map.put("processDefKey", processDefKey);
			map.put("processDefDesc", processDefDesc);
			ipage.setParams(map);
			ipage = processDefinitionFeign.findByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}	
	
	
/***新系统 end***/	
}
