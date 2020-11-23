;
var coAgency = function(window, $) {
	var _init = function () {
		
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfCusCustomer/findCoAgencyByPageAjax",//列表数据查询的url
	    	tableId:"tablecuscoagency0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
		    callback:function(){
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};
	var _getById = function (obj ,url){
		var resultObj = StringUtil.urlParamsToObj(url,"cusType");
		var title = "客户信息";
		var cusTypeObj = JSON.parse(cusTypeMap);
		title = cusTypeObj[resultObj.cusType];
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,title,function(){});
	};
	  
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById : _getById
	};
}(window, jQuery);