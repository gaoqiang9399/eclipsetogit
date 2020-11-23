<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="cn.mftcc.util.PropertiesUtil"%>
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
	
	<script type="text/javascript" src="${webPath}/pageoffice/pageOffice/js/pageOffice.js"></script>
	<script type="text/javascript" src="${webPath}/pageoffice/pageOffice/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript">
    	var basePath  = "${basePath}";
    	//保存文件
		function savefile() {
			document.getElementById("PageOfficeCtrl").WebSave();
			//保存成功则保存文件记录到表中
			if(document.getElementById("PageOfficeCtrl").CustomSaveResult=="ok") {
				$("#pageOfficeDiv").html("");//清空DIV才能显示提示框
				var saveUrl=$("#saveUrl").val();
				if(saveUrl!=null && saveUrl!="") {//新增保存
					window.open(saveUrl,'_self','');
				}else {//编辑保存
					poFactorClose();
				}
			}else {
				poFactorClose();
			}
		}
		
		//保存PDF文件 
		function saveAsPdf() {
			document.getElementById("PageOfficeCtrl").WebSaveAsPDF();
			//保存成功则保存文件记录到表中
			if(document.getElementById("PageOfficeCtrl").CustomSaveResult=="ok") {
				$("#pageOfficeDiv").html("");//清空DIV才能显示提示框
				var saveUrl=$("#saveUrl").val();
				if(saveUrl!=null && saveUrl!="") {//新增保存
					window.open(saveUrl,'_self','');
				}else {//编辑保存
					poFactorClose();
				}
			}else {
				poFactorClose();
			}
		}
	</script>
  </head>
  <body>
  	<div id="paraDiv" style="display:none">
  		<input id="fileType" type="hidden" value="${poPage.poCnt.fileType}"/>
  		<input id="saveUrl" type="hidden" value='${poPage.poCnt.saveUrl}'/>
  		<input id="markUrl" type="hidden" value="${poPage.poCnt.markUrl}"/>
  		<input id="returnUrl" type="hidden" value='${poPage.poCnt.returnUrl}'/>
  		<input id="initMacro" type="hidden" value="${poPage.poCnt.initMacro}"/>
  		<input id="autoFullScreen" type="hidden" value="${poPage.poCnt.autoFullScreen}"/>
  		<input id="replaceExcelFunc" type="hidden" value="${poPage.poCnt.replaceExcelFunc}"/>
  	</div>
  	<div id="pageOfficeDiv">
    	<po:PageOfficeCtrl id="PageOfficeCtrl"></po:PageOfficeCtrl>
    </div>
</body>
</html>