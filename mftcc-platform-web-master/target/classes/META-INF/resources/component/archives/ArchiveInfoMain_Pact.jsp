<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>项目详情</title>
		<script type="text/javascript">
			$(function() {
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced : {
// 						theme : "minimal-dark",
// 						updateOnContentResize : true
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
		
			/* function myclose(){
				$(top.window.document).find("#showDialog .close").click();
			} */
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="formdl_archive_pact01" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formdl_archive_pact01" mode="query" />
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
