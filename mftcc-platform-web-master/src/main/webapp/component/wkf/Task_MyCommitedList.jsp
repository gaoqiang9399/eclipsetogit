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
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/task/myCommitedTask">
		<p class="p_blank">&nbsp;</p>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
					<div style="float: left" class="tabTitle">
									已办任务信息列表
								</div>
						<dhcc:tableTag paginate="historyTaskList" property="tablewkf0030" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>