;
var MfOaAdjustment_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaAdjustment/findByPageAjax", //列表数据查询的url
				tableId : "tableadjustment0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(webPath+"/mfOaAdjustment/input","新增调薪调岗申请", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _adjustmentDetail = function(obj,url) {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url+"&entryFlag=1","调薪调岗详情", function(){
 			updateTableData();
 		});	
	};
	var _printApproveTable = function(obj,url){
		var oaAppId = url.split("?")[1].split("=")[1];
		var poCntJson = {
				filePath : "",
				fileName : fileName,
				oaAppId : oaAppId,
				saveBtn : "0",
				fileType : 0
			};
		mfPageOffice.openPageOffice(poCntJson);
	};
	return {
		init : _init,
		adjustmentDetail:_adjustmentDetail,
		applyInsert:_applyInsert,
		printApproveTable:_printApproveTable
	};
}(window, jQuery);
