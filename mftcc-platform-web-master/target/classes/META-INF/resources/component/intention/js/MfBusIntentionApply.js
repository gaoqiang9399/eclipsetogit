;
var MfBusIntentionApply = function(window, $) {

    var _showApplyDetail = function (obj,param) {

        top.window.openBigForm(webPath+"/mfBusIntentionApply/showApplyDetail"+param,"申请意向详情",function(){
        });
    };

    var _creditApply = function () {
        var showType = "1";
        var cusNo = $("[name='cusNo']").val();
        top.openBigForm(webPath + "/mfCusCreditApply/creditInitInput?showType=" + showType+"&cusNo="+cusNo, "授信申请", function () {

        });
    }

    return {
        showApplyDetail:_showApplyDetail,
        creditApply:_creditApply
    };
}(window, jQuery);
