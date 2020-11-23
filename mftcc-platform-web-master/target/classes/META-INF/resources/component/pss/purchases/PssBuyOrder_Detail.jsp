<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>购货订单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/purchases/js/PssBuyOrder_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var businessType = "${pssBuyOrder.businessType }";
		    var buyOrderId = "${buyOrderId}";
		    var auditStsed = "${pssBuyOrder.auditStsed }";
		    var enabledStatus = "${pssBuyOrder.enabledStatus }";
		    var buyOrderNo = "${pssBuyOrder.buyOrderNo }";
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
				PssBuyOrder_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-saveAndAddBuyOrder">
					<button type="button" id="btnSaveAndAddBuyOrder" class="btn btn-primary hide" onclick="PssBuyOrder_Detail.saveAndAddBuyOrder('#formdl_pssbuyorder02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-generateBuyBill">
					<button type="button" id="btnGenerateBuyBill" class="btn btn-primary hide" onclick="PssBuyOrder_Detail.generateBuyBill('#formdl_pssbuyorder02');">生成购货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-generateBuyReturnBill">
					<button type="button" id="btnGenerateBuyReturnBill" class="btn btn-primary hide" onclick="PssBuyOrder_Detail.generateBuyReturnBill('#formdl_pssbuyorder02');">生成退货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-addBuyOrder">
					<button type="button" id="btnAddBuyOrder" class="btn btn-primary hide" onclick="PssBuyOrder_Detail.addBuyOrder();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-copyBuyOrder">
					<button type="button" id="btnCopyBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.copyBuyOrder();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-printBuyOrder">
					<button type="button" id="btnPrintBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-saveBuyOrder">
					<button type="button" id="btnSaveBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.saveBuyOrder('#formdl_pssbuyorder02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-auditBuyOrder">
					<button type="button" id="btnAuditBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.auditBuyOrder('#formdl_pssbuyorder02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-reverseAuditBuyOrder">
					<button type="button" id="btnReverseAuditBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.reverseAuditBuyOrder();">反审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-closeBuyOrder">
					<button type="button" id="btnCloseBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.closeBuyOrder();">关闭</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-buyOrder-detail-enableBuyOrder">
					<button type="button" id="btnEnableBuyOrder" class="btn btn-default hide" onclick="PssBuyOrder_Detail.enableBuyOrder();">开启</button>
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
										<span class="txt fl">供应商:</span> 
										<input class="items-btn" id="top-supplier" name="top-supplier" value="${dataMap.supplierId }"></input>
										<span class="txt">单据日期:</span>
										<input class="items-btn pss-date" type="text" id="top-orderDate" name="top-orderDate" size="12" readonly value="${dataMap.orderDate }"></input>
										<span class="txt">交货日期:</span>
										<input class="items-btn pss-date" type="text" id="top-deliveryDate" name="top-deliveryDate" size="12" readonly value="${dataMap.deliveryDate }"></input>
										<input type="radio" id="radioBuy" name="top-businessType" value="01" />
										<span class="txt">购货</span>
										<input type="radio" id="radioReturn" name=top-businessType value="02" />
										<span class="txt">退货</span>
									</div>
									<div class="right">
										<span class="txt">单据编号:</span>
										<input class="items-btn" type="text" id="top-buyOrderNo" name="top-buyOrderNo" size="20" value="${dataMap.buyOrderNo }"/>
									</div>
									<c:if test='${dataMap.auditStsed eq "1" && dataMap.enabledStatus eq "1"}'>
										<div id="auditTag" class="i i-warehouse pss-chapter-font">
											<div class="chapter-name-div">已审核</div>
										</div>
									</c:if>
									<c:if test='${dataMap.enabledStatus eq "0"}'>
										<div id="enabledTag" class="i i-warehouse pss-chapter-font">
											<div class="chapter-name-div">已关闭</div>
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
							<form method="post" enctype="multipart/form-data" id="formdl_pssbuyorder02" theme="simple" name="operform" action="${webPath}/pssBuyOrder/saveBuyOrderAjax">
								<dhcc:bootstarpTag property="formdl_pssbuyorder02" mode="query" />
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