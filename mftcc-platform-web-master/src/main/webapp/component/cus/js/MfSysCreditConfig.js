;
var MfSysCreditConfig11 = function (window, $) {
    var _init = function () {
        $("span.btn.btn-config").eq(0).click();
        $("span.btn.btn-config").eq(0).addClass("active");
        $(".config-div").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true,
            }
        });
    };
    //根据类型后台获取相应的配置信息
    var _getCreditConfigByType = function(obj,configType){
        var htmlStr="";
        $.ajax({
            url:webPath+'/mfSysKind/getCreditConfigByKindNoAjax',
            data:{kindNo:creditId,configType:configType,creditModel:creditModel},
            success:function(data){
                if(data.flag == "success"){
                    var creditConfigData = data.creditConfigData;
                    $(".nav-title").text(creditConfigData.busModelName);
                    setKindConfigHtml(data,configType);
                    $(".btn-config").removeClass("active");
                    $(obj).addClass("active");
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL"),0);
            }
        });
        return htmlStr;
    };
    //获取产品配置的html
    var setKindConfigHtml = function(data,configType){
        if("base"==configType){//基础配置
            MfCreditBaseConfig.init(data);
        }else if("doc"==configType){//影像配置
            MfCreditDocConfig.init(data);
        }else if("flow"==configType){//流程配置
            MfCreditFlowConfig.init(data);
        }else if("template"==configType){//模板配置
            MfCreditTemplateConfig.init(data);
        }else if("timeResult"== configType){//失效配置
            MfBusCreditApplyTimeResult.init(data);
        }
    };
    //节点定制
    var _addBusNode = function(busModel, configType, nodeNo, mainApprove){
        // debugger;
        top.itemId="";
        top.flag= false;
        top.configType = configType;
        window.parent.openBigForm(webPath+'/mfKindNode/getCreditNodeList?configType='+configType+'&busModel='+busModel+'&kindNo='+kindNo+ '&nodeNo=' + nodeNo + '&mainApprove=' + mainApprove,"选择业务节点",function(){
            if(top.flag){
                //异步更新产品下设置的节点定制
                addBusNodeCallBack(top.itemId, kindNo, busModel, top.configType, nodeNo);

            }
        },"790px","450px");
    };
    //节点定制的回调处理
    var addBusNodeCallBack = function(itemIds, kindNo, busModel, configType, nodeNo){
        $.ajax({
            url : webPath+'/mfKindNode/insertForCreditAjax?configType='+configType+'&kindNo='+kindNo+'&busModel='+busModel+"&nodeNo="+encodeURI(itemIds) + '&nodeNoNew=' + nodeNo,
            success:function(data){
                if(data.flag == "success"){
                    if(configType=="fee"){
                        MfKindFeeConfig.init(data);
                    }else if(configType=="doc"){
                        MfCreditDocConfig.init(data);
                    }else if(configType=="template"){
                        MfCreditTemplateConfig.init(data);
                    }else if(configType=="form"){

                    }
                }else{
                    window.top.alert(top.getMessage("FAILED_OPERATION",{"operation":'节点定制'}),0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION",{"operation":'节点定制'}),0);
            }
        });
    };
    //返回至客户配置首页
    var _backToCusConfigPage = function(){
        window.location.href = webPath+'/mfCusFormConfig/getMfCusConfig';
    };
    //更新授信配置
    var _updateCreditConfig = function (ajaxData, updateCallBack) {
        $.ajax({
            url: webPath + '/mfCusCreditConfig/updateCreditConfigAjax',
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
    return{
        init:_init,
        getCreditConfigByType:_getCreditConfigByType,
        addBusNode:_addBusNode,
        backToCusConfigPage:_backToCusConfigPage,
        updateCreditConfig:_updateCreditConfig
    }
}(window, jQuery);