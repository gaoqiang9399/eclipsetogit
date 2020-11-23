var MfCusCreditApply_inputStop = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

    var _ajaxSave = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            alert(top.getMessage("CONFIRM_OPERATION", "授信终止"), 2, function() {
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                $.ajax({
                    url : url,
                    data : {
                        ajaxData:dataParam
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        if (data.flag == "success") {
                            top.ifCreditStopFlag = true;
                            window.top.alert(data.msg, 3,function(){
                                myclose_click();
                            });
                        } else {
                            alert(data.msg, 0);
                        }
                    },
                    error : function() {
                        alert("终止授信失败", 0);
                    }
                });
            });
        }
    };
	return{
		init:_init,
        ajaxSave:_ajaxSave
	};
}(window,jQuery);