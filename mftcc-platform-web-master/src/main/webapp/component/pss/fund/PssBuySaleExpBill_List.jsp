<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表表</title>
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherPayBill_List.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssBuySaleExpBill_List.js"></script>
<script type="text/javascript">
	//初始化表头状态
	var isCheckAll = false;
	
	$(function(){
		pssBuySaleExpBillList.init();
		
	});
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="pss-buy-sale-exp-pay">
						<button type="button" class="btn btn-primary" onclick="pssBuySaleExpBillList.payExp();">支付费用</button>
					</dhcc:pmsTag>
					<!-- <button type="button" class="btn btn-primary" onclick="Pss.insertPssBuySaleExpBill();">新增</button> -->
					<!-- <button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出</button> -->
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=源单据号" />
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
		"optCode" : "sourceBillDate",
		"dicType" : "date"
	},{
		"optCode" : "paidState",
		"optName" : "付款状态",
		"parm" : ${pssPaidStsedJsonArray},
		"dicType" : "y_n"
	}
	];
</script>
</html>