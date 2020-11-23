var MfCusAccountDetail = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
        $("input[name=id]").popupSelection({//应收账款人名称选择
            searchOn : true, // false-不启用搜索，true-启用搜索
            inline : true, // true-内联,false-弹出
            ajaxUrl : webPath+"/mfCusAccountDebtor/getAll?cusNo="+cusNo,
            multiple : false, // false-单选,true-复选
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name=debtorName]"));
                $("input[name=debtorName]").val($("input[name=id]").parents("td").find(".pops-value").html());
            },
        });
	};
	var _insertAjax = function (obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	return {
		init : _init,
		insertAjax : _insertAjax
	};
}(window, jQuery);