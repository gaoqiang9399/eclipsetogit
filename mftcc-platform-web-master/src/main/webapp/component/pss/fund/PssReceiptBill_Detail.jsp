<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>收款单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssFund.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssReceiptBill.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssReceiptBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			/* .pss-bigform-table {
				height: 21%;
				weight: 80%;
			}
			.pss-bigform-form {
				height: 20%;
				weight: 80%;
				padding: 0px 40px 10px 20px;
			} 
			.txt {
				margin-right: 0px !important;
			}*/
		</style>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var auditSts =  '${dataMap.auditSts}';
		    var receiptId = '${pssReceiptBill.receiptId}';
		    
			$(function() {
				pssReceiptBillDetail.init();
				
			});
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-receipt-insert">
					<button type="button" class="btn btn-primary" onclick="pssReceiptBill.receiptBillInsertLink();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-receipt-print">
					<button type="button" class="btn btn-default" onclick="pssReceiptBillDetail.printBill();">打印</button>
				</dhcc:pmsTag>
				<c:if test='${dataMap.auditSts eq "0"}'>
					<dhcc:pmsTag pmsId="pss-receipt-insert">
						<button type="button" class="btn btn-default" onclick="pssReceiptBill.saveOrder('#formpssreceiptbill0002');">保存</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pss-receipt-check">
						<button type="button" class="btn btn-default" onclick="pssReceiptBill.auditOrder('#formpssreceiptbill0002');">审核</button>
					</dhcc:pmsTag>
				</c:if>
				<dhcc:pmsTag pmsId="pss-receipt-check">
					<c:if test='dataMap.auditSts=="1"'>
						<button type="button" class="btn btn-default" onclick="pssReceiptBill.reAuditOrder('#formpssreceiptbill0002');">反审核</button>
					</c:if>
				</dhcc:pmsTag>
			</div>
		</div>
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="col-md-12 column">
						<div class="search-div">
							<div class="col-xs-11 column mysearch-div" id="pills">
								<div class="mod-toolbar-top">
									<div class="left">
										<span class="txt fl"><label class="pssTabRed">*</label>客户：</span> 
										<input class="items-btn" id="cus" name="cus"/>
										<!-- <span class="txt">总欠款：</span>
										<input class="items-btn" type="text" id="top-totalDebt" name="top-totalDebt" readonly style="width:100px"></input> -->
										<span class="txt">收款人：</span> 
										<input class="items-btn" id="top-PayeeId" name="top-PayeeId"/>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-receiptNo" name="top-receiptNo" readonly/>
									</div>
									<c:if test='${dataMap.auditSts eq "1"}'>
										<div id="auditTag" class="i i-warehouse pss-chapter-font">
											<div class="chapter-name-div">已审核</div>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="content1" class="table_content pss_detail_list">
						</div>
					</div>
					<div class="btn-div column">
						<div class="show-btn" style="float:left">
							<c:if test='${dataMap.auditSts ne "1"}'>
								<button type="button" class="btn btn-primary" onclick="pssReceiptBill.selectSourceBill('#formpssreceiptbill0002');">选择源单</button>
							</c:if>
							<c:if test='${dataMap.auditSts eq "1"}'>
								<button type="button" class="btn btn-primary" onclick="javascript:alert('已审核...',1);">选择源单</button>
							</c:if>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="content2" class="table_content pss_detail_list">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formpssreceiptbill0002" theme="simple" name="operform" action="${webPath}/pssReceiptBill/updateAjax">
								<dhcc:bootstarpTag property="formpssreceiptbill0002" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="pssReceiptBill.cancelInsert();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>