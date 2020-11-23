/**
 * 双击保存单字段方法说明
 * 1.校验方法重写（更新时特殊验证更新值的正确性）
 *   1).function dblValidate(key,val){}
 *      key 更新字段
 *      val 为更新后将更改的值
 *   2).使用方法
 *      function dblValidate(key,val){if(key=="demoId"){checkTelOnly(document.getElementsByName('demoId')[0]);}}
 * 2.同步更新字段重写（更新时同时更新相应字段）
 *   1).function dblUpdateVal(key,data){}
 *      key 为当前更新的字段
 *      data为更新后台的json对象
 *   2).使用方法（当前使用jsp页面或所引用的js里）：
 *      function dblUpdateVal(key,data){if(key=="demoId"){data["demoName"]="例子";}}
 *
 * 3.修改成功回调重写方法 (为 锚点触发做显隐处理)
 * function dblSuccessCallback(dataname){
 *   handleAnchorFun($(".fieldReal [name='"+dataname+"']"));
 * };
 */
$(function () {
    dblclickflag();
    $("body").bind("click", function (e) {
        /*
         * 除"changeval"外还有"i i-editable"-小笔编辑图标
         * add by LiuYF.list-setval
         * pops、mCSB、list-checkbox、M-box 这种情况是单子段编辑和选择组件结合使用时，排除的情况--20171123
         */
        if (e.target.className != "changeval" && e.target.className != "list-setval" && e.target.className != "list-checkbox" && e.target.className.indexOf("pops") == -1 && e.target.className != "M-box" && e.target.className.indexOf("mCSB") == -1 && e.target.className != "i i-editable" && $(e.target).parents(".changeval").length == 0 && $(e.target).parents(".pops-list-body").length == 0) {

            $(".changeval").remove();
        }
    });
});
//单独为某个表单添加，$(selector).dblclickflag();
(function ($) {
    $.fn.dblclickflag = function () {
        $(this).find(".fieldShow").each(function () {
            addTextToNone(this);
        });
    };
})(jQuery);

//异步加载表单完成后调用，对所有表单生效。
function dblclickflag() {
    $(".fieldShow").each(function () {
        addTextToNone(this);
    });
}

/**
 * 为未填写的无内容字段，增加“未登记”字样。
 * @param fieldShowDiv
 * @author LiuYF
 */
function addTextToNone(fieldShowDiv) {
    var fieldShow = $(fieldShowDiv);
    var str = fieldShow.text();
    if (!str) {
        fieldShow.html('<em style="color:#b1b1b1; font-style: italic;">未登记</em>');
    }
}

//双击修改公共方法
//$(function(){
//json 更新方法
var ajaxUpdateOne = function ($obj, jsonData, primarykeys, formNo, callback, disVal) {
    var tablename = $
    var tempJson = $.extend({}, jsonData);
    var flag = false, dblValidateFlag = true;
    $.each(jsonData, function (key, val) {
        if ($.isFunction(window.dblValidate)) {
            dblValidateFlag = window.dblValidate(key, val);
        }
    });
    if (dblValidateFlag) {//字段校验
        var formId = $obj.parents(".changeval").attr("formId");
        var primarykeyVals = primarykeys.split("&");
        $.each(primarykeyVals, function (index, val) {
            if (val != null && val.indexOf("=") != -1) {
                var primarykey = val.split("=")[0];
                var value = val.split("=")[1];
                jsonData[primarykey] = value;
            }
        });
        var dataP = [];
        $.each(jsonData, function (key, val) {
            var pushJson = {};
            pushJson.name = key;
            pushJson.value = val;
            dataP.push(pushJson);
        });
        var formIdNum = {};
        formIdNum.name = "formId";
        formIdNum.value = formNo;
        dataP.push(formIdNum);
        var submitUrl = $("#" + formId).attr('action');
        var dataParam = JSON.stringify(dataP);
        var cusFlag = false;
        //判断是否是修改的字段是否要走审批
        var arrayUrl = new Array(); //定义一数组
        arrayUrl = submitUrl.split("/"); //字符分割
        var identification = arrayUrl[arrayUrl.length-2];
        identification=identification.slice(0, 1).toUpperCase() + identification.slice(1);
        cusFlag = ifApprovalCusInfo(dataParam, identification);
        if (!cusFlag) {
            jQuery.ajax({
                url: submitUrl,
                data: {ajaxData: dataParam, "formId": formNo},
                type: "POST",
                dataType: "json",
                async: false,//关键
                success: function (data) {
                    if (data.flag == "error") {
                        alert(data.msg, 0);
                    } else {
                        flag = true;//必须写
                        if (callback) {
                            callback.call($("#" + formId), dataP, disVal);
                        }
                        if (typeof(oneCallback) == "function") {
                            oneCallback.call($("#" + formId), dataP, disVal);
                        }
//						if($("#"+formId).parents(".listshow-tr").length>0){
//							changeListVal(tempJson,$("#"+formId).parents(".listshow-tr"));
//						}
                    }
                }, error: function (data) {
                    //$.myAlert.Alert("保存失败！");
                    alert("保存失败！", 0);
                }
            });
        }
    }
    return flag;
};

