;
var MfRepaymentTailForFincMain = function(window,$){
    var _init = function(){
        $("#plan_content").html(ajaxData.tableHtml);
        $("#tablist").show();
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        $("input[name='thisTailDate']").each(function(){
            $(this).click(function(){
                fPopUpCalendarDlg({max:today})
            });

        });
        $("input[name='thisRepayAmt']").each(function(){
            $(this).blur(function(){
                var num = $(this).val();
                var tailBalAmt = $(this).parent().parent().find("input[name='tailBalAmt']").val();
                var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
                if(exp.test(num)){
                    num = num.replace(",","");
                    tailBalAmt = tailBalAmt.replace(",","");
                    if(CalcUtil.subtract(tailBalAmt,num)<0){
                        alert('解付金额不能大于解付余额',0);
                    }else{
                        _repayAmtChange();
                    }

                }else{
                    alert('请输入金额',0);
                }

            });
        });
    }

    var _repayAmtChange = function () {
        var  repayAmt = 0;
        $("input[name='thisRepayAmt']").each(function(){
            if($(this).val()!=null){
                repayAmt = Number(repayAmt) + Number($(this).val());
            }
        });
        $("input[name='repayAmt']").val(repayAmt);
    }
    var _tailAmtChange = function (){
        var tailAmt =$("input[name=tailAmt]").val();
        var balAmt =$("input[name=balAmt]").val();
        tailAmt = tailAmt.replace(",","");
        balAmt = balAmt.replace(",","");
        if(CalcUtil.subtract(balAmt,tailAmt)<0){
            alert('解付金额不能大于解付余额',0);
        }
    }
    var  _ajaxInsert = function (formObj){
        if(overFlag=="1"){
            window.top.alert("该账款所在的融资下存在逾期的应收账款是否继续解付？",2,function(){
                _save(formObj);
            });
        }else{
            _save(formObj);
        }



    };
    var _save = function (formObj) {
        var flag = submitJsMethod($(formObj).get(0), '');
        var repayList = [];
        $("#tab").find("tr").each(function(){
            var tdArr = $(this).children();
            var fincId = tdArr.find("input[name='fincId']").val();
            var receId = tdArr.find("input[name='receId']").val();
            var balId = tdArr.find("input[name='balId']").val();
            var thisRepayAmt = tdArr.find("input[name='thisRepayAmt']").val();
            var thisTailDate = tdArr.find("input[name='thisTailDate']").val();
            if((thisRepayAmt!="")&&(thisTailDate=="")){
                alert('请输入还款金额的列请输入还款日期',0);
                return ;
            }
            var repayInfo = new Object();
            repayInfo.fincId = fincId;
            repayInfo.receId = receId;
            repayInfo.thisRepayAmt = thisRepayAmt;
            repayInfo.thisTailDate = thisTailDate;
            repayInfo.balId = balId;
            repayList.push(repayInfo);
        });
        if(flag){
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
    return{
        ajaxInsert:_ajaxInsert,
        tailAmtChange:_tailAmtChange,
        init:_init
    };
}(window,jQuery);