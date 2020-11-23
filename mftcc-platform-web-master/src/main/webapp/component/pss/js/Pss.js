var Pss = function(window, $) {
	var _addRowOperateEvent = function(th, goodsNameTh, storehouseNameThs, unitNameTh, refreshFormData) {
		var thIndex = $("#tablist>thead th[name='" + th + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).addClass("col_operate");
			$(tr).on({
				mouseenter: function() {
					var $td = $(tr).children("td:eq(" + thIndex + ")");
					var tdText = $td.text();
					var $operateTd = $("<div class='operate' val='" + tdText + "'></div>");
					$td.text("");
					var $addA = $("<a title='增加行' class='add'></a>");
					var $delA = $("<a title='删除行' class='del'></a>");
					$operateTd.append($addA).append($delA);
					$td.append($operateTd);
					$(this).addClass("current");
				},
				mouseleave: function() {
					var $td = $(tr).children("td:eq(" + thIndex + ")");
					var tdText = $td.children("div").attr("val");
					$td.children().remove();
					$td.text(tdText);
					$(this).removeClass("current");
				}
			});
		});
		
		$("#tablist").on("click", ".add", function(a) {
			var $tr = $(this).parents('tr');
			var $newTr = $tr.clone(true);
			var $tds = $newTr.children("td");
			$tds.each(function() {
			    if ($(this).children("input").length == 0) {
			    	$(this).text("");
			    } else {
			    	$(this).children("input").val("");
			    }
			});
			$newTr.off();
			$newTr.on({
				mouseenter: function() {
					var $td = $newTr.children("td:eq(" + thIndex + ")");
					var tdText = $td.text();
					var $operateTd = $("<div class='operate' val='" + tdText + "'></div>");
					$td.text("");
					var $addA = $("<a title='增加行' class='add'></a>");
					var $delA = $("<a title='删除行' class='del'></a>");
					$operateTd.append($addA).append($delA);
					$td.append($operateTd);
					$(this).addClass("current");
				},
				mouseleave: function() {
					var $td = $newTr.children("td:eq(" + thIndex + ")");
					var tdText = $td.children("div").attr("val");
					$td.children().remove();
					$td.text(tdText);
					$(this).removeClass("current");
				}
			});
			$tr.before($newTr);
			_initSequence(th);
			var goodsNameThIndex = $("#tablist>thead th[name='" + goodsNameTh + "']").index();
			if (goodsNameThIndex >= 0) {
				var $goodsNameTd = $newTr.children("td:eq(" + goodsNameThIndex + ")");
				$goodsNameTd.text("");
				$goodsNameTd.click();
			}
			if (Array.isArray(storehouseNameThs) && storehouseNameThs.length > 0) {
				for(var index in storehouseNameThs) {
					var storehouseNameThIndex = $("#tablist>thead th[name='" + storehouseNameThs[index] + "']").index();
					if (storehouseNameThIndex >= 0) {
						var $storehouseNameTd = $newTr.children("td:eq(" + storehouseNameThIndex + ")");
						$storehouseNameTd.text("");
						$storehouseNameTd.click();
					}
				}
			}
			var searchFlagThIndex = $("#tablist>thead th[name=searchFlag]").index();
			if (searchFlagThIndex >= 0) {
				var $searchFlagTd = $newTr.children("td:eq(" + searchFlagThIndex + ")");
				if($searchFlagTd.hasClass("i i-select")){
					$searchFlagTd.removeClass("i i-select");
					$searchFlagTd.click();
				}
			}
			var unitNameThIndex = $("#tablist>thead th[name='" + unitNameTh + "']").index();
			if (unitNameThIndex >= 0) {
				var $unitNameTd = $newTr.children("td:eq(" + unitNameThIndex + ")");
				$unitNameTd.text("");
				//$unitNameTd.click();
				$unitNameTd.unbind("click");
			}
			//支出类别
			var saleTypeThIndex = $("#tablist>thead th[name='saleType']").index();
			if (saleTypeThIndex >= 0) {
				var $saleTypeTd = $newTr.children("td:eq(" + saleTypeThIndex + ")");
				$saleTypeTd.text("");
				$saleTypeTd.click();
			}
			//收入类别
			var buyTypeThIndex = $("#tablist>thead th[name='buyTypeName']").index();
			if (buyTypeThIndex >= 0) {
				var $buyTypeTd = $newTr.children("td:eq(" + buyTypeThIndex + ")");
				$buyTypeTd.text("");
				$buyTypeTd.click();
			}
			
			//首要联系人
			var isfirstContactThIndex = $("#tablist>thead th[name='isfirstContactManName']").index();
			if (isfirstContactThIndex >= 0) {
				var $isfirstContactTd = $newTr.children("td:eq(" + isfirstContactThIndex + ")");
				$isfirstContactTd.text("");
				$isfirstContactTd.click();
			}
			
			//使用状态
			var enabledStatusNameThIndex = $("#tablist>thead th[name='enabledStatusName']").index();
			if (enabledStatusNameThIndex >= 0) {
				var $enabledStatusNameTd = $newTr.children("td:eq(" + enabledStatusNameThIndex + ")");
				$enabledStatusNameTd.text("");
				$enabledStatusNameTd.click();
			}
		});
		$("#tablist").on("click", ".del", function(a) {
			var trLength = $("#tablist>tbody tr").length;
			if (trLength > 1) {
				var $tr = $(this).parents('tr');
				$tr.remove();
				_initSequence(th);
				if (typeof(refreshFormData) == 'function') {
					refreshFormData();
				}
			} else {
				alert(top.getMessage("ONLY_CW_VOUCHER_ONEENTRY", ""), 0);
			}
		});
	};
	
	var _initSequence = function(th) {
		var thIndex = $("#tablist>thead th[name='" + th + "']").index();
		var trs = $("#tablist>tbody tr");
		var i = 0;
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + thIndex + ")").text(++i);
		});
	};
	
	var _disableGoodsMap = null;
	
	var _addGoodsEvent = function(goodsNameTh, goodsIdTh, afterCheckGoods) {
		var goodsNameThIndex = $("#tablist>thead th[name='" + goodsNameTh + "']").index();
		var goodsIdThIndex = $("#tablist>thead th[name='" + goodsIdTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $goodsNameTd = $(tr).children("td:eq(" + goodsNameThIndex + ")");
			var $input = $("<input style='display:none;' id='goodsInput" + index + "'></input>");
			$goodsNameTd.append($input);
			
			var $goodsIdTd = $(tr).children("td:eq(" + goodsIdThIndex + ")");
			var goodsId = $goodsIdTd.children("input[name='" + goodsIdTh + "']").val();
			if (goodsId.length > 0) {
				$input.val(goodsId);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + goodsNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $goodsIdTd = $td.parent("tr").children("td:eq(" + goodsIdThIndex + ")");
				var goodsId = $goodsIdTd.children("input[name='" + goodsIdTh + "']").val();
				var inputLength = $td.children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='goodsInput" + index + "'></input>");
					$td.append($input);
				}
				var divLength = $td.children("div .pops-select").length;
				if (divLength == 0) {
					var goodsJsonArray = ajaxData.goods.slice();
					if (Pss.disableGoodsMap !== null && Pss.disableGoodsMap !== undefined && Pss.disableGoodsMap !== "") {
						var disableGoods = Pss.disableGoodsMap[goodsId];
						if (disableGoods !== null && disableGoods !== undefined) {
							var disableGoodsJson = { "id": disableGoods.goodsId,
									 "name": disableGoods.goodsName,
									 "goodsModel" : disableGoods.goodsModel,
									 "goodsUnit" : disableGoods.goodsUnit,
									 "unitId" : disableGoods.unitId,
									 "storehouseId" : disableGoods.storehouseId,
									 "estimatedPurchasePrice" : disableGoods.estimatedPurchasePrice,
									 "retailPrice" : disableGoods.retailPrice,
									 "wholesalePrice" : disableGoods.wholesalePrice,
									 "vipPrice" : disableGoods.vipPrice,
									 "discountRate1" : disableGoods.discountRate1,
									 "discountRate2" : disableGoods.discountRate2,
									 "flag" : disableGoods.flag };
							goodsJsonArray.push(disableGoodsJson);
						}
					}
					$td.children("input").popupSelection({
						searchOn:true, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:goodsJsonArray,
						changeCallback : function (obj, elem) {
							$goodsIdTd.children("input[name='" + goodsIdTh + "']").val(obj.val());
							for (var i in goodsJsonArray) {
								if (obj.val() == goodsJsonArray[i].id) {
									if (typeof(afterCheckGoods) == 'function') {
										afterCheckGoods($td.parent("tr"), index, goodsJsonArray[i]);
									}
									break;
								}
							}
							$tdSearchFlag = $td.parent("tr").children("td[name=searchFlag]");
							if(!$tdSearchFlag.hasClass("i i-select")){
								$tdSearchFlag.addClass("i i-select");
							}
							
							_popsSelectSelected($td);
						}
					});
					_addPopsSelectClick($td);
				}
			}).click();
		});
	};
	
	var _addUnitEvent = function(unitNameTh, unitIdTh, goodsIdTh, afterCheckUnit) {
		var unitNameThIndex = $("#tablist>thead th[name='" + unitNameTh + "']").index();
		var unitIdThIndex = $("#tablist>thead th[name='" + unitIdTh + "']").index();
		var goodsIdThIndex = $("#tablist>thead th[name='" + goodsIdTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $unitNameTd = $(tr).children("td:eq(" + unitNameThIndex + ")");
			var $input = $("<input style='display:none;' id='unitInput" + index + "'></input>");
			$unitNameTd.append($input);
			
			var $unitIdTd = $(tr).children("td:eq(" + unitIdThIndex + ")");
			var unitId = $unitIdTd.children("input[name='" + unitIdTh + "']").val();
			if (unitId.length > 0) {
				$input.val(unitId);
			}
		});
		
		$(trs).each(function(index, tr) {
			var $goodsIdTd = $(tr).children("td:eq(" + goodsIdThIndex + ")");
			var goodsIdVal = $goodsIdTd.children("input[name='" + goodsIdTh + "']").val();
			var $unitIdTd = $(tr).children("td:eq(" + unitIdThIndex + ")");
			var unitIdVal = $unitIdTd.children("input[name='" + unitIdTh + "']").val();
			if (goodsIdVal.length > 0) {
				jQuery.ajax({
					url : webPath+"/pssUnitGoodsRel/getAllByGoodsIdAjax",
					data : {
						goodsId : goodsIdVal
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						LoadingAnimate.stop();
						if (data.success) {
							var pssUnitGoodsRelMap = data.pssUnitGoodsRelMap;
							if (pssUnitGoodsRelMap !== null && pssUnitGoodsRelMap !== undefined && Object.keys(pssUnitGoodsRelMap).length > 0) {
								var unitJsonArray = new Array();
								for (var unitId in pssUnitGoodsRelMap) {
									var unitJson = { "id": unitId,
											 		 "name": pssUnitGoodsRelMap[unitId].unitName };
									unitJsonArray.push(unitJson);
									
									if (unitIdVal.length == 0 && pssUnitGoodsRelMap[unitId].isBase == "1") {
										$(tr).children("td:eq(" + unitNameThIndex + ")").children("input").val(unitId);
										$unitIdTd.children("input[name='" + unitIdTh + "']").val(unitId);
									}
								}
								
								$(tr).children("td:eq(" + unitNameThIndex + ")").on('click', function() {
									var $td = $(this);
									var $unitIdTd = $(this).parent("tr").children("td:eq(" + unitIdThIndex + ")");
									var inputLength = $(this).children("input").length;
									if (inputLength == 0) {
										var $input = $("<input style='display:none;' id='unitInput" + index + "'></input>");
										$(this).append($input);
									}
									var divLength = $(this).children("div .pops-select").length;
									if (divLength == 0 && $unitIdTd.children("input[name='" + unitIdTh + "']").val() != "") {
										$(this).children("input").popupSelection({
											searchOn:false, //启用搜索
											inline:true, //下拉模式
											multiple:false, //多选
											items:unitJsonArray,
											changeCallback : function (obj, elem) {
												$unitIdTd.children("input[name='" + unitIdTh + "']").val(obj.val());
												if (typeof(afterCheckUnit) == 'function') {
													afterCheckUnit($(tr), index, pssUnitGoodsRelMap[obj.val()]);
												}
												
												_popsSelectSelected($td);
											}
										});
										_addPopsSelectClick($td);
									}
								}).click();
							}
						}
					}
				});
			}
		});
	};
	
	var _disableStorehouseMap = null;
	
	var _addStorehouseEvent = function(storehouseNameTh, storehouseIdTh, afterCheckStorehouse) {
		var storehouseNameThIndex = $("#tablist>thead th[name='" + storehouseNameTh + "']").index();
		var storehouseIdThIndex = $("#tablist>thead th[name='" + storehouseIdTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $storehouseNameTd = $(tr).children("td:eq(" + storehouseNameThIndex + ")");
			var $input = $("<input style='display:none;' id='storehouseInput" + index + "'></input>");
			$storehouseNameTd.append($input);
			
			var $storehouseIdTd = $(tr).children("td:eq(" + storehouseIdThIndex + ")");
			var storehouseId = $storehouseIdTd.children("input[name='" + storehouseIdTh + "']").val();
			if (storehouseId.length > 0) {
				$input.val(storehouseId);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + storehouseNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $storehouseIdTd = $(this).parent("tr").children("td:eq(" + storehouseIdThIndex + ")");
				var storehouseId = $storehouseIdTd.children("input[name='" + storehouseIdTh + "']").val();
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='storehouseInput" + index + "'></input>");
					$(this).append($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					var storehouseJsonArray = ajaxData.storehouse.slice();
					if (Pss.disableStorehouseMap !== null && Pss.disableStorehouseMap !== undefined && Pss.disableStorehouseMap !== "") {
						var disableStorehouse = Pss.disableStorehouseMap[storehouseId];
						if (disableStorehouse !== null && disableStorehouse !== undefined) {
							var storehouseJson = { "id": disableStorehouse.storehouseId,
									 "name": disableStorehouse.storehouseName,
									 "flag" : disableStorehouse.flag };
							storehouseJsonArray.push(storehouseJson);
						}
					}
					$(this).children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:storehouseJsonArray,
						changeCallback : function (obj, elem) {
							$storehouseIdTd.children("input[name='" + storehouseIdTh + "']").val(obj.val());
							$tdSearchFlag = $storehouseIdTd.parent("tr").children("td[name=searchFlag]");
							if(!$tdSearchFlag.hasClass("i i-select")){
								$tdSearchFlag.addClass("i i-select");
							}
							
							for (var i in storehouseJsonArray) {
								if (obj.val() == storehouseJsonArray[i].id) {
									if (typeof(afterCheckStorehouse) == 'function') {
										afterCheckStorehouse($td.parent("tr"), index, storehouseJsonArray[i]);
									}
									break;
								}
							}
							
							_popsSelectSelected($td);
						}
					});
					
					/*for (var i in storehouseJsonArray) {
						if (storehouseId == storehouseJsonArray[i].id) {
							if (typeof(afterCheckStorehouse) == 'function') {
								afterCheckStorehouse($td.parent("tr"), index, storehouseJsonArray[i]);
							}
							break;
						}
					}*/
					
					_addPopsSelectClick($td);
				}
			}).click();
		});
	};
	
	var _addFreightSpaceEvent = function(freightSpaceNoTh, freightSpaceIdTh, storehouseIdTh) {
		var freightSpaceNoThIndex = $("#tablist>thead th[name='" + freightSpaceNoTh + "']").index();
		var freightSpaceIdThIndex = $("#tablist>thead th[name='" + freightSpaceIdTh + "']").index();
		var storehouseIdThIndex = $("#tablist>thead th[name='" + storehouseIdTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $freightSpaceNoTd = $(tr).children("td:eq(" + freightSpaceNoThIndex + ")");
			var $input = $("<input style='display:none;' id='freightSpaceInput" + index + "'></input>");
			$freightSpaceNoTd.append($input);
			
			var $freightSpaceIdTd = $(tr).children("td:eq(" + freightSpaceIdThIndex + ")");
			var freightSpaceId = $freightSpaceIdTd.children("input[name='" + freightSpaceIdTh + "']").val();
			if (freightSpaceId.length > 0) {
				$input.val(freightSpaceId);
			}
		});
		
		$(trs).each(function(index, tr) {
			var $storehouseIdTd = $(tr).children("td:eq(" + storehouseIdThIndex + ")");
			var storehouseIdVal = $storehouseIdTd.children("input[name='" + storehouseIdTh + "']").val();
			var $freightSpaceIdTd = $(tr).children("td:eq(" + freightSpaceIdThIndex + ")");
			var freightSpaceIdVal = $freightSpaceIdTd.children("input[name='" + freightSpaceIdTh + "']").val();
			if (storehouseIdVal.length > 0) {
				jQuery.ajax({
					url : webPath+"/pssFreightSpace/getAllByStorehouseIdAjax",
					data : {
						storehouseId : storehouseIdVal
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						LoadingAnimate.stop();
						if (data.success) {
							var pssFreightSpaceMap = data.pssFreightSpaceMap;
							if (pssFreightSpaceMap !== null && pssFreightSpaceMap !== undefined && Object.keys(pssFreightSpaceMap).length > 0) {
								var freightSpaceJsonArray = new Array();
								for (var freightSpaceId in pssFreightSpaceMap) {
									if (pssFreightSpaceMap[freightSpaceId].enabledStatus == "1" ||
											freightSpaceId == freightSpaceIdVal) {
										var freightSpaceJson = { "id": freightSpaceId,
						 		 				 				 "name": pssFreightSpaceMap[freightSpaceId].freightSpaceNo };
										freightSpaceJsonArray.push(freightSpaceJson);
									}
								}
								
								$(tr).children("td:eq(" + freightSpaceNoThIndex + ")").on('click', function() {
									var $td = $(this);
									var $freightSpaceIdTd = $(this).parent("tr").children("td:eq(" + freightSpaceIdThIndex + ")");
									var inputLength = $(this).children("input").length;
									if (inputLength == 0) {
										var $input = $("<input style='display:none;' id='freightSpaceInput" + index + "'></input>");
										$(this).append($input);
									}
									var divLength = $(this).children("div .pops-select").length;
									if (divLength == 0 && $freightSpaceIdTd.children("input[name='" + freightSpaceIdTh + "']").val() != "") {
										$(this).children("input").popupSelection({
											searchOn:false, //启用搜索
											inline:true, //下拉模式
											multiple:false, //多选
											items:freightSpaceJsonArray,
											changeCallback : function (obj, elem) {
												$freightSpaceIdTd.children("input[name='" + freightSpaceIdTh + "']").val(obj.val());
												
												_popsSelectSelected($td);
											}
										});
										_addPopsSelectClick($td);
									}
								}).click();
							}
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			}
		});
	};
	
	//table中td里是选择组件时，记录当前点击的td
	var $_clickTd = null;
	
	//table中选择组件点击事件，将td中的选择组建移到body中显示
	var _addPopsSelectClick = function($td) {
		$td.children().click(function(event) {
		    event.stopPropagation();
		});
		
		$td.children("div .pops-value").on('click', function() {
			if (Pss.$clickTd != null) {
				Pss.$clickTd.append($(".popsSelectDiv").children());
				$(".popsSelectDiv").remove();
			}
			var $td = $(this).parent();
			Pss.$clickTd = $td;
			var $popsSelectDiv = $("<div class='popsSelectDiv' style='position:absolute;'></div>");
			$popsSelectDiv.click(function(event) {
			    event.stopPropagation();
			});
			$popsSelectDiv.append($td.children());
			$("body").append($popsSelectDiv);
			var $storehouseSelect = $($popsSelectDiv.children(".pops-select").get(0));
			$popsSelectDiv.width($storehouseSelect.width());
			$popsSelectDiv.height($storehouseSelect.height());
			$popsSelectDiv.css({'left' : $td.offset().left});
			if (document.documentElement.clientHeight - $td.offset().top - $popsSelectDiv.height() < 60) {
				$popsSelectDiv.css({'bottom' : 60});
			} else {
				$popsSelectDiv.css({'top' : $td.offset().top});
			}
			var $storehouseValue = $($popsSelectDiv.children(".pops-value").get(0));
			$td.append($storehouseValue);
			$td.mouseleave();
		});
	};
	
	//body中选择组件选择后调用，将body中的选择组建移到当前td中
	var _popsSelectSelected = function($td) {
		$td.append($(".popsSelectDiv").children());
		$($td.children(".pops-value").get(0)).show();
		$(".popsSelectDiv").remove();
	};
	
	var _addQuantityEvent = function(quantityThs, pssConfig, automaticCalculation, refreshFormData) {
		$(quantityThs).each(function(index, th) {
			var thIndex = $("#tablist>thead th[name='" + th + "']").index();
			var trs = $("#tablist>tbody tr");
			$(trs).each(function(index, tr) {
				var text = '';
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					text = $.trim($(this).text());
					var inputCount = $(this).children("input").length;
					if(inputCount == 0) {
						var $input = $("<input type='text' class='editbox quantity' datatype='12' dbmaxlength='18' dotlength='" + pssConfig.numDecimalDigit + "' onkeydown='enterKey();' autocomplete='off'>");
						if(text != '') {
							text = _numberFormatter(text);
						}
						$(this).html($input).addClass('pad0');
						$(this).children('.editbox').val(text).select();
					}else{
						$(this).children('.editbox').select();
					}
				});
				
				$(tr).children("td:eq(" + thIndex + ")").on('blur', '.editbox', function() {
					var val = $.trim($(this).val());
					if (val.length == 0 || isNaN(val)) {
						val = "";
					}
					var $td = $(this).parent();
					if (val != text && typeof(automaticCalculation) == 'function') {
						automaticCalculation($td.parent("tr"), th, val, pssConfig);
					}
					
					var isQuantity = $(this).hasClass('quantity');
					if(val != '' && isQuantity){
						$(this).val(parseFloat(val).toFixed(pssConfig.numDecimalDigit));
						toDouble(this);
						val = $(this).val();
					}
					$td.html(val).removeClass('pad0');
					
					if (typeof(refreshFormData) == 'function') {
						refreshFormData();
					}
				});
			});
	    });
	};
	
	var _addMoneyEvent = function(moneyThs, pssConfig, automaticCalculation, refreshFormData) {
		$(moneyThs).each(function(index, th) {
			var thIndex = $("#tablist>thead th[name='" + th + "']").index();
			var trs = $("#tablist>tbody tr");
			$(trs).each(function(index, tr) {
				var text = '';
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					text = $.trim($(this).text());
					var inputCount = $(this).children("input").length;
					if (inputCount == 0) {
						var $input = $("<input type='text' class='editbox money' datatype='12' dbmaxlength='18' dotlength='" + pssConfig.amtDecimalDigit + "' onkeydown='enterKey();' autocomplete='off'>");
						if(text != '') {
							text = _numberFormatter(text);
						}
						$(this).html($input).addClass('pad0');
						$(this).children('.editbox').val(text).select();
					} else {
						$(this).children('.editbox').select();
					}
				});
				
				$(tr).children("td:eq(" + thIndex + ")").on('blur', '.editbox', function() {
					var val = $.trim($(this).val());
					if (val.length == 0 || isNaN(val)) {
						val = "";
					}
					var $td = $(this).parent();
					if (val != text && typeof(automaticCalculation) == 'function') {
						automaticCalculation($td.parent("tr"), th, val, pssConfig);
					}
					
					var isMoney = $(this).hasClass('money');
					if(val != '' && isMoney){
						$(this).val(parseFloat(val).toFixed(pssConfig.amtDecimalDigit));
						toMoney(this);
						val = $(this).val();
					}
					$td.html(val).removeClass('pad0');
					
					if (typeof(refreshFormData) == 'function') {
						refreshFormData();
					}
				});
			});
	    });
	};
	
	var _addDoubleEvent = function(doubleThs, pssConfig, automaticCalculation, refreshFormData) {
		$(doubleThs).each(function(index, th) {
			var thIndex = $("#tablist>thead th[name='" + th + "']").index();
			var trs = $("#tablist>tbody tr");
			$(trs).each(function(index, tr) {
				var text = '';
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					text = $.trim($(this).text());
					var inputCount = $(this).children("input").length;
					if(inputCount == 0) {
						var $input = $("<input type='text' class='editbox double' datatype='12' dbmaxlength='12' dotlength='8' onkeydown='enterKey();' autocomplete='off'>");
						if(text != '') {
							text = _numberFormatter(text);
						}
						$(this).html($input).addClass('pad0');
						$(this).children('.editbox').val(text).select();
					}else{
						$(this).children('.editbox').select();
					}
				});
				
				$(tr).children("td:eq(" + thIndex + ")").on('blur', '.editbox', function() {
					var val = $.trim($(this).val());
					if (val.length == 0 || isNaN(val)) {
						val = "";
					}
					var $td = $(this).parent();
					if (val != text && typeof(automaticCalculation) == 'function') {
						automaticCalculation($td.parent("tr"), th, val, pssConfig);
					}
					
					var isDouble = $(this).hasClass('double');
					if(val != '' && isDouble){
						toDouble(this);
						val = $(this).val();
					}
					$td.html(val).removeClass('pad0');
					
					if (typeof(refreshFormData) == 'function') {
						refreshFormData();
					}
				});
			});
	    });
	};
	
	var _addStringEvent = function(stringThs) {
		$(stringThs).each(function(index, th) {
			var thIndex = $("#tablist>thead th[name='" + th + "']").index();
			var trs = $("#tablist>tbody tr");
			$(trs).each(function(index, tr) {
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					var text = $.trim($(this).text());
					var inputCount = $(this).children("input").length;
					if(inputCount == 0) {
						var $input = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
						$(this).html($input).addClass('pad0');
						$(this).children('.editbox').focus().val(text);
					}else{
						$(this).children('.editbox').focus();
					}
				});
				
				$(tr).children("td:eq(" + thIndex + ")").on('blur', '.editbox', function() {
					var val = $.trim($(this).val());
					var $td = $(this).parent();
					$td.html(val).removeClass('pad0');
				});
			});
	    });
	};
	
	//table必输js
	var _pssAddTabMustEnter = function(tabHeadThs, id){
		$(tabHeadThs).each(function(index, th) {
			var $th = $(id + " #tablist>thead th[name='" + th + "']");
			if(!$th.children('span').hasClass('pssTabRed')){
				$th.prepend('<span class="pssTabRed">*</span>');
			}
	    });
	};
	//table必输校验
	var _pssTabMustEnterValid = function(tabHeadThs, id){
		var validVal = false;
		var isFirst = false;
		var isSuccess = false;
		var msg = "";
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			isFirst = false;
			$(tabHeadThs).each(function(index, th) {
				var $th = $(id + " #tablist>thead th[name='" + th + "']");
				var thIndex = $th.index();
				var thName = $th.html().replace("*","");
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var tdVal = $td.html();
				if($td.find("input").length != 0){
					tdVal = $td.find("input:first").val();
				}
				if(null == tdVal || "" == tdVal){
					validVal = false;
					msg = thName;
					return false;
				}else{
					validVal = true;
					isFirst = true;
					return true;
				}
			});
			if(isFirst && !validVal){
				return false;
			}
			if(validVal){
				isSuccess = true;
			}
		});
		if(isSuccess){
			if(isFirst && !validVal){
				alert(msg + "不能为空！", 1);
				return false;
			}else{
				return true;
			}
		}else{
			if(!validVal){
				alert(msg + "不能为空！", 1);
				return false;
			}else{
				return true;
			}
		}
	};
	
	//联动计算（购货订单、购货单、购货退货单、销货订单、销货单、销货退货单）
	var _automaticCalculation = function($tr, thName, tdValue, pssConfig) {
		//商品ID
		var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
		var $goodsIdTd = $tr.children("td:eq(" + goodsIdThIndex + ")");
		var goodsId = $goodsIdTd.children("input[name=goodsId]").val();
		if (goodsId.length == 0) {
			return;
		}
		//数量
		var quantityThIndex = $("#tablist>thead th[name=quantity]").index();
		var $quantityTd = $tr.children("td:eq(" + quantityThIndex + ")");
		//购货单价
		var unitPriceThIndex = $("#tablist>thead th[name=unitPrice]").index();
		var $unitPriceTd = $tr.children("td:eq(" + unitPriceThIndex + ")");
		//含税单价
		var taxUnitPriceThIndex = $("#tablist>thead th[name=taxUnitPrice]").index();
		var $taxUnitPriceTd = $tr.children("td:eq(" + taxUnitPriceThIndex + ")");
		//折扣率(%)
		var discountRateThIndex = $("#tablist>thead th[name=discountRate]").index();
		var $discountRateTd = $tr.children("td:eq(" + discountRateThIndex + ")");
		//折扣额
		var discountAmountThIndex = $("#tablist>thead th[name=discountAmount]").index();
		var $discountAmountTd = $tr.children("td:eq(" + discountAmountThIndex + ")");
		//金额
		var amountThIndex = $("#tablist>thead th[name=amount]").index();
		var $amountTd = $tr.children("td:eq(" + amountThIndex + ")");
		//税率(%)
		var taxRateThIndex = $("#tablist>thead th[name=taxRate]").index();
		var $taxRateTd = $tr.children("td:eq(" + taxRateThIndex + ")");
		//税额
		var taxAmountThIndex = $("#tablist>thead th[name=taxAmount]").index();
		var $taxAmountTd = $tr.children("td:eq(" + taxAmountThIndex + ")");
		//价税合计
		var totalPriceWithTaxThIndex = $("#tablist>thead th[name=totalPriceWithTax]").index();
		var $totalPriceWithTaxTd = $tr.children("td:eq(" + totalPriceWithTaxThIndex + ")");
		
		if (tdValue.length == 0 || isNaN(tdValue)) {
			tdValue = 0;
		}
        var quantity,taxUnitPrice,discountRate,taxRate,totalPriceWithTax,discountAmount,amount,unitPrice,taxAmount;
		if (thName == "quantity") {
			//数量联动计算
				quantity = tdValue;
				taxUnitPrice = _numberFormatter($taxUnitPriceTd.text());
				discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
				taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			if (taxUnitPrice.length > 0) {
				//折扣额 = 含税单价 / (1 + 税率) * 数量 * 折扣率
				discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//金额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率)
				amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 含税单价 * 数量 * (1 - 折扣率)
				totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$discountAmountTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "unitPrice") {
			//购货单价联动计算
            	unitPrice = _numberFormatter(tdValue);
				quantity = $quantityTd.text();
				discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
				taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			$unitPriceTd.text(_fmoney(parseFloat(unitPrice).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			//含税单价 = 购货单价 * (1 + 税率)
				taxUnitPrice = parseFloat(unitPrice) * (1 + parseFloat(taxRate) / 100);
			$taxUnitPriceTd.text(_fmoney(taxUnitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (quantity.length > 0) {
				//折扣额 = 购货单价 * 数量 * 折扣率
				discountAmount = parseFloat(unitPrice) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//金额 = 购货单价 * 数量 * (1 - 折扣率)
				amount = parseFloat(unitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 购货单价 * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(unitPrice) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 购货单价 * 数量 * (1 - 折扣率) * (1 + 税率)
				totalPriceWithTax = parseFloat(unitPrice) * parseFloat(quantity) * (1 - discountRate / 100) * (1 + taxRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$discountAmountTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "taxUnitPrice") {
			//含税单价联动计算
				quantity = $quantityTd.text();
				taxUnitPrice = _numberFormatter(tdValue);
				discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
				taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			$taxUnitPriceTd.text(_fmoney(parseFloat(taxUnitPrice).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			//购货单价 = 含税单价 / (1 + 税率)
			unitPrice = parseFloat(taxUnitPrice) / (1 + parseFloat(taxRate) / 100);
			$unitPriceTd.text(_fmoney(unitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (quantity.length > 0) {
				//折扣额 = 含税单价 / (1 + 税率) * 数量 * 折扣率
				discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//金额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率)
				amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 含税单价 * 数量 * (1 - 折扣率)
				totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$discountAmountTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "discountRate") {
			//折扣率联动计算
			quantity = $quantityTd.text();
			taxUnitPrice = _numberFormatter($taxUnitPriceTd.text());
			discountRate = tdValue;
			if (discountRate.length == 0) {
				discountRate = 0;
			}
				taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			if (quantity.length > 0 && taxUnitPrice.length > 0) {
				//折扣额 = 含税单价 / (1 + 税率) * 数量 * 折扣率
				discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//金额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率)
				amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 含税单价 * 数量 * (1 - 折扣率)
				totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$discountAmountTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "discountAmount") {
			//折扣额联动计算
			quantity = $quantityTd.text();
			taxUnitPrice = _numberFormatter($taxUnitPriceTd.text());
			discountAmount = _numberFormatter(tdValue);
			taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			$discountAmountTd.text(_fmoney(parseFloat(discountAmount).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (quantity.length > 0 && taxUnitPrice.length > 0 && parseFloat(taxUnitPrice) != 0) {
				//折扣率 = 折扣额 / (含税单价 / (1 + 税率) * 数量)
				discountRate = parseFloat(discountAmount) / (parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity)) * 100;
				$discountRateTd.text(parseFloat(discountRate.toFixed(8)));
				//金额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率)
				amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 含税单价 * 数量 * (1 - 折扣率)
				totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$discountRateTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "amount") {
			//金额联动计算
			quantity = $quantityTd.text();
			discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
			amount = _numberFormatter(tdValue);
			taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			$amountTd.text(_fmoney(parseFloat(amount).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (quantity.length > 0) {
				//含税单价 = 金额  / 数量 / (1 - 折扣率) * (1 + 税率)
				taxUnitPrice = parseFloat(amount) / parseFloat(quantity) / (1 - discountRate / 100) * (1 + taxRate / 100);
				$taxUnitPriceTd.text(_fmoney(taxUnitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//购货单价 = 含税单价 / (1 + 税率)
				unitPrice = parseFloat(taxUnitPrice) / (1 + parseFloat(taxRate) / 100);
				$unitPriceTd.text(_fmoney(unitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//折扣额 = 含税单价 / (1 + 税率) * 数量 * 折扣率
				discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 含税单价 * 数量 * (1 - 折扣率)
				totalPriceWithTax = parseFloat(taxUnitPrice) * parseFloat(quantity) * (1 - discountRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$taxUnitPriceTd.text("");
				$unitPriceTd.text("");
				$discountAmountTd.text("");
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "taxRate") {
			//税率联动计算
			quantity = $quantityTd.text();
			unitPrice = _numberFormatter($unitPriceTd.text());
			discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
			taxRate = tdValue;
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			if (unitPrice.length > 0) {
				//含税单价 = 购货单价 * (1 + 税率)
				taxUnitPrice = parseFloat(unitPrice) * (1 + parseFloat(taxRate) / 100);
				$taxUnitPriceTd.text(_fmoney(taxUnitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			}
			if (quantity.length > 0 && unitPrice.length > 0) {
				//税额 = 购货单价 * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(unitPrice) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//价税合计 = 购货单价 * 数量 * (1 - 折扣率) * (1 + 税率)
				totalPriceWithTax = parseFloat(unitPrice) * parseFloat(quantity) * (1 - discountRate / 100) * (1 + taxRate / 100);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$taxAmountTd.text("");
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "taxAmount") {
			//税额联动计算
			amount = _numberFormatter($amountTd.text());
			taxAmount = _numberFormatter(tdValue);
			$taxAmountTd.text(_fmoney(parseFloat(taxAmount).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (amount.length > 0) {
				//价税合计 = 金额 + 税额
				totalPriceWithTax = parseFloat(amount) + parseFloat(taxAmount);
				$totalPriceWithTaxTd.text(_fmoney(totalPriceWithTax.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$totalPriceWithTaxTd.text("");
			}
		} else if (thName == "totalPriceWithTax") {
			//价税合计联动计算
			quantity = $quantityTd.text();
			discountRate = $discountRateTd.text();
			if (discountRate.length == 0) {
				discountRate = 0;
			}
			taxRate = $taxRateTd.text();
			if (taxRate.length == 0) {
				taxRate = 0;
			}
			totalPriceWithTax = _numberFormatter(tdValue);
			$totalPriceWithTaxTd.text(_fmoney(parseFloat(totalPriceWithTax).toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			if (quantity.length > 0) {
				//含税单价 = 价税合计  / 数量 / (1 - 折扣率)
				taxUnitPrice = parseFloat(totalPriceWithTax) / parseFloat(quantity) / (1 - discountRate / 100);
				$taxUnitPriceTd.text(_fmoney(taxUnitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//购货单价 = 含税单价 / (1 + 税率)
				unitPrice = parseFloat(taxUnitPrice) / (1 + parseFloat(taxRate) / 100);
				$unitPriceTd.text(_fmoney(unitPrice.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//折扣额 = 含税单价 / (1 + 税率) * 数量 * 折扣率
				discountAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * parseFloat(discountRate) / 100;
				$discountAmountTd.text(_fmoney(discountAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//金额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率)
				amount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100);
				$amountTd.text(_fmoney(amount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
				//税额 = 含税单价 / (1 + 税率) * 数量 * (1 - 折扣率) * 税率
				taxAmount = parseFloat(taxUnitPrice) / (1 + taxRate / 100) * parseFloat(quantity) * (1 - discountRate / 100) * taxRate / 100;
				$taxAmountTd.text(_fmoney(taxAmount.toFixed(pssConfig.amtDecimalDigit), pssConfig.amtDecimalDigit));
			} else {
				$taxUnitPriceTd.text("");
				$unitPriceTd.text("");
				$discountAmountTd.text("");
				$amountTd.text("");
				$taxAmountTd.text("");
			}
		}
	};
	
	//查询库存
	var _dealSearchFlagHead = function(searchFlag, goodsId, id, isInvariant){
		var $searchFlagTh = $(id + " #tablist>thead th[name='" + searchFlag + "']");
		$searchFlagTh.addClass("pssSearchFlag");
		var searchFlagThIndex = $searchFlagTh.index();
		if (!isInvariant) {
			$searchFlagTh.addClass("pssSearchFlagRightBorder");
			$searchFlagTh.html("");
		}
		var goodsIdThIndex = $(id + " #tablist>thead th[name='" + goodsId + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $goodsIdTd = $(tr).children("td:eq(" + goodsIdThIndex + ")");
			var $searchFlagTd = $(tr).children("td:eq(" + searchFlagThIndex + ")");
			if(null != $goodsIdTd.find("input:first").val() && "" != $goodsIdTd.find("input:first").val()){
				$searchFlagTd.addClass("i i-select");
			}
			$searchFlagTd.attr("name", "searchFlag");
			$searchFlagTd.addClass("pssSearchFlag");
			if (!isInvariant) {
				$searchFlagTd.addClass("pssSearchFlagRightBorder");
			}
			$searchFlagTd.on('click', function(){
				var $td = $(this);
				$searchFlagTd = $td.parent("tr").children("td:eq(" + searchFlagThIndex + ")");
				$goodsIdTd = $td.parent("tr").children("td:eq(" + goodsIdThIndex + ")");
				if(!"searchFlag" == $searchFlagTd.attr("name")){
					$searchFlagTd.attr("name", "searchFlag");
				}
				if(!$searchFlagTd.hasClass("pssSearchFlag")){
					$searchFlagTd.addClass("pssSearchFlag");
				}
				
				if(!$searchFlagTd.hasClass("i i-select")){
					return;
				}
				var goodsIdTdVal = $goodsIdTd.html();
				//alert(goodsIdTdVal);
				if($goodsIdTd.find("input").length != 0){
					goodsIdTdVal = $goodsIdTd.find("input:first").val();
				}
				//alert(goodsIdTdVal);
				if(goodsIdTdVal == null || goodsIdTdVal == ""){
					alert("请先录入商品！", 1);
					return;
				}else{
					top.createShowDialog(webPath+"/pssStoreStock/getListPage?goodsId=" + goodsIdTdVal, '', '500px', '600px', function(){
					});
				}
			});
			//$searchFlagTd.attr("disabled", true);
		});
	};
	
	//去掉字符串中除数字和.之外的其它字符
	var _numberFormatter = function(text) {
		if (text !== null && text !== undefined && text !== '') {
			text += "";
			return text.replace(/[^\d\.-]/g, "");
		} else {
			return text;
		}
	};
	
	//金额型数字格式化（金额字符串, 保留n位小数）
	var _fmoney = function(s, n) { 
		var flag = 0;
		if(s < 0){
			s = Math.abs(s);
			flag = 1;
		}
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (var i = 0; i < l.length; i++) { 
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		}
		if(flag != 1){
			return t.split("").reverse().join("") + "." + r;
		}else{
			return "-" + t.split("").reverse().join("") + "." + r;
		}
	};
	
	//判断如果是金额型字符串，则转为数字型字符串
	var _ifMoneyToNumber = function(inputValue) {
		if(/^(-)?((\d){1,3})(([,](\d){3})+)([.](\d)+)?$/.test(inputValue)){
			return inputValue.replace(/,/g,"");
		} else {
			return inputValue;
		}
	};
	
	//新增采购销售费用清单
	var _insertPssBuySaleExpBill = function(sourceBillNo, callback){
		top.flag = false;
		if(!top.pssBuySaleExpArr){
			top.pssBuySaleExpArr = new Array();
		}
		//alert("top.pssBuySaleExpArr:"+JSON.stringify(top.pssBuySaleExpArr));
		var url = webPath+"/pssBuySaleExpBill/input?jsonArr="+encodeURIComponent(encodeURIComponent(JSON.stringify(top.pssBuySaleExpArr)))
			+"&sourceBillNo="+encodeURIComponent(encodeURIComponent(sourceBillNo));
		//alert(url);
		top.createShowDialog(url, '采购费用', '800px', '400px', function(){
			if(top.flag){
//				alert(JSON.stringify(pssBuySaleExpBillList.pssBuySaleExpArr));
//				alert(JSON.stringify(top.pssBuySaleExpArr));
				if (typeof(callback) == 'function') {
					callback();
				}
				return top.pssBuySaleExpArr;
			}
		});
	};
	
	var _isCheckAll = false;
	//全选事件
	var _addCheckAllEvent = function() {
		$(".table-float-head").delegate("th:first-child", "click", function() {
			if (Pss.isCheckAll) {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					this.checked = false;
				});
			 	Pss.isCheckAll = false;
			} else {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					this.checked = true;
				});
			 	Pss.isCheckAll = true;
			}
		});
	};
	
	//单选事件
	var _checkCurrentTr = function(obj) {
		var trCheckbox = $(obj).find(':checkbox').get(0);
		if (trCheckbox.checked) {
			trCheckbox.checked = false;
		} else {
			trCheckbox.checked = true;
		}
	};
	
	//日期控件
	var _dateClickEvent = function(obj) {
	    $('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: true,
				choose:function(data){
				}	
			});
		});
	};
	
	var _getCurrentSystemDate = function(sep){
		var date = new Date(); 
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
		if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
		return year + sep + month + sep + strDate;
	};
	
	return {
		addRowOperateEvent : _addRowOperateEvent,
		initSequence : _initSequence,
		disableGoodsMap : _disableGoodsMap,
		addGoodsEvent : _addGoodsEvent,
		addUnitEvent : _addUnitEvent,
		disableStorehouseMap : _disableStorehouseMap,
		addStorehouseEvent : _addStorehouseEvent,
		addFreightSpaceEvent : _addFreightSpaceEvent,
		$clickTd : $_clickTd,
		addPopsSelectClick : _addPopsSelectClick,
		popsSelectSelected : _popsSelectSelected,
		addMoneyEvent : _addMoneyEvent,
		addDoubleEvent : _addDoubleEvent,
		addStringEvent : _addStringEvent,
		addQuantityEvent : _addQuantityEvent,
		pssAddTabMustEnter : _pssAddTabMustEnter,
		pssTabMustEnterValid : _pssTabMustEnterValid,
		automaticCalculation : _automaticCalculation,
		dealSearchFlagHead : _dealSearchFlagHead,
		numberFormatter : _numberFormatter,
		fmoney : _fmoney,
		ifMoneyToNumber : _ifMoneyToNumber,
		insertPssBuySaleExpBill : _insertPssBuySaleExpBill,
		
		isCheckAll : _isCheckAll,
		addCheckAllEvent : _addCheckAllEvent,
		checkCurrentTr : _checkCurrentTr,
		dateClickEvent : _dateClickEvent,
		
		getCurrentSystemDate : _getCurrentSystemDate
	};
}(window, jQuery);

//窗口改变bug处理
window.onresize = function(){
	setTimeout(function(){
		$('.pss_detail_list').css('height', 'auto');
	    $('.pss_detail_list #mCSB_1').css('height', 'auto');
	    $('.pss_detail_list #mCSB_2').css('height', 'auto');
	    $('.pss_detail_list .table-float-head').remove();
	},200);
};

//重写toFixed方法  
Number.prototype.toFixed = function(n) {
    if (n > 20 || n < 0) {
    	window.top.alert('toFixed() digits argument must be between 0 and 20', 0);
    	return false;
    }
 
    var number = this;
     
    if (isNaN(number) || number >= Math.pow(10, 21)) {
        return number.toString();
    }
    if (typeof(n) == 'undefined' || n == 0) {
        return (Math.round(number)).toString();
    }
 
    var result = number.toString();
    var arr = result.split('.');
    var i;
 
    // 整数的情况
    if (arr.length < 2) {
        result += '.';
        for (i = 0; i < n; i++) {
            result += '0';
        }
        return result;
    }
 
    var integer = arr[0];
    var decimal = arr[1];
    if (decimal.length == n) {
        return result;
    }
    if (decimal.length < n) {
        for (i = 0; i < n - decimal.length; i++) {
            result += '0';
        }
        return result;
    }
    result = integer + '.' + decimal.substr(0, n);
 
    var last = decimal.substr(n, 1);
 
    // 四舍五入，转换为整数再处理，避免浮点数精度的损失
    if (parseInt(last) >= 5) {
        var x = Math.pow(10, n);
        if (result >= 0) {
          result = Math.round((parseFloat(result) * x + 1)) / x;
        } else {
          result = Math.round((parseFloat(result) * x - 1)) / x;
        }
        
        result = result.toFixed(n);
    }
    return result;
 
};

$(document).click(function() {
	if (Pss.$clickTd != null) {
		Pss.$clickTd.append($(".popsSelectDiv").children());
		$(".popsSelectDiv").remove();
	}
});