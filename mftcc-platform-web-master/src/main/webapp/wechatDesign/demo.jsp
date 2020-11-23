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
<link type="image/x-icon" rel="shortcut icon" href="wechatDesign/img/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>标签解析测试页面</title>
<link rel="stylesheet" type="text/css" href="wechatDesign/weblib/font-awesome-4.7.0/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="wechatDesign/weblib/jQueryWeUI/lib/weui.min.css" />
<link rel="stylesheet" type="text/css" href="wechatDesign/weblib/jQueryWeUI/css/jquery-weui.css" />
<link rel="stylesheet" type="text/css" href="wechatDesign/css/weui.extend.css" />
<script src="wechatDesign/js/jquery-1.11.0.js" type="text/javascript"></script>
<script src="wechatDesign/weblib/jQueryWeUI/js/jquery-weui.js" type="text/javascript" charset="utf-8"></script>
<script src="wechatDesign/js/wxForm.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
	h3{
		text-align: center;
		padding: 20px 10px 0px 10px;
	}
	.weui_cells_form{
	    padding-left: 10px;
  		padding-right: 10px;
  		padding-bottom: 30px;
    }
    .weui_btn {
	    margin-top: 10px;
	}
	.weui_label{
		font-weight: 700;
	}
</style>
</head>
<body>
<!-- <button type="button" onclick="getFormValue('demo0001','SendValueForm','callback')">取值</button>
 -->
 <h3>表单解析demo0001</h3>
<wx:wxformTag2 property="test0001"/>
</body>
</html>