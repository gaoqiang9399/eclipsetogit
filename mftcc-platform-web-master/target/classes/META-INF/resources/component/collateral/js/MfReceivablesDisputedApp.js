;
var MfReceivablesDisputedApp=function(window,$){
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
		_setInitiatorOption();
		_getPledgeData();
		//_getRelInvoiceData();
	};
	//争议申请保存
	var _ajaxDisputedAppSave=function(obj){
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
						top.disputedFlag=true;
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
	var _relieveDisputedSave=function(obj){
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
						top.relieveFlag=true;
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
	//给争议发起人赋值
	var _setInitiatorOption=function(){
		var optionHtml ="<option value='" + cusNo
		+ "' selected>" + cusName + "</option>";
		optionHtml=  optionHtml + "<option value='" + cusNoCore
		+ "'>" + cusNameCore + "</option>";
		$("select[name=disputedInitiatorNo]").html(optionHtml);
		$("input[name=disputedInitiatorName]").val(cusName);
	}
	//选择发起方回调赋值
	var _initiatorChange=function(){
		var initiatorNo = $("select[name=disputedInitiatorNo]").val();
		if(initiatorNo==cusNo){
			$("input[name=disputedInitiatorName]").val(cusName);
		}else{
			$("input[name=disputedInitiatorName]").val(cusNameCore);
		}
	};
	/*var _relInvoiceInit=function(){
		$("input[name=relInvoiceNum]").popupSelection({
			//ajaxUrl:webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			searchOn:true,//启用搜索
			inline:true,
			multiple:true,//单选
			items:items2
		});
	};*/
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=relPledgeNo]").popupSelection({
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
	//根据选择的关联押品初始化关联发票
	var _getRelInvoiceData=function(){
		var pleStr = $("input[name=relPledgeNo]").val();
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getRelInvoiceByPledgeAjax?busCollateralId="+busPleId+"&pleStr="+pleStr,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=relInvoiceNum]").popupSelection({
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
	return{
		init:_init,
		ajaxDisputedAppSave:_ajaxDisputedAppSave,
		relieveDisputedSave:_relieveDisputedSave,
		initiatorChange:_initiatorChange,
	};
}(window,jQuery);