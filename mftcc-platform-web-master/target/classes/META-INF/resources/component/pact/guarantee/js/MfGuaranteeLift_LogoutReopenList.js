;
var MfGuaranteeLift_LogoutReopenList = function(window, $) {
	var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfGuaranteeLift/findLogoutReopenByPageAjax", //列表数据查询的url
            tableId : "tableguaranteeLiftLogoutReopen", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 ,//加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			callback:function(){
			}
        });
	};

	//跳转至续保申请
	var _apply = function(obj,url) {
        window.location.href = webPath + url;
	};
//跳转至详情
    var _getDetailPage = function(obj,url) {
        top.openBigForm(webPath + url,"详情", function(){
        });
    };
	return {
		init : _init,
        apply:_apply,
        getDetailPage:_getDetailPage,
	};
}(window, jQuery);