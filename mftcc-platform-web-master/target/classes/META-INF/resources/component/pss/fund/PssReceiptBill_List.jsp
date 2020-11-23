<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssReceiptBill.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssReceiptBill_List.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	//初始化表头状态
	var isCheckAll = false;
	var basePath = '<%=basePath %>';
	
	$(function(){
		pssReceiptBillList.init();
		
	});
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="pss-receipt-insert">
						<button type="button" class="btn btn-primary" onclick="pssReceiptBill.receiptBillInsertPop();">新增</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pss-receipt-check">
						<span id="pssChkRB">
							<button type="button" class="btn btn-default" onclick="pssReceiptBillList.auditBatch();">
								审核
								<span class="triangle-down"></span>
							</button>
							<button id="pssHideChkRB" type="button" class="pss-hide-btn" onclick="pssReceiptBillList.reAuditBatch();">
								反审核
							</button>
						</span>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pss-receipt-print">
						<button type="button" class="btn btn-default" onclick="pssReceiptBillList.batchPrintBill();">打印</button>
					</dhcc:pmsTag>
					<%-- <dhcc:pmsTag pmsId="pss-receipt-import">
						<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出</button>
					</dhcc:pmsTag> --%>
					<dhcc:pmsTag pmsId="pss-receipt-delete">
						<button type="button" class="btn btn-default" onclick="pssReceiptBillList.deleteBatch();">删除</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号" />
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
	},{
		"optCode" : "auditSts",
		"optName" : "审核状态",
		"parm" : ${pssAuditStsedJsonArray},
		"dicType" : "y_n"
	}
	];
</script>
</html>