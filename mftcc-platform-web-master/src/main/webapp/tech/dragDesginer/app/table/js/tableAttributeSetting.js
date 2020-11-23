/*table attribute setting 列表属性设置*/
define(function(require, exports, module) {
    var commonFunction = require("../../common/js/commonFunction.js");
    var initTableSetting = require("./initTableSetting.js");
    window.open= function(){
        var self = this,
            scrollTop = window.scrollY || document.documentElement.scrollTop,
            scrollLeft = window.scrollX || document.documentElement.scrollLeft,
            scrollOffset = self.notInViewport(scrollTop);

        self.closeAll();
        self.getMaxHeight();
        //alert(self.maxHeight);
        self.$select.focus();
        //alert(self.maxHeight);
        window.scrollTo(scrollLeft, scrollTop+scrollOffset);
        self.$container.addClass('open');
        self.$scrollWrapper.css('height',self.maxHeight+'px');
        self.down = true;
    }
    /**
     * function:获取字段属性
     * parameter:tableListId(字段id)
     */
    exports.setAttributeDes = function(tableListId){
        setTableListId(tableListId);
        var tableId = commonFunction.getFileName("table");
        $.post("TableDataForToolBean/getTableElement",
            {tableId:tableId,tableListId:tableListId},
            function(data){
                $("#tableLabel").text(data.label);
                $(".span_lableName input").val(changeNull(data.label));
                $("#fieldName").text(data.fieldName);
                $(".span_filedName input").val(changeNull(data.fieldName));
                $("#tableWidth").val(data.width);
                setPseudoSelectIcon("#tableAlign",data.align);
                $("#tableIndexed").val(data.indexed);
                setPseudoSelect("#tableType",data.type);
                $("#tableTypePara").val(data.typePara);
                $("#tableAuthority").val(data.authority);
                $("#tableMytitle").val(data.mytitle);
                $("#tableButtonMark").val(data.buttonMark);
                $("#fieldStyle").val(data.fieldStyle);
                $("#defaultShow").val(data.defaultShow);
                setPseudoSelect("#sortType",data.sortType);
                setPseudoSelect("#widthType",data.widthType?data.widthType:"0");
                $("#colorCol").val(data.colorCol);
        },"json");
    }
    /**
     *方法：取值PseudoSelect
     *参数：1.idStr(select id)
     */
    function getPseudoSelect(idStr){
        return $.trim($(idStr).attr("val"));
    }
    /**
    /**
     *方法：选定PseudoSelect
     *参数：1.idStr(select id)
     *     2.selectVal(选中的值)
     */
    function setPseudoSelect(idStr,selectVal){
        var objText = "";
        var selects = $(idStr).parents(".a_select").find(".showSelect li");
        $(idStr).attr("val","");
        $(idStr).val("");
        for(var i=0;i<selects.length;i++){
            if(selects.eq(i).attr("value")==selectVal){
                objText = $.trim(selects.eq(i).text());
                $(idStr).attr("val",selectVal);
                $(idStr).val(objText);
                break;
            }
        }
    }
    /**
     *方法：选定PseudoSelect
     *参数：1.idStr(select id)
     *     2.selectVal(选中的值)
     */
    function setPseudoSelectIcon(idStr,selectVal){
        var classm = "";
        var selects = $(idStr).parents(".icon_select").find(".showSelectIcon li");
        for(var i=0;i<selects.length;i++){
            if(selects.eq(i).attr("val")==selectVal){
                classm = $.trim(selects.eq(i).attr("classm"));
                $(idStr).removeClass($(idStr).attr("classm"));
                $(idStr).addClass(classm);
                $(idStr).attr("val",selectVal);
                $(idStr).attr("classm",classm);
                break;
            }
        }
    }
    function getAttributeDes(){
        var returnStr = "{";
        var label = $.trim($("#tableLabel").text());
        returnStr+='"label":"'+label+'",';
        var fieldName = $.trim($("#fieldName").text());
        returnStr+='"fieldName":"'+fieldName+'",';
        var width = $("#tableWidth").val();
        returnStr+='"width":"'+width+'",';
        var align = getPseudoSelect("#tableAlign");
        returnStr+='"align":"'+align+'",';
        var indexed = $("#tableIndexed").val();
        returnStr+='"indexed":"'+indexed+'",';
        var type = getPseudoSelect("#tableType");
        returnStr+='"type":"'+type+'",';
        var widthType = getPseudoSelect("#widthType");
        returnStr+='"widthType":"'+(widthType?widthType:"0")+'",';
        var typePara = $("#tableTypePara").val();
        returnStr+='"typePara":"'+typePara+'",';
        var authority = $("#tableAuthority").val();
        returnStr+='"authority":"'+authority+'",';
        var mytitle = $("#tableMytitle").val();
        returnStr+='"mytitle":"'+mytitle+'",';
        var buttonMark = $("#tableButtonMark").val();
        returnStr+='"buttonMark":"'+buttonMark+'",';
        var fieldStyle = $("#fieldStyle").val();
        returnStr+='"fieldStyle":"'+fieldStyle+'",';
        var sortType = getPseudoSelect("#sortType");
        returnStr+='"sortType":"'+sortType+'",';
        var defaultShow = $("#defaultShow").val();
        returnStr+='"defaultShow":"'+defaultShow+'",';
        var colorCol = $("#colorCol").val();
        returnStr+='"colorCol":"'+colorCol+'"';
        returnStr+="}";
        return returnStr;
    }
    function getDefaultAttributeDes(type,label){
        if(label==""||label===undefined){
            label = "label";
        }
        var returnStr = "{";
        returnStr+='"label":"'+label+'",';
        returnStr+='"fieldName":"fieldName",';
        returnStr+='"width":"0",';
        returnStr+='"align":"2",';
        returnStr+='"indexed":"",';
        returnStr+='"type":"'+type+'",';
        returnStr+='"typePara":"",';
        returnStr+='"authority":"",';
        returnStr+='"mytitle":"",';
        returnStr+='"buttonMark":"",';
        returnStr+='"row":"1",';
        returnStr+='"rowSpan":"1",';
        returnStr+='"colSpan":"1",';
        returnStr+='"colorCol":""';
        returnStr+="}";
        return returnStr;
    }
    /**
     * function:校验确认
     */
    function validateTableAttributeDes(){
        var returnStr = "";
        var label = $.trim($("#tableLabel").text());
        var align = $.trim(getPseudoSelect("#tableAlign"));
        var indexed = $.trim($("#tableIndexed").val());
        var type = $.trim(getPseudoSelect("#tableType"));
        var widthType = $.trim(getPseudoSelect("#widthType"));
        if(label==""){
            returnStr+="列名称不能为空！";
        }
        if(align==""){
            returnStr+="对齐方式不能为空！";
        }
        if(indexed==""){
            returnStr+="列顺序不能为空！";
        }
        if(type==""){
            returnStr+="类型不能为空！";
        }
        if(widthType==""){
        	returnStr+="列宽单位不能为空！";
        }
        return returnStr;
    }
    /**
     * function:新增保存属性
     */
    exports.insertAttributeDes = function(targetId,type,label){
        var tableId = commonFunction.getFileName("table");
        var tableElement = getDefaultAttributeDes(type,label);
        targetId = targetId.match("^\\d+$")+"";
        $.post("TableDataForToolBean/insertTableElement",
            {tableId:tableId,targetId:targetId,tableElement:tableElement},
            function(data){
                if($.trim(data)=="0000"){
                    //setTimeout(commonFunction.reloadFormOrTable,1000);
                    //setTimeout(initTableSetting.initTableframe,5000);
                }

            },"html");
    }
    /**
     * function:更新保存属性
     * parameter:tableListId(字段id)
     */
    exports.updateAttributeDes = function(tableListId){
        var tableId = commonFunction.getFileName("table");
        var tableElement = getAttributeDes();
        $.post("TableDataForToolBean/updateTableElement",
            {tableId:tableId,tableListId:tableListId,tableElement:tableElement},
            function(data){
                if($.trim(data)=="0000"){
                    //commonFunction.reloadFormOrTable();
                    initTableSetting.initTableframe();
                }

            },"html");
    }
    /**
     * function:更新保存属性
     * parameter:tableListId(字段id)
     */
    exports.changePosition = function(tableListId,targetId){
        var tableId = commonFunction.getFileName("table");
        $.post("TableDataForToolBean/updateTableElementCol",
            {tableId:tableId,tableListId:tableListId,targetId:targetId},
            function(data){
                if($.trim(data)=="0000"){
                }
            },"html");
    }
    /**
     * function:删除字段
     * parameter:tableListId(字段id)
     */
    exports.deleteTableElement = function(tableListId){
        var tableId = commonFunction.getFileName("table");
        $.post("TableDataForToolBean/delTableElement",
            {tableId:tableId,tableListId:tableListId},
            function(data){
                if($.trim(data)=="0000"){
                    //commonFunction.reloadFormOrTable();
                     initTableSetting.initTableframe();
                }
            },"html");
    }
    /**
     * function:table属性置空初始化
     */
    exports.initAttributeDes = function(){
        $("#tableLabel").val("");
        $("#fieldName").val("");
        $("#tableWidth").val("");
        setPseudoSelectIcon("#tableAlign","1");
        $("#tableIndexed").val("");
        setPseudoSelect("#tableType","0");
        setPseudoSelect("#widthType","0");
        $("#tableTypePara").val("");
        $("#tableAuthority").val("");
        $("#tableMytitle").val("");
        $("#tableButtonMark").val("");
        $("#fieldStyle").val("");
        $("#defaultShow").val("");
        $("#colorCol").val("");
    }
    function setTableListId(tableListId){
        $(".setProperties").attr("tableId","attribute_"+tableListId);
    }
    function getTableListId(){
        var returnStr = "";
        var tableListId =  $(".setProperties").attr("tableId");
        returnStr = tableListId.split("_")[1];
        return returnStr;
    }
    function changeNull(objStr){
        if(objStr=="null"){
            objStr="";
        }
        return objStr;
    }
    window.initTrash = function(){
    	var trashHtml = '<div style="display: none;" onclick="toolsHoverDelete(this);" class="tools2 toolsHover">'; 
    	trashHtml += '<b sign="trash" class="cC0 nui-ico nui-ico-smartTrash tableTrash"> ';
    	trashHtml += '<b class="nui-ico nui-ico-smartTrash-head"></b> ';
    	trashHtml += '<b class="nui-ico nui-ico-smartTrash-body"></b> ';
    	trashHtml += '</b>';
    	trashHtml += '</div>';
        $(".mainContent #table2 tbody").find("tr td").hover(function(){
        	$(this).parents("tr").find(".toolsHover").remove();
        	$(this).append(trashHtml);
            //console.log("进");
            $(this).find(".toolsHover").css("display","block");
        },function(){
            $(this).find(".toolsHover").css("display","none");
            //$(this).find(".toolsHover").remove();
            //console.log("出");
        });
    }
    //页面加载执行
    $(function(){
        $("#drag-calculator").click(function(){
            var fileName = commonFunction.getFileName();
            var attributeId = $("#tableDataSetting").attr("tableid");
            if(fileName!==false&&attributeId!==undefined){
                $("#tableDataSetting").slideToggle("slow");
            }else if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else{
                $.myAlert.Alert("请打开一个字段属性！");
            }
        });
        $("#dragClose").click(function () {
            $("#tableDataSetting").slideToggle("slow");
        });
        window.updateAttributeDesTable = function(){
            var tableListId = getTableListId();
            var flag = validateTableAttributeDes();
            if(flag==""){
                exports.updateAttributeDes(tableListId);
                $("#tableDataSetting").slideToggle("slow");
            }else{
                $.myAlert.Alert(flag);
            }
        }
        $("#tableAttributeDel").click(function(){
            var tableListId = getTableListId();
            exports.deleteTableElement(tableListId);
            $("#tableDataSetting").slideToggle("slow");
        });
        $("#clearAttribute").click(function(){
            exports.initAttributeDes();
        });
    });
});