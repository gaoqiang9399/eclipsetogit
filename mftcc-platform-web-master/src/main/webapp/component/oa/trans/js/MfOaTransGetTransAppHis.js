;
var OaTrans = function(window, $) {
	var _init = function () {	
//		_getSPInfo();
		showWkfFlow($("#wj-modeler2"),id);
		_bindClose();
	};
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	var _getSPInfo = function(){
		$.ajax({
			type: "post",
			data:{id:id},
			dataType: 'json',
			url: webPath+"/mfOaTrans/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
	};
		
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);
