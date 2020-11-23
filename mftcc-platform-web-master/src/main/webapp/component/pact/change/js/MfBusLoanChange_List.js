;
var MfBusloanChange_List = function(window, $) {
	var _pactChangeInput = function(){
        //top.window.openBigForm(webPath + "/mfBusLoanChange/input","保后变更申请",function(){});
		window.location.href= webPath + "/mfBusLoanChange/input"; //列表数据查询的url
	};
    var _fincRatechang=function () {
       var fincRateChange=$("input[name='fincRateChange']").val();
       if(fincRateChange==null || fincRateChange==""){
           return;
       }
       var pactId=$("input[name='pactId']").val();
        var termType=$("input[name='termType']").val();
        $.ajax({
            url : webPath+"/mfBusLoanChange/getChangevalueAjax",
            data: {
                fincRateChange:fincRateChange,
                pactId:pactId,
                termType:termType
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if(data.flag == "success"){
                    $("input[name='overRateChange']").val(data.mfBusLoanChangeTmp.overRateChangeStr);
                    $("input[name='cmpdRateChange']").val(data.mfBusLoanChangeTmp.cmpdRateChangeStr);
                }
            }
        });
    }

    // 跳转至详情页
    var _getDetailPage = function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.LoadingAnimate.start();
        top.openBigForm(url,"详情",function(){
        });
    };

	return {
        pactChangeInput : _pactChangeInput,
        fincRatechang:_fincRatechang,
        getDetailPage:_getDetailPage
	};
}(window, jQuery);
