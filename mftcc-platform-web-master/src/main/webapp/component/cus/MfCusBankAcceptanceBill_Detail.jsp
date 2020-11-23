<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
				<form  method="post" id="cusBankAccUpdate" theme="simple" name="operform" action="${webPath}/mfCusBankAcceptanceBill/updateAjax">
					<dhcc:bootstarpTag property="formcusbankbill0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusBankAccUpdate');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
	</div>
		<script type="text/javascript">
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
		});
		function updateCallBack(){
			top.addFlag = true;
			
			myclose_click();
		};
		</script>
	</body>
</html>