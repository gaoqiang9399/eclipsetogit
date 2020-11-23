;
var MfCusRelationTypeInsert = function(window,$){
	var _openRelationCusSelect = function(obj){
		var noSelectCusId = $("[name='cusNo']").val();//关联关系不能关联主题客户自己
		//去掉当前主客户号
		selectCusDialog(getCusDialog,noSelectCusId,null,4);
	};
	return {
		openRelationCusSelect:_openRelationCusSelect
	};
}(window,jQuery);