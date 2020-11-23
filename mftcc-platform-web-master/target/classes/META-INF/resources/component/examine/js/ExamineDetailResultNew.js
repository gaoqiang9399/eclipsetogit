$(function () {
    if (dataMap != undefined && dataMap != null && dataMap != "") {
        var $table = $(".info .evel_table tbody");
        var $tr = $("<tr></tr>");
        var totalScore = 0;
        if (dataMap.entityData !== undefined) {
            var $td1 = $('<td style="width:50%"></td>');
            //var evalIndexType = dataMap.evalIndexType;
            var riskLevel = dataMap.riskLevel;
            var fontColor = "#40B745";
            if (riskLevel == "2") {
                fontColor = "#47ADC3";
            } else if (riskLevel == "1") {
                fontColor = "#C3A34C";
            }
            ;
            totalScore = dataMap.entityData.examineScore;
            if (!dataMap.remark) {
                dataMap.remark = "";
            }
            $td1.append('<span class="evel_title">客户名称</span>' +
                '<span style="font-size: 15px; margin-left: 25px;">' + dataMap.cusName + '</span>' +
                '<span class="evaluation">' +
                '<span class="evel_title">检查模型</span>' +
                '<span style="font-size: 15px;margin-left: 25px;">' + dataMap.templateName + '</span>' +
                '<span class="evaluation">' +
                '<span class="evel_title">综合结果</span>' +
                '<span style="font-size: 41px;color: ' + fontColor + ';margin-left: 25px;">' + dataMap.riskLevelRemark + '</span>' +
                '<span class="evaluation">' +
                dataMap.remark + '</span><span style="font-size: 11px;"><a href="#evalHis-div" name="evalHis-div">检查历史</a></span>');
            $tr.append($td1);
            var $td2 = $('<td style="width:50%"></td>');
            $td2.append('<div style="text-align: right;">' +
                '<span class="score_lable">得分</span>' +
                '<span class="count_score">' + totalScore + '</span>' +
                '</div>');
            $td2.append('<div class="bar_div"><span class="bar_outter">' +
                '<span class="bar_inner"></span>' +
                '</span></div>');

            var $showEval = $('<div class="div_line ">' +
                '<span class="radio_span">' +
                '<span class="radio_span_inner"></span>' +
                '</span>' +
                '<span class="radio_lable"></span>' +
                '<span class="radio_filed"></span>' +
                '</div>');
            //$(".li_content_type").hide();
            //检查模型配置了检查项
            if (dataMap.indexFlag == "1") {
                $.each(dataMap.examCardListData, function (i, obj) {
                    var $tempShow = $showEval.clone(true);
                    if (i > 4) {
                        i = 0;
                    }
                    $tempShow.addClass('show_' + i);
                    $tempShow.find(".radio_lable").html('<a href="#dx' + obj.examineCardId + '" name="dx' + obj.examineCardId + '">' + obj.examineCardName + '</a>');
                    if (dataMap["dxData" + obj.examineCardId] != null && dataMap["dxData" + obj.examineCardId].score > 0) {//finData
                        $tempShow.find(".radio_filed").text("+" + dataMap["dxData" + obj.examineCardId].score);
                    } else {
                        $tempShow.find(".radio_filed").text("");
                    }
                    $td2.append($tempShow);
                })
                //去掉得分
                //$tr.append($td2);
            }
        } else {
            var $td = $('<td></td>');
            $td.append('<span class="evel_title">综合结果</span>');
            $td.append('<span class="evaluation">该合同没有审批通过的检查</span>');
            $tr.append($td);
        }
        $table.append($tr);
        $table.find(".bar_inner").animate({width: totalScore + "%"}, 1200);
    }
    if (dataMap.indexFlag == "1") {
        //初始化页面的表头
        initThead(showData);
        initData(dataMap.listData);
    } else {
        $("#examCardInfo").hide();
    }
    /*showWkfFlow($("#wj-modeler2"),evalAppNo);*/
    if (examProcessId != "") {
        $("#examApproveHis-div").show();
        showWkfFlowVertical($("#wj-modeler2"), examHisId, "7","exam_approval");
    }

    $(".scroll-content").mCustomScrollbar({
        advanced: {
            updateOnContentResize: true
        }
    });


});
var showData = {
    fin: {//财务
        "indexName": "指标名称",
        "stdCore": "指标值"
    },
    dx: {//定性
        "indexName": "指标名称",
        "ctrl_btn": "打分选项"
    },
    dl: {//
        "indexName": "指标名称",
        "stdCore": "指标值"
    },
    adj: {
        "indexName": "指标名称",
        "ctrl_btn": "加减分选项卡"
    }
};

function initThead(showData) {
    if (dataMap.examCardListData) {
        $.each(dataMap.examCardListData, function (i, obj) {
            $.each(showData, function (index, dataObj) {
                var $liContent = $("#freewall").find("div[name=" + index + obj.examineCardId + "]");
                var $table = $liContent.find("table");
                var $thTr = $('<tr></tr>');
                $.each(dataObj, function (key, dic) {
                    var $th = $('<th name=' + key + '>' + dic + '</th>');
                    $thTr.append($th);
                    if (key == "indexName") {
                        $th.before('<th style="width:10%;" name="noneshow">&nbsp;</th>');
                        $th.addClass("text_align_s");
                    }
                });
                $table.find("thead").append($thTr);
            });
        });
    }
}

