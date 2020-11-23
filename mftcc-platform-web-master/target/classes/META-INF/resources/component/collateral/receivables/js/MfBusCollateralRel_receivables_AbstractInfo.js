;
var  MfBusCollateralRel_receivables_AbstractInfo = function(window,$){
	var _cusNo = '';
	var _relId = '';
	var _creditAppId = '';
	var _busModel = '';
	var _operable = '';
	var _type = '';
	var _fincId = '';
	var _busEntrance = '';
	var _vouType = '';
	var _collateralRelId='';
	var _appId = '';
	var _init= function(){
		$.ajax({
			url:webPath+"/mfRecievables/getCollateralInfoAjax",
			data:{
				appId:MfBusCollateralRel_receivables_AbstractInfo.relId,
				cusNo:MfBusCollateralRel_receivables_AbstractInfo.cusNo,
				collateralType:"account"
			},
			type:"POST",
			dataType:"json",
			success:function(data) {
				if (data.flag == "success") {
					// 填充业务信息
					_setCollateralInfo(data.collateralRel);
					MfBusCollateralRel_receivables_AbstractInfo.collateralRelId = data.collateralRel.busCollateralId;
					$("#pleInfo_receivables").removeClass("hide");
					$("#pleInfo_receivables").addClass("show");
				}else{
					$("#pleInfono_receivables").removeClass("hide");
					$("#pleInfono_receivables").addClass("show");
					if(MfBusCollateralRel_receivables_AbstractInfo.relId!=""&&MfBusCollateralRel_receivables_AbstractInfo.relId!=null){
							$("#addCollateralInfo_receivables").show();
					}else{
						$("#noPledge_receivables").show();
					}
				}
			},
			error:function() {
	
			}
		});
	};
	
	var _addCollateralInfo = function(){
		top.addCollateral=false;
		var url=webPath+"/mfBusCollateralRel/infoCollectInput?popFlag=1&appId="+MfBusCollateralRel_receivables_AbstractInfo.relId+"&cusNo="+MfBusCollateralRel_receivables_AbstractInfo.cusNo+"&entrFlag=businessNoTask&infoType=account";
		top.openBigForm(url,"新增应收账款",function(){
			if(top.addCollateral){
				top.LoadingAnimate.start();
				window.location.href=webPath+"/mfRecievables/getCollateralInfo?cusNo="+MfBusCollateralRel_receivables_AbstractInfo.cusNo+"&appId="+MfBusCollateralRel_receivables_AbstractInfo.appId
				+"&relId="+MfBusCollateralRel_receivables_AbstractInfo.appId+"&busEntrance="+MfBusCollateralRel_receivables_AbstractInfo.busEntrance+"&entrance="+"&creditAppId="+MfBusCollateralRel_receivables_AbstractInfo.creditAppId+"&collateralType=account";
			}
		});
	};
	var _getCollateralRelInfo = function(appId,busCollateralId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfRecievables/getCollateralInfo?busEntrance="+MfBusCollateralRel_receivables_AbstractInfo.busEntrance+"&operable="+MfBusCollateralRel_receivables_AbstractInfo.operable+"&cusNo="+MfBusCollateralRel_receivables_AbstractInfo.cusNo
			+"&relId="+appId+"&appId="+appId+"&busCollateralId="+busCollateralId+"&entrance="+MfBusCollateralRel_receivables_AbstractInfo.entrance
			+"&type="+MfBusCollateralRel_receivables_AbstractInfo.type+"&fincId="+MfBusCollateralRel_receivables_AbstractInfo.fincId+"&creditAppId="+MfBusCollateralRel_receivables_AbstractInfo.creditAppId+"&collateralType=account";
	};
	//填充应收账款信息
	var _setCollateralInfo = function(collateralRel){
		var title = "应收账款";
		var titleClass="i i-rece font-icon";
		var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_10">'
				+ '<button type="button" class="btn btn-font-pledge padding_left_15" onclick="MfBusCollateralRel_receivables_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');">'
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
				+ '<button type="button" class="btn btn-font-qiehuan" onclick="MfBusCollateralRel_receivables_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>'
				+ '</div>'
				+ '<div class="row clearfix pledge-rece" id="pledgeInfo_receivables">'
				+ '<div>'
            		+'<table><tbody><tr>'
					+'<td>'
                    +'<p class="ptitle">转让金额</p>'
					+'<p class="pvalue">'
					+'<span class="span-value" id="envalueAmt_receivables">'+ collateralRel.receTransAmt.toFixed(2)+'</span>'
					+'<span>&nbsp;万</span>'
					+'</p>'
			        +'</td>'
					+'<td>'
					+'<p class="ptitle">转让余额</p>'
					+'<p class="pvalue">'
					+'<span class="span-value" id="receiptsAmount_receivables">'+ collateralRel.receTransBal.toFixed(2)+'</span>'
					+'<span>&nbsp;万</span>'
					+'</p>'
					+'</td>'
					+'</tr></tbody></table>'
				+'</div>'
			+ '</div>';
		$("#pleInfo_receivables .cont-row").html(htmlStr);
	};
	return{
		init:_init,
		addCollateralInfo:_addCollateralInfo,
		getCollateralRelInfo:_getCollateralRelInfo,
		setCollateralInfo:_setCollateralInfo,
		cusNo:_cusNo,
		relId:_relId,
		creditAppId:_creditAppId,
		busModel:_busModel,
		operable:_operable,
		type:_type,
		fincId:_fincId,
		busEntrance:_busEntrance,
		vouType:_vouType,
		collateralRelId:_collateralRelId,
		appId:_appId
	};
}(window,jQuery);