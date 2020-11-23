<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>付款单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssFund.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssPaymentBill.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssPaymentBill_Input.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
			$(function() {
				pssPaymentBillInput.init();
				
			});
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-payment-insert">
					<button type="button" class="btn btn-primary" onclick="pssPaymentBill.saveAndAddOrder('#formpsspaymentbill0002');">保存并新增</button>
					<button type="button" class="btn btn-default" onclick="pssPaymentBill.saveOrder('#formpsspaymentbill0002');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-payment-check">
					<button type="button" class="btn btn-default" onclick="pssPaymentBill.auditOrder('#formpsspaymentbill0002');">审核</button>
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
										<span class="txt fl"><label class="pssTabRed">*</label>供应商：</span> 
										<input class="items-btn" id="supp" name="supp"/>
										<span class="txt">付款人：</span> 
										<input class="items-btn" id="top-PayerId" name="top-PayerId"/>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-paymentNo" name="top-paymentNo" value="${dataMap.paymentNo} "/>
									</div>
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
							<button type="button" class="btn btn-primary" onclick="pssPaymentBill.selectSourceBill('#formpsspaymentbill0002');">选择源单</button>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="content2" class="table_content pss_detail_list">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formpsspaymentbill0002" theme="simple" name="operform" action="${webPath}/pssPaymentBill/insertAjax">
								<dhcc:bootstarpTag property="formpsspaymentbill0002" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="pssPaymentBill.cancelInsert();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>