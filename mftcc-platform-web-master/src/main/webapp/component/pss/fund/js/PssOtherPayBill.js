;
var pssOtherPayBill = function(window, $){
	//关闭
	var _cancelInsert = function(){
//		window.location.href=webPath+"/pssOtherPayBill/getListPage";
		myclose_click();
	};
	
	var _calCurrAdvRecAmtCover = function(){
		pssFund.calCurrAdvRecAmt("#formpssotherpaybill0002", "currAdvRecAmt", "#content1", "recAmt", "#content2", "currCancelAmt", "fullDiscount");
	};
	
	//保存
	var _saveOrder = function(obj) {
		LoadingAnimate.start();
		//校验：支出类别、金额必输
		var tabHeadThs = new Array("saleType", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
			LoadingAnimate.stop();
			return;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！",1);
				return;
			}
			var $billDate = $("#formpssotherpaybill0002 input[name='billDate']");
			$billDate.val(billDateVal);
			
			var otherPayNoVal = $("#top-otherPayNo").val();
			if(otherPayNoVal == null || otherPayNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！",1);
				return;
			}
			var $otherPayNo = $("#formpssotherpaybill0002 input[name='otherPayNo']");
			$otherPayNo.val(otherPayNoVal);
			
			//供应商
			var supplierIdVal = $("#supp").val();
			if(supplierIdVal !=null && supplierIdVal != ''){
				$("#formpssotherpaybill0002 input[name=supplierId]").val(supplierIdVal);
			}
			
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			var jsonArr = new Array();
			jsonArr = pssFund.tableJsonDeal("#content");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArr" : JSON.stringify(jsonArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(data.data.failed !=''){
							window.top.alert(data.data.failed ,2);
						}else{
							$('button[type="button"]').attr("disabled", true);
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "其他支出单"), 1);
							window.location.href = webPath+"/pssOtherPayBill/getById?otherPayId="+data.otherPayId;
						}
					} else if(data.flag == "error"){
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		}else{
			LoadingAnimate.stop();
		}
	};
	
	//保存并新增
	var _saveAndAddOrder = function(obj) {
		LoadingAnimate.start();
		//校验：支出类别、金额必输
		var tabHeadThs = new Array("saleType", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
			LoadingAnimate.stop();
			return;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！",1);
				return;
			}
			var $billDate = $("#formpssotherpaybill0002 input[name='billDate']");
			$billDate.val(billDateVal);
			
			var otherPayNoVal = $("#top-otherPayNo").val();
			if(otherPayNoVal == null || otherPayNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！",1);
				return;
			}
			var $otherPayNo = $("#formpssotherpaybill0002 input[name='otherPayNo']");
			$otherPayNo.val(otherPayNoVal);
			
			//供应商
			var supplierIdVal = $("#supp").val();
			if(supplierIdVal !=null && supplierIdVal != ''){
				$("#formpssotherpaybill0002 input[name=supplierId]").val(supplierIdVal);
			}
			
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			var jsonArr = new Array();
			jsonArr = pssFund.tableJsonDeal("#content");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArr" : JSON.stringify(jsonArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(data.data.failed !=''){
							window.top.alert(data.data.failed ,2);
						}else{
							$('button[type="button"]').attr("disabled", true);
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "其他支出单"), 1);
							window.location.href = webPath+"/pssOtherPayBill/input";
						}
					} else if(data.flag == "error"){
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		}else{
			LoadingAnimate.stop();
		}
	};
	
	//审核
	var _auditOrder = function(obj) {
		LoadingAnimate.start();
		//校验：支出类别、金额必输
		var tabHeadThs = new Array("saleType", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content")){
			LoadingAnimate.stop();
			return;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！",1);
				return;
			}
			var $billDate = $("#formpssotherpaybill0002 input[name='billDate']");
			$billDate.val(billDateVal);
			
			var otherPayNoVal = $("#top-otherPayNo").val();
			if(otherPayNoVal == null || otherPayNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！",1);
				return;
			}
			var $otherPayNo = $("#formpssotherpaybill0002 input[name='otherPayNo']");
			$otherPayNo.val(otherPayNoVal);
			
			//供应商
			var supplierIdVal = $("#supp").val();
			if(supplierIdVal !=null && supplierIdVal != ''){
				$("#formpssotherpaybill0002 input[name=supplierId]").val(supplierIdVal);
			}
			
			var url = webPath+"/pssOtherPayBill/auditOrderAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			var jsonArr = new Array();
			jsonArr = pssFund.tableJsonDeal("#content");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArr" : JSON.stringify(jsonArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(data.data.failed !=''){
							alert(data.data.failed, 2);
						}else{
							$('button[type="button"]').attr("disabled", true);
							alert(top.getMessage("SUCCEED_OPERATION"), 1);
							window.location.href = webPath+"/pssOtherPayBill/getById?otherPayId="+data.otherPayId;
						}
					} else if(data.flag == "error"){
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage(
							"ERROR_REQUEST_URL", url), 0);
				}
			});
		}else{
			LoadingAnimate.stop();
		}
	};
	
	//反审核
	var _reAuditOrder = function(obj) {
		LoadingAnimate.start();
		var otherPayNoVal = $("#top-otherPayNo").val();
		if(otherPayNoVal == null || otherPayNoVal == ""){
			LoadingAnimate.stop();
			alert("单据编号不能为空！",1);
			return;
		}
		var $otherPayNo = $("#formpssotherpaybill0002 input[name='otherPayNo']");
		$otherPayNo.val(otherPayNoVal);
		
		var url = webPath+"/pssOtherPayBill/reAuditOrderAjax";
		var dataParm = JSON.stringify($(obj).serializeArray());
		
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParm
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					if(data.data.failed !=''){
						alert(data.data.failed, 2);
					}else{
						$('button[type="button"]').attr("disabled", true);
						alert(top.getMessage("SUCCEED_OPERATION"), 1);
						window.location.href = webPath+"/pssOtherPayBill/getById?otherPayId="+data.otherPayId;
					}
				} else if(data.flag == "error"){
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage(
						"ERROR_REQUEST_URL", url), 0);
			}
		});
	};
	
	//新增（弹出）
	var _otherPayBillInsertPop = function(){
		window.parent.openBigForm(webPath+'/pssOtherPayBill/input', '新增其他支出单', updateTableData);
	};
	
	//新增（跳转）
	var _otherPayBillInsertLink = function(){
		window.location.href = webPath+"/pssOtherPayBill/input";
	};
	
	var _refreshFormData = function (){
		var amount = 0;

		var saleTypeIdThIndex = $("#tablist>thead th[name=saleType]").index();

		var detailPayAmtThIndex = $("#tablist>thead th[name=payAmt]").index();

		$("#tablist>tbody tr").each(function(trIndex, tr) {

			var $saleTypeIdTd = $(tr).children("td:eq(" + saleTypeIdThIndex + ")");
			var saleType = $saleTypeIdTd.children("input").val();

			if(saleType.length > 0){
				var $detailPayAmtTd = $(tr).children("td:eq(" + detailPayAmtThIndex + ")");

				var detailPayAmt = $detailPayAmtTd.text();
				if(detailPayAmt.length > 0){
					detailPayAmt = Pss.ifMoneyToNumber(detailPayAmt);
					amount = amount + parseFloat(detailPayAmt);
				}
			}

		});

		var $amount = $("#formpssotherpaybill0002 input[name=payAmt]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));

	};
	
	return{
		cancelInsert : _cancelInsert,
		saveOrder : _saveOrder,
		saveAndAddOrder : _saveAndAddOrder,
		auditOrder : _auditOrder,
		reAuditOrder : _reAuditOrder,
		otherPayBillInsertPop : _otherPayBillInsertPop,
		otherPayBillInsertLink : _otherPayBillInsertLink,
		calCurrAdvRecAmtCover : _calCurrAdvRecAmtCover,
		refreshFormData : _refreshFormData
	};
}(window, jQuery);