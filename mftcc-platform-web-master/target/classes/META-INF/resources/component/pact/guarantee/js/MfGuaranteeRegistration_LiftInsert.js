;
var MfGuaranteeRegistration_LiftInsert = function(window, $) {
    var ue;
    window.UPLOAD_IMG_URL = webPath + "/ueditor?action=uploadimage";
    window.UPLOAD_IMAGE_PATH = webPath;
    var _init = function() {
        _getUeditor();
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
    var _getUeditor = function () {
        $("[name='courierNo']").hide();
        var ueditorHtml = '<script id="courierNo"  type="text/plain"style="width:100%;height:390px;"></script>';
        var oldVal=$('textarea[name="courierNo"]').val(); //获取文本原来的值
        $("[name='courierNo']").after(ueditorHtml);
        ue = UM.getEditor('courierNo', {
            width : "100%",
            initialFrameHeight:320,
        });
        ue.ready(function () {
            ue.setContent(decodeURIComponent(oldVal));
			ue.setDisabled();
        });
    };
	var _ajaxSave = function(obj){
		var flagSub = submitJsMethod($(obj).get(0), '');
		if (flagSub) {
            var noticeContentVal = encodeURIComponent(ue.getAllHtml());
            $("textarea[name=courierNo]").val(noticeContentVal);
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
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	return {
		init : _init,
		ajaxSave:_ajaxSave,
	};
}(window, jQuery);
