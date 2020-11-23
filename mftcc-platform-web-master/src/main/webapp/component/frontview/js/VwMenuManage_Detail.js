;
var VwMenuManageDetail = function(window, $) {
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
function myUpdateAjax(dom){//新增方法
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
				top.updateFlag=true;
				window.top.alert(data.msg, 1);
				 $(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
			} else {
				window.top.alert(data.msg, 0);
			}
		},
		error : function() {
			LoadingAnimate.stop(); 
			alert(top.getMessage("ERROR_REQUEST_URL", url));
		}
	});
}
var baseSort=$("[name=sort]").val();
var basePId=$("[name=pId]").val();
function getMaxSort(dom){
	var pId=$(dom).val();
	if(basePId!=pId){
		$.ajax({
			url:webPath+'/vwMenuManage/getMaxSort',
			dataType:'json',
			type:'post',
			data:'pId='+pId,
			success:function(data){
				$("[name=sort]").val(parseInt(data.maxSort)+1);
			}
		})
	}else{
		$("[name=sort]").val(baseSort);
	}
}