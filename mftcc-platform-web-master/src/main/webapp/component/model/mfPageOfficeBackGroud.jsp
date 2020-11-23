<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.zhuozhengsoft.pageoffice.FileMakerCtrl" %>
<%@ page import="app.component.model.entity.PageOfficePage" %>
<%
    PageOfficePage PageOfficePage = (app.component.model.entity.PageOfficePage) request.getAttribute("poPage");
    FileMakerCtrl fmCtrl = PageOfficePage.getFmCtrl();
    fmCtrl.setTagId("FileMakerCtrl"); //此行必须 */
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
	
	<script type="text/javascript" src="${webPath}/pageoffice/pageOffice/js/pageOffice.js"></script>
	<script type="text/javascript" src="${webPath}/pageoffice/pageOffice/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript">
        function OnProgressComplete(){
            alert('${poPage.poCnt.saveUrl}');
        }
	</script>
  </head>
  <body>
  	<div id="pageOfficeDiv">
		<!--**************   卓正 PageOffice 客户端代码结束    ************************-->
		<po:FileMakerCtrl id="FileMakerCtrl"/>
    </div>
</body>
</html>