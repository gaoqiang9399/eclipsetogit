;
// 展期历史
var pub_extension_list = function (window, $) {
    // 初始化
    var _init = function (fincId, planTableId) {
        $.ajax({
            url: webPath + "/pubviewExtension/getExtensionListAjax?fincId=" + fincId + "&tableId=" + planTableId,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                var html = data.htmlStr;
                var pageSum = data.pageSum;
                if (pageSum == 0) {
                    $("#extensionListNew").hide();
                } else {
                    $("#extensionListNew").show();
                    $("#extensionList").empty().html(html);
                }
            }
        });
    };

    return {
        init: _init
    };

}(window, jQuery);
