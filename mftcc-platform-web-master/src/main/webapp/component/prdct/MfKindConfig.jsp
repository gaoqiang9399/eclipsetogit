<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>

<link id="filter" rel="stylesheet" href="${webPath}/themes/factor/css/filter${skinSuffix}.css?v=${cssJsVersion}" />
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link id="MfKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfKindConfig${skinSuffix}.css?v=${cssJsVersion}">

<title>产品设置</title>
</head>
<body class="bg-white">
	<div class="container">
		<!-- 新增产品 begin-->
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary" onclick="MfKindConfig.addKind();">新增产品</button>
				</div>
			</div>
		</div>
		<!-- 新增产品 end-->
		<!-- 产品配置信息 begin -->
		<div class="row clearfix config-div">
			
		</div>
		<!-- 产品配置信息 end -->
		<!-- 导航轴 -->
		<div class="row clearfix">
			<div class="work-zone-timeLine" style="position: fixed; margin-top: -78px; margin-right: 79px;">
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
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript">
	var basePath = '${webPath}';
	var ajaxData = ${ajaxData};
	//ajaxData = JSON.parse(ajaxData);
	var mfSysKindList = ajaxData.mfSysKindList;
	var feeMap = ajaxData.feeMap;
	var templateMap = ajaxData.templateMap;
	var recDataMap = ajaxData.recDataMap;
	var docMap = ajaxData.docMap;
	var repayType = ajaxData.repayType;
	var kindNodeMap = ajaxData.kindNodeMap;
	var kindNodeConfigMap = ajaxData.kindNodeConfigMap;
	var approvalWkfMap = ajaxData.approvalWkfMap;
	var repayTypeMap = ajaxData.repayTypeMap;
	var rateTypeList = ajaxData.rateTypeList;
	var icTypeList = ajaxData.icTypeList;
	var preRepayIntsTypeList = ajaxData.preRepayIntsTypeList;
	var rateDigitsList = ajaxData.rateDigitsList;
	var instCollectTypeList = ajaxData.instCollectTypeList;
	var feeCollectWayList = ajaxData.feeCollectWayList;
	//还款顺序
	var repaymentOrderTypeStr = ajaxData.repaymentOrderTypeStr;
	var countDef = 5;
	var isMousemove = false;//全局变量，用来存贮鼠标移动状态
	$(function() {
		MfKindConfig.init();
		//调用时间轴
		navLine.createNavLine();
	});
</script>
</html>
