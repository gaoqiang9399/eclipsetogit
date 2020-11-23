;
var MfRepaymentJspForBuy = function(window,$){
    var _init = function(){
       /* $("#plan_content").html(ajaxData.tableHtml);
        $("#tablist").show();*/
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        /*$("input[name='repayAmtThis']").each(function(){
            $(this).blur(function(){
                var num = $(this).val();
                var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
                if(exp.test(num)){
                    _repayAmtChange();
                }else{
                    alert('请输入金额');
                }

            });
        });*/
    }
    var  _buyRepayDate= function() {
        if(repayDate!=''){
            fPopUpCalendarDlg({min: '${mfRepaymentBean.shangCiHuanKuanRiQi}',max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});
        }else {
            fPopUpCalendarDlg({min: new Date().toLocaleDateString(),max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});

        }
    }
    var _repayAmtChange = function () {
        var  repayAmt = 0;
        $("input[name='repayAmtThis']").each(function(){
            repayAmt = Number(repayAmt) + Number($(this).val());
        });
        $("input[name='repayAmt']").val(repayAmt);
    }
    //新增操作
    var  _ajaxInsert = function (formObj){
        var flag = submitJsMethod($(formObj).get(0), '');
        if(flag){
            var url = $(formObj).attr("action");
            var dataForm = JSON.stringify($(formObj).serializeArray());
            var pledgeList = [];
            /*$("#tab").find("tr").each(function(){
                var tdArr = $(this).children();
                var pledgeNo = tdArr.find("input[name='pledgeNo']").val();
                var repayAmtThis = tdArr.find("input[name='repayAmtThis']").val();
                var pledgeInfo = new Object();
                pledgeInfo.pledgeNo = pledgeNo;
                pledgeInfo.repayAmtThis = repayAmtThis;
                pledgeList.push(pledgeInfo);
            });
            var jsonString = JSON.stringify(pledgeList);*/
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
        buyRepayDate:_buyRepayDate
    };
}(window,jQuery);