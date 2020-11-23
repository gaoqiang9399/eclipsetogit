<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var  caseId = '${caseId}';
			$(function(){
				MfAssetsPreservation_CaseList.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=押品名称"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript" src="${webPath}/component/assetspreservation/js/MfAssetsPreservation_CaseList.js"></script>
		<script type="text/javascript">
			var caseId = "${caseId}";
			filter_dic =[
				{"optCode":"involvementSts",
					"optName":"涉案状态",
					"parm":[{"optName":"涉诉","optCode":"1"},
						{"optName":"查封","optCode":"2"},
						{"optName":"解封","optCode":"3"},
						{"optName":"抵债","optCode":"4"}],
					"dicType":"y_n"}];
		</script>
	</body>
</html>
