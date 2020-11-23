<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
		.bigForm_content_form table tr td {
			padding-bottom: 0px;
			padding: 0 10px;
		}
		</style>
	</head>
	<body class="body_bg">
		<div class="bigform_content" style="width: 80%;margin: 20px auto;">
			<div class="bigForm_content_form">
				<c:if test="${mfCusCorpBaseInfo==null}">
					<div style="text-align: center;">未登记客户基本信息</div>
				</c:if>
				<c:if test="${mfCusCorpBaseInfo!=null}">
				<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusCorpBaseInfo/updateAjaxByOne">
					<dhcc:propertySeeTag property="formcuscorp00001" mode="query"/>
				</form>	
				</c:if>
			</div>
		</div>
	</body>
</html>