<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
<body onselectstart="return false">
	<div class="info">
		<form  method="post" theme="simple" name="operform" action="${webPath}/demo2/update">
			<dhcc:propertySeeTag property="formdemo0008" mode="query"/>
		</form>
    </div>
</body>
</html>