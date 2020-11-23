<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程修改</title>
	</head>
	
	<body class="body_bg">
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${pageContext.request.webPath}/workCalendar.update">
						<dhcc:formTag property="formhom2013" mode="query" />
					<div class="formRow">
							<input type="button" name="back" value="返回" onClick="func_close()" class="button_form" />
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function func_close(){
		self.close()
		window.opener.location.reload();
	}
	
</script>
</html>