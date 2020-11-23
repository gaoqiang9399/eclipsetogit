;
var MfReceRepoAffirm=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//保存方法
	var _ajaxRepoAffirmSave = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
						top.repoAffirmFlag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	//减免金额变化判断是否小于等于应付未付利息和更新实收金额
	var _reduceAmtChange=function(){
		var reduceAmt = $("input[name=reduceAmt]").val();
		reduceAmt= reduceAmt.replace(/,/g,"");
		if(parseFloat(accruedInterest)<parseFloat(reduceAmt)){
			var reduceAmtTitle=$("input[name=reduceAmt]").attr("title");
			var accruedInterestTitle=$("input[name=accruedInterest]").attr("title");
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":reduceAmtTitle,"timeTwo":accruedInterestTitle}),3);
			$("input[name=reduceAmt]").val("");
			$("input[name=receiptAmt]").val("");
			return false;
		}
		if(reduceAmt!=""){
			var receiptAmt = parseFloat(accruedInterest)+parseFloat(fincPrepayBal)-parseFloat(reduceAmt);
			$("input[name=receiptAmt]").val(receiptAmt);
		}
	};
	return{
		init:_init,
		ajaxRepoAffirmSave:_ajaxRepoAffirmSave,
		reduceAmtChange:_reduceAmtChange
	};
}(window,jQuery);