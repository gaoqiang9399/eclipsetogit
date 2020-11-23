<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html  style="height:100% !important;">
<head>
<title>详情</title>
<script type="text/javascript">
		var webPath = '${webPath}';
		var id = '${mfBusCompensatoryApprove.id}';
</script>
</head>
<body class="overflowHidden bg-white">   
		<div class="container form-container">
			<div id="infoDiv" style="display:block;height:100%;">
				<iframe src="${webPath}/mfBusPact/getById?pactId=${mfBusCompensatoryApprove.pactId}&compensatoryApproveId=${mfBusCompensatoryApprove.id}&busEntrance=5&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</div>
			<div id="approvalDiv" class="scroll-content" style="display:none;">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfBusCompensatoryApproveForm" theme="simple" name="operform" action="${webPath}/mfBusCompensatoryApprove/submitUpdateAjax">
							<dhcc:bootstarpTag property="formcompensatoryApprove0001" mode="query"/>
						</form>
					</div>
					<!-- 要件信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>要件上传</span>
							<iframe id="mfBusCompensatoryUploadIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusCompensatoryUpload.jsp?query=query&compensatoryId=${mfBusCompensatoryApprove.compensatoryId }"></iframe>

							</div>
						</div>
					</div>
					<div class="bigform_content col_content" style="display: none">
						<!-- <div class="title"><h5 >检查指标子项</h5>
						</div> -->
						<div id="mfBusCompensatoryApplyList" class="table_content">
							<dhcc:tableTag paginate="mfBusCompensatoryApplyDetailList" property="tablecompensatoryApprove" head="true" />
						</div>
					</div>
				</div>
	   		</div>
			<div id="approvalBtn" class="formRowCenter" style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="MfBusCompensatoryApprove.getApprovaPage();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
					<dhcc:thirdButton value="提交" action="提交" onclick="MfBusCompensatoryApprove.doSubmitAjax('#mfBusCompensatoryApproveForm');"></dhcc:thirdButton>
					<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfBusCompensatoryApprove.approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
		<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript" src="${webPath}/component/compensatory/js/MfBusCompensatoryApprove.js"></script>
<script type="text/javascript">
		$(function(){
			MfBusCompensatoryApprove.init();
		});	
</script>
</html>