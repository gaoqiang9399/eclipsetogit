;
var pssCancelVerificationBill = function(window, $){
	//关闭
	var _cancelInsert = function(){
//		window.location.href=webPath+"/pssCancelVerificationBill/getListPage";
		myclose_click();
	};
	
	//选择预收单据
	var _selectBefRecSourceBill = function(obj){
		var cusNoVal = $("#formpsscancelverificationbill0002 input[name='cusNo']").val();
		if(null == cusNoVal || "" == cusNoVal){
			alert("请选择客户", 1);
			return;
		}
		top.flag = false;
		var url = webPath+"/pssSourceDetailBill/getListPageForBefRec?cusNo=" + cusNoVal;
//		window.parent.openBigForm(url, '选择源单', selectBefRecSourceBillBack);
		top.createShowDialog(url, '选择源单', '900px', '500px', selectBefRecSourceBillBack);
	};
	
	//选择预收单据回调
	function selectBefRecSourceBillBack(){
		if(top.flag){
			myCustomScrollbar({
				obj:"#content1",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/getListPageForBefRecBackAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill00001",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				data : {"sourceBillNos":top.sourceBillNos},//指定参数 (不会过滤掉已经封挡的数据)
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content1");
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", true, "notCancelAmt");
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
	
	//选择应收单据
	var _selectShouldRecSourceBill = function(obj){
		var cusNoVal = $("#formpsscancelverificationbill0002 input[name='cusNo']").val();
		if(null == cusNoVal || "" == cusNoVal){
			alert("请选择客户", 1);
			return;
		}
		top.flag = false;
		var url = webPath+"/pssSourceDetailBill/getListPageForRec?cusNo=" + cusNoVal;
//		window.parent.openBigForm(url, '选择源单', selectShouldRecSourceBillBack);
		top.createShowDialog(url, '选择源单', '900px', '500px', selectShouldRecSourceBillBack);
	};
	
	//选择应收单据回调
	function selectShouldRecSourceBillBack(){
		if(top.flag){
			var id = "";
			var tableId = "";
			if(cancelType == "1"){
				id = "#content2";
				tableId = "tablepsssourcedetailbill0002";
			}else if(cancelType == "3"){
				id = "#content1";
				tableId = "tablepsssourcedetailbill0001";
			}
			myCustomScrollbar({
				obj:id,//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/getListPageForRecBackAjax",//列表数据查询的url
				tableId:tableId,//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				data : {"sourceBillNos":top.sourceBillNos},//指定参数 (不会过滤掉已经封挡的数据)
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", id);
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, id, true, "notCancelAmt");
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
	
	//选择预付单据
	var _selectBefPaySourceBill = function(obj){
		var supplierIdVal = $("#formpsscancelverificationbill0002 input[name='supplierId']").val();
		if(null == supplierIdVal || "" == supplierIdVal){
			alert("请选择供应商", 1);
			return;
		}
		top.flag = false;
		var url = webPath+"/pssSourceDetailBill/getListPageForBefPay?supplierId=" + supplierIdVal;
//		window.parent.openBigForm(url, '选择源单', selectBefPaySourceBillBack);
		top.createShowDialog(url, '选择源单', '900px', '500px', selectBefPaySourceBillBack);
	};
	
	//选择预收单据回调
	function selectBefPaySourceBillBack(){
		if(top.flag){
			myCustomScrollbar({
				obj:"#content1",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/getListPageForBefPayBackAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill00001",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				data : {"sourceBillNos":top.sourceBillNos},//指定参数 (不会过滤掉已经封挡的数据)
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content1");
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", true, "notCancelAmt");
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
	
	//选择应付单据
	var _selectShouldPaySourceBill = function(obj){
		var supplierIdVal = $("#formpsscancelverificationbill0002 input[name='supplierId']").val();
		if(null == supplierIdVal || "" == supplierIdVal){
			alert("请选择供应商", 1);
			return;
		}
		top.flag = false;
		var url = webPath+"/pssSourceDetailBill/getListPageForPay?supplierId=" + supplierIdVal;
//		window.parent.openBigForm(url, '选择源单', selectShouldPaySourceBillBack);
		top.createShowDialog(url, '选择源单', '900px', '500px', selectShouldPaySourceBillBack);
	};
	
	//选择应收单据回调
	function selectShouldPaySourceBillBack(){
		if(top.flag){
			myCustomScrollbar({
				obj:"#content2",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/getListPageForPayBackAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill0002",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				data : {"sourceBillNos":top.sourceBillNos},//指定参数 (不会过滤掉已经封挡的数据)
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content2");
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content2", true, "notCancelAmt");
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
	
	//保存
	var _saveOrder = function(obj) {
		LoadingAnimate.start();
		
		var befCurrCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content1", ajaxData.pssConfig.amtDecimalDigit);
		if(!befCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		var shouldCurrCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content2", ajaxData.pssConfig.amtDecimalDigit);
		if(!shouldCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		if(befCurrCancelAmtVal != 'N' && shouldCurrCancelAmtVal != 'N' && befCurrCancelAmtVal != shouldCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("核销金额不相等", 1);
			return;
		}
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			/*if(cancelType == "1"){
				
			}else if(cancelType == "2"){
				
			}else if(cancelType == "3"){
				
			}*/
			
			var billDateVal = $("#top-billDate").val();
			if(billDateVal == null || billDateVal == ""){
				LoadingAnimate.stop();
				alert("单据日期不能为空！",1);
				return;
			}
			var $billDate = $("#formpsscancelverificationbill0002 input[name='billDate']");
			$billDate.val(billDateVal);

			var cancelNoVal = $("#top-cancelNo").val();
			if(cancelNoVal == null || cancelNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！",1);
				return;
			}
			var $cancelNo = $("#formpsscancelverificationbill0002 input[name='cancelNo']");
			$cancelNo.val(cancelNoVal);

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
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "核销单"), 1);
							window.location.href = webPath+"/pssCancelVerificationBill/getById?cancelId="+data.cancelId;
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
		
		var befCurrCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content1", ajaxData.pssConfig.amtDecimalDigit);
		if(!befCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		var shouldCurrCancelAmtVal = pssFund.calCancelAmt("sourceBillNo", "currCancelAmt", "#content2", ajaxData.pssConfig.amtDecimalDigit);
		if(!shouldCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("请填写本次核销金额且不能为0", 1);
			return;
		}
		if(befCurrCancelAmtVal != 'N' && shouldCurrCancelAmtVal != 'N' && befCurrCancelAmtVal != shouldCurrCancelAmtVal){
			LoadingAnimate.stop();
			alert("核销金额不相等", 1);
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
			var $billDate = $("#formpsscancelverificationbill0002 input[name='billDate']");
			$billDate.val(billDateVal);

			var cancelNoVal = $("#top-cancelNo").val();
			if(cancelNoVal == null || cancelNoVal == ""){
				LoadingAnimate.stop();
				alert("单据编号不能为空！",1);
				return;
			}
			var $cancelNo = $("#formpsscancelverificationbill0002 input[name='cancelNo']");
			$cancelNo.val(cancelNoVal);

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
							window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "核销单"), 1);
							window.location.href = webPath+"/pssCancelVerificationBill/input";
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
	
	//新增（弹出）
	var _cancelVerificationBillInsertPop = function(){
//		window.location.href = webPath+"/pssCancelVerificationBill/input";
		window.parent.openBigForm(webPath+'/pssCancelVerificationBill/input', '新增核销单', updateTableData);
	};
	
	//新增（跳转）
	var _cancelVerificationBillInsertLink = function(){
		window.location.href = webPath+"/pssCancelVerificationBill/input";
//		window.parent.openBigForm(webPath+'/pssCancelVerificationBill/input', '新增核销单', updateTableData);
	};
	
	return{
		cancelInsert : _cancelInsert,
		selectBefRecSourceBill : _selectBefRecSourceBill,
		selectShouldRecSourceBill : _selectShouldRecSourceBill,
		selectBefPaySourceBill : _selectBefPaySourceBill,
		selectShouldPaySourceBill : _selectShouldPaySourceBill,
		saveOrder : _saveOrder,
		saveAndAddOrder : _saveAndAddOrder,
		cancelVerificationBillInsertPop : _cancelVerificationBillInsertPop,
		cancelVerificationBillInsertLink : _cancelVerificationBillInsertLink
	};
}(window, jQuery);