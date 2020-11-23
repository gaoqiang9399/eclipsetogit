;
var MfSysKindConfig=function(window, $){
    var _init = function (kindNo) {
//		MfConfigNavLine.createKindConfigNavLine(configTypeList);
        $("span.btn.btn-config").eq(0).click();
        $("span.btn.btn-config").eq(0).addClass("active");
        $(".config-div").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true,
            }
        });
    };

    //根据类型后台获取相应的配置信息
    var _getKindConfigByType = function (obj, configType) {
        var htmlStr = "";
        $.ajax({
            url: webPath + '/mfSysKind/getConfigByKindNoAjax',
            data: {kindNo: kindNo, configType: configType},
            success: function (data) {
                if (data.flag == "success") {
                    var mfSysKind = data.mfSysKind;
                    $(".nav-title").text(mfSysKind.kindName + "--" + mfSysKind.busModelName);
                    setKindConfigHtml(data, configType);
                    $(".btn-config").removeClass("active");
                    $(obj).addClass("active");
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error: function () {
                alert(top.getMessage("ERROR_REQUEST_URL"), 0);
            }
        });
        return htmlStr;
    };


    //获取产品配置的html
    var setKindConfigHtml = function (data, configType) {
        if ("base" == configType) {//基础配置
            MfKindBaseConfig.init(data);
        } else if ("calc" == configType) {//核算配置
            MfKindCalcConfig.init(data);
        } else if ("intercept" == configType) {//风控模型配置
            MfKindInterceptConfig.init(data);
        } else if ("form" == configType) {//表单配置
            MfKindFormConfig.init(data);
        } else if ("doc" == configType) {//影像配置
            MfKindDocConfig.init(data);
        } else if ("flow" == configType) {//流程配置
            MfKindFlowConfig.init(data);
        } else if ("fee" == configType) {//费用配置
            MfKindFeeConfig.init(data);
        } else if ("template" == configType) {//模板配置
            MfKindTemplateConfig.init(data);
        }
    };

    //返回至产品配置首页
    var _backToKindConfigPage = function () {
        window.location.href = webPath + '/mfSysKind/toKindConfig';
    };

    //更新产品配置
    var _updateKindConfig = function (ajaxData, updateCallBack) {
        $.ajax({
            url: webPath + '/mfSysKind/updateKindCalcConfigAjax',
            type: 'post',
            data: {ajaxData: ajaxData},
            success: function (data) {
                if (data.flag = "success") {
                    if (updateCallBack !== undefined && typeof(updateCallBack) == "function") {
                        updateCallBack(data);
                    }
                }
            }, error: function () {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //节点定制
    var _addBusNode = function (busModel, configType, nodeNo, mainApprove) {
        top.itemId = "";
        top.flag = false;
        top.configType = configType;
        window.parent.openBigForm(webPath + '/mfKindNode/getKindNodeList?configType=' + configType + '&busModel=' + busModel + '&kindNo=' + kindNo + '&nodeNo=' + nodeNo + '&mainApprove=' + mainApprove, "选择业务节点", function () {
            if (top.flag) {
                //异步更新产品下设置的节点定制
                addBusNodeCallBack(top.itemId, kindNo, busModel, top.configType, nodeNo);

            }
        }, "790px", "450px");
    };
    //节点定制的回调处理
    var addBusNodeCallBack = function (itemIds, kindNo, busModel, configType, nodeNo) {
        $.ajax({
            url: webPath + '/mfKindNode/insertForPrdctAjax?configType=' + configType + '&kindNo=' + kindNo + '&busModel=' + busModel + "&nodeNo=" + encodeURI(itemIds) + '&nodeNoNew=' + nodeNo,
            success: function (data) {
                if (data.flag == "success") {
                    if (configType == "fee") {
                        MfKindFeeConfig.init(data);
                    } else if (configType == "doc") {
                        MfKindDocConfig.init(data);
                    } else if (configType == "template") {
                        MfKindTemplateConfig.init(data);
                    } else if (configType == "form") {

                    }
                } else {
                    window.top.alert(top.getMessage("FAILED_OPERATION", {"operation": '节点定制'}), 0);
                }
            }, error: function () {
                alert(top.getMessage("FAILED_OPERATION", {"operation": '节点定制'}), 0);
            }
        });
    };
    return {
        init: _init,
        addBusNode: _addBusNode,
        backToKindConfigPage: _backToKindConfigPage,
        getKindConfigByType: _getKindConfigByType,
        updateKindConfig: _updateKindConfig,
    };
}(window, jQuery);