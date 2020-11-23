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
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/transition/findBackTransition" target="curWindow">
		<input name="taskId"  id="taskId" type="hidden" value=${taskId} />
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<dhcc:tableTag paginate="transitionList" property="tablewkf0012" head="true"  />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>