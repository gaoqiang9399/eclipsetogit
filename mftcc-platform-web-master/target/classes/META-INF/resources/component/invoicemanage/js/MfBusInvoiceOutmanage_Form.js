;
var MfBusInvoiceOutmanage_Form = function(window, $) {

    var _init = function(){
        fieldUpdateList();
    };


    //点击取消，返回列表页面
    var _cancelInsert=function(){
        //window.location.href=webPath+"/mfBusInvoicemanage/getFincListPage";
        myclose_click();
    };
    //客户列表
    var _selectLegalPerson = function () {
        $("input[name=cusName]").parent().find('div').remove();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusInvoicemanage/selectCusInfo",//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                _selectCusBack2(tmpData);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "idNum": "证件号码"
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    };



//项目列表
    var _selectLegalpro = function () {
        // debugger;
        var cusNo =$("input[name=cusNo]").val();
        $("input[name=invoiceFincname]").parent().find('div').remove();
        $('input[name=invoiceFincname]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusInvoicemanage/selectCusPro?cusNo="+cusNo,//请求数据URL
            valueElem: "input[name=appId]",//真实值选择器
            title: "选择客户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                _selectCusBack1(tmpData);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "appName": "项目名称"
            },
            returnData: {//返回值配置
                disName: "appName",//显示值
                value: "appId"//真实值
            }
        });
        $("input[name=invoiceFincname]").parent().find(".pops-value").click();
        fieldUpdateList(cusNo);
    };

    //选择项目回调
    var _selectCusBack1 = function (cus) {
        $("input[name=idNum]").val(cus.ext1);
    };
    //选择地址回调
    var _selectCusBack2 = function (cus) {
        $("input[name=commAddress]").val(cus.commAddress);
    };

    //计算现金资产合计
    var _cashTotalAmt=function(){
        //现值金额合计
        var invoiceMoney = $("input[name=invoiceMoney]").val();//金额
        var invoiceTax = $("input[name=invoiceTax]").val();//税率
        var invoiceTaxprice=CalcUtil.multiply(invoiceMoney,invoiceTax);
        var invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
        //赋值
        $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
        _assetsTotal();
    }

    //展示发票列表信息
    function fieldUpdateList(cusNo){
        // debugger;
        $.ajax({
            url:"/mfBusInvoicemanage/cusBusinessInfo?cusNo="+cusNo,
            success:function(data){
                var html = data.htmlStr;
                $("#tableInvoiceManage").empty().html(html);
            }
        });
    };
    var _getDetail = function() {
        // debugger;
        var cusNo =$("input[name=cusNo]").val();
        $("input[name=invoiceTermnum]").parent().find('div').remove();
        $('input[name=invoiceTermnum]').popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusInvoicemanage/cusBusinessInfoppp?cusNo="+cusNo,//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            tablehead: {//列表显示列配置
                "appName": "客户名称",
                "pactId": "证件号码"
            },
            returnData: {//返回值配置
                disName: "appName",//显示值
                value: "pactId"//真实值
            }
        });
        $("input[name=invoiceTermnum]").parent().find(".pops-value").click();
    };
    var _ajaxSave = function(obj){
        // debugger;
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data : {
                    ajaxData:dataParam
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        top.updateflag = true;
                        myclose_click();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                }
            });
        }
    };




    //跳转至详情
    var _getFormOut = function(obj,url) {
        top.openBigForm(webPath + url,"发票作废申请", function(){
            updateTableData();
        });
    };
    //跳转至还款历史详情
    var _getNumberDetail = function(obj,url) {
        // debugger;
        top.openBigForm(webPath + url, function(){
            updateTableData();
        });
    };
    //跳转至操作详情
    var _getFormOutCz = function(obj,url) {
        top.openBigForm(webPath + url,"发票详情", function(){
            updateTableData();
        });
    };


    return {
        init:_init,
        cancelInsert:_cancelInsert,
        selectLegalPerson: _selectLegalPerson,
        selectLegalpro: _selectLegalpro,
        cashTotalAmt:_cashTotalAmt,
        getDetail:_getDetail,
        ajaxSave :_ajaxSave,
        getFormOut:_getFormOut,
        getFormOutCz:_getFormOutCz,
        getNumberDetail:_getNumberDetail
    };
}(window, jQuery);