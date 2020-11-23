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
    //ajaxData = JSON.parse(ajaxData);
    var rateTypeMap = ajaxData.rateType;
    var PROJECT_ID = '${PROJECT_ID}';
    var processId =null;
    var projectName = '${projectName}';
    var rechargeAmtBal =  '${rechargeAmtBal}';
    var rechargeAmtBalShow =  '${rechargeAmtBalShow}';
    var withdrawAmtBal =  '${withdrawAmtBal}';
    var withdrawAmtBalShow =  '${withdrawAmtBalShow}';
    var creditSum = null;// 授信总额
    var sign = "apply";//申请确认标志
    //选择借据
    function selectBusFincApp(){
        //var cusNo = $("input[name=cusNo]").val();
        selectFincDialog(_selectFincBack1,cusNo,"选择借据","finc_sts5");
    };
    //选择借据回调
    var _selectFincBack1=function(finc){
        //debugger;
        cusNo=finc.cusNo;
        $("input[name=fincId]").val(finc.fincId);
        //借新还旧关联借据隐藏域赋值
        $("input[name=fincIdOld]").val(finc.fincId);
        $("input[name=fincShowId]").val(finc.fincShowId);
        //借据金额
        $("input[name=putoutAmt1]").val(finc.putoutAmt1);
        //开始日期
        $("input[name=intstBeginDate1]").val(finc.intstBeginDate);
        //到期日期
        $("input[name=intstEndDate1]").val(finc.intstEndDateShow);
        //利率 前端转换为两位有效小数
        //利率整数的时候保留两位小数，是小数的时候直接显示
        var lilv=finc.fincRate.toString();
        //可以保留两位小数
        //var value=Math.round(parseFloat(lilv)*100)/100;
        var xsd=lilv.toString().split(".");
        if(xsd.length==1){
            lilv=lilv.toString()+".00";
        }
        if(xsd.length>1){
            if(xsd[1].length<2){
                lilv=lilv.toString()+"0";
            }
        }
        $("input[name=fincRateOld]").val(lilv);
        $("input[name=fincRateOld]").next().html(finc.fincRateUnit);
        //担保方式
        $("[name=vouTypeOld]").val(finc.vouType);
        $("[name=vouTypeOld]").attr("disabled","true");
        //将借据余额自动引入到新业务申请中，作为新业务的申请金额
        $("input[name=appAmt]").val(finc.loanBal1);
        //借新还旧 贷款项目默认为 周转
        var fincUseDes="周转";
        $("input[name=fincUseDes]").val(fincUseDes);
    };
    $(function() {
        getForm(firstKindNo);
        myCustomScrollbarForForm({
            obj : ".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        creditBusinessRelation.init();
        MfBusApplyInput.init();
        //客户类型为农户且产品为农地贷（协同）业务 则业务类型只能是新增

    });
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
                        getForm(kindNo);
                        oldKindNo = $("select[name=kindNo]").val();
                    });
                } else {
                    getForm(kindNo);
                    oldKindNo = $("select[name=kindNo]").val();
                }
            },error : function() {
            }
        });
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
                }
                if(itemNo==14||itemNo==15){
                    trObj.hide();
				}
            });
            $('#busfee-list table').find("colgroup").remove();
            $('.busfeeInfo').removeClass('hidden');
        }
    }
    function getForm(val) {
        $.ajax({
            url : webPath+"/mfBusApply/chooseFormAjax?kindNo=" + val + "&cusNo=" + cusNo,
            success : function(data) {
                if (data.flag == "success") {
                    var html = data.htmlStr;
                    $("#insertForApplyForm").empty().html(html);
                    busFeeInfo(data.feeShowFlag,data.feeHtmlStr);
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

                    showDealersRate();
                    var vouTypeOtherClass = $("[name=vouTypeOther]").parents("td").attr("class");
                    if(typeof(vouTypeOtherClass) != "undefined" ){
                        var   parmDicList =  data.parmDicList;
                        $("[name=vouTypeOther]").parents("tr").children('td').eq(3).children('div').empty();
                        for ( var index in parmDicList) {
                            var optName  =  parmDicList[index].optName;
                            var optCode  =  parmDicList[index].optCode;
                            $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append('<input type="checkbox" name="vouTypeOther" title="其他担保方式" mustinput="" datatype="0" datavalue='+optName+'   value=' +optCode+ ' onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">');
                            $("[name=vouType]").parents("tr").children('td').eq(3).children("div").append(parmDicList[index].optName);
                        }
                        if($("[name=vouType]").is(':visible')){
                            bindVouTypeByKindNoZB($("#insertForApplyForm").find('[name=vouType]'), val);
                        }

                    }else{
                        if($("[name=vouType]").is(':visible')){
                            bindVouTypeByKindNo($("#insertForApplyForm").find('[name=vouType]'), val);
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
                    //初始化加载
                    if(processId ==null || processId==''){//如果不存在processId,则隐藏该字段
                        $("[name=firstApprovalUserName]").parents("tr").remove();// 字段td
                    }

                    //加载线下的产品下拉框列表
                    $("select[name=kindNo]").empty();
                    var mfSysKindList = data.mfSysKindList;
                    for ( var index in mfSysKindList) {
                        $("select[name=kindNo]").append("<option value="+ mfSysKindList[index].kindNo+">"+ mfSysKindList[index].kindName+ "</option>");
                    }
                    $("select[name=kindNo]").val(val);
                    // 是否隐藏 复利利率上浮字段
                    if (data.cmpdRateType == "0") {//隐藏
                        $('input[name=cmpdFloat]').parents("td").hide();// 字段td
                        $('input[name=cmpdFloat]').parents("td").prev("td").hide();// 标签td
                        $("input[name=cmpdFloat]").attr("mustinput", "");

                    }
                    $('input[name=creditPactNo]').val(data.creditPactNo);
                    $('input[name=creditPactId]').val(data.creditPactId);
                    creditBusinessRelation.init();
                    //保证金比例
                    var $_depositRate = $("input[name=depositRate]");
                    if (typeof $_depositRate != "undefined" && $_depositRate) {
                        $_depositRate.val(depositRate);
                    }
                    MfBusApplyInput.init();

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
    //循环额度贷款，根据基地展示保证金比例
    function showDepositRateByBaseType(obj) {
        var baseTypeVal = $(obj).val();
        if (baseTypeVal) {
            LoadingAnimate.start();
            $.ajax({
                url : webPath + "/mfBusApply/getDepositRateByBaseTypeAjax?baseType=" + baseTypeVal + "&cusNo=" + cusNo,
                type : "post",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        depositRate = data.depositRate;
                        var $_depositRate = $("input[name=depositRate]");
                        if (typeof $_depositRate != "undefined" && $_depositRate) {
                            $_depositRate.val(depositRate);
                        }
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert("保证金比例获取失败！", 0);
                }
            });
        }
    };
    function tryCalDeposit(){
        var appAmtVal = $("input[name=appAmt]").val();
        if(!appAmtVal){
            alert("申请金额不能为空！", 0);
            return;
        }
        appAmtVal = appAmtVal.replace(/,/g, '');

        var kindNoVal = $("select[name=kindNo]").val();
        var baseTypeVal = $("select[name=baseType]").val();
        if("1000" == kindNoVal && !baseTypeVal){
            alert("请选择基地类型！", 0);
            return;
        }

        var depositRateVal = Number($("input[name=depositRate]").val());
        if(depositRateVal < 0){
            alert("保证金比例不能为空且不能小于0！", 0);
            return;
        }
        var parmData = {
            'cusNo' : cusNo,
            'kindNo' : kindNoVal,
            'baseType' : baseTypeVal,
            'appAmt' : appAmtVal,
            'depositRate' : depositRateVal
        };
        var ajaxData = JSON.stringify(parmData);
        var url = webPath + "/mfBusApply/tryCalDeposit?ajaxData=" + encodeURIComponent(ajaxData);
        top.window.openBigForm(url, "保证金试算", function() {
        });
    };
    //经销商利率维护
    var dealersRate;
    function showDealersRate(){
        /**
         1、判断是否有第二执行利率字段
         2、存在添加描述span
         3、加载事件
         加载经销商利率
         判断初始时是否是‘3-利随本清’
         */

        var secondRate = $("input[name=secondRate]");

        var inputCount = secondRate.length;
        if(inputCount > 0){
            var repayType = $("select[name=repayType]");
            var fincRate = $("input[name=fincRate]");
            var rateMonth = $("input[name=rateMonth]");
            var bSpan = '<span class="bigAmt" style="display: table-cell;"></span>';
            var fsCount = fincRate.siblings('.bigAmt').length;
            if(fsCount == 0) {
                fincRate.parent().append(bSpan);
            }
            var sdCount = secondRate.siblings('.bigAmt').length;
            if(sdCount == 0) {
                secondRate.parent().append(bSpan);
            }
            //查询该客户的经销商利率
            if(!dealersRate){
                var cusNo =  $("input[name=cusNo]").val();
                $.ajax({
                    url : webPath+"/mfBusDealersRate/getRateByCusNoAjax?cusNo=" + cusNo,
                    type : "post",
                    dataType : "json",
                    success : function(data) {
                        if (data.flag == "success") {
                            dealersRate = data.dealersRate;
                            if('3'==repayType.val()){
                                fincRate.val(dealersRate.firstRate);
                                secondRate.val(dealersRate.secondRate);
                                rateMonth.val(dealersRate.termMonth);
                                fincRate.siblings('.bigAmt').html('（前' + dealersRate.termMonth + '个月执行）');
                                secondRate.siblings('.bigAmt').html('（' + dealersRate.termMonth + '个月后执行）');
                            }
                            //表单替换后锚点触发事件需要手动
                            repayType.initAchor(function(){
                                if('3'==$(this).val()){
                                    fincRate.val(dealersRate.firstRate);
                                    secondRate.val(dealersRate.secondRate);
                                    rateMonth.val(dealersRate.termMonth);
                                    fincRate.siblings('.bigAmt').html('（前' + dealersRate.termMonth + '个月执行）');
                                    secondRate.siblings('.bigAmt').html('（' + dealersRate.termMonth + '个月后执行）');
                                }else{
                                    secondRate.val('');
                                    rateMonth.val('');
                                    fincRate.siblings('.bigAmt').html('');
                                    secondRate.siblings('.bigAmt').html('');
                                }
                            });
                        }
                    }
                })

            }else{
                if('3'==repayType.val()){
                    fincRate.val(dealersRate.firstRate);
                    secondRate.val(dealersRate.secondRate);
                    rateMonth.val(dealersRate.termMonth);
                    fincRate.siblings('.bigAmt').html('（前' + dealersRate.termMonth + '个月执行）');
                    secondRate.siblings('.bigAmt').html('（' + dealersRate.termMonth + '个月后执行）');
                }
                //表单替换后锚点触发事件需要手动
                repayType.initAchor(function(){
                    if('3'==$(this).val()){
                        fincRate.val(dealersRate.firstRate);
                        secondRate.val(dealersRate.secondRate);
                        rateMonth.val(dealersRate.termMonth);
                        fincRate.siblings('.bigAmt').html('（前' + dealersRate.termMonth + '个月执行）');
                        secondRate.siblings('.bigAmt').html('（' + dealersRate.termMonth + '个月后执行）');
                    }else{
                        secondRate.val('');
                        rateMonth.val('');
                        fincRate.siblings('.bigAmt').html('');
                        secondRate.siblings('.bigAmt').html('');
                    }
                });
            }
        }
    }

    function selectKindNo() {
        var kindNo = $("[name=kindNo]").val();
        interceptAndGetForm(kindNo);
        $("#tableHtml").empty();
    }

    function getFincUse(obj) {
        $("input[name=fincUse]").val(obj.fincUse);
        $("input[name=fincUseName]").val(obj.fincUseShow);
    };
    function cancelApply() {
        if (fromPage == "cusbody") {
            window.location.href = webPath+"/mfCusCustomer/getById?cusNo="
                + cusNo + "&appId=" + appId;
        } else {
            $(top.window.document).find("#showDialog").remove();
        }
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
    //农机租赁检测信息是否填写完整
    function getParcelInfoForSubmit(){
        var returnList = new Array();
        $(".parcel-box").each(function(){
            var parcel = new Object();
            //获取购机信息
            $(this).find(".addParcel").find("input").each(function(){
                var inputName = $(this).attr("name");
                var inputVal = $(this).val();
                if ("powerMarketPrice"==inputName && "" == inputVal) {//功率市场价格为空时为0
                    inputVal = "0.000000"
                }
                parcel[inputName] = inputVal;
            })
            $(this).find(".addParcel").find("select").each(function(){
                var inputName = $(this).attr("name");
                var inputVal = $(this).val();
                parcel[inputName] = inputVal;
            })
            //获取购机目的
            var commonList = new Array();
            parcel.commonList = commonList;
            returnList.push(parcel);
        })
        var commonList = new Array();
        $(".addPartOwner").each(function(){
            var commonObj = new Object();
            $(this).find("input").each(function(){
                var inputName = $(this).attr("name");
                var inputVal = $(this).val();
                if ("" == inputVal) {//购机目的部分double类型字段不填时为 0
                    if ("landArea"==inputName || "haProduction"==inputName ||  "yearlyOutput"==inputName || "yearlySalaries"==inputName || "netSalaries"==inputName || "villageTotalLand"==inputName){
                        inputVal = "0.000000"
                    }
                }
                commonObj[inputName] = inputVal;
            })
            $(this).find("select").each(function(){
                var inputName = $(this).attr("name");
                var inputVal = $(this).val();
                commonObj[inputName] = inputVal;
            })
            commonList.push(commonObj);
        })
        returnList[0].commonList=commonList;
        return returnList;
    };

    function insertForApply(obj) {
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

    function saveApplyInfo(obj){
        var dataParam = JSON.stringify($(obj).serializeArray());
        var param =  {ajaxData : dataParam};
        var url = webPath + "/mfCusCustomer/getCusSort?cusNo=" + cusNo;
        $.ajax({
            url : url,
            data : param,
            success : function(data) {
                if (data.flag == "success") {
                    if(data.classifyType == '1'){//如果是黑名单客户，则进行提醒
                        alert(top.getMessage("CUS_CLASSIFY_TYPE","黑名单客户"),2,function(){
                            saveApplyInfoSizer(obj);
                        });
                        $.myAlert.Confirm("该客户为黑名单客户，确定是否发起业务申请","黑名单客户",function(){ saveApplyInfoSizer(obj);});
					}else{
                        saveApplyInfoSizer(obj);
					}
                } else {
                    alert(data.msg, 0);
                }
            },error : function() {
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });
	}

    function saveApplyInfoSizer(obj) {
        var url = $(obj).attr("action");

        checkByKindInfo($("[name='appAmt']"));// 用此方法控制下申请金额为0的问题
        if(!MfBusApplyInput.creditRatioOnblur()){
           return;
        }
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
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
            var dataParam = JSON.stringify($(obj).serializeArray());
            var param =  {ajaxData : dataParam,ajaxDataList : JSON.stringify(datas)};
            var kindno = $("[name=kindNo]").val();
            $.ajax({
                url : url,
                data : param,
                success : function(data) {
                    if (data.flag == "success") {
                        var url = webPath+'/mfBusApply/getSummary?appId='+ data.appId + '&busEntrance=1';
                        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
                        $(top.window.document).find("#showDialog").remove();
                    } else {
                        alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
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
            if (maxAmt != null && minAmt != null && maxAmt != "" && minAmt != "") {
                maxAmtNum = new Number(maxAmt);
                minAmtNum = new Number(minAmt);
                var appAmt = $(obj).val();
                appAmt = appAmt.replace(/,/g, "");
                if(canApplyAmt!= null ){
					var canApplyAmtNum =  new Number(canApplyAmt);
					//申请金额 大于可以申请的金额
                    if(parseFloat(appAmt) > parseFloat(canApplyAmtNum)){
                        $(obj).val(null);
                        alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
                            "info" : "该产品可以申请的剩余总金额上限",
                            "field" : title,
                            "value" : fmoney(canApplyAmtNum, 2)
                        }), 0);
                        return ;
					}
				}


                if (parseFloat(appAmt) < parseFloat(minAmtNum)|| parseFloat(appAmt) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info" : "产品设置",
                        "field" : title,
                        "value1" : fmoney(minAmtNum, 2),
                        "value2" : fmoney(maxAmtNum, 2)
                    }), 0);
                } else {
                    /* var creditPactId = $("INPUT[name='creditPactId']").val();
                    if(creditPactId==""||creditPactId==null){
                        return;
                    } */
                    if (creditAmt != null && creditAmt != "" && creditSum !=null && creditSum != "") { //判断申请金额是否在授信余额内
                        creditNum = new Number(creditAmt);
                        creditSum = new Number(creditSum);
						if (parseFloat(appAmt) > parseFloat(creditNum)) {
							alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
								"info" : "该客户授信",
								"field" : title,
								"value" : fmoney(creditNum, 2)
							}), 0);
						} else {
							if (kindCreditAmt != null && kindCreditAmt != "") { //判断申请金额是否在产品授信余额内
								kindCreditNum = new Number(kindCreditAmt);
								if (parseFloat(appAmt) > parseFloat(kindCreditNum)) {
									$(obj).val(null);
									alert(top.getMessage("NOT_APPLY_VALUE_BIG",{"info" : "该客户产品授信","field" : title,"value" : fmoney(kindCreditNum,2)}), 0);
								};
							};
						};
                    };
                };
            };
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
            $("[name=term]").attr("placeholder",
                appMinTerm + "-" + appMaxTerm);
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
					  action="${webPath}/mfBusApply/insertForApplyAjax_query">
					<dhcc:bootstarpTag property="formapply0007_query" mode="query" />
				</form>
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
			<div id="coborrNumName" class="row clearfix" >
				<%@ include file="/component/app/MfBusCoborrList.jsp"%>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存"
						  onclick="insertForApply('#insertForApplyForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
						  onclick="cancelApply();"></dhcc:thirdButton>
	</div>
	<div style="display: none;" id="fincUse-div"></div>
</div>
</body>
</html>