var PssBuyBill_List = function(window, $) {
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
			url : webPath+"/pssBuyBill/findByPageAjax",//列表数据查询的url
			tableId : "tabledl_pssbuybill01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {"auditStsed" : auditStsed},
			callback : function(obj) {
				var auditOpNameThIndex = $("#tablist>thead th[name=auditOpName]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $auditOpNameTd = $(tr).children("td:eq(" + auditOpNameThIndex + ")");
					if ($auditOpNameTd.text().length == 0) {
						$(tr).children("td").addClass("gray");
					}
				});
				
	    		obj.data.auditStsed = null;
	    		Pss.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssBuyBill_List.stopPropagation();
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
	
	//新增购货单
	var _addBuyBill = function() {
		window.parent.openBigForm(webPath+'/pssBuyBill/getAddPage', '购货单', function() {
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
	
	//查看购货订单
	var _getBuyOrder = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '购货订单', function() {
			updateTableData();
		});
	};
	
	//获取已选择的购货单集合
	var _getCheckedBuyBills = function() {
		var pssBuyBills = new Array();
		var $checkboxes = $("#tablist [name=buyBillId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssBuyBill = new Object();
				pssBuyBill.buyBillId = $(this).val().split("=")[1];
				pssBuyBills.push(pssBuyBill);
			});
		}
		return pssBuyBills;
	};
	
	//批量审核购货单
	var _batchAuditBuyBill = function() {
		var pssBuyBills = _getCheckedBuyBills();
		if (pssBuyBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货单审核"), 2, function(){
				var url = webPath+'/pssBuyBill/batchAuditBuyBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyBillsJson : JSON.stringify(pssBuyBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的购货单"), 1);
		}
	};
	
	//批量反审核购货单
	var _batchReverseAuditBuyBill = function() {
		var pssBuyBills = _getCheckedBuyBills();
		if (pssBuyBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货单反审核"), 2, function(){
				var url = webPath+'/pssBuyBill/batchReverseAuditBuyBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyBillsJson : JSON.stringify(pssBuyBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的购货单"), 1);
		}
	};
	
	//删除购货单
	var _deleteBuyBill = function(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货单删除"), 2, function() {
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
	
	//批量删除购货单
	var _batchDeleteBuyBill = function() {
		var pssBuyBills = _getCheckedBuyBills();
		if (pssBuyBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "购货单删除"), 2, function(){
				var url = webPath+'/pssBuyBill/batchDeleteBuyBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssBuyBillsJson : JSON.stringify(pssBuyBills)
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的购货单"), 1);
		}
	};
	
	//导入
	var _importExcel = function() {
		//alert('建设中，敬请关注...',1);
		window.parent.openBigForm(webPath+'/pssBuyBill/importExcel', '购货单导入', function(){
			updateTableData();//重新加载列表数据
		});
	};
	
	//导出
	var _exportExcel = function() {
		//alert('建设中，敬请关注...',1);
		var buyBillIds = "";
        var buyBillId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		buyBillId = $(this).val().substring(10);
        		if(buyBillId != "" && buyBillId != null){
            		buyBillIds = buyBillIds + "," + buyBillId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	 $(".table_content").find(':checkbox').each(function() {
           		buyBillId = $(this).val().substring(10);
           		if(buyBillId != "" && buyBillId != null){
               		buyBillIds = buyBillIds + "," + buyBillId;
           		}
             });
        }
       	buyBillIds = buyBillIds.substring(1);
        
       	window.location.href = webPath+"/pssBuyBill/downloadToExcel?buyBillIds="+buyBillIds;
	};
	
	var _batchPrintBill = function(){
		var pssBuyBills = _getCheckedBuyBills();
		if (pssBuyBills.length > 0) {
			
			window.top.alert("请确认是否打印购货单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'GHD'";
				var templateFileName = "'templateFile_pssghd_batch.doc'";
				var printBizType = "'GHD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssBuyBills))+'|width=1200px;height=800px;||';
			});
			
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的购货单"), 1);
		}
	};
	
	return {
		stopPropagation : _stopPropagation,
		initTableData : _initTableData,
		init : _init,
		addBuyBill : _addBuyBill,
		getBuyBill : _getBuyBill,
		getBuyOrder : _getBuyOrder,
		getCheckedBuyBills : _getCheckedBuyBills,
		batchAuditBuyBill : _batchAuditBuyBill,
		batchReverseAuditBuyBill : _batchReverseAuditBuyBill,
		deleteBuyBill : _deleteBuyBill,
		batchDeleteBuyBill : _batchDeleteBuyBill,
		importExcel : _importExcel,
		exportExcel : _exportExcel,
		batchPrintBill : _batchPrintBill
	};
}(window, jQuery);