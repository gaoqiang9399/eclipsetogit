var MfCusCustomer_head4customers = function (window, $) {
    var _accountBalanceList = function (cusNo) {
        top.window.openBigForm(webPath + "/mfBusAccountBalQuery/accountBalanceList?cusNo=" + cusNo + "&query=query", '账户余额', myclose);
    };

    return {
        accountBalanceList: _accountBalanceList
    }
}(window, jQuery);