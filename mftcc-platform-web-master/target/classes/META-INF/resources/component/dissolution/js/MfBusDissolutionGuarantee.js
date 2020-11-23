;
var MfBusDissolutionGuarantee = function(window, $) {


    var _getDetailPage = function(obj,url){
        top.LoadingAnimate.start();
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        window.location.href=url;
        event.stopPropagation();
    };

    //解保信息登记页面
    var _openInputPage=function (obj,param) {
        top.window.openBigForm(webPath+"/mfBusDissolutionGuarantee/input"+param,"解保登记",function(){
            window.location.href = webPath + "/mfBusDissolutionGuarantee/getListPage";
        });
    };

    //解保历史详情页面
    var _openDetailPage=function (obj,param) {
        top.window.openBigForm(webPath+"/mfBusDissolutionGuarantee/detail"+param,"解保详情",function(){
        });
    };

    //登记解保信息
    var _insert=function (dom) {
        var flag = submitJsMethod($(dom).get(0), '');
        if(flag){
            var url = $(dom).attr("action");
            var dissolutionType = $("select[name='dissolutionType']").val();
            var realPutoutAmt = $("input[name='realPutoutAmt']").val();
            var repayAmt = $("input[name='repayAmt']").val();
            if(realPutoutAmt != repayAmt && dissolutionType === '2'){
                alert("该合同还款未完成，不能选择提前解保！",0);
                return;
            }

            if(repayAmt == 0.00 && (dissolutionType === '1' || dissolutionType === '3')){
                alert("该合同还未放款，只能选择提前解保！",0);
                return;
            }

            if(realPutoutAmt == repayAmt && dissolutionType === '3'){
                alert("该合同还款已完成或没放款，不能选择代偿解保！",0);
                return;
            }
            var msg = "您确定此合同要解保吗？";
            if(dissolutionType ==='2'){//提前解保，获取退费信息进行提示
                var pactId = $("input[name='pactId']").val();
                var dissolutionDate = $("input[name='dissolutionDate']").val();
                $.ajax({
                    url:webPath+"/mfBusDissolutionGuarantee/calculateRefund",
                    data:{pactId : pactId,
                        dissolutionDate:dissolutionDate
                        },
                    type:'post',
                    dataType:'json',
                    async:false,
                    success : function(data) {
                        if (data.flag == "success") {
                            msg ="提前解保此合同预计退担保费:"+data.amt+"元，是否继续操作？";
                        }
                    },
                    error : function() {
                        LoadingAnimate.stop();
                        alert(top.getMessage("ERROR_REQUEST_URL", url));
                    }
                });
            }
            alert(msg,2,function(){
                var dataParam = JSON.stringify($(dom).serializeArray());
                LoadingAnimate.start();
                $.ajax({
                    url:url,
                    data : {
                        ajaxData : dataParam
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            alert(data.msg, 1);
                            $(top.window.document).find(".dhccModalDialog").eq($(top.window.document).find(".dhccModalDialog").length-1).find(".i-x5").click();
                        } else {
                            window.top.alert(data.msg, 0);
                        }
                    },
                    error : function() {
                        LoadingAnimate.stop();
                        alert(top.getMessage("ERROR_REQUEST_URL", url));
                    }
                });
            });
        }
    };


    /**
     * 在return方法中声明公开接口。
     */
    return {
        getDetailPage:_getDetailPage,
        openInputPage:_openInputPage,
        openDetailPage:_openDetailPage,
        insert:_insert
    };
}(window, jQuery);
