/**
 * Created by Administrator on 2015/6/16.
 */
define(function(require, exports, module){
    var commonFunction = require("./commonFunction.js");
    $(function () {
        //页面加载样式
        $("body").click(function (e) {
            if ($(e.target).parents(".a_select").length == 0) {
                $(".showSelect").hide();
            }
            if ($(e.target).parents(".icon_select").length == 0) {
                $(".showSelectIcon").hide();
            }
        });
        //定时器全局变量
        var intTimer;
        var times = "800";
        $(".a_up").click(function () {
            clearInterval(intTimer);
            var val = $(this).parents(".a_up_down").find("span").eq(0).find("input").val();
            if (val == "") {
                val = 0;
            } else {
                val = parseInt(val);
                val += 1;
            }
            if (val > 1000) {
                $.myAlert.Alert("已经达到上限,不能超过1000");
                val = 1000;
            }
            $(this).parents(".a_up_down").find("span").eq(0).find("input").val(val);
            intTimer = setInterval(function () {
                clearInterval(intTimer);
                formAttributeUpdate();//更新属性
            }, times);
        });
        $(".a_down").click(function () {
            clearInterval(intTimer);
            var val = $(this).parents(".a_up_down").find("span").eq(0).find("input").val();
            if (val == "") {
                val = 0;
            } else {
                val = parseInt(val);
                val -= 1;
            }
            if (val < 0) {
                val = 0;
            }
            $(this).parents(".a_up_down").find("span").eq(0).find("input").val(val);
            intTimer  = setInterval(function () {
                clearInterval(intTimer);
                formAttributeUpdate();//更新属性
            }, times);
        });
        $(".blur_readonly").focus(function () {
            $(this).blur();
        });
        $(".a_select_down").click(function () {
            var obj = $(this).parent(".a_select").find(".showSelect");
            if ($(obj).css("display") == "none") {
                var valStr = $(this).parent(".a_select").find("input").attr("val");
                if (valStr != undefined && valStr != "") {
                    var selects = obj.find("li");
                    for (var i = 0; i < selects.length; i++) {
                        if (selects.eq(i).attr("value") == valStr) {
                            selects.eq(i).addClass("pseudoSelected");
                        }
                    }
                }
                $(obj).show();
            } else {
                $(obj).hide();
            }
            var a_select = this;
            $(this).parent(".a_select").find(".showSelect").unbind("hover");
            $(this).parent(".a_select").find(".showSelect").hover(function () {
                $(this).find("li").removeClass("pseudoSelected");
            });
            $(this).parent(".a_select").find(".showSelect li").unbind("click");
            $(this).parent(".a_select").find(".showSelect li").click(function () {
                var thisVal = $(this).attr("value");
                var thisText = $.trim($(this).text());
                var oldVal = $(a_select).parent(".a_select").find(".blur_readonly").attr("val");
                var isDataType = $.trim($(a_select).parent(".a_select").find(".blur_readonly").attr("id"));
                $(a_select).parent(".a_select").find(".blur_readonly").val(thisText);
                $(a_select).parent(".a_select").find(".blur_readonly").attr("val", thisVal);
                if(isDataType=="dataType"){
                    var setUnit = "";
                    if(thisVal==12||thisVal==13||thisVal==14||thisVal==15||thisVal==16){//金额类型
                        setUnit = thisText.substr(thisText.indexOf("(")+1,thisText.indexOf(")")-thisText.indexOf("(")-1);
                    }else if(thisVal==17||thisVal==18||thisVal==19){//分笔
                        setUnit = thisText.substr(-1,1);
                    }
                    if(setUnit!==undefined&&setUnit!=""&&setUnit!=null){
                        $("#unit").val(setUnit);
                    }else if(oldVal==12||oldVal==13||oldVal==14||oldVal==15||
                        oldVal==16||oldVal==17||oldVal==18||oldVal==19){
                        $("#unit").val("");
                    }
                }else if(isDataType=="horVer"){
                	if(thisVal==1){//纵向选择
                		$("#labelAlign").attr("val","1");
                	}else if(thisVal==0){
                		$("#labelAlign").attr("val","3");
                	}
                }
                selectrollback($(this));
                $(this).parents(".showSelect").hide();
                attributeUpdateAll($(this), $(a_select).parent(".a_select").find(".blur_readonly"));//更新属性
            });
        });
        $(".selectIcon").click(function () {
            var obj = $(this).parent(".icon_select").find(".showSelectIcon");
            if ($(obj).css("display") == "none") {
                var valStr = $(this).parent(".icon_select").find(".show_icon_event").attr("val");
                if (valStr != undefined && valStr != "") {
                    var selects = obj.find("li");
                    for (var i = 0; i < selects.length; i++) {
                        if (selects.eq(i).attr("val") == valStr) {
                            selects.eq(i).addClass("pseudoSelected");
                        }
                    }
                }
                $(obj).show();
            } else {
                $(obj).hide();
            }
            var select = this;
            $(this).parents(".icon_select").find(".showSelectIcon").unbind("hover");
            $(this).parents(".icon_select").find(".showSelectIcon").hover(function () {
                $(this).find("li").removeClass("pseudoSelected");
            });
            $(this).parent(".icon_select").find(".showSelectIcon>li").unbind("click");
            $(this).parent(".icon_select").find(".showSelectIcon>li").click(function () {
                var showObj = $(select).parent(".icon_select").find(".selectIcon>.show_icon");
                var thisClassm = $(this).attr("classm");
                var showClassm = showObj.attr("classm");
                if (thisClassm != showClassm) {
                    $(showObj).attr("val", $.trim($(this).attr("val")));
                    $(showObj).removeClass($(showObj).attr("classm"));
                    $(showObj).addClass(thisClassm);
                    $(showObj).attr("classm", thisClassm);
                }
                $(this).parent(".showSelectIcon").hide();
                attributeUpdateAll_form_table();//更新属性
            });
        });
        //联动计算关闭
        $(".linkageCalculationColse").click(function () {
            hideCover("#linkageCalculation");
            clearDialogShow("#eventConfiguration");
        });
        $(".eventConfiguration_btn").click(function () {
            var formTableFlag = commonFunction.isFormOrTable();
            var fileId = commonFunction.getFileName(formTableFlag);
            var setProperties = $(".setProperties").attr("formactiveid");
            if(setProperties===undefined||setProperties==""){
                setProperties="";
            }else{
                setProperties = $.trim(setProperties).split("_")[1];
            }
            if(formTableFlag=="form"&&fileId!=false&&setProperties!=""){
                clearDialogShow("#eventConfiguration");
                showCover("#eventConfiguration");
            }else if(formTableFlag=="form"&&fileId==false){
                $.myAlert.Alert("请打开表单！");
            }else if(formTableFlag=="form"&&fileId!=false){
                $.myAlert.Alert("请打开字段！");
            }
        });
        $(".eventConfigurationColse").click(function () {
            hideCover("#eventConfiguration");
        });
        $(".eventConfigurations .event").click(function () {
            var thisVal = $(this).find(".event_name").attr("val");
            if (thisVal === undefined) {
                thisVal = "";
            }
            var roles = $(this).find(".event_name").attr("roles");
            assignmentDialogShow("#eventConfiguration", thisVal, roles);
            showCover("#eventConfiguration");
        });
        $("#eventConfigurationConfirm").click(function () {
            var textarea = $("#eventConfigurationShow").val();
            var valStr = $("#eventConfiguration .a_select input").attr("val");
            if (valStr === undefined || valStr == "") {
                $.myAlert.Alert("事件类型不可为空！");
            } else {
                var event_names = $(".eventConfigurations .event_name");
                if ($.trim(textarea) !== "") {
                    $(event_names).each(function (i) {
                        if (event_names.eq(i).attr("roles") == valStr) {
                            event_names.eq(i).attr("val", textarea);
                            event_names.eq(i).parent().show();
                            return false;
                        }
                    });
                } else {
                    $(event_names).each(function (i) {
                        if (event_names.eq(i).attr("roles") == valStr) {
                            event_names.eq(i).attr("val", textarea);
                            event_names.eq(i).parent().hide();
                            return false;
                        }
                    });
                }
                hideCover("#eventConfiguration");
                attributeUpdatechangeNameAll();//更新属性
            }
        });
        /**
         * 点击按钮事件绑定
         */
        $(".yes_no_btn").click(function () {
            var idvalue = $(this).attr("val");
            var choiceStr = $.trim($(this).attr("choice"));
            $(this).removeClass(choiceStr + "-unchoice").removeClass(choiceStr + "-choice");
            if (idvalue == 0) {
                $(this).addClass(choiceStr + "-choice");
                $(this).attr("val", 1);
            } else if (idvalue == 1) {
                $(this).addClass(choiceStr + "-unchoice");
                $(this).attr("val", 0);
            }
            formAttributeUpdate();//更新属性
        });
        /***
         * 回掉函数执行同步下拉与textera
         * @param obj
         */
        function selectrollback(obj){
            if ($(obj).parents("#eventConfiguration").length > 0) {//事件配置
                var thisVal = $(obj).attr("value");
                var event_names = $(".eventConfigurations .event_name");
                $(event_names).each(function (i) {
                    if (event_names.eq(i).attr("roles") == thisVal) {
                        $("#eventConfiguration textarea").val(event_names.eq(i).attr("val"));
                        return false;
                    }
                });
            }
        }

        /***
         * 清理弹出框的显示
         * @param idName
         */
        function clearDialogShow(idName) {
            $(idName).find("input").val("");
            $(idName).find("input").removeAttr("val");
            $(idName).find("textarea").val("");
        }

        /***
         * 像弹出框赋值
         * @param idName
         * @param val
         * @param text
         */
        function assignmentDialogShow(idName, value, roles) {
            var lis = $(idName).find(".showSelect li");
            $(lis).each(function (i) {
                if (lis.eq(i).attr("value") == roles) {
                    $(idName).find("input").val($.trim(lis.eq(i).text()));
                    return false;
                }
            });
            $(idName).find("input").attr("val", roles);
            $(idName).find("textarea").val(value);
        }

        /**
         * function:隐藏遮盖层
         * parameter:对象id
         */
        function hideCover(idStr) {
            $(idStr).modal("hide");
            $(".modal-backdrop").remove();
        }

        /**
         * function:显示遮盖层
         * parameter:对象id
         */
        function showCover(idStr) {
            $(idStr).modal({
                backdrop: false,
                show: true,
                keyboard: false
            });
            $("body").append("<div class='modal-backdrop fade in'></div>");
        }

        $("#labelName,#tableLabel").dblclick(function () {
            $(this).hide();
            $(".span_lableName input").val($.trim($(this).text()));
            $(".span_lableName").show();
            $(".span_lableName input").focus();
        });
        $("#fieldName").dblclick(function () {
            $(this).hide();
            $(".span_filedName input").val($.trim($(this).text()));
            $(".span_filedName").show();
            $(".span_filedName input").focus();
        });
        $("#tableLabel").dblclick(function () {
            $(this).hide();
            $(".span_lableName_table input").val($.trim($(this).text()));
            $(".span_lableName_table").show();
            $(".span_lableName_table input").focus();
        });
        $(".span_lableName_table").focusout(function () {
            $(this).hide();
            $("#tableLabel").show();
            if ($.trim($("#tableLabel").text()) != $(".span_lableName_table input").val() && $(".span_lableName_table input").val() != "") {
                $("#tableLabel").text($(".span_lableName_table input").val());
                attributeUpdatechangeNameAll();//更新属性
            }
        });
        $(".span_lableName_table").keydown(function (e) {
            switch (e.keyCode) {
                case 13:
                {
                    $(".span_lableName_table").hide();
                    $("#tableLabel").show();
                    if ($.trim($("#tableLabel").text()) != $(".span_lableName_table input").val() && $(".span_lableName_table input").val() != "") {
                        $("#tableLabel").text($(".span_lableName_table input").val());
                        attributeUpdatechangeNameAll();//更新属性
                    }
                }
                    ;
            }
        });
        $(".span_lableName").focusout(function () {
            $(".span_lableName").hide();
            $("#labelName").show();
            if ($.trim($("#labelName").text()) != $(".span_lableName input").val() && $(".span_lableName input").val() != "") {
                $("#labelName").text($(".span_lableName input").val());
                attributeUpdatechangeNameAll();//更新属性
            }
        });
        $(".span_filedName").focusout(function () {
            $(".span_filedName").hide();
            $("#fieldName").show();
            if ($.trim($("#fieldName").text()) != $(".span_filedName input").val() && $(".span_filedName input").val() != "") {
                $("#fieldName").text($(".span_filedName input").val());
                attributeUpdatechangeNameAll();//更新属性
            }
        });
        $(".span_lableName").keydown(function (e) {
            switch (e.keyCode) {
                case 13:
                {
                    $(".span_lableName").hide();
                    $("#labelName").show();
                    if ($.trim($("#labelName").text()) != $(".span_lableName input").val() && $(".span_lableName input").val() != "") {
                        $("#labelName").text($(".span_lableName input").val());
                        attributeUpdatechangeNameAll();//更新属性
                    }
                }
                    ;
            }
        });
        $(".span_filedName").keydown(function (e) {
            switch (e.keyCode) {
                case 13:
                {
                    $(".span_filedName").hide();
                    $("#fieldName").show();
                    if ($.trim($("#fieldName").text()) != $(".span_filedName input").val() && $(".span_filedName input").val() != "") {
                        $("#fieldName").text($(".span_filedName input").val());
                        attributeUpdatechangeNameAll();//更新属性
                    }
                }
                    ;
            }
        });
        $(".tableShow").bind('mousewheel', function(event, delta) {
            var dir = delta > 0 ? 'Up' : 'Down',
                vel = Math.abs(delta);
            var topval = $(this).scrollTop();
            $(this).scrollTop(topval-delta*50);
            return false;
        });
        /**
         * 更新字段所有属性
         * fileType:form/table
         */
        window.attributeUpdateAll = function (obj,inputObj) {
            var fileType = commonFunction.isFormOrTable();
            if (fileType == "form") {
                if($(inputObj).attr("id")!==undefined){
                    formAttributeUpdate();
                }
            } else if (fileType == "table") {
                updateAttributeDesTable();
            }
        }
        window.attributeUpdatechangeNameAll = function (obj,inputObj) {
            var fileType = commonFunction.isFormOrTable();
            if (fileType == "form") {
                    formAttributeUpdate();
            } else if (fileType == "table") {
                updateAttributeDesTable();
            }
        }
        window.attributeUpdateAll_form_table = function () {
            var fileType = commonFunction.isFormOrTable();
            if (fileType == "form") {
                    formAttributeUpdate();
            }else if (fileType == "table") {
                updateAttributeDesTable();
            }
        }
        //取消更新表单列表名称和ID
        window.cancelFormIdTableIdUpdate = function(){
        	$(".span_lableName").hide();
            $("#labelName").show();
            if ($(".span_lableName input").val() != "") {
                $("#labelName").text($(".span_lableName input").val());
            }
        	$(".span_filedName").hide();
            $("#fieldName").show();
            if ($(".span_filedName input").val() != "") {
                $("#fieldName").text($(".span_filedName input").val());
            }
        }
    });
});