<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
		<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinMain_List.js?v=${cssJsVersion}'></script>
		<script type="text/javascript" src='${webPath}/component/importexcel/js/MfBusImportExcel_List.js?v=${cssJsVersion}'></script>
		<script type="text/javascript" >
			var isUpload = false;
			var cusNo = '${cusNo}';
			var webPath = "${webPath}";
			var sysProjectName = "${sysProjectName}";
			$(function(){
                BusImportExcel.init();
			});
		</script>
	</head>
	<body class="bg-white " style="overflow: hidden;">
		<div class="container">
			<div class="row clearfix  margin_top_20">
				<div class="col-md-12 column">
					<ul class="tab-ul">
						<li  onclick="BusImportExcel.exportExcel('5');">
							<span>农行代扣功能模板下载</span> <i class="i i-xiazai"></i>
						</li>
					</ul>
				</div>
			</div>
			<div class="row clearfix margin_top_80">
				<div class="col-xs-6 col-xs-offset-3 column" id="newParm-div">
					<div id="uploader" class="wu-example">
						<div id="thelist" class="cb-upload-div input-group input-group-lg">
							<input name="uploadFile" readonly="readonly" type="text" class="form-control">
							<span id="picker" readonly="readonly" class="input-group-addon pointer">上传...</span>
							<div class="hidden" id="picker-outer"></div>
						</div>
					</div>
					<div style="color: red; margin-bottom: 50px;" id="showMsg"></div>
				</div>
				<div id="finData" class="data-list" style="display: none">
					<input type="hidden" name="finData">
					<input type="hidden" name="finRptType">
					<input type="hidden" name="finRptDate">
					<input type="hidden" name="cusName">
				</div>
			</div>
		</div>
	</body>	
</html>
