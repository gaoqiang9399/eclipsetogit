;
var MfMaintenance_Insert = function(window, $) {
    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    };
    var _ajaxSave = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data : {
                    ajaxData:dataParam
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        top.addFlag = true;
                        if (data.htmlStrFlag == "1") {
                            top.htmlStrFlag = true;
                            top.htmlString = data.htmlStr;
                            top.updateFlag = true;
                            top.tableName = "mf_maintenance";
                        }
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                }
            });
        }
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        ajaxSave:_ajaxSave
    };
}(window, jQuery);
