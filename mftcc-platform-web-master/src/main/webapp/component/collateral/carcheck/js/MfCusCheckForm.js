;
var MfCusCheckForm = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});

	};

	 var _submitCusAjax = function(obj){
         alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
		 var dataParam = JSON.stringify($(obj).serializeArray());
		 $.ajax({
				url : webPath+'/mfCarCheckForm/submitCusAjax',
				data : {"ajaxData" : dataParam},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag == "success"){
                        top.flag = true;
						window.top.alert(data.msg,3);
						myclose_click();
					}else{
						alert(top.getMessage("ERROR_INSERT"),0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
         });
	 }
	
	return{
		init:_init,
        submitCusAjax:_submitCusAjax,
	};
}(window,jQuery);