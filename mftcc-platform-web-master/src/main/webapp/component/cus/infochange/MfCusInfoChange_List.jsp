<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript"
		src="${webPath}/component/cus/infochange/js/MfCusInfoChange_List.js"></script>
		<script type="text/javascript" >
			var webPath = "${webPath}";
			var cusNo = "${cusNo}";
			$(function(){
				MfCusInfoChange_List.init();
			 });
		</script>
	</body>
</html>
