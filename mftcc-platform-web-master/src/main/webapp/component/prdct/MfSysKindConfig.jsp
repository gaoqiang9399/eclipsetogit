<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>

<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link id="MfSysKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfSysKindConfig${skinSuffix}.css?v=${cssJsVersion}">

<title>产品设置</title>
</head>
<body class="overflowHidden">
	<div class="container set-div">
		<!-- 导航 -->
		<div class="row clearfix bg-white nav-head">
			<div class="col-md-12 column">
				<div style="height:42px;border-bottom:1px solid #ddd;">
				<ul class="nav nav-tabs nav-div">
					<li class="nav-div-li">
						 <div><span class="nav-title"></span></div>
					</li>
					<li class="back pull-right padding_right_10" onclick="MfSysKindConfig.backToKindConfigPage();">
						<button type="button" class="btn margin_top_3">返回</button>
					</li>
					<%@ include file="/component/prdct/MfKindConfigNav.jsp" %>
				</ul>
				</div>
			</div>
		</div>
		<!--横向导航 -->
		<div class="row clearfix">
			<div class="btn-config-div margin_top_20">
				<div class="padding_left_35">
					<c:forEach items="${configTypeList}"  var="parmDic" varStatus="status">
						<span class="btn btn-config" onclick="MfSysKindConfig.getKindConfigByType(this,'${parmDic.optCode }');">${parmDic.optName }</span>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- 配置信息 -->
		<div class="row clearfix config-div margin_top_5">
			<div class="nav-content-div"  style="width:730px;margin:0px auto;position: relative;">
			
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
<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKindConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindPubConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfConfigNavLine.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindBaseConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindCalcConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindInterceptConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindFormConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindDocConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindFlowConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindFeeConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindTemplateConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var kindNo = '${kindNo}';
var ajaxData = '${ajaxData}';
	ajaxData = JSON.parse(ajaxData);
var configTypeList = ajaxData.configTypeList;
$(function(){
	MfSysKindConfig.init(kindNo);
});
</script>
</html>
