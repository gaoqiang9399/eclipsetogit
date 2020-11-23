;
var MfRiskRegistration_Detail = function(window, $) {

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
	};
	
	//展示审批历史
    function _showApproveHis(){
		showWkfFlowVertical($("#wj-modeler2"),riskId,"40","riskreg_approval");
		$("#riskRegistrationApproveHis").show();
	}
	function _ajaxSave(){
		$.ajax({
			url : webPath+"/mfRiskLevelManage/submitProcessAjax",
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
