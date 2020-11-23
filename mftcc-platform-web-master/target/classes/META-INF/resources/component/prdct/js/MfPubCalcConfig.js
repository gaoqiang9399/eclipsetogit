;
var MfPubCalcConfig=function(window, $){
	//初始化产品核算配置信息
	var _init = function(data){
		$(".tabCont").html("").addClass("hide");
		var htmlStr = getPrdctCalcConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		//初始化属性上的绑定事件
		initBindEvent();
	};
	//产品核算配置Html
	var getPrdctCalcConfigHtml = function(data){
		var mfSysKind= data.mfSysKindBase;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div calcConfig"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content margin_top_15">';
		//融资期限
		htmlStr = htmlStr+ getTermTypeConfigHtml(mfSysKind,data.termTypeList);
		//融资金额
		htmlStr = htmlStr+ getFincAmtConfigHtml(mfSysKind);
		//利率类型
		htmlStr = htmlStr+ getRateTypeConfigHtml(mfSysKind,data.rateTypeList);
		//还款方式
		htmlStr = htmlStr+ getRepayTypeConfigHtml(mfSysKind,data.repayTypeList);
		//利息计算方式
		htmlStr = htmlStr+getNormCalcTypeHtml(mfSysKind,data.normCalcTypeList);
		//按月结息时不足一月利息计算方式
		htmlStr = htmlStr+getSecondNormCalcTypeHtml(mfSysKind);
		//利息计算基数
		htmlStr = htmlStr+getInstCalcBaseHtml(mfSysKind);
		//利息减免
		htmlStr = htmlStr+getInterestDerateHtml(mfSysKind);
		//节假日利息收取设置
		htmlStr = htmlStr+getFestivalTypeHtml(mfSysKind);
		//允许最后一期结余
		htmlStr = htmlStr+getLastTermBalanceTypeHtml(mfSysKind);
		//还款日设置
		htmlStr = htmlStr+getRepayDateSetHtml(mfSysKind);
		//利息收息方式
		htmlStr = htmlStr+getInstCollectTypeHtml(mfSysKind,data.instCollectTypeList);
		//费用收取方式
		htmlStr = htmlStr+getFeeCollectWayHtml(mfSysKind,data.feeCollectWayList);
		//利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
		htmlStr = htmlStr+getLsbqChargeIntstHtml(mfSysKind);
		//预先支付利息收取方式
		htmlStr = htmlStr+getPreInstCollectTypeHtml(mfSysKind);
		//提前还款
		htmlStr = htmlStr+getPreRepayTypeHtml(mfSysKind);
		//提前还款利息
		htmlStr = htmlStr+getPreRepayInstCalcHtml(mfSysKind,data.preRepayIntsTypeList);
		//核算高级设置
		htmlStr = htmlStr+getCalcAdvancedSetHtml(mfSysKind,data);
		
		htmlStr = htmlStr+'</div></div></div>';
		
		return htmlStr;
		
	};
	
	//初始化属性上的绑定事件
	var initBindEvent = function(){
		termTypeBindEvent();
		fincAmtBindEvent();
		rateTypeBindEvent();
		repayTypeBindEvent();
		normCalcTypeBindEvent();
		interestDerateBindEvent();
		festivalTypeBindEvent();
		lastTermBalanceTypeBindEvent();
		repayDateBindEvent();
		instCollectTypeBindEvent();
		feeCollectWayBindEvent();
		lsbqChargeIntstBindEvent();
		secondNormCalcTypeBindEvent();
		instCalcBaseBindEvent();
		preInstCollectTypeBindEvent();
		preRepayTypeBindEvent();
		preRepayInstCalcBindEvent();
		yearDaysBindEvent();
		rateDecimalDigitsBindEvent();
		icTypeBindEvent();
		returnPlanPointBindEvent();
		feePlanMergeBindEvent();
		multiplePlanMergeBindEvent();
		balanceDealTypeBindEvent();
		overCmpdRateInputtypeBindEvent();
		cmpdRateTypeBindEvent();
		penaltyFincBindEvent();
		preRepayPenaltyBindEvent();
	};
	
	//融资期限
	var  getTermTypeConfigHtml = function(mfSysKind,termTypeList){
		var termTypeUnit="个月";
		if(mfSysKind.termType=="2"){
			termTypeUnit="天";
		}
		var subHtmlStr = "";
		$.each(termTypeList,function(i,parmDic){
			var checkStr="";
			if(mfSysKind.termType==parmDic.optCode){
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="termType" value="'+parmDic.optCode+'" '+checkStr+'>'+parmDic.optName+'</span>';
		});
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div termType" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title">'
		+'<span>融资期限：</span>'
		+'<span class="item-title-desc padding_left_5">'+mfSysKind.kindName+'的期限介于<span id="minTermDesc">'+mfSysKind.minTerm+'</span>到<span id="maxTermDesc">'+mfSysKind.maxTerm+'</span><span class="termTypeUnit">'+termTypeUnit+'</span>之间'
		+'</div>'
		+'<div class="item-content padding_left_5 margin_bottom_5">'
		+ subHtmlStr
		+'<span class="margin_left_60"> 期限：'
		+'<span id="minTermRead" class="span-read"><span class="span-text">'+mfSysKind.minTerm+'</span><i data-type="minTerm" class="i i-editable"></i></span>'
		+'<span id="minTermEdit" class="span-edit"><input type="text" name="minTerm" value="'+mfSysKind.minTerm+'"/><i class="i i-duihao ok"></i></span> 至 '
		+'<span id="maxTermRead" class="span-read"><span class="span-text">'+mfSysKind.maxTerm+'</span><i data-type="maxTerm" class="i i-editable"></i></span>'
		+'<span id="maxTermEdit" class="span-edit"><input type="text" name="maxTerm" value="'+mfSysKind.maxTerm+'"/><i class="i i-duihao ok"></i></span>'
		+'<span class="termTypeUnit">'+termTypeUnit+'</span></span>'
		+'</div>'
		+'<div class="item-content padding_left_5">'
		+'<span>默认期限：<input type="text" class="termDef'+mfSysKind.kindNo+'" maxlength="5" value=""/><span class="termTypeUnit">'+termTypeUnit+'</span></span>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//融资期限绑定事件
	var termTypeBindEvent = function(){
		$(".termType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.termType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				var termTypeUnit="个月";
				if(mfSysKindTmp.termType=="2"){
					termTypeUnit="天";
				}
				$(".termTypeUnit").text(termTypeUnit);
				
			});
		});
		//期限编辑小笔事件
		$(".termType .i-editable").bind("click",function(){
			var type=$(this).data("type");
			if("minTerm"==type){
				$("#minTermRead").css("display","none");
				$("#minTermEdit").css("display","inline-block");
			}else if("maxTerm"==type){
				$("#maxTermRead").css("display","none");
				$("#maxTermEdit").css("display","inline-block");
			}
		});
		//期限编辑input框事件
		$(".termType .ok").bind("click",function(){
			var $input = $(this).parents(".span-edit").find("input");
			var type = $input.attr("name");
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if("minTerm"== type){
				mfSysKind.minTerm=$input.val();
			}else if("maxTerm" == type){
				mfSysKind.maxTerm=$input.val();
			}
			top.type=type;
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if("minTerm"== top.type){
					$("#minTermDesc").text(mfSysKindTmp.minTerm);
					$("#minTermRead .span-text").text(mfSysKindTmp.minTerm);
					$("#minTermEdit").css("display","none");
					$("#minTermRead").css("display","inline-block");
				}else if("maxTerm"== top.type){
					$("#maxTermDesc").text(mfSysKindTmp.maxTerm);
					$("#maxTermRead .span-text").text(mfSysKindTmp.maxTerm);
					$("#maxTermEdit").css("display","none");
					$("#maxTermRead").css("display","inline-block");
				}
			});
		});
	};
	//融资金额
	var getFincAmtConfigHtml = function(mfSysKind){
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div fincAmt" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title">'
		+'<span>融资金额：</span>'
		+'<span class="item-title-desc padding_left_5">'+mfSysKind.kindName+'的融资金额介于<span id="minAmtDesc">'+mfSysKind.minAmt+'</span>到<span id="maxAmtDesc">'+mfSysKind.maxAmt+'</span>元之间</span>'
		+'</div>'
		+'<div class="item-content padding_left_5 margin_bottom_5">'
		+'<span> 金额：'
		+'<span id="minAmtRead" class="span-read"><span class="span-text">'+mfSysKind.minAmt+'</span><i data-type="minAmt" class="i i-editable"></i></span>'
		+'<span id="minAmtEdit" class="span-edit"><input type="text" name="minAmt" value="'+mfSysKind.minAmt+'"/><i class="i i-duihao ok"></i></span> 至 '
		+'<span id="maxAmtRead" class="span-read"><span class="span-text">'+mfSysKind.maxAmt+'</span><i data-type="maxAmt" class="i i-editable"></i></span>'
		+'<span id="maxAmtEdit" class="span-edit"><input type="text" name="maxAmt" value="'+mfSysKind.maxAmt+'"/><i class="i i-duihao ok"></i></span>'
		+'</span>'
		+'</div>'
		+'<div class="item-content padding_left_5">'
		+'<span>默认金额： <input type="text" class="amtDef'+mfSysKind.kindNo+'" maxlength="20" value=""/>元</span>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//融资期限绑定事件
	var fincAmtBindEvent = function(){
		//期限编辑小笔事件
		$(".fincAmt .i-editable").bind("click",function(){
			var type=$(this).data("type");
			if("minAmt"==type){
				$("#minAmtRead").css("display","none");
				$("#minAmtEdit").css("display","inline-block");
			}else if("maxAmt"==type){
				$("#maxAmtRead").css("display","none");
				$("#maxAmtEdit").css("display","inline-block");
			}
		});
		//期限编辑input框事件
		$(".fincAmt .ok").bind("click",function(){
			var $input = $(this).parents(".span-edit").find("input");
			var type = $input.attr("name");
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if("minAmt"== type){
				mfSysKind.minAmt=$input.val();
			}else if("maxAmt" == type){
				mfSysKind.maxAmt=$input.val();
			}
			top.type=type;
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if("minAmt"== top.type){
					$("#minAmtDesc").text(mfSysKindTmp.minAmt);
					$("#minAmtRead .span-text").text(mfSysKindTmp.minAmt);
					$("#minAmtEdit").css("display","none");
					$("#minAmtRead").css("display","inline-block");
				}else if("maxAmt"== top.type){
					$("#maxAmtDesc").text(mfSysKindTmp.maxAmt);
					$("#maxAmtRead .span-text").text(mfSysKindTmp.maxAmt);
					$("#maxAmtEdit").css("display","none");
					$("#maxAmtRead").css("display","inline-block");
				}
			});
		});
	};
	//利率类型
	var getRateTypeConfigHtml = function(mfSysKind,rateTypeList){
		var nameStr="";
		var rateTypeShow="";
		var subHtmlStr="";
		$.each(rateTypeList,function(i,parmDic){
			var checkStr="";
			if(mfSysKind.rateType==parmDic.optCode){
				rateTypeShow = parmDic.remark;
				nameStr = parmDic.optName;
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" '+checkStr+' name="rateType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			
		});
		var htmlStr=""
		//获取
		if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
			htmlStr = '<div class="item-div rateType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>利率类型：</span>'
			+'<span class="item-title-desc padding_left_5">'+mfSysKind.kindName+'的融资利率使用<span id="rateType'+mfSysKind.kindNo+'" >'+nameStr+'</span>格式，默认利率为<span class="fincRate'+mfSysKind.kindNo+'">'+mfSysKind.fincRate+'</span><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
			+'<span id="rateTypeShowName'+mfSysKind.kindNo+'" >,默认逾期利率上浮为</span><span class="overFltRateDef'+mfSysKind.kindNo+'">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName'+mfSysKind.kindNo+'Show">%</span>'
			+'</span>'
			+'</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span>'
			+ subHtmlStr
			+ '</span>'
			+ '</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span>融资利率上限：<input type="text" class="maxFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.maxFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '<span class="padding_left_20">融资利率下限：<input type="text" class="minFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.minFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '<span class="padding_left_20">默认利率：<input type="text" class="fincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.fincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '<span class="hide" id="maxFincRateSpan'+mfSysKind.kindNo+'">'+mfSysKind.maxFincRate+'</span><span class="hide" id="minFincRateSpan'+mfSysKind.kindNo+'">'+mfSysKind.minFincRate+'</span>'
			+ '</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span  id="overFlotRateDef'+mfSysKind.kindNo+'" ><span id="overFlotRateDefName'+mfSysKind.kindNo+'">默认逾期利率上浮：</span><input type="text" class="overFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.overFltRateDef+'"/><span class="overFltRateDef'+mfSysKind.kindNo+'Show">%</span></span>'
			+ '</div>'
			+ '</div>';
		}else{//默认利率
			htmlStr = '<div class="item-div rateType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >利率类型：</span>'
			+'<span class="item-title-desc padding_left_5">'+mfSysKind.kindName+'的融资利率使用<span id="rateType'+mfSysKind.kindNo+'" >'+nameStr+'</span>格式，默认利率为<span class="fincRate'+mfSysKind.kindNo+'">'+mfSysKind.fincRate+'</span><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
			+'<span id="rateTypeShowName'+mfSysKind.kindNo+'" >,默认逾期利率为</span><span class="overFltRateDef'+mfSysKind.kindNo+'">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
			+'</span>'
			+'</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span>'
			+ subHtmlStr
			+ '</span>'
			+ '</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span >融资利率上限：<input type="text" class="maxFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.maxFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '<span class="padding_left_20">融资利率下限：<input type="text" class="minFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.minFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '<span class="padding_left_20">默认利率：<input type="text" class="fincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.fincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '</div>'
			+ '<div class="item-content margin_bottom_5 padding_left_5">'
			+ '<span><span id="overFlotRateDefName'+mfSysKind.kindNo+'">默认逾期利率：</span><input type="text" class="overFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.overFltRateDef+'"/><span class="overFltRateDef'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '</div>'
			+ '</div>';		
		}
		return htmlStr;
	};
	
	//利率类型绑定事件
	var rateTypeBindEvent = function(){
		$(".rateType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			var rateTypeShow;
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.rateType=$(this).val();
			if(mfSysKind.rateType=="1"){
				rateTypeShow = "%";
			}else if(mfSysKind.rateType=="2"){
				rateTypeShow = "‰";
			}else if(mfSysKind.rateType=="3"){
				rateTypeShow = "‱";
			}else if(mfSysKind.rateType=="4"){
				rateTypeShow = "%";
			}else if(mfSysKind.rateType=="5"){
				rateTypeShow = "%";
			}
			mfSysKind.fincRate=0.00;
			mfSysKind.minFincRate=0.00;
			mfSysKind.maxFincRate=0.00;
			var ajaxData = JSON.stringify(mfSysKind);
			top.rateTypeName=$(this).data("name");
			top.kindNo =mfSysKind.kindNo; 	
			mfSysKind.overCmpdRateInputtype=$("input[name='overCmpdRateInputtype"+mfSysKind.kindNo+"']:checked").val();
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				$(".rateType"+top.kindNo+"Show").text(rateTypeShow);
				$("#rateType"+top.kindNo).text(top.rateTypeName);
				$(".fincRate"+top.kindNo).text(0.00);
				$(".fincRate"+top.kindNo).val(0.00);
				$(".minFincRate"+top.kindNo).val(0.00);
				$(".maxFincRate"+top.kindNo).val(0.00);
				$("#minFincRateSpan"+top.kindNo).text(0.00);
				$("#maxFincRateSpan"+top.kindNo).text(0.00);
				if(mfSysKind.overCmpdRateInputtype=="1"){//利率
					$(".overFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
					$("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);	
					$("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
					//复利利率
					$(".cmpFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
				}else{//利率浮动
					$(".overFltRateDef"+top.kindNo+"Show").text("%");
					$("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
					$("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
					$(".overFltRateDef"+top.kindNo+"Show").text("%");
					//复利利率
					$(".cmpFltRateDef"+top.kindNo+"Show").text("%");					
				}
				//逾期利率上浮
				$(".overFltRateDef"+top.kindNo).text(0.00);
				$(".overFltRateDef"+top.kindNo).val(0.00);
			});
		});
		
		$(".rateType input[type=text]").bind("change",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			var classAttr=$(this).attr("class");
			var oldFincRate = $("span.fincRate"+mfSysKind.kindNo).text();
			var oldMinFinsRate = $("#minFincRateSpan"+mfSysKind.kindNo).text();
			var oldMaxFinsRate = $("#maxFincRateSpan"+mfSysKind.kindNo).text();

            var minFincRate,maxFincRate,fincRate;
			if(classAttr=="fincRate"+mfSysKind.kindNo){//贷款利率
				mfSysKind.fincRate=$(this).val();
				maxFincRate = $(".maxFincRate"+mfSysKind.kindNo).val();
				minFincRate = $(".minFincRate"+mfSysKind.kindNo).val();
				if ($(this).val() * 1 > maxFincRate*1) {//默认融资利率 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(oldFincRate);
					return false;
				}
				if ($(this).val() * 1 < minFincRate*1) {//默认融资利率 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(oldFincRate);
					return false;
				}
				
			}else if(classAttr=="overFltRateDef"+mfSysKind.kindNo){//逾期利率上浮
				mfSysKind.overFltRateDef=$(this).val();
			}else if(classAttr=="minFincRate"+mfSysKind.kindNo){//贷款利率下限
				mfSysKind.minFincRate=$(this).val();
				minFincRate = $(this).val();
				maxFincRate = $(".maxFincRate"+mfSysKind.kindNo).val();
				fincRate  = $("input.fincRate"+mfSysKind.kindNo).val();
				if ($(this).val() * 1 > maxFincRate*1) {//贷款利率下限 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '融资利率下限',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(oldMinFinsRate);
					return false;
				}
				if (fincRate * 1 < minFincRate*1) {//默认融资利率 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(oldMinFinsRate);
					return false;
				}
				
			}else if(classAttr=="maxFincRate"+mfSysKind.kindNo){//贷款利率上限
				mfSysKind.maxFincRate=$(this).val();
				maxFincRate = $(this).val();
				minFincRate = $(".minFincRate"+mfSysKind.kindNo).val();
				fincRate  = $("input.fincRate"+mfSysKind.kindNo).val();
				if ($(this).val() * 1 < minFincRate*1) {//贷款利率上限 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '融资利率上限',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(oldMaxFinsRate);
					return false;
				}
				if (fincRate * 1 > maxFincRate*1) {//默认融资利率 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(oldMaxFinsRate);
					return false;
				}

			}
			mfSysKind.rateType=$("input[name='rateType"+mfSysKind.kindNo+"']:checked").val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				$(".fincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.fincRate);
				$(".overFltRateDef"+mfSysKindTmp.kindNo).text(mfSysKindTmp.overFltRateDef);
				$(".minFincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.minFincRate);
				$(".maxFincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.maxFincRate);
				$("#minFincRateSpan"+mfSysKindTmp.kindNo).text(mfSysKindTmp.minFincRate);
				$("#maxFincRateSpan"+mfSysKindTmp.kindNo).text(mfSysKindTmp.maxFincRate);
			});
		});
	};
	
	//还款方式
	var getRepayTypeConfigHtml =function(mfSysKind,repayTypeList){
		var subStr = "";
		var repayTypeTip="";
		var repayTypeDefTip="";
		$.each(repayTypeList,function(i,parmDic){
			var flag=false;
			if(mfSysKind.repayType.indexOf(parmDic.optCode+"|")!=-1 || mfSysKind.repayType==parmDic.optCode){
				flag=true;
			}
			if(flag){
				repayTypeTip=repayTypeTip+parmDic.optName+"、";
				if(mfSysKind.repayTypeDef!=parmDic.optCode){
					subStr=subStr+'<span class="option-div" data-repaytype="'+parmDic.optCode+'" data-name="'+parmDic.optName+'"><span>'+parmDic.optName+'</span><i class="i i-sanjiaoduihao"></i></span>';
				}else{//默认还款方式
					subStr=subStr+'<span class="option-div selected" data-repaytype="'+parmDic.optCode+'" data-name="'+parmDic.optName+'"><span>'+parmDic.optName+'</span><i class="i i-sanjiaoduihao"></i></span>';
					repayTypeDefTip=parmDic.optName;
				}
			}
		});
		repayTypeTip = repayTypeTip.substring(0, repayTypeTip.length-1);
		//还款方式
		var htmlStr ='<div class="item-div repayType margin_bottom_10" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title">'
		+'<span>还款方式：</span>'
		+'<span class="item-title-desc padding_left_5">'+mfSysKind.kindName+'支持<span id="repayTypeTip'+mfSysKind.kindNo+'">'+repayTypeTip+'</span>，其中默认还款方式为：<span id="repayTypeDefTip'+mfSysKind.kindNo+'">'+repayTypeDefTip+'</span></span>'
		+'</div>'
		+'<div class="item-content padding_left_5">'
		+'<button class="btn btn-default btn-add">添加</button>'
		+'<span id="repayTypeOption'+mfSysKind.kindNo+'">'
		+ subStr
		+'</span>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//还款方式绑定事件
	var repayTypeBindEvent = function(){
		$(".repayType .btn-add").bind("click",function(){
			top.flag=false;
			top.itemId="";
			window.parent.openBigForm("MfSysKindAction_getKindObjectForSelect.action?kindNo="+kindNo+"&keyName=REPAY_TYPE","选择还款方式",function(){
				if(top.flag){
					var itemId = top.itemId;
					var mfSysKind={};
					mfSysKind.kindNo=kindNo;
					mfSysKind.repayType=itemId;
					var ajaxData = JSON.stringify(mfSysKind);
					MfSysKindConfig.updateKindConfig(ajaxData,function(data){
						var subStr = "";
						var repayTypeTip="";
						var repayTypeDefTip="";
						$.each(data.repayTypeList,function(i,mapObj){
							repayTypeTip=repayTypeTip+mapObj.repayTypeName+"、";
							if(mapObj.repayTypeDef=="0"){
								subStr=subStr+'<span class="option-div" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
							}else{//默认还款方式
								subStr=subStr+'<span class="option-div selected" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
								repayTypeDefTip=mapObj.repayTypeName;
							}
						});
						repayTypeTip = repayTypeTip.substring(0, repayTypeTip.length-1);
						$("#repayTypeOption"+data.mfSysKind.kindNo).html(subStr);
						
						$("#repayTypeTip"+kindNo).text(repayTypeTip);
						$("#repayTypeDefTip"+kindNo).text(repayTypeDefTip);
						
						$("#repayTypeOption"+kindNo+" .option-div").bind("click",function(){
							var $thisItemDiv = $(this).parents(".item-div");
							$thisItemDiv.find(".option-div").removeClass("selected");
							$(this).addClass("selected");
							var mfSysKind={};
							mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
							mfSysKind.repayTypeDef=$(this).data("repaytype");
							var ajaxData = JSON.stringify(mfSysKind);
							top.repayTypeDef=$(this).data("name");
							MfSysKindConfig.updateKindConfig(ajaxData,function(){
								$("#repayTypeDefTip"+kindNo).text(top.repayTypeDef);
							});
						});
					});
				}
			},"790px","450px");
		});
		
		$(".repayType .option-div").bind("click",function(){
			var $thisItemDiv = $(this).parents(".item-div");
			$thisItemDiv.find(".option-div").removeClass("selected");
			$(this).addClass("selected");
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.repayTypeDef=$(this).data("repaytype");
			var ajaxData = JSON.stringify(mfSysKind);
			top.repayTypeDef=$(this).data("name");
			MfSysKindConfig.updateKindConfig(ajaxData,function(){
				$("#repayTypeDefTip"+kindNo).text(top.repayTypeDef);
			});
		});
		
		
	};
	
	//利息计算方式
	var getNormCalcTypeHtml = function(mfSysKind,normCalcTypeList){
		var subHtmlStr="";
		$.each(normCalcTypeList,function(i,parmDic){
			var checkStr="";
			if(mfSysKind.normCalcType==parmDic.optCode){
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr+'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="normCalcType" '+checkStr+' value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
		});
		var htmlStr ='<div class="item-div normCalcType" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title margin_bottom_10">'
		+'<span>利息计算方式：</span>'
		+'</div>'
		+'<div class="item-content padding_left_5">'
		+ subHtmlStr
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//利息计算方式绑定事件
	var normCalcTypeBindEvent = function(){
		$(".normCalcType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.normCalcType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if(mfSysKindTmp.normCalcType=="3"){
					$(".secondCalcType").removeClass("show");
					$(".secondCalcType").addClass("hide");
					
				}else{
					$(".secondCalcType").removeClass("hide");
					$(".secondCalcType").addClass("show");
				}
			});
		});
	};
	
	//按月结息时不足一月利息计算方式second_norm_calc_type
	var getSecondNormCalcTypeHtml = function(mfSysKind){
		var checkStr2="";
		var checkStr3="";
		var showOrHide="show";
		if(mfSysKind.normCalcType=="3"){//如果是按月计息，不足一月按月计息
			showOrHide="hide";
		}
		if(mfSysKind.secondNormCalcType=="2"){
			checkStr2='checked="checked"';
		}else if(mfSysKind.secondNormCalcType=="3"){
			checkStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div secondCalcType '+showOrHide+'" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title margin_bottom_10">'
		+'<span >不足一月利息计算方式：</span>'
		+'</div>'
		+ '<div class="item-content padding_left_5">'
		+ '<span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="secondCalcType" value="2" '+checkStr2+'/>按月计息</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="secondCalcType" value="3" '+checkStr3+'/>按日计息</span>'
		+ '</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	//按月结息时不足一月利息计算方式绑定事件
	var secondNormCalcTypeBindEvent = function(){
		$(".secondCalcType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.secondNormCalcType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//利息计算基数
	var getInstCalcBaseHtml = function(mfSysKind){
		var checkStr1="";
		var checkStr2="";
		if(mfSysKind.instCalcBase=="1"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.instCalcBase=="2"){
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div instCalcBase" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title margin_bottom_10">'
		+'<span >利息计算基数：</span>'
		+'</div>'
		+ '<div class="item-content padding_left_5">'
		+ '<span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="instCalcBase" value="1" '+checkStr1+'/>贷款金额</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="instCalcBase" value="2" '+checkStr2+'/>贷款余额</span>'
		+ '</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	//利息计算基数绑定事件
	var instCalcBaseBindEvent = function(){
		$(".instCalcBase input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.instCalcBase=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};

	
	//利息减免
	var getInterestDerateHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.interestDerateFlag=="0"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div instDerate" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>启用利息减免：</span>'
			+'<span class="item-title-desc padding_left_15">还款时，利息支持减免优惠，包括正常利息和罚息</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//利息减免绑定点击事件
	var interestDerateBindEvent = function(){
		$(".instDerate .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			
			if($(this).hasClass("curChecked")){//禁用
				mfSysKind.interestDerateFlag="0";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.interestDerateFlag="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式
	var getFestivalTypeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.festivalType=="0"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div festivalType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>节假日或周末利息收取方式：</span>'
			+'<span class="item-title-desc padding_left_15">还款日与法定节假日或周末重合时逾期后逾期利息、复利不收取</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式绑定点击事件
	var festivalTypeBindEvent = function(){
		$(".festivalType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).hasClass("curChecked")){
				mfSysKind.festivalType="1";	//收取
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.festivalType="0";//不收取
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//允许最后一期结余：0-不允许、1-允许  getLastTermBalanceTypeHtml
	var getLastTermBalanceTypeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.lastTermBalanceType=="1"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div lastTermBalanceType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>允许最后一期结余：</span>'
			+'<span class="item-title-desc padding_left_15">还款时允许最后一期还款存在结余</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//允许最后一期结余：0-不允许、1-允许绑定点击事件 lastTermBalanceTypeBindEvent
	var lastTermBalanceTypeBindEvent = function(){
		$(".lastTermBalanceType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).hasClass("curChecked")){
				mfSysKind.lastTermBalanceType="0";	//允许
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.lastTermBalanceType="1";//不允许
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//还款日设置
	var getRepayDateSetHtml = function(mfSysKind){
		var checkStr1="",checkStr2="",checkStr3="",showOrHide='style="display:none;"';
		var repayDateSetTip="";
		if(mfSysKind.repayDateSet=="1"){
			checkStr1='checked="checked"';
			repayDateSetTip="在贷款发放日";
		}else if(mfSysKind.repayDateSet=="2"){
			checkStr2='checked="checked"';
			repayDateSetTip="在月末";
		}else if(mfSysKind.repayDateSet=="3"){
			checkStr3='checked="checked"';
			showOrHide = 'style="display:inline-block;"';
			repayDateSetTip='固定在每月的<span id="repayDateDefSpan">'+mfSysKind.repayDateDef+'</span>日';
		}
		var htmlStr = '<div class="item-div repayDate" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >还款日设置：</span>'
			+'<span class="item-title-desc padding_left_15">'+mfSysKind.kindName+'<span id="repayDateSetTip">'+repayDateSetTip+'</span>还款</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="1" '+checkStr1+' data-name="贷款发放日"/>贷款发放日</span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="2" '+checkStr2+' data-name="月末"/>月末</span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="3" '+checkStr3+' data-name="固定还款日"/>固定还款日</span>'
			+ '</span>'
			+'<span id="repayDateDef" '+showOrHide+' class="padding_left_15 color_black">默认还款日：<input type="text" name="repayDateDef" value="'+mfSysKind.repayDateDef+'"/></span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//还款日设置绑定事件
	var repayDateBindEvent = function(){
		$(".repayDate input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.repayDateSet=$(this).val();
			mfSysKind.repayDateDef="";
			var ajaxData = JSON.stringify(mfSysKind);
			top.repayDateSetName=$(this).data("name");
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var kindObj = data.mfSysKind;
				if(kindObj.repayDateSet=="3"){
					$("#repayDateDef").css("display","inline-block");
					var repayDateDefTmp = "";
					if(kindObj.repayDateDef!=null){
						repayDateDefTmp=kindObj.repayDateDef;
					}
					$("#repayDateSetTip").html('固定在每月的<span id="repayDateDefSpan">'+repayDateDefTmp+'</span>日');
					$("input[name=repayDateDef]").val(repayDateDefTmp);
				}else{
					$("#repayDateDef").css("display","none");
					$("#repayDateSetTip").text("在"+top.repayDateSetName);
				}
			});
		});
		$(".repayDate input[type=text]").bind("change",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.repayDateDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				$("#repayDateDefSpan").text(mfSysKindTmp.repayDateDef);
			});
		});
		
		
	};
	
	//还款时利随本清的利息收取
	var getLsbqChargeIntstHtml = function(mfSysKind){
		var checkStr1="",checkStr2="",showOrHide='style="display:none;"';
		var lsbqChargeIntstTip="";
		if(mfSysKind.lsbqChargeIntst=="1"){
			checkStr1='checked="checked"';
			lsbqChargeIntstTip="在还款时分次收取部分利息";
		}else if(mfSysKind.lsbqChargeIntst=="2"){
			checkStr2='checked="checked"';
			lsbqChargeIntstTip="在还款时一次收取全部利息";
		}
		var htmlStr = '<div class="item-div lsbqChargeInts" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >利随本清的利息收取：</span>'
			+'<span class="item-title-desc padding_left_15">'+mfSysKind.kindName+'<span id="lsbqChargeIntstTip">'+lsbqChargeIntstTip+'</span></span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="1" '+checkStr1+' data-name="分次收取部分利息"/><span id="lsbqChargeIntst_span1">分次收取部分利息</span></span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="2" '+checkStr2+' data-name="一次收取全部利息"/><span id="lsbqChargeIntst_span2">一次收取全部利息</span></span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	var lsbqChargeIntstBindEvent = function(){
		$(".lsbqChargeInts input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.lsbqChargeIntst=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var kindObj = data.mfSysKind;
				$("#lsbqChargeIntstTip").text("在还款时"+$("#lsbqChargeIntst_span"+kindObj.lsbqChargeIntst).text());
			});
		});
	};
	
	//利息收息方式
	var getInstCollectTypeHtml = function(mfSysKind,instCollectTypeList){
		var inputStr="";
		$.each(instCollectTypeList,function(i,parmDic){
			if(mfSysKind.interestCollectType==parmDic.optCode){
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" checked="checked" name="interestCollectType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}else{
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="interestCollectType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}
			
		});
		var htmlStr = '<div class="item-div instCollect" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >利息收息方式：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ inputStr
			+ '</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	var instCollectTypeBindEvent = function(){
		$(".instCollect input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.interestCollectType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//费用收取方式
	var getFeeCollectWayHtml = function(mfSysKind,feeCollectWayList){
		var inputStr="";
		$.each(feeCollectWayList,function(i,parmDic){
			if(mfSysKind.feeCollectWay==parmDic.optCode){
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" checked="checked" name="feeCollectWay" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}else{
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="feeCollectWay" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}
			
		});
		var htmlStr = '<div class="item-div feeCollectWay" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title margin_bottom_10">'
		+'<span >费用收取方式：</span>'
		+'</div>'
		+ '<div class="item-content padding_left_5">'
		+ '<span>'
		+ inputStr
		+ '</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	var feeCollectWayBindEvent = function(){
		$(".feeCollectWay input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.feeCollectWay=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//预先支付利息收取方式
	var getPreInstCollectTypeHtml = function(mfSysKind){
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		if(mfSysKind.preInstCollectType=="1"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.preInstCollectType=="2"){
			checkStr2='checked="checked"';
		}else if(mfSysKind.preInstCollectType=="3"){
			checkStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div preInstCollect" data-kindno="'+mfSysKind.kindNo+'">'
		+'<div class="item-title margin_bottom_10">'
		+'<span >预先支付利息收取方式：</span>'
		+'</div>'
		+ '<div class="item-content padding_left_5">'
		+ '<span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="1" '+checkStr1+'/>合并第一期</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="2" '+checkStr2+'/>独立一期</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="3" '+checkStr3+'/>放款时收取</span>'
		+ '</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	var preInstCollectTypeBindEvent = function(){
		$(".preInstCollect input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.preInstCollectType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//提前还款
	var getPreRepayTypeHtml = function(mfSysKind){
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		if(mfSysKind.preRepayType=="1"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.preRepayType=="2"){
			checkStr2='checked="checked"';
		}else if(mfSysKind.preRepayType=="3"){
			checkStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div preRepayType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >提前还款本金：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="2" '+checkStr2+'/>偿还部分剩余本金</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="1" '+checkStr1+'/>偿还全部剩余本金</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="3" '+checkStr3+'/>一次性偿还所有未还本金、利息</span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	var preRepayTypeBindEvent = function(){
		$(".preRepayType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.preRepayType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//提前还款利息计算
	var getPreRepayInstCalcHtml = function(mfSysKind,preRepayIntsTypeList){
		var preRepayInstCalcTip ="";
		if(mfSysKind.termInstMustBack=="1"){
			preRepayInstCalcTip="必须结清当期本金和利息方可进行提前还款";
		}else{
			if(mfSysKind.preRepayInstAccoutBase=="1"){
				preRepayInstCalcTip="提前还款时按借据余额计算利息";
			}else if(mfSysKind.preRepayInstAccoutBase=="2"){
				preRepayInstCalcTip="提前还款时按提前还款本金计算利息";
			}else if(mfSysKind.preRepayInstAccoutBase=="3"){
				preRepayInstCalcTip="提前还款时只还本金，分段计算利息";
			}
		}
		var inputStr ="";
		var dataIdStr="";
		$.each(preRepayIntsTypeList,function(i,parmDic){
			if(parmDic.optCode=="0"){
				dataIdStr='data-id="termInstMustBack"';
			}else{
				dataIdStr="";
			}
			if(mfSysKind.preRepayInstAccoutBase==parmDic.optCode){
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" '+dataIdStr+' checked="checked" name="preRepayInstAccoutBase" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}else{
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" '+dataIdStr+' name="preRepayInstAccoutBase" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}
		});
		var htmlStr = '<div class="item-div preRepayInstCalc" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span >提前还款利息：</span>'
			+'<span class="item-title-desc padding_left_15">'+mfSysKind.kindName+'要求<span id="preRepayInstCalcTip">'+preRepayInstCalcTip+'</span></span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
				+'<span id="preRepayInstAccoutBase" class="padding_left_10">'
				+ inputStr
				+'</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//提前还款利息计算
	var preRepayInstCalcBindEvent = function(){
		$(".preRepayInstCalc input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).data("id")=="termInstMustBack"){
				mfSysKind.termInstMustBack="1";
				mfSysKind.preRepayInstAccoutBase=$(this).val();
				$("#preRepayInstCalcTip").text("必须结清当期本金和利息方可进行提前还款");
			}else{
				mfSysKind.preRepayInstAccoutBase=$(this).val();
				mfSysKind.termInstMustBack="0";
				$("#preRepayInstCalcTip").text("提前还款时"+$(this).data("name")+"计算利息");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	
	
	//一年天数设置
	var getYearDaysHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.yearDays=="360"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.yearDays=="365"){
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div yearDays" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >年天数：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
				+ '<span>'
					+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="yearDays" value="360" '+checkStr1+'/>360天</span>'
					+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="yearDays" value="365" '+checkStr2+'/>365天</span>'
				+'</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//年天数设置绑定事件
	var yearDaysBindEvent = function(){
		$(".yearDays input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.yearDays=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//利率小数位数设置
	var getRateDecimalDigitsHtml = function(mfSysKind,rateDigitsList){
		var inputStr="";
		$.each(rateDigitsList,function(i,parmDic){
			if(mfSysKind.rateDecimalDigits==parmDic.optCode){
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" checked="checked" name="rateDecimalDigits" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}else{
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="rateDecimalDigits" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}
		});
		
		var htmlStr = '<div class="item-div rateDecimalDigits" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >利率小数位数：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
				+ '<span>'
				+ inputStr
				+'</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//利率小数位数设置绑定事件
	var rateDecimalDigitsBindEvent = function(){
		$(".rateDecimalDigits input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.rateDecimalDigits=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//计息方式
	var getIcTypeHtml = function(mfSysKind,icTypeList){
		var inputStr ="";
		$.each(icTypeList,function(i,parmDic){
			if(mfSysKind.icType==parmDic.optCode){
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" checked="checked" name="icType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}else{
				inputStr = inputStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="icType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			}
			
		});
		var htmlStr = '<div class="item-div icType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >计息方式：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ inputStr
			+ '</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//计息方式绑定事件
	var icTypeBindEvent = function(){
		$(".icType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.icType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//还款计划保留位数
	var getReturnPlanPointHtml = function(mfSysKind){
		var checkStr1="",checkStr2="",checkStr3="";
		if(mfSysKind.returnPlanPoint=="2"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.returnPlanPoint=="1"){
			checkStr2='checked="checked"';
		}else if(mfSysKind.returnPlanPoint=="0"){
			checkStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div returnPlanPoint" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >还款计划保留位数：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="2" '+checkStr1+'/>保留两位</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="1" '+checkStr2+'/>保留一位</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="0" '+checkStr3+'/>不保留</span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		
		return htmlStr;
	};
	//还款计划保留位数绑定事件
	var returnPlanPointBindEvent = function(){
		$(".returnPlanPoint input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.returnPlanPoint=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//还款计划小数舍入方式
	var getReturnPlanRoundHtml = function(mfSysKind){
		var htmlStr = '<div class="item-div returnPlanRound" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >还款计划小数舍入方式：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanRound" value="2" checked="checked"/>四舍五入</span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	//费用计划是否与还款计划合并
	var getFeePlanMergeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.feePlanMerge=="0"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div feePlanMerge" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>费用计划与还款计划合并</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
		return htmlStr;
	};
	//费用计划是否与还款计划合并绑定事件
	var feePlanMergeBindEvent = function(){
		$(".feePlanMerge .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).hasClass("curChecked")){//禁用
				mfSysKind.feePlanMerge="0";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.feePlanMerge="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//多次放款还款计划合并
	var getMultipleLoanPlanMergeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.multipleLoanPlanMerge=="0"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div multiplePlanMerge" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_20">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>多次放款还款计划合并</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
		
		return htmlStr;
	};
	
	//费用计划是否与还款计划合并绑定事件
	var multiplePlanMergeBindEvent = function(){
		$(".multiplePlanMerge .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).hasClass("curChecked")){//禁用
				mfSysKind.multipleLoanPlanMerge="0";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.multipleLoanPlanMerge="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//还款顺序
	var getRepaymentOrderHtml = function(mfSysKind){
		var htmlStr = '<div class="item-div">'
			+'<div class="item-title margin_bottom_20">'
			+'<span >还款顺序：</span>'
			+'<span class="item-title-desc padding_left_15">罚息>违约金>利息>费用>本金</span>'
			+'</div>'
			+ '</div>';
		
		return htmlStr;
	};
	//结余处理方式
	var getBalanceDealTypeHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.balanceDealType=="1"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div balanceDealType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >结余处理方式：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="balanceDealType" value="1" '+checkStr1+'/>冲抵贷款</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="balanceDealType" value="2" '+checkStr2+'/>退款</span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		
		return htmlStr;
	};
	
	//结余处理方式
	var balanceDealTypeBindEvent = function(){
		$(".balanceDealType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.balanceDealType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//逾期利率复利利率输入类型
	var getOverCmpdRateInputtypeHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.overCmpdRateInputtype=="0"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div overCmpdRateInputtype" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >逾期利率复利利率输入类型：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+ '<span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdRateInputtype" value="0" '+checkStr1+'/>利率浮动</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdRateInputtype" value="1" '+checkStr2+'/>利率</span>'
			+ '</span>'
			+ '</div>'
			+ '</div>';
		
		return htmlStr;
	};
	
	//逾期利率复利利率输入类型 
	var overCmpdRateInputtypeBindEvent = function(){
		$(".overCmpdRateInputtype input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.overCmpdRateInputtype=$(this).val();
			top.kindNo =mfSysKind.kindNo;
			var rateTypeShow;
	        mfSysKind.rateType=$("input[name='rateType']:checked").val();
			if(mfSysKind.rateType=="1"){
				rateTypeShow = "%";
			}else if(mfSysKind.rateType=="2"){
				rateTypeShow = "‰";
			}else if(mfSysKind.rateType=="3"){
				rateTypeShow = "‱";
			}else if(mfSysKind.rateType=="4"){
				rateTypeShow = "%";
			}
			if(mfSysKind.overCmpdRateInputtype=="1"){//利率
				$(".overFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
				$("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);	
				$("#rateTypeShowName"+top.kindNo).text("默认逾期利率为");
				$("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
				$("#overFlotRateDefName"+top.kindNo).text("默认逾期利率：");
				$("#cmpFltRateDefName"+top.kindNo).text("默认复利利率：");
				$(".cmpFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
			}else{//利率浮动
				$(".overFltRateDef"+top.kindNo+"Show").text("%");
				$("#overFltRateDefShowName"+top.kindNo+"Show").text("%");	
				$("#rateTypeShowName"+top.kindNo).text("默认逾期利率上浮为");
				$("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
				$("#overFlotRateDefName"+top.kindNo).text("默认逾期利率上浮：");
				$("#cmpFltRateDefName"+top.kindNo).text("默认复利利率上浮：");
				$(".cmpFltRateDef"+top.kindNo+"Show").text("%");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//复利利息是否收取 1 不收取 2 收取
	var getCmpdRateTypeHtml = function(mfSysKind,rateTypeList){
		var rateTypeShow="";
		var inputStr="";
		$.each(rateTypeList,function(i,parmDic){
			if(mfSysKind.rateType==parmDic.optCode){
				rateTypeShow = parmDic.remark;
			}
		});
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.cmpdRateType=="1"){//收取
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr=""
		if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
			htmlStr = '<div class="item-div cmpdRateType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>复利利息收取：</span>'
			+'</span>'
			+'</div>'
			+'<div class="item-content padding_left_5">'
			+ '<span><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率上浮：</span><input type="text" class="cmpFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.cmpFltRateDef+'"/><span class="cmpFltRateDef'+mfSysKind.kindNo+'Show">%</span></span>'
			+ '</div>'
			+ '</div>';
		}else{
			htmlStr = '<div class="item-div cmpdRateType" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>复利利息收取：</span>'
			+'</span>'
			+'</div>'
			+'<div class="item-content padding_left_5">'
			+ '<span><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率：</span><input type="text" class="cmpFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.cmpFltRateDef+'"/><span class="cmpFltRateDef'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
			+ '</div>'
			+ '</div>';	
		}
		
		return htmlStr;
	};
	//复利利息是否收取绑定事件
	var cmpdRateTypeBindEvent = function(){
		$(".cmpdRateType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			if($(this).hasClass("curChecked")){//不收取
				mfSysKind.cmpdRateType="0";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.cmpdRateType="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
		//复利利息上浮绑定事件
		$(".cmpdRateType input[type=text]").bind("change",function(){
			var mfSysKind={};
			mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysKind.cmpFltRateDef=$(this).val();
			top.cmpFltRateDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			top.kindNo =mfSysKind.kindNo; 
			MfSysKindConfig.updateKindConfig(ajaxData,function(){
				$(".cmpFltRateDef"+top.kindNo).text(top.cmpFltRateDef);
			});
		});
	};
	
	
	//逾期违约金
	var getPenaltyFincHtml = function(mfSysKind){
		var overPenaltyMain = mfSysKind.overPenaltyMain;
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		var baseTypeStr1 = "",baseTypeStr2 = "",baseTypeStr3 = "",baseTypeStr4 = "";
		var cycleStr1 = "",cycleStr2 = "";
		var contentStr1 = 'style="display:none;"',contentSpanStr1 = 'style="display:none;"',contentStr2 = 'style="display:none;"',contentStr3 = 'style="display:none;"';
		if(overPenaltyMain.penaltyReceiveType=="1"){
			checkStr1='checked="checked"';
			contentStr1 = 'style="display:block;"';
			contentSpanStr1 = 'style="display:inline-block;"';
		}else if(overPenaltyMain.penaltyReceiveType=="2"){
			checkStr2='checked="checked"';
			contentStr2 = 'style="display:inline-block;"';
		}else if(overPenaltyMain.penaltyReceiveType=="3"){
			checkStr3='checked="checked"';
			contentStr3 = 'style="display:inline-block;"';
		}
		if(overPenaltyMain.penaltyCalcBaseType=="1"){
			baseTypeStr1='checked="checked"';
		}else if(overPenaltyMain.penaltyCalcBaseType=="2"){
			baseTypeStr2='checked="checked"';
		}else if(overPenaltyMain.penaltyCalcBaseType=="3"){
			baseTypeStr3='checked="checked"';
		}else if(overPenaltyMain.penaltyCalcBaseType=="4"){
			baseTypeStr4='checked="checked"';
		}
		if(overPenaltyMain.penaltyReceiveCycle=="1"){
			cycleStr1='checked="checked"';
		}else if(overPenaltyMain.penaltyReceiveCycle=="2"){
			cycleStr2='checked="checked"';
		}
		
		
		
		var htmlStr = '<div class="item-div penaltyFinc" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >逾期违约金：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
				+'<div class="margin_bottom_10">'
					+'<span >'
						+'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="1" '+checkStr1+'/>按比例</span>'
					+'</span>'
					+'<span class="padding_left_15 penalty" id="penalty1" '+contentSpanStr1+'>'
					+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveValue" type="text" name="peneceiveValue" value="'+overPenaltyMain.penaltyReceiveValue+'"/>%</span>'
					+'</span>'
					+'<div class="padding_left_15 margin_top_10 divPenalty" id="divPenalty1" '+contentStr1+'>'
					    +'<div class="margin_bottom_10">'
						    +'<span >计算基数</span>'
							+'<span class="padding_left_15">'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="1" '+baseTypeStr1+'/>合同金额</span>'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="2" '+baseTypeStr2+'/>借据金额</span>'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="3" '+baseTypeStr3+'/>逾期本金</span>'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType'+mfSysKind.kindNo+'" value="4" '+baseTypeStr4+'/>逾期金额</span>'
							+'</span>'
						+ '</div>'
						+'<div class="margin_bottom_10">'
							+'<span >计算周期</span>'
							+'<span class="padding_left_15">'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle" value="1" '+cycleStr1+'/>一次性</span>'
							+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle" value="2" '+cycleStr2+'/>按逾期天数</span>'
							+'</span>'
						+ '</div>'
					+ '</div>'
				+ '</div>'
				+'<div class="margin_bottom_10">'
				+'<span >'	
					+'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="2" '+checkStr2+'/>固定金额</span>'
				+'</span>'
				+'<span class="padding_left_15 penalty" id="penalty2" '+contentStr2+'>'
					+ '<span class="margin_right_10"><input class="margin_right_5 margin_left_10 penaltyReceiveValue" type="text" name="peneceiveValue" value="'+overPenaltyMain.penaltyReceiveValue+'"/>元</span>'
				+'</span>'
				+ '</div>'
				+'<div class="margin_bottom_10">'	
					+'<span >'
						+ '<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="3" '+checkStr3+'/>根据逾期天数不同设置不同比例或金额(阶梯式收取)</span>'
					+'</span>'
					+'<a href="javascript:void(0);" onclick="MfKindCalcConfig.openPenaltyChild(\''+mfSysKind.kindNo+'\',1);" class="padding_left_15 pointer penalty" id="penalty3" '+contentStr3+'>配置</a>'
				+ '</div>'
				+ '</div>'
			 +'</div>';
		
		return htmlStr;
	};
	//违约金设置绑定事件
	var penaltyFincBindEvent = function(){
		$(".penaltyFinc .typeInput").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyReceiveType=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData,function(data){
				var mfSysPenaltyMain = data.mfSysPenaltyMain;
				$(".penalty").css("display","none");
				$(".divPenalty").css("display","none");
				
				$("#penalty"+mfSysPenaltyMain.penaltyReceiveType).css("display","inline-block");
				if(mfSysPenaltyMain.penaltyReceiveType=="1"){//按比例
					$("#divPenalty"+mfSysPenaltyMain.penaltyReceiveType).css("display","block");
				}
			});
		});
		
		$(".penaltyFinc .penaltyReceiveValue").bind("change",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		$(".penaltyFinc .penaltyCalcBaseType").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyCalcBaseType=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		$(".penaltyFinc .penaltyReceiveCycle").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyReceiveCycle=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		
	};
	
	//提前还款违约金
	var getAdvanceRepayHtml = function(mfSysKind){ 
		var prePenaltyMain = mfSysKind.prePenaltyMain;
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		var baseTypeStr1 = "",baseTypeStr2 = "",baseTypeStr3 = "";
		var contentStr1 = 'style="display:none;"',contentSpanStr1 = 'style="display:none;"', contentStr2 = 'style="display:none;"',contentStr3 = 'style="display:none;"';
		if(prePenaltyMain.penaltyReceiveType=="1"){
			checkStr1='checked="checked"';
			contentStr1 = 'style="display:block;"';
			contentSpanStr1 = 'style="display:inline-block;"';
		}else if(prePenaltyMain.penaltyReceiveType=="2"){
			checkStr2='checked="checked"';
			contentStr2 = 'style="display:inline-block;"';
		}else if(prePenaltyMain.penaltyReceiveType=="3"){
			checkStr3='checked="checked"';
			contentStr3 = 'style="display:inline-block;"';
		}
		
		if(prePenaltyMain.penaltyCalcBaseType=="1"){
			baseTypeStr1='checked="checked"';
		}else if(prePenaltyMain.penaltyCalcBaseType=="2"){
			baseTypeStr2='checked="checked"';
		}else if(prePenaltyMain.penaltyCalcBaseType=="3"){
			baseTypeStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div preRepayPenalty" data-kindno="'+mfSysKind.kindNo+'">'
			+'<div class="item-title margin_bottom_10">'
			+'<span >提前还款违约金：</span>'
			+'</div>'
			+ '<div class="item-content padding_left_5">'
			+'<div class="margin_bottom_10">'
			+'<span >'
				+'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>按比例</span>'
			+'</span>'
			+'<span class="padding_left_15 prePenalty'+mfSysKind.kindNo+'" id="prePenalty1'+mfSysKind.kindNo+'" '+contentSpanStr1+'>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveValue" type="text" name="prePeneceiveValue'+mfSysKind.kindNo+'" value="'+prePenaltyMain.penaltyReceiveValue+'"/>%</span>'
			+'</span>'
			+'<div class="padding_left_15 margin_top_10 divPrePenalty'+mfSysKind.kindNo+'" id="divPrePenalty1'+mfSysKind.kindNo+'" '+contentStr1+'>'
			    +'<div class="margin_bottom_10">'
				    +'<span >计算基数</span>'
					+'<span class="padding_left_15">'
					+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="1" '+baseTypeStr1+'/>合同金额</span>'
					+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="2" '+baseTypeStr2+'/>借据金额</span>'
					+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="3" '+baseTypeStr3+'/>提前还款本金</span>'
					+'</span>'
				+ '</div>'
			+ '</div>'
		+ '</div>'
		+'<div class="margin_bottom_10">'
		+'<span >'	
			+'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>固定金额</span>'
		+'</span>'
		+'<span class="padding_left_15 prePenalty'+mfSysKind.kindNo+'" id="prePenalty2'+mfSysKind.kindNo+'" '+contentStr2+'>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveValue" type="text" name="prePeneceiveValue'+mfSysKind.kindNo+'" value="'+prePenaltyMain.penaltyReceiveValue+'"/>元</span>'
		+'</span>'
		+ '</div>'
		+'<div class="margin_bottom_10">'	
			+'<span >'
				+ '<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>根据期限不同设置不同比例或金额(阶梯式收取)</span>'
			+'</span>'
			+'<a href="javascript:void(0);" onclick="MfKindConfig.openPenaltyChild(\''+mfSysKind.kindNo+'\',2);" class="padding_left_15 pointer prePenalty'+mfSysKind.kindNo+'" id="prePenalty3'+mfSysKind.kindNo+'" '+contentStr3+'>配置</a>'
		+ '</div>'
		+ '</div>'
		+ '</div>';
		
		return htmlStr;
	};
	//违约金设置绑定事件
	var preRepayPenaltyBindEvent = function(){
		$(".preRepayPenalty .typeInput").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyReceiveType=$(this).val();
			mfSysPenaltyMain.penaltyType="2";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData,function(data){
				var mfSysPenaltyMain = data.mfSysPenaltyMain;
				$(".prePenalty"+mfSysPenaltyMain.kindNo).css("display","none");
				$(".divPrePenalty"+mfSysPenaltyMain.kindNo).css("display","none");
				
				$("#prePenalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","inline-block");
				if(mfSysPenaltyMain.penaltyReceiveType=="1"){//按比例
					$("#divPrePenalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","block");
				}
			});
		});
		
		$(".preRepayPenalty .penaltyReceiveValue").bind("change",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
			mfSysPenaltyMain.penaltyType="2";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		$(".preRepayPenalty .penaltyCalcBaseType").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
			mfSysPenaltyMain.penaltyCalcBaseType=$(this).val();
			mfSysPenaltyMain.penaltyType="2";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
	};
	//违约金修改
	var updateMfSysPenalty = function(ajaxData,penaltyCallBack){
		$.ajax({
			url:webPath+"/mfSysKind/updateMfSysPenaltyAjax",
			type:'post',
			data:{ajaxData:ajaxData},
			success:function(data){
				if(data.flag="success"){
					if(penaltyCallBack!==undefined&&typeof(penaltyCallBack) == "function"){
						penaltyCallBack(data);
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	
	//违约金阶梯设置
	var _openPenaltyChild=function(kindNo,penaltyType){
		var penaltyTitle = penaltyType=='1'?'逾期违约金阶梯设置':'提前还款违约金阶梯设置';
		var url=webPath+"/mfSysPenaltyChild/getPenaltyChildList?kindNo="+kindNo+"&penaltyType="+penaltyType;
		window.parent.openBigForm(url,penaltyTitle,function(){
		});
	};
	
	//核算的高级设置
	var getCalcAdvancedSetHtml = function(mfSysKind,data){
		var contentHtml ="";
		//一年天数设置
		contentHtml= contentHtml + getYearDaysHtml(mfSysKind);
		//利率小数位数设置
		contentHtml= contentHtml + getRateDecimalDigitsHtml(mfSysKind,data.rateDigitsList);
		//计息方式
		contentHtml= contentHtml + getIcTypeHtml(mfSysKind,data.icTypeList);
		//还款计划保留位数
		contentHtml= contentHtml + getReturnPlanPointHtml(mfSysKind);
		//还款计划小数舍入方式
		contentHtml= contentHtml + getReturnPlanRoundHtml(mfSysKind);
		//费用计划是否与还款计划合并
		contentHtml= contentHtml + getFeePlanMergeHtml(mfSysKind);
		//多次放款还款计划合并
		contentHtml= contentHtml + getMultipleLoanPlanMergeHtml(mfSysKind);
		//还款顺序
		contentHtml= contentHtml + getRepaymentOrderHtml(mfSysKind);
		//结余处理方式
		contentHtml= contentHtml + getBalanceDealTypeHtml(mfSysKind);
		//逾期利率复利利率输入类型  0-利率浮动、1-利率 (0 表示表单输入的是利率浮动百分比，1 表示是利率值)
		contentHtml= contentHtml + getOverCmpdRateInputtypeHtml(mfSysKind);	
		//复利利息是否收取  1 不收取 2 收取
		contentHtml= contentHtml + getCmpdRateTypeHtml(mfSysKind,data.rateTypeList);
		//逾期违约金
		contentHtml= contentHtml + getPenaltyFincHtml(mfSysKind);
		//提前还款违约金
		contentHtml= contentHtml + getAdvanceRepayHtml(mfSysKind);
		var optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#advanceSet'+mfSysKind.kindNo+'">'
				+'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
				+'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
				+'</div>';
		var htmlStr = '<div id="advanceSet'+mfSysKind.kindNo+'" class="more-div collapse">'+contentHtml+'</div>'+optStr;
		return htmlStr;
	};
	return{
		init:_init,
		openPenaltyChild:_openPenaltyChild,
	};
}(window, jQuery);