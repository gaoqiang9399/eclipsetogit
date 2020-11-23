<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/TemplateConfig.css?v=${cssJsVersion}">
<link id="MfSysKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfSysKindConfig${skinSuffix}.css?v=${cssJsVersion}">
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/model/js/useFlagRcswitcher.js"></script>

<title>产品设置</title>

</head>
<body class="overflowHidden">
<div class="container set-div">
	<!-- 导航 -->
	<div class="row clearfix bg-white nav-head">
		<div class="col-md-12 column">
			<ul class="nav nav-tabs">
				<li class="nav-li">
					 <a id="kindConfig" href="javascript:void(0);" onclick="MfSysKindList.toKindConfig();">产品设置</a>
				</li>
				<li class="nav-li active">
					 <a id="pubConfig" href="javascript:void(0);" onclick="MfKindPubConfig.toPubConfig();">公共设置</a>
				</li>
				<%@ include file="/component/prdct/MfKindConfigNav.jsp" %>
			</ul>
		</div>
	</div>
	<!-- 新增按钮 -->
	<div class="row clearfix tabCont hide bg-white margin_top_5 padding_top_15">
			
	</div>
	<!-- 配置信息 -->
	<div class="row clearfix bg-white config-div margin_top_5">
		<div class="nav-content-div col-md-9 pub-config">
		
		</div>
	</div>
	<!-- 纵向导航轴 -->
	<div class="row clearfix">
		<div class="work-zone-timeLine" style="position: fixed; margin-right: 79px;">
			<div class="time_contents">
				<div class="time-line-bg">
					<div class="time-line-line"></div>
					<div class="time-line-body">
						<dl class="time-line-dl"></dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 导航轴 end -->
</div>
</body>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKindList.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindPubConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfPubFlowConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfPubCalcConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/TemplateConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfConfigNavLine.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var ajaxData = '${ajaxData}';
		ajaxData = JSON.parse(ajaxData);
	var configTypeList = ajaxData.configTypeList;
	$(function(){
		$(".nav-content").mCustomScrollbar({
			horizontalScroll : true
		});
		$(".panel-body").mCustomScrollbar({
			horizontalScroll : true
		});
		MfKindPubConfig.init();
	});
</script>
</html>