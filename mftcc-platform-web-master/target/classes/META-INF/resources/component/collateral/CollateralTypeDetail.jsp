<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		
		<script type="text/javascript" src='${webPath}/component/collateral/js/CollateralTypeInput.js'> </script>
		<script type="text/javascript">
		var formId = '${formId}';
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="collateralClass" theme="simple" name="operform" action="${webPath}/mfCollateralClass/collateralTypeUpdateAjax">
					<dhcc:bootstarpTag property="formdlcollateralclass0002" mode="query" />
				</form>
			</div>
		</div>
	</div> 
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#collateralClass');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div> 
	</body>
</html>