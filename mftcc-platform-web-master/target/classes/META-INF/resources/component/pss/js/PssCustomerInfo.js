
var PssCustomerInfo = function(window,$){
	
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	var _refreshTableData = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssCustomerInfo/findByPageAjax",//列表数据查询的url
			tableId : "tablepsscustomerinfo0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			//data : {"auditStsed" : auditStsed},
			callback : function(obj) {
	    		$("#tablist").tableRcswitcher({name:"enabledStatus", onText:"启用", offText:"关闭"});
	    		//obj.data.auditStsed = null;
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssCustomerInfo.stopPropagation();
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
	
	var _customerInfoInsert = function(){
		window.parent.openBigForm(webPath+'/pssCustomerInfo/getAddPage?saveOrEditFlag=1', '新增客户', function() {
			//_refreshTableData();
			updateTableData();
		});
	};
	
	var _batchEnableCutomerInfo = function(){
		var pssCustomerInfos = _getCheckedCustomerInfos();
		if (pssCustomerInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "客户启用"), 2, function(){
				var url = webPath+'/pssCustomerInfo/batchEnableCustomerInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssCustomerInfosJson : JSON.stringify(pssCustomerInfos)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要启用的客户"), 1);
		}
	};
	
	var _batchCloseCutomerInfo = function(){
		var pssCustomerInfos = _getCheckedCustomerInfos();
		if (pssCustomerInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "客户关闭"), 2, function(){
				var url = webPath+'/pssCustomerInfo/batchCloseCustomerInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssCustomerInfosJson : JSON.stringify(pssCustomerInfos)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要关闭的客户"), 1);
		}
	};
	
	var _deleteBatch = function(){
		var pssCustomerInfos = _getCheckedCustomerInfos();
		if (pssCustomerInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "客户删除"), 2, function(){
				var url = webPath+'/pssCustomerInfo/batchDeleteCustomerInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssCustomerInfosJson : JSON.stringify(pssCustomerInfos)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的客户"), 1);
		}
	};
	
	var _getCheckedCustomerInfos = function() {
		var pssCustomerInfos = new Array();
		var $checkboxes = $("#tablist [name=cusNo]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssCustomerInfo = new Object();
				pssCustomerInfo.cusNo = $(this).val().split("=")[1];
				pssCustomerInfos.push(pssCustomerInfo);
			});
		}
		return pssCustomerInfos;
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
	
	var _deleteCustomerInfo = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "客户删除"), 2, function() {
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
	
	var _getCustomerInfo = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '客户信息', function() {
			_refreshTableData();
		});
	};
	
	return {
		init:_init,
		customerInfoInsert : _customerInfoInsert,
		batchEnableCutomerInfo : _batchEnableCutomerInfo,
		batchCloseCutomerInfo : _batchCloseCutomerInfo,
		deleteBatch : _deleteBatch,
		stopPropagation : _stopPropagation,
		updateEnabledStatus : _updateEnabledStatus,
		deleteCustomerInfo : _deleteCustomerInfo,
		getCustomerInfo : _getCustomerInfo
	};
}(window,jQuery);