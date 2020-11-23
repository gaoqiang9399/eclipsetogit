<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusStaff.js"></script>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">员工信息</div>
				<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusStaff/updateAjaxByOne">
					<dhcc:propertySeeTag property="formcusstaffDetail" mode="query" />
				</form>	
			</div>
		</div>
	</body>
</html>