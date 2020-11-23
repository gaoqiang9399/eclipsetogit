<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
            var fincNoArray ='${fincNoArray}';
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-12 column margin_top_20" style="padding:0px 50px;">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincRepayment" theme="simple" name="cms_form" action="${webPath}/mfRepayApply/insertAjax">
						<dhcc:bootstarpTag property="formfincRepayment" mode="query"/>

					</form>
					<%--<dhcc class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">--%>
						<%--<button id="mfRepayPlanTrial_button" type="button" class="btn btn-default" value="试算" style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;" onclick="MfBusFincApp_BatchRepayment.trial()">试算</button>--%>
					<%--</dhcc>--%>
				</div>
				<div id="fincList" style="margin-top: 78px">

				</div>
			</div>
		</div>

		<c:if test="${source == null or source == '' or source == 'repay'}">
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincApp_Repayment.ajaxInsert('#fincRepayment');"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
			</div>
		</c:if>
	</div>
</body>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_Repayment.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript">
	var repayAppId = "${repayAppId}";
	$(function() {
        MfBusFincApp_Repayment.init();
	});
</script>	
</html>
