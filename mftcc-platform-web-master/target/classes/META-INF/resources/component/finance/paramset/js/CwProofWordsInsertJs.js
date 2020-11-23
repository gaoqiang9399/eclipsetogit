;
var CwProofWordsInsertJs=function(window,$){
	var _init=function(){
		_bindClose();
	}
	var _bindClose = function () {//绑定关闭事件
		$(".cancel").bind("click", function(event){
			$(top.window.document).find("#showDialog").remove();
		});
	}; 
	return {init:_init}
}(window,jQuery)

function myInsertAjax(dom){//新增方法
	
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
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
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
	}
}

