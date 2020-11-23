;
var VwFeatureManageInsert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);
//$(".scroll-content").mCustomScrollbar({
//	advanced:{
//		theme:"minimal-dark",
//		updateOnContentResize:true
//	}
//});
myCustomScrollbarForForm({
	obj:".scroll-content",
	advanced : {
		theme : "minimal-dark",
		updateOnContentResize : true
	}
});
$(".cancel").bind("click", function(event){
	$(top.window.document).find("#showDialog").remove();
});
function myInsertAjax(dom){//新增方法
	var url = $(dom).attr("action");
	var dataParam = JSON.stringify($(dom).serializeArray());
	LoadingAnimate.start();
	$.ajax({
		url:url,
		data : {
			ajaxData : dataParam
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop(); 
			if (data.flag == "success") {
				top.addFlag=true;
				window.top.alert(data.msg, 1);
				 $(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
			} else {
				window.top.alert("操作成功！", 0);//消息模板等功能完成了再使用。
			}
		},
		error : function() {
			LoadingAnimate.stop(); 
			alert(top.getMessage("ERROR_REQUEST_URL", url));
		}
	});
}