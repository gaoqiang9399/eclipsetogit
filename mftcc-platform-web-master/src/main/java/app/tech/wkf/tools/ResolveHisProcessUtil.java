package app.tech.wkf.tools;

import java.util.List;

import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.task.Task;

import app.base.SpringUtil;
import app.component.wkf.entity.WkfApprovalUser;
import app.component.wkf.feign.WkfApprovalUserFeign;
import app.tech.wkf.bo.WfExecutionFeign;
import app.tech.wkf.entity.WfExecution;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResolveHisProcessUtil {
	
	/**
	 * 通过业务编号 获取审批流程图所需要的数据
	 * @param appNo
	 * @return
	 * @throws Exception
	 */
	public static JSONArray resolveProcess(String appNo) throws Exception{
		JSONArray array = new JSONArray();
		WfExecutionFeign wfExecutionFeign = (WfExecutionFeign) SpringUtil.getBean(WfExecutionFeign.class);
		WfExecution wfExecution = new WfExecution();
		wfExecution.setAppId(appNo);
		JSONArray jsonArray = JSONArray.fromObject(wfExecutionFeign.getByIdForHis(wfExecution));
		if(jsonArray.size()==0){//没有历史流程
			return array;
		}
		for(int i=jsonArray.size()-1;i>=0;i--){
			if("obsolete".equals(jsonArray.getJSONObject(i).getString("state"))){
				jsonArray.remove(i);
				continue;
			}
			if(jsonArray.getJSONObject(i).getString("activityname").indexOf("temp_")!=-1){
				jsonArray.remove(i);
				continue;
			}
		}
		
		WkfApprovalUserFeign wkfApprovalUserFeign = (WkfApprovalUserFeign) SpringUtil.getBean(WkfApprovalUserFeign.class);
		
		for(int i=0;i<jsonArray.size();i++){
			String taskId = jsonArray.getJSONObject(i).getString("dbid");
			String activityName = jsonArray.getJSONObject(i).getString("activityname");
			String state = jsonArray.getJSONObject(i).getString("state");
			String create = jsonArray.getJSONObject(i).getString("create");
			String end = jsonArray.getJSONObject(i).getString("end");
			String result = jsonArray.getJSONObject(i).getString("result");
			String approveIdea = jsonArray.getJSONObject(i).getString("approveIdea");
			String appValue = jsonArray.getJSONObject(i).getString("appValue");
			String name = jsonArray.getJSONObject(i).getString("description");
			String activityType = jsonArray.getJSONObject(i).getString("activityType"); 
			String assignee = jsonArray.getJSONObject(i).getString("assignee");
			String assigneeName = "";
			String roleNo = "";
			String roleName = "";
			WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
			wkfApprovalUser.setWkfUserName(assignee);
			List<WkfApprovalUser> appUserList = wkfApprovalUserFeign.getAllList(wkfApprovalUser);
			if(appUserList.size()>0){
				assigneeName = appUserList.get(0).getDisplayname();
				roleNo +=  appUserList.get(0).getWkfRoleNo();
			}
			JSONObject node = new JSONObject();
			node.put("name", name);
			if("signtask".equals(activityType)){
				activityType = "node";
			}
			node.put("id", activityName);
			node.put("type", activityType);
			node.put("group", roleNo);
			node.put("groupName", roleName);
			node.put("user", assignee);
			node.put("userName", assigneeName);
			if("open".equals(state)){
				Task task = WFUtil.getTaskService().getTask(taskId);
				String appUrl=task.getApproveUrl();
				String url=WFUtil.getTaskService().getTaskURL(taskId,appUrl);
				if(url.indexOf("?")>-1){
					url += "&taskId="+taskId;
				}else{
					url += "?taskId="+taskId;
				}
				node.put("url", url);
			}
			node.put("taskId", taskId);
			node.put("state", state);
			node.put("create", create.substring(0, 19));
			if(null!=end&&!"".equals(end)){
				node.put("end", end.substring(0, 19));
			}else{
				node.put("end", end);
			}
			node.put("result", result);
			node.put("approveIdea", approveIdea);
			JSONObject obj = new JSONObject();
			if (!"".equals(appValue)) {
				String[] arr = appValue.split(",", 0);
				for (String str : arr) {
					String[] tmpArr = str.split(":", 2);
					if ("null".equals(tmpArr[1])) {
						obj.put(tmpArr[0], "");
					} else {
						obj.put(tmpArr[0], tmpArr[1]);
					}
				}
			}
			node.put("appValue", obj);
			array.add(node);
		}
		return array;
	}
	/**
	 * 更新json
	 * @param array
	 * @param nodeName
	 * @param assignee
	 * @param assigneeName
	 * @param roleNo
	 * @param roleName
	 * @return
	 */
	private static JSONArray updateNodeJson(String taskId,JSONArray array,String nodeName,String assignee,String assigneeName,String roleNo,String roleName,String state,String create,String end,String result,String approveIdea,String appValue){
		for(int i=0;i<array.size();i++){
			JSONObject node = array.getJSONObject(i);
			if(node.getString("id").equals(nodeName)){
				
				break;
			}
		}
		return array;
	}
}
