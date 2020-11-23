;
var MfAppDevice_Insert = function(window,$){
	var _myclose = function(){
		location.href=webPath+"/mfAppDevice/getListPage";
	};
	var _ajaxSave = function(obj){
		//obj是form对象
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			LoadingAnimate.start();
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
//						  alert("操作成功！",1);
						  _myclose();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},
				error:function(data){
					LoadingAnimate.stop();
				}
			});
		}
	};
	return {
		ajaxSave:_ajaxSave,
		myclose:_myclose
	}
}(window,jQuery);