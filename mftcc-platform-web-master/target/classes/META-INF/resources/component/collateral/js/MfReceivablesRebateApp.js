;
var MfReceivablesRebateApp=function(window,$){
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
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_getPledgeData();
	};
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=rebateReces]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:true,//单选
						items:data.items
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//申请折让金额变化时，修改折让后需偿还本金及利息。
	var _rebateAmtChange=function(){
		var rebateAmt = $("input[name=rebateAmt]").val();
		rebateAmt= rebateAmt.replace(/,/g,"");
		if(parseFloat(fincAppAmt)<parseFloat(rebateAmt)){
			var rebateAmtTitle=$("input[name=rebateAmt]").attr("title");
			var fincAppAmtTitle=$("input[name=fincAppAmt]").attr("title");
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":rebateAmtTitle,"timeTwo":fincAppAmtTitle}),3);
			$("input[name=rebateAmt]").val("");
			$("input[name=rebateRepayAmt]").val("");
			return false;
		}
		if(rebateAmt!=""){
			$("input[name=rebateRepayAmt]").val(rebateAmt);
		}
	};
	//折让申请保存
	var _ajaxRebateAppSave=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.rebate=true;
						window.top.alert(data.msg,3);
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
					myclose_click();
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	return{
		init:_init,
		rebateAmtChange:_rebateAmtChange,
		ajaxRebateAppSave:_ajaxRebateAppSave,
	};
}(window,jQuery);