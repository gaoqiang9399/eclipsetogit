var MfCusSharePledge_Insert  = function (window, $) {
    //初始化客户查询历史列表
    var _init = function () {
    }
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
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                    alert("新增股权质押失败", 0);
                }
            });
        }
    };

    return {
        init: _init,
        ajaxSave: _ajaxSave,
    }

}(window, jQuery);