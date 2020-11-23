;
var MfOaOpeningList = function(window, $) {
	
	
	//新增
	var _insert = function(){
		//大型新增弹框
			top.window.openBigForm(webPath+'/mfOaOpening/input','开户申请',function(){
			updateTableData();//重新加载列表数据
		});
	}
	
	//详情弹框
	 var _getByIdThis = function(url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,'开户详情',function(){
			 updateTableData();
		});
	  };
	  
	  
	  //确认开户弹框
	  var _confirmOpen = function(url){
		 if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,'确认开户',function(){
			updateTableData();
		});
	  }
	  
	
	  
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		insert:_insert,
		getByIdThis:_getByIdThis,
		confirmOpen:_confirmOpen
	};
}(window, jQuery);
