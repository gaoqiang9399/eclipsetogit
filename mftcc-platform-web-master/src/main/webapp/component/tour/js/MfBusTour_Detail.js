;
var MfBusTour_Detail = function (window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
	};
	 _myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 window.location.href = webPath+"/mfCusWhitename/getListPage";//重新刷新列表
		 };
	//详情页面修改的方法；
		 var _ajaxSave = function(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					$.ajax({
						url : url,
						data : {
							ajaxData:dataParam
							},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if (data.flag == "success") {
								window.top.alert(data.msg, 3);
								myclose_click();
							} else {
								alert(data.msg, 0);
							}
						},
						error : function() {
						}
					});
				}
			};
	return {
		init:_init,
		myclose:_myclose,
		ajaxSave:_ajaxSave,
	};
}(window,jQuery);
