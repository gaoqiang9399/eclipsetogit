<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
	    <div class="container">
	    <br>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						
					</div>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuit_SelectInfo.js"></script>
	<script type="text/javascript">
		$(function(){
			MfLawsuit_SelectInfo.init();
		});
	</script>
</html>
