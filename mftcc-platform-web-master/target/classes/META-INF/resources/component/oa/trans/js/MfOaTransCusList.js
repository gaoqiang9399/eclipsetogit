;
var OaTransCusList = function(window, $) {
	var _init = function () {	
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfOaTrans/getCustomerListAjax?cusMngNo="+cusMngNo,//列表数据查询的url
			tableId : "tablecustrans00001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
		
	};
	var _selectCusInfo = function (){	
		var cusTransNo="";
		var cusTransName="";
			if($("#content input:checkbox[name='cusNo']:checked").length>0){
			$("#content input:checkbox[name='cusNo']:checked").each(function(){
				var cusInfo = $(this).val();
				var cusObj = StringUtil.urlParamsToObj(cusInfo);
				cusTransNo = cusTransNo+cusObj.cusNo+",";
				cusTransName = cusTransName +cusObj.cusName+",";
			});	
		}
		var cusInfo = cusTransNo + ";"+	cusTransName;		
		parent.dialog.get("transCusInfo").close(cusInfo);
	}
	var _closeDialog = function(){
		parent.dialog.get("transCusInfo").close();
	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectCusInfo:_selectCusInfo,
		closeDialog:_closeDialog
	};
}(window, jQuery);