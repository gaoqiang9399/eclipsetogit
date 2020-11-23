<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.NodeType,app.util.User"%>
<%
String brNo=User.getBrno(request);
String fkFlag = request.getParameter("fkFlag");
%>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/dwr.js'></script>
<script type='text/javascript' src='${webPath}/dwr/interface/workflowDwr.js'></script>
<script type="text/javascript">
	dwr.engine.setAsync(false);
	var brNo = "<%=brNo%>";
	var fkFlag="<%=fkFlag%>";
	function isEmpty(val) 
	{
		if( val == "undefined" || val == null || val == "" ) 
		{
			return true;
		}
		return false;
	}
	
	function selectTransition(taskId) 
	{
		var result = "";
		if( isEmpty(taskId) ) 
		{
			taskId = document.all("taskId").value;
		}
		workflowDwr.findNextTransition(taskId, function(data) 
		{
			if( data != null ) 
			{ 
				if("SELECT"==data)
				{
					result =  window.showModalDialog(webPath+'/transition/findNextTransition?taskId='+taskId,'','dialogWidth:500px;dialogHeight:300px;status:no;');
				}
				else
				{
					result=data;
				}	
				if( isEmpty(result) ) 
				{
					return "";
				}
			}
		});
		return result;
	}
	
	function selectBackTransition(taskId) 
	{
		var result = "";
		if( isEmpty(taskId) )  {
			taskId = document.all("taskId").value;
		}
		workflowDwr.findBackTransition(taskId, function(data) 
		{
			if( data != null ) 
			{ 
				if("SELECT"==data)
				{
					result =  window.showModalDialog(webPath+'/transition/findBackTransition?taskId='+taskId,'','dialogWidth:500px;dialogHeight:300px;status:no;');
				}
				else
				{
					result=data;
				}
				if( isEmpty(result) ) 
				{
					return "";
				}
			}
		});
		return result;
	}
	
	function isExpression(val) 
	{
		return (val.indexOf("#") > 0 || val.indexOf("$") > 0);
	}
	
	function getRoleNoByGroup(candidateGroup) 
	{
		var canSelectRole = isEmpty(candidateGroup) || isExpression(candidateGroup);
		if( canSelectRole ) {
			return "";
		}
		return candidateGroup;
	}
	
	function selectNextUser(taskId, transitionName, isAssignNext) 
	{
		dwr.engine.setAsync(false);
		var nextUser = "";
		var designateType=document.getElementsByName("designateType")[0].value;
		var appNo=document.getElementsByName("appNo")[0].value;
		if(isAssignNext != "1" && isAssignNext != "2"&&designateType!="AUTO_APP"&&designateType!="AUTO_WF") 
		{
			return "";
		}
		workflowDwr.findExecutorByTransition(taskId, transitionName, designateType,brNo,appNo, function(data) 
		{
			if( data != null ) 
			{
				var candidateUser = data[0];
				var candidateGroup = data[1];
				var candidateNextUser = data[2];
				var branchId = data[3];
				var title=data[4];
				var nextNodeType = data[5];
				if(fkFlag=="1"){
					branchId = data[6];
				}
				if(!(isEmpty(candidateUser)) && !(isExpression(candidateUser))) 
				{
					nextUser= candidateUser;
					return ;
				}
				if(isExpression(candidateUser) ) 
				{
					return "WF_ROLE";
				}
				
				var wf_roleno = getRoleNoByGroup(candidateGroup);
				if(!isEmpty(nextNodeType) && nextNodeType == "signtask" ){//会签
					var result =  window.showModalDialog(webPath+'/wkfApprovalUser/findUserForSignTask?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ branchId +'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
					if(isEmpty(result)){
						return "";
					}
					nextUser = result;
				}else{
					if(isEmpty(nextUser)&&isEmpty(candidateGroup)&&!isEmpty(candidateNextUser))
					{
						nextUser=candidateNextUser;
					}
					else if(transitionName=="flow9795394886")
					{
						return "";
					}
					else
					{
						var result =  window.showModalDialog(webPath+'/wkfApprovalUser/findApprovalUserByPage?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ branchId +'&fkFlag='+fkFlag+'&appNo='+appNo+'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
						if( isEmpty(result) ) 
						{
							return "";
						}
						nextUser = result;
					}
				}
			}
		});
		if(nextUser.length > 0) 
		{
			var index = nextUser.indexOf(":");
			if(index > 0)
			{
				if(confirm("确定提交给[" + nextUser.substring(index+1) + "]"))
				{
					return nextUser.substring(0,index);
				} 
				else 
				{
					nextUser="";
				}
			}
			else
			{
				return nextUser;
			}
		}
	}
	
	function approve()
	{
		var opinionType=document.getElementsByName("opinionType")[0].value;
		var approvalOpinion=document.getElementsByName("approvalOpinion")[0].value;
		if(null==opinionType||""==opinionType)
		{
			alert("意见类型不能为空");
			return false;
		}
		if(null==approvalOpinion||""==approvalOpinion)
		{
			alert("审批意见不能为空");
			return false;
		}
		if( "2" != opinionType ) 
		{
			if( "1"== opinionType||"5"==opinionType ) 
			{//同意
				return approveAgree();
			}
			else if("3"==opinionType)
			{//退回
				return approveRollback();
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	function approveAgree() 
	{
		var taskId = document.getElementsByName("taskId")[0].value;
		var isAssignNext = document.getElementsByName("isAssignNext")[0].value;
		var transition=document.getElementsByName("transition")[0].value;
		if(null==transition||""==transition||"null"==transition)
		{
			transition = selectTransition(taskId);
		}
		if( null == transition || "" == transition ) 
		{
			return false;
		}
		var nextUser = selectNextUser(taskId, transition, isAssignNext);
		if( null==nextUser||undefined==nextUser||""==nextUser) 
		{
			nextUser="";
		}
		if( nextUser == "WF_ROLE") 
		{
			nextUser = "";
		}
		else if( isAssignNext == "1" && nextUser.length <= 0 ) 
		{
			if(!confirm("确定不选择提交人员?")) 
			{
				return false;
			}
		}
		else if( isAssignNext == "2" && nextUser.length <= 0 ) 
		{
			{
				alert("必须选择下一任务审批人员!");
				return false;
			}
		}
		document.getElementsByName("transition")[0].value=transition;
		document.getElementsByName("nextUser")[0].value=nextUser;
		return true;
	}
	
	function approveRollback() 
	{
		var taskId = document.getElementsByName("taskId")[0].value;
		var activityType = document.getElementsByName("activityType")[0].value;
		if(activityType == '<%=NodeType.SIGNTASK %>')
		{
			alert("会签任务不可退回！");
			return false;
		}
		var transition = selectBackTransition(taskId);
		if( transition != "NONE" ) {
			if( isEmpty(transition) ) {
				alert("请选择一个退回路径！");
				return false;
			}
		}
		else if(transition == "NONE" )
		{
			transition="";
		}
		if(confirm("确认退回?")) {
			document.getElementsByName("transition")[0].value = transition;
		}
		else
		{
			return false;
		}
		return true;
	}
	
	function selectProcess() 
	{
		var result =  window.showModalDialog(webPath+'/processDefinition/selectProcess','选择流程','dialogWidth:600px;dialogHeight:450px;status:no;');
		if( isEmpty(result) ) 
		{
			return "";
		}
		return result;
	}
	
	function approveIdea(executionId) 
	{
		window.showModalDialog(webPath+'/task/approveIdea?executionId=' + executionId,'查看审批意见','dialogWidth:800px;dialogHeight:550px;status:no;');
	}
</script>