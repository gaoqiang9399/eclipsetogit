;
var MfEvalIndexSub = function(window, $){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//子项选择选择组件
		$("input[name=subNo]").popupSelection({
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/appProperty/getAllAppPropertyList",
			multiple : true, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=subNo]"));
				$("input[name=subNoTmp]").val($("input[name=subNo]").val());
			},
		});
		
	};
	//评级指标子项新增修改保存
	var _ajaxInsertOrUpdate=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							top.saveFlag = true;
							window.top.alert(data.msg, 1);
							myclose_click();
						}
					},error:function(data){
						LoadingAnimate.stop();
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			
			}
	};
	var _close=function(){
		var evalSub=new Object();
		parent.dialog.get("evalSubDialog").close(evalSub);
	};
	return{
		init:_init,
		ajaxInsertOrUpdate:_ajaxInsertOrUpdate,
		close:_close,
	};
}(window, jQuery);