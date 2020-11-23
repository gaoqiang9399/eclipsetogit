<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String contextpath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath+"/";
%>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<script type="text/javascript" src="<%=contextpath%>/wechat/libs/jQueryWeUI/lib/jquery-2.1.4.js"></script>
<!-- FastClick消除点击延时提高程序的运行效率 -->
<script type="text/javascript" src="<%=contextpath%>/wechat/libs/jQueryWeUI/lib/fastclick.js"></script>
<!-- jQuery WeUI 组建 -->
<script type="text/javascript" src="<%=contextpath%>/wechat/libs/jQueryWeUI/js/jquery-weui.js"></script>
<!-- 移动触摸滑动插件 -->
<script type="text/javascript" src="<%=contextpath%>/wechat/libs/jQueryWeUI/js/swiper.min.js"></script>
<!-- 控制台打印工具，调试的时候使用 -->
<%-- <script type="text/javascript" src="<%=contextpath%>/wechat/libs/vConsole-2.5.1/vconsole.min.js"></script>
 --%>
<!-- 解析列表 -->
<script type="text/javascript" src="<%=contextpath%>/wechat/include/wx_Parselist.js"></script>
<!-- 字体图库 -->
<link rel="stylesheet" href="<%=contextpath%>/layout/view/themes/iconfont/css/iconfont.css" />
<!-- jQuery WeUI 组建 -->
<link rel="stylesheet" href="<%=contextpath%>/wechat/libs/jQueryWeUI/lib/weui.css"/>
<link rel="stylesheet" href="<%=contextpath%>/wechat/libs/jQueryWeUI/css/jquery-weui.css"/>
<script type="text/javascript">
	$(function() {  
	    FastClick.attach(document.body); //实例化fastclick
	}); 
</script>