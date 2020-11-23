<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<script type="text/javascript" src='${webPath}/component/query/js/queryDisagree_viewPoint.js'></script>
<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var appId = '${mfBusApply.appId}';
	var termType = '${mfBusApply.termType}';
	var ajaxData = JSON.parse('${ajaxData}');
	var mfSysKind = '${mfSysKind}';
	var appTerm = '${mfBusApply.term}';
	var maxTerm = '${mfSysKind.maxTerm}';
	var minTerm = '${mfSysKind.minTerm}';
	var unit = termType == "1" ? "个月" : "天";
	var minTime = minTerm + unit;
	var maxTime = maxTerm + unit;
	var cmpdRateType = '${mfBusApply.cmpFltRateShow}';

	$(function() {
		queryDisagree_viewPoint.init();
	});
</script>
</head>

<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display: block; height: 100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display: none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="form" mode="query" />
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display: block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="queryDisagree_viewPoint.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display: none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="queryDisagree_viewPoint.doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="queryDisagree_viewPoint.approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
</body>
<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>
