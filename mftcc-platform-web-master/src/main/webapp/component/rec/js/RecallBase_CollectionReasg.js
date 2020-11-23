
var RecallBaseQueryEntrance1 = function(window,$){
    var _init = function(){
        //初始化滚动条
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });

        // $('input[name=planRepayDate]').onfocus({
        //     fPopUpCalendarDlg({min:'',choose:repayDateChangeEvent});
        // });
        //初始化借据号选择组件
        $('input[name=fincShowId]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/recallBase/findLoanAfterByPageAjax",//请求数据URL
            valueElem:"input[name=fincId]",//真实值选择器
            title: "选择借据",//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name=fincShowId]"));
                var fincId = $("input[name='fincId']").val();
                $.ajax({
                    url:webPath+"/recallBase/getRecallBaseAjax",
                    data:{fincId:fincId},
                    type : 'post',
                    dataType : 'json',
                    success:function(data){
                        //// debugger
                        $("[name=cusName]").val(data.mfCusCustomer.cusName);//客户名称
                        $("[name=appName]").val(data.mfBusFincApp.appName);//项目名称
                        $("[name=cusContactName]").val(data.recallBase.cusContactName);//联系人
                        $("[name=recallUnpayAmt1]").val(data.recallUnpayAmt1);//应还本金
                        $("[name=recallUnpayAmt2]").val(data.recallUnpayAmt2);//应还利息
                        $("[name=cusTel]").val(data.recallBase.cusTel);//联系电话
                        $("[name=brcContAmt]").val(data.brcContAmt);//违约金
                        $("[name=recallAmt]").val(data.recallAmt);//催收总额
                        $("[name=pactNo]").val(data.pactNo);//合同号
                        // $("[name=appId]").val(data.mfBusFincApp.appId);//申请编号
                        $("[name=pactId]").val(data.pactId);//合同编号
                        $("[name=cusNo]").val(data.cusNo);//客户号

                        $("[name=putoutAmt]").val(data.putoutAmt);//放款金额
                        $("[name=loanBal]").val(data.loanBal);//放款余额
                        $("[name=putoutAppDate]").val(data.putoutAppDate);//放款日期
                        $("[name=intstEndDate]").val(data.intstEndDate);//到期日期
                        $("[name=yuqilixi]").val(data.yuqilixi);//逾期利息
                        $("[name=fulilixi]").val(data.fulilixi);//复利利息
                        $("[name=deliveryAddress]").val(data.mfCusCustomer.commAddress);//客户通讯地址
                        //隐藏域合同号赋值
                        $("[name=conNo]").val(data.recallBase.conNo);//客户号
                        // $("[name=kindName]").val(data.mfBusFincApp.kindName);//产品种类
                        // $("[name=pactAmt]").val(data.pactAmt);//合同金额
                        // $("[name=fincRate]").val(data.mfBusFincApp.fincRate);//合同利率
                        // $("[name=fincRate]").parent(".input-group").find(".input-group-addon").remove();
                        // $("[name=fincRate]").parent(".input-group").append('<span class="input-group-addon">'+data.rateUnit+'</span>');
                        // $("[name=intstBeginDate]").val(data.mfBusFincApp.intstBeginDate);//借据起息日
                        // $("[name=intstEndDate]").val(data.mfBusFincApp.intstEndDate);//借据到期日
                        // $("[name=putoutAmt]").val(data.putoutAmt);//借据金额
                        // $("[name=loanBal]").val(data.loanBal);//剩余本金
                        //
                        // $("[name=appAmt]").val(0.00);

                    },
                    error:function(data){
                        alert("选择借据失败", 0);
                    }
                });
            },
            tablehead:{//列表显示列配置
                "pactNo":"合同编号",
                "appName":"项目名称",
                "fincShowId":"借据号"
            },
            returnData:{//返回值配置
                disName:"fincShowId",//显示值
                value:"fincId"//真实值
            }
        });
    };


    var _doSubmit = function (obj,formType){

        var dataParam = JSON.stringify($(obj).serializeArray());
        var ajaxUrl = $(obj).attr("action");
        jQuery.ajax({
            url : ajaxUrl,
            data : {ajaxData : dataParam},
            type : "POST",
            dataType : "json",
            beforeSend : function() {
                LoadingAnimate.start();
            },success : function(data){
                if(data.flag == "success"){
                    window.top.alert(data.msg,1);
                    top.formType = formType;
                    top.flag=true;
                    myclose_click();
                }else{
                    window.top.alert(data.msg,0);
                }
            },
            error : function(data) {
                if(data.flag == "error"){
                    window.top.alert("操作失败！",0);
                }
            },complete: function(){
                LoadingAnimate.stop();
            }
        });

    };
    return{
        init:_init,
        doSubmit:_doSubmit,
    };

}(window,jQuery);