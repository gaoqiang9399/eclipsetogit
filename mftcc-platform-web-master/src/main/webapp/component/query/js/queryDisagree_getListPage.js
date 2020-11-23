var queryDisagree_getListPage = function(window, $) {
	var _init = function() {
		myCustomScrollbar({
			obj : "#content",// 页面内容绑定的id
			url : webPath+"/mfQueryDisagree/findDisagreeListByPage",// 列表数据查询的url
			tableId : "tablequeryDisagreelist",// 列表数据查询的table编号
			tableType : "thirdTableTag",// table所需解析标签的种类
			pageSize : 30,// 加载默认行数(不填为系统默认行数)
			topHeight : 100
		// 顶部区域的高度，用于计算列表区域高度。
		});
	};

	var _disagreeReconsider = function(url) {
		// url = QueryDisagreeActionAjax_disagreeReconsider.action;appId-appId;onClick-queryDisagree_getListPage.disagreeReconsider
		alert(top.getMessage("CONFIRM_OPERATION", "发起复议"), 2, function() {
			$.ajax({
				url : webPath+url,
				data : {},
				type : 'post',
				dataType : 'json',
				async : true,// true异步, false同步
				error : function() {
					alert("发起复议失败_25193836984664068:" + this.url, 0);
				},
				success : function(data) {
					if (data.flag == 'success') {// 发起复议成功
						_init();
					} else {
						alert(top.getMessage("FAILED_SAVE_CONTENT", { "content" : "发起复议", "reason" : "<br>" + data.msg }), 0);
					}
				}
			});
		});
	};

	// 否决复议
	var _getDetailPage = function(obj, url) {
		if(url.substr(0,1)=="/"){
			url = url; 
		}else{
			url = "/" + url;
		}
		top.LoadingAnimate.start();
		window.location.href = webPath+url;
	};

	return {
		init : _init,
		getDetailPage : _getDetailPage,
		disagreeReconsider : _disagreeReconsider
	};

}(window, jQuery);
