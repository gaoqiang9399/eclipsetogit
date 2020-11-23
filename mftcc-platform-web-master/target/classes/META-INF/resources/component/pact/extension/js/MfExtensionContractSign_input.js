;
var MfExtensionContractSign_input=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//发起展期申请
	var _extensionContractSignAjax=function(formObj,skipFlag){
		if(skipFlag=="1"){
			_insertContractSignAjax(formObj,skipFlag);
		}else{
			var flag = submitJsMethod($(formObj).get(0), '');
			if (flag) {
				_insertContractSignAjax(formObj,skipFlag);
			}
		}
	};
	var _insertContractSignAjax=function(formObj,skipFlag){
		var url = $(formObj).attr("action");
		var dataForm = JSON.stringify($(formObj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url : url,
			data : {
				ajaxData : dataForm,
				skipFlag:skipFlag,
				"nodeNo":nodeNo
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					top.extenFlag=true;
					myclose_click();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				top.loadingAnimate.stop();
				window.top.alert(data.msg, 0);
			}
		});
	};
	return{
		init:_init,
		extensionContractSignAjax:_extensionContractSignAjax,
	};
}(window, jQuery);