;
var MfReportQueryConditionConfig = function(window, $) {

    var _input = function(){
        window.location.href=webPath+"/mfReportQueryConditionConfigSet/input";
    };
    //查看详情 跳转页面
    var _getDetailPage = function (obj,url){
        top.LoadingAnimate.start();
        window.location.href=webPath+url;
    };
    //返回报表查询条件列表
    var _cancelReportCondition=function(){
        window.location.href =webPath+"/mfReportQueryConditionConfigSet/getListPage";
    };
    var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:"POST",
                dataType:"json",
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag == "success"){
                        alert(data.msg,1);
                       // myclose_click();
                        _cancelReportCondition();
                    }else if(data.flag == "error"){
                        alert(data.msg,0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                },complete:function(){
                    LoadingAnimate.stop();
                }
            });
        }
    };
    //demo编辑
    var _ajaxUpdate = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:"POST",
                dataType:"json",
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag == "success"){
                        alert(data.msg,1);
                       // myclose_click();
                        _cancelReportCondition();
                    }else if(data.flag == "error"){
                        alert(data.msg,0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                },complete:function(){
                    LoadingAnimate.stop();
                }
            });
        }
    };
//验证报表编号report_id的唯一性
    var _validateReportId=function(){
        var reportId=$("[name=reportId]").val();
        if(reportId==""){
            return;
        }
        $.ajax({
            url:webPath+'/mfReportQueryConditionConfigSet/getByIdAjax',
            data:{reportId:reportId},
            dataType:'json',
            async:false,
            type:'POST',
            success:function(data){
                if(data.flag=="success"){
                        $("input[name=reportId]").val("");
                        alert(top.getMessage("EXIST_INFORMATION_EVAL","报表编号"),3);
                }
            }
        });
    };
    /**
     * 在return方法中声明公开接口。
     */
    return {
       input : _input,
       ajaxInsert:_ajaxInsert,
       ajaxUpdate:_ajaxUpdate,
       cancelReportCondition:_cancelReportCondition,
       getDetailPage: _getDetailPage,
       validateReportId:_validateReportId

    };
}(window, jQuery);