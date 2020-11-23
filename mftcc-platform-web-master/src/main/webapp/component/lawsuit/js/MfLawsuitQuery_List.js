;
var lawsuitQueryList = function(window, $) {
	
	var _init = function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfLawsuit/findByPageQueryAjax",//列表数据查询的url
	    	tableId:"tablelawsuit0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	        topHeight:100,//顶部区域的高度，用于计算列表区域高度。
	        ownHeight : true
	    });
	};
	
	var _getById = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url;
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url+"&flag=show","案件详情", function(){});
	};
	var _input=function(){
		var url="/mfLawsuit/input1";
        top.openBigForm(url,"案件新增", function(){
        	 updateTableData();
        });
	}
	var _getDetailPage=function(obj,url){		
		top.LoadingAnimate.start();	
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		window.location.href=url;			
	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById : _getById,
		input : _input,
		getDetailPage:_getDetailPage
	};
}(window, jQuery);