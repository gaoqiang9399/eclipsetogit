<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/manage/js/CwCollectConfim_Insert.js"></script>
 <link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
 <script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
 <script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
    var cusNo = "${cusNo}";
    var collectType = "${collectType}";
    var appId = '${appId}';
    var nodeNo = '${nodeNo}';
    var temBizNo = '${cwCollectConfim.chargeId}';
    var chargeId = '${cwCollectConfim.chargeId}';
    var feeChargeType = '${feeChargeType}';
    var chargeSts = '${chargeSts}';
    $(function () {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                updateOnContentResize : true
            }
        })

        //判断审批历史模块的显隐
        if (chargeSts != '0' &&chargeId!='') {
            showWkfFlowVertical($("#wj-modelerrece"), chargeId, "","charge-fee");
        } else {
            $("#receChargeFeeInfo-block").remove();
        }
    })

    //到账确认
    function save(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                type : "POST",
                data:{ajaxData:JSON.stringify($(obj).serializeArray())},
                url : "${webPath}/cwCollectConfim/collectConfimAjax",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg,1);
                        myclose_click();
                    }else{
                        window.top.alert(data.msg,0);
                    }
                }
            });
        }
    }

    //发回重审
    function rallback(obj){
        var msg = "确定发回重审？"
        if(collectType != '1'){
            msg = "保后收费发回重审需要再次发起申请，确定发回重审？"
        }
        window.top.alert(msg,2,function(){
            LoadingAnimate.start();
            $.ajax({
                type : "POST",
                data:{ajaxData:JSON.stringify($(obj).serializeArray())},
                url : "${webPath}/cwCollectConfim/rollbackAjax",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg,1);
                        myclose_click();
                    }else{
                        window.top.alert(data.msg,0);
                    }
                }
            });
        });
    }

    function calRefundAmt(){
        $("input[name='refundAmt']").val("");
        var reguaranteeAmt = parseFloat($("input[name='reguaranteeAmt']").val().replace(/,/g,''));//应收担保费
        var reassessAmt = parseFloat($("input[name='reassessAmt']").val().replace(/,/g,''));//应收评审费
        var actualReceivedAmt = parseFloat($("input[name='actualReceivedAmt']").val().replace(/,/g,''));//实际到账金额
        if(actualReceivedAmt > (reguaranteeAmt + reassessAmt)){
            var refundAmt = actualReceivedAmt - (reguaranteeAmt + reassessAmt);
            $("input[name='refundAmt']").val(refundAmt.toFixed(2));
        }
    }

    function closeWindow(){
        myclose_click();
    };

    function changeCollectBankId(bankAcc){
        $("input[name='receviedAccoutName']").val(bankAcc.accountName);
        $("input[name='receviedBankName']").val(bankAcc.bank);
        $("input[name='receviedAccout']").val(bankAcc.accountNo);
    }
    function selectCollectBankAccDialog(){
        selectBankAccDialog(changeCollectBankId,cusNo,"选择到账账号");
    }

    function changeCollectBankInfo(bankAcc){
        $("input[name='receivedAccId']").val(bankAcc.id);
        $("input[name='receviedAccoutName']").val(bankAcc.accountName);
        $("input[name='receviedBankName']").val(bankAcc.bank);
        $("input[name='receviedAccout']").val(bankAcc.accountNo);
    }
    function selectBankDialog(){
        selectCusBankAccDialog(changeCollectBankInfo,"选择担保公司账号");
    }
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-title"></div>
                <form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/cwCollectConfim/collectConfimAjax">
                    <dhcc:bootstarpTag property="formcwcollectconfimdetail" mode="query"/>
                </form>
            </div>
            <c:choose>
                <c:when test="${entranceNo == null || entranceNo == ''}">
                    <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
                </c:when>
                <c:otherwise>
                    <div class="row clearfix">
                        <%@ include file="/component/model/templateIncludePage.jsp"%>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="clearfix approval-hist col-md-10 col-md-offset-1" id="receChargeFeeInfo-block">
            <div class="col-xs-12 column">
                <div class="list-table">
                    <div class="title">
                        <span><i class="i i-xing blockDian"></i>缴款通知书审批历史</span>
                        <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receFincSpInfo-div">
                            <i class='i i-close-up'></i> <i class='i i-open-down'></i>
                        </button>
                    </div>
                    <div class="content margin_left_15 collapse in " id="receFincSpInfo-div">
                        <div class="approval-process">
                            <div id="modelerrecefinc" class="modeler">
                                <ul id="wj-modelerrece" class="wj-modeler" isApp="false">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
