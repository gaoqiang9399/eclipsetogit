<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		$(function(){
			MfAssetsManage_List.init();
		 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfAssetsManage_List.applyInsert();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">资产管理</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/资产名称"/>
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
		<script type="text/javascript" src="${webPath}/component/assetsmanage/js/MfAssetsManage_List.js"></script>
		<script type="text/javascript">
		filter_dic =[{"optCode":"pledgeName","optName":"资产名称","dicType":"val"},
			{"optCode":"cusName","optName":"客户名称","dicType":"val"},
			{"optCode":"debtAmt","optName":"抵债金额","dicType":"num"},
			{"optCode":"assessAmt","optName":"抵债资产评估价值","dicType":"num"}];
		</script>
	</body>
</html>