//判断修改客户信息是否走审批
var ifApprovalCusInfo = function (dataParam, identification) {
    var flag = false;
    var effectFlag;
    if (effectFlag == "2") {// 客户状态为销户，不允许修改数据
        flag = true;
        alert("客户已被销户，不允许修改客户信息");
        return flag;
    }
    jQuery.ajax({
        url: webPath + "/mfCusKeyInfoFields/ifKeyField",
        data: {ajaxData: dataParam, identification: identification},
        type: "POST",
        dataType: "json",
        async: false,//关键
        success: function (data) {
            if (data.flag == "error") {
                flag = true;
                alert("保存失败");
            } else {
                if (data.ifKeyField == "1") {
                    dataParam = data.updeteJson;
                    ifApprovl = data.ifApprovl
                    if (ifApprovl == "0") {
                        ifApprovlRecordInfo(data.mfCusInfoChange)
                        flag = false;
                    } else {
                        flag = true;
                        top.openBigForm(webPath + "/mfCusInfoChange/input?ajaxData=" + encodeURIComponent(data.mfCusInfoChange), "客户信息修改申请", function () {
                        })
                    }
                } else {
                    flag = false;
                }
            }
        }, error: function (data) {
            flag = true;
            alert("保存失败");
        }
    });
    return flag;

}

//配置字段但不走审批
var ifApprovlRecordInfo = function(mfCusInfoChange){
    jQuery.ajax({
        url: webPath + "/mfCusInfoChange/ifApprovlRecordInfo",
        data: {ajaxData: mfCusInfoChange},
        type: "POST",
        dataType: "json",
        async: false,//关键
        success: function (data) {
            if (data.flag == "success") {//单字段修改成功后，改变信息变更记录按钮颜色
                $("#cusInfoChangeRecordQuery").removeClass("btn-lightgray");// 去掉灰色样式
                $("#cusInfoChangeRecordQuery").addClass("btn-dodgerblue");// 添加蓝色
            }
        }, error: function () {
            LoadingAnimate.stop();
        }
    });
}


