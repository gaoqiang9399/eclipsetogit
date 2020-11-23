/* 调用规则的公共js */

var rulesCall = function(window,$){
	
	//业务验证规则
	var _validateRules = function(parmData,ruleCusNo){
		var ruleFlag = true;
		//var parmData = {'nodeNo':ruleNodeNo, 'relNo': ruleRelNo, 'cusNo': ruleCusNo,'kindNo':kindNo};
		$.ajax({
			url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
			type : "post",
			async: false,
			data : {ajaxData: JSON.stringify(parmData)}, 
			dataType : "json",
			success : function(data) {
				if (data.exsitRefused == true) {// 存在业务拒绝
					top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ruleCusNo,'风险拦截信息',function(){
						
					});
					ruleFlag = false;
				}else if(data.exsitFX == true){//存在风险项
					alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"业务申请"}), 2, function() {
					},function(){
					});
				}else {
				}
			},
			error : function() {
			}
		});
		return ruleFlag;
	};
	
	return{
		validateRules:_validateRules,
	};
}(window,jQuery);



