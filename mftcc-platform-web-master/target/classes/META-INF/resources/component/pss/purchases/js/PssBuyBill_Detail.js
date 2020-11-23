var PssBuyBill_Detail = function(window, $) {
	var _init = function() {
		var supplierId = $("#formdl_pssbuybill02 input[name=supplierId]").val();
		//供应商组件
		var supplierJsonArray = ajaxData.supplier.slice();
		if (disableSupplierMap !== null && disableSupplierMap !== undefined && disableSupplierMap !== "") {
			var disableSupplier = disableSupplierMap[supplierId];
			if (disableSupplier !== null && disableSupplier !== undefined) {
				var disableSupplierJson = { "id": disableSupplier.supplierId,
						 "name": disableSupplier.supplierName,
						 "taxRate" : disableSupplier.taxRate,
						 "enabledStatus" : disableSupplier.enabledStatus };
				supplierJsonArray.push(disableSupplierJson);
			}
		}
		$("#top-supplier").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false,//多选
			items : supplierJsonArray,
			changeCallback : function (obj, elem) {
				$("#formdl_pssbuybill02 input[name=supplierId]").val(obj.val());
				var supplier = supplierMap[obj.val()];
				if (supplier !== null && supplier !== undefined && supplier !== '') {
					$("#formdl_pssbuybill02 input[name=taxRate]").val(supplierMap[obj.val()].taxRate);
				}
			} 
		});
		
		if (supplierId !== null && supplierId !== undefined && supplierId !== '') {
			$("#formdl_pssbuybill02 input[name=taxRate]").val(supplierMap[supplierId].taxRate);
		}
		
		//结算账户组件
		$("#formdl_pssbuybill02 input[name=settlementAccount]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//单选
			items : ajaxData.settlementAccount
		});
		
		Pss.dateClickEvent();
		
		//录入采购费用
		top.pssBuySaleExpArr = new Array();
		$("#inputBuyExpense").on('click', function() {
			Pss.insertPssBuySaleExpBill($("#top-buyBillNo").val(), _updateBuyExpense);
		});
		
		if (buyBillId.length == 0) {
			//新增
			$("#btnSaveAndAddBuyBill").removeClass("hide");
			$("#btnSaveBuyBill").removeClass("hide");
			$("#btnAuditBuyBill").removeClass("hide");
		} else {
			//详情
			if (auditStsed == "0") {
				//未审核
				$("#btnAddBuyBill").removeClass("hide");
				$("#btnCopyBuyBill").removeClass("hide");
				$("#btnPrintBuyBill").removeClass("hide");
				$("#btnSaveBuyBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnAuditBuyBill").removeClass("hide");
			} else if (auditStsed == "1") {
				//已审核
				$("#btnGenerateBuyReturnBill").removeClass("hide");
				$("#btnAddBuyBill").removeClass("hide");
				$("#btnCopyBuyBill").removeClass("hide");
				$("#btnPrintBuyBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnReverseAuditBuyBill").removeClass("hide");
				
				$("#top-supplier").nextAll("div .pops-value").attr('disabled', true);
				$("#top-billDate").attr('disabled', true);
				$("#formdl_pssbuybill02 input[name=memo]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=discountRate]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=discountAmount]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=discountAfterAmount]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=thisPayment]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=popssettlementAccount]").nextAll("div .pops-value").attr('disabled', true);
				$("#formdl_pssbuybill02 input[name=debt]").attr('readonly', true);
				$("#formdl_pssbuybill02 input[name=buyExpense]").attr('readonly', true);
			}
			
			//单据编号
			$("#top-buyBillNo").attr('disabled', true);
		}
		
		if (buyOrderId.length != 0) {
			//由购货订单生成购货单
			buyBillNo = "";
		} else {
			//购货单新增或查看
			buyOrderNo = "";
		}
		
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssBuyBillDetail/findByPageAjax",//列表数据查询的url
	    	tableId : "tabledl_pssbuybill_detail01",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 999999,//加载默认行数(不填为系统默认行数)
	    	data : {"buyOrderNo" : buyOrderNo,
    				"buyBillNo" : buyBillNo},
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
	    		var moneyThs = new Array("unitPrice", "taxUnitPrice", "discountAmount", "amount", "taxAmount", "totalPriceWithTax", "buyExpense");
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
	    
	    $("#content #tablist").width(1800);
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
					$quantityTd.text(parseFloat(1).toFixed(ajaxData.pssConfig.numDecimalDigit));
					
					//税率(%)
					var taxRateThIndex = $("#tablist>thead th[name=taxRate]").index();
					var $taxRateTd = $tr.children("td:eq(" + taxRateThIndex + ")");
					var taxRate = 0;
					if($("#top-supplier").val().length > 0) {
						taxRate = $("#formdl_pssbuybill02 input[name=taxRate]").val();
						$taxRateTd.text(taxRate);
					} else {
						taxRate = ajaxData.pssConfig.dutyRate;
						$taxRateTd.text(taxRate);
					}
					
					//购货单价
					var taxUnitPrice = 0;
					if (goods.unitId.length > 0) {
						var unitGoodsRel = pssUnitGoodsRelMap[$unitInput.val()];
						if (unitGoodsRel !== null && unitGoodsRel !== undefined && unitGoodsRel !== '') { 
							taxUnitPrice = unitGoodsRel.estimatedPurchasePrice;
						}
					} else {
						taxUnitPrice = goods.estimatedPurchasePrice;
					}
					if (taxUnitPrice !== null && taxUnitPrice !== undefined && taxUnitPrice !== '') { 
						taxUnitPrice = parseFloat(taxUnitPrice);
					} else {
						taxUnitPrice = 0;
					}
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
					var discountRate = 0;
					$discountRateTd.text(discountRate);
					
					//折扣额
					var discountAmountThIndex = $("#tablist>thead th[name=discountAmount]").index();
					var $discountAmountTd = $tr.children("td:eq(" + discountAmountThIndex + ")");
					var discountAmount = 0;
					$discountAmountTd.text(Pss.fmoney(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//金额
					var amountThIndex = $("#tablist>thead th[name=amount]").index();
					var $amountTd = $tr.children("td:eq(" + amountThIndex + ")");
					$amountTd.text(Pss.fmoney(unitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//税额
					var taxAmountThIndex = $("#tablist>thead th[name=taxAmount]").index();
					var $taxAmountTd = $tr.children("td:eq(" + taxAmountThIndex + ")");
					var taxAmount = unitPrice * taxRate / 100;
					$taxAmountTd.text(Pss.fmoney(taxAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//价税合计
					var totalPriceWithTaxThIndex = $("#tablist>thead th[name=totalPriceWithTax]").index();
					var $totalPriceWithTaxTd = $tr.children("td:eq(" + totalPriceWithTaxThIndex + ")");
					$totalPriceWithTaxTd.text(Pss.fmoney(taxUnitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//采购费用
					var buyExpenseThIndex = $("#tablist>thead th[name=buyExpense]").index();
					var $buyExpenseTh = $tr.children("td:eq(" + buyExpenseThIndex + ")");
					$buyExpenseTh.text("");
					
					//备注
					var memoThIndex = $("#tablist>thead th[name=memo]").index();
					var $memoTh = $tr.children("td:eq(" + memoThIndex + ")");
					$memoTh.text("");
					
					//购货订单号
					var buyOrderNoThIndex = $("#tablist>thead th[name=buyOrderNo]").index();
					var $buyOrderNoTh = $tr.children("td:eq(" + buyOrderNoThIndex + ")");
					$buyOrderNoTh.text("");
					
					//购货订单明细主键
					var buyOrderDetailIdThIndex = $("#tablist>thead th[name=buyOrderDetailId]").index();
					var $buyOrderDetailIdTh = $tr.children("td:eq(" + buyOrderDetailIdThIndex + ")");
					$buyOrderDetailIdTh.text("");
					
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
		//含税单价
		var taxUnitPrice = 0;
		if (unitGoodsRel !== null && unitGoodsRel !== undefined && unitGoodsRel !== '') { 
			taxUnitPrice = unitGoodsRel.estimatedPurchasePrice;
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
		var amountWithoutTax = 0; //购货金额(不包括税额,对应明细中的amount)
		var amount = 0; //购货金额(包括税额,对应明细中的totalPriceWithTax)
		var discountAfterAmount = 0;
		var debt = 0;
		var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
		var quantityThIndex = $("#tablist>thead th[name=quantity]").index();
		var amountThIndex = $("#tablist>thead th[name=amount]").index();
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
				var $amountTd = $(tr).children("td:eq(" + amountThIndex + ")");
				var subAmountWithoutTax = $amountTd.text();
				if (subAmountWithoutTax.length > 0) {
					subAmountWithoutTax = Pss.ifMoneyToNumber(subAmountWithoutTax);
					amountWithoutTax = amountWithoutTax + parseFloat(subAmountWithoutTax);
				}
				var $totalPriceWithTaxTd = $(tr).children("td:eq(" + totalPriceWithTaxThIndex + ")");
				var totalPriceWithTax =$totalPriceWithTaxTd.text();
				if (totalPriceWithTax.length > 0) {
					totalPriceWithTax = Pss.ifMoneyToNumber(totalPriceWithTax);
					amount = amount + parseFloat(totalPriceWithTax);
				}
			}
		});
		var $quantity = $("#formdl_pssbuybill02 input[name=quantity]");
		$quantity.val(quantity.toFixed(ajaxData.pssConfig.numDecimalDigit));
		var $amountWithoutTax = $("#formdl_pssbuybill02 input[name=amountWithoutTax]");
		$amountWithoutTax.val(amountWithoutTax.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $amount = $("#formdl_pssbuybill02 input[name=amount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $discountAmount = $("#formdl_pssbuybill02 input[name=discountAmount]");
		var discountAmount = $discountAmount.val();
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		var discountRate;
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
		} else {
			discountRate = parseFloat(0);
		}
		$discountRate.val(discountRate.toFixed(2));
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		discountAfterAmount = amount - discountAmount;
		$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var thisPayment = $thisPayment.val();
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		debt = discountAfterAmount - parseFloat(thisPayment);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//表单优惠率改变联动计算
	var _discountRateKeyup = function() {
		var $amount = $("#formdl_pssbuybill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuybill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisPayment.val());
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountRateBlur = function() {
		var $amount = $("#formdl_pssbuybill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuybill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0 || isNaN(discountRate)) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisPayment.val());
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountRate.val(parseFloat(discountRate));
		}
	};
	
	//表单付款优惠改变联动计算
	var _discountAmountKeyup = function() {
		var $amount = $("#formdl_pssbuybill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuybill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisPayment.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountAmountBlur = function() {
		var $amount = $("#formdl_pssbuybill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuybill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0 || isNaN(discountAmount)) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisPayment.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(parseFloat(discountAmount).toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//表单本次付款改变联动计算
	var _thisPaymentKeyup = function() {
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var thisPayment = $thisPayment.val();
		if (thisPayment.length == 0) {
			thisPayment = 0;
		}
		var debt = parseFloat($discountAfterAmount.val()) - parseFloat(thisPayment);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	var _thisPaymentBlur = function() {
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		var $debt = $("#formdl_pssbuybill02 input[name=debt]");
		var thisPayment = $thisPayment.val();
		if (thisPayment.length == 0 || isNaN(thisPayment)) {
			thisPayment = 0;
		}
		var debt = parseFloat($discountAfterAmount.val()) - parseFloat(thisPayment);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		$thisPayment.val(parseFloat(thisPayment).toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//刷新采购费用			
	var _updateBuyExpense = function() {
		if (top.pssBuySaleExpArr !== null && top.pssBuySaleExpArr !== undefined && top.pssBuySaleExpArr.length > 0) {
			var buyExpense = 0;
			for (var i = 0; i < top.pssBuySaleExpArr.length; i++) {
				var pssBuySaleExp = top.pssBuySaleExpArr[i];
				if (pssBuySaleExp.saleType.length > 0 && pssBuySaleExp.expAmt.length > 0) {
					buyExpense += parseFloat(pssBuySaleExp.expAmt);
				}
			}
			var $buyExpense = $("#formdl_pssbuybill02 input[name=buyExpense]");
			$buyExpense.val(buyExpense.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//分摊采购费用
	var _apportionBuyExpense = function() {
		var totalAmount = $("#formdl_pssbuybill02 input[name=amountWithoutTax]").val();
		var totalBuyExpense = $("#formdl_pssbuybill02 input[name=buyExpense]").val();
		var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
		var amountThIndex = $("#tablist>thead th[name=amount]").index();
		var buyExpenseThIndex = $("#tablist>thead th[name=buyExpense]").index();
		var $lastTr = null;
		var buyExpenseBalance = parseFloat(totalBuyExpense);
		var lastBuyExpense = 0;
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var $goodsIdTd = $(tr).children("td:eq(" + goodsIdThIndex + ")");
			var goodsId = $goodsIdTd.children("input[name=goodsId]").val();
			if (goodsId.length > 0) {
				var $amountTd = $(tr).children("td:eq(" + amountThIndex + ")");
				var amount = $amountTd.text();
				if (amount.length > 0 && parseFloat(totalBuyExpense) > 0) {
					amount = Pss.ifMoneyToNumber(amount);
					if (parseFloat(amount) != 0) {
						var buyExpense = parseFloat(amount) / parseFloat(totalAmount) * parseFloat(totalBuyExpense);
						buyExpense = buyExpense.toFixed(ajaxData.pssConfig.amtDecimalDigit);
						$(tr).children("td:eq(" + buyExpenseThIndex + ")").text(Pss.fmoney(buyExpense, ajaxData.pssConfig.amtDecimalDigit));
						$lastTr = $(tr);
						buyExpenseBalance = buyExpenseBalance - parseFloat(buyExpense);
						lastBuyExpense = buyExpense;
					} else {
						$(tr).children("td:eq(" + buyExpenseThIndex + ")").text("");
					}
				} else {
					$(tr).children("td:eq(" + buyExpenseThIndex + ")").text("");
				}
			}
		});
		lastBuyExpense = parseFloat(lastBuyExpense) + buyExpenseBalance;
		lastBuyExpense = lastBuyExpense.toFixed(ajaxData.pssConfig.amtDecimalDigit);
		if ($lastTr !== null && $lastTr !== undefined) {
			$lastTr.children("td:eq(" + buyExpenseThIndex + ")").text(Pss.fmoney(lastBuyExpense, ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//填充form表单数据
	var _fillFormData = function() {
		var billDateVal = $("#top-billDate").val();
		var $billDate = $("#formdl_pssbuybill02 input[name=billDate]");
		$billDate.val(billDateVal);
		
		var buyBillNoVal = $("#top-buyBillNo").val();
		var $buyBillNo = $("#formdl_pssbuybill02 input[name=buyBillNo]");
		$buyBillNo.val(buyBillNoVal);
	};
	
	//获取table分录数据数组
	var _getTableData = function() {
		var pssBuyBillDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var pssBuyBillDetail = new Object();
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name=" + $(th).attr("name") + "]").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssBuyBillDetail[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssBuyBillDetail[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssBuyBillDetail[$(th).attr("name")] = tdText;
				}
			});
			pssBuyBillDetails.push(pssBuyBillDetail);
		});
		return pssBuyBillDetails;
	};
	
	//验证提交数据
	var _validateSubmitData = function(pssBuyBillDetails) {
		var $supplierId = $("#formdl_pssbuybill02 input[name=supplierId]");
		if ($supplierId.val().length == 0) {
			DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "供应商"), 3000);
			return false;
		}
		
		var $buyBillNo = $("#formdl_pssbuybill02 input[name=buyBillNo]");
		if ($.trim($buyBillNo.val()).length == 0) {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "单据编号"), 3000);
			$("#top-buyBillNo").focus();
			return false;
		}
		
		var $discountRate = $("#formdl_pssbuybill02 input[name=discountRate]");
		if (parseFloat($discountRate.val()) < 0 || parseFloat($discountRate.val()) > 100) {
			DIALOG.tip("优惠率要为[0-100]之间数字，请输入有效数字！", 3000);
			return false;
		}
		
		var $thisPayment = $("#formdl_pssbuybill02 input[name=thisPayment]");
		if (parseFloat($thisPayment.val()) < 0) {
			DIALOG.tip("本次付款不能为负数！", 3000);
			return false;
		}
		if ($thisPayment.val().length > 0 && parseFloat($thisPayment.val()) > 0.0) {
			var $settlementAccount = $("#formdl_pssbuybill02 input[name=settlementAccount]");
			if ($settlementAccount.val().length == 0 || $settlementAccount.val() == "-1") {
				DIALOG.tip("付款额不为空时，请选择结算账户！", 3000);
				$("#formdl_pssbuybill02 input[name=settlementAccountName]").focus();
				return false;
			}
		}
		
		var $discountAfterAmount = $("#formdl_pssbuybill02 input[name=discountAfterAmount]");
		if (parseFloat($thisPayment.val()) > parseFloat($discountAfterAmount.val())) {
			DIALOG.tip("本次付款不能大于折后金额！", 3000);
			return false;
		}
		
		var $buyExpense = $("#formdl_pssbuybill02 input[name=buyExpense]");
		if (parseFloat($buyExpense.val()) < 0) {
			DIALOG.tip("采购费用不能为负数！", 3000);
			return false;
		}
		
		var rightCount = 0;
		var totalBuyExpense = 0;
		if (pssBuyBillDetails != null && pssBuyBillDetails.length > 0) {
			for (var index in pssBuyBillDetails) {
				if (pssBuyBillDetails[index].goodsId != null && pssBuyBillDetails[index].goodsId.length > 0) {
					/*if (pssBuyBillDetails[index].unitId == null || pssBuyBillDetails[index].unitId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的单位"), 3000);
						return false;
					}*/
					if (pssBuyBillDetails[index].storehouseId == null || pssBuyBillDetails[index].storehouseId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的仓库"), 3000);
						return false;
					}
					if (pssBuyBillDetails[index].quantity == null || pssBuyBillDetails[index].quantity.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyBillDetails[index].goodsName + " 数量"), 3000);
						return false;
					} else if (parseFloat(pssBuyBillDetails[index].quantity) <= 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 数量必须大于0！", 3000);
						return false;
					}
					if (pssBuyBillDetails[index].unitPrice == null || pssBuyBillDetails[index].unitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyBillDetails[index].goodsName + " 购货单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyBillDetails[index].unitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 购货单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyBillDetails[index].taxUnitPrice == null || pssBuyBillDetails[index].taxUnitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyBillDetails[index].goodsName + " 含税单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyBillDetails[index].taxUnitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 含税单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyBillDetails[index].amount == null || pssBuyBillDetails[index].amount.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyBillDetails[index].goodsName + " 采购金额"), 3000);
						return false;
					} else if (parseFloat(pssBuyBillDetails[index].amount) < 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 采购金额不能小于0！", 3000);
						return false;
					}
					if (parseFloat(pssBuyBillDetails[index].totalPriceWithTax) < 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 价税合计不能小于0！", 3000);
						return false;
					}
					if (pssBuyBillDetails[index].buyExpense == null || pssBuyBillDetails[index].buyExpense.length == 0) {
						
					} else if (parseFloat(pssBuyBillDetails[index].buyExpense) < 0) {
						DIALOG.tip("商品 " + pssBuyBillDetails[index].goodsName + " 采购费用不能小于0！", 3000);
						return false;
					} else {
						totalBuyExpense = totalBuyExpense + parseFloat(pssBuyBillDetails[index].buyExpense);
					}
					rightCount++;
				}
			}
			if (rightCount == 0) {
				DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品信息"), 3000);
				return false;
			} else {
				$buyExpense = $("#formdl_pssbuybill02 input[name=buyExpense]");
				if (totalBuyExpense != 0 && parseFloat($buyExpense.val()) != totalBuyExpense) {
					DIALOG.tip("总采购费用和分录采购费用之和不相等！", 3000);
					return false;
				}
			}
		} else {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品信息"), 3000);
			return false;
		}
		
		return true;
	};
	
	//保存并新增购货单
	var _saveAndAddBuyBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyBillDetails)) {
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
					pssBuyBillDetailsJson : JSON.stringify(pssBuyBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyBill/getAddPage";
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
	
	//生成购货退货单
	var _generateBuyReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyBill/beforeGenerateBuyReturnBillAjax?buyBillId=" + buyBillId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					$("#myModalLabel", window.parent.document).text("购货退货单");
					window.location.href = webPath+"/pssBuyReturnBill/getAddPage?buyBillId=" + buyBillId;
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
	
	//新增购货单
	var _addBuyBill = function() {
		window.location.href = webPath+"/pssBuyBill/getAddPage";
	};
	
	//复制购货单
	var _copyBuyBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyBill/getNewBuyBillNoAjax",
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					DIALOG.tip(data.msg, 2000);
					var buyBillNoVal = data.buyBillNo;
					$("#top-buyBillNo").val(buyBillNoVal);
					top.pssBuySaleExpArr = new Array();
					var $buyExpense = $("#formdl_pssbuybill02 input[name=buyExpense]");
					$buyExpense.val(0);
					var $buyBillId = $("#formdl_pssbuybill02 input[name=buyBillId]");
					$buyBillId.val("");
					var $buyOrderNo = $("#formdl_pssbuybill02 input[name=buyOrderNo]");
					$buyOrderNo.val("");
					$("#tablist>tbody tr").each(function(trIndex, tr) {
						var buyExpenseThIndex = $("#tablist>thead th[name=buyExpense]").index();
						var $buyExpenseTd = $(tr).children("td:eq(" + buyExpenseThIndex + ")");
						$buyExpenseTd.text("");
						var buyOrderNoThIndex = $("#tablist>thead th[name=buyOrderNo]").index();
						var $buyOrderNoTd = $(tr).children("td:eq(" + buyOrderNoThIndex + ")");
						$buyOrderNoTd.text("");
						var buyOrderDetailIdThIndex = $("#tablist>thead th[name=buyOrderDetailId]").index();
						var $buyOrderDetailIdTd = $(tr).children("td:eq(" + buyOrderDetailIdThIndex + ")");
						$buyOrderDetailIdTd.children("input[name=buyOrderDetailId]").val("");
					});
					$("#btnSaveAndAddBuyBill").removeClass("hide");
					$("#btnGenerateBuyReturnBill").addClass("hide");
					$("#btnAddBuyBill").addClass("hide");
					$("#btnCopyBuyBill").addClass("hide");
					$("#btnPrintBuyBill").addClass("hide");
					$("#btnSaveBuyBill").removeClass("hide");
					$("#btnExportSN").addClass("hide");
					$("#btnAuditBuyBill").removeClass("hide");
					$("#btnReverseAuditBuyBill").addClass("hide");
					$("#top-buyBillNo").removeAttr('disabled');
					$("#top-billDate").removeAttr('disabled');
					$("#formdl_pssbuybill02 input[name=memo]").attr("readonly", false);
					$("#formdl_pssbuybill02 input[name=discountRate]").attr("readonly", false);
					$("#formdl_pssbuybill02 input[name=discountAmount]").attr("readonly", false);
					$("#formdl_pssbuybill02 input[name=thisPayment]").attr("readonly", false);
					$("#formdl_pssbuybill02 input[name=popssettlementAccount]").nextAll("div .pops-value").removeAttr('disabled');
					$("#auditTag").addClass("hide");
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
	
	//保存购货单
	var _saveBuyBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyBillDetails)) {
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
					pssBuyBillDetailsJson : JSON.stringify(pssBuyBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyBill/getDetailPage?buyBillId=" + data.buyBillId;
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
	
	//审核购货单
	var _auditBuyBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyBillDetails)) {
			LoadingAnimate.stop();
			return false;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/pssBuyBill/auditBuyBillAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssBuyBillDetailsJson : JSON.stringify(pssBuyBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyBill/getDetailPage?buyBillId=" + data.buyBillId;
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
	
	//反审核购货单
	var _reverseAuditBuyBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyBill/reverseAuditBuyBillAjax?buyBillId=" + buyBillId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssBuyBill/getDetailPage?buyBillId=" + data.buyBillId;
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
		
		window.top.alert("请确认是否打印购货单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssBuyBillAjax?buyBillId=" + buyBillId,
				data : {
					fileName : 'templateFile_pssghd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssBuyBillObj = $.parseJSON(data.pssBuyBill);
						mfPageOffice.openPageOffice(pssBuyBillObj);
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
		afterCheckUnit : _afterCheckUnit,
		afterCheckStorehouse : _afterCheckStorehouse,
		refreshFormData : _refreshFormData,
		discountRateKeyup : _discountRateKeyup,
		discountRateBlur : _discountRateBlur,
		discountAmountKeyup : _discountAmountKeyup,
		discountAmountBlur : _discountAmountBlur,
		thisPaymentKeyup : _thisPaymentKeyup,
		thisPaymentBlur : _thisPaymentBlur,
		updateBuyExpense : _updateBuyExpense,
		apportionBuyExpense : _apportionBuyExpense,
		fillFormData : _fillFormData,
		getTableData : _getTableData,
		validateSubmitData : _validateSubmitData,
		saveAndAddBuyBill : _saveAndAddBuyBill,
		generateBuyReturnBill : _generateBuyReturnBill,
		addBuyBill : _addBuyBill,
		copyBuyBill : _copyBuyBill,
		saveBuyBill : _saveBuyBill,
		auditBuyBill : _auditBuyBill,
		reverseAuditBuyBill : _reverseAuditBuyBill,
		printBill : _printBill
	};
}(window, jQuery);