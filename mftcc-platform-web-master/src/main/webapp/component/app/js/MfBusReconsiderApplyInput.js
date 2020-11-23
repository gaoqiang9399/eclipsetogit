
var MfBusApplyInput = function (window,$) {

    var  _insertReconsiderApply  =   function (obj){
        var url = $(obj).attr("action");
        var flag=submitJsMethod($(obj).get(0), '');
        if(flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:'post',
                dataType:'json',
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg, 3);
                        window.location.href=webPath+"/mfQueryDisagree/getReconListPage";
                    }else{
                        alert(  top.getMessage("FAILED_SAVE"),0);
                    }
                },error:function(){
                    LoadingAnimate.stop();
                    alert(  top.getMessage("FAILED_SAVE"),0);
                }
            });
        }else{
            alert(  top.getMessage("FAILED_SAVE"),0);
        }
    };


    //验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
    var  _checkByKindInfo =  function (obj) {
        var name = $(obj).attr("name");
        var title = $(obj).attr("title").split("(")[0];
        //申请金额
        if (name == "appAmtNew") {
            if (maxAmt != null && minAmt != null && maxAmt != "" && minAmt != "") {
                maxAmtNum = new Number(maxAmt);
                minAmtNum = new Number(minAmt);
                var appAmt = $(obj).val();
                appAmt = appAmt.replace(/,/g, "");
                if (parseFloat(appAmt) < parseFloat(minAmtNum) || parseFloat(appAmt) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info": "产品设置",
                        "field": title,
                        "value1": fmoney(minAmtNum, 2),
                        "value2": fmoney(maxAmtNum, 2)
                    }), 0);
                }
            }
        }
    };

    //验证期限
    var  _checkTerm = function (obj) {
        //修改校验期限的方法使其支持选择框
        var   isExist  =  $("select[name=termNew]").val();
        var appTerm;
        if(typeof(isExist) == "undefined"){
            appTerm = $("input[name=termNew]").val();
        }else{
            appTerm = $("select[name=termNew] option:checked").text();
        }
        appTerm = appTerm.replace(/,/g, "");
        var title = $("[name=termNew]").attr("title").split("(")[0];
        var appTermType = termType;
        appTermType = appTermType.replace(/,/g, "");
        var appMinTerm;
        var appMaxTerm;
        //申请期限
        if (minTerm != null && maxTerm != null && minTerm != ""
            && maxTerm != "" && termType != null && termType != "") {
            minTermNum = new Number(minTerm);
            maxTermNum = new Number(maxTerm);
            var unit = appTermType == "1" ? "个月" : "天";
            if (appTermType == "1") {//表单填写申请期限为月
                if (termType == "2") {//产品申请期限为日
                    minTermNum = (minTerm / 30).toFixed();
                    maxTermNum = (maxTerm / 30).toFixed();
                }
            }
            if (appTermType == "2") {//表单填写申请期限为日
                if (termType == "1") {//产品申请期限为月
                    minTermNum = (minTerm * 30).toFixed();
                    maxTermNum = (maxTerm * 30).toFixed();
                }
            }
            appMinTerm = minTermNum + unit;
            appMaxTerm = maxTermNum + unit;
            $("[name=termNew]").attr("placeholder",
                appMinTerm + "-" + appMaxTerm);
            if (parseFloat(appTerm) < parseFloat(minTermNum)
                || parseFloat(appTerm) > parseFloat(maxTermNum)) {
                $("[name=termNew]").val("");
                alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                    "info" : "产品设置",
                    "field" : title,
                    "value1" : appMinTerm,
                    "value2" : appMaxTerm
                }), 0);
            }
        }
    };


    return{
        insertReconsiderApply:_insertReconsiderApply,
        checkByKindInfo:_checkByKindInfo,
        checkTerm:_checkTerm

    };
}(window,jQuery)