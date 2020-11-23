<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../include/pub.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<form method="post" theme="simple" name="operform"
					action="${webPath}/sysMenu/Insert">
					<dhcc:formTag property="formsys0016" mode="query" />
					<div class="from_btn">
						<dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						<dhcc:button typeclass="button_form" value="返回" action="返回" onclick="${webPath}/sysMenu/findByPage"></dhcc:button>
					</div>
				</form>
			</div>
			</div>
		</div>
	</body>
</html>