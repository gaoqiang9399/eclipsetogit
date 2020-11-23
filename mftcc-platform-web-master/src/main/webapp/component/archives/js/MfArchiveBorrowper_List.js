;
var MfArchiveBorrowper_List = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj : "#content",
            url : webPath+"/mfArchiveBorrow/findByPageAjax", //列表数据查询的url
            tableId : "tablemfarchiveborrowlist", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30, //加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
        });
    };
    /**
     * 借阅申请新增页面请求
     * @private
     */
    var _toApplyInsert = function(){
        top.window.openBigForm(webPath+"/mfArchiveBorrow/input","借阅申请" );
    };
    var _toRegister = function(){
        top.window.openBigForm(webPath+"/mfArchiveBorrow/register","借阅登记" );
    };
    return {
        init : _init,
        toApplyInsert: _toApplyInsert,
        toRegister:_toRegister
    };
}(window, jQuery);
