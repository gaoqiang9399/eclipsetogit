;
var MfLawsuit_SelectInfo = function(window, $) {
	
	var _init = function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath + "/mfLawsuit/findByPageAjax",//列表数据查询的url
	    	tableId:"tableLawsuitSelectInfo",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	    });
	};
	// 选择
	var _selectinfo = function(url){
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					parent.dialog.get("Lawsuit").close(data.mfLawsuit);
					myclose_click();
				} else {
					alert(data.msg, 0);
				}
			},
			error : function() {
				alert("获取案件信息失败", 0);
			}
		});
		
	}
	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectinfo : _selectinfo
	};
}(window, jQuery);