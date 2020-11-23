<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary"
							onclick="MfFinancingOptions_List.applyInsert();">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">金融方案管理</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include
						page="/component/include/mySearch.jsp?blockType=3&placeholder=适用产品" />
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
	<script type="text/javascript"
		src="${webPath}/component/financingoptions/js/MfFinancingOptions_List.js"></script>
	<script type="text/javascript">
			filter_dic =[
				{"optCode":"financingTerm","optName":"融资期限","parm":[{"optName":"12","optCode":"0"},{"optName":"18","optCode":"1"},{"optName":"24","optCode":"2"},{"optName":"36","optCode":"3"},{"optName":"48","optCode":"4"}],"dicType":"y_n"},
				{"optCode":"financingRate","optName":"融资利率","parm":[{"optName":"0","optCode":"0"},{"optName":"10","optCode":"1"},{"optName":"20","optCode":"2"},{"optName":"30","optCode":"3"},{"optName":"40","optCode":"4"},{"optName":"50","optCode":"5"},{"optName":"60","optCode":"6"},{"optName":"70","optCode":"7"},{"optName":"80","optCode":"8"}],"dicType":"y_n"}
			];
			$(function(){
				MfFinancingOptions_List.init();
			});
		</script>
</body>
</html>
