;
var MfRefundManage_InitBusInfo = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
	var _refundManage = function(pactId){
        window.location.href = webPath+"/mfRefundManage/input?pactId=" + pactId;
	};
	return {
		init : _init,
        refundManage:_refundManage,
	};
}(window, jQuery);
