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
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=配置名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" 	src="${webPath}/component/cus/trenchsubsidiary/js/MfShareProfitConfig_List.js"></script>
		<script type="text/javascript" >
			$(function(){
				MfShareProfitConfig_List.init();
			});
		</script>
	</body>
</html>
