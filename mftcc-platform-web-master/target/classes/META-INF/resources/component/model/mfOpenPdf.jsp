<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.PDFCtrl"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${poPage.poCnt.title}</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${webPath}/pageoffice/pageOffice/css/jquery.alerts.css" type="text/css" media="screen"></link>
	
	<script type="text/javascript" src="${webPath}/component/model/js/MfOpenPdf.js"></script>
  </head>
  <body>
  	<div style="width: auto; height: 700px;">
		<po:PDFCtrl id="PDFCtrl">
		</po:PDFCtrl>
	</div>
</body>
</html>