//动态修改列表值
var changeListVal = function (jsonData, $tr) {
    var $table = $tr.parents("table.ls_list");
    var prevTr = $tr.prev();
    $table.find("thead th").each(function () {
        if (jsonData.hasOwnProperty($(this).attr("name"))) {
            var tempTd = prevTr.find("td").eq($(this).index());
            if (tempTd.find("a").length > 0) {
                tempTd.find("a").html(jsonData[$(this).attr("name")]);
            } else {
                tempTd.html(jsonData[$(this).attr("name")]);
            }
        }
    });
};
//追加显示框
var addChangevalDiv = function ($event, name, fieldVal, clickEvent) {
    var $obj = $event.find(".fieldReal").children();
    var formId = $event.parents("form").attr("id");
    var attrArr = ["name", "title", "datatype", "mustinput", "maxlength", "onblur", "onclick", "onmousedown", "onkeyup", "onkeydown", "onchange", "onfocus"];
    if ($obj.html() !== undefined) {
        var inputType = $obj[0].type;
        var inputHtml = "";
        var width = $event.parent().width();
        var maxlength;
        /*
         * 遮挡到后方字段了，减去后置图标的宽度。
         * add by LiuYF
         * 20170516
         */
        width = width - 21;
        if (width < 40) {
            width = 40;
        }
        var height = $event.find(".fieldShow").height();
        var str = "";
        for (var x = 0; x < attrArr.length; x++) {
            if (typeof($obj.attr(attrArr[x])) != "undefined") {
                if (attrArr[x] == "onblur") {
//						str+=" onblurs=\""+$obj.attr(attrArr[x])+"\" ";
                    //单字段编辑验证信息以alert框形式提示--zhs 20170726
                    var blur = $obj.attr(attrArr[x]);
                    if (!blur || blur.indexOf("func_uior_valTypeImm(this);") === -1) {
                        str += " onblurs=\"" + $obj.attr(attrArr[x]) + "\" ";
                    } else {
                        blur = blur.replace('func_uior_valTypeImm(this);', '');
                        blur = "func_uior_valTypeImm_alert(this);" + blur;
                        str += " onblurs=\"" + blur + "\" ";
                    }
                } else {
                    str += " " + attrArr[x] + "=\"" + $obj.attr(attrArr[x]) + "\" ";
                }
            }
        }
        if (inputType == "text") {//文本 文本域
            maxlength = $obj.attr("maxlength");
            if (maxlength === undefined || maxlength == null) {
                maxlength = "";
            }
            var datatype = $obj.attr("datatype");

            if (clickEvent != null) {
                inputHtml += '<input onclick="' + clickEvent + '" class="inputText" name="' + name + '" type="text" maxlength="' + maxlength + '" style="width:' + (width) + 'px;" value="' + $obj.val() + '"/>';
                if (datatype == "6") {
                    inputHtml += '<i onclick="' + clickEvent + '" class="fa fa-calendar" ></i>';
                }
            } else {
                if (datatype == "6") {
                    var timestamp = Date.parse(new Date());
                    var inputId = "date" + timestamp;
                    var length = $obj.val().length;
                    var onclickVal = '';
                    //	var onclickVal = 'onClick="fPopUpCalendarDlg({elem:\'#'+inputId+'\',format:\'YYYYMMDD\'});"';
                    if (length == 10) {
                        //	onclickVal = 'onClick="fPopUpCalendarDlg({elem:\'#'+inputId+'\',format:\'YYYY-MM-DD\'});"';
                    } else {
                    }
                    inputHtml += '<input ' + str + ' id="' + inputId + '" ' + onclickVal + ' class="inputText" type="text"  style="width:' + width + 'px" value="' + $obj.val() + '"/>';
                    inputHtml += '<i ' + onclickVal + ' class="fa fa-calendar" ></i>';
                } else {
                    inputHtml += '<input class="inputText" type="text" ' + str + ' style="width:' + (width) + 'px;" value="' + $obj.val() + '"/>';
                }
            }
        } else if (inputType == "textarea") {
            maxlength = $obj.attr("maxlength");
            if (maxlength === undefined || maxlength == null) {
                maxlength = "";
            }
            inputHtml += '<textarea ' + str + ' id="' + inputId + '" ' + onclickVal + ' style="min-width:' + width + 'px;border: 1px solid #acb9bd;height:' + $event.parent().height() + 'px;" >' + $obj.val() + '</textarea>';
        } else if (inputType == "checkbox") {//复选
            $obj.each(function () {
                var type = $(this).attr("type");
                if (type == "checkbox") {
                    var value = $(this).val();
                    var textVal = $(this).attr("dataValue");
                    inputHtml += '<input ' + str + ' type="checkbox" name="' + name + '" value="' + value + '" datavalue="' + textVal + '"  ' + ($(this).prop("checked") ? "checked" : "") + '/><span>' + textVal + '</span>&nbsp;';
                }
            });
        } else if (inputType == "radio") {//单选
            $obj.each(function () {
                var type = $(this).attr("type");
                if (type == "radio") {
                    var value = $(this).val();
                    var textVal = $(this).attr("dataValue");
                    inputHtml += '<input ' + str + ' type="radio" name="' + name + '" value="' + value + '" datavalue="' + textVal + '"/><span>' + textVal + '</span>&nbsp;';
                }
            });
        } else if (inputType == "select-one") {
            inputHtml += '<select name="' + name + '" ' + str + ' style="width:' + (width) + 'px;">';
            $obj.find("option").each(function (index) {
                if ($(this).text().trim() == fieldVal) {
                    $(this).attr("selected", true);
                } else {
                    $(this).removeAttr("selected");
                }
            });
            inputHtml += $obj.html();
            inputHtml += '</select >';
        }
        var changevalDivHtml = '<div class="changeval" dataname="' + name + '" formId="' + formId + '"  style="display: none;">';
        changevalDivHtml += '<span class="inputcontent" >';
        changevalDivHtml += inputHtml;
        changevalDivHtml += '</span>';
        changevalDivHtml += '<i class="ok ok_dbl"></i>';
        //	changevalDivHtml += '<i class="no close_dbl"></i>';
        changevalDivHtml += '</div>';
        /*
         * 修复主体页面单字段编辑在加了自定义滚动条后，编辑框不随页面滚动的问题。
         * 需要给body加滚动，并且首层是container-DIV。
         * by LiuYF 2017-03-17.
         */
//				if($(".container").length > 0){
//					$(".container").append(changevalDivHtml);
//				}else{
        $("body").append(changevalDivHtml);
//				}
    } else {
        //$.myAlert.Alert("数据字典项有误，请检查!");
    }
};

