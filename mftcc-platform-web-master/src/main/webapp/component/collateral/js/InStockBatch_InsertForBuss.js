;
var InStockBatch_InsertForBuss = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		var groupNameLabel = $("input[name=groupName]").parents(".rows")
				.find(".form-label");
		var groupNameLabelText = $(groupNameLabel).text();
		$(groupNameLabel).empty().append(
				"<span class='required'>*</span>" + groupNameLabelText);
		$("input[name=groupName]").attr("mustinput", "1");
	};
	var _getLegalIdType=function(){
		var legalIdType = $("select[name =legalIdType]").val();
		if (legalIdType == "0") {
			$("input[name=legalIdNum]").attr("alt", "idnum");
		} else {
			$("input[name=legalIdNum]").attr("alt", "tmp");
		}
		$("input[name=legalIdNum]").val("");
	};
	//押品入库保存
	var _saveInsertForBuss=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						myclose_click();
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	return{
		init:_init,
		saveInsertForBuss:_saveInsertForBuss
	};
}(window, jQuery);