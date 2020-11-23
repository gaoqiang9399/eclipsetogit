<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>销售开票</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<link rel="stylesheet" href='${webPath}/component/pss/stock/css/PssStock_common.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleInvoice_Detail.js"></script>
		<script type="text/javascript" >
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
			$(function() {
				PssSaleInvoice_Detail.initPssSaleInvoiceBillList();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="col-md-12 column">
						<div class="search-div">
							<div class="col-xs-11 column mysearch-div" id="pills">
								<div class="mod-toolbar-top">
									<div class="left">
										<span class="txt f1">单据日期:</span>
										<input class="items-btn pss-date" type="text" id="top-startBillDate" name="top-startBillDate" readonly value="${dataMap.startBillDate }"></input>
										至&nbsp;&nbsp;
										<input class="items-btn pss-date" type="text" id="top-endBillDate" name="top-endBillDate" readonly value="${dataMap.endBillDate }"></input>
										
										<span class="txt">单据编号:</span>
										<input class="items-btn" type="text" id="top-billNo" name="top-billNo" size="20" style="background: #FFF !important;">
										
										<span class="txt">客户:</span> 
										<input class="items-btn" id="top-customer" name="top-customer"></input>
										
										<input type="radio" id="top-invoiceState" name="top-invoiceState" value="01" />
										<span class="txt">已全部开票</span>
										<input type="radio" id="top-invoiceState" name=top-invoiceState value="02" checked/>
										<span class="txt">未全部开票</span>
										
										
									</div>
									<div class="right" style="">
										<a class="ui-btn" onclick="PssSaleInvoice_Detail.searchPssSaleInvoiceBillList();" id="pssSaleInvoiceBillList">查询</a>
									</div>
								</div>
							</div>
						</div>
						<!--页面显示区域-->
						<div class="pss-bigform-table">
							
							<div id="content" class="table_content pss_detail_list"></div>
							
							<div class="search-div">
								<div class="col-xs-11 column mysearch-div" id="pills">
									<div class="mod-toolbar-top">
										<div class="left">
											<span class="txt f1">开票日期:</span>
											<input class="items-btn pss-date" type="text" id="top-invoiceDate" name="top-invoiceDate" readonly value="${dataMap.invoiceDate }" ></input>
											
											<span style="color:#ff4136">*</span><span class="txt">发票号:</span>
											<input class="items-btn" type="text" id="top-invoiceNo" name="top-invoiceNo" size="20" style="background: #FFF !important;">
											
											<span class="txt">发票金额:</span>
											<input class="items-btn" type="text" id="top-invoiceAmount" name="top-invoiceAmount" size="20"  style="background: #FFF !important;" readonly>
											
											<span style="color:#ff4136">*</span><span class="txt">发票抬头:</span>
											<input class="items-btn" type="text" id="top-invoiceTitle" name="top-invoiceTitle" size="20" style="background: #FFF !important;">
											
										</div>
										
									</div>
								</div>
							</div>
						
						</div>
						
						<%-- <div class="search-div">
							<div class="col-xs-11 column mysearch-div" id="pills">
								<div class="mod-toolbar-top">
									<div class="left">
										<span class="txt f1">开票日期:</span>
										<input class="items-btn pss-date" type="text" id="top-invoiceDate" name="top-invoiceDate" readonly value="${dataMap.invoiceDate }" ></input>
										
										<span style="color:#ff4136">*</span><span class="txt">发票号:</span>
										<input class="items-btn" type="text" id="top-invoiceNo" name="top-invoiceNo" size="20" style="background: #FFF !important;">
										
										<span class="txt">发票金额:</span>
										<input class="items-btn" type="text" id="top-invoiceAmount" name="top-invoiceAmount" size="20"  style="background: #FFF !important;" readonly>
										
										<span style="color:#ff4136">*</span><span class="txt">发票抬头:</span>
										<input class="items-btn" type="text" id="top-invoiceTitle" name="top-invoiceTitle" size="20" style="background: #FFF !important;">
										
									</div>
									
								</div>
							</div>
						</div> --%>
						
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="开票" action="开票" onclick="PssSaleInvoice_Detail.batchInvoice();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>