<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			
			function cancel(){
				myclose_click();
			};
			
			
		</script>
	</head>

<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">			
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					
					<form method="post" theme="simple" name="operform" action="">
						<dhcc:bootstarpTag property="formquerypledge0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
	   		<dhcc:thirdButton value="确定" action="保存" onclick="myclose_click();"></dhcc:thirdButton>
	   		<%-- <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancel();"></dhcc:thirdButton> --%>
	   	</div>
	</div>
</body>

</html>