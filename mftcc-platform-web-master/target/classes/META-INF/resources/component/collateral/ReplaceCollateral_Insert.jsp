<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
		var ajaxData = '${ajaxData}';
	    ajaxData = eval("("+ajaxData+")");
	    var busModel='${busModel}';
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content"
			});
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
	            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/repCollateralAjax">
						<dhcc:bootstarpTag property="formdlcollateralbase0004" mode="query"/>
					</form>	
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="CollateralInsert.replaceCollateral('#pledgeBaseInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js"></script>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	<script type="text/javascript">
		var collateralId_old = '${collateralId_old}';
		var entrance='${entrance}';
		var formId='${formId}';
		var appId='${appId}';
		var pledgeMethod='${pledgeMethod}';
		var entrFlag='';
		var classId='${classId}';
		var isQuote="0";
		var vouType = '${vouType}';
		var cusNo = '${cusNo}';
		var supportSkipFlag="0";
		$(function() {
			CollateralInsert.init();
		});
	</script>
</html>
