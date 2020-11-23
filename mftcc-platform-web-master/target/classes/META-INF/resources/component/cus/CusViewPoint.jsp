<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cusNo = (String)request.getAttribute("cusNo");
String cusType = (String)request.getAttribute("cusType");
//String cusId = "001";
String jsonBean = (String)request.getAttribute("jsonBean");
%>
<%-- <jsp:include page="/creditapp/talkjs.jsp"></jsp:include> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/xixi.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/viewpoint.js"></script>
		<%--bootstrap框架--%>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		
		<%--保理样式 --%>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/screen.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/menu.css" />
		<script type="text/javascript">
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		var vpNo = '<%=(String)request.getParameter("vpNo")%>';
		var jsonBean = eval("("+ '<%=jsonBean%>'+")");
		function getBigForm(){
			window.parent.openBigForm(webPath+"/mfCusCustomer/getBigForm?cusNo=<%=cusNo%>&cusType=<%=cusType%>","客户信息");
		}
		function submitUpdate(){
			window.location.href=webPath+"/appProjectPas/submitUpdate?appNo=";
		}
		function appChange(){
			window.parent.openBigForm(webPath+"/appProjectMod/getListPage?appNo=");
		}
		function submitAfterDoc(){
			document.location.href = webPath+"/appProjectPas/submitAfterDoc?appNo=";
			<%-- document.getElementById('iframepage').contentWindow.commitProcess("${webPath}/appProjectPas/submitAfterDoc?appNo="+<%=appNo%>); --%>
		}
		function backSubmit(){
			document.location.href = webPath+"/appProjectPas/backSubmit?appNo=";
		}
		</script>
</head>
 
<body>
	<!-- 下拉菜单 -->
	<div class="inner-north" style="background-color: #FFFFFF;">
		<div id="wrapper">
			<ul class="lavaLamp">
			</ul>
		</div>
		<div id="wrapper-content" class="wrapper-content" style="display:none;width: 100%;height: auto;position: absolute; background-color: rgba(222, 222, 222, 0.9); ">
		</div>
	
	</div>
	
	<!-- 内容 -->
	<div class="inner-center">
		<iframe src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
	</div>
</body>
</html>