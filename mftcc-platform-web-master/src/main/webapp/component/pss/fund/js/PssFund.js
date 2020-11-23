;
var pssFund = function(window, $){
	var _addRowOperateEvent = function (th, id, refreshFormData) {
		var thIndex = $(id + " #tablist>thead th[name='" + th + "']").index();
		var trs = $(id + " #tablist>tbody tr");
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
		
		$(id + " #tablist").on("click", ".add", function(a) {
			var $tr = $(this).parents('tr');
			var $newTr = $tr.clone(true);
			var $tds = $newTr.children("td");
			$tds.each(function() {
			    if ($(this).children("input").length == 0) {
			    	$(this).text("");
			    } else if ("display:none" == $(this).attr("style")){
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
			initSequence(th, id);
			//结算账户
			var clearanceAccountNameThIndex = $(id + " #tablist>thead th[name='clearanceAccountName']").index();
			if (clearanceAccountNameThIndex >= 0) {
				var $clearanceAccountNameTd = $newTr.children("td:eq(" + clearanceAccountNameThIndex + ")");
				$clearanceAccountNameTd.text("");
				$clearanceAccountNameTd.click();
			}
			//结算方式
			var clearanceAccountTypeThIndex = $(id + " #tablist>thead th[name='clearanceAccountType']").index();
			if (clearanceAccountTypeThIndex >= 0) {
				var $clearanceAccountTypeTd = $newTr.children("td:eq(" + clearanceAccountTypeThIndex + ")");
				$clearanceAccountTypeTd.text("");
				$clearanceAccountTypeTd.click();
			}
			//供应商
			var supplierNameThIndex = $(id + " #tablist>thead th[name='supplierName']").index();
			if(supplierNameThIndex >= 0){
				var $supplierNameTd = $newTr.children("td:eq(" + supplierNameThIndex + ")");
				$supplierNameTd.text("");
				$supplierNameTd.click();
			}
			//支出类别
			var saleTypeThIndex = $(id + " #tablist>thead th[name='saleType']").index();
			if (saleTypeThIndex >= 0) {
				var $saleTypeTd = $newTr.children("td:eq(" + saleTypeThIndex + ")");
				$saleTypeTd.text("");
				$saleTypeTd.click();
			}
		});
		$(id + " #tablist").on("click", ".del", function(a) {
			var trLength = $(id + " #tablist>tbody tr").length;
			if (trLength > 1) {
				var $tr = $(this).parents('tr');
				$tr.remove();
				initSequence(th, id);
				if (typeof(refreshFormData) == 'function') {
					refreshFormData();
				}
			} else {
				alert(top.getMessage("ONLY_CW_VOUCHER_ONEENTRY", ""), 0);
			}
		});
	};
	
	function initSequence(th, id) {
		var thIndex = $(id + " #tablist>thead th[name='" + th + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		var i = 0;
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + thIndex + ")").text(++i);
		});
	}
	
	var _addAccountEvent = function (clearAccNameTh, clearAccIdTh, id) {
		var clearAccNameThIndex = $(id + " #tablist>thead th[name='" + clearAccNameTh + "']").index();
		var clearAccIdThIndex = $(id + " #tablist>thead th[name='" + clearAccIdTh + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $clearAccNameTd = $(tr).children("td:eq(" + clearAccNameThIndex + ")");
			var $input = $("<input style='display:none;' id='clearAccInput" + index + "'></input>");
			$clearAccNameTd.html($input);
			
			var $clearAccIdTd = $(tr).children("td:eq(" + clearAccIdThIndex + ")");
			var clearAccId = $clearAccIdTd.children("input[name='" + clearAccIdTh + "']").val();
			if (clearAccId.length > 0) {
				$input.val(clearAccId);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + clearAccNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $clearAccIdInput = $(this).parent("tr").children("td:eq(" + clearAccIdThIndex + ")").children("input[name='" + clearAccIdTh + "']");
				var $clearAccNameInput = $(this).children("input");
				var inputLength = $clearAccNameInput.length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='clearAccInput" + index + "'></input>");
					$(this).html($input);
					$clearAccNameInput = $(this).children("input");
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:true, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.settlementAccount,
						changeCallback : function (obj, elem) {
							$clearAccIdInput.val(obj.val());
							$clearAccNameInput.val(obj.data("text"));
							
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
	
	var _addMoneyEvent = function (moneyTh, dotLength, id, isCheck, chkMoneyTh, isCal, calCurrAdvRecAmtFunc) {
		var moneyThIndex = $(id + " #tablist>thead th[name='" + moneyTh + "']").index();
		var chkMoneyThIndex = 0;
		if(isCheck){
			chkMoneyThIndex = $(id + " #tablist>thead th[name='" + chkMoneyTh + "']").index();
		}
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + moneyThIndex + ")").on('click', function() {
				var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
				var inputCount = $(this).children("input").length;
				if(inputCount == 0) {
					var $input = $("<input type='text' class='editbox money' datatype='12' dbmaxlength='18' dotlength='" + dotLength + "' onkeydown='enterKey();' autocomplete='off'>");
					if(text != '') {
						text = text.replace(/[^\d\.-]/g, "");
					}
					$(this).html($input).addClass('pad0');
					$(this).children('.editbox').focus().val(text);
				}else{
					$(this).children('.editbox').focus();
				}
			});
			
			$(tr).children("td:eq(" + moneyThIndex + ")").on('blur', '.editbox', function() {
				var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				var $td = $(this).parent();
				var isMoney = $(this).hasClass('money');
				if(val != ''){
					if(/^((-)?)((\d)+)([.](\d)+)?$/.test(val)){
					}else{
						alert("输入金额不合法请重新输入，参考666.66", 1);
						val = '';
					}
				}
				if(val != '' && isCheck){
					var $chkMoneyTr = $(tr).children("td:eq(" + chkMoneyThIndex + ")");
					var chkMoneyVal = $chkMoneyTr.html().replace(/,/g,"");
					if(Number(chkMoneyVal) > 0 && Number(val) > Number(chkMoneyVal)){
						alert("本次核销金额不能大于未核销金额", 1);
						val = '';
					}else if(Number(chkMoneyVal) < 0 && Number(val) < Number(chkMoneyVal)){
						alert("本次核销金额不能小于未核销金额", 1);
						val = '';
					}
				}
				if(val != '' && isMoney){
					val = Number(val).toFixed(dotLength).toString();
					$(this).val(val);
					toMoney(this);
					val = $(this).val();
					//val = Number(val).toFixed(dotLength).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
				}
				$td.html(val).removeClass('pad0');
				if(isCal && typeof(calCurrAdvRecAmtFunc) == 'function'){
					calCurrAdvRecAmtFunc.call(this, dotLength);
				}
			});
		});
	};
	
	var _addStringEvent = function (stringThs, id) {
		$(stringThs).each(function(index, th) {
			var thIndex = $(id + " #tablist>thead th[name='" + th + "']").index();
			var trs = $(id + " #tablist>tbody tr");
			$(trs).each(function(index, tr) {
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
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
					var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
					var $td = $(this).parent();
					$td.html(val).removeClass('pad0');
				});
			});
	    });
	};
	
	var _addClearAccTypeEvent = function (clearAccTypeTh, id) {
		var clearAccTypeThIndex = $(id + " #tablist>thead th[name='" + clearAccTypeTh + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $clearAccTypeTd = $(tr).children("td:eq(" + clearAccTypeThIndex + ")");
			var clearAccTypeVal = $clearAccTypeTd.html();
			var $input = $("<input style='display:none;' id='clearAccType" + index + "'></input>");
			$clearAccTypeTd.html($input);
			
			if (clearAccTypeVal.length > 0) {
				$input.val(clearAccTypeVal);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + clearAccTypeThIndex + ")").on('click', function() {
				var $td = $(this);
				//var $clearAccTypeTd = $(this).parent("tr").children("td:eq(" + clearAccTypeThIndex + ")");
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='clearAccType" + index + "'></input>");
					$(this).html($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:true, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.clearAccType,
						addBtn:{//添加扩展按钮
							"title":"新增",
							"fun":function(hiddenInput, elem){
								$(elem).popupSelection("hideSelect", elem);
								BASE.openDialogForSelect('结算方式','PSS_CLEAR_ACC_TYPE', elem);
							}
						},
						changeCallback : function (obj, elem) {
							/*$clearAccTypeTd.children("input[name='" + clearAccIdTh + "']").val(obj.val());*/
							Pss.popsSelectSelected($td);
						}
					});
				}
				Pss.addPopsSelectClick($td);
				/*var tdTop = $(this).position().top;
				$(this).children("div .pops-select").css({'top':tdTop});*/
			}).click();
		});
	};
	
	//供应商
	var _addSupplierNameEvent = function (supplierNameTh, supplierIdTh, id) {
		var supplierNameThIndex = $(id + " #tablist>thead th[name='" + supplierNameTh + "']").index();
		var supplierIdThIndex = $(id + " #tablist>thead th[name='" + supplierIdTh + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $supplierNameTd = $(tr).children("td:eq(" + supplierNameThIndex + ")");
			var $input = $("<input style='display:none;' id='supplierInput" + index + "'></input>");
			$supplierNameTd.html($input);
			
			var $supplierIdTd = $(tr).children("td:eq(" + supplierIdThIndex + ")");
			var supplierId = $supplierIdTd.children("input[name='" + supplierIdTh + "']").val();
			if (supplierId.length > 0) {
				$input.val(supplierId);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + supplierNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $supplierIdInput = $(this).parent("tr").children("td:eq(" + supplierIdThIndex + ")").children("input[name='" + supplierIdTh + "']");
				var $supplierNameInput = $(this).children("input");
				var inputLength = $supplierNameInput.length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='supplierInput" + index + "'></input>");
					$(this).html($input);
					$supplierNameInput = $(this).children("input");
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:true, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.supp,
						changeCallback : function (obj, elem) {
							$supplierIdInput.val(obj.val());
							$supplierNameInput.val(obj.data("text"));
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
	
	//支出类别
	var _addSaleTypeEvent = function (saleTypeTh, id) {
		var saleTypeThIndex = $(id + " #tablist>thead th[name='" + saleTypeTh + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $saleTypeTd = $(tr).children("td:eq(" + saleTypeThIndex + ")");
			var saleTypeVal = $saleTypeTd.html();
			var $input = $("<input style='display:none;' id='saleType" + index + "'></input>");
			$saleTypeTd.html($input);
			
			if (saleTypeVal.length > 0) {
				$input.val(saleTypeVal);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + saleTypeThIndex + ")").on('click', function() {
				var $td = $(this);
				//var $clearAccTypeTd = $(this).parent("tr").children("td:eq(" + clearAccTypeThIndex + ")");
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='saleType" + index + "'></input>");
					$(this).html($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:true, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.saleType,
						/*addBtn:{//添加扩展按钮
							"title":"新增",
							"fun":function(hiddenInput, elem){
								$(elem).popupSelection("hideSelect", elem);
								BASE.openDialogForSelect('支出类别','PSS_SALE_TYPE', elem);
							}
						},*/
						changeCallback : function (obj, elem) {
							/*$clearAccTypeTd.children("input[name='" + clearAccIdTh + "']").val(obj.val());*/
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
	
	//计算收付款金额
	var _calRecPayAmt = function(recPayAmt, id, amtDecimalDigit){
		if(undefined == amtDecimalDigit){
			amtDecimalDigit = ajaxData.pssConfig.amtDecimalDigit;
		}
		var recPayAmtThIndex = $(id + " #tablist>thead th[name='" + recPayAmt + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		var recPayAmtVal = 0;
		$(trs).each(function(index, tr) {
			var $recPayAmtTd = $(tr).children("td:eq(" + recPayAmtThIndex + ")");
			//recPayAmtVal += Number($recPayAmtTd.html().replace(/,/g, ""));
			recPayAmtVal = _calDouComMethod(recPayAmtVal, Number($recPayAmtTd.html().replace(/,/g, "")), amtDecimalDigit, 'add');
		});
		return recPayAmtVal;
	};
	
	//计算核销金额 return: -1（无源单信息），0（有源单信息但未录入本次核销金额或者录入为0）
	var _calCancelAmt = function(sourceBillNo, currCancelAmt, id, amtDecimalDigit){
		if(undefined == amtDecimalDigit){
			amtDecimalDigit = ajaxData.pssConfig.amtDecimalDigit;
		}
		var isCal = false;
		var isZero = false;
		var sourceBillNoThIndex = $(id + " #tablist>thead th[name='" + sourceBillNo + "']").index();
		var currCancelAmtThIndex = $(id + " #tablist>thead th[name='" + currCancelAmt + "']").index();
		var trs = $(id + " #tablist>tbody tr");
		var currCancelAmtVal = 0;
		$(trs).each(function(index, tr) {
			var $sourceBillNoTd = $(tr).children("td:eq(" + sourceBillNoThIndex + ")");
			if("" == $sourceBillNoTd.html()){
				return true;
			}
			
			isCal = true;
			var $currCancelAmtTd = $(tr).children("td:eq(" + currCancelAmtThIndex + ")");
			var currCancelAmtTdVal = Number($currCancelAmtTd.html().replace(/,/g, ""));
			if(currCancelAmtTdVal == 0 || isNaN(currCancelAmtTdVal)){
				currCancelAmtVal = 0;
				isZero = false;
				return false;
			}
			//currCancelAmtVal += currCancelAmtTdVal;
			currCancelAmtVal = _calDouComMethod(currCancelAmtVal, currCancelAmtTdVal, amtDecimalDigit, 'add');
			isZero = true;
			
		});
		if(!isCal){
			return 'N';
		}else if(isCal && isZero && currCancelAmtVal == 0){
			return 'N';
		}
		return currCancelAmtVal;
	};
	
	var _tableJsonDeal = function(id){
		var jsonArr = new Array();
		$(id + " table tbody tr").each(function(trIndex, tr) {
			var jsonObj = new Object();
			$(id + " table thead th").each(function(thIndex, th) {
				var tdVal = $(tr).children("td:eq(" + thIndex + ")").text();
				if(/^(-)?((\d){1,3})(([,](\d){3})+)([.](\d)+)?$/.test(tdVal)){
					tdVal = tdVal.replace(/,/g, "");
				}
				//加入日期格式校验
				if(/^[1-2]\d{3}-(0?[1-9]||1[0-2])-(0?[1-9]||[1-2][0-9]||3[0-1])$/.test(tdVal)){
					tdVal = tdVal.replace(/\-/g, "");
				}
				//获取隐藏域中值
				if($(tr).children("td:eq(" + thIndex + ")").find("input").length != 0){
					tdVal = $(tr).children("td:eq(" + thIndex + ")").find("input").val();
				}
				jsonObj[$(th).attr("name")] = tdVal;
			});
			jsonArr.push(jsonObj);
		});
		return jsonArr;
	};
	
	//计算本次预收款 参数1：formId 参数2：预收金额  参数3：收款tableid 参数4：收款金额字段 参数5：核销tableid 参数6：核销金额字段  参数7：折扣金额字段（在form中） 参数8：金额保留位数
	var _calCurrAdvRecAmt = function(formId, currAdvRecPayAmtTh, id1, recPayAmtTh, id2, currCancelAmtTh, fullDiscountTh, amtDecimalDigit){
		var recPayAmtThIndex = $(id1 + " #tablist>thead th[name='" + recPayAmtTh + "']").index();
		var currCancelAmtThIndex = $(id2 + " #tablist>thead th[name='" + currCancelAmtTh + "']").index();
		
		var recPayAmtVal = 0;
		var tr1s = $(id1 + " #tablist>tbody tr");
		$(tr1s).each(function(index, tr) {
			var $recPayAmt = $(tr).children("td:eq(" + recPayAmtThIndex + ")");
			if($recPayAmt.hasClass('money')){
				if("" == $recPayAmt.children(".money").val()){
					return true;
				}
				//recPayAmtVal += Number($recPayAmt.children(".money").val());
				recPayAmtVal = _calDouComMethod(recPayAmtVal, Number($recPayAmt.children(".money").val()), amtDecimalDigit, 'add');
			}else{
				if("" == $recPayAmt.html()){
					return true;
				}
				//recPayAmtVal += Number($recPayAmt.html().replace(/,/g, ""));
				recPayAmtVal = _calDouComMethod(recPayAmtVal, Number($recPayAmt.html().replace(/,/g, "")), amtDecimalDigit, 'add');
			}
		});
		
		var currCancelAmtVal = 0;
		var tr2s = $(id2 + " #tablist>tbody tr");
		$(tr2s).each(function(index, tr){
			var $currCancelAmt = $(tr).children("td:eq(" + currCancelAmtThIndex + ")");
			if($currCancelAmt.hasClass('money')){
				if("" == $currCancelAmt.children(".money").val()){
					return true;
				}
				//currCancelAmtVal += Number($currCancelAmt.children(".money").val());
				currCancelAmtVal = _calDouComMethod(currCancelAmtVal, Number($currCancelAmt.children(".money").val()), amtDecimalDigit, 'add');
			}else{
				if("" == $currCancelAmt.html()){
					return true;
				}
				//currCancelAmtVal += Number($currCancelAmt.html().replace(/,/g, ""));
				currCancelAmtVal = _calDouComMethod(currCancelAmtVal, Number($currCancelAmt.html().replace(/,/g, "")), amtDecimalDigit, 'add');
			}
		});
		
		var $currAdvRecPayAmt = $(formId + " input[name=" + currAdvRecPayAmtTh + "]");
		var $fullDiscount = $(formId + " input[name=" + fullDiscountTh + "]");
		var fullDiscountVal = Number($fullDiscount.val().replace(/,/g, ""));
		//$currAdvRecPayAmt.val(recPayAmtVal - currCancelAmtVal + fullDiscountVal);
		$currAdvRecPayAmt.val(_calDouComMethod(_calDouComMethod(recPayAmtVal, currCancelAmtVal, amtDecimalDigit, 'sub')
				, fullDiscountVal, amtDecimalDigit, 'add'));
	};
	
	//double类型数据公共计算处理 参数1：主参数  参数2：副参数  参数3：保留位数  参数4：计算方式
	var _calDouComMethod = function(dou1, dou2, digit, operator){
		var result = 0;
		var m = Math.pow(10, digit);
		if('add' == operator){
			result = Number(((dou1 * m + dou2 * m) / m).toFixed(digit));
		}else if('sub' == operator){
			result = Number(((dou1 * m - dou2 * m) / m).toFixed(digit));
		}else if('mul' == operator){
			result = Number((((dou1 * m) * (dou2 * m)) / m*m).toFixed(digit));
		}else if('div' == operator || operator == '' || operator == undefined){
			result = Number((((dou1 * m) / (dou2 * m))).toFixed(digit));
		}
		
		return result;
	};
	
	return{
		addRowOperateEvent : _addRowOperateEvent,
		addAccountEvent : _addAccountEvent,
		addMoneyEvent : _addMoneyEvent,
		addStringEvent : _addStringEvent,
		addClearAccTypeEvent : _addClearAccTypeEvent,
		addSupplierNameEvent : _addSupplierNameEvent,
		addSaleTypeEvent : _addSaleTypeEvent,
		calRecPayAmt : _calRecPayAmt,
		calCancelAmt : _calCancelAmt,
		tableJsonDeal : _tableJsonDeal,
		calCurrAdvRecAmt : _calCurrAdvRecAmt,
		calDouComMethod : _calDouComMethod
	};
}(window, jQuery);