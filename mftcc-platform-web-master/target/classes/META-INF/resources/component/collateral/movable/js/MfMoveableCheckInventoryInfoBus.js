	function setCheckOpNo(userInfo){
		$("input[name=checkOpName]").val(userInfo.opName);
		$("input[name=checkOpNo]").val(userInfo.opNo);
	};
;
var MfMoveableCheckInventoryInfoBus=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//保存方法
	var _ajaxCheckInventorySave = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.tuningReport = true;
						top.appSts = data.appSts;
						top.refsh = true;
						top.flag=true;
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
		}
	};
	//跳过
	var _submitBussProcessAjax = function(){
		LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfMoveableCheckInventoryInfo/submitBussProcessAjax",
			data : {
				appId:appId
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					top.flag=true;
					top.tuningReport = true;
					top.appSts = data.appSts;
					top.refsh = true;
					top.flag=true;
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
	var _setCheckOpNo=function(userInfo){
		$("input[name=checkOpName]").val(userInfo.opName);
		$("input[name=checkOpNo]").val(userInfo.opNo);
	};
	return{
		init:_init,
		ajaxCheckInventorySave:_ajaxCheckInventorySave,
		setCheckOpNo:_setCheckOpNo,
		submitBussProcessAjax:_submitBussProcessAjax
	};
}(window,jQuery);