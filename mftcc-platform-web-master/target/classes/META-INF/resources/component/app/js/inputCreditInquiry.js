;
var inputCreditInquiry = function(window, $) {
	var _init = function () {	
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _docommit = function(obj){
		alert(top.getMessage("CONFIRM_OPERATION","提交"+nodeName+"信息"),2,function(){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						window.top.alert(data.msg,3);
						top.flag = true;
						myclose_click();
					}else{
						alert(data.msg,0);
					}
				},error:function(){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		});
	};
	var _clossWindow = function clossWindow(){
		window.location.href=webPath+"/mfBusApply/getListPage";
	};
	return {
		init :_init,
		docommit : _docommit,
		clossWindow : _clossWindow,
	};
}(window, jQuery);
