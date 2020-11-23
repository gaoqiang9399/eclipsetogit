;
var MfBusInvoicemanage_Form = function(window, $) {
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
        if(cusNo==""){
            top.alert("请先选择客户",2);
        }else{
            $("input[name=fincShowId]").parent().find('div').remove();
            $('input[name=fincShowId]').popupList({
                searchOn: true, //启用搜索
                multiple: false, //false单选，true多选，默认多选
                ajaxUrl: webPath + "/mfBusInvoicemanage/selectCusPro?cusNo="+cusNo,//请求数据URL
                valueElem: "input[name=fincId]",//真实值选择器
                title: "选择借据号",//标题
                changeCallback: function (elem) {//回调方法
                    var tmpData = elem.data("selectData");
                    _selectCusBack1(tmpData);
                    _refreshGoodsDetailList(tmpData.fincId,cusNo);
                },
                tablehead: {//列表显示列配置
                    "cusName": "客户名称",
                    "fincShowId": "借据号"
                },
                returnData: {//返回值配置
                    disName: "fincShowId",//显示值
                    value: "fincId"//真实值
                }
            });
            $("input[name=fincShowId]").parent().find(".pops-value").click();
        }
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
        invoiceMoney=invoiceMoney.replace(/,/g,'');
        var invoiceTaxprice=CalcUtil.multiply(invoiceMoney,invoiceTax);
        var invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
        //赋值
        $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
        var fincId =$("input[name=fincId]").val();
        MfBusInvoicemanage_Form.checkAllGoodsOneDetail();
    }

    //展示发票列表信息
    function fieldUpdateList(cusNo){
        $.ajax({
            url:"/mfBusInvoicemanage/cusBusinessInfo?cusNo="+cusNo,
            success:function(data){
                var html = data.htmlStr;
                $("#tableInvoiceManage").empty().html(html);
            }
        });
    };
    var _getDetail = function() {
        var fincId =$("input[name=fincId]").val();
        _refreshGoodsDetailList(fincId);

       /* var cusNo =$("input[name=cusNo]").val();
        $("input[name=invoiceTermnum]").parent().find('div').remove();
        $('input[name=invoiceTermnum]').popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusInvoicemanage/cusBusinessInfoppp?cusNo="+cusNo,//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            tablehead: {//列表显示列配置
                "appName": "客户名称",
                "cusNo": "证件号码"
            },
            returnData: {//返回值配置
                disName: "appName",//显示值
                value: "cusNo"//真实值
            }
        });
        $("input[name=invoiceTermnum]").parent().find(".pops-value").click();*/
    };
    var _ajaxSave = function(obj){
        var price= $("input[name=invoiceMoney]").val();
        if(price==0){
             top.alert("请选择开票列表",2);
        }else {
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                $.ajax({
                    url : url,
                    data : {
                        ajaxData:dataParam,
                        invoiceIdOne:invoiceId
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        if (data.flag == "success") {
                            window.top.alert(data.msg, 3);
                            top.updateflag = true;
                            myclose_click();
                            _cancelInsert();
                        } else {
                            alert(data.msg, 0);
                        }
                    },
                    error : function() {
                    }
                });
            }

        }


    };
    var _refreshGoodsDetailList=function(fincId,cusNo){
        jQuery.ajax({
            url : webPath+"/mfBusInvoicemanage/getRepayHistory",
            type : "POST",
            dataType : "json",
            data:{tableId:"tableInvoiceManagerRepayPlan",fincId:fincId,cusNo:cusNo,isDealing:"1",pledgeBillSts:"0"},//tabledlpledgebaseinfobill0006
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    haveGoodsFlag="1";
                    var totalPriceAll =0.00;
                    var moneyAll=0.00;
                    var invoiceTermnum=""
                    $("#goodsDetailList").html(data.tableData);
                    if($('.table_content #tab').find($('input[type=checkbox]'))>0){
                    }
                    $("#goodsDetailListdiv").show();
                    $("th[name=number]").html('<a href="javascript:void(0);" onclick="MfBusInvoicemanage_Form.checkAllGoodsDetail()">全选</a>');
                    $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
                        $(this).bind("click",function(){
                            MfBusInvoicemanage_Form.checkAllGoodsOneDetail();

                        });
                    });
                }else{
                    top.alert("暂没有可以开启审批的发票",2);
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };

    var _checkAllGoodsOneDetail=function(){

        var totalPriceAll =0.00;
        var moneyAll=0.00;
        var invoiceTermnum="";
        var invoiceTax,invoiceTaxprice,invoiceTaxpriceNow;
        //var invoiceTax=0.00;
        $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
            if($(this).prop("checked")){
                // debugger;
                var val=this.value;
                var totalPriceShow=val.split("&")[0];
                var totalPrice=totalPriceShow.split("=")[1];
                var repayIdLsShow=val.split("&")[1];
                var repayIdLs=repayIdLsShow.split("=")[1];
                totalPriceAll=CalcUtil.add(totalPrice,totalPriceAll);
                invoiceTermnum=repayIdLs+"-"+invoiceTermnum;
                totalPriceAll=CalcUtil.formatMoney(totalPriceAll,2);
                $("input[name=invoiceMoney]").val(totalPriceAll);
                invoiceTax = $("input[name=invoiceTax]").val();//税率
                totalPriceAll=totalPriceAll.replace(/,/g,'');
                invoiceTaxprice=CalcUtil.multiply(totalPriceAll,invoiceTax);
                invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
                //赋值
                invoiceTaxpriceNow=CalcUtil.formatMoney(invoiceTaxpriceNow,2);
                $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
                var fincId =$("input[name=fincId]").val();
                invoiceTaxprice=CalcUtil.formatMoney(invoiceTaxprice,2);
                invoiceTaxprice=$("input[name=invoiceTaxprice]").val();
                invoiceTaxprice=invoiceTaxprice.replace(/,/g,'');
                moneyAll=CalcUtil.add(totalPriceAll,invoiceTaxprice);
                moneyAll=CalcUtil.formatMoney(moneyAll,2);
                $("input[name=invoiceTotaltaxprice]").val(moneyAll);
                $("input[name=invoiceTermnum]").val(invoiceTermnum);

            }else{
                $("input[name=invoiceMoney]").val(totalPriceAll);
                invoiceTax = $("input[name=invoiceTax]").val();//税率
                invoiceTaxprice=CalcUtil.multiply(totalPriceAll,invoiceTax);
                invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
                //赋值
                invoiceTaxpriceNow=CalcUtil.formatMoney(invoiceTaxpriceNow,2);
                $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
                //var fincId =$("input[name=fincId]").val();
                invoiceTaxprice=CalcUtil.formatMoney(invoiceTaxprice,2);
                invoiceTaxprice=$("input[name=invoiceTaxprice]").val();
                invoiceTaxprice=invoiceTaxprice.replace(/,/g,'');
                moneyAll=CalcUtil.add(totalPriceAll,invoiceTaxprice);
                moneyAll=CalcUtil.formatMoney(moneyAll,2);
                $("input[name=invoiceTotaltaxprice]").val(moneyAll);
                $("input[name=invoiceTermnum]").val(invoiceTermnum);
            }
        });

    };



    var _checkAllGoodsDetail=function(){
        var totalPriceAll =0.00;
        var moneyAll=0.00;
        var invoiceTermnum="";
        var invoiceTax,invoiceTaxprice,invoiceTaxpriceNow;
        $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
            if(($(this).prop("checked"))!=true){
                $(this).prop("checked",true);
                var val=this.value;
                var totalPriceShow=val.split("&")[0];
                var totalPrice=totalPriceShow.split("=")[1];
                var repayIdLsShow=val.split("&")[1];
                var repayIdLs=repayIdLsShow.split("=")[1];
                totalPriceAll=CalcUtil.add(totalPrice,totalPriceAll);
                invoiceTermnum=repayIdLs+"-"+invoiceTermnum;
                totalPriceAll=CalcUtil.formatMoney(totalPriceAll,2);

                $("input[name=invoiceMoney]").val(totalPriceAll);
                totalPriceAll=totalPriceAll.replace(/,/g,'');
                invoiceTax = $("input[name=invoiceTax]").val();//税率
                invoiceTaxprice=CalcUtil.multiply(totalPriceAll,invoiceTax);
                invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
                //赋值
                invoiceTaxpriceNow=CalcUtil.formatMoney(invoiceTaxpriceNow,2);
                $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
                var fincId =$("input[name=fincId]").val();
                invoiceTaxprice=CalcUtil.formatMoney(invoiceTaxprice,2);
                invoiceTaxprice=$("input[name=invoiceTaxprice]").val();
                invoiceTaxprice=invoiceTaxprice.replace(/,/g,'');
                moneyAll=CalcUtil.add(totalPriceAll,invoiceTaxprice);
                moneyAll=CalcUtil.formatMoney(moneyAll,2);
                $("input[name=invoiceTotaltaxprice]").val(moneyAll);
                $("input[name=invoiceTermnum]").val(invoiceTermnum);
            }else{
                $(this).prop("checked",false);
                $("input[name=invoiceMoney]").val(totalPriceAll);
                invoiceTax = $("input[name=invoiceTax]").val();//税率
                invoiceTaxprice=CalcUtil.multiply(totalPriceAll,invoiceTax);
                invoiceTaxpriceNow=CalcUtil.divide(invoiceTaxprice,100);
                //赋值
                invoiceTaxpriceNow=CalcUtil.formatMoney(invoiceTaxpriceNow,2);
                $("input[name=invoiceTaxprice]").val(invoiceTaxpriceNow);
                //var fincId =$("input[name=fincId]").val();
                invoiceTaxprice=CalcUtil.formatMoney(invoiceTaxprice,2);
                invoiceTaxprice=$("input[name=invoiceTaxprice]").val();
                invoiceTaxprice=invoiceTaxprice.replace(/,/g,'');
                moneyAll=CalcUtil.add(totalPriceAll,invoiceTaxprice);
                moneyAll=CalcUtil.formatMoney(moneyAll,2);
                $("input[name=invoiceTotaltaxprice]").val(moneyAll);
                $("input[name=invoiceTermnum]").val(invoiceTermnum);

            }
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
        refreshGoodsDetailList:_refreshGoodsDetailList,
        checkAllGoodsDetail:_checkAllGoodsDetail,
        checkAllGoodsOneDetail:_checkAllGoodsOneDetail


};
}(window, jQuery);