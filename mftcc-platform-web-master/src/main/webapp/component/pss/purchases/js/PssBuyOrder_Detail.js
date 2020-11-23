var PssBuyOrder_Detail = function(window, $) {
	var _init = function() {
		var supplierId = $("#formdl_pssbuyorder02 input[name=supplierId]").val();
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
				$("#formdl_pssbuyorder02 input[name=supplierId]").val(obj.val());
				var supplier = supplierMap[obj.val()];
				if (supplier !== null && supplier !== undefined && supplier !== '') {
					$("#formdl_pssbuyorder02 input[name=taxRate]").val(supplierMap[obj.val()].taxRate);
				}
			}
		});
		var supplierPopsDiv = $("#top-supplier").nextAll("div .pops-value").get(0);
		var supplierLeft = $(supplierPopsDiv).position().left;
		var supplierSelectDiv = $("#top-supplier").next("div .pops-select").get(0);
		$(supplierPopsDiv).width(100);
		$(supplierSelectDiv).css({'left':supplierLeft});
		$(supplierSelectDiv).width(228); //selectDiv比PopsDiv长28px
		$(supplierSelectDiv).find("li").width(180);
		$(supplierSelectDiv).find("div .pops-search").width(228);
		
		if (supplierId !== null && supplierId !== undefined && supplierId !== '') {
			$("#formdl_pssbuyorder02 input[name=taxRate]").val(supplierMap[supplierId].taxRate);
		}
		
		Pss.dateClickEvent();
		
		//业务类别
		if (businessType == "01") {
			$("#radioBuy").prop("checked", true);
			$("#radioReturn").prop("checked", false);
		} else if (businessType == "02") {
			$("#radioBuy").prop("checked", false);
			$("#radioReturn").prop("checked", true);
		} else {
			$("#radioBuy").prop("checked", true);
			$("#radioReturn").prop("checked", false);
		}
		
		if (buyOrderId.length == 0) {
			//新增
			$("#btnSaveAndAddBuyOrder").removeClass("hide");
			$("#btnSaveBuyOrder").removeClass("hide");
			$("#btnAuditBuyOrder").removeClass("hide");
			
			$("#radioBuy").prop("checked", true);
			$("#radioReturn").prop("checked", false);
		} else {
			//详情
			if (auditStsed == "0") {
				//未审核
				$("#btnAddBuyOrder").removeClass("hide");
				$("#btnCopyBuyOrder").removeClass("hide");
				$("#btnPrintBuyOrder").removeClass("hide");
				$("#btnSaveBuyOrder").removeClass("hide");
				$("#btnAuditBuyOrder").removeClass("hide");
			} else if (auditStsed == "1") {
				//已审核
				if (enabledStatus == "1") {
					//开启
					if (businessType == "01") {
						$("#btnGenerateBuyBill").removeClass("hide");
					} else if (businessType == "02") {
						$("#btnGenerateBuyReturnBill").removeClass("hide");
					} 
					$("#btnAddBuyOrder").removeClass("hide");
					$("#btnCopyBuyOrder").removeClass("hide");
					$("#btnPrintBuyOrder").removeClass("hide");
					$("#btnReverseAuditBuyOrder").removeClass("hide");
					$("#btnCloseBuyOrder").removeClass("hide");
				} else if (enabledStatus == "0") {
					//关闭
					$("#btnAddBuyOrder").removeClass("hide");
					$("#btnCopyBuyOrder").removeClass("hide");
					$("#btnPrintBuyOrder").removeClass("hide");
					$("#btnEnableBuyOrder").removeClass("hide");
				}
				
				$("#top-supplier").nextAll("div .pops-value").attr('disabled', true);
				$("#top-orderDate").attr('disabled', true);
				$("#top-deliveryDate").attr('disabled', true);
				$("#formdl_pssbuyorder02 input[name=memo]").attr('readonly', true);
				$("#formdl_pssbuyorder02 input[name=discountRate]").attr('readonly', true);
				$("#formdl_pssbuyorder02 input[name=discountAmount]").attr('readonly', true);
				$("#formdl_pssbuyorder02 input[name=discountAfterAmount]").attr('readonly', true);
			}
			
			//单据编号
			$("#top-buyOrderNo").attr('disabled', true);
		}
		
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssBuyOrderDetail/findByPageAjax",//列表数据查询的url
	    	tableId : "tabledl_pssbuyorder_detail01",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 999999,//加载默认行数(不填为系统默认行数)
	    	data : {"buyOrderNo" : buyOrderNo,
	    			saleOrderDetailDatas : ajaxData.saleOrderDetailDatas,
	    			replenishmentGoodsDatas : ajaxData.replenishmentGoodsDatas},
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
	    		
	    		var tabHeadThs = new Array("goodsName", "storehouseName", "quantity", "unitPrice", "taxUnitPrice", "amount");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				//以销定购
				if (data.isSaleToBuy || data.isSmartToBuy) {
					_refreshFormData();
				}
	    	}
	    });
	    
	    $('.footer_loader').remove();
	    $('.table-float-head').remove();
	    
	    $('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    $("#content #tablist").width(1700);
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
						taxRate = $("#formdl_pssbuyorder02 input[name=taxRate]").val();
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
					var unitPrice = taxUnitPrice / (1 + parseFloat(taxRate) / 100);
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
					var taxAmount = unitPrice * parseFloat(taxRate) / 100;
					$taxAmountTd.text(Pss.fmoney(taxAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
					//价税合计
					var totalPriceWithTaxThIndex = $("#tablist>thead th[name=totalPriceWithTax]").index();
					var $totalPriceWithTaxTd = $tr.children("td:eq(" + totalPriceWithTaxThIndex + ")");
					$totalPriceWithTaxTd.text(Pss.fmoney(taxUnitPrice.toFixed(ajaxData.pssConfig.amtDecimalDigit), ajaxData.pssConfig.amtDecimalDigit));
					
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
		var $quantity = $("#formdl_pssbuyorder02 input[name=quantity]");
		$quantity.val(quantity.toFixed(ajaxData.pssConfig.numDecimalDigit));
		var $amount = $("#formdl_pssbuyorder02 input[name=amount]");
		$amount.val(amount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		var $discountAmount = $("#formdl_pssbuyorder02 input[name=discountAmount]");
		var discountAmount = $discountAmount.val();
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		var discountRate;
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
		} else {
			discountRate = parseFloat(0);
		}
		$discountRate.val(discountRate.toFixed(2));
		var $discountAfterAmount = $("#formdl_pssbuyorder02 input[name=discountAfterAmount]");
		discountAfterAmount = amount - discountAmount;
		$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
	};
	
	//表单优惠率改变联动计算
	var _discountRateKeyup = function() {
		var $amount = $("#formdl_pssbuyorder02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyorder02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyorder02 input[name=discountAfterAmount]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountRateBlur = function() {
		var $amount = $("#formdl_pssbuyorder02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyorder02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyorder02 input[name=discountAfterAmount]");
		var discountRate = $discountRate.val();
		if (discountRate.length == 0 || isNaN(discountRate)) {
			discountRate = 0;
		}
		if ($amount.val().length > 0) {
			var discountAmount = parseFloat($amount.val()) * parseFloat(discountRate) / 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(discountAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountRate.val(parseFloat(discountRate));
		}
	};
	
	//表单优惠金额改变联动计算
	var _discountAmountKeyup = function() {
		var $amount = $("#formdl_pssbuyorder02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyorder02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyorder02 input[name=discountAfterAmount]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	var _discountAmountBlur = function() {
		var $amount = $("#formdl_pssbuyorder02 input[name=amount]");
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		var $discountAmount = $("#formdl_pssbuyorder02 input[name=discountAmount]");
		var $discountAfterAmount = $("#formdl_pssbuyorder02 input[name=discountAfterAmount]");
		var discountAmount = $discountAmount.val();
		if (discountAmount.length == 0 || isNaN(discountAmount)) {
			discountAmount = 0;
		}
		if ($amount.val().length > 0 && parseFloat($amount.val()) != 0) {
			var discountRate = parseFloat(discountAmount) / parseFloat($amount.val()) * 100;
			var discountAfterAmount = parseFloat($amount.val()) * (1 - parseFloat(discountRate) / 100);
			$discountRate.val(discountRate.toFixed(2));
			$discountAfterAmount.val(discountAfterAmount.toFixed(ajaxData.pssConfig.amtDecimalDigit));
			$discountAmount.val(parseFloat(discountAmount).toFixed(ajaxData.pssConfig.amtDecimalDigit));
		}
	};
	
	//填充form表单数据
	var _fillFormData = function() {
		var orderDateVal = $("#top-orderDate").val();
		var $orderDate = $("#formdl_pssbuyorder02 input[name=orderDate]");
		$orderDate.val(orderDateVal);
		
		var deliveryDateVal = $("#top-deliveryDate").val();
		var $deliveryDate = $("#formdl_pssbuyorder02 input[name=deliveryDate]");
		$deliveryDate.val(deliveryDateVal);
		
		var businessTypeVal = $("input[name=top-businessType]:checked").val();
		var $businessType = $("#formdl_pssbuyorder02 input[name=businessType]");
		$businessType.val(businessTypeVal);
		
		var buyOrderNoVal = $("#top-buyOrderNo").val();
		var $buyOrderNo = $("#formdl_pssbuyorder02 input[name=buyOrderNo]");
		$buyOrderNo.val(buyOrderNoVal);
	};
	
	//获取table分录数据数组
	var _getTableData = function() {
		var pssBuyOrderDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var pssBuyOrderDetail = new Object();
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssBuyOrderDetail[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssBuyOrderDetail[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssBuyOrderDetail[$(th).attr("name")] = tdText;
				}
			});
			pssBuyOrderDetails.push(pssBuyOrderDetail);
		});
		return pssBuyOrderDetails;
	};
	
	//验证提交数据
	var _validateSubmitData = function(pssBuyOrderDetails) {
		var $supplierId = $("#formdl_pssbuyorder02 input[name=supplierId]");
		if ($supplierId.val().length == 0) {
			DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "供应商"), 3000);
			return false;
		}
		
		var $buyOrderNo = $("#formdl_pssbuyorder02 input[name=buyOrderNo]");
		if ($.trim($buyOrderNo.val()).length == 0) {
			DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "单据编号"), 3000);
			$("#top-buyOrderNo").focus();
			return false;
		}
		
		var $discountRate = $("#formdl_pssbuyorder02 input[name=discountRate]");
		if (parseFloat($discountRate.val()) < 0 || parseFloat($discountRate.val()) > 100) {
			DIALOG.tip("优惠率要为[0-100]之间数字，请输入有效数字！", 3000);
			return false;
		}
		
		var rightCount = 0;
		if (pssBuyOrderDetails != null && pssBuyOrderDetails.length > 0) {
			for (var index in pssBuyOrderDetails) {
				if (pssBuyOrderDetails[index].goodsId != null && pssBuyOrderDetails[index].goodsId.length > 0) {
					/*if (pssBuyOrderDetails[index].unitId == null || pssBuyOrderDetails[index].unitId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的单位"), 3000);
						return false;
					}*/
					if (pssBuyOrderDetails[index].storehouseId == null || pssBuyOrderDetails[index].storehouseId.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的仓库"), 3000);
						return false;
					}
					if (pssBuyOrderDetails[index].quantity == null || pssBuyOrderDetails[index].quantity.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyOrderDetails[index].goodsName + " 数量"), 3000);
						return false;
					} else if (parseFloat(pssBuyOrderDetails[index].quantity) <= 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 数量必须大于0！", 3000);
						return false;
					}
					if (pssBuyOrderDetails[index].quantity == null || pssBuyOrderDetails[index].quantity.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyOrderDetails[index].goodsName + " 数量"), 3000);
						return false;
					} else if (parseFloat(pssBuyOrderDetails[index].quantity) <= 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 数量必须大于0！", 3000);
						return false;
					}
					if (pssBuyOrderDetails[index].unitPrice == null || pssBuyOrderDetails[index].unitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyOrderDetails[index].goodsName + " 购货单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyOrderDetails[index].unitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 购货单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyOrderDetails[index].taxUnitPrice == null || pssBuyOrderDetails[index].taxUnitPrice.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyOrderDetails[index].goodsName + " 含税单价"), 3000);
						return false;
					} else if (parseFloat(pssBuyOrderDetails[index].taxUnitPrice) < 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 含税单价不能小于0！", 3000);
						return false;
					}
					if (pssBuyOrderDetails[index].amount == null || pssBuyOrderDetails[index].amount.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "商品 " + pssBuyOrderDetails[index].goodsName + " 采购金额"), 3000);
						return false;
					} else if (parseFloat(pssBuyOrderDetails[index].amount) < 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 采购金额不能小于0！", 3000);
						return false;
					}
					if (parseFloat(pssBuyOrderDetails[index].totalPriceWithTax) < 0) {
						DIALOG.tip("商品 " + pssBuyOrderDetails[index].goodsName + " 价税合计不能小于0！", 3000);
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
	
	//保存并新增购货订单
	var _saveAndAddBuyOrder = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyOrderDetails = _getTableData();
		if(!_validateSubmitData(pssBuyOrderDetails)) {
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
					pssBuyOrderDetailsJson : JSON.stringify(pssBuyOrderDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyOrder/getAddPage";
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
	
	//生成购货单
	var _generateBuyBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/beforeGenerateBillAjax?buyOrderId=" + buyOrderId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					$("#myModalLabel", window.parent.document).text("购货单");
					window.location.href = webPath+"/pssBuyBill/getAddPage?buyOrderId=" + buyOrderId;
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
	
	//生成购货退货单
	var _generateBuyReturnBill = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/beforeGenerateBillAjax?buyOrderId=" + buyOrderId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					$("#myModalLabel", window.parent.document).text("购货退货单");
					window.location.href = webPath+"/pssBuyReturnBill/getAddPage?buyOrderId=" + buyOrderId;
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
	
	//新增购货订单
	var _addBuyOrder = function() {
		window.location.href = webPath+"/pssBuyOrder/getAddPage";
	};
	
	//复制购货订单
	var _copyBuyOrder = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/getNewBuyOrderNoAjax",
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					DIALOG.tip(data.msg, 2000);
					var buyOrderNoVal = data.buyOrderNo;
					$("#top-buyOrderNo").val(buyOrderNoVal);
					var $buyOrderId = $("#formdl_pssbuyorder02 input[name=buyOrderId]");
					$buyOrderId.val("");
					$("#btnSaveAndAddBuyOrder").removeClass("hide");
					$("#btnGenerateBuyBill").addClass("hide");
					$("#btnGenerateBuyReturnBill").addClass("hide");
					$("#btnAddBuyOrder").addClass("hide");
					$("#btnCopyBuyOrder").addClass("hide");
					$("#btnPrintBuyOrder").addClass("hide");
					$("#btnSaveBuyOrder").removeClass("hide");
					$("#btnAuditBuyOrder").removeClass("hide");
					$("#btnReverseAuditBuyOrder").addClass("hide");
					$("#btnCloseBuyOrder").addClass("hide");
					$("#btnEnableBuyOrder").addClass("hide");
					$("#top-buyOrderNo").removeAttr('disabled');
					$("#top-supplier").nextAll("div .pops-value").removeAttr('disabled');
					$("#top-orderDate").removeAttr('disabled');
					$("#top-deliveryDate").removeAttr('disabled');
					$("#formdl_pssbuyorder02 input[name=memo]").attr("readonly", false);
					$("#formdl_pssbuyorder02 input[name=discountRate]").attr("readonly", false);
					$("#formdl_pssbuyorder02 input[name=discountAmount]").attr("readonly", false);
					$("#auditTag").addClass("hide");
					$("#enabledTag").addClass("hide");
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
	
	//保存购货订单
	var _saveBuyOrder = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyOrderDetails = _getTableData();
		if(!_validateSubmitData(pssBuyOrderDetails)) {
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
					pssBuyOrderDetailsJson : JSON.stringify(pssBuyOrderDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyOrder/getDetailPage?buyOrderId=" + data.buyOrderId;
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
	
	//审核购货订单
	var _auditBuyOrder = function(obj) {
		LoadingAnimate.start();
		_fillFormData();
		var pssBuyOrderDetails = _getTableData();
		if(!_validateSubmitData(pssBuyOrderDetails)) {
			LoadingAnimate.stop();
			return false;
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = webPath+"/pssBuyOrder/auditBuyOrderAjax";
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssBuyOrderDetailsJson : JSON.stringify(pssBuyOrderDetails)
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						window.location.href = webPath+"/pssBuyOrder/getDetailPage?buyOrderId=" + data.buyOrderId;
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
	
	//反审核购货订单
	var _reverseAuditBuyOrder = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/reverseAuditBuyOrderAjax?buyOrderId=" + buyOrderId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssBuyOrder/getDetailPage?buyOrderId=" + data.buyOrderId;
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
	
	//关闭购货订单
	var _closeBuyOrder = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/closeBuyOrderAjax?buyOrderId=" + buyOrderId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssBuyOrder/getDetailPage?buyOrderId=" + data.buyOrderId;
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
	
	//开启购货订单
	var _enableBuyOrder = function() {
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/pssBuyOrder/enableBuyOrderAjax?buyOrderId=" + buyOrderId,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 1);
					window.location.href = webPath+"/pssBuyOrder/getDetailPage?buyOrderId=" + data.buyOrderId;
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
		
		window.top.alert("请确认是否打印购货订单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssBuyOrderAjax?buyOrderId=" + buyOrderId,
				data : {
					fileName : 'templateFile_pssghdd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssBuyOrderObj = $.parseJSON(data.pssOrderBill);
						mfPageOffice.openPageOffice(pssBuyOrderObj);
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
		fillFormData : _fillFormData,
		getTableData : _getTableData,
		validateSubmitData : _validateSubmitData,
		saveAndAddBuyOrder : _saveAndAddBuyOrder,
		generateBuyBill : _generateBuyBill,
		generateBuyReturnBill : _generateBuyReturnBill,
		addBuyOrder : _addBuyOrder,
		copyBuyOrder : _copyBuyOrder,
		saveBuyOrder : _saveBuyOrder,
		auditBuyOrder : _auditBuyOrder,
		reverseAuditBuyOrder : _reverseAuditBuyOrder,
		closeBuyOrder : _closeBuyOrder,
		enableBuyOrder : _enableBuyOrder,
		printBill : _printBill
	};
}(window, jQuery);