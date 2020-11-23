<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>提前放款申请</title>
    <script type="text/javascript" src="${webPath}/component/pact/advanceloan/js/MfBusAdvanceLoan.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
</head>
<style type="text/css">
    .mCSB_container {
        min-height: 100%;
    }
</style>
<script type="text/javascript">
    var cusNo = '${cusNo}';
    var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
    var pactId  ='${pactId}';
    var processId = '203136979533';
    $(function() {
    });
</script>
<body class="overflowHidden bg-white">
<div class="container form-container" id="normal">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form id="inputAdvanceLoan" method="post" theme="simple" name="operform" action="${webPath}/mfBusAdvanceLoan/insertAjax">
                    <dhcc:bootstarpTag property="busAdvanceLoan" mode="query" />
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="提交" action="提交" onclick="MfBusAdvanceLoan.insertAdvanceLoan('#inputAdvanceLoan');"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>