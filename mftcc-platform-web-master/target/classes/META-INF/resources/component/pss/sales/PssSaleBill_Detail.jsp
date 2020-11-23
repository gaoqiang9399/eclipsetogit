<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>销货单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
		    var auditStsed = "${pssSaleBill.auditStsed }";
			var docBizNo = '${dataMap.docBizNo }';
			var saleBillNo = '${pssSaleBill.saleBillNo }';
			var saleBillId = "${saleBillId}";
			var saleOrderId = "${saleOrderId}";
			var saleOrderNo = "${pssSaleBill.saleOrderNo }";
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
				PssSaleBill_Detail.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-saleBill-detail-saveAndAddSaleBill">
					<button type="button" id="btnSaveAndAddSaleBill" class="btn btn-primary hide" onclick="PssSaleBill_Detail.saveAndAddSaleBill('#formdl_psssalebill02');">保存并新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-generateSaleReturnBill">
					<button type="button" id="btnGenerateSaleReturnBill" class="btn btn-primary hide" onclick="PssSaleBill_Detail.generateSaleReturnBill('#formdl_psssalebill02');">生成销货退货单</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-addSaleBill">
					<button type="button" id="btnAddSaleBill" class="btn btn-primary hide" onclick="PssSaleBill_Detail.addSaleBill();">新增</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-copySaleBill">
					<button type="button" id="btnCopySaleBill" class="btn btn-default hide" onclick="PssSaleBill_Detail.copySaleBill();">复制</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-printSaleBill">
					<button type="button" id="btnPrintSaleBill" class="btn btn-default hide" onclick="PssSaleBill_Detail.printBill();">打印</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-saveSaleBill">
					<button type="button" id="btnSaveSaleBill" class="btn btn-default hide" onclick="PssSaleBill_Detail.saveSaleBill('#formdl_psssalebill02');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-exportSN">
					<button type="button" id="btnExportSN" class="btn btn-default hide" onclick="javascript:alert('建设中，敬请关注...');">导出SN</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-auditSaleBill">
					<button type="button" id="btnAuditSaleBill" class="btn btn-default hide" onclick="PssSaleBill_Detail.auditSaleBill('#formdl_psssalebill02');">审核</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-saleBill-detail-reverseAuditSaleBill">
					<button type="button" id="btnReverseAuditSaleBill" class="btn btn-default hide" onclick="PssSaleBill_Detail.reverseAuditSaleBill();">反审核</button>
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
										<input class="items-btn" type="text" id="top-saleBillNo" name="top-saleBillNo" size="20" value="${dataMap.saleBillNo }"/>
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
							<form method="post" enctype="multipart/form-data" id="formdl_psssalebill02" theme="simple" name="operform" action="${webPath}/pssSaleBill/saveSaleBillAjax">
								<dhcc:bootstarpTag property="formdl_psssalebill02" mode="query" />
							</form>
							<!-- 上传单据 -->
							<div class="row clearfix">
								<div class="col-xs-12 column">
									<iframe id="pssSourceDocumentIframe" frameborder=0 width="100%" src="${webPath}/component/pss/sales/PssSourceDocument.jsp?docBizNo=${dataMap.docBizNo }&auditStsed=${pssSaleBill.auditStsed }"></iframe>
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