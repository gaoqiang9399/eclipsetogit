;
var MfAgenciesCreditAmtModifyHis_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _insertAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				success:function(data){
					if(data.flag == "success"){
						  top.addAmtFlag = true;
						  myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				}
			});
		}
	};
	return{
		init:_init,
		insertAjax:_insertAjax,
	}
}(window,jQuery)