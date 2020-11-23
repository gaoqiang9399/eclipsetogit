;
var MfHandAmtManage_InitBusInfo = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
	var _handAmtManage = function(pactId){
        window.location.href = webPath+"/mfHandAmtManage/input?pactId=" + pactId;
	};
	return {
		init : _init,
        handAmtManage:_handAmtManage,
	};
}(window, jQuery);
