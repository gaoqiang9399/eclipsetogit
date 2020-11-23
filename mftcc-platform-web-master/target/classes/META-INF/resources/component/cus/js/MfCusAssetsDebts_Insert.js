;
var MfCusAssetsDebts_Insert = function(window, $) {
    var _init = function() {
        myCustomScrollbarForForm({
            obj : ".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
    };
    var _ajaxSave = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };
    var _myclose = function(){
        myclose_click();
    };
    return {
        init : _init,
        myclose:_myclose,
        ajaxSave:_ajaxSave,
    };
}(window, jQuery);