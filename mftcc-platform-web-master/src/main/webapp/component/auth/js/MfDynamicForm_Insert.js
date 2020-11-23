var mfDynamicFormInsert = function(window,$){
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
		if(flag){
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:webPath+ "/" +url,
				data:{ajaxData:dataForm},
				type:"post",
				dataType:"json",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg,1);
						var actionUrl = path+"/MfDynamicFormAction_getListPage.action";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",actionUrl);
						myclose();
					}else{
						alert(data.msg,0);
					}
				},
				error:function(data){
					loadingAnimate.stop();
					alert("新增操作发生异常",0);
				}
			});
		}
	};
	
	//关闭
	var _close = function(){
		myclose();
	};
	
	return{
		init:_init,
		ajaxInsert:_ajaxInsert,
		close:_close	
	};
}(window,jQuery);