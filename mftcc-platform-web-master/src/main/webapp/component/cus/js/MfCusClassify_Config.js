function init(){
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			theme:"minimal-dark",
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
}
function ajaxUpdateThis(obj){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
						url:url+"?formId="+formId,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								top.flag="1";
								top.optName=data.optName;
								top.optCode=data.optCode;
								top.remark=data.remark;
								window.top.alert(data.msg, 1);
								myclose_click();
							}
						},error:function(data){
							LoadingAnimate.stop();
							 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				
				}
		
		}