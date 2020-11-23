<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="app.base.*,app.component.common.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程填报</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<!--框架css-->
		<link href='${webPath}component/nmd/Calendarjs/fullcalendar.css' rel='stylesheet' />
		<script src='${webPath}component/nmd/Calendarjs/moment.min.js'></script>
		<script src='${webPath}component/nmd/Calendarjs/jquery.skygqOneDblClick.mini.1.0.js'></script>
		<script src='${webPath}component/nmd/Calendarjs/fullcalendar.js'></script>
		<!--语言js-->
		<script src='${webPath}component/nmd/Calendarjs/lang-all.js'></script>
		<!-- 滚动轴 -->
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" /> 
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
		<script src='${webPath}component/nmd/Calendarjs/workCalendar.js'></script>
		<link href='${webPath}component/nmd/Calendarjs/workCalendar.css' rel='stylesheet' />
		<style type="text/css">
			.date-zone-head .i-x2::before {
			    cursor: pointer;
			}
		</style>
</head>
	<body>
	 <dhcc:markPoint markPointName="FullCalendar_month_list"/>
		<div class="fc-diy-toolbar">
			<div class="fc-diy-left">
				<div class="fc-diy-button-group">
					
					
				</div>
			</div>
			<div class="fc-diy-right">
				<div class="fc-diy-button-group">
					<button type="button" class="i on" data-click="month">月</button>
					<button type="button" class="i"  data-click="agendaWeek">周</button>
					<button type="button" class="i"  data-click="agendaDay">日</button>
				</div>
			</div>
			<div class="fc-diy-center">
				<button type="button" class="i i-jiantou3  prev" data-click="prev"></button>
				<h2>2016年 五月</h2>
				<button type="button" class="i i-jiantou2 next" data-click="next"></button>
			</div>
		</div>
		<div id="calendar"></div>
	</body>
</html>
