;
var WkfVouAfterRiskLevelAdjustViewPoint = function (window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		//意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
	};
	var _doSubmitAjax = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
			//没有选择审批意见默认同意
			if(opinionType == undefined || opinionType == ''){
				opinionType = "1";
			}
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfVouAfterRiskLevelAdjust/submitUpdateAjax?appNo="+id+"&opinionType="+opinionType,formObj,'VouAfterRiskLevelAdjust');
		}
	};

	return {
		init:_init,
		doSubmitAjax:_doSubmitAjax
	};
}(window,jQuery);