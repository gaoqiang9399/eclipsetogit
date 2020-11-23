var PssSaleInvoice_List = function(window, $) {
	
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	var _refreshTableData = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSaleInvoice/findByPageAjax",//列表数据查询的url
			tableId : "tablepsssaleinvoice0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			//data : {"auditStsed" : auditStsed},
			callback : function(obj) {
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssSaleInvoice_List.stopPropagation();
	    		
	    		var thIndex = $("#tablist>thead th[name=saleBillType]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $businessTypeTd = $(tr).children("td:eq(" + thIndex + ")").children("input[name=saleBillType]");
					if($businessTypeTd.val() == '02'){
						$(tr).children("td").addClass("red");
					}
				});
	    	}
		});
	};
	
	var _init = function(){
		_refreshTableData();
		
		Pss.addCheckAllEvent();
	};
	
	var _saleInvoiceInsert = function(){
		window.parent.openBigForm(webPath+'/pssSaleInvoice/getAddPage', '销售开票', function() {
			//_refreshTableData();
			updateTableData();
		});
	};
	
	var _getCheckedInvoiceInfos = function() {
		var pssInvoiceInfos = new Array();
		var $checkboxes = $("#tablist [name=saleInvoiceId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssInvoiceInfo = new Object();
				pssInvoiceInfo.saleInvoiceId = $(this).val().split("=")[1];
				pssInvoiceInfos.push(pssInvoiceInfo);
			});
		}
		return pssInvoiceInfos;
	};
	
	var _batchDeleteInvoiceInfo = function() {
		var pssInvoiceInfos = _getCheckedInvoiceInfos();
		if (pssInvoiceInfos.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "发票信息删除"), 2, function(){
				var url = webPath+'/pssSaleInvoice/batchDeleteInvoiceInfoAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssInvoiceInfosJson : JSON.stringify(pssInvoiceInfos)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的发票信息"), 1);
		}
	};
	
	var _deletePssSaleInvoice = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "发票信息删除"), 2, function() {
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
		init : _init,
		stopPropagation : _stopPropagation,
		saleInvoiceInsert : _saleInvoiceInsert,
		batchDeleteInvoiceInfo : _batchDeleteInvoiceInfo,
		deletePssSaleInvoice : _deletePssSaleInvoice
	};
}(window, jQuery);
