<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
    <script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/manage/js/CwCollectConfim_Insert.js"></script>
<script type="text/javascript">
    var cusNo = "${cusNo}";
    var collectType = "${collectType}";
    var chargeId = "${chargeId}";
    var feeChargeType = "${feeChargeType}";
    var appId = '${appId}';
    var nodeNo = '${nodeNo}';
    var busModel = '${busModel}';
    var temBizNo = '${cwCollectConfim.chargeId}';
    var feeChargeType = '${feeChargeType}';
    $(function () {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                updateOnContentResize : true
            }
        })
    })

    //到账确认
    function save(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                type : "POST",
                data:{ajaxData:JSON.stringify($(obj).serializeArray()),busModel:busModel},
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
        var actualReceivedAmt = parseFloat($("input[name='actualReceivedAmt']").val().replace(/,/g,''));//实际到账金额
        if("12" != busModel){
            var reassessAmt = parseFloat($("input[name='reassessAmt']").val().replace(/,/g,''));//应收评审费
            if(actualReceivedAmt > (reguaranteeAmt + reassessAmt)){
                var refundAmt = actualReceivedAmt - (reguaranteeAmt + reassessAmt);
                $("input[name='refundAmt']").val(refundAmt.toFixed(2));
            }
        }else{
            var accountAmt = parseFloat($("input[name='accountAmt']").val().replace(/,/g,''));//应收总额
            if(actualReceivedAmt > accountAmt){
                var refundAmt = actualReceivedAmt - accountAmt;
                $("input[name='refundAmt']").val(refundAmt.toFixed(2));
            }else{
                $("input[name='refundAmt']").val(0.00);
            }
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
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/cwCollectConfim/collectConfimAjax">
                    <dhcc:bootstarpTag property="formcwcollectconfimedit" mode="query"/>
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
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="发回重审" action="发回重审" onclick="rallback('#insertForm');"></dhcc:thirdButton>
        <dhcc:thirdButton value="提交" action="提交" onclick="save('#insertForm');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
