;
var PssAlloTransBill = function(window, $){
	
	//选择完商品调用
	var _afterSelectGoods = function ($tr, trIndex, goods) {
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
					var storehouseNameThIndex = $("#tablist>thead th[name=outStorehouseName]").index();
					var $storehouseNameTd = $tr.children("td:eq(" + storehouseNameThIndex + ")");
					var storehouseIdThIndex = $("#tablist>thead th[name=outStorehouseId]").index();
					var $storehouseIdTd = $tr.children("td:eq(" + storehouseIdThIndex + ")");
					$storehouseIdTd.children("input[name=outStorehouseId]").val(goods.storehouseId);
					var $storehouseInput = $("<input style='display:none;' id='storehouseInput" + trIndex + "'></input>");
					$storehouseNameTd.children().remove();
					$storehouseNameTd.append($storehouseInput);
					if (goods.storehouseId.length > 0) {
						$storehouseNameTd.children("input").val(goods.storehouseId);
						
						//仓位
						var storehouse = new Object();
						storehouse.id = goods.storehouseId;
						_afterSelectOutStorehouse($tr, trIndex, storehouse);
					}
					$storehouseNameTd.children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.storehouse,
						changeCallback : function (obj, elem) {
							$storehouseIdTd.children("input[name=outStorehouseId]").val(obj.val());
							//仓位
							var storehouse = new Object();
							storehouse.id = obj.val();
							_afterSelectOutStorehouse($tr, trIndex, storehouse);
							Pss.popsSelectSelected($storehouseNameTd);
						}
					});
					Pss.addPopsSelectClick($storehouseNameTd);
					
					//仓位
					var freightSpaceNoThIndex = $("#tablist>thead th[name=outFreightSpaceNo]").index();
					var $freightSpaceNoTd = $tr.children("td:eq(" + freightSpaceNoThIndex + ")");
					var freightSpaceIdThIndex = $("#tablist>thead th[name=outFreightSpaceId]").index();
					var $freightSpaceIdTd = $tr.children("td:eq(" + freightSpaceIdThIndex + ")");
					var $freightSpaceInput = $("<input style='display:none;' id='outFreightSpaceInput" + trIndex + "'></input>");
					$freightSpaceNoTd.children().remove();
					$freightSpaceNoTd.append($freightSpaceInput);
					$freightSpaceIdTd.children("input[name=outFreightSpaceId]").val("");
					
				}
			},error:function(){
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
		
	};
	
	var _afterSelectOutStorehouse = function($tr, trIndex, storehouse){
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
					
					var freightSpaceNoThIndex = $("#tablist>thead th[name=outFreightSpaceNo]").index();
					var $freightSpaceNoTd = $tr.children("td:eq(" + freightSpaceNoThIndex + ")");
					var freightSpaceIdThIndex = $("#tablist>thead th[name=outFreightSpaceId]").index();
					var $freightSpaceIdTd = $tr.children("td:eq(" + freightSpaceIdThIndex + ")");
					var $freightSpaceInput = $("<input style='display:none;' id='outFreightSpaceInput" + trIndex + "'></input>");
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
								$freightSpaceIdTd.children("input[name=outFreightSpaceId]").val(obj.val());
								
								Pss.popsSelectSelected($freightSpaceNoTd);
							}
						});
						Pss.addPopsSelectClick($freightSpaceNoTd);
					} else {
						$freightSpaceIdTd.children("input[name=outFreightSpaceId]").val("");
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
	
	var _afterSelectInStorehouse = function($tr, trIndex, storehouse){
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
					
					var freightSpaceNoThIndex = $("#tablist>thead th[name=inFreightSpaceNo]").index();
					var $freightSpaceNoTd = $tr.children("td:eq(" + freightSpaceNoThIndex + ")");
					var freightSpaceIdThIndex = $("#tablist>thead th[name=inFreightSpaceId]").index();
					var $freightSpaceIdTd = $tr.children("td:eq(" + freightSpaceIdThIndex + ")");
					var $freightSpaceInput = $("<input style='display:none;' id='inFreightSpaceInput" + trIndex + "'></input>");
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
								$freightSpaceIdTd.children("input[name=inFreightSpaceId]").val(obj.val());
								
								Pss.popsSelectSelected($freightSpaceNoTd);
							}
						});
						Pss.addPopsSelectClick($freightSpaceNoTd);
					} else {
						$freightSpaceIdTd.children("input[name=inFreightSpaceId]").val("");
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
	
	return{
		afterSelectGoods:_afterSelectGoods,
		afterSelectOutStorehouse:_afterSelectOutStorehouse,
		afterSelectInStorehouse:_afterSelectInStorehouse,
	};
}(window, jQuery);