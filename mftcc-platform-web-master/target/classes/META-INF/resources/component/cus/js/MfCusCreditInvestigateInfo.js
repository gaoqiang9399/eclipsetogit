
var MfCusCreditInvestigateInfo=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	//新增保存
	var  _insertCusCreditInvestigateInfo = function(obj) {
		ajaxInsertCusForm(obj);
	};
	
	return{
		init:_init,
		insertCusProfitLossAnalyseBase:_insertCusCreditInvestigateInfo
	};
}(window, jQuery);
