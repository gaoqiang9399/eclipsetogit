<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>缴款通知书</title>
<script type="text/javascript" src='${webPath}/component/app/js/MfBusChargeFee_input.js'></script>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_feeCollect.js'></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>

	<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var appId = '${appId}';
	var pactId = '${mfBusApply.pactId}';
    var guaranteeAmt = '${guaranteeAmt}';
    var guaranteeAmtMsg = '${guaranteeAmtMsg}';
    var guaranteeAmtTaxMsg = '${guaranteeAmtTaxMsg}';
    var reviewAmt = '${reviewAmt}';
    var reviewAmtMsg = '${reviewAmtMsg}';
    var reviewAmtTaxMsg = '${reviewAmtTaxMsg}';
    var handAmtMsg = '${handAmtMsg}';
    var handAmtTaxMsg = '${handAmtTaxMsg}';
    var busModel = '${busModel}';
	var feePower = '1';//改
	var entranceNo = '${entranceNo}';
	var nodeNo = '${nodeNo}';
	var ifWrite = '${ifWrite}';

    var approvalNodeNo ;
    var querySaveFlag_pl;
	if(entranceNo == 'FEE_COLLECT_ALONE'){
        var chargeId = '${chargeId}';
        var feeChargeType = '${feeChargeType}';
	    var temBizNo = '${chargeId}';
		var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&nodeNo=' + nodeNo + '&chargeId=' + temBizNo;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
	}
	$(function() {
		//只展示无需产品号
		bindVouTypeByKindNo($("input[name=vouType]"), '');

        MfBusChargeFee_input.init();

	});
    //行业分类选择后的回调处理
    function nmdWaycCallBack(nmdWayInfo){
        var oldWayClass = $("input[name=wayClass]").val();
        $("input[name=wayClassDesc]").val(nmdWayInfo.wayName);
        $("input[name=wayClass]").val(nmdWayInfo.wayNo);
    };
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/mfBusChargeFee/chargeSubmitAjax">
						<dhcc:bootstarpTag property="formzgcjktzs0001" mode="query" />
					</form>
				</div>
				<c:choose>
					<c:when test="${entranceNo == null || entranceNo == ''}">
						<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
					</c:when>
					<c:otherwise>
						<div class="row clearfix">
							<%@ include file="/component/model/templateIncludePage.jsp"%>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="formRowCenter">
			<c:if test="${appSts == '0'}">
				<dhcc:thirdButton value="暂存" action="暂存" onclick="MfBusChargeFee_input.updateForm('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="提交" action="提交" onclick="MfBusChargeFee_input.submitForm('#operform');"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusChargeFee_input.closeFeeChare();"></dhcc:thirdButton>

		</div>
	</div>

</body>
</html>
