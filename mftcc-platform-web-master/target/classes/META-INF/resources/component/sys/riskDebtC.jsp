<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<%@ include file="/component/include/webPath.jsp" %>
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
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
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
	<body  style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="sysBizC"/>
		<div class="opt_div">
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>催收配置</h2>
                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallConfig/getListPage">催收配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/getListPage">催收任务</a>
						</li>
						<!-- <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/getListPageForSend">催收任务指派</a>
						</li> -->
					  	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/toSendList">催收任务指派</a>
						</li>
					</ul>
             </div>
             <!-- 租后检查 begin -->
             <!--  <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>租后跟踪</h2>
                <ul>
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskBase/getListPage">租后检查任务</a>
					</li>
				</ul>
             </div> -->
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>风险管理</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/riskItem/getListPage">风险预警方案配置</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/riskPrevent/getListPage">风险拦截预警配置</a>
							</li>
						</ul>
             </div>
            
             <c:if test="ifRegion==0">
             	<!-- 租后管理 begin -->
	              <div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>五级分类配置</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/claTaskBase/getListPageForRiskManagement">五级分类任务</a>
						</li> 
					</ul>
	             </div>
             </c:if>
            
             
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>利率调整</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/alpRateAdj/getListPage">利率调整</a>
					</li>
				</ul>
             </div>
		<script type="text/javascript">
		//window.parent.getHelp();
		getHelp();
		</script>
	</body>
</html>
