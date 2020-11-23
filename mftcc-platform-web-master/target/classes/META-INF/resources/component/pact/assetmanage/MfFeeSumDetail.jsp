<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>费用登记</title>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="costreg" theme="simple" name="operform" action="">
					<dhcc:bootstarpTag property="formfeesumdetail" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="lawsuitDetail.bindClose()"></dhcc:thirdButton>
	</div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuitDetail.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<script type="text/javascript">
    var query = '${query}';
    $(function() {
        lawsuitDetail.init();
    });
    //金额格式化
    function formatPrice(money) {
        var tpMoney = '0.00';
        if (money != null) {
            tpMoney = money;
        }
        tpMoney = new Number(tpMoney);
        if (isNaN(tpMoney)) {
            return '0.00';
        }
        tpMoney = tpMoney.toFixed(2) + '';
        var re = /^(-?\d+)(\d{3})(\.?\d*)/;
        while (re.test(tpMoney)) {
            tpMoney = tpMoney.replace(re, "$1,$2$3")
        }
        return tpMoney;
    };
    //实时计算费用总额
    setInterval(function(){
        var filingFee=Number($("input[name=filingFee]").val().replace(/,/g,''));
        var saveCost=Number($("input[name=saveCost]").val().replace(/,/g,''));
        var securityPremium=Number($("input[name=securityPremium]").val().replace(/,/g,''));
        var appraisalCost=Number($("input[name=appraisalCost]").val().replace(/,/g,''));
        var announcementFee=Number($("input[name=announcementFee]").val().replace(/,/g,''));
        var executionFee=Number($("input[name=executionFee]").val().replace(/,/g,''));
        var postFee=Number($("input[name=postFee]").val().replace(/,/g,''));
        var barFee=Number($("input[name=barFee]").val().replace(/,/g,''));
        var otherCharges=Number($("input[name=otherCharges]").val().replace(/,/g,''));
        var assessmentFee=Number($("input[name=assessmentFee]").val().replace(/,/g,''));
        var advancePayFee=Number($("input[name=advancePayFee]").val().replace(/,/g,''));
        var amount=filingFee+saveCost+securityPremium+appraisalCost+announcementFee+executionFee+postFee+barFee+otherCharges+assessmentFee-advancePayFee;
        amount=formatPrice(amount);
        $("input[name=totalExpenses]").val(amount);
        var monry=$("input[name=totalExpenses]").val();
        if(monry=='0.00'){
            var monry=$("input[name=totalExpenses]").val('');
        }
    },100);
</script>
</html>
