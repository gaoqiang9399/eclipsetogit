/**
 * Created by jzh on 2015/2/2.
 * 对页面属性赋值等相关操作
 */

define(function(require, exports, module) {
    var commonFunction =  require("../../common/js/commonFunction.js");
    var dataTypeAll = ["dragLable-1-1","dragInput-1-2","dragSelect-2-2","dragDate-1-2","dragCheckbox-3-2",
        "dragTextArea-5-2","dragRadio-4-2", "dragPassWord-6-2","dragOther-1-2","dragBartop-00-1",
        "dragAnchorStart-01-1","dragAnchorEnd-02-1",
        "dragInputGroup-1-0","dragSelectGroup-2-0", "dragDateGroup-1-0","dragTextAreaGroup-5-0",
        "dragCheckboxGroup-3-0","dragRadioGroup-4-0","dragPassWordGroup-6-0", "dragOtherGroup-1-0","dragCustom-xx-0"];
    /**
     *方法名称：form属性赋值
     * 参数：formAttribute(json 键值对)
     *
     */
    exports.AttributeEvaluation = function(formId,formActiveId){
        var labelName = "";
        $.post("./FormDataForToolBean/getFormElement",{formId:formId,formActiveId:formActiveId},function(data){
            //1.标签名称
            $("#labelName").text(changeNull(data.labelName));
            $(".span_lableName input").val(changeNull(data.labelName));
            //2.字段名称
            $("#fieldName").text(changeNull(data.fieldName));
            $(".span_filedName input").val(changeNull(data.fieldName));
            //3.字段类型
            setPseudoSelect("#fieldType",changeNull(data.fieldType));
            //4.数据类型
            setPseudoSelect("#dataType",changeNull(data.dataType));
            //5.标签行次
            $("#row").val(changeNull(data.row));
            //6.标签列次
            $("#labelCol").val(changeNull(data.labelCol));
            //7.字段列次
            $("#fieldCol").val(changeNull(data.fieldCol));
            //8.尺寸/选项
            $("#fieldSize").val(changeNull(data.fieldSize));
            //9.最大长度
            $("#maxLength").val(changeNull(data.maxLength));
            //10.是否只读
            setBtnShow("#readonly",changeNull(data.readonly));
            //11.是否必输
            setBtnShow("#mustInput",changeNull(data.mustInput));
            //12.初值
            $("#initValue").val(changeNull(data.initValue));
            //13.单位
            $("#unit").val(changeNull(data.unit));
            //14.字段说明
            $("#alt").val(changeNull(data.alt));
            //15.参数
            $("#para").val(changeNull(data.para));
            //16.标签跨行数
            $("#rowSpan").val(changeNull(data.rowSpan));
            //17.标签跨列数
            $("#labelColSpan").val(changeNull(data.labelColSpan));
            //18.字段跨行数
            $("#fieldColSpan").val(changeNull(data.fieldColSpan));
            //19.定义范围
            setPseudoSelectIcon("#selRange",changeNull(data.selRange));
            //20.标签对齐
            setPseudoSelectIcon("#labelAlign",changeNull(data.labelAlign));
            //21.字段对齐
            setPseudoSelectIcon("#fieldAlign",changeNull(data.fieldAlign));
            //22.文字方向
            setPseudoSelectIcon("#labelDirect",changeNull(data.labelDirect));
            //23.标签宽度
            $("#labelWidth").val(changeNull(data.labelWidth));
            //24.标签样式
            $("#labelStyle").val(changeNull(data.labelStyle));
            //25.字段宽度
            $("#fieldWidth").val(changeNull(data.fieldWidth));
            //26.字段样式
            $("#fieldStyle").val(changeNull(data.fieldStyle));
            //27.文本域内容行数设置
            $("#textAreaFieldRows").val(changeNull(data.textAreaFieldRows));
            //28.标签链接
            $("#labelLink").val(changeNull(data.labelLink));
            //29.按钮链接
            $("#buttonCondition").val(changeNull(data.buttonCondition));
            //30.点击事件
            setEvent("#onclick",changeNull(data.onclick));
            //31.获得焦点
            setEvent("#onfocus",changeNull(data.onfocus));
            //32.焦点离开
            setEvent("#onblur",changeNull(data.onblur));
            //33.键盘松开
            setEvent("#onkeyup",changeNull(data.onkeyup));
            //34.键盘按下
            setEvent("#onkeydown",changeNull(data.onkeydown));
            //35.内容变更
            setEvent("#onchange",changeNull(data.onchange));
            //36.鼠标经过
            setEvent("#onkeypress",changeNull(data.onkeypress));
            //37.鼠标按下
            setEvent("#onmousedown",changeNull(data.onmousedown));
            //38.事件类型
            setPseudoSelect("#calculationType",changeNull(data.calculationType));
            //39.联动公式计算
            $("#calculationVal").attr("val",changeNull(data.calculation));
            $("#calculationVal").attr("calculationShow",changeNull(data.calculationShow));
            //41.显示方向
            setPseudoSelect("#horVer",changeNull(data.horVer))+'",';
            //42.是否可更改
            //setBtnShow("#writeAuth",changeNull(data.writeAuth))+'"';
            $("#writeAuth").val(changeNull(data.writeAuth));
            //43 字段截图长度
             $("#cutOff").val(changeNull(data.cutOff));
            //alert("horVer:"+changeNull(data.horVer)+",writeAuth:"+data.writeAuth);
        },"json");

    }
    /**
     * 设置点击按钮（0:否,1:是）
     * @param idName
     * @param idvalue
     */
    function setBtnShow(idName,idvalue){
        var choiceStr = $.trim($(idName).attr("choice"));
        $(idName).removeClass(choiceStr+"-unchoice").removeClass(choiceStr+"-choice");
        if(idvalue==0){
            $(idName).addClass(choiceStr+"-unchoice");
            $(idName).attr("val",idvalue);
        }else if(idvalue==1){
            $(idName).addClass(choiceStr+"-choice");
            $(idName).attr("val",idvalue);
        }
    }
    /**
     * 事件赋值
     * 参数：1.事件名（idName）
     *      2.事件值（idValue）
     */
    function setEvent(idName,idValue){
        $(idName).attr("val",idValue);
        if(idValue==""){
            $(idName).parent().hide();
        }else{
            $(idName).parent().show();
        }
    }
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
     *方法：取值PseudoSelect
     *参数：1.idStr(select id)
     */
    function getPseudoSelect(idStr){
        return $.trim($(idStr).attr("val"));
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
    /**
     * 字段属性更新
     * 参数:formId(表单id)
     *     formActiveId(表单属性id)
     */
    exports.AttributeUpdate = function(formId,formActiveId){
        var formAttribute = getformAttribute();
        var row = parseInt($("#row").val());
        if(row<200){
            $.post("./FormDataForToolBean/updateFormElement",
                {formId:formId,formActiveId:formActiveId,formAttribute:formAttribute},
                function(data){//返回结果
                    //myAlert.myAlert(data);
                    //commonFunction.reloadFormOrTable();
                    initframe();
                },"html");
        }else{
            $.myAlert.Alert("标签行次过大,行次超过"+row+"！");
        }

    };
    /**
     * 字段属性删除
     * 参数:formId(表单id)
     *     formActiveId(表单属性id)
     */
    exports.AttributeDel = function(formId,formActiveId){
        $.post("./FormDataForToolBean/delFormElement",
            {formId:formId,formActiveId:formActiveId},
            function(data){//返回结果
                //myAlert.myAlert(data);
                //commonFunction.reloadFormOrTable();
                initframe();
            },"html");
    }

    /**
     * 获取表单字段的所有属性值
     * 返回值：json串
     */
    function getformAttribute(){
        var formAttribute = "{";

        //1.标签名称
        formAttribute+='"labelName":"'+$.trim($("#labelName").text())+'",';
        //2.字段名称
        formAttribute+='"fieldName":"'+$.trim($("#fieldName").text())+'",';
        //3.字段类型
        formAttribute+='"fieldType":"'+getPseudoSelect("#fieldType")+'",';
        //4.数据类型
        formAttribute+='"dataType":"'+getPseudoSelect("#dataType")+'",';
        //5.标签行次
        formAttribute+='"row":"'+$("#row").val()+'",';
        //6.标签列次
        formAttribute+='"labelCol":"'+ $("#labelCol").val()+'",';
        //7.字段列次
        formAttribute+='"fieldCol":"'+ $("#fieldCol").val()+'",';
        //8.尺寸/选项
        formAttribute+='"fieldSize":"'+ $("#fieldSize").val()+'",';
        //9.最大长度
        formAttribute+='"maxLength":"'+ $("#maxLength").val()+'",';
        //10.是否只读
        formAttribute+='"readonly":"'+getPseudoSelect("#readonly")+'",';
        //11.是否必输
        formAttribute+='"mustInput":"'+getPseudoSelect("#mustInput")+'",';
        //12.初值
        formAttribute+='"initValue":"'+ $("#initValue").val()+'",';
        //13.单位
        formAttribute+='"unit":"'+ $("#unit").val()+'",';
        //14.字段说明
        formAttribute+='"alt":"'+ $("#alt").val()+'",';
        //15.参数
        formAttribute+='"para":"'+ $("#para").val()+'",';
        //16.标签跨行数
        formAttribute+='"rowSpan":"'+ $("#rowSpan").val()+'",';
        //17.标签跨列数
        formAttribute+='"labelColSpan":"'+ $("#labelColSpan").val()+'",';
        //18.字段跨行数
        formAttribute+='"fieldColSpan":"'+ $("#fieldColSpan").val()+'",';
        //19.定义范围
        formAttribute+='"selRange":"'+getPseudoSelect("#selRange")+'",';
        //20.标签对齐
        formAttribute+='"labelAlign":"'+getPseudoSelect("#labelAlign")+'",';
        //21.字段对齐
        formAttribute+='"fieldAlign":"'+getPseudoSelect("#fieldAlign")+'",';
        //22.文字方向
        formAttribute+='"labelDirect":"'+getPseudoSelect("#labelDirect")+'",';
        //23.标签宽度
        formAttribute+='"labelWidth":"'+ $("#labelWidth").val()+'",';
        //24.标签样式
        formAttribute+='"labelStyle":"'+ $("#labelStyle").val()+'",';
        //25.字段宽度
        formAttribute+='"fieldWidth":"'+ $("#fieldWidth").val()+'",';
        //26.字段样式
        formAttribute+='"fieldStyle":"'+ $("#fieldStyle").val()+'",';
        //27.文本域内容行数设置
        formAttribute+='"textAreaFieldRows":"'+ $("#textAreaFieldRows").val()+'",';
        //28.标签链接
        formAttribute+='"labelLink":"'+ $("#labelLink").val()+'",';
        //29.按钮链接
        formAttribute+='"buttonCondition":"'+ $("#buttonCondition").val()+'",';
        //30.点击事件
        formAttribute+='"onclick":"'+ $("#onclick").attr("val")+'",';
        //31.获得焦点
        formAttribute+='"onfocus":"'+ $("#onfocus").attr("val")+'",';
        //32.焦点离开
        formAttribute+='"onblur":"'+ $("#onblur").attr("val")+'",';
        //33.键盘松开
        formAttribute+='"onkeyup":"'+ $("#onkeyup").attr("val")+'",';
        //34.键盘按下
        formAttribute+='"onkeydown":"'+ $("#onkeydown").attr("val")+'",';
        //35.内容变更
        formAttribute+='"onchange":"'+ $("#onchange").attr("val")+'",';
        //36.鼠标经过
        formAttribute+='"onkeypress":"'+ $("#onkeypress").attr("val")+'",';
        //37.鼠标按下
        formAttribute+='"onmousedown":"'+ $("#onmousedown").attr("val")+'",';
        //38.事件类型
        formAttribute+='"calculationType":"'+getPseudoSelect("#calculationType")+'",';
        //39.联动公式计算
        formAttribute+='"calculation":"'+$("#calculationVal").attr("val") +'",';
        //40.是否显示
        formAttribute+='"calculationShow":"'+$("#calculationVal").attr("calculationShow")+'",';
        //41.显示方向
        formAttribute+='"horVer":"'+getPseudoSelect("#horVer")+'",';
        //42.是否可更改
        //formAttribute+='"writeAuth":"'+getPseudoSelect("#writeAuth")+'"';
        formAttribute+='"writeAuth":"'+$("#writeAuth").val()+'",';
        //43.字段截图长度
        formAttribute+='"cutOff":"'+$("#cutOff").val()+'"';
        formAttribute+="}";
        return formAttribute;
    }
    /**
     * function:获取字段类型
     * parameter:拖动对象
     * return:FieldType类型
     */
    exports.getFieldType = function(obj){
        var returnStr ="";
        var dataType = $(obj).find(".reallyObj").attr("data-type");
        for(var i=0;i<dataTypeAll.length;i++){
            if(dataType==dataTypeAll[i].split("-")[0]){
                returnStr = dataTypeAll[i].split("-")[1];
            }
        }
        return returnStr;
    }
    exports.removeFlagClass = function(){
        $(".centerBroder").removeClass("centerBroder");
        $(".leftBroder").removeClass("leftBroder");
        $(".rightBroder").removeClass("rightBroder");
        $(".oneBroder").removeClass("oneBroder");
    }
    /**
     * function:判断是标准还是基本主键
     * parameter:拖动对象
     * return:false/true
     */
    exports.getGroupOrSingle = function(obj){
        var returnStr =false;
        var cloneFlag = $(obj).parents(".left-nav").length;
        var tableFlag = $(obj).parents(".mainContent").length;
        if(cloneFlag==1){
            if($(obj).parents(".collapseThree").length==1){
                returnStr = true;
            }else if($(obj).parents(".collapseOne").length==1){
                returnStr = "one";
            }else if($(obj).parents(".collapseTwo").length==1){
                returnStr = "head";
            }else if($(obj).parents(".collapseFour").length==1){
                returnStr = "custom";
            }
        }else if(tableFlag==1){
           var objId = $(obj).attr("id");
            if(objId!==undefined&&$.trim(objId.split("_")[0])=="left"){
                var objRObj = $(obj).parents("td").next("td").find(".drag");
                if($(objRObj).html()!==undefined){
                    var objRId = $(objRObj).attr("id");
                    if($.trim(objId.split("_")[1])!=$.trim(objRId.split("_")[1])){
                        returnStr = true;
                    }
                }else{
                    returnStr = true;
                }
            }else if(objId!==undefined&& $.trim(objId.split("_")[0])=="right"){
                var objLObj = $(obj).parents("td").prev("td").find(".drag");
                if(objLObj.html()!==undefined){
                    var objLId = $(objLObj).attr("id");
                    if($.trim(objId.split("_")[1])!=$.trim(objLId.split("_")[1])){
                        returnStr = true;
                    }
                }else{
                    returnStr = true;
                }
            }
        }
        return returnStr;
    }
    /**
     * function:获取合并列数
     */
    exports.getColSpanNo = function(obj){
        var returnStr = 0;
        var objColspan = 0;
        var tableFlag = $(obj).parents(".mainContent").length;
        if(tableFlag==1){
            var objId = $(obj).attr("id");
            objColspan = parseInt($(obj).parents("td").attr("colspan"));
            if(objColspan===undefined){
                objColspan = 1;
            }
            returnStr+=objColspan;
            if(objId!==undefined&&$.trim(objId.split("_")[0])=="left"){
                var objRObj = $(obj).parents("td").next("td").find(".drag");
                if($(objRObj).html()!==undefined){
                    var objRId = $(objRObj).attr("id");
                    if($.trim(objId.split("_")[1])==$.trim(objRId.split("_")[1])){
                        objColspan = parseInt($(objRObj).parents("td").attr("colspan"));
                        returnStr+=objColspan;
                    }
                }
            }else if(objId!==undefined&& $.trim(objId.split("_")[0])=="right"){
                var objLObj = $(obj).parents("td").prev("td").find(".drag");
                if(objLObj.html()!==undefined){
                    var objLId = $(objLObj).attr("id");
                    if($.trim(objId.split("_")[1])==$.trim(objLId.split("_")[1])){
                        objColspan = parseInt($(objLObj).parents("td").attr("colspan"));
                        returnStr+=objColspan;
                    }
                }
            }
        }
        return  returnStr;
    }
    /**
     * function:判断字段类型
     * parameter:字段类型
     * return:FieldType类型
     */
    exports.judgeFileIdType =function(fieldType){
        if(fieldType=="00"){
            fieldType="1";
        }
        return fieldType;
    }
    /**
     * function:判断标签作用范围
     * parameter:拖动对象
     * return:标签作用范围
     */
    exports.judgeSelRange = function(obj){
        var returnStr ="";
        var dataType = $(obj).find(".reallyObj").attr("data-type");
        for(var i=0;i<dataTypeAll.length;i++){
            if(dataType==dataTypeAll[i].split("-")[0]){
                returnStr = dataTypeAll[i].split("-")[2];
            }
        }
        return returnStr;
    }
    /**
     * function:获取默认属性
     * parameter:1.row(行次)
     *           2.labelCol(标签行次)
     *           3.fieldCol(标签列次)
     *           4.fieldType(字段类型)
     *           5.selRange(定义范围)
     * return:json串
     */
    exports.getformAttributeDefault = function(row,labelCol,fieldCol,fieldType,dataType,selRange,labelColSpan,labelName,titleFlag){
        ///alert("labelColSpanStart:"+labelColSpan);
        if(labelColSpan==""||labelColSpan===undefined){
            labelColSpan = "1";
        }
        //alert("labelColSpanEnd:"+labelColSpan);
        if(labelName==""||labelName===undefined){
            labelName="labelName";
        }
        var formAttribute = "{";
        //1.标签名称
        formAttribute+='"labelName":"'+labelName+'",';
        //2.字段名称
        if(dataType=="dragAnchorStart"||dataType=="dragAnchorEnd"){
        	formAttribute+='"fieldName":"anchorFieldName",';
        }else{
        	formAttribute+='"fieldName":"fieldName",';
        }
        //3.字段类型
        formAttribute+='"fieldType":"'+fieldType+'",';
        //4.数据类型
        if(dataType=="dragDate"||dataType=="dragDateGroup"){
            formAttribute+='"dataType":"6",';
        }else{
            formAttribute+='"dataType":"0",';
        }
        //5.标签行次
        formAttribute+='"row":"'+row+'",';
        //6.标签列次
        formAttribute+='"labelCol":"'+labelCol+'",';
        //7.字段列次
        formAttribute+='"fieldCol":"'+ fieldCol+'",';
        //9.最大长度
        formAttribute+='"maxLength":"0",';
        //16.标签跨行数
        formAttribute+='"rowSpan":"1",';
        //17.标签跨列数
        formAttribute+='"labelColSpan":"'+labelColSpan+'",';
        //18.字段跨行数
        formAttribute+='"fieldColSpan":"1",';
        //19.定义范围
        formAttribute+='"selRange":"'+selRange+'",';
        //20.标签对齐
        if(titleFlag==true){
            formAttribute+='"labelAlign":"2",';
        }else{
            formAttribute+='"labelAlign":"3",';
        }
        //21.字段对齐
        formAttribute+='"fieldAlign":"1",';
        //22.文字方向
        formAttribute+='"labelDirect":"0",';
        //23.标签宽度
        formAttribute+='"labelWidth":"0",';
        //25.字段宽度
        formAttribute+='"fieldWidth":"0",';
        //30.点击事件
        formAttribute+='"onclick":"",';
        //31.获得焦点
        formAttribute+='"onfocus":"",';
        //32.焦点离开
        formAttribute+='"onblur":"",';
        //33.键盘松开
        formAttribute+='"onkeyup":"",';
        //34.键盘按下
        formAttribute+='"onkeydown":"",';
        //35.内容变更
        formAttribute+='"onchange":"",';
        //36.鼠标经过
        formAttribute+='"onkeypress":"",';
        //37.鼠标按下
        formAttribute+='"onmousedown":"",';
        if(dataType=="dragAnchorStart"){
        	formAttribute+='"labelStyle":"anchor-start",';
        }else if(dataType=="dragAnchorEnd"){
        	formAttribute+='"labelStyle":"anchor-end",';
        }else if(titleFlag==true){
            formAttribute+='"labelStyle":"bartop",';
        }else {
            formAttribute+='"labelStyle":"",';
        }
        //41.显示方向
        formAttribute+='"horVer":"0",';
        //42.是否可更改
        formAttribute+='"writeAuth":""';
        formAttribute+="}";
        return formAttribute;
    }
    function changeNull(objStr){
        if(objStr=="null"){
            objStr="";
        }
        return objStr;
    }
    $(function() {
    });
});