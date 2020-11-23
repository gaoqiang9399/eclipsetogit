var creditApprovalHis = function(window,$){	
	var _init = function(){
//		creditApprovalHis.getSPInfo();
//		showWkfFlow($("#wj-modeler2"),appNo);
		showWkfFlowVertical($("#wj-modeler2"),appNo,"1","credit_approval");
	};
	
	var _getSPInfo = function(){
		$.ajax({
			type: "post",
			data:{appNo:appNo},
			dataType: 'json',
			url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {}
		});
	};
	
	
	return{
		init:_init,
		getSPInfo:_getSPInfo
	};
}(window,jQuery);