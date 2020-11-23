var PssBuyReturnBill_Detail = function(window, $) {
	var _init = function() {
		var supplierId = $("#formdl_pssbuyreturnbill02 input[name=supplierId]").val();
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
			multiple : false,//多选选
			items : supplierJsonArray,
			changeCallback : function (obj, elem) {
				$("#formdl_pssbuyreturnbill02 input[name=supplierId]").val(obj.val());
				var supplier = supplierMap[obj.val()];
				if (supplier !== null && supplier !== undefined && supplier !== '') {
					$("#formdl_pssbuyreturnbill02 input[name=taxRate]").val(supplierMap[obj.val()].taxRate);
				}
			} 
		});
		
		if (supplierId !== null && supplierId !== undefined && supplierId !== '') {
			$("#formdl_pssbuyreturnbill02 input[name=taxRate]").val(supplierMap[supplierId].taxRate);
		}
		
		//结算账户组件
		$("#formdl_pssbuyreturnbill02 input[name=settlementAccount]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//单选
			items : ajaxData.settlementAccount
		});
		
		Pss.dateClickEvent();
		
		//录入采购费用
		top.pssBuySaleExpArr = new Array();
		$("#inputBuyExpense").on('click', function() {
			Pss.insertPssBuySaleExpBill($("#top-buyReturnBillNo").val(), _updateBuyExpense);
		});
		
		if (buyReturnBillId.length == 0) {
			//新增
			$("#btnSaveAndAddBuyReturnBill").removeClass("hide");
			$("#btnSaveBuyReturnBill").removeClass("hide");
			$("#btnAuditBuyReturnBill").removeClass("hide");
		} else {
			//详情
			if (auditStsed == "0") {
				//未审核
				$("#btnAddBuyReturnBill").removeClass("hide");
				$("#btnCopyBuyReturnBill").removeClass("hide");
				$("#btnPrintBuyReturnBill").removeClass("hide");
				$("#btnSaveBuyReturnBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnAuditBuyReturnBill").removeClass("hide");
			} else if (auditStsed == "1") {
				//已审核
				$("#btnAddBuyReturnBill").removeClass("hide");
				$("#btnCopyBuyReturnBill").removeClass("hide");
				$("#btnPrintBuyReturnBill").removeClass("hide");
				$("#btnReverseAuditBuyReturnBill").removeClass("hide");
				$("#btnExportSN").removeClass("hide");
				$("#btnReverseAuditBuyReturnBill").removeClass("hide");
				
				$("#top-supplier").nextAll("div .pops-value").attr('disabled', true);
				$("#top-billDate").attr('disabled', true);
				$("#formdl_pssbuyreturnbill02 input[name=memo]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=discountRate]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=discountAmount]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=thisRefund]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=popssettlementAccount]").nextAll("div .pops-value").attr('disabled', true);
				$("#formdl_pssbuyreturnbill02 input[name=debt]").attr('readonly', true);
				$("#formdl_pssbuyreturnbill02 input[name=buyRefundExpense]").attr('readonly', true);
			}
			
			//单据编号
			$("#top-buyReturnBillNo").attr("disabled", true);
		}
		
		if (buyOrderId.length != 0) {
			//由购货订单生成购货退货单
			buyBillNo = "";
			buyReturnBillNo = "";
		} else if (buyBillId.length != 0) {
			//由购货单生成购货退货单
			buyOrderNo = "";
			buyReturnBillNo = "";
		} else {
			//购货退货单新增或查看
			buyOrderNo = "";
			buyBillNo = "";
		}
		
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssBuyReturnBillDetail/findByPageAjax",//列表数据查询的url
	    	tableId : "tabledl_pssbuyreturnbill_detail01",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 999999,//加载默认行数(不填为系统默认行数)
	    	data : {"buyOrderNo" : buyOrderNo,
					"buyBillNo" : buyBillNo,
					"buyReturnBillNo" : buyReturnBillNo},
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
						taxRate = $("#formdl_pssbuyreturnbill02 input[name=taxRate]").val();
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
					
					//购货单明细主键
					/*var buyBillDetailIdThIndex = $("#tablist>thead th[name=buyBillDetailId]").index();
					var $buyBillDetailIdTh = $tr.children("td:eq(" + buyBillDetailIdThIndex + ")");
					$buyBillDetailIdTh.text("");*/
					
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
		var amount = 0;
		var discountAfterAmount = 0;
		var debt = 0;
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
		var $quantity = $("#formdl_pssbuyreturnbill02 input[name=quantity]");
		$quantity.val(quantity.toFixed(ajaxData.pssConfig.numDecimalDigit));
		var $amount = $("#formdl_pssbuyreturnbill02 input[name=amount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $discountAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAmount]");
		var discountAmount = $discountAmount.val();
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		var discountRate;
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
		} else {
			discountRate = parseFloat(0);
		}
		$discountRate.val(discountRate.toFixed(2));
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		discountAfterAmount = amount - discountAmount;
		$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var thisRefund = $thisRefund.val();
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		debt = discountAfterAmount - parseFloat(thisRefund);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//表单优惠率改变联动计算
	var _discountRateKeyup = function() {
		var $amount = $("#formdl_pssbuyreturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountRateBlur = function() {
		var $amount = $("#formdl_pssbuyreturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0 || isNaN(discountRate)) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountRate.val(parseFloat(discountRate));
		}
	};
	
	//表单退款优惠改变联动计算
	var _discountAmountKeyup = function() {
		var $amount = $("#formdl_pssbuyreturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountAmountBlur = function() {
		var $amount = $("#formdl_pssbuyreturnbill02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0 || isNaN(discountAmount)) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			var debt = discountAfterAmount - parseFloat($thisRefund.val());
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(parseFloat(discountAmount).toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//表单本次退款改变联动计算
	var _thisRefundKeyup = function() {
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var thisRefund = $thisRefund.val();
		if (thisRefund.length == 0) {
			thisRefund = 0;
		}
		var debt = parseFloat($discountAfterAmount.val()) - parseFloat(thisRefund);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	var _thisRefundBlur = function() {
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		var $debt = $("#formdl_pssbuyreturnbill02 input[name=debt]");
		var thisRefund = $thisRefund.val();
		if (thisRefund.length == 0 || isNaN(thisRefund)) {
			thisRefund = 0;
		}
		var debt = parseFloat($discountAfterAmount.val()) - parseFloat(thisRefund);
		$debt.val(debt.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		$thisRefund.val(parseFloat(thisRefund).toFixed(ajaxData.pssConfig.amtDecimalDigit));
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
			var $buyRefundExpense = $("#formdl_pssbuyreturnbill02 input[name=buyRefundExpense]");
			$buyRefundExpense.val(buyExpense.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//填充form表单数据
	var _fillFormData = function() {
		var billDateVal = $("#top-billDate").val();
		var $billDate = $("#formdl_pssbuyreturnbill02 input[name='billDate']");
		$billDate.val(billDateVal);
		
		var buyReturnBillNoVal = $("#top-buyReturnBillNo").val();
		var $buyReturnBillNo = $("#formdl_pssbuyreturnbill02 input[name='buyReturnBillNo']");
		$buyReturnBillNo.val(buyReturnBillNoVal);
	};
	
	//获取table分录数据数组
	var _getTableData = function() {
		var pssBuyReturnBillDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var pssBuyReturnBillDetail = new Object();
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssBuyReturnBillDetail[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssBuyReturnBillDetail[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssBuyReturnBillDetail[$(th).attr("name")] = tdText;
				}
			});
			pssBuyReturnBillDetails.push(pssBuyReturnBillDetail);
		});
		return pssBuyReturnBillDetails;
	};
	
	//验证提交数据
	var _validateSubmitData = function(pssBuyReturnBillDetails) {
		var $supplierId = $("#formdl_pssbuyreturnbill02 input[name=supplierId]");
		if ($supplierId.val().length == 0) {
			DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "供应商"), 3000);
			return false;
		}
		
		var $buyReturnBillNo = $("#formdl_pssbuyreturnbill02 input[name=buyReturnBillNo]");
		if ($.trim($buyReturnBillNo.val()).length == 0) {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "单据编号"), 3000);
			$("#top-buyReturnBillNo").focus();
			return false;
		}
		
		var $discountRate = $("#formdl_pssbuyreturnbill02 input[name=discountRate]");
		if (parseFloat($discountRate.val()) < 0 || parseFloat($discountRate.val()) > 100) {
			DIALOG.tip("优惠率要为[0-100]之间数字，请输入有效数字！", 3000);
			return false;
		}
		
		var $thisRefund = $("#formdl_pssbuyreturnbill02 input[name=thisRefund]");
		if (parseFloat($thisRefund.val()) < 0) {
			DIALOG.tip("本次退款不能为负数！", 3000);
			return false;
		}
		if ($thisRefund.val().length > 0 && parseFloat($thisRefund.val()) > 0.0) {
			var $settlementAccount = $("#formdl_pssbuyreturnbill02 input[name=settlementAccount]");
			if ($settlementAccount.val().length == 0 || $settlementAccount.val() == "-1") {
				DIALOG.tip("退款额不为空时，请选择结算账户！", 3000);
				$("#formdl_pssbuyreturnbill02 input[name=settlementAccountName]").focus();
				return false;
			}
		}
		
		var $discountAfterAmount = $("#formdl_pssbuyreturnbill02 input[name=discountAfterAmount]");
		if (parseFloat($thisRefund.val()) > parseFloat($discountAfterAmount.val())) {
			DIALOG.tip("本次退款不能大于折后金额！", 3000);
			return false;
		}
		
		var $buyRefundExpense = $("#formdl_pssbuyreturnbill02 input[name=buyRefundExpense]");
		if (parseFloat($buyRefundExpense.val()) < 0) {
			DIALOG.tip("采购退货费用不能为负数！", 3000);
			return false;
		}
		
		var rightCount = 0;
		if (pssBuyReturnBillDetails != null && pssBuyReturnBillDetails.length > 0) {
			for (var index in pssBuyReturnBillDetails) {
				if (pssBuyReturnBillDetails[index].goodsId != null && pssBuyReturnBillDetails[index].goodsId.length > 0) {
					/*if (pssBuyReturnBillDetails[index].unitId == null || pssBuyReturnBillDetails[index].unitId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的单位"), 3000);
						return false;
					}*/
					if (pssBuyReturnBillDetails[index].storehouseId == null || pssBuyReturnBillDetails[index].storehouseId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的仓库"), 3000);
						return false;
					}
					if (pssBuyReturnBillDetails[index].quantity == null || pssBuyReturnBillDetails[index].quantity.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyReturnBillDetails[index].goodsName + " 数量"), 3000);
						return false;
					} else if (parseFloat(pssBuyReturnBillDetails[index].quantity) <= 0) {
						DIALOG.tip("商品 " + pssBuyReturnBillDetails[index].goodsName + " 数量必须大于0！", 3000);
						return false;
					}
					if (pssBuyReturnBillDetails[index].unitPrice == null || pssBuyReturnBillDetails[index].unitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyReturnBillDetails[index].goodsName + " 购货单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyReturnBillDetails[index].unitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyReturnBillDetails[index].goodsName + " 购货单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyReturnBillDetails[index].taxUnitPrice == null || pssBuyReturnBillDetails[index].taxUnitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyReturnBillDetails[index].goodsName + " 含税单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyReturnBillDetails[index].taxUnitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyReturnBillDetails[index].goodsName + " 含税单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyReturnBillDetails[index].amount == null || pssBuyReturnBillDetails[index].amount.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyReturnBillDetails[index].goodsName + " 采购金额"), 3000);
						return false;
					} else if (parseFloat(pssBuyReturnBillDetails[index].amount) < 0) {
						DIALOG.tip("商品 " + pssBuyReturnBillDetails[index].goodsName + " 采购金额不能小于0！", 3000);
						return false;
					}
					if (parseFloat(pssBuyReturnBillDetails[index].totalPriceWithTax) < 0) {
						DIALOG.tip("商品 " + pssBuyReturnBillDetails[index].goodsName + " 价税合计不能小于0！", 3000);
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
	
	//保存并新增购货退货单
	var _saveAndAddBuyReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyReturnBillDetails)) {
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
					pssBuyReturnBillDetailsJson : JSON.stringify(pssBuyReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyReturnBill/getAddPage";
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
	
	//新增购货退货单
	var _addBuyReturnBill = function() {
		window.location.href = webPath+"/pssBuyReturnBill/getAddPage";
	};
	
	//复制购货退货单
	var _copyBuyReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyReturnBill/getNewBuyReturnBillNoAjax",
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					DIALOG.tip(data.msg, 2000);
					var buyReturnBillNoVal = data.buyReturnBillNo;
					$("#top-buyReturnBillNo").val(buyReturnBillNoVal);
					top.pssBuySaleExpArr = new Array();
					var $buyRefundExpense = $("#formdl_pssbuyreturnbill02 input[name=buyRefundExpense]");
					$buyRefundExpense.val(0);
					var $buyReturnBillId = $("#formdl_pssbuyreturnbill02 input[name=buyReturnBillId]");
					$buyReturnBillId.val("");
					var $buyOrderNo = $("#formdl_pssbuyreturnbill02 input[name=buyOrderNo]");
					$buyOrderNo.val("");
					var $buyBillNo = $("#formdl_pssbuyreturnbill02 input[name=buyBillNo]");
					$buyBillNo.val("");
					$("#tablist>tbody tr").each(function(trIndex, tr) {
						var buyOrderNoThIndex = $("#tablist>thead th[name=buyOrderNo]").index();
						var $buyOrderNoTd = $(tr).children("td:eq(" + buyOrderNoThIndex + ")");
						$buyOrderNoTd.text("");
						var buyOrderDetailIdThIndex = $("#tablist>thead th[name=buyOrderDetailId]").index();
						var $buyOrderDetailIdTd = $(tr).children("td:eq(" + buyOrderDetailIdThIndex + ")");
						$buyOrderDetailIdTd.children("input[name=buyOrderDetailId]").val("");
						var buyBillNoThIndex = $("#tablist>thead th[name=buyBillNo]").index();
						var $buyBillNoTd = $(tr).children("td:eq(" + buyBillNoThIndex + ")");
						$buyBillNoTd.text("");
						var buyBillDetailIdThIndex = $("#tablist>thead th[name=buyBillDetailId]").index();
						var $buyBillDetailIdTd = $(tr).children("td:eq(" + buyBillDetailIdThIndex + ")");
						$buyBillDetailIdTd.children("input[name=buyBillDetailId]").val("");
					});
					$("#btnSaveAndAddBuyReturnBill").removeClass("hide");
					$("#btnAddBuyReturnBill").addClass("hide");
					$("#btnCopyBuyReturnBill").addClass("hide");
					$("#btnPrintBuyReturnBill").addClass("hide");
					$("#btnSaveBuyReturnBill").removeClass("hide");
					$("#btnExportSN").addClass("hide");
					$("#btnAuditBuyReturnBill").removeClass("hide");
					$("#btnReverseAuditBuyReturnBill").addClass("hide");
					$("#top-buyReturnBillNo").removeAttr('disabled');
					$("#top-billDate").removeAttr('disabled');
					$("#formdl_pssbuyreturnbill02 input[name=memo]").attr("readonly", false);
					$("#formdl_pssbuyreturnbill02 input[name=discountRate]").attr("readonly", false);
					$("#formdl_pssbuyreturnbill02 input[name=discountAmount]").attr("readonly", false);
					$("#formdl_pssbuyreturnbill02 input[name=thisRefund]").attr("readonly", false);
					$("#formdl_pssbuyreturnbill02 input[name=popssettlementAccount]").nextAll("div .pops-value").removeAttr('disabled');
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
	
	//保存购货退货单
	var _saveBuyReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyReturnBillDetails)) {
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
					pssBuyReturnBillDetailsJson : JSON.stringify(pssBuyReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyReturnBill/getDetailPage?buyReturnBillId=" + data.buyReturnBillId;
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
	
	//审核购货退货单
	var _auditBuyReturnBill = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyReturnBillDetails = _getTableData();
		if(!_validateSubmitData(pssBuyReturnBillDetails)) {
			LoadingAnimate.stop();
			return false;
		}
			
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/pssBuyReturnBill/auditBuyReturnBillAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssBuyReturnBillDetailsJson : JSON.stringify(pssBuyReturnBillDetails),
					pssBuySaleExpBillsJson : JSON.stringify(top.pssBuySaleExpArr)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyReturnBill/getDetailPage?buyReturnBillId=" + data.buyReturnBillId;
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
	
	//反审核购货退货单
	var _reverseAuditBuyReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyReturnBill/reverseAuditBuyReturnBillAjax?buyReturnBillId=" + buyReturnBillId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssBuyReturnBill/getDetailPage?buyReturnBillId=" + data.buyReturnBillId;
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
		
		window.top.alert("请确认是否打印购货退货单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssBuyReturnBillAjax?buyReturnBillId=" + buyReturnBillId,
				data : {
					fileName : 'templateFile_pssghthd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssBuyReturnBillObj = $.parseJSON(data.pssBuyReturnBill);
						mfPageOffice.openPageOffice(pssBuyReturnBillObj);
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
		refreshFormData : _refreshFormData,
		discountRateKeyup : _discountRateKeyup,
		discountRateBlur : _discountRateBlur,
		discountAmountKeyup : _discountAmountKeyup,
		discountAmountBlur : _discountAmountBlur,
		thisRefundKeyup : _thisRefundKeyup,
		thisRefundBlur : _thisRefundBlur,
		updateBuyExpense : _updateBuyExpense,
		fillFormData : _fillFormData,
		getTableData : _getTableData,
		validateSubmitData : _validateSubmitData,
		saveAndAddBuyReturnBill : _saveAndAddBuyReturnBill,
		addBuyReturnBill : _addBuyReturnBill,
		copyBuyReturnBill : _copyBuyReturnBill,
		saveBuyReturnBill : _saveBuyReturnBill,
		auditBuyReturnBill : _auditBuyReturnBill,
		reverseAuditBuyReturnBill : _reverseAuditBuyReturnBill,
		printBill : _printBill
	};
}(window, jQuery);