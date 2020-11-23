;
var MfOaEntryManagement_List = function(window, $) {
	var _init = function() {
		myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath+"/mfOaEntryManagement/findByPageAjax", // 列表数据查询的url
			tableId : "tableentrymanagement0001", // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30
		// 加载默认行数(不填为系统默认行数)
		});
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfOaEntryManagement/input","入职申请", function(){
 			updateTableData();
 		});	
	};
	var _ajaxGetById = function(obj, url) {
		var applySts = url.split("?")[1].split("&")[1].split("=")[1];
		if (applySts == "0") {
			
		} else {
			var entryManagementId = url.split("?")[1].split("&")[0].split("=")[1];
			url = webPath+"/mfOaEntryManagement/getEntryApplyInfo?entryManagementId=" + entryManagementId + "&entryFlag=1";
		}
		top.openBigForm(url,"入职申请", function(){
			updateTableData();
		});	
	};
	var _printApproveTable = function(obj,url){
		var oaAppId = url.split("?")[1].split("&")[0].split("=")[1];
		var oaBaseId = url.split("?")[1].split("&")[1].split("=")[1];
		var poCntJson = {
				filePath : "",
				fileName : fileName,
				oaAppId : oaAppId,
				oaBaseId : oaBaseId,
				saveBtn : "0",
				fileType : 0
			};
		mfPageOffice.openPageOffice(poCntJson);
	};
	return {
		init : _init,
		applyInsert:_applyInsert,
		ajaxGetById:_ajaxGetById,
		printApproveTable:_printApproveTable
	};
}(window, jQuery);
