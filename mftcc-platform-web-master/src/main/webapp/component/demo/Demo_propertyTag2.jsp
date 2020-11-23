<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="layout layoutTag">
	<dhcc:markPoint markPointName="Demo_propertyTag2"/>
		<div id="tts" class='cell' style=' height:auto; background-color:#e8edf6' data-handle=".handle">
			<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
				<div class="info">
					<dhcc:propertySeeTag property="formdemo0003" mode="query"/>
					<a href="#" class="more font-small bold">更多信息<i class="i i-jiantoua"></i></a>
				</div>
			</form>	
		</div>
	</body>
</html>
