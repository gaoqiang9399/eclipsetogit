;

//成员列表
var takeupHis = function(window, $) {
    //正常初始化
    var _init = function (){
        $.ajax({
            url: webPath+'/mfBusAlliance/getTakeupListAjax?allianceId='+allianceId+"&tableId="+allianceTakeupHisFormId,
            type:'post',
            dataType:'json',
            success:function(data){
                var $html = $(data.tableData);
                $(".content[name=takeupHis]").html(data.tableData);
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
    return {
        init : _init
    };
}(window, jQuery);



