;
var MfCusDotInfo_Insert = function (window, $) {
    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });

    };

    //新增保存
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init,
        ajaxSave: _ajaxSave,
    };

}(window, jQuery);

