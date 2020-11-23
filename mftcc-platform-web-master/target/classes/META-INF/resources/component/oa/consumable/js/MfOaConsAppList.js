;
var OaConsAppList = function(window, $) {
	
	var _init = function () {
		
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaConsOperate/findAppByPageAjax",//列表数据查询的url
	    	tableId:"tableconsapp0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	        ownHeight : true,
		    callback:function(){
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
		 _consAppInsert();
		 
	};
   var _consAppInsert = function() {
		$("#consAppInsert").bind("click", function(event) {
			top.addFlag = false;
			top.openBigForm(webPath+"/mfOaCons/inputApp", "新增申领", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	}; 
	
var _ajaxGetById = function (obj ,url){
    top.addFlag = false;
	top.openBigForm(url,"低值易申领详情", function() {
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