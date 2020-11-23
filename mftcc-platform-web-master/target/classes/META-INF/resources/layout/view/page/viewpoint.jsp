<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<script type="text/javascript" src="${webPath}/layout/view/page/js/jQuery.easing.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/viewpoint.js"></script>
		<!--帮助组件  -->
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		<script type="text/javascript">
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		</script>
</head>
 
<body>
	<!-- 下拉菜单 -->
	<div class="inner-north" style="background-color: #EAEBEC;">
		<div id="wrapper">
			<ul class="lavaLamp">
			</ul>
			<div class="app-screen">
					<button type="button" class="drop-down fa fa-angle-down" ></button>
					<button type="button">业务变更</button>
					<button type="button">资料上传</button>
					<button type="button">业务提交</button>
			</div>
		</div>
		<div id="wrapper-content" class="wrapper-content" style="display:none;width: 100%;height: auto;position: absolute; background-color: rgba(222, 222, 222, 0.9); ">
		</div>
		<div class="app-screen-list">
				<ul>
						<li><button type="button">业务变更</button></li>
						<li><button type="button">业务变更</button></li>
						<li><button type="button">业务变更</button></li>
						<li><button type="button">业务变更</button></li>
				</ul>
		</div>
	</div>
	
	<!-- 内容 -->
	<div class="inner-center">
		<iframe src="AppProjectAction_getById.action" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
	</div>
</body>
</html>