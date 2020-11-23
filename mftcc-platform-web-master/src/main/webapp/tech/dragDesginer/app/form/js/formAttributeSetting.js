define(function(require, exports, module) {
    var validate = require("./validate.js");
    var formDataCtrl = require("./formDataCtrl.js");
    var commonFunction = require("../../common/js/commonFunction.js");
    var tablePosition = require("./tablePosition.js");
    var clickFlag = false;
    /**
     * function:设置操作当前属性框id
     *
     */
    window.setAttrbuteId = function(objStr){
        if(objStr!=""&&objStr!==undefined){
            $(".setProperties").attr("formActiveId","formActiveId_"+objStr);
        }
    }
    window.getAttrbuteId = function(){
        return $(".setProperties").attr("formActiveId");
    }
    /**
     * function:删除或隐藏字段
     */
    window.delCurrentField = function(e){
        var formId = commonFunction.getFileName("form");
        var formActiveId = $.trim($(e).parent("td").find(".drag").attr("id").split("_")[1]);
        $.myAlert.Choose("请选择改变该字段方式！","提示信息",["隐藏","删除"],
            [function(){
                $.post("./FormDataForToolBean/updateHide",
                {formId: formId, formActiveId: formActiveId},
                function (data) {//返回结果
                    if ($.trim(data) == "0000") {
                        initframe();
                    } else {
                        $.myAlert.Alert(data);
                    }
                }, "html");},
            function(){formDataCtrl.AttributeDel(formId,formActiveId);}
            ]);
    }
    /**
     * function:
     */
    window.colCtrlFun = function(colNo,type){
    	var tdNum = $("#table2").find("tr").first().find("td").length;
    	if(tdNum>6||type=="add"){
    		var formId = commonFunction.getFileName("form");
	        colNo++;
	        if(type=="add"){
	            //console.log("add-colNo:"+colNo);
	            $.myAlert.Confirm("是否在当前列新增一列？","提示信息",function() {
	                $.post("FormDataForToolBean/insertColumn",
	                    {formId: formId, columnNum: colNo},
	                    function (data) {
	                        //console.log(data);
	                        if($.trim(data)=="0000"){
	                            initframe();
	                        } else {
	                            $.myAlert.Alert(data);
	                        }
	                    }, "html");
	            });
	        }else if(type=="del"){
	            //console.log("del-colNo:"+colNo);
	            $.myAlert.Confirm("是否删除当前列？","提示信息",function() {
	                $.post("FormDataForToolBean/delColumn",
	                    {formId: formId, columnNum: colNo},
	                    function (data) {
	                        //console.log(data);
	                        if($.trim(data)=="0000"){
	                            initframe();
	                        } else {
	                            $.myAlert.Alert(data);
	                        }
	                    }, "html");
	            });
	        }
    	}else{
    		 $.myAlert.Alert("设计器标准为6列不可任意删除!");
    	}
    }
    /**
     * function:
     */
    window.rowCtrlFun = function(colNo,type){
        var formId = commonFunction.getFileName("form");
        colNo++;
        if(type=="add"){
            //console.log("add-colNo:"+colNo);
            if(tablePosition.isBartitle()){//判断是否存在标题
                colNo-=1;
            }
            if(formId!=null&&formId!=""&&formId!==undefined){
                $.myAlert.Confirm("是否在当前行增加一行？","提示信息",function() {
                    $.post("FormDataForToolBean/insertLine",
                        {formId:formId,lineNum:colNo},
                        function(data){
                            //console.log(data);
                            if($.trim(data)=="0000"){
                                initframe();
                            } else {
                                $.myAlert.Alert(data);
                            }
                        },"html");
                });
            }
        }else if(type=="del"){
            //console.log("del-colNo:"+colNo);
            if (tablePosition.isBartitle()) {//判断是否存在标题
                colNo -= 1;
            }
            if (formId != null && formId != "" && formId !== undefined) {
                $.myAlert.Confirm("是否删除当前行？","提示信息",function() {
                    $.post("FormDataForToolBean/delLine",
                        {formId: formId, lineNum: colNo},
                        function (data) {
                            //console.log(data);
                            if($.trim(data)=="0000"){
                                initframe();
                            } else {
                                $.myAlert.Alert(data);
                            }
                        }, "html");
                });
            }
        }
    }
    /**
     * function:获取改变行列数组
     * parameter:1.row(标签行次),
     *           2.labelCol(标签列次),
     *           3.fieldCol(字段列次),
     *           4.rowSpan(标签字段跨行数),
     *           5.labelColSpan(标签跨列数),
     *           6.fieldColSpan(字段跨列数)
     * return:数组
     */
    function getRowColArray(row,labelCol,fieldCol,rowSpan,labelColSpan,fieldColSpan){
        var rowColArray="";
            rowColArray+="row:"+row+",";
            rowColArray+="labelCol:"+labelCol+",";
            rowColArray+="fieldCol:"+fieldCol+",";
            rowColArray+="rowSpan:"+rowSpan+",";
            rowColArray+="labelColSpan:"+labelColSpan+",";
            rowColArray+="fieldColSpan:"+fieldColSpan;
        return rowColArray;
    }
    /**
     * function:改变跨行跨列
     * parameter:1.this(当前对象),
     *           2.type(类型){"plus"(增),"subtract"(减)}；
     *           3.rcFlag(行列标志){"row","col"};
     *           4.direction(方向){"up"(上),"down"(下),"left"(左),"right"(右)};
     */
    window.changRowCol = function(thisObj,type,rcFlag,direction){
        var alignFlag = tablePosition.isLeftOrRight($(thisObj).parents("td"));//label or input
        var groupFlag = tablePosition.isCombinationYN($(thisObj).parents("td"));//组合/非组合
        //console.log("type："+type+",rcFlag:"+rcFlag+",direction:"+direction+"--alignFlag:"+alignFlag+",groupFlag:"+groupFlag);
        var row=0,labelCol=0,fieldCol=0,rowSpan=0,labelColSpan=0,fieldColSpan=0;
        if(rcFlag=="row"){
            if(type=="plus"){
                if(direction=="down"){//向下加
                    rowSpan=1;
                }else if(direction=="up"){//向上加
                    row=-1;
                    rowSpan=1;
                }
            }else if(type=="subtract"){
                if(direction=="down"){//向下减
                    row=1;
                    rowSpan=-1;
                }else if(direction=="up"){//向上减
                    rowSpan=-1;
                }
            }
        }else if(rcFlag=="col") {
            if (alignFlag == "left") {//label
                if (type == "plus") {
                    if (direction == "left") {
                        labelCol = -1;
                        labelColSpan = 1;
                    } else if (direction == "right") {
                        labelColSpan = 1
                    }
                } else if (type == "subtract") {
                    if (direction == "left") {
                        if (groupFlag == true) {
                            fieldCol = -1;
                        }
                        labelColSpan = -1;
                    } else if (direction == "right") {
                        labelCol = 1;
                        labelColSpan = -1
                    }
                }
            }else if (alignFlag == "right") {
                if (type == "plus") {
                    if (direction == "left") {
                        fieldCol = -1;
                        fieldColSpan = 1;
                    } else if (direction == "right") {
                        fieldColSpan = 1
                    }
                } else if (type == "subtract") {
                    if (direction == "left") {
                        fieldColSpan = -1;
                    } else if (direction == "right") {
                        if (groupFlag == true) {
                            labelCol = 1;
                        }
                        fieldCol = 1;
                        fieldColSpan = -1
                    }
                }
            }
        }
        if(!(row==0&&labelCol==0&&fieldCol==0&&rowSpan==0&&labelColSpan==0&&fieldColSpan==0)){
            var formId = commonFunction.getFileName("form");
            var formActiveId = $.trim($(thisObj).parents("td").find(".drag").attr("id").split("_")[1]);
            $.post("FormDataForToolBean/updateFormElementForRowspanColspan",
                {formId:formId,formActiveId:formActiveId,formAttribute: getRowColArray(row, labelCol, fieldCol, rowSpan, labelColSpan, fieldColSpan)},
                function (data) {
                    if ($.trim(data) == "0000"||$.trim(data).indexOf("0000")!=-1) {
                        initframe();
                    }
                }
                , "html");
        }
    }
    $(function(){
        //获取主窗体宽度
        var mainTableWidth =$("#table2").width();
        //给属性框(formDataSetting、nav_content)赋值宽度
        //$(".formDataSetting").css("width", mainTableWidth+"px");
        $(".nav_content").css("width", mainTableWidth-100-5+"px");  //100为nav_left的宽度  10为原本padding的宽度
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
        //联动计算
        $(".calculation_btn").click(function () {
            var formTableFlag = commonFunction.isFormOrTable();
            var fileId = commonFunction.getFileName(formTableFlag);
            var setProperties = $(".setProperties").attr("formactiveid");
            if(setProperties===undefined||setProperties==""){
                setProperties="";
            }else{
                setProperties = $.trim(setProperties).split("_")[1];
            }
            if(formTableFlag=="form"&&fileId!=false&&setProperties!=""){
                $("#calculation").val( $("#calculationVal").attr("val"));
                $("#calculation").attr("calculationShow",$("#calculationVal").attr("calculationshow"));
                commonFunction.initInsertValues("calculationInsertSelect");
                showCover("#linkageCalculation");
            }else if(formTableFlag=="form"&&fileId==false){
                $.myAlert.Alert("请打开表单！");
            }else if(formTableFlag=="form"&&fileId!=false){
                $.myAlert.Alert("请打开字段！");
            }
        });
        //联动计算校验
        $("#validateFormula").click(function(){
            var calculationTextarea = $("#calculation");
            var valText = calculationTextarea.val();
            //var valText =  calculationTextarea.attr("calculationShow");
            var formId = commonFunction.getFileName("form");
            $.post("./FormDataForToolBean/checkCalculation",{formId:formId,calculation:valText},
                function(data){
                    if($.trim(data)=="0000"){
                        $.myAlert.Alert("校验成功","校验信息");
                    }else{
                        $.myAlert.Alert(data,"校验信息");
                    }
                },"html");
        });
        //联动计算确定
        $("#linkageCalculationConfirm").click(function(){
            var calculationTextarea = $("#calculation");
            var valText = calculationTextarea.val();
            var showText =  calculationTextarea.attr("calculationShow");
            var formId = commonFunction.getFileName("form");
            $.post("./FormDataForToolBean/checkCalculation",{formId:formId,calculation:valText},
                function(data){
                    if($.trim(data)=="0000"){
                        $("#calculationVal").attr("val",valText);
                        $("#calculationVal").attr("calculationShow",showText);
                        hideCover("#linkageCalculation");
                        $.myAlert.Alert("保存成功","提示信息");
                        formAttributeUpdate();//更新属性
                    }else{
                        $.myAlert.Alert(data,"校验信息");
                    }
                },"html");
        });
        $("#linkageCalculation .btnInput").click(function(){
            var calculationTextarea = $("#calculation");
            var calculationSelect = $("#linkageCalculation .a_select");
            var  valText= calculationTextarea.val();
            var  showText= calculationTextarea.attr("calculationShow");
            if($(this).text()=="←"){
                var option = calculationSelect.find(".showSelect li");
                var tmp="";
                var tmpVal="";
                for(var i=0;i<option.length;i++){//for-start
                    var opt = option.eq(i).attr("value");
                    var optionTmp = $.trim(option.eq(i).text());
                    var optVal = optionTmp.substr(0,optionTmp.lastIndexOf("("));
                    if(valText.length>=opt.length){
                        if(valText.substr(-opt.length,opt.length)==opt){
                            if(opt.length>tmp.length){
                                tmpVal= optVal;
                                tmp =opt;
                            }
                        }
                    }
                }//for-end
                var businessDateStr = "营业日期";
                var businessDateStrVal = "$txDt";
                //console.log("tmp|"+tmp+"|"+"tmpVal|"+tmpVal+"|");
                if(tmp==""){
                    if(valText.length>=businessDateStrVal.length&&valText.substr(-businessDateStrVal.length,businessDateStrVal.length)==businessDateStrVal){
                        //calculationTextarea.val(showText.substr(0,showText.length-businessDateStr.length));
                        //calculationTextarea.attr("calculationShow",valText.substr(0,showText.length-businessDateStrVal.length));
                        calculationTextarea.attr("calculationShow",showText.substr(0,showText.length-businessDateStr.length));
                        calculationTextarea.val(valText.substr(0,showText.length-businessDateStrVal.length));
                    }else{
                        //calculationTextarea.val(showText.substr(0,showText.length-1));
                        //calculationTextarea.attr("calculationShow",valText.substr(0,valText.length-1));
                        calculationTextarea.attr("calculationShow",showText.substr(0,showText.length-1));
                        calculationTextarea.val(valText.substr(0,valText.length-1));
                    }
                }else{
                    //calculationTextarea.val(showText.substr(0,showText.length-tmpVal.length));
                    //calculationTextarea.attr("calculationShow",valText.substr(0,valText.length-tmp.length));
                    calculationTextarea.attr("calculationShow",showText.substr(0,showText.length-tmpVal.length));
                    calculationTextarea.val(valText.substr(0,valText.length-tmp.length));
                }
            }else if($(this).attr("id")=="calculationInsert"){//插入
                var valueStr =$(this).parents(".btnCtrl").find(".a_select input").attr("val");
                var selects = $(this).parents(".btnCtrl").find(".a_select input").val();
                if(valueStr===undefined){
                    valueStr="";
                }
                var valueStrTmp = selects.substr(0,selects.lastIndexOf("("));
                //calculationTextarea.val(showText+valueStrTmp);
                //calculationTextarea.attr("calculationShow",valText+valueStr);
                calculationTextarea.attr("calculationShow",showText+valueStrTmp);
                calculationTextarea.val(valText+valueStr);
            }else if($(this).attr("id")=="businessDate"){//营业日期
                //calculationTextarea.val(calculationTextarea.val()+"营业日期");
                //calculationTextarea.attr("calculationShow",valText+"$txDt");
                calculationTextarea.attr("calculationShow",calculationTextarea.val()+"营业日期");
                calculationTextarea.val(valText+"$txDt");
            }else if($(this).hasClass("clearVal")){//清空
                $.myAlert.Confirm("确定要清空联动计算值吗？","清除提示",function(){
                    calculationTextarea.val("");
                    calculationTextarea.attr("calculationShow","");
                    calculationTextarea.focus();
                });
            }else{
                var valueTmp =$(this).text();
                if(valueTmp=="≥"){
                    valueTmp =">="
                }else if(valueTmp=="≤"){
                    valueTmp ="<="
                }
                calculationTextarea.attr("calculationShow",showText+valueTmp);
                calculationTextarea.val(valText+valueTmp);
            }
            calculationTextarea.focus();
            calculationTextarea.scrollTop(1000);
        });
    });
});