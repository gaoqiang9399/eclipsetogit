
var MfBusApply_applyInput=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        if(liftType == "3" || liftType == "4"){
            cusNo = liftCusNo;
            getForm(liftKindNo);
        }
	};
	//选择客户
	var _selectCusDialog=function(){
		selectCusDialog(_selectCusBack,"","","8");
	};
	var _selectFincDialog=function(){
		var cusNo = $("input[name=cusNo]").val();
		selectFincDialog(_selectFincBack,cusNo,"","1");
	};
	var _selectFincBack=function(mfBusFincApp){
		$("input[name=isBrrNew2ReOld]").val("1");//借新还旧标识置为1
		$("input[name=oldAppName]").val(mfBusFincApp.appName);
		
		$("input[name=oldFincId]").val(mfBusFincApp.fincId);
		$("input[name=oldAppId]").val(mfBusFincApp.appId);
		$("input[name=oldAppName]").val(mfBusFincApp.appName);
		$("input[name=oldPactId]").val(mfBusFincApp.fincId);
		$("input[name=oldPactNo]").val(mfBusFincApp.pactNo);
		$("input[name=oldKindNo]").val(mfBusFincApp.kindNo);
		$("input[name=oldKindName]").val(mfBusFincApp.kindName);
		//TODO 金额、利率带出
		$("input[name=appAmt]").val(mfBusFincApp.putoutAmt);
		$("input[name=term]").val(mfBusFincApp.termMonth);
		$("select[name=rateType] option").prop('selected',false);
		$("select[name=rateType]").find("option[value='"+mfBusFincApp.rateType+"']").prop("selected",true);
		$("input[name=fincRate]").val(mfBusFincApp.fincRate);
		$("input[name=overRate]").val(mfBusFincApp.overRate);
		$("select[name=repayType] option").prop('selected',false);
		$("select[name=repayType]").find("option[value='"+mfBusFincApp.repayType+"']").prop("selected",true);
		$("input[name=cmpdRate]").val(mfBusFincApp.cmpdRate);
		$("select[name=icType] option").prop('selected',false);
		$("select[name=icType]").find("option[value='"+mfBusFincApp.icType+"']").prop("selected",true);
		//修改担保方式
		var vouTypeName = $("select[name=popsvouType]").find("option[value='"+mfBusFincApp.vouType+"']").text();
		$("select[name=popsvouType]").parent().find(".pops-value").text(vouTypeName);
		$("input[name=vouType]").val(mfBusFincApp.vouType);
		
		//贷款用途
		$("select[name=fincUse] option").prop('selected',false);
		$("select[name=fincUse]").find("option[value='"+mfBusFincApp.fincUse+"']").prop("selected",true);
		//征信用途
		$("input[name=fincUseSmDes]").val(mfBusFincApp.fincUseSmDes);
		$("textarea[name=fincUseDes]").val(mfBusFincApp.fincUseDes);
		$("input[name=fincUseSm]").val(mfBusFincApp.fincUseSm);
		
	};
	//选择客户回调
	var _selectCusBack=function(cus){
		cusNo=cus.cusNo;
        $.ajax({
            url : webPath+"/mfCusCustomer/checkCusProSizeInfo",
            type : "post",
            async: false,
            data : {cusNo:cusNo},
            dataType : "json",
            success : function(data) {
                if(data.flag == "success"){
                    $("input[name=cusNo]").val(cusNo);
                    $("input[name=cusName]").val(cus.cusName);
                    _checkCus();
                }else{
                    window.top.alert(data.msg,0);
                    $("input[name='cusNo']").val("");
                    $("input[name='cusName']").parent().find(".pops-value").text("");
                    return;
                }
            },
            error : function() {
                window.top.alert(top.getMessage("ERROR_DATA_CREDIT","客户信息"),0);
            }
        });

	};
	//验证客户是否能申请业务.验证客户基本信息是否填写，验证客户准入
	var _checkCus=function(){

		//校验业务节点的必填要件上传
		if(!valiDocIsUp(cusNo)){
			return false;
		}
       //校验客户基本信息处的必填要件上传
       if( !_valiCusDocIsUp(cusNo)){
           return false;
	    }

      // 判断该客户是否完善了基本信息
		$.ajax({
			url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
			success : function(data) {
				if (data.fullFlag == '1') {// 全部都填写了
					// 准入拦截
					var parmData = {'nodeNo':'cus_apply', 'relNo': cusNo, 'cusNo': cusNo};
					$.ajax({
						url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
						type : "post",
						data : {ajaxData: JSON.stringify(parmData)}, 
						dataType : "json",
						success : function(data) {
							if (data.exsitRefused == true) {// 存在业务拒绝
								top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){
									
								});
								$("input[name=cusNo]").val("");
								$("input[name=cusName]").val("");
							}else if(data.exsitFX == true){//存在风险项
								alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"业务申请"}), 2, function() {
									$("select[name=kindNo]").val(firstKindNo);
									selectKindNo();
								},function(){
									$("input[name=cusNo]").val("");
									$("input[name=cusName]").val("");
								});
							}else {
								selectKindNo();
							}
						},
						error : function() {
						}
					});
				} else if (data.fullFlag == '0') {
					$("input[name=cusNo]").val("");
					$("input[name=cusName]").val("");
					alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
				}
			}
		});
	}

	//进件处控制 客户基本信息要件的上传
    var _valiCusDocIsUp = function(relNo){
        var flag = true;
        $.ajax({
            type: "post",
            dataType: 'json',
            url:webPath+ "/docBizManage/valiCusDocIsUp",
            data:{relNo:relNo},
            async: false,
            success: function(data) {
                if(!data.flag){
                    window.top.alert(data.msg,0);
                }
                flag = data.flag;
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
            }
        });
        return flag;
    }



	//返回进件列表
	var _cancelApply=function(){
	    let url = webPath+"/mfBusApply/getListPage?entranceNo=2";
		if(liftType == "4"){
            url = webPath+"/mfGuaranteeLift/getRenewInsuranceListPage";
        }
        window.location.href = url;
    };
	//返回所有已完成授信合同
    var _selectCreditContract=function(){
        selectCreditContract(_selectCreditBack,"","","6");
    };
    //授信合同回调函数
    var _selectCreditBack=function(info){
        $("input[name=creditPactId]").val(info.id);//授信合同id
        $("input[name=creditAppId]").val(info.creditAppId);//授信合同id
        $("input[name=creditSum]").val(fmoney(info.creditSum, 2));//授信总额
        $("input[name=creditAmt]").val(fmoney(info.authBal, 2));//授信余额
        $("input[name=creditPactNo]").val(info.pactNo);//授信合同id
        $("select[name=vouType1]").val(info.creVouType);//授信合同id
        $("select[name=vouType1]").attr("disabled",true);
        //初始化合作银行
        _agenciesInit(info.creditAppId);

        $("input[name=agenciesUid]").val(info.agenciesUid);//合作银行id
        $("input[name=agenciesName]").val(info.agenciesName);//合作银行名称
    };
   var  _agenciesInit = function (creditAppId) {
        $.ajax({
            url:  webPath + "/mfCusCreditApply/getAgenciesListByCreditAppId",
            data:{creditAppId:creditAppId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    if($("[name=popsagenciesId]").length > 0){
                        $("[name=popsagenciesId]").popupSelection("updateItems",data.items);
                    }else{
                        $("input[name='agenciesId']").popupSelection({
                            searchOn: false,//启用搜索
                            inline: true,//下拉模式
                            multiple: false,//多选
                            items : data.items,
                            changeCallback: function (obj,elem) {
                                $("input[name='agenciesName']").val(obj.data("text"));
                                var agenciesId = $("input[name='agenciesId']").val()
                                var  agenciesList = data.items;
                                for (var index in agenciesList){
                                    if(agenciesId==agenciesList[index].id){
                                        $("input[name='agenciesCreditAmt']").val(agenciesList[index].bankCreditAmt);
                                        $("input[name='putoutTerm']").val(agenciesList[index].putoutTerm);
                                        $("input[name='extendTerm']").val(agenciesList[index].extendTerm);
                                    }
                                }
                                $("input[name='breedName']").val("");
                                $("input[name='breedNo']").val("");
                                $("input[name='breedCreditAmt']").val("");
                                $("input[name='agenciesId']").parent().find(".error").remove();
                                _breedInit(agenciesId,creditAppId);
                            }
                        });
                    }
                    var agenciesId = $("input[name='agenciesId']").val()
                    if(agenciesId!=''){
                        var  agenciesList = data.items;
                        for (var index in agenciesList){
                            if(agenciesId==agenciesList[index].id){
                                $("input[name='agenciesCreditAmt']").val(agenciesList[index].bankCreditAmt);
                                $("input[name='putoutTerm']").val(agenciesList[index].putoutTerm);
                                $("input[name='extendTerm']").val(agenciesList[index].extendTerm);
                            }
                        }
                    }
                }
            }
        });
    }
    //新增业务品种初始化
    var _breedInit =function (breedAgenciesId,creditAppId){
        $.ajax({
            url:  webPath + "/mfCusCreditApply/getBreedListByCreditAppId",
            data:{creditAppId:creditAppId,agenciesId:breedAgenciesId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    if(breedInitFlag==0){
                        $("input[name='breedNo']").popupSelection({
                            searchOn: false,//启用搜索
                            inline: true,//下拉模式
                            multiple: false,//多选
                            items : data.items,
                            changeCallback: function (obj,elem) {
                                $("input[name='breedName']").val(obj.data("text"));
                                var breedNo = $("input[name='breedNo']").val()
                                var  breedList = data.items;
                                for (var index in breedList){
                                    if(breedNo==breedList[index].id){
                                        $("input[name='breedCreditAmt']").val(breedList[index].breedCreditAmt);
                                    }
                                }
                            }
                        })
                        breedInitFlag=1;
                        var breedNo = $("input[name='breedNo']").val()
                        if(breedNo!=''){
                            var  breedList = data.items;
                            for (var index in breedList){
                                if(breedNo==breedList[index].id){
                                    $("input[name='breedCreditAmt']").val(breedList[index].breedCreditAmt);
                                }
                            }
                        }
                    }else{
                        $("input[name=popsbreedNo]").popupSelection("updateItems",data.items);
                    }

                }
            }
        });
    }
    //处理基础利率以及浮动点数及百分比（输入期限或者修改期限时使用）
    var _getCalcRateByTermAjax = function () {
        var term = $("[name=term]").val();//申请期限
        if(term==0){
            $("[name=term]").val('');
            window.top.alert("申请期限不能为零！",0);
        }
        var termType = $("[name=termType]").val();//期限类型
        var kindNo = $("[name=kindNo]").val();//产品号
        var fincRate = $("[name=fincRate]").not(':disabled').val();//申请利率
        var baseRateType = $("[name=baseRateType]").val();//基础利率类型
        var rateType = $("[name=rateType]").val();//执行利率类型
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfBusApply/getCalcRateByTermAjax",
            data: {
                term: term,
                termType: termType,
                kindNo: kindNo,
                fincRate: fincRate,
                baseRateType: baseRateType,
                rateType: rateType
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    if (baseRateType == "4") {
                        $("input[name=baseRate]").val(data.baseRate);
                        $("input[name=floatNumber]").val(data.floatNumber);
                        $("input[name=lprNumber]").val(data.rateNo);
                    } else {
                        $("input[name=baseRate]").val(data.baseRate);
                        $("input[name=fincRateFloat]").val(data.fincRateFloat);
                    }
                } else {
                    window.alert(data.msg, 0);
                }
            },
            error: function () {
                window.alert("输入或修改期限处理利率失败", 0);
            }
        });
    };


    //处理以及浮动点数及百分比（输入执行利率或修改逾期利率时使用）
    var _getCalcRateByFincRateAjax = function () {
        var term = $("[name=term]").val();//申请期限
        if (term == "") {
            window.alert("请先输入期限", 0);
            $("input[name=fincRate]").val("");
            return false;
        }
        var baseRate = $("input[name=baseRate]").val();//基础利率
        var fincRate = $("[name=fincRate]").not(':disabled').val();//执行利率
        var baseRateType = $("[name=baseRateType]").val();//基础利率类型
        var rateType = $("[name=rateType]").val();//利率类型
        var kindNo = $("[name=kindNo]").val();//产品号
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfBusApply/getCalcRateByFincRateAjax",
            data: {
                fincRate: fincRate,
                baseRateType: baseRateType,
                rateType: rateType,
                kindNo: kindNo,
                baseRate: baseRate
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    if (baseRateType == "4") {
                        $("input[name=floatNumber]").val(data.floatNumber);
                    } else {
                        $("input[name=fincRateFloat]").val(data.fincRateFloat);
                    }
                } else {
                    window.alert(data.msg, 0);
                }
            },
            error: function () {
                window.alert("输入或修改执行利率处理失败", 0);
            }
        });
    };


    var _onchangeBaseRateType = function () {
        $("input[name=term]").val("");
        $("input[name=baseRate]").val("");
        $("input[name=floatNumber]").val("");
        $("input[name=lprNumber]").val("");
        $("input[name=baseRate]").val("");
        $("input[name=fincRateFloat]").val("");
    };

    var _selectChangeApply = function () {
        $("input[name='appIdOld']").popupList({
            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusApply/findLoanFrontByPageAjax",//请求数据URL
            valueElem:"input[name='appIdOld']",//真实值选择器
            title: "选择业务",//标题
            searchplaceholder: "客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.appId);
            },
            tablehead:{//列表显示列配置
                "appId":"申请编号",
                "appName":"项目名称",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"appName",//显示值
                value:"appId"//真实值
            }
        });
        $("input[name='appIdOld']").next().click();
    };

    var _getForm = function(appId){
        $.ajax({
            url : webPath+"/mfBusApply/getApplyChange?appId=" + appId,
            success : function(data) {
                if (data.flag == "success") {
                    var mfBusApply = data.mfBusApply;
                    busModel = data.busModel;
                    $("input[name='cusName']").val(mfBusApply.cusName);
                    $("input[name='cusNo']").val(mfBusApply.cusNo);
                    $("select[name='kindNo']").val(mfBusApply.kindNo);
                    $("input[name='appAmt']").val(mfBusApply.appAmt);
                    $("select[name='rateType']").val(mfBusApply.rateType);
                    $("input[name='term']").val(mfBusApply.term);
                    $("select[name='termType']").val(mfBusApply.termType);
                    $("input[name='baseRateType']").val(mfBusApply.baseRateType);
                    $("input[name='baseRate']").val(mfBusApply.baseRate);
                    $("input[name='fincRateFloat']").val(mfBusApply.fincRateFloat);
                    $("input[name='floatNumber']").val(mfBusApply.floatNumber);
                    $("select[name='repayType']").val(mfBusApply.repayType);
                    $("input[name='overRate']").val(mfBusApply.overRate);
                    $("input[name='repayTerm']").val(mfBusApply.repayTerm);
                    $("input[name='repayPrcpAmt']").val(mfBusApply.repayPrcpAmt);
                    $("select[name='icType']").val(mfBusApply.icType);
                    $("input[name='cmpdRate']").val(mfBusApply.cmpdRate);
                    $("select[name='fincUse']").val(mfBusApply.fincUse);
                    $("input[name='vouRate']").val(mfBusApply.vouRate);
                    $("select[name='vouType']").val(mfBusApply.vouType);
                    $("select[name='vouOption']").val(mfBusApply.vouOption);
                    $("input[name='remark']").val(mfBusApply.remark);
                    $("select[name='popsfincUse']").val(mfBusApply.fincUse);
                    busFeeInfo(data.feeShowFlag,data.feeHtmlStr);
                }
            }
        });

    };

    var _repayplanInfo=function (){
        var repayType =  $("select[name=repayType]").val();
        if(repayType==6){
            $.ajax({
                url : webPath+"/mfBusApply/repayplanInitAjax?repayType=" + repayType ,
                success : function(data) {
                    if (data.flag == "success") {
                        var planShowFlag = data.planShowFlag;
                        var planHtmlStr = data.planHtmlStr;
                        if (planShowFlag == "1" && planHtmlStr != "") {
                            $('#repayplan-list').html(planHtmlStr);
                            $('#repayplan-list tbody tr').each(function(){
                                var trObj = $(this);
                                trObj.find('input').each(function(){
                                    var txt = "<span id = '"+ $(this).attr("name") +"Span'>" + $(this).val() + "</span>";
                                    $(this).before(txt).hide();
                                });
                                trObj.find('select').each(function(){
                                    var txt = $(this).find("option:selected").text();
                                    $(this).before(txt).hide();
                                });
                            });
                            $('#repayplan-list table').find("colgroup").remove();
                            $('.repayplanInfo').removeClass('hidden');
                        }
                    }
                }
            });
        }else{
            $('#repayplan-list').html("");
            $('.repayplanInfo').addClass('hidden');
        }
    }
    //添加一列新的td
    var _insertTr = function(){
        var appAmt =  $("input[name=appAmt]").val();
        if(appAmt==""){
            window.top.alert("请先输入申请金额!",0);
            return ;
        }
        //期号
        var termNum = "";
        //开始日期
        var planBeginDate = "";
        $(".repayplanInfo").find(".tableMessage").hide();
        var obj = $(".repayplanInfo").find("tr:last");
        //获取上一期的剩余本金
        var repayPrcpBalAfterOld = obj.find("td").find("input[name='repayPrcpBalAfter']").val();
        if(repayPrcpBalAfterOld!=null&&repayPrcpBalAfterOld!=undefined){
            repayPrcpBalAfterOld = repayPrcpBalAfterOld.replace(/,/g,"");
        }else{
            repayPrcpBalAfterOld = appAmt;
        }
        //获取上一期的还款月份
        var repayMonthLast = obj.find("td").find("input[name='repayMonth']").val();
        if(repayMonthLast!=null&&repayMonthLast!=undefined){
            repayMonthLast = repayMonthLast.replace(/,/g,"");
        }else{
            repayMonthLast = "0";
        }
        var reg = /^[1-9]+[0-9]*]*$/;//验证是否为正整数
        termNum = obj.find("input[name='termNum']").val();
        planBeginDate = obj.find("input[name='planEndDate']").val();

        if(termNum!='' && termNum!= null && termNum != undefined){
            var repayPrcp = obj.find("input[name='repayPrcp']").val();
            if(repayPrcp=='' || repayPrcp== null || repayPrcp == undefined|| Number(repayPrcp)<=0){
                window.top.alert("请先完成上期本金填写!",0);
                return ;
            }
            var repayMonth = obj.find("input[name='repayMonth']").val();
            if(repayMonth=='' || repayMonth== null || repayMonth == undefined|| Number(repayMonth)  <=0){
                window.top.alert("请先完成上期还款月填写!",0);
                return ;
            }
            termNum = Number(termNum)+Number(1);
        }else {
            termNum = 0;
            termNum = Number(termNum)+Number(1);
        }
        if(planBeginDate == null || planBeginDate == '' || planBeginDate == undefined){
            planBeginDate = obj.find("input[name='planEndDate']").val();
        }
        if(planBeginDate == null || planBeginDate == '' || planBeginDate == undefined){
            planBeginDate="";
        }
        if(!reg.test(termNum)){
            window.top.alert("请输入正确的期号格式!",0);
            return ;
        }

        var date = "";
        if(planBeginDate == null || planBeginDate == '' || planBeginDate == undefined){
        }else{
           // date= _getNextMonth(planBeginDate);
            planBeginDate = _getNextDate(planBeginDate,1);//获取下个月的前一天
        }
        var input = "<tr>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 40px' disabled readonly name=\"termNum\" value="+termNum+"></td>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 100px'disabled name=\"planBeginDate\" readonly  ></td>\n" +
            "<td align=\"center\"><input  style='width: 100px' name=\"planEndDate\" disabled readonly   ></td>\n" +
            "<td align=\"center\"><input  style='width: 100px' name=\"repayMonth\"  onblur='MfBusApply_applyInput.termMonthChange(this);' ></td>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 100px' datatype='12' onblur='func_uior_valType(this);MfBusApply_applyInput.repayPrcpChange(this)' name=\"repayPrcp\" value=0.00></td>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 100px' disabled readonly    name=\"repayPrcpBalAfter\" value="+repayPrcpBalAfterOld+"></td>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 100px' hidden    name=\"repayPrcpBalAfterOld\" value="+repayPrcpBalAfterOld+"></td>\n" +
            "<td align=\"center\"><input type=\"text\" style='width: 100px' hidden    name=\"repayMonthLast\" value="+repayMonthLast+"></td>\n" +
            "<td align=\"center\"><a href=\"javascript:void(0);\" onclick=\"MfBusApply_applyInput.deleteTr(this,'"+termNum+"');return false;\" class=\"abatch\">删除</a></td>\n"+
            "</tr>";

        $(".repayplanInfo").find("tbody").append(input);
        // $(".repayplanInfo").find("input[name=planBeginDate]").on('click', function(){
        //     var _this = this;
        //     fPopUpCalendarDlg({elem:_this,choose:function(){
        //         }})
        // });
        // $(".repayplanInfo").find("input[name=planEndDate]").on('click', function(){
        //     var _this = this;
        //     fPopUpCalendarDlg({elem:_this,choose:function(){
        //         }})
        // });
    }
        //本金输入后事件
    var  _termMonthChange=function (obj) {
        var reg = /^[1-9]+[0-9]*]*$/;//验证是否为正整数
        var  termMonth = $(obj).val();
        var  repayMonthLast = $(obj).parent().parent().find("[name='repayMonthLast']").val();
        if(!reg.test(termMonth)){
            window.top.alert("请输入正确的期限格式!",0);
            $(obj).val("");
            return ;
        }
        if( Number(termMonth)<= Number(repayMonthLast)){
            window.top.alert("本期还款月需要大于上期!",0);
            $(obj).val("");
            return ;
        }

    }
    //期限输入后事件
    var  _repayPrcpChange=function (obj) {
        var  repayPrcp = $(obj).val();
        if(repayPrcp!=null&&repayPrcp!=undefined){
            repayPrcp = repayPrcp.replace(/,/g,"");
        }
        var repayPrcpBalAfter = $(obj).parent().parent().find("input[name='repayPrcpBalAfterOld']").val();
        if(repayPrcpBalAfter!=null&&repayPrcpBalAfter!=undefined){
            repayPrcpBalAfter = repayPrcpBalAfter.replace(/,/g,"");
            repayPrcpBalAfter = repayPrcpBalAfter-repayPrcp;
            if(repayPrcpBalAfter>=0){
                $(obj).parent().parent().find("input[name='repayPrcpBalAfter']").val(repayPrcpBalAfter);
            }else{
                window.top.alert("本期还款金额大于剩余本金!",0);
                $(obj).val("");
            }

        }
    }
    //获取指定日期的后一个月
    var _getNextMonth=function (date) {
        var arr = date.split('-');
        var year = arr[0]; //获取当前日期的年份
        var month = arr[1]; //获取当前日期的月份
        var day = arr[2]; //获取当前日期的日
        var days = new Date(year, month, 0);
        days = days.getDate(); //获取当前日期中的月的天数
        var year2 = year;
        var month2 = parseInt(month) + 1;
        if (month2 == 13) {
            year2 = parseInt(year2) + 1;
            month2 = 1;
        }
        var day2 = day;
        var days2 = new Date(year2, month2, 0);
        days2 = days2.getDate();
        if (day2 > days2) {
            day2 = days2;
        }
        if (month2 < 10) {
            month2 = '0' + month2;
        }

        var t2 = year2 + '-' + month2 + '-' + day2;
        return t2;
    }
    //获取指定日期的前一天或后一天
    // getNextDate('2018-02-28', 1)
    // getNextDate('2018-03-01', -1)
    var _getNextDate = function (date,day) {
        var dd = new Date(date);
        dd.setDate(dd.getDate() + day);
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
        var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
        return y + "-" + m + "-" + d;
    };
