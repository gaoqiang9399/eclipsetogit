var MfCusAccountDebtor = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name='accountNumber']").on('keyup input',function(){
		 	  var  accountNo =$(this).val();
		        var reg=/^-?[0-9,\s]*$/;//此写法允许首字符为0
				if(!reg.test(accountNo)){
					$(this).val("");
				}else{
					 if(/\S{5}/.test(accountNo)){
			            this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
					}
		       	}		       	
	        });
        $("input[name='reconciliationMonthDate']").blur(function(){
            var reconciliationNo = $("input[name='reconciliationMonthDate']").val();
            if(reconciliationNo == "" || reconciliationNo<1 || reconciliationNo >31){
                window.top.alert("每月对账日期输入有误",3);
                $("input[name='reconciliationMonthDate']").val('');
            }
        });
	};
	//GPS信息登记
	var _insertAjax = function (obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	return {
		init : _init,
		insertAjax : _insertAjax
	};
}(window, jQuery);