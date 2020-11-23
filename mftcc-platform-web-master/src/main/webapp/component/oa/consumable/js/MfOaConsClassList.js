;
var OaConsClassList = function(window, $) {
	
	var _init = function () {
		
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaConsClass/findByPageAjax",//列表数据查询的url
	    	tableId:"tableconsclass0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
		    callback:function(){
			}//方法执行完回调函数（取完数据做处理的时候）
	    });

		
		
		 _consClassInsert();
		 //_ajaxGetById(obj,url);
		// _ajaxFindNoticeLooking(obj,url);
		 
	};
	

   var _consClassInsert = function() {
		$("#consClassInsert").bind("click", function(event) {
			/* top.window.openBigForm(webPath+'/mfOaNotice/input','新增公告',_closeCallBack); */
			top.addFlag = false;
			top.openBigForm(webPath+"/mfOaConsClass/input", "新增类别", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	}; 
	
	
   
	  var _ajaxGetById = function (obj ,url){
		    top.addFlag = false;
			top.openBigForm(url,"低值易耗品信息", function() {
			myclose();
			//window.parent.openBigForm(url,"通知公告信息");
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