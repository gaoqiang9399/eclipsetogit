<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>其他收入单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssOtherRecBill_Input.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    
		    PssOtherRecBillInput.otherRecNo = '${pssOtherRecBill.otherRecNo}';
			PssOtherRecBillInput.otherRecId = '${otherRecId}';
			PssOtherRecBillInput.auditSts = "${pssOtherRecBill.auditSts }";
			
			$(function() {
				PssOtherRecBillInput.init();
			});
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="btn-div column">
			<div class="show-btn" style="float:left">
				<dhcc:pmsTag pmsId="pss-other-rec-insert">
					<button type="button" id="btnSaveAndAddOtherRecBill" class="btn btn-primary hide" onclick="PssOtherRecBillInput.saveAndAddOtherRecBill('#formpssotherrecbill0002');">保存并新增</button>
					<button type="button" id="btnAddOtherRecBill" class="btn btn-primary hide" onclick="PssOtherRecBillInput.addOtherRecBill();">新增</button>
					<button type="button" id="btnSaveOtherRecBill" class="btn btn-default hide" onclick="PssOtherRecBillInput.saveOtherRecBill('#formpssotherrecbill0002');">保存</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="pss-other-rec-check">
					<button type="button" id="btnAuditOtherRecBill" class="btn btn-default hide" onclick="PssOtherRecBillInput.auditOtherRecBill('#formpssotherrecbill0002');">审核</button>
					<button type="button" id="btnReverseAuditOtherRecBill" class="btn btn-default hide" onclick="PssOtherRecBillInput.reverseAuditOtherRecBill();">反审核</button>
				</dhcc:pmsTag>
				
				<%-- <dhcc:pmsTag pmsId="pss-other-rec-print"> --%>
					<button type="button" id="btnPrintOtherRecBill" class="btn btn-default hide" onclick="PssOtherRecBillInput.printBill();">打印</button>
				<%-- </dhcc:pmsTag> --%>
				
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
										<span class="txt fl">客户：</span> 
										<input class="items-btn" id="top-cus" name="top-cus" value="${dataMap.cusNo }"></input>
										<span class="txt">单据日期：</span>
										<input class="items-btn pss-date" type="text" id="top-billDate" name="top-billDate" size="12" readonly value="${dataMap.billDate }"></input>
									</div>
									<div class="right">
										<span class="txt">单据编号：</span>
										<input class="items-btn" type="text" id="top-otherRecNo" name="top-otherRecNo" size="20" value="${dataMap.otherRecNo }"/>
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
							<form method="post" enctype="multipart/form-data" id="formpssotherrecbill0002" theme="simple" name="operform" action="${webPath}/pssOtherRecBill/saveOtherRecBillAjax">
								<dhcc:bootstarpTag property="formpssotherrecbill0002" mode="query" />
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