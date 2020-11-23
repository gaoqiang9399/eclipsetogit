;
var MfAssetsPreservation_List = function(window, $) {
	var _init = function() {
		 myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfAssetsPreservation/findByPageAjax", //列表数据查询的url
			tableId : "tableassetspreservationlist", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
	    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfAssetsPreservation/input","新增", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getById = function(url) {
		top.openBigForm(webPath + url,"押品详情", function(){
 			updateTableData();
 		});	
	};
    var _getLawsuitById = function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.openBigForm(url+"&flag=show","案件详情", function(){});
    };
	//查封,解封，抵债
	var _assetsPreservation = function(url){
		var involvementSts = url.split("?")[1].split("&")[1].split("=")[1];
		var showName = "查封";
		if(involvementSts == "3"){
			showName = "解封";
		}else if(involvementSts == "4"){
			showName = "抵债";
		}else if(involvementSts == "5"){
            showName = "续查封";
		}
		top.openBigForm(webPath + url,showName, function(){
 			updateTableData();
 		});	
	}
	
	return {
		init : _init,
		getById:_getById,
		applyInsert:_applyInsert,
		assetsPreservation:_assetsPreservation,
        getLawsuitById:_getLawsuitById
	};
}(window, jQuery);
