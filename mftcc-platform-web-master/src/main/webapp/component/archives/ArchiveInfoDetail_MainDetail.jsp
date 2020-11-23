<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			 + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>归档明细信息</title>
<script type="text/javascript"
	src="${webPath}component/doc/webUpload/js/jquery-1.11.0.js"></script>
<link rel="stylesheet"
	href="${webPath}UIplug/Font-Awesome/css/font-awesome.css" />
<%--滚动条js 和鼠标滚动事件js--%>
<link rel="stylesheet"
	href="${webPath}UIplug/customScrollbar/css/jquery.mCustomScrollbar.css"
	type="text/css">
<script type="text/javascript"
	src="${webPath}UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript"
	src="${webPath}UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<style type="text/css">
body { /* background-color: #ffffff; */
	margin: 0px;
	background: #f2f2f2;
}

p {
	padding: 0;
	margin: 0;
}

table {
	font-size: 12px;
	color: #999;
}

table td {
	padding-left: 20px;
	width: 125px;
}

table td .formLbl {
	color: #000;
	padding-top: 20px;
	display: block;
}

table td div {
	word-break: break-all;
	word-wrap: break-word;
}
</style>
<script type="text/javascript">
	/* (function($){
	 $(window).load(function(){
	
	 });
	 })(jQuery);
	 */
	$(function() {
		$("body").height($(window).height());
		$("body").mCustomScrollbar({
			theme : "minimal-dark"
		});
	});
</script>
</head>
<body>
	<dhcc:bigFormTag property="formdl_archive_detail02" mode="query" />
</body>
</html>
