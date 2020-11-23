function getAssName1Dialog(userInfo) {
    $("input[name=assName1]").val(userInfo.opName);
    $("input[name=assNo1]").val(userInfo.opNo);
};

//修改额度是否可循环后控制合同结束日期的最大值
function changeAuthCycle() {
    var beginDate = $("input[name=beginDate]").val();
    if (beginDate == "") {
        return;
    }
    var term = $("input[name=term]").val();
    var termType = $("[name=termType]").val();

    $.ajax({
        url: webPath + "/mfBusPact/getPactEndDateInfoMapAjax",
        data: {
            "beginDate": beginDate,
            "term": term,
            "termType": termType,
            "calcIntstFlag": calcIntstFlag,
            "pactEndDateShowFlag": pactEndDateShowFlag,
            "appId": appId,
            "pactId": pactId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                var endDate = data.endDate;
                $("input[name=endDateShow]").unbind("click");
                $("input[name=endDateShow]").on("click", function () {
                    var _this = this;
                    fPopUpCalendarDlg({elem: _this, max: endDate});
                });
            } else {
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}

//选择合同签订日后带出合同开始日期
function updatePactBeginDate() {
    var signDate = $("input[name=signDate]").val();
    $("input[name=beginDate]").val(signDate);
    //选择合同签订日后，清除合同开始日中的不能为空提示
    $("input[name=beginDate]").parent().find(".error.required").remove();
    updatePactEndDate();
}

function updatePactEndDate() {
    var beginDate = $("input[name=beginDate]").val();
    var signDate = $("input[name=signDate]").val();//签约日期
    //开始日期选择后，默认带出签约日期
    //$("input[name=signDate]").val(beginDate);
    var termShow = $("input[name=termShow]").val();
    var term = $("input[name=term]").val();
    var termType = $("[name=termType]").val();

    $.ajax({
        url: webPath + "/mfBusPact/getPactEndDateInfoMapAjax",
        data: {
            "beginDate": beginDate,
            "term": term,
            "termType": termType,
            "calcIntstFlag": calcIntstFlag,
            "pactEndDateShowFlag": pactEndDateShowFlag,
            "appId": appId,
            "pactId": pactId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                var endDate = data.endDate;
                var endDateShow = data.endDateShow;
                $("input[name=endDateShow]").val(endDateShow);
                $("input[name=endDate]").val(endDate);
                //选择合同开始日后，清除合同结束日中的不能为空提示
                $("input[name=endDateShow]").parent().find(".error.required").remove();
            } else {
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};

// 字符串替换
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

function editFollowPactNo(obj, id) {
    id = id.substring(10);
    $(obj).hide();
    if (followPactNoShowSts == "1") {
        $(obj).after("<input name=\"followPactNoShow\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNoShow(this,'" + id + "');\">");
    } else {
        $(obj).after("<input name=\"followPactNo\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNo(this,'" + id + "');\">");
    }

    $("input[name='followPactNo']")[0].focus();
}

function updateFollowPactNo(obj, id) {
    var followPactNo = $(obj).val();
    if (followPactNo != $(obj).prev().text() && "" != followPactNo.replaceAll(" ", "")) {
        $.ajax({
            url: webPath + "/mfBusCollateralDetailRel/updateFollowPactNoAjax",
            data: {id: id, followPactNo: followPactNo},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    $(obj).hide();
                    $(obj).prev().text(followPactNo);
                    $(obj).prev().show();
                    $(obj).remove();
                } else {
                    $(obj).hide();
                    $(obj).prev().show();
                    $(obj).remove();
                    window.top.alert(data.msg, 0);

                }
            }, error: function () {
                window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    } else {
        $(obj).hide();
        $(obj).prev().show();
        $(obj).remove();
    }
}

function updateFollowPactNoShow(obj, id) {
    var followPactNoShow = $(obj).val();
    if (followPactNoShow != $(obj).prev().text() && "" != followPactNoShow.replaceAll(" ", "")) {
        $.ajax({
            url: webPath + "/mfBusCollateralDetailRel/updateFollowPactNoShowAjax",
            data: {id: id, followPactNoShow: followPactNoShow},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    $(obj).hide();
                    $(obj).prev().text(followPactNoShow);
                    $(obj).prev().show();
                    $(obj).remove();
                } else {
                    $(obj).hide();
                    $(obj).prev().show();
                    $(obj).remove();
                    window.top.alert(data.msg, 0);

                }
            }, error: function () {
                window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    } else {
        $(obj).hide();
        $(obj).prev().show();
        $(obj).remove();
    }
}

function getTemplateBizConfigId(obj, id) {
    id = id.substring(10);
    $.ajax({
        url: webPath + "/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
        data: {id: id},
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                printFollowPactFile(data.templateBizConfigId, data.repayDetailId);
            } else {
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}

var printFollowPactFile = function (templateBizConfigId, repayDetailId) {
    var url = webPath + "/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
    var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
    var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&repayDetailId=' + repayDetailId;
    $.ajax({
        url: url + "&" + temParm,
        data: {
            "returnUrl": backUrl
        },
        type: 'post',
        dataType: 'json',
        beforeSend: function () {
            LoadingAnimate.start();
        },
        complete: function () {
            LoadingAnimate.stop();
        },
        error: function () {
            alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
        },
        success: function (data) {
            var poCntObj = $.parseJSON(data.poCnt);
            mfPageOffice.openPageOffice(poCntObj);
        }
    });
};

function checkRepayFixDate(obj) {
    var name = $(obj).attr("name");
    var title = $(obj).attr("title").split("(")[0];
    //固定还款日
    if (name == "repayDateDef") {
        var repayDateDefVal = $(obj).val();
        if (parseFloat(repayDateDefVal) < parseFloat(1) || parseFloat(repayDateDefVal) > parseFloat(29)) {
            $(obj).val(0);
            alert(title + "必须在" + new Number(1) + "到" + new Number(29) + "之间", 0);
        }
    }
};

function getCusMngNameDialog(userInfo) {
    $("input[name=cusMngName]").val(userInfo.opName);
    $("input[name=cusMngNo]").val(userInfo.opNo);
};
;
var MfBusPactInsert = function (window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });

        // 是否隐藏 复利利率上浮字段
        if (cmpdRateType == "0") {//隐藏
            $('input[name=cmpdFloat]').parent('.input-group').hide();
            $('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
        }
        //是否要走合同审批，走合同审批并且要指派第一个审批人员时，展示“指派审批人员”字段
        if (processId == null || processId == '') {
            //$("[name=firstApprovalUserName]").parents("td").hide();// 字段td
            //$("[name=firstApprovalUserName]").parents("td").prev("td").hide();// 标签td
            $("[name=firstApprovalUserName]").parents("tr").remove();// 字段td

        }
        //共同借款人处理
        if ($("input[name=coborrNum]").is(':visible')) {
            var $obj = $("input[name=coborrNum]");
            $("input[name=coborrNum]").popupSelection({
                searchOn: true,//启用搜索
                inline: false,//下拉模式
                items: coborrNum,//请求数据URL
                multiple: true,//多选
                title: "共同借款人",
                handle: false
            });
            if (typeof($obj.attr('readonly')) != 'undefined') {
                $obj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }

        //单纯下拉款展示还款方式(表单样式配置【toSelect】)
        var toSelect = $("#pactInsertForm .toSelect").length;
        if (toSelect > 0) {
            var repayTypeMap = ajaxData.repayTypeMap;
            $("[name=repayType]").empty();
            for (var index in repayTypeMap) {
                if (repayTypeMap[index].id) {
                    $("[name=repayType]").append("<option value=" + repayTypeMap[index].id + ">" + repayTypeMap[index].name + "</option>");
                }
            }
            $("[name=repayType]").val(repayType);
        } else {
            //还款方式处理
            var $repayObj = $("input[name=repayType]");
            if ($repayObj.is(':visible')) {
                $("input[name=repayType]").popupSelection({
                    searchOn: false,//启用搜索
                    inline: false,//下拉模式
                    multiple: false,//多选选
                    title: "还款方式",
                    valueClass: "show-text",
                    items: ajaxData.repayTypeMap,
                    changeCallback: function (obj, elem) {
                    }
                });
                if (typeof($repayObj.attr('readonly')) != 'undefined') {
                    $repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
                }
            }
        }


        //担保方式处理
        var $vouObj = $("input[name=vouType]");
        if ($vouObj.is(':visible')) {
            $("input[name=vouType]").popupSelection({
                searchOn: false,//启用搜索
                inline: true,//下拉模式
                multiple: false,//多选选
                title: "担保方式",
                items: ajaxData.vouTypeMap,
                changeCallback: function (obj, elem) {
                }
            });
            if (typeof($vouObj.attr('readonly')) != 'undefined') {
                $vouObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }
//		bindVouTypeByKindNo($("input[name=vouType]"), kindNo);

        var $repayDateSetObj = $("input[name=repayDateSet]");
        var repayDateSet1 = $("input[name=repayDateSet]").val();
        if (repayDateSet1 != "3") {
            $("input[name=repayDateDef]").addClass('hidden').parents('td').prev('td').children('label').addClass('hidden');
        }
        if ($repayDateSetObj.is(':visible')) {
            $("input[name=repayDateSet]").popupSelection({
                searchOn: false,//启用搜索
                inline: false,//下拉模式
                multiple: false,//多选选
                title: "还款日设置",
                valueClass: "show-text",
                items: ajaxData.repayDateSetMap,
                changeCallback: function (elem) {
                    var callBackVal = elem.data("values").val();
                    var callBackValArr = callBackVal.split("_");
                    //还款日类型为固定还款日时，固定还款日字段允许编辑,其他类型不允许编辑
                    if (callBackValArr[0] == "3") {
                        $("input[name=repayDateDef]").removeClass('hidden').parents('td').prev('td').children('label').removeClass('hidden');
                    } else {
                        $("input[name=repayDateDef]").addClass('hidden').parents('td').prev('td').children('label').addClass('hidden');
                    }
                }
            });
            if (typeof($repayDateSetObj.attr('readonly')) != 'undefined') {
                $repayDateSetObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }
        if ($("input[name=repayMode]").css("display") == "none" || $("input[name=repayMode]").attr("type") == "hidden") {

        } else {
            $("input[name=repayMode]").popupSelection({
                searchOn: false,//启用搜索
                inline: false,//下拉模式
                multiple: true,//多选选
                groupFlag: true,//启用分组
                title: "还款渠道",
                labelShow: false,
                items: ajaxData.repayModeMap,
                changeCallback: function (obj, elem) {
                }
            });
        }

        //处理其他合同类型-开始
        var $list = $(".input-group input[name='otherPact']");
        var arr = [[],[],[],[]];
        var content = '';
        $list.each(function(){
            var value = $(this).val();
            var listHtml ="";
            if (value.startsWith("1")){
                listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[0].push(listHtml)
            }
            if (value.startsWith("2")){
                listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[1].push(listHtml)
            }
            if (value.startsWith("3")){
                listHtml = $(this).prop('outerHTML') + $(this).attr('datavalue');
                arr[2].push(listHtml)
            }
        });
        content +="<div class='form-tips'>除系统生成合同以外，可以添加其他合同，如下：</div><br>";
        arr.forEach(function (item,index) {
            if(index == 0){
                content+="<label style='font-size: larger;font-weight: bold'>授信：</label>";
            }
            if(index == 1){
                content+="<label style='font-size: larger;font-weight: bold'>单笔：</label>";
            }
            if(index == 2){
                content+="<label style='font-size: larger;font-weight: bold'>其他：</label>";
            }
            item.forEach(function (item2) {
                content += item2
            });
            content += '</br></br>'
        });
        $(".input-group input[name='otherPact']").parent().html(content);
        //处理其他合同类型-结束
        MfBusBankAccCom.bankAccInit();
        MfBusPactInsert.agencySelect($("input[name=agencyName]"));
        if (baseRateChange == "false") {
            window.top.alert("市场报价利率已重新发布,该业务将按照新的市场报价利率进行执行", 3);
        }
        if(nodeNoOld=="contract_print"){
            $("#mfBusPactExtendListDiv").hide();
        }
    };

    //保存验证
    var _ajaxUpdateConfirm = function (obj, temporaryStorage) {
        var flag = true;
        if (temporaryStorage!='1') {
            flag = submitJsMethod($(obj).get(0), '');
        }
        if (flag) {
            var verity = _verityPactNo();
            if (verity != "1") {
                return;
            }
            ;
            //规则验证
            var ruleCusNo = $("input[name=cusNo]").val();
            var rulePactAmt = $("input[name=pactAmt]").val().replace(/,/g, "");
            var parmData = {'nodeNo': 'contract_sign', 'relNo': ruleCusNo, 'cusNo': ruleCusNo, 'pactAmt': rulePactAmt};
            $.ajax({
                url: webPath + "/riskForApp/getNodeRiskDataForBeginAjax",
                data: {ajaxData: JSON.stringify(parmData)},
                success: function (data) {
                    if (data.exsitRefused == true) {// 存在业务拒绝
                        top.window.openBigForm(webPath + '/riskForApp/preventList?relNo=' + ruleCusNo, '风险拦截信息', function () {
                        });
                    } else if (data.exsitFX == true) {//存在风险项
                        alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {
                            "content": data.fxlist,
                            "operation": "合同登记"
                        }), 2, function () {
                            _ajaxUpdate(obj, temporaryStorage);
                        });
                    } else {
                        if (temporaryStorage == '1') {// 暂存
                            _updateFormAjax(obj, temporaryStorage);
                        } else {
                            alert(top.getMessage("CONFIRM_OPERATION", "提交下一步"), 2, function () {
                                _ajaxUpdate(obj, temporaryStorage);
                            });
                        }
                    }
                }, error: function () {
                }
            });
        }
    };


    //合同签约保存方法
    var _ajaxUpdate = function (obj, temporaryStorage) {
        var url = $(obj).attr("action");
        var dataParam = JSON.stringify($(obj).serializeArray());
        LoadingAnimate.start();
        jQuery.ajax({
            url: url,
            data: {
                ajaxData: dataParam,
                wkfAppId: wkfAppId,
                taskId: taskId,
                appId: appId,
                'temporaryStorage': temporaryStorage,
                'nodeNoOld': nodeNoOld
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    top.flag = true;
                    top.pactUpdateFlag = true;//表示是否是合同签约节点
                    top.pactSts = data.pactSts;
                    top.pactDetailInfo = data.pactDetailInfo;
                    myclose_click();
                }
                if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };

    //合同签约暂存方法
    var _updateFormAjax = function (obj, temporaryStorage) {
        var url = webPath + '/mfBusPact/updateFormAjax';
        var dataParam = JSON.stringify($(obj).serializeArray());
        LoadingAnimate.start();
        jQuery.ajax({
            url: url,
            data: {
                ajaxData: dataParam,
                wkfAppId: wkfAppId,
                taskId: taskId,
                appId: appId,
                'temporaryStorage': temporaryStorage,
                'nodeNoOld': nodeNoOld
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert(data.msg,1);
                    window.location.reload();
                }
                if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };

    //初始化资金机构  根据需要传入titleName
    var _initCusNameFund = function () {
        $("input[name=cusNameFund]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusAgencies/getMfBusAgenciesListAjax", // 请求数据URL
            valueElem: "input[name='cusNoFund']",//真实值选择器
            title: "选择机构",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=cusNoFund]").val(tmpData.agenciesId);
                $("input[name=cusNameFund]").val(tmpData.agenciesName);
            },
            tablehead: {//列表显示列配置
                "agenciesName": "名称",
                "agenciesId": "编号"
            },
            returnData: {//返回值配置
                disName: "agenciesName",//显示值
                value: "agenciesId"//真实值
            }
        });
        $("input[name=cusNameFund]").next().click();
    };

    //合同结束日期修改后,自动计算期数
    var _updateTermByEndDate = function () {
        var beginDate = $("input[name=beginDate]").val().replace(/,/g, '');
        var endDate = $("input[name=endDateShow]").val().replace(/,/g, '');//签约日期
        if (parseInt(beginDate) > parseInt(endDate)) {
            alert(top.getMessage("NOT_FORM_TIME", {"timeOne": "合同开始日", "timeTwo": "合同结束日"}), 0);
            $("input[name=endDateShow]").val('');
            $("input[name=endDate]").val('');
            return;
        }
        $("input[name=endDate]").val(endDate);
        $.ajax({
            url: webPath + "/mfBusPact/getTermByEndDateBeginDateAjax",
            data: {"beginDate": beginDate, "endDate": endDate},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    var term = data.term;
                    var termUnit = '';
                    var termType = $("input[name=termType]").val();
                    if (termType == '1') {
                        termUnit = "个月";
                    } else if (termType == '2') {
                        termUnit = "天";
                    }
                    $("input[name=term]").val(term);
                    $("input[name=termShow]").val(term + termUnit);
                    //$("input[name=endDateShow]").parent().find(".error.required").remove();
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error: function () {
                window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };

    var _verityPactNo = function () {
        var verity = "1";
        var url = webPath + "/mfBusPact/verityPactNo";
        var pactNo = $('input[name=pactNo]').val();//获取合同编号
        var pactId = $('input[name=pactId]').val();//获取合同流水号
        if (pactNo != '' && pactNo != null) {
            $.ajax({
                async: false,
                url: url,
                data: {pactNo: pactNo, pactId: pactId},
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                },
                success: function (data) {
                    if (data.flag == 0) {
                        window.top.alert(data.msg, 3);
                        verity = "0";
                    }
                    ;
                },
                error: function (data) {
                    window.top.alert(data.msg, 3);
                    verity = "0";
                }
            });
        }

        return verity;
    }


    //判断银行合同登记日期是否在授信合同结束日期之前
    var _creditEndDate = function () {
        var startDate1 = $("input[name=beginDate]").val().replace(/-/g, '');
        var pactId = creditPactId;
        $.ajax({
            async: false,
            url: webPath + "/mfCusCreditContract/getByPactIdAjax",
            data: {pactId: pactId},
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    var endDate1 = data.mfCusCreditContract.endDate;
                    if (parseInt(startDate1) <= parseInt(endDate1)) {
                        updatePactEndDate();
                        return true;
                    } else {
                        window.top.alert("合同的签订日期不可在授信结束日期之后", 0);
                        $("input[name=beginDate]").val("");
                        return false;
                    }


                }
                ;
            }
        });

    }

    var _agencySelect = function (obj) {
        var element = $(obj).attr('name');
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfBusInstitutions/getAgenciesDataSourse",// 请求数据URL
            valueElem: "input[name=" + element + "]",//真实值选择器
            title: "选择经办机构",//标题
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=" + element + "]"));
                // debugger
                var sltVal = elem.data("selectData");
                $("input[name='agencyName']").val(sltVal.agenciesName);
                $("input[name='agenctNo']").val(sltVal.agenciesId);
            },
            tablehead: {//列表显示列配置
                "agenciesName": "机构名称",
                "agenciesId": "机构编号"

            },
            returnData: {//返回值配置
                disName: "agenciesName",//显示值
                value: "agenciesId"//真实值
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
    var   _addPactExtend  = function(queryType){
        top.openBigForm(webPath+"/mfBusPact/addPactExtend?appId="+appId+"&cusNo="+cusNo+"&queryType="+queryType, "添加非系统生成相关合同", function(){
            _getExendListHtmlAjax(queryType);
        });
    }
    var   _pactExtendDetail  = function(obj,url){
        top.window.openBigForm( webPath + url + "&appId="+appId,'编辑非系统生成相关合同',function() {
                _getExendListHtmlAjax();
        });
    }
    var _deleteExtendAjax = function(obj,url){
        alert(top.getMessage("CONFIRM_DELETE"),2,function() {
            $.ajax({
                url: webPath + url + "&appId=" + appId,
                success: function (data) {
                    if (data.flag == "success") {
                        _getExendListHtmlAjax();
                    } else {
                    }
                }, error: function () {

                }
            });
        });
    };
//刷新权证列表数据
    var _getExendListHtmlAjax = function  (queryType){
        var tableId = "tablemfBusPactExtendAppList";
        var queryType1 = "";
        if(typeof (queryType) != "undefined" && "2" == queryType){
            tableId = "tablepactExtendStamp";
            queryType1 = queryType;
        }
        $.ajax({
            url: webPath + "/mfBusPact/getExendListHtmlAjax",
            data:{appId:appId,
                tableId:tableId,queryType:queryType1},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#mfBusPactExtendList").html(data.tableData);
                } else {

                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    };
    return {
        init: _init,
        ajaxUpdate: _ajaxUpdate,
        initCusNameFund: _initCusNameFund,
        ajaxUpdateConfirm: _ajaxUpdateConfirm,
        verityPactNo: _verityPactNo,
        updateTermByEndDate: _updateTermByEndDate,
        creditEndDate: _creditEndDate,
        agencySelect: _agencySelect,
        getCalcRateByFincRateAjax: _getCalcRateByFincRateAjax,
        addPactExtend: _addPactExtend,
        pactExtendDetail: _pactExtendDetail,
        deleteExtendAjax:_deleteExtendAjax,
    };
}(window, jQuery);