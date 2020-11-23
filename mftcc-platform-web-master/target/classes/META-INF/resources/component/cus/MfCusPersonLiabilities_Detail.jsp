<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusPersonLiabilities.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
	<script type="text/javascript">
	$(function() {
		MfCusPersonLiabilities.init();

	});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusPersonLiabilitiesForm" theme="simple" name="operform" action="${webPath}/mfCusPersonLiabilities/updateAjax">
							<dhcc:bootstarpTag property="formcusliabilitiesDetail" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusPersonLiabilities.insertCusPersonLiabilitiesBase('#MfCusPersonLiabilitiesForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>