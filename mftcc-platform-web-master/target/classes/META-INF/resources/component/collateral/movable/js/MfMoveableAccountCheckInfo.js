;
var MfMoveableAccountCheckInfo=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_checkResultTypeChange();
	};
	//保存方法
	var _ajaxAccountCheckSave = function(formObj){
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
					top.flag=true;
					LoadingAnimate.stop();
					if (data.flag == "success") {
						_closeDialog("save");
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
	var _checkResultTypeChange=function(){
		var checkResultType =$("select[name=checkResultType]").val();
		if(checkResultType=="1"){
			$("textarea[name=unusualRemark]").parents("tr").eq(0).hide();
		}
		if(checkResultType=="2"){
			$("textarea[name=unusualRemark]").parents("tr").eq(0).show();
		}
	};
	//关闭弹窗
	var _closeDialog=function(parm){
		if(parm == "save"){
			var obj=new Object();
			parent.dialog.get("showDialog").close(obj).remove();
		}else{
			parent.dialog.get("showDialog").close("").remove();
		}
	};
	return{
		init:_init,
		ajaxAccountCheckSave:_ajaxAccountCheckSave,
		closeDialog:_closeDialog,
		checkResultTypeChange:_checkResultTypeChange,
	};
}(window,jQuery);