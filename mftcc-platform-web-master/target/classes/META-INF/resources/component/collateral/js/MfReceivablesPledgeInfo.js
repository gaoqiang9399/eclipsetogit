;
var MfReceivablesPledgeInfo=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		//保理业务
		if(busModel=="13"){
			$("input[name=pledgorName]").attr("title","出让人");
			$("input[name=pledgorName]").parents("tr").find("label").html("出让人");
			$("input[name=pledgorRegisterAddress]").attr("title","出让人注册地址");
			$("input[name=pledgorRegisterAddress]").parents("tr").find("label").html("出让人注册地址");
			$("input[name=pledgeeName]").attr("title","受让人");
			$("input[name=pledgeeName]").parents("tr").find("label").html("受让人");
			$("input[name=pledgeeRegisterAddress]").attr("title","受让人注册地址");
			$("input[name=pledgeeRegisterAddress]").parents("tr").find("label").html("受让人注册地址");
		}
	};
	var _insertAjax=function(obj){
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
						top.flag=true;
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	//信用担保方式情况下，跳过担保登记业务节点直接进行下一步
	var _submitBussProcessAjax = function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralRel/submitBussProcessAjax",
			data : {appId:appId},
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					top.flag=true;
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete : function(){
				LoadingAnimate.stop();
			}
		});
	};
	
	return{
		init:_init,
		insertAjax:_insertAjax,
		submitBussProcessAjax:_submitBussProcessAjax,
	};
}(window,jQuery);