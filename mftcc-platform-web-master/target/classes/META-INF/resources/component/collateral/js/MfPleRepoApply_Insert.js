var mfPleRepoApplyInsert = function(window,$){
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			$.ajax({
				url : url,
				data : {ajaxData : dataForm},
				success : function(data) {
					if (data.flag == "success") {
						top.buyBackFlag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	
	//关闭
	var _close = function(){
		//myclose_click();
		myclose();
	};
	
	return{
		init:_init,
		close:_close,
		ajaxInsert:_ajaxInsert
	};
}(window,jQuery);