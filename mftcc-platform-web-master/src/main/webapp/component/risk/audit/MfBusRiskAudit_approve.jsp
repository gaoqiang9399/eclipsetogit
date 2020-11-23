<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>稽核审核</title>
<script type="text/javascript" src='${webPath}/component/risk/audit/js/MfBusRiskAudit_approve.js'></script>
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
	var auditIdStr = '${auditId}';
    var taskId = '${taskId}';
    //文档模板需要参数
    var temBizNo = '${auditId}';
    var cusNo ='${cusNo}';
    var nodeNo ='${nodeNo}';
    var querySaveFlag = "0";
    var approvalNodeNo ="";
    var querySaveFlag_pl="";
    //要件所需参数
    var docParm = "relNo="+auditIdStr+"&scNo="+nodeNo;//查询文档信息的url的参数
    var temParm = 'cusNo=' + cusNo + '&nodeNo=' + nodeNo + '&collateralId=' + auditIdStr;// 文档书签取值依赖条件
	$(function() {
        MfBusRiskAudit_approve.init();
        showWkfFlowVertical($("#wj-modelerrece"), auditIdStr,"","risk-audit");
	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/mfBusRiskAudit/doSubmitAjax">
						<dhcc:bootstarpTag property="baseform" mode="query" />
					</form>
				</div>
				<div class="row clearfix approval-hist" id="receFincSpInfo-block">
					<div class="col-xs-12 column">
						<div class="list-table">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>风控稽核审批历史</span>
								<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receFincSpInfo-div">
									<i class='i i-close-up'></i> <i class='i i-open-down'></i>
								</button>
							</div>
							<div class="content margin_left_15 collapse in " id="receFincSpInfo-div">
								<div class="approval-process">
									<div id="modelerrecefinc" class="modeler">
										<ul id="wj-modelerrece" class="wj-modeler" isApp="false">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- 检查资料 -->
				<!-- 检查资料 -->
				<div class="row clearfix">
					<%@ include file="/component/model/templateIncludePage.jsp"%>
				</div>
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusRiskAudit_approve.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
