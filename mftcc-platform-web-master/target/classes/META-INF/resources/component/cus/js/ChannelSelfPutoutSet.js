;
var ChannelSelfPutoutSet=function(window,$){
	//初始化
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
	};
	
	//保存方法
	var _insertAjax = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {ajaxData:dataParam},
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						top.flag = true;
						myclose_click();
					}else{
						window.top.alert(data.msg, 0);
					}
				},error : function() {
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			});
		}
	};
	return{
		init:_init,
		insertAjax:_insertAjax,
	}
}(window,jQuery);