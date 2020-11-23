;
var MfBusApply_RiskManagementYYResult = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    };



    //更新操作
    var _selectAjax = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url:webPath+"/apiReturnRecord/getThirdAjax",
                data:{
                    ajaxData : dataParam,
                    type : type,
                    cusNo : cusNo
                },
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        alert(top.getMessage("ERROR_INSERT"),0);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            });
        }
    };
    var _myclose = function(){
        myclose_click();
    };
    return{
        init:_init,
        myclose : _myclose,
        selectAjax:_selectAjax,
    };
}(window,jQuery);