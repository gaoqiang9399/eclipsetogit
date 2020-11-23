<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfReceivablesDisputedAppForm" theme="simple" name="operform" action="${webPath}/mfReceivablesDisputedApp/insertAjax">
							<dhcc:bootstarpTag property="formrecedisputed0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfReceivablesDisputedApp.ajaxDisputedAppSave('#MfReceivablesDisputedAppForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/MfReceivablesDisputedApp.js"></script>
	<script type="text/javascript">
		var cusNo='${mfBusApply.cusNo}';
		var cusName='${mfBusApply.cusName}';
		var cusNoCore='${mfBusApply.cusNoCore}';
		var cusNameCore='${mfBusApply.cusNameCore}';
		var busPleId='${busPleId}';
		$(function(){
			MfReceivablesDisputedApp.init();
		});
		$(document.body).height($(window).height());
	</script>
</html>
