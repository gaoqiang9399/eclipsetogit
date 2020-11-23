;
var MfBusGpsReg_CarCheck = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _submitAjax = function(){
		var url = webPath+"/mfCusCustomer/validateCusInfo";
		$.ajax({
			url : url,
			data : {"appId" : appId,"cusNo" : cusNo},
			async : false,
			success : function(data) {
				if (data.flag == "success") {
					if(!data.complete){
						window.top.alert(data.msg, 3);
					}else{
						alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
							$.ajax({
								url : webPath+"/mfLoanApply/commitProcessAjax",
								data : {"appId" : appId},
								async : false,
								success : function(data) {
									if (data.flag == "success") {
										top.flag = true;
										top.appSts = data.appSts;
										window.top.alert(data.msg, 3);
										myclose_click();
									} 
								},error : function() {
									alert(top.getMessage("ERROR_SERVER"),0);
								}
							});
						});
					} 
				}
			},
			error : function() {
				alert(top.getMessage("ERROR_SERVER"),0);
			}
		});
	};
	return {
		init : _init,
		submitAjax:_submitAjax,
	};
}(window, jQuery);


function addCusFormInfoCall() {
	if (top.addFlag) {
		MfCusDyForm.initCusIntegrity(top.infIntegrity);
		$("#rotate-body").find(".rotate-div[name=" + top.action + "]").remove();
		if ($(".dynamic-block[name=" + top.action + "]").length) {
			var $dynamicBlock = $(".dynamic-block[name=" + top.action + "]");
			$dynamicBlock.find(".formDel-btn").remove();
			$dynamicBlock.show();
			if (top.htmlStrFlag) {
				if (top.showType == "1") {
					$dynamicBlock.find(".content form").empty();
					$dynamicBlock.find(".content form").html(top.htmlString);
					$dynamicBlock.find(".formAdd-btn").remove();
				} else {
					$dynamicBlock.find(".content").empty();
					if (top.action == "MfCusBankAccManageAction") {
						$dynamicBlock.find(".content").html(top.htmlString+"<input id='updateByOneUrl' type='hidden' value=webPath+'/mfCusBankAccManage/updateByOneAjax'></input>");
					}else{
						$dynamicBlock.find(".content").html(top.htmlString);
					}	
				}
			}
			$("table td[mytitle]:contains('...')").initMytitle();
			
		} else {
			if (top.htmlStrFlag) {
				MfCusDyForm.setBlock(top.showType, top.title, top.action, top.htmlString,"1", top.isMust, top.tableName, null, null,top.sort);
				dblclickflag();
			}
		}
		if (top.action == "MfCusBankAccManageAction") {
			dealBankNo();
		}
	}
}

//处理银行卡号
function dealBankNo(){
	$(".bankNo").each(function(i, item) {
		var itemBankNo = $(item).text();
		var itemBankNoHtml = $(item).html();
		if(/\S{5}/.test(itemBankNo)){
			 $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
		}
	});
}