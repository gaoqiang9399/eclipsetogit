define(function(require, exports, module) {

    var commonFunction = require("../../common/js/commonFunction.js");
    //form属性设置模块
    var formDataCtrl = require("./formDataCtrl.js");
    var utilJs = require("../../common/js/commonUtil.js");;
    var tablePosition =  require("./tablePosition.js");
    //控制台颜色的全局class数组
    //var consoleBorder = ["border-left-blue","border-left-light-blue","border-left-orange","border-left-light-orange","border-left-purple","border-left-light-purple","border-left-pink","border-left-light-pink"];
    var consoleBorder = ["border-left-green"];
    var consoleBorder1 = ["border-left-red"];
    var consoleBorder2 = ["border-left-yellow"];

    /*
    * 删除单个隐藏域
    */
    window.consoleDelete = function(el){
        //$.myAlert.Confirm("确认删除该字段属性吗？","删除提示",function(){consoleDeleteConfirm(el);});
    	//$.myAlert.Alert("非开发人员不可删除隐藏字段！");
    }
    /**
     * function:恢复
     */
    window.reserveAttrCurrentField = function(e){
    	 var item = $(e).parent();
    	 var formActiveId = $(item).attr("id").split("_")[1];
         var formId = commonFunction.getFileName("form");
        $.myAlert.Choose("是否恢复该备用字段！","提示信息",["恢复"],
            [function(){
                $.post("./FormDataForToolBean/reserveAttrRecoveryFormElement",
                {formId: formId, formActiveId: formActiveId},
                function (data) {//返回结果
                    if ($.trim(data) == "0000") {
                        initframe();
                    } else {
                        $.myAlert.Alert(data);
                    }
                }, "html");}
            ]);
    }
    function consoleDeleteConfirm(el){
        var item = $(el).parent();
        var formActiveId = $(item).attr("id").split("_")[1];
        var formId = commonFunction.getFileName("form");
        if(item.hasClass("slideInLeft")){
            item.removeClass("slideInLeft");
            item.removeClass("animatedQuick");
        }
        item.addClass('slideOutRight').addClass('animated').delay(400).queue(function (next) {
            $(this).remove();
            formDataCtrl.AttributeDel(formId,formActiveId);
            next();
        });
    }
    /*
    * 添加单个隐藏域
    */
    window.consoleAdd = function(){
        var formId = commonFunction.getFileName("form");
        if(formId!=null&&formId!=""&&formId!==undefined){
            //首先判断当前控制台是否打开
            var consoleBox = $(".console-box");
            if(consoleBox.hasClass("show")){
                appendConsole();
            }else{
                var $icon = $(".spin-icon").find("i");
                if($icon.hasClass("animated")){
                    $icon.removeClass("animated").addClass("animated-hover");
                }
                consoleBox.find(".spin-icon").addClass("spin-icon-show");
                consoleBox.addClass("show");
                setTimeout("appendConsole()",800)
            }
        }else{
            $.myAlert.Alert("请打开表单！");
        }
    }
    /*
    * 拼接单个控制台的html
    */
    window.appendConsole = function(){
        /*从数组中随即获取一种样式*/
        var consoleBorderStyle = randomColor();
        var  todoList = $('.console-setting .todo-list');
        todoList.append('<li class="'+consoleBorderStyle+' animatedQuick slideInLeft">' +
        '<label class="text">' + "隐藏域:labelName(fieldName)" + '</label>' +
        '<div class="tools" onclick="consoleDelete(this)">' +
        '<b sign="trash" class="cC0 nui-ico nui-ico-smartTrash">' +
        '<b class="nui-ico nui-ico-smartTrash-head"></b>' +
        '<b class="nui-ico nui-ico-smartTrash-body"></b>' +
        '</b>' +
        '</div>' +
        '</li>');
        var formId = commonFunction.getFileName("form");
        var pRow = 1;
        var pCol = 1;
        var pFieldType = "99";
        var dataType = "0";
        var pSelRange = "0";
        var labelColSpan = "1";
        var targetName = "";
        tablePosition.dragAddPosition(formId,pRow,pCol,pFieldType,dataType,pSelRange,labelColSpan,targetName);
        consoleDoubleClick(".console-setting li");
    }
    /**
     * function:获取随机颜色
     * return:返回颜色 class
     */
    function randomColor(hiddenOrError){
    	var consoleBorderStyle = "";
        if(hiddenOrError=="error"){
            consoleBorderStyle = consoleBorder1[Math.floor(Math.random()*(consoleBorder1.length - 1))];
        }else if(hiddenOrError=="spare"){
            consoleBorderStyle = consoleBorder2[Math.floor(Math.random()*(consoleBorder.length - 1))];
        }else{
            consoleBorderStyle = consoleBorder[Math.floor(Math.random()*(consoleBorder.length - 1))];
        }
        return consoleBorderStyle;
    }
    /**
     * function:初始化控制台
     * parameter:formId(表单编号)
     */
    exports.initConsole = function(formId){
        $.post("FormDataForToolBean/getFormSpecialElement",{formId: formId},
            function(data){
                if(data!=""){
                    var errorCount = utilJs.getStrInStrLen(data,"consoleBorderStyleError");
                    var hideCount = utilJs.getStrInStrLen(data,"consoleBorderStyle");
                    var spareCount = utilJs.getStrInStrLen(data,"consoleBorderStyleSpare");
                    var consoleBorderStyle;
                    while(errorCount>0){
                        consoleBorderStyle = randomColor("error");
                        data = data.replace("consoleBorderStyleError",consoleBorderStyle);
                        errorCount--;
                    }
                    while(spareCount>0){
                        consoleBorderStyle = randomColor("spare");
                        data = data.replace("consoleBorderStyleSpare",consoleBorderStyle);
                        spareCount--;
                    }
                    while(hideCount>0){
                        consoleBorderStyle = randomColor();
                        data = data.replace("consoleBorderStyle",consoleBorderStyle);
                        hideCount--;
                    }
                    var $data = $("<lu>"+data+"</lu>");
                    $data.find(".border-left-red,.border-left-green").remove();
                    $data.find("li .tools").attr("onclick","reserveAttrCurrentField(this)");
                    $data.find("li .tools").children().attr("class","fa fa-mail-reply");
                    $data.find("li .tools").children().css({"color":"#015ab8","margin-top":"-10px"});
                    $data.find("li .tools").children().children().remove();
                    $(".console .console-setting").find("ul").html($data.html());
                    consoleDoubleClick();
                }else if($.trim(data)==""){
                    $(".console .console-setting").find("ul").html("");
                }
                //if($.trim(data)==""){
                //    $(".spin-icon").find("li").removeClass("animated").addClass("animated-hover");
                //}
            }, "html");
    }
    /**
     * function:控制在双击事件绑定
     * parameter:双击对象
     */
    function consoleDoubleClick(){
        $(".console-setting li").off("dblclick");//解绑双击事件
        $(".console-setting li").on("dblclick",function(e){
            var objId = $(this).attr("id");
            var formId = commonFunction.getFileName("form");
            var formActiveId = $.trim(objId.split("_")[1]);
            setAttrbuteId(formActiveId);
            if(formId!=false){
                formDataCtrl.AttributeEvaluation(formId,formActiveId);
                $(".console .console-box").removeClass("show");
                $(".console .console-box").find(".spin-icon").removeClass("spin-icon-show");
            }
            $("#delColCtrl").css("display","none");
        });
    }
    $(function(){
        /*判断监控台是否有内容，  如果有内容则让图标闪动*/
        var $todoList =  $(".todo-list li");
        if($todoList.length != 0){
            $todoList.parents(".console-box").find(".spin-icon i").removeClass("animated-hover").addClass("animated");
        }
        /*监控按钮的事件绑定（那个眼睛的图标）*/
        $(".spin-icon").addClass("animated");
        $(".spin-icon").click(function(){
            var $icon = $(this).find("i");
            if($icon.hasClass("animated")){
                $icon.removeClass("animated").addClass("animated-hover");
            }
            var $consoleBox = $(this).parent(".console-box");
            if($consoleBox.hasClass("show")){
                $consoleBox.removeClass("show");
                $(this).removeClass("spin-icon-show");
            }else{
                $consoleBox.addClass("show");
                $(this).addClass("spin-icon-show");
            }
        });
        $(".console-setting .todo-list").css("max-height",$(window).height()*0.9+"px");
    })
});

