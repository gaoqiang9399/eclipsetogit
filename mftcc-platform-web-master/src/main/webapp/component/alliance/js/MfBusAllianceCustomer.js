;
var MfBusAllianceCustomer = function(window, $) {
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
        myclose : _myclose,
        update : _updateAjax
    };
}(window, jQuery);