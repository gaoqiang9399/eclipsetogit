;
var MfCusBusService_Insert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
		 });
	};
    var _calcAmt = function () {
    	//收入数量
     	var inNum = $("input[name='inNum']").val();
     	//收入单价
     	var inPrice = $("input[name='inPrice']").val();
     	//收入额
		var busIncome=0.00;
		if(inNum!=null&&inNum!=''&&inNum!=undefined&&inPrice!=null&&inPrice!=undefined&&inPrice!=''){
            inNum =inNum.replace(",","");
            inPrice =inPrice.replace(",","");
            busIncome = (inNum*1)*(inPrice*1);
            $("input[name='busIncome']").val(busIncome);

		}

        //支出数量
        var expNum = $("input[name='expNum']").val();
        //支出单价
        var expPrice = $("input[name='expPrice']").val();
        //支出额
        var busExp=0.00;
        if(expNum!=null&&expNum!=''&&expNum!=undefined&&expPrice!=null&&expPrice!=undefined&&expPrice!=''){
            expNum =expNum.replace(",","");
            expPrice =expPrice.replace(",","");
            busExp = (expNum*1)*(expPrice*1);
            $("input[name='busExp']").val(busExp);

        }

        //计算业务毛利润
        var profit = 0;
        if(busIncome!=''&& busExp!='' ){
            profit = busIncome*1 - busExp*1;
            $("input[name='profit']").val(profit.toFixed(2));
        }

        //计算毛利率
        var profitRate = 0;
        if(busIncome!=''&& profit!='' ){
            profitRate = (profit*1)/(busIncome*1)*100;
            $("input[name='profitRate']").val(profitRate.toFixed(2));
        }

    };

    //新增保存
    var  _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };
    //新增保存
    var  _ajaxSaveAndAdd = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusBusService/input?cusNo="+cusNo;
        if(flag){
            ajaxInserAndAddCusForm(obj,inputUrl);
        }
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        calcAmt : _calcAmt,
        ajaxSave : _ajaxSave,
        ajaxSaveAndAdd : _ajaxSaveAndAdd,
	};
	
}(window, jQuery);

