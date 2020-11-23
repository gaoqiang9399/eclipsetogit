;
var OaArchivesEducation = function (window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindInsertAjax("#education_form");
	};
	var _bindInsertAjax = function(obj){
		$(".education_insertAjax").bind("click", function(event){
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
								top.educationFlag = true;
								top.educationTableHtml = data.educationTableHtml;
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