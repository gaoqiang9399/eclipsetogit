;
var MfOaTransGetViewPoint = function(window, $) {
	var transId = '';
	
	var _init = function (id) {
		transId = id;
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
		_bindInsertAjax("#OaTransInsert");
		_bindClose();
	};
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose_task();
		});
	}; 
	var _bindInsertAjax = function (obj) {		  
		$(".insertAjax").bind("click", function(event){
			var opinionType = $("select[name=opinionType]").val();
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				commitProcess(webPath+"/mfOaTrans/updateAjax?opinionType"+opinionType+"&id="+transId,obj,'applySP');
			}				
		});
	}; 
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);