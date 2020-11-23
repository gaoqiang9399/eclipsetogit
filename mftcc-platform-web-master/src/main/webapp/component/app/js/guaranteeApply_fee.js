;
var guaranteeApply_fee = function(window, $) {
	var _init = function() {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
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
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
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

    //判断实收费用是否大于应收费用
    var _getFeiCha = function (obj) {
        var fundsReceived = $("input[name=fundsReceived]").val();
        var feeTotalAmt = $("input[name=feeTotalAmt]").val();
        if(Number(fundsReceived) > Number(feeTotalAmt)){
            alert("实收费用不能大于应收费用", 2);
            $("input[name=fundsReceived]").val(feeTotalAmt);
        }
    };

	return {
		init : _init,
		submitForm : _submitForm,
		setFeeMain :_setFeeMain,
        getFeiCha :_getFeiCha
	};
}(window, jQuery);
