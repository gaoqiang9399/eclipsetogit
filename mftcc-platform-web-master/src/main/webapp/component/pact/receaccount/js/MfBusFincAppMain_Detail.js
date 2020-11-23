function getNextBusPoint(){
    $.ajax({
        url: webPath + "/mfBusFincAppMain/getMfBusFincMainInfoAjax?fincMainId="+fincMainId,
        success: function (resData) {
            var fincSts = resData.fincSts;
            appId = resData.appId;
            if(fincSts== '2'){//放款审批中
                var approveInfo="";
                var approveNodeName=resData.approveNodeName;
                var approvePartName=resData.approvePartName;
                if(approvePartName!=""&&approvePartName!=null){
                    approveInfo="正在"+approveNodeName+"岗位的"+approvePartName+"审批";
                }else{
                    approveInfo="正在"+approveNodeName+"岗位审批";
                }
                var busPointInfo="";
                if(approveInfo.length>40){
                    //提示内容长度大于40时，截取展示并添加鼠标title提示。
                    var approveTitleInfo=approveInfo;
                    approveInfo=approveInfo.substring(0, 40)+"...";
                    busPointInfo = "<span title="+approveTitleInfo+">"+approveInfo+"</span>";
                }else{
                    busPointInfo = "<span>"+approveInfo+"</span>";
                }
                $(".block-next").append(busPointInfo);
                $(".next-div").removeClass("hide");
                $(".next-div").addClass("show");
            }else if(fincSts == '3'){
                $(".block-next").append("<span>审批已被否决，业务结束</span>");
                $(".next-div").removeClass("hide");
                $(".next-div").addClass("show");
            }else {
                $.ajax({
                    url:webPath+"/mfBusFincAppMain/getTaskInfoAjax?wkfAppId="+wkfAppId+"&fincMainId="+fincMainId,
                    success:function(data){
                        var url = data.url;
                        var title=data.title;
                        var nodeName = data.nodeName;
                        var checkPmsBizFlag;
                        $(".block-next").empty();
                        $(".next-div").unbind();
                        if(data.wkfFlag!="0"){//流程未结束
                            if(fincSts=="4" && nodeName=="finc_approval"){//如果审批状态是通过，但是当前节点还是在合同审批阶段的话，需要手动触发一下业务流程的提交
                                checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("manual_submit");
                                if(checkPmsBizFlag){
                                    $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>业务提交>></a></span>");
                                    $(".block-next").click(function(){
                                        alert(top.getMessage("CONFIRM_OPERATION","业务提交"),2,function(){
                                            doCommitProcess();
                                        });
                                    });
                                    $(".next-div").removeClass("hide");
                                    $(".next-div").addClass("show");
                                }else{
                                    $(".block-next").append("<span>暂时没有权限操作业务提交</span>");
                                    $(".next-div").removeClass("hide");
                                    $(".next-div").addClass("show");
                                }
                            }else {
                                checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
                                if(checkPmsBizFlag){
                                    if(data.assign){
                                        $(".block-next").append("<span>暂时没有权限操作该节点</span>");
                                        $(".next-div").removeClass("hide");
                                        $(".next-div").addClass("show");
                                    }else if(data.isShow){
                                        $(".block-next").append("<span>"+title+"</span>");
                                        $(".next-div").removeClass("hide");
                                        $(".next-div").addClass("show");

                                    }else{
                                        $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+"&gt;&gt;</a></span>");
                                        $(".next-div").removeClass("hide");
                                        $(".next-div").addClass("show");
                                        $(".next-div").click(function(){
                                            toNextBusPoint(webPath+url,title,nodeName);
                                        });
                                    }
                                }else{
                                    $(".block-next").append("<span>暂时没有权限操作该节点</span>");
                                    $(".next-div").removeClass("hide");
                                    $(".next-div").addClass("show");
                                }
                            }
                            //放款申请节点、放款审批节点、还款计划节点，展示合同放款状态
                            if(nodeName=="putout_apply"||nodeName=="putout_approval"||nodeName=="review_finc"){
                                $('#putoutSts-div').show();
                            }
                        }else if(data.wkfFlag=="0"){//流程结束
                            $(".next-div").removeClass("show");
                            $(".next-div").addClass("hide");
                        }
                    }
                });

            }
        }
    });
}


//单独提交业务流程
function doCommitProcess(){
    $.ajax({
        url:webPath+"/mfBusFincAppMain/commitBusProcessAjax?fincMainId="+fincMainId,
        success:function(data){
            if(data.flag=="success"){
                alert(data.msg,1);
                getNextBusPoint();
                $("#wj-modeler1").empty();
                showWkfFlow($("#wj-modeler1"),wkfAppId);
            }else{
                alert(data.msg,0);
            }
        }
    });
}

