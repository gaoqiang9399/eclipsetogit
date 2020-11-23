<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>购货退货单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/purchases/js/PssBuyReturnBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var buyReturnBillNo = "${pssBuyReturnBill.buyReturnBillNo }";
		    var buyReturnBillId = "${buyReturnBillId}";
		    var auditStsed = "${pssBuyReturnBill.auditStsed }";
		    var buyOrderId = "${buyOrderId}";
			var buyBillId = "${buyBillId}";
			var buyOrderNo = "${pssBuyReturnBill.buyOrderNo }";
			var buyBillNo = "${pssBuyReturnBill.buyBillNo }";
			var supplierMap = '${dataMap.supplierMap }';
		    if (supplierMap != '') {
		    	supplierMap = JSON.parse(supplierMap);
		    }
		    var disableSupplierMap = '${dataMap.disableSupplierMap }';
		    if (disableSupplierMap != '') {
		    	disableSupplierMap = JSON.parse(disableSupplierMap);
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
				PssBuyReturnBill_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-saveAndAddBuyReturnBill">
					<button type="button" id="btnSaveAndAddBuyReturnBill" class="btn btn-primary hide" onclick="PssBuyReturnBill_Detail.saveAndAddBuyReturnBill('#formdl_pssbuyreturnbill02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-addBuyReturnBill">
					<button type="button" id="btnAddBuyReturnBill" class="btn btn-primary hide" onclick="PssBuyReturnBill_Detail.addBuyReturnBill();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-copyBuyReturnBill">
					<button type="button" id="btnCopyBuyReturnBill" class="btn btn-default hide" onclick="PssBuyReturnBill_Detail.copyBuyReturnBill();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-printBuyReturnBill">
					<button type="button" id="btnPrintBuyReturnBill" class="btn btn-default hide" onclick="PssBuyReturnBill_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-saveBuyReturnBill">
					<button type="button" id="btnSaveBuyReturnBill" class="btn btn-default hide" onclick="PssBuyReturnBill_Detail.saveBuyReturnBill('#formdl_pssbuyreturnbill02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-exportSN">
					<button type="button" id="btnExportSN" class="btn btn-default hide" onclick="javascript:alert('建设中，敬请关注...');">导出SN</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-auditBuyReturnBill">
					<button type="button" id="btnAuditBuyReturnBill" class="btn btn-default hide" onclick="PssBuyReturnBill_Detail.auditBuyReturnBill('#formdl_pssbuyreturnbill02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyReturnBill-detail-reverseAuditBuyReturnBill">
					<button type="button" id="btnReverseAuditBuyReturnBill" class="btn btn-default hide" onclick="PssBuyReturnBill_Detail.reverseAuditBuyReturnBill();">反审核</button>
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
										<span class="txt fl">供应商：</span> 
										<input class="items-btn" id="top-supplier" name="top-supplier" value="${dataMap.supplierId }"></input>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" size="12" readonly value="${dataMap.billDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-buyReturnBillNo" name="top-buyReturnBillNo" size="20" value="${dataMap.buyReturnBillNo }"/>
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
							<form method="post" enctype="multipart/form-data" id="formdl_pssbuyreturnbill02" theme="simple" name="operform" action="${webPath}/pssBuyReturnBill/saveBuyReturnBillAjax">
								<dhcc:bootstarpTag property="formdl_pssbuyreturnbill02" mode="query" />
							</form>
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