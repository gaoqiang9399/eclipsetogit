var MfUseMoney = function(window, $) {
	//初始化
	var _initTableList = function(){
		$(function(){
			myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/batchPrint/useMoneyQuery/findByPageAjax", //列表数据查询的url
				tableId : "tableusemoneyquery0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				callback : function() {
					
				}// 方法执行完回调函数（取完数据做处理的时候）
			});
		});

	};
	
	var _getCheckedBills = function() {
		var bills = new Array();
		var $checkboxes = $("#tablist [name=fincId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var bill = new Object();
				bill.fincId = $(this).val().split("=")[1];
				bills.push(bill);
			});
		}
		return bills;
	};
	
	var _batchPrint = function(){
		var bills = _getCheckedBills();
		if (bills.length != 0) {
			var printBizType = "'USEMONEYQUERY'";
			var templateFileName = "'templateFile_batch.doc'";
			var fileNamePrefix = "'USEMONEYQUERY'";
			window.location.href = 'PageOffice://|'+webPath+'/component/batchprint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(bills))+'|width=1200px;height=800px;||';
			
			/*var url = webPath+'/batchPrint/batchPrintAjax';
			jQuery.ajax({
				url : url,
				data : {
					bussinessId : bills[0].fincId,
					printBizType : 'USEMONEYQUERY',
					fileName : 'templateFile_newhopeusemoney_batch.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag) {
						var poCnt = $.parseJSON(data.poCnt);
						mfPageOffice.openPageOffice(poCnt);
					}
				}, 
				error : function(data) {
					 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});*/
		}else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要打印的用款申请信息"), 1);
		}
	};
	
	var _refresh = function(){
		updateTableData();
	};
	
	return {
		initTableList : _initTableList,
		refresh : _refresh,
		getCheckedBills : _getCheckedBills,
		batchPrint : _batchPrint
	};
}(window, jQuery);