;
var MfCusPersonalProfileInfo_Insert = function (window, $) {
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

//分级加载areaProvice隐藏，areaCity显示
function selectAreaProviceCallBack(areaInfo){
    $("input[name=careaProvice]").val(areaInfo.disNo);
    $("input[name=careaCity]").val(areaInfo.disName);
    $("input[name=address]").val(areaInfo.disName);
};