var PssSaleInvoice_Detail = function(window, $) {
	
	var _initCheckBox = function() {
		$("#tablist>tbody tr").find(':checkbox').each(function(){
			var $checkBox = $(this);
			$checkBox.change(function(){
                var thisCurrInvoiceAmountThIndex;
				if(!$checkBox.is(":checked")){
					thisCurrInvoiceAmountThIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
					addFormData();
					$checkBox.parents("tr").children("td:eq(" + thisCurrInvoiceAmountThIndex + ")").text("");		
				}else{
					var notInvoiceAmountThIndex = $("#tablist>thead th[name=notInvoicedAmount]").index();
					var notInvoiceAmountText = $checkBox.parents("tr").children("td:eq(" + notInvoiceAmountThIndex + ")").text();
					
					thisCurrInvoiceAmountThIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
					if($checkBox.parents("tr").children("td:eq(" + thisCurrInvoiceAmountThIndex + ")").text().length == 0){
						$checkBox.parents("tr").children("td:eq(" + thisCurrInvoiceAmountThIndex + ")").text(notInvoiceAmountText);
					}
					addFormData();
				}

				var $checkBoxs = $("#tablist [name=billId]:checkbox:checked");
				if($checkBoxs != null && $checkBoxs.length > 0){					
					var thIndex = $("#tablist>thead th[name=cusName]").index();					
					var cusNameText = $($checkBoxs.get(0)).parent('td').parent('tr').children("td:eq(" + thIndex + ")").text();
					$("input[name=top-invoiceTitle]").val(cusNameText);
				}else{
					$("input[name=top-invoiceTitle]").val("");
				}
				
			});
		});
	};
	
	var _checkCurrentTr = function(obj){
		
		var trCheckbox = $(obj).find(':checkbox').get(0);
		if (trCheckbox.checked) {
			$(trCheckbox).prop('checked', false).change();
		} else {
			$(trCheckbox).prop('checked', true).change();
		}
	};
	
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
		
		var thisCurrInvoiceAmountThIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr){
			$(tr).children("td:eq(" + thisCurrInvoiceAmountThIndex + ")").bind('click', function(event) {
				event.stopPropagation();
			});
		});
	};
	
	var _isCheckAll = false;
	var _addCheckAllEvent = function() {
		$("#tablist").delegate("th[name=billId]", "click", function() {
			if (PssSaleInvoice_Detail.isCheckAll) {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					//this.checked = false;
					$(this).prop('checked', false).change();
				});
			 	PssSaleInvoice_Detail.isCheckAll = false;
			} else {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					//this.checked = true;
					$(this).prop('checked', true).change();
				});
			 	PssSaleInvoice_Detail.isCheckAll = true;
			}
		});
	};
	
	function addFormData(){
		var flag = true;//(ALL XHT)
		var amount = 0;
		//var billIdThIndex = $("#tablist>thead th[name=billId]").index();
		//var currInvoiceAmountThIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
		//$("#tablist>tbody tr").each(function(trIndex, tr) {
		var $checkBoxs = $("#tablist [name=billId]:checkbox:checked");
		$checkBoxs.each(function(index) {
			//var $billIdTd = $(this).children("td:eq(" + billIdThIndex + ")");
			//var billId = $billIdTd.children("input[name=billId]").val();
			//if(billId.length > 0){
				var $checkBoxTdTr  = $($checkBoxs.get(index)).parent('td').parent('tr');
				
				var thIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
				var currInvoiceAmountText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				if(currInvoiceAmountText.length > 0){
					thIndex = $("#tablist>thead th[name=busType]").index();
					var busTypeText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").children("input[name=busType]").val();
					if(busTypeText == '01'){//XH
						flag = false;
						amount = amount + parseFloat(Pss.ifMoneyToNumber(currInvoiceAmountText));
					}else{//XHT
						amount = amount - parseFloat(Pss.ifMoneyToNumber(currInvoiceAmountText));
					}
				}
				
				/**
				var $currInvoiceAmountTd = $(this).children("td:eq(" + currInvoiceAmountThIndex + ")");				
				var currInvoiceAmount = $currInvoiceAmountTd.text();
				if(currInvoiceAmount.length > 0){		
					var busTypeIndex = $("#tablist>thead th[name=busType]").index();
					var busTypeText = $(this).children("td:eq(" + busTypeIndex + ")").children("input[name=busType]").val();
					if(busTypeText == '01'){//XH
						//currInvoiceAmount = Pss.ifMoneyToNumber(currInvoiceAmount);
						flag = false;
						amount = amount + parseFloat(Pss.ifMoneyToNumber(currInvoiceAmount));
					}else{//XHT
						//currInvoiceAmount = (-1) * Pss.ifMoneyToNumber(currInvoiceAmount);
						amount = amount - parseFloat(Pss.ifMoneyToNumber(currInvoiceAmount));
					}
				}
				**/
			//}
		});
		
		if(flag == true){
			amount = Pss.ifMoneyToNumber(amount) * (-1);
		}
		var $amount = $("input[name=top-invoiceAmount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		
	};
	
	function decFormData(thisCurrInvoiceAmount,busType){
		var amount = $("input[name=top-invoiceAmount]").val();
		if(busType == '01'){
			amount = amount - parseFloat(Pss.ifMoneyToNumber(thisCurrInvoiceAmount));
		}else{
			amount = amount - (parseFloat(Pss.ifMoneyToNumber(thisCurrInvoiceAmount) * (-1)));
		}
		
		var $amount = $("input[name=top-invoiceAmount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		
	};
	
	var _initPssSaleInvoiceBillList = function(){
		
		$("input[name=top-customer]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				$("#top-customer").val(obj.val());
			}
		});
		var cusPopsDiv = $("#top-customer").nextAll("div .pops-value").get(0);
		var cusLeft = $(cusPopsDiv).position().left;
		var cusSelectDiv = $("#top-customer").next("div .pops-select").get(0);
		$(cusPopsDiv).width(120);
		$(cusSelectDiv).css({'left':cusLeft});
		$(cusSelectDiv).width(228); //selectDiv比PopsDiv长28px
		$(cusSelectDiv).find("li").width(180);
		$(cusSelectDiv).find("div .pops-search").width(228);
		
		
		$('.pss-date').on('click', function() {
			fPopUpCalendarDlg({
				isclear : false,
				/* min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
				max: currDate + ' 23:59:59', //最大日期 */
				choose : function(data) {
				}
			});
		});
		
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSaleInvoice/initPssSaleInvoiceBillListAjax",//列表数据查询的url
			tableId : "tablepsssaleinvoice0002",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 10000,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			//data : {"auditStsed" : auditStsed},
			callback : function(obj) {
				
	    		$('.footer_loader').remove();
	    		
	    		var moneyThs = new Array("currInvoiceAmount");
	    		Pss.addMoneyEvent(moneyThs,ajaxData.pssConfig,'',addFormData);
	    		
	    		var tabHeadThs = new Array("currInvoiceAmount");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				PssSaleInvoice_Detail.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssSaleInvoice_Detail.stopPropagation();
	    		
				_initCheckBox();
				
				var thIndex = $("#tablist>thead th[name=busType]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $businessTypeTd = $(tr).children("td:eq(" + thIndex + ")").children("input[name=busType]");
					if($businessTypeTd.val() == '02'){
						$(tr).children("td").addClass("red");
					}
				});
	    	}
		});

		$("table.table-float-head").remove();
		
		PssSaleInvoice_Detail.addCheckAllEvent();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	var _searchPssSaleInvoiceBillList = function() {
		
		var startBillDate = $("#top-startBillDate").val();
		var endBillDate = $("#top-endBillDate").val();
		var billNo = $("#top-billNo").val();
		var cusNo = $("#top-customer").val();
		var invoicedState = $('input:radio[name="top-invoiceState"]:checked').val();
		
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSaleInvoice/searchPssSaleInvoiceBillListAjax",//列表数据查询的url
			tableId : "tablepsssaleinvoice0002",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 10000,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {
					"startBillDate" : startBillDate,
					"endBillDate" : endBillDate,
					"billNo" : billNo,
					"cusNo" : cusNo,
					"invoicedState" : invoicedState
					},
			callback : function(obj) {
	    		$('.footer_loader').remove();
	    			    		
	    		var moneyThs = new Array("currInvoiceAmount");
	    		Pss.addMoneyEvent(moneyThs,ajaxData.pssConfig,'',addFormData);
	    		
	    		var tabHeadThs = new Array("currInvoiceAmount");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				PssSaleInvoice_Detail.isCheckAll = false;
				//停止checkBox事件冒泡
	    		PssSaleInvoice_Detail.stopPropagation();
	    		
				_initCheckBox();
				
				var thIndex = $("#tablist>thead th[name=busType]").index();
				var trs = $("#tablist>tbody tr");
				$(trs).each(function(index, tr) {
					var $businessTypeTd = $(tr).children("td:eq(" + thIndex + ")").children("input[name=busType]");
					if($businessTypeTd.val() == '02'){
						$(tr).children("td").addClass("red");
					}
				});
	    	}
		});
		$("table.table-float-head").remove();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	    
	};
	
	var _getCheckedBills = function() {
		var bills = new Array();
		var $checkboxes = $("#tablist [name=billId]:checkbox:checked");
		if ($checkboxes.length > 0) {
			$checkboxes.each(function() {
				var bill = new Object();
				bill.billId = $(this).val().split("=")[1];
				bills.push(bill);
			});
		}
		return bills;
	};
	
	var _batchInvoice = function() {
		var bills = _getCheckedBills();
		if(bills.length > 0){
			var invoiceNo = $("input[name=top-invoiceNo]").val();
			var invoiceDate = $("input[name=top-invoiceDate]").val();
			var invoiceAmount = $("input[name=top-invoiceAmount]").val();
			var invoiceTitle = $("input[name=top-invoiceTitle]").val();
			
			if(invoiceNo == ''){
				DIALOG.tip("请录入发票号!", 3000);
				return false;
			}
			if(invoiceTitle == ''){
				DIALOG.tip("请录入发票抬头!", 3000);
				return false;
			}
			
			var pssSaleInvoice = new Object();
			var pssSaleInvoices = new Array();
			pssSaleInvoice.invoiceNo = invoiceNo;
			pssSaleInvoice.invoiceDate = invoiceDate.replace("-","").replace("-","");
			pssSaleInvoice.invoiceAmount = invoiceAmount;
			pssSaleInvoice.invoiceTitle = invoiceTitle;
			pssSaleInvoices.push(pssSaleInvoice);
			
			var pssSaleInvoiceDetails = new Array();
			var $checkBoxs = $("#tablist [name=billId]:checkbox:checked");
			var flag = true;
			$checkBoxs.each(function(index){
				
				var $checkBoxTdTr  = $($checkBoxs.get(index)).parent('td').parent('tr');
				
				var thIndex = $("#tablist>thead th[name=currInvoiceAmount]").index();
				var currInvoiceAmountText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				if(currInvoiceAmountText.length == 0){
					DIALOG.tip("本次开票金额不能为空!", 3000);
					flag = false;
					return false;
				}
				if(Pss.ifMoneyToNumber(currInvoiceAmountText) <= 0){
					DIALOG.tip("本次开票金额不能为0或者负数!", 3000);
					flag = false;
					return false;
				}
				
				thIndex = $("#tablist>thead th[name=billId]").index();
				var billIdText = $($checkBoxs.get(index)).val().split("=")[1];
				
				thIndex = $("#tablist>thead th[name=billNo]").index();
				var billNoText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				
				thIndex = $("#tablist>thead th[name=busType]").index();
				var busTypeText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").children("input[name=busType]").val();
				
				thIndex = $("#tablist>thead th[name=billDate]").index();
				var billDateText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				
				thIndex = $("#tablist>thead th[name=amount]").index();
				var totalPriceWithTaxText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				
				thIndex = $("#tablist>thead th[name=cusNo]").index();
				var cusNoText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").children("input[name=cusNo]").val();
				
				thIndex = $("#tablist>thead th[name=invoicedAmount]").index();
				var invoicedAmountText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				
				thIndex = $("#tablist>thead th[name=notInvoicedAmount]").index();
				var notInvoicedAmountText = $checkBoxTdTr.children("td:eq(" + thIndex + ")").text();
				
				var pssSaleInvoiceDetail = new Object();
				pssSaleInvoiceDetail.billId = billIdText;
				pssSaleInvoiceDetail.currInvoiceAmount = currInvoiceAmountText.replace(/,/g,"");
				pssSaleInvoiceDetail.billNo = billNoText;
				pssSaleInvoiceDetail.busType = busTypeText;
				pssSaleInvoiceDetail.billDate = billDateText.replace("-","").replace("-","");
				pssSaleInvoiceDetail.amount = totalPriceWithTaxText.replace(/,/g,"");
				pssSaleInvoiceDetail.cusNo = cusNoText;
				pssSaleInvoiceDetail.invoicedAmount = invoicedAmountText.replace(/,/g,"");
				pssSaleInvoiceDetail.notInvoicedAmount = notInvoicedAmountText.replace(/,/g,"");
				
				pssSaleInvoiceDetails.push(pssSaleInvoiceDetail);
			});
			
			if(flag == false){
				return false;
			}
			
			var url = webPath+'/pssSaleInvoice/batchInvoiceAjax';
			jQuery.ajax({
				url : url,
				data : {
					pssSaleInvoiceDetails : JSON.stringify(pssSaleInvoiceDetails),
					pssSaleInvoices : JSON.stringify(pssSaleInvoices)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.success) {
						window.top.alert(data.msg, 3);
						
						updateTableData();
						$('.footer_loader').remove();
						$("table.table-float-head").remove();
						
						$("input[name=top-invoiceNo]").val("");
						$("input[name=top-invoiceDate]").val(Pss.getCurrentSystemDate("-"));
						$("input[name=top-invoiceAmount]").val("");
						$("input[name=top-invoiceTitle]").val("");
						
						//myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				}, 
				error : function(data) {
					 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
			
		}else{
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "要开票的单据"), 1);
		}
	};
	
	return {
		initPssSaleInvoiceBillList : _initPssSaleInvoiceBillList,
		stopPropagation : _stopPropagation,
		searchPssSaleInvoiceBillList : _searchPssSaleInvoiceBillList,
		addCheckAllEvent : _addCheckAllEvent,
		isCheckAll : _isCheckAll,
		checkCurrentTr : _checkCurrentTr,
		batchInvoice : _batchInvoice
	};
}(window, jQuery);
