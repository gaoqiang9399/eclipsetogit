var MfCusComplaintMent = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    }
    /** 保存 */
    var _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

    var _getInput = function (obj,url){
        top.openBigForm(url,"申诉管理详情", function(){
            window.location.reload();
        });
    };
    return {
        init : _init,
        ajaxSave : _ajaxSave,
        getInput : _getInput
    };
}(window, jQuery);