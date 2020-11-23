<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<style type="text/css">
	.change-td-color-3{
		color:blue !important;
	}
	.change-td-color-2{
		color:orange !important;
	}
	.change-td-color-1{
		color:red !important;
	}
</style>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					<div class="col-md-12 text-center">
						<span class="top-title">风险登记</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include
						page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称" />
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
		src="${webPath}/component/risk/risklevelmanage/js/MfRiskRegistration_List.js"></script>
	<script type="text/javascript">
		filter_dic = [ {
			"optCode" : "riskLevel",
			"optName" : "风险等级",
			"parm" : ${risllevelJsonArray},
			"dicType" : "y_n"
		}, {
			"optCode" : "pactNo",
			"optName" : "合同号",
			"dicType" : "val"
		}, {
			"optCode" : "fincShowId",
			"optName" : "借据号",
			"dicType" : "val"
		} ];
		var webPath = "${webPath}";
		$(function() {
			MfRiskRegistration_List.init();
		});
	</script>
</body>
</html>
