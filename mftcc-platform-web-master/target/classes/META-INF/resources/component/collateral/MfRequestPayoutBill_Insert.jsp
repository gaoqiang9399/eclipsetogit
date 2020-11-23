<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/MfRequestPayoutBill_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/MfRequestPayoutBill_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		<script type="text/javascript" src="${webPath}/themes/factor/js/selectInfo.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
            var webPath = '${webPath}';
            var requestId = '${requestId}';
            var requestState = '${requestState}';
            var collClass = '${collClass}';
            collClass =JSON.parse(collClass);
            var relationRequestIdMap = ${relationRequestIdMap};
            var aloneFlag = true;
            var dataDocParm = {
                relNo:requestId,
                docType:"messageDoc",
                docTypeName:"要件资料",
                docSplitName:"要件资料",
                query:''
            };
            $(function() {
                mfRequestPayoutBillInsert.init();
                mfRequestPayoutDetail.init();
                if(requestState==''||requestState=='null'){
                    $("#requestSaveBtn").css({display:"block"});
                    $("#requestSubmitBtn").css({display:"none"});
				}else{
                    $("#requestSaveBtn").css({display:"none"});
                    $("#requestSubmitBtn").css({display:"block"});
				}

            });
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container" id="myRequestPayoutFund">
			<div class="scroll-content ">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfRequestPayoutBillForm" theme="simple" name="operform" action="${webPath}/mfRequestPayoutBill/insertAjax">
							<dhcc:bootstarpTag property="formrequestpayoutbillbase" mode="query"/>
						</form>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
					</div>
				</div>
				<div class="col-xs-10 col-xs-offset-1">
					<%--<div class="arch_sort" style="border: 0px;">--%>
						<div class="dynamic-block" title="请款项明细" name="requestPayoutFunds" data-sort="14" data-tablename="mf_request_Payout_funds">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>请款项明细</span>
									<button id="fundsadd" class="btn btn-link formAdd-btn"  title="新增">
										<i class="i i-jia3"></i>
									</button>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#requestPayoutFunds">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div class="content collapse in" id="requestPayoutFunds">
									${detailTableHtml}
								</div>
							</div>
						</div>
					<%--</div>--%>
				</div>

			</div>
		</div>
		<div id="requestSaveBtn" class="formRowCenter "  style="display:none;">
			<dhcc:thirdButton value="保存" action="保存" onclick="mfRequestPayoutBillInsert.saveRequestPayoutBaseAjax('#MfRequestPayoutBillForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
		<div id="requestSubmitBtn" class="formRowCenter "  style="display:none;">
			<dhcc:thirdButton value="提交申请" action="提交申请" onclick="mfRequestPayoutBillInsert.saveRequestPayoutBillAjax('#MfRequestPayoutBillForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>

	</body>
</html>
