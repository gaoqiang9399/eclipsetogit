<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
		var billId = '${billId}';
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		});															
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">						
						<form method="post" theme="simple" name="operform" action="${webPath}/mfCusBankAccManage/insertAjax">
					<dhcc:bootstarpTag property="formbanknote0004" mode="query" />
						</form>
					</div>					
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>