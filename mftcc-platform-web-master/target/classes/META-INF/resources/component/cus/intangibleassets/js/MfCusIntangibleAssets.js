var MfCusIntangibleAssets = function(window, $) {
	//保存无形资产信息
    var _saveCusIntangibleAssets = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };

	return {
        saveCusIntangibleAssets:_saveCusIntangibleAssets
	};

}(window, jQuery);