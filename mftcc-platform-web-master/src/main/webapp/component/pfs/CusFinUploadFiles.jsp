<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>财报批量导入</title>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinUploadFiles.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css">  
		<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinUploadFiles.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript">
			var cusNo = '<%=(String)request.getAttribute("cusNo")%>';
			var info = '<%=(String)request.getAttribute("info")%>';
		</script>
	</head>
<body class="body_bg">
	<div id="uploader" class="wu-example">
	    <!--用来存放文件信息-->
	    <div id="thelist" class="uploader-list"></div>
	     <div id="finData" class="data-list" style="display:none">
	     	<input type="hidden" name="finData">
	     </div>
	    <div class="btns">
	        <div id="picker">选择文件</div>
	        <button id="ctlBtn" class="btn btn-default">开始上传</button>
	        <button id="submitData" class="btn btn-default submitdata">导入数据</button>
	    </div>
	</div>
</body>
</html>