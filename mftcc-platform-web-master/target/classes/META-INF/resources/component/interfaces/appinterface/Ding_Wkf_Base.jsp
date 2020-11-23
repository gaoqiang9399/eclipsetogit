<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="app.base.User"%>
<%@page language="java" import="com.dhcc.workflow.NodeType"%>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/dwr.js'></script>
<script type='text/javascript' src='${webPath}/dwr/interface/workflowDwr.js'></script>
<%-- <script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script> --%>
<script type="text/javascript" src="${webPath}/component/interfaces/appinterface/js/DingWkfApprove.js"></script>
<script type="text/javascript">
<%
String orgNo = (String)User.getOrgNo(request);
String taskId = (String)request.getParameter("taskId");
String activityType = (String)request.getParameter("activityType");
String isAssignNext = (String)request.getParameter("isAssignNext");
String appId = (String)request.getParameter("appId");
%>
		//当前岗位前面审批过的岗位
		var befNodesjsonArray='${befNodesjsonArray}';
		if(befNodesjsonArray!=""){
			befNodesjsonArray = eval("(" + '${befNodesjsonArray}' + ")");
		}
		$(function(){
			;
			//给发回岗位下拉赋值
			DingWkfApprove.setRollbackOption(befNodesjsonArray);
			//根据意见类型初始化，发回岗位显示和隐藏
			DingWkfApprove.opinionTypeChange();
			//初始化下一审批岗位人员选择
			addNextApprovalPerson();
		})
		function isEmpty(val) {
			if( val == "undefined" || val == null || val == "" ) {
				return true;
			}
			return false;
		}
		
		function addNextApprovalPerson(){
			var appNo = '<%=appId%>';
			dwr.engine.setAsync(false);
			var activityType = '<%=activityType%>';
			var transition = selectTransition(<%=taskId%>);
			var isAssignNext = '<%=isAssignNext%>';
			if(isEmpty(transition)){
				return false;
			}
			;
			workflowDwr.getTaskInfo(appNo,<%=taskId%>, function(data) {
				if(data != null){
					var	nextUser = "";
					if(isAssignNext == "0" || isAssignNext=="-1") {//当人工分派下一环节执行人员 不可配置时，该值是-1（与结束节点相连的任务节点会出现这种情况）
					}else{
						fillNextPerson(appNo,<%=taskId%>,transition,isAssignNext);
						$("#nextApprovePerson-div").show();
					}
				}
			});
			
		}
		
		//该方法从selectNextOpUser方法复制而来，只保留获取数据的方法，以及走选择下一审批人的分支，其他分支代码全部删除，防止影响流程
		function fillNextPerson(appNo,taskId, transitionName, isAssignNext){
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
							return (nextUser);
					}
					
					var wf_roleno = getRoleNoByGroup(candidateGroup);
					if(!isEmpty(nextNodeType) && nextNodeType == "signtask" ){//下节点会签
					}else{
						if(isEmpty(nextUser)&&isEmpty(candidateGroup)&&!isEmpty(candidateNextUser)){
						}else{
							$.ajax({
								url : webPath+'/wkfApprovalUser/findApprovalUserByPageAjax?wkfRoleNo='+wf_roleno+'&wkfBrNo='+branchId,
								type : "get",
								dataType : "json",
								success : function(data) {
									var list =data.list;
									$("#nextApprovePerson").html("");
									for(var i in list){
										$option = $("<option></option>").text(list[i].displayname+"["+list[i].seq+"]").attr("value",list[i].wkfUserName);
										$("#nextApprovePerson").append($option);
									}
								},error:function(){
									
//			 						LoadingAnimate.stop();
//			 						top.alert(top.getMessage("FAILED_SUBMIT"),0);
								}
							});	
						/* 	getNextUserPage(title,wf_roleno,branchId,function(data){
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
							}); */
						}
					}
					
				}
			});
		}
		
	
		function isExpression(val) {
			return (val.indexOf("#") > 0 || val.indexOf("$") > 0);
		}
	
		function getRoleNoByGroup(candidateGroup) {
			;
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
			;
			var alertMessage="";
			if(opinionType=="2"){//否决，特别提示
// 				alertMessage=top.getMessage("CONFIRM_APPROVAL_REJECT");
				alertMessage="确认要否决吗？";//top.getMessage("CONFIRM_APPROVAL_REJECT");
			}else{
// 				alertMessage=top.getMessage("CONFIRM_COMMIT");
				alertMessage="确认要提交吗？"//top.getMessage("CONFIRM_COMMIT");
			}
			$.confirm(alertMessage, function() {
			  //点击确认后的回调函数
				getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm);
			  }, function() {
			  //点击取消后的回调函数
			  });
		/* 	window.top.alert(alertMessage,2,function(){
// 				if(!valiWkfDocIsUp(appNo)){
// 					return false;
// 				}
	
				getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm);
			}); */
			
		}
		
		function getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm){
			;
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
					if(opinionType=="1"){
						if(isAssignNext == "0" || isAssignNext=="-1") {//当人工分派下一环节执行人员 不可配置时，该值是-1（与结束节点相连的任务节点会出现这种情况）
							var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
							urlSubmit(url,formId,requestForm);
						}else{
							selectNextOpUser(appNo,<%=taskId%>,transition,isAssignNext,function(data){
								nextUser = data;
								if( isAssignNext == "1" && nextUser.length <= 0 ) {
									if(!confirm("确定不选择提交人员?")) {
										return false;
									}
								}else if( isAssignNext == "2" && nextUser.length <= 0 ) {
									alert("必须选择下一任务审批人员!");
									return false;
								
								}
								
								if(!isEmpty(activityType) && activityType == "signtask" ){//会签
										var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
										urlSubmit(url,formId,requestForm);
								
								}else{
									if(typeof(nextUser)!="undefined" && nextUser!=null && nextUser!=""){
										var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
										urlSubmit(url,formId,requestForm);
									}
								}
							
							});
						}
					}else{
						var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
						urlSubmit(url,formId,requestForm);		
					}
				}
			});
		}
		
		function urlSubmit(url,formId,requestForm){
			$.showLoading("正在加载...");
			;
			if(requestForm != ''){
				var formData=$(formId).serializeArray();
				var dataParam = JSON.stringify(formData); 
				var datas = [];
				;
				//业务审批、合同审批、放款审批保存审批意见处理费用相关
				if(requestForm=="applySP"){
					var idIndex = $("#busfee-div").find("thead th[name=id]").index();
					var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
					$("#busfee-div").find("tbody tr").each(function(index){
						var entity = {};
						$thisTr = $(this);
						entity.id = $thisTr.find("td").eq(idIndex).html().trim();
						entity.rateScale = $thisTr.find("td").eq(rateScaleIndex).find("input").val().replace(/,/g, "");
						datas.push(entity);
					});
				}else if(requestForm=="busSP"){
					if($('#bus_approval_list').length > 0){
						$("#bus_approval_list").find("tbody tr").each(function(index){
							var $thisTr = $(this);
							var entity = {};
							$thisTr.find("select, input").each(function(){
								var name = this.name;
								var val = $(this).val();
								entity[name] = val;
							});
							datas.push(entity);
						});
					}
				}
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					data:{ajaxData:dataParam,ajaxDataList:JSON.stringify(datas)},
					success : function(data) {
						$.hideLoading();
//  						LoadingAnimate.stop();
						if(data.flag=="success"){
							$.toast("提交成功");
							//引用页面的返回列表方法 如不能共用可以改为history.go(-1);							
							toList();
// 							location.href="${webPath}/dingBusApproval/getSysTaskApproveList";						
						}else{
							$.toast("提交失败", "cancel");
							
						}
					},error:function(){
						$.hideLoading();
						$.toast("提交失败", "cancel");
// 						LoadingAnimate.stop();
// 						top.alert(top.getMessage("FAILED_SUBMIT"),0);
					}
				});	
			}else{
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					success : function(data) {
//  						LoadingAnimate.stop();
						$.hideLoading();
						if(data.flag=="success"){
							//审批提醒弹窗，不自动关闭
							window.top.alert(data.msg,3,function(){
								var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								$(top.window.document).find("#taskShowDialog").remove();
							});
							
						}else{
							var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1";
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							$(top.window.document).find("#taskShowDialog").remove();
						}
					},error:function(){
// 						LoadingAnimate.stop();
						$.hideLoading();
						$.toast("提交失败", "cancel");
// 						top.alert(top.getMessage("FAILED_SUBMIT"),0);
					}
				});	
			}
			
			
		}
		
		

