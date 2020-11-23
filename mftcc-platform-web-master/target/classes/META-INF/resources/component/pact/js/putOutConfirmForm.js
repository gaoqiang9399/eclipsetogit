;
var fincappInfo = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
	};
	
	var _insertZHfincapp = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var newAccountNo = $("input[name='incomAccount']").val().trim().replace(/\s/g,"");
			$("input[name='incomAccount']").val(newAccountNo);
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start(); 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam,wkfAppId:wkfAppId,taskId:taskId,appId:appId,isNewBank:isNewBank},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
					    window.top.alert(data.msg,3);
						top.flag = true;
						var url = webPath+'/mfBusPact/getById?pactId='+data.pactId;
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}else{
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	return {
		init : _init,
		insertZHfincapp : _insertZHfincapp,
	};
}(window, jQuery);
