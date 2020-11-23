<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<link type="text/css" rel="stylesheet" href="${webPath}/component/examine/css/ExamineApply.css" />
	<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
	<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
	<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
	<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinMain_List.js?v=${cssJsVersion}'></script>
	<script type="text/javascript" src='${webPath}/component/importexcel/js/MfCusImportExcel_upload.js?v=${cssJsVersion}'></script>
	<script type="text/javascript">
        var webPath = "${webPath}";
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="eval-content" style="">
	<div class="content_show">
		<div id="uploadSuccessLi-div" class="li_content_type container form-container">
			<div id="importSuccessList" class="table_content">
				<dhcc:tableTag property="tableimportexcelcensuslist" paginate="mfCusImportExcelCensusList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>
</body>
</html>
