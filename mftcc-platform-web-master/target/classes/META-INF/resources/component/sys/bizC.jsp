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
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="bizC"/>
		<div class="opt_div">
	    	<div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>页面设计</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
					</li>
				</ul>
           	</div>
           	 <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>流程配置</h2>
                <ul>
                	<li><i class="i i-jiantoua"></i>
						<a href="#" onclick="openProcessDesignerNew()">流程设计器（新）</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="#" onclick="openProcessDesigner()">流程设计器</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/wkfApprovalRole/getConfPage">审批角色/用户配置</a>
					</li>
				</ul>
             </div>
       	     <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>要件/模板管理</h2>
                <ul>
                	<li><i class="icon-building"></i>
						<a href="${webPath}/docBizSceConfig/config">上传要件配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfSysTemplate/getListPage" >设计模板</a>
					</li>
					
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>评级/授信配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalScenceConfig/getSetting">评级场景配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalSysAssess/getListPage">评级信息配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appProperty/getListPage">业务属性配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/authConfig/getListPage">授信配置</a>
					</li>
				</ul>
             </div>
             
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>系统设置</h2>
                <ul>
               		<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysUser/getListPage">用户列表</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysOrg/listSysOrg">机构管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysRole/getAll" >角色权限配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/parmKey/getListPage">数据字典项配置</a>
					</li>
					<li><i class="icon-cog"></i>
						<a href="${webPath}/lease/mfCusFormConfig/getAllList">客户表单配置</a>
					</li>
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysKind/getListPage">产品设置</a>
					</li>
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysFeeStd/getListPage">费用设置</a>
					</li>
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysParmCtrl/getMainPage">参数化设置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/workCalendar/fullCalendarmonthlist?wait=0">日程填报</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/secEntrance/showConfig">安全审计配置</a>
					</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>保全配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/recallConfig/getListPage">催收配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/recallBase/getListPage">催收任务</a>
					</li> 
				  	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/recallBase/toSendList">催收任务指派</a>
					</li>
					<li><i class="i i-jiantoua"></i>
					<a href="${webPath}/riskItem/getListPage">风险预警方案配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/riskPrevent/getListPage">风险拦截预警配置</a>
					</li>
					<%--无对应前端页面
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfWarningParm/getListPage">预警天数设置</a>
					</li>--%>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskConf/getStatsPage">检查配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcModelConf/getAllListPage">检查模版配置</a>
					</li>
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskBase/getListPage">检查任务</a>
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
