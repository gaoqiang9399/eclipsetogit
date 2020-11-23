;
var MfExamRiskLevelConfig=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	//风险级别保存
	var _ajaxExamRiskLevelSave=function(obj){
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
						top.addFlag=true;
						top.examRiskLevelConfig=data.examRiskLevelConfig;
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	return{
		init:_init,
		ajaxExamRiskLevelSave:_ajaxExamRiskLevelSave,
	}
}(window,jQuery);