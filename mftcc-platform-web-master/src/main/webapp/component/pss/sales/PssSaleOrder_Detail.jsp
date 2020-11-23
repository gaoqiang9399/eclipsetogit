<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>销货订单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleOrder_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
		    var businessType = "${pssSaleOrder.businessType }";
		    var saleOrderId = "${saleOrderId}";
		    var auditStsed = "${pssSaleOrder.auditStsed }";
		    var enabledStatus = "${pssSaleOrder.enabledStatus }";
		    var saleOrderNo = "${pssSaleOrder.saleOrderNo }";
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
				PssSaleOrder_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-saveAndAddSaleOrder">
					<button type="button" id="btnSaveAndAddSaleOrder" class="btn btn-primary hide" onclick="PssSaleOrder_Detail.saveAndAddSaleOrder('#formdl_psssaleorder02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-generateSaleBill">
					<button type="button" id="btnGenerateSaleBill" class="btn btn-primary hide" onclick="PssSaleOrder_Detail.generateSaleBill('#formdl_psssaleorder02');">生成销货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-generateSaleReturnBill">
					<button type="button" id="btnGenerateSaleReturnBill" class="btn btn-primary hide" onclick="PssSaleOrder_Detail.generateSaleReturnBill('#formdl_psssaleorder02');">生成退货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-addSaleOrder">
					<button type="button" id="btnAddSaleOrder" class="btn btn-primary hide" onclick="PssSaleOrder_Detail.addSaleOrder();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-copySaleOrder">
					<button type="button" id="btnCopySaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.copySaleOrder();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-printSaleOrder">
					<button type="button" id="btnPrintSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-saveSaleOrder">
					<button type="button" id="btnSaveSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.saveSaleOrder('#formdl_psssaleorder02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-auditSaleOrder">
					<button type="button" id="btnAuditSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.auditSaleOrder('#formdl_psssaleorder02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-reverseAuditSaleOrder">
					<button type="button" id="btnReverseAuditSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.reverseAuditSaleOrder();">反审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-closeSaleOrder">
					<button type="button" id="btnCloseSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.closeSaleOrder();">关闭</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleOrder-detail-enableSaleOrder">
					<button type="button" id="btnEnableSaleOrder" class="btn btn-default hide" onclick="PssSaleOrder_Detail.enableSaleOrder();">开启</button>
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
										<input class="items-btn pss-date" type="text" id="top-orderDate" name="top-orderDate" size="12" readonly value="${dataMap.orderDate }"></input>
										<span class="txt">交货日期:</span>
										<input class="items-btn pss-date" type="text" id="top-deliveryDate" name="top-deliveryDate" size="12" readonly value="${dataMap.deliveryDate }"></input>
										<input type="radio" id="radioSale" name="top-businessType" value="01" />
										<span class="txt mgr">销货</span>
										<input type="radio" id="radioReturn" name=top-businessType value="02" />
										<span class="txt">退货</span>
									</div>
									<div class="right">
										<span class="txt">单据编号:</span>
										<input class="items-btn" type="text" id="top-saleOrderNo" name="top-saleOrderNo" size="20" value="${dataMap.saleOrderNo }"/>
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
						<div id="content" class="table_content pss_detail_list" style="height:auto; width:auto;">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formdl_psssaleorder02" theme="simple" name="operform" action="${webPath}/pssSaleOrder/saveSaleOrderAjax">
								<dhcc:bootstarpTag property="formdl_psssaleorder02" mode="query" />
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