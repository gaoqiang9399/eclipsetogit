<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/model/js/useFlagRcswitcher.js"></script>
<link id="MfSysKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfSysKindConfig${skinSuffix}.css?v=${cssJsVersion}">
<title>产品设置</title>
</head>
<body class="overflowHidden">
<div class="container set-div">
	<!-- 导航 -->
	<div class="row clearfix bg-white nav-head">
		<div class="col-md-12 column">
			<ul class="nav nav-tabs">
				<dhcc:pmsTag pmsId="set-kind-conf-tab">
				<li class="nav-li active">
					 <a id="kindConfig" href="javascript:void(0);" onclick="MfSysKindList.toKindConfig();">产品设置</a>
				</li>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="set-pub-conf-tab">
					<li class="nav-li">
						 <a id="pubConfig" href="javascript:void(0);" onclick="MfKindPubConfig.toPubConfig();">公共设置</a>
					</li>
				</dhcc:pmsTag>
				<%@ include file="/component/prdct/MfKindConfigNav.jsp" %>
			</ul>
		</div>
	</div>
	<!-- 导航内容 -->
	<div class="row clearfix margin_top_5 nav-content">
		<div class="col-md-12 column kind-list">
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKindList.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindPubConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function(){
		MfSysKindList.init();
	});
</script>
</html>