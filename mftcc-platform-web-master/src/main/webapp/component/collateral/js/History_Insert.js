;
var HistoryInsert = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});

		//支持跳过时，跳过按钮显示
		/*if(supportSkipFlag=="1"){
			$(".skipButton").show();
		}else{
			$(".skipButton").hide();
		}*/

	};

	var _insertBase=function(obj){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		var param = {ajaxData : dataParam,cusNo:cusNo,appId:appId}
		jQuery.ajax({
			url : url,
			data : param,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag == "success") {
					top.historyNo =data.historyNo;
                    top.flag=true;
                    top.history = true;
                  /*inputHistoryDetail.init();*/
                    /*window.location.reload();*/
					alert(data.msg, 1);
					myclose_click();

				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};

	return {
		init:_init,
        insertBase:_insertBase,
	};
}(window, jQuery);