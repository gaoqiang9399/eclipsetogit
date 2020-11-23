<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/inputDisagreeBuss.js?v=${cssJsVersion}"></script>
</head>
<style type="text/css">
.mCSB_container {
	min-height: 100%;
}
</style>
<script type="text/javascript">
	$(function() {
		inputDisagreeBuss.init();
	});
</script>
<body class="overflowHidden bg-white">
	<div class="container form-container" id="normal">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="pactPreEnd" method="post" theme="simple" name="operform" action="${webPath}/mfBusPact/insertPactPreEndInfoAjax">
						<dhcc:bootstarpTag property="formpact0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="inputDisagreeBuss.insertPactPreEnd('#pactPreEnd');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>