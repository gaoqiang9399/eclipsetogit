<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>风控稽核</title>
<script type="text/javascript" src='${webPath}/component/agencies/js/MfBusAgenciesPledgeRel_Insert.js'></script>
<script type="text/javascript">
	var appId = '${appId}';
	$(function() {
        MfBusAgenciesPledgeRel_Insert.init();

	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/mfBusAgenciesPledgeRel/insertAjax">
						<dhcc:bootstarpTag property="baseform" mode="query" />
					</form>
				</div>
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusAgenciesPledgeRel_Insert.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
