<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<script type="text/javascript">
        var cusNo='${mfBusPact.cusNo}';
        var appId='${mfBusPact.appId}';
        var pactId='${mfBusPact.pactId}';
        var pactNo='${mfBusPact.pactNo}';
        var ajaxData = '${ajaxData}';
        ajaxData = JSON.parse(ajaxData);
        var nodeNo='${nodeNo}';
        var isCusDoc = "cusDoc";
        var docParm = 'relNo=' + appId + '&scNo=finc_amt_confirm';
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20" >
			<div class="bootstarpTag fourColumn">
				<div class="form-title">融资申请</div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="receFincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincAmtConfirm/insertAjax">
					<dhcc:bootstarpTag property="formfincamtconfirmbase" mode="query"/>
				</form>
			</div>
			<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincAmtConfirm_Insert.ajaxInsert('#receFincAppInsertForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusFincAmtConfirm_Insert.cancelBack();"></dhcc:thirdButton>
	</div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusFincAmtConfirm_Insert.js"></script>
<script type="text/javascript">

    $(function() {
        MfBusFincAmtConfirm_Insert.init();
    });
</script>
</html>