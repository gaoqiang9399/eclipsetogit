<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="app.base.User"%>
<%@page language="java" import="com.dhcc.workflow.NodeType"%>
<%@ page import="app.tech.oscache.CodeUtils" %>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/interface/workflowDwr.js'></script>
<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript">
<%
String orgNo = (String)User.getOrgNo(request);
String taskId = (String)request.getParameter("taskId");
String activityType = (String)request.getParameter("activityType");
String isAssignNext = (String)request.getParameter("isAssignNext");
//发回重审进行再次提交时 1代表按照流程配置的角色进行审批 0代表提交给该岗位之前审批过的人员进行审批
String rolebasckApproveType = new CodeUtils().getSingleValByKey("ROLLBACK_APPROVE_TYPE");
%>
		//当前岗位前面审批过的岗位
		var befNodesjsonArray='${befNodesjsonArray}';
		var rolebasckApproveType="<%=rolebasckApproveType%>";
        // 当前岗位类型 pass正常审批流转到的岗位 rollback发回重审流程到的岗位
        var approveType = '${approveType}';
        var lastApproveResult = '${lastApproveResult}';//上次审批意见类型
		if(befNodesjsonArray!=""){
			befNodesjsonArray = eval("(" + '${befNodesjsonArray}' + ")");
		}
		$(function(){
			//给发回岗位下拉赋值
			WkfApprove.setRollbackOption(befNodesjsonArray);
			//根据意见类型初始化，发回岗位显示和隐藏
			WkfApprove.opinionTypeChange();
		})
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
			// 判断任务是否还存在
			if(!ifExistence()){
				return;
			}
			if("3"==opinionType||"4"==opinionType){//3退回上环节4发回重审
				return approveRollback(urlstr,opinionType,formId,requestForm);
			}else{
				return doCommit(appNo,urlstr,opinionType,formId,requestForm);
			}
		}
		function ifExistence(appNo){
			var ifExistenceFlag = false;
			$.ajax({
				url : webPath + "/sysTaskInfo/getSysTaskInfoAjax",
				data : {
					wkfTaskNo:<%=taskId%>
					},
				type : 'post',
				dataType : 'json',
				async: false,
				success : function(data) {
					if (data.flag == "success") {
						ifExistenceFlag = true;
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
			return ifExistenceFlag;
		}
		function doCommit(appNo,urlstr,opinionType,formId,requestForm) {
			var alertMessage="";
			if(opinionType=="2"){//否决，特别提示
				alertMessage=top.getMessage("CONFIRM_APPROVAL_REJECT");
			}else{
				alertMessage=top.getMessage("CONFIRM_COMMIT");
			}
			dwr.engine.setAsync(false);
			workflowDwr.getTask(<%=taskId%>,function(data){
				if(data=="false"){
					window.top.alert(top.getMessage("NO_TASK_EXIST"),0,function(){
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src","${webPath}/sysTaskInfo/getListPage?pasMaxNo=1");
						$(top.window.document).find("#taskShowDialog").remove();
					});
				}else{
					window.top.alert(alertMessage,2,function(){
						getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm);
					});
				}
			});
			
			
		}
		
		function getInfoAndSelect(appNo,urlstr,opinionType,formId,requestForm){
			dwr.engine.setAsync(false);
			
			var activityType = '<%=activityType%>';
			var formData = $(formId).serializeArray();
			var dataParam = JSON.stringify(formData);
			workflowDwr.getTaskInfo(appNo,<%=taskId%>, dataParam, function(data) {
				var transition = selectTransition(<%=taskId%>);
				var isAssignNext = '<%=isAssignNext%>';
				if(transition=="END"){
					isAssignNext="0";
				}
				if(isEmpty(transition)){
					return false;
				}
				if(data != null){
					var	nextUser = "";
					if(opinionType=="1"){
						if (data[2] == '-1') {// 之后为判定节点，判定节点执行后是结束节点，不需要选择下一岗位人员
							nextUser = "nextUser";// nextUser不能为空
							var url = urlstr + "&nextUser=" + nextUser + "&transition=" + transition + "&taskId="+<%=taskId%>;
							urlSubmit(url, formId, requestForm);
						} else if(isAssignNext == "0" || isAssignNext=="-1") {//当人工分派下一环节执行人员 不可配置时，该值是-1（与结束节点相连的任务节点会出现这种情况）
							var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
							urlSubmit(url,formId,requestForm);
						}else{
						    //当本次提交是发挥重审提交，且此节点必须指派审批人时，按照
                            //ROLLBACK_APPROVE_TYPE 发回重审进行再次提交时 0代表提交给该岗位之前审批过的人员进行审批 1代表按照流程配置的角色进行审批
                            if (lastApproveResult != undefined && approveType != undefined){//暂时适用于业务审批功能
                                //如果上次是发回重审，再次提交。且ROLLBACK_APPROVE_TYPE为0，不再弹框指派审批人直接提交
                                if (lastApproveResult == '4' &&
                                    rolebasckApproveType == '0' &&
                                    approveType =='rollback'){
                                    var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
                                    urlSubmit(url,formId,requestForm);
                                }else {
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
                            }else {
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

						}
					}else{
						var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+<%=taskId%>;
						urlSubmit(url,formId,requestForm);		
					}
				}
			});
		}
		
		function urlSubmit(url,formId,requestForm){
			if(requestForm != ''){
				var formData=$(formId).serializeArray();
				var dataParam = JSON.stringify(formData); 
				var datas = [];
				//业务审批、合同审批、放款审批保存审批意见处理费用相关
				if(requestForm=="applySP"){
                    $("#busfee-list").find("tbody tr")
                        .each(
                            function(index) {
                                var entity = {};
                                entity.id = $(this).find("input[name=feeId]").val();
                                entity.itemNo = $(this).find("input[name=itemNo]")
                                    .val();
                                entity.rateScale = $(this).find(
                                    "input[name=rateScale]").val().replace(/,/g, "");
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
						if(data.flag=="success"){
							//审批提醒弹窗，不自动关闭
							window.top.alert(data.msg,3,function(){
								if(requestForm == "dealerInf"){//经销商补充信息
									myclose_click();
								}else{
								    var callback = top.callbackInViewpointByApprove;
                                    if (callback !== undefined && typeof(callback) == "function") {
                                        callback.call(this);
                                        top.callbackInViewpointByApprove = undefined;
                                    } else {
                                        var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1&pasMaxNo=1";
                                        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
                                    }
                                    setTimeout(function() {
                                        $(top.window.document).find("#taskShowDialog").remove();
                                    }, 100);
                                }
							});									
						}else{
							alert(data.msg,0);
							// var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1&pasMaxNo=1";
                            //
							// $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							//
							// setTimeout(function() {
							// 	$(top.window.document).find("#taskShowDialog").remove();
							// }, 100);
						}
					}
				});	
			}else{
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.flag=="success"){
							//审批提醒弹窗，不自动关闭
							window.top.alert(data.msg,3,function(){
								var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1&pasMaxNo=1";
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								$(top.window.document).find("#taskShowDialog").remove();
							});
							
						}else{
							alert(data.msg,0);
							var url=webPath+"/sysTaskInfo/getListPage?entranceNo=1&pasMaxNo=1";
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							$(top.window.document).find("#taskShowDialog").remove();
						}
					}
				});	
			}
			
			
		};
		
		
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
<%-- 				var result =  window.showModalDialog('<%=request.getContextPath() %>/wkfApprovalUser/findUserForSignTask?wkfRoleNo=' + wf_roleno+'&wkfBrNo='+ branchId +'&title='+title,'','dialogWidth:800px;dialogHeight:450px;status:no;'); --%>
                if(isAssignNext=="2"){//如果是选择必须指定下一岗位审批人，弹出选择审批人
                    getNextUserPageForSignTask(wf_roleno,candidateUser,branchId,function(data){
                        var result = data.selectedValue;
                        if(isEmpty(result)){
                            if(typeof(callback)== "function"){
                                callback("");
                            }
                        }
                        nextUser = result;
                        if(typeof(callback)== "function"){
                            getFinalUser(nextUser,callback);
                        }
                    });
                }else{
                    $.ajax({
                        url : webPath+'/wkfApprovalUser/findUserForSignTaskAjax?wkfRoleNo='+wf_roleno+'&wkfBrNo='+branchId,
                        type : "POST",
                        dataType : "json",
                        success : function(data) {
                            var result = data.selectedValue;
                            nextUser = result;
                            if(typeof(callback)== "function"){
                                getFinalUser(nextUser,callback);
                            }
                        }
                    });
                }



				/* getNextUserPageForSignTask(wf_roleno,branchId,function(data){
					var result = data.selectedValue;
					if(isEmpty(result)){
						if(typeof(callback)== "function"){
							callback("");
						}
					}
					nextUser = result;
					if(typeof(callback)== "function"){
						getFinalUser(nextUser,callback);
					}
				}); */
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
var getNextUserPageForSignTask = function(wkfRoleNo,wkfUserName,wkfBrNo,callback){
	dialog({
		id:"nextUserDialog",
		title:"选择会签人员",
		url:webPath+'/wkfApprovalUser/findUserForSignTask?wkfRoleNo='+wkfRoleNo+'&wkfBrNo='+wkfBrNo+'&wkfUserName='+wkfUserName,
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
//选择下一步操作员页面
var getNextUserPage = function(title,wkfRoleNo,wkfBrNo,callback){
	dialog({
		id:"nextUserDialog",
		title:"选择操作员",
		url:webPath+'/wkfApprovalUser/findApprovalUserByPage?wkfRoleNo='+wkfRoleNo+'&wkfBrNo='+wkfBrNo+'&title='+title+'&taskId='+<%=taskId%>,
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
				result =  window.showModalDialog(webPath+'/Transition/findBackTransition?taskId='+taskId,'','dialogWidth:500px;dialogHeight:300px;status:no;');
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
	
};
	
function selectProcess() {
	var result =  window.showModalDialog(webPath+'/processDefinition/selectProcess','选择流程','dialogWidth:600px;dialogHeight:450px;status:no;');
	if( isEmpty(result) ) {
		return "";
	}
	return result;
};

function commitProcessForOption(appNo,urlstr,formId,requestForm,taskId,isAssignNext,opinionType,activityType,dataParam){//下拉方式的审批调用
			if("3"==opinionType||"4"==opinionType){//3退回上环节4发回重审
				return approveRollback(urlstr,opinionType,formId,requestForm);
			}else{
				return doCommitForOption(appNo,urlstr,opinionType,formId,requestForm,taskId,isAssignNext,activityType,dataParam);
			}
		}
		
		function doCommitForOption(appNo,urlstr,opinionType,formId,requestForm,taskId,isAssignNext,activityType,dataParam) {
			var alertMessage="";
			if(opinionType=="2"){//否决，特别提示
				alertMessage=top.getMessage("CONFIRM_APPROVAL_REJECT");
			}else{
				alertMessage=top.getMessage("CONFIRM_COMMIT");
			}
			dwr.engine.setAsync(false);
			workflowDwr.getTask(taskId,function(data){
				if(data=="false"){
					window.top.alert(top.getMessage("NO_TASK_EXIST"),0,function(){
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",webPath+"/sysTaskInfo/getListPage?pasMaxNo=1");
						$(top.window.document).find("#taskShowDialog").remove();
					});
				}else{
					window.top.alert(alertMessage,2,function(){
						getInfoAndSelectForOption(appNo,urlstr,opinionType,formId,requestForm,taskId,isAssignNext,activityType,dataParam);
					});
				}
			});
			
			
		}
		
		function getInfoAndSelectForOption(appNo,urlstr,opinionType,formId,requestForm,taskId,isAssignNext,activityType,dataParam){
			dwr.engine.setAsync(false);
			var transition = selectTransitionForOption(taskId);
			if(transition=="END"){
				isAssignNext="0";
			}
			if(isEmpty(transition)){
				return false;
			}
			workflowDwr.getTaskInfo(appNo,taskId, dataParam, function(data) {
				if(data != null){
					var	nextUser = "";
					if(opinionType=="1"){
						if (data[2] == '-1') {// 之后为判定节点，判定节点执行后是结束节点，不需要选择下一岗位人员
							nextUser = "nextUser";// nextUser不能为空
							var url = urlstr + "&nextUser=" + nextUser + "&transition=" + transition;
							urlSubmitForOption(url, formId, requestForm,dataParam);
						} else if(isAssignNext == "0" || isAssignNext=="-1") {//当人工分派下一环节执行人员 不可配置时，该值是-1（与结束节点相连的任务节点会出现这种情况）
							var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition+"&taskId="+taskId;
							urlSubmitForOption(url,formId,requestForm,dataParam);
						}else{
							selectNextOpUserForOption(appNo,taskId,transition,activityType,isAssignNext,function(data){
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
										var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition;
										urlSubmitForOption(url,formId,requestForm,dataParam);
								
								}else{
									if(typeof(nextUser)!="undefined" && nextUser!=null && nextUser!=""){
										var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition;
										urlSubmitForOption(url,formId,requestForm,dataParam);
									}
								}
							
							});
						}
					}else{
						var url = urlstr+"&nextUser=" +nextUser+"&transition="+transition;
						urlSubmitForOption(url,formId,requestForm,dataParam);		
					}
				}
			});
		}
		//选择下一个审批人员
function selectNextOpUserForOption(appNo,taskId, transitionName, activityType,isAssignNext,callback){
	dwr.engine.setAsync(false);
	var nextUser = "";
	var orgNo = "<%=orgNo%>";
	var designateType = "";
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
				getNextUserPageForSignTask(wf_roleno,candidateUser,branchId,function(data){
					var result = data.selectedValue;
					if(isEmpty(result)){
						if(typeof(callback)== "function"){
							callback("");
						}
					}
					nextUser = result;
					if(typeof(callback)== "function"){
						getFinalUser(nextUser,callback);
					}
				});
			}else{
				if(isEmpty(nextUser)&&isEmpty(candidateGroup)&&!isEmpty(candidateNextUser)){
					nextUser=candidateNextUser;
					if(typeof(callback)== "function"){
						getFinalUser(nextUser,callback);
					}
				}else{
					getNextUserPageForOption(title,wf_roleno,branchId,taskId,function(data){
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
function urlSubmitForOption(url,formId,requestForm,dataParam){
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					//$this.changeTaskSts(info);
					window.top.alert(data.msg,3);
					$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",webPath+"/sysTaskInfo/getListPage?entranceNo=1");
					$(top.window.document).find("#taskShowDialog").remove();
					// alert(top.getMessage("SUCCEED_OPERATION"));
				}else if(data.flag=="error"){
					if(data.flag!==undefined&&data.flag!=null&&data.flag!=""){
						alert(data.msg,0);
					}else{
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);						 
			}
		});
	}
	function selectTransitionForOption(taskId) {
		var result = "";
		if( isEmpty(taskId) ) {
			taskId = taskId;
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
	};
		//选择下一步操作员页面
var getNextUserPageForOption = function(title,wkfRoleNo,wkfBrNo,taskId,callback){
	dialog({
		id:"nextUserDialog",
		title:"选择操作员",
		url:webPath+'/wkfApprovalUser/findApprovalUserByPage?wkfRoleNo='+wkfRoleNo+'&wkfBrNo='+wkfBrNo+'&title='+title+'&taskId='+taskId,
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
</script>
