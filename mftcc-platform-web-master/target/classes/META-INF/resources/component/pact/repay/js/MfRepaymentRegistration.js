;
var MfRepaymentRegistration = function(window, $) {
    // 初始化
    var _init = function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
        $("input[name='yiHuanPrcp']").val(yiHuanPrcp);
        $("select[name='repayType']").val("1");
    };
    var _shiHuanPrcpChange = function () {
       var shiHuanPrcp =  $("input[name='shiHuanPrcp']").val();
        if(shiHuanPrcp!=''){
            shiHuanPrcp = shiHuanPrcp.replace(/,/g,"");
        }else{
            return;
        }
       var yiHuanPrcp =$("input[name='yiHuanPrcp']").val();
       if(CalcUtil.compare(shiHuanPrcp,yiHuanPrcp)==1){
           alert("实还金额不能大于贷款金额");
           $("input[name='shiHuanPrcp']").val('');
       }
    };
    var _myclose = function () {
        myclose_click();
    };
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url: url,
                data: {ajaxData: dataParam},
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                    LoadingAnimate.start();
                }, success: function (data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        myclose_click();
                        window.location.href=webPath+"/mfBusPact/getPactFincById?appId="+appId+"&busEntrance=3&fincId="+fincId;
                    } else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }, complete: function () {
                    LoadingAnimate.stop();
                }
            });
        }
    };
    return {
        init : _init,
        myclose:_myclose,
        ajaxSave:_ajaxSave,
        shiHuanPrcpChange:_shiHuanPrcpChange
    };
}(window, jQuery);