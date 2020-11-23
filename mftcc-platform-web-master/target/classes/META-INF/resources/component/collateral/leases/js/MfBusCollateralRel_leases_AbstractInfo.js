;
var  MfBusCollateralRel_leases_AbstractInfo = function(window,$){
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
			url:webPath+"/mfLeases/getCollateralInfoAjax",
			data:{
				appId:MfBusCollateralRel_leases_AbstractInfo.relId,
				cusNo:MfBusCollateralRel_leases_AbstractInfo.cusNo,
				collateralType:"lease"
			},
			type:"POST",
			dataType:"json",
			success:function(data) {
				if (data.flag == "success") {
					// 填充业务信息
					_setCollateralInfo(data.collateralRel);
					MfBusCollateralRel_leases_AbstractInfo.collateralRelId = data.collateralRel.busCollateralId;
					$("#pleInfo_leases").removeClass("hide");
					$("#pleInfo_leases").addClass("show");
				}else{
					$("#pleInfono_leases").removeClass("hide");
					$("#pleInfono_leases").addClass("show");
					if(MfBusCollateralRel_leases_AbstractInfo.relId!=""&&MfBusCollateralRel_leases_AbstractInfo.relId!=null){
							$("#addCollateralInfo_leases").show();
					}else{
						$("#noPledge_leases").show();
					}
				}
			},
			error:function() {
	
			}
		});
	};
	
	var _addCollateralInfo = function(){
		top.addCollateral=false;
		var url=webPath+"/mfBusCollateralRel/infoCollectInput?popFlag=1&appId="+MfBusCollateralRel_leases_AbstractInfo.relId+"&cusNo="+MfBusCollateralRel_leases_AbstractInfo.cusNo+"&entrFlag=businessNoTask&infoType=lease";
		top.openBigForm(url,"新增租赁物",function(){
			if(top.addCollateral){
				top.LoadingAnimate.start();
				window.location.href=webPath+"/mfLeases/getCollateralInfo?cusNo="+MfBusCollateralRel_leases_AbstractInfo.cusNo+"&appId="+MfBusCollateralRel_leases_AbstractInfo.appId
				+"&relId="+MfBusCollateralRel_leases_AbstractInfo.appId+"&busEntrance="+MfBusCollateralRel_leases_AbstractInfo.busEntrance+"&entrance="+"&creditAppId="+MfBusCollateralRel_leases_AbstractInfo.creditAppId+"&collateralType=lease";
			}
		});
	};
	var _getCollateralRelInfo = function(appId,busCollateralId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfLeases/getCollateralInfo?busEntrance="+MfBusCollateralRel_leases_AbstractInfo.busEntrance+"&operable="+MfBusCollateralRel_leases_AbstractInfo.operable+"&cusNo="+MfBusCollateralRel_leases_AbstractInfo.cusNo
			+"&relId="+appId+"&appId="+appId+"&busCollateralId="+busCollateralId+"&entrance="+MfBusCollateralRel_leases_AbstractInfo.entrance
			+"&type="+MfBusCollateralRel_leases_AbstractInfo.type+"&fincId="+MfBusCollateralRel_leases_AbstractInfo.fincId+"&creditAppId="+MfBusCollateralRel_leases_AbstractInfo.creditAppId+"&collateralType=lease";
	};
	//填充租赁物信息
	var _setCollateralInfo = function(collateralRel){
		var title = "租赁物";
		var titleClass="i i-lease font-icon";
		var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_10">'
				+ '<button type="button" class="btn btn-font-pledge padding_left_15" onclick="MfBusCollateralRel_leases_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');">'
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
				+ '<button type="button" class="btn btn-font-qiehuan" onclick="MfBusCollateralRel_leases_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>'
				+ '</div>'
				+ '<div class="row clearfix pledge-rece" id="pledgeInfo_leases">'
					+'<table><tbody><tr>'
					+'<td>'
					+'<p class="ptitle">租赁物价值</p>'
					+'<p class="pvalue">'
					+'<span class="span-value" id="receiptsAmount_leases">'+ collateralRel.collateralAmt.toFixed(2)+'</span>'
					+'<span>&nbsp;万</span>'
					+'</p>'
					+'</td>'
					+'</tr></tbody></table>'
				+ '</div>';
		$("#pleInfo_leases .cont-row").html(htmlStr);
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