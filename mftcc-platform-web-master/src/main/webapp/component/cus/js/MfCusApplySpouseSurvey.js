var MfCusApplySpouseSurvey=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	//新增保存
	var  _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0),'');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	
	return{
		init:_init,
		ajaxSave:_ajaxSave
	};
}(window, jQuery);
