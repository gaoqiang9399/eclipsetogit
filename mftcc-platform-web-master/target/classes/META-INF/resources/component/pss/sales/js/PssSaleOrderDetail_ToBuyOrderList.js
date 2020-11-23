var PssSaleOrderDetail_ToBuyOrderList = function(window, $) {
	//全选事件
	var _isCheckAll = false;
	var _addCheckAllEvent = function() {
		$("#tablist").delegate("th[name=checkbox]", "click", function() {
			if (PssSaleOrderDetail_ToBuyOrderList.isCheckAll) {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
			 		$(this).prop('checked', false).change();
				});
			 	PssSaleOrderDetail_ToBuyOrderList.isCheckAll = false;
			} else {
			 	$("#tablist>tbody tr").find(':checkbox').each(function() {
			 		$(this).prop('checked', true).change();
				});
			 	PssSaleOrderDetail_ToBuyOrderList.isCheckAll = true;
			}
		});
	};;
	
	//行单击事件
	var _checkCurrentTr = function(obj) {
		var trCheckbox = $(obj).find(':checkbox').get(0);
		if (trCheckbox.checked) {
			$(trCheckbox).prop('checked', false).change();
		} else {
			$(trCheckbox).prop('checked', true).change();
		}
	};
	
	//checkBox的change事件
	var _initCheckBoxChange = function() {
		$("#tablist>tbody tr").find(':checkbox').each(function() {
			var $checkBox = $(this);
			$checkBox.change(function () {
                var thisBuyQuantityThIndex;
				if ($checkBox.is(":checked")) {
					var willBuyQuantityThIndex = $("#tablist>thead th[name=willBuyQuantity]").index();
					var willBuyQuantityText = $(this).parents("tr").children("td:eq(" + willBuyQuantityThIndex + ")").text();
					thisBuyQuantityThIndex = $("#tablist>thead th[name=thisBuyQuantity]").index();
					if ($checkBox.parents("tr").children("td:eq(" + thisBuyQuantityThIndex + ")").text().length == 0) {
						$checkBox.parents("tr").children("td:eq(" + thisBuyQuantityThIndex + ")").text(willBuyQuantityText);
					}
				} else {
					thisBuyQuantityThIndex = $("#tablist>thead th[name=thisBuyQuantity]").index();
					$checkBox.parents("tr").children("td:eq(" + thisBuyQuantityThIndex + ")").text("");
				}
			});
		});
	};
	
	//停止checkBox和本次采购数量/价格点击事件冒泡
	var _stopPropagation = function() {
		$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
			event.stopPropagation();
		});
		
		var thisBuyQuantityThIndex = $("#tablist>thead th[name=thisBuyQuantity]").index();
		var thisBuyUnitPriceThIndex = $("#tablist>thead th[name=thisBuyUnitPrice]").index();
		var searchFlagThIndex = $("#tablist>thead th[name=searchFlag]").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + thisBuyQuantityThIndex + ")").bind('click', function(event) {
				event.stopPropagation();
			});
			$(tr).children("td:eq(" + thisBuyUnitPriceThIndex + ")").bind('click', function(event) {
				event.stopPropagation();
			});
			$(tr).children("td:eq(" + searchFlagThIndex + ")").bind('click', function(event) {
				event.stopPropagation();
			});
		});
	};
	
	var _myCustomScrollbarOptions = null;
	//初始化列表
	var _initTableData = function() {
		PssSaleOrderDetail_ToBuyOrderList.myCustomScrollbarOptions = 
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSaleOrderDetail/findToBuyOrderByPageAjax",//列表数据查询的url
			tableId : "tabledl_psssaleorder_detail02",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			data : {"isCompletedBuyNotShow" : $("#completedBuyNotShowCheckbox").is(":checked")},
			callback : function(options, data) {
				//数量列事件
	    		var quantityThs = new Array("thisBuyQuantity");
	    		Pss.addQuantityEvent(quantityThs, ajaxData.pssConfig);
	    		//金额列事件
	    		var moneyThs = new Array("thisBuyUnitPrice");
	    		Pss.addMoneyEvent(moneyThs, ajaxData.pssConfig);
	    		
	    		var tabHeadThs = new Array("thisBuyQuantity");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				Pss.dealSearchFlagHead("searchFlag", "goodsId", "#content", true);
	    		
				PssSaleOrderDetail_ToBuyOrderList.isCheckAll = false;
				//停止checkBox和本次采购数量/价格点击事件冒泡
				_stopPropagation();
				_initCheckBoxChange();
			}
		});
	};
	
	function _dealTableTh() {
		var floatTr = $("#tablist>thead").children("tr");
		var head = $("<tr>"
				+ "<th scope='col' align='center' colspan='7'>销货订单信息</th>"
				+ "<th scope='col' align='center' colspan='8'>实际出入库数量</th>"
				+ "<th scope='col' align='center' colspan='2'>本次采购信息</th>"
				+ "</tr>");
		floatTr.before(head);
	}
	
	var _init = function() {
		var $completedBuyNotShowLabel = $("<label class='chk checked'></label>");
		var $completedBuyNotShowCheckbox = $("<input type='checkbox' id='completedBuyNotShowCheckbox' checked='checked'>");
		$completedBuyNotShowLabel.append("销售订单范围：").append($completedBuyNotShowCheckbox).append(" 已采购完订单不显示");
		var $completedBuyNotShowDiv = $("<div class='pull-right'></div>");
		$completedBuyNotShowDiv.append($completedBuyNotShowLabel);
		$completedBuyNotShowCheckbox.change(function(){
			if ($completedBuyNotShowCheckbox.is(":checked")) {
				PssSaleOrderDetail_ToBuyOrderList.myCustomScrollbarOptions.data.isCompletedBuyNotShow = true;
			} else {
				PssSaleOrderDetail_ToBuyOrderList.myCustomScrollbarOptions.data.isCompletedBuyNotShow = false;
		  	}
		});
		$(".search-div .mysearch-div").append($completedBuyNotShowDiv);
		
		_initTableData();
		$(".table-float-head").hide();
		_dealTableTh();
		
		//全选事件
		_addCheckAllEvent();
	};
	
	//查看销货订单
	var _getSaleOrder = function(url){
		url = encodeURI(url);
		window.parent.openBigForm(url, '销货订单', function() {
			updateTableData();
		});
	};
	
	//查看购货订单
	var _getBuyOrder = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '购货订单', function() {
			updateTableData();
		});
	};
	
	//查看销货单
	var _getSaleBill = function(url) {
		url = encodeURI(url);
		window.parent.openBigForm(url, '销货单', function() {
			updateTableData();
		});
	};
	
	//获取已选择的销货订单明细集合
	var _getCheckedSaleOrderDetails = function() {
		var pssSaleOrderDetails = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var push = true;
			var pssSaleOrderDetail = new Object();
			var checkbox = $(tr).find(':checkbox').get(0);
			if (!$(checkbox).is(":checked")) {
				push = false;
				return;
			}
			var saleOrderIdThIndex = $("#tablist>thead th[name=saleOrderId]").index();
			var saleOrderIdValue = $(tr).children("td:eq(" + saleOrderIdThIndex + ")").children("input[name=saleOrderId]").val();
			pssSaleOrderDetail.saleOrderId = saleOrderIdValue;
			
			var goodsIdThIndex = $("#tablist>thead th[name=goodsId]").index();
			var goodsIdValue = $(tr).children("td:eq(" + goodsIdThIndex + ")").children("input[name=goodsId]").val();
			pssSaleOrderDetail.goodsId = goodsIdValue;
			
			var willBuyQuantityThIndex = $("#tablist>thead th[name=willBuyQuantity]").index();
			var willBuyQuantityText = $(tr).children("td:eq(" + willBuyQuantityThIndex + ")").text();
			pssSaleOrderDetail.willBuyQuantity = willBuyQuantityText;
			
			var thisBuyQuantityThIndex = $("#tablist>thead th[name=thisBuyQuantity]").index();
			var thisBuyQuantityText = $(tr).children("td:eq(" + thisBuyQuantityThIndex + ")").text();
			pssSaleOrderDetail.thisBuyQuantity = thisBuyQuantityText;
			
			var thisBuyUnitPriceThIndex = $("#tablist>thead th[name=thisBuyUnitPrice]").index();
			var thisBuyUnitPriceText = $(tr).children("td:eq(" + thisBuyUnitPriceThIndex + ")").text();
			thisBuyUnitPriceText = Pss.ifMoneyToNumber(thisBuyUnitPriceText);
			pssSaleOrderDetail.thisBuyUnitPrice = thisBuyUnitPriceText;
			if (push) {
				pssSaleOrderDetails.push(pssSaleOrderDetail);
			}
		});
		return pssSaleOrderDetails;
	};
	
	//生成购货订单
	var _generateBuyOrder = function(object, url) {
		LoadingAnimate.start();
		var pssSaleOrderDetails = new Array();
		if (url !== "" && url !== undefined && url !== null) {
			var pssSaleOrderDetail = new Object();
			var parameters = url.split("?")[1].split("&");
			pssSaleOrderDetail.saleOrderId = parameters[0].split("=")[1];
			pssSaleOrderDetail.goodsId = parameters[1].split("=")[1];
			pssSaleOrderDetail.willBuyQuantity = parameters[2].split("=")[1];
			
			var willBuyQuantityThIndex = $("#tablist>thead th[name=willBuyQuantity]").index();
			var willBuyQuantityText = $(object).children("td:eq(" + willBuyQuantityThIndex + ")").text();
			if (willBuyQuantityText.length == 0) {
				willBuyQuantityText = 0;
			}
			pssSaleOrderDetail.thisBuyQuantity = willBuyQuantityText;
			
			var thisBuyUnitPriceThIndex = $("#tablist>thead th[name=thisBuyUnitPrice]").index();
			var thisBuyUnitPriceText = $(object).children("td:eq(" + thisBuyUnitPriceThIndex + ")").text();
			thisBuyUnitPriceText = Pss.ifMoneyToNumber(thisBuyUnitPriceText);
			if (thisBuyUnitPriceText.length == 0) {
				thisBuyUnitPriceText = 0;
			}
			pssSaleOrderDetail.thisBuyUnitPrice = thisBuyUnitPriceText;
			
			pssSaleOrderDetails.push(pssSaleOrderDetail);
		} else {
			pssSaleOrderDetails = _getCheckedSaleOrderDetails();
		}
		if (pssSaleOrderDetails.length > 0) {
			var saleOrderDetailDatas = "";
			for (var i in pssSaleOrderDetails)
			{
				var saleOrderId = pssSaleOrderDetails[i].saleOrderId;
				var goodsId = pssSaleOrderDetails[i].goodsId;
				var willBuyQuantity = pssSaleOrderDetails[i].willBuyQuantity;
				var thisBuyQuantity = pssSaleOrderDetails[i].thisBuyQuantity;
				if (thisBuyQuantity.length == 0) {
					LoadingAnimate.stop();
					DIALOG.tip("请录入本次采购数量!", 3000);
					return false;
				}
				var thisBuyUnitPrice = pssSaleOrderDetails[i].thisBuyUnitPrice;
				if (thisBuyUnitPrice == null || thisBuyUnitPrice == undefined || thisBuyUnitPrice == "") {
					thisBuyUnitPrice = 0;
				}
				if (parseFloat(willBuyQuantity) > 0.0) {
					saleOrderDetailDatas = saleOrderDetailDatas + saleOrderId + "," + goodsId + "," + thisBuyQuantity + "," + thisBuyUnitPrice + ";";
				} else {
					LoadingAnimate.stop();
					DIALOG.tip("待采购数量需大于0!", 3000);
					return false;
				}
			}
			
			saleOrderDetailDatas = saleOrderDetailDatas.substr(0, saleOrderDetailDatas.length - 1);
			window.parent.openBigForm(webPath+'/pssBuyOrder/getAddPage?saleOrderDetailDatas=' + saleOrderDetailDatas, '购货订单', function() {
				updateTableData();
			});
		} else {
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "单据"), 1);
		}
		LoadingAnimate.stop();
	};
	
	return {
		isCheckAll : _isCheckAll,
		addCheckAllEvent : _addCheckAllEvent,
		checkCurrentTr : _checkCurrentTr,
		initCheckBoxChange : _initCheckBoxChange,
		stopPropagation : _stopPropagation,
		myCustomScrollbarOptions : _myCustomScrollbarOptions,
		initTableData : _initTableData,
		init : _init,
		dealTableTh : _dealTableTh,
		getSaleOrder : _getSaleOrder,
		getBuyOrder : _getBuyOrder,
		getSaleBill : _getSaleBill,
		getCheckedSaleOrderDetails : _getCheckedSaleOrderDetails,
		generateBuyOrder : _generateBuyOrder
	};
}(window, jQuery);