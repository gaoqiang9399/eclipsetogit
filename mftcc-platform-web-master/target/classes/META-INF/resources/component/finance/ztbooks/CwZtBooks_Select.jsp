<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			function chooseFinBooks(obj, ajaxUrl){
				$.get(webPath+ajaxUrl, function(data) {
					top.selectFlag = true;
					myclose_showDialogClick();
				});
				
				
			}
			$(function(){
				/* $(".scroll-content").mCustomScrollbar({
		 			advanced : {
		 				theme : "minimal-dark",
		 				updateOnContentResize : true
 					 }
				}); */
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
	<body class="overflowHidden  scroll-content">
		<!-- <div class="scroll-content"> -->
			<div class="container">
				<div class="row clearfix">
					<div class="col-md-12">
						<div id="content" class="table_content" style="height: auto;">
							<dhcc:tableTag paginate="cwZtBooksList" property="tableztbooks0002" head="true" />
						</div>
					</div>
				</div>
			</div>
		<!-- </div> -->
	</body>
	
</html>
