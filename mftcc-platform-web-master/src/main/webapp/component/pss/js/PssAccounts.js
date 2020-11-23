
var PssAccounts = function(window, $){
	
	var _refreshTableData = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssAccounts/findByPageAjax",//列表数据查询的url
			tableId : "tablepssaccounts0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			//data : {"auditStsed" : auditStsed},
			callback : function(obj) {
	    		
	    	}
		});
	};
	
	var _init = function(){
		_refreshTableData();
	};
	
	var _refreshTable = function(){
		_refreshTableData();
	};
	
	var _accountInsert = function(){
		window.parent.openBigForm(webPath+'/pssAccounts/getAddPage', '新增账户', function() {
			_refreshTableData();
		});
	};
	
	var _getAccount = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '账户信息', function() {
			_refreshTableData();
		});
	};
	
	var _deleteAccount = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "账户删除"), 2, function() {
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						window.top.alert(data.msg, 1);
						_refreshTableData();
					} else {
						window.top.alert(data.msg, 0);
					}
				}, error:function() {
					alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		});
	};
	
	return {
		init:_init,
		accountInsert : _accountInsert,
		refreshTable : _refreshTable,
		deleteAccount : _deleteAccount,
		getAccount : _getAccount
	};
}(window, jQuery);