;
var MfRiskLevelAdjust_Detail = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		if(applySts != 0){
			_showApproveHis();
		}
		$(".ls_list").show();
	};
	
	//展示审批历史
    function _showApproveHis(){
		//获得审批历史信息
    	if(operateType == "4"){
    		if(comeFrom == "1"){
    			showWkfFlowVertical($("#wj-modeler2"),id,"35","cus_asset_dispose_approval");
    		}else{
    			showWkfFlowVertical($("#wj-modeler2"),id,"32","risk_dispose_approval");
    		}
    		
    	}else{
    		if(comeFrom == "1"){
    			showWkfFlowVertical($("#wj-modeler2"),id,"34","cus_risk_manage_approval");
    		}else{
    			showWkfFlowVertical($("#wj-modeler2"),id,"29","risk_manage_approval");
    		}
    	}
		$("#riskLevelAdjustApproveHis").show();
	}
	function _ajaxSave(){
		$.ajax({
			url : webPath+"/mfRiskLevelAdjust/submitProcessAjax",
			data : {
				id:id
				},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					window.top.alert(data.msg, 3);
					myclose_click();
				} else {
					alert(data.msg, 0);
				}
			},
			error : function() {
			}
		});
	}

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		showApproveHis:_showApproveHis
	};
}(window, jQuery);
