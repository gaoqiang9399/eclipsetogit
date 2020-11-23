<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	window.name = "curWindow";
			function cancelClick() {
				window.close();
			}
			
			function enterClick(obj) {
				var selectedValue = obj.split("=")[1];
				if( selectedValue == "" ) {
					alert("请选择流转路径！");
					return;
				}
				window.returnValue = selectedValue;
				window.close();
				return;
			}
		</script>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong>下一审批环节</strong>
			</div>
		</div>
		<form method="post" theme="simple" name="cms_form"
		action="${webPath}/transition/findNextTransition" target="curWindow">
		<input name="taskId"  id="taskId" type="hidden" value=${taskId} />
		<div id="content" class="table_content"  style="height: auto;">
			<dhcc:tableTag paginate="transitionList" property="tablewkf0011" head="true"  />
		</div>
	</form>
</body>
</html>