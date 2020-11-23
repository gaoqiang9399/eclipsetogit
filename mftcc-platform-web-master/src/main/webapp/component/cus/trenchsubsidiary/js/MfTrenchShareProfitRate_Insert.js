;
var MfTrenchShareProfitRate_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	var _insertAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	return{
		init:_init,
		insertAjax:_insertAjax
	}
}(window,jQuery)