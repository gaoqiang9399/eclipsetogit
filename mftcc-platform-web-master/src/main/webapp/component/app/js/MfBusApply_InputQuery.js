//产品改变时，校验拦截并获取该产品的表单
	function interceptAndGetForm(kindNo) {
		var parmData = {'nodeNo':'apply','relNo':cusNo,'cusNo':cusNo};
		if(kindNo!=null){
			parmData = {'nodeNo':'apply','relNo':cusNo,'cusNo':cusNo,'kindNo':kindNo};
		}
		$.ajax({
			url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
			data : {ajaxData:JSON.stringify(parmData)},
			async : false,
			success : function(data) {
				if (data.exsitRefused == true) {// 存在业务拒绝
					top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ cusNo, '风险拦截信息', function() {
						$("select[name=kindNo]").val(oldKindNo);
					});
				} else if (data.exsitFX == true) {//存在风险项
					alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : "该客户存在风险项","operation" : "新增业务"}), 2, function() {
						getForm(kindNo);
						oldKindNo = $("select[name=kindNo]").val();
					});
				} else {
					getForm(kindNo);
				 }
			},error : function() {
			}
		});
	};

	function getForm(val) {
		$.ajax({
			url : webPath+"/mfBusApply/chooseFormInitAjax",
			type : "post",
			dataType : "json",
			data:{cusNo:cusNo,kindNo:val,liftType:liftType,liftId:liftId},
			success : function(data) {
                var html;
                var index;
                var mfSysKindList;
				if (data.flag == "success") {
					html = data.htmlStr;
					$("#insertForApplyForm").empty().html(html);
                    $("#coborrNumName").css("display","block");
                     rechargeAmtBal =  data.rechargeAmtBal;
                     rechargeAmtBalShow =  data.rechargeAmtBalShow;
                     withdrawAmtBal =  data.withdrawAmtBal;
                     withdrawAmtBalShow =  data.withdrawAmtBalShow;
                     busModel =  data.busModel;
					if (data.feeShowFlag == "1") {
						var feeHtml = data.feeHtmlStr;
                        busFeeInfo(data.feeShowFlag,feeHtml);
						//去掉下拉项中的第一个
			 			$("select[name=feeType] option:first").remove();
			 			$("select[name=takeType] option:first").remove();
			 			//给费率值列添加验证事件
			 			$("input[name=rateScale]").attr("title","费率值");
			 			$("input[name=rateScale]").attr("datatype","3");
			 			$("input[name=rateScale]").attr("mustinput","1");
			 			// $("input[name=rateScale]").attr("onblur","checkFeeRateScale(this);")
					}
					//getKindList(val);
					maxAmt = data.maxAmt;
					minAmt = data.minAmt;
					minTerm = data.minTerm;
					maxTerm = data.maxTerm;
					termType = data.termType;
					minFincRate = data.minFincRate;
					maxFincRate = data.maxFincRate;
					if (data.creditflag == "success") {
						creditAmt = data.creditAmt;
						creditSum = data.creditSum;
						if (data.kindCreditflag == "success") {
							kindCreditAmt = data.kindCreditAmt;
						}
					}

                   /* var vouTypeOtherClass = $("[name=vouTypeOther]").parents("td").attr("class");
                    if(typeof(vouTypeOtherClass) != "undefined"){
                        if($("[name=vouType]").is(':visible')){
                            bindVouTypeByKindNoZB($("#insertForApplyForm").find('[name=vouType]'), data.firstKindNo);
                        }
                        bindVouTypeByKindNoOther($("#insertForApplyForm").find('[name=vouTypeOther]'), data.firstKindNo);
                    }else{
                        if($("[name=vouType]").is(':visible')){
                            bindVouTypeByKindNo($("#insertForApplyForm").find('[name=vouType]'), data.firstKindNo);
                        }
					}*/
                   var vouTypeOtherClass = $("[name=vouTypeOther]").parents("td").attr("class");
                   if(typeof(vouTypeOtherClass) != "undefined"){
                   	   var   parmDicList =  data.parmDicList;
                       $("[name=vouTypeOther]").parents("tr").children('td').eq(3).children('div').empty();
                       for ( index in parmDicList) {
                            var optName  =  parmDicList[index].optName;
                            var optCode  =  parmDicList[index].optCode;
                            $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append('<input type="checkbox" name="vouTypeOther" title="其他担保方式" mustinput="" datatype="0" datavalue='+optName+'   value=' +optCode+ ' onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">');
                       	    $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append(parmDicList[index].optName);
                       	}
                       if($("[name=vouType]").is(':visible')){
                           bindVouTypeByKindNoZB($("#insertForApplyForm").find('[name=vouType]'), data.firstKindNo);
                       }

                   }else{
                        if($("[name=vouType]").is(':visible')){
                            bindVouTypeByKindNo($("#insertForApplyForm").find('[name=vouType]'), data.firstKindNo);
                        }
                    }
                    //贷款投向选择组件
                    $("select[name=fincUse]").empty();
                    $("select[name=fincUse]").popupSelection({
                        searchOn : true,//启用搜索
                        inline : true,
                        items : data.fincUse,
                        multiple : false,
                        changeCallback:cleanFincUseSmShow
                        //单选
                    });
					//加载线下的产品下拉框列表
					$("select[name=kindNo]").empty();
					mfSysKindList = data.mfSysKindList;
					for ( index in mfSysKindList) {
						$("select[name=kindNo]").append("<option value="+ mfSysKindList[index].kindNo+">"+ mfSysKindList[index].kindName+ "</option>");
					}
					$("select[name=kindNo]").val(data.firstKindNo);

					// 是否隐藏 复利利率上浮字段
					if (data.cmpdRateType == "0") {//隐藏						
						$('input[name=cmpdFloat]').parents("td").hide();// 字段td
						$('input[name=cmpdFloat]').parents("td").prev("td")
								.hide();// 标签td
						$("input[name=cmpdFloat]").attr("mustinput", "");

					}
					//客户详情中发起的业务申请，去掉客户名称上的选择客户的事件和放大镜单位
					if(applyEnt=="cus"){
						$("input[name=cusName]").attr("onclick","");
						$("input[name=cusName]").parent().find("i").remove();
					}
					//初始化加载
					if(processId ==null || processId==''){//如果不存在processId,则隐藏该字段
					$("[name=firstApprovalUserName]").parents("tr").remove();// 字段td
					}
                    MfBusApplyInput.init();
					if(liftType != "3" && liftType != "4"){
                        MfBusApply_applyInput.init();
                    }else{
						if(liftType == "3"){
							let breedName1 = data.breedName1;
                            var kindNo =  $("input[name='kindNo']").val();
                            $.ajax({
                                url:  webPath + "/mfCusCreditApply/breedInitForApply",
                                data:{kindNo:kindNo},
                                type:'post',
                                dataType:'json',
                                sync:true,
                                success: function (data) {
                                    if (data.flag == "success") {
                                        $("input[name='breedNo1']").popupSelection({
                                            searchOn: false,//启用搜索
                                            inline: true,//下拉模式
                                            multiple: false,//多选
											async:false,
                                            items : data.items,
                                            changeCallback: function (obj,elem) {
                                                $("input[name='breedName1']").val(obj.data("text"));
                                            }
                                        });
                                        $("[name=popsbreedNo1]").parent().find(".pops-value").text(breedName1);
                                    }
                                }
                            });
						}
					}
                    // 初始化creditPactNo,creditPactId
                    $("input[name=creditPactNo]").val(data.creditPactNo);
                    $("input[name=creditPactId]").val(data.creditPactId);
                    creditBusinessRelation.init();
                    //初始化加载
            		$(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
            		$(".anchor-ctrl select").each(function(){handleAnchorFun(this);});           		
            		$("input[name=recommenderName]").click();
            		let loanKind = $("[name='loanKind']").val();
            		if(loanKind != "5" && loanKind != "6"){
                        $("[name='loanKind'] option[value='5']").remove();
                        $("[name='loanKind'] option[value='6']").remove();
                    }
                    if(busModel == "12"){
                        $("[name='vouType'] option[value='4']").remove();
                        $("[name='vouType1'] option[value='4']").remove();
                        $("#temporaryStorage").show();
                        $("#applySaveBth").hide();
					}else{
                        $("#temporaryStorage").hide();
                        $("#applySaveBth").show();
					}
				}else if(data.flag == "error"){
					html = data.htmlStr;
					$("#insertForApplyForm").empty().html(html);
					$("select[name=kindNo]").html("");
					$("input[name=cusName]").val(data.cusName);
					mfSysKindList = data.mfSysKindList;
					for ( index in mfSysKindList) {
						$("select[name=kindNo]").append("<option value="+ mfSysKindList[index].kindNo+">"+ mfSysKindList[index].kindName+ "</option>");
					}
					$("select[name=kindNo]").val("");
			    }

                if(data.ifQuotaCalc == '1'){
                    $("#quotaCalc").empty().html(data.formQuotaCalcStr);
                }else{
                    $("input[name='quotaCalc']").parent().parent().parent().hide();
                }
			}
		});
	}


	function calcQuotaSts(){
		$("#applyBaseDive").css('display','none');
		$("#applySaveBth").css('display','none');

		$("#quotaCalcDiv").css('display','block');
		$("#saveBtnCalc").css('display','block');

	}

	function changeFormDisplay  () {
		$("#quotaCalcDiv").css('display','none');
		$("#saveBtnCalc").css('display','none');

		$("#applyBaseDive").css('display','block');
		$("#applySaveBth").css('display','block');
	}

	function calcQuotaAjax (obj) {
		debugger;
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data: {ajaxData: dataParam},
				async : false,
				success : function(data) {
					if (data.flag == "success") {
						changeFormDisplay();
						window.top.alert(data.msg,1);
						$("input[name='quotaCalc']").val(CalcUtil.formatMoney(data.quotaCalc,2));
						$("input[name='appAmt']").val(CalcUtil.formatMoney(data.quotaCalc,2));
						if(typeof (data.bjAmtCalc) != 'undefined'){
							$("input[name='bjAmtCalc']").val(CalcUtil.formatMoney(data.bjAmtCalc,2));
						}
						if(typeof (data.houseAmtCalc) != 'undefined'){
							$("input[name='houseAmtCalc']").val(CalcUtil.formatMoney(data.houseAmtCalc,2));
						}
                        if(typeof (data.baseAmt) != 'undefined') {
                            $("input[name='baseAmtCalc']").val(CalcUtil.formatMoney(data.baseAmt,2));
                        }
                        if(typeof (data.guaranteeAmt) != 'undefined'){
                            $("input[name='guaranteeAmtCalc']").val(CalcUtil.formatMoney(data.guaranteeAmt,2));
                        }
                        if(typeof (data.financeLimit) != 'undefined'){
                            $("input[name='financeAmtCalc']").val(CalcUtil.formatMoney(data.financeLimit,2));
                        }
                        if(typeof (data.taxCalc) != 'undefined'){
                            $("input[name='taxAmtCalc']").val(CalcUtil.formatMoney(data.taxCalc,2));
                        }
					}else{
						window.top.alert(data.msg, 0);
					}
				},error : function() {
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			});
		}
	};


	function cleanFincUseSmShow() {
		$("[name=fincUseSmShow]").val("");
	}
	function selectKindNo() {
		var kindNo = $("select[name=kindNo]").val();
		if(kindNo!=undefined&&kindNo!=null&&kindNo!=''){
            interceptAndGetForm(kindNo);
            $("#tableHtml").empty();
		}else{
            $.ajax({
                url : webPath + "/mfBusApply/getKindNoAjax",
                data : {
                    cusNo:cusNo
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                    	if(data.mfSysKindSize > 0){
                            interceptAndGetForm(data.mfSysKind.kindNo);
                        }else{
                            getForm("");
						}
                    } else {
                        getForm("");
                    }
                },
                error : function() {
                    getForm("");
                }
            });
		}

	}



	function getFincUse(obj) {
		$("input[name=fincUse]").val(obj.fincUse);
		$("input[name=fincUseName]").val(obj.fincUseShow);
	};
	//跟据贷款投向获取二级分类
	function queryFincUseDetail() {
		var useid= $("input[name=fincUse]").val();
		$.ajax({
			url : webPath+"/parLoanuse/getFincUseSmAjax?useid="+useid,
			success : function(data) {
				//详细分类选择组件
				 $("input[name=popsfincUseSm]").popupSelection("updateItems",data.items)
				},error:function(){
				}
		});
	}
	function cancelApply() {
		myclose_click();
	};

	function showRiskInfo() {
		var appId = $("input[name=appId]").val();
		top.createShowDialog(webPath+'/riskForApp/preventList?relNo='+ appId, '风险拦截信息');
	};
	function hideDialog() {
		dialog.get('infoDialog').close();
	};
	function submitForm() {
		var obj = $("form[name=operform]");
		dialog.get('infoDialog').close();
		//if(submitJsMethod(obj,'')){
		obj.submit();
		//}

	};
	function getCusInfoArtDialog(cusInfo) {
		$("input[name=cusNameTogetherBorrower]").val(cusInfo.cusName);
		$("input[name=cusNoTogetherBorrower]").val(cusInfo.cusNo);
	};

	function getCusMngNameDialog(userInfo) {
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};

	function getOrgNameDialog(userInfo) {
		$("input[name=orgName]").val(userInfo.opName);
		$("input[name=orgNo]").val(userInfo.opNo);
	};

	function getAssName1Dialog(userInfo) {
		$("input[name=assName1]").val(userInfo.opName);
		$("input[name=assNo1]").val(userInfo.opNo);
	};

	function getAssName2Dialog(userInfo) {
		$("input[name=assName2]").val(userInfo.opName);
		$("input[name=assNo2]").val(userInfo.opNo);
	};
	function insertForApply(obj,type) {
        $("select[name=vouType1]").attr("disabled",false);
		var kindno = $("[name=kindNo]").val();
		var appId = $("[name=appId]").val();
		var ruleAppAmt = $("[name=appAmt]").val().replace(/,/g, "");
		var cusNoFund = $("[name=cusNoFund]").val();
		var parmData = {'nodeNo':'apply','relNo':cusNo,'cusNo':cusNo,'kindNo':kindno,'appAmt':ruleAppAmt,'appId':appId};
		if(cusNoFund!=null){
			parmData = {'nodeNo':'apply','relNo':cusNo,'cusNo':cusNo,'kindNo':kindno,'appAmt':ruleAppAmt,'cusNoFund':cusNoFund};
		}
		//检验保证金
        var assureAmt = $("[name=assureAmt]").val();
        var bondAccount = $("[name=bondAccount]").val();
        if (parseFloat(assureAmt) > 0&&(bondAccount==null||bondAccount=="")) {
            alert("请选择保证金账户！", 0);
            return;
        }
		$.ajax({
			url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
			data : {ajaxData : JSON.stringify(parmData)},
			success : function(data) {
				if (data.exsitRefused == true) {// 存在业务拒绝
					top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ cusNo, '风险拦截信息', function() {
						$("select[name=kindNo]").val(oldKindNo);
					});
				} else if (data.exsitFX == true) {//存在风险项
					//alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : "该客户存在风险项","operation" : "新增业务"}), 2, function() {
					alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : data.fxlist,"operation" : "新增业务"}), 2, function() {
                        saveApplyInfo(obj,type);
					});
				} else {
					var fincRate = $("input[name=fincRate]").val();
					if (parseFloat(fincRate) == 0) {
						alert(top.getMessage("CONFIRM_OPERATION_REASON", {"reason" : '申请利率为0',"operation" : '申请保存'}), 2, function() {
							saveApplyInfo(obj,type);
						});
					} else {
						saveApplyInfo(obj,type);
					}
				}
			},error : function() {
			}
		});
	};

	function saveApplyInfo(obj,type) {
		var url = $(obj).attr("action");
		var repayType = $("select[name='repayType']").val();
		var flag = submitJsMethod($(obj).get(0), '');
        var billflag = verificationAppAmt();
        if (flag&&billflag) {
            var datas = [];
            $("#busfee-list").find("tbody tr")
                .each(
                    function(index) {
                        var entity = {};
                        $thisTr = $(this);
                        entity.id = $thisTr.find("input[name=id]").val();
                        entity.itemNo = $thisTr.find("input[name=itemNo]")
                            .val();
                        entity.feeType = $thisTr.find(
                            "select[name=feeType]").val();
                        entity.takeType = $thisTr.find(
                            "select[name=takeType]").val();
                        entity.rateScale = $thisTr.find(
                            "input[name=rateScale]").val().replace(
                            /,/g, "");
						if(busModel=='12'){
                            entity.receivableFeeAmt = $thisTr.find(
                                "input[name=receivableFeeAmt]").val().replace(
                                /,/g, "");
						}
                        datas.push(entity);
                    });
            var plandatas = [];
            var appAmt =$("input[name=appAmt]").val().replace(/,/g, "");
            var repayPrcpSum =0;
            $("#repayplan-list").find("tbody tr")
                .each(
                    function(index) {
                        var entity = {};
                        $thisTr = $(this);
                        var repayPrcp =  $thisTr.find("input[name=repayPrcp]").val().replace(/,/g, "");
                        entity.termNum = $thisTr.find("input[name=termNum]").val();
                        entity.repayMonth = $thisTr.find("input[name=repayMonth]").val();
                        entity.repayPrcp =repayPrcp;
                        entity.repayPrcpBalAfter = $thisTr.find("input[name=repayPrcpBalAfter]").val().replace(/,/g, "");
                        repayPrcpSum = CalcUtil.add(repayPrcpSum,repayPrcp);
                        plandatas.push(entity);
                    });
            if(repayPrcpSum!=appAmt&&repayType==6){
                if(repayPrcpSum==0){
                    alert("请录入还款计划!", 0);
                }else{
                    alert("还款计划本金之和不等于申请金额", 0);
                }
                return;
			}
			var paramArray =  $(obj).serializeArray();
			paramArray.push({name:"actionType",value:type});
			if(updateType){
				var kindNo = $("[name=kindNo]").val();
				paramArray.push({name:"kindNo",value:kindNo});
				paramArray.push({name:"updateType",value:updateType});
			}
            if($("input[name='calc13']")!=null&&$("input[name='calc13']").val()!=undefined){
                paramArray.push({name:"calc13",value:$("input[name='calc13']").val().replace(/,/g, "")});
                paramArray.push({name:"calc1",value:$("select[name='calc1']").val()});
                paramArray.push({name:"peopleNum",value:$("input[name='peopleNum']").val()});
			}
            if($("input[name='calc2']")!=null&&$("input[name='calc2']").val()!=undefined){
                paramArray.push({name:"calc2",value:$("input[name='calc2']").val().replace(/,/g, "")});
            }
            paramArray = duplicateArrayRemoval(paramArray);
			var dataParam = JSON.stringify(paramArray);
			$.ajax({
				url : url,
				data : {ajaxData : dataParam,ajaxDataList : JSON.stringify(datas),planDataList : JSON.stringify(plandatas)},
				success : function(data) {
					if (data.flag == "success") {
						var url = webPath+'/mfBusApply/getSummary?appId='+ data.appId + '&busEntrance=apply';
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
						$(top.window.document).find("#showDialog").remove();
						showBusinessCount.applyCount();
						// 关闭当前弹层。
						myclose_click();
					} else {
						DIALOG.error(top.getMessage("FAILED_SAVE_CONTENT",{content : "", reason: data.msg}));
					}
				},error : function() {
					alert(top.getMessage("FAILED_SAVE"), 0);
				}
			});
		} else {
			//  				alert(  top.getMessage("FAILED_SAVE"),0);
		}
	}

	function duplicateArrayRemoval(paramArray) {
		var obj = {},//声明一个json对象
		result = [],//声明一个数组
		len = paramArray.length;
		for (var i = 0; i < paramArray.length; i++) {
			if (!obj[paramArray[i].name]) {
				obj[paramArray[i].name] = 1;
				result.push(paramArray[i]);
			}
		}
		return result;
	};

	//验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
	function checkByKindInfo(obj) {
		var name = $(obj).attr("name");
		var title = $(obj).attr("title").split("(")[0];
		//申请金额
		if (name == "appAmt") {
			if (maxAmt != null && minAmt != null && maxAmt != ""&& minAmt != "") {
				maxAmtNum = new Number(maxAmt);
				minAmtNum = new Number(minAmt);
				if(minAmtNum!=0 || maxAmtNum!=0 ){//金额范围最大值和最小值都为0时，申请金额大小不做控制
					var appAmt = $(obj).val();
					appAmt = appAmt.replace(/,/g, "");
					if (parseFloat(appAmt) < parseFloat(minAmtNum)|| parseFloat(appAmt) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
						$(obj).val(null);
						alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {"info" : "产品设置","field" : title,"value1" : fmoney(minAmtNum, 2),"value2" : fmoney(maxAmtNum, 2)}), 0);
					} else {
						if (typeof (creditAmt) != "undefined" && creditAmt != null && creditAmt != "" && typeof (creditSum) != "undefined" && creditSum !=null && creditSum != "") { //判断申请金额是否在授信余额内
							creditNum = new Number(creditAmt);
							creditSum = new Number(creditSum);
							var isRelCredit =$("select[name='isRelCredit']").val();
							if(isRelCredit==1){
                                if (parseFloat(appAmt) > parseFloat(creditSum)) {
                                    $(obj).val(null);
                                    alert("根据该客户的授信信息：申请金额不能大于授信余额：" +fmoney(creditSum, 2), 0);
                                } else {
                                    if (kindCreditAmt != null && kindCreditAmt != "") { //判断申请金额是否在产品授信余额内
                                        kindCreditNum = new Number(kindCreditAmt);
                                        if (parseFloat(appAmt) > parseFloat(kindCreditNum)) {
                                            $(obj).val(null);
                                            alert(top.getMessage("NOT_APPLY_VALUE_BIG",{"info" : "该客户产品授信","field" : title,"value" : fmoney(kindCreditNum,2)}), 0);
                                        };
                                    };
                                };
							}
						};
					};
				};
			};
		} else if (name == "fincRate") {//检测融资利率
			if (minFincRate != null && maxFincRate != null && minFincRate != "" && maxFincRate != "") {
				var maxFincRateNum = new Number(maxFincRate);
				var minFincRateNum = new Number(minFincRate);
				if(minFincRateNum!=0.00 || maxFincRateNum!=0.00){//利率范围最大值和最小值都为0时，申请利率大小不做控制
					var fincRate = $(obj).val();
					if (parseFloat(fincRate) < parseFloat(minFincRateNum)|| parseFloat(fincRate) > parseFloat(maxFincRateNum)) {//判断申请金额是否在产品设置范围内
						$(obj).val(null);
						alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {"info" : "产品设置","field" : title,"value1" : minFincRateNum,"value2" : maxFincRateNum}), 0);
					}
				}
			}
		}
	};
	function chooseKindType(obj) {
		selectKindNo();
	}
	function checkTerm(obj) {
		//修改校验期限的方法使其支持选择框
		var   isExist  =  $("select[name=term]").val();
        var appTerm;
		if(typeof(isExist)== "undefined"){
			appTerm = $("input[name=term]").val();
		}else{
			appTerm = $("select[name=term] option:checked").text();
		}
		appTerm = appTerm.replace(/,/g, "");
		var title = $("[name=term]").attr("title").split("(")[0];
		var appTermType = $("[name=termType]").val();
		appTermType = appTermType.replace(/,/g, "");
		var appMinTerm;
		var appMaxTerm;
		//申请期限
		if (minTerm != null && maxTerm != null && minTerm != "" && maxTerm != "" && termType != null && termType != "") {
			minTermNum = new Number(minTerm);
			maxTermNum = new Number(maxTerm);
			if(minTermNum!=0 || maxTermNum!=0){//期限范围在最大值最小值都是0的时候，申请期限不做限制
				var unit = appTermType == "1" ? "个月" : "天";
				if (appTermType == "1") {//表单填写申请期限为月
					if (termType == "2") {//产品申请期限为日
						minTermNum = (minTerm / 30).toFixed();
						maxTermNum = (maxTerm / 30).toFixed();
					}
				}
				if (appTermType == "2") {//表单填写申请期限为日
					if (termType == "1") {//产品申请期限为月
						minTermNum = (minTerm * 30).toFixed();
						maxTermNum = (maxTerm * 30).toFixed();
					}
				}
				appMinTerm = minTermNum + unit;
				appMaxTerm = maxTermNum + unit;
				//$("[name=term]").attr("placeholder",appMinTerm + "-" + appMaxTerm);
				if (parseFloat(appTerm) < parseFloat(minTermNum)|| parseFloat(appTerm) > parseFloat(maxTermNum)) {
					$("[name=term]").val("");
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {"info" : "产品设置","field" : title,"value1" : appMinTerm,"value2" : appMaxTerm}), 0);
				}
			}
		}
	
	};

	//利率类型变化事件，同时修改申请利率的单位
	function reteTypeChangeEvent(obj) {
		var rateType = $(obj).val();
		var unit = rateTypeMap[rateType].remark;
		$("input[name=fincRate]").parent(".input-group").find(".input-group-addon").text(unit);
		$("input[name=overRate]").parent(".input-group").find(".input-group-addon").text(unit);
		$("input[name=cmpdRate]").parent(".input-group").find(".input-group-addon").text(unit);
		var kindNoTmp = $("[name=kindNo]").val();
		$.ajax({
			url : webPath+"/mfBusApply/getFincRateRangeAjax",
			data : {kindNo:kindNoTmp,rateType:rateType},
            type : "post",
            dataType : "json",
			success : function(data) {
				if(data.flag=="success"){
					if(data.fincRateRange!=""){
						var maxFincRateNum = new Number(data.minFincRate);
						var minFincRateNum = new Number(data.maxFincRate);
						$("input[name=fincRate]").attr("placeholder",data.fincRateRange);
						$("input[name=fincRate]").val(data.fincRate);
						minFincRate = data.minFincRate;
						maxFincRate = data.maxFincRate;
					}
				}
			},error:function(){
				
			}
		});
		
		
	}
	function updatePactEndDate() {
		var beginDate = $("input[name=ext5]").val();
		var term = $("input[name=term]").val();
		if (term == '') {
			return;
		}
		var termType = $("[name=termType]").val();
		var intTerm = parseInt(term);
        var d,str;
		if (1 == termType) { //融资期限类型为月 
			d = new Date(beginDate);
			d.setMonth(d.getMonth() + intTerm);
			str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? d.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
			$("input[name=ext6]").val(str);
		} else { //融资期限类型为日 
			d = new Date(beginDate);
			d.setDate(d.getDate() + intTerm);
			str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? beginDate.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
			$("input[name=ext6]").val(str);
		}
	}

	function changeDisplayInput() {
		var busModel = $("input[name=busModel]").val();
		if (busModel == '18') {//
			var ext2 = $("select[name=ext2]").val();
			if (ext2 == '0') {
				$("select[name=ext2]").parent().parent().next().next().hide();
				$("select[name=ext2]").parent().parent().next().hide();
				$("input[name=ext3]").removeAttr("mustinput");
			} else {
				$("select[name=ext2]").parent().parent().next().show();
				$("select[name=ext2]").parent().parent().next().find("label")
						.prepend("<font color=\"#FF0000\">*</font>");
				$("select[name=ext2]").parent().parent().next().next().show();
				$("input[name=ext3]").attr("mustinput", "1");
			}
		}
	}
	

	function busFeeInfo(feeShowFlag,feeHtmlStr){
		if (feeShowFlag == "1" && feeHtmlStr != "") {
			$('#busfee-list').html(feeHtmlStr);
			$('#busfee-list tbody tr').each(function(){
				var trObj = $(this);
				var optPower = trObj.find('input[name=optPower]').val();
				var itemNo = trObj.find('input[name=itemNo]').val();
				if(optPower!='1'){//改
					trObj.find('input').each(function(){
						var txt = "<span id = '"+ $(this).attr("name") +"Span'>" + $(this).val() + "</span>";
						$(this).before(txt).hide();
					});
					trObj.find('select').each(function(){
						var txt = $(this).find("option:selected").text();
						$(this).before(txt).hide();
					});
				}else{
					if(busModel == "12"){
                        trObj.find('input[name=rateScale]').bind("change",function(){
                            calcFee(this);
						});
                        trObj.find('input[name=receivableFeeAmt]').bind("change",function(){
                            calcSumFee(this);
						});
                    }
				}
                if(itemNo==13||itemNo==14||itemNo==15){
                    trObj.hide();
                }
			});
			$('#busfee-list table').find("colgroup").remove();
			$('.busfeeInfo').removeClass('hidden');
		}
	}

    function calcFee(obj){
		let appAmt = $("[name='appAmt']").val().replace(/,/g,"");
		let rateScale = $(obj).val();
		$(obj).parent().next().find("input[name=receivableFeeAmt]").val(CalcUtil.formatMoney(CalcUtil.divide(CalcUtil.multiply(appAmt,rateScale),100),2));
        let shouldFeeSum = 0.00;
        $('#busfee-list tbody tr').each(function(){
            var trObj = $(this);
            let obj = trObj.find('input[name=rateScale]');
            let rateScale = $(obj).val();
            let  itemNo = $(obj).parents("tr").find("input[name=itemNo]").val();
            if(itemNo == "1" || itemNo == "3"){
                shouldFeeSum = CalcUtil.add(shouldFeeSum,CalcUtil.divide(CalcUtil.multiply(appAmt,rateScale),100));
            }
        });
        $("[name='shouldFeeSum']").val(CalcUtil.formatMoney(shouldFeeSum,2));
    }
    function calcSumFee(obj){
		let shouldFeeSum = 0.00;
        $('#busfee-list tbody tr').each(function(){
            var trObj = $(this);
            let obj = trObj.find('input[name=receivableFeeAmt]');
            let receivableFeeAmt = $(obj).val();
            if(receivableFeeAmt != null && receivableFeeAmt != ""){
                receivableFeeAmt = receivableFeeAmt.replace(/,/g,"");
			}
            let  itemNo = $(obj).parents("tr").find("input[name=itemNo]").val();
            if(itemNo == "1" || itemNo == "3"){
                shouldFeeSum = CalcUtil.add(shouldFeeSum,receivableFeeAmt);
            }
        });
        $("[name='shouldFeeSum']").val(CalcUtil.formatMoney(shouldFeeSum,2));
    }
    //查询银行借款
	function  getBorrowBank(){
		$("input[name=cusNameFund]").popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl : webPath+"/mfBusAgencies/getMfBusPageListAjax", // 请求数据URL
			valueElem:"input[name='cusNoFund']",//真实值选择器
			title: "选择机构",//标题
			changeCallback:function(elem){//回调方法
				var tmpData = elem.data("selectData");
				$("input[name=cusNoFund]").val(tmpData.agenciesDetailUid);
				$("input[name=cusNameFund]").val(tmpData.agenciesDetailName);
			},
			tablehead:{//列表显示列配置
				"agenciesDetailName":"名称",
				"agenciesDetailUid":"机构编号"
			},
			returnData:{//返回值配置
				disName:"agenciesDetailName",//显示值
				value:"agenciesDetailUid"//真实值
			}
		});
		$("input[name=cusNameFund]").next().click();
	 }
	
	//查询签约主体
	function  getSigningSubject(){
    	 $("input[name=signingSubject]").popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl : webPath+"/sysOrg/getSigningSubject", // 请求数据URL
			valueElem:"input[name='signingSubject']",//真实值选择器
			title: "选择签约主体",//标题
			changeCallback:function(elem){//回调方法
				var tmpData = elem.data("selectData");
				$("input[name=signingSubject]").val(tmpData.brNo);
				//$("input[name=cusNameFund]").val(tmpData.agenciesName);
			},
			tablehead:{//列表显示列配置
				"brName":"签约主体名称",
				"brNo":"签约主体编号"
			},
			returnData:{//返回值配置
				disName:"brName",//显示值
				value:"brNo"//真实值
			}
		});
		$("input[name=signingSubject]").next().click();
	}

	//根据产品号确定产品的期限
	function    changeKindNo(){
		 var  kindNo  =	$("select[name=kindNo]").val();
		 if(kindNo == "1004"){
			$("input[name=term]").val("3"); 
			$("input[name=term]").attr({ readonly: 'true' });
		 }
	}

    function changeVouTypeNew(vouType) {
        var  kindNo =  $('[name=kindNo]').val();
        getSubOpinionForChange(kindNo,vouType);
	}

  function  getSubOpinionForChange(kindNo,vouType){
    var opinionType = $("[name=opinionType]").val();
    $.ajax({
		url:webPath+"/mfSysKind/getVouTypeOtherSelectByNoNewAjax?kindNo="+kindNo+"&vouTypeOwner="+vouType,
        success : function(data) {
            var   parmDicList =  data.parmDicList;
            $("[name=vouTypeOther]").parents("tr").children('td').eq(3).children('div').empty();
            for ( var index in parmDicList) {
                $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append('<input type="checkbox" name="vouTypeOther" title="其他担保方式" mustinput="" datatype="0" datavalue='+parmDicList[index].optName+' value='+parmDicList[index].optCode+' onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">');
                $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append(parmDicList[index].optName);
            }
        },error : function() {

        }
    });
};


