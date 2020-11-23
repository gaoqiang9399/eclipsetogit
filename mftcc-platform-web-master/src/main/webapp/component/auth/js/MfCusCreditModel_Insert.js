var mfCusCreditModelInsert = function(window, $){
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
	
	//保存
	var _ajaxInsert = function(formObj) {
		var flag = submitJsMethod($(formObj).get(0), '');
		$("input[name=creditFormId]").val($("select[name=creditFormName]").val());
		$("input[name=reportTemplateId]").val($("select[name=reportTemplateName]").val());
		$("input[name=protocolTemplateId]").val($("select[name=protocolTemplateName]").val());
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						window.location.href = path+"/MfCusFormConfigAction_getMfCusConfig.action";
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					alert("新增操作发生异常", 0);
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
		ajaxInsert:_ajaxInsert,
		back:_close
	};
}(window, jQuery); 