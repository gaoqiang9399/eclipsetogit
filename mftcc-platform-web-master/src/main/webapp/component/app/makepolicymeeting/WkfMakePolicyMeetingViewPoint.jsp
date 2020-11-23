<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>

<!DOCTYPE html>
<html  style="height:100% !important;">
<head>
<title>详情</title>
<script type="text/javascript">
		var makePolicyMeetingAppId = '${makePolicyMeetingAppId}';
		var from = '${from}';
        var cusNo = '${cusNo}';
        var appId = '${mfMakePolicyMeeting.appId}';
        var entrFlag = '${entrFlag}';
        var nodeNo = '${nodeNo}';
        var relNo = '${makePolicyMeetingAppId}';
        var makePolicyMeetingAppId = '${makePolicyMeetingAppId}';
        var count = '${count}';
        var mfReviewMemberList = ${mfReviewMemberList};
</script>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div id="infoDiv" style="display:block;height:100%;">
                <c:choose>
                    <c:when test="${entrFlag != null and entrFlag == 'credit'}">
                        <iframe src="${webPath}/mfCusCreditApply/getCusCreditView?cusNo=${cusNo}&busEntrance=credit&entrance=credit&creditAppId=${appId}&nodeNo=protocolPrint" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
                    </c:when>
                    <c:otherwise>
                        <iframe src="${webPath}/mfBusApply/getSummary?appId=${appId}&busEntrance=apply_approve&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
                    </c:otherwise>
                </c:choose>
			</div>
			<div id="approvalDiv" class="scroll-content" style="display:none;padding-bottom: 50px;">
				<div class="col-md-8 col-md-offset-2 column margin_top_20" >
					<div class="bootstarpTag fourColumn" id="formButton">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form id="mfMakePolicyMeetingForm">
							<dhcc:bootstarpTag property="formMakePolicyMeetingApprove" mode="query" />
						</form>
					</div>
					<%@ include file="/component/include/approval_biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="WkfMakePolicyMeetingViewPoint.getApprovaPage();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
				<dhcc:thirdButton value="提交" action="提交" onclick="WkfMakePolicyMeetingViewPoint.doSubmitAjax('#mfMakePolicyMeetingForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfMakePolicyMeetingViewPoint.approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
		<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript" src="${webPath}/component/app/makepolicymeeting/js/WkfMakePolicyMeetingViewPoint.js"></script>
<script type="text/javascript">
		$(function(){
			WkfMakePolicyMeetingViewPoint.init();
		});
</script>
<style>
	.block-left {
		padding-top: 10px;
		padding-right: 5px;
		padding-left: 2px;
	}
	.col-md-offset-1 {
		margin-left: 3%;
	}
	.bootstarpTag .table>tbody>tr>td, .bootstarpTag .table>tbody>tr>th, .bootstarpTag .table>tfoot>tr>td, .bootstarpTag .table>tfoot>tr>th, .bootstarpTag .table>thead>tr>td, .bootstarpTag .table>thead>tr>th {
		padding: 2px 2px;
		line-height: 1.42857143;
		vertical-align: middle;
		border-top: 1px solid #ddd;
	}
	.bootstarpTag.fourColumn .tdlable {
		width: 105px;
		min-width: 105px;
	}
	.mCustomScrollBox {
		background-color: #fff;
	}
	.mCSB_draggerRail {
		background: #fff;
		width: 9px;
		border-radius: 3px;
	}
	.upload-div .filelist {
		padding: 0px 0px 10px 30px;
	}
	.form-container {
		padding-bottom: 0px;
	}
	.scroll-content {
		border-top: 10px solid #F0F5FB;
		border-right: 9.5px solid #F0F5FB;
	}
</style>
</html>