;
var survey = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _insertAppAndCus = function(obj){
		var url = $(obj).attr("action");
		var flag= submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
					$.ajax({
						url:url,
						data:{ajaxData:dataParam,taskId:taskId,appId:appId,wkfAppId:wkfAppId},
						type:'post',
						dataType:'json',
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag=="success"){
								var url = webPath+'/mfBusApply/getSummary?appId='+data.appId;
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								myclose();
							}else{
								alert(data.msg,0);
							}
						},error:function(){
							LoadingAnimate.stop();
							alert(top.getMessage("FAILED_SAVE"),0);
						}
					});
			}
	};
	return {
		init : _init,
		insertAppAndCus :_insertAppAndCus,
	};
}(window, jQuery);
