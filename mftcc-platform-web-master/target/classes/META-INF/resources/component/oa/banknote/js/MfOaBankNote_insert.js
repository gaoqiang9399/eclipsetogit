;
var OaBankNoteInsert = function (window,$){
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=accountNo]").on('keyup input',function(){
	           var  accountNo =$(this).val();
	           if(/\S{5}/.test(accountNo)){
	            	this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
	       	 	}
     });
		_bindInsertAjax("#banknoteForm");
	};
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
								window.top.alert(data.msg, 3);
								top.addFlag = true;
								myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
						
					}
				});
			}
		});
	};
	var _getBankByCardNumber = function(obj){
		var identifyNumber = obj.value.trim().replace(/\s/g,"");
		$.ajax({
			url:webPath+"/bankIdentify/getByIdAjax",
			data:{identifyNumber:identifyNumber},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name=bankNo]").val(data.bankId);
					$("input[name=bank]").val(data.bankName);
				}else{
					$("input[name=bankNo]").val("");
					$("input[name=bank]").val("");
				}	
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		};
	return {
		init:_init,
		getBankByCardNumber:_getBankByCardNumber,
	};
}(window,jQuery);