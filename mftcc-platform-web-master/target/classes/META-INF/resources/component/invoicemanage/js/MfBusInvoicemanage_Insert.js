;
var MfBusInvoicemanage_Insert = function(window, $) {
    var _init = function(){
        // 加载列表数据
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url : webPath + "/mfBusInvoicemanage/findInvoiceByPageAjax?fincId=" + fincId,//列表数据查询的url
            tableId:"tableInvoiceManagerRepayPlan",//列表数据查询的table编号
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
            callback:function(){
            }//方法执行完回调函数（取完数据做处理的时候）
        });
    };

    //保存发票
    var _invoiceSave = function () {
        var path = webPath + "/mfBusInvoicemanage/saveInvoiceByPageAjax";
        var invoiceType = $("#invoiceType option:selected").val();
        var dataList = {
            fincId:fincId,
            appId:appId,
            invoiceAmount:invoiceAmount,
            invoiceTerm:invoiceTerm,
            invoiceType:invoiceType
        };
        if (invoiceAmount == 0 || invoiceTerm == ""){
            alert("请选择期号~");
        }else{
            $.post(path,dataList,function (data) {
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    myclose_click();
                    /*if (data.opNum == "0000"){
                        myclose_click();
                       /window.location.href= webPath + "/mfBusInvoicemanage/getListPage";
                    }else {
                        window.location.href= webPath + "/mfBusInvoicemanage/getListPage";
                    }*/
                    updateTableData();
                } else {
                    alert(data.msg, 0);
                }
            });
        }
    };

    var _myclose = function(){
        myclose_click();
    };
    return {
        init : _init,
        myclose:_myclose,
        invoiceSave : _invoiceSave
    };
}(window, jQuery);
