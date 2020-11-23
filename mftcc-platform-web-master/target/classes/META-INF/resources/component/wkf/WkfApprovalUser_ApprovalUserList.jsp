<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
		<div class="formRowCenter hide">
			<dhcc:thirdButton value="确认" action="确认" onclick="WkfApprovalUser.multiSelect();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/wkf/js/WkfApprovalUser.js"> </script>
<script type="text/javascript" >
var multipleFlag = '${multipleFlag}';
var processId = '${processId}';
var nodeName = '${nodeName}';
var ifFilterFlag  = '${ifFilterFlag}';
var committeeFlag = '${committeeFlag}';
var committeeMember = '${committeeMember}';
var taskId = '${taskId}';
var creditAppId = '${creditAppId}';
var pasSubType = '${pasSubType}';
$(function(){
   WkfApprovalUser.init();
 });
window.name = "curWindow";
function cancelClick() {
	window.close();
}
	
function enterClick(lk){
	var parm=lk.split("?")[1];
	var parmArray=parm.split("&");
	var selectedValue = "";
	var userId=parmArray[0].split("=")[1];
	var userName=parmArray[1].split("=")[1];
	if( userId == "" ) {
		alert("请选择用户！");
		return;
	}
	selectedValue=userId+":"+userName;
	window.returnValue = selectedValue;
	var nextUser = new Object();
	nextUser.selectedValue = selectedValue;
	nextUser.displayName = userName;
	nextUser.wkfUserName = userId;
	parent.dialog.get('nextUserDialog').close(nextUser).remove();
};
</script>
</html>
