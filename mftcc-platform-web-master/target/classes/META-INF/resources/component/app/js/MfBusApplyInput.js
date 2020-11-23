;
var MfBusApplyInput = function (window,$) {
    var _init = function() {
        _initOnCardsCity();
        _initCreditRatio();
        _initPledgeSelect();
    };
    var _fullPackagePriceChange = function () {
        var fullPackagePrice = $("input[name=fullPackagePrice]").val();
        var downPayments = $("input[name=downPayments]").val();
        if(fullPackagePrice!=""&&downPayments!=""){
            fullPackagePrice = fullPackagePrice.replace(/,/g, "");
            downPayments = downPayments.replace(/,/g, "");
            var fullPackagePriceNum = new Number(fullPackagePrice);
            var downPaymentsNum = new Number(downPayments);
            if(downPaymentsNum>fullPackagePriceNum){
                $("input[name=downPayments]").val("0");
                window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"首付","timeTwo":"全包价"}), 0);
            }else{
                var appAmt = CalcUtil.formatMoney(fullPackagePriceNum-downPaymentsNum,2);
                $("input[name=appAmt]").val(appAmt);
            }
        }
    };

    var _carModelCallBack =function (data) {
        $("input[name=carModelId]").val(data.modelId);
        $("input[name=carModelName]").val(data.modelName);
        _getByModelId();
    };

    var _getByModelId = function () {
        var modelId = $("input[name=carModelId]").val();
        var onCardsCityNo = $("input[name=onCardsCityNo]").val();
        if (modelId!=""&&onCardsCityNo!=""){
            $.ajax({
                url : webPath+"/mfSalesOptions/getByModelIdAjax",
                data : {modelId:modelId,salesArea:onCardsCityNo},
                async : false,
                success : function(data) {
                    if (data.flag == "success"){
                        var mfSalesOptions = data.mfSalesOptions;
                        $("input[name=fullPackagePrice]").val(CalcUtil.formatMoney(mfSalesOptions.fullPackagePrice,2));
                    }
                },error : function() {
                }
            });
            _getFinancingOptions();
        }
    };
    var _getFinancingOptions = function () {
        var kindNo = $("select[name=kindNo]").val();
        if(kindNo==""){
            return;
        }
        $.ajax({
            url : webPath+"/mfFinancingOptions/getByKindNoAjax",
            data : {kindNo:kindNo},
            async : false,
            success : function(data) {
                if (data.flag == "success"){
                    var mfFinancingOptions = data.mfFinancingOptions;
                    $("input[name=deposit]").val(CalcUtil.formatMoney(mfFinancingOptions.deposit,2));//定金
                    /*var downPaymentRatio = mfFinancingOptions.downPaymentRatio;//首付比例
                    var fullPackagePrice = $("input[name=fullPackagePrice]").val();
                    fullPackagePrice = fullPackagePrice.replace(/,/g, "");
                    if(fullPackagePrice!=""&&fullPackagePrice>0){
                        var fullPackagePriceNum = new Number(fullPackagePrice);
                        var downPayments = fullPackagePriceNum*downPaymentRatio;
                        $("input[name=downPayments]").val(downPayments);
                        $("input[name=appAmt]").val(fullPackagePriceNum-downPayments);
                        _getFincRate();
                    }*/
                }
            },error : function() {
            }
        });
    }
    var _getFincRate = function () {
        var appAmt = $("input[name=appAmt]").val().replace(/,/g, "");
        var term = $("input[name=term]").val();
        var rateType = $("select[name=rateType]").val();
        var nominalRate = $("input[name=nominalRate]").val();
        $.ajax({
            url : webPath+"/mfBusApply/getFincRateAjax",
            data : {appAmt:appAmt,term:term,rateType:rateType,nominalRate:nominalRate},
            async : false,
            success : function(data) {
                $("input[name=fincRate]").val(data.fincRateShow);
            },error : function() {
            }
        });
    }
    var _initOnCardsCity = function () {
        //上牌城市选择组件
        $("input[name=onCardsCityNo]").popupSelection({
            ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
            searchOn : true,// 启用搜索
            multiple : false,// 单选
            ztree : true,
            ztreeSetting : setting,
            title : "上牌城市",
            handle : BASE.getIconInTd($("input[name=onCardsCityNo]")),
            changeCallback : function(elem) {
                BASE.removePlaceholder($("input[name=onCardsCityNo]"));
                var value = $("input[name=onCardsCityNo]").parent().find(".pops-label-alt").html();
                $("input[name=onCardsCity]").val(value);
                _getByModelId();
            }
        });
    };
    var _initCreditRatio = function () {
        if(typeof (rechargeAmtBalShow) != "undefined" && typeof (withdrawAmtBalShow) != "undefined"){
            $("[name='rechargeAmt']").attr("placeholder","0.00-" + rechargeAmtBalShow + "元");
            $("[name='withdrawAmt']").attr("placeholder","0.00-" + withdrawAmtBalShow + "元");
        }
    };
    var _creditRatioOnblur = function () {
        if(typeof (rechargeAmtBal) != "undefined" && typeof (withdrawAmtBal) != "undefined"){
            var rechargeAmt = $("[name='rechargeAmt']").val();
            if(rechargeAmt != null && rechargeAmt != ""){
                if(rechargeAmt.indexOf("-") != -1){
                    alert("充值金额不能为负数",0);
                    return false;
                }
                rechargeAmt = rechargeAmt.replace(/,/g,"");
                if(parseFloat(rechargeAmt) > parseFloat(rechargeAmtBal)){
                    alert("充值金额不能大于" + rechargeAmtBalShow + "元",0);
                    return false;
                }
            }
            var withdrawAmt = $("[name='withdrawAmt']").val();
            if(withdrawAmt != null && withdrawAmt != ""){
                if(withdrawAmt.indexOf("-") != -1){
                    alert("提现金额不能为负数",0);
                    return false;
                }
                withdrawAmt = withdrawAmt.replace(/,/g,"");
                if(parseFloat(withdrawAmt) > parseFloat(withdrawAmtBal)){
                    alert("提现金额不能大于" + withdrawAmtBalShow + "元",0);
                    return false;
                }
            }
            if(rechargeAmt != null && rechargeAmt != "" && withdrawAmt != null && withdrawAmt != ""){
                var appAmtList = [
                    $("[name='withdrawAmt']").val(),
                    $("[name='rechargeAmt']").val(),
                ];
                var appAmt = CalcUtil.sum(appAmtList);
                $("[name='appAmt']").val(fmoney(appAmt, 2));
            }
        }
        return true;
    };
  var _getIndustryChain = function(){//初始化所属产业链的选择组件
		$(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
		$(".anchor-ctrl select").each(function(){handleAnchorFun(this);});
		$("select[name=industryChainClass]").popupSelection({
					searchOn : true,//启用搜索
					inline : true,//下拉模式
					multiple : false,//单选
					addBtn:{
						"title":"新增",
						"fun":function(hiddenInput, elem){
							$(elem).popupSelection("hideSelect", elem);
							BASE.openDialogForSelect('新增产业链分类','INDUSTRY_CHAIN', elem);
						}
					}
				});
		};
	// 初始化资金机构
	var _initAgencies = function(){
		var kindNo = $("[name=kindNo]").val();
		if(kindNo != null && kindNo != ""){
			$.ajax({
	            url : webPath + "/mfBusAgencies/getByKindNoAjax",
	            data : {kindNo:kindNo},
	            success : function(data) {
	                if(data.flag == "success"){
	                	$("input[name='cusNoFund']").popupSelection({
	            			searchOn:true,//启用搜索
	            			inline:true,//下拉模式
	            			multiple:false,//单选
	            			items:data.agenciesMap,
	            			changeCallback : function(obj, elem) {
	            				$("[name=cusNameFund]").val( $("[name=cusNoFund]").prev().text());
	            			}
	            		});
	                }else{
	                	alert(data.msg,0);
	                }
	            },error : function() {
	            	alert("根据产品编号查找关联资金机构失败！",0);
	            }
	        });
		}
	};

    //初始化选择押品选择组件
    var _initPledgeSelect = function () {
        var plegdeName = $("[name=pledgeName]").val();
        var pledgeMethod = $("[name=vouType]").val();
        $("[name=pledgeName]").popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/pledgeBaseInfo/getPledgePageByCusNo?cusNo="+cusNo,// 请求数据URL"&keepStatus=5&refFlag=0"
            valueElem: "input[name=pledgeNo]",//真实值选择器
            title: "选择押品",//标题
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=pledgeName]"));
                var pledgeNos = "";
                $.each($("[name=pledgeName]").parent().find(".pops-label"),function (i) {
                    pledgeNos = pledgeNos+$(this).attr("data-id")+"@";
                });
                _initPledgeBillSelect(pledgeNos);
            },
            tablehead: {//列表显示列配置
                "pledgeName": "押品名称",
                "classSecondName": "押品类型",
                "pleMediaModel": "SKU编码",
            },
            returnData: {//返回值配置
                disName: "pledgeName",//显示值
                value: "pledgeNo"//真实值
            }
        });
    };

    //初始化选择押品清单选择组件
    var _initPledgeBillSelect = function (pledgeNos) {
        $("[name=pledgeBillNo]").val("");
        $("[name=pledgeBillNo]").parent().find(".pops-value").remove();
        $("[name=pledgeBillNo]").popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/pledgeBaseInfoBill/getBillPageByPledgeNos?pledgeNos="+pledgeNos+"&pledgeBillSts=5&refFlag=0",// 请求数据URL
            valueElem: "input[name=pledgeBillNo]",//真实值选择器
            title: "选择押品清单",//标题
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=pledgeBillNo]"));
                _verificationAppAmt();
            },
            tablehead: {//列表显示列配置
                "billName": "名称",
                "unitPrice": "单价",
                "count": "数量",
            },
            returnData: {//返回值配置
                disName: "billName",//显示值
                value: "pledgeBillNo"//真实值
            }
        });
    };
    //校验关联押品货值金额和融资申请金额
    var _verificationAppAmt = function () {
        var appAmt = $("input[name='appAmt']").val();
        var pledgeBillNo = $("input[name='pledgeBillNo']").val();
        var cusNo = $("input[name='cusNo']").val();
        var creditProjectId = $("input[name='creditProjectId']").val();
        var flag =true;
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
        return flag;
    };

    return{
        init:_init,
        fullPackagePriceChange:_fullPackagePriceChange,
        carModelCallBack:_carModelCallBack,
        getFincRate:_getFincRate,
        getIndustryChain:_getIndustryChain,
        initAgencies:_initAgencies,
        creditRatioOnblur:_creditRatioOnblur,
        verificationAppAmt:_verificationAppAmt
    };
}(window,jQuery)