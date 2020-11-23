;
var MfReceBusinessContractInfo_Insert = function(window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
    };

    //账款登记保存方法
    var _insertAjax = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam},
                success:function(data){
                    if(data.flag == "success"){
                        top.addBusContractFlag=true;
                        top.recePactId=data.recePactId;
                        myclose_click();
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        }
    };

     var _updatePactEndDate = function(){
        var beginDate =  $("input[name=recePactBeginDate]").val();
        //开始日期选择后，默认带出签约日期
        var term = $("input[name=term]").val();
        var termType = $("[name=termType]").val();
        $.ajax({
            url:webPath+"/mfReceBusinessContractInfo/getRecePactEndDateAjax",
            data:{"recePactBeginDate":beginDate,"term":term,"termType":termType},
            success:function(data){
                if(data.flag == "success"){
                    var recePactEndDate=data.recePactEndDate;
                    $("input[name=recePactEndDate]").val(recePactEndDate);
                    //选择合同开始日后，清除合同结束日中的不能为空提示
                    $("input[name=recePactEndDate]").parent().find(".error.required").remove();
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION",""),0);
            }
        });
    };


    return {
        init : _init,
        insertAjax:_insertAjax,
        updatePactEndDate:_updatePactEndDate,
    };
}(window, jQuery);