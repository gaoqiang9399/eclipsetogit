<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
            var fincNoArray ='${fincNoArray}';
            var repayId = '${repayId}';
            var aloneFlag = true;
            var dataDocParm={
                relNo:repayId,
                docType:'27',
                docTypeName:"还款资料",
                docSplitName:"还款凭证",
                query:''
            };
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-12 column margin_top_20" style="padding:0px 50px;">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="repaymentApply" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/batchRepaymentAjax">
						<dhcc:bootstarpTag property="formrepaymentApply" mode="query"/>
					</form>
				</div>
				<div id="fincList" style="margin-top: 78px">

				</div>
				<!-- 还款资料 -->
				<div class="col-md-12 col-md-offset-0 column" >
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
		</div>

		<c:if test="${source == null or source == '' or source == 'repay'}">
			<div class="formRowCenter">
				<dhcc:thirdButton value="打回" action="打回" onclick="MfBusFincApp_repaymentApproval.repaymentConfirm('#repaymentApply')"></dhcc:thirdButton>
				<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincApp_repaymentApproval.batchRepayment('#repaymentApply');"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
			</div>
		</c:if>
	</div>
</body>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_repaymentApproval.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript">
	var repayAppId = "${repayAppId}";
	$(function() {
		MfBusFincApp_repaymentApproval.init();
	});
</script>	
</html>
