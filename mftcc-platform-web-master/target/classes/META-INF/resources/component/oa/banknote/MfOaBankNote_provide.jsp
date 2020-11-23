<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
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
			showWkfFlowVertical($("#wj-modeler2"),billId,"","");
		});															
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container">
		<div class="content margin_left_15 margin_top_20 margin_right_20">
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</body> 
</html>