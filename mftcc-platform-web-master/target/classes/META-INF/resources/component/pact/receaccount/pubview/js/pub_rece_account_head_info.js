;
var pub_rece_account_head_info = function(window, $) {
    var _appId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusReceBaseInfo/getReceAbstractInfoAjax",
            data:{appId:pub_rece_account_head_info.appId},
            success:function(data) {
                if (data.flag == "success") {
                    $("#receTransAmtSum").text(data.receTransAmtSum);
                    $("#receTransBalSum").text(data.receTransBalSum);
                    $("#receEndDateMin").text(data.receEndDateMin);
                }else{
                }
            },error:function() {

            }
        });
	};

    //转让证明详情
    var _getReceTranInfo=function(){
        // top.window.openBigForm(webPath+'/mfReceivablesPledgeInfo/getById?busCollateralId='+busCollateralId+"&appId="+appId,"转让证明",function(){});
    };
    //折让历史
    var _getRebateHistory=function(){
        // top.window.openBigForm(webPath+'/mfReceivablesRebateApp/getListPage?busPleId='+busCollateralId+"&appId="+appId,"折让历史",function(){});
    };
    //争议历史
    var _getDisputedHistory=function(){
        // top.window.openBigForm(webPath+'/mfReceivablesDisputedApp/getListPage?busPleId='+busCollateralId+"&appId="+appId,"争议历史",function(){});
    };
    //反转让确认信息
    var _getRepoAffirmInfo=function(){
        // top.window.openBigForm(webPath+'/mfPleRepoApply/getById?busPleId='+busCollateralId+"&appId="+appId,"反转让确认信息",function(){});
    };
	return {
		init : _init,
        getReceTranInfo:_getReceTranInfo,
        getRebateHistory:_getRebateHistory,
        getDisputedHistory:_getDisputedHistory,
        getRepoAffirmInfo:_getRepoAffirmInfo,
	};
}(window, jQuery);
