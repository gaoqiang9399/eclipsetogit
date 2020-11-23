;
var pub_rece_finc_main_head_info = function(window, $) {
    var _appId = '';
    var _pactId = '';
    var _fincMainId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusFincAppMain/getFincMainHeadInfoAjax",
            data:{fincMainId:pub_rece_finc_main_head_info.fincMainId,appId:pub_rece_finc_main_head_info.appId,pactId:pub_rece_finc_main_head_info.pactId},
            success:function(dataMap) {
                if (dataMap.flag == "success") {
                    var mfBusPact = dataMap.mfBusPact;
                    var mfBusApply = dataMap.mfBusApply;
                    $("#fincAmt").text(dataMap.fincAmt);
                    $("#fincBal").text(dataMap.fincBal);
                    $("#factorCoopWay").text(dataMap.factorCoopWay);
                    //产品信息
                    if(mfBusPact.kindName.length>8){
                        $('.head-info .btn-link').attr('title', mfBusPact.kindName).text(mfBusPact.kindName.substring(0,8));
                    }else{
                        $('.head-info .btn-link').text(mfBusPact.kindName);
                    }
                    //头部名称
                    $('.head-info .head-title').attr('title', mfBusPact.appName).text(mfBusPact.appName);
                    var moreApplyCount = dataMap.moreApplyCount;
                    $('.head-info .more-apply-count').text(moreApplyCount);
                    var morePactCount = dataMap.morePactCount;
                    $('.head-info .more-pact-count').text(morePactCount);
                    var moreFincCount = dataMap.moreFincCount;
                    $('.head-info .more-finc-count').text(moreFincCount);

                    var moreAssureCount = dataMap.moreAssureCount;
                    $('.head-info .more-assure-count').text(moreAssureCount);
                    //机构
                    var $relate = $('');
                    var method;
                    if(dataMap.wareHouseCusNo!=null && dataMap.wareHouseCusNo!="" && dataMap.wareHouseCusNo!="0"){
                        method = "getInfoForView('103','"+ dataMap.wareHouseCusNo + "','仓储机构');";
                        $relate = $('<span  class="relate-corp" data-view="cuswarehouse"><i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="'+method+'">' + mfBusApply.cusNameWarehouse + '</a>保管货物 </span></span>');
                    }else if(dataMap.coreCusNo!=null && dataMap.coreCusNo!="" && dataMap.coreCusNo!="0"){
                        method = "getInfoForView('108','" + dataMap.coreCusNo + "','核心企业');";
                        $relate = $('<span class="relate-corp" data-view="cuscore"><i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);"  onclick="'+method+'">' + mfBusApply.cusNameCore + '</a> 推荐</span></span>');
                    }else if(dataMap.fundCusNo!=null && dataMap.fundCusNo!="" && dataMap.fundCusNo !="0"){
                        method = "getInfoForView('109','" + dataMap.fundCusNo + "','资金机构 ');";
                        $relate = $('<span class="relate-corp" data-view="fundorg" ><i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);" onclick="'+method+'">' + mfBusApply.cusNameFund + '</a> 放款</span></span>');
                    }
                    $('.head-info .btn-special').empty().append($relate);
                }else{
                }
            },error:function() {

            }
        });
	};
    //查看产品信息
    var _getKindInfo = function(){
        top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+pub_rece_finc_main_head_info.appId,'产品信息',function(){});
    }
	return {
		init : _init,
        getKindInfo:_getKindInfo,
	};
}(window, jQuery);
