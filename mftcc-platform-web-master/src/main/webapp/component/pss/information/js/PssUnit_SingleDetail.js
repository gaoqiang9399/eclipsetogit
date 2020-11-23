var PssUnit_SingleDetail = function(window, $) {
	var _init  = function() {
		$(function(){
		myCustomScrollbarForForm({
		obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	});
	};
	var _updateUnit = function(obj) {
	var flag = submitJsMethod($(obj).get(0), '');
	if (flag) {
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
};
	return {
		init : _init,
		updateUnit : _updateUnit
	};
}(window, jQuery);

window.onresize = function(){
	setTimeout(function(){
		$('.pss_detail_list_sp').css('height', '280');
		$('.pss_detail_list_sp #mCSB_1').css('height', 'auto');
	    $('.pss_detail_list_sp #mCSB_2').css('height', 'auto');
	    $('.pss_detail_list_sp .table-float-head').remove();
	    
	},200);
};