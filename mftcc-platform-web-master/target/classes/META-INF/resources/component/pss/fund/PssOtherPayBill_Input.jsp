<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>其他支出单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssFund.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherPayBill.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherPayBill_Input.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
			
			var buySaleExpIds = '${dataMap.buySaleExpIds}';
			var supplierId = '${dataMap.supplierId}';
			
			$(function() {
				pssOtherPayBillInput.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-other-pay-insert">
					<button type="button" class="btn btn-primary" onclick="pssOtherPayBill.saveAndAddOrder('#formpssotherpaybill0002');">保存并新增</button>
					<button type="button" class="btn btn-default" onclick="pssOtherPayBill.saveOrder('#formpssotherpaybill0002');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-other-pay-check">
					<button type="button" class="btn btn-default" onclick="pssOtherPayBill.auditOrder('#formpssotherpaybill0002');">审核</button>
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
										<input class="items-btn" id="supp" name="supp"/>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" readonly value="${dataMap.currDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-otherPayNo" name="top-otherPayNo" value="${dataMap.otherPayNo} "/>
									</div>
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
							<form method="post" enctype="multipart/form-data" id="formpssotherpaybill0002" theme="simple" name="operform" action="${webPath}/pssOtherPayBill/insertAjax">
								<dhcc:bootstarpTag property="formpssotherpaybill0002" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>