<%@ page language="java" import="java.util.*,com.dhcc.wxtld.core.base.*,com.dhcc.wxtld.core.domain.*,com.dhcc.wechat.entity.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<title>进件</title>
		<script src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
		<script src="https://g.alicdn.com/ilw/ding/0.9.2/scripts/dingtalk.js"></script>
		<script type="text/javascript" src="/factor/component/doc/webUpload/js/jquery-1.11.0.js"></script>
		<script>
			$(function() {
				MfAppBusApply.getDDReady();
				dd.ready(function() {
					
				})
					
			})
		</script>
	</head>

	<body>
		<input type="hidden" id="phone" />
		<input type="hidden" id="code" />
		<div id="userInfo">to be continued</div>
		 
	</body>

</html>