<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/PledgeBaseInfoBill_Register.js"></script>
<script type="text/javascript">
    var appId = '${appId}';
    var collateralId = '${collateralId}';
    var webPath = "${webPath}";
    $(function () {
        PledgeBaseInfoBill_Register.init();
	})
	function closeWindow(){
		myclose_click();
	};
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="billForm" theme="simple" name="operform" action="${webPath}/pledgeBaseInfo/registerBillAjax">
					<dhcc:bootstarpTag property="formregisterBill" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="PledgeBaseInfoBill_Register.save('#billForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
