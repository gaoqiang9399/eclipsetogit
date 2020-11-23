var PssBuyOrder_List = function(window, $) {
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
			url : webPath+"/pssBuyOrder/findByPageAjax",//列表数据查询的url
			tableId : "tabledl_pssbuyorder01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {"auditStsed" : auditStsed},
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
	    		obj.data.auditStsed = null;
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssBuyOrder_List.stopPropagation();
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
	};
	
	//新增购货订单
	var _addBuyOrder = function() {
		window.parent.openBigForm(webPath+'/pssBuyOrder/getAddPage', '购货订单', function() {
			updateTableData();
		});
	};
	
	//查看购货订单
	var _getBuyOrder = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '购货订单', function() {
			updateTableData();
		});
	};
	
	//查看购货单
	var _getBuyBill = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '购货单', function() {
			updateTableData();
		});
	};
	
	//查看购货退货单
	var _getBuyReturnBill = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '购货退货单', function() {
			updateTableData();
		});
	};
	
	//获取已选择的购货订单集合
	var _getCheckedBuyOrders = function() {
		var pssBuyOrders = new Array();
		var $checkboxes = $("#tablist [name=buyOrderId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssBuyOrder = new Object();
				pssBuyOrder.buyOrderId = $(this).val().split("=")[1];
				pssBuyOrders.push(pssBuyOrder);
			});
		}
		return pssBuyOrders;
	};
	
	//批量审核购货订单
	var _batchAuditBuyOrder = function() {
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单审核"), 2, function(){
				var url = webPath+'/pssBuyOrder/batchAuditBuyOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyOrdersJson : JSON.stringify(pssBuyOrders)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的购货订单"), 1);
		}
	};
	
	//批量反审核购货订单
	var _batchReverseAuditBuyOrder = function() {
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单反审核"), 2, function(){
				var url = webPath+'/pssBuyOrder/batchReverseAuditBuyOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyOrdersJson : JSON.stringify(pssBuyOrders)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的购货订单"), 1);
		}
	};
	
	//批量关闭购货订单
	var _batchCloseBuyOrder = function() {
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单关闭"), 2, function(){
				var url = webPath+'/pssBuyOrder/batchCloseBuyOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyOrdersJson : JSON.stringify(pssBuyOrders)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要关闭的购货订单"), 1);
		}
	};
	
	//批量启用购货订单
	function _batchEnableBuyOrder() {
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单启用"), 2, function(){
				var url = webPath+'/pssBuyOrder/batchEnableBuyOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyOrdersJson : JSON.stringify(pssBuyOrders)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要启用的购货订单"), 1);
		}
	};
	
	//删除购货订单
	var _deleteBuyOrder = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单删除"), 2, function() {
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
	
	//批量删除购货订单
	var _batchDeleteBuyOrder = function() {
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货订单删除"), 2, function(){
				var url = webPath+'/pssBuyOrder/batchDeleteBuyOrderAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyOrdersJson : JSON.stringify(pssBuyOrders)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的购货订单"), 1);
		}
	};
	
	var _batchPrintBill = function(){
		var pssBuyOrders = _getCheckedBuyOrders();
		if (pssBuyOrders.length > 0) {
			
			window.top.alert("请确认是否打印购货订单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'GHDD'";
				var templateFileName = "'templateFile_pssghdd_batch.doc'";
				var printBizType = "'GHDD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssBuyOrders))+'|width=1200px;height=800px;||';
			});
			
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的购货订单"), 1);
		}
		
		
	};
	
	//导入
	var _importExcel = function() {
		//alert('建设中，敬请关注...',1);
		window.parent.openBigForm(webPath+'/pssBuyOrder/importExcel', '购货订单导入', function(){
			updateTableData();//重新加载列表数据
		});
	};
	
	//导出
	var _exportExcel = function() {
		//alert('建设中，敬请关注...',1);
		var buyOrderIds = "";
        var buyOrderId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		buyOrderId = $(this).val().substring(11);
        		if(buyOrderId != "" && buyOrderId != null){
            		buyOrderIds = buyOrderIds + "," + buyOrderId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	 $(".table_content").find(':checkbox').each(function() {
           		buyOrderId = $(this).val().substring(11);
           		if(buyOrderId != "" && buyOrderId != null){
               		buyOrderIds = buyOrderIds + "," + buyOrderId;
           		}
             });
        }
       	buyOrderIds = buyOrderIds.substring(1);
        
       	window.location.href = webPath+"/pssBuyOrder/downloadToExcel?buyOrderIds="+buyOrderIds;
	};
	
	return {
		stopPropagation : _stopPropagation,
		initTableData : _initTableData,
		init : _init,
		addBuyOrder : _addBuyOrder,
		getBuyOrder : _getBuyOrder,
		getBuyBill : _getBuyBill,
		getBuyReturnBill : _getBuyReturnBill,
		getCheckedBuyOrders : _getCheckedBuyOrders,
		batchAuditBuyOrder : _batchAuditBuyOrder,
		batchReverseAuditBuyOrder : _batchReverseAuditBuyOrder,
		batchCloseBuyOrder : _batchCloseBuyOrder,
		batchEnableBuyOrder : _batchEnableBuyOrder,
		deleteBuyOrder : _deleteBuyOrder,
		batchDeleteBuyOrder : _batchDeleteBuyOrder,
		batchPrintBill : _batchPrintBill,
		importExcel : _importExcel,
		exportExcel : _exportExcel
	};
}(window, jQuery);