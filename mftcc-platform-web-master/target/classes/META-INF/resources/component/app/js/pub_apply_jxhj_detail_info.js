var pubApplyJxhjDetailInfo = function(window, $) {
    //正常初始化
    var _init = function (tableId,appId){
        var url = webPath + "/mfBusApply/findFincOldAjax";//查找客户借据关联旧订单
        var data={
            "tableId":tableId,
            "appId":appId,
            "tableType":"tableTag"
        };
        //// debugger
        $.ajax({
            url:url,//查找客户借据关联旧订单
            data:data,
            type:'post',
            dataType:'json',
            success:function(dataReturn){
                var $html = $(dataReturn.tableData);
                $("#fincOldDetail").html(dataReturn.tableData);
                if($("#fincOldDetail").find("table #tab tr").length>0){
                    $('#finc-old-list').show();
                }else{
                    $('#finc-old-list').hide()
                }
                 $("#finc-list").css("text-align","left");
                 $("#fincOldDetail").css("text-align","left");
            },
        });
    };
    return {
        init : _init
    };
}(window, jQuery);