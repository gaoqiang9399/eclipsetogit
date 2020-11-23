<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">资产保全（涉诉，查封（保全），解封，抵债的押品）</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfAssetsPreservationForm" theme="simple" name="operform" action="${webPath}/mfAssetsPreservation/assetsPreservationAjax">
							<dhcc:bootstarpTag property="formassetspreservationbase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfAssetsPreservation_AssetsPreservation.ajaxSave('#MfAssetsPreservationForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		<script type="text/javascript" src="${webPath}/component/assetspreservation/js/MfAssetsPreservation_AssetsPreservation.js"></script>
		<script type="text/javascript">
		$(function(){
			MfAssetsPreservation_AssetsPreservation.init();
		});
		</script>
	</body>
</html>
