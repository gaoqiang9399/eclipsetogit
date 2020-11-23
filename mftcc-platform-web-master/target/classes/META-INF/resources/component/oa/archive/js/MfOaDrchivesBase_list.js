;
var OaArchivesList = function (window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaArchivesBase/findByPageAjax",//列表数据查询的url
	    	tableId:"tablearchives00001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30,//加载默认行数(不填为系统默认行数)
	    	ownHeight : true,
	    });
	};
	
	var _ajaxGetById = function(obj ,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(encodeURI(url),"个人档案信息", function(){
 			updateTableData();
 		});	
	};
	var _applyInsert = function(obj){
		top.openBigForm(webPath+"/mfOaArchivesBase/input","新增基本信息", function(){
 			updateTableData();
 		});		
	};
	return {
		init:_init,
		ajaxGetById:_ajaxGetById,
		applyInsert:_applyInsert
	};
}(window,jQuery);