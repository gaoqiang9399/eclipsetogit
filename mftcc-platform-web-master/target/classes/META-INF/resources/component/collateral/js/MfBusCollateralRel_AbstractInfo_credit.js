;
var  MfBusCollateralRel_AbstractInfo = function(window,$){
	var _cusNo = '';
	var _relId = '';
	var _creditAppId = '';
	var _busModel = '';
	var _operable = '';
	var _type = '';
	var _fincId = '';
	var _fincMainId = '';
	var _busEntrance = '';
	var _vouType = '';
	var _collateralRelId='';
	var _appId = '';
	var _init = function(){
		$.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralInfoAjax",
			data:{appId:MfBusCollateralRel_AbstractInfo.creditAppId,cusNo:MfBusCollateralRel_AbstractInfo.cusNo},
			type:"POST",
			dataType:"json",
			success:function(data) {
				if (data.flag == "success") {
					// 填充业务信息
					_setCollateralInfo(data.collateralRel);
					MfBusCollateralRel_AbstractInfo.collateralRelId = data.collateralRel.busCollateralId;
					$("#pleInfo").removeClass("hide");
					$("#pleInfo").addClass("show");
					if(data.busModel!="5"){
						if(data.evalFlag=="0"){
							$("#abstractInfo-envalueAmt").html('<i class="i i-rmb"></i>未评估').attr("class","content-span unregistered").css("font-size","14px");
							$("#abstractInfo-receiptsAmount").html("未评估").attr("class","content-span unregistered").css("font-size","14px");
						}
					}
				}else{
					$("#pleInfono").removeClass("hide");
					$("#pleInfono").addClass("show");
					if(MfBusCollateralRel_AbstractInfo.busModel=="5"){
						$("#title").html("应收账款");
						$("#noPledge").html("暂无应收账款");
						$("#titleLi").attr("class","i i-rece font-icon");
					}
					if(MfBusCollateralRel_AbstractInfo.relId!=""&&MfBusCollateralRel_AbstractInfo.relId!=null){
						var vouType1 =MfBusCollateralRel_AbstractInfo.vouType;
						var vouTypeShort =MfBusCollateralRel_AbstractInfo.vouType.split(".")[0];
						if(MfBusCollateralRel_AbstractInfo.vouType=="1"||vouTypeShort=="1"){
							$("#creditPledge").show();
						}else{
							$("#addCollateralInfo").show();
						}
					}else{
						$("#noPledge").show();
					}
				}
			},
			error:function() {
	
			}
		});
	};
	var _setCollateralInfo=function(collateralRel){
		var title = "反担保信息";
		var titleClass="i i-pledge font-icon";
		if(MfBusCollateralRel_AbstractInfo.busModel=="5"){
			title="应收账款";
			titleClass="i i-rece font-icon";
		}
		//控制担保比例（0~100）%
		var collateral_ratio = '';

		if(collateralRel.collateralRate>100){
			collateral_ratio=collateralRel.collateralRate=100;
		}else{
			collateral_ratio=collateralRel.collateralRate;
		}
		var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_10">'
				+ '<button type="button" class="btn btn-font-pledge padding_left_15" onclick="MfBusCollateralRel_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');">'
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
				+ '<button type="button" class="btn btn-font-qiehuan" onclick="MfBusCollateralRel_AbstractInfo.getCollateralRelInfo(\''+collateralRel.appId+'\',\''+collateralRel.busCollateralId+'\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>'
				+ '</div>'
				+ '<div class="row clearfix pledge-rece" id="pledgeInfo">'

				+ '<div>'
				+'<table><tbody><tr>'
				
				+'<td>'
				+'<p class="ptitle">反担保数量</p>'
				+'<p class="pvalue">'
				+'<span class="span-value" id="abstractInfo-envalueAmt">'+ collateralRel.pledgeNum+'</span>'
				+'<span>&nbsp;</span>'
				+'</p>'
				+'</td>'
				
				
				
				/*+'<td>'
				+'<p class="ptitle">担保金额</p>'
				+'<p class="pvalue">'
				+'<span class="span-value" id="abstractInfo-envalueAmt">'+ collateralRel.collateralAmt.toFixed(2)+'</span>'
				+'<span>&nbsp;万</span>'
				+'</p>'
				+'</td>'*/
				/*+'<td>'
				+'<p class="ptitle">担保比例</p>'
				+'<p class="pvalue">'
				+'<span class="span-value" id="abstractInfo-receiptsAmount">'+ collateral_ratio.toFixed(2)+'</span>'
				+'<span>&nbsp;%</span>'
				+'</p>'
				+'</td>'*/
				+'</tr></tbody></table>'
				+'</div>'
				+ '</div>';
		$("#pleInfo .cont-row").html(htmlStr);
	};
	var _getCollateralRelInfo=function(appId,busCollateralId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?busEntrance="+MfBusCollateralRel_AbstractInfo.busEntrance+"&operable="+MfBusCollateralRel_AbstractInfo.operable
			+"&cusNo="+MfBusCollateralRel_AbstractInfo.cusNo+"&relId="+appId+"&appId="+appId+"&busCollateralId="+MfBusCollateralRel_AbstractInfo.busCollateralId
			+"&fincMainId="+MfBusCollateralRel_AbstractInfo.fincMainId
			+"&entrance="+MfBusCollateralRel_AbstractInfo.entrance+"&type="+MfBusCollateralRel_AbstractInfo.type+"&fincId="+MfBusCollateralRel_AbstractInfo.fincId+"&creditAppId="+MfBusCollateralRel_AbstractInfo.creditAppId;
	};
	
	var _addCollateralInfo = function(){
		top.addCollateral=false
		var url = "";
		if(MfBusCollateralRel_AbstractInfo.busEntrance == 'credit' 
			|| MfBusCollateralRel_AbstractInfo.busEntrance == 'cus_credit' 
				|| MfBusCollateralRel_AbstractInfo.busEntrance == 'cus_core_company' 
					|| MfBusCollateralRel_AbstractInfo.busEntrance == 'cus_trench'){
			url=webPath+'/mfBusCollateralRel/insertInput?cusNo='+MfBusCollateralRel_AbstractInfo.cusNo+"&appId="+MfBusCollateralRel_AbstractInfo.relId+"&entrFlag=credit"+"&entrance=credit";
		}else{
			url=webPath+'/mfBusCollateralRel/insertInput?cusNo='+MfBusCollateralRel_AbstractInfo.cusNo+"&appId="+MfBusCollateralRel_AbstractInfo.relId+"&entrFlag=businessNoTask";
		}
		top.openBigForm(url,"新增押品",function(){
			if(top.addCollateral){
				top.LoadingAnimate.start();
				window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+MfBusCollateralRel_AbstractInfo.cusNo+"&appId="+MfBusCollateralRel_AbstractInfo.appId+"&relId="+MfBusCollateralRel_AbstractInfo.relId
					+"&busEntrance="+MfBusCollateralRel_AbstractInfo.busEntrance+"&entrance="+MfBusCollateralRel_AbstractInfo.entrance+"&creditAppId="+MfBusCollateralRel_AbstractInfo.creditAppId;;
			}
		});
	};
	return{
		init:_init,
		getCollateralRelInfo:_getCollateralRelInfo,
		setCollateralInfo:_setCollateralInfo,
		addCollateralInfo:_addCollateralInfo,
		cusNo:_cusNo,
		relId:_relId,
		creditAppId:_creditAppId,
		busModel:_busModel,
		operable:_operable,
		type:_type,
		fincId:_fincId,
		fincMainId:_fincMainId,
		busEntrance:_busEntrance,
		vouType:_vouType,
		collateralRelId:_collateralRelId,
		appId:_appId
	};
}(window,jQuery);