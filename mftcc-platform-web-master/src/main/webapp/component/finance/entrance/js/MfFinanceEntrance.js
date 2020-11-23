;
var MfFinanceEntrance = function (window, $) {

    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        $("body").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });
        _bindClickEvent();
    };

    var _bindClickEvent = function () {
        $(".btn-app").bind("click", function (event) {
            if (!$(event.target).is("a")) {
                top.LoadingAnimate.start();
                switch ($(this).attr("id")) {
                    case "accountConfirm"://到账确认（小微）
                        window.location.href = webPath + "/cwCollectConfim/getListPage?queryType=1";
                        break;
                    case "cw-fee-confirm"://到账确认(工程)
                        window.location.href = webPath + "/cwCollectConfim/getListPage?queryType=2";
                        break;
                    case "query-refund-confirm"://退费确认(工程)
                        window.location.href = webPath + "/mfRefundRegister/getConfirmListPage";
                        break;
                    case "invoiceManage"://开票管理
                        window.location.href= webPath+"/cwBillManage/getListPage";
                        break;
                    case "spendingConfirm"://支出确认
                        window.location.href = webPath + "/cwPayConfim/getListPage";
                        break;
                    case "recoverConfirm"://追偿到账确认
                        window.location.href = webPath + "/cwRecourseConfim/getListPage";
                        break;
                    case "invoicePrint"://发票打印
                        window.location.href= webPath+"/cwInvoice/getListPage";
                        break;
                    case "accountDerived"://用友账务导出
                        window.location.href = webPath + "/todo";
                        break;
                    default:
                        break;
                }
            }
        });
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init
    };
}(window, jQuery);