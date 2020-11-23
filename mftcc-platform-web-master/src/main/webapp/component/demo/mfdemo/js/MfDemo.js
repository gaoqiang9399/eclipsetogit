;
var MfDemo = function(window, $) {
	//初始化
	var _init = function(){
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
	//demo新增
	var _ajaxInsert = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						alert(data.msg,1);
						myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	};
	//demo编辑
	var _ajaxUpdate = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						alert(data.msg,1);
						myclose_click();
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	};
	
	return {
		init : _init,
		ajaxInsert:_ajaxInsert,
		ajaxUpdate:_ajaxUpdate
	};
}(window, jQuery);