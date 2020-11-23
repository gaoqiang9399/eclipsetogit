;
var mfRequestPayoutDetail = function (window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
        _cen();
    };
    var _cen = function(){
        $(".myback").bind("click", function(event) {
            myclose_click();
        });
    };
    //更新请款单信息
    var _updateRequestPayoutBill = function() {
        url = webPath + "/mfRequestPayoutBill/inputUpdate?requestId=" + requestId;
        top.baseFlag = false;
        top.openBigForm(url,"请款单信息",rollback,"80","80");
    };
    //点击funds“+” 弹出新表单
    var _fundsadd = function(url) {
        if("undefined" == typeof url){
            url = webPath + "/mfRequestPayoutBill/inputDetail?requestId=" + requestId;
        }
        top.fundsFlag = false;
        top.openBigForm(url,"请款条例明细", function(){
            if(top.fundsFlag){
                $("#requestPayoutFunds").html(top.detailTableHtml);
                $("input[name=payoutTotalAmount]").val(top.payoutTotalAmount);
            }
        },"80","80");
    };
    //点击funds删除记录
    var _fundsdelete = function(obj,url) {
        window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            jQuery.ajax({
                url:url,
                type:"POST",
                dataType:"json",
                beforeSend:function(){
                },success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
                        $("#requestPayoutFunds").html(data.detailTableHtml);
                    }else if(data.flag == "error"){
                        alertFlag.Alert(data.msg);
                    }
                },error:function(data){
                    window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        });
    };

    //回调函数
    function rollback(){
        if(top.baseFlag ){
            if(top.mfOaArchivesBase!=""){
                $("select").removeAttr("disabled");
                _freshBaseDiv();
                _freshheadDiv("#headMessage [name]");
                $("#headMessage [name=education]").text(top.mfOaArchivesBase.education);
                $("#headMessage [name=birthday]").text(_formatDate(top.mfOaArchivesBase.birthday));
                $("#headMessage [name=email]").text(top.mfOaArchivesBase.email);
            }
        }
    }
    var _freshBaseDiv = function(obj) {
        $("#MfOaArchivesBaseAction").html(top.htmlStr);
        dblclickflag();
    };
    var _freshheadDiv = function(obj) {
        $(obj).each(function(){
            $(this).text(top.mfOaArchivesBase[$(this).attr("name")]);
        });
    };

    var _formatDate = function(startDate){
        if(startDate!=""){
            var date = startDate.substr(0,4)+"-"+startDate.substr(4,2)+"-"+startDate.substr(6,2);
            return date;
        }else{
            return ;
        }
    };

    return {
        init:_init,
        updateRequestPayoutBill:_updateRequestPayoutBill,
        fundsadd:_fundsadd,
        fundsdelete:_fundsdelete
    };
}(window,jQuery);