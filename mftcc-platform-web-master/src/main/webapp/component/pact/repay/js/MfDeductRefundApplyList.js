
var MfDeductRefundApplyList = function(window,$){
	//提前还款申请新增
	var _applyInsert = function(){
		top.window.openBigForm(webPath+"/mfDeductRefundApply/input","减免/退款申请",function(){
			updateTableData(true);
		});
	};
	
	
	var _getDetialPage = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		top.window.openBigForm(url,"减免/退费申请详情",function(){
			updateTableData(true);
		});		
	};
	return{ 
		applyInsert:_applyInsert,
		getDetailPage:_getDetialPage,
	};
	 
}(window,jQuery);