;
var MfAssetsDisposal_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
	};	
	var _ajaxSave = function(obj) {
		if(!_onblur()){
			return;
		};
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					handleType : handleType
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						top.msg = data.msg;
						top.addFlag = true;
						top.auctionHtml = data.auctionHtml;
                        top.leaseHtml = data.leaseHtml;
                       top.allHtml = data.allHtml;
						top.mfAssetsDisposal = data.mfAssetsDisposal;
						top.appSts = data.appSts;
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	// 抵贷额要小于成交价格，租赁金额
	var _onblur = function(){
		const transactionPrice = $("input[name='transactionPrice']").val();
		const loanAmount = $("input[name='loanAmount']").val();
		if(CalcUtil.compare(loanAmount,transactionPrice) == 1){
			if(handleType == "1"){//拍卖
				alert("抵贷额不能大于成交价格",0);
			}else{
				alert("抵贷额不能大于租赁金额",0);
			}
			return false;
		}
		return true;
	};
	return {
		init : _init,
		ajaxSave : _ajaxSave,
		onblur:_onblur
	};
}(window, jQuery);
