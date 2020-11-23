<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>购货单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/purchases/js/PssBuyBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var buyBillId = "${buyBillId}";
		    var auditStsed = "${pssBuyBill.auditStsed }";
		    var buyOrderId = "${buyOrderId}";
		    var buyOrderNo = "${pssBuyBill.buyOrderNo }";
		    var buyBillNo = "${pssBuyBill.buyBillNo }";
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
				PssBuyBill_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-buyBill-detail-saveAndAddBuyBill">
					<button type="button" id="btnSaveAndAddBuyBill" class="btn btn-primary hide" onclick="PssBuyBill_Detail.saveAndAddBuyBill('#formdl_pssbuybill02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-generateBuyReturnBill">
					<button type="button" id="btnGenerateBuyReturnBill" class="btn btn-primary hide" onclick="PssBuyBill_Detail.generateBuyReturnBill('#formdl_pssbuybill02');">生成购货退货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-addBuyBill">
					<button type="button" id="btnAddBuyBill" class="btn btn-primary hide" onclick="PssBuyBill_Detail.addBuyBill();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-copyBuyBill">
					<button type="button" id="btnCopyBuyBill" class="btn btn-default hide" onclick="PssBuyBill_Detail.copyBuyBill();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-printBuyBill">
					<button type="button" id="btnPrintBuyBill" class="btn btn-default hide" onclick="PssBuyBill_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-saveBuyBill">
					<button type="button" id="btnSaveBuyBill" class="btn btn-default hide" onclick="PssBuyBill_Detail.saveBuyBill('#formdl_pssbuybill02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-exportSN">
					<button type="button" id="btnExportSN" class="btn btn-default hide" onclick="javascript:alert('建设中，敬请关注...');">导出SN</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-auditBuyBill">
					<button type="button" id="btnAuditBuyBill" class="btn btn-default hide" onclick="PssBuyBill_Detail.auditBuyBill('#formdl_pssbuybill02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyBill-detail-reverseAuditBuyBill">
					<button type="button" id="btnReverseAuditBuyBill" class="btn btn-default hide" onclick="PssBuyBill_Detail.reverseAuditBuyBill();">反审核</button>
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
										<input class="items-btn" type="text" id="top-buyBillNo" name="top-buyBillNo" size="20" value="${dataMap.buyBillNo }"/>
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
						<div id="content" class="table_content pss_detail_list">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formdl_pssbuybill02" theme="simple" name="operform" action="${webPath}/pssBuyBill/saveBuyBillAjax">
								<dhcc:bootstarpTag property="formdl_pssbuybill02" mode="query" />
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