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
					<form method="post" id="fincBathRepayment" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/batchRepaymentAjax">
						<dhcc:bootstarpTag property="formfincBathRepayment" mode="query"/>
					</form>
				</div>
				<div id="fincList">

				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincApp_BatchRepayment.batchRepayment('#fincBathRepayment');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
		</div>
	</div>
</body>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_BathchRepayment.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript">
	$(function() {
        MfBusFincApp_BatchRepayment.init();
	});
</script>	
</html>
