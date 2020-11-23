;
var MfTagTableConfig_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//保存标签信息
	var _insertTagTableConfig=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url:webPath+"/mfTagTableConfig/insertAjax",
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
					alert(top.getMessage("ERROR_REQUEST_URL", webPath+"/mfSysTemplate/insertAjax"));
				}
			}); 
		}
	};
	return{
		init:_init,
		insertTagTableConfig:_insertTagTableConfig
	};
}(window,jQuery);