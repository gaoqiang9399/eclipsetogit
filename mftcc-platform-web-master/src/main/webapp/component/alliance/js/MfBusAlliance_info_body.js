;
//联保体详情信息
var allianceDetailInfo = function(window, $) {
    //正常初始化
    var _init = function (){

        $.ajax({
            url:webPath+"/mfBusAlliance/getAllianceDetailFormAjax?id="+allianceId+"&formId="+allianceFormId,
            type:'post',
            dataType:'json',
            success:function(data){
                var html = data.htmlStr;
                $("#pactDetailForm").empty().html(html);
            }
        });
    };
    var _selectCus=function () {
        selectCusDialog(_getCusDialog,null,null,1);
    };
    var _getCusDialog = function (customer) {
        $("input[name=allianceLeaderId]").val(customer.cusNo);
        $("input[name=allianceLeaderName]").val(customer.cusName);
        $("input[name=contactInformation]").val(customer.cusTel);

    };
    return {
        init : _init,
        selectCus:_selectCus
    };
}(window, jQuery);

