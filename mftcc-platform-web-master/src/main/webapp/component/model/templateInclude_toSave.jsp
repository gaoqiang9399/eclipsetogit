<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<html>
<head>
<title>文档保存后记录文档保存信息</title>
<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		var templateBizConfigId = '${param.templateBizConfigId}';
		var docFilePath = '${param.docFilePath}';
		var docFileName = '${param.docFileName}';
		var opName = '${param.opName}';
		var orgNo = '${param.orgNo}';
		var orgName = '${param.orgName}';
		//验证串
		var userId = '${param.userId}';
		var pageOffice = '${param.pageOffice}';
		var waterId = '${param.waterId}';
		
		var param = {
				"templateBizConfigId" : templateBizConfigId,
				"docFilePath" : docFilePath,
				"docFileName" : docFileName,
				"waterId" : waterId,
				"userId" : userId,
				"pageOffice" : pageOffice,
				"opName" : opName,
				"orgNo" : orgNo,
				"orgName" : orgName
			};
		$.ajax({
			url:webPath+"/mfTemplateBizConfig/updateTemplateDataAjax",
			type : "post",
			data : param,
			async : false,
			dataType : "json",
			error : function(a,b,c) {
				alert('error' + a + b +c);
			},
			success : function(data) {
				window.external.close();//关闭pageOfficeLink窗口
			}
		});
	});
</script>
</head>
<body class="body_bg">
</body>
</html>