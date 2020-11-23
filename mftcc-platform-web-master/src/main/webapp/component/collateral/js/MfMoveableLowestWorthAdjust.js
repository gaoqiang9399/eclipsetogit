;
var MfMoveableLowestWorthAdjust=function(window,$){
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
	};
	//保存方法
	var _ajaxLowestWorthAdjustSave = function(formObj){
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
	return{
		init:_init,
		ajaxLowestWorthAdjustSave:_ajaxLowestWorthAdjustSave,
	};
}(window,jQuery);