function bindVouTypeByKindNoZB(input, kindNo){
    var $obj = $(input);
    if($obj.length > 0) {
        $obj.popupSelection({
            ajaxUrl: webPath + "/mfSysKind/getVouTypeSelectByNoAjax?kindNo=" + kindNo,
            searchOn: false,//启用搜索
            inline: true,//下拉模式
            multiple: false,//单选
            changeCallback: function (elem) {
            	changeVouTypeNew(elem.val());
            }
        })
       }
    }
//校验申请金额和关联押品货值金额大小
function verificationAppAmt(){
    var flag =true;
    var appAmt = $("input[name='appAmt']").val();
    var pledgeBillNo = $("input[name='pledgeBillNo']").val();
    if (pledgeBillNo!=undefined&&pledgeBillNo!=""){
        var cusNo = $("input[name='cusNo']").val();
        var creditProjectId = $("input[name='creditProjectId']").val();
        $.ajax({
            url : webPath + "/pledgeBaseInfo/verificationAppAmt",
            data : {cusNo:cusNo,
                appAmt:appAmt,
                pledgeBillNo:pledgeBillNo,
                creditProjectId:creditProjectId},
            async:false,
            success : function(data) {
                $("input[name=pledgeBillValue]").val(data.pledgeBillValue);
                $("input[name=sumBillValue]").val(data.sumBillValue);
                if (data.billNoFlag==1){
                    alert(data.msg,0);
                    flag =false;
                }
            },error : function() {

            }
        });
    }
    return flag;
}