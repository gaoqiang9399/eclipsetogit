;
var MfAssetsDisposal_Detail  = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
		if(applySts != 0){
			_showApproveHis();
		}
	};
	//展示审批历史
    var _showApproveHis = function (){
		//获得审批历史信息
    	var type = "43";
    	var approveType="sale_apply_approval";
    	switch (handleType) {
		case "1"://拍卖
			type = "43";
            approveType="sale_apply_approval";
			break;
		case "2"://租赁
			type = "44";
            approveType="lease_apply_approval";
			break;
		default:
			break;
		}

		showWkfFlowVertical($("#wj-modeler2"),disposalId,type,approveType);
		$("#assetsDisposalHis").show();
	}
	return {
		init : _init,
	};
}(window, jQuery);
