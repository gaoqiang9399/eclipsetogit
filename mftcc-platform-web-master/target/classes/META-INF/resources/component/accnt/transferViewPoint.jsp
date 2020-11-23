<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String transferId = (String)request.getParameter("transferId");
//String taskId = (String)request.getParameter("taskId");
/* String cusType = (String)request.getParameter("cusType");
String jsonBean = (String)request.getAttribute("jsonBean"); */
JSONArray wkfArray = null;
if(request.getAttribute("wkfVpList")!=null){
	wkfArray = JSONArray.fromObject(request.getAttribute("wkfVpList"));
}
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%-- <jsp:include page="/creditapp/talkjs.jsp"></jsp:include> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/xixi.js"></script>
		<%-- <script type="text/javascript" src="${webPath}/layout/view/page/js/wkfviewpoint.js"></script> --%>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/wkfviewpoint.css" />
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.dev.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.month.js"></script>
		<style type="text/css">
			.inner-center{
				height : calc(100% - 150px) !important;
			}
		</style>
		<script type="text/javascript">
		//审批
		function Wkf_addDiyDom(treeId, treeNode) {
				var liObj = $("#" + treeNode.tId).empty();
				var icon = "<span id='" +treeNode.tId+"_icon' class='" +treeId+"_icon'><i class='fa fa-circle'></i></span>";
				var line = "<span id='" +treeNode.tId+"_line' class='" +treeId+"_line'></span>";
				var endDate = "<span id='" +treeNode.tId+"_date' class='" +treeId+"_date'>"+treeNode.end.split(" ")[0]+"</span>";
				var description = "<span id='" +treeNode.tId+"_description' class='" +treeId+"_description'>"+treeNode.description+"</span>";
				var endTime = "<span id='" +treeNode.tId+"_time' class='" +treeId+"_time'>"+treeNode.end.split(" ")[1]+"</span>";
				var optName = "<span id='" +treeNode.tId+"_optName' class='" +treeId+"_optName'>"+treeNode.optName+"</span>";
		/* 		var result = "<span id='" +treeNode.tId+"_result' class='" +treeId+"_result'>"+treeNode.result+"</span>"; */
				var approveIdea = "<span id='" +treeNode.tId+"_approveIdea' class='" +treeId+"_approveIdea' >"+treeNode.approveIdea+"</span>";
				liObj.append(icon+line+endDate+description+endTime+optName+approveIdea);
				$("#" +treeNode.tId+"_line").css("height",liObj.outerHeight()-10+"px");
				
			};
		var Wkf_zTreeObj,
			Wkf_setting = {
				view: {
					selectedMulti: false,
					showIcon: false,
					addDiyDom: Wkf_addDiyDom
				}
			},
			Wkf_zTreeNodes = [];
		var vpNo="3";
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		var wkfModeNo = '<%=request.getParameter("wkfModeNo") %>';
		var wkfVpNo = '<%=request.getParameter("wkfVpNo") %>';
		var wkfVpList =  eval("("+'<%=wkfArray %>'+")");
		<%-- var jsonBean = eval("("+ '<%=jsonBean%>'+")"); --%>
		$(function(){
			$.ajax({
				type: "post",
				data:{appNo:"${transferId}"},
				dataType: 'json',
				url: webPath + "/wkfApprovalOpinion/getApplyApprovalOpinionList",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					Wkf_zTreeNodes=data.zTreeNodes;
					Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
				}
			});
			
		});
		
		function doSubmit(){
			if(isEmpty($("#opinionType").val())){
				alert(top.getMessage("NOT_APPROVAL_TYPE_EMPTY"));
				return;
			} 
			if(isEmpty($("#approvalOpinion").val())){
				alert(top.getMessage("NOT_APPROVAL_IDEA_EMPTY"));
				return;
			}
			var dataParam = JSON.stringify($("#transfer-form").serializeArray()); 
			<%--   jQuery.ajax({
					url:"MfAccntTransferActionAjax_commitProcess.action?transferId="+<%=transferId%>
					+"&opinionType="+$("#opinionType").val()+"&approvalOpinion="+$("#approvalOpinion").val()+"&taskId="+<%=taskId%>,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							$.myAlert.Alert(top.getMessage("SUCCEED_COMMIT"));
						}else if(data.flag=="error"){
							 window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							$.myAlert.Alert(top.getMessage("FAILED_SUBMIT"));
						}
					},error:function(data){
						if(alertFlag){
							 window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
						}else{
							$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
						}
					}
				}); --%>
			commitProcess(webPath + "/mfAccntTransfer/commitProcess?transferId="+"<%=transferId%>"
			+"&opinionType="+$("#opinionType").val()+"&approvalOpinion="+$("#approvalOpinion").val()+"&ajaxData="+dataParam,"#transfer-form","");
		}
		</script>
