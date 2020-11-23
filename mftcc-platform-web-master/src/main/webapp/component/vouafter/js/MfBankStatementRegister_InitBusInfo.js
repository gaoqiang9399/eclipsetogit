;
var MfBankStatementRegister_InitBusInfo = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
	var _BankStatementRegister = function(pactId){
        window.location.href = webPath+"/mfBankStatementRegister/input?pactId=" + pactId;
	};
	return {
		init : _init,
        BankStatementRegister:_BankStatementRegister,
	};
}(window, jQuery);
