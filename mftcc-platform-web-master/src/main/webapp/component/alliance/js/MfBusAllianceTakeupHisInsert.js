var MfBusAllianceTakeupHisInsert = function(window, $) {
    var _init = function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });


    };
    // 保存
    var _insertAjax = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : dataParam
                },
                type : "POST",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.alert(data.msg, 1);
                        myclose_click();
                    }
                    if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }
            });
        }
    };
    //更新按钮
    var _updateAjax = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:"POST",
                dataType:"json",
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag == "success"){
                        alert(data.msg,1);
                        myclose_click();
                    }
                    if(data.flag == "error"){
                        alert(data.msg,0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                },
                complete:function(){
                    LoadingAnimate.stop();
                }
            });
        }
    };
    var _myclose = function(){
        myclose_click();
    };
    return {
        init : _init,
        insertAjax : _insertAjax,
        updateAjax:_updateAjax,
        myclose : _myclose
    };
}(window, jQuery);