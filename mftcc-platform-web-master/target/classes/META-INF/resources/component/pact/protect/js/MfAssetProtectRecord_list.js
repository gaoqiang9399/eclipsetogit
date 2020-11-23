;
var MfAssetProtectRecord=function(window,$){
	var _init=function(){
		 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfAssetProtectRecord/findByPageAjax", //列表数据查询的url
				tableId : "tableassetprotect0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			   });
	};
	//新增资产信息
	var _addAssetProtectRecord=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfAssetProtectRecord/input','新增资产信息',function(){
			if(top.flag){
				_init();
			}
		});
	};
	var _getDetailPage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	var _assetProtectRecordDetail=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,'资产详情',function(){
			
		});
	};
	//资产变更
	var _assetChange=function(obj,url){
		var protectId=url.split("?")[1].split("=")[1];
		top.flag=false;
		top.window.openBigForm(webPath+'/mfAssetProtectRecord/inputAssetChange?protectId='+protectId,'资产变更',function(){
			if(top.flag){
				_init();
			}
		});
	}
	//资产处置
	var _assetHandle=function(obj,url){
		var protectId=url.split("?")[1].split("=")[1];
		top.flag=false;
		top.window.openBigForm(webPath+'/mfAssetHandleInfo/input?protectId='+protectId,'资产处置',function(){
			if(top.flag){
				_init();
			}
		});
	};
    var _chaFengAjax = function(obj, url) {
        top.window.openBigForm( webPath + url  ,'资产查封',function() {
            updateTableData();
        });
    }
    var _jieFengAjax = function(obj, url) {
        top.window.openBigForm( webPath + url  ,'资产解封',function() {
            updateTableData();
        });
    }
    var _diZhaiAjax = function(obj, url) {
        top.window.openBigForm( webPath + url  ,'资产抵债',function() {
            updateTableData();
        });
    }
	return{
		init:_init,
		addAssetProtectRecord:_addAssetProtectRecord,
		getDetailPage:_getDetailPage,
		assetHandle:_assetHandle,
		assetChange:_assetChange,
		assetProtectRecordDetail:_assetProtectRecordDetail,
        chaFengAjax:_chaFengAjax,
        jieFengAjax:_jieFengAjax,
        diZhaiAjax:_diZhaiAjax
	};
}(window,jQuery)