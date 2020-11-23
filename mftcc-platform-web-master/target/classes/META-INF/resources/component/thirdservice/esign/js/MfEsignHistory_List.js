;
var MfEsignHistory_List = function(window, $) {
    var _init = function(){
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfEsignHistory/findByPageAjax?cusNo="+cusNo + "&appId=" + appId , //列表数据查询的url
            tableId : "tableEsignHistoryList", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
    }
	var _getByIdAjax = function (url){
		top.window.openBigForm(url,"查看文档",function(){});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
	    init:_init,
        getByIdAjax:_getByIdAjax,
	};
}(window, jQuery);