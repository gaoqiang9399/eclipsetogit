<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript">
		var cusNo = '${cusNo}';
		var fundCompanyNameArray = '${fundCompanyNameArray}';
		var fundCompanyNameObj = JSON.parse(fundCompanyNameArray);
	</script>
	</head>
<body class="bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
			<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusCooperativeAgency/insertMfBusFundDetailAjax"  id="insertForm">
				<dhcc:bootstarpTag property="formmfbusfunddetail0001" mode="query"/>
			</form>	
		</div>
			</div>
		</div>
		<div id="formRowCenter" class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusCustomer_CooperativeView.insertMfBusFundDetailAjax('#insertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/commonview/js/MfCusCustomer_CooperativeView.js"></script>
<script type="text/javascript">
	$(function(){
		MfCusCustomer_CooperativeView.initMfBusFundDetailInput();
	});
	
</script>
</html>
