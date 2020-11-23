;
var MfBusInvoicemanage_List = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusInvoicemanage/findByPageAjax?fincId=" + fincId, //列表数据查询的url
            tableId : "tableInvoiceManage", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
        });
        $(".footer_loader").css("display","none");

    };
    //跳转至借据页面
    var _applyInsert = function() {
        top.openBigForm(webPath+"/mfBusInvoicemanage/getFincListPage","新增发票", function(){
            updateTableData();
        });
    };

    //跳转至详情
    var _getDetail = function(obj,url) {
        top.openBigForm(webPath + url,"发票详情", function(){
            updateTableData();
        });
    };


    var _printApproveTable = function(fincId,invoiceId,cusNo,appId,pactId){
        // debugger;
        var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjaxWithoutWkf?templateNo=19101514090364510";
        var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
        var temParm = 'fincId=' + fincId + '&invoiceId=' + invoiceId+ '&cusNo=' + cusNo+ '&appId=' + appId+ '&pactId=' + pactId;
        $.ajax({
            url : url + "&" + temParm,
            data : {
                "returnUrl" : backUrl
            },
            type : 'post',
            dataType : 'json',
            beforeSend : function() {
                LoadingAnimate.start();
            },
            complete : function() {
                LoadingAnimate.stop();
            },
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },
            success : function(data) {
                // debugger;
                var poCntObj = $.parseJSON(data.poCnt);
                mfPageOffice.openPageOffice(poCntObj);
            }
        });
    };
    var _myclose = function(){
        myclose_click();
    };
    return {
        init : _init,
        applyInsert : _applyInsert,
        getDetail : _getDetail,
        printApproveTable: _printApproveTable,
        myclose: _myclose
    };
}(window, jQuery);
