;
var pssReceiptBillDetail = function(window, $){
	var _init = function(){
		//初始赋值
		$("input[name=cus]").val($("#formpssreceiptbill0002 input[name='cusNo']").val());
		$("input[name=top-PayeeId]").val($("#formpssreceiptbill0002 input[name='payeeId']").val());
		$("#top-billDate").val($("#formpssreceiptbill0002 input[name='billDate']").val());
		var $receiptNoVal = $("#formpssreceiptbill0002 input[name='receiptNo']").val();
		$("#top-receiptNo").val($receiptNoVal);
		if(auditSts == '1'){
			$("#formpssreceiptbill0002 input[name='memo']").attr("readonly","readonly");
			$("#formpssreceiptbill0002 input[name='fullDiscount']").attr("readonly","readonly");
		}
		
		//客户新组件
		$("input[name=cus]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				$("input[name=cusNo]").val(obj.val());
				$("input[name=cusName]").val(obj.data("text"));
			}
		});
		var cusPopsDiv = $("#cus").nextAll("div .pops-value").get(0);
		if(cusPopsDiv != undefined){
			var cusLeft = $(cusPopsDiv).position().left;
			var cusSelectDiv = $("#cus").next("div .pops-select").get(0);
			$(cusPopsDiv).width(120);
			$(cusSelectDiv).css({'left':cusLeft});
			$(cusSelectDiv).width(228); //selectDiv比PopsDiv长28px
			$(cusSelectDiv).find("li").width(180);
			$(cusSelectDiv).find("div .pops-search").width(228);
			
			$("input[name=popscus]").remove();
		}
		
		//收款人新组件-操作员
		$("input[name=top-PayeeId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.sysUser,
			changeCallback : function (obj, elem) {
				$("input[name=payeeId]").val(obj.val());
				$("input[name=payeeName]").val(obj.data("text"));
			}
		});
		var topPayeeIdPopsDiv = $("#top-PayeeId").nextAll("div .pops-value").get(0);
		var topPayeeIdLeft = $(topPayeeIdPopsDiv).position().left;
		var topPayeeIdSelectDiv = $("#top-PayeeId").next("div .pops-select").get(0);
		$(topPayeeIdPopsDiv).width(80);
		$(topPayeeIdSelectDiv).css({'left':topPayeeIdLeft});
		$(topPayeeIdSelectDiv).width(224); //selectDiv比PopsDiv长28px
		$(topPayeeIdSelectDiv).find("li").width(80);
		if(auditSts == '1'){
			$("input[name=popstop-PayeeId]").remove();
		}
		
		myCustomScrollbar({
			obj:"#content2",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/getSourceDetailBillListForRecPayCancelAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			data:{"billNo":$receiptNoVal},
			ownHeight:true,
			callback:function(options, data){
				if(auditSts != '1'){
					pssFund.addRowOperateEvent("sequence", "#content2", pssReceiptBill.calCurrAdvRecAmtCover);
					pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content2", true, "notCancelAmt", true, pssReceiptBill.calCurrAdvRecAmtCover);
				}
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content2");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();

		myCustomScrollbar({
			obj:"#content1",//页面内容绑定的id
			url:webPath+"/pssReceiptDetailBill/getReceiptDetailBillListAjax",//列表数据查询的url
			tableId:"tablepssreceiptdetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			data:{"receiptNo":$receiptNoVal},
			ownHeight:true,
			callback:function(options, data){
				pssFund.addAccountEvent("clearanceAccountName", "clearanceAccountId", "#content1");
				pssFund.addClearAccTypeEvent("clearanceAccountType", "#content1");
				if(auditSts != '1'){
					pssFund.addRowOperateEvent("sequence", "#content1", pssReceiptBill.calCurrAdvRecAmtCover);
					pssFund.addMoneyEvent("recAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", false, "", true, pssReceiptBill.calCurrAdvRecAmtCover);
					var stringThs = new Array("clearanceAccountNum", "memo");
					pssFund.addStringEvent(stringThs, "#content1");
				}else{
					$("div .pops-value").attr('disabled', true);
				}
				var tabHeadThs = new Array("clearanceAccountName", "recAmt");
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
		
		window.top.alert("请确认是否打印收款单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssReceiptBillAjax?receiptId=" + receiptId,
				data : {
					fileName : 'templateFile_pssskd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssReceiptBillObj = $.parseJSON(data.pssReceiptBill);
						mfPageOffice.openPageOffice(pssReceiptBillObj);
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