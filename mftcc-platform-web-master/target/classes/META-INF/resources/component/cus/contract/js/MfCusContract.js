var MfCusContract = function(window, $) {
    //保存无形资产信息
    var _saveCusContract = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };

    //计算合同额增长率
    var _calcGrowthRate = function(obj){
        var dataParam = JSON.stringify($(obj).serializeArray());
        LoadingAnimate.start();
        $.ajax({
            url:webPath + "/mfCusContract/calcGrowthRate",
            data : {
                ajaxData : dataParam
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    $("input[name='growthRate']").val(data.growthRate);
                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("ERROR_REQUEST_URL", url));
            }
        });
    };

    return {
        saveCusContract:_saveCusContract,
        calcGrowthRate:_calcGrowthRate
    };

}(window, jQuery);