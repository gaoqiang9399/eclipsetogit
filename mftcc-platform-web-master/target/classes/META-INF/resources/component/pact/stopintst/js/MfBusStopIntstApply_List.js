var MfBusStopIntstApply_List = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj : "#content",
            url : webPath+"/mfBusStopIntstApply/findByPageAjax", //列表数据查询的url
            tableId : "tabletablestopintstlist", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30, //加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
        });
    };
    /**
     * 停息申请新增页面请求
     * @private
     */
    var _toApplyInsert = function(){
        top.openBigForm(webPath+"/mfBusStopIntstApply/insetintst","新增停息申请",function(){
            updateTableData();
        } );
    };

    var _stopinsts = function(obj,url){
        top.window.openBigForm(url,"停息结算确认",function () {
            updateTableData();
        } );
    };
    var _showDetail = function(obj,url){
        top.window.openBigForm(url,"停息结算",function () {
            
        } );
    };
    var _showCus = function(obj,url){
        top.addFlag = false;
        top.createShowDialog(url,"客户信息",'90','90',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    };
    var _showApp = function (obj,url) {
        top.addFlag = false;
        top.createShowDialog(url,"项目信息",'90','90',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    };
    return {
        init : _init,
        toApplyInsert: _toApplyInsert,
        stopinsts:_stopinsts,
        showCus:_showCus,
        showApp:_showApp,
        showDetail:_showDetail
    };
}(window, jQuery);