<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>销货退货单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleReturnBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
		    var auditStsed = "${pssSaleReturnBill.auditStsed }";
			var docBizNo = '${dataMap.docBizNo }';
			var saleReturnBillNo = '${pssSaleReturnBill.saleReturnBillNo }';
			var saleReturnBillId = "${saleReturnBillId}";
			var saleOrderId = "${saleOrderId}";
			var saleBillId = "${saleBillId}";
			var saleOrderNo = "${pssSaleReturnBill.saleOrderNo }";
			var saleBillNo = "${pssSaleReturnBill.saleBillNo }";
			var customerMap = '${dataMap.customerMap }';
		    if (customerMap != '') {
		    	customerMap = JSON.parse(customerMap);
		    }
		    var disableCustomerMap = '${dataMap.disableCustomerMap }';
		    if (disableCustomerMap != '') {
		    	disableCustomerMap = JSON.parse(disableCustomerMap);
		    }
		    var disableGoodsMap = '${dataMap.disableGoodsMap }';
		    if (disableGoodsMap != '') {
		    	Pss.disableGoodsMap = JSON.parse(disableGoodsMap);
		    }
		    var disableStorehouseMap = '${dataMap.disableStorehouseMap }';
		    if (disableStorehouseMap != '') {
		    	Pss.disableStorehouseMap = JSON.parse(disableStorehouseMap);
		    }
		    
			$(function() {				
				PssSaleReturnBill_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-saveAndAddSaleReturnBill">
					<button type="button" id="btnSaveAndAddSaleReturnBill" class="btn btn-primary hide" onclick="PssSaleReturnBill_Detail.saveAndAddSaleReturnBill('#formdl_psssalereturnbill02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-addSaleReturnBill">
					<button type="button" id="btnAddSaleReturnBill" class="btn btn-primary hide" onclick="PssSaleReturnBill_Detail.addSaleReturnBill();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-copySaleReturnBill">
					<button type="button" id="btnCopySaleReturnBill" class="btn btn-default hide" onclick="PssSaleReturnBill_Detail.copySaleReturnBill();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-printSaleReturnBill">
					<button type="button" id="btnPrintSaleReturnBill" class="btn btn-default hide" onclick="PssSaleReturnBill_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-saveSaleReturnBill">
					<button type="button" id="btnSaveSaleReturnBill" class="btn btn-default hide" onclick="PssSaleReturnBill_Detail.saveSaleReturnBill('#formdl_psssalereturnbill02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-exportSN">
					<button type="button" id="btnExportSN" class="btn btn-default hide" onclick="javascript:alert('建设中，敬请关注...');">导出SN</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-auditSaleReturnBill">
					<button type="button" id="btnAuditSaleReturnBill" class="btn btn-default hide" onclick="PssSaleReturnBill_Detail.auditSaleReturnBill('#formdl_psssalereturnbill02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleReturnBill-detail-reverseAuditSaleReBill">
					<button type="button" id="btnReverseAuditSaleReturnBill" class="btn btn-default hide" onclick="PssSaleReturnBill_Detail.reverseAuditSaleReturnBill();">反审核</button>
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
										<span class="txt fl">客户:</span> 
										<input class="items-btn" id="top-cus" name="top-cus" value="${dataMap.cusNo }"></input>
										<span class="txt">销售人员:</span> 
										<input class="items-btn" id="top-saler" name="top-saler" size="8" value="${dataMap.salerNo }"></input>
										<span class="txt">单据日期:</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" size="12" readonly value="${dataMap.billDate }"></input>
										<!-- <span class="txt">附单据 0 张</span> -->
										<input type="hidden" id="top-docBizNo" value='${dataMap.docBizNo }' />
									</div>
									<div class="right">
										<span class="txt">单据编号:</span>
										<input class="items-btn" type="text" id="top-saleReturnBillNo" name="top-saleReturnBillNo" size="20" value="${dataMap.saleReturnBillNo }"/>
									</div>
									<c:if test='${dataMap.auditStsed eq "1"}'>
										<div id="auditTag" class="i i-warehouse pss-chapter-font">
											<div class="chapter-name-div">已审核</div>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="content" class="table_content pss_detail_list" style="height:auto; width:auto;">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formdl_psssalereturnbill02" theme="simple" name="operform" action="${webPath}/pssSaleReturnBill/saveSaleReturnBillAjax">
								<dhcc:bootstarpTag property="formdl_psssalereturnbill02" mode="query" />
							</form>
							<!-- 上传单据 -->
							<div class="row clearfix">
								<div class="col-xs-12 column">
									<iframe id="pssSourceDocumentIframe" frameborder=0 width="100%" src="${webPath}/component/pss/sales/PssSourceDocument.jsp?docBizNo=${dataMap.docBizNo }&auditStsed=${pssSaleReturnBill.auditStsed }"></iframe>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="返回列表" action="返回列表" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>