;
var MfBusPactChange_List = function(window, $) {
	var _pactChangeInput = function(){
		window.location.href= webPath + "/mfBusPactChange/input"; //列表数据查询的url
	};
    var _fincRatechang=function () {
       var fincRateChange=$("input[name='fincRateChange']").val();
       if(fincRateChange==null || fincRateChange==""){
           return;
       }
       var pactId=$("input[name='pactId']").val();
        var termType=$("input[name='termType']").val();
        $.ajax({
            url : webPath+"/mfBusPactChange/getChangevalueAjax",
            data: {
                fincRateChange:fincRateChange,
                pactId:pactId,
                termType:termType
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if(data.flag == "success"){
                    $("input[name='overRateChange']").val(data.mfBusPactChangeTmp.overRateChangeStr);
                    $("input[name='cmpdRateChange']").val(data.mfBusPactChangeTmp.cmpdRateChangeStr);
                }
            }
        });
    }
	return {
        pactChangeInput : _pactChangeInput,
        fincRatechang:_fincRatechang
	};
}(window, jQuery);
