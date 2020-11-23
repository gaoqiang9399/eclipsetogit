<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>展期业务审批</title>
		<script type="text/javascript" src="${webPath}/component/pact/extension/js/WkfExtensionViewPoint.js"></script>
		<script type="text/javascript">
			var extensionApplyId='${mfBusExtensionApply.extensionApplyId}';
			var cusNo='${mfBusExtensionApply.cusNo}';
			var appId='${mfBusExtensionApply.appId}';
			var pactId='${mfBusExtensionApply.pactId}';
			var fincId='${mfBusExtensionApply.fincId}';
			var calcIntstFlag='${calcIntstFlag}';
			$(function(){
				WkfExtensionViewPoint.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">   
		<div class="container form-container">
			<div id="infoDiv" style="display:block;height:100%;">
				<iframe src="${webPath}/mfBusPact/getPactFincById?fincId=${mfBusExtensionApply.fincId }&appId=${mfBusExtensionApply.appId }&busEntrance=6&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</div>
			<div id="approvalDiv" class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusExtensionApproveForm"  name="operform" action="${webPath}/mfBusExtensionApply/saveInvestigationReportAjax">
							<dhcc:bootstarpTag property="formextensionApprove" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</div>
	   		</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="WkfExtensionViewPoint.getApprovaPage();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
				<dhcc:thirdButton value="提交" action="提交" onclick="WkfExtensionViewPoint.doSubmitAjax('#MfBusExtensionApproveForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfExtensionViewPoint.approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId }"/>
		<input name="activityType" id="activityType" type="hidden" value="${activityType }"/>
		<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext }/>
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType }"/>
	</body>
</html>
