var MfCusProfitLoss=function(window, $){
	var _init=function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("table").find("tr").css("border-right"," 1px solid #ddd ");
		
	}
    var  _insertAjax = function(obj) {
        ajaxInsertCusForm(obj);
    }
    //分期还款改变事件
	var _amortizationLoanChange= function(){
        //分期还款
        var amortizationLoan =  $("input[name=amortizationLoan]").val();
        amortizationLoan= amortizationLoan.replace(/,/g,'');
        //月均
        var amortizationLoanAverage = CalcUtil.formatMoney(CalcUtil.divide(amortizationLoan,12),2) ;
        $("input[name=amortizationLoanAverage]").val(amortizationLoanAverage);
        //可支配收入
        var disposable =  $("input[name=disposable]").val();
        disposable= disposable.replace(/,/g,'');
        disposable = CalcUtil.add(disposable,amortizationLoan*-1);
        $("input[name=disposable]").val(disposable);
        var disposableAverage = CalcUtil.formatMoney(CalcUtil.divide(disposable,12),2) ;
        $("input[name=disposableAverage]").val(disposableAverage);
	}
    //其他支出改变事件
	var _otherExpChange= function(){
        //分期还款
        var otherExp =  $("input[name=otherExp]").val();
        otherExp= otherExp.replace(/,/g,'');
        //月均
        var otherExpAverage = CalcUtil.formatMoney(CalcUtil.divide(otherExp,12),2) ;
        $("input[name=otherExpAverage]").val(otherExpAverage);
        //可支配收入
        var disposable =  $("input[name=disposable]").val();
        disposable= disposable.replace(/,/g,'');
        disposable = CalcUtil.add(disposable,otherExp*-1);
        $("input[name=disposable]").val(disposable);
        var disposableAverage = CalcUtil.formatMoney(CalcUtil.divide(disposable,12),2) ;
        $("input[name=disposableAverage]").val(disposableAverage);
	}

	return{
		init:_init,
        insertAjax:_insertAjax,
        amortizationLoanChange:_amortizationLoanChange,
        otherExpChange:_otherExpChange,
	};
}(window, jQuery);

