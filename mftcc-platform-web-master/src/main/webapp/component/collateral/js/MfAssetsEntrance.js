;
var MfAssetsEntrance = function (window, $) {
    /**
     * 在此处定义全局变量-各函数内公共使用的。
     * 函数作用域内的局部变量还是要通过var在函数内部声明。
     */
    var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。

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

    };


    var _bindClickEvent = function () {
        $(".btn-app").bind("click", function (event) {
            if (!$(event.target).is("a")) {
                top.LoadingAnimate.start();
                switch ($(this).attr("id")) {
                    case "applyMoney"://请款申请
                         window.location.href = webPath + "/mfRequestPayoutBill/getListPage";
                         break;
                    case "assetsManage"://资产管理
                        window.location.href= webPath+"/mfBusCollateral/getListPage?entranceType=assets";
                        break;
                    case "assetsQuery"://资产查询
                        window.location.href = webPath + "/mfBusCollateral/getListPage?entranceType=assets&subEntrance=query";
                        break;
                    // case "assetsAccount"://资产台账
                    //     window.location.href = webPath + "/mfOaDebtexpense/getListPage";
                    //     break;
                    case "assetsProtect"://资产保全
                        window.location.href = webPath + "/mfAssetProtectRecord/getListPage";
                        break;
                    case "financingPlan"://金融方案
                        window.location.href = webPath + "/mfFinancingOptions/getListPage";
                        break;
                    case "salesPlan"://销售方案
                    	window.location.href = webPath + "/mfSalesOptions/getListPage";
                    	break;
                    case "assetsCategory"://资产类别
                        window.location.href = webPath + "/mfCollateralClass/getMfCollateralConfig?entranceType=assets&classFirstNo=A,B,C,D";
                        break;
                    case "assetsWarning"://资产预警
                        window.location.href = webPath + "/mfMsgPledge/getListPage";
                        break;
                    // 应收账款begin
                    case "accountManage"://应收账款管理
                        window.location.href= webPath+"/mfBusCollateral/getListPage?entranceType=account";
                        break;
                    case "accountQuery"://应收账款查询
                        window.location.href = webPath + "/mfBusCollateral/getListPage?entranceType=account&subEntrance=query";
                        break;
                    case "accountCategory"://应收账款类别
                        window.location.href = webPath + "/mfCollateralClass/getMfCollateralConfig?entranceType=account&classFirstNo=E";
                        break;
                    // case "accountWarning"://应收账款预警
                    //     window.location.href = webPath + "/mfMsgPledge/getListPage";
                    //     break;
                     // 应收账款end
                     // 租赁物begin
                    case "leaseManage"://租赁物管理
                        window.location.href= webPath+"/mfBusCollateral/getListPage?entranceType=lease";
                        break;
                    case "leaseQuery"://租赁物查询
                        window.location.href = webPath + "/mfBusCollateral/getListPage?entranceType=lease&subEntrance=query";
                        break;
                    case "leaseCategory"://租赁物类别
                        window.location.href = webPath + "/mfCollateralClass/getMfCollateralConfig?entranceType=lease&classFirstNo=F";
                        break;
                    case "assetsPledgeManage"://押品管理（客户押品）
                    	window.location.href = webPath+"/mfBusCollateral/getPledgeListPage";
                    	break;
                    case "assetsAssetsPreservation"://资产保全（涉诉相关）
                    	window.location.href = webPath + "/mfAssetsPreservation/getListPage";
                    	break;
                    case "assetsAssetsManagement"://资产管理（自有资产）
                    	window.location.href = webPath + "/mfAssetsManage/getListPage";
                    	break;
                    // case "leaseWarning"://租赁物预警
                    //     window.location.href = webPath + "/mfMsgPledge/getListPage";
                    //     break;
                    //租赁物end
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