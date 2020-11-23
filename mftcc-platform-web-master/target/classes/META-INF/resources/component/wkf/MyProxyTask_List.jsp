<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/tablelistshowdiv.js"></script>
<script type="text/javascript" src="${webPath}/component/pfs/js/pop.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	function reAssign(taskId) {
		taskId=taskId.split("=")[1];
		funcWkfApprovalUserPop('wkfUserName','wkfUserName');
		var result=document.getElementsByName("wkfUserName")[0].value;
	
		if( result!="" && result != null && result != "undefined" ) {
			
			if(confirm("确定改派给[" + result + "]?")){
				window.location.href="<%=webPath %>/Task/reAssign?taskId=" + taskId + "&UserId=" + result;
			}
		}
	}
	</script>
	<body class="body_bg">
	<input type="hidden" name="wkfUserName">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/task/findByPage">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle">代理任务列表</div>
						</div>
						<dhcc:tableTag paginate="taskList" property="tablewkf0027" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>