<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" />
</head>
 
<body>
	<!-- 下拉菜单 -->
	<div class="inner-north" style="background-color: #EAEBEC;">
		<div id="wrapper">
			<ul class="lavaLamp">
			</ul>
			<div class="app-screen">
					<button type="button" class="drop-down fa fa-angle-down" ></button>
					<dhcc:thirdRoleButton value="提交" action="提交" onclick="doSubmit()"></dhcc:thirdRoleButton>
					<dhcc:thirdRoleButton value="返回" action="返回" onclick="getBigForm()"></dhcc:thirdRoleButton>
					<dhcc:thirdRoleButton value="打印" action="打印" onclick="submitUpdate()"></dhcc:thirdRoleButton>
			</div>
			<%-- <div>
				<form id="transfer-form">
					 <dhcc:formTag property="formaccnttrans0002"  mode="query"></dhcc:formTag>
				</form>
			</div> --%>
			<div class="bigform_content">
				<div class="content_table">
				<form id="transfer-form" >
				<dhcc:bigFormTag property="formaccnttrans0002" mode="query"/>
				</form>	
				</div>
			</div>
			<div class="info">
					 <ul id="wfTree" class="ztree">
					 </ul> 
				</div>
		</div>
		<div id="wrapper-content" class="wrapper-content" style="display:none;width: 100%;height: auto;position: absolute; background-color: rgba(222, 222, 222, 0.9); ">
		
		
		</div>
		<div class="app-screen-list">
				<ul>
						<li><dhcc:thirdRoleButton value="资料上传" action="资料上传" onclick=""></dhcc:thirdRoleButton></li>
						<li><dhcc:thirdRoleButton value="资料上传" action="资料上传" onclick=""></dhcc:thirdRoleButton></li>
						<li><dhcc:thirdRoleButton value="资料上传" action="资料上传" onclick=""></dhcc:thirdRoleButton></li>
				</ul>
		</div>
	</div>
	
	<!-- 内容 -->
	<div class="inner-center">
	<!-- 	<iframe src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
	 -->
	<%--  <dhcc:formTag property="formaccnttrans0002"  mode="query"></dhcc:formTag> --%>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=<s:property value='taskId'/> />
	<input name="activityType" id="activityType" type="hidden" value=<s:property value='activityType'/> />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=<s:property value='isAssignNext'/> />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=<s:property value='designateType'/> />
	
		<div class="cell" data-handle=".handle" style="bottom:0px; left:0px; right: 0px; height: 100px; margin:8px;background-color:#EBEBEB" cellid="cell_1">
			<div class="cover">
				<div class="handle">
					<span>审批信息</span>
				</div>
			</div>
			<div class="info">
				<div class="appType">
					<label for="opinionType">意见类型</label>
					<select id="opinionType">
					</select>
				</div>
				<div class="appContent">
					<label for="approvalOpinion">审批意见</label>
					<textarea id="approvalOpinion" rows="3" cols="20" placeholder="请介绍自己..."></textarea>
				</div>
				
			</div>
		</div>
		
</body>
</html>