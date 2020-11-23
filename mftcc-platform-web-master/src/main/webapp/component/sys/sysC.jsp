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
	<dhcc:markPoint markPointName="sysC"/>
		<div class="opt_div">
            
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>文档配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/docBizSceConfig/config">上传文档配置</a>
					</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>安全选项设置</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/secEntrance/showConfig">安全审计配置</a>
							</li>
						</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>流程配置</h2>
                <ul>
                			<li><i class="i i-jiantoua"></i>
								<a href="#" onclick="openProcessDesignerNew()">流程设计器</a>
							</li>
							<!-- <li><i class="i i-jiantoua"></i>
								<a href="#" onclick="openProcessDesigner()">流程设计器</a>
							</li> -->
							
							<%-- <li><i class="i i-jiantoua"></i>
								<a href="${webPath}/wkfApprovalUser/batchInput">批量添加审批用户</a>
							</li> --%>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/processDefinition/getListPage">流程定义管理</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/task/getListPage">流程任务管理</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/processInstance/getListPage">流程实例管理</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/wkfBusinessConfig/getListPage">工作流业务配置</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/wkfApprovalRole/getConfPage">审批角色/用户配置</a>
							</li>
						</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>参数配置</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/sysUser/getListPage">用户列表</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/sysOrg/listSysOrg">机构管理</a>
							</li>
						</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>系统设置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/pms/pms_entrance.jsp">入口配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/pms/pms_viewpoint.jsp">业务对象配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/pmsDataRang/findByPage">数据权限定义</a>
					</li>
					<li><i class="i i-jiantoua"></i>
					<a href="${webPath}/sysRole/getAll" >角色权限配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/parmKey/getListPage">数据字典项配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysDescTemp/getListPage">业务描述模板配置</a>
					</li>
					<%-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysMsgConfig/getListPage">消息模板配置</a>
					</li> --%>
				</ul>
             </div>
             
            
             
             <!-- 租后检查end -->
              <!-- 统计分析 begin -->
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>统计分析配置</h2>
                	<ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/chartPoint/getListPage">关注点配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/reportPoint/getListPage">报表配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/component/analysis/UserCpRelRep_List.jsp">统计关注点及报表授权</a>
						</li>
					</ul>
             </div>
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>页面设计</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/lease/desginModeler">布局设计器</a>
							</li>
							<%-- <li><i class="i i-jiantoua"></i>
								<a href="${webPath}/component/pms/pms_GetFilterSetting.jsp" target="_blank">筛选自定义生成</a>
							</li> --%>
				</ul>
             </div>
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>报表设计</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
								<a href="http://localhost:8080/REPORT_ISSUE/report/rbc/selectDataSourceAll"  >数据源管理</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="http://localhost:8080/REPORT_ISSUE/report/rbc/selectReportFileAll" >模板管理</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="http://localhost:8080/REPORT_ISSUE/report/rbc/List_Demo.jsp" >报表演示</a>
							</li>
							<!-- <li><i class="i i-jiantoua"></i>
								<a href="http://localhost:8080/REPORT_ISSUE/report/rbc/RptConfig/getTabList" >自定义配置</a>
							</li> -->
				</ul>
             </div>
		<script type="text/javascript">
		//window.parent.getHelp();
// 		getHelp();
		</script>
	</body>
</html>
