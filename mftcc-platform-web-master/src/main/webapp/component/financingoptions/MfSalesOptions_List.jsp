<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary"
							onclick="MfSalesOptions_List.applyInsert();">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">销售方案管理</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include
						page="/component/include/mySearch.jsp?blockType=3&placeholder=车型/车系/品牌" />
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
		src="${webPath}/component/financingoptions/js/MfSalesOptions_List.js"></script>
	<script type="text/javascript">
			filter_dic =[
				{"optCode":"brandName","optName":"品牌","dicType":"val"},
				{"optCode":"seriesName","optName":"车系","dicType":"val"},
				{"optCode":"modelName","optName":"车型","dicType":"val"},
			];
			$(function(){
				MfSalesOptions_List.init();
			});
		</script>
</body>
</html>
