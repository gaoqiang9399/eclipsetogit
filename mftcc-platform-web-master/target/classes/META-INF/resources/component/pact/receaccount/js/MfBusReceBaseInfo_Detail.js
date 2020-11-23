;
var  MfBusReceBaseInfo_Detail = function(window,$){
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
        _operateBtnControl();
	};
    var _repayCloseCallBack =function (){
        window.location.href=webPath+"/mfBusReceBaseInfo/getReceDetailById?receId="+receId+"&fincId="+fincId+"&busEntrance=rece";
    }
    //买方付款
    var _buyerRepayment =  function(){
        top.flag=false;
        top.window.openBigForm(webPath+'/mfRepayment/repaymentJspForBuy?fincId='+fincId,'买方付款',function(){
            if(top.flag){
                pub_accnt_repay_detail_list.init();
                pub_rece_base_head_info.init();
                _operateBtnControl();
            }
        });
    }
    //卖方还款
    var _repayment = function(){
        top.flag=false;
        top.window.openBigForm(webPath+'/mfRepayment/repaymentJspForAccount?fincId='+fincId,'反转让',function(){
            if(top.flag){
                pub_accnt_repay_detail_list.init();
                _operateBtnControl();
            }
        });
    }
    //尾款结付
    var  _tailPayment =function(){
        top.flag=false;
        top.window.openBigForm(webPath+'/mfRepayment/tailPayment?fincId='+fincId,'尾款结付',_repayCloseCallBack);
    }





    //应收账款反转让
    var _receBuyBack = function(){
        top.buyBackFlag=false;
        top.window.openBigForm(webPath+'/mfPleRepoApply/input?receId='+receId+"&appId="+appId,"应收账款反转让申请",function(){
            if(top.buyBackFlag){
                pub_rece_buyback_approve_his.init();
            }
        });
    };

    //应收账款反转让确认
    var _receRepoAffirm=function(){
        top.repoAffirmFlag=false;
        top.window.openBigForm(webPath+'/mfPleRepoApply/inputAffirm?receId='+receId+"&appId="+appId,"应收账款反转让确认",function(){
            if(top.repoAffirmFlag){
                pub_accnt_repay_detail_list.init();
                _operateBtnControl();
            }
        });
    };

    //头部操作按钮控制
    var _operateBtnControl = function(){
        $.ajax({
            url:webPath+"/mfBusReceBaseInfo/getBtnControlFlagAjax",
            data:{receId:receId,fincId:fincId},
            success:function(data) {
                if (data.flag == "success") {
                    var repayFlag = data.repayFlag;
                    var jiefuFlag = data.jiefuFlag;
                    var buyBackFlag = data.buyBackFlag;
                    var buyBackConfirmFlag = data.buyBackConfirmFlag;
                    //判断详情页面还款按钮的显隐
                    if(repayFlag){//还款操作不能点击
                        $("#repay").attr("disabled",false);
                        $("#repay").removeClass("btn-opt-dont");
                        $("#repay").addClass("btn-opt");
                        $("#buyerRepay").attr("disabled",false);
                        $("#buyerRepay").removeClass("btn-opt-dont");
                        $("#buyerRepay").addClass("btn-opt");
                    }else{
                        $("#repay").attr("disabled",true);
                        $("#repay").removeClass("btn-opt");
                        $("#repay").addClass("btn-opt-dont");
                        $("#buyerRepay").attr("disabled",true);
                        $("#buyerRepay").removeClass("btn-opt");
                        $("#buyerRepay").addClass("btn-opt-dont");
                    }

                    if(jiefuFlag){
                        //尾款结付 按钮相关控制
                        $("#tailPayment").attr("disabled",false);
                        $("#tailPayment").removeClass("btn-opt-dont");
                        $("#tailPayment").addClass("btn-opt");
                    }else {
                        //尾款结付 按钮相关控制
                        $("#tailPayment").attr("disabled",true);
                        $("#tailPayment").removeClass("btn-opt");
                        $("#tailPayment").addClass("btn-opt-dont");
                    }
                    //账款反转让按钮相关控制
                    if(buyBackFlag){
                        //尾款结付 按钮相关控制
                        $("#receBuyBack").attr("disabled",false);
                        $("#receBuyBack").removeClass("btn-opt-dont");
                        $("#receBuyBack").addClass("btn-opt");
                    }else {
                        //尾款结付 按钮相关控制
                        $("#receBuyBack").attr("disabled",true);
                        $("#receBuyBack").removeClass("btn-opt");
                        $("#receBuyBack").addClass("btn-opt-dont");
                    }
                    //账款反转让确认按钮相关控制
                    if(buyBackConfirmFlag){
                        //尾款结付 按钮相关控制
                        $("#receRepoAffirm").attr("disabled",false);
                        $("#receRepoAffirm").removeClass("btn-opt-dont");
                        $("#receRepoAffirm").addClass("btn-opt");
                    }else {
                        //尾款结付 按钮相关控制
                        $("#receRepoAffirm").attr("disabled",true);
                        $("#receRepoAffirm").removeClass("btn-opt");
                        $("#receRepoAffirm").addClass("btn-opt-dont");
                    }
                    MfBusExtensionCommonForAccount.showExtenWkfFlowInfo();
                }else{

                }
            },error:function() {

            }
        });
    };

    return {
        init:_init,
        buyerRepayment:_buyerRepayment,
        repayment:_repayment,
        tailPayment:_tailPayment,
        receBuyBack:_receBuyBack,
        receRepoAffirm:_receRepoAffirm,
	};
}(window, jQuery);