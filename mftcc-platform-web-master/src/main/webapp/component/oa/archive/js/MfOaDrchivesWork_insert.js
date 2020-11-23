;
var OaArchivesWorkInsert = function (window,$){
	var _init = function(){
		$(".mf_contentt").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindInsertAjax("#work_form");
	};
	var _bindInsertAjax = function(obj){
		$(".work_insertAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
								window.top.alert(data.msg, 1);
								top.workFlag = true;
								top.workTableHtml = data.workTableHtml;
								myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
						
					}
				});
			}
		});
	};
	
	return {
		init:_init,
	};
}(window,jQuery);