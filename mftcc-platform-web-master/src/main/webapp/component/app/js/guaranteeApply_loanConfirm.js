;
var guaranteeApply_loanConfirm = function(window, $) {
	var _init = function() {
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

	var _submitForm = function(obj) {
		var url = $(obj).attr("action");
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {'appId':appId, ajaxData : dataParam },
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						var url = webPath+'/mfBusPact/getById?appId=' + data.appId+'&busEntrance=2';
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
						top.flag = true;
						myclose_click();
					} else {
						alert(top.getMessage("FAILED_SAVE"), 0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_SAVE"), 0);
				}
			});
		} else {
			alert(top.getMessage("FAILED_SAVE"), 0);
		}
	};

	return {
		init : _init
		, submitForm : _submitForm
	};
}(window, jQuery);
