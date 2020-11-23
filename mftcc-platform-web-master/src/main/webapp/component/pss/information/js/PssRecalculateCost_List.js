;
var pssRecalculateCost_List = function(window, $){
	var _recalculateCost = function(){
		LoadingAnimate.start();
		var url = webPath+"/pssRecalculateCost/recalculateCostAjax";
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					//$('button[type="button"]').attr("disabled", true);
					window.top.alert("重算成本成功！", 1);
					myclose_click();
				} else if(data.flag == "error"){
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage(
						"ERROR_REQUEST_URL", url), 0);
			}
		});
	};
	
	return{
		recalculateCost : _recalculateCost
	};
}(window, jQuery);