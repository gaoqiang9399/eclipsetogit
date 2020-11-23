;
var MfOaCounttransList = function(window, $) {
	
	var _init = function () {
		top.LoadingAnimate.start();
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaCounttrans/findByPageAjax",//列表数据查询的url
	    	tableId:"tablechangemoney0001",//列表数据查询的table编号
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
			/*
			var url = webPath+"/mfOaCons/input";
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
			*/
			top.window.openBigForm(webPath+'/mfOaCounttrans/input','新增转款',function(){
				// _init();
				updateTableData();//重新加载列表数据
			});
		});
	};
	  var _ajaxGetById = function (obj,msg,url){
		    top.addFlag = false;
		    if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url,msg, function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
	  };
	  
	  var _getByIdThis = function(url){
		  if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
		  top.window.openBigForm(url,'转款详情',function(){
				initPage();
			});
	  };
	  
	  var _openChangePage = function(url){
		  if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
		  top.window.openBigForm(url,'确认转款',function(){
			  updateTableData();//重新加载列表数据
		  });
	  };
	  
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById : _ajaxGetById,
		getByIdThis : _getByIdThis,
		openChangePage : _openChangePage
	};
}(window, jQuery);