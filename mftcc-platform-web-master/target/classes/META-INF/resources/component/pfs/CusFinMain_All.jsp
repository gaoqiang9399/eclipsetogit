<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	var webPath = "${webPath}";
	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
	</script>
	<script type="text/javascript" src="${webPath}/themes/menu/js/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
	<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinMainAll.css" type="text/css" />
	<link rel="stylesheet" href="${webPath}/themes/view/css/filter.css" type="text/css" />
	<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" type="text/css" />
	<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinMainAll.js"></script>
	<%--bootstrap框架--%>
	<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
	 <!--加载动画js  -->
 	<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
	<style>
	</style>
	<script type="text/javascript">
		//初始化数据
		dataMap = eval("(" + '${json}' + ")");
		var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
	</script>
	<body style=" height:99.5%;">
		<input type="hidden" name="cusNo"/>
		<div class="div_role_parent">
			<div class="div_role_child">
				<div id="div_role_body">
				</div>
			</div>	
		</div>
	</body>
</html>