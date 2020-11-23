var MfCusCredit_List = function (window, $) {
    var _init = function () {
        myCustomScrollbar({
            obj: "#content",//页面内容绑定的id
            url: webPath + "/mfStampCredit/findStampContract",//列表数据查询的url
            tableId: "tablestampcreditListBase",//列表数据查询的table编号
            tableType: "tableTag",//table所需解析标签的种类
            pageSize: 30//加载默认行数(不填为系统默认行数)
        });
    }
    var _input = function () {
        var showType = "1";
        top.openBigForm(webPath + "/mfCusCreditApply/creditInitInput?showType=" + showType, "授信申请", function () {
            _closeProject();
        });
    }
    var _inputIntention = function (creditType) {
        var url = webPath + "/mfCreditIntentionApply/inputIntention?creditType=" + creditType;
        var title = "";
        if (creditType == 1) {
            title = "意向申请";
        } else if (creditType == 2) {
            title = "临额申请";
        }
        top.openBigForm(url, title, function () {
            ;
        });
    }
    var _inputProject = function () {
        var showType = "1";
        top.openBigForm(webPath + "/mfCusCreditApply/projectCreditInitInput?showType=" + showType, "预立项申请", function () {
            _closeProject();
        });
        // window.top.dhccModalDialog.open( webPath+"/mfCusCreditApply/projectCreditInitInput?showType="+showType, '预立项申请', _closeProject(), "90", "90", "900", "500");
    }
    var _closeProject = function () {
        if (top.creditSave == "1") {
            top.creditSave = "0";
            window.location.href = webPath + "/mfCusCreditApply/getCusCreditView?&cusNo=" + top.cusNo + "&creditAppId=" + top.creditAppId + "&busEntrance=credit" + "&entrance=credit";

        }
    }

    var _transferCreditInitInput = function (obj, url) {
        if (url.substr(0, 1) == "/") {
            url = webPath + url;
        } else {
            url = webPath + "/" + url;
        }
        var oldCreditAppIdStr = url.substring(url.indexOf("?") + 1);
        var creditAppIdValueStr = oldCreditAppIdStr.substring(0, oldCreditAppIdStr.indexOf("&"));
        var creditAppIdValue = creditAppIdValueStr.substring(creditAppIdValueStr.indexOf("=") + 1);

        jQuery.ajax({
            url: webPath + "/mfCusCreditApply/transferCreditCheck",
            data: {
                oldCreditAppId: creditAppIdValue
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    window.location.href = url;
                } else {
                    window.top.alert(data.msg, 0);
                }
            },
            error: function (data) {
                window.top.alert(top.getMessage("ERROR_REQUEST_URL", webPath + "/mfCusCreditApply/transferCreditCheck"));
            }
        });
    };

    var _getDetailPage = function (obj, url) {
        top.LoadingAnimate.start();
        window.location.href = webPath + url;
    };
    var _getCusDetailPage = function (obj, url) {
        var cusNo = url.split("?")[1].split("&")[0].split("=")[1];
        var cusBaseType = url.split("?")[1].split("&")[1].split("=")[1];
        if (typeof (cusBaseType) != "undefined" && cusBaseType != null && cusBaseType != "") {
            switch (cusBaseType) {
                case "1"://企业客户
                    window.location.href = webPath + url;
                    break;
                case "2"://个人客户
                    window.location.href = webPath + url;
                    break;
                case "3"://核心企业
                    url = "/mfCusCoreCompany/getCoreCompanyView?coreCompanyUid=" + cusNo + "&busEntrance=cus_core_company&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "4"://渠道商
                    url = "/mfBusTrench/getTrenchView?cusNo=" + cusNo + "&busEntrance=cus_trench&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "5"://资金机构
                    url = "/mfBusAgencies/getAgenciesView?cusNo=" + cusNo + "&busEntrance=cus_agencies&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "6"://仓储机构
                    url = "/mfCusWarehouseOrg/getWarehouseOrgView?cusNo=" + cusNo + "&busEntrance=cus_warehouse_org&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "9"://担保公司
                    url = "/mfCusAssureCompany/assureCompanyView?cusNo=" + cusNo + "&busEntrance=cus_assure&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "A"://集团客户
                    url = "/mfCusGroup/cusGroupView?groupNo=" + cusNo + "&busEntrance=cus_group&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "B"://个人渠道
                    url = "/mfBusTrench/getTrenchView?cusNo=" + cusNo + "&busEntrance=cus_trench&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                case "C"://合作机构
                    url = "/mfCusCooperativeAgency/cooperativeAgencyView?orgaNo=" + cusNo + "&busEntrance=cus_coopAgency&baseType=" + cusBaseType;
                    window.location.href = webPath + url;
                    break;
                default:
                    alert("该客户类型暂不支持，开发中。。。", 3);
                    break;
            }

        } else {
            alert("客户类型不存在", 0);
        }
    };
    return {
        init: _init,
        input: _input,
        getDetailPage: _getDetailPage,
        transferCreditInitInput: _transferCreditInitInput,
        inputProject: _inputProject,
        inputIntention: _inputIntention,
        getCusDetailPage: _getCusDetailPage
    };
}(window, jQuery);