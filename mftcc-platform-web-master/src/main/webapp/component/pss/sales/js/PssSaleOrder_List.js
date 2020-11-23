var PssSaleOrder_List = function(window, $) {
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	//初始化列表
	var _initTableData = function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSaleOrder/findByPageAjax",//列表数据查询的url
			tableId : "tabledl_psssaleorder01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {"orderState" : orderState,
					"auditStsed" : auditStsed,
					"enabledStatus" : enabledStatus},
			callback : function(obj) {
				var businessTypeThIndex = $("#tablist>thead th[name=businessType]").index();
				var auditOpNameThIndex = $("#tablist>thead th[name=auditOpName]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $businessTypeTd = $(tr).children("td:eq(" + businessTypeThIndex + ")");
					var $auditOpNameTd = $(tr).children("td:eq(" + auditOpNameThIndex + ")");
					if ($businessTypeTd.text() == "退货") {
						$(tr).children("td").addClass("red");
					} else if ($auditOpNameTd.text().length == 0) {
						$(tr).children("td").addClass("gray");
					}
				});
				
	    		$("#tablist").tableRcswitcher({name:"enabledStatus", onText:"启用", offText:"关闭"});
	    		obj.data.orderState = null;
	    		obj.data.auditStsed = null;
	    		obj.data.enabledStatus = null;
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssSaleOrder_List.stopPropagation();
	    	}
		});
	};
	
	var _init = function() {
		_initTableData();
		Pss.addCheckAllEvent();
		
		//审核/反审核
		$('#auditSpan').hover(
			function() {
				$('#auditSpan').find('button:eq(0) span').addClass('triangle-up');
				$('#auditSpan').find('button:eq(0) span').removeClass('triangle-down');
				$('#reverseAuditButton').addClass('pss-audit-left').addClass('pss_block');
			},
			function() {
				$('#auditSpan').find('button:eq(0) span').addClass('triangle-down');
				$('#auditSpan').find('button:eq(0) span').removeClass('triangle-up');
				$('#reverseAuditButton').removeClass('pss-audit-left').removeClass('pss_block');
		});

		//导入/导出
		$('#importSpan').hover(
			function() {
				$('#importSpan').find('button:eq(0) span').addClass('triangle-up');
				$('#importSpan').find('button:eq(0) span').removeClass('triangle-down');
				$('#exportButton').addClass('pss-import-left').addClass('pss_block');
			},
			function() {
				$('#importSpan').find('button:eq(0) span').addClass('triangle-down');
				$('#importSpan').find('button:eq(0) span').removeClass('triangle-up');
				$('#exportButton').removeClass('pss-impport-left').removeClass('pss_block');
		});
		
		//关闭/启用
		$('#closeSpan').hover(
			function() {
				$('#closeSpan').find('button:eq(0) span').addClass('triangle-up');
				$('#closeSpan').find('button:eq(0) span').removeClass('triangle-down');
				$('#enableButton').addClass('pss-close-left').addClass('pss_block');
			},
			function() {
				$('#closeSpan').find('button:eq(0) span').addClass('triangle-down');
				$('#closeSpan').find('button:eq(0) span').removeClass('triangle-up');
				$('#enableButton').removeClass('pss-close-left').removeClass('pss_block');
		});
		
		//锁定/解锁
		$('#lockSpan').hover(
			function() {
				$('#lockSpan').find('button:eq(0) span').addClass('triangle-up');
				$('#lockSpan').find('button:eq(0) span').removeClass('triangle-down');
				$('#unlockButton').addClass('pss-lock-left').addClass('pss_block');
			},
			function() {
				$('#lockSpan').find('button:eq(0) span').addClass('triangle-down');
				$('#lockSpan').find('button:eq(0) span').removeClass('triangle-up');
				$('#unlockButton').removeClass('pss-lock-left').removeClass('pss_block');
		});
	};
	
	//新增销货订单
	var _addSaleOrder = function(){
		window.parent.openBigForm(webPath+'/pssSaleOrder/getAddPage', '销货订单', function() {
			updateTableData();
		});
	};
	
	//查看销货订单
	var _getSaleOrder = function(url){
		url = encodeURI(url);
		window.parent.openBigForm(url, '销货订单', function() {
			updateTableData();
		});
	};
	
	//查看销货单
	var _getSaleBill = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '销货单', function() {
			updateTableData();
		});
	};
	
	//查看销货退货单
	var _getSaleReturnBill = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '销货退货单', function() {
			updateTableData();
		});
	};
	
	//获取已选择的销货订单集合
	var _getCheckedSaleOrders = function() {
		var pssSaleOrders = new Array();
		var $checkboxes = $("#tablist [name=saleOrderId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssSaleOrder = new Object();
				pssSaleOrder.saleOrderId = $(this).val().split("=")[1];
				pssSaleOrders.push(pssSaleOrder);
			});
		}
		return pssSaleOrders;
	};
	
	//批量审核销货订单
	var _batchAuditSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单审核"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchAuditSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的销货订单"), 1);
		}
	};
	
	//批量反审核销货订单
	var _batchReverseAuditSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单反审核"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchReverseAuditSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的销货订单"), 1);
		}
	};
	
	//批量关闭销货订单
	var _batchCloseSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单关闭"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchCloseSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要关闭的销货订单"), 1);
		}
	};
	
	//批量启用销货订单
	var _batchEnableSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单启用"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchEnableSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要启用的销货订单"), 1);
		}
	};
	
	//批量锁定销货订单
	var _batchLockSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单锁定"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchLockSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要锁定的销货订单"), 1);
		}
	};
	
	//批量解锁销货订单
	var _batchUnlockSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单解锁"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchUnlockSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要解锁的销货订单"), 1);
		}
	};
	
	//删除销货订单
	var _deleteSaleOrder = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单删除"), 2, function() {
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						window.top.alert(data.msg, 1);
						updateTableData();
					} else {
						window.top.alert(data.msg, 0);
					}
				}, error:function() {
					alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		});
	};
	
	//批量删除销货订单
	var _batchDeleteSaleOrder = function() {
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货订单删除"), 2, function(){
				var url = webPath+'/pssSaleOrder/batchDeleteSaleOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleOrdersJson : JSON.stringify(pssSaleOrders)
					},
					type : "POST",
					dataType : "json",
					success:function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							updateTableData();
						} else {
							window.top.alert(data.msg, 0);
						}
					}, 
					error:function(data) {
						 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的销货订单"), 1);
		}
	};
	
	var _batchPrintBill = function(){
		var pssSaleOrders = _getCheckedSaleOrders();
		if (pssSaleOrders.length > 0) {
			
			window.top.alert("请确认是否打印销货订单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'XHDD'";
				var templateFileName = "'templateFile_pssxhdd_batch.doc'";
				var printBizType = "'XHDD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssSaleOrders))+'|width=1200px;height=800px;||';
			});
			
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的销货订单"), 1);
		}
	};
	
	//导入
	var _importExcel = function() {
		//alert('建设中，敬请关注...',1);
		window.parent.openBigForm(webPath+'/pssSaleOrder/importExcel', '销货订单导入', function(){
			updateTableData();//重新加载列表数据
		});
	};
	
	//导出
	var _exportExcel = function() {
		//alert('建设中，敬请关注...',1);
		var saleOrderIds = "";
        var saleOrderId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		saleOrderId = $(this).val().substring(12);
        		if(saleOrderId != "" && saleOrderId != null){
            		saleOrderIds = saleOrderIds + "," + saleOrderId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
			$(".table_content").find(':checkbox').each(function() {
				saleOrderId = $(this).val().substring(12);
				if(saleOrderId != "" && saleOrderId != null){
				   	saleOrderIds = saleOrderIds + "," + saleOrderId;
				}
			});
        }
       	saleOrderIds = saleOrderIds.substring(1);
        
       	window.location.href = webPath+"/pssSaleOrder/downloadToExcel?saleOrderIds="+saleOrderIds;
	};
	
	return {
		stopPropagation : _stopPropagation,
		initTableData : _initTableData,
		init : _init,
		addSaleOrder : _addSaleOrder,
		getSaleOrder : _getSaleOrder,
		getSaleBill : _getSaleBill,
		getSaleReturnBill : _getSaleReturnBill,
		getCheckedSaleOrders : _getCheckedSaleOrders,
		batchAuditSaleOrder : _batchAuditSaleOrder,
		batchReverseAuditSaleOrder : _batchReverseAuditSaleOrder,
		batchCloseSaleOrder : _batchCloseSaleOrder,
		batchEnableSaleOrder : _batchEnableSaleOrder,
		batchLockSaleOrder : _batchLockSaleOrder,
		batchUnlockSaleOrder : _batchUnlockSaleOrder,
		deleteSaleOrder : _deleteSaleOrder,
		batchDeleteSaleOrder : _batchDeleteSaleOrder,
		batchPrintBill : _batchPrintBill,
		importExcel : _importExcel,
		exportExcel : _exportExcel
	};
}(window, jQuery);