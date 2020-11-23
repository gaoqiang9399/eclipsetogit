<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_InterestAccruedList.js"></script>
		<script type="text/javascript" >
			var queryMonth='${queryMonth}';
			$(function(){
				MfBusFincApp_InterestAccruedList.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<div style="margin-top: 8px;">查询月份：</div>
						<div style="margin-left: 71px;margin-top: -28px;">
							<input type="text" class="form-control" title="查询月份" style="width:8%;" name="queryMonth"  id="queryMonth" onclick="laydatemonth(this);">
							<button type="button" class="btn btn-primary"  style="margin-top: -34px;margin-left: 113px;" onclick="MfBusFincApp_InterestAccruedList.queryByMonth();">查询</button>
						</div>
					</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/合同号/借据号"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic =[{ "optCode" : "putoutAmt",
			 "optName" : "借据金额",
			 "dicType" : "num" },{ "optCode" : "fincRate",
			 "optName" : "合同利率",
			 "dicType" : "val" }];
	</script>
</html>
