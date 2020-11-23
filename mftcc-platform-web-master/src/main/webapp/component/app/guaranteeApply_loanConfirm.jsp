<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<!-- 放款确认 -->

<html>
<head>
<title>放款确认</title>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_loanConfirm.js'></script>
<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var appId = '${appId}';
	var pactId = '${mfBusPact.pactId}';
	$(function() {
		//只展示无需产品号
		bindVouTypeByKindNo($("input[name=vouType]"), '');
		
		guaranteeApply_loanConfirm.init();
	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/guaranteeApply/loanConfirmSubmitAjax">
						<dhcc:bootstarpTag property="formData" mode="query" />
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="guaranteeApply_loanConfirm.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
