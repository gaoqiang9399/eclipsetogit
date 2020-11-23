<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/webPath.jsp" %>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript" src="${webPath}/themes/menu/js/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" type="text/css" />
	<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" type="text/css" />
	<%--滚动条js 和鼠标滚动事件js--%>
	<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
	<link rel="stylesheet" href="${webPath}/component/sys/css/sysRoleAll.css" type="text/css" />
	<script type="text/javascript" src="${webPath}/component/sys/js/sysRoleAll.js"></script>
	<script type="text/javascript">
	//初始化数据
	dataMap = eval("(" + '${json}' + ")");
	</script>
	<body style="overflow:hidden">
		<dhcc:markPoint markPointName="C"/>
		<div class="div_role_body" style="height: 25px;margin-top: 10px;">
			<div class="div_role_search">
				<input type="text" id="searchvalue">
				<i class="i i-fangdajing" onclick="searchRole();"></i>
			</div>
		</div>
		<div class="div_role_body_content" id="div_role_body_content">
			<div class="div_role_body" id="div_role_body">
			</div>
		</div>
		<div class='div_role_view' id="div_role_view">
			<div class="div_role_edit" id="div_role_edit" style="display: none;">
				<input class="close_btn" type="button" value="关闭" onclick="close_view();"/>
				<div class="edit_tile">
					<span>角色信息</span>
				</div>
				<div class="edit_input">
					<label>角色号</label>
					<input type="text" id="roleNo" name="roleNo"/>
				</div>
				<div class="edit_input">
					<label>角色名称</label>
					<input type="text" id="roleName" name="roleName"/>
				</div>
				<div class="edit_input">
					<label>ROLE_TYPE</label>
					<input type="text" id="roleType" name="roleType"/>
				</div>
				<div class="edit_input">
					<label>ROLE_LEV</label>
					<input type="text" id="roleLev" name="roleLev"/>
				</div>
			</div>
		</div>
	</body>
</html>