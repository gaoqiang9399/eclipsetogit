;
var MfTagColumnConfig_Detail=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//保存标签信息
	var _saveEditTagColumnConfig=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url:webPath+"/mfTagColumnConfig/updateAjax",
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						myclose_click();
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", "${webPath}/mfSysTemplate/insertAjax"));
				}
			}); 
		}
	};
	return{
		init:_init,
		saveEditTagColumnConfig:_saveEditTagColumnConfig
	};
}(window,jQuery);