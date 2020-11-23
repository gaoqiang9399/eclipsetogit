;
var MfFrontAppformActiveSetting = function(){
	//表单异步保存
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam,tableId:top.tableId},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						  window.top.alert(data.msg,1);
//						  top.appformSaveFlag = true;
//						  top.appformDataParam = dataParam;
						  $(".dhccModalDialog .i-x5",parent.document).last().click();//多个弹框先选择最后一个弹框关闭
						  //feeItemClose();
					}else{
						  window.top.alert(top.getMessage("ERROR_INSERT"),1);
					}
				},error:function(){
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			}); 
		}

	};
	var _myclose = function(obj){
		var $this = $(obj);
		$(".dhccModalDialog .i-x5",parent.document).last().click();//多个弹框先选择最后一个弹框关闭
//		$(top.window.document).find(".dhccModalDialog .i-x5").click();
//		myclose_click();//统一关闭表单
	};
	return {
		myclose:_myclose,
		ajaxSave:_ajaxSave
	};
}(window,jQuery);