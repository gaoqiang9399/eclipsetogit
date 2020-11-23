;
var PssStockCommon = function(window, $){
	
	//鼠标点击选择商品
	var _addSelectGoodsEvent = function(tdName) {
		var thIndex = $("#tablist>thead th[name='" + tdName + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + thIndex + ")").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				items:ajaxData.goods,
				changeCallback : function (obj, elem) {
					//alert(obj.val());
					$(tr).children("td:eq(" + thIndex + ")").text(obj.val());
				}
			});
			
		});
	};
	
	//鼠标点击选择仓库
	/*var _addSelectStoresEvent = function(tdNames){
		$(tdNames).each(function(index, th){
			var thIndex = $("#tablist>thead th[name='" + th + "']").index();
			var trs = $("#tablist>tbody tr");
			$(trs).each(function(index, tr){
				$(tr).children("td:eq(" + thIndex + ")").on('click', function() {
					var text = $(this).text();
					var selectCount = $(this).children("select").length;
					if(selectCount == 0){
						var $select = $("<select class='items-btn menu-btn' name='storeId' id='storeId' autocomplete='off' onchange='storeChange("+$(this)+");'></select>");
						$(this).html($select);
						if(storeIds){
							var pact = eval(storeIds);
							var opt = $(this).children('#storeId')[0];
							opt.options.length=0;
							for(var i=0; i<pact.length; i++){
								opt.add(new Option(pact[i].storehouseName, pact[i].storehouseId));
							}
							if(text != ''){
								$(this).children('#storeId').val(text);
							}
							$(this).children('#storeId').focus();
						}
					}
				});
				
				$(tr).children("td:eq(" + thIndex + ")").on('blur', '#storeId', function() {
					var optVal = $(this).val();
					var optName = $(this).find("option:selected").text();
					var $td = $(this).parent();
					$td.html(optName);
				});
			});
		});
	};*/
	
	//鼠标点击选择仓库 tdOptName-选择列名称（显示选择名称），tdOptValue-选择列值（显示选择值）
	var _addSelectStoresEvent = function(tdOptValue, tdOptName){
		var thValIndex = $("#tablist>thead th[name='" + tdOptValue + "']").index();
		var thNameIndex = $("#tablist>thead th[name='" + tdOptName + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr){
			var tdVal = "";
			var tdName = "";
			$(tr).children("td:eq(" + thNameIndex + ")").on('click', function() {
				var selectCount = $(this).children("select").length;
				if(selectCount == 0){
					var $tdVal = $(tr).children("td:eq(" + thValIndex + ")");
					tdVal = $tdVal.find("input").val();
					tdName = $(this).text();
					var $select = $("<select class='items-btn menu-btn' name='storeId' id='storeId' autocomplete='off'></select>");
					$(this).html($select);
					if(storeIds){
						var pact = eval(storeIds);
						var opt = $(this).children('#storeId')[0];
						opt.options.length=0;
						for(var i=0; i<pact.length; i++){
							opt.add(new Option(pact[i].storehouseName, pact[i].storehouseId));
						}
						//if(tdVal != ''){
							$(this).children('#storeId').val(tdVal);
						//}
						$(this).children('#storeId').focus();
					}
				}
			});
			
			$(tr).children("td:eq(" + thNameIndex + ")").on('blur', '#storeId', function() {
				var optVal = $(this).val();
				var optName = $(this).find("option:selected").text();
				var $tdVal = $(tr).children("td:eq(" + thValIndex + ")").find("input");
				var $tdName = $(this).parent();
				if(null != optName && "" != optName){
				}else{
					optVal = tdVal;
					optName = tdName;
				}
				$tdVal.val(optVal);
				$tdName.html(optName);					
			});
		});
	};
	
	//根据系统参数1、输入参数2、值参数3、输入保留小数位数参数4、运算符（add,sub,mul,div）参数5进行计算(默认sub)
	var _calChkStockResult = function(sysTdName, chgTdName, calTdName, pssConfig, operator){
		var sysThIndex = $("#tablist>thead th[name='" + sysTdName + "']").index();
		var chgThIndex = $("#tablist>thead th[name='" + chgTdName + "']").index();
		var calThIndex = $("#tablist>thead th[name='" + calTdName + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + chgThIndex + ")").on('click', function() {
				var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
				var inputCount = $(this).children("input").length;
				if(inputCount == 0) {
					var $input = $("<input type='text' class='editbox quantity' datatype='12' dbmaxlength='18' dotlength='" + pssConfig.numDecimalDigit + "' onkeydown='enterKey();' autocomplete='off'>");
					if(text != '') {
						text = text.replace(/[^\d\.-]/g, "");
					}
					$(this).html($input).addClass('pad0');
					$(this).children('.editbox').focus().val(text);
				}else{
					$(this).children('.editbox').focus();
				}
			});
			
			$(tr).children("td:eq(" + chgThIndex + ")").on('blur', '.editbox', function() {
				var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				var $td = $(this).parent();
				var isQuantity = $(this).hasClass('quantity');
				if(val != '' && isQuantity){
					toDouble(this);
					val = $(this).val();
				}
				$td.removeClass('pad0');
				var $calTd = $td.parent().children("td:eq(" + calThIndex + ")"); 
				if(/^-?[1-9]\d*|0$/.test(val)){
					val = Number(val);
					var sysVal = $td.parent().children("td:eq(" + sysThIndex + ")").html().replace(/,/g,'');
					if(sysVal == ''){
						$td.html('');
						$calTd.html('');
						return;
					}
					var calVal;
					if('add' == operator){
						calVal = val + sysVal;
					}else if('mul' == operator){
						calVal = val * sysVal;
					}else if('div' == operator){
						calVal = val / sysVal;
					}else{
						calVal = val - sysVal;
					}
					if(calVal < 0){
						$calTd.addClass('td_color');
					}else{
						if($calTd.hasClass('td_color')){
							$calTd.removeClass('td_color');
						}
					}
					/*calVal = calVal.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");*/
					$td.html(val.toFixed(pssConfig.numDecimalDigit));
					$calTd.html(calVal.toFixed(pssConfig.numDecimalDigit));
				}else if(val == ''){
					$td.html(val);
					$calTd.html('');
				}else{
					$td.html('');
					$calTd.html('');
					alert("输入格式有误，参考：666",1);
				}
			});
		});
	};
	
	//根据系统参数1、输入参数2、值参数3、输入保留小数位数参数4、运算符（add,sub,mul,div）参数5进行计算(默认sub)
	var _calInStockCost = function(sysTdName, chgTdName, calTdName, pssConfig, operator){
		var sysThIndex = $("#tablist>thead th[name='" + sysTdName + "']").index();
		var chgThIndex = $("#tablist>thead th[name='" + chgTdName + "']").index();
		var calThIndex = $("#tablist>thead th[name='" + calTdName + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + chgThIndex + ")").on('click', function() {
				var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
				var inputCount = $(this).children("input").length;
				if(inputCount == 0) {
					var $input = $("<input type='text' class='editbox money' datatype='12' dbmaxlength='18' dotlength='" + pssConfig.amtDecimalDigit + "' onkeydown='enterKey();' autocomplete='off'>");
					if(text != '') {
						text = text.replace(/[^\d\.-]/g, "");
					}
					$(this).html($input).addClass('pad0');
					$(this).children('.editbox').focus().val(text);
				}else{
					$(this).children('.editbox').focus();
				}
			});
			
			$(tr).children("td:eq(" + chgThIndex + ")").on('blur', '.editbox', function() {
				var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				var $td = $(this).parent();
				var isMoney = $(this).hasClass('money');
				if(val != '' && isMoney){
//					func_uior_valTypeImm(this);姑且直接四舍五入
					val = Number(val).toFixed(pssConfig.amtDecimalDigit).toString();
					$(this).val(val);
					toMoney(this);
					val = $(this).val();
				}
				$td.removeClass('pad0');
				var $calTd = $td.parent().children("td:eq(" + calThIndex + ")"); 
				if(/^(-)?((\d){1,3})(([,](\d){3})*)([.](\d)+)?$/.test(val)){
					$td.html(val);
					val = val.replace(/,/g,'');
					var sysVal = $td.parent().children("td:eq(" + sysThIndex + ")").html().replace(/,/g,'');
					if(sysVal == ''){
						$td.html('');
						$calTd.html('');
						return;
					}
					var calVal;
					if('add' == operator){
						calVal = val + sysVal;
					}else if('mul' == operator){
						calVal = val * sysVal;
					}else if('div' == operator){
						calVal = val / sysVal;
					}else{
						calVal = val - sysVal;
					}
					if(calVal < 0){
						$calTd.addClass('td_color');
					}else{
						if($calTd.hasClass('td_color')){
							$calTd.removeClass('td_color');
						}
					}
					calVal = calVal.toFixed(pssConfig.amtDecimalDigit).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
					$calTd.html(calVal);
				}else if(val == ''){
					$td.html(val);
					$calTd.html('');
				}else{
					$td.html('');
					$calTd.html('');
					alert("输入格式有误，参考：666",1);
				}
			});
		});
	};
	
	//根据系统参数1、输入参数2、值参数3、输入保留小数位数参数4、运算符（add,sub,mul,div）参数5进行计算(默认sub)
	var _calInStockCostPlus = function(obj, chgTdName, chgTdVal, pssConfig, sysTdName, calTdName, operator){
		var sysThIndex = $("#tablist>thead th[name='" + sysTdName + "']").index();
		var calThIndex = $("#tablist>thead th[name='" + calTdName + "']").index();
		
		var $calTd = obj.children("td:eq(" + calThIndex + ")");
		var $sysTd = obj.children("td:eq(" + sysThIndex + ")");
		var sysVal = $sysTd.html().replace(/,/g,'');
		var calVal;
		if('add' == operator){
			calVal = chgTdVal + sysVal;
		}else if('mul' == operator){
			calVal = chgTdVal * sysVal;
		}else if('div' == operator){
			calVal = chgTdVal / sysVal;
		}else{
			calVal = chgTdVal - sysVal;
		}
		if(calVal < 0){
			$calTd.addClass('td_color');
		}else{
			if($calTd.hasClass('td_color')){
				$calTd.removeClass('td_color');
			}
		}
		calVal = calVal.toFixed(pssConfig.amtDecimalDigit).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
		$calTd.html(calVal);
	};
	
	//选择完商品调用带出商品默认仓库（除调拨单外）
	var _afterCheckGoods = function($tr, trIndex, goods) {
		LoadingAnimate.start();
		$.ajax({
			url:webPath+"/pssUnitGoodsRel/getAllByGoodsIdAjax",
			data:{"goodsId":goods.id},
			type:'post',
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop();
				if (data.success) {
					//商品已配置单位
					var pssUnitGoodsRelMap = data.pssUnitGoodsRelMap;
					if(pssUnitGoodsRelMap !== null && pssUnitGoodsRelMap !== undefined && Object.keys(pssUnitGoodsRelMap).length > 0){
						var unitIdThIndex = $("#tablist>thead th[name=unitId]").index();
						var unitNameThIndex = $("#tablist>thead th[name=unitName]").index();
						
						var unitIdTd = $tr.children("td:eq(" + unitIdThIndex + ")");
						var unitNameTd = $tr.children("td:eq(" + unitNameThIndex + ")");
						
						var unitInput = $("<input style='display:none;' id='unitIdInput" + trIndex + "'></input>");
						unitNameTd.children().remove();
						unitNameTd.append(unitInput);
						var unitJsonArray = new Array();
						for(var unitId in pssUnitGoodsRelMap){
							if(pssUnitGoodsRelMap[unitId].isBase == '1'){
								unitInput.val(unitId);
								unitIdTd.children("input[name=unitId]").val(unitId);
							}
							var pssUnitGoodsRelJson = {"id":unitId,"name":pssUnitGoodsRelMap[unitId].unitName};
							unitJsonArray.push(pssUnitGoodsRelJson);
						}
						unitNameTd.children("input").popupSelection({
							searchOn:false, //启用搜索
							inline:true, //下拉模式
							multiple:false, //多选
							items:unitJsonArray,
							changeCallback : function (obj, elem) {
								unitIdTd.children("input[name=unitId]").val(obj.val());
								Pss.popsSelectSelected(unitNameTd);
							}
						});
						Pss.addPopsSelectClick(unitNameTd);
					}
					
					//仓库
					var storehouseNameThIndex = $("#tablist>thead th[name=storehouseName]").index();
					var $storehouseNameTd = $tr.children("td:eq(" + storehouseNameThIndex + ")");
					var storehouseIdThIndex = $("#tablist>thead th[name=storehouseId]").index();
					var $storehouseIdTd = $tr.children("td:eq(" + storehouseIdThIndex + ")");
					$storehouseIdTd.children("input[name=storehouseId]").val(goods.storehouseId);
					var $storehouseInput = $("<input style='display:none;' id='storehouseInput" + trIndex + "'></input>");
					$storehouseNameTd.children().remove();
					$storehouseNameTd.append($storehouseInput);
					if (goods.storehouseId.length > 0) {
						$storehouseNameTd.children("input").val(goods.storehouseId);
						
						//仓位
						var storehouse = new Object();
						storehouse.id = goods.storehouseId;
						_afterSelectStorehouse($tr, trIndex, storehouse);
					}
					$storehouseNameTd.children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.storehouse,
						changeCallback : function (obj, elem) {
							$storehouseIdTd.children("input[name=storehouseId]").val(obj.val());
							//仓位
							var storehouse = new Object();
							storehouse.id = obj.val();
							_afterSelectStorehouse($tr, trIndex, storehouse);
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
				}
			},error:function(){
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	
	//仓库模块下处理table数据公共方法
	var _tableJsonDeal = function(){
		var jsonArr = new Array();
		$(".pss-bigform-table table tbody tr").each(function(trIndex, tr) {
			var jsonObj = new Object();
			$(".pss-bigform-table table thead th").each(function(thIndex, th) {
				var tdVal = $(tr).children("td:eq(" + thIndex + ")").text();
				if(/^(-)?((\d){1,3})(([,](\d){3})+)([.](\d)+)?$/.test(tdVal)){
					tdVal = tdVal.replace(/,/g,"");
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
	
	var _afterSelectStorehouse = function($tr, trIndex, storehouse){
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
	
	return {
		/*addSelectGoodsEvent:_addSelectGoodsEvent,
		addSelectStoresEvent:_addSelectStoresEvent,*/
		calChkStockResult:_calChkStockResult,
		calInStockCost:_calInStockCost,
		calInStockCostPlus:_calInStockCostPlus,
		afterCheckGoods:_afterCheckGoods,
		tableJsonDeal:_tableJsonDeal,
		afterSelectStorehouse:_afterSelectStorehouse
	};
}(window, jQuery);