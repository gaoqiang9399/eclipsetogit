;
var MfSalesOptions_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
		// 品牌选择组件
		$("input[name=brandId]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选选
			items : mfCarBrandMap,
			changeCallback : function(obj, elem) {
				var brandId = $(obj).val();
				$("[name=brandName]").val( $("[name=brandId]").prev().text());
				$.ajax({
						url : webPath + "/mfSalesOptions/getSeriesByBrandIdAjax",
						data : { brandId : brandId },
						type : 'post',
						dataType : 'json',
						success : function(data) {
								if (data.flag == "success") {
									$("[name=popsseriesId]").popupSelection("updateItems",data.mfCarSeriesMap);
									$("[name=popsmodelId]").popupSelection("updateItems",data.mfCarModelMap);
								}
							},
							error : function() {
							}
						});
					}
				});
		// 初始化车系
		$("input[name=seriesId]") .popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选选
			items : mfCarSeriesMap,
			changeCallback : function( obj, elem) {
				var seriesId = $(obj).val();
				$("[name=seriesName]").val($("[name=seriesId]").prev().text());
				$.ajax({
						url : webPath + "/mfSalesOptions/getModelBySeriesIdAjax",
						data : { seriesId : seriesId },
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if (data.flag == "success") {
								$("[name=popsmodelId]").popupSelection("updateItems",data.mfCarModelMap);
							}
						},
						error : function() {
						}
				});
		}
	});
		// 初始化车型
		$("input[name=modelId]").popupSelection( {
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选选
			items : mfCarModelMap,
			changeCallback : function( obj, elem) {
				$("[name=modelName]").val($("[name=modelId]").prev().text());
				var modelId = $("[name=modelId]").val();
                $.ajax({
                    url : webPath + "/mfCarModel/getCarModelDetailById",
                    data : { modelId : modelId },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        if (data.flag == "success") {
                        	var mfCarModelDetail = data.mfCarModelDetail;
                            $("input[name=colorsDic]").val(mfCarModelDetail.colorsDic);
                            $("input[name='selectColors']").popupSelection({
                                searchOn:false,//不启用搜索
                                inline:true,//下拉模式
                                multiple:true,//多选
                                items:data.items
                            });
                        }
                    },
                    error : function() {
                    }
                });
			}
		});
		// 销售地区选择组件
		$("input[name=salesArea]").popupSelection({
			ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
			searchOn : true,// 启用搜索
			multiple : true,// 单选
			ztree : true,
			ztreeSetting : setting,
			title : "销售地区",
			handle : BASE.getIconInTd($("input[name=salesArea]")),
			changeCallback : function(elem) {
				BASE.removePlaceholder($("input[name=salesArea]"));
				var businessAreaNo = elem.data("values").val();
				var nodes = elem.data("treeNode");
				var salesAreaName = "";
				var len = elem.data("treeNode").length;
				for (var i = 0; i < len; i++) {
					salesAreaName += nodes[i].name + "|";
				}
				$("input[name=salesAreaName]").val(salesAreaName);
			}
		});
		$("input[name='applicableProducts']").popupSelection({
			searchOn:false,//不启用搜索
			inline:true,//下拉模式
			multiple:true,//多选
			items:kindNo
		});
	};
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if(!_ifRepeat()){
			return;
		}
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	var _sumAmt = function() {

	    const sumList = [
	        $("[name='carSalesPrice']").val(),
            $("[name='purchaseTax']").val(),
            $("[name='insuranceFee']").val(),
            $("[name='registerFee']").val(),
            //$("[name='gpsFee']").val(),
            $("[name='parkingFee']").val(),
            $("[name='fuelCosts']").val(),
            $("[name='washFee']").val(),
            //$("[name='margin']").val(),
            $("[name='licenseFee']").val(),
            $("[name='ownGpsFee']").val()
        ];
        const sumAmt = CalcUtil.sum(sumList);
		$("[name='fullPackagePrice']").val(fmoney(sumAmt, 2));
		// 上牌需汇
		const remittanceList = [
			$("[name='purchaseTax']").val(),
			$("[name='insuranceFee']").val(),
			$("[name='registerFee']").val(),
			$("[name='gpsFee']").val()
			];
		const remittance = CalcUtil.sum(remittanceList);
		$("[name='remittance']").val(fmoney(remittance, 2));

	}
	var _ifRepeat = function(){
		var flag = true;
		var modelId = $("[name='modelId']").val();
		var salesArea =  $("[name='salesArea']").val();
		var selectColors =  $("[name='selectColors']").val();
		var url = webPath + "/mfSalesOptions/ifRepeat";
		$.ajax({
			url : url,
			data : {
				modelId : modelId,
				salesArea:salesArea,
                selectColors:selectColors
			},
			async:false, 
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					if(data.result == "0"){
						alert("该车型已有销售地区", 0);
						flag = false;
					}
				} else {
					flag = false;
					alert(data.msg, 0);
				}
			},
			error : function() {
				flag = false;
			}
		});
		return flag;
		
	}
	
	return {
		init : _init,
		ajaxSave : _ajaxSave,
		sumAmt : _sumAmt,
		ifRepeat:_ifRepeat
	};
}(window, jQuery);