//删除制定tr
    var _deleteTr =  function (obj,termNum){
        if(termNum.indexOf("=")!=-1){
            termNum = termNum.split("=")[1];
        }
        //aList.push(termNum);
        $('.repayplanInfo').find("tr").each(function(i) {//循环封装参数
            var tdArr = $(this);
            var term = tdArr.find("td").find("input[name='termNum']").val();
            if(term == null || term == '' || term == undefined){
                term = tdArr.find("td").eq(0).text();
            }
            if(term==termNum){
                tdArr.remove();
            }
        })
    };
    var _agenciesInput = function () {
        var kindNo =  $("input[name='kindNo']").val();
        top.openBigForm(webPath + "/mfCusCreditApply/agenciesApplyView?kindNo=" + kindNo, "添加合作银行", function () {
            $("input[name='agenciesId1']").val(top.applyAgenceisId);
            $("input[name='agenciesName1']").val(top.applyAgenceisName);
            $("input[name='agenciesName1']").parent().find(".error").remove()
        });
    };
    var _breedInit1 = function () {
        var kindNo =  $("[name='kindNo']").val();
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
                        items : data.items,
                        changeCallback: function (obj,elem) {
                            $("input[name='breedName1']").val(obj.data("text"));
                        }
                    })
                }
            }
        });
    };
    var _calcAssureAmt = function(){
        let appAmt = $("[name='appAmt']").val();
        let assureAmtRate = $("[name='assureAmtRate']").val();
        if(appAmt != null && appAmt != ""){
            appAmt = appAmt.replace(/,/g,"");
        }
        let assureAmt = CalcUtil.formatMoney(CalcUtil.divide(CalcUtil.multiply(appAmt,assureAmtRate),100),2);
        $("[name='assureAmt']").val(assureAmt);
        let shouldFeeSum = 0.00;
        $('#busfee-list tbody tr').each(function(){
            var trObj = $(this);
            let obj = trObj.find('input[name=rateScale]');
            let rateScale = $(obj).val();
            $(obj).parent().next().find("input[name=receivableFeeAmt]").val(CalcUtil.formatMoney(CalcUtil.divide(CalcUtil.multiply(appAmt,rateScale),100),2));
            let  itemNo = $(obj).parents("tr").find("input[name=itemNo]").val();
            if(itemNo == "1" || itemNo == "3"){
                shouldFeeSum = CalcUtil.add(shouldFeeSum,CalcUtil.divide(CalcUtil.multiply(appAmt,rateScale),100));
            }
        });
        $("[name='shouldFeeSum']").val(CalcUtil.formatMoney(shouldFeeSum,2));
    }

    function _selectBoodBankAcc(){
        selectCusBankAccDialog(getBoodBankAccInfoArtDialog,"选择保证金账号","04");
    };
    function getBoodBankAccInfoArtDialog(accountInfo){
        var accountNo = accountInfo.accountNo;
        var space = " ";
        var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
        $("input[name='bondAccount']").val(accountNo);
        $("input[name='bondBank']").val(accountInfo.bank);
        $("input[name='bondAccName']").val(accountInfo.accountName);
        $("input[name='bondAccId']").val(accountInfo.id);//银行卡Id
    };

    function _selectBankAcc(){
        selectCusBankAccDialog(getBankAccInfoArtDialog,"选择应缴账号","03");
    };
    function getBankAccInfoArtDialog(accountInfo){
        var accountNo = accountInfo.accountNo;
        var space = " ";
        var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
        $("input[name='collectAccount']").val(accountNo);
        $("input[name='collectBank']").val(accountInfo.bank);
        $("input[name='collectAccName']").val(accountInfo.accountName);
        $("input[name='collectAccId']").val(accountInfo.id);//银行卡Id
    };

	return{
		init:_init,
		selectCusDialog:_selectCusDialog,
		cancelApply:_cancelApply,
		selectFincDialog:_selectFincDialog,
        valiCusDocIsUp:_valiCusDocIsUp,
        selectCreditContract:_selectCreditContract,
        selectCreditBack:_selectCreditBack,
        getCalcRateByTermAjax: _getCalcRateByTermAjax,
        getCalcRateByFincRateAjax: _getCalcRateByFincRateAjax,
        onchangeBaseRateType: _onchangeBaseRateType,
        repayplanInfo: _repayplanInfo,
        selectChangeApply:_selectChangeApply,
        insertTr:_insertTr,
        deleteTr:_deleteTr,
        repayPrcpChange:_repayPrcpChange,
        termMonthChange:_termMonthChange,
        agenciesInput:_agenciesInput,
        breedInit1:_breedInit1,
        breedInit:_breedInit,
        agenciesInit:_agenciesInit,
        calcAssureAmt:_calcAssureAmt,
        selectBankAcc:_selectBankAcc,
        selectBoodBankAcc:_selectBoodBankAcc
	}
}(window,jQuery);
	function selectCreditContract(callback,cusType,title,selectType){
        var kindNo = $("select[name=kindNo]").val();
        dialog({
            id:'cusDialog',
            title:"授信合同",
			//url:webPath+"/mfCusCustomer/getListPageForSelect?selectType="+selectType,
            url:webPath+"/mfCusCreditContract/getCreditContractAllByCus?cusNo="+cusNo+"&kindNo="+kindNo,
            width:900,
            height:400,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性:实体类MfCusCustomer中的所有属性
                    if(typeof(callback)== "function"){
                        callback(this.returnValue);
                    }else{
                        getCusInfoArtDialog(this.returnValue);
                    }
                }
            }
        }).showModal();

    }
