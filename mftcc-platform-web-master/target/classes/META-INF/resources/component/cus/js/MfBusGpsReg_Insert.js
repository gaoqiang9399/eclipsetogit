var MfBusGpsReg_Insert = function(window,$){
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
	var _saveMfBusGpsRegInfo = function (obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam,wkfAppId:wkfAppId,taskId:taskId,appId:appId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						 top.flag=true;
						 top.gpsUpdateFlag=true;//表示是否是GPS节点
						 top.gpsDetailInfo = data.gpsDetailInfo;
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
		});
	}
};
	return {
		init : _init,
		saveMfBusGpsRegInfo : _saveMfBusGpsRegInfo
	};
}(window, jQuery);