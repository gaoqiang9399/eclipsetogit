<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/tablelistshowdiv.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	function reAssignForApp(parm) {
		parm=parm.split("?")[1];
		var taskId=parm.split("&")[0];
		taskId=taskId.split("=")[1];
		var appId=parm.split("&")[1];
		appId=appId.split("=")[1];
		funcWkfApprovalUserForReAssignPop('wkfUserName','wkfUserName',taskId);
		var result=document.getElementsByName("wkfUserName")[0].value;
	
		if( result!="" && result != null && result != "undefined" ) {
			
			if(confirm("确定改派给[" + result + "]?")){
				window.location.href=webPath+"/Task/reAssignForApp?taskId=" + taskId + "&appId="+appId +"&UserId=" + result;
			}
		}
	}
	</script>
	<body class="body_bg">

	<input type="hidden" name="wkfUserName">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/task/findByPageForApp">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0021" mode="query" /></td>
							</tr>
						</table>
						<div class="tools_372">
							<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<p class="p_blank">&nbsp;</p>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
						<div style="float: left" class="tabTitle">
									任务改派信息列表
								</div>
						</div>
						<dhcc:tableTag paginate="taskList" property="tablewkf0013" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>