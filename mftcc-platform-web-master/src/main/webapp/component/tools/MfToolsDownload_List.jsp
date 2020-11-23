<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}"> 
		<script type="text/javascript" src='${webPath}/component/pfs/js/MfToolsDownload_List.js?v=${cssJsVersion}'></script>		
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfToolsDownload/findByPageAjax",//列表数据查询的url
			    	tableId:"tabletoolsdownload",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body>
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<div class="form-tips" style="margin-bottom:20px;margin-top:5px;margin-left: 15px">说明：请选择你需要下载的工具。</div>
			</div>
			<div>
				<div style="vertical-align: bottom;display: block;" class="tabCont"></div>
			</div>
			<!--页面显示区域-->
			<div id="content" class="table_content"  style="height: auto;"></div>
		</div>
	</body>	
</html>
