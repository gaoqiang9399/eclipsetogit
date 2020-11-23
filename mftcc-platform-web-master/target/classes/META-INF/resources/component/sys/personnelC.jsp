<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<%-- <script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script> --%>
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
		<script type="text/javascript">
	function openProcessDesigner(){
		window.open("workflow/WFDLDesigner.jsp");
	}
	function openProcessDesignerNew(){
		window.open("tech/wkf/modelerEditor.jsp");
	}
	$(function(){
	$("body").mCustomScrollbar()
	})
	</script>
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="sysC"/>
		<div class="opt_div">
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>OA其他功能</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCurveyInfo/getListPage">满意度调查</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCurveyConfig/getListPage">满意度调查模板</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaSatisfactionSurvey/getListPage">总裁信箱</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCoursesInfo/getListPage">课程管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaJoinCourses/getListPage">上课登记</a>
					</li>
				</ul>
             </div>
		</div>
		
		<script type="text/javascript">
		//window.parent.getHelp();
		getHelp();
		</script>
	</body>
</html>
