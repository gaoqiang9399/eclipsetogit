var Notice = function (window, $) {
    var _init = function () {
        var downloadNotice = $.cookie('downloadNotice');
        if(downloadNotice == null || downloadNotice == 'undefined'){
            var content = "您好！</br>&emsp;在使用系统前需要安装文档编辑控件，<a href='javascript:void(0);' onclick='downloadTools();' style='text-decoration: underline;font-weight: bold'>请下载</a>（安装时需要关闭所有浏览器）。";
            content += "</br>&emsp;项目经理请在使用系统功能时认真阅读《项目经理操作指引》。";
            content += "</br></br><div style='text-align: center;position: relative;top: 10px'><input id='downloadNotice' type='checkbox'> 不再提醒</div>";
            var d = dialog({
                title: '使用须知',
                padding : 25,
                okValue : "关闭",
                content:content,
                ok: function () {
                    var notice = $("#downloadNotice").prop('checked');
                    if(notice){
                        $.cookie('downloadNotice', '1', { expires: 365*100, path: '/' });
                    }
                    this.remove();
                }
            });
            d.show();
        }
    };

    return {
        init:_init
    };

}(window, jQuery);