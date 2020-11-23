;
var CollateralUpdate = function(window, $) {
	
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _closeMyOnly = function (){
		var length = $(top.window.document).find(".dhccModalDialog").length;
		if(length>2){//说明有多个
			var url = $(top.window.document).find('.dhccModalDialog').eq(1).find('iframe').attr('src');
			$(top.window.document).find(".dhccModalDialog").eq(1).find("iframe").attr('src',url);
			$(top.window.document).find(".dhccModalDialog").eq(2).remove();
		}else{
			$(top.window.document).find(".dhccModalDialog").eq(1).remove();
		}
	};
	var _updatetCollateralBase = function(obj){
		var url = $(obj).attr("action");
		var flag=submitJsMethod($(obj).get(0), '');
		var dataParam = JSON.stringify($(obj).serializeArray()); 
			if(flag){
				LoadingAnimate.start();
				$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							alert(data.msg,3);
							var url = $(top.window.document).find('.dhccModalDialog').eq(0).find('iframe').attr('src');
							$(top.window.document).find(".dhccModalDialog").eq(0).find("iframe").attr('src',url);
							 top.flag = true;
							 _closeMyOnly();
						}else{
							alert(top.getMessage("FAILED_SAVE"),0);
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
		updatetCollateralBase :_updatetCollateralBase,
		closeMyOnly:_closeMyOnly,
	};
}(window, jQuery);
