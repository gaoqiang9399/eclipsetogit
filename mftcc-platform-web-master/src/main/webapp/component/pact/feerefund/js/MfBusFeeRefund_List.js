;
var MfBusFeeRefund_List = function(window, $) {
	var _feeRefundInput = function(){
		window.location.href= webPath + "/mfBusFeeRefund/input"; //列表数据查询的url
	};
    var _getDetailPage = function (obj,url){
        top.LoadingAnimate.start();
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        window.location.href=url;
    };
	return {
        feeRefundInput : _feeRefundInput,
        getDetailPage:_getDetailPage,
	};
}(window, jQuery);
