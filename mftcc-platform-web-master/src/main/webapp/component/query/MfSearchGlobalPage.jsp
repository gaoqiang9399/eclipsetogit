<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查询入口页面</title>
<%
 String layout = "layout/view";
 %>
<script>
//全局搜索标识,后台已默认1开启
//var globalSearchOpenFlag = '${dataMap.globalSearchOpenFlag}';
var globalSearchOpenFlag = '1';

</script>
<script type="text/javascript" src="${webPath}/<%=layout%>/js/zl.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script><!--不能删，报错-->
<script type="text/javascript" src="${webPath}/<%=layout%>/js/autocompleter.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>

<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
<link id="MfQueryEntrance" rel="stylesheet" href="${webPath}/component/query/css/MfSearchGlobalPage${skinSuffix}.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/query/js/MfQueryEntrance.js?v=${cssJsVersion}"></script>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div style="height: 200px;"></div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-2 col-md-offset-5 column">
				<img  src="${webPath}/${mfSysCompanyMst.searchPageLogoImg }?v=${cssJsVersion}" class="logoimg" style="width: 300px;">
			</div>
		</div>
		<div class="row clearfix global-search">
			<div class="col-md-6 col-md-offset-3 column">
				<!-- 筛选条件 -->
				<div>
					<ul class="nav nav-tabs margin_bottom_5" id="topSearch">
						<li class="s_li" id="allSearch"><a class="btn">全部</a></li>
						<li class="s_li" id="cusSearch"><a class="btn">客户</a></li>
						<li class="s_li" id="pactSearch"><a class="btn">合同</a></li>
						<li class="s_li" id="applySearch"><a class="btn">业务</a></li>
					</ul>
					<input type="hidden" id="wayclass" value="allSearch"/>
					<input type="hidden" id="firstKindNo" value="${firstKindNo }"/>
				</div>
				<!-- 搜索框开始 -->
				<div> 
					<div class="input-group input-group-lg search-input-div">
						<input id="nope1" type="text" class="form-control search-input">
						<span id="search" class="input-group-addon pointer"><i class="i i-fangdajing"></i>搜索</span>
					</div>
				</div> 
				<!-- 搜索框结束 -->
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-2 col-md-offset-5 column">
				<div style="height: 100px"></div>
			</div>
		</div>
		<%--<div class="row clearfix">--%>
			<%--<div class="col-md-6 col-md-offset-3 column">--%>
				<%--<div style="font-size: 20px;padding-bottom:15px">常用功能</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="row clearfix">--%>
			<%--<div class="col-md-6 col-md-offset-3 column">--%>
				<%--<div class="row">--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">催收管理</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">进件管理</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">资产保全</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">业务变更</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">客户列表</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">还款管理</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">催收管理</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">进件管理</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">资产保全</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">业务变更</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">客户列表</div>--%>
					<%--</div>--%>
					<%--<div class="col-md-2 column">--%>
						<%--<div class="shortcut-button">还款管理</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
	</div>
<script type="text/javascript">
	$(function(){
		QueryEntrance.init();
	});
</script>
</body>
</html>