;
var MfOaBecomeQualified_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaBecomeQualified/findByPageAjax", //列表数据查询的url
				tableId : "tablebecomequalified0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfOaBecomeQualified/input","新增转正申请", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _becomeQualifiedDetail = function(obj,url) {
		top.openBigForm(url+"&entryFlag=1","转正申请详情", function(){
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
		becomeQualifiedDetail:_becomeQualifiedDetail,
		applyInsert:_applyInsert,
		printApproveTable:_printApproveTable
	};
}(window, jQuery);