function initData(listData) {
    $.each(listData, function (key, data) {
        initTbody(key, data);
    });
    $.each(dataMap.examCardListData, function (iArgs, obj) {
        if (dataMap["dxData" + obj.examineCardId]) {
            var str = "dxData" + obj.examineCardId;
            var dxList = dataMap[str].scoreList;
            for (var i = 0; i < dxList.split("@").length - 1; i++) {
                var dxobj = dxList.split("@")[i];
                var name = dxobj.split(":")[0];
                var vuale = dxobj.split(":")[1];
                $("div[name=dx" + obj.examineCardId + "]").find("input[type=radio][name=" + name + "]").each(function (index) {
                    if (vuale == $(this).attr("value")) {
                        $(this).prop("checked", true);
                        $(this).attr("checked", "checked");
                        return false;
                    } else {//如果选中的不是第一个，去掉第一个默认选中状态
                        $(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
                    }
                });
            }
            $("div[name=dx" + obj.examineCardId + "]").find("input[type=radio]").attr("disabled", "true");
        }
    });
    $(".li_content_type").find("tr").css({height: "26px"});
    /*$("input[type=radio]").hide();
    $("input[type=radio]").parent().hide();
    $("input[checked=checked]").parent().show();*/
};

function initTbody(type, data) {
    $(".li_content_type").each(function (index) {
        var $this = $(this);
        var name = $(this).attr("name");
        var nameUpperCase = name.toUpperCase();
        type = type.toUpperCase();
        if (name !== undefined && nameUpperCase.indexOf(type) != -1) {
            var $table = $(this).find("table[class=ls_list_a]");
            var thLength = $table.find("thead th").length;
            if (type == nameUpperCase) {
                $table.find("tbody[class=level1]").html("");
                $.each(data, function (index, entityObj) {
                    var $tr = $("<tr></tr>");
                    var level = entityObj.level;
                    var upIndexId = entityObj.upIndexId;
                    $tr.data("entityObj", entityObj);
                    if (level == 1) {
                        $tr.append('<td class="font_weight border_left first-td">' + entityObj.indexName + '</td><td class="border_left td" colspan="2"></td>');
                        $table.find("tbody[class=level1]").append($tr);
                    } else if (level == 2) {
                        $table.find("tbody tr").each(function (index) {
                            if ($(this).data("entityObj") !== undefined) {
                                var $ValTd = $(this).find("td").eq(1);
                                var thisIndexId = $(this).data("entityObj").indexId;
                                if (upIndexId == thisIndexId) {
                                    var $table = $("<table style='height:26px;width:100%'></table>");
                                    $tr.append('<td name = "' + upIndexId + '" class="border_left second-td-adj" rowspan="1">' + entityObj.indexName + '</td><td class="border_right" style="width: 66%;" colspan="2"></td>');
                                    $table.append($tr);
                                    $(this).find("td").eq(1).append($table);
                                }
                            }
                        });
                    } else {
                        $table.find("tbody tr").each(function (index) {
                            if ($(this).data("entityObj") !== undefined) {
                                var $ValTd = $(this).find("td").eq(1);
                                var thisIndexId = $(this).data("entityObj").indexId;
                                if (upIndexId == thisIndexId) {
                                    var $radioInput = $('<input onclick="dxScoreChange(this);id="' + entityObj.indexId + '" name="' + entityObj.indexId + '" type="radio" value="' + entityObj.indexRiskLevel + '">');
                                    var $thisTbody = $ValTd.find("table tbody");
                                    if ($ValTd.find("table").length > 0) {
                                        var trNum = false;
                                        $thisTbody.find("tr").each(function (index) {
                                            for (var int = 0; int < $(this).find("td").length; int++) {
                                                var len = $(this).find("td").eq(int).html().length;
                                                if (len <= 0) {
                                                    $(this).find("td").eq(int).append($radioInput.prop('outerHTML') + entityObj.indexName);
                                                    trNum = true;
                                                    break;
                                                }
                                            }
                                        });
                                        if (!trNum) {
                                            $thisTbody.append('<tr><td>' + $radioInput.prop('outerHTML') + entityObj.indexName + '</td><td></td><td></td></tr>');
                                        }
                                    } else {
                                        var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody class="level3"><tr><td></td><td></td><td></td></tr></tbody></table>');
                                        //$radioInput.attr("checked","checked");
                                        $thisTable.find("tr").find("td").eq(0).append($radioInput.prop('outerHTML') + entityObj.indexName);
                                        $ValTd.append($thisTable);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }
    });
    $(".second-td-adj").css('border-bottom', '0px');
    $(".td").children("tr:first-child").children().css('border-top', '0px');
};