<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
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
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" id="expenseInsert">申请报销</button>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=报销名称/报销金额"/>
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
 <script type="text/javascript" src="${webPath}/component/oa/expense/js/MfOaExpenseList.js"></script>
 <script type="text/javascript">
OAExpenseList.path = "${webPath}";
		$(function() {
			OAExpenseList.init(); 
		});
		/*我的筛选加载的json*/
	filter_dic = [{
		"optName" : "费用金额",
		"parm" : [],
		"optCode" : "amt",
		"dicType" : "num"
	},{
		"optName" : "登记日期",
		"parm" : [],
		"optCode" : "regTime",
		"dicType" : "date"
	},{
		"optCode" : "expenseSts",
		"optName" : "状态",
		"parm" : ${expenseStsJsonArray},
		"dicType" : "y_n"
	},];
 </script>	
</html>
