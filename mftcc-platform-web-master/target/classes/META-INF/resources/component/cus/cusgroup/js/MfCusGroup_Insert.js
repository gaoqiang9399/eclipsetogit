;
var MfCusGroup_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

	 _myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 window.location.href = webPath+"/mfCusGroup/getListPage";//重新刷新列表
		 };
	//demo新增
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						alert(data.msg,1);
						window.top.alert(data.msg, 3);//进入流程审批的确认窗口
						myclose_click();//保存完成之后关闭弹窗，回到列表
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	};


	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myclose : _myclose,
		ajaxSave:_ajaxSave,
		
	};
}(window, jQuery);
