var PssBuyOrderDetail_SmartReplenishment = function(window, $) {
	//停止checkBox事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
	};
	
	//全选事件
	var _isCheckAll = false;
	var _addCheckAllEvent = function() {
		$("#tablist").delegate("th[name=checkbox]", "click", function() {
			if (PssBuyOrderDetail_SmartReplenishment.isCheckAll) {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					this.checked = false;
				});
			 	PssBuyOrderDetail_SmartReplenishment.isCheckAll = false;
			} else {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
					this.checked = true;
				});
			 	PssBuyOrderDetail_SmartReplenishment.isCheckAll = true;
			}
		});
	};;
	
	var _myCustomScrollbarOptions = null;
	//初始化列表
	var _initTableData = function() {
		PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions = 
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssBuyOrderDetail/findSmartToBuyOrderByPageAjax",//列表数据查询的url
			tableId : "tabledl_pssbuyorder_detail02",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {"isAdviseBuyQuantityMoreThan0" : $("#adviseBuyQuantityCheckbox").is(":checked"),
					"isSubtractBuyOrderQuantity" : $("#isSubtractBuyOrderQuantity").is(":checked"),
					"isAddMinimumStockQuantity" : $("#isAddMinimumStockQuantity").is(":checked")},
			callback : function(options, data) {
				PssBuyOrderDetail_SmartReplenishment.isCheckAll = false;
				//停止checkBox事件冒泡
				PssBuyOrderDetail_SmartReplenishment.stopPropagation();
				
				if (PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions != null) {
					if (!PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isSubtractBuyOrderQuantity &&
							PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAddMinimumStockQuantity) {
						$("#content #tablist>thead th[name='buyOrderQuantity']").hide();
						$("#content #tablist>thead th[name='minimumStockQuantity']").show();
					} else if (PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isSubtractBuyOrderQuantity &&
							!PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAddMinimumStockQuantity) {
						$("#content #tablist>thead th[name='buyOrderQuantity']").show();
						$("#content #tablist>thead th[name='minimumStockQuantity']").hide();
					} else if (!PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isSubtractBuyOrderQuantity &&
							!PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAddMinimumStockQuantity) {
						$("#content #tablist>thead th[name='buyOrderQuantity']").hide();
						$("#content #tablist>thead th[name='minimumStockQuantity']").hide();
					} else {
						$("#content #tablist>thead th[name='buyOrderQuantity']").show();
						$("#content #tablist>thead th[name='minimumStockQuantity']").show();
					}
				}
			}
		});
	};
	
	var _init = function() {
		_initFormulaBoxClick();
		
		var $adviseBuyQuantityLabel = $("<label class='chk checked'></label>");
		var $adviseBuyQuantityCheckbox = $("<input type='checkbox' id='adviseBuyQuantityCheckbox' checked='checked'>");
		$adviseBuyQuantityLabel.append($adviseBuyQuantityCheckbox).append(" 只显示［建议采购量］大于0的商品");
		var $adviseBuyQuantityDiv = $("<div class='pull-right'></div>");
		$adviseBuyQuantityDiv.append($adviseBuyQuantityLabel);
		$adviseBuyQuantityCheckbox.change(function(){
			if ($adviseBuyQuantityCheckbox.is(":checked")) {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAdviseBuyQuantityMoreThan0 = true;
			} else {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAdviseBuyQuantityMoreThan0 = false;
		  	}
		});
		
		var $tipsDiv = $("<div id='tips' style='float: right; margin: -2 0 0 20'></div>");
		var $tipsImg = $("<img src='/factor/component/pss/images/pd-tips.png' style='width: 20px; height: 20px'>");
		$tipsDiv.append($tipsImg);
		$adviseBuyQuantityDiv.append($tipsDiv);
		$(".search-div .mysearch-div").append($adviseBuyQuantityDiv);
		
		var $tipsBox = $("#tipsBox");
		$tipsDiv.hover(function() {
			$tipsBox.css({'left' : $(this).offset().left - $tipsBox.width() - 16});
			$tipsBox.css({'top' : $(this).offset().top + 16});
			$tipsBox.show();
		}, function() {
			$tipsBox.hide();
		});
		
		$("#content").click(function() {
			var $formulaBox = $("#formulaBox");
			if (!$formulaBox.is(":hidden")) {
				$formulaBox.hide();
			}
		});
		
		_initTableData();
		$(".table-float-head").hide();
		
		//全选事件
		_addCheckAllEvent();
	};
	
	//初始化计算公式点击事件
	var _initFormulaBoxClick = function() {
		var $isSubtractBuyOrderQuantity = $("#isSubtractBuyOrderQuantity");
		$isSubtractBuyOrderQuantity.change(function() {
			if ($isSubtractBuyOrderQuantity.is(":checked")) {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isSubtractBuyOrderQuantity = true;
			} else {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isSubtractBuyOrderQuantity = false;
		  	}
		});
		
		var $isAddMinimumStockQuantity = $("#isAddMinimumStockQuantity");
		$isAddMinimumStockQuantity.change(function() {
			if ($isAddMinimumStockQuantity.is(":checked")) {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAddMinimumStockQuantity = true;
			} else {
				PssBuyOrderDetail_SmartReplenishment.myCustomScrollbarOptions.data.isAddMinimumStockQuantity = false;
		  	}
		});
	};
	
	//获取已选择的补货商品集合
	var _getCheckedReplenishmentGoodsArray = function() {
		var replenishmentGoodsArray = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var push = true;
			var replenishmentGoods = new Object();
			var checkbox = $(tr).find(':checkbox').get(0);
			if (!$(checkbox).is(":checked")) {
				push = false;
				return;
			}
			var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
			var goodsIdValue = $(tr).children("td:eq(" + goodsIdThIndex + ")").children("input[name=goodsId]").val();
			replenishmentGoods.goodsId = goodsIdValue;
			
			var adviseBuyQuantityThIndex = $("#tablist>thead th[name=adviseBuyQuantity]").index();
			var adviseBuyQuantityText = $(tr).children("td:eq(" + adviseBuyQuantityThIndex + ")").text();
			replenishmentGoods.adviseBuyQuantity = adviseBuyQuantityText;
			
			if (push) {
				replenishmentGoodsArray.push(replenishmentGoods);
			}
		});
		return replenishmentGoodsArray;
	};
	
	//显示隐藏计算公式
	var _showFormulaBox = function() {
		var $formulaBox = $("#formulaBox");
		if ($formulaBox.is(":hidden")) {
			$formulaBox.show();
		} else {
			$formulaBox.hide();
		}
	};
	
	//生成购货订单
	var _generateBuyOrder = function() {
		LoadingAnimate.start();
		var replenishmentGoodsArray = _getCheckedReplenishmentGoodsArray();
		if (replenishmentGoodsArray.length > 0) {
			var replenishmentGoodsDatas = "";
			for (var i in replenishmentGoodsArray)
			{
				var goodsId = replenishmentGoodsArray[i].goodsId;
				var adviseBuyQuantity = replenishmentGoodsArray[i].adviseBuyQuantity;
				replenishmentGoodsDatas = replenishmentGoodsDatas + goodsId + "," + adviseBuyQuantity + ";";
			}
			
			replenishmentGoodsDatas = replenishmentGoodsDatas.substr(0, replenishmentGoodsDatas.length - 1);
			window.parent.openBigForm(webPath+'/pssBuyOrder/getAddPage?replenishmentGoodsDatas=' + replenishmentGoodsDatas, '购货订单', function() {
				updateTableData();
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "商品"), 1);
		}
		LoadingAnimate.stop();
	};
	
	return {
		isCheckAll : _isCheckAll,
		addCheckAllEvent : _addCheckAllEvent,
		stopPropagation : _stopPropagation,
		myCustomScrollbarOptions : _myCustomScrollbarOptions,
		initTableData : _initTableData,
		init : _init,
		initFormulaBoxClick : _initFormulaBoxClick,
		getCheckedReplenishmentGoodsArray : _getCheckedReplenishmentGoodsArray,
		showFormulaBox : _showFormulaBox,
		generateBuyOrder : _generateBuyOrder
	};
}(window, jQuery);