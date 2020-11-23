;
var pssBuySaleExpBillInput = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/pssBuySaleExpBill/findByPageForPopAjax",//列表数据查询的url
			tableId:"tablepssbuysaleexpbill0002",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			data:{"jsonArr":jsonArr,"sourceBillNo":sourceBillNo},
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content");
				pssFund.addSupplierNameEvent("supplierName", "supplierId", "#content");
				Pss.addMoneyEvent(["expAmt"], ajaxData.pssConfig);
				var stringThs = new Array("memo");
				pssFund.addStringEvent(stringThs, "#content");
				pssFund.addSaleTypeEvent("saleType", "#content");
				
				var tabHeadThs = new Array("saleType", "expAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	var _confirmPssBuySaleExp = function(){
		top.flag = true;
		var jsonArrBD = new Array();
		jsonArrBD = pssFund.tableJsonDeal("#content");
//		parent.pssBuySaleExpBillList.pssBuySaleExpArr = jsonArrBD;
//		alert(JSON.stringify(pssBuySaleExpBillList.pssBuySaleExpArr));
		top.pssBuySaleExpArr = jsonArrBD;
		myclose_showDialogClick();
	};
	
	return {
		init : _init,
		confirmPssBuySaleExp : _confirmPssBuySaleExp
	};
}(window, jQuery);