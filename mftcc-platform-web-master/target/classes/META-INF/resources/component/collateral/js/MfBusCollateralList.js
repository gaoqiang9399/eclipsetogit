;
var MfBusCollateralList = function(window, $) {
	var _init = function(){
        myCustomScrollbar({
            obj : "#content",//页面内容绑定的id
            url:webPath+"/pledgeBaseInfo/findByPageAjax?classFirstNo="+classFirstNo,//列表数据查询的url
            tableId : tableId,//列表数据查询的table编号
            tableType : "tableTag",//table所需解析标签的种类
            pageSize : 30,//加载默认行数(不填为系统默认行数)
            topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
        });
	};

	//押品新增
	var _pledgeInsert = function() {
        window.location.href=webPath+"/pledgeBaseInfo/inputInsertNew?classFirstNo="+classFirstNo+"&entranceType="+entranceType;
    };

    var _getDetailPage = function (obj, url) {
        top.LoadingAnimate.start();
        window.location.href = webPath + url;
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        pledgeInsert : _pledgeInsert,
        getDetailPage: _getDetailPage
    };
}(window, jQuery);