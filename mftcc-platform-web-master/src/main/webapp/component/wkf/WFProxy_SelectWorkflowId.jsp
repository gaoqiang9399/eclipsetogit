<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	window.name = "curWindow";
	function returnWorkflowId(str){
		window.returnValue = (str.split("=")[1]);
		window.close();
	}
	</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wFProxy/selectWorkflowId" target="curWindow">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle">系统可用流程配置列表</div>
						</div>
						<dhcc:tableTag paginate="pocessDefinitionList" property="tablewkf1009" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>