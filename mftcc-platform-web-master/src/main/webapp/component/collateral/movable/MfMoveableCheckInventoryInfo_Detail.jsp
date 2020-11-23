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
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="MfMoveableCheckInventoryInfoForm" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formcheckinvinfo0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
		</div>
	</body>
	<script type="text/javascript">]
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					updateOnContentResize:true
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
</html>