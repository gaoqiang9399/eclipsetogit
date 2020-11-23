;
var mfBusApplyList = function(window, $) {
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfBusApply/findTrenchApplyAjax",//列表数据查询的url
			data:{cusNo:cusNo},
			tableId : "tablecusandapply0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	};
	
	var _getDetailPage = function (obj,url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};

	var _ajaxInprocess = function (obj, ajaxUrl) {
		var contentForm = $(obj).parents(".content_table");
		var tableId = contentForm.find(".ls_list").attr("title");
		jQuery.ajax({
			url:ajaxUrl,
			data:{tableId : tableId,appId : appId},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},success : function(data) {
				if (data.flag == "success") {
					$.myAlert.Alert(top.getMessage("SUCCEED_INSERT_PROCESS"));
					if (data.tableData != undefined && data.tableData != null) {
						var tableHtml = $(data.tableData).find("tbody").html();
						contentForm.find(".ls_list tbody").html(tableHtml);
					}
				} else if (data.flag == "error") {
					if (alertFlag) {
						window.parent.window.$.myAlert.Alert(data.msg);
					} else {
						$.myAlert.Alert(data.msg);
					}
				}
			},error : function(data) {
				if (alertFlag) {
					window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				} else {
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getDetailPage :_getDetailPage,
		ajaxInprocess :_ajaxInprocess,
	};
}(window, jQuery);