function selectNextOpUser(appNo,taskId, transitionName, isAssignNext,callback){
	;
	dwr.engine.setAsync(false);
	var nextUser = "";
	var orgNo = "<%=orgNo%>";
	var designateType = "";
	var activityType = '<%=activityType%>';
	workflowDwr.findExecutorByTransition(taskId, transitionName, designateType,orgNo,appNo, function(data){
		;
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
					//选择人员直接从页面中取
					
				//	getNextUserPage(title,wf_roleno,branchId,function(data){
						var result = $("#nextApprovePerson").val();
						if( isEmpty(result) ) {
							if(typeof(callback)== "function"){
								callback("");
							}
						}
						nextUser = result;
						if(typeof(callback)== "function"){
							getFinalUser(nextUser,callback);
						}
				//	});
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

var getFinalUser = function(nextUser,callback){
	;
	if(nextUser.length > 0) {
		var index = nextUser.indexOf(":");
		if(index > 0){
			alert("确定提交给[" + nextUser.substring(index+1) + "]?",2,function(){//确定
					callback(nextUser.substring(0,index));
			  },function(){//取消
					nextUser="";
					callback(nextUser);
			  });
		}else{
			callback(nextUser);
		}
	} else {
		callback(nextUser);
	}
};

function selectBackTransition(taskId) {
	var result = "";
	if(isEmpty(taskId)){
		taskId = <%=taskId%>;
	}
	dwr.engine.setAsync(false);
	workflowDwr.findBackTransition(taskId, function(data) {
		if(data != null ) { 
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
};
	
function approveRollback(urlstr,opinionType,formId,requestForm) {
	;
	var taskId = '<%=taskId%>';
	var activityType = '<%=activityType%>';
	var nextUser ="";
	var transition="";
	if(activityType == 'signtask'){
		$.alert("会签任务不可退回！");
		return false;
	}
	if("4"==opinionType){
		transition = selectBackTransition(taskId);
		if( transition != "NONE" ) {
			if( isEmpty(transition) ) {
				$.alert("请选择一个退回路径！");
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
	$.confirm("确定进行退回操作吗？", function() {
		//点击确认后的回调函数
		var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+taskId;
		urlSubmit(url,formId,requestForm);
	}, function() {
	  //点击取消后的回调函数
	});
	
};
	
function selectProcess() {
	var result =  window.showModalDialog(webPath+'/processDefinition/selectProcess','选择流程','dialogWidth:600px;dialogHeight:450px;status:no;');
	if( isEmpty(result) ) {
		return "";
	}
	return result;
};
</script>
