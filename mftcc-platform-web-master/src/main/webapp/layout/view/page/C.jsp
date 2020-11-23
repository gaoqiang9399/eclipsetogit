<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/Font-Awesome/css/font-awesome.css" />
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
	</head>
	<body>
		<div class="header_div">
			<table class="charts_table">
				<tr class="charts_table_tit">
					<th><B>|</B> 服务器内存运行情况</th>
					<th><B>|</B >连接占用率</th>
					<th><B>|</B> 用户在线率</th>
					<th><B>|</B> 流程实例数</th>
					<th><B>|</B> 系统错误数</th>
				</tr>
				<tr class="charttd">
					<td><img src="imgs/charts_1.gif"></td>
					<td><img src="imgs/charts_2.gif"></td>
					<td><img src="imgs/charts_3.gif"></td>
					<td><img src="imgs/charts_4.gif"></td>
					<td><img src="imgs/charts_5.gif"></td>
				</tr>
			</table>
		</div>
		<div class="opt_div">
			<table>
				<th><i class="icon-desktop"></i>安全选项设置</th>
				<th><i class="icon-calendar"></i>安全审计查询</th>
				<th><i class="icon-bar-chart"></i>系统日志查询</th>
				<th><i class="icon-laptop"></i>页面设计</th>
				<tr>
					<td>
						<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoProperty.action?demoId=100001">(详情页面)PropertyTag</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoProperty2.action?demoId=100001">(详情页面)PropertyTag2</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoRecord.action?demoId=100001">(记录数页面)RecordTag</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoRecord2.action?demoId=100001">(记录数页面)RecordTag2</a>
							</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoAction_input.action?demoId=100001">标准交易新增</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoBigForm.action?demoId=100001">大表单页面(bigForm)</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_standard.action?demoId=100001">standard异步</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/Demo3Action_getListPage.action">Demo模板3</a>
							</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/layout/view/page/bigform2.jsp">表单自动下拉选择</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/pactCharts.action">合同图表</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/DemoAction_getById.action">业务申请详情</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/Demo2Action_getListPage.action">Demo模板2</a>
							</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-building"></i>
								<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/desginModeler.action">布局设计器</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/ProdDefAction_getListPage.action">销售产品</a>
							</li>
							<li><i class="icon-cog"></i>密码校验规则</li>
						</ul>
					</td>
				</tr>
			</table>
			<div class="clear-line"></div>
			<table>
				<th colspan="4"><i class="icon-edit"></i>安全选项设置</th>
				<th><i class="icon-edit"></i>系统设置</th>
				<tr>
					<td>
						<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/lease/DemoTestAction_demoCzr.action">承租人</a>
							</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>密码校验规则</li>
							<li><i class="icon-building"></i>
								<a href="${webPath}/SysUserAction_getListPage.action">用户列表</a>
							</li>
							
						</ul>
					</td>
					<td>
						<ul>
							<li><i class="icon-building"></i>
								<a href="${webPath}/component/pms/pms_entrance.jsp">入口配置</a>
							</li>
							<li><i class="icon-building"></i>
								<a href="${webPath}/component/pms/pms_viewpoint.jsp">视角配置</a>
							</li>
							<li><i class="icon-building"></i>
								<a href="${webPath}/PmsDataRangAction_findByPage.action">数据权限定义</a>
							</li>
							<li><i class="icon-building"></i>
							<a href="${webPath}/SysRoleAction_getAll.action" >角色权限配置</a>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
