
var PssSupplierInfo = function(window,$){
	
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	var _refreshTableData = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSupplierInfo/findByPageAjax",//列表数据查询的url
			tableId : "tablepsssupplierinfo0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			//data : {"auditStsed" : auditStsed},
			callback : function(obj) {
	    		$("#tablist").tableRcswitcher({name:"enabledStatus", onText:"启用", offText:"关闭"});
	    		//obj.data.auditStsed = null;
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssSupplierInfo.stopPropagation();
	    	}
		});
	};
	
	var _init = function(){
		_refreshTableData();
		
		Pss.addCheckAllEvent();
		
		$('#closeSpan').hover(
			function() {
				$('#closeSpan').find('button:eq(0) span').addClass('triangle-up');
				$('#closeSpan').find('button:eq(0) span').removeClass('triangle-down');
				$('#enableButton').addClass('pss-audit-left').addClass('pss_block');
			},
			function() {
				$('#closeSpan').find('button:eq(0) span').addClass('triangle-down');
				$('#closeSpan').find('button:eq(0) span').removeClass('triangle-up');
				$('#enableButton').removeClass('pss-audit-left').removeClass('pss_block');
		});
		
	};
	
	var _supplierInfoInsert = function(){
		window.parent.openBigForm(webPath+'/pssSupplierInfo/getAddPage?saveOrEditFlag=1', '新增供应商', function() {
			//_refreshTableData();
			updateTableData();
		});
	};
	
	var _batchEnableSupplierInfo = function(){
		var pssSupplierInfos = _getCheckedSupplierInfos();
		if (pssSupplierInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "供应商启用"), 2, function(){
				var url = webPath+'/pssSupplierInfo/batchEnableSupplierInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSupplierInfosJson : JSON.stringify(pssSupplierInfos)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							_refreshTableData();
						} else {
							window.top.alert(data.msg, 0);
						}
					}, 
					error : function(data) {
						 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要启用的供应商"), 1);
		}
	};
	
	var _batchCloseSupplierInfo = function(){
		var pssSupplierInfos = _getCheckedSupplierInfos();
		if (pssSupplierInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "供应商关闭"), 2, function(){
				var url = webPath+'/pssSupplierInfo/batchCloseSupplierInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSupplierInfosJson : JSON.stringify(pssSupplierInfos)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							_refreshTableData();
						} else {
							window.top.alert(data.msg, 0);
						}
					}, 
					error : function(data) {
						 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要关闭的供应商"), 1);
		}
	};
	
	var _deleteBatch = function(){
		var pssSupplierInfos = _getCheckedSupplierInfos();
		if (pssSupplierInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "供应商删除"), 2, function(){
				var url = webPath+'/pssSupplierInfo/batchDeleteSupplierInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSupplierInfosJson : JSON.stringify(pssSupplierInfos)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							_refreshTableData();
						} else {
							window.top.alert(data.msg, 0);
						}
					}, 
					error : function(data) {
						 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的供应商"), 1);
		}
	};
	
	var _getCheckedSupplierInfos = function() {
		var pssSupplierInfos = new Array();
		var $checkboxes = $("#tablist [name=supplierId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssSupplierInfo = new Object();
				pssSupplierInfo.supplierId = $(this).val().split("=")[1];
				pssSupplierInfos.push(pssSupplierInfo);
			});
		}
		return pssSupplierInfos;
	};
	
	var _updateEnabledStatus = function(obj, url) {
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
	};
	
	var _deleteSupplierInfo = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "供应商删除"), 2, function() {
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
	
	var _getSupplierInfo = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '供应商信息', function() {
			_refreshTableData();
		});
	};
	
	return {
		init:_init,
		supplierInfoInsert : _supplierInfoInsert,
		batchEnableSupplierInfo : _batchEnableSupplierInfo,
		batchCloseSupplierInfo : _batchCloseSupplierInfo,
		deleteBatch : _deleteBatch,
		stopPropagation : _stopPropagation,
		updateEnabledStatus : _updateEnabledStatus,
		deleteSupplierInfo : _deleteSupplierInfo,
		getSupplierInfo : _getSupplierInfo
	};
}(window,jQuery);