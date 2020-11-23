;
var  MfBusFincAppMain_AbstractInfo = function(window,$){
	var _busEntrance = '';
	var _appId = '';
	var _pactId = '';
	var _fincMainId = '';
	var _init= function(){
		$.ajax({
			url:webPath+"/mfBusFincAppMain/getFincMainHeadInfoAjax",
			data:{
                fincMainId:MfBusFincAppMain_AbstractInfo.fincMainId,
                appId:MfBusFincAppMain_AbstractInfo.appId
			},
			success:function(data) {
				if (data.flag == "success") {
					$("#fincAmt").text(data.fincAmt);
					$("#fincBal").text(data.fincBal);
				}
			},error:function() {

			}
		});
	};


    var _getFincMainDetail = function(){
        top.LoadingAnimate.start();
        window.location.href=webPath+"/mfBusFincAppMain/getSummary?busEntrance="+MfBusFincAppMain_AbstractInfo.busEntrance+"&fincMainId="+MfBusFincAppMain_AbstractInfo.fincMainId
        	+"&appId="+MfBusFincAppMain_AbstractInfo.appId;
    };
	return{
		init:_init,
        getFincMainDetail:_getFincMainDetail,
		busEntrance:_busEntrance,
        fincMainId:_fincMainId,
        appId:_appId,
        pactId:_pactId,
	};
}(window,jQuery);