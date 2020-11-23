;
var ArchiveEntrance = function (window, $) {

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
                    case "archiveApply"://资料合同归档
                        window.location.href = webPath + "/archiveInfoMain/getListPageForApply";
                        break;
                    case "archiveVoucherApply"://凭证归档
                        window.location.href = webPath + "/archiveInfoMain/getListPageForVoucherApply";
                        break;
                    case "archiveConfim"://归档确认
                        window.location.href = webPath + "/archiveInfoMain/getListPageForConfim";
                        break;
                    case "lendIn"://借阅申请
                        window.location.href= webPath+"/archiveInfoBorrow/getListPageForIn";
                        break;
                    case "lendOut"://借出申请
                        window.location.href = webPath + "/archiveInfoBorrow/getListPageForOut";
                        break;
                    case "archivePact"://归档合同
                        window.location.href = webPath + "/archiveInfoBorrow/getListPageForOut";
                        break;
                    case "voucherReturn"://凭证退还
                        window.location.href = webPath + "/archiveInfoVoucherReturn/getListPage";
                        break;
                    case "voucherConfim"://退还确认
                        window.location.href = webPath + "/archiveInfoVoucherReturn/getListPageForConfim";
                        break;
                    case "archiveReturn"://档案归还
                        window.location.href = webPath + "/archiveInfoBorrow/getListPageForReturn";
                        break;
                    case "archiveBlock"://档案封存
                        window.location.href = webPath + "/archiveInfoMain/getListPageForBlock";
                        break;
                    case "transferApply"://移交申请
                        window.location.href = webPath + "/archiveInfoTransfer/getListPage";
                        break;
                    case "receiveConfirm"://接收确认
                        window.location.href = webPath + "/archiveInfoTransfer/getListPageForConfim";
                        break;
                    case "archiveQuery"://档案查询
                        window.location.href = webPath + "/archiveInfoMain/getListPageForQuery";
                        break;
                    case "archiveStatic"://档案统计
                        window.location.href = webPath + "/archiveInfoDetail/getListPageForVoucherQuery";
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