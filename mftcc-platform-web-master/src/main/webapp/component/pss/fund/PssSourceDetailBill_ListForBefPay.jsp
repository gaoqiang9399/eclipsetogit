<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表表</title>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssSourceDetailBill_List.js"></script>
<script type="text/javascript">
	var supplierId = '${dataMap.supplierId}';
	//初始化表头状态
	var isCheckAll = false;
	
	$(function(){
		pssSourceDetailBillList.initBefPay();
	});
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display: none;">
					<input name="kindName" type="hidden"></input> 
					<input name="kindNo" type="hidden"></input>
				</div>
				<div class="btn-div">
					<button type="button" class="btn btn-primary" onclick="pssSourceDetailBillList.billConfirm();">确定</button>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=源单编号" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "单据日期",
		"parm" : [],
		"optCode" : "billDate",
		"dicType" : "date"
	} ];
</script>
</html>