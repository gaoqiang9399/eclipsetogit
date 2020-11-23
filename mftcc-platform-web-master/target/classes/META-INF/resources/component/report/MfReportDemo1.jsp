<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%String contextPath = request.getContextPath(); 
String parm = request.getParameter("parm");
 %>
 <style type="text/css">
 .reportTu{
 	background: url("images/yibiaopan1.png");
	width: 500px;
	height: 317px;
	margin-left: 25%;
 }
 </style>
 </head>
 <script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
 <script>
 var parm = '<%=parm%>';
$(function () {
    if(parm == "2"){
    	$(".reportTu").css("background","url('images/yibiaopan2.png')");
    }
});
</script>
<body>
<div class="container" style="margin-top: 50px;">
	<div class="reportTu" id="tu1"><!--五级分类分布图 -->
	</div>
</div>
</body>
</html>