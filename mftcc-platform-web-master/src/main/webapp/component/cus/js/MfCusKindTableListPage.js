;
var MfCusKindTableListPage = function(window, $) {
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
									} else {
										DIALOG.error(data.msg);
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

// 判断修改客户信息是否走审批
var ifApprovalCusInfo = function(dataParam){
    var flag = false;
    if(effectFlag == "0"){//客户信息未生效，直接返回，客户信息可以直接修改
        return flag;
    }
    if(effectFlag == "2"){// 客户状态为销户，不允许修改数据
        flag = true;
        alert("客户已被销户，不允许修改客户信息");
        return flag;
    }
    jQuery.ajax({
        url: webPath + "/mfCusKeyInfoFields/ifKeyField",
        data:{ajaxData:dataParam},
        type:"POST",
        dataType:"json",
        async:false,//关键
        success:function(data){
            if(data.flag == "error"){
                flag = true;
                alert("保存失败");
            }else{
                if(data.ifKeyField == "1"){
                    flag = true;
                    top.openBigForm(webPath+"/mfCusInfoChange/input?cusNo=" + data.cusNo + "&ajaxData=" + encodeURIComponent(data.ajaxData),"客户信息修改申请", function(){
                    });
                }else{
                    flag = false;
                }
            }
        },error:function(data){
            flag = true;
            alert("保存失败");
        }
    });
    return flag;

}
