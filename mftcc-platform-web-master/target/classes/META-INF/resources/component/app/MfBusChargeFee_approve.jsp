<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>缴款通知书审核</title>
<script type="text/javascript" src='${webPath}/component/app/js/MfBusChargeFee_approve.js'></script>
<script type="text/javascript" src='${webPath}/component/app/js/MfBusChargeFee_input.js'></script>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_chargeFee.js'></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript">
	var chargeIdStr = '${chargeId}';
	var chargeId = '${chargeId}';
	var cusNo = '${cusNo}';
	var appId = '${appId}';
	var busModel = '${busModel}';
    var taskId = '${taskId}';
    var feeChargeType = '${feeChargeType}';
	$(function() {
        MfBusChargeFee_approve.init();
        //判断审批历史模块的显隐
        if (chargeId!='') {
            showWkfFlowVertical($("#wj-modelerrece"), chargeId, "","charge-fee");
        } else {
            $("#receChargeFeeInfo-block").remove();
        }
	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/mfBusChargeFee/doSubmitAjax">
						<dhcc:bootstarpTag property="formzgcjktzs0002" mode="query" />
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
                <!--  缴款通知书审批历史 -->
                <div class="row clearfix approval-hist" id="receChargeFeeInfo-block">
                    <div class="col-xs-12 column">
                        <div class="list-table">
                            <div class="title">
                                <span><i class="i i-xing blockDian"></i>缴款审批历史</span>
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
		    </div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusChargeFee_approve.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
