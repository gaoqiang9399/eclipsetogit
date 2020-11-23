<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ webPath + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath %>">
<title></title>
<script type="text/javascript"
	src="${webPath}/component/doc/webUpload/js/jquery-1.11.0.js"></script>
<script type="text/javascript"
	src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/doc/webUpload/webuploader/webuploader.css" />
<script type="text/javascript"
	src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<%-- <link rel="stylesheet"
	href="${webPath}UIplug/Font-Awesome/css/font-awesome.css" /> --%>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<%--滚动条js 和鼠标滚动事件js--%>
<link rel="stylesheet"
	href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css"
	type="text/css">
<script type="text/javascript"
	src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript"
	src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script> 
<script type="text/javascript"
	src="${webPath}/component/doc/webUpload/js/upload.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}" />
<script type="text/javascript"
	src="${webPath}/UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css"
	type="text/css">
<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
 <link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css?v=${cssJsVersion}"/>
<script type="text/javascript">
	var zTreeNodes = eval('('+'<%=request.getAttribute("zTreeNodes")%>' + ')');
	query = "<%=request.getAttribute("query")%>";
	$(function() {
		if(zTreeNodes.length == 0){
			$("#te").css('display','');
		}
	});
</script>
<style type="text/css">
body {
	background-color: #fff;
}
</style>
</head>

<body>
	<div id="te" style="display:none;text-align:center">未匹配到文档模型</div>
	<div class="upload_body">
		<ul class="ztree" id="uploadTree"></ul>
	</div>
	<div id="view-imgs">
		<div class="enlarge-img">
			<input class="close_btn" type="button" value=""
				onclick="close_view();" />
			<div class="img-tools">
				<span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>
				<strong class="title"></strong>
				<div class="view-img last-img">
					<div></div>
				</div>
				<div class="view-img next-img">
					<div></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</body>
</html>