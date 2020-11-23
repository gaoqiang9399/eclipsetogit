<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/pub_wx.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>主页</title>
<link rel="stylesheet"
	href="<%=contextpath%>/wechat/layout/css/wx_view.css" />
<style type="text/css">
</style>
</head>
<body ontouchstart="">
	<nav id="menu">
		<header>
			<div class="weui_panel weui_panel_access">
			  <div class="weui_panel_bd">
			    <a href="javascript:void(0);" class="weui_media_box weui_media_appmsg">
			      <div class="weui_media_hd">
			        <img class="weui_media_appmsg_thumb" src="<%=contextpath%>/wechat/images/enterLogo.png" alt="">
			      </div>
			      <div class="weui_media_bd">
			        <h4 class="weui_media_title"><%=request.getSession().getAttribute("cusName")%></h4>
			        <p id="currTime" class="weui_media_desc"></p>
			      </div>
			    </a>
			  </div>
			</div>
		</header>
		<ul class="menu-body">
		</ul>
		<footer>
			<a href="WxSysLoginAction_logout.action"  class="weui_btn weui_btn_mini weui_btn_default">退出</a>
		</footer>
	</nav>
	<main id="panel" >
		<div class="header">
			<div class="weui-row weui-no-gutter">
				<div class="weui-col-10 header-icon">
					<a id="menuBtn" href="javascript:;"> <i class="i i-gengduo"></i>
					</a>
				</div>
				<div class="weui-col-80 header-center">
					<h3 id="menu-title">标题</h3>
				</div>
				<div class="weui-col-10 header-icon">
					<a target="iframepage" href="javascript:;"> <i class="i i-chilun"></i>
					</a>
				</div>
			</div>
		</div>
		<div class="mainbody">
			<iframe id="a_iframe" src="<%=contextpath%>/WxCusRenterAction_getRenterInfo.action" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="iframepage"></iframe>
		</div>
	</main>
	<script type="text/javascript">
	var path = '<%=contextpath%>';
	var viewMenuData = eval("("+'<%=request.getSession().getAttribute("viewMenuData") %>'+")");
	</script>
	<script src="<%=contextpath%>/wechat/libs/slideout/slideout.min.js"></script>
	<script type="text/javascript" src="<%=contextpath%>/wechat/layout/js/wx_view.js"></script> 
</body>
</html>