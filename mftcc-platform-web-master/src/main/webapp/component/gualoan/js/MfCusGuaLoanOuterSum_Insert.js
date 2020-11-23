;
var MfCusGuaLoanOuterSum_Insert = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	}
	
	//新增申请
	var _insertPreRepayApply= function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			 alert('确定要提交吗?',2,function(){//确定
					 saveInfo(obj);
			  },function(){//取消
			  }); 
		}
	};
	//保存申请信息
	var saveInfo = function(obj){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : webPath+url,
			data : {ajaxData : dataParam},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					window.top.alert(data.msg,1);
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	};
	$("input[name='guaranteeAmt'] , input[name='guaranteeBal']").bind('blur',function() {
		if(parseInt($("input[name='guaranteeAmt']").val().replace(/,/g,'')) < parseInt($("input[name='guaranteeBal']").val().replace(/,/g,''))){
			window.top.alert("担保余额不能大于担保总额",0);
		}
	});
	$("input[name='loanAmt'] , input[name='loanBal']").bind('blur',function() {
		if(parseInt($("input[name='loanAmt']").val().replace(/,/g,'')) < parseInt($("input[name='loanBal']").val().replace(/,/g,''))){
			window.top.alert("贷款余额不能大于贷款总额",0);
		}
	});
	return{
		init:_init,
//		insertPreRepayApply:_insertPreRepayApply
	}
}(window,jQuery);