var MfCusExecNotice = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
        initPlace();
    }
    /** 保存 */
    var _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };
    return {
        init : _init,
        ajaxSave : _ajaxSave
    };
}(window, jQuery);