//追加显示框(座机)
var addChangevalDivTel = function ($event, name, fieldVal, clickEvent, fieldType) {
    var $obj = $event.find(".fieldReal").children();
    var formId = $event.parents("form").attr("id");
    var attrArr = ["datatype", "mustinput", "maxlength", "onblur", "onclick", "onmousedown", "onkeyup", "onkeydown", "onchange", "onfocus"];
    var widthArr = ["40", "80", "40"];
    if ($obj.html() !== undefined) {
        var inputHtml = "";
        var width = 190;
        width = width - 21;
        if (width < 40) {
            width = 40;
        }
        var height = $event.find(".fieldShow").height();
        $obj.find("input").each(function (index, inputObj) {
            var $inputObj = $(inputObj);
            var inputName = $inputObj.attr("name");
            var str = "";
            for (var x = 0; x < attrArr.length; x++) {
                if (typeof($inputObj.attr(attrArr[x])) != "undefined") {
                    if (attrArr[x] = "onblur") {
                        str += " onblurs=\"" + $inputObj.attr(attrArr[x]) + "\" ";
                    } else {
                        str += " " + attrArr[x] + "=\"" + $inputObj.attr(attrArr[x]) + "\" ";
                    }
                }
            }
            var maxlength = $inputObj.attr("maxlength");
            if (maxlength === undefined || maxlength == null) {
                maxlength = "";
            }
            if (index > 0) {
                inputHtml += "<span>-</span>";
            }
            inputHtml += '<span><input class="inputText" name="' + inputName + '" type="text" maxlength="' + maxlength + '" style="text-align: center;width:' + (widthArr[index]) + 'px;" value="' + $inputObj.val() + '"/></span>';
        });
        var changevalDivHtml = '<div class="changeval" dataname="' + name + '" formId="' + formId + '"  style="display: none;">';
        changevalDivHtml += '<span class="inputcontent" >';
        changevalDivHtml += inputHtml;
        changevalDivHtml += '</span>';
        changevalDivHtml += '<i class="telok ok_dbl"></i>';
        changevalDivHtml += '</div>';
        $("body").append(changevalDivHtml);
    } else {
        //$.myAlert.Alert("数据字典项有误，请检查!");
    }
};

