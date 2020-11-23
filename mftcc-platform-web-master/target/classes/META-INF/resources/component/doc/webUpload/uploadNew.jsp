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
	href="${webPath}/component/doc/webUpload/css/upload.css" />
<script type="text/javascript"
	src="${webPath}/UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}UIplug/ViewImg/viewer.css"
	type="text/css">
<script type="text/javascript">
			query = "<%=request.getAttribute("query")%>";
	$(function() {
		$.ajax({
			type:"post",
			async:false,
			cache:false,
			url:webPath+"/docManage/initUploadNewAjax",
			data:{
				scNo:'${scNo}',
				relNo:'${relNo}',
				cusNo:'${cusNo}'
			},
			dataType:"json",
			success:function(jsonData){
				var zTreeObj = $.fn.zTree.init($("#uploadTree"), setting, jsonData.zTreeNodes);
			},
			error:function(){
				alert("error");
			}
		});
		$("body").height($(window).height());
		$("body").mCustomScrollbar({
			theme:"minimal-dark"
		});
	});
	function viewFile(imgInfo){
		window.top.loadding();
		notie.alert(4, '文件正在打开请稍等', -1);
		$.ajax({
			type:"post",
			url:webPath+"/docUpload/viewFileAjax",
			dataType:"json",
			data:imgInfo,
			success:function(jsonData){
				if(jsonData.flag=="success"){
					window.top.dhccModalDialog.open(webPath+'/UIplug/PDFjs/web/viewer.html?file=${webPath}file/'
					+jsonData.viewPath,jsonData.fileName,function(){},"90","90","400","300");
				}else{
					window.top.alert("不支持的文档类型或文件不存在！",0);
				}
			},
			error:function(){
				window.top.alert("不支持的文档类型或文件不存在！",0);
			},complete:function(){
				window.top.loaded();
				notie.alert_hide();
			}
		});
	}
</script>
<style type="text/css">
body {
	background-color: #ffffff;
}
</style>
</head>

<body>
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