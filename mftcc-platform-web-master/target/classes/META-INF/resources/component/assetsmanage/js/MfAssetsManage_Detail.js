;
var MfAssetsManage_Detail = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
		if(appSts != "2" && appSts != ""){
			$(".seal-name-div").html(appStsShow);
		}
	};
	// 处置方式：1处置（公开拍卖/协议处置）/2租赁
	var _addDisposal = function(handleType){
		var showName = "处置";
		switch(handleType){
		case "1":
			showName = "拍卖";
			break;
		case "2":
			showName = "租赁";
			break;
		default:
			break;
			
		}
		top.addFlag = false;
		top.openBigForm(webPath + "/mfAssetsDisposal/input?handleType=" + handleType + "&assetsManageId=" + assetsManageId,"新增" + showName, function(html){
			if(top.addFlag){
				$("#manageDetail").html(top.auctionHtml);
				$(".seal-name-div").html(top.appSts);
				$("button[name='assetsManage']").attr("disabled","disabled");
				alert(top.msg,3);
			}
 		});	
	}
    // 处置方式：1处置（公开拍卖/协议处置）/2租赁
    var _chooseDisposal = function(handleType){
        var showName = "处置";
        switch(handleType){
            case "1":
                showName = "资产处置";
                break;
            case "2":
                showName = "租赁";
                break;
            default:
                break;

        }
        top.addFlag = false;
        top.openBigForm(webPath + "/mfAssetsDisposal/input?handleType=" + handleType + "&assetsManageId=" + assetsManageId,"新增" + showName, function(html){
            if(top.addFlag){
                $("#manageDetail").empty().html(top.allHtml);
                $(".seal-name-div").html(top.appSts);
                $("button[name='assetsManage']").attr("disabled","disabled");
                switch(handleType){
                    case "1":
                        $("#disposalDetail").empty().html(top.auctionHtml);
                        break;
                    case "2":
                        $("#leaseDetail").empty().html(top.leaseHtml);
                        break;
                    default:
                        break;

                }
                alert(top.msg,3);
            }
        });
    }
	var _getAssetsDisposalDetail = function(url){
		top.openBigForm(webPath + url + "&entryFlag=1","详情", function(){
 		});
	}
    var _getAssetsDailyDetail = function(url){
        top.openBigForm(webPath + url + "&entryFlag=1","日常管理详情", function(){
            $.ajax({
                url : webPath+ "/mfAssetsDailyManage/callBackAjax?assetsManageId="+assetsManageId,
                data : {
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        $("#dailyManageDetail").empty().html(data.dailyManageHtml);
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                }
            });
        });
    }
	var _insertDailyManage = function () {
        top.openBigForm(webPath+ "/mfAssetsDailyManage/input?assetsManageId="+assetsManageId,"新增日常管理", function(){
            $.ajax({
                url : webPath+ "/mfAssetsDailyManage/callBackAjax?assetsManageId="+assetsManageId,
                data : {
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        $("#dailyManageDetail").empty().html(data.dailyManageHtml);
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                }
            });
        });
    }
	return {
		init : _init,
		addDisposal:_addDisposal,
		getAssetsDisposalDetail:_getAssetsDisposalDetail,
        insertDailyManage:_insertDailyManage,
        chooseDisposal:_chooseDisposal,
        getAssetsDailyDetail:_getAssetsDailyDetail
	};
}(window, jQuery);
