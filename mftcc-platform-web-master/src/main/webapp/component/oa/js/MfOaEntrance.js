;
var OaEntrance = function (window, $) {
    /**
     * 在此处定义全局变量-各函数内公共使用的。
     * 函数作用域内的局部变量还是要通过var在函数内部声明。
     */
    var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。
    var path;	// 用于重置显示tips框的自动关闭时间。

    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        $("body").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });
        // bind event
        _bindClickEvent();


        $("#trans").find(".badge").remove();
        // 从后台异步获取每项的统计数量
        _getAllCountsAjax();

    };


    var _bindClickEvent = function () {
        $(".btn-app").bind("click", function (event) {
            if (!$(event.target).is("a")) {
                top.LoadingAnimate.start();
                switch ($(this).attr("id")) {
                    case "notice":
                        window.location.href = webPath + "/mfOaNotice/getListPage";
                        break;
                    case "leave":
                        window.location.href = webPath + "/mfOaLeave/getListPage";
                        break;
                    case "consumablemanage":
                        window.location.href = webPath + "/mfOaCons/getListPage";
                        break;
                    case "borrow":
                        window.location.href = webPath + "/mfOaDebtexpense/getListPage";
                        break;
                    case "consumable":
                        window.location.href = webPath + "/mfOaConsOperate/getAppListPage";
                        break;
                    case "schedule":
                        window.location.href = webPath + "/workCalendar/fullCalendarmonthlist";
                        break;
                    case "personnel":
                        window.location.href = webPath + "/mfOaArchivesBase/getListPage";
                        break;
                    case "trans":
                        window.location.href = webPath + "/mfOaTrans/input";
                        break;
                    case "hosting":
                        window.location.href = webPath + "/mfOaAccredit/getListPage";
                        break;
                    case "budget":
                        window.location.href = webPath + "/mfOaBudgetMst/getListPage";
                        break;
                    case "filecountersign":
                        window.location.href = webPath + "/mfOaFileCountersign/getListPage";
                        break;
                    case "chapter":
                        window.location.href = webPath + "/mfOaBadge/getListPage";
                        break;
                    case "chaptermanage":
                        window.location.href = webPath + "/mfOaBadge/getListPage2";
                        break;
                    case "openaccount":
                        window.location.href = webPath + "/mfOaOpening/getListPage";
                        break;
                    case "stiff":
                        window.location.href = webPath + "/mfOaBankNote/getListPage";
                        break;
                    case "transapply":
                        window.location.href = webPath + "/mfOaCounttrans/getListPage";
                        break;
                    case "reimbursement":
                        window.location.href = webPath + "/mfOaExpense/getListPage";
                        break;
                    case "communication":
                        window.location.href = webPath + "/mfOaInternalCommunication/getAcceptInfoListPage";
                        break;
                    case "entryManagement"://入职管理
                        window.location.href = webPath + "/mfOaEntryManagement/getListPage";
                        break;
                    case "becomeQualified"://转正申请
                        window.location.href = webPath + "/mfOaBecomeQualified/getListPage";
                        break;
                    case "adjustment"://调薪调岗
                        window.location.href = webPath + "/mfOaAdjustment/getListPage";
                        break;
                    case "dimission"://离职管理
                        window.location.href = webPath + "/mfOaDimission/getListPage";
                        break;
                    case "fullToPart"://全职转兼职管理
                        window.location.href = webPath + "/mfOaFullToPart/getListPage";
                        break;
                    case "trainingNeeds"://培训需求
                        window.location.href = webPath + "/mfOaTrainingNeeds/getListPage";
                        break;
                    case "addItem":
                        top.itemId = "";
                        top.flag = false;
                        top.openBigForm(webPath + "/mfQueryItem/getBaseOaItemList?funcType=2", "添加关注", function () {
                            if (top.flag) {
                                _addItemCallBack(top.itemId);
                            }
                        });
                        top.LoadingAnimate.stop();
                        break;
                    case "humanresources":
                        window.location.href = webPath + "/mfOaHumanResources/getListPage";
                        break;
                    case "mattersreported":
                        window.location.href = webPath + "/mfOaMattersReported/getListPage";
                        break;
                    case "lawyer":
                        window.location.href = webPath + "/mfOaLawyer/getListPage";
                        break;
                    case "blankTemplatePrint":
                        window.location.href = webPath+"/mfSysTemplate/toCreditTemplateList?templateType=13&tableId=tableBlankTemplatePrint";
                        break;
                    default:
                        _showTips(this);
                        break;
                }
            } else {//移除功能
                _deleteItem(this);
            }
        });

    }

    var _deleteItem = function (obj) {
        var itemId = $(obj).attr("id");
        alert(top.getMessage("CONFIRM_OPERATION", "移除关注"), 2, function () {
            top.LoadingAnimate.start();
            $.ajax({
                url: webPath + "/mfQueryItem/deleteAjax?funcType=2&itemId=" + itemId,
                datatype: "json",
                success: function (data) {
                    top.LoadingAnimate.stop();
                    $(obj).remove();
                }, error: function () {
                    top.LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION", "添加关注"), 0);
                }

            });
        });
    };


    var _addItemCallBack = function (itemId) {
        top.LoadingAnimate.start();
        $.ajax({
            url: webPath + "/mfQueryItem/insertAjax?funcType=2&itemId=" + encodeURI(itemId),
            datatype: "json",
            success: function (data) {
                top.LoadingAnimate.stop();
                if (data.flag == "success") {
                    top.LoadingAnimate.stop();
                    var len = data.mfQueryItemList.length;
                    var htmlStr = "";
                    $.each(data.mfQueryItemList, function (i, mfQueryItem) {
                        htmlStr = htmlStr + '<div class="btn btn-app" id="' + mfQueryItem.itemId + '">'
                            + '<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>'
                            + '<div><i class="i i-' + mfQueryItem.itemIcon + '"></i></div>'
                            + '<div>' + mfQueryItem.itemName + '</div>'
                            + '<a class="attention-link">移除</a>'
                            + '</div>';
                    });
                    htmlStr = htmlStr + '<div class="btn btn-app" id="addItem" title="添加关注">'
                        + '<div class="/margin_top_15"><i class="i i-jia1 color_theme"></i></div>'
                        + '</div>';
                    $(".box").html(htmlStr);
                    _bindClickEvent();
                    _getAllCountsAjax();
                }
            }, error: function () {
                top.LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", "添加关注"), 0);
            }
        });
    };

    var _getAllCountsAjax = function () {
        jQuery.ajax({
            url: webPath + "/mfOa/findCountAjax",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    for (var key in data.countMap) {
                        // 后台传过来的对象包含2个属性：id跟页面对应，count是具体的值。
                        $("#" + key).find(".badge").html(data.countMap[key]);
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
                alert(data.msg, 0);
            }
        });
    };

    var _showTips = function (obj) {
        top.LoadingAnimate.stop();
        var d = dialog({
            id: "oaInBuilding",
            content: "正在建设中，敬请期待。",
            padding: "3px"
        }).show(obj);
        if (tipsTimeoutId) {
            clearTimeout(tipsTimeoutId);
        }
        tipsTimeoutId = setTimeout(function () {
            d.close().remove();
        }, 1000);
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init
    };
}(window, jQuery);