var MfCusPersonIncomeSurvey=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		
		//月收入1
		$('input[name=incomeMonthAmt]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		//月收入2
		$('input[name=incomeMonthAmtTwo]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		//月收入3
		$('input[name=incomeMonthAmtThree]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		//月收入4
		$('input[name=incomeMonthAmtFour]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		//月收入5
		$('input[name=incomeMonthAmtFive]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		//月收入6
		$('input[name=incomeMonthAmtSix]').blur(function(){
			_calSumAmt();
			_calAverageAmt();
		});
		
	};
	
	//新增保存
	var  _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0),'');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	
	// 计算总额
	var _calSumAmt = function(obj) {
		var incomeMonthAmt = $('input[name=incomeMonthAmt]').val().replace(/,/g,'');
		var incomeMonthAmtTwo = $('input[name=incomeMonthAmtTwo]').val().replace(/,/g,'');
		var incomeMonthAmtThree = $('input[name=incomeMonthAmtThree]').val().replace(/,/g,'');
		var incomeMonthAmtFour = $('input[name=incomeMonthAmtFour]').val().replace(/,/g,'');
		var incomeMonthAmtFive = $('input[name=incomeMonthAmtFive]').val().replace(/,/g,'');
		var incomeMonthAmtSix = $('input[name=incomeMonthAmtSix]').val().replace(/,/g,'');
		var calSumAmtFlag = true;
		if(calSumAmtFlag){
			var sum1 = CalcUtil.add(incomeMonthAmt,incomeMonthAmtTwo);
			var sum2 = CalcUtil.add(incomeMonthAmtThree,incomeMonthAmtFour);
			var sum3 = CalcUtil.add(incomeMonthAmtFive,incomeMonthAmtSix);
			var sum11 = CalcUtil.add(sum1,sum2);
			$('input[name=incomeSumAmt]').val(CalcUtil.formatMoney(CalcUtil.add(sum11,sum3),2));
		}
	};
	
	// 计算6个月平均收入
	var _calAverageAmt = function() {
		var incomeSumAmt = $('input[name=incomeSumAmt]').val().replace(/,/g,'');
		$('input[name=incomeAverageAmt]').val(CalcUtil.formatMoney(CalcUtil.divide(incomeSumAmt,'6'),2));
	};
	
	//修改月份
	var _changeMonthShow = function() {
		var month1 =  $('input[name=incomeMonth]').val();//月份1
		if(month1!='' && month1.length==6){
			var month2 = addMonth(month1,1);
			var month3 = addMonth(month2,1);
			var month4 = addMonth(month3,1);
			var month5 = addMonth(month4,1);
			var month6 = addMonth(month5,1);
			$('input[name=incomeMonthTwo]').val(month2);
			$('input[name=incomeMonthThree]').val(month3);
			$('input[name=incomeMonthFour]').val(month4);
			$('input[name=incomeMonthFive]').val(month5);
			$('input[name=incomeMonthSix]').val(month6);
		}
	};

	function addMonth(month,num){
		var year = month.substring(0,4);
		var calMonth = parseInt(month.substring(4,6))+num;
		if(calMonth < 10){
			return year+"0"+calMonth; 
		}else if(calMonth<=12){
			return year+calMonth; 
		}else{
			return (parseInt(year)+num)+"01"; 
		}
	}
	
	return{
		init:_init,
		ajaxSave:_ajaxSave,
		calAverageAmt:_calAverageAmt,
		calSumAmt:_calSumAmt,
		changeMonthShow:_changeMonthShow
	};
}(window, jQuery);
