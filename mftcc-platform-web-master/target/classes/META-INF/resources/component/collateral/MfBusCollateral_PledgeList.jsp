<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/collateral/js/MfBusCollateral_PledgeList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var classFirstNo='A,B,C,D';
	$(function() {
		MfBusCollateral_PledgeList.init();
	});


</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-12 text-center">
						<span class="top-title">押品管理</span>
					</div>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=押品名称/关联客户"/>
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
	filter_dic = [ 
	{
		"optCode" : "keepStatus",
		"optName" : "押品状态",
		"parm" : ${keepStatusJsonArray},
		"dicType" : "y_n"
	},{
		"optCode" : "importDate",
		"optName" : "入库日期",
		"dicType" : "date"
	},{
		"optCode" : "exportDate",
		"optName" : "出库日期",
		"dicType" : "date"
	},{
		"optCode" : "pledgeMethod",
		"optName" : "担保方式",
		"parm" : ${pledgeMethodJsonArray},
		"dicType" : "y_n" 
	},{
		"optCode" : "regDate",
		"optName" : "登记日期",
		"dicType" : "date"
	}];
</script>
</html>
