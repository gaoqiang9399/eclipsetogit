<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE 
html>
<html>
<head>
<link type="image/x-icon" rel="shortcut icon"
	href="wechatDesign/img/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>标签解析测试页面</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/wechatDesign/weblib/font-awesome-4.7.0/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/wechatDesign/weblib/jQueryWeUI/lib/weui.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/wechatDesign/weblib/jQueryWeUI/css/jquery-weui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/wechatDesign/css/weui.extend.css" />
<script src="<%=basePath%>/wechatDesign/js/jquery-1.11.0.js"
	type="text/javascript"></script>
<script
	src="<%=basePath%>/wechatDesign/weblib/jQueryWeUI/js/jquery-weui.js"
	type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>/wechatDesign/js/wxForm.js"
	type="text/javascript" charset="utf-8"></script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
	</style>
</head>

<body>
	<div class="weui_tab">
		<div class="weui_tab_bd">
			<div id="tab1" class="weui_tab_bd_item weui_tab_bd_item_active">
				<iframe width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0"  src="<%=basePath %>/wechatDesign/demo/timeline.jsp"></iframe>
			</div>
			<div id="tab2" class="weui_tab_bd_item">
				<iframe width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0"  src="<%=basePath %>/wechatDesign/demo/desc.jsp"></iframe>
			</div>
			<div id="tab3" class="weui_tab_bd_item">
				<iframe width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0"  src="<%=basePath %>/wechatDesign/demo/datashow.jsp"></iframe>
			</div>
			<div id="tab4" class="weui_tab_bd_item">
				<h1 class="doc-head">其他</h1>
			</div>
		</div>
		<div class="weui_tabbar">
			<a href="#tab1" class="weui_tabbar_item weui_bar_item_on">
				<div class="weui_tabbar_icon">
					<i class="fa fa-calendar"></i>
				</div>
				<p class="weui_tabbar_label">时间轴</p>
			</a> <a href="#tab2" class="weui_tabbar_item">
				<div class="weui_tabbar_icon">
					<i class="fa fa-file-text-o"></i>
				</div>
				<p class="weui_tabbar_label">申请条件</p>
			</a> <a href="#tab3" class="weui_tabbar_item">
				<div class="weui_tabbar_icon">
					<i class="fa fa-credit-card"></i>
				</div>
				<p class="weui_tabbar_label">分期付款</p>
			</a> <a href="#tab4" class="weui_tabbar_item">
				<div class="weui_tabbar_icon">
					<i class="fa fa-bars"></i>
				</div>
				<p class="weui_tabbar_label">其他</p>
			</a>
		</div>
	</div>
</body>
</html>
