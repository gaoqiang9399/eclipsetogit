;
var  MfBusReceBaseInfo_AbstractInfo = function(window,$){
	var _cusNo = '';
	var _busEntrance = '';
	var _appId = '';
	var _fincMainId = '';
	var _init= function(){
		$.ajax({
			url:webPath+"/mfBusReceBaseInfo/getReceAbstractInfoAjax",
			data:{
				appId:MfBusReceBaseInfo_AbstractInfo.appId
			},
			success:function(data) {
				if (data.flag == "success") {
					// 填充业务信息
					_setReceInfo(data);
					$("#receInfo").removeClass("hide");
					$("#receInfo").addClass("show");
				}else{
					$("#noReceInfo").removeClass("hide");
					$("#noReceInfo").addClass("show");
					// if(data.mfBusReceBaseInfo){
						$("#addReceInfo").show();
					// }else{
					// 	$("#noReceInfoSpan").show();
					// }
				}
			},error:function() {
	
			}
		});
	};
	//填充应收账款信息
	var _setReceInfo = function(mfBusReceBaseInfo){
		var title = "应收账款";
		var titleClass="i i-rece font-icon";
		var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_10">'
				+ '<button type="button" class="btn btn-font-pledge padding_left_15" onclick="MfBusReceBaseInfo_AbstractInfo.getReceDetail();">'
				+ '<i class="'+titleClass+'"></i>'
				+ '<div class="font-text">'+title+'</div>'
				+ '</button>'
				+ '</div>'
				+ '<div class="col-xs-9 col-md-9 column" >'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column margin_top_20">'
				+ '<button type="button" id="apply-name" class="btn btn-link content-title"  title="'
				+ '">'
				+ '</button>'
				+ '</div>'
				+ '<div class="col-xs-2 col-md-2 column" style="margin-top: -5px;">'
				+ '<button type="button" class="btn btn-font-qiehuan" onclick="MfBusReceBaseInfo_AbstractInfo.getReceDetail();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>'
				+ '</div>'
				+ '<div class="row clearfix pledge-rece" id="pledgeInfo_receivables">'
				+ '<div>'
            		+'<table><tbody><tr>'
					+'<td>'
                    +'<p class="ptitle">转让金额</p>'
					+'<p class="pvalue">'
					+'<span class="span-value" id="receTransAmtSum">'+ mfBusReceBaseInfo.receTransAmtSum+'</span>'
					+'<span>&nbsp;万</span>'
					+'</p>'
			        +'</td>'
					+'<td>'
					+'<p class="ptitle">转让余额</p>'
					+'<p class="pvalue">'
					+'<span class="span-value" id="receTransBalSum">'+ mfBusReceBaseInfo.receTransBalSum+'</span>'
					+'<span>&nbsp;万</span>'
					+'</p>'
					+'</td>'
					+'</tr></tbody></table>'
				+'</div>'
			+ '</div>';
		$("#receInfo .cont-row").html(htmlStr);
	};


    var _addReceInfo = function(){
        top.addReceInfoFlag=false;
        top.window.openBigForm(webPath+"/mfBusReceBaseInfo/input?appId="+MfBusReceBaseInfo_AbstractInfo.appId+"&cusNo="+MfBusReceBaseInfo_AbstractInfo.cusNo,'账款登记',function() {
            if(top.addReceInfoFlag){
                _getReceDetail();
            }
        });
    };
    var _getReceDetail = function(){
        top.LoadingAnimate.start();
        window.location.href=webPath+"/mfBusReceBaseInfo/getReceDetail?busEntrance="+MfBusReceBaseInfo_AbstractInfo.busEntrance
        	+"&appId="+MfBusReceBaseInfo_AbstractInfo.appId+"&fincMainId="+MfBusReceBaseInfo_AbstractInfo.fincMainId;
    };
	return{
		init:_init,
		addReceInfo:_addReceInfo,
		getReceDetail:_getReceDetail,
		setReceInfo:_setReceInfo,
		cusNo:_cusNo,
		busEntrance:_busEntrance,
		appId:_appId,
        fincMainId:_fincMainId,
	};
}(window,jQuery);