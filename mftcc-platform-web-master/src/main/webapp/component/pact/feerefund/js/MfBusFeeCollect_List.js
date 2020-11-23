;
var MfBusFeeCollect_List = function(window, $) {
	var _feeCollectInput = function(){
		window.location.href= webPath + "/mfBusFeeCollect/input"; //列表数据查询的url
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
        feeCollectInput  : _feeCollectInput,
        getDetailPage:_getDetailPage,
	};
}(window, jQuery);
