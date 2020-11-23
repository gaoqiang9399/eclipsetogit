;
var MfVouAfterRiskLevelAdjust_InitBusInfo = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
	var _vouAfterRiskLevelAdjust = function(pactId){
        window.location.href = webPath+"/mfVouAfterRiskLevelAdjust/input?pactId=" + pactId;
	};
	return {
		init : _init,
        vouAfterRiskLevelAdjust:_vouAfterRiskLevelAdjust,
	};
}(window, jQuery);
