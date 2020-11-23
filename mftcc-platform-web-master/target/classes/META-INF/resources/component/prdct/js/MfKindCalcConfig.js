;
var MfKindCalcConfig=function(window, $){
	//初始化产品核算配置信息
	var _init = function(data){
		var htmlStr = getPrdctCalcConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
		//初始化属性上的绑定事件
		initBindEvent();
	};
	//产品核算配置Html
	var getPrdctCalcConfigHtml = function(data){
		var  mfSysKind= data.mfSysKind;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div calcConfig"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content padding_left_20 margin_top_15">';
		//融资期限
		htmlStr = htmlStr+ getTermTypeConfigHtml(mfSysKind,data.termTypeList);
		//融资金额
		htmlStr = htmlStr+ getFincAmtConfigHtml(mfSysKind);
		//还款方式
		htmlStr = htmlStr+ getRepayTypeConfigHtml(mfSysKind,data.repayTypeList);
		//利率&利息相关
		htmlStr = htmlStr+ getRateAndInterestConfigHtml(mfSysKind,data);
		//还款计划
		htmlStr = htmlStr+ getRepayPlanConfigHtml(mfSysKind);
		//提前还款相关
		htmlStr = htmlStr+ getPreRepayConfigHtml(mfSysKind,data);
		//逾期相关
		htmlStr = htmlStr+ getOverDueConfigHtml(mfSysKind,data);
		//还款设置
		htmlStr = htmlStr+ getRepayConfigHtml(mfSysKind,data);
		//结余设置
		htmlStr = htmlStr+ getBalanceDealConfigHtml(mfSysKind);
		//其他
		htmlStr = htmlStr+ getOtherConfigHtml(mfSysKind);
		
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
        festivalRepayplanOverBindEvent();
        festivalFincOverBindEvent();
        festivalRepayPlanFlagBindEvent();
		lastTermBalanceTypeBindEvent();
		repayDateBindEvent();
		instCollectTypeBindEvent();
		feeCollectWayBindEvent();
        lsbqInterestCollectTypeBindEvent();
		lsbqChargeIntstBindEvent();
		secondNormCalcTypeBindEvent();
		instCalcBaseBindEvent();
		uniqueRepaymethodChargeIntstBindEvent();
		preInstCollectTypeBindEvent();
		preRepayTypeBindEvent();
		preRepayInstCalcBindEvent();
		yearDaysBindEvent();
		repaymentOrderChangeflagBindEvent();
        repaymentOrderChangeOverdayBindEvent();
		rateDecimalDigitsBindEvent();
        /*icTypeBindEvent();*/
		returnPlanPointBindEvent();
		feePlanMergeBindEvent();
        repayPlanOverFlagBindEvent();
		multiplePlanMergeBindEvent();
		balanceDealTypeBindEvent();
		overCmpdRateInputtypeBindEvent();
		overCmpdFltSaveflagBindEvent();
        calcOverPrcpTypeBindEvent();
        overIntstFlagBindEvent();
		overDaysFlagBindEvent();
		cmpdRateTypeBindEvent();
		feesumFaxiTypeBindEvent();
		penaltyFincBindEvent();
		preRepayPenaltyBindEvent();
        partRepayPreFlagBindEvent();
        calcIntstFlagBindEvent();
        pactEnddateShowFlagBindEvent();
        fincBeginDatePactOrNowBindEvent();
        fincEndDateOrOverPactBindEvent();
        baseRateTypeBindEvent();
        icTypeBindEvent();
	};
	
	//融资期限
	var  getTermTypeConfigHtml = function(mfSysKind,termTypeList){
		var termTypeUnit="个月";
		if(mfSysKind.termType=="2"){
			termTypeUnit="天";
		}
		var termDef = mfSysKind.termDef;
		if(termDef=="" || termDef==null){
            termDef='<span class="unregistered">未登记</span>';
            mfSysKind.termDef="";
		}
		var subHtmlStr = '<div class="main-content-div"><div class="main-content first"><span class="margin_right_10">期限类型：</span>';
		$.each(termTypeList,function(i,parmDic){
			var checkStr="";
			if(mfSysKind.termType==parmDic.optCode){
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="termType" value="'+parmDic.optCode+'" '+checkStr+'>'+parmDic.optName+'</span>';
		});
		subHtmlStr = subHtmlStr+'</div>';
		var htmlStr='';
		htmlStr=htmlStr+'<div class="item-div termType">'
		+'<div class="item-title">'
		+'<span>融资期限</span>'
		+'</div>'
		+'<div class="item-content margin_bottom_5">'
		+'<div class="main-content-desc"><span class="content-desc">'+mfSysKind.kindName+'的期限介于<span id="minTermDesc">'+mfSysKind.minTerm+'</span>到<span id="maxTermDesc">'+mfSysKind.maxTerm+'</span><span class="termTypeUnit">'+termTypeUnit+'</span>之间</div>'
		+ subHtmlStr
		+'<div class="main-content">'
		+'<span class="margin_right_10">合同期限范围：</span>'
		+'<span id="minTermRead" data-type="minTerm" class="span-read"><span class="span-text">'+mfSysKind.minTerm+'</span></span>'
		+'<span id="minTermEdit" class="span-edit"><input type="text" name="minTerm" value="'+mfSysKind.minTerm+'"/></span> <span class="padding_right_10">至</span> '
		+'<span id="maxTermRead" data-type="maxTerm" class="span-read"><span class="span-text">'+mfSysKind.maxTerm+'</span></span>'
		+'<span id="maxTermEdit"  class="span-edit"><input type="text" name="maxTerm" value="'+mfSysKind.maxTerm+'"/></span>'
		+'<span class="termTypeUnit">'+termTypeUnit+'</span>'
		+'</div>'
		+'<div class="main-content">'
		+'<span class="margin_right_10">借据期限范围：</span>'
		+'<span id="minFincTermRead" data-type="minFincTerm" class="span-read"><span class="span-text">'+mfSysKind.minFincTerm+'</span></span>'
		+'<span id="minFincTermEdit" class="span-edit"><input type="text" name="minFincTerm" value="'+mfSysKind.minFincTerm+'"/></span> <span class="padding_right_10">至</span> '
		+'<span id="maxFincTermRead" data-type="maxFincTerm" class="span-read"><span class="span-text">'+mfSysKind.maxFincTerm+'</span></span>'
		+'<span id="maxFincTermEdit"  class="span-edit"><input type="text" name="maxFincTerm" value="'+mfSysKind.maxFincTerm+'"/></span>'
		+'<span class="termTypeUnit">'+termTypeUnit+'</span>'
		+'</div>'
		// +'<div class="main-content termDef">'
		// +'<span class="margin_right_10">默认期限：</span>'
		// +'<span id="termDefRead" data-type="termDef" class="span-read"><span class="span-text">'+termDef+'</span></span>'
		// +'<span id="termDefEdit" class="span-edit"><input type="text" name="termDef"  maxlength="5" class="termDef" value=""/></span><span class="termTypeUnit">'+termTypeUnit+'</span>'
		// +'</div>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//融资期限绑定事件
	var termTypeBindEvent = function(){
		$(".termType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		//期限双击编辑事件
		$(".termType .span-read").dblclick(function(){
			var type=$(this).data("type");
			if("minTerm"==type){
				$("#minTermRead").css("display","none");
				$("#minTermEdit").css("display","inline-block");
				$("input[name=minTerm]").css("width",$(this).width());
				$("input[name=minTerm]").focus();
			}else if("maxTerm"==type){
				$("#maxTermRead").css("display","none");
				$("#maxTermEdit").css("display","inline-block");
				$("input[name=maxTerm]").css("width",$(this).width());
				$("input[name=maxTerm]").focus();
			}else if("termDef"==type){
                $("#termDefRead").css("display","none");
                $("#termDefEdit").css("display","inline-block");
                $("input[name=termDef]").css("width",$(this).width());
                $("input[name=termDef]").focus();
            }else if("minFincTerm"==type){
                $("#minFincTermRead").css("display","none");
                $("#minFincTermEdit").css("display","inline-block");
                $("input[name=minFincTerm]").css("width",$(this).width());
                $("input[name=minFincTerm]").focus();
			}else if("maxFincTerm"==type) {
                $("#maxFincTermRead").css("display", "none");
                $("#maxFincTermEdit").css("display", "inline-block");
                $("input[name=maxFincTerm]").css("width", $(this).width());
                $("input[name=maxFincTerm]").focus();
            }
		});
		//期限编辑input框事件
		$(".termType .span-edit input").blur(function(){
			var type = $(this).attr("name");
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if("minTerm"== type){
				mfSysKind.minTerm=$(this).val();
			}else if("maxTerm" == type){
				mfSysKind.maxTerm=$(this).val();
			}else if("termDef" == type){
                mfSysKind.termDef=$(this).val();
            }else if("minFincTerm" == type){
                mfSysKind.minFincTerm=$(this).val();
            }else if("maxFincTerm" == type){
                mfSysKind.maxFincTerm=$(this).val();
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
				}else if("termDef"== top.type){
                    $("#termDefRead .span-text").text(mfSysKindTmp.termDef);
                    $("#termDefEdit").css("display","none");
                    $("#termDefRead").css("display","inline-block");
                }else if("minFincTerm"== top.type){
                    $("#minFincTermDesc").text(mfSysKindTmp.minFincTerm);
                    $("#minFincTermRead .span-text").text(mfSysKindTmp.minFincTerm);
                    $("#minFincTermEdit").css("display","none");
                    $("#minFincTermRead").css("display","inline-block");
                }else if("maxFincTerm"== top.type){
                    $("#maxFincTermDesc").text(mfSysKindTmp.maxFincTerm);
                    $("#maxFincTermRead .span-text").text(mfSysKindTmp.maxFincTerm);
                    $("#maxFincTermEdit").css("display","none");
                    $("#maxFincTermRead").css("display","inline-block");
                }
			});
		});
	};
	//融资金额
	var getFincAmtConfigHtml = function(mfSysKind){
		var totalMaxAmt = mfSysKind.totalMaxAmt;
		if(mfSysKind.totalMaxAmt==""||mfSysKind.totalMaxAmt==null){
			totalMaxAmt='<span class="unregistered">未登记</span>';
            mfSysKind.totalMaxAmt ="0.00";
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div fincAmt">'
		+'<div class="item-title">'
		+'<span>融资金额</span>'
		+'</div>'
		+'<div class="item-content margin_bottom_5">'
		+'<div class="main-content-desc"><span class="content-desc">'+mfSysKind.kindName+'的融资金额介于<span id="minAmtDesc">'+mfSysKind.minAmt+'</span>到<span id="maxAmtDesc">'+mfSysKind.maxAmt+'</span>元之间</span></div>'
		+'<div class="main-content-div">'
		+'<div class="main-content first">'
		+'<span class="margin_right_10"> 金额：</span>'
		+'<span id="minAmtRead" class="span-read" data-type="minAmt"><span class="span-text">'+mfSysKind.minAmt+'</span></span>'
		+'<span id="minAmtEdit" class="span-edit"><input title="融资金额下限" name="minAmt" mustinput="1" maxlength="19" value="'+mfSysKind.minAmt+'" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" dbmaxlength="18" type="text"/></span><span class="padding_right_10">至</span> '
		+'<span id="maxAmtRead" class="span-read" data-type="maxAmt"><span class="span-text">'+mfSysKind.maxAmt+'</span></span>'
		+'<span id="maxAmtEdit" class="span-edit"><input title="融资金额上限" name="maxAmt" mustinput="1" maxlength="19" value="'+mfSysKind.maxAmt+'" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" dbmaxlength="18" type="text"/></span>'
		+'<div class="main-content">'
		+'<span class="margin_right_10"> 总金额上限：</span>'
		+'<span id="totalMaxAmtRead" class="span-read" data-type="totalMaxAmt"><span class="span-text">'+totalMaxAmt+'</span></span>'
		+'<span id="totalMaxAmtEdit" class="span-edit"><input title="融资总金额上限" name="totalMaxAmt" mustinput="1" maxlength="19" value="'+mfSysKind.totalMaxAmt+'" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" dbmaxlength="18" type="text"/></span>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//融资金额绑定事件
	var fincAmtBindEvent = function(){
		//金额编辑小笔事件
		$(".fincAmt .span-read").dblclick(function(){
			var type=$(this).data("type");
			if("minAmt"==type){
				$("#minAmtRead").css("display","none");
				$("#minAmtEdit").css("display","inline-block");
				$("input[name=minAmt]").css("width",$(this).width());
				$("input[name=minAmt]").focus();
			}else if("maxAmt"==type){
				$("#maxAmtRead").css("display","none");
				$("#maxAmtEdit").css("display","inline-block");
				$("input[name=maxAmt]").css("width",$(this).width());
				$("input[name=maxAmt]").focus();
			}else if("totalMaxAmt"==type){
				$("#totalMaxAmtRead").css("display","none");
				$("#totalMaxAmtEdit").css("display","inline-block");
				$("input[name=totalMaxAmt]").css("width",$(this).width());
				$("input[name=totalMaxAmt]").focus();
			}
		});
		//金额编辑input框事件
		$(".fincAmt .span-edit input").blur(function(){
			var type = $(this).attr("name");
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).val()==""){
				return;
			}
			if("minAmt"== type){
				mfSysKind.minAmt=$(this).val().replace(/,/g, "");
			}else if("maxAmt" == type){
				mfSysKind.maxAmt=$(this).val().replace(/,/g, "");
			}else if("totalMaxAmt" == type){
				mfSysKind.totalMaxAmt=$(this).val().replace(/,/g, "");
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
				}else if("totalMaxAmt"== top.type){
					$("#totalMaxAmtRead .span-text").text(mfSysKindTmp.totalMaxAmt);
					$("#totalMaxAmtEdit").css("display","none");
					$("#totalMaxAmtRead").css("display","inline-block");
				}
			});
		});
	};


    //利率类型
    var getRateTypeConfigHtml = function (mfSysKind, rateTypeList, baseRateTypeList) {
		var nameStr="";
		var rateTypeShow="";
		var subHtmlStr="";
        var baseRateTypeStr = "";
        //贷款基准利率类型
        $.each(baseRateTypeList, function (i, parmDic) {
            var checkspan = '<span  id="' + parmDic.optCode + '" class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
            var baseRateType = mfSysKind.baseRateType;
            if (baseRateType.indexOf(parmDic.optCode) < 0) {
                checkspan = '<span id="' + parmDic.optCode + '" class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
            }
            baseRateTypeStr = baseRateTypeStr
                + '<span class="item-checkbox margin_right_15">'
                + checkspan
                + '<span>' + parmDic.optName + '</span>'
                + '<span class="content-desc"></span>'
                + '</span>';

        });
        var htmlStr = '<div class="item-div baseRateType">'
            + '<div class="main-content first">'
            + '<span class="margin_right_10">基础利率类型：</span>'
            + '<span>'
            + baseRateTypeStr
            + '</span>'
            + '</div>'
            + '</div>';
		$.each(rateTypeList,function(i,parmDic){
			var checkStr="";
			if(mfSysKind.rateType==parmDic.optCode){
				rateTypeShow = parmDic.remark;
				nameStr = parmDic.optName;
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr +'<span class="margin_right_10"><input class="margin_right_5" type="radio" '+checkStr+' name="rateType" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
			
		});
        htmlStr = htmlStr + '<div class="item-div rateType">';
		//获取
		if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
            htmlStr = htmlStr + '<div class="main-content">'
			+'<span class="margin_right_10">利率类型：</span>'
			+ '<span>'
			+ subHtmlStr
			+ '</span>'
			+'<div class="content-desc">'+mfSysKind.kindName+'的融资利率使用<span id="rateTypeNameShow" >'+nameStr+'</span>格式，默认利率为<span class="fincRateShow">'+mfSysKind.fincRate+'</span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+'<span id="rateTypeShowName" >,默认逾期利率上浮为</span><span class="overFltRateDef">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName">%</span>'
			+'</div>'
			+'</div>'
			+ '<div class="main-content">'
			+ '<span class="margin_right_10">利率范围：</span>'
			+ '<span id="minFincRateRead" class="span-read"><span class="span-text">'+mfSysKind.minFincRate+'</span></span>'
			+ '<span id="minFincRateEdit" class="span-edit"><input type="text" name="minFincRate"  maxlength="10" value="'+mfSysKind.minFincRate+'"></span><span class="padding_right_10">至</span>'
			+ '<span id="maxFincRateRead" class="span-read"><span class="span-text">'+mfSysKind.maxFincRate+'</span></span>'
			+ '<span id="maxFincRateEdit" class="span-edit"><input type="text" name="maxFincRate"  maxlength="10" value="'+mfSysKind.maxFincRate+'"></span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+ '</div>'
			+ '<div class="main-content">'
			+ '<span class="margin_right_10">默认利率：</span>'
			+ '<span id="fincRateRead" class="span-read"><span class="span-text">'+mfSysKind.fincRate+'</span></span>'
			+ '<span id="fincRateEdit" class="span-edit"><input type="text" name="fincRate"  maxlength="10" value="'+mfSysKind.fincRate+'"></span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+'</div>'
			+ '<div class="main-content">'
			+ '<span id="overFlotRateDefName" class="margin_right_10">默认逾期利率上浮：</span>'
			+ '<span id="overFlotRateDefRead" class="span-read"><span class="span-text">'+mfSysKind.overFltRateDef+'</span></span>'
			+ '<span id="overFlotRateDefEdit" class="span-edit"><input type="text" name="overFltRateDef"  maxlength="10" value="'+mfSysKind.overFltRateDef+'"></span><span class="overFltRateDefShow">%</span>'
			+ '</div>'
			// + '<div class="main-content">'
			// + '<span id="monthTotalRateName" class="margin_right_10">综合费率：</span>'
			// + '<span id="monthTotalRateRead" class="span-read"><span class="span-text">'+mfSysKind.monthTotalRate+'</span></span>'
			// + '<span id="monthTotalRateEdit" class="span-edit"><input type="text" name="monthTotalRate"  maxlength="10" value="'+mfSysKind.monthTotalRate+'"></span><span class="monthTotalRateDefShow">'+rateTypeShow+'</span>'
			// + '</div>';
		}else{//默认利率
            htmlStr = htmlStr + '<div class="main-content">'
			+'<span class="margin_right_10">利率类型：</span>'
			+ '<span>'
			+ subHtmlStr
			+ '</span>'
			+'<div class="content-desc">'+mfSysKind.kindName+'的融资利率使用<span id="rateTypeNameShow" >'+nameStr+'</span>格式，默认利率为<span class="fincRateShow">'+mfSysKind.fincRate+'</span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+'<span id="rateTypeShowName">,默认逾期利率为</span><span class="overFltRateDef">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName">'+rateTypeShow+'</span>'
			+'</div>'
			+'</div>'
			+ '<div class="main-content">'
			+ '<span class="margin_right_10">利率范围：</span>'
			+ '<span id="minFincRateRead" class="span-read"><span class="span-text">'+mfSysKind.minFincRate+'</span></span>'
			+ '<span id="minFincRateEdit" class="span-edit"><input type="text" name="minFincRate"  maxlength="10" value="'+mfSysKind.minFincRate+'"></span><span class="padding_right_10">至</span> '
			+ '<span id="maxFincRateRead" class="span-read"><span  class="span-text">'+mfSysKind.maxFincRate+'</span></span>'
			+ '<span id="maxFincRateEdit" class="span-edit"><input type="text" name="maxFincRate"  maxlength="10" value="'+mfSysKind.maxFincRate+'"></span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+ '</div>'
			+ '<div class="main-content">'
			+ '<span class="margin_right_10">默认利率：</span>'
			+ '<span id="fincRateRead" class="span-read"><span class="span-text">'+mfSysKind.fincRate+'</span></span>'
			+ '<span id="fincRateEdit" class="span-edit"><input type="text" name="fincRate" maxlength="10" value="'+mfSysKind.fincRate+'"></span><span class="rateTypeShow">'+rateTypeShow+'</span>'
			+ '</div>'
			+ '<div class="main-content">'
			+ '<span id="overFlotRateDefName" class="margin_right_10">默认逾期利率：</span>'
			+ '<span id="overFlotRateDefRead" class="span-read"><span id="" class="span-text">'+mfSysKind.overFltRateDef+'</span></span>'
			+ '<span id="overFlotRateDefEdit" class="span-edit"><input type="text" name="overFltRateDef"  maxlength="10" value="'+mfSysKind.overFltRateDef+'"></span><span class="overFltRateDefShow">'+rateTypeShow+'</span>'
			+'</div>'
			// + '<div class="main-content">'
			// + '<span id="monthTotalRateName" class="margin_right_10">综合费率：</span>'
			// + '<span id="monthTotalRateRead" class="span-read"><span class="span-text">'+mfSysKind.monthTotalRate+'</span></span>'
			// + '<span id="monthTotalRateEdit" class="span-edit"><input type="text" name="monthTotalRate"  maxlength="10" value="'+mfSysKind.monthTotalRate+'"></span><span class="monthTotalRateDefShow">'+rateTypeShow+'</span>'
			// + '</div>';;
		}
		htmlStr = htmlStr +'</div>';
		return htmlStr;
	};


    //基准利率类型绑定事件
    var baseRateTypeBindEvent = function () {
        $(".baseRateType .checkbox-span").bind("click", function () {
            var mfSysKind = {};
            mfSysKind.kindNo = kindNo;
            if ($(this).hasClass("curChecked")) {//禁用
                $(this).removeClass("curChecked");
            } else {
                $(this).addClass("curChecked");
            }
            //处理复选框已选中的值
            var baseRateTypeNew = document.querySelector(".baseRateType").querySelectorAll(".checkbox-span");
            //var baseRateTypeNew = $(".baseRateType").children(".checkbox-span curChecked");
            var baseRateType = "";
            var a = 0;
            for (var i = 0, j = baseRateTypeNew.length; i < j; i++) {
                var baseRateTypeObj = baseRateTypeNew[i];
                var baseRateTypeClassName = baseRateTypeObj.className;
                if (baseRateTypeClassName.indexOf("curChecked") >= 0) {
                    if (a != 0) {
                        baseRateType = baseRateType + "|";
                    }
                    baseRateType = baseRateType + baseRateTypeNew[i].id;
                    a++;
                }
            }
            mfSysKind.baseRateType = baseRateType;

            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //计息方式
    var geticTypeConfigHtml = function (mfSysKind, icTypeList) {
        var icTypeStr = "";
        //贷款基准利率类型
        $.each(icTypeList, function (i, parmDic) {
            var checkspan = '<span  id="' + parmDic.optCode + '" class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
            var icType = mfSysKind.icType;
            if (icType.indexOf(parmDic.optCode) < 0) {
                checkspan = '<span id="' + parmDic.optCode + '" class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
            }
            icTypeStr = icTypeStr
                + '<span class="item-checkbox margin_right_15">'
                + checkspan
                + '<span>' + parmDic.optName + '</span>'
                + '<span class="content-desc"></span>'
                + '</span>';

        });
        var htmlStr = '<div class="item-div icType">'
            + '<div class="main-content">'
            + '<span class="margin_right_10">计息方式：</span>'
            + '<span>'
            + icTypeStr
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //基准利率类型绑定事件
    var icTypeBindEvent = function () {
        $(".icType .checkbox-span").bind("click", function () {
            var mfSysKind = {};
            mfSysKind.kindNo = kindNo;
            if ($(this).hasClass("curChecked")) {//禁用
                $(this).removeClass("curChecked");
            } else {
                $(this).addClass("curChecked");
            }
            //处理复选框已选中的值
            var icTypeNew = document.querySelector(".icType").querySelectorAll(".checkbox-span");
            //var baseRateTypeNew = $(".baseRateType").children(".checkbox-span curChecked");
            var icType = "";
            var a = 0;
            for (var i = 0, j = icTypeNew.length; i < j; i++) {
                var icTypeObj = icTypeNew[i];
                var icTypeClassName = icTypeObj.className;
                if (icTypeClassName.indexOf("curChecked") >= 0) {
                    if (a != 0) {
                        icType = icType + "|";
                    }
                    icType = icType + icTypeNew[i].id;
                    a++;
                }
            }
            mfSysKind.icType = icType;

            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };
	
	//利率类型绑定事件
	var rateTypeBindEvent = function(){
		$(".rateType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			var rateTypeShow="%";
			mfSysKind.kindNo=kindNo;
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
			mfSysKind.overFltRateDef=0.00;
			var ajaxData = JSON.stringify(mfSysKind);
			top.rateTypeName=$(this).data("name");
			mfSysKind.overCmpdRateInputtype=$("input[name=overCmpdRateInputtype]:checked").val();
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				$(".rateTypeShow").text(rateTypeShow);
				$("#rateTypeNameShow").text(top.rateTypeName);
				$(".fincRateShow").text(0.00);
				$("#fincRateRead .span-text").text(0.00);
				$("#fincRateEdit input[name=fincRate]").val(0.00);
				$("#minFincRateRead .span-text").text(0.00);
				$("#minFincRateEdit input[name=minFincRate]").val(0.00);
				$("#maxFincRateRead .span-text").text(0.00);
				$("#maxFincRateEdit input[name=maxFincRate]").val(0.00);
				$("#overFlotRateDefRead .span-text").text(0.00);
				$("#overFlotRateDefEdit input[name=overFltRateDef]").val(0.00);
				if(mfSysKind.overCmpdRateInputtype=="1"){//利率
					$(".overFltRateDefShow").text(rateTypeShow);
					$("#overFltRateDefShowName").text(rateTypeShow);	
					//复利利率
					$(".cmpFltRateDefShow").text(rateTypeShow);
				}else{//利率浮动
					$(".overFltRateDefShow").text("%");
					$("#overFltRateDefShowName").text("%");
					//复利利率
					$(".cmpFltRateDefShow").text("%");					
				}
				//产品综合费率随利率类型改变而改变
				// $(".monthTotalRateDefShow").text(rateTypeShow);
				
			});
		});
		
		$(".rateType .span-read").dblclick(function(){
			var id = $(this).attr("id");
			if(id=="minFincRateRead"){
				$("#minFincRateRead").css("display","none");
				$("#minFincRateEdit").css("display","inline-block");
				$("input[name=minFincRate]").css("width",$(this).width());
				$("input[name=minFincRate]").focus();
				
			}else if(id=="maxFincRateRead"){
				$("#maxFincRateRead").css("display","none");
				$("#maxFincRateEdit").css("display","inline-block");
				$("input[name=maxFincRate]").css("width",$(this).width());
				$("input[name=maxFincRate]").focus();
			}else if(id=="fincRateRead"){
				$("#fincRateRead").css("display","none");
				$("#fincRateEdit").css("display","inline-block");
				$("input[name=fincRate]").css("width",$(this).width());
				$("input[name=fincRate]").focus();
			}else if(id=="overFlotRateDefRead"){
				$("#overFlotRateDefRead").css("display","none");
				$("#overFlotRateDefEdit").css("display","inline-block");
				$("input[name=overFltRateDef]").css("width",$(this).width());
				$("input[name=overFltRateDef]").focus();
			}else if(id=="monthTotalRateRead"){
				// $("#monthTotalRateRead").css("display","none");
				// $("#monthTotalRateEdit").css("display","inline-block");
				// $("input[name=monthTotalRate]").css("width",$(this).width());
				// $("input[name=monthTotalRate]").focus();
			}
		});
		
		$(".rateType input[type=text]").blur(function(){
			var id = $(this).parents(".span-edit").attr("id");
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			var fincRate = $("#fincRateRead .span-text").text();
			var minFincRate = $("#minFincRateRead .span-text").text();
			var maxFincRate = $("#maxFincRateRead .span-text").text();
			if(id=="fincRateEdit"){//默认利率
				mfSysKind.fincRate=$(this).val();
				if ($(this).val() * 1 > maxFincRate*1) {//默认融资利率 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(fincRate);
					return false;
				}
				if ($(this).val() * 1 < minFincRate*1) {//默认融资利率 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(fincRate);
					return false;
				}

				// 给上浮赋值, 使后台更新逾期利率及复利利率
                mfSysKind.overFltRateDef = $("#overFlotRateDefEdit input[name=overFltRateDef]").val();
                mfSysKind.cmpFltRateDef = $("#cmpFltRateDefEdit input[name=cmpFltRateDef]").val();
			}else if(id=="minFincRateEdit"){//贷款利率下限
				mfSysKind.minFincRate=$(this).val();
				fincRate = $("#fincRateRead .span-text").text();
				minFincRate = $("#minFincRateRead .span-text").text();
				maxFincRate = $("#maxFincRateRead .span-text").text();
				if ($(this).val() * 1 > maxFincRate*1) {//贷款利率下限 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '融资利率下限',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(minFincRate);
					return false;
				}
				if (fincRate * 1 < $(this).val()*1) {//默认融资利率 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(minFincRate);
					return false;
				}
				
			}else if(id=="maxFincRateEdit"){//贷款利率上限
				mfSysKind.maxFincRate=$(this).val();
				fincRate = $("#fincRateRead .span-text").text();
				minFincRate = $("#minFincRateRead .span-text").text();
				maxFincRate = $("#maxFincRateRead .span-text").text();
				if ($(this).val() * 1 < minFincRate*1) {//贷款利率上限 不能小于贷款利率下限
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne" : '融资利率上限',"timeTwo" : '融资利率下限'}), 0);
					$(this).val(maxFincRate);
					return false;
				}
				if (fincRate * 1 > $(this).val()*1) {//默认融资利率 不能大于贷款利率上限
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : '默认融资利率',"timeTwo" : '融资利率上限'}), 0);
					$(this).val(oldMaxFinsRate);
					return false;
				}

			}else if(id=="overFlotRateDefEdit"){
				mfSysKind.overFltRateDef=$(this).val();
			}else if(id=="monthTotalRateEdit"){
				// mfSysKind.monthTotalRate=$(this).val();
			}
			mfSysKind.rateType=$("input[name='rateType']:checked").val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				$(".fincRateShow").text(mfSysKindTmp.fincRate);
				$("#fincRateRead .span-text").text(mfSysKindTmp.fincRate);
				$("#fincRateEdit input[name=fincRate]").val(mfSysKindTmp.fincRate);
				$("#minFincRateRead .span-text").text(mfSysKindTmp.minFincRate);
				$("#minFincRateEdit input[name=minFincRate]").val(mfSysKindTmp.minFincRate);
				$("#maxFincRateRead .span-text").text(mfSysKindTmp.maxFincRate);
				$("#maxFincRateEdit input[name=maxFincRate]").val(mfSysKindTmp.maxFincRate);
				$("#overFlotRateDefRead .span-text").text(mfSysKindTmp.overFltRateDef);
				$("#overFlotRateDefEdit input[name=overFltRateDef]").val(mfSysKindTmp.overFltRateDef);
				// $("#monthTotalRateRead .span-text").text(mfSysKindTmp.monthTotalRate);
				// $("#monthTotalRateEdit input[name=monthTotalRate]").val(mfSysKindTmp.monthTotalRate);
				$(".rateType .span-read").css("display","inline-block");
				$(".rateType .span-edit").css("display","none");
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
		+'<span>还款方式</span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-desc"><span class="content-desc">'+mfSysKind.kindName+'支持<span id="repayTypeTip'+mfSysKind.kindNo+'">'+repayTypeTip+'</span>，其中默认还款方式为：<span id="repayTypeDefTip'+mfSysKind.kindNo+'">'+repayTypeDefTip+'</span></span></div>'
		+'<div class="main-content-div">'
		+'<button class="btn btn-default btn-add">添加</button>'
		+'<span id="repayTypeOption'+mfSysKind.kindNo+'">'
		+ subStr
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//还款方式绑定事件
	var repayTypeBindEvent = function(){
		$(".repayType .btn-add").bind("click",function(){
			top.flag=false;
			top.itemId="";
			window.parent.openBigForm(webPath+"/mfSysKind/getKindObjectForSelect?kindNo="+kindNo+"&keyName=REPAY_TYPE","选择还款方式",function(){
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
							mfSysKind.kindNo=kindNo;
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
			mfSysKind.kindNo=kindNo;
			mfSysKind.repayTypeDef=$(this).data("repaytype");
			var ajaxData = JSON.stringify(mfSysKind);
			top.repayTypeDef=$(this).data("name");
			MfSysKindConfig.updateKindConfig(ajaxData,function(){
				$("#repayTypeDefTip"+kindNo).text(top.repayTypeDef);
			});
		});
	};
	
	//利率&利息相关
	var getRateAndInterestConfigHtml = function(mfSysKind,data){
		var htmlStr ='<div class="item-div">'
		+'<div class="item-title margin_bottom_10">'
		+'<span>利率&利息相关</span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-desc"><span class="content-desc">主要设置利率以及利息计算的相关参数</span></div>'
		+'<div class="main-content-div">';
		//利率类型
        htmlStr = htmlStr + getRateTypeConfigHtml(mfSysKind, data.rateTypeList, data.baseRateTypeList);
		//利息计算方式
		htmlStr = htmlStr + getNormCalcTypeHtml(mfSysKind,data.normCalcTypeList);
		//按月结息时不足一月利息计算方式
		htmlStr = htmlStr + getSecondNormCalcTypeHtml(mfSysKind);
		//利息计算基数
		htmlStr = htmlStr + getInstCalcBaseHtml(mfSysKind);
		//特殊还款方式利息计算时天数的获取
		htmlStr = htmlStr + getUniqueRepaymethodChargeIntstHtml(mfSysKind);
		//利率小数位数设置
		htmlStr= htmlStr + getRateDecimalDigitsHtml(mfSysKind,data.rateDigitsList);
		//利息收息方式
		htmlStr = htmlStr + getInstCollectTypeHtml(mfSysKind,data.instCollectTypeList);
		//费用收取方式
		htmlStr = htmlStr + getFeeCollectWayHtml(mfSysKind,data.feeCollectWayList);
        //放款时利随本清还款时利息收取
        htmlStr = htmlStr + getLsbqInterestCollectTypeHtml(mfSysKind);
		//利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
		htmlStr = htmlStr + getLsbqChargeIntstHtml(mfSysKind);
		//预先支付利息收取方式
		htmlStr = htmlStr + getPreInstCollectTypeHtml(mfSysKind);
		//首尾计息方式
		htmlStr = htmlStr + getCalcIntstFlagHtml(mfSysKind,data.calcIntstFlagList);
        //合同、借据结束日展示
        htmlStr = htmlStr + getPactEnddateShowFlagHtml(mfSysKind, data.pactEnddateShowFlagList);
        //借据的开始日期默认是借据开始日期还是现在1--当前日期,2--合同开始日期
        htmlStr = htmlStr + getFincBeginDatePactOrNowHtml(mfSysKind, data.fincBeginDatePactOrNowList);
        //借据的结束日期是否允许超过合同的结束日期1--允许超过,2--不允许超过
        htmlStr = htmlStr + getFincEndDateOrOverPactHtml(mfSysKind, data.fincEndDateOrOverPactList);
        //计息方式
        htmlStr = htmlStr + geticTypeConfigHtml(mfSysKind, data.icTypeList);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
	};
	//还款计划相关
	var getRepayPlanConfigHtml = function(mfSysKind){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>还款计划</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置还款计划计算的相关参数</span></div>'
			+'<div class="main-content-div">';
		//还款计划保留位数
		htmlStr= htmlStr + getReturnPlanPointHtml(mfSysKind);
		//还款计划小数舍入方式
		htmlStr= htmlStr + getReturnPlanRoundHtml(mfSysKind);
		//费用计划是否与还款计划合并
/*		htmlStr= htmlStr + getFeePlanMergeHtml(mfSysKind);
		//多次放款还款计划合并
		htmlStr= htmlStr + getMultipleLoanPlanMergeHtml(mfSysKind);*/
		//还款计划当期利息或本息归还后 还款计划是否标记为逾期
		htmlStr= htmlStr + getRepayPlanOverFlagHtml(mfSysKind);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
	};
	//提前还款相关
	var getPreRepayConfigHtml = function(mfSysKind,data){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>提前还款</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置提前还款时计算的相关参数</span></div>'
			+'<div class="main-content-div">';
		//提前还款
		htmlStr = htmlStr + getPreRepayTypeHtml(mfSysKind);
		//提前还款利息
		htmlStr = htmlStr + getPreRepayInstCalcHtml(mfSysKind,data.preRepayIntsTypeList);
		//提前还款违约金
		htmlStr= htmlStr + getAdvanceRepayHtml(mfSysKind);
        //部分还款是否支持提前还款 partRepayPreFlag 部分还款后是否允许提前还款标志 0 部分还款后不允许提前还款 1 部分还款后允许提前还款
        htmlStr= htmlStr+getPartRepayPreFlagHtml(mfSysKind);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
	};
	//逾期相关
	var getOverDueConfigHtml = function(mfSysKind,data){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>逾期相关</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置逾期时逾期、复利以及违约金计算的相关参数</span></div>'
			+'<div class="main-content-div">';
		//逾期利率复利利率输入类型  0-利率浮动、1-利率 (0 表示表单输入的是利率浮动百分比，1 表示是利率值)
		htmlStr= htmlStr + getOverCmpdRateInputtypeHtml(mfSysKind);	
		//overCmpdFltSaveflag 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值）
		htmlStr= htmlStr + getOverCmpdFltSaveflagHtml(mfSysKind);
		//逾期利息计算基数 0逾期本金  1借据余额
		htmlStr= htmlStr + getCalcOverPrcpTypeHtml(mfSysKind);
		//逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则）
		htmlStr= htmlStr + getOverIntstFlagHtml(mfSysKind);
		//逾期利息计算天数 0 取正常每一期逾期的天数 1 取借据逾期的天数
		htmlStr= htmlStr + getOverDaysFlagHtml(mfSysKind);
		//复利利息是否收取  1 不收取 2 收取
		htmlStr= htmlStr + getCmpdRateTypeHtml(mfSysKind,data.rateTypeList);
		//费用罚息收取：0-不收取、1-收取 
		htmlStr= htmlStr + getFeesumFaxiTypeHtml(mfSysKind,data.rateTypeList);
		//逾期违约金
		htmlStr= htmlStr + getPenaltyFincHtml(mfSysKind);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
	};
	//还款相关
	var getRepayConfigHtml = function(mfSysKind,data){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>还款相关</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置还款时计算的相关参数</span></div>'
			+'<div class="main-content-div">';
		//还款日设置
		htmlStr = htmlStr + getRepayDateSetHtml(mfSysKind);
		//一年天数设置
		htmlStr= htmlStr + getYearDaysHtml(mfSysKind);
		//还款顺序
		htmlStr= htmlStr + getRepaymentOrderHtml(mfSysKind,data.repaymentOrderTypeStr);
		//还款顺序变更
		htmlStr= htmlStr+getRepaymentOrderChangeflagHtml(mfSysKind);
		//逾期N天还款顺序变更repayment_order_change_overday
		htmlStr= htmlStr+getRepaymentOrderChangeOverdayHtml(mfSysKind);

		htmlStr= htmlStr+'</div></div>';
		return htmlStr;
	};
	//结余相关
	var getBalanceDealConfigHtml = function(mfSysKind){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>结余</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置结余处理的相关参数</span></div>'
			+'<div class="main-content-div">';
		//结余处理方式
		htmlStr= htmlStr + getBalanceDealTypeHtml(mfSysKind);
		//允许最后一期结余
		htmlStr = htmlStr + getLastTermBalanceTypeHtml(mfSysKind);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
	};
	//其他设置
	var getOtherConfigHtml = function(mfSysKind){
		var htmlStr ='<div class="item-div">'
			+'<div class="item-title margin_bottom_10">'
			+'<span>其他</span>'
			+'</div>'
			+'<div class="item-content">'
			+'<div class="main-content-desc"><span class="content-desc">主要设置利息是否减免以及节假日利息收取相关参数</span></div>'
			+'<div class="main-content-div">';
		//利息减免
		htmlStr = htmlStr+getInterestDerateHtml(mfSysKind);
		//节假日利息收取设置
		htmlStr = htmlStr+getFestivalTypeHtml(mfSysKind);
        //还款计划逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息
		htmlStr = htmlStr+getFestivalRepayplanOverHtml(mfSysKind);
		//节假日（宽限期）内借据逾期 还款时是否收取正常利息 0-不收取 1-收取
		htmlStr = htmlStr+getFestivalFincOverHtml(mfSysKind);
		//还款计划到期日期是节假日时 还款计划是否标记为逾期  默认是0  0 按正常（逾期） 1 不逾期（顺延不逾期）
		htmlStr = htmlStr+getFestivalRepayPlanFlagHtml(mfSysKind);
		htmlStr= htmlStr+'</div></div></div>';
		return htmlStr;
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
		var htmlStr ='<div class="item-div normCalcType">'
		+'<div class="main-content">'
		+'<span class="margin_right_10">利息计算方式：</span>'
		+ subHtmlStr
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//利息计算方式绑定事件
	var normCalcTypeBindEvent = function(){
		$(".normCalcType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div secondCalcType '+showOrHide+'">'
		+ '<div class="main-content">'
		+ '<span class="margin_right_10">不足一月利息计算方式：</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="secondCalcType" value="2" '+checkStr2+'/>按月计息</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="secondCalcType" value="3" '+checkStr3+'/>按日计息</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	//按月结息时不足一月利息计算方式绑定事件
	var secondNormCalcTypeBindEvent = function(){
		$(".secondCalcType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.secondNormCalcType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//利息计算基数
	var getInstCalcBaseHtml = function(mfSysKind){
		var checkStr1="";
		var checkStr2="";
		var checkStr3="";
		if(mfSysKind.instCalcBase=="1"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.instCalcBase=="2"){
			checkStr2='checked="checked"';
		}else if(mfSysKind.instCalcBase=="3"){
			checkStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div instCalcBase">'
		+ '<div class="main-content">'
		+ '<span class="margin_right_10">特殊还款方式利息计算基数：</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="instCalcBase" value="1" '+checkStr1+'/>贷款金额</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="instCalcBase" value="2" '+checkStr2+'/>贷款余额</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="instCalcBase" value="3" '+checkStr3+'/>当期本金</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	//利息计算基数绑定事件
	var instCalcBaseBindEvent = function(){
		$(".instCalcBase input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.instCalcBase=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};


    //利息计算方式
    var getCalcIntstFlagHtml = function(mfSysKind,calcIntstFlagList){
        var subHtmlStr="";
        $.each(calcIntstFlagList,function(i,parmDic){
            var checkStr="";
            if(mfSysKind.calcIntstFlag==parmDic.optCode){
                checkStr='checked="checked"';
            }
            subHtmlStr = subHtmlStr+'<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="calcIntstFlag" '+checkStr+' value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName+'</span>';
        });
        var htmlStr = '<div class="item-div calcIntstFlag">'
            +'<div class="main-content">'
            +'<span class="margin_right_10">首尾计息方式：</span>'
            + subHtmlStr
            +'</div>'
            +'</div>';
        return htmlStr;
    };


    //利息计算方式绑定事件
    var calcIntstFlagBindEvent = function(){
        $(".calcIntstFlag input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.calcIntstFlag=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //合同、借据、展期结束日展示
    var getPactEnddateShowFlagHtml = function (mfSysKind, pactEnddateShowFlagList) {
        var subHtmlStr = "";
        $.each(pactEnddateShowFlagList, function (i, parmDic) {
            var checkStr = "";
            if (mfSysKind.pactEnddateShowFlag == parmDic.optCode) {
                checkStr = 'checked="checked"';
            }
            subHtmlStr = subHtmlStr + '<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="pactEnddateShowFlag" ' + checkStr + ' value="' + parmDic.optCode + '"  data-name="' + parmDic.optName + '"/>' + parmDic.optName + '</span>';
        });
        var htmlStr = '<div class="item-div pactEnddateShowFlag">'
            + '<div class="main-content">'
            + '<span class="margin_right_10">合同、借据、展期结束日展示：</span>'
            + subHtmlStr
            + '</div>'
            + '</div>';
        return htmlStr;
    };


    //合同、借据、展期结束日展示绑定事件
    var pactEnddateShowFlagBindEvent = function () {
        $(".pactEnddateShowFlag input[type=radio]").bind("click", function () {
            var mfSysKind = {};
            mfSysKind.kindNo = kindNo;
            mfSysKind.pactEnddateShowFlag = $(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //借据的开始日期默认是借据开始日期还是现在1--当前日期,2--合同开始日期
    var getFincBeginDatePactOrNowHtml = function (mfSysKind, fincBeginDatePactOrNowList) {
        var subHtmlStr = "";
        $.each(fincBeginDatePactOrNowList, function (i, parmDic) {
            var checkStr = "";
            if (mfSysKind.fincBeginDatePactOrNow == parmDic.optCode) {
                checkStr = 'checked="checked"';
            }
            subHtmlStr = subHtmlStr + '<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="fincBeginDatePactOrNow" ' + checkStr + ' value="' + parmDic.optCode + '"  data-name="' + parmDic.optName + '"/>' + parmDic.optName + '</span>';
        });
        var htmlStr = '<div class="item-div fincBeginDatePactOrNow">'
            + '<div class="main-content">'
            + '<span class="margin_right_10">借据的开始日期默认值：</span>'
            + subHtmlStr
            + '</div>'
            + '</div>';
        return htmlStr;
    };


    //借据的开始日期默认是借据开始日期还是现在绑定事件
    var fincBeginDatePactOrNowBindEvent = function () {
        $(".fincBeginDatePactOrNow input[type=radio]").bind("click", function () {
            var mfSysKind = {};
            mfSysKind.kindNo = kindNo;
            mfSysKind.fincBeginDatePactOrNow = $(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //借据的结束日期是否允许超过合同的结束日期
    var getFincEndDateOrOverPactHtml = function (mfSysKind, fincEndDateOrOverPactList) {
        var subHtmlStr = "";
        $.each(fincEndDateOrOverPactList, function (i, parmDic) {
            var checkStr = "";
            if (mfSysKind.fincEndDateOrOverPact == parmDic.optCode) {
                checkStr = 'checked="checked"';
            }
            subHtmlStr = subHtmlStr + '<span class="margin_right_10"><input class="margin_right_5" type="radio"  name="fincEndDateOrOverPact" ' + checkStr + ' value="' + parmDic.optCode + '"  data-name="' + parmDic.optName + '"/>' + parmDic.optName + '</span>';
        });
        var htmlStr = '<div class="item-div fincEndDateOrOverPact">'
            + '<div class="main-content">'
            + '<span class="margin_right_10">借据的结束日期是否允许超过合同的结束日期：</span>'
            + subHtmlStr
            + '</div>'
            + '</div>';
        return htmlStr;
    };


    //借据的结束日期是否允许超过合同的结束日期绑定事件
    var fincEndDateOrOverPactBindEvent = function () {
        $(".fincEndDateOrOverPact input[type=radio]").bind("click", function () {
            var mfSysKind = {};
            mfSysKind.kindNo = kindNo;
            mfSysKind.fincEndDateOrOverPact = $(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //还特殊还款方式利息计算时天数的获取 1 取正常计算的每一期天数  2取到期日期和借据开始日期之间的天数 ';
	var getUniqueRepaymethodChargeIntstHtml = function(mfSysKind){
		var checkStr1="";
		var checkStr2="";
		if(mfSysKind.uniqueRepaymethodChargeIntst=="1"){
			checkStr1='checked="checked"';
		}else if(mfSysKind.uniqueRepaymethodChargeIntst=="2"){
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div uniqueRepaymethodChargeIntst">'
		+ '<div class="main-content">'
		+ '<span class="margin_right_10">特殊还款方式利息计算时天数的获取：</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="uniqueRepaymethodChargeIntst" value="1" '+checkStr1+'/>取正常计算的每一期天数</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="uniqueRepaymethodChargeIntst" value="2" '+checkStr2+'/>取到期日期和借据开始日期之间的天数</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	//还款方式为特殊还款方式时利息计算时天数的计算方式  绑定事件
	var uniqueRepaymethodChargeIntstBindEvent = function(){
		$(".uniqueRepaymethodChargeIntst input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.uniqueRepaymethodChargeIntst=$(this).val();
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
		var htmlStr = '<div class="item-div instDerate">'
			+'<div class="main-content first">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>启用利息减免：</span>'
			+'<span class="content-desc">还款时，利息支持减免优惠，包括正常利息和罚息</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//利息减免绑定点击事件
	var interestDerateBindEvent = function(){
		$(".instDerate .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			
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
		var htmlStr = '<div class="item-div festivalType">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>节假日（周末/宽限天）利息收取方式：</span>'
			+'<span class="content-desc">还款日与法定节假日（周末）重合时或在宽限期内逾期后逾期利息、复利是否收取</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式绑定点击事件
	var festivalTypeBindEvent = function(){
		$(".festivalType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
	//还款计划逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息0-不收取 1-收取
	var getFestivalRepayplanOverHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.festivalRepayplanOver=="1"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div festivalRepayplanOver">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>还款计划逾期 节假日（宽限期）还款时是否收取正常利息 </span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//还款计划逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息绑定点击事件
	var festivalRepayplanOverBindEvent = function(){
		$(".festivalRepayplanOver .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){
				mfSysKind.festivalRepayplanOver="0";//不收取
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.festivalRepayplanOver="1";//收取
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//借据逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息0-不收取 1-收取
	var getFestivalFincOverHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.festivalFincOver=="1"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div festivalFincOver">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>借据逾期 节假日（宽限期）还款时是否收取正常利息 </span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//借据逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息绑定点击事件
	var festivalFincOverBindEvent = function(){
		$(".festivalFincOver .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){
				mfSysKind.festivalFincOver="0";//不收取
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.festivalFincOver="1";//收取
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//还款计划到期日期是节假日时 还款计划是否标记为逾期  默认是0  0 按正常（逾期） 1 不逾期（顺延不逾期）
	var getFestivalRepayPlanFlagHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.festivalRepayPlanFlag=="0"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div festivalRepayPlanFlag">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>还款计划到期日期是节假日时,还款计划是否标记为逾期 </span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//借据逾期 节假日（宽限期）还款时是否收取 节假日（宽限期）正常利息绑定点击事件
	var festivalRepayPlanFlagBindEvent = function(){
		$(".festivalRepayPlanFlag .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){
				mfSysKind.festivalRepayPlanFlag="1";// 不逾期（顺延不逾期）
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.festivalRepayPlanFlag="0";// 0 按正常（逾期）
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
		var htmlStr = '<div class="item-div lastTermBalanceType">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>允许最后一期结余：</span>'
			+'<span class="content-desc">还款时允许最后一期还款存在结余</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//允许最后一期结余：0-不允许、1-允许绑定点击事件 lastTermBalanceTypeBindEvent
	var lastTermBalanceTypeBindEvent = function(){
		$(".lastTermBalanceType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var repayDateDef = mfSysKind.repayDateDef;
		if(mfSysKind.repayDateDef==""){
			repayDateDef='<span class="unregistered">未登记</span>';
		}
		var htmlStr = '<div class="item-div repayDate">'
			+'<div class="main-content first">'
				+'<span class="margin_right_10">还款日设置：</span>'
				+ '<span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="1" '+checkStr1+' data-name="贷款发放日"/>贷款发放日</span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="2" '+checkStr2+' data-name="月末"/>月末</span>'
				+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="repayDateSet" value="3" '+checkStr3+' data-name="固定还款日"/>固定还款日</span>'
				+ '</span>'
				+'<span id="repayDateDef" class="margin_left_60" '+showOrHide+'>默认还款日：'
				+'<span id="repayDateDefRead" class="span-read"><span class="span-text">'+repayDateDef+'</span></span>'
				+'<span id="repayDateDefEdit" class="span-edit"><input type="text" name="repayDateDef" maxlength="10" class="repayDateDef" value="'+mfSysKind.repayDateDef+'"/></span>'
				+'</span>'
			+ '</div>'
			+'<div class="content-desc">'+mfSysKind.kindName+'<span id="repayDateSetTip">'+repayDateSetTip+'</span>还款</div>'
			+'</div>';
		return htmlStr;
	};
	
	//还款日设置绑定事件
	var repayDateBindEvent = function(){
		$(".repayDate input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		$("#repayDateDefRead").dblclick(function(){
			$("#repayDateDefRead").css("display","none");
			$("#repayDateDefEdit").css("display","inline-block");
			$("#repayDateDef").css("display","inline-block");
			$("#repayDateDefEdit input[name=repayDateDef]").css("width",$(this).width());
			$("#repayDateDefEdit input[name=repayDateDef]").focus();
		
		});
		
		$("#repayDateDefEdit input[name=repayDateDef]").blur(function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.repayDateDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if(mfSysKindTmp.repayDateDef==""){
					$("#repayDateDefRead .span-text").html('<span class="unregistered">未登记</span>');
				}else{
					$("#repayDateDefRead .span-text").html(mfSysKindTmp.repayDateDef);
				}
				$("#repayDateDefSpan").text(mfSysKindTmp.repayDateDef);
				$("#repayDateDefRead").css("display","inline-block");
				$("#repayDateDefEdit").css("display","none");
			});
		});
		
		
	};
	
	//放款时利随本清还款时利息收取
	var getLsbqInterestCollectTypeHtml = function(mfSysKind){
		var checkStr1="",checkStr2="",checkStr3="",showOrHide='style="display:none;"';
		var lsbqInterestCollectTypeTip="";
		if(mfSysKind.lsbqInterestCollectType=="1"){
			checkStr1='checked="checked"';
            lsbqInterestCollectTypeTip="放款时收取全部利息";
		}else if(mfSysKind.lsbqInterestCollectType=="2"){
			checkStr2='checked="checked"';
            lsbqInterestCollectTypeTip="在还款时按照还款时利息收取方式收取";
		}else if(mfSysKind.lsbqInterestCollectType=="3"){
			checkStr3='checked="checked"';
            lsbqInterestCollectTypeTip="放款时按照放款申请时添加的收取天数先收取N天利息";
		}
		var htmlStr = '<div class="item-div lsbqInterestCollectType">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">利随本清放款时利息收取：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqInterestCollectType" value="1" '+checkStr1+' data-name="放款时收取全部利息"/><span id="lsbqInterestCollectType_span1">放款时收取全部利息</span></span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqInterestCollectType" value="2" '+checkStr2+' data-name="还款时收利息"/><span id="lsbqInterestCollectType_span2">还款时收利息</span></span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqInterestCollectType" value="3" '+checkStr3+' data-name="按照天数收取利息"/><span id="lsbqInterestCollectType_span3">按照天数收取利息</span></span>'
			+'</div>'
			+'<div class="content-desc">'+mfSysKind.kindName+'<span id="lsbqInterestCollectTypeTip">'+lsbqInterestCollectTypeTip+'</span></div>'
			+'</div>';
		return htmlStr;
	};
	var lsbqInterestCollectTypeBindEvent = function(){
		$(".lsbqInterestCollectType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.lsbqInterestCollectType=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var kindObj = data.mfSysKind;
				$("#lsbqInterestCollectTypeTip").text($("#lsbqInterestCollectType_span"+kindObj.lsbqInterestCollectType).text());
			});
		});
	};

    //还款时利随本清还款时利息收取
    var getLsbqChargeIntstHtml = function(mfSysKind){
        var checkStr1="",checkStr2="",checkStr3="",checkStr4="",showOrHide='style="display:none;"';
        var lsbqChargeIntstTip="";
        var contentStr2 = 'style="display:none;"';
        if(mfSysKind.lsbqChargeIntst=="1"){
            checkStr1='checked="checked"';
            lsbqChargeIntstTip="在还款时分次收取部分利息";
            contentStr2 = 'style="display:none;"';
        }else if(mfSysKind.lsbqChargeIntst=="2"){
            checkStr2='checked="checked"';
            lsbqChargeIntstTip="在还款时一次收取全部利息";
            contentStr2 = 'style="display:none;"';
        }else if(mfSysKind.lsbqChargeIntst=="3"){
            checkStr3='checked="checked"';
            lsbqChargeIntstTip="按还款本金收取利息（还款本金*（还款日期-还款计划开始日期）*日利率）";
            contentStr2 = 'style="display:none;"';
        }else if(mfSysKind.lsbqChargeIntst=="4"){
            checkStr4='checked="checked"';
            lsbqChargeIntstTip="最少收取N天利息";
            contentStr2 = 'style="display:inline-block;"';
        }
        var htmlStr = '<div class="item-div lsbqChargeInts">'
            +'<div class="main-content">'
            +'<span class="margin_right_10">利随本清还款时利息收取：</span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="1" '+checkStr1+' data-name="分次收取部分利息"/><span id="lsbqChargeIntst_span1">分次收取部分利息</span></span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="2" '+checkStr2+' data-name="一次收取全部利息"/><span id="lsbqChargeIntst_span2">一次收取全部利息</span></span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="3" '+checkStr3+' data-name="按还款本金收取利息（还款本金*（还款日期-还款计划开始日期）*日利率）"/><span id="lsbqChargeIntst_span3">按还款本金收取利息（还款本金*（还款日期-还款计划开始日期）*日利率）</span></span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="lsbqChargeIntst" value="4" '+checkStr4+' data-name="最少收取N天利息"/><span id="lsbqChargeIntst_span4">最少收取N天利息</span></span>'
            +'<span class="lsbqChargeIntstDays" data-type="2" id="lsbqChargeIntst2" '+contentStr2+'>'
            +'<span id="lsbqChargeIntstDaysRead2"  class="span-read"><span class="span-text">'+mfSysKind.lsbqChargeIntstDays+'</span></span>'
            +'<span id="lsbqChargeIntstDaysEdit2" class="span-edit"><input type="text" name="lsbqChargeIntstDaysEdit" class="lsbqChargeIntstDaysEdit" value="'+mfSysKind.lsbqChargeIntstDays+'"/></span>天 '
            +'</div>'
            +'<div class="content-desc">'+mfSysKind.kindName+'<span id="lsbqChargeIntstTip">'+lsbqChargeIntstTip+'</span></div>'
            +'</div>';
        return htmlStr;
    };
    var lsbqChargeIntstBindEvent = function(){
        $(".lsbqChargeInts input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.lsbqChargeIntst=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            if(mfSysKind.lsbqChargeIntst=="4"){//收取全部利息
                $(".lsbqChargeIntstDays").css("display","inline-block");
            }else{
                $(".lsbqChargeIntstDays").css("display","none");
            }
            MfSysKindConfig.updateKindConfig(ajaxData,function(data){
                var kindObj = data.mfSysKind;
                $("#lsbqChargeIntstTip").text("在还款时"+$("#lsbqChargeIntst_span"+kindObj.lsbqChargeIntst).text());
            });
        });

        $(".lsbqChargeIntstDays .span-read").dblclick(function(){
            var type = $(this).parents(".lsbqChargeIntstDays").data("type");
            $("#lsbqChargeIntstDaysRead2").css("display","none");
            $("#lsbqChargeIntstDaysEdit2").css("display","inline-block");
            $("#lsbqChargeIntstDaysEdit2 input[name=lsbqChargeIntstDaysEdit]").css("width",$(this).width());
            $("#lsbqChargeIntstDaysEdit2 input[name=lsbqChargeIntstDaysEdit]").focus();
        });
        $(".lsbqChargeIntstDays .span-edit input").blur(function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.lsbqChargeIntstDays=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
            $(".lsbqChargeIntstDays .span-text").text(mfSysKind.lsbqChargeIntstDays);
            $(".lsbqChargeIntstDays input[name=lsbqChargeIntstDaysEdit]").val(mfSysKind.lsbqChargeIntstDays);
            $("#lsbqChargeIntstDaysEdit2").css("display","none");
            $("#lsbqChargeIntstDaysRead2").css("display","inline-block");
        });
    };

    //利息收息方式 上收息 下收息
    var getInstCollectTypeHtml = function(mfSysKind){
        var checkStr1 = "",checkStr2 = "";
        var baseTypeStr1 = "",baseTypeStr2 = "";
        var contentStr1 = 'style="display:none;"',contentSpanStr1 = 'style="display:none;"', contentStr2 = 'style="display:none;"';
        if(mfSysKind.interestCollectType =="1"){
            checkStr1='checked="checked"';
            contentStr1 = 'style="display:block;"';
            contentSpanStr1 = 'style="display:inline-block;"';
        }else{
            checkStr2='checked="checked"';
            contentStr2 = 'style="display:inline-block;"';
        }

        if(mfSysKind.interestCollectTermType=="9999"){
            baseTypeStr1='checked="checked"';
            contentStr2 = 'style="display:none;"';
        }else {
            baseTypeStr2='checked="checked"';
            contentStr2 = 'style="display:inline-block;"';
        }
        var htmlStr = '<div class="item-div interestCollectType">'
            +'<div class="main-content">'
            +'<span class="margin_right_10">放款时利息收取方式：</span>'
            +'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="interestCollectType" value="1" '+checkStr1+'/>上收息</span>'
            +'<span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="interestCollectType" value="2" '+checkStr2+'/>下收息</span>'
            +'<div class="margin_top_10 divInterestCollectType" id="divInterestCollectType1" '+contentStr1+'>'
            +'<span >上收息参数：</span>'
            +'<span class="padding_left_15">'
            + '<span class="margin_right_10"><input class="margin_right_5 interestCollectTermTypeAll" type="radio" name="interestCollectTermType" value="9999" '+baseTypeStr1+'/>收取全部利息</span>'
            + '<span class="margin_right_10"><input class="margin_right_5 interestCollectTermTypeAll" type="radio" name="interestCollectTermType" value="2" '+baseTypeStr2+'/>收取前N期利息</span>'
            +'</span>'
            +'<span class="interestCollectTermTypeValue" data-type="2" id="interestCollectTermType2" '+contentStr2+'>'
            +'<span id="interestCollectTermTypeValueRead2"  class="span-read"><span class="span-text">'+mfSysKind.interestCollectTermType+'</span></span>'
            +'<span id="interestCollectTermTypeValueEdit2" class="span-edit"><input type="text" name="interestCollectTermTypeValueEdit" class="interestCollectTermTypeValueEdit" value="'+mfSysKind.interestCollectTermType+'"/></span>期 '
            +'</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //利息放款时收取 绑定事件
    var instCollectTypeBindEvent = function(){
        $(".interestCollectType .typeInput").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.interestCollectType=$(this).val();

            if(mfSysKind.interestCollectType=="2"){//下收息时
                mfSysKind.interestCollectTermType="0";
			}else{
                mfSysKind.interestCollectTermType=$(this).val();
			}
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);

            if(mfSysKind.interestCollectType=="1"){//上收息
                $(".divInterestCollectType").css("display","inline-block");
			}else{
                $(".divInterestCollectType").css("display","none");
			}
			if(mfSysKind.interestCollectTermType=="9999"){//收取全部利息
                $(".interestCollectTermTypeValue").css("display","none");
			}else{
                $(".interestCollectTermTypeValue").css("display","inline-block");
			}
        });
        $(".interestCollectTermTypeValue .span-read").dblclick(function(){
            var type = $(this).parents(".interestCollectTermTypeValue").data("type");
            $("#interestCollectTermTypeValueRead2").css("display","none");
            $("#interestCollectTermTypeValueEdit2").css("display","inline-block");
            $("#interestCollectTermTypeValueEdit2 input[name=interestCollectTermTypeValueEdit]").css("width",$(this).width());
            $("#interestCollectTermTypeValueEdit2 input[name=interestCollectTermTypeValueEdit]").focus();
        });

        $(".interestCollectTermTypeValue .span-edit input").blur(function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.interestCollectTermType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
			$(".interestCollectTermTypeValue .span-text").text(mfSysKind.interestCollectTermType);
			$(".interestCollectTermTypeValue input[name=interestCollectTermTypeValueEdit]").val(mfSysKind.interestCollectTermType);
            $("#interestCollectTermTypeValueEdit2").css("display","none");
            $("#interestCollectTermTypeValueRead2").css("display","inline-block");
        });
        //上收息参数 绑定事件
        $(".interestCollectType .interestCollectTermTypeAll").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.interestCollectTermType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
            if(mfSysKind.interestCollectTermType=="9999"){//收取全部利息
                $(".interestCollectTermTypeValue").css("display","none");
            }else{
                $(".interestCollectTermTypeValue").css("display","inline-block");
			}
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
		var htmlStr = '<div class="item-div feeCollectWay">'
		+'<div class="main-content">'
		+'<span class="margin_right_10">费用收取方式：</span>'
		+ inputStr
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	var feeCollectWayBindEvent = function(){
		$(".feeCollectWay input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div preInstCollect">'
		+'<div class="main-content">'
		+'<span class="margin_right_10">预先支付利息收取方式：</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="1" '+checkStr1+'/>合并第一期</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="2" '+checkStr2+'/>独立一期</span>'
		+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preInstCollectType" value="3" '+checkStr3+'/>放款时收取</span>'
		+ '</div>'
		+ '</div>';
		return htmlStr;
	};
	
	var preInstCollectTypeBindEvent = function(){
		$(".preInstCollect input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div preRepayType">'
			+'<div class="main-content first">'
			+'<span class="margin_right_10">提前还款本金：</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="2" '+checkStr2+'/>偿还部分剩余本金</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="1" '+checkStr1+'/>偿还全部剩余本金</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="preRepayType" value="3" '+checkStr3+'/>一次性偿还所有未还本金、利息</span>'
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	var preRepayTypeBindEvent = function(){
		$(".preRepayType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div preRepayInstCalc">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">提前还款利息：</span>'
			+'<span id="preRepayInstAccoutBase">'
			+ inputStr
			+'</span>'
			+ '</div>'
			+'<div class="content-desc">'+mfSysKind.kindName+'要求<span id="preRepayInstCalcTip">'+preRepayInstCalcTip+'</span></div>'
			+ '</div>';
		return htmlStr;
	};
	
	//提前还款利息计算
	var preRepayInstCalcBindEvent = function(){
		$(".preRepayInstCalc input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div yearDays">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">年天数：</span>'
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
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div rateDecimalDigits">'
			+ '<div class="main-content">'
			+ '<span class="margin_right_10">利率小数位数：</span>'
			+ inputStr
			+ '</div>'
			+ '</div>';
		return htmlStr;
	};
	
	//利率小数位数设置绑定事件
	var rateDecimalDigitsBindEvent = function(){
		$(".rateDecimalDigits input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
			+'<span >计息方式</span>'
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
    /*var icTypeBindEvent = function(){
        $(".icType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.icType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };*/
	
	
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
		var htmlStr = '<div class="item-div returnPlanPoint">'
			+'<div class="main-content first">'
			+'<span class="margin_right_10">还款计划保留位数：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="2" '+checkStr1+'/>保留两位</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="1" '+checkStr2+'/>保留一位</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanPoint" value="0" '+checkStr3+'/>不保留</span>'
			+'</div>'
			+'</div>';
		return htmlStr;
	};
	//还款计划保留位数绑定事件
	var returnPlanPointBindEvent = function(){
		$(".returnPlanPoint input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.returnPlanPoint=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//还款计划小数舍入方式
	var getReturnPlanRoundHtml = function(mfSysKind){
		var htmlStr = '<div class="item-div returnPlanRound">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">还款计划小数舍入方式：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="returnPlanRound" value="2" checked="checked"/>四舍五入</span>'
			+'</div>'
			+'</div>';
		return htmlStr;
	};
	//费用计划是否与还款计划合并
	var getFeePlanMergeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.feePlanMerge=="0"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div feePlanMerge">'
			+'<div class="main-content">'
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
			mfSysKind.kindNo=kindNo;
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
	
	//费用计划是否与还款计划合并
	var getRepayPlanOverFlagHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.repayPlanOverFlag=="1"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div repayPlanOverFlag">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>还款计划当期利息或本息归还后,还款计划是否标记为逾期</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
		return htmlStr;
	};
	//费用计划是否与还款计划合并绑定事件
	var repayPlanOverFlagBindEvent = function(){
		$(".repayPlanOverFlag .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){//禁用
				mfSysKind.repayPlanOverFlag="1";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.repayPlanOverFlag="0";
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
		var htmlStr = '<div class="item-div multiplePlanMerge">'
			+'<div class="main-content">'
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
			mfSysKind.kindNo=kindNo;
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
	var getRepaymentOrderHtml = function(mfSysKind,repaymentOrderTypeStr){
		var htmlStr = '<div class="item-div">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">还款顺序：</span>'
			+'<span>'+repaymentOrderTypeStr+'</span>'
			+'</div>'
			+ '</div>';
		
		return htmlStr;
	};
	
	//还款顺序变更
	var getRepaymentOrderChangeflagHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.repaymentOrderChangeflag=="0"){
			checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div repaymentOrderChangeflag">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>还款顺序可以变更：</span>'
			+'<span class="content-desc">逾期后还款顺序可以变更（通过规则获取新的还款顺序）</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//还款顺序变更绑定点击事件
	var repaymentOrderChangeflagBindEvent = function(){
		$(".repaymentOrderChangeflag .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){//不可以变更
				mfSysKind.repaymentOrderChangeflag="0";
				$(this).removeClass("curChecked");
			}else{//可以变更
				mfSysKind.repaymentOrderChangeflag="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	//逾期N天还款顺序变更
	var getRepaymentOrderChangeOverdayHtml = function(mfSysKind){
		var spanTextValue='<span class="unregistered">未登记</span>';
		if(mfSysKind.repaymentOrderChangeOverday!="" && mfSysKind.repaymentOrderChangeOverday!=null) {
            spanTextValue = mfSysKind.repaymentOrderChangeOverday;
        }
		var htmlStr = '<div class="item-div repaymentOrderChangeOverday">'
			+'<div class="main-content">'
            +'<span class="margin_right_10"> 逾期N天还款顺序变更：</span>'
            +'<span id="repaymentOrderChangeOverdayRead" class="span-read" data-type="repaymentOrderChangeOverday">'
            +'<span class="span-text">'+spanTextValue+'</span>天'
            +'</span>'
            +'<span id="repaymentOrderChangeOverdayEdit" class="span-edit">'
            +'<input name="repaymentOrderChangeOverday" mustinput="1" maxlength="19" value="'+mfSysKind.repaymentOrderChangeOverday+'" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();" dbmaxlength="18" type="text">天'
            +'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//还款顺序变更绑定点击事件
	var repaymentOrderChangeOverdayBindEvent = function(){
		$("#repaymentOrderChangeOverdayRead").dblclick(function(){
			$("#repaymentOrderChangeOverdayRead").css("display","none");
			$("#repaymentOrderChangeOverdayEdit").css("display","inline-block");
			$("#repaymentOrderChangeOverday").css("display","inline-block");
			$("#repaymentOrderChangeOverdayEdit input[name=repaymentOrderChangeOverday]").css("width",$(this).width());
			$("#repaymentOrderChangeOverdayEdit input[name=repaymentOrderChangeOverday]").focus();

		});

		$("#repaymentOrderChangeOverdayEdit input[name=repaymentOrderChangeOverday]").blur(function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.repaymentOrderChangeOverday=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if(mfSysKindTmp.repaymentOrderChangeOverday==""){
					$("#repaymentOrderChangeOverdayRead .span-text").html('<span class="unregistered">未登记</span>');
				}else{
					$("#repaymentOrderChangeOverdayRead .span-text").html(mfSysKindTmp.repaymentOrderChangeOverday);
				}
				$("#repaymentOrderChangeOverdayRead").css("display","inline-block");
				$("#repaymentOrderChangeOverdayEdit").css("display","none");
			});
		});
	};

	
	
	//结余处理方式
	var getBalanceDealTypeHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.balanceDealType=="1"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div balanceDealType">'
			+'<div class="main-content first">'
			+'<span class="margin_right_10">结余处理方式 ：</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="balanceDealType" value="1" '+checkStr1+'/>冲抵贷款</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="balanceDealType" value="2" '+checkStr2+'/>退款</span>'
			+ '</div>'
			+ '</div>';
		
		return htmlStr;
	};
	//结余处理方式
	var balanceDealTypeBindEvent = function(){
		$(".balanceDealType input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div overCmpdRateInputtype">'
			+'<div class="main-content first">'
			+'<span class="margin_right_10">逾期利率复利利率输入类型：</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdRateInputtype" value="0" '+checkStr1+'/>利率浮动</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdRateInputtype" value="1" '+checkStr2+'/>利率</span>'
			+ '</div>'
			+ '</div>';
		
		return htmlStr;
	};
	
	//逾期利率复利利率输入类型 
	var overCmpdRateInputtypeBindEvent = function(){
		$(".overCmpdRateInputtype input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
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
				$(".overCmpdFltSaveflag").hide();
				$(".overFltRateDefShow").text(rateTypeShow);
				$("#overFltRateDefShowName").text(rateTypeShow);	
				$("#rateTypeShowName").text("默认逾期利率为");
				$("#overFltRateDefShowName").text(rateTypeShow);
				$("#overFlotRateDefName").text("默认逾期利率：");
				$("#cmpFltRateDefName").text("默认复利利率：");
				$(".cmpFltRateDefShow").text(rateTypeShow);
			}else{//利率浮动
				$(".overCmpdFltSaveflag").show();
				$(".overFltRateDefShow").text("%");
				$("#overFltRateDefShowName").text("%");	
				$("#rateTypeShowName").text("默认逾期利率上浮为");
				$("#overFltRateDefShowName").text("%");
				$("#overFlotRateDefName").text("默认逾期利率上浮：");
				$("#cmpFltRateDefName").text("默认复利利率上浮：");
				$(".cmpFltRateDefShow").text("%");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//overCmpdFltSaveflag 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值）
	var getOverCmpdFltSaveflagHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.overCmpdFltSaveflag=="0"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div overCmpdFltSaveflag">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">逾期利率和复利利率保存到数据库的值：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdFltSaveflag" value="0" '+checkStr1+'/>正常年利率加浮动利率值</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overCmpdFltSaveflag" value="1" '+checkStr2+'/>浮动利率值</span>'
			+'</div>'
			+'</div>';
		return htmlStr;
	};
	
	//overCmpdFltSaveflag 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值） 点击事件
	var overCmpdFltSaveflagBindEvent = function(){
		$(".overCmpdFltSaveflag input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.overCmpdFltSaveflag=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};



    //逾期利息计算基数 0逾期本金  1借据余额
    var getCalcOverPrcpTypeHtml = function(mfSysKind){
        var checkStr1="",checkStr2="";
        if(mfSysKind.calcOverPrcpType=="0"){
            checkStr1='checked="checked"';
        }else{
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div calcOverPrcpType">'
            +'<div class="main-content">'
            +'<span class="margin_right_10">逾期利息计算基数：</span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="calcOverPrcpType" value="0" '+checkStr1+'/>逾期本金</span>'
            +'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="calcOverPrcpType" value="1" '+checkStr2+'/>借据余额</span>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    //逾期利息计算基数 0逾期本金  1借据余额  点击事件
    var calcOverPrcpTypeBindEvent = function(){
        $(".calcOverPrcpType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            mfSysKind.calcOverPrcpType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };


    //逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则）
	var getOverIntstFlagHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.overIntstFlag=="0"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div overIntstFlag">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">逾期（复利）利息计算方式：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overIntstFlag" value="0" '+checkStr1+'/>按照固定利率</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overIntstFlag" value="1" '+checkStr2+'/>按阶梯规则（需配置规则）</span>'
			+'</div>'
			+'</div>';
		return htmlStr;
	};
	
	//逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则） 点击事件
	var overIntstFlagBindEvent = function(){
		$(".overIntstFlag input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.overIntstFlag=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};

	//逾期利息计算天数 0 取正常每一期逾期的天数 1 取借据逾期的天数
	var getOverDaysFlagHtml = function(mfSysKind){
		var checkStr1="",checkStr2="";
		if(mfSysKind.overDaysFlag=="0"){
			checkStr1='checked="checked"';
		}else{
			checkStr2='checked="checked"';
		}
		var htmlStr = '<div class="item-div overDaysFlag">'
			+'<div class="main-content">'
			+'<span class="margin_right_10">逾期利息计算天数：</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overDaysFlag" value="0" '+checkStr1+'/>取正常每一期逾期的天数</span>'
			+'<span class="margin_right_10"><input class="margin_right_5" type="radio" name="overDaysFlag" value="1" '+checkStr2+'/>取借据逾期的天数</span>'
			+'</div>'
			+'</div>';
		return htmlStr;
	};
	
	//逾期利息计算天数 0 取正常每一期逾期的天数 1 取借据逾期的天数
	var overDaysFlagBindEvent = function(){
		$(".overDaysFlag input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.overDaysFlag=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//复利利息是否收取 1 不收取 2 收取
	var getCmpdRateTypeHtml = function(mfSysKind,rateTypeList){
		var rateTypeShow="";
		var inputStr="";
		var displayStr='style="display:none;"';
		$.each(rateTypeList,function(i,parmDic){
			if(mfSysKind.rateType==parmDic.optCode){
				rateTypeShow = parmDic.remark;
			}
		});
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.cmpdRateType=="1"){//收取
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
			displayStr="";
		}
		var htmlStr=""
		if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
			htmlStr = '<div class="item-div cmpdRateType">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>复利利息收取</span>'
			+'</span>'
			+'<span class="margin_left_60 cmpFltRateDefSpan" '+displayStr+'>'
			+ '<span><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率上浮：</span>'
			+'<span id="cmpFltRateDefRead" class="span-read"><span class="span-text">'+mfSysKind.cmpFltRateDef+'</span></span>'
			+'<span id="cmpFltRateDefEdit" class="span-edit"><input type="text" name="cmpFltRateDef" maxlength="10" class="cmpFltRateDef" value="'+mfSysKind.cmpFltRateDef+'"/></span><span class="cmpFltRateDefShow">%</span>'
			+'</span>'
			+ '</span>'
			+'</div>'
			+ '</div>';
		}else{
			htmlStr = '<div class="item-div cmpdRateType" >'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>复利利息收取</span>'
			+'</span>'
			+'<span class="margin_left_60 cmpFltRateDefSpan" '+displayStr+'>'
			+'<span><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率：</span>'
			+'<span id="cmpFltRateDefRead" class="span-read"><span class="span-text">'+mfSysKind.cmpFltRateDef+'</span></span>'
			+'<span id="cmpFltRateDefEdit" class="span-edit"><input type="text" name="cmpFltRateDef" maxlength="10" class="cmpFltRateDef" value="'+mfSysKind.cmpFltRateDef+'"/></span><span class="cmpFltRateDefShow">'+rateTypeShow+'</span>'
			+'</span>'
			+ '</span>'
			+'</div>'
			+ '</div>';	
		}
		
		return htmlStr;
	};
	//复利利息是否收取绑定事件
	var cmpdRateTypeBindEvent = function(){
		$(".cmpdRateType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){//不收取
				mfSysKind.cmpdRateType="0";
				mfSysKind.cmpFltRateDef="0";
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.cmpdRateType="1";
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				if(mfSysKindTmp.cmpdRateType=="1"){
					$(".cmpFltRateDefSpan").css("display","inline-block");
				}else{
					$(".cmpFltRateDefSpan").css("display","none");
					$("#cmpFltRateDefRead .span-text").text(mfSysKindTmp.cmpFltRateDef);
					$("input[name=cmpFltRateDef]").text(mfSysKindTmp.cmpFltRateDef);
				}
			});
		});
		//期限双击编辑事件
		$(".cmpdRateType .span-read").dblclick(function(){
			var type=$(this).data("type");
			$("#cmpFltRateDefRead").css("display","none");
			$("#cmpFltRateDefEdit").css("display","inline-block");
			$("input[name=cmpFltRateDef]").css("width",$(this).width());
			$("input[name=cmpFltRateDef]").focus();
		});
		//复利利息上浮编辑input框事件
		$(".cmpdRateType .span-edit input").blur(function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.cmpFltRateDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData,function(data){
				var mfSysKindTmp = data.mfSysKind;
				$("#cmpFltRateDefRead .span-text").text(mfSysKindTmp.cmpFltRateDef);
				$("#cmpFltRateDefEdit").css("display","none");
				$("#cmpFltRateDefRead").css("display","inline-block");
			});
		});
	};
	
	//费用罚息收取：0-不收取、1-收取 
	var getFeesumFaxiTypeHtml = function(mfSysKind){
		var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
		if(mfSysKind.feesumFaxiType=="1"){
			checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
		}
		var htmlStr = '<div class="item-div feesumFaxiType">'
			+'<div class="main-content">'
			+'<span class="item-checkbox">'
			+ checkspan
			+'<span>费用罚息收取：</span>'
			+'<span class="content-desc">逾期时是否收取费用产生的罚息</span>'
			+'</span>'
			+'</div>'
			+ '</div>';
			return htmlStr;
	};
	//费用罚息收取：0-不收取、1-收取绑定点击事件 feesumFaxiType
	var feesumFaxiTypeBindEvent = function(){
		$(".feesumFaxiType .checkbox-span").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			if($(this).hasClass("curChecked")){
				mfSysKind.feesumFaxiType="0";	//不收取
				$(this).removeClass("curChecked");
			}else{
				mfSysKind.feesumFaxiType="1";//收取
				$(this).addClass("curChecked");
			}
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//逾期违约金
	var getPenaltyFincHtml = function(mfSysKind){
		var overPenaltyMain = mfSysKind.overPenaltyMain;
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		var baseTypeStr1 = "",baseTypeStr2 = "",baseTypeStr3 = "",baseTypeStr4 = "",baseTypeStr5 = ""; 
		var cycleStr1 = "",cycleStr2 = "",cycleStr3 = "";
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
		}else if(overPenaltyMain.penaltyCalcBaseType=="5"){
			baseTypeStr5='checked="checked"';
		}
		if(overPenaltyMain.penaltyReceiveCycle=="1"){
			cycleStr1='checked="checked"';
		}else if(overPenaltyMain.penaltyReceiveCycle=="2"){
			cycleStr2='checked="checked"';
		}else if(overPenaltyMain.penaltyReceiveCycle=="3"){
			cycleStr3='checked="checked"';
		}
		var htmlStr = '<div class="item-div penaltyFinc">'
			+'<div class="main-content">'
			+'<span >逾期违约金：</span>'
			+'<div class="margin_top_10">'
			+'<span><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="1" '+checkStr1+'/>按比例</span></span>'
			+'<span class="padding_left_15 penalty" data-type="1" id="penalty1" '+contentSpanStr1+'>'
			+ '<span class="margin_right_10">'
			+'<span id="penaltyFincRead1" class="span-read"><span class="span-text">'+overPenaltyMain.penaltyReceiveValue+'</span></span>'
			+'<span id="penaltyFincEdit1" class="span-edit"><input type="text" name="peneceiveValue" class="penaltyReceiveValue" value="'+overPenaltyMain.penaltyReceiveValue+'"/></span>% '
			+'</span>'
			+'</span>'
			+'</div>'
			+'<div class="margin_top_10 divPenalty" id="divPenalty1" '+contentStr1+'>'
			+'<div class="padding_left_20 margin_bottom_10">'
			+'<span >计算基数：</span>'
			+'<span class="padding_left_15">'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="1" '+baseTypeStr1+'/>合同金额</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="2" '+baseTypeStr2+'/>借据金额</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="3" '+baseTypeStr3+'/>逾期本金</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="4" '+baseTypeStr4+'/>逾期金额</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType" value="5" '+baseTypeStr5+'/>当期应还总额</span>'
			+'</span>'
			+ '</div>'
			+'<div class="padding_left_20">'
			+'<span >计算周期：</span>'
			+'<span class="padding_left_15">'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle" value="1" '+cycleStr1+'/>一次性</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle" value="2" '+cycleStr2+'/>按逾期天数</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle" value="3" '+cycleStr3+'/>按逾期月份</span>'
			+'</span>'
			+ '</div>'
			+ '</div>'
			+'<div class="margin_top_10">'
			+'<span ><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="2" '+checkStr2+'/>固定金额</span></span>'
			+'<span class="padding_left_15 penalty" data-type="2" id="penalty2" '+contentStr2+'>'
			+'<span class="margin_right_10">'
			+'<span id="penaltyFincRead2" class="span-read"><span class="span-text">'+overPenaltyMain.penaltyReceiveValue+'</span></span>'
			+'<span id="penaltyFincEdit2" class="span-edit"><input type="text" name="peneceiveValue" class="penaltyReceiveValue" value="'+overPenaltyMain.penaltyReceiveValue+'"/></span>元 '
			+'</span>'
			+'</span>'
			+ '</div>'
			+'<div class="margin_top_10">'	
			+'<span><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType" value="3" '+checkStr3+'/>阶梯式收取(根据逾期天数不同设置不同比例或金额)</span></span>'
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
			mfSysPenaltyMain.kindNo=kindNo;
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
			mfSysPenaltyMain.kindNo=kindNo;
			mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		
		$(".penaltyFinc .span-read").dblclick(function(){
			var type = $(this).parents(".penalty").data("type");
			$("#penaltyFincRead"+type).css("display","none");
			$("#penaltyFincEdit"+type).css("display","inline-block");
			$("#penaltyFincEdit"+type+" input[name=peneceiveValue]").css("width",$(this).width());
			$("#penaltyFincEdit"+type+" input[name=peneceiveValue]").focus();
		});
		
		$(".penaltyFinc .span-edit input").blur(function(){
			var type = $(this).parents(".penalty").data("type");
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
			mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			top.penaltyType = type;
			updateMfSysPenalty(ajaxData,function(data){
				var mfSysPenaltyMain = data.mfSysPenaltyMain;
				$(".penalty .span-text").text(mfSysPenaltyMain.penaltyReceiveValue);
				$(".penalty input[name=peneceiveValue]").val(mfSysPenaltyMain.penaltyReceiveValue);
				$("#penaltyFincEdit"+top.penaltyType).css("display","none");
				$("#penaltyFincRead"+top.penaltyType).css("display","inline-block");
			});
		});
		
		$(".penaltyFinc .penaltyCalcBaseType").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
			mfSysPenaltyMain.penaltyCalcBaseType=$(this).val();
			mfSysPenaltyMain.penaltyType="1";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData);
		});
		$(".penaltyFinc .penaltyReceiveCycle").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
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
		var htmlStr = '<div class="item-div preRepayPenalty">'
			+'<div class="main-content">'
			+'<span>提前还款违约金：</span>'
			+'<div class="margin_top_10">'
			+'<span><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType" value="1" '+checkStr1+'/>按比例</span></span>'
			+'<span class="prePenalty" data-type="1" id="prePenalty1" '+contentSpanStr1+'>'
			+'<span id="penaltyReceiveValueRead1"  class="span-read"><span class="span-text">'+prePenaltyMain.penaltyReceiveValue+'</span></span>'
			+'<span id="penaltyReceiveValueEdit1" class="span-edit"><input type="text" name="prePeneceiveValue" class="penaltyReceiveValue" value="'+prePenaltyMain.penaltyReceiveValue+'"/></span>% '
			+'</span>'
			+'</span>'
			+'</div>'
			+'<div class="margin_top_10 divPrePenalty" id="divPrePenalty1" '+contentStr1+'>'
			+'<div class="padding_left_20 margin_bottom_10">'
			+'<span >计算基数：</span>'
			+'<span class="padding_left_15">'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType" value="1" '+baseTypeStr1+'/>合同金额</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType" value="2" '+baseTypeStr2+'/>借据金额</span>'
			+ '<span class="margin_right_10"><input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType" value="3" '+baseTypeStr3+'/>提前还款本金</span>'
			+'</span>'
			+ '</div>'
			+ '</div>'
			+'<div class="margin_top_10">'
			+'<span><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType" value="2" '+checkStr2+'/>固定金额</span></span>'
			+'<span class="prePenalty" data-type="2" id="prePenalty2" '+contentStr2+'>'
			+'<span id="penaltyReceiveValueRead2"  class="span-read"><span class="span-text">'+prePenaltyMain.penaltyReceiveValue+'</span></span>'
			+'<span id="penaltyReceiveValueEdit2" class="span-edit"><input type="text" name="prePeneceiveValue" class="penaltyReceiveValue" value="'+prePenaltyMain.penaltyReceiveValue+'"/></span>元 '
			+'</span>'
			+ '</div>'
			+'<div class="margin_top_10">'	
			+'<span><span class="margin_right_10"><input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType" value="3" '+checkStr3+'/>阶梯式收取(根据期限不同设置不同比例或金额)</span></span>'
			+'<a href="javascript:void(0);" onclick="MfKindCalcConfig.openPenaltyChild(\''+mfSysKind.kindNo+'\',2);" class="pointer prePenalty" id="prePenalty3" '+contentStr3+'>配置</a>'
			+ '</div>'
			+ '</div>'
		 + '</div>';
		
		return htmlStr;
	};
	//违约金设置绑定事件
	var preRepayPenaltyBindEvent = function(){
		$(".preRepayPenalty .typeInput").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
			mfSysPenaltyMain.penaltyReceiveType=$(this).val();
			mfSysPenaltyMain.penaltyType="2";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			updateMfSysPenalty(ajaxData,function(data){
				var mfSysPenaltyMain = data.mfSysPenaltyMain;
				$(".prePenalty").css("display","none");
				$(".divPrePenalty").css("display","none");
				
				$("#prePenalty"+mfSysPenaltyMain.penaltyReceiveType).css("display","inline-block");
				if(mfSysPenaltyMain.penaltyReceiveType=="1"){//按比例
					$("#divPrePenalty"+mfSysPenaltyMain.penaltyReceiveType).css("display","block");
				}
			});
		});

		$(".prePenalty .span-read").dblclick(function(){
			var type = $(this).parents(".prePenalty").data("type");
			$("#penaltyReceiveValueRead"+type).css("display","none");
			$("#penaltyReceiveValueEdit"+type).css("display","inline-block");
			$("#penaltyReceiveValueEdit"+type+" input[name=prePeneceiveValue]").css("width",$(this).width());
			$("#penaltyReceiveValueEdit"+type+" input[name=prePeneceiveValue]").focus();
		});
		
		$(".prePenalty .span-edit input").blur(function(){
			var type = $(this).parents(".prePenalty").data("type");
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
			mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
			mfSysPenaltyMain.penaltyType="2";
			var ajaxData = JSON.stringify(mfSysPenaltyMain);
			top.prePenaltyType = type;
			updateMfSysPenalty(ajaxData,function(data){
				var mfSysPenaltyMain = data.mfSysPenaltyMain;
				$(".prePenalty .span-text").text(mfSysPenaltyMain.penaltyReceiveValue);
				$(".prePenalty input[name=prePeneceiveValue]").val(mfSysPenaltyMain.penaltyReceiveValue);
				$("#penaltyReceiveValueEdit"+top.prePenaltyType).css("display","none");
				$("#penaltyReceiveValueRead"+top.prePenaltyType).css("display","inline-block");
			});
		});
		
		
		$(".preRepayPenalty .penaltyCalcBaseType").bind("click",function(){
			var mfSysPenaltyMain={};
			mfSysPenaltyMain.kindNo=kindNo;
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
    // 部分还款后是否允许提前还款标志 0 部分还款后不允许提前还款 1 部分还款后允许提前还款
    var getPartRepayPreFlagHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.partRepayPreFlag=="0"){
            checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div partRepayPreFlag">'
            +'<div class="main-content">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>部分还款后允许提前还款：</span>'
            +'<span class="content-desc">存在部分还款的借据允许提前还款</span>'
            +'</span>'
            +'</div>'
            + '</div>';
        return htmlStr;
    };
    //还款顺序变更绑定点击事件
    var partRepayPreFlagBindEvent = function(){
        $(".partRepayPreFlag .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=kindNo;
            if($(this).hasClass("curChecked")){//部分还款后 不允许提前还款
                mfSysKind.partRepayPreFlag="0";
                $(this).removeClass("curChecked");
            }else{//部分还款后 允许提前还款
                mfSysKind.partRepayPreFlag="1";
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            MfSysKindConfig.updateKindConfig(ajaxData);
        });
    };
//	//核算的高级设置
//	var getCalcAdvancedSetHtml = function(mfSysKind,data){
//		var contentHtml ="";
//		//一年天数设置
//		contentHtml= contentHtml + getYearDaysHtml(mfSysKind);
//		//利率小数位数设置
//		contentHtml= contentHtml + getRateDecimalDigitsHtml(mfSysKind,data.rateDigitsList);
//		//计息方式
//		contentHtml= contentHtml + getIcTypeHtml(mfSysKind,data.icTypeList);
//		//还款计划保留位数
//		contentHtml= contentHtml + getReturnPlanPointHtml(mfSysKind);
//		//还款计划小数舍入方式
//		contentHtml= contentHtml + getReturnPlanRoundHtml(mfSysKind);
//		//费用计划是否与还款计划合并
//		contentHtml= contentHtml + getFeePlanMergeHtml(mfSysKind);
//		//多次放款还款计划合并
//		contentHtml= contentHtml + getMultipleLoanPlanMergeHtml(mfSysKind);
//		//还款顺序
//		contentHtml= contentHtml + getRepaymentOrderHtml(mfSysKind,data.repaymentOrderTypeStr);
//		//结余处理方式
//		contentHtml= contentHtml + getBalanceDealTypeHtml(mfSysKind);
//		//逾期利率复利利率输入类型  0-利率浮动、1-利率 (0 表示表单输入的是利率浮动百分比，1 表示是利率值)
//		contentHtml= contentHtml + getOverCmpdRateInputtypeHtml(mfSysKind);	
//		//复利利息是否收取  1 不收取 2 收取
//		contentHtml= contentHtml + getCmpdRateTypeHtml(mfSysKind,data.rateTypeList);
//		//逾期违约金
//		contentHtml= contentHtml + getPenaltyFincHtml(mfSysKind);
//		//提前还款违约金
//		contentHtml= contentHtml + getAdvanceRepayHtml(mfSysKind);
//		var optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#advanceSet'+mfSysKind.kindNo+'">'
//				+'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
//				+'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
//				+'</div>';
//		var htmlStr = '<div id="advanceSet'+mfSysKind.kindNo+'" class="more-div collapse">'+contentHtml+'</div>'+optStr;
//		return htmlStr;
//	};
	return{
		init:_init,
		openPenaltyChild:_openPenaltyChild,
	};
}(window, jQuery);