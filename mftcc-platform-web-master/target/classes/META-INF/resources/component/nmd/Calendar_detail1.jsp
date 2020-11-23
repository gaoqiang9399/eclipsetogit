<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程修改</title>
		<script src="${webPath}/creditapp/js/sys_manage.js" type="text/javascript"></script>
	</head>
	
	<body class="body_bg">
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${pageContext.request.webPath}/workCalendar/update">
						<dhcc:formTag property="formhom2003" mode="query" />
					<div class="from_btn">
				
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