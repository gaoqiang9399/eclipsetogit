;
var MfHandAmtManage_List = function(window, $) {
	var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfHandAmtManage/findByPageAjax", //列表数据查询的url
            tableId : "tableHandAmtManagelist", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfHandAmtManage/initBusInfo","新增手续费管理", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _detail = function(obj,url) {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url+"&entryFlag=1","手续费管理详情", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getDetailPage = function(obj,url) {
		top.openBigForm(webPath + url,"详情", function(){
 		});
	};
	return {
		init : _init,
        detail:_detail,
        getDetailPage:_getDetailPage,
        applyInsert:_applyInsert,
	};
}(window, jQuery);
