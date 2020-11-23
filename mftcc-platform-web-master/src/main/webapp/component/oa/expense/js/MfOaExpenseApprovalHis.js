;
var OaExpenseApp = function(window, $) {
	var _init = function () {	
		$(function(){
//			$(".scroll-content").mCustomScrollbar({
//				advanced : {
//				updateOnContentResize : true
//				}
//			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		});
//		_getSPInfo();
//		showWkfFlow($("#wj-modeler2"),expenseId);
		showWkfFlowVertical($("#wj-modeler2"),expenseId,"","")
	};
	
	function _getSPInfo(){
		$.ajax({
			type: "post",
			data:{expenseId:expenseId},
			dataType: 'json',
			url: webPath+"/mfOaExpense/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});" "
	}
	return {
		init : _init
	};
}(window, jQuery);