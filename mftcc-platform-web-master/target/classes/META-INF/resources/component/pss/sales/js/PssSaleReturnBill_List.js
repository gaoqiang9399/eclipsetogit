var PssSaleReturnBill_List = function(window, $) {
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
			url : webPath+"/pssSaleReturnBill/findByPageAjax",//列表数据查询的url
			tableId : "tabledl_psssalereturnbill01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			callback : function(obj) {
				var auditOpNameThIndex = $("#tablist>thead th[name=auditOpName]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $auditOpNameTd = $(tr).children("td:eq(" + auditOpNameThIndex + ")");
					if ($auditOpNameTd.text().length > 0) {
						$(tr).children("td").addClass("red");
					} else {
						$(tr).children("td").addClass("gray");
					}
				});
				
				Pss.isCheckAll = false;
				//停止checkBox事件冒泡
				PssSaleReturnBill_List.stopPropagation();
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
	};
	
	//新增销货退货单
	var _addSaleReturnBill = function() {
		window.parent.openBigForm(webPath+'/pssSaleReturnBill/getAddPage', '销货退货单', function() {
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
	
	//查看销货订单
	var _getSaleOrder = function(url) {
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
	
	//获取已选择的销货退货单集合
	var _getCheckedSaleReturnBills = function() {
		var pssSaleReturnBills = new Array();
		var $checkboxes = $("#tablist [name=saleReturnBillId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssSaleReturnBill = new Object();
				pssSaleReturnBill.saleReturnBillId = $(this).val().split("=")[1];
				pssSaleReturnBills.push(pssSaleReturnBill);
			});
		}
		return pssSaleReturnBills;
	};
	
	//批量审核销货退货单
	var _batchAuditSaleReturnBill = function() {
		var pssSaleReturnBills = _getCheckedSaleReturnBills();
		if (pssSaleReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货退货单审核"), 2, function(){
				var url = webPath+'/pssSaleReturnBill/batchAuditSaleReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleReturnBillsJson : JSON.stringify(pssSaleReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的销货退货单"), 1);
		}
	};
	
	//批量反审核销货退货单
	var _batchReverseAuditSaleReturnBill = function() {
		var pssSaleReturnBills = _getCheckedSaleReturnBills();
		if (pssSaleReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货退货单反审核"), 2, function(){
				var url = webPath+'/pssSaleReturnBill/batchReverseAuditSaleReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleReturnBillsJson : JSON.stringify(pssSaleReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的销货退货单"), 1);
		}
	};
	
	//删除销货退货单
	var _deleteSaleReturnBill = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货退货单删除"), 2, function() {
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				success:function(data) {
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
	
	//批量删除销货退货单
	var _batchDeleteSaleReturnBill = function() {
		var pssSaleReturnBills = _getCheckedSaleReturnBills();
		if (pssSaleReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "销货退货单删除"), 2, function(){
				var url = webPath+'/pssSaleReturnBill/batchDeleteSaleReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssSaleReturnBillsJson : JSON.stringify(pssSaleReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的销货退货单"), 1);
		}
	};
	
	var _batchPrintBill = function(){
		var pssSaleReturnBills = _getCheckedSaleReturnBills();
		if (pssSaleReturnBills.length > 0) {
			
			window.top.alert("请确认是否打印购货订单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'XHTHD'";
				var templateFileName = "'templateFile_pssxhthd_batch.doc'";
				var printBizType = "'XHTHD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssSaleReturnBills))+'|width=1200px;height=800px;||';
			});
			
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的销货退货单"), 1);
		}
	};
	
	//导入
	var _importExcel = function() {
		//alert('建设中，敬请关注...',1);
		window.parent.openBigForm(webPath+'/pssSaleReturnBill/importExcel', '销货退货单导入', function(){
			updateTableData();//重新加载列表数据
		});
	};
	
	//导出
	var _exportExcel = function() {
		//alert('建设中，敬请关注...',1);
		var saleReturnBillIds = "";
        var saleReturnBillId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		saleReturnBillId = $(this).val().substring(17);
        		if(saleReturnBillId != "" && saleReturnBillId != null){
            		saleReturnBillIds = saleReturnBillIds + "," + saleReturnBillId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
			$(".table_content").find(':checkbox').each(function() {
				saleReturnBillId = $(this).val().substring(17);
				if(saleReturnBillId != "" && saleReturnBillId != null){
				   	saleReturnBillIds = saleReturnBillIds + "," + saleReturnBillId;
				}
			});
        }
       	saleReturnBillIds = saleReturnBillIds.substring(1);
        
       	window.location.href = webPath+"/pssSaleReturnBill/downloadToExcel?saleReturnBillIds="+saleReturnBillIds;
	};
	
	return {
		stopPropagation : _stopPropagation,
		initTableData : _initTableData,
		init : _init,
		addSaleReturnBill : _addSaleReturnBill,
		getSaleReturnBill : _getSaleReturnBill,
		getSaleOrder : _getSaleOrder,
		getSaleBill : _getSaleBill,
		getCheckedSaleReturnBills : _getCheckedSaleReturnBills,
		batchAuditSaleReturnBill : _batchAuditSaleReturnBill,
		batchReverseAuditSaleReturnBill : _batchReverseAuditSaleReturnBill,
		deleteSaleReturnBill : _deleteSaleReturnBill,
		batchDeleteSaleReturnBill : _batchDeleteSaleReturnBill,
		batchPrintBill : _batchPrintBill,
		importExcel : _importExcel,
		exportExcel : _exportExcel
	};
}(window, jQuery);