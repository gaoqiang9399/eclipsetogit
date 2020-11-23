;
var accountBalanceList = function (window, $) {
    var _init = function () {
        myCustomScrollbar({
            obj: "#content",//页面内容绑定的id
            url: webPath + "/mfBusAccountBalQuery/accountBalanceListAjax?cusNo=" + cusNo,//列表数据查询的url
            tableId: "tableaccountBalanceList",//列表数据查询的table编号
            tableType: "tableTag",//table所需解析标签的种类
            pageSize: 30,//加载默认行数(不填为系统默认行数)
            topHeight: 100 //顶部区域的高度，用于计算列表区域高度。
        });
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init
    };
}(window, jQuery);