;
var MfCusDyTable = function (window, $) {
    var _init = function () {
        var $dom = $('.dynamic-block');
        $dom.each(function () {
            var action = $(this).attr("name");
            var title = $(this).attr("title");
            var $div = $("div[id="+action+"]");
            FormFactor.moreCallBack($div,cusNo,action,formEditFlag,title)
        })
    };

    return {
        init: _init
    };
}(window, jQuery);