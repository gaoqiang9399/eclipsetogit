;
var MfBusReceTransferMain_Insert = function(window, $) {
    var _init = function(){

        $(".scroll-content").mCustomScrollbar({
            advanced:{
                updateOnContentResize:true
            }
        });
    };

	return {
		init : _init,
	};
}(window, jQuery);
