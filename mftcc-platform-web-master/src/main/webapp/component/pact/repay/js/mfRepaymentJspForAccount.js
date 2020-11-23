;
var MfRepaymentJspForAccount = function(window,$){
    var _init = function(){
       /* $("#plan_content").html(ajaxData.tableHtml);
        $("#tablist").show();*/
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
    }
    var _repayDateChange = function () {
        $("input[name=benjinAmt]").val("");
        $("input[name=shishouLiXi]").val("");
        $("input[name=breakAmt]").val("");
        $("input[name=discountAmt]").val("");
    }
    var  _buyRepayDate= function() {
        if(repayDate!=''){
            fPopUpCalendarDlg({min: '${mfRepaymentBean.shangCiHuanKuanRiQi}',max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});
        }else {
            fPopUpCalendarDlg({min: new Date().toLocaleDateString(),max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});

        }
    }
    var _repayAmtChange = function () {
        var benjinAmt = $("input[name=benjinAmt]").val();
        var fincLoanBal = $("input[name=fincLoanBal]").val();
        var repayDate = $("input[name=repayDate]").val();
        var intstEndDate = $("input[name=intstEndDate]").val();
        var fincId = $("input[name=fincId]").val();
        if(repayDate<intstEndDate){
            benjinAmt =benjinAmt.replace(",","");
            fincLoanBal = fincLoanBal.replace(",","");
            if(CalcUtil.subtract(benjinAmt,fincLoanBal)<0){
                $.ajax({
                    url:webPath+"/mfRepayment/doCalcLiXiTiQianHuanKuan",
                    data:{fincId:fincId,repayDate:repayDate,tiQianHuanBen:benjinAmt},
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        top.flag = true;
                        if(data.flag == "success"){
                            var shiShouLiXi = data.shiShouLiXiFormat;
                            var tiQianHuanKuanWeiYueJin = data.tiQianHuanKuanWeiYueJinFormat;
                            yiShouLiXi = shiShouLiXi.replace(",","");
                            $("input[name=shishouLiXi]").val(shiShouLiXi);
                            $("input[name=breakAmt]").val(tiQianHuanKuanWeiYueJin);
                        }else{
                        }
                    },
                    error:function(data){
                        alert(top.getMessage("FAILED_OPERATION", "获取实收利息和违约金信息"), 0);
                    }
                });
            }else{
                alert('提前还款时还款金额不能大于融资余额！',0);
                $("input[name=benjinAmt]").val("");
            }
            }
        }
    var _discountAmtChange = function () {
        var discountAmt = $("input[name=discountAmt]").val().replace(",","");
        var shishouLiXi = $("input[name=shishouLiXi]").val().replace(",","");
        if(CalcUtil.subtract(shishouLiXi,discountAmt)<0){
            alert('优惠金额不能大于应收利息！',0);
            $("input[name=discountAmt]").val(0.0);
            $("input[name=shishouLiXi]").val(yiShouLiXi);
        }else{
            shishouLiXi= CalcUtil.subtract(yiShouLiXi,discountAmt);
            $("input[name=shishouLiXi]").val(shishouLiXi);
        }

    }
    //新增操作
    var  _ajaxInsert = function (formObj){
        var flag = submitJsMethod($(formObj).get(0), '');
        if(flag){
            var url = $(formObj).attr("action");
            var dataForm = JSON.stringify($(formObj).serializeArray());
            $.ajax({
                url:url,
                data:{ajaxData:dataForm},
                type:"post",
                dataType:"json",
                success:function(data){
                    top.flag = true;
                    if(data.flag == "success"){
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        alert(data.msg,0);
                    }
                },
                error:function(data){
                    alert(top.getMessage("FAILED_OPERATION", "买方还款"), 0);
                }
            });
        }
    };
    return{
        ajaxInsert:_ajaxInsert,
        init:_init,
        buyRepayDate:_buyRepayDate,
        repayAmtChange:_repayAmtChange,
        discountAmtChange:_discountAmtChange,
        repayDateChange:_repayDateChange,
    };
}(window,jQuery);