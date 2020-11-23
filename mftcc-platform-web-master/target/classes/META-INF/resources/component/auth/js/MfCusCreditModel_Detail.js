var mfCusCreditModelDetail = function(window, $){
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
	
	//更新操作
	var _ajaxUpdate = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if(flag){
			var url = $(formObj).attr("action");
			$("input[name=creditFormId]").val($("select[name=creditFormName]").val());
			$("input[name=reportTemplateId]").val($("select[name=reportTemplateName]").val());
			$("input[name=protocolTemplateId]").val($("select[name=protocolTemplateName]").val());
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:webPath+ "/" + url,
				data:{ajaxData:dataForm},
				type:"post",
				dataType:"json",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg,1);
						/*top.flag="1";
						top.creditModel=data.creditModel;
						_close();*/
						window.location.href = path+"/MfCusFormConfigAction_getMfCusConfig.action";
					}else{
						alert(data.msg,0);
					}
				},
				error:function(data){
					loadingAnimate.stop();
					alert("更新操作发生异常",0);
				}
			});
		}
	};
	
	//关闭
	var _close = function(){
		//myclose_click();
		window.location.href = path+"/MfCusFormConfigAction_getMfCusConfig.action";
	};
	
	return{
		init:_init,
		back:_close,
		ajaxUpdate:_ajaxUpdate
	};
}(window,jQuery);