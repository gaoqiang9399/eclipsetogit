/**
 * Created by jzh on 2015/2/7.
 */
define(function(require, exports, module) {
    var commonFunction = require("./commonFunction.js");
    var contextpath = $("#contextpath").val();
    require("customScrollbar/js/jquery.mCustomScrollbar.js");
    require("customScrollbar/js/jquery.mousewheel.min.js");
    exports.getFormOrTable = function(){
        var returnStr="";
        var formFlag = $("#form-search").css("display");
        var tableFlag = $("#table-search").css("display");
        if(formFlag=="block"&&tableFlag=="none"){
            returnStr = "form";
        }else if(tableFlag=="block"&&formFlag=="none"){
            returnStr = "table";
        }
        return returnStr;
    }
    /**
     * function:隐藏遮盖层
     * parameter:对象id
     */
    function hideCover(idStr){
        $(idStr).modal("hide");
        $(".modal-backdrop").remove();
    }
    /**
     * function:显示遮盖层
     * parameter:对象id
     */
    function showCover(idStr){
        $(idStr).modal({
            backdrop:false,
            show:true,
            keyboard: false
        });
        $("body").append("<div class='modal-backdrop fade in'></div>");
    }
    /*初始化form*/
    function loadFormData(){
        $.post(contextpath+"/tech/dragDesginer/FormForToolBean/loadFormData", function(data){
            //-------------------更改form中的节点
            var formSearchUl = $(".form-search-ul");
            formSearchUl.empty();
            var dataForm = data.form;
            for(var i=0; i<dataForm.length;i++){
                formSearchUl.append("<li  onclick='searchClickLi(this)'><div class='dragFormName'>"+dataForm[i].dragFormName+"</div><div class='dragFormDesc'>"+dataForm[i].dragFormDesc+"</div></li>")
            }
            //查询的配置
            var formSearchOsptions = {
                valueNames: [ 'dragFormName','dragFormDesc' ]
            };
            var formList = new List('form-search', formSearchOsptions);
        },"json");

    }
    /*初始化tabel*/
    function loadTableData(){
        $.post(contextpath+"/tech/dragDesginer/TableForToolBean/loadTableData", function(data){
            //------------------再更改table中的节点
            var tableSearchUl = $(".table-search-ul");
            tableSearchUl.empty();
            var dataTable = data.table;
            for(var i=0; i<dataTable.length;i++){
                tableSearchUl.append("<li onclick='searchClickLi(this)'><div class='dragTableName'>"+dataTable[i].dragTableName+"</div><div class='dragTableDesc'>"+dataTable[i].dragTableDesc+"</div></li>")
            }
            //查询的配置
            var tableSearchOsptions = {
                valueNames: [ 'dragTableName','dragTableDesc' ]
            };
            var tableList = new List('table-search', tableSearchOsptions);
        },"json");
    }
    /**
     * function:校验新增确定
     */
    function validateAddDescription(objStr){
        var returnStr = "";
        if(objStr=="form"){
            var formId = $.trim($("#addFormId").val());
            var title = $.trim($("#addTitle").val());
            if(formId==""){
                returnStr+="表单号不能为空！";
            }
            if(title==""){
                returnStr+="表单名称不能为空！";
            }
        }else if(objStr=="table"){
            var tableId = $.trim($("#addTableId").val());
            var name = $.trim($("#addTableName").val());
            if(tableId==""){
                returnStr+="列表编号不能为空！";
            }
            if(name==""){
                returnStr+="列表名称不能为空！";
            }
        }
        return returnStr;
    }
    /**
     * function:校验新增确定
     */
    function validateModifyDescription(objStr){
        var returnStr = "";
        if(objStr=="form"){
            var title = $.trim($("#title").val());
            if(title==""){
                returnStr+="表单名称不能为空！";
            }
        }else if(objStr=="table"){
            var name = $.trim($("#modifyTableName").val());
            if(name==""){
                returnStr+="列表名称不能为空！";
            }
        }
        return returnStr;
    }

    function validateCopyDescription(objStr){
        var returnStr = "";
        if(objStr=="form"){
            var formId = $.trim($("#newFormId").val());
            var title = $.trim($("#newTitle").val());
            if(formId==""){
                returnStr+="新表单号不能为空！";
            }
            if(title==""){
                returnStr+="表单名称不能为空！";
            }
        }else if(objStr=="table"){
            var tableId = $.trim($("#newTableId").val());
            var name = $.trim($("#newName").val());
            if(tableId==""){
                returnStr+="新表单号不能为空！";
            }
            if(name==""){
                returnStr+="列表名称不能为空！";
            }
        }
        return returnStr;
    }
    //页面初始化
    $(function(){
        $("#validateSettingSelect .showSelect").mCustomScrollbar({
            theme:"dark",
            scrollButtons:{
                enable:false,
                autoHideScrollbar: true
            },
            advanced:{
                updateOnBrowserResize:true,
                updateOnContentResize:true
            }
        });
        /**
         * function:新增事件绑定
         */
        $("#addDescription").click(function(){
            var formTableFlag = commonFunction.isFormOrTable();
            if(formTableFlag=="form"){
                showCover("#addDes");
            }else if(formTableFlag=="table"){
                showCover("#addTableDes");
            }else{
                $.myAlert.Alert("新增无效,请切换到表单或列表模式下!");
            }
        });
        /**
         * function:新增form确定
         */
        $("#addDesConfirm").click(function(){
            var flag = validateAddDescription("form");
            if(flag==""){
                var formId = $("#addFormId").val();
                var formTitleShowFlag = false;
                var cssFlag =  $("#addFormTitleShowFlag").parents(".bootstrap-switch-container").css("margin-left");
                if(cssFlag=="0px"){
                    formTitleShowFlag = true;
                }
                var title = $("#addTitle").val();
                $.post(contextpath+"/tech/dragDesginer/FormForToolBean/insertForm",
                    {formId:formId,formTitleShowFlag:formTitleShowFlag,title:title},
                    function(data){
                        if($.trim(data)=="0000"){
                            loadFormData();
                            commonFunction.changFilename("form",formId,title);
                            $(".formInfor").hide();
                            //打开form/table infor
                            $(".formInfor").fadeIn(600);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    },"html");
                hideCover("#addDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        /**
         * function:新增table确定
         */
        $("#addTableDesConfirm").click(function(){
            var flag = validateAddDescription("table");
            if(flag=="") {
                var tableId = $("#addTableId").val();
                var exportExcel = "0";
                var cssFlag = $("#addTableExportExcel").parents(".bootstrap-switch-container").css("margin-left");
                if (cssFlag == "0px") {
                    exportExcel = "1";
                }
                var name = $("#addTableName").val();
                var type = commonFunction.getSelected("addTableType");
                var pageLink = $("#addTablePara").val();
                var para = "eadis_page";
                var trClick = $("#addTableTrClick").val();
                var trDbClick = $("#addTableTrDbClick").val();
                var colorCol = $("#addTableTrColorCol").val();
                //alert("tableId:"+tableId+"-exportExcel:"+exportExcel+"-name:"+name+"-type:"+type+"-pageLink："+pageLink);
                $.post(contextpath + "/tech/dragDesginer/TableForToolBean/insertTable",
                    {
                        tableId: tableId,
                        exportExcel: exportExcel,
                        name: name,
                        type: type,
                        pageLink: pageLink,
                        para: para,
                        trClick:trClick,
                        trDbClick:trDbClick,
                        colorCol:colorCol
                    },
                    function (data) {
                        if ($.trim(data) == "0000") {
                            loadTableData();
                            commonFunction.changFilename("table",tableId,name);
                            $(".formInfor").hide();
                            //打开form/table infor
                            $(".formInfor").fadeIn(600);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    }, "html");
                hideCover("#addTableDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        /**
         * function:新增关闭
         */
        $(".addDesColse").click(function(){
            hideCover("#addDes");
            hideCover("#addTableDes");
        });
        /**
         * function:修改描述点击事件绑定事件
         */
        $("#ModifyDescription").click(function(){
            var formTableFlag = commonFunction.isFormOrTable();
            var fileId = commonFunction.getFileName(formTableFlag);
            if(fileId!=false&&formTableFlag=="form"){
                formModifyDes(fileId);
            }else if(fileId!=false&&formTableFlag=="table"){
                tableModifyDes(fileId);
            }else{
                if(formTableFlag=="form"){
                    $.myAlert.Alert("修改无效,请打开表单！");
                }else if(formTableFlag=="table"){
                    $.myAlert.Alert("修改无效,请打开列表！");
                }else{
                    $.myAlert.Alert("请打开表单或列表！");
                }
            }
        });
        //form修改描述
        function formModifyDes(formId){
            $.post(contextpath+"/tech/dragDesginer/FormForToolBean/initForm",{formId:formId},function(data){
                var formIdTmp = data.fromId;
                var formTitleShowFlag = data.titleShowFlag;
                var title =  data.title;
                $("#formId").val(formIdTmp);
                var titleShowFlag =  $("#titleShowFlag").parents(".bootstrap-switch-container");
                var titleShowFlagP =  $("#titleShowFlag").parents(".bootstrap-switch");
                if(formTitleShowFlag=="true"){
                    titleShowFlagP.removeClass("bootstrap-switch-off").addClass("bootstrap-switch-on");
                    titleShowFlag.css({"margin-left":"0px"});
                    titleShowFlag.find(".bootstrap-switch-label").text("否");
                }else{
                    titleShowFlagP.removeClass("bootstrap-switch-on").addClass("bootstrap-switch-off");
                    titleShowFlag.css({"margin-left":"-38px"});
                    titleShowFlag.find(".bootstrap-switch-label").text("是");
                }
                $("#title").val(title);
                $("#ModifyDes").modal({
                    backdrop:false,
                    show:true,
                    keyboard: false
                });
                $("body").append("<div class='modal-backdrop fade in'></div>");
                //commonFunction.reloadFormOrTable();
            },"json");
        }
        //table修改描述
        function tableModifyDes(tableId){
            $.post(contextpath+"/tech/dragDesginer/TableForToolBean/getTableSelfElement",{tableId:tableId},function(data){
                var name = data.name;
                var type =  data.type;
                var exportExcel = data.exportExcel;
                var pageLink =  data.pageLink;
                var trClick = data.trClick;
                var trDbClick = data.trDbClick;
                var colorCol = data.colorCol;
                $("#modifyTableId").val(tableId);
                $("#modifyTableName").val(name);
                $("#modifyTablePageLink").val(changeNull(pageLink));
                $("#modifyTableTrClick").val(changeNull(trClick));
                $("#modifyTableTrDbClick").val(changeNull(trDbClick));
                $("#modifyTableTrColorCol").val(changeNull(colorCol));
                setSelected("modifyTableType",type);
                if(exportExcel=="1"){
                    $("#modifyTableExportExcel").parents(".bootstrap-switch").removeClass("bootstrap-switch-off").addClass("bootstrap-switch-on");
                    $("#modifyTableExportExcel").parents(".bootstrap-switch-container").css({"margin-left":"0px"});
                }else{
                    $("#modifyTableExportExcel").parents(".bootstrap-switch").removeClass("bootstrap-switch-on").addClass("bootstrap-switch-off");
                    $("#modifyTableExportExcel").parents(".bootstrap-switch-container").css({"margin-left":"-38px"});
                }
                $("#modifyTableDes").modal({
                    backdrop:false,
                    show:true,
                    keyboard: false
                });
                $("body").append("<div class='modal-backdrop fade in'></div>");
            },"json");
        }
        /**
         * 修改描述确认
         */
        $("#modifyDesConfirm").click(function() {
            var flag = validateModifyDescription("form");
            if(flag=="") {
                var formId = commonFunction.getFileName("form");
                var formTitleShowFlag = false;
                var cssFlag = $("#titleShowFlag").parents(".bootstrap-switch-container").css("margin-left");
                if (cssFlag == "0px") {
                    formTitleShowFlag = true;
                }
                var title = $("#title").val();
                //alert("formId:"+formId+"formTitleShowFlag:"+formTitleShowFlag+",title:"+title);
                $.post(contextpath + "/tech/dragDesginer/FormForToolBean/updateForm",
                    {formId: formId, formTitleShowFlag: formTitleShowFlag, title: title},
                    function (data) {
                        if ($.trim(data) == "0000") {
                            commonFunction.changFilename("modify",false,title);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    }, "html");
                //隐藏弹出框
                hideCover("#ModifyDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        $("#modifyTableDesConfirm").click(function() {
            var flag = validateModifyDescription("table");
            if(flag=="") {
                var tableId = commonFunction.getFileName("table");
                var name = $("#modifyTableName").val();
                var type = getSelected("modifyTableType");
                var exportExcel = "0";
                var cssFlag = $("#modifyTableExportExcel").parents(".bootstrap-switch-container").css("margin-left");
                if (cssFlag == "0px") {
                    exportExcel = "1";
                }
                var pageLink = $("#modifyTablePageLink").val();
                var trClick = $("#modifyTableTrClick").val();
                var trDbClick = $("#modifyTableTrDbClick").val();
                var colorCol = $("#modifyTableTrColorCol").val();
                //alert("name:"+name+",type"+type+",exportExcel:"+exportExcel+",pageLink:"+pageLink);
                $.post(contextpath + "/tech/dragDesginer/TableForToolBean/updateTable",
                    {tableId: tableId, name: name, type: type, exportExcel: exportExcel, pageLink: pageLink,trClick:trClick,trDbClick:trDbClick,colorCol:colorCol},
                    function (data) {
                        if ($.trim(data) == "0000") {
                            $("#newTableId").val("");
                            $("#newName").val("");
                            $("#newPageLink").val("");
                            $("#newPageTrClick").val("");
                            $("#newPageTrDbClick").val("");
                            setSelected("newTableType", "1");
                            commonFunction.changFilename("modify",false,name);
                            $("#newPageTrColorCol").val(colorCol);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    }, "html");
                //隐藏弹出框
                hideCover("#modifyTableDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        /**
         * 修改描述取消
         */
        $(".modifyDesColse").click(function (){
            hideCover("#ModifyDes");
            hideCover("#modifyTableDes");
        });

        /**
         * 删除表单事件绑定
         */
        $("#delFile").click(function(){
            var flag = exports.getFormOrTable();
            var fileName =  commonFunction.getFileName("");
            var showStr;
            if(flag=="form"&&fileName!=false){//form
                var formId = commonFunction.getFileName("form");
                showStr = " 是否确定删除 :<span style='color:red'>form"+formId+"</span>这个表单?";
                $.myAlert.Confirm(showStr,"提示信息",function(){delFileConfirm()});
            }else if(flag=="table"&&fileName!=false){//table
                var tableId = commonFunction.getFileName("table");
                showStr = " 是否确定删除 :<span style='color:red'>table"+tableId+"</span>这个列表?";
                $.myAlert.Confirm(showStr,"提示信息",function(){delFileConfirm()});
            }else{
                if(flag=="form"){
                    $.myAlert.Alert("删除无效,请打开表单！");
                }else if(flag=="table"){
                    $.myAlert.Alert("删除无效,请打开列表！");
                }else{
                    $.myAlert.Alert("删除无效,请打开表单或列表！");
                }
            }
        });
        //删除确定
        function delFileConfirm(){
            var flag = exports.getFormOrTable();
            if(flag=="form"){//form
                var formId = commonFunction.getFileName("form");
                $.post(contextpath+"/tech/dragDesginer/FormForToolBean/deleteForm",{formId:formId},
                    function(data){
                        if($.trim(data)=="0000"){
                            hideCover("#delFileShow");
                            loadFormData();
                            $(".formInfor").hide();
                            commonFunction.changFilename("","","");
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                },"html");
            }else if(flag=="table"){//table
                var tableId = commonFunction.getFileName("table");
                $.post(contextpath+"/tech/dragDesginer/TableForToolBean/deleteTable",{tableId:tableId},
                    function(data){
                        if($.trim(data)=="0000"){
                            hideCover("#delFileShow");
                            loadTableData();
                            $(".formInfor").hide();
                            commonFunction.changFilename("","","");
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    },"html");
            }
        }
        /***文件复制***/
        $("#copyFile").click(function(){
            var flag = exports.getFormOrTable();
            var fileName =  commonFunction.getFileName("");
            if(flag=="form"&&fileName!=false){
                var formId = commonFunction.getFileName("form");
                $("#oldFormId").val(formId);
                showCover("#copyFileDes");
            }else if(flag=="table"&&fileName!=false){
                var tableId = commonFunction.getFileName("table");
                $("#oldTableId").val(tableId);
                showCover("#copyTableFileDes");
            }else{
                if(flag=="form"){
                    $.myAlert.Alert("复制无效,请打开表单！");
                }else if(flag=="table"){
                    $.myAlert.Alert("复制无效,请打开列表！");
                }else{
                    $.myAlert.Alert("复制无效,请打开表单或列表！");
                }
            }
        });
        //复制确定
        $("#copyFileDesConfirm").click(function(){
            var flag = validateCopyDescription("form");
            if(flag=="") {
                var copyFormId = commonFunction.getFileName("form");
                var formId = $("#newFormId").val();
                var formTitleShowFlag = false;
                if ($("#newTitleShowFlag").parents(".switch").find(".bootstrap-switch-on").length > 0) {
                    formTitleShowFlag = true;
                }
                var title = $("#newTitle").val();
                $.post(contextpath + "/tech/dragDesginer/FormForToolBean/copyForm",
                    {formId: formId, copyFormId: copyFormId, formTitleShowFlag: formTitleShowFlag, title: title},
                    function (data) {
                        hideCover("#copyFileDes");
                        loadFormData();
                        if ($.trim(data) == "0000") {
                            $("#newFormId").val("");
                            $("#newTitle").val("");
                            $("#titleShowFlag").parents(".bootstrap-switch").removeClass("bootstrap-switch-off").addClass("bootstrap-switch-on");
                            $("#titleShowFlag").parents(".bootstrap-switch-container").css({"margin-left": "0px"});
                            commonFunction.changFilename("form",formId,title);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    }, "html");
                hideCover("#copyFileDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        //复制确定
        $("#copyTableFileDesConfirm").click(function() {
            var flag = validateCopyDescription("table");
            if(flag=="") {
                var copyTableId = commonFunction.getFileName("table");
                var tableId = $("#newTableId").val();
                var name = $("#newName").val();
                var type = getSelected("newTableType");
                var pageLink = $("#newPageLink").val();
                var para = "eadis_page";
                var exportExcel = "0";
                var trClick =  $("#newPageTrClick").val();
                var trDbClick =  $("#newPageTrDbClick").val();
                $.post(contextpath + "/tech/dragDesginer/TableForToolBean/copyTable",
                    {
                        tableId: tableId,
                        copyTableId: copyTableId,
                        name: name,
                        type: type,
                        pageLink: pageLink,
                        para: para,
                        exportExcel: exportExcel,
                        trClick:trClick
                    },
                    function (data) {
                        hideCover("#copyFileDes");
                        loadTableData();
                        if ($.trim(data) == "0000") {
                            $("#newTableId").val("");
                            $("#newName").val("");
                            $("#newPageLink").val("");
                            $("#newPageTrClick").val("");
                            $("#newPageTrDbClick").val("");
                            setSelected("newTableType", "1");
                            commonFunction.changFilename("table",tableId,name);
                            commonFunction.reloadFormOrTable();
                        }else{
                            $.myAlert.Alert($.trim(data));
                        }
                    }, "html");
                hideCover("#copyTableFileDes");
            }else{
                $.myAlert.Alert(flag);
            }
        });
        //复制取消
        $(".copyFileDesColse").click(function(){
            hideCover("#copyFileDes");
            hideCover("#copyTableFileDes");
        });
        //同步缓存事件绑定
        $("#refreshCache").click(function(){
            var flag = exports.getFormOrTable();
                $.myAlert.Confirm("是否确定同步("+flag+")缓存?", "提示信息", function () {
                    refreshCacheDesConfirm()
                });
        });
        //同步缓存确认
        function refreshCacheDesConfirm(){
            var divWidth = $(document).width()/2-60;
            var divHeght = $(document).height()/2;
            var showStr = '<div class="lodding" style="margin-top:'+divHeght+'px;margin-left:'+divWidth+'px;">'+
                '<img style="float: left" src="./app/common/img/lodding.gif"><div style="float: left">'+
                '<font size="5">正在同步. . .</font></div></div>';
            $("body").append('<div class="modal-backdrop fade in">'+showStr+'</div>');
            var flag = exports.getFormOrTable();
            if(flag=="form"){
                $.post(contextpath + "/tech/dragDesginer/FormForToolBean/reloadFormXmlInCache",{},
                    function(data){
                        hideCover("#lodding");
                        reloadFormOrTableSearch("form");
                    });
            }else if(flag=="table"){
                $.post(contextpath + "/tech/dragDesginer/TableForToolBean/reloadTableXmlInCache",{},
                    function(data){
                        hideCover("#lodding");
                        reloadFormOrTableSearch("table");
                    });
            }
        }
    });
    //校验公式事件绑定
    $(".formula_validate").click(function(){
        var flag = exports.getFormOrTable();
        var fileName =  commonFunction.getFileName("");
        var actionFlag = $("#actionFlag");
        if(actionFlag.html()!==undefined){
            flag = "form";
        }
        if(flag=="form"&&fileName!=false){
            $("#validateSettingSelect input").val("");
            $("#validateSettingSelect input").attr("calculationShow","");
            commonFunction.initInsertValues("validateSettingSelect");
            commonFunction.setValidteVal();
            showCover("#validateSetting");
        }else{
            $.myAlert.Alert("校验公式打开无效,请打开表单!");
        }

    });
    $(".validateSettingColse").click(function(){
        hideCover("#validateSetting");
    });
    //校验公式确定时间绑定
    $("#validateSettingConfirm").click(function(){
        var formId = commonFunction.getFileName("form");
        var formulavalidate= $("#formulaCheckShow").val();
        var formulavalidateShow= $("#formulaCheckShow").attr("calculationShow");
        $.post("./FormDataForToolBean/saveFormulavalidate",
            {formId:formId,formulavalidate:formulavalidate,formulavalidateShow:formulavalidateShow},
            function(data){
                if($.trim(data)=="0000"){
                    $.myAlert.Alert("保存成功","信息提示");
                }else{
                    $.myAlert.Alert(data,"信息提示");
                }
            },"html");
        hideCover("#validateSetting");
    });
    /***
     * 表单校验公式的公式校验
     */
    $("#validateFormula").click(function(){
        var formId = commonFunction.getFileName("form");
        //var formulavalidate= $("#formulaCheckShow").attr("calculationShow");
        var formulavalidate= $("#formulaCheckShow").val();
        $.post("FormDataForToolBean/checkFormulavalidate",{formId:formId,formulavalidate:formulavalidate},
            function(data){
                if($.trim(data)=="0000"){
                    $.myAlert.Alert("校验成功","校验信息");
                }else{
                    $.myAlert.Alert(data,"校验信息");
                }
            },"html");
    });
    /**
     * 校验公式按钮--valalidate formula delete
     */
    $("#validateSetting .btnInput").click(function(){
        var textAreaValue= $("#formulaCheckShow");
        var valalidateSelect = $("#validateSettingSelect");
        var  showText= textAreaValue.val();
        var actualValue = textAreaValue.attr("calculationShow");
        if($(this).text()=="←"){//删除键
            var option = valalidateSelect.find(".showSelect li");
            var tmp="";
            var tmpVal="";
            for(var i=0;i<option.length;i++){//for-start
                var opt = option.eq(i).attr("value");
                var optionTmp = $.trim(option.eq(i).text());
                var optVal = optionTmp.substr(0,optionTmp.lastIndexOf("("));
                if(showText.length>=opt.length){
                    if(showText.substr(-opt.length,opt.length)==opt){
                        if(opt.length>tmp.length){
                            tmp = opt;
                            tmpVal =optVal;
                        }
                    }
                }
            }//for-end
            var businessDateStr = "营业日期";
            var businessDateStrVal = "$txDt";
            if(tmp==""){
                if(showText.length>=businessDateStrVal.length&&showText.substr(-businessDateStrVal.length,businessDateStrVal.length)==businessDateStrVal){
                    textAreaValue.val(showText.substr(0,showText.length-businessDateStrVal.length));
                    textAreaValue.attr("calculationShow",actualValue.substr(0,actualValue.length-businessDateStr.length));
                }else{
                    textAreaValue.val(showText.substr(0,showText.length-1));
                    textAreaValue.attr("calculationShow",actualValue.substr(0,actualValue.length-1));
                }
            }else{
                textAreaValue.val(showText.substr(0,showText.length-tmp.length));
                textAreaValue.attr("calculationShow",actualValue.substr(0,actualValue.length-tmpVal.length));
            }
        }else if($(this).attr("id")=="businessDate"){//营业日期
            textAreaValue.val(textAreaValue.val()+"$txDt");
            textAreaValue.attr("calculationShow",textAreaValue.attr("calculationShow")+"营业日期");
        }else if($(this).attr("id")=="valalidateInsert"){//插入
            var valueStr =$(this).parents(".btnCtrl").find(".a_select input").attr("val");
            var selects = $(this).parents(".btnCtrl").find(".a_select input").val();
            if(valueStr===undefined){
                valueStr="";
            }
            var valueStrTmp = selects.substr(0,selects.lastIndexOf("("));
            textAreaValue.val(showText+valueStr);
            textAreaValue.attr("calculationShow",actualValue+valueStrTmp);
        }else{
            var valueTmp =$(this).text();
            if(valueTmp=="≥"){
                valueTmp =">=";
            }else if(valueTmp=="≤"){
                valueTmp ="<=";
            }
            textAreaValue.val(textAreaValue.val()+valueTmp);
            textAreaValue.attr("calculationShow",actualValue+valueTmp);
        }
        textAreaValue.focus();
        textAreaValue.scrollTop(1000);
    });


    /*保存*/
    $("#validateSave").click(function(){

    });
    /*清空*/
    $("#validateClear").click(function(){
        validateClear();
    });
    /*关闭*/
    $("#validateClose").click(function(){
        validateSlideUp();
    });
    //清空校验公式
    function validateClear(){
        $("#valalidateShow .valalidateTextarea").val("").focus();
        $("#valalidateShow .valalidateTextarea").attr("actualValue","");
    }

    /**
     *方法：选定selected
     *参数：1.idStr(select id)
     *     2.selectVal(选中的值)
     */
    function setSelected(idStr,selectVal){
        var selectObj =$("#"+idStr);
        var pObj = selectObj.parents(".dropdown");
        var text = "";
        var count = selectObj.find("option").length;
        selectObj.find("option").removeAttr("selected");
        pObj.find("div ul li").removeClass("active");
        for(var i=0;i<count;i++){
            if(selectObj.get(0).options[i].value == selectVal) {
                text = selectObj.get(0).options[i].text;
                selectObj.get(0).options[i].selected=true;
                pObj.find("div ul li").eq(i).addClass("active");
            }
        }
        pObj.find(".selected").html(text);
    }
    function getSelected(idStr){
        var selectObj =$("#"+idStr);
        var pObj = selectObj.parents(".dropdown");
        var selectedVal = "";
        var text = $.trim(pObj.find("div ul .active").text());
        var count = selectObj.find("option").length;
        for(var i=0;i<count;i++){
            if(selectObj.get(0).options[i].text == text) {
                selectedVal = selectObj.get(0).options[i].value;
            }
        }
        return selectedVal;
    }
    function changeNull(objStr){
        if(objStr=="null"){
            objStr="";
        }
        return objStr;
    }
});