<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
String path = request.getContextPath();
String jsonBean = (String)request.getAttribute("jsonBean");
JSONArray wkfArray = null;
if(request.getAttribute("wkfVpList")!=null){
	wkfArray = JSONArray.fromObject(request.getAttribute("wkfVpList"));
}
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/xixi.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/viewpoint.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/wkfviewpoint.css" />
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.dev.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.month.js"></script>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/screen.css" />
		<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
		
		<style type="text/css">
 		#search-div{
	 		padding-top:0px;
	 		padding-bottom:0px;
	 		padding-left:2px;
	 		height:40px
 		}
		</style>
		<script type="text/javascript">
			vpNo="12";
			var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
			var jsonBean = eval("("+ '<%=jsonBean%>'+")");
		</script>
</head>
<body  class="overFlowHidden">
	<!-- 下拉菜单 -->
	 <div class="inner-north" style="background-color: #F8F9FC;">
		<div id="wrapper">
			<ul class="lavaLamp">
			</ul>
		</div>			
	</div> 	
	<!-- 内容 -->
	<div class="inner-center">
		<iframe src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" height="100%" width="100%" id="iframepage" name="iframepage"></iframe>
	</div>
</body>
</html>