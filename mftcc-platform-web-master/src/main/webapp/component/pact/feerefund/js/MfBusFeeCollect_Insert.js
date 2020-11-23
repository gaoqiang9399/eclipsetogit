;
var MfBusFeeCollect_Insert = function(window, $) {

    var _init = function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
    };
    var _selectPact = function () {
        $("input[name='pactNo']").popupList({
            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusFeeCollect/getPactList",//请求数据URL
            title: "选择合同",//标题
            searchplaceholder: "合同编号/客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.appId);
            },
            tablehead:{//列表显示列配置
                "pactNo":"合同编号",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"pactNo"//真实值
            },
        });
        $("input[name='pactNo']").next().click();
    };

    var _getForm = function(appId){
        window.location.href = webPath +"/mfBusChargeFee/chargeFee?appId=" + appId+"&entranceNo=FEE_COLLECT_ALONE";
    }
    
    var _calcreguaranteeAmt = function () {
        var pactId = $("input[name='pactId']").val();
        var dateStr = $("input[name='receviedDate']").val();
        $.ajax({
            url : webPath+"/mfBusFeeCollect/calcReguaranteeAmt",
            data: {pactId: pactId,dateStr:dateStr},
            type: "POST",
            dataType: "json",
            success : function(data) {
                if (data.flag == "success") {
                    $("input[name='reguaranteeAmt']").parent().parent().prev().attr("title",data.taxTitle);
                    $("input[name='guaranteeAmt']").parent().parent().prev().attr("title",data.title);

                    $("input[name='reguaranteeAmt']").val(data.reguaranteeAmt);
                    $("input[name='guaranteeAmt']").val(data.guaranteeAmt);
                }
            },
            error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    }

    var _ajaxSave = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url: url,
                data: {ajaxData: dataParam},
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                    LoadingAnimate.start();
                }, success: function (data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        _myclose_reload();
                    } else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }, complete: function () {
                    LoadingAnimate.stop();
                }
            });
        }
    };

    var _myclose_reload  = function (){
        window.location.href=webPath+"/mfBusFeeCollect/getListPage";
    }

	return {
		init : _init,
        selectPact:_selectPact,
        ajaxSave:_ajaxSave,
        myclose_reload:_myclose_reload,
        getForm:_getForm,
        calcreguaranteeAmt:_calcreguaranteeAmt
    };
}(window, jQuery);
