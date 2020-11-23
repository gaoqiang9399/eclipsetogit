;
var MfRepaymentTail = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });

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
        if(flag){
            var url = $(formObj).attr("action");
            var dataForm = JSON.stringify($(formObj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataForm},
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