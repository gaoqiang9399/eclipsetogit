;
var MfBusModifyFincUseDes_List = function (window, $) {
    var _init = function () {
        myCustomScrollbar({
            obj: "#content", //页面内容绑定的id
            url: webPath + "/mfBusModifyFincUseDes/findByPageAjax",//列表数据查询的url
            tableId: "tableModifyFincUseDesList",//列表数据查询的table编号
            tableType: "thirdTableTag", //table所需解析标签的种类
            pageSize: 30 //加载默认行数(不填为系统默认行数)
        });
    };

    //跳转至新增
    var _applyInsert = function () {
        top.openBigForm(webPath + "/mfBusModifyFincUseDes/input", "修改贷款用途", function () {
            updateTableData();
        });
    };

    var _getDetailPage = function (obj, url) {

        if (url.substr(0, 1) == "/") {
            url = webPath + url;
        } else {
            url = webPath + "/" + url;
        }
        top.window.openBigForm(url, '修改详情', function () {
        });
    };

    return {
        init: _init,
        applyInsert: _applyInsert,
        getDetailPage: _getDetailPage
    };
}(window, jQuery);