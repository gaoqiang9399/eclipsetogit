;
var pssPaymentBill = function(window, $){
	//关闭
	var _cancelInsert = function(){
//		window.location.href=webPath+"/pssPaymentBill/getListPage";
		myclose_click();
	};
	
	//选择源单
	var _selectSourceBill = function(obj){
		var supplierIdVal = $("#formpsspaymentbill0002 input[name='supplierId']").val();
		if(null == supplierIdVal || "" == supplierIdVal){
			alert("请选择供应商", 1);
			return;
		}
		top.flag = false;
		var url = webPath+"/pssSourceDetailBill/getListPageForPay?supplierId=" + supplierIdVal;
//		window.parent.openBigForm(url, '选择源单', selectSourceBillBack);
		top.createShowDialog(url, '选择源单', '900px', '500px', selectSourceBillBack);
	};
	
	//选择源单回调
	function selectSourceBillBack(){
		if(top.flag){
			myCustomScrollbar({
				obj:"#content2",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/getListPageForPayBackAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill0001",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				data : {"sourceBillNos":top.sourceBillNos},//指定参数 (不会过滤掉已经封挡的数据)
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content2", pssPaymentBill.calCurrAdvRecAmtCover);
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content2", true, "notCancelAmt", true, pssPaymentBill.calCurrAdvRecAmtCover);
					
					$('.footer_loader').remove();
				}//方法执行完回调函数（取完数据做处理的时候）
			});
			
			//去掉table头部
			$("table.table-float-head").remove();
			
			$('.pss_detail_list').css('height', 'auto');
		    $('#mCSB_1').css('height', 'auto');
		    $('#mCSB_2').css('height', 'auto');
		}
	};
	
	var _calCurrAdvRecAmtCover = function(amtDecimalDigit){
		if(undefined == amtDecimalDigit){
			amtDecimalDigit = ajaxData.pssConfig.amtDecimalDigit;
		}
		pssFund.calCurrAdvRecAmt("#formpsspaymentbill0002", "currAdvPayAmt", "#content1", "payAmt", "#content2", "currCancelAmt", "fullDiscount", amtDecimalDigit);
	};
	
	//保存
	var _saveOrder = function(obj) {
		LoadingAnimate.start();
		
		//校验：结算账户必输，付款金额必输
		var tabHeadThs = new Array("clearanceAccountName", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content1")){
			LoadingAnimate.stop();
			return;
		}
		
		var currCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content2", ajaxData.pssConfig.amtDecimalDigit);
		if(currCancelAmtVal){
			if(currCancelAmtVal != 'N'){
				var payAmt = pssFund.calRecPayAmt("payAmt", "#content1", ajaxData.pssConfig.amtDecimalDigit);
				if(currCancelAmtVal > payAmt){
					LoadingAnimate.stop();
					alert("本次核销金额不能大于付款金额", 1);
					return;
				}
			}
		}else{
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var suppVal = $("#supp").val();
			if(suppVal == null || suppVal == ""){
				suppVal = $("input[name='supp']").val();
				if(suppVal == null || suppVal == ""){
					LoadingAnimate.stop();
					alert("供应商不能为空！",1);
					return;					
				}
			}
			
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！", 1);
				return;
			}
			var $billDate = $("#formpsspaymentbill0002 input[name='billDate']");
			$billDate.val(billDateVal);
			
			var paymentNoVal = $("#top-paymentNo").val();
			if(paymentNoVal == null || paymentNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！", 1);
				return;
			}
			var $paymentNo = $("#formpsspaymentbill0002 input[name='paymentNo']");
			$paymentNo.val(paymentNoVal);
			
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			var jsonArrBD = new Array();
			jsonArrBD = pssFund.tableJsonDeal("#content1");
			
			var jsonArrRD = new Array();
			jsonArrRD = pssFund.tableJsonDeal("#content2");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArrBD" : JSON.stringify(jsonArrBD),
					"jsonArrRD" : JSON.stringify(jsonArrRD)
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
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "付款单"), 1);
							window.location.href = webPath+"/pssPaymentBill/getById?paymentId="+data.paymentId;
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
	
	//保存并新增
	var _saveAndAddOrder = function(obj) {
		LoadingAnimate.start();
		
		//校验：结算账户必输，付款金额必输
		var tabHeadThs = new Array("clearanceAccountName", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content1")){
			LoadingAnimate.stop();
			return;
		}
		
		var currCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content2", ajaxData.pssConfig.amtDecimalDigit);
		if(currCancelAmtVal){
			if(currCancelAmtVal != 'N'){
				var payAmt = pssFund.calRecPayAmt("payAmt", "#content1", ajaxData.pssConfig.amtDecimalDigit);
				if(currCancelAmtVal > payAmt){
					LoadingAnimate.stop();
					alert("本次核销金额不能大于付款金额", 1);
					return;
				}
			}
		}else{
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var suppVal = $("#supp").val();
			if(suppVal == null || suppVal == ""){
				suppVal = $("input[name='supp']").val();
				if(suppVal == null || suppVal == ""){
					LoadingAnimate.stop();
					alert("供应商不能为空！",1);
					return;					
				}
			}
			
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！", 1);
				return;
			}
			var $billDate = $("#formpsspaymentbill0002 input[name='billDate']");
			$billDate.val(billDateVal);

			var paymentNoVal = $("#top-paymentNo").val();
			if(paymentNoVal == null || paymentNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！", 1);
				return;
			}
			var $paymentNo = $("#formpsspaymentbill0002 input[name='paymentNo']");
			$paymentNo.val(paymentNoVal);

			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			var jsonArrBD = new Array();
			jsonArrBD = pssFund.tableJsonDeal("#content1");
			
			var jsonArrRD = new Array();
			jsonArrRD = pssFund.tableJsonDeal("#content2");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArrBD" : JSON.stringify(jsonArrBD),
					"jsonArrRD" : JSON.stringify(jsonArrRD)
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
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "付款单"), 1);
							window.location.href = webPath+"/pssPaymentBill/input";
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
	
	//审核
	var _auditOrder = function(obj) {
		LoadingAnimate.start();
		
		//校验：结算账户必输，付款金额必输
		var tabHeadThs = new Array("clearanceAccountName", "payAmt");
		if(!Pss.pssTabMustEnterValid(tabHeadThs, "#content1")){
			LoadingAnimate.stop();
			return;
		}
		
		var currCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content2", ajaxData.pssConfig.amtDecimalDigit);
		if(currCancelAmtVal){
			if(currCancelAmtVal != 'N'){
				var payAmt = pssFund.calRecPayAmt("payAmt", "#content1", ajaxData.pssConfig.amtDecimalDigit);
				if(currCancelAmtVal > payAmt){
					LoadingAnimate.stop();
					alert("本次核销金额不能大于付款金额", 1);
					return;
				}
			}
		}else{
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var suppVal = $("#supp").val();
			if(suppVal == null || suppVal == ""){
				suppVal = $("input[name='supp']").val();
				if(suppVal == null || suppVal == ""){
					LoadingAnimate.stop();
					alert("供应商不能为空！",1);
					return;					
				}
			}
			
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！", 1);
				return;
			}
			var $billDate = $("#formpsspaymentbill0002 input[name='billDate']");
			$billDate.val(billDateVal);

			var paymentNoVal = $("#top-paymentNo").val();
			if(paymentNoVal == null || paymentNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！", 1);
				return;
			}
			var $paymentNo = $("#formpsspaymentbill0002 input[name='paymentNo']");
			$paymentNo.val(paymentNoVal);

			var url = webPath+"/pssPaymentBill/auditOrderAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());

			var jsonArrBD = new Array();
			jsonArrBD = pssFund.tableJsonDeal("#content1");
			
			var jsonArrRD = new Array();
			jsonArrRD = pssFund.tableJsonDeal("#content2");
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					"jsonArrBD" : JSON.stringify(jsonArrBD),
					"jsonArrRD" : JSON.stringify(jsonArrRD)
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
							window.location.href = webPath+"/pssPaymentBill/getById?paymentId="+data.paymentId;
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
		
		var paymentNoVal = $("#top-paymentNo").val();
		if(paymentNoVal == null || paymentNoVal == ""){
			LoadingAnimate.stop();
			alert("单据编号不能为空！", 1);
			return;
		}
		var $paymentNo = $("#formpsspaymentbill0002 input[name='paymentNo']");
		$paymentNo.val(paymentNoVal);
		
		var url = webPath+"/pssPaymentBill/reAuditOrderAjax";
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
						var paymentId = $("#formpsspaymentbill0002 input[name='paymentId']").val();
						window.location.href = webPath+"/pssPaymentBill/getById?paymentId="+paymentId;
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
	var _paymentBillInsertPop = function(){
//		window.location.href = webPath+"/pssPaymentBill/input";
		window.parent.openBigForm(webPath+'/pssPaymentBill/input', '新增付款单', updateTableData);
	};
	
	//新增（跳转）
	var _paymentBillInsertLink = function(){
		window.location.href = webPath+"/pssPaymentBill/input";
//		window.parent.openBigForm(webPath+'/pssPaymentBill/input', '新增付款单', updateTableData);
	};
	
	return{
		cancelInsert : _cancelInsert,
		selectSourceBill : _selectSourceBill,
		saveOrder : _saveOrder,
		saveAndAddOrder : _saveAndAddOrder,
		auditOrder : _auditOrder,
		reAuditOrder : _reAuditOrder,
		paymentBillInsertPop : _paymentBillInsertPop,
		paymentBillInsertLink : _paymentBillInsertLink,
		calCurrAdvRecAmtCover : _calCurrAdvRecAmtCover
	};
}(window, jQuery);