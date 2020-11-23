;
var MfBusReceTransfer_List = function(window, $) {
    var _init = function(){
        $(".scroll-content").mCustomScrollbar({
            advanced:{
                updateOnContentResize:true
            }
        });
    };

    //账款转让
    var _insertReceTransInfo = function(){
        top.addFlag = false;
        top.htmlStr = $("#accountList").html();
        top.openBigForm(webPath+"/mfBusReceTransfer/input?pactId="+pactId+"&transMainId="+transMainId,'账款转让',function() {
            if(top.addFlag){
                $("#accountList").html(top.htmlStr);
            }
        });
    };


    var _getDetailPage = function(obj,url){
        top.openBigForm(url,'账款转让详情',myclose);
    };


    //保存转让主表
    var _insertReceTransMainAjax = function(obj){
        //校验账款数量
        if($("#accountList tbody tr").length==0){
            alert(top.getMessage("FIRST_OPERATION","账款转让"),0);
            return false;
        }
        // 要件必传验证
        if(!validateDocMustInput(obj)){
            return false;
        };

        $.ajax({
            url : webPath+"/mfBusReceTransferMain/insertAjax",
            data:{pactId:pactId},
            success:function(data){
                if(data.flag=="success"){
                    if(data.ifApproval=="1"){
                        alert(data.msg,2,function () {
                            _submitProcess(data.transMainId);
                        });
                    }else{
                        alert(data.msg,3);
                    }
                }else{
                    alert(data.msg,0);
                }
            }
        });
    };

    var _updateReceTransMainAjax = function(){
        $.ajax({
            url : webPath+"/mfBusReceTransferMain/updateAjax",
            data:{pactId:pactId,transMainId:transMainId},
            success:function(data){
                if(data.flag=="success"){
                    if(data.ifApproval=="1"){
                        alert(data.msg,2,function () {
                            _submitProcess(data.transMainId);
                        });
                    }else{
                        alert(data.msg,3);
                    }
                }
            }
        });
    };

    //提交审批流程
    var _submitProcess = function(transMainId){
        $.ajax({
            url : webPath+"/mfBusReceTransferMain/submitProcess",
            data:{pactId:pactId,transMainId:transMainId},
            success:function(data){
                if(data.flag=="success"){
                    // alert(data.msg,3);
                    $("#accountList").html(data.htmlStr);
                    DIALOG.msg(data.msg,function () {
                        window.location.href = webPath+"/mfBusReceTransferMain/getListPage";
                    })

                }
            }
        });
    };

    var _receTranBack = function(){
        window.location.href = webPath+"/mfBusReceTransferMain/getListPage";
    };

    return {
        init:_init,
        insertReceTransInfo:_insertReceTransInfo,
        insertReceTransMainAjax:_insertReceTransMainAjax,
        updateReceTransMainAjax:_updateReceTransMainAjax,
        getDetailPage:_getDetailPage,
        submitProcess:_submitProcess,
        receTranBack:_receTranBack,
    };
}(window, jQuery);
