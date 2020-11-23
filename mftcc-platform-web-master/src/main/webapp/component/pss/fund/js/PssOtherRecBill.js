var PssOtherRecBill = function(window, $){
	
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssOtherRecBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepssotherrecbill0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
			}
		});
		
		addCheckAllEvent();
		
		$('#pssChkRB').hover(function(){
	    	$('#pssChkRB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssChkRB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideChkRB').addClass('pss-chk-left').addClass('pss_block');
	    },function(){
	    	$('#pssChkRB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssChkRB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideChkRB').removeClass('pss-chk-left').removeClass('pss_block');
	    });
		
	};
	
	//去除表头 点击事件 换成 全选事件
	function addCheckAllEvent() {
		 $(".table-float-head").delegate("th:first-child","click",function(){
			if (isCheckAll) {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = false;
				});
				isCheckAll = false;
			} else {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = true;
				});
				isCheckAll = true;
			}
		});
	};
	
	var _getByIdThis = function(obj, url){
		window.parent.openBigForm(url, '查看其他收入单', updateTableData);
	};
	
	var _otherRecBillInsert = function(){
		window.parent.openBigForm(webPath+'/pssOtherRecBill/input', '新增其他收入单', function() {
			//refreshTableData();
			updateTableData();
			isCheckAll = false;
		});
	};
	
	//批量删除其他收入单
	var _deleteBatch = function(){
		var pssOtherRecBills = _getCheckedOtherRecBills();
		if (pssOtherRecBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "删除其他收入单"), 2, function(){
				var url = webPath+'/pssOtherRecBill/batchDeleteOtherRecBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssOtherRecBillsJson : JSON.stringify(pssOtherRecBills)
					},
					type : "POST",
					dataType : "json",
					success:function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							refreshTableData();
							isCheckAll = false;
							//停止checkBox事件冒泡
							stopPropagation();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要删除的其他收入单"), 1);
		}
	};
	
	//批量审核其他收入单
	var _auditBatch = function(){
		var pssOtherRecBills = _getCheckedOtherRecBills();
		if (pssOtherRecBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "其他收入单"), 2, function(){
				var url = webPath+'/pssOtherRecBill/batchAuditOtherRecBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssOtherRecBillsJson : JSON.stringify(pssOtherRecBills)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							refreshTableData();
							isCheckAll = false;
							//停止checkBox事件冒泡
							stopPropagation();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要审核的其他收入单"), 1);
		}
	};
	
	//批量反审核其他收入单
	var _reAuditBatch = function(){
		var pssOtherRecBills = _getCheckedOtherRecBills();
		if (pssOtherRecBills.length > 0) {
			window.top.alert(top.getMessage("CONFIRM_OPERATION", "反审核其他收入单"), 2, function(){
				var url = webPath+'/pssOtherRecBill/batchReverseAuditOtherRecBillAjax';
				jQuery.ajax({
					url : url,
					data : {
						pssOtherRecBillsJson : JSON.stringify(pssOtherRecBills)
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success) {
							window.top.alert(data.msg, 3);
							refreshTableData();
							isCheckAll = false;
							//停止checkBox事件冒泡
							stopPropagation();
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
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要反审核的其他收入单"), 1);
		}
	};
	
	//停止checkBox事件冒泡
	function stopPropagation() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	//获取已选择的其他收入单集合
	function _getCheckedOtherRecBills() {
		var pssOtherRecBills = new Array();
		var $checkboxes = $("#tablist [name=otherRecId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var pssOtherRecBill = new Object();
				pssOtherRecBill.otherRecId = $(this).val().split("=")[1];
				pssOtherRecBills.push(pssOtherRecBill);
			});
		}
		return pssOtherRecBills;
	};
	
	//删除其他收入单
	function _deleteOtherRecBill(obj, url) {
		window.top.alert(top.getMessage("CONFIRM_OPERATION", "删除其他收入单"), 2, function() {
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						window.top.alert(data.msg, 1);
						refreshTableData();
						isCheckAll = false;
						//停止checkBox事件冒泡
						stopPropagation();
					} else {
						window.top.alert(data.msg, 0);
					}
				}, error:function() {
					alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		});
	}
	
	//刷新列表
	function refreshTableData() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssOtherRecBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepssotherrecbill0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100//顶部区域的高度，用于计算列表区域高度。
			/*data : {"auditStsed" : auditStsed},
			callback : function(obj) {
	    		obj.data.auditStsed = null;
	    	}*/
		});
	}
	
	var _batchPrintBill = function(){
		var pssOtherRecBills = _getCheckedOtherRecBills();
		if (pssOtherRecBills.length > 0) {
			
			window.top.alert("请确认是否打印其他收入单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'QTSRD'";
				var templateFileName = "'templateFile_pssqtsrd_batch.doc'";
				var printBizType = "'QTSRD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(pssOtherRecBills))+'|width=1200px;height=800px;||';
			});
			
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的其他收入单"), 1);
		}
	};
	
	return {
		init : _init,
		otherRecBillInsert : _otherRecBillInsert,
		deleteBatch : _deleteBatch,
		auditBatch : _auditBatch,
		reAuditBatch : _reAuditBatch,
		getByIdThis : _getByIdThis,
		deleteOtherRecBill : _deleteOtherRecBill,
		batchPrintBill : _batchPrintBill
	};
	
}(window, jQuery);