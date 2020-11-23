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
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
	            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				
					<form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/insertCollateralCreditAjax">
						<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						<input type="hidden" name = "isQuote"/>
						<input type="hidden" name = "entrFlag"/>
					</form>	
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="CollateralInsert.insertCollateralBase('#pledgeBaseInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js"></script>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	<script type="text/javascript">
	var appId = '${appId}';
	var entrance = "credit";
	var entrFlag="credit";
	var formId='${formId}';
	var classId='${classId}';
		$(function() {
			CollateralInsert.init();
			top.creditAppId =appId;
		});
	</script>
</html>