//跳转至下一业务节点
function toNextBusPoint(url,title,nodeName){
    top.flag=false;//表示是否进行业务操作
    top.putoutFlag=false;//表示是否是放款申请节点
    top.putoutReviewFlag=false;//表示是否是放款复核节点
    top.getInfoFlag = false;//业务操作后表示是否需要获得信息
    top.pactUpdateFlag=false;//表示是否是合同签约节点
    top.repayReviewFlag=false;//表示是否是还款复核节点
    top.pactDetailInfo = "";
    top.putoutSaveFlag=false;//表示放款标识
    var tmpUrl=url.split("&")[0];
    var popFlag = tmpUrl.split("?")[1].split("=")[1];
    if(popFlag=="0"){
        alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
            if(!valiDocIsUp(pactId)){
                return false;
            }
            $.ajax({
                url:url,
                async:false,
                success:function(data){
                    if(data.flag=="success"){
                        if(data.node=="processaudit"){
                            if(data.processType == 'pact'){
                                pactSts = data.pactSts;
                            }
                            if(data.processType == 'finc'){
                                fincSts = data.fincSts;
                            }
                            window.top.alert(data.msg,3);
                        }else if(data.node == "join_zh"){
                            window.top.alert(data.msg,3);
                        }
                        getNextBusPoint();
                        $("#wj-modeler1").empty();
                        showWkfFlow($("#wj-modeler1"),wkfAppId);
                        initDocNodes();//重新初始化要件
                    }else{
                        alert(data.msg);
                    }
                }
            });
        });
    }else{
        //还款计划节点验证是否启用资金方
        if(nodeName=="review_finc"){
            $.ajax({
                url: webPath+"/mfBusAppKind/getByAppIdAjax",
                data:{appId:appId},
                async: false,
                success: function(data) {
                    if(data.flag=="success"){
                        var isFund=data.busAppKind.isFund;
                        if(isFund=="1"){
                            window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"已启用自动处理,","content2":"再手动操作"}), 3);
                        }else if(isFund=="0"){
                            top.window.openBigForm(url,title,wkfCallBack);
                        }
                    }else{
                        top.window.openBigForm(url,title,wkfCallBack);
                    }
                },error: function(XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
                }
            });
        }else{
            top.window.openBigForm(url,title,wkfCallBack);
        }
    }
}

//回调函数
function wkfCallBack(){
    if(top.flag){
        var tableHtml;
        //放款复核后，处理还款节点以及显示收款计划
        if(top.putoutReviewFlag){
            $(".next-div").removeClass("show");
            $(".next-div").addClass("hide");
            $("#repay").show();
            tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
                +'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
                +'</div>'
                +'<div class="list-table">'
                +'<div class="content">'
                + top.tableHtml
                +'</div>'
                +'</div>';
            $("#fincAppDetail").after(tableHtml);
        }
        if(top.getInfoFlag){
            fincSts = top.fincSts;
            if(top.showType != null){
                setBlock(top.showType,top.title,top.name,top.getInfoUrl);
            }
        }
        if(top.pactUpdateFlag){
            pactSts = top.pactSts;
            pubPactDetailInfo.init();
            if(pactSts=="4"){
                $('.btn-file-archive').removeClass('hidden');
            }
        }
        if( top.repayReviewFlag){//还款复核成功后处理
            $(".next-div").removeClass("show");
            $(".next-div").addClass("hide");
            tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
                +'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
                +'</div>'
                +'<div class="list-table">'
                +'<div class="content">'
                + top.tableHtml
                +'</div>'
                +'</div>';
            $("#fincAppDetail").after(tableHtml);
        }
        getNextBusPoint();
        $("#wj-modeler1").empty();
        showWkfFlow($("#wj-modeler1"),wkfAppId);
        initDocNodes();//重新初始化要件
    }
};
//跳转至客户的详情页面
function getCusInfo(cusNo){
    top.LoadingAnimate.start();
    window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance="+busEntrance+"&fincMainId="+fincMainId;
}

//验证文档是否上传
function valiDocIsUp(relNo){
    var flag = true;
    $.ajax({
        type: "post",
        dataType: 'json',
        url: webPath+"/docBizManage/valiWkfDocIsUp",
        data:{relNo:relNo},
        async: false,
        success: function(data) {
            if(!data.flag){
                window.top.alert(data.msg,0);
            }
            flag = data.flag;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
        }
    });
    return flag;
};

//多业务大于3条时，弹层列表页面
function getMultiBusList(flag){
    if('apply'==flag){
        top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中业务",function(){});
    }else if('pact'==flag){
        top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行合同",function(){});
    }else if('finc'==flag){
        top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行借据",function(){});
    }else if('assure'==flag){
        top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"担保项目",function(){});
    }
}
;
var  MfBusFincAppMain_Detail = function(window,$){
	var _init = function(){
        /**滚动条**/
        $("body").mCustomScrollbar({
            advanced:{
                updateOnContentResize:true
            },
            callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
                whileScrolling: function(){
                    if ($(".changeval").length>0) {
                        $(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
                    }
                }
            }
        });
        getNextBusPoint();
        top.LoadingAnimate.stop();
	};

    return {
        init:_init,
	};
}(window, jQuery);