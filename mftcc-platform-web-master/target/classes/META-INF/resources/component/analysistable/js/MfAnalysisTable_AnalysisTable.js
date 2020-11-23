;
var MfAnalysisTable_AnalysisTable = function(window, $) {
	
	var _init = function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
	};
	
	return {
		init : _init
	};
}(window, jQuery);
