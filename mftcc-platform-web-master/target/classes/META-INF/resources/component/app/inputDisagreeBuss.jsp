<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/inputDisagreeBuss.js?v=${cssJsVersion}"></script>
    <script type="text/javascript"
            src="${webPath}/component/pact/extension/js/MfBusExtensionCommon.js?v=${cssJsVersion}"></script>

</head>
<style type="text/css">
.mCSB_container {
	min-height: 100%;
}
</style>
<script type="text/javascript">
	var cusNo = '${cusNo}';
	var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
    var busFlag = '${busFlag}';//disagreeBuss终止业务  disagreeFinc拒绝放款  disagreeBussExtension终止本次展期申请
	var pactId  ='${pactId}';
	var fincId  ='${fincId}';
	var busEntrance  ='${busEntrance}';
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
					<form id="inputDisagree" method="post" theme="simple" name="operform" action="${webPath}/mfBusApply/insertDisagreeInfoAjax">
						<dhcc:bootstarpTag property="formdisagreeBuss" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="inputDisagreeBuss.insertDisagreeInfo('#inputDisagree');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>