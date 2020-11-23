;
var MfReceivablesTransferInfo=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	var _insertReceTranApp=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag = true;
						top.transferFlag=true;
						myclose_click();
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	var _receTranAmtChange=function(){
		var tranAmt = $("input[name=tranAmt]").val();
		var receAmt = $("input[name=receAmt]").val();
		var receBal = $("input[name=receBal]").val();
		tranAmt= tranAmt.replace(/,/g,"");
		receAmt=receAmt.replace(/,/g,"");
		receBal=receBal.replace(/,/g,"");
		//转让金额不能大于应收账款余额
		if(parseFloat(receAmt)<parseFloat(tranAmt)){
			var tranAmtTitle=$("input[name=tranAmt]").attr("title");
			var receBalTitle=$("input[name=receBal]").attr("title");
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":tranAmtTitle,"timeTwo":receBalTitle}),3);
			$("input[name=tranAmt]").val("");
			return false;
		}
		if(tranAmt!=""){
			var tranRate = (parseFloat(tranAmt)/parseFloat(receAmt))*100;
			$("input[name=tranRate]").val(Math.floor(tranRate));
		}
	};
	return{
		init:_init,
		insertReceTranApp:_insertReceTranApp,
		receTranAmtChange:_receTranAmtChange
	};
}(window,jQuery);