;
var mfCusApply_List = function(window, $) {
    var _init = function(){
        myCustomScrollbar({
            obj : "#content",//页面内容绑定的id
            url : webPath+"/mfCusCustomer/getcusInfoByTrenchPageAjax.action",//列表数据查询的url
            data:{cusNo:cusNo},
            tableId : "tablebuscusInfoList",//列表数据查询的table编号
            tableType : "thirdTableTag",//table所需解析标签的种类
            pageSize : 30,//加载默认行数(不填为系统默认行数)
            topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
            callback:function(){
                $("table").tableRcswitcher({
                    name:"groomflag",onText:"有效",offText:"无效"});
            }//方法执行完回调函数（取完数据做处理的时候）
        });
    };

    var _getDetailPage = function (obj,url){
        top.LoadingAnimate.start();
        window.location.href=webPath+url;
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        getDetailPage :_getDetailPage
    };
}(window, jQuery);