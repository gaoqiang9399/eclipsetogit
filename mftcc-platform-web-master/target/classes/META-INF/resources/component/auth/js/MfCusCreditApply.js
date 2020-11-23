var mfCusCreditApply= function(window,$){
    //检查授信业务流程是否完成
    var _checkWkfEndSts=function(){
        $.ajax({
            url : webPath+"/mfCusCreditApply/checkWkfEndSts",
            type : 'post',
            dataType : 'json',
            data : {
                wkfAppId:wkfAppId,
                cusNo : cusNo
            },
            success : function(data) {
                if(data.status == "0") {//流程未结束

                }else{

                }
            }
        });
    };
    //检查客户的基本信息、财务报表、评级是否完成
    var _checkCusBus=function(){
        $.ajax({
            url : webPath+"/mfCusCustomer/checkCusBus",
            data : {
                cusNo : cusNo,
                cusType : cusType
            },
            type : "post",
            dataType : "json",
            success : function(data) {
                if (data.fullFlag == "0") {//检查不通过

                }
            }
        };
        var _creditApply=function(){

        };
        var _getNextBusPoint=function(){
            $.ajax({
                url : webPath+"/mfCusCreditApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    var wkfFlag = data.wkfFlag;
                    if (wkfFlag == '0') {
                        $(".bg-danger").removeClass("show");
                        $(".bg-danger").addClass("hide");
                    } else if (wkfFlag == '1') {
                        var url = data.url;
                        var title = data.title;
                        var nodeName = data.nodeName;
                        $(".block-next").empty();
                        $(".next-div").unbind();
                        if ((data.creditSts == "2" || data.creditSts == "3") && nodeName == "credit_approval") { // 审批环节
                            $(".block-next").append("<span>业务在" + title + "阶段</span>");
                            $(".next-div").removeClass("hide");
                            $(".next-div").addClass("show");
                            $("#showWkf").removeClass("hide");
                            $("#showWkf").addClass("show");
                        } else {
                            $(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
                            $(".next-div").removeClass("hide");
                            $(".next-div").addClass("show");
                            $("#showWkf").removeClass("hide");
                            $("#showWkf").addClass("show");
                            $(".next-div").click(function() {
                                //toNextBusPoint(url, title, nodeName, wkfAppId);
                                toNextBusPoint(url, title, nodeName);
                            });
                        }
                    }
                }
            });
        };
        var _toNextBusPoint=function(url, title, nodeName){
            var tmpUrl = url.split("&")[0];
            var popFlag = tmpUrl.split("?")[1].split("=")[1];
            if (popFlag == "0") {// popFlag=1打开新窗口 popFlag=0反之
                if (nodeName == "credit_approval") {
                    alert(top.getMessage("CONFIRM_OPERATION",title), 2, function() {
                        LoadingAnimate.start();
                        $.ajax({
                            url : tmpUrl,
                            type : 'post',
                            dataType : 'json',
                            data : {
                                cusNo : cusNo,
                                wkfAppId : wkfAppId
                            },
                            success : function(data) {
                                LoadingAnimate.stop();
                                if (data.flag == "success") {
                                    if (data.node == "processaudit") {
                                        window.top.alert(data.msg, 3);
                                        //实时更新授信状态
                                        $.ajax({
                                            url : webPath+"/mfCusCreditApply/getCreditStsInfo",
                                            data : {
                                                wkfAppId : wkfAppId
                                            },
                                            type : 'post',
                                            dataType : 'json',
                                            success : function(data) {
                                                var status = data.status;
                                                var creditSum = data.creditSum;
                                                var applySum = data.applySum;
                                                getCreditSts(status,creditSum,applySum);
                                            },
                                            error : function() {
                                                alert(data.msg, 0);
                                            }
                                        });
                                        getNextBusPoint();
//									showWkfFlow(wkfAppId);
                                        $("#wj-modeler1").empty();
                                        showWkfFlow($("#wj-modeler1"),wkfAppId);
                                    } else {
                                        getNextBusPoint();
//									showWkfFlow(wkfAppId);
                                        $("#wj-modeler1").empty();
                                        showWkfFlow($("#wj-modeler1"),wkfAppId);
                                    }
                                } else {
                                    alert(data.msg);
                                }
                            }
                        });
                    });
                }
            } else {
                top.creditFlag=false;
                if (nodeName == "collateral") { // 担保信息
                    top.addCreditCollaFlag=false;
                    top.creditAppId="";
                    top.window.openBigForm(url, title, toCollateralDetail, "80", "80");
                }
                if (nodeName == "protocolPrint") {// 授信协议
                    var url = webPath+"/mfCusCreditApply/protocolPrint?wkfAppId=" + wkfAppId;
                    top.window.openBigForm(url, title, wkfCallBack, "80", "80");
                }
                if (nodeName == "report") { // 尽职报告
                    var url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=1";
                    top.openBigForm(url, "授信尽调报告", wkfCallBack);
                }
            }
        }
        return{
            creditApply:_creditApply
        };
    }(window,jQuery);