//移上
window.hoverDbClick = function (obj) {
    /*		var $this = $(obj);
            var offset = $this.find(".fieldShow").offset();
            var $penHtml = $('<div class="isChange i i-bi"></div>');
            if($("body").find(".isChange").length==0){
                $("body").append($penHtml);
            }else{
                $penHtml = $("body").find(".isChange");
            }
            $penHtml.css({
                position: "absolute",
                top:offset.top+4,
                left:offset.left-12,
                height:"18px",
                fontSize:"12px"
            });*/
};
$(function () {
    //当表单横向显示时，可修改的字段的class是bolddblclickflag，当表单竖向展示时，可修改字段的class是dblclickflag
    $("body").delegate(".bolddblclickflag, .dblclickflag", {
        "mouseenter": function () {
            var $this = $(this);

            var $ipen = $this.data("editpen");
            if (!$ipen) {
                $ipen = $("<div class='editpen' onclick='changedDbClick(this.parentNode||this.parentElement);'><i class ='i i-editable'></i></div>");
                $ipen.appendTo($this);
                $this.data("editpen", $ipen);
            } else {
                $ipen.show();
            }
        },
        "mouseleave": function () {
            var $this = $(this);
            $this.find(".editpen").hide();
        }
    });

    //2016-11-04  失去焦点就提交这种方式，在需要弹窗选择时，必将使失去焦点，导致还未选择就提交了，所以先注释掉
    /*$("body").delegate(".inputcontent input,select", "blur", function(){
        $(this).parents(".changeval").find(".ok").click();
    });*/
});


