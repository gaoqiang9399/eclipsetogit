<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/tablelistshowdiv.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	function reAssign(taskId) {
		taskId=taskId.split("=")[1];
		funcWkfApprovalUserPop('wkfUserName','wkfUserName',taskId);
		var result=document.getElementsByName("wkfUserName")[0].value;
		if( result!="" && result != null && result != "undefined" ) 
		{
			if(confirm("确定改派给[" + result + "]?")){
				window.location.href=webPath+"/task/reAssign?taskId=" + taskId + "&UserId=" + result;
			}
		}
	}
	</script>
	<body class="body_bg">
	<input type="hidden" name="wkfUserName">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/task/findByPage">
		<!-- <div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<dhcc:formTag property="formwkf0015" mode="query" />
								</td>
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
							<div style="float:left" class="tabTitle">流程任务列表</div>
						</div>
						<dhcc:tableTag paginate="taskList" property="tablewkf0023" head="true" />
					</div>
				</div>
			</div>
		</div> -->
		<div>
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<strong>流程任务列表</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:formTag property="formwkf0015" mode="query" />
					<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
					
				</div>
			</div>
			</div>
		</div>
		<div id="content" class="table_content" style="height: auto;">
			<dhcc:tableTag paginate="taskList" property="tablewkf0023" head="true" />
		</div>
	</form>
</body>
</html>