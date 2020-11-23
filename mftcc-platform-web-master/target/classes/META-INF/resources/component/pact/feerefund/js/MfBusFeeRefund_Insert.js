;
var MfBusFeeRefund_Insert = function(window, $) {

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
            ajaxUrl:webPath+"/mfBusFeeRefund/getPactList",//请求数据URL
            title: "选择合同",//标题
            searchplaceholder: "合同编号/客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.pactId);
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

    var _getForm = function(pactId){
        $.ajax({
            url : webPath+"/mfBusFeeRefund/getFeeRefundApply?pactId=" + pactId,
            success : function(data) {
                if (data.flag == "success") {
                    var html = data.htmlStr;
                    $("#MfBusFeeRefundForm").empty().html(html);
                    dataDocParm={
                        relNo:data.relNo,
                        docType:data.nodeNo,
                        docTypeName:"调查资料",
                        docSplitName:"调查资料",
                        query:query,
                    };
                    $("#fileInput").load("/component/doc/webUpload/uploadutil.jsp");
                    // $("#fileInput").append("");
                    // _changeType();
                    _bindBillManage(data.pactId);
                }
            },
            error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });

    }

    var _changeType = function () {
        var appAmtTmp = $("input[name='appAmtTmp']").val();
        var reasion = $("select[name='reasion']  option:selected").val();
        if(reasion == '1'){
            $("input[name='appAmt']").val(appAmtTmp);
        }else{
            $("input[name='appAmt']").val(0.00);
        }
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
        window.location.href=webPath+"/mfBusFeeRefund/getListPage";
    }

    var _bindBillManage = function(pactId){
        $("input[name='invoiceNo']").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/cwBillManage/getBillManageAccountByPactId?pactId=" + pactId,// 请求数据URL
            valueElem:"input[name='invoiceNo']",
            title: "选择发票",//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name='invoiceNo']"));
                var sltVal = elem.data("selectData");
                $("input[name='invoiceNo']").val(sltVal.account);
            },
            tablehead:{//列表显示列配置
                "account" : "发票号",
                "actualGuaranteeAmt" : "发票金额(元)",
                "regTime" : "登记日期"
            },
            returnData:{//返回值配置
                disName:"account",//显示值
            }
        });
        // $("input[name='invoiceNo']").next().click();
    };

	return {
		init : _init,
        selectPact:_selectPact,
        ajaxSave:_ajaxSave,
        myclose_reload:_myclose_reload,
        getForm:_getForm,
        changeType:_changeType,
        bindBillManage:_bindBillManage
    };
}(window, jQuery);
