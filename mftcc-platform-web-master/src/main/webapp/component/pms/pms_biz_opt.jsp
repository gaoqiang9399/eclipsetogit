<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link rel="stylesheet" href="${webPath}/component/pms/zTree_v3/css/zTreeStyle/zTreeStyle.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/pms/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/pms/js/pmsBizOpt.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/component/pms/css/pmsBizOpt.css" />
		<script type="text/javascript">
		 var path = '${webPath}';
		</script>
	</head>
	<body>
		<ul id="pmsBizTree" class="ztree" style="padding: 0;"></ul>
	</body>
</html>