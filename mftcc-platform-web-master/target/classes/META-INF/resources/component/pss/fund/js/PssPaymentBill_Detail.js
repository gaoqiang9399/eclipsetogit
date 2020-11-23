;
var pssPaymentBillDetail = function(window, $){
	var _init = function(){
		//初始赋值
		$("input[name=supp]").val($("#formpsspaymentbill0002 input[name='supplierId']").val());
		$("input[name=top-PayerId]").val($("#formpsspaymentbill0002 input[name='payerId']").val());
		$("#top-billDate").val($("#formpsspaymentbill0002 input[name='billDate']").val());
		var $paymentNoVal = $("#formpsspaymentbill0002 input[name='paymentNo']").val();
		$("#top-paymentNo").val($paymentNoVal);
		if(auditSts == '1'){
			$("#formpsspaymentbill0002 input[name='memo']").attr("readonly","readonly");
			$("#formpsspaymentbill0002 input[name='fullDiscount']").attr("readonly","readonly");
		}
		
		//供应商新组件
		$("input[name=supp]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				$("input[name=supplierId]").val(obj.val());
				$("input[name=supplierName]").val(obj.data("text"));
			}
		});
		var suppPopsDiv = $("#supp").nextAll("div .pops-value").get(0);
		if(suppPopsDiv != undefined){
			var suppLeft = $(suppPopsDiv).position().left;
			var suppSelectDiv = $("#supp").next("div .pops-select").get(0);
			$(suppPopsDiv).width(120);
			$(suppSelectDiv).css({'left':suppLeft});
			$(suppSelectDiv).width(228); //selectDiv比PopsDiv长28px
			$(suppSelectDiv).find("li").width(180);
			$(suppSelectDiv).find("div .pops-search").width(228);
			
			$("input[name=popssupp]").remove();
		}
		
		//收款人新组件-操作员
		$("input[name=top-PayerId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.sysUser,
			changeCallback : function (obj, elem) {
				$("input[name=payerId]").val(obj.val());
				$("input[name=payerName]").val(obj.data("text"));
			}
		});
		var topPayerIdPopsDiv = $("#top-PayerId").nextAll("div .pops-value").get(0);
		var topPayerIdLeft = $(topPayerIdPopsDiv).position().left;
		var topPayerIdSelectDiv = $("#top-PayerId").next("div .pops-select").get(0);
		$(topPayerIdPopsDiv).width(80);
		$(topPayerIdSelectDiv).css({'left':topPayerIdLeft});
		$(topPayerIdSelectDiv).width(224); //selectDiv比PopsDiv长28px
		$(topPayerIdSelectDiv).find("li").width(80);
		if(auditSts == '1'){
			$("input[name=popstop-PayerId]").remove();
		}
		
		myCustomScrollbar({
			obj:"#content2",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/getSourceDetailBillListForRecPayCancelAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			data:{"billNo":$paymentNoVal},
			ownHeight:true,
			callback:function(options, data){
				if(auditSts != '1'){
					pssFund.addRowOperateEvent("sequence", "#content2", pssPaymentBill.calCurrAdvRecAmtCover);
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content2", true, "notCancelAmt", true, pssPaymentBill.calCurrAdvRecAmtCover);
				}
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content2");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();

		myCustomScrollbar({
			obj:"#content1",//页面内容绑定的id
			url:webPath+"/pssPaymentDetailBill/getPaymentDetailBillListAjax",//列表数据查询的url
			tableId:"tablepsspaymentdetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			data:{"paymentNo":$paymentNoVal},
			ownHeight:true,
			callback:function(options, data){
				pssFund.addAccountEvent("clearanceAccountName", "clearanceAccountId", "#content1");
				pssFund.addClearAccTypeEvent("clearanceAccountType", "#content1");
				if(auditSts != '1'){
					pssFund.addRowOperateEvent("sequence", "#content1", pssPaymentBill.calCurrAdvRecAmtCover);
					pssFund.addMoneyEvent("payAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", false, "", true, pssPaymentBill.calCurrAdvRecAmtCover);
					var stringThs = new Array("clearanceAccountNum", "memo");
					pssFund.addStringEvent(stringThs, "#content1");
				}
				var tabHeadThs = new Array("clearanceAccountName", "payAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content1");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		if(auditSts != '1'){
			$('.pss-date').on('click', function() {
				fPopUpCalendarDlg({
					isclear : true,
					choose : function(data) {
					}
				});
			});
		}
		
		$("table.table-float-head").remove();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    $('#mCSB_2').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	var _printBill = function(){
		
		window.top.alert("请确认是否打印付款单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssPaymentBillAjax?paymentId=" + paymentId,
				data : {
					fileName : 'templateFile_pssfkd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssPaymentBillObj = $.parseJSON(data.pssPaymentBill);
						mfPageOffice.openPageOffice(pssPaymentBillObj);
					}else{
						window.top.alert(data.msg, 0);
					}
				}
			});
		});
		
	};
	
	return {
		init : _init,
		printBill : _printBill
	};
}(window, jQuery);