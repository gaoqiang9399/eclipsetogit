;
var MfBusLoanChange_Insert = function(window, $) {

    var _init = function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
        //初始化补充报告涉及事项选择组件
        _initReportPopupSelection(JSON.parse(reportMatter));
    };
    var _selectChangePact = function () {
        $("input[name='pactNo']").popupList({
            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusLoanChange/getPactList",//请求数据URL
            title: "选择合同",//标题
            searchplaceholder: "合同编号/客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.pactNo);
            },
            tablehead:{//列表显示列配置
                "pactNo":"合同编号",
                "kindName":"产品名称",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"pactNo"//真实值
            },
        });
        $("input[name='pactNo']").next().click();
    };

    var _getForm = function(pactNo){
        $.ajax({
            url : webPath+"/mfBusLoanChange/getPactChangeApply?pactNo=" + pactNo,
            success : function(data) {
                if (data.flag == "success") {
                    var html = data.htmlStr;
                    $("#MfBusPactChangeForm").empty().html(html);
                    if($("[name=vouType]").is(':visible')){
                        bindVouTypeByKindNo($("#MfBusPactChangeForm").find('[name=vouType]'), data.kindNo);
                    }
                    maxAmt = data.maxAmt;
                    minAmt = data.minAmt;
                    minTerm = data.minTerm;
                    maxTerm = data.maxTerm;
                    termType = data.termType;
                    minFincRate = data.minFincRate;
                    maxFincRate = data.maxFincRate;
                }
            }
        });

    };

    var _ajaxSave = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        var flag1 = submitJsMethod($('#supplementform').get(0), '');
        if(flag&&flag1){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            var supdataForm = JSON.stringify($('#supplementform').serializeArray());
            jQuery.ajax({
                url: url,
                data: {ajaxData: dataParam,
                       formData:supdataForm},
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                    LoadingAnimate.start();
                }, success: function (data) {
                    if (data.flag == "success") {
                        /*window.top.alert(data.msg, 3);
                        _myclose_reload();*/
                        var url = webPath+'/mfBusApply/getSummary?appId='+ data.appId + '&busEntrance=1';
                        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
                        $(top.window.document).find("#showDialog").remove();
                    } else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }, complete: function () {
                    LoadingAnimate.stop();
                }
            });
        }
    };

    var _myclose_reload  = function (){
        window.location.href=webPath+"/mfBusLoanChange/getListPage";
    };

    //验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
    var _checkByKindInfo = function (obj) {
        var name = $(obj).attr("name");
        var title = $(obj).attr("title").split("(")[0];
        //申请金额
        if (name == "pactAmtChange") {
            if (maxAmt != null && minAmt != null && maxAmt != "" && minAmt != "") {
                maxAmtNum = new Number(maxAmt);
                minAmtNum = new Number(minAmt);
                var pactAmtChange = $(obj).val();
                pactAmtChange = pactAmtChange.replace(/,/g, "");
                if (parseFloat(pactAmtChange) < parseFloat(minAmtNum)|| parseFloat(pactAmtChange) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info" : "合同金额",
                        "field" : title,
                        "value1" : fmoney(minAmtNum, 2),
                        "value2" : fmoney(maxAmtNum, 2)
                    }), 0);
                }
            };
        } else if (name == "fincRateChange") {//检测融资利率
            if (minFincRate != null && maxFincRate != null && minFincRate != "" && maxFincRate != "") {
                var maxFincRateNum = new Number(maxFincRate);
                var minFincRateNum = new Number(minFincRate);
                var fincRateChange = $(obj).val();
                if (parseFloat(fincRateChange) < parseFloat(minFincRateNum)
                    || parseFloat(fincRateChange) > parseFloat(maxFincRateNum)) {//判断申请金额是否在产品设置范围内
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

   var _checkTerm = function (obj) {
        var appTerm = $("input[name=termChange]").val();
        appTerm = appTerm.replace(/,/g, "");
        var title = $("input[name=termChange]").attr("title").split("(")[0];
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
            $("input[name=term]").attr("placeholder",
                appMinTerm + "-" + appMaxTerm);
            if (parseFloat(appTerm) < parseFloat(minTermNum)
                || parseFloat(appTerm) > parseFloat(maxTermNum)) {
                $("input[name=term]").val("");
                alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                    "info" : "产品设置",
                    "field" : title,
                    "value1" : appMinTerm,
                    "value2" : appMaxTerm
                }), 0);
            }
        }
    };

    //初始化补充报告涉及事项选择组件
    var _initReportPopupSelection = function (data) {
        $("input[name=reportMatter]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            title: "补充报告涉及事项",
            items: data,
            labelShow: false,
            changeCallback: function (obj, elem) {

            },
        });
    };

	return {
		init : _init,
        selectChangePact:_selectChangePact,
        ajaxSave:_ajaxSave,
        myclose_reload:_myclose_reload,
        getForm:_getForm,
        checkByKindInfo:_checkByKindInfo,
        checkTerm:_checkTerm,
        initReportPopupSelection:_initReportPopupSelection
    };
}(window, jQuery);
