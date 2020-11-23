;
var MfCollateralTable_CarCheck = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _submitAjax = function(){
		alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/pledgeBaseInfo/submitForm",
				data : 
				{
					appId: appId, 
					pledgeNo : pledgeNo
				},
				async : false,
				success : function(data) {
					if (data.flag == "success") {
						top.flag = true;
						window.top.alert(data.msg, 3);
						myclose_click();
					}else if (data.flag == "checkError") {
						window.top.alert(data.msg, 3);
					}
				},error : function() {
					 LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	return {
		init : _init,
		submitAjax:_submitAjax,
	};
}(window, jQuery);


function addPledgeFormInfoCall() {
	if (top.addFlag) {
		MfCusDyFormCar.initCusIntegrity(top.infIntegrity);
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