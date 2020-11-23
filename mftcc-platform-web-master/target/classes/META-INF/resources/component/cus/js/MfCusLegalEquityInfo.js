var MfCusLegalEquityInfo = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    }
    $("input[name=pushCapitalType]").popupSelection({
        searchOn:true,//启用搜索
        inline:false,//弹出模式
        multiple:true,//单选
        labelShow: false,//不显示已选选项
        title:"出资方式",
        items:PUSH_CAPITAL_TYPE
    });
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