var PssBuyReturnBill_List = function(window, $) {
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
			url : webPath+"/pssBuyReturnBill/findByPageAjax",//列表数据查询的url
			tableId : "tabledl_pssbuyreturnbill01",//列表数据查询的table编号
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
				PssBuyReturnBill_List.stopPropagation();
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
	
	//新增购货退货单
	var _addBuyReturnBill = function() {
		window.parent.openBigForm(webPath+'/pssBuyReturnBill/getAddPage', '购货退货单', function() {
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
	
	//获取已选择的购货退货单集合
	var _getCheckedBuyReturnBills = function() {
		var pssBuyReturnBills = new Array();
		var $checkboxes = $("#tablist [name=buyReturnBillId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssBuyReturnBill = new Object();
				pssBuyReturnBill.buyReturnBillId = $(this).val().split("=")[1];
				pssBuyReturnBills.push(pssBuyReturnBill);
			});
		}
		return pssBuyReturnBills;
	};
	
	//批量审核购货退货单
	var _batchAuditBuyReturnBill = function() {
		var pssBuyReturnBills = _getCheckedBuyReturnBills();
		if (pssBuyReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货退货单审核"), 2, function(){
				var url = webPath+'/pssBuyReturnBill/batchAuditBuyReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyReturnBillsJson : JSON.stringify(pssBuyReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的购货退货单"), 1);
		}
	};
	
	//批量反审核购货退货单
	var _batchReverseAuditBuyReturnBill = function() {
		var pssBuyReturnBills = _getCheckedBuyReturnBills();
		if (pssBuyReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货退货单反审核"), 2, function(){
				var url = webPath+'/pssBuyReturnBill/batchReverseAuditBuyReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyReturnBillsJson : JSON.stringify(pssBuyReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的购货退货单"), 1);
		}
	};
	
	//删除购货退货单
	var _deleteBuyReturnBill = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货退货单删除"), 2, function() {
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
	
	//批量删除购货退货单
	var _batchDeleteBuyReturnBill = function() {
		var pssBuyReturnBills = _getCheckedBuyReturnBills();
		if (pssBuyReturnBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货退货单删除"), 2, function(){
				var url = webPath+'/pssBuyReturnBill/batchDeleteBuyReturnBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyReturnBillsJson : JSON.stringify(pssBuyReturnBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的购货退货单"), 1);
		}
	};
	
	var _batchPrintBill = function(){
		var pssBuyReturnBills = _getCheckedBuyReturnBills();
		if (pssBuyReturnBills.length > 0) {
			
			window.top.alert("请确认是否打印购货退货单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'GHTHD'";
				var templateFileName = "'templateFile_pssghthd_batch.doc'";
				var printBizType = "'GHTHD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssBuyReturnBills))+'|width=1200px;height=800px;||';
			});

		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的购货退货单"), 1);
		}
	};
	
	//导入
	var _importExcel = function() {
		//alert('建设中，敬请关注...',1);
		window.parent.openBigForm(webPath+'/pssBuyReturnBill/importExcel', '购货退货单导入', function(){
			updateTableData();//重新加载列表数据
		});
	};
	
	//导出
	var _exportExcel = function() {
		//alert('建设中，敬请关注...',1);
		var buyReturnBillIds = "";
        var buyReturnBillId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		buyReturnBillId = $(this).val().substring(16);
        		if(buyReturnBillId != "" && buyReturnBillId != null){
            		buyReturnBillIds = buyReturnBillIds + "," + buyReturnBillId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
			$(".table_content").find(':checkbox').each(function() {
				buyReturnBillId = $(this).val().substring(16);
				if(buyReturnBillId != "" && buyReturnBillId != null){
				   	buyReturnBillIds = buyReturnBillIds + "," + buyReturnBillId;
				}
			});
        }
       	buyReturnBillIds = buyReturnBillIds.substring(1);
        
       	window.location.href = webPath+"/pssBuyReturnBill/downloadToExcel?buyReturnBillIds="+buyReturnBillIds;
	};
	
	return {
		stopPropagation : _stopPropagation,
		initTableData : _initTableData,
		init : _init,
		addBuyReturnBill : _addBuyReturnBill,
		getBuyReturnBill : _getBuyReturnBill,
		getBuyOrder : _getBuyOrder,
		getBuyBill : _getBuyBill,
		getCheckedBuyReturnBills : _getCheckedBuyReturnBills,
		batchAuditBuyReturnBill : _batchAuditBuyReturnBill,
		batchReverseAuditBuyReturnBill : _batchReverseAuditBuyReturnBill,
		deleteBuyReturnBill : _deleteBuyReturnBill,
		batchDeleteBuyReturnBill : _batchDeleteBuyReturnBill,
		batchPrintBill : _batchPrintBill,
		importExcel : _importExcel,
		exportExcel : _exportExcel
	};
}(window, jQuery);