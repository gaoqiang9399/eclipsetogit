var PssOtherRecBillInput = function(window, $){
	var _otherRecNo = '';
	var _otherRecId = '';
	var _auditSts = '';
	var _init = function(){
		//客户
		$("#top-cus").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false,//多选
			items : ajaxData.cus,
				changeCallback : function (obj, elem) {
				$("#formpssotherrecbill0002 input[name=cusName]").val(obj.data("text"));
				$("#formpssotherrecbill0002 input[name=cusNo]").val(obj.val());
			} 
		});
		
		//结算账户
		$("#formpssotherrecbill0002 input[name=clearanceAccountId]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//单选
			items : ajaxData.settlementAccount,
			changeCallback : function (obj, elem) {
				$("#formpssotherrecbill0002 input[name=clearanceAccountName]").val(obj.data("text"));
				$("#formpssotherrecbill0002 input[name=clearanceAccountId]").val(obj.val());				
			}
		});
		
		//日期控件
	    $('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: true,
				choose:function(data){
				}	
			});
		});

	    if(this.otherRecId.length == 0){
	    	$("#btnSaveAndAddOtherRecBill").removeClass("hide");
	    	$("#btnSaveOtherRecBill").removeClass("hide");
	    	$("#btnAuditOtherRecBill").removeClass("hide");
	    }else{
	    	if(this.auditSts == "0"){
	    		$("#btnAddOtherRecBill").removeClass("hide");
	    		$("#btnSaveOtherRecBill").removeClass("hide");
	    		$("#btnAuditOtherRecBill").removeClass("hide");
	    	}else if(this.auditSts == "1"){
	    		$("#btnAddOtherRecBill").removeClass("hide");
	    		$("#btnReverseAuditOtherRecBill").removeClass("hide");	
	    	}
	    	$("#btnPrintOtherRecBill").removeClass("hide");
	    }
	        
	    myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	//url : webPath+"/pssOtherRecDetailBill/findByPageAjax?otherRecNo="+this.otherRecNo,//列表数据查询的url
	    	url : webPath+"/pssOtherRecDetailBill/findByPageAjax",
	    	tableId : "tablepssotherrecdetailbill0001",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 30,//加载默认行数(不填为系统默认行数)
	    	data : {"otherRecNo" : this.otherRecNo},
	    	/* ownHeight : true, */
	    	callback : function(options, data) {
	    		Pss.addRowOperateEvent("sequence",null,null,null,refreshFormData);
	    		
	    		_addBuyTypeEvent("buyTypeName", "buyType");
	    		
	    		var stringThs = new Array("memo");
	    		Pss.addStringEvent(stringThs);
	    		
	    		var moneyThs = new Array("recAmt");
	    		Pss.addMoneyEvent(moneyThs,ajaxData.pssConfig,'',refreshFormData);
	    		
				var tabHeadThs = new Array("buyTypeName", "recAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
	    	}
	    });
	    
	    $('.footer_loader').remove();
	    $('.table-float-head').remove();
	    
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
	
	function refreshFormData(){
		var amount = 0;
	
		var buyTypeIdThIndex = $("#tablist>thead th[name=buyType]").index();
		
		var detailRecAmtThIndex = $("#tablist>thead th[name=recAmt]").index();
		
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			
			var $buyTypeIdTd = $(tr).children("td:eq(" + buyTypeIdThIndex + ")");
			var buyType = $buyTypeIdTd.children("input[name=buyType]").val();
			
			if(buyType.length > 0){
				var $detailRecAmtTd = $(tr).children("td:eq(" + detailRecAmtThIndex + ")");

				var detailRecAmt = $detailRecAmtTd.text();
				if(detailRecAmt.length > 0){
					detailRecAmt = Pss.ifMoneyToNumber(detailRecAmt);
					amount = amount + parseFloat(detailRecAmt);
				}
			}
			
		});
		
		var $amount = $("#formpssotherrecbill0002 input[name=recAmt]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		
	}
	
	var _addBuyTypeEvent = function (buyTypeNameTh, buyTypeIdTh){
		var buyTypeNameThIndex = $("#tablist>thead th[name='" + buyTypeNameTh + "']").index();
		var buyTypeIdThIndex = $("#tablist>thead th[name='" + buyTypeIdTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $buyTypeNameTd = $(tr).children("td:eq(" + buyTypeNameThIndex + ")");
			var $input = $("<input style='display:none;' id='buyTypeInput" + index + "'></input>");
			$buyTypeNameTd.append($input);
			
			var $buyTypeIdTd = $(tr).children("td:eq(" + buyTypeIdThIndex + ")");
			var buyTypeId = $buyTypeIdTd.children("input[name='" + buyTypeIdTh + "']").val();
			if (buyTypeId.length > 0) {
				$input.val(buyTypeId);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + buyTypeNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $buyTypeIdTd = $(this).parent("tr").children("td:eq(" + buyTypeIdThIndex + ")");
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='buyTypeInput" + index + "'></input>");
					$(this).append($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.buyType,
						changeCallback : function (obj, elem) {
							$buyTypeIdTd.children("input[name='" + buyTypeIdTh + "']").val(obj.val());
							Pss.popsSelectSelected($td);
						}
					});
					Pss.addPopsSelectClick($td);
				}
				/*var tdTop = $(this).position().top;
				$(this).children("div .pops-select").css({'top':tdTop});*/
			}).click();
		});
	};
	
	//填充form表单数据
	function fillFormData() {
		var billDateVal = $("#top-billDate").val();
		var $billDate = $("#formpssotherrecbill0002 input[name=billDate]");
		$billDate.val(billDateVal);
		
		var otherRecNoVal = $("#top-otherRecNo").val();
		var $otherRecNo = $("#formpssotherrecbill0002 input[name=otherRecNo]");
		$otherRecNo.val(otherRecNoVal);
	};
	
	//获取table分录数据数组
	function getTableData(){
		var pssOtherRecDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr){
			var pssOtherRecDetail = new Object();
			
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssOtherRecDetail[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssOtherRecDetail[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssOtherRecDetail[$(th).attr("name")] = tdText;
				}
			});
			
			pssOtherRecDetails.push(pssOtherRecDetail);
		});
		return pssOtherRecDetails;
	};
	
	//验证提交数据
	function validateSubmitData(pssOtherRecDetails) {
		/*var $cusNo = $("#formpssotherrecbill0002 input[name=cusNo]");
		if ($cusNo.val().length == 0) {
			DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "客户"), 3000);
			return false;
		}*/
		
		var $otherRecNo = $("#formpssotherrecbill0002 input[name=otherRecNo]");
		if ($.trim($otherRecNo.val()).length == 0) {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "单据编号"), 3000);
			$("#top-otherRecNo").focus();
			return false;
		}
		
		var rightCount = 0;
		if (pssOtherRecDetails != null && pssOtherRecDetails.length > 0){
			for (var index in pssOtherRecDetails){
				if (pssOtherRecDetails[index].buyType != null && pssOtherRecDetails[index].buyType.length > 0){
					
					if (pssOtherRecDetails[index].recAmt == null || pssOtherRecDetails[index].recAmt.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "收入类别为: " + pssOtherRecDetails[index].buyTypeName + " 金额"), 3000);
						return false;
					} else if (parseFloat(pssOtherRecDetails[index].recAmt) < 0) {
						DIALOG.tip("收入类别为:  " + pssOtherRecDetails[index].buyTypeName + " 金额不能小于0！", 3000);
						return false;
					}
					
					rightCount++;
				}
			}
			
			if (rightCount == 0) {
				DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "收入信息"), 3000);
				return false;
			}
		}else {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "收入信息"), 3000);
			return false;
		}
		
		return true;
	};
	
	//保存
	function _saveOtherRecBill(obj){
		LoadingAnimate.start();
		fillFormData();
		var pssOtherRecDetails = getTableData();
		if(!validateSubmitData(pssOtherRecDetails)) {
			LoadingAnimate.stop();
			return false;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssOtherRecDetailsJson : JSON.stringify(pssOtherRecDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						$('button[type="button"]').attr("disabled", true);
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssOtherRecBill/getDetailPage?otherRecId=" + data.otherRecId;
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
		LoadingAnimate.stop();
	};
	
	//保存并新增
	function _saveAndAddOtherRecBill(obj){
		LoadingAnimate.start();
		fillFormData();
		var pssOtherRecDetails = getTableData();
		if(!validateSubmitData(pssOtherRecDetails)) {
			LoadingAnimate.stop();
			return false;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
					
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssOtherRecDetailsJson : JSON.stringify(pssOtherRecDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						$('button[type="button"]').attr("disabled", true);
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssOtherRecBill/input";
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}else{
			LoadingAnimate.stop();
		}
		
		LoadingAnimate.stop();
	};
	
	//新增
	function _addOtherRecBill(){
		window.location.href = webPath+"/pssOtherRecBill/input";
	};
	
	//审核
	function _auditOtherRecBill(obj){
		LoadingAnimate.start();
		fillFormData();
		var pssOtherRecDetails = getTableData();
		if(!validateSubmitData(pssOtherRecDetails)) {
			LoadingAnimate.stop();
			return false;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/pssOtherRecBill/auditOtherRecBillAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssOtherRecDetailsJson : JSON.stringify(pssOtherRecDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						$('button[type="button"]').attr("disabled", true);
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssOtherRecBill/getDetailPage?otherRecId=" + data.otherRecId;
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
		LoadingAnimate.stop();
	};
	
	//反审核
	function _reverseAuditOtherRecBill(){
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssOtherRecBill/reverseAuditOtherRecBillAjax?otherRecId="+this.otherRecId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					$('button[type="button"]').attr("disabled", true);
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssOtherRecBill/getDetailPage?otherRecId=" + data.otherRecId;
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	};
	
	var _printBill = function(){
		
		window.top.alert("请确认是否打印其他收入单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssOtherRecBillAjax?otherRecId=" + this.otherRecId,
				data : {
					fileName : 'templateFile_pssqtsrd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssOtherRecBillObj = $.parseJSON(data.pssOtherRecBill);
						mfPageOffice.openPageOffice(pssOtherRecBillObj);
					}else{
						window.top.alert(data.msg, 0);
					}
				}
			});
		});
		
	};
	
	return {
		init : _init,
		saveAndAddOtherRecBill : _saveAndAddOtherRecBill,
		addOtherRecBill : _addOtherRecBill,
		saveOtherRecBill : _saveOtherRecBill,
		auditOtherRecBill : _auditOtherRecBill,
		reverseAuditOtherRecBill : _reverseAuditOtherRecBill,
		otherRecNo : _otherRecNo ,
		otherRecId : _otherRecId ,
		auditSts : _auditSts,
		printBill : _printBill
	};
}(window, jQuery);