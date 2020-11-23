<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherPayBill.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherPayBill_List.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	//初始化表头状态
	var isCheckAll = false;
	var basePath = '<%=basePath %>';
	
	$(function(){
		pssOtherPayBillList.init();
	});
	
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-other-pay-insert">
							<button type="button" class="btn btn-primary" onclick="pssOtherPayBill.otherPayBillInsertPop();">新增</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-other-pay-check">
							<span id="pssChkOPB">
								<button type="button" class="btn btn-default" onclick="pssOtherPayBillList.auditBatch();">
									审核
									<span class="triangle-down"></span>
								</button>
								<button id="pssHideChkOPB" type="button" class="pss-hide-btn" onclick="pssOtherPayBillList.reAuditBatch();">
									反审核
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-other-pay-print">
							<button type="button" class="btn btn-default" onclick="pssOtherPayBillList.batchPrintBill();">打印</button>
						</dhcc:pmsTag>
						<%-- <dhcc:pmsTag pmsId="pss-other-pay-import">
							<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出</button>
						</dhcc:pmsTag> --%>
						<dhcc:pmsTag pmsId="pss-other-pay-delete">
							<button type="button" class="btn btn-default" onclick="pssOtherPayBillList.deleteBatch();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号" />
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;"></div>
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