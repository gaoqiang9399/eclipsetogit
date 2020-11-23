;
var OaConsList = function(window, $) {
	
	var _init = function () {
		top.LoadingAnimate.start();
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaCons/findByPageAjax",//列表数据查询的url
	    	tableId:"tableconsumable0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	        ownHeight : true,
		    callback:function(){
		    	top.LoadingAnimate.stop();
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
		 _consumableInsert();
	};
   var _consumableInsert = function() {
		$("#consInsert").bind("click", function(event) {
			var url = webPath+"/mfOaCons/input";
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		});
	}; 
	  var _ajaxGetById = function (obj,msg,url){
		    top.addFlag = false;
			top.openBigForm(url,msg, function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
	  };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById : _ajaxGetById
	};
}(window, jQuery);