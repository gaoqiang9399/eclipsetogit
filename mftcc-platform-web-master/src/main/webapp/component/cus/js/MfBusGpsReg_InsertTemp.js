var MfBusGpsReg_InsertTemp = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//GPS信息登记
	var _saveGpsRegInfo = function (obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		LoadingAnimate.start();
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					top.flag = true;
					top.gpsListInfo = data.gpsListInfo;
					myclose_click();
				}
				if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
};
	return {
		init : _init,
		saveGpsRegInfo : _saveGpsRegInfo
	};
}(window, jQuery);