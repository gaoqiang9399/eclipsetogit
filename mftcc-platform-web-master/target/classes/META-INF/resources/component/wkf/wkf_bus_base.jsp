<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="app.base.User"%>
<%@page language="java" import="com.dhcc.workflow.NodeType"%>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/dwr.js'></script>
<script type='text/javascript' src='${webPath}/dwr/interface/workflowDwr.js'></script>
<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript">
<%
	String orgNo = (String)User.getOrgNo(request);
	String taskId = (String)request.getParameter("taskId");
	String activityType = (String)request.getParameter("activityType");
	String isAssignNext = (String)request.getParameter("isAssignNext");
%>

	var isAssignNext = <%=isAssignNext%>;
	var taskId = <%=taskId%>;
	function doCommitNextUser(appNo,callBack){
		var transition = selectTransition(taskId);
		//节点处选择必须分派下一人员
		if(isAssignNext == '2'){
			if(isEmpty(transition)){
				return false;
			}
			selectNextOpUser(appNo,taskId,transition,isAssignNext,function(data){
				callBack(data);
			});
		}else{
			//没有选择分派人员提示是否进行下一步提交
			alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
				callBack('');
			});
		}
	}

	
	function selectTransition(taskId) {
		dwr.engine.setAsync(false);
		var result = "";
		if( isEmpty(taskId) ) {
			taskId = '<%=taskId%>';
		}
		workflowDwr.findNextTransition(taskId, function(data) {
			if( data != null ) { 
				if("SELECT"==data){
					result =  window.showModalDialog(webPath+'/transition/findNextTransition?taskId='+taskId,'','dialogWidth:500px;dialogHeight:300px;status:no;');
				}else{
					result=data;
				}
				if(isEmpty(result)){
					return "";
				}
			}
		});
		return result;
	}
	
	
	function getRoleNoByGroup(candidateGroup) {
		var canSelectRole = isEmpty(candidateGroup) || isExpression(candidateGroup);
		if( canSelectRole ) {
			return "";
		}
		return candidateGroup;
	}
	function isEmpty(val) {
		if( val == "undefined" || val == null || val == "" ) {
			return true;
		}
		return false;
	}
	function isExpression(val) {
		return (val.indexOf("#") > 0 || val.indexOf("$") > 0);
	}


//选择下一个审批人员
	function selectNextOpUser(appNo,taskId, transitionName, isAssignNext,callback){
		dwr.engine.setAsync(false);
		var nextUser = "";
		var orgNo = "<%=orgNo%>";
		var designateType = "";
		var activityType = '<%=activityType%>';
		workflowDwr.findExecutorByTransition(taskId, transitionName, designateType,orgNo,appNo, function(data){
			if( data != null ) {
				var candidateUser = data[0];
				var candidateGroup = data[1];
				var candidateNextUser = data[2];
				var branchId = data[3];
				var title=data[4];
				var nextNodeType = data[5];
				if(!(isEmpty(candidateUser)) && !(isExpression(candidateUser))) {
					nextUser= candidateUser;
				}
				if(!isEmpty(activityType) && activityType == "signtask" ){//当前会签
					if(typeof(callback)== "function"){
						callback(nextUser);
					}
				}
				
				var wf_roleno = getRoleNoByGroup(candidateGroup);
				if(!isEmpty(nextNodeType) && nextNodeType == "signtask" ){//下节点会签
					var result =  window.showModalDialog(webPath+'/wkfApprovalUser/findUserForSignTask?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ branchId +'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
					if(isEmpty(result)){
						if(typeof(callback)== "function"){
							callback("");
						}
					}
					nextUser = result;
					if(typeof(callback)== "function"){
						getFinalUser(nextUser,callback);
					}
				}else{
					if(isEmpty(nextUser)&&isEmpty(candidateGroup)&&!isEmpty(candidateNextUser)){
						nextUser=candidateNextUser;
						if(typeof(callback)== "function"){
							getFinalUser(nextUser,callback);
						}
					}else{
						getNextUserPage(title,wf_roleno,branchId,function(data){
							var result = data.selectedValue;
							if( isEmpty(result) ) {
								if(typeof(callback)== "function"){
									callback("");
								}
							}
							nextUser = result;
							if(typeof(callback)== "function"){
								getFinalUser(nextUser,callback);
							}
						});
					}
				}
				
			}
		});
	}
		
//选择下一步操作员页面
	var getNextUserPage = function(title,wkfRoleNo,wkfBrNo,callback){
		dialog({
			id:"nextUserDialog",
			title:"选择操作员",
			url:webPath+'/wkfApprovalUser/findApprovalUserByPage?wkfRoleNo='+wkfRoleNo+'&wkfBrNo='+wkfBrNo+'&title='+title,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	};
	//获得最终的审批人员
	var getFinalUser = function(nextUser,callback){
		if(nextUser.length > 0) {
			var index = nextUser.indexOf(":");
			if(index > 0){
				alert(top.getMessage("CONFIRM_OPERATION","提交给[" + nextUser.substring(index+1) + "]"),2,function(){
					callback(nextUser.substring(0,index));
				});
			}else{
				callback(nextUser);
			}
		} else {
			callback(nextUser);
		}
	};
</script>
