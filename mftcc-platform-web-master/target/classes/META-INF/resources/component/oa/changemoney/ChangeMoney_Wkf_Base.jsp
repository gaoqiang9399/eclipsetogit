<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="app.base.User"%>
<%@page language="java" import="com.dhcc.workflow.NodeType"%>
<script type='text/javascript'
	src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript'
	src='${webPath}/dwr/dwr.js'></script>
<script type='text/javascript'
	src='${webPath}/dwr/interface/workflowDwr.js'></script>
<script type="text/javascript"
	src="${webPath}/component/include/myAlert.js"></script>

<script type="text/javascript">
<%String orgNo = (String) User.getOrgNo(request);
			String taskId = (String) request.getParameter("taskId");
			String activityType = (String) request.getParameter("activityType");
			String isAssignNext = (String) request.getParameter("isAssignNext");%>
		
		function isEmpty(val) {
			if( val == "undefined" || val == null || val == "" ) {
				return true;
			}
			return false;
		}
	
		function isExpression(val) {
			return (val.indexOf("#") > 0 || val.indexOf("$") > 0);
		}
	
		function getRoleNoByGroup(candidateGroup) {
			var canSelectRole = isEmpty(candidateGroup) || isExpression(candidateGroup);
			if( canSelectRole ) {
				return "";
			}
			return candidateGroup;
		}
	
		function selectTransition(taskId) {
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
		
		/**czk 2016-08-20
		 * requestForm 表示审批请求的来源，formId审批表单编号,applySP表示来自申请审批,authSP表示授信审批,evalSP表示评级审批
		 * 不同的审批需要传递的参数不一致 
		*/
		function commitProcess(urlstr,formId,requestForm){
			var parms = urlstr.split(/\?/)[1].split(/&/);
			var appNo;
			var opinionType;
			for(var i=0;i<parms.length;i++){
				var keyvalue = parms[i].split(/=/);
				if(keyvalue[0]=="appNo"){
					appNo = keyvalue[1];
				}
				if(keyvalue[0]=="opinionType"){
					opinionType = keyvalue[1];
				}
			}
			
			if("3"==opinionType||"4"==opinionType){//3退回上环节4发回重审
				return approveRollback(urlstr,opinionType,formId,requestForm);
			}else{
				return doCommit(appNo,urlstr,opinionType,formId,requestForm);
			}
		}
		
		function doCommit(appNo,urlstr,opinionType,formId,requestForm) {
			window.top.alert(top.getMessage("CONFIRM_COMMIT"),2,function(){
// 				if(!valiWkfDocIsUp(appNo)){


// 					return false;
// 				}
				getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm);
				
			});
			
		}
		
		function getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm){
			dwr.engine.setAsync(false);
			
			var activityType = '<%=activityType%>';
			var transition = selectTransition(<%=taskId%>);
			var isAssignNext = '<%=isAssignNext%>';
			if(isEmpty(transition)){
				return false;
			}
			workflowDwr.getTaskInfo(appNo,<%=taskId%>, function(data) {
				if(data != null){
					var	nextUser = "";
					
// 						document.location.href = urlstr
<%-- 								+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>; --%>
						var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
						urlSubmit(url,formId,requestForm);		
				
				}
			});
		}
		
		function urlSubmit(url,formId,requestForm){
			LoadingAnimate.start();
			if(requestForm != ''){
				var dataParam = JSON.stringify($(formId).serializeArray()); 
				var datas = [];
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					data:{ajaxData:dataParam,ajaxDataList:JSON.stringify(datas)},
					success : function(data) {
 						LoadingAnimate.stop();
						if(data.flag=="success"){
							//审批提醒弹窗，不自动关闭
							window.top.alert(data.msg,3,function(){
								var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								$(top.window.document).find("#taskShowDialog").remove();
							});									
						}else{
							alert(data.msg,0);
							var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							$(top.window.document).find("#taskShowDialog").remove();
						}
					},error:function(){
						LoadingAnimate.stop();
						top.alert(top.getMessage("FAILED_SUBMIT"),0);
					}
				});	
			}else{
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					success : function(data) {
 						LoadingAnimate.stop();
						if(data.flag=="success"){
							//审批提醒弹窗，不自动关闭
							window.top.alert(data.msg,3,function(){
								var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								$(top.window.document).find("#taskShowDialog").remove();
							});
							
						}else{
							alert(data.msg,0);
							var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							$(top.window.document).find("#taskShowDialog").remove();
						}
					},error:function(){
						LoadingAnimate.stop();
						top.alert(top.getMessage("FAILED_SUBMIT"),0);
					}
				});	
			}
			
			
		}
		
		
		
		function selectNextOpUser(appNo,taskId, transitionName, isAssignNext){
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
						return nextUser;
					}
					
					var wf_roleno = getRoleNoByGroup(candidateGroup);
					if(!isEmpty(nextNodeType) && nextNodeType == "signtask" ){//下节点会签
						var result =  window.showModalDialog(webPath+'/wkfApprovalUser/findUserForSignTask?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ branchId +'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
						if(isEmpty(result)){
							return "";
						}
						nextUser = result;
					}else{
						if(isEmpty(nextUser)&&isEmpty(candidateGroup)&&!isEmpty(candidateNextUser)){
							nextUser=candidateNextUser;
						}else{
							//var result =  window.showModalDialog('${webPath}/sysOpAreaList/getAreaOpList?wkfRoleNo=' + wf_roleno+'&appNo='+appNo+'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
							var result =  window.showModalDialog(webPath+'/wkfApprovalUser/findApprovalUserByPage?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ orgNo +'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;');
							if( isEmpty(result) ) {
								return "";
							}
							nextUser = result;
						}
					}
					
				}
			});
			if(nextUser.length > 0) {
				var index = nextUser.indexOf(":");
				if(index > 0){
					if(confirm("确定提交给[" + nextUser.substring(index+1) + "]")){
						return nextUser.substring(0,index);
					} else {
						nextUser="";
					}
				}else{
					return nextUser;
				}
			}
		}
	
		function selectBackTransition(taskId) {
			var result = "";
			if(isEmpty(taskId)){
				taskId = <%=taskId%>;
			}
			dwr.engine.setAsync(false);
			workflowDwr.findBackTransition(taskId, function(data) {
				if( data != null ) { 
					if("SELECT"==data){
						result =  window.showModalDialog(webPath+'/transition/findBackTransition?taskId='+taskId,'','dialogWidth:500px;dialogHeight:300px;status:no;');
					}else{
						result=data;
					}if( isEmpty(result) ) {
						return "";
					}
				}
			});
			dwr.engine.setAsync(true);
			return result;
		}
	
		function approveRollback(urlstr,opinionType,formId,requestForm) {
			var taskId = '<%=taskId%>';
			var activityType = '<%=activityType%>';
			var nextUser ="";
			var transition="";
			if(activityType == 'signtask'){
				alert("会签任务不可退回！",0);
				return false;
			}
			if("4"==opinionType){
				transition = selectBackTransition(taskId);
				if( transition != "NONE" ) {
					if( isEmpty(transition) ) {
						alert("请选择一个退回路径！",0);
						return false;
					}
				}else if(transition == "NONE" ){
					transition="";
				}
			}
			
// 			if(confirm("确认退回?")) {
// // 				document.location.href = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+taskId;
// 				var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+taskId;
// 				urlSubmit(url,formId,requestForm);
// 			}else{
// 				return false;
// 			}
			alert(top.getMessage("CONFIRM_OPERATION","退回"),2,function(){
				var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+taskId;
					urlSubmit(url,formId,requestForm);
				
			});
			
		}
	
		function selectProcess() {
			var result =  window.showModalDialog(webPath+'/processDefinition/selectProcess','选择流程','dialogWidth:600px;dialogHeight:450px;status:no;');
			if( isEmpty(result) ) {
				return "";
			}
			return result;
		}
</script>
