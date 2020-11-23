<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>展期签约</title>
		<script type="text/javascript" src="${webPath}/component/pact/extension/js/MfExtensionContractSign_input.js"></script>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsert.js"></script>
		<script type="text/javascript">
			var extensionApplyId='${extensionApplyId}';
			var cusNo='${mfBusExtensionApply.cusNo}';
			var appId='${mfBusExtensionApply.appId}';
			var pactId='${mfBusExtensionApply.pactId}';
			var fincId='${mfBusExtensionApply.fincId}';
			var nodeNo ='${nodeNo}';
			var relNo = '${extensionApplyId}';// 要件业务编号
			var temBizNo = '${extensionApplyId}';
			var projectName = '${projectName}';
            var followPactNoShowSts = '${followPactNoShowSts}';
            var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=' + fincId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
            $(function(){
				MfExtensionContractSign_input.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusExtensionApplyForm"  name="operform" action="${webPath}/mfBusExtensionApply/saveContractSignAjax">
							<dhcc:bootstarpTag property="formextensionContractSign" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfExtensionContractSign_input.extensionContractSignAjax('#MfBusExtensionApplyForm','0')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="跳过" action="跳过" onclick="MfExtensionContractSign_input.extensionContractSignAjax('#MfBusExtensionApplyForm','1')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
