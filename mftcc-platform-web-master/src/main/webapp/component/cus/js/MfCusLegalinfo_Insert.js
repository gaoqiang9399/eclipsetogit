;
var MfCusLegalinfo_Insert = function(window, $) {
    var _init = function() {
        myCustomScrollbarForForm({
            obj : ".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
    };
    var _myclose = function(){
        myclose_click();
    };
    //新增保存
    var  _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };
    return {
        ajaxSave:_ajaxSave,
        init : _init,
        myclose:_myclose
    };
}(window, jQuery);