;
var MfBusInvoicemanageFinc_List = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusInvoicemanage/findFincByPageAjax", //列表数据查询的url
            tableId : "tableinvoiceFinc", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
        });

    };
    //跳转至发票新增页
    var _invoiceInsert = function(obj,url) {
        top.openBigForm(webPath + url ,"新增发票", function(){
            updateTableData();
        });
    };
    var _printApproveTable = function(obj,url){
        var oaAppId = url.split("?")[1].split("=")[1];
        var poCntJson = {
            filePath : "",
            fileName : fileName,
            oaAppId : oaAppId,
            saveBtn : "0",
            fileType : 0
        };
        mfPageOffice.openPageOffice(poCntJson);
    };

    //跳转至详情
    var _getDetail = function(obj,url) {
        top.openBigForm(webPath + url,"发票详情", function(){
            updateTableData();
        });
    };

    //获取表单详情

    var _getForm= function () {
       // window.location.href=webPath+"/mfBusInvoicemanage/getForm";
        window.parent.openBigForm(webPath+"/mfBusInvoicemanage/getForm","发票申请", function() {
            updateTableData();
        });
    }
    //点击取消，返回列表页面
    var _cancelInsert=function(){
        window.location.href=webPath+"/mfBusInvoicemanage/getListPage";
    };
    //点击下载
    var _getDownload=function(){
        window.location.href=webPath+"/mfBusInvoicemanage/getDownload";
    };
    return {
        init: _init,
        invoiceInsert: _invoiceInsert,
        getDetail: _getDetail,
        cancelInsert:_cancelInsert,
        getForm :_getForm,
        getDownload:_getDownload
    };
}(window, jQuery);