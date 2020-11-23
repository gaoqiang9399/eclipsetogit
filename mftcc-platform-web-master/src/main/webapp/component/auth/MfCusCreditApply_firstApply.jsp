<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/creditBusinessRelation.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_InputQuery.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/nmd/js/parLoanuse.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/MfBusApplyInput.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
</head>
<style type="text/css">
	.mCSB_container {
		min-height: 100%;
	}
</style>
<script type="text/javascript">
    var cusNo = '${cusNo}';
    var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
    var fromPage = '${dataMap.fromPage}';
    var firstKindNo = '${kindNo}';
    var cusType = '${cusType}';
    var maxAmt = null;
    var minAmt = null;
    var canApplyAmt = null;
    var minTerm = null;
    var maxTerm = null;
    var minFincRate = null;
    var maxFincRate = null;
    var creditAmt = null;
    var kindCreditAmt = null;
    var depositRate = null;
    var oldKindNo = firstKindNo;
    var ajaxData = ${ajaxData};
    var processId =null;
    var creditSum = null;// 授信总额
    var sign = "apply";//申请确认标志
	var breedInitFlag=0;

	var creditAppId ='${creditAppId}';
	var wkfAppId ='${wkfAppId}';
    $(function() {
       getForm(firstKindNo);
        myCustomScrollbarForForm({
            obj : ".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        //初始化费用信息
        busFeeInfo(firstKindNo);
		//初始化合作银行
		agenciesInit();

    });
    function agenciesInit() {
        $.ajax({
            url:  webPath + "/mfCusCreditApply/getAgenciesListByCreditAppId",
            data:{creditAppId:creditAppId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    // if(agenicesInitFlag==0){
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
                                breedInit(agenciesId);
                            }
                        })
					// }else{
                     //    $("input[name=popsagenciesId]").popupSelection("updateItems",data.items);
					// }

                }
            }
        });
	}
    //新增业务品种初始化
    function breedInit(breedAgenciesId){
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
                    }else{
                        $("input[name=popsbreedNo]").popupSelection("updateItems",data.items);
                    }

                }
            }
        });
    }
    //产品改变时，校验拦截并获取该产品的表单
    function interceptAndGetForm(kindNo) {
        var app_amt = $('input[name=appAmt]').val().replace(/,/g, "");
        if(typeof(app_amt) == 'undefined' || app_amt==''){
            app_amt = '0';
        }
        var parmData = {'nodeNo' : 'apply','relNo' : cusNo,'cusNo' : cusNo,'kindNo' : kindNo,'app_amt' : app_amt,'cusType' : cusType};
        $.ajax({
            url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
            data : {ajaxData : JSON.stringify(parmData)},
            async : false,
            success : function(data) {
                if (data.exsitRefused == true) {// 存在业务拒绝
                    top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ cusNo, '风险拦截信息', function() {
                        $("select[name=kindNo]").val(oldKindNo);
                    });
                } else if (data.exsitFX == true) {//存在风险项
                    alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : "该客户存在风险项","operation" : "新增业务"}), 2, function() {
                        oldKindNo = $("select[name=kindNo]").val();
                    });
                } else {
                    oldKindNo = $("select[name=kindNo]").val();
                }
            },error : function() {
            }
        });
    }
    function busFeeInfo(kindNo){
        var feeShowFlag ="";
        var feeHtmlStr ="";
        $.ajax({
            url : webPath+"/mfCusCreditApply/getFeeHtmlForKindAjax?kindNo=" + kindNo + "&creditAppId=" + creditAppId,
            success : function(data) {
                if (data.flag == "success") {
                    feeShowFlag = data.feeShowFlag;
                    feeHtmlStr = data.feeHtmlStr;
                }
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
                        }
                        if(itemNo==14||itemNo==15){
                            trObj.hide();
                        }
                    });
                    $('#busfee-list table').find("colgroup").remove();
                    $('.busfeeInfo').removeClass('hidden');
                }
            }
        });

    }
    function getForm(val) {
        $.ajax({
            url : webPath+"/mfBusApply/chooseFormAjax?kindNo=" + val + "&cusNo=" + cusNo,
            success : function(data) {
                if (data.flag == "success") {
                    maxAmt = data.maxAmt;
                    minAmt = data.minAmt;
                    canApplyAmt = data.canApplyAmt;
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
                            depositRate = data.depositRate;
                        } else {
                            kindCreditAmt = null;
                            depositRate = null;
                        }
                    } else {
                        creditAmt = null;
                    }
                }
                $(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
                $(".anchor-ctrl select").each(function(){handleAnchorFun(this);});
            }
        });

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

    function cleanFincUseSmShow() {
        $("[name=fincUseSmShow]").val("");
    }




    function selectKindNo() {
        var kindNo = $("[name=kindNo]").val();
        interceptAndGetForm(kindNo);
        $("#tableHtml").empty();
    }


    function insertForApply(obj) {
        //判断期限
        var putoutTerm =$("[name=putoutTerm]").val();
        var extendTerm =$("[name=extendTerm]").val();
        var term =$("[name=term]").val();
        if(Number(term) > Number(putoutTerm)+Number(extendTerm)){
            alert("申请期限不能大于合作银行提款期，延长期之和",3);
            return;
        }

        var kindno = $("[name=kindNo]").val();
        var appAmt = $("[name=appAmt]").val();
        var fincUse = $("[name=fincUseSm]").val();
        var term = $("[name=term]").val();
        var organType = $("[name=organType]").val();
        var cusName = $("[name=cusName]").val();
        var cusNoFund = $("[name=cusNoFund]").val();
        var loanKind = $("[name=loanKind]").val();
        var channelSourceNo = $("[name=channelSourceNo]").val();
        appAmt = appAmt.replace(/,/g,'');
        var relNo = cusNo;

        var appId = $("[name=appId]").val();
        if (appId) {// 如果有申请号, 风险信息应该与申请号关联
            relNo = appId;
        }
		//判读申请金额是否小于授信银行额度，业务品种额度

        var breedCreditAmt = $("input[name=breedCreditAmt]").val().replace(/,/g, "");
        var agenciesCreditAmt = $("input[name=agenciesCreditAmt]").val().replace(/,/g, "");
        var appAmt = $("input[name=appAmt]").val().replace(/,/g, "");
        var authBal = $("input[name=authBal]").val().replace(/,/g, "");
        if (CalcUtil.compare(appAmt, authBal) ==1 ) {
            window.top.alert(top.getMessage("NOT_FORM_TIME", {
                "timeOne" : "申请额度:",
                "timeTwo" : "授信额度:"
                + CalcUtil.formatMoney(authBal, 2)
            }), 0);
            return;
        }
        if (CalcUtil.compare(appAmt, agenciesCreditAmt) ==1 ) {
            window.top.alert(top.getMessage("NOT_FORM_TIME", {
                "timeOne" : "申请额度:",
                "timeTwo" : "合作银行授信额度:"
                + CalcUtil.formatMoney(agenciesCreditAmt, 2)
            }), 0);
            return;
        }
        if (CalcUtil.compare(appAmt, breedCreditAmt) ==1 ) {
            window.top.alert(top.getMessage("NOT_FORM_TIME", {
                "timeOne" : "申请额度:",
                "timeTwo" : "业务品种授信额度:"
                + CalcUtil.formatMoney(breedCreditAmt, 2)
            }), 0);
            return;
        }
        var parmData = {'nodeNo': 'apply', 'relNo': relNo, 'cusNo': cusNo, 'kindNo': kindno, 'cusType': cusType, 'fincUse': fincUse, 'term': term, 'app_amt': appAmt, 'organType': organType, 'cusName': cusName, 'cusNoFund': cusNoFund, 'channelSourceNo': channelSourceNo,'loanKind':loanKind};
        $.ajax({
            url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
            data : {ajaxData : JSON.stringify(parmData)},
            async : false,
            success : function(data) {
                if (data.exsitRefused == true) {// 存在业务拒绝
                    top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ relNo, '风险拦截信息', function() {
                        $("select[name=kindNo]").val(oldKindNo);
                    });
                } else if (data.exsitFX == true) {//存在风险项
                    alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : "该客户存在风险项","operation" : "新增业务"}), 2, function() {
                        // var fincRate = $("input[name=fincRate]").val();
                        // if (parseFloat(fincRate) == 0) {
							// alert(top.getMessage("CONFIRM_OPERATION_REASON", {"reason" : '申请利率为0',"operation" : '申请保存'}), 2, function() {
							// 	saveApplyInfo(obj,type);
							// });
                        // } else {
                            saveApplyInfo(obj);
                        // }
                    });
                } else {
                    // var fincRate = $("input[name=fincRate]").val();
                    // if (parseFloat(fincRate) == 0) {
						// alert(top.getMessage("CONFIRM_OPERATION_REASON", {"reason" : '申请利率为0',"operation" : '申请保存'}), 2, function() {
						// 	saveApplyInfo(obj);
						// });
                    // } else {
                        saveApplyInfo(obj);
                    // }
                }
            },error : function() {
            }
        });
    };

    function saveApplyInfo(obj) {

        var url = $(obj).attr("action");
        checkByKindInfo($("[name='appAmt']"));// 用此方法控制下申请金额为0的问题
        if(!MfBusApplyInput.creditRatioOnblur()){
           return;
        }
        var flag = submitJsMethod($(obj).get(0), '');
        var repayType = $("select[name='repayType']").val();
        if (flag) {
            alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
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
                var dataParam = JSON.stringify($(obj).serializeArray());
                var param =  {ajaxData : dataParam,ajaxDataList : JSON.stringify(datas) ,wkfAppId:wkfAppId,
                    commitType:"REPORT",planDataList : JSON.stringify(plandatas)};
                var kindno = $("[name=kindNo]").val();
                $.ajax({
                    url : url,
                    data : param,
                    error:function(){
                        alert('提交到下一个节点时发生异常', 0);
                    },
                    success:function(data){
                        if(data.flag == "success"){
                            top.creditFlag=true;
                            top.creditAppId=creditAppId;
                            //top.creditType=creditType;
                            top.creditType=wkfAppId;
                            myclose_click();
                        }
                    }
                });
            });

        } else {
        }
    }

    //验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
    function checkByKindInfo(obj) {
        var name = $(obj).attr("name");
        var title = $(obj).attr("title").split("(")[0];
        //申请金额
        if (name == "appAmt") {
            // if (maxAmt != null && minAmt != null && maxAmt != "" && minAmt != "") {
            //     maxAmtNum = new Number(maxAmt);
            //     minAmtNum = new Number(minAmt);
            //     var appAmt = $(obj).val();
            //     appAmt = appAmt.replace(/,/g, "");
            //     // if(canApplyAmt!= null ){
				// // 	var canApplyAmtNum =  new Number(canApplyAmt);
				// // 	//申请金额 大于可以申请的金额
            //      //    if(parseFloat(appAmt) > parseFloat(canApplyAmtNum)){
            //      //        $(obj).val(null);
            //      //        alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
            //      //            "info" : "该产品可以申请的剩余总金额上限",
            //      //            "field" : title,
            //      //            "value" : fmoney(canApplyAmtNum, 2)
            //      //        }), 0);
            //      //        return ;
				// // 	}
				// // }
            //     if (parseFloat(appAmt) < parseFloat(minAmtNum)|| parseFloat(appAmt) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
            //         $(obj).val(null);
            //         alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
            //             "info" : "产品设置",
            //             "field" : title,
            //             "value1" : fmoney(minAmtNum, 2),
            //             "value2" : fmoney(maxAmtNum, 2)
            //         }), 0);
            //     } else {
            //         var authBal = $("input[name='authBal']").val();
            //         authBal = authBal.replace(/,/g, "");
            //         if (authBal != null && authBal != "" ) { //判断申请金额是否在授信余额内
            //             authBal = new Number(authBal);
				// 		if (parseFloat(appAmt) > parseFloat(authBal)) {
				// 			alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
				// 				"info" : "该客户授信",
				// 				"field" : title,
				// 				"value" : fmoney(authBal, 2)
				// 			}), 0);
				// 		}
            //         };
            //     };
            // };
        } else if (name == "fincRate") {//检测融资利率
            if (minFincRate != null && maxFincRate != null && minFincRate != "" && maxFincRate != "") {
                var maxFincRateNum = new Number(maxFincRate);
                var minFincRateNum = new Number(minFincRate);
                var fincRate = $(obj).val();
                if (parseFloat(fincRate) < parseFloat(minFincRateNum)
                    || parseFloat(fincRate) > parseFloat(maxFincRateNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info" : "产品设置",
                        "field" : title,
                        "value1" : minFincRateNum,
                        "value2" : maxFincRateNum
                    }), 0);
                }
            }
        }
    };
    function chooseKindType(obj) {
        selectKindNo();
    }

    //根据产品号确定产品的期限
    function    changeKindNo(){
        var  kindNo  =	$("select[name=kindNo]").val();
        if(kindNo == "1004"){
            $("input[name=term]").val("3");
            $("input[name=term]").attr({ readonly: 'true' });
        }
    }
    function checkTerm(obj) {
        //修改校验期限的方法使其支持选择框
        var   isExist  =  $("select[name=term]").val();
        if(typeof(isExist) == "undefined"){
            var appTerm = $("input[name=term]").val();
        }else{
            var appTerm = $("select[name=term] option:checked").text();
        }
        appTerm = appTerm.replace(/,/g, "");
        var title = $("[name=term]").attr("title").split("(")[0];
        var appTermType = $("[name=termType]").val();
        appTermType = appTermType.replace(/,/g, "");
        var appMinTerm;
        var appMaxTerm;
        //申请期限
        if (minTerm != null && maxTerm != null && minTerm != ""
            && maxTerm != "" && termType != null && termType != "") {
            minTermNum = new Number(minTerm);
            maxTermNum = new Number(maxTerm);
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
            // $("[name=term]").attr("placeholder",
            //     appMinTerm + "-" + appMaxTerm);
            if (parseFloat(appTerm) < parseFloat(minTermNum)
                || parseFloat(appTerm) > parseFloat(maxTermNum)) {
                $("[name=term]").val("");
                alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                    "info" : "产品设置",
                    "field" : title,
                    "value1" : appMinTerm,
                    "value2" : appMaxTerm
                }), 0);
            }
        }
    };

    function updatePactEndDate() {
        var beginDate = $("input[name=ext5]").val();
        var term = $("input[name=term]").val();
        if (term == '') {
            return;
        }
        var termType = $("[name=termType]").val();
        var intTerm = parseInt(term);
        if (1 == termType) { //融资期限类型为月
            var d = new Date(beginDate);
            d.setMonth(d.getMonth() + intTerm);
            var str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? d.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
            $("input[name=ext6]").val(str);
        } else { //融资期限类型为日
            var d = new Date(beginDate);
            d.setDate(d.getDate() + intTerm);
            var str = d.getFullYear()+ "-"+ (d.getMonth() >= 9 ? beginDate.getMonth() + 1 : "0"+ (d.getMonth() + 1)) + "-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
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



</script>
<body class="overflowHidden bg-white">
<div class="container form-container" id="normal">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<c:if  test='${query=="cusbody"}'>
					<div class="form-title">业务申请</div>
				</c:if>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form id="insertForApplyForm" method="post" theme="simple"
					  name="operform"
					  action="${webPath}/mfCusCreditApply/insertForApplyAjax">
					<dhcc:bootstarpTag property="formcreditapply0001" mode="query" />
				</form>
			</div>
			<div class="repayplanInfo showOrHide hidden">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>还款计划</span>
						<button class="btn btn-link formAdd-btn"  onclick="MfBusApply_applyInput.insertTr()" title="新增"><i class="i i-jia3"></i></button>
						<button class="btn btn-link pull-right formAdd-btn"
								data-toggle="collapse" data-target="#repayplan-list">
							<i class='i i-close-up'></i> <i class='i i-open-down'></i>
						</button>
					</div>
					<div class="content_table collapse in" id="repayplan-list"></div>
				</div>
			</div>
			<div class="busfeeInfo showOrHide hidden">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>费用标准</span>
						<button class="btn btn-link pull-right formAdd-btn"
								data-toggle="collapse" data-target="#busfee-list">
							<i class='i i-close-up'></i> <i class='i i-open-down'></i>
						</button>
					</div>
					<div class="content_table collapse in" id="busfee-list"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="提交" action="提交"
						  onclick="insertForApply('#insertForApplyForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
						  onclick="cancelApply();"></dhcc:thirdButton>
	</div>
	<div style="display: none;" id="fincUse-div"></div>
</div>
</body>
</html>