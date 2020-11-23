<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.mftcc.util.PropertiesUtil" %>
<%
	String fileSize=PropertiesUtil.getUploadProperty("fileSize");
%>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/webuploader/webuploader.css" />
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<%-- <link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" /> --%>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/js/uploadForMainPage.js"></script>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css" type="text/css">
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var fileSize=<%=fileSize%>;
    if(fileSize==null){
        fileSize=100*1024*1024;//如果配置文件中没有配置upload.fileSize属性，默认大小是20M
    }
</script>
<div class="upload_body">
	<ul class="ztree" id="uploadTree"></ul>
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
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
	
