;
var MfBusExtensionApply_Insert=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//发起展期申请
	var _ExtensionApplySaveAjax=function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
                        top.alert("保存成功！",1);
						top.saveFlag=true;
						top.wkfAppId=data.extensionApply.wkfAppId
						var fincId = data.extensionApply.fincId;
                        extensionList(fincId, 'tablepubviewExtensionListBase');
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

    function extensionList(fincId, planTableId) {
        $.ajax({
            url: webPath + "/pubviewExtension/getExtensionListAjax?fincId=" + fincId + "&tableId=" + planTableId,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                var html = data.htmlStr;
                var pageSum = data.pageSum;
                $("#extensionList").empty().html(html);
            }
        });
    };

	return{
		ExtensionApplySaveAjax:_ExtensionApplySaveAjax,
		init:_init
	};
}(window, jQuery);