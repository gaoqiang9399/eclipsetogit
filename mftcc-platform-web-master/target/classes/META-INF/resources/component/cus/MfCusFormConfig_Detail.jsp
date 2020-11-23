<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">客户表单配置</div>
				<form  method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formcusfcon00001" mode="query" />
				</form>	
			</div>
		</div>
	</body>
</html>