window.changedDbClick = function (objThis, callback) {
    var $this = $(objThis);
    var formNo = $this.parents("table").attr("title");
    var fieldType = $this.attr("field-type");
    var formId = $this.parents("table").find("input[name=formId]").val();
    var name = $this.find(".fieldReal").children().eq(0).attr("name");
    var clickEvent = $this.find(".fieldReal").children().eq(0).attr("onclick");
    var fieldVal = $this.find(".fieldShow").text().trim();
    var cutoff = $this.find(".fieldShow").attr("cutoff");

    var primarykeys = $this.find(".fieldReal").attr("primarykeys");
    if (name === undefined && fieldType != undefined) {
        name = $this.attr("field-name");
        if (fieldType == "91"//座机 区域-主机号
            || fieldType == "92") {//座机 区域-主机号-分机号
            addChangevalDivTel($this, name, fieldType);
        }
    } else if ($('.changeval[dataname="' + name + '"]').html() === undefined) {
        addChangevalDiv($this, name, fieldVal, clickEvent);
    }
    if (name !== undefined) {
        var $div = $('.changeval[dataname="' + name + '"]');
        $div.show();
        var offset = $this.find(".fieldShow").offset();
        var poffset = $this.parent().offset();
        $div.css({
            position: "absolute",
            top: offset.top,
            left: poffset.left,
            height: $this.outerHeight(true)
        });
        $div.data("top", offset.top);
        $div.data("msctop", parseInt($(".mCSB_container").css("top")));
        $div.unbind();
        $div.find("input").bind("keydown", function () {

        });
        $div.find(".ok").bind("click", function () {
            var dataname = $(this).parents(".changeval").attr("dataname");
            var dataVal = "";
            var htmlTestVal = "";
            var $contentObj = $(this).parent().find(".inputcontent");
            var $inputObj = $contentObj.children();
            var onblurFunctions = $inputObj.attr("onblurs");
            $inputObj.attr("onblur", onblurFunctions);
            var onblurFlag = true;
            if (onblurFunctions != null && onblurFunctions !== undefined && onblurFunctions != "") {
                $.each(onblurFunctions.split(";"), function (index, fun) {
                    if (fun != null && fun != "" && fun !== undefined) {
                        fun = fun.replace(/\s+/g, "");
                        if (fun.indexOf("(") != -1 && fun.indexOf(")") != -1) {
                            var funStr = fun.substr(0, fun.indexOf("("));
                            var funParms = fun.substr(fun.indexOf("(") + 1, fun.indexOf(")") - fun.indexOf("(") - 1);
                            if (funParms == "") {
                                onblurFlag = eval(funStr).call(this);
                            } else if (funParms == "this") {
                                onblurFlag = eval(funStr).call(this, $inputObj[0]);
                            }
                            if (!onblurFlag && onblurFlag != undefined) {
                                return false;
                            }
                        }
                    }
                });
            }
            $inputObj.attr("onblur", "");
            if (!onblurFlag && onblurFlag != undefined) {
                return false;
            }
            var inputType = $inputObj[0].type;
            var $radioObj;
            if (inputType == "text" || inputType == "textarea") {//文本 文本域
                dataVal = $contentObj.children().eq(0).val().trim();
                htmlTestVal = cutStrForLen(dataVal, cutoff);
                $this.find(".fieldReal").children().val(dataVal);
            } else if (inputType == "checkbox") {//复选
                $radioObj = $contentObj.find("input[type='checkbox']");
                var flag = 0;
                $radioObj.each(function (i, e) {
                    if ($(e).is(':checked')) {
                        if (flag++ != 0) {
                            htmlTestVal += "|";
                        }
                        dataVal += $(e).val().trim() + "|";
                        $this.find(".fieldReal").find("input[type='checkbox']").eq(i).attr("checked", "true");
                        htmlTestVal += $(e).attr("datavalue").trim();
                    } else {
                        $this.find(".fieldReal").find("input[type='checkbox']").eq(i).removeAttr("checked");
                    }
                });
            } else if (inputType == "radio") {//单选
                $radioObj = $contentObj.find("input[type='radio']");
                $radioObj.each(function (i, e) {
                    if ($(e).is(':checked')) {
                        dataVal += $(e).val().trim();
                        $this.find(".fieldReal").find("input[type='radio']").eq(i).attr("checked", "true");
                        htmlTestVal += $(e).attr("datavalue").trim();
                    } else {
                        $this.find(".fieldReal").find("input[type='radio']").eq(i).removeAttr("checked");
                    }
                });
            } else if (inputType == "select-one") {
                htmlTestVal = $contentObj.children().eq(0).find("option:selected").text();
                dataVal = $contentObj.children().eq(0).val();
                $this.find(".fieldReal").find("select").val(dataVal)
            }
            var jsonData = {};
            if ($.isFunction(window.dblUpdateVal)) {
                window.dblUpdateVal(dataname, jsonData);
            }
            jsonData[dataname] = dataVal;

            if (ajaxUpdateOne($(this), jsonData, primarykeys, formId, callback, htmlTestVal)) {
                //$('[name="'+dataname+'"]').parents(".font-smallup").find(".fieldShow").text(htmlTestVal);//一个页面有多个相同name时,无法区分
                $('table[title="' + formNo + '"]').find('[name="' + dataname + '"]').parents(".font-smallup").find(".fieldShow").text(htmlTestVal);
                $(this).parents(".changeval").remove();
                if (typeof(handleAnchorFun) === "function") {
                    handleAnchorFun($(".fieldReal [name='" + dataname + "']"));
                }
                if ($.isFunction(window.dblSuccessCallback)) {
                    window.dblSuccessCallback(dataname);
                }
            }
            addTextToNone($this.find(".fieldShow"));
        });

        $div.find(".telok").bind("click", function () {
            var dataname = $(this).parents(".changeval").attr("dataname");
            var dataVal = "";
            var htmlTestVal = "";
            var $contentObj = $(this).parent().find(".inputcontent");
            var jsonData = {};
            var telInputKeyVal = {};
            $contentObj.find("input").each(function (inputIndex, inputObj) {
                var $inputObj = $(inputObj);
                var inuputName = $inputObj.attr("name");
                var onblurFunctions = $inputObj.attr("onblurs");
                $inputObj.attr("onblur", onblurFunctions);
                var onblurFlag = true;
                if (onblurFunctions != null && onblurFunctions !== undefined && onblurFunctions != "") {
                    $.each(onblurFunctions.split(";"), function (index, fun) {
                        if (fun != null && fun != "" && fun !== undefined) {
                            fun = fun.replace(/\s+/g, "");
                            if (fun.indexOf("(") != -1 && fun.indexOf(")") != -1) {
                                var funStr = fun.substr(0, fun.indexOf("("));
                                var funParms = fun.substr(fun.indexOf("(") + 1, fun.indexOf(")") - fun.indexOf("(") - 1);
                                if (funParms == "") {
                                    onblurFlag = eval(funStr).call(this);
                                } else if (funParms == "this") {
                                    onblurFlag = eval(funStr).call(this, $inputObj[0]);
                                }
                                if (!onblurFlag && onblurFlag != undefined) {
                                    return false;
                                }
                            }
                        }
                    });
                }
                if (inputIndex > 0) {
                    dataVal += "-";
                    htmlTestVal += "-";
                }
                dataVal += $inputObj.val();
                htmlTestVal += $inputObj.val();
                jsonData[inuputName] = $inputObj.val();
                telInputKeyVal[inuputName] = $inputObj.val();
                $inputObj.attr("onblur", "");
                if (!onblurFlag && onblurFlag != undefined) {
                    return false;
                }
            });
            htmlTestVal = cutStrForLen(dataVal, cutoff);
            $this.find(".fieldReal").children().val(dataVal);
            if ($.isFunction(window.dblUpdateVal)) {
                window.dblUpdateVal(dataname, jsonData);
            }
            jsonData[dataname] = dataVal;
            if (ajaxUpdateOne($(this), jsonData, primarykeys, formNo, callback, htmlTestVal)) {
                $('table[title="' + formNo + '"]').find('[field-name="' + dataname + '"]').find(".fieldShow").text(htmlTestVal);
                $.each(telInputKeyVal, function (key, val) {
                    $this.find(".fieldReal").find("input[name=\"" + key + "\"]").val(val);
                });
                $(this).parents(".changeval").remove();
                if ($.isFunction(window.dblSuccessCallback)) {
                    window.dblSuccessCallback(dataname);
                }
            }
            addTextToNone($this.find(".fieldShow"));
        });

        $div.find(".no").bind("click", function () {
            $(this).parents(".changeval").remove();
        });
    } else {
        alert("文档结构错误，请检查!", 0);
    }
};
//});
//是否存在指定函数
function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch (e) {
    }
    return false;
}

//是否存在指定变量
function isExitsVariable(variableName) {
    try {
        if (typeof(variableName) == "undefined") {
            return false;
        } else {
            return true;
        }
    } catch (e) {
    }
    return false;
}

function cutStrForLen(str, len) {
    if (len !== undefined && len != null && len != "" && len > 0) {
        var returnStr = "";
        var array = str.split("");
        var countLen = 0;
        $.each(array, function (index, value) {
            countLen += getStrLen(value);
            if (countLen <= len) {
                returnStr += value;
            } else {
                returnStr += "...";
                return false;
            }
        });
        return returnStr;
    } else {
        return str;
    }
}

function getStrLen(str) {
    var byteLen = 0, len = str.length;
    if (str) {
        for (var i = 0; i < len; i++) {
            if (str.charCodeAt(i) > 255) {
                byteLen += 2;
            } else {
                byteLen++;
            }
        }
        return byteLen;
    } else {
        return 0;
    }
}