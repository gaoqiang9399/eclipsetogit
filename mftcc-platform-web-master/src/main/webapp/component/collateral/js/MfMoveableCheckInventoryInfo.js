	function setCheckOpNo(userInfo){
		$("input[name=checkOpName]").val(userInfo.opName);
		$("input[name=checkOpNo]").val(userInfo.opNo);
	};
;
var MfMoveableCheckInventoryInfo=function(window,$){
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
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					//top.flag=true;
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.addFlag=true;
						if (data.htmlStrFlag == "1") {
							top.htmlStrFlag = true;
							top.htmlString = data.htmlStr;
							top.tuningReport = true;
							top.refsh = true;
							top.flag = "add";
							top.showType=="1";
						}
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
	var _setCheckOpNo=function(userInfo){
		$("input[name=checkOpName]").val(userInfo.opName);
		$("input[name=checkOpNo]").val(userInfo.opNo);
	};
	return{
		init:_init,
		ajaxCheckInventorySave:_ajaxCheckInventorySave,
		setCheckOpNo:_setCheckOpNo
	};
}(window,jQuery);