;
var MfRepaymentJspForFincMain = function(window,$){
    var _init = function(){
        $("#plan_content").html(ajaxData.tableHtml);
        $("#tablist").show();
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        $("input[name='thisRepayAmt']").each(function(){
            $(this).blur(function(){
                var num = $(this).val();
                var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
                if(exp.test(num)){
                    _repayAmtChange();
                }else{
                    alert('请输入金额',0);
                }

            });
        });
        $("input[name='thisRepayDate']").each(function(){
            $(this).click(function(){
                fPopUpCalendarDlg()
            });
        });
    }
    var  _buyRepayDate= function() {
        if(repayDate!=''){
            fPopUpCalendarDlg({min: '${mfRepaymentBean.shangCiHuanKuanRiQi}',max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});
        }else {
            fPopUpCalendarDlg({min: new Date().toLocaleDateString(),max: new Date().toLocaleDateString(),choose:shiShouBenJinByLsbqHuanKuanInputOnblur});

        }
    }
    var _repayAmtChange = function () {
        // var  repayAmt = 0;
        // $("input[name='thisRepayAmt']").each(function(){
        //     if($(this).val()!=null){
        //         repayAmt = Number(repayAmt) + Number($(this).val());
        //     }
        // });
        // $("input[name='repayAmt']").val(repayAmt);
    }
    //新增操作
    var  _ajaxInsert = function (formObj){
        var flag = submitJsMethod($(formObj).get(0), '');
        var saveFlag ="0";
        var repayAmtSum = 0.00;
        if(flag){
            var url = $(formObj).attr("action");
            var dataForm = JSON.stringify($(formObj).serializeArray());
            var repayList = [];
            $("#tab").find("tr").each(function(){
                var tdArr = $(this).children();
                var $checkbox = tdArr.find("input[type=checkbox]");
                if($checkbox.is(':checked')) {
                    var fincId = tdArr.find("input[name='fincId']").val();
                    var receId = tdArr.find("input[name='receId']").val();
                    var thisRepayAmt = tdArr.find("input[name='thisRepayAmt']").val();
                    var thisRepayDate = tdArr.find("input[name='thisRepayDate']").val();
                    var thisRepayObject = tdArr.find("select[name='repayObject']").val();
                    if ((thisRepayAmt != "") && (thisRepayDate == "")) {
                        alert('请输入还款金额的列请输入还款日期', 0);
                        return;
                    }
                    if(thisRepayAmt!="" &&　parseFloat(thisRepayAmt)>0) {
                        repayAmtSum =  repayAmtSum*1 +thisRepayAmt*1;
                    }
                    if ((thisRepayAmt != "") && (thisRepayDate != "") && (thisRepayObject != "")) {
                        saveFlag = "1";
                    }
                    var repayInfo = new Object();
                    repayInfo.fincId = fincId;
                    repayInfo.receId = receId;
                    repayInfo.thisRepayAmt = thisRepayAmt;
                    repayInfo.thisRepayDate = thisRepayDate;
                    repayInfo.thisRepayObject = thisRepayObject;
                    repayList.push(repayInfo);
                }
            });
            //校验账款还款总额与手动填写的还款总额是否一直
            var repayAmt =$("#buyerRepayment input[name=repayAmt]").val().replace(/,/g, "");
            if(parseFloat(repayAmtSum) !=parseFloat(repayAmt)){
                window.top.alert(top.getMessage("NOT_MONEY_SAME",{"money1":"账款还款总额" , "money2": "表单还款金额"}), 3);
                return false;
            }
            if(saveFlag == "0"){
                alert('输入还款金额,还款日期后保存',0);
            }else {
                var jsonString = JSON.stringify(repayList);
                $.ajax({
                    url:url,
                    data:{ajaxData:dataForm,ajaxList:jsonString},
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

        }
    };
    //处理融资申请金额（按照账款到期日的升序顺序，根据账款的可融资金额瓜分融资申请金额）
    var _dealRepayAmt = function(obj){
        var repayAmt =$(obj).val().replace(/,/g, "");
        $("#plan_content tbody tr input[type=checkbox]").prop("checked",false);
        $("#plan_content tbody tr input[name=thisRepayAmt]").val("0.00");
        var repayAmtSum = repayAmt;
        $("#plan_content tbody tr").each(function(){
            if(parseFloat(repayAmtSum)>0){
                $thisTr = $(this);
                var receTransAmt = $thisTr.find("input[name=receTransAmt]").val().replace(/,/g, "");
                if(parseFloat(repayAmtSum)>=parseFloat(receTransAmt)){//如果总的申请金额没有被分配完，该账款的融资金额就是其可融资金额
                    $thisTr.find("input[name=thisRepayAmt]").val(receTransAmt);
                    repayAmtSum=parseFloat(repayAmtSum)-parseFloat(receTransAmt);
                    repayAmtSum = repayAmtSum.toFixed(2);
                }else{//如果剩余的申请金额不够分配了，直接将剩余的赋值给该账款
                    $thisTr.find("input[name=thisRepayAmt]").val(repayAmtSum);
                    repayAmtSum=0.00;
                }
                $thisTr.find("input[type=checkbox]").prop("checked",true);
            }
        });
    }
    return{
        ajaxInsert:_ajaxInsert,
        init:_init,
        dealRepayAmt:_dealRepayAmt,
        buyRepayDate:_buyRepayDate
    };
}(window,jQuery);