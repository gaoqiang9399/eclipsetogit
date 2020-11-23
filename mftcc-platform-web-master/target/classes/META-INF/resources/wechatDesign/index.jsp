<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String contextpath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>标签解析测试页面</title>
<link rel="stylesheet" type="text/css" href="weblib/font-awesome-4.7.0/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="weblib/jQueryWeUI/lib/weui.min.css" />
<link rel="stylesheet" type="text/css" href="weblib/jQueryWeUI/css/jquery-weui.css" />
<style type="text/css">
	.weui_cells_form{
	    padding-left: 10px;
  		padding-right: 10px;
    }
    .weui_btn {
	    margin-top: 10px;
	}
</style>
</head>
<body>
<wx:wxformTag property="demo0001"/>
<script src="js/jquery-1.11.0.js" type="text/javascript"></script>
<script src="weblib/jQueryWeUI/js/jquery-weui.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>