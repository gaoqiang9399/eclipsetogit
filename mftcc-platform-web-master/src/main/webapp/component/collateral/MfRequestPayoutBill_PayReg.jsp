<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>请款详情</title>
	<link rel="stylesheet" href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container scroll-content" >
		<div class="col-md-10 col-md-offset-1 margin_top_20">
			<div class="bootstarpTag">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="MfPaymentRegPageForm" theme="simple" name="operform" action="${webPath}/mfRequestPayoutBill/paymentReg">
					<dhcc:bootstarpTag property="formbillpaymentregpage" mode="query"/>
					<%--<table class="table table-bordered" title="formbillpaymentregpage">
							<tbody>
							<tr>
								<td class="tdlable right" colspan="1" rowspan="1">
									<label class="control-label startRang ">付款日期</label>
								</td>
								<td class="tdvalue  right" colspan="1" rowspan="1">
									<div class="input-group">
										<input type="text" title="付款日期" name="paymentDate" datatype="6" mustinput="1" class="calendar form-control "
											   maxlength="17" onclick="selectrili(this,null,null,today);;" onmousedown="enterKey()" onkeydown="enterKey();"
											   onblur="func_uior_valTypeImm(this);">
										<span class="input-group-addon">
											<i class='i i-rili pointer' onClick='selectrili(this,null,null,today);'></i>
										</span>
									</div>
									<div class="hidden">
										<input type="hidden" title="请款编号" name="requestId" datatype="0" mustinput="0" value="${mfRequestPayoutBill.requestId}">
									</div>
								</td>
							</tr>
							</tbody>
						</table>--%>
				</form>
			</div>
		</div>
		<div class="col-xs-10 col-xs-offset-1">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="基本信息" name="MfRequestPayoutBillAction" data-sort="14" data-tablename="mf_request_payout_bill">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>基本信息</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRequestPayoutBillAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfRequestPayoutBillAction" style="margin-top: 10px;">
							<form method="post" id="MfRequestPayoutBillActionForm" theme="simple" name="operform" action="${webPath}/mfRequestPayoutBill/updateAjaxByOne">
								${requestpayoutbilldetailhtml}
							</form>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column info-block">
						<div class="form-table">
							<!--上传文件-->
							<div class="row clearfix">
								<div class="col-xs-12 col-md-offset-0 column">
									<%@include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort family_sort" style="border: 0px;">
				<div class="dynamic-block" title="请款明细" name="requestPayoutFunds" data-sort="14" data-tablename="mf_oa_archives_family">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>请款明细</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#requestPayoutFunds">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="requestPayoutFunds">
							${detailTableHtml}
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort">
				<div id="requestPayoutApproveHis" class="row clearfix" style="display: none;">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block" class="approval-hist">
							<div class="list-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>请款审批历史</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in "
									 id="spInfo-div">
									<div class="approval-process">
										<div id="modeler1" class="modeler">
											<ul id="wj-modeler2" class="wj-modeler" isApp="false">
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="height: 10px;"></div>
	<div id="requestSaveBtn" class="formRowCenter "  style="display:block;">
		<dhcc:thirdButton value="登记" action="登记" onclick="savePaymentRegAjax('#MfPaymentRegPageForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfRequestPayoutBill_Detail.js"></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript">
	var requestId = '${requestId}';
    var applySts = '${mfRequestPayoutBill.applySts}';
	var webPath = '${webPath}';
    var aloneFlag = true;
    var dataDocParm={
        relNo:requestId,
        docType:'messageDoc',
        docTypeName:"要件资料",
        docSplitName:"要件资料",
        query:''
    };
    $(function(){
        mfRequestPayoutDetail.init();
        if(applySts != 0){
            showApproveHis();
        }
    })
    //展示审批历史
    function showApproveHis(){
        //获得审批历史信息
        showWkfFlowVertical($("#wj-modeler2"),requestId,"42","request_money_approval");
        $("#requestPayoutApproveHis").show();
    }
    function ajaxSave(){
        $.ajax({
            url : webPath+"/mfRequestPayoutBill/submitProcessAjax",
            data : {
                requestId:requestId
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    myclose_click();
                } else {
                    alert(data.msg, 0);
                }
            },
            error : function() {
            }
        });
    }

    //保存付款登记
   	function savePaymentRegAjax(obj){
        var appFlag = submitJsMethod($(obj).get(0), '');
        if (appFlag) {
            var url = $(obj).attr("action");
            var appDataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : appDataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.flag=true;
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    }else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    }

</script>
</html>