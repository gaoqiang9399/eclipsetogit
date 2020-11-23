;
var lawsuitList = function(window, $) {
	
	var _init = function(){
		//处理暂无数据的情况
		if($('#content tbody tr').length == 0){
		    var thCount = $('#content thead th').length;
			$('#content tbody').append('<tr><td style="text-align: center;" colspan="'+thCount+'">暂无数据</td></tr>');
		}
	};
	
	var _getById = function(objArgs,url){
		top.lawsuitFlag = "detail";
		var obj = $(top.window.document).find("body");
		obj.find("#bigFormShowiframe").attr("src",url);
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById : _getById
	};
}(window, jQuery);