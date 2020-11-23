package app.component.wkf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.ProcessInstance;
import com.dhcc.workflow.api.ProcessInstanceQuery;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.component.wkf.feign.ProcessFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/processInstance")
public class ProcessInstanceController extends BaseFormBean 
{
	private static final long serialVersionUID = 2547130658073798571L;
	

	@Autowired
	private ProcessFeign processFeign ;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private List<ProcessInstance> pocessInstanceList;
	@RequestMapping(value = "/findByPage")
	public String findByPage(String processDefKey,String processInstanceKey,String processDefDesc,String appId) throws Exception {
		ActionContext.initialize(request,response);
//		HttpServletRequest request = getHttpRequest();
		FormService formService = new FormService();
		FormData formwkf0014 = formService.getFormData("wkf0014");
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		Ipage ipage = this.getIpage();
		ProcessInstanceQuery query = WFUtil.getExecutionService().createProcessInstanceQuery();
		if (null != processDefKey && !"".equals(processDefKey))
		{
			query.processDefinitionKey(processDefKey);
			this.changeFormProperty(formwkf0014,"processDefKey","initValue",processDefKey);
		}
		if (null != processInstanceKey && !"".equals(processInstanceKey))
		{
			query.processInstanceId(processInstanceKey);
			this.changeFormProperty(formwkf0014,"processInstanceKey","initValue",processInstanceKey);
		}
		if (null != processDefDesc && !"".equals(processDefDesc))
		{
			query.processDefinitionDesc(processDefDesc);
			this.changeFormProperty(formwkf0014,"processDefDesc","initValue",processDefDesc);
		}
		if (null != appId && !"".equals(appId))
		{
			query.appId(appId);
			this.changeFormProperty(formwkf0014,"appId","initValue",appId);
		}
		
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		pocessInstanceList = query.page(firstResult, ipage.getPageSize()).orderAsc(ProcessInstanceQuery.PROPERTY_DBID).list();
		ipage.setResult(pocessInstanceList);
//		request.setAttribute(WF.PARAM_LIST_NAME, list);
		/**page end**/
		
		return "/component/wkf/processInstanceList"; 
	}
	//ɾ��
	@RequestMapping(value = "/delete")
	public String delete() throws Exception {
		String processInstanceId = getHttpRequest().getParameter(WF.PARAM_PROCINST_ID);
		processFeign.deleteProcessInstance(processInstanceId);
		return getListPage();
	}
	
	//�ָ�
	@RequestMapping(value = "/resume")
	public String resume() throws Exception {
		String processInstanceId = getHttpRequest().getParameter(WF.PARAM_PROCINST_ID);
		WFUtil.getExecutionService().resume(processInstanceId);
		return getListPage();
	}

	//��ͣ
	@RequestMapping(value = "/suspend")
	public String suspend() throws Exception {

		String processInstanceId = getHttpRequest().getParameter(WF.PARAM_PROCINST_ID);
		WFUtil.getExecutionService().suspend(processInstanceId);
		return getListPage();
	}

	//����
	@RequestMapping(value = "/cancel")
	public String cancel() throws Exception {
		String processInstanceId = getHttpRequest().getParameter(WF.PARAM_PROCINST_ID);
		processFeign.doCancel(processInstanceId);
		return getListPage();
	}

	public String getListPage()  throws Exception {
		ActionContext.initialize(request,response);
		
		return "/component/wkf/ProcessInstance_ListPage";
	}
	
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(FormData formwkf0014,String processDefKey,String processInstanceKey,String processDefDesc,String appId,String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			ProcessInstanceQuery query = WFUtil.getExecutionService().createProcessInstanceQuery();
			if (null != processDefKey && !"".equals(processDefKey))
			{
				query.processDefinitionKey(processDefKey);
				this.changeFormProperty(formwkf0014,"processDefKey","initValue",processDefKey);
			}
			if (null != processInstanceKey && !"".equals(processInstanceKey))
			{
				query.processInstanceId(processInstanceKey);
				this.changeFormProperty(formwkf0014,"processInstanceKey","initValue",processInstanceKey);
			}
			if (null != processDefDesc && !"".equals(processDefDesc))
			{
				query.processDefinitionDesc(processDefDesc);
				this.changeFormProperty(formwkf0014,"processDefDesc","initValue",processDefDesc);
			}
			if (null != appId && !"".equals(appId))
			{
				query.appId(appId);
				this.changeFormProperty(formwkf0014,"appId","initValue",appId);
			}
			JSONArray arr = JSONArray.fromObject(ajaxData);
			JSONObject obj = JSONObject.fromObject(arr.get(0));
			if(StringUtils.isNotBlank(obj.getString("customQuery"))){
				query.appId(obj.getString("customQuery"));
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.initPageCounts(new Integer[] { (int) query.count()});
			int firstResult = (pageNo-1) * ipage.getPageSize();
			pocessInstanceList = query.page(firstResult, ipage.getPageSize()).orderAsc(ProcessInstanceQuery.PROPERTY_DBID).list();
			ipage.setResult(pocessInstanceList);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}
