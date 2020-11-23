;
var OaEntrance = function(window, $) {
	/**
	 * 在此处定义全局变量-各函数内公共使用的。
	 * 函数作用域内的局部变量还是要通过var在函数内部声明。
	 */
	var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。
	
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {

		// bind event
		$(".btn-app").bind("click", function(event){
			switch ($(this).attr("id")) {
			case "notice":
				window.location.href = webPath+"/mfOaNotice/getListPage";
				break;
			default:
				_showTips(this);
				break;
			}
		});
		
		// 从后台异步获取每项的统计数量
		_getAllCounts();
		
	};
	

	var _getAllCounts = function() {
		//演示效果，有功能支撑后从后台获取具体的值。
		setTimeout(function() {
			$(".badge").html("0");
		},1000);
		/**
		var url = "";
		$.get(url, function(counts) {
			for (var c in counts) {
				// 后台传过来的对象包含2个属性：id跟页面对应，count是具体的值。
				$("#" + c["id"]).find(".badge").html(c["count"]);
			}
		});
		*/
	};
	
	var _showTips = function (obj) {
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);