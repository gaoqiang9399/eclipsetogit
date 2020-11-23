<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>尽调报告</title>
		<script type="text/javascript" src="${webPath}/component/pact/extension/js/MfBusExtensionInvestigation_Input.js"></script>
		<script type="text/javascript">
			var extensionApplyId='${extensionApplyId}';
			var cusNo='${mfBusExtensionApply.cusNo}';
			var appId='${mfBusExtensionApply.appId}';
			var pactId='${mfBusExtensionApply.pactId}';
			var fincId='${mfBusExtensionApply.fincId}';
            var nodeNo='${nodeNo}';
			$(function(){
				MfBusExtensionInvestigation_Input.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusExtensionApplyForm"  name="operform" action="${webPath}/mfBusExtensionApply/saveInvestigationReportAjax">
							<dhcc:bootstarpTag property="formextensionapply0002" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusExtensionInvestigation_Input.saveInvestigationReportAjax('#MfBusExtensionApplyForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
