;
var MfBusAdvanceLoan=function(window,$){
    var _init = function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });

    };

    //打开提前还款表单
    var _openAdvanceLoan=function(){
        top.window.openBigForm(webPath+"/mfBusAdvanceLoan/openAdvanceLoan?appId="+appId+"&pactId="+pactId, '提前放款', function(){
            if(top.advanceLoanFlag){
                $("#MfBusPact_DynaDetail_advance_button").attr("disabled","disabled").addClass("btn-opt-dont");
                //显示审批记录
                $("#advanceLoanInfo-block").show();
                showWkfFlowVertical($("#advanceLoanInfo-modelerrece"), top.advanceLoanId, "","advance-loan");

            }
        });
    };

    //提前放款提交
    var _insertAdvanceLoan=function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            alert(top.getMessage("CONFIRM_OPERATION", "提前放款申请"), 2, function() {
                var dataParam = JSON.stringify($(obj).serializeArray());
                var url = $(obj).attr("action");
                $.ajax({
                    url:url,
                    data:{ajaxData : dataParam},
                    success:function(data){
                        if(data.flag=="success"){
                            top.advanceLoanFlag=true;
                            top.advanceLoanId=data.advanceLoanId;
                            window.top.alert(data.msg, 3,function(){
                                myclose_click();
                            });
                        }else if(data.flag=="error"){
                            window.top.alert(data.msg,0);
                        }
                    },
                    error:function(data){
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                });
            });
        };
    };

    var _submitForm = function(obj) {        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            commitProcess(url+"?advanceLoanId="+advanceLoanId,obj,'sp');
        }
    };

    return{
        init:_init,
        openAdvanceLoan:_openAdvanceLoan,
        insertAdvanceLoan:_insertAdvanceLoan,
        submitForm:_submitForm
    };
}(window,jQuery);