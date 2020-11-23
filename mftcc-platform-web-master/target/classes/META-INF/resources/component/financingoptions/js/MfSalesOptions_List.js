;
var MfSalesOptions_List = function(window, $) {
	var _init = function() {
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfSalesOptions/findByPageAjax", //列表数据查询的url
			tableId : "tablesalesoptionslist", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfSalesOptions/input","新增销售方案", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getById = function(url) {
		top.openBigForm(webPath + url,"销售方案详情", function(){
 			updateTableData();
 		});	
	};
	//删除销售方案
	var _deleteSales = function(url) {
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			$.ajax({
				url : webPath + url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						updateTableData();
					}else{
						alert("删除失败");
					}
				},
				error : function() {
					alert("删除失败");
				}
			});
		});
	};
	
	return {
		init : _init,
		getById:_getById,
		applyInsert:_applyInsert,
		deleteSales:_deleteSales
	};
}(window, jQuery);
