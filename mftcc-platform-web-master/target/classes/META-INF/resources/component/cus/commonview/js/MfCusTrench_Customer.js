;

//成员列表
var MfCusTrench_Customer = function(window, $) {
    //正常初始化
    var _init = function (){
        $.ajax({
            url: webPath+'/mfBusTrench/getCustomerListAjax?trenchId='+trenchId,
            type:'post',
            dataType:'json',
            success:function(data){
                var $html = $(data.tableData);
                $(".content[name=putoutHis]").html(data.tableData);
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };


    return {
        init : _init
    };
}(window, jQuery);



