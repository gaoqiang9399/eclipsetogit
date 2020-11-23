var PssSaleReturnBill_Detail = function(window, $) {
	var _init = function() {
		var cusNo = $("#formdl_psssalereturnbill02 input[name=cusNo]").val();
		//客户组件
		var cusJsonArray = ajaxData.cus.slice();
		if (disableCustomerMap !== null && disableCustomerMap !== undefined && disableCustomerMap !== "") {
			var disableCustomer = disableCustomerMap[cusNo];
			if (disableCustomer !== null && disableCustomer !== undefined) {
				var disableCustomerJson = { "id": disableCustomer.cusNo,
						 "name": disableCustomer.cusName,
						 "cusGrade": disableCustomer.cusGrade,
						 "salerNo": disableCustomer.salerNo,
						 "salerName": disableCustomer.salerName,
						 "accountsReceivedBalance": disableCustomer.accountsReceivedBalance,
						 "enabledStatus": disableCustomer.enabledStatus };
				cusJsonArray.push(disableCustomerJson);
			}
		}
		$("#top-cus").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false,//多选
			items : cusJsonArray,
				changeCallback : function (obj, elem) {
				$("#formdl_psssalereturnbill02 input[name=cusNo]").val(obj.val());
				var customer = customerMap[obj.val()];
				if (customer !== null && customer !== undefined && customer !== '') {
					$("#formdl_psssalereturnbill02 input[name=cusGrade]").val(customer.cusGrade);
					var $topSalerPopsValueDiv = $("#top-saler").next("div.pops-select").next("div.pops-value");
					var $salerNo = $("#formdl_psssalereturnbill02 input[name=salerNo]");
					var salerNo = customer.salerNo;
					if (salerNo !== null && salerNo !== undefined && salerNo !== '') {
						$topSalerPopsValueDiv.text(customer.salerName);
						$salerNo.val(salerNo);
					} else {
						$topSalerPopsValueDiv.text("(空)");
						$salerNo.val("-1");
					}
				}
			} 
		});
		var cusPopsDiv = $("#top-cus").nextAll("div .pops-value").get(0);
		var cusLeft = $(cusPopsDiv).position().left;
		var cusSelectDiv = $("#top-cus").next("div .pops-select").get(0);
		$(cusPopsDiv).width(100);
		$(cusSelectDiv).css({'left':cusLeft});
		$(cusSelectDiv).width(228); //selectDiv比PopsDiv长28px
		$(cusSelectDiv).find("li").width(180);
		$(cusSelectDiv).find("div .pops-search").width(228);
		
		if (cusNo !== null && cusNo !== undefined && cusNo !== '') {
			$("#formdl_psssalereturnbill02 input[name=cusGrade]").val(customerMap[cusNo].cusGrade);
		}
		
		//销售人员组件
		$("#top-saler").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false,//多选
			items : ajaxData.saler,
				changeCallback : function (obj, elem) {
				$("#formdl_psssalereturnbill02 input[name=salerNo]").val(obj.val());
			} 
		});
		var salerPopsDiv = $("#top-saler").nextAll("div .pops-value").get(0);
		var salerLeft = $(salerPopsDiv).position().left;
		var salerSelectDiv = $("#top-saler").next("div .pops-select").get(0);
		$(salerPopsDiv).width(80);
		$(salerSelectDiv).css({'left':salerLeft});
		$(salerSelectDiv).width(128); //selectDiv比PopsDiv长28px
		$(salerSelectDiv).find("li").width(80);
		$(salerSelectDiv).find("div .pops-search").width(128);
		
		//结算账户组件
		$("#formdl_psssalereturnbill02 input[name=settlementAccount]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//单选
			items : ajaxData.settlementAccount
		});
		
		Pss.dateClickEvent();
		
		//录入销售费用
		top.pssBuySaleExpArr = new Array();
		$("#inputSaleExpense").on('click', function() {
			Pss.insertPssBuySaleExpBill($("#top-saleReturnBillNo").val(), _updateSaleExpense);
		});
		
		if (saleReturnBillId.length == 0) {
			//新增
			$("#btnSaveAndAddSaleReturnBill").removeClass("hide");
			$("#btnSaveSaleReturnBill").removeClass("hide");
			$("#btnAuditSaleReturnBill").removeClass("hide");
		} else {
			//详情
			if (auditStsed == "0") {
				//未审核
				$("#btnAddSaleReturnBill").removeClass("hide");
				$("#btnCopySaleReturnBill").removeClass("hide");
				$("#btnPrintSaleReturnBill").removeClass("hide");
				$("#btnSaveSaleReturnBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnAuditSaleReturnBill").removeClass("hide");
			} else if (auditStsed == "1") {
				//已审核
				$("#btnAddSaleReturnBill").removeClass("hide");
				$("#btnCopySaleReturnBill").removeClass("hide");
				$("#btnPrintSaleReturnBill").removeClass("hide");
				$("#btnReverseAuditSaleReturnBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnReverseAuditSaleReturnBill").removeClass("hide");
				
				$("#top-cus").nextAll("div .pops-value").attr('disabled', true);
				$("#top-saler").nextAll("div .pops-value").attr('disabled', true);
				$("#top-billDate").attr('disabled', true);
				$("#formdl_psssalereturnbill02 input[name=memo]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=discountRate]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=discountAmount]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=discountAfterAmount]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=thisRefund]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=popssettlementAccount]").nextAll("div .pops-value").attr('disabled', true);
				$("#formdl_psssalereturnbill02 input[name=thisDebt]").attr('readonly', true);
				$("#formdl_psssalereturnbill02 input[name=saleRefundExpense]").attr('readonly', true);
			}
			
			//单据编号
			$("#top-saleReturnBillNo").attr("disabled", true);
		}
		
		if (saleOrderId.length != 0) {
			//由销货订单生成销货退货单
			saleBillNo = "";
			saleReturnBillNo = "";
		} else if (saleBillId.length != 0) {
			//由销货单生成销货退货单
			saleOrderNo = "";
			saleReturnBillNo = "";
		} else {
			//销货退货单新增或查看
			saleOrderNo = "";
			saleBillNo = "";
		}
		
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssSaleReturnBillDetail/findByPageAjax",//列表数据查询的url
	    	tableId : "tabledl_psssalereturnbill_detail01",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 999999,//加载默认行数(不填为系统默认行数)
	    	data : {"saleOrderNo" : saleOrderNo,
    				"saleBillNo" : saleBillNo,
    				"saleReturnBillNo" : saleReturnBillNo},
	    	callback : function(options, data) {
	    		//行操作事件
	    		Pss.addRowOperateEvent("sequence", "goodsName", ["storehouseName"], "unitName", _refreshFormData);
	    		//商品列事件
	    		Pss.addGoodsEvent("goodsName", "goodsId", _afterCheckGoods);
	    		//单位列事件
	    		Pss.addUnitEvent("unitName", "unitId", "goodsId", _afterCheckUnit);
	    		//仓库列事件
	    		Pss.addStorehouseEvent("storehouseName", "storehouseId", _afterCheckStorehouse);
	    		//仓位列事件
	    		Pss.addFreightSpaceEvent("freightSpaceNo", "freightSpaceId", "storehouseId");
	    		//数量列事件
	    		var quantityThs = new Array("quantity");
	    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig, Pss.automaticCalculation, _refreshFormData);
	    		//金额列事件
	    		var moneyThs = new Array("unitPrice", "taxUnitPrice", "discountAmount", "amount", "taxAmount", "totalPriceWithTax");
	    		Pss.addMoneyEvent(moneyThs, ajaxData.pssConfig, Pss.automaticCalculation, _refreshFormData);
	    		//数字列事件
	    		var doubleThs = new Array("discountRate", "taxRate");
	    		Pss.addDoubleEvent(doubleThs, ajaxData.pssConfig, Pss.automaticCalculation, _refreshFormData);
	    		//字符串列事件
	    		var stringThs = new Array("memo");
	    		Pss.addStringEvent(stringThs);
	    		
	    		Pss.dealSearchFlagHead("searchFlag", "goodsId", "#content");
	    		
	    		_refreshFormData();
	    		
	    		var tabHeadThs = new Array("goodsName", "storehouseName", "quantity", "unitPrice", "taxUnitPrice", "amount");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
	    	}
	    });
	    
	    $('.footer_loader').remove();
	    $('.table-float-head').remove();
	    
	    $('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    $("#content #tablist").width(1900);
		$("#content .mCSB_container").css("overflow-x", "auto");
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	//选择完商品调用
	var _afterCheckGoods = function($tr, trIndex, goods) {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssUnitGoodsRel/getAllByGoodsIdAjax",
			data : {
				goodsId : goods.id
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					var pssUnitGoodsRelMap = data.pssUnitGoodsRelMap;
					
					//型号
					var goodsModelThIndex = $("#tablist>thead th[name=goodsModel]").index();
					var $goodsModelTd = $tr.children("td:eq(" + goodsModelThIndex + ")");
					$goodsModelTd.text(goods.goodsModel);
					
					//单位
					var unitNameThIndex = $("#tablist>thead th[name=unitName]").index();
					var $unitNameTd = $tr.children("td:eq(" + unitNameThIndex + ")");
					var unitIdThIndex = $("#tablist>thead th[name=unitId]").index();
					var $unitIdTd = $tr.children("td:eq(" + unitIdThIndex + ")");
					var $unitInput = $("<input style='display:none;' id='unitInput" + trIndex + "'></input>");
					$unitNameTd.children().remove();
					$unitNameTd.append($unitInput);
					if (pssUnitGoodsRelMap !== null && pssUnitGoodsRelMap !== undefined && Object.keys(pssUnitGoodsRelMap).length > 0) {
						var unitJsonArray = new Array();
						for (var unitId in pssUnitGoodsRelMap) {
							if (pssUnitGoodsRelMap[unitId].isBase == "1") {
								$unitInput.val(unitId);
								$unitIdTd.children("input[name=unitId]").val(unitId);
							}
							var unitJson = { "id": unitId,
									 		 "name": pssUnitGoodsRelMap[unitId].unitName };
							unitJsonArray.push(unitJson);
						}
						
						$unitInput.popupSelection({
							searchOn:false, //启用搜索
							inline:true, //下拉模式
							multiple:false, //多选
							items:unitJsonArray,
							changeCallback : function (obj, elem) {
								$unitIdTd.children("input[name=unitId]").val(obj.val());
								_afterCheckUnit($tr, trIndex, pssUnitGoodsRelMap[obj.val()]);
							
								Pss.popsSelectSelected($unitNameTd);
							}
						});
						Pss.addPopsSelectClick($unitNameTd);
					} else {
						$unitIdTd.children("input[name=unitId]").val("");
					}
					
					//仓库
					var storehouseNameThIndex = $("#tablist>thead th[name=storehouseName]").index();
					var $storehouseNameTd = $tr.children("td:eq(" + storehouseNameThIndex + ")");
					var storehouseIdThIndex = $("#tablist>thead th[name=storehouseId]").index();
					var $storehouseIdTd = $tr.children("td:eq(" + storehouseIdThIndex + ")");
					var $storehouseInput = $("<input style='display:none;' id='storehouseInput" + trIndex + "'></input>");
					$storehouseNameTd.children().remove();
					$storehouseNameTd.append($storehouseInput);
					if (goods.storehouseId.length > 0) {
						if (Pss.disableStorehouseMap !== null && Pss.disableStorehouseMap !== undefined && Pss.disableStorehouseMap !== "") {
							var disableStorehouse = Pss.disableStorehouseMap[goods.storehouseId];
							if (disableStorehouse == null || disableStorehouse == undefined) {
								$storehouseIdTd.children("input[name=storehouseId]").val(goods.storehouseId);
								$storehouseInput.val(goods.storehouseId);
							} else {
								$storehouseIdTd.children("input[name=storehouseId]").val("");
								$storehouseInput.val("");
							}
						} else {
							if (JSON.stringify(ajaxData.storehouse).indexOf(goods.storehouseId) > 0) {
								$storehouseIdTd.children("input[name=storehouseId]").val(goods.storehouseId);
								$storehouseInput.val(goods.storehouseId);
							}
						}
						
						//仓位
						if ($storehouseInput.val().length > 0) {
							var storehouse = new Object();
							storehouse.id = goods.storehouseId;
							_afterCheckStorehouse($tr, trIndex, storehouse);
						}
					} else {
						$storehouseIdTd.children("input[name=storehouseId]").val("");
					}
					$storehouseInput.popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.storehouse,
						changeCallback : function (obj, elem) {
							$storehouseIdTd.children("input[name=storehouseId]").val(obj.val());
							var storehouse = new Object();
							storehouse.id = obj.val();
							_afterCheckStorehouse($tr, trIndex, storehouse);
							
							Pss.popsSelectSelected($storehouseNameTd);
						}
					});
					Pss.addPopsSelectClick($storehouseNameTd);
					
					//仓位
					var freightSpaceNoThIndex = $("#tablist>thead th[name=freightSpaceNo]").index();
					var $freightSpaceNoTd = $tr.children("td:eq(" + freightSpaceNoThIndex + ")");
					var freightSpaceIdThIndex = $("#tablist>thead th[name=freightSpaceId]").index();
					var $freightSpaceIdTd = $tr.children("td:eq(" + freightSpaceIdThIndex + ")");
					var $freightSpaceInput = $("<input style='display:none;' id='freightSpaceInput" + trIndex + "'></input>");
					$freightSpaceNoTd.children().remove();
					$freightSpaceNoTd.append($freightSpaceInput);
					$freightSpaceIdTd.children("input[name=freightSpaceId]").val("");
					
					//数量
					var quantityThIndex = $("#tablist>thead th[name=quantity]").index();
					var $quantityTd = $tr.children("td:eq(" + quantityThIndex + ")");
					var quantity = 1;
					$quantityTd.text(parseFloat(quantity).toFixed(ajaxData.pssConfig.numDecimalDigit));
					
					//税率(%)
					var taxRateThIndex = $("#tablist>thead th[name=taxRate]").index();
					var $taxRateTd = $tr.children("td:eq(" + taxRateThIndex + ")");
					var taxRate = ajaxData.pssConfig.dutyRate;
					$taxRateTd.text(taxRate);
					
					var cusGrade = "";
					if($("#top-cus").val().length > 0) {
						cusGrade = $("#formdl_psssalereturnbill02 input[name=cusGrade]").val();
					} else {
						cusGrade = "01";
					}
					
					var taxUnitPrice = 0;
					var discountRate = 0;
					if (goods.unitId.length > 0) {
						var unitGoodsRel = pssUnitGoodsRelMap[$unitInput.val()];
						if (unitGoodsRel !== null && unitGoodsRel !== undefined && unitGoodsRel !== '') {
							if (cusGrade !== null && cusGrade !== undefined && cusGrade !== '') {
								if (cusGrade == "01") {
									taxUnitPrice = unitGoodsRel.retailPrice;
								} else if (cusGrade == "02") {
									taxUnitPrice = unitGoodsRel.wholesalePrice;
								} else if (cusGrade == "03") {
									taxUnitPrice = unitGoodsRel.vipPrice;
								} else {
									taxUnitPrice = unitGoodsRel.retailPrice;
								}
								
								if (cusGrade == "04") {
									discountRate = unitGoodsRel.discountRate1;
								} else if (cusGrade == "05") {
									discountRate = unitGoodsRel.discountRate2;
								}
							} else {
								taxUnitPrice = unitGoodsRel.retailPrice;
							}
						}
					} else {
						if (cusGrade !== null && cusGrade !== undefined && cusGrade !== '') {
							if (cusGrade == "01") {
								taxUnitPrice = goods.retailPrice;
							} else if (cusGrade == "02") {
								taxUnitPrice = goods.wholesalePrice;
							} else if (cusGrade == "03") {
								taxUnitPrice = goods.vipPrice;
							} else {
								taxUnitPrice = goods.retailPrice;
							}
							
							if (cusGrade == "04") {
								discountRate = goods.discountRate1;
							} else if (cusGrade == "05") {
								discountRate = goods.discountRate2;
							}
						} else {
							taxUnitPrice = goods.retailPrice;
						}
					}
					if (taxUnitPrice !== null && taxUnitPrice !== undefined && taxUnitPrice !== '') { 
						taxUnitPrice = parseFloat(taxUnitPrice);
					} else {
						taxUnitPrice = 0;
					}
					
					//销售单价
					var unitPrice = taxUnitPrice / (1 + taxRate / 100);
					var unitPriceThIndex = $("#tablist>thead th[name=unitPrice]").index();
					var $unitPriceTd = $tr.children("td:eq(" + unitPriceThIndex + ")");
					$unitPriceTd.text(Pss.fmoney(unitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//含税单价
					var taxUnitPriceThIndex = $("#tablist>thead th[name=taxUnitPrice]").index();
					var $taxUnitPriceTd = $tr.children("td:eq(" + taxUnitPriceThIndex + ")");
					$taxUnitPriceTd.text(Pss.fmoney(taxUnitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//折扣率(%)
					var discountRateThIndex = $("#tablist>thead th[name=discountRate]").index();
					var $discountRateTd = $tr.children("td:eq(" + discountRateThIndex + ")");
					$discountRateTd.text(discountRate);
					
					//折扣额
					var discountAmountThIndex = $("#tablist>thead th[name=discountAmount]").index();
					var $discountAmountTd = $tr.children("td:eq(" + discountAmountThIndex + ")");
					var discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
					$discountAmountTd.text(Pss.fmoney(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//金额
					var amountThIndex = $("#tablist>thead th[name=amount]").index();
					var $amountTd = $tr.children("td:eq(" + amountThIndex + ")");
					var amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
					$amountTd.text(Pss.fmoney(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//税额
					var taxAmountThIndex = $("#tablist>thead th[name=taxAmount]").index();
					var $taxAmountTd = $tr.children("td:eq(" + taxAmountThIndex + ")");
					var taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
					$taxAmountTd.text(Pss.fmoney(taxAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//价税合计
					var totalPriceWithTaxThIndex = $("#tablist>thead th[name=totalPriceWithTax]").index();
					var $totalPriceWithTaxTd = $tr.children("td:eq(" + totalPriceWithTaxThIndex + ")");
					var totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
					$totalPriceWithTaxTd.text(Pss.fmoney(totalPriceWithTax.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//备注
					var memoThIndex = $("#tablist>thead th[name=memo]").index();
					var $memoTh = $tr.children("td:eq(" + memoThIndex + ")");
					$memoTh.text("");
					
					//销货订单号
					var saleOrderNoThIndex = $("#tablist>thead th[name=saleOrderNo]").index();
					var $saleOrderNoTh = $tr.children("td:eq(" + saleOrderNoThIndex + ")");
					$saleOrderNoTh.text("");
					
					//销货订单明细主键
					var saleOrderDetailIdThIndex = $("#tablist>thead th[name=saleOrderDetailId]").index();
					var $saleOrderDetailIdTh = $tr.children("td:eq(" + saleOrderDetailIdThIndex + ")");
					$saleOrderDetailIdTh.text("");
					
					//销货单明细主键
					/*var saleBillDetailIdThIndex = $("#tablist>thead th[name=saleBillDetailId]").index();
					var $saleBillDetailIdTh = $tr.children("td:eq(" + saleBillDetailIdThIndex + ")");
					$saleBillDetailIdTh.text("");*/
					
					_refreshFormData();
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
	
	//选择完单位调用
	var _afterCheckUnit = function($tr, trIndex, unitGoodsRel) {
		var cusGrade = "";
		if($("#top-cus").val().length > 0) {
			cusGrade = $("#formdl_psssalereturnbill02 input[name=cusGrade]").val();
		} else {
			cusGrade = "01";
		}
		
		//含税单价
		var taxUnitPrice = 0;
		if (unitGoodsRel !== null && unitGoodsRel !== undefined && unitGoodsRel !== '') {
			if (cusGrade !== null && cusGrade !== undefined && cusGrade !== '') {
				if (cusGrade == "01") {
					taxUnitPrice = unitGoodsRel.retailPrice;
				} else if (cusGrade == "02") {
					taxUnitPrice = unitGoodsRel.wholesalePrice;
				} else if (cusGrade == "03") {
					taxUnitPrice = unitGoodsRel.vipPrice;
				} else {
					taxUnitPrice = unitGoodsRel.retailPrice;
				}
			} else {
				taxUnitPrice = unitGoodsRel.retailPrice;
			}
		}
		if (taxUnitPrice !== null && taxUnitPrice !== undefined && taxUnitPrice !== '') { 
			taxUnitPrice = parseFloat(taxUnitPrice);
		} else {
			taxUnitPrice = 0;
		}
		var taxUnitPriceThIndex = $("#tablist>thead th[name=taxUnitPrice]").index();
		var $taxUnitPriceTd = $tr.children("td:eq(" + taxUnitPriceThIndex + ")");
		$taxUnitPriceTd.text(Pss.fmoney(taxUnitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
		
		Pss.automaticCalculation($tr, "taxUnitPrice", Pss.numberFormatter($taxUnitPriceTd.text()), ajaxData.pssConfig);
		
		_refreshFormData();
	};
	
	//选择完仓库调用
	var _afterCheckStorehouse = function($tr, trIndex, storehouse) {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssFreightSpace/getAllByStorehouseIdAjax",
			data : {
				storehouseId : storehouse.id
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					//仓位
					var pssFreightSpaceMap = data.pssFreightSpaceMap;
					
					var freightSpaceNoThIndex = $("#tablist>thead th[name=freightSpaceNo]").index();
					var $freightSpaceNoTd = $tr.children("td:eq(" + freightSpaceNoThIndex + ")");
					var freightSpaceIdThIndex = $("#tablist>thead th[name=freightSpaceId]").index();
					var $freightSpaceIdTd = $tr.children("td:eq(" + freightSpaceIdThIndex + ")");
					var $freightSpaceInput = $("<input style='display:none;' id='freightSpaceInput" + trIndex + "'></input>");
					$freightSpaceNoTd.children().remove();
					$freightSpaceNoTd.append($freightSpaceInput);
					if (pssFreightSpaceMap !== null && pssFreightSpaceMap !== undefined && Object.keys(pssFreightSpaceMap).length > 0) {
						var freightSpaceJsonArray = new Array();
						for (var freightSpaceId in pssFreightSpaceMap) {
							if (pssFreightSpaceMap[freightSpaceId].enabledStatus == "1") {
								var freightSpaceJson = { "id": freightSpaceId,
						 		 		 				 "name": pssFreightSpaceMap[freightSpaceId].freightSpaceNo };
								freightSpaceJsonArray.push(freightSpaceJson);
							}
						}
						
						$freightSpaceInput.popupSelection({
							searchOn:true, //启用搜索
							inline:true, //下拉模式
							multiple:false, //多选
							items:freightSpaceJsonArray,
							changeCallback : function (obj, elem) {
								$freightSpaceIdTd.children("input[name=freightSpaceId]").val(obj.val());
								
								Pss.popsSelectSelected($freightSpaceNoTd);
							}
						});
						Pss.addPopsSelectClick($freightSpaceNoTd);
					} else {
						$freightSpaceIdTd.children("input[name=freightSpaceId]").val("");
					}
					
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
	
	//刷新form表单数据
	var _refreshFormData = function() {
		var quantity = 0;
		var amount = 0;
		var discountAfterAmount = 0;
		var thisDebt = 0;
		var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
		var quantityThIndex = $("#tablist>thead th[name=quantity]").index();
		var totalPriceWithTaxThIndex = $("#tablist>thead th[name=totalPriceWithTax]").index();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var $goodsIdTd = $(tr).children("td:eq(" + goodsIdThIndex + ")");
			var goodsId = $goodsIdTd.children("input[name=goodsId]").val();
			if (goodsId.length > 0) {
				var $quantityTd = $(tr).children("td:eq(" + quantityThIndex + ")");
				var subQuantity =$quantityTd.text();
				if (subQuantity.length > 0) {
					subQuantity = Pss.ifMoneyToNumber(subQuantity);
					quantity = quantity + parseFloat(subQuantity);
				}
				var $totalPriceWithTaxTd = $(tr).children("td:eq(" + totalPriceWithTaxThIndex + ")");
				var totalPriceWithTax =$totalPriceWithTaxTd.text();
				if (totalPriceWithTax.length > 0) {
					totalPriceWithTax = Pss.ifMoneyToNumber(totalPriceWithTax);
					amount = amount + parseFloat(totalPriceWithTax);
				}
			}
		});
		var $quantity = $("#formdl_psssalereturnbill02 input[name=quantity]");
		$quantity.val(quantity.toFixed(ajaxData.pssConfig.numDecimalDigit));
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var discountAmount = $discountAmount.val();
		var $discountRate = $("#formdl_psssalereturnbill02 input[name=discountRate]");
		var discountRate;
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
		} else {
			discountRate = parseFloat(0);
		}
		$discountRate.val(discountRate.toFixed(2));
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		discountAfterAmount = amount - discountAmount;
		$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var thisRefund = $thisRefund.val();
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		thisDebt = discountAfterAmount - parseFloat(thisRefund);
		$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//表单优惠率改变联动计算
	var _discountRateKeyup = function() {
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_psssalereturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var thisDebt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountRateBlur = function() {
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_psssalereturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0 || isNaN(discountRate)) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var thisDebt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountRate.val(parseFloat(discountRate));
		}
	};
	
	//表单退款优惠改变联动计算
	var _discountAmountKeyup = function() {
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_psssalereturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var thisDebt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountAmountBlur = function() {
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_psssalereturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0 || isNaN(discountAmount)) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var thisDebt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(parseFloat(discountAmount).toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//表单本次退款改变联动计算
	var _thisRefundKeyup = function() {
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var thisRefund = $thisRefund.val();
		if (thisRefund.length == 0) {
			thisRefund = 0;
		}
		var thisDebt = parseFloat($discountAfterAmount.val()) - parseFloat(thisRefund);
		$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	var _thisRefundBlur = function() {
		var $discountAfterAmount = $("#formdl_psssalereturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		var thisRefund = $thisRefund.val();
		if (thisRefund.length == 0 || isNaN(thisRefund)) {
			thisRefund = 0;
		}
		var thisDebt = parseFloat($discountAfterAmount.val()) - parseFloat(thisRefund);
		$thisDebt.val(thisDebt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		$thisRefund.val(parseFloat(thisRefund).toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//刷新销售费用			
	var _updateSaleExpense = function() {
		if (top.pssBuySaleExpArr !== null && top.pssBuySaleExpArr !== undefined && top.pssBuySaleExpArr.length > 0) {
			var saleExpense = 0;
			for (var i = 0; i < top.pssBuySaleExpArr.length; i++) {
				var pssBuySaleExp = top.pssBuySaleExpArr[i];
				if (pssBuySaleExp.saleType.length > 0 && pssBuySaleExp.expAmt.length > 0) {
					saleExpense += parseFloat(pssBuySaleExp.expAmt);
				}
			}
			var $saleRefundExpense = $("#formdl_psssalereturnbill02 input[name=saleRefundExpense]");
			$saleRefundExpense.val(saleExpense.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//填充form表单数据
	var _fillFormData = function() {
		var billDateVal = $("#top-billDate").val();
		var $billDate = $("#formdl_psssalereturnbill02 input[name=billDate]");
		$billDate.val(billDateVal);
		
		var saleReturnBillNoVal = $("#top-saleReturnBillNo").val();
		var $saleReturnBillNo = $("#formdl_psssalereturnbill02 input[name=saleReturnBillNo]");
		$saleReturnBillNo.val(saleReturnBillNoVal);
		
		var docBizNoVal = $("#top-docBizNo").val();
		var $docBizNo = $("#formdl_psssalereturnbill02 input[name=docBizNo]");
		$docBizNo.val(docBizNoVal);
	};
	
	//获取table分录数据数组
	var _getTableData = function() {
		var pssSaleReturnBillDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var pssSaleReturnBillDetail = new Object();
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssSaleReturnBillDetail[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssSaleReturnBillDetail[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssSaleReturnBillDetail[$(th).attr("name")] = tdText;
				}
			});
			pssSaleReturnBillDetails.push(pssSaleReturnBillDetail);
		});
		return pssSaleReturnBillDetails;
	};
	
	//验证提交数据
	var _validateSubmitData = function(pssSaleReturnBillDetails) {
		var $cusNo = $("#formdl_psssalereturnbill02 input[name=cusNo]");
		if ($cusNo.val().length == 0) {
			DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "客户"), 3000);
			return false;
		}
		
		var $saleReturnBillNo = $("#formdl_psssalereturnbill02 input[name=saleReturnBillNo]");
		if ($.trim($saleReturnBillNo.val()).length == 0) {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "单据编号"), 3000);
			$("#top-saleReturnBillNo").focus();
			return false;
		}
		
		var $discountAmount = $("#formdl_psssalereturnbill02 input[name=discountAmount]");
		var $amount = $("#formdl_psssalereturnbill02 input[name=amount]");
		if (parseFloat($discountAmount.val()) > parseFloat($amount.val())) {
			DIALOG.tip("优惠金额不能大于合计金额！", 3000);
			return false;
		}
		
		var $thisRefund = $("#formdl_psssalereturnbill02 input[name=thisRefund]");
		if (parseFloat($thisRefund.val()) < 0) {
			DIALOG.tip("本次退款不能为负数！", 3000);
			return false;
		}
		if ($thisRefund.val().length > 0 && parseFloat($thisRefund.val()) > 0.0) {
			var $settlementAccount = $("#formdl_psssalereturnbill02 input[name=settlementAccount]");
			if ($settlementAccount.val().length == 0 || $settlementAccount.val() == "-1") {
				DIALOG.tip("退款额不为空时，请选择结算账户！", 3000);
				$("#formdl_psssalereturnbill02 input[name=settlementAccountName]").focus();
				return false;
			}
		}
		
		var $thisDebt = $("#formdl_psssalereturnbill02 input[name=thisDebt]");
		if (parseFloat($thisDebt.val()) < 0) {
			DIALOG.tip("本次欠款不能为负数！", 3000);
			return false;
		}
		
		var $saleRefundExpense = $("#formdl_psssalereturnbill02 input[name=saleRefundExpense]");
		if (parseFloat($saleRefundExpense.val()) < 0) {
			DIALOG.tip("销售退货费用不能为负数！", 3000);
			return false;
		}
		
		var rightCount = 0;
		if (pssSaleReturnBillDetails != null && pssSaleReturnBillDetails.length > 0) {
			for (var index in pssSaleReturnBillDetails) {
				if (pssSaleReturnBillDetails[index].goodsId != null && pssSaleReturnBillDetails[index].goodsId.length > 0) {
					/*if (pssSaleReturnBillDetails[index].unitId == null || pssSaleReturnBillDetails[index].unitId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的单位"), 3000);
						return false;
					}*/
					if (pssSaleReturnBillDetails[index].storehouseId == null || pssSaleReturnBillDetails[index].storehouseId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的仓库"), 3000);
						return false;
					}
					if (pssSaleReturnBillDetails[index].quantity == null || pssSaleReturnBillDetails[index].quantity.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssSaleReturnBillDetails[index].goodsName + " 数量"), 3000);
						return false;
					} else if (parseFloat(pssSaleReturnBillDetails[index].quantity) <= 0) {
						DIALOG.tip("商品 " + pssSaleReturnBillDetails[index].goodsName + " 数量必须大于0！", 3000);
						return false;
					} else if (parseFloat(pssSaleReturnBillDetails[index].quantity) > parseFloat(pssSaleReturnBillDetails[index].sourceQuantity)) {
						DIALOG.tip("退货数量超过源单数量，不允许保存！", 3000);
						return false;
					}
					if (pssSaleReturnBillDetails[index].unitPrice == null || pssSaleReturnBillDetails[index].unitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssSaleReturnBillDetails[index].goodsName + " 销售单价"), 3000);
						return false;
					} else if (parseFloat(pssSaleReturnBillDetails[index].unitPrice) < 0) {
						DIALOG.tip("商品 " + pssSaleReturnBillDetails[index].goodsName + " 销售单价不能小于0！", 3000);
						return false;
					}
					if (pssSaleReturnBillDetails[index].taxUnitPrice == null || pssSaleReturnBillDetails[index].taxUnitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssSaleReturnBillDetails[index].goodsName + " 含税单价"), 3000);
						return false;
					} else if (parseFloat(pssSaleReturnBillDetails[index].taxUnitPrice) < 0) {
						DIALOG.tip("商品 " + pssSaleReturnBillDetails[index].goodsName + " 含税单价不能小于0！", 3000);
						return false;
					}
					if (pssSaleReturnBillDetails[index].amount == null || pssSaleReturnBillDetails[index].amount.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssSaleReturnBillDetails[index].goodsName + " 销售金额"), 3000);
						return false;
					} else if (parseFloat(pssSaleReturnBillDetails[index].amount) < 0) {
						DIALOG.tip("商品 " + pssSaleReturnBillDetails[index].goodsName + " 销售金额不能小于0！", 3000);
						return false;
					}
					if (parseFloat(pssSaleReturnBillDetails[index].totalPriceWithTax) < 0) {
						DIALOG.tip("商品 " + pssSaleReturnBillDetails[index].goodsName + " 价税合计不能小于0！", 3000);
						return false;
					}
					rightCount++;
				}
			}
			if (rightCount == 0) {
				DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品信息"), 3000);
				return false;
			}
		} else {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品信息"), 3000);
			return false;
		}
		
		return true;
	};
	
	//保存并新增销货退货单
	var _saveAndAddSaleReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssSaleReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssSaleReturnBillDetails)) {
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
					pssSaleReturnBillDetailsJson : JSON.stringify(pssSaleReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssSaleReturnBill/getAddPage";
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
	
	//新增销货退货单
	var _addSaleReturnBill = function() {
		window.location.href = webPath+"/pssSaleReturnBill/getAddPage";
	};
	
	//复制销货退货单
	var _copySaleReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssSaleReturnBill/getNewSaleReturnBillNoAjax",
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					DIALOG.tip(data.msg, 2000);
					var saleReturnBillNoVal = data.saleReturnBillNo;
					$("#top-saleReturnBillNo").val(saleReturnBillNoVal);
					var docBizNoVal = data.docBizNo;
					$("#top-docBizNo").val(docBizNoVal);
					top.pssBuySaleExpArr = new Array();
					var $saleRefundExpense = $("#formdl_psssalereturnbill02 input[name=saleRefundExpense]");
					$saleRefundExpense.val(0);
					var $saleReturnBillId = $("#formdl_psssalereturnbill02 input[name=saleReturnBillId]");
					$saleReturnBillId.val("");
					var $saleOrderNo = $("#formdl_psssalereturnbill02 input[name=saleOrderNo]");
					$saleOrderNo.val("");
					var $saleBillNo = $("#formdl_psssalereturnbill02 input[name=saleBillNo]");
					$saleBillNo.val("");
					$("#tablist>tbody tr").each(function(trIndex, tr) {
						var saleOrderNoThIndex = $("#tablist>thead th[name=saleOrderNo]").index();
						var $saleOrderNoTd = $(tr).children("td:eq(" + saleOrderNoThIndex + ")");
						$saleOrderNoTd.text("");
						var saleOrderDetailIdThIndex = $("#tablist>thead th[name=saleOrderDetailId]").index();
						var $saleOrderDetailIdTd = $(tr).children("td:eq(" + saleOrderDetailIdThIndex + ")");
						$saleOrderDetailIdTd.children("input[name=saleOrderDetailId]").val("");
						var saleBillNoThIndex = $("#tablist>thead th[name=saleBillNo]").index();
						var $saleBillNoTd = $(tr).children("td:eq(" + saleBillNoThIndex + ")");
						$saleBillNoTd.text("");
						var saleBillDetailIdThIndex = $("#tablist>thead th[name=saleBillDetailId]").index();
						var $saleBillDetailIdTd = $(tr).children("td:eq(" + saleBillDetailIdThIndex + ")");
						$saleBillDetailIdTd.children("input[name=saleBillDetailId]").val("");
					});
					$("#btnSaveAndAddSaleReturnBill").removeClass("hide");
					$("#btnAddSaleReturnBill").addClass("hide");
					$("#btnCopySaleReturnBill").addClass("hide");
					$("#btnPrintSaleReturnBill").addClass("hide");
					$("#btnSaveSaleReturnBill").removeClass("hide");
					$("#btnExportSN").addClass("hide");
					$("#btnAuditSaleReturnBill").removeClass("hide");
					$("#btnReverseAuditSaleReturnBill").addClass("hide");
					$("#top-saleReturnBillNo").removeAttr('disabled');
					$("#top-cus").nextAll("div .pops-value").removeAttr('disabled');
					$("#top-saler").nextAll("div .pops-value").removeAttr('disabled');
					$("#top-billDate").removeAttr('disabled');
					$("#formdl_psssalereturnbill02 input[name=memo]").attr("readonly", false);
					$("#formdl_psssalereturnbill02 input[name=discountRate]").attr("readonly", false);
					$("#formdl_psssalereturnbill02 input[name=discountAmount]").attr("readonly", false);
					$("#formdl_psssalereturnbill02 input[name=thisRefund]").attr("readonly", false);
					$("#formdl_psssalereturnbill02 input[name=popssettlementAccount]").nextAll("div .pops-value").removeAttr('disabled');
					$("#auditTag").addClass("hide");
					var url = $('#pssSourceDocumentIframe').attr('src');
					url = url.substring(0, url.indexOf("?")) + "?docBizNo=" + data.docBizNo + "&auditStsed=0";
					$('#pssSourceDocumentIframe').attr('src', url);
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
	
	//保存销货退货单
	var _saveSaleReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssSaleReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssSaleReturnBillDetails)) {
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
					pssSaleReturnBillDetailsJson : JSON.stringify(pssSaleReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssSaleReturnBill/getDetailPage?saleReturnBillId=" + data.saleReturnBillId;
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
	
	//审核销货退货单
	var _auditSaleReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssSaleReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssSaleReturnBillDetails)) {
			LoadingAnimate.stop();
			return false;
		}
			
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/pssSaleReturnBill/auditSaleReturnBillAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssSaleReturnBillDetailsJson : JSON.stringify(pssSaleReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssSaleReturnBill/getDetailPage?saleReturnBillId=" + data.saleReturnBillId;
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
	
	//反审核销货退货单
	var _reverseAuditSaleReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssSaleReturnBill/reverseAuditSaleReturnBillAjax?saleReturnBillId=" + saleReturnBillId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssSaleReturnBill/getDetailPage?saleReturnBillId=" + data.saleReturnBillId;
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
		
		window.top.alert("请确认是否打印销货退货单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssSaleReturnBillAjax?saleReturnBillId=" + saleReturnBillId,
				data : {
					fileName : 'templateFile_pssxhthd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssSaleReturnBillObj = $.parseJSON(data.pssSaleReturnBill);
						mfPageOffice.openPageOffice(pssSaleReturnBillObj);
					}else{
						window.top.alert(data.msg, 0);
					}
				}
			});
		});
		
	};
	
	return {
		init : _init,
		afterCheckGoods : _afterCheckGoods,
		refreshFormData : _refreshFormData,
		discountRateKeyup : _discountRateKeyup,
		discountRateBlur : _discountRateBlur,
		discountAmountKeyup : _discountAmountKeyup,
		discountAmountBlur : _discountAmountBlur,
		thisRefundKeyup : _thisRefundKeyup,
		thisRefundBlur : _thisRefundBlur,
		updateSaleExpense : _updateSaleExpense,
		fillFormData : _fillFormData,
		getTableData : _getTableData,
		validateSubmitData : _validateSubmitData,
		saveAndAddSaleReturnBill : _saveAndAddSaleReturnBill,
		addSaleReturnBill : _addSaleReturnBill,
		copySaleReturnBill : _copySaleReturnBill,
		saveSaleReturnBill : _saveSaleReturnBill,
		auditSaleReturnBill : _auditSaleReturnBill,
		reverseAuditSaleReturnBill : _reverseAuditSaleReturnBill,
		printBill : _printBill
	};
}(window, jQuery);