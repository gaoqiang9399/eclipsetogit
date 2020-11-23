<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- <%@ taglib prefix="s" uri="/struts-tags"%> --%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath %>">
<title></title>
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/webuploader/webuploader.css" />
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/js/upload.js"></script>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css?v=${cssJsVersion}" type="text/css">
<script type="text/javascript">
	var zTreeNodes = eval('('+'${zTreeNodes}'+')');
	query = "${dataMap.query}";
	var optType = '${optType}';
	var flag = '${flag}';

	var ajaxData = '${ajaxData}';
	ajaxData = JSON.parse(ajaxData);
	var successTemplateList = ajaxData.bizConfigs;
	var archiveBusinessInfo = ajaxData.archiveBusinessInfo;
	var cusNo = archiveBusinessInfo.cusNo;
	var appId = archiveBusinessInfo.appId;
	var pactId = archiveBusinessInfo.pactId;
	var pleId = archiveBusinessInfo.pleId;
	var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&pleId=' + pleId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
	$(function(){
		ArchiveInfoMain_fileList.init();
	});
</script>
<style type="text/css">
.upload_body{
	margin-top:250px;
}
.tip-div{
	text-align:center;
	line-height: 500px;
	font-size: 25px;
	color: #9DA4A9;
}
.template_div{
	margin-left:30px;
	margin-right:30px;
}
.template_div .list-table{
	margin-top:5px;
	
}
</style>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div id="fileArchive" class="tip-div hide">暂无需归档的文件</div>
			<div id="fileSeal" class="tip-div hide">暂无需要封档的文件</div>
			<div class="col-md-10 col-md-offset-1 colum margin_top_20" id="paperInfo" style="display:none">
					<div class="bootstarpTag fourColumn">
						<form  method="post" id="ArchiveInfoMainForm" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formdl_archive_main03" mode="query"/>
						</form>
					</div>
				</div>
			<div class="upload_body">
				<ul class="ztree" id="uploadTree"></ul>
			</div>
			<div id="template_div" class="template_div">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>文档模板</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
							<i class="i i-close-up"></i>
							<i class="i i-open-down"></i>
						</button>
					</div>
				</div>
				<div id="bizConfigs" class="template-config item-div padding_left_15 collapse in" aria-expanded="true"></div>
			</div>
			<div id="view-imgs">
				<div class="enlarge-img">
					<input class="close_btn" type="button" value="" onclick="close_view();" />
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
		</div>
		<div class="formRowCenter">
	 		<dhcc:thirdButton value="归档" action="关闭" typeclass="file-archive hide" onclick="ArchiveInfoMain_fileList.fileArchive();"></dhcc:thirdButton>
	 		<dhcc:thirdButton value="封档" action="封档" typeclass="file-seal hide" onclick="ArchiveInfoMain_fileList.fileSeal();"></dhcc:thirdButton>
	 		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	  	</div>
	</div>
	<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</body>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_fileList.js"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>