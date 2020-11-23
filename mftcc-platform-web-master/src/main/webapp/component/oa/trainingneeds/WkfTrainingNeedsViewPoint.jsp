<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>

<!DOCTYPE html>
<html style="height:100% !important;">
<head>
<title>详情</title>
<script type="text/javascript">
		var trainingNeedsId = '${trainingNeedsId}';
		var processId = '${processId}';
		var webPath = '${webPath}';
</script>
</head>
<body class="overflowHidden bg-white">   
		<div class="container form-container">
			<div id="infoDiv" style="display:block;height:100%;" >
				<iframe src="/mfOaTrainingNeeds/getById?trainingNeedsId=${trainingNeedsId }" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</div>
			<div id="approvalDiv" class="scroll-content" style="display:none;">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfOaTrainingNeedsForm" theme="simple" name="operform" action="<%=webPath %>/mfOaTrainingNeeds/submitUpdateAjax">
							<dhcc:bootstarpTag property="formTrainingNeedsApprove" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="WkfTrainingNeedsViewPoint.getApprovaPage();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
					<dhcc:thirdButton value="提交" action="提交" onclick="WkfTrainingNeedsViewPoint.doSubmitAjax('#mfOaTrainingNeedsForm');"></dhcc:thirdButton>
					<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfTrainingNeedsViewPoint.approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId }" />
		<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript" src="${webPath}/component/oa/trainingneeds/js/WkfTrainingNeedsViewPoint.js"></script>
<script type="text/javascript">
		$(function(){
			WkfTrainingNeedsViewPoint.init();
		});	
</script>
</html>