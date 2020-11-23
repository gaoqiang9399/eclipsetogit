;
var MfCusSaleProject_Insert = function(window, $) {
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
    	//销售数量
     	var productNum = $("input[name='productNum']").val();
     	//销售单价
     	var productPrice = $("input[name='productPrice']").val();
     	//销售额
		var saleAmt=0.00;
		if(productNum!=null&&productNum!=''&&productNum!=undefined&&productPrice!=null&&productPrice!=undefined&&productPrice!=''){
            productNum =productNum.replace(",","");
            productPrice =productPrice.replace(",","");
            saleAmt = (productNum*1)*(productPrice*1);
            $("input[name='saleAmt']").val(saleAmt);

		}
        var profit =saleAmt;
        //原料成本一
        var cosRaw1 = $("input[name='cosRaw1']").val();
        if (cosRaw1!=null&&cosRaw1!=undefined&&cosRaw1!=''){
            cosRaw1 =cosRaw1.replace(",","");
            profit = profit*1-cosRaw1*1;
        }
        //原料成本二
        var cosRaw2 = $("input[name='cosRaw2']").val();
        if (cosRaw2!=null&&cosRaw2!=undefined&&cosRaw2!=''){
            cosRaw2 =cosRaw2.replace(",","");
            profit = profit*1-cosRaw2*1;
        }
        //原料成本三
        var cosRaw3 = $("input[name='cosRaw3']").val();
        if (cosRaw3!=null&&cosRaw3!=undefined&&cosRaw3!=''){
            cosRaw3 =cosRaw3.replace(",","");
            profit = profit*1-cosRaw3*1;
        }
        //原料成本四
        var cosRaw4 = $("input[name='cosRaw4']").val();
        if (cosRaw4!=null&&cosRaw4!=undefined&&cosRaw4!=''){
            cosRaw4 =cosRaw4.replace(",","");
            profit = profit*1-cosRaw4*1;
        }
        //劳务成本一
        var cosLabour1 = $("input[name='cosLabour1']").val();
        if (cosLabour1!=null&&cosLabour1!=undefined&&cosLabour1!=''){
            cosLabour1 =cosLabour1.replace(",","");
            profit = profit*1-cosLabour1*1;
        }
        //劳务成本二
        var cosLabour2 = $("input[name='cosLabour2']").val();
        if (cosLabour2!=null&&cosLabour2!=undefined&&cosLabour2!=undefined){
            cosLabour2 =cosLabour2.replace(",","");
            profit = profit*1-cosLabour2*1;
        }
        $("input[name='profit']").val(profit);
        var profitRate=0;
        if(saleAmt!=''&&profit!=''){
            if(saleAmt!=0.00){
                profitRate =(profit*1)/(saleAmt*1)*100;
                $("input[name='profitRate']").val(profitRate);
            }
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
        var inputUrl = webPath+"/mfCusSaleProduct/input?cusNo="+cusNo;
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

