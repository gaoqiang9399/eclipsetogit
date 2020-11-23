<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信模型配置详情</title>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditModel_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditComon.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var wkfCreditId = "${mfCusCreditModel.wkfCreditId}";
			var creditFormId = "${mfCusCreditModel.creditFormId}";
			var reportTemplateId = "${mfCusCreditModel.reportTemplateId}";
			var protocolTemplateId = "${mfCusCreditModel.protocolTemplateId}";
			//初始化
			$(function(){
				mfCusCreditComon.init();
				mfCusCreditModelDetail.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
					<div class="form-title">授信申请模型设置</div>
					
				 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					
						<form  method="post" theme="simple" name="operform" id="operform" action= "${webPath}/mfCusCreditModel/updateAjax">
							<dhcc:bootstarpTag property="formcreditmodel0002" mode="query"/>
						</form>
					
						</div>
					</div>
				</div>
			
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="mfCusCreditModelDetail.ajaxUpdate('#operform')"></dhcc:thirdButton>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditModelDetail.back();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>