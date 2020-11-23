<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
			<form  method="post" theme="simple" name="operform" action="${webPath}/mfBusAgencies/updateConfigAjax"  id="insertForm">
				<dhcc:bootstarpTag property="formmfbusagenciesconfig0001" mode="query"/>
			</form>	
		</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myUpdateConfigAjax('#insertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="closeMfAgenciesConfigInput();"></dhcc:thirdButton>
		</div>
		<div id="formCloseCenter" class="formRowCenter">
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="closeMfAgenciesConfigInput();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
<script src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
<script type="text/javascript">
	var agenciesDetailName = '${agenciesDetailName}';
	$(function(){
		MfBusAgencies.updateConfigInit();
	});
	
</script>
</html>