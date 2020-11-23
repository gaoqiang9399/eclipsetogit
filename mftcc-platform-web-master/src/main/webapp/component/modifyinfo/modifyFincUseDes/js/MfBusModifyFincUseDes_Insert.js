;
var MfBusModifyFincUseDes_Insert = function (window, $) {
    var _init = function () {

    };
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url: url,
                data: {
                    ajaxData: dataParam
                },
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error: function () {
                }
            });
        }

    };

    //选择项目后的回调函数
    function selectBus(slectAppId){
        $.ajax({
            url :webPath+"/mfBusApply/getBusDetailInfoAjax?appId="+slectAppId,
            type:'post',
            dataType:'json',
            success : function(dataMap) {
                if (dataMap) {
                    var mfBusApply = dataMap.mfBusApply;
                    $("input[name=appAmt]").val(mfBusApply.appAmt);
                    $("input[name=fincRate]").val(mfBusApply.fincRate);
                    $("input[name=cusNo]").val(mfBusApply.cusNo);
                    $("input[name=cusName]").val(mfBusApply.cusName);
                    $("input[name=fincRate]").val(mfBusApply.fincRate);
                    $("input[name=kindNo]").val(mfBusApply.kindNo);
                    $("input[name=kindName]").val(mfBusApply.kindName);
                    $("textarea[name=oldFincUseDes]").val(mfBusApply.fincUseDes);
                } else {
                    window.top.alert("获取原业务信息失败", 0);
                }
            },
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    var _getBusInfo = function (obj){
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        var element = $(obj).attr('name');
        var ajaxUrl =webPath+"/mfBusModifyFincUseDes/getApplyListAjax";//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:ajaxUrl,
            bgClass:"tranType1",
            title: "查看业务",//标题
            searchplaceholder: "融资申请号",//查询输入框的悬浮内容
            valueElem:"input[name=appId]",//真实值选择器
            labelShow:false,
            changeCallback:function(elem){//回调方法
               //BASE.removePlaceholder($("input[name="+element+"]"));
                var slectAppId = $("input[name=appId]").val();
                selectBus(slectAppId);
            },
            tablehead:{//列表显示列配置
                "appName":{"disName":"融资申请号","width":"35%"},
                "appAmt":{"disName":"融资金额(元)","align":"right","width":"20%","dataType":"money"},
                "busStage":{"disName":"业务阶段","width":"15%","align":"center"},
                "termShow":{"disName":"期限","align":"center","width":"10%"},
            },
            returnData:{//返回值配置
                disName:"appName",//显示值
                value:"appId"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    };


    var _myclose = function () {
        myclose_click();
    };
    return {
        init: _init,
        ajaxSave: _ajaxSave,
        getBusInfo: _getBusInfo,
        myclose: _myclose
    };
}(window, jQuery);
