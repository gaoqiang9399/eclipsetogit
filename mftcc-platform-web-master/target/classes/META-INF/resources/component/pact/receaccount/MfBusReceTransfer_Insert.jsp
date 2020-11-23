<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceTransfer_Insert.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
        var appId='${appId}';
        var cusNo='${cusNo}';
        var transMainId='${transMainId}';
        var receItems ='${receItems}';
        receItems = JSON.parse(receItems);
        $(function() {
            MfBusReceTransfer_Insert.init();
        });

        function validateDupCertiNo() {
            MfBusReceTransfer_Insert.validateDupCertiNo();
        }
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="receTransInsert" theme="simple" name="operform" action="${webPath}/mfBusReceTransfer/insertAjax">
					<dhcc:bootstarpTag property="formrecetransBase" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="MfBusReceTransfer_Insert.insertAjax('#receTransInsert');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
