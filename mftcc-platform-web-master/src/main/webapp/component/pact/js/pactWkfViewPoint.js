;
var WkfViewPoint = function(window,$){
	 //计算放款成数:放款成数=批复金额/车辆评估值
    var _countLoanRate = function(obj){
        var name = $(obj).attr("name");
        var pactAmt = "";
        var evalAmt = "";
        if(name == "pactAmt"){
            pactAmt = $(obj).val();
        }else{
            pactAmt = $("input[name='pactAmt']").val();
        }

        if(name == "carEvalAmt"){
            evalAmt = $(obj).val();
        }else{
            evalAmt = $("input[name='carEvalAmt']").val();
        }
        pactAmt = pactAmt.replace(/,/g,'');
        evalAmt = evalAmt.replace(/,/g,'');
        var loanRate = CalcUtil.divide(pactAmt,evalAmt);
        loanRate = CalcUtil.multiply(loanRate,100);
        loanRate =  CalcUtil.formatMoney(loanRate,2);
        $("input[name='loanRate']").val(loanRate);
    }

	return{
        countLoanRate : _countLoanRate
	}
}(window,jQuery);