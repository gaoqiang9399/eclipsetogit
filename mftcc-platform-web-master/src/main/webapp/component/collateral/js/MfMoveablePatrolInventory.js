	function setCheckOpNo(userInfo){
		$("input[name=patrolOpName]").val(userInfo.opName);
		$("input[name=patrolOpNo]").val(userInfo.opNo);
	};
;
var MfMoveablePatrolInventory=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_isRiskChange();
	};
	//保存方法
	var _ajaxPatrolInventoryApplySave = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	var _setCheckOpNo=function(userInfo){
		$("input[name=checkOpName]").val(userInfo.opName);
		$("input[name=checkOpNo]").val(userInfo.opNo);
	};
	var _isRiskChange=function(){
		var isRisk=$("select[name=isRisk]").val();
		var $riskType=$("select[name=riskType]").parents("tr").eq(0);
		var $riskRemark=$("textarea[name=riskRemark]").parents("tr").eq(0);
		if(isRisk=="0"){
			$riskType.hide();
			$riskRemark.hide();
		}
		if(isRisk=="1"){
			$riskType.show();
			$riskRemark.show();
		}
	}
	return{
		init:_init,
		ajaxPatrolInventoryApplySave:_ajaxPatrolInventoryApplySave,
		setCheckOpNo:_setCheckOpNo,
		isRiskChange:_isRiskChange
	};
}(window,jQuery);