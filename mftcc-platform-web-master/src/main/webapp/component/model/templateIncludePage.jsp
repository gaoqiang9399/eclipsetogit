<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script> 

<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function() {
		templateIncludePage.init();
	});
</script>

<div id="template_div" class="template_div" style="display: none;">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>电子文档</span>
			<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
				<i class="i i-close-up"></i>
				<i class="i i-open-down"></i>
			</button>
			<button type="button" class=" btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.downloadSelTemplateZip();">打包下载</button>
			<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.checkAll();">全选</button>
		</div>
		<div id="bizConfigs" class="content collapse in" aria-expanded="true"></div>
		<div id="qrcode"></div>
	</div>
</div>
<script type="text/javascript">
       	var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
       /*  PageMessageSend.onPageLoad(userId); 
		dwr.engine.setActiveReverseAjax(true);
	 	dwr.engine.setNotifyServerOnPageUnload(true); */
</script>
