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
						<form method="post" id="costreg" theme="simple" name="operform" action="${webPath}/mfLawsuit/costRegInsert">
							<dhcc:bootstarpTag property="formcostreg" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="lawsuitDetail.bindInsertAjax('#costreg')"></dhcc:thirdButton>
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
        var filingFee=$("input[name=filingFee]").val();
        if(filingFee!=undefined&&filingFee!=null){
            filingFee = filingFee.replace(/,/g,'');
            filingFee = Number(filingFee);
		}else{
            filingFee=0.00;
		}
        var saveCost=$("input[name=saveCost]").val();
        if(saveCost!=undefined&&saveCost!=null){
            saveCost = saveCost.replace(/,/g,'');
            saveCost = Number(saveCost);
        }else{
            saveCost=0.00;
        }
        var securityPremium=$("input[name=securityPremium]").val();
        if(securityPremium!=undefined&&securityPremium!=null){
            securityPremium = securityPremium.replace(/,/g,'');
            securityPremium = Number(securityPremium);
        }else{
            securityPremium=0.00;
        }
        var appraisalCost=$("input[name=appraisalCost]").val();
        if(appraisalCost!=undefined&&appraisalCost!=null){
            appraisalCost = appraisalCost.replace(/,/g,'');
            appraisalCost = Number(appraisalCost);
        }else{
            appraisalCost=0.00;
        }
        var announcementFee=$("input[name=announcementFee]").val();
        if(announcementFee!=undefined&&announcementFee!=null){
            announcementFee = announcementFee.replace(/,/g,'');
            announcementFee = Number(announcementFee);
        }else{
            announcementFee=0.00;
        }
        var executionFee=$("input[name=executionFee]").val();
        if(executionFee!=undefined&&executionFee!=null){
            executionFee = executionFee.replace(/,/g,'');
            executionFee = Number(executionFee);
        }else{
            executionFee=0.00;
        }
        var postFee=$("input[name=postFee]").val();
        if(postFee!=undefined&&postFee!=null){
            postFee = postFee.replace(/,/g,'');
            postFee = Number(postFee);
        }else{
            postFee=0.00;
        }
        var barFee=$("input[name=barFee]").val();
        if(barFee!=undefined&&barFee!=null){
            barFee = barFee.replace(/,/g,'');
            barFee = Number(barFee);
        }else{
            barFee=0.00;
        }
        var otherCharges=$("input[name=otherCharges]").val();
        if(otherCharges!=undefined&&otherCharges!=null){
            otherCharges = otherCharges.replace(/,/g,'');
            otherCharges = Number(otherCharges);
        }else{
            otherCharges=0.00;
        }
        var assessmentFee=$("input[name=assessmentFee]").val();
        if(assessmentFee!=undefined&&assessmentFee!=null){
            assessmentFee = assessmentFee.replace(/,/g,'');
            assessmentFee = Number(assessmentFee);
        }else{
            assessmentFee=0.00;
        }
        var advancePayFee=$("input[name=advancePayFee]").val();
        if(advancePayFee!=undefined&&advancePayFee!=null){
            advancePayFee = advancePayFee.replace(/,/g,'');
            advancePayFee = Number(advancePayFee);
        }else{
            advancePayFee=0.00;
        }
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
