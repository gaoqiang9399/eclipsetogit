<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String contextpath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath+"/";
	String formId = (String)request.getParameter("formId");
%>
<!DOCTYPE html>
<html>
<head>
<link type="image/x-icon" rel="shortcut icon" href="img/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>标签解析预览页面</title>
<link rel="stylesheet" type="text/css" href="weblib/font-awesome-4.7.0/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="weblib/customScrollbar/css/jquery.mCustomScrollbar.css"/>
<link rel="stylesheet" type="text/css" href="weblib/jQueryWeUI/lib/weui.min.css" />
<link rel="stylesheet" type="text/css" href="weblib/jQueryWeUI/css/jquery-weui.css" />
<script src="js/jquery-1.11.0.js" type="text/javascript"></script>
<script type="text/javascript" src="weblib/customScrollbar/js/jquery.mousewheel-3.0.6.js" ></script>
<script src="weblib/customScrollbar/js/jquery.mCustomScrollbar.js" type="text/javascript" charset="utf-8"></script>
<script src="weblib/vConsole-2.5.1/vconsole.min.js" type="text/javascript" charset="utf-8"></script>
<script src="weblib/jQueryWeUI/js/jquery-weui.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
	body{
	    padding-left: 10px;
  		padding-right: 10px;
  		padding-bottom: 30px;
  		height: 519px;
    }
    .weui_btn {
	    margin-top: 10px;
	}
	.mCSB_scrollTools{
		width: 4px;
	}
</style>
</head>
<body>
<wx:wxformTag2 property="<%=formId %>"/>
</body>
<script type="text/javascript">
$(window).load(function(){
	//$("body").height(window.innerHeight);
    $("body").mCustomScrollbar({theme:"minimal-dark"});
});
</script>
</html>