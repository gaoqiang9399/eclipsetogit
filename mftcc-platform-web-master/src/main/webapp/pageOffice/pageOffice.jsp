<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${poPage.poCnt.title}</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/pageoffice/pageOffice/css/jquery.alerts.css" type="text/css" media="screen"></link>
	
	<script type="text/javascript" src="/pageoffice/pageOffice/js/pageOffice.js"></script>
	<script type="text/javascript" src="/pageoffice/pageOffice/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/pageoffice/pageOffice/js/jquery-ui-1.8.5.custom.js"></script>
	<script type="text/javascript" src="/pageoffice/pageOffice/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="/pageoffice/pageOffice/js/mes.js"></script>
    <script type="text/javascript">
    	//保存文件
		function savefile() {
			document.getElementById("PageOfficeCtrl").WebSave();
			//保存成功则保存文件记录到表中
			if(document.getElementById("PageOfficeCtrl").CustomSaveResult=="ok") {
				$("#pageOfficeDiv").html("");//清空DIV才能显示提示框
				//var parm ={'id':'B0001','parm0':''};
				//jAlert('2',parm,'系统提示',function(data){
				//-------------------------------------------
				var saveUrl=$("#saveUrl").val();
				if(saveUrl!="" && saveUrl!=null) {//新增保存
					var returnUrl=$("#returnUrl").val();
					if(returnUrl==null || returnUrl=="") {
						saveUrl+="&returnUrl=";
					}else {
						saveUrl+="&returnUrl="+encodeURIComponent(returnUrl);
					}
					window.open(saveUrl,'_self','');
				}else {//编辑保存
					poClose();
				}
				//-------------------------------------------
				//});
			}else {
				poClose();
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