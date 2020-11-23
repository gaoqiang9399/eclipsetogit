;
var MfBusFincApp_BatchRepayment = function(window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        // // 初始化客户类型
        // $("input[name=fincNo]").popupSelection( {
        //     searchOn : true,// 启用搜索
        //     inline : false,// 下拉模式
        //     multiple : true,// 单选
        //     items : fincNoArray ,
        //     changeCallback : function(elem) {
        //         var fincNo = elem.data("values").val();
        //         // 初始化客户类型
        //        _selectFincBack(fincNo);
        //     }
        // });
    };
    //选择客户
    var _selectCusDialog=function(){
        selectCusDialog(_selectCusBack,"","","12");
    };
    //选择借据
    var _selectFincDialog=function(){
        var cusNo =  $("input[name=cusNo]").val();
        if(cusNo!=undefined&&cusNo!=''){
            var ajaxUrl = webPath+"/mfBusFincApp/getFincSelectByCus?cusNo="+cusNo;// 请求数据URL;
            $("input[name=fincName]").parent().find('div').remove();
            $("input[name='fincName']").popupList({
                searchOn : true, // 启用搜索
                multiple : true, // false单选，true多选，默认多选
                ajaxUrl : ajaxUrl,
                valueElem : "input[name='fincNo']",// 真实值选择器
                title : "选择借据",// 标题
                labelShow : false,
                changeCallback : function(elem) {// 回调方法
                    BASE.removePlaceholder($("input[name='fincName']"));
                    // var sltVal = elem.data("selectData");
                    // $("input[name='fincNo']").val(sltVal.fincShowId);
                    // $("input[name='fincName']").val(sltVal.fincId);
                    $('.hidden-content').find('div[class=pops-value]').remove();
                    _selectFincBack();
                },
                tablehead : {// 列表显示列配置
                    "fincShowId" : {"disName":"借据展示号","align":"center"},
                    "loanBal1" : {"disName":"借据余额(元)","align":"right"},
                },
                returnData : {// 返回值配置
                    disName : "fincShowId",// 显示值
                    value : "fincId"// 真实值
                }
            });
            $('input[name="fincName"]').next().click();
        }else{
            alert("请先选择客户",0);
        }
    };
    var _selectFincBack = function () {
        // 根据客户号获取客户剩余借款的信息
        var  fincNo = $("input[name='fincNo']").val();
        $("#fincList").html("");
        $.ajax({
            url : webPath+"/mfBusFincApp/getFincInfoByFincNo",
            data:{fincNo:fincNo},
            dataType:'json',
            type:'post',
            success : function(data) {
                var fincHtmlList =data.fincHtmlList;
                $.each(fincHtmlList, function (index, obj) {
                    var htmlStr = '<div class="list-table"><div class="title"><span>'+obj.mfBusFincApp.fincShowId+'</span></div><div id="'+obj.mfBusFincApp.fincId+'List"></div> </div>'
                    $("#fincList").append(htmlStr);
                    $("#"+obj.mfBusFincApp.fincId+"List").append(obj.htmlStr);
                });
                $.each($(".ls_list"), function (index, obj) {
                    $(obj).find( "tbody tr:eq(0) td input").attr("readonly","readonly");
                });
            }
        });

    }

    //选择客户后选择借据
    var _selectCusBack=function(cus){
        var cusNo = cus.cusNo;
        $("input[name=cusNo]").val(cusNo);
        $("input[name=cusName]").val(cus.cusName);
        $.ajax({
            url : webPath+"/mfBusFincApp/getLoanInfoByCusNo?cusNo="+ cusNo,
            success : function(data) {
                if (data.loanFlag == '1') {// 全部都填写了
                    $("input[name=pactNum]").val(data.pactNum);
                    $("input[name=pactAmtSum]").val(data.pactAmt);
                    $("input[name=loanBalSum]").val(data.loanBalSum);
                    // $("input[name=popsfincNo]").popupSelepre_credit_sumction("updateItems",data.fincList);
                } else if (data.loanFlag == '0') {
                    $("input[name=cusNo]").val("");
                    $("input[name=cusName]").val("");
                    alert("当前客户不存在履行中的借款",0);
                }
            }
        });
    };
    //选择日期
    var _selectRepayDateBack=function(){
        var fincNo = $("input[name='fincNo']").val();
        var repayDate = $("input[name='repayDate']").val();
        $.ajax({
            url : webPath+"/mfBusFincApp/getFincInfoByFincNo",
            data:{fincNo:fincNo,repayDate:repayDate},
            dataType:'json',
            type:'post',
            success : function(data) {
                var fincHtmlList =data.fincHtmlList;
                $("#fincList").html("");
                $.each(fincHtmlList, function (index, obj) {
                    var htmlStr = '<div class="list-table"><div class="title"><span>'+obj.mfBusFincApp.fincShowId+'</span></div><div id="'+obj.mfBusFincApp.fincId+'List"></div> </div>'
                    $("#fincList").append(htmlStr);
                    $("#"+obj.mfBusFincApp.fincId+"List").append(obj.htmlStr);
                });
                $.each($(".ls_list"), function (index, obj) {
                    $(obj).find( "tbody tr:eq(0) td input").attr("readonly","readonly");
                });
            }
        });
    };
    var _repayAmtChange = function (){
        var repayAmt =$("input[name='repayAmt']").val();
        var repayDate =$("input[name='repayDate']").val();
        if(repayAmt!=undefined&&repayAmt!=''){
            if(repayDate!=''){
                repayAmt = repayAmt.replace(/,/g,'');;
                _checkAmtOneByOne(repayAmt);
            }else{
                alert('请先选择还款时间',0);
            }

        }
    }
    /**
     * 按照还款顺序 计算相关金额
     *
     * @param paySum
     *            实还总额
     * @author WD
     */
    var _checkAmtOneByOne = function (paySum) {
        var tmpSum = paySum;// 实收总额
        var ifSetZero = false;// 是否为0
        var tmpAmt = 0;// 每项应还金额
        var fincNo = $("input[name='fincNo']").val();
        var fincNoArr =fincNo.split("|");
        for (var  j = 0; j < fincNoArr.length; j++) {
            var fincNoShow = fincNoArr[j];
            if(fincNoShow==''){
                continue;
            }
            var objTr=$("#"+fincNoShow+"List").find("tbody tr:eq(1)");
            var fincId = objTr.find("input[name='fincId']").val();// 获取期号
            //获取还款顺
            var payOrder ="";
            $.ajax({
                url : webPath+"/mfBusFincApp/getRepayOrder",
                data:{fincId:fincId},
                dataType:'json',
                type:'post',
                async:false,
                success : function(data) {
                    payOrder = data.repaymentOrderStr;
                    var orderAry = payOrder.split(",");// 还款顺序
                    // "YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin";//代表还款顺序
                    // 应该取传过来的值 YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,FeiYongFaXi,BenJin
                    var orderLen = orderAry.length;
                    for ( var i = 0; i < orderLen; i++) {
                        // 每项应还金额
                        var str = orderAry[i];
                        str =  str.replace(str[0],str[0].toLowerCase());
                        tmpAmt =objTr.parent().find("tr:eq(0)").find("input[name='"+str+"']").val();
                        if(tmpAmt!=undefined&&tmpAmt!=''){
                            tmpSum = CalcUtil.subtract(tmpSum, tmpAmt);
                            if (ifSetZero) {
                                objTr.find("input[name='"+str+"']").val(0.00);
                            } else {
                                if (tmpSum <= 0) {
                                    //因为tmpSum是负数或者0，因此这里用 add
                                    var spicalVal = CalcUtil.add(tmpAmt, tmpSum);
                                    objTr.find("input[name='"+str+"']").val(spicalVal);
                                    ifSetZero = true;
                                } else {// 还是原来的 每项应还的金额
                                    objTr.find("input[name='"+str+"']").val(tmpAmt);
                                }
                            }
                        }
                    }

                }
            });
        }

        // $(".ls_list").find("tbody tr:eq(1)").each(function() {
        //    var fincId = $(this).find("input[name='fincId']").val();// 获取期号
        //     var objTr=$(this);
        //     //获取还款顺
        //     var payOrder ="";
        //     $.ajax({
        //         url : webPath+"/mfBusFincApp/getRepayOrder",
        //         data:{fincId:fincId},
        //         dataType:'json',
        //         type:'post',
        //         success : function(data) {
        //             payOrder = data.repaymentOrderStr;
        //             var orderAry = payOrder.split(",");// 还款顺序
        //             // "YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin";//代表还款顺序
        //             // 应该取传过来的值 YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,FeiYongFaXi,BenJin
        //             var orderLen = orderAry.length;
        //             for ( var i = 0; i < orderLen; i++) {
        //                 // 每项应还金额
        //                 var str = orderAry[i];
        //                 str =  str.replace(str[0],str[0].toLowerCase());
        //                 tmpAmt =objTr.parent().find("tr:eq(0)").find("input[name='"+str+"']").val();
        //                 if(tmpAmt!=undefined&&tmpAmt!=''){
        //                     tmpSum = CalcUtil.subtract(tmpSum, tmpAmt);
        //                     if (ifSetZero) {
        //                         objTr.find("input[name='"+str+"']").val(0.00);
        //                     } else {
        //                         if (tmpSum <= 0) {
        //                             //因为tmpSum是负数或者0，因此这里用 add
        //                             var spicalVal = CalcUtil.add(tmpAmt, tmpSum);
        //                             objTr.find("input[name='"+str+"']").val(spicalVal);
        //                             ifSetZero = true;
        //                         } else {// 还是原来的 每项应还的金额
        //                             objTr.find("input[name='"+str+"']").val(tmpAmt);
        //                         }
        //                     }
        //                 }
        //             }
        //
        //         }
        //     });
        //
        // });
    }
    var _batchRepayment = function (formObj) {
        var flag = submitJsMethod($(formObj).get(0), '');
        var zongJi =0;
        var repayList = [];
        $(".ls_list").find("tbody tr:eq(1)").each(function(){
            var eachAmt =0;
            var tdArr = $(this);
            var fincId = tdArr.find("input[name='fincId']").val();
            var benJin = tdArr.find("input[name='benJin']").val();
            var liXi = tdArr.find("input[name='liXi']").val();
            var yuQiLiXi = tdArr.find("input[name='yuQiLiXi']").val();
            var feiYong = tdArr.find("input[name='feiYong']").val();
            var repayInfo = new Object();
            eachAmt =CalcUtil.add(eachAmt, benJin);
            eachAmt =CalcUtil.add(eachAmt, liXi);
            eachAmt =CalcUtil.add(eachAmt, yuQiLiXi);
            eachAmt =CalcUtil.add(eachAmt, feiYong);
            if(eachAmt>0){
                repayInfo.fincId = fincId;
                repayInfo.benJin = benJin;
                repayInfo.liXi = liXi;
                repayInfo.yuQiLiXi = yuQiLiXi;
                repayInfo.feiYong = feiYong;
                repayList.push(repayInfo);
            }
            zongJi = CalcUtil.add(zongJi, eachAmt);
        });

        if(flag){
            var repayAmt =$("input[name='repayAmt']").val();
            repayAmt = repayAmt.replace(/,/g,'');;
            if(repayAmt!=zongJi){
                alert('还款总额和下面填写的金额累计不一致',0);
            }
            var url = $(formObj).attr("action");
            var jsonString = JSON.stringify(repayList);
            var dataForm = JSON.stringify($(formObj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataForm,ajaxList:jsonString},
                type:"post",
                dataType:"json",
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag == "success"){
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        alert(data.msg,0);
                    }
                },
                error:function(data){
                    LoadingAnimate.stop();
                }
            });
        }
    }

    var _trial = function () {
        $("#fincList").css('display','block');
        _repayAmtChange();
    }
    return {
        init : _init,
        selectCusDialog : _selectCusDialog,
        selectRepayDateBack : _selectRepayDateBack,
        repayAmtChange : _repayAmtChange,
        batchRepayment : _batchRepayment,
        selectFincDialog : _selectFincDialog,
        trial:_trial,
    };
}(window, jQuery);