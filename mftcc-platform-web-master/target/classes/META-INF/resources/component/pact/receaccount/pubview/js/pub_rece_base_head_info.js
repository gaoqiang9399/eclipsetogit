;
var pub_rece_base_head_info = function(window, $) {
    var _receId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusReceBaseInfo/getReceBaseInfoAjax",
            data:{receId:pub_rece_base_head_info.receId},
            success:function(data) {
                if (data.flag == "success") {
                    var mfBusReceBaseInfo = data.mfBusReceBaseInfo;
                    $("#receTransAmt").text(data.receTransAmt);
                    $("#receTransBal").text(data.receTransBal);
                    $("#receEndDate").text(mfBusReceBaseInfo.receEndDate);
                }else{
                }
            },error:function() {

            }
        });
	};

    //转让证明详情
    var _getReceTranInfo=function(){
        top.window.openBigForm(webPath+'/mfReceivablesPledgeInfo/getById?busCollateralId='+pub_rece_base_head_info.receId+"&appId="+appId,"转让证明",function(){});
    };
    //反转让确认信息
    var _getRepoAffirmInfo=function(){
        top.window.openBigForm(webPath+'/mfPleRepoApply/getById?busPleId='+pub_rece_base_head_info.receId+"&appId="+appId,"反转让确认信息",function(){});
    };
	return {
		init : _init,
        getReceTranInfo:_getReceTranInfo,
        getRepoAffirmInfo:_getRepoAffirmInfo,
	};
}(window, jQuery);
