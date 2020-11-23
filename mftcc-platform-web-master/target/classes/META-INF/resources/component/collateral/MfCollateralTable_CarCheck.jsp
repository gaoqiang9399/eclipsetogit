<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript"  src='${webPath}/component/collateral/js/MfCollateralTable_CarCheck.js?v=${cssJsVersion}'> </script>
<script type="text/javascript">
	var cusTableList = ${ajaxData};
	var cusNo = '${cusNo}';
	var appId = '${appId}';
	var pledgeMethod = '${pledgeMethod}';
	var pledgeNo = '${pledgeNo}';
	var busSubmitCnt = '1';
	var dataValidate = true;
	var query = '${query}';
	var pageView = "cusView";//客户视角
</script>
</head>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix">
					<div class="col-md-10 col-md-offset-1 column margin_top_20">
						<div class="block-add" style="display: none;"></div>
					</div>
				</div>
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" typeclass ="insertAjax" onclick="MfCollateralTable_CarCheck.submitAjax();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyFormCar.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function(){
		MfCusDyFormCar.init(cusTableList);
		MfCollateralTable_CarCheck.init();
	})
</script>
</body>
</html>