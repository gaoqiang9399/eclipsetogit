;
var MfCus_RiskManagement = function(window, $) {
    var _init = function(){
        $(".scroll-content").mCustomScrollbar({
            advanced:{
                //滚动条根据内容实时变化
                updateOnContentResize:true
            }
        });
    };


//获取企业关系人列表
var _getcusHighList=function (){
    $.ajax({
        url: webPath + "/apiReturnRecord/getCorpPartyList",
        data: {cusNo: cusNo},
        type: "POST",
        dataType: "json",
        beforeSend: function () {
        }, success: function (data) {
            LoadingAnimate.stop();
            if (data.flag == "success") {
                $("#corpParty").html(data.tableHtml);
            }
        }
    })
}


//获取核查结果列表
    var _getRiskOutcomeList=function (){
        $.ajax({
            url: webPath + "/apiReturnRecord/getRiskOutcomeList",
            data: {cusNo: cusNo},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    $("#riskVerification").html(data.tableHtml);
                }
            }
        })
    }
    //同盾核查
    var _tongduCheck=function (obj,url) {
        $.ajax({
            url: webPath+ url+"&cusNo="+cusNo,
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    alert(data.msg,1);
                    _getRiskOutcomeList();
                }else {
                    alert(data.msg,0);
                }
            },error : function() {
                alert(top.getMessage("FAILED_OPERATION","同盾核查"),0);
            }
        })
    }

    //打开风险查询结果
    var _openResultHtml=function (obj,url) {
        top.window.openBigForm( webPath+ url,"风险查询结果",function(){
        });
    }



    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        openResultHtml:_openResultHtml,
        tongduCheck:_tongduCheck
    };
}(window, jQuery);