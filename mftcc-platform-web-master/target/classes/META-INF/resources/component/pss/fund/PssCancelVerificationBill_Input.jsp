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
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssCancelVerificationBill.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssCancelVerificationBill_Input.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var cancelType = '${dataMap.cancelType}';
		    
			$(function() {
				pssCancelVerificationBillInput.init();
				
			});
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-cancel-verification-insert">
					<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.saveAndAddOrder('#formpsscancelverificationbill0002');">保存并新增</button>
					<button type="button" class="btn btn-default" onclick="pssCancelVerificationBill.saveOrder('#formpsscancelverificationbill0002');">保存</button>
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
										<span class="txt fl">业务类型：</span> 
										<input class="items-btn" id="top-cancelType" name="top-cancelType" style="width:100px;"/>
										<c:if test='${dataMap.cancelType eq "1"} || ${dataMap.cancelType eq "3"}'>
											<span class="txt"><label class="pssTabRed">*</label>客户：</span> 
											<input class="items-btn" id="cus" name="cus"/>
										</c:if>
										<c:if test='${dataMap.cancelType eq "2"} || ${dataMap.cancelType eq "3"}'>
											<span class="txt"><label class="pssTabRed">*</label>供应商：</span> 
											<input class="items-btn" id="supp" name="supp"/>
										</c:if>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-cancelNo" name="top-cancelNo" value="${dataMap.cancelNo} "/>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-div column">
							<div class="show-btn" style="float:left">
								<c:if test='${dataMap.cancelType eq "1"}'>
									<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.selectBefRecSourceBill('#formpsscancelverificationbill0002');">选择预收单据</button>
								</c:if>
								<c:if test='${dataMap.cancelType eq "2"}'>
									<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.selectBefPaySourceBill('#formpsscancelverificationbill0002');">选择预付单据</button>
								</c:if>
								<c:if test='${dataMap.cancelType eq "3"}'>
									<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.selectShouldRecSourceBill('#formpsscancelverificationbill0002');">选择应收单据</button>
								</c:if>
							</div>
						</div>
						<div class="pss-bigform-table">
							<div id="content1" class="table_content pss_detail_list">
							</div>
						</div>
						<div class="btn-div column">
							<div class="show-btn" style="float:left">
								<c:if test='${dataMap.cancelType eq "1"}'>
									<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.selectShouldRecSourceBill('#formpsscancelverificationbill0002');">选择应收单据</button>
								</c:if>
								<c:if test='${dataMap.cancelType eq "2"} || ${dataMap.cancelType eq "3"}'>
									<button type="button" class="btn btn-primary" onclick="pssCancelVerificationBill.selectShouldPaySourceBill('#formpsscancelverificationbill0002');">选择应付单据</button>
								</c:if>
							</div>
						</div>
						<div class="pss-bigform-table">
							<div id="content2" class="table_content pss_detail_list">
							</div>
						</div>
						<div class="pss-bigform-form">
							<div class="bootstarpTag">
								<form method="post" enctype="multipart/form-data" id="formpsscancelverificationbill0002" theme="simple" name="operform" action="${webPath}/pssCancelVerificationBill/insertAjax">
									<dhcc:bootstarpTag property="formpsscancelverificationbill0002" mode="query" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="pssCancelVerificationBill.cancelInsert();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>