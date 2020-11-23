;
var guaranteeApply_feeForReceFinc = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _submitForm = function(obj) {
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            $.ajax({
                url : url,
                data : { ajaxData : dataParam,fincMainId:fincMainId},
                success : function(data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        top.flag = true;
                        myclose_click();
                    }else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_click();
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
        } else {
            alert(top.getMessage("FAILED_SAVE"), 0);
        }
		
	};
	
	var _setFeeMain = function(sysOrg){
		$("input[name=feeMainNo]").val(sysOrg.brNo);
		$("input[name=feeMainName]").val(sysOrg.brName);
	};
	
	return {
		init : _init,
		submitForm : _submitForm,
		setFeeMain:_setFeeMain
	};
}(window, jQuery);
