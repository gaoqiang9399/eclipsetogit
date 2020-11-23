/**
 * Created by zhangzhuo on 2015/1/19.
 */

define(function (require, exports, module) {

    var rightClick = require("./rightClick.js");
    require("datetimepicker/js/jquery.datetimepicker.js");//日期框
    var tablePosition = require("./tablePosition.js");

    /*
     事件驱动分两种：
     1.从工具栏往列表中拖拽   ->  toolDataTypeEvent
     2.列表单元格间的拖拽     ->  tableDataTypeEvent
     */
    /*
     从工具栏往列表中拖拽
     */
    exports.toolDataTypeEvent = function (targetCell, rd, empty, dataType,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
        call(dataType, targetCell, rd, empty, "tool",rdLTdColspan,rdRTdColspan,rdTdRowspan);
    }

    /*根据方法名称调用方法
     * dragType: tool(工具栏拖拽) table(列表中拖拽)
     * */
    function call(functionName, targetCell, rd, empty,dragType,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
        if (dragType == "tool") {
            if (toolDataTypeMethod[functionName] !== undefined) {
                toolDataTypeMethod[functionName](targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
            }
        } else if (dragType == "table") {
            if (tableDataTypeMethod[functionName] !== undefined) {
                tableDataTypeMethod[functionName](targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
            }
        }
    }

    var toolDataTypeMethod = {
        //基本组件
        dragLable:function(targetCell, rd, empty){
            //dragBasicTool(targetCell, rd, empty);
        },
        dragInput:function(targetCell, rd, empty){
            dragBasicTool(targetCell, rd, empty);
        },
        dragSelect:function(targetCell, rd, empty){
            dragBasicTool(targetCell, rd, empty);
        },
        dragDate:function(targetCell, rd, empty){
            dragBasicTool(targetCell, rd, empty);
        },
        //组合input框
        dragInputGroup: function (targetCell, rd, empty,rdLTdColspan,rdRTdColspan) {
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        //组合select框
        dragSelectGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },//组合下拉框
        dragDateGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },//组合文本域
        dragTextAreaGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        dragCheckboxGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        dragRadioGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        dragPassWordGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        dragOtherGroup:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        },
        //标题
        dragBartop:function(targetCell, rd, empty){
            dragBartopTool(targetCell, rd);
        },
        dragCustom:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
            dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan);
        }
    }
    /*拖动组合工具栏*/
    function dragGropTool(targetCell, rd, empty,rdLTdColspan,rdRTdColspan){
        var checkFlag = checkNextCell(targetCell,rd,rdLTdColspan,rdRTdColspan);
        if (!checkFlag) {
            //目标单元格清空
            $(targetCell).empty();
        }else{
            var leftObj = $(rd.obj).find(".leftObj")[0].outerHTML;
            var rightObj = $(rd.obj).find(".rightObj")[0].outerHTML;
            rd.obj.innerHTML = leftObj;
            //获取组合右边内容
            var objNew = rd.cloneObject(rd.obj);
            objNew.innerHTML = rightObj;
            var nextCel = $(targetCell).next("td");
            $(nextCel).append(objNew);
            rightObjCss[getFunctionName(objNew)](objNew);
        }
    }
    /*拖动组合工具栏显示*/
    exports.dragGropToolShow = function(){

    }
    /*拖动基本工具栏*/
    function dragBasicTool(targetCell, rd, empty){
        var rightObj = $(rd.obj).find(".rightObj")[0].outerHTML;
        rd.obj.innerHTML = rightObj;
        var objNew = rd.cloneObject(rd.obj);
        objNew.innerHTML = rightObj;
        rightObjCss[getFunctionName(objNew)](objNew);
    }

    /*
     根据不同的组件类型执行对应操作   dateType
     */
    exports.tableDataTypeEvent = function (targetCell, rd, empty, dataType,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
        call(dataType, targetCell, rd, empty,"table",rdLTdColspan,rdRTdColspan,rdTdRowspan);
    }


    var rightObjCss = {
        dragSelect: function(obj){
            dragSelect(obj);
        },
        dragDate:function(obj){
            dragDate(obj);
        }
        ,
        default:function(obj) {
        }
    }

    /*根据方法名称调用方法*/
    var tableDataTypeMethod = {
        //-----------------------------组合组件-----------------------------//
        //拖动文本字段左侧
        dragInputGroupLeft: function (targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
            dragGroupLeft(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },//拖动文本字段右侧
        dragInputGroupRight: function (targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
            dragGroupRight(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },//拖动选择域左侧
        dragSelectGroupLeft:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
            dragGroupLeft(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },//拖动选择域右侧
        dragSelectGroupRight:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
            dragGroupRight(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },//拖动日期框左侧
        dragDateGroupLeft: function (targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
            dragGroupLeft(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },//拖动日期框右侧
        dragDateGroupRight: function (targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan) {
            dragGroupRight(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },
        dragTextAreaGroupRight:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
            dragGroupRight(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },
        dragTextAreaGroupLeft:function(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
            dragGroupLeft(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        },
        //标题
        dragBartop:function(targetCell, rd, empty){
            dragBartopTable(targetCell, rd);
        }
    }

    /****
     * 获取拖拽对象占的单元格坐标
     * @param trIndex
     * @param tdIndex
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     * @returns {{}}
     */
    function getTr_tdIndex(trIndex,tdIndex,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        var trtdArray = new Array();
        var index=0;
        for(var i=0;i<rdTdRowspan;i++){
            for(var j=0;j<rdLTdColspan+rdRTdColspan;j++){
                trtdArray[index++]=(trIndex+i)+"_"+(tdIndex+j);
            }
        }
        return trtdArray;
    }

    /***
     * function:判断合并行的内容可不可放（拖动左边的）
     * @param targetCell
     * @param rd
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     * @returns {boolean}
     */
    function checkNextTrandTdLeft(targetCell, rd,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        var tdIndex = tablePosition.getColIndex($(rd.obj).parents("td"));//真实td索引位置
        var trIndex = $(rd.obj).parents("tr").index();
        var trTdIndexArray = getTr_tdIndex($(rd.td.source).parents("tr").index(),$(rd.td.source).index(),rdLTdColspan,rdRTdColspan,rdTdRowspan);
        var trTdIndex="";
        var returnFlag = true;
        //var oldrdNextObj = $(rd.td.source).next().find(".drag");
        var i;
        var tdActualIndex,actualTdObj;
        if(rdTdRowspan==1){
            var trRdObj = $(targetCell).parents("tr");
            for(i=1;i<rdLTdColspan+rdRTdColspan;i++){//判断左侧的单元格可不可放置
                var temptdIndexL = tdIndex+i-1;
                tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexL);//显示列索引
                if (tdActualIndex == -1) {//目标列被合并
                    returnFlag = false;
                }else{
                    actualTdObj = $(trRdObj).find("td").eq(tdActualIndex-1);
                    if (actualTdObj.html()!== undefined) {
                        if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                            $.trim($(rd.obj).attr("id").split("_")[1])) {
                            returnFlag = false;
                            break;
                        }
                    } else {
                        returnFlag = false;
                    }
                }
                if(!returnFlag){
                    break;
                }
            }
        }
        var nextTrobj = $(rd.obj).parents("tr").next();
        for(i=1;i<rdTdRowspan;i++){
                var temptdIndex = tdIndex;
                if($(nextTrobj).html()!==undefined&&returnFlag) {
                    for(var j=1;j<=rdLTdColspan+rdRTdColspan;j++) {//卡列循环
                        tdActualIndex = tablePosition.getColIndexActualIndex(nextTrobj, temptdIndex);//显示列索引
                        trTdIndex = (trIndex+1)+"_"+(temptdIndex-1);
                        //var actualTdObj;
                        if (tdActualIndex == -1) {//目标列被合并
                            returnFlag = false;
                            var k= trTdIndexArray.length;
                            while(k>0){
                                if(trTdIndexArray[k-1]==trTdIndex){
                                    returnFlag = true;
                                }
                                k--;
                            }
                            if(!returnFlag){
                                break;
                            }
                        } else {
                            actualTdObj = $(nextTrobj).find("td").eq(tdActualIndex-1);
                            if (actualTdObj !== undefined) {
                                if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                    $.trim($(rd.obj).attr("id").split("_")[1])) {
                                    returnFlag = false;
                                    break;
                                }
                            } else {
                                returnFlag = false;
                                break;
                            }
                        }
                        temptdIndex++;
                    }
                }else{
                    returnFlag = false;
                    break;
                }
                nextTrobj = $(nextTrobj).next();
                trIndex++;
            }
        return returnFlag;
    }
    /***
     * function:判断合并行的内容可不可放显示阴影（拖动左边的）
     * @param targetCell
     * @param rd
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     * @returns {boolean}
     */
     exports.checkNextTrandTdLeftShow = function(tdObj,row,cell,rd,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        //var tdIndex = tablePosition.getColIndex($(tdObj).parents("td"));//真实td索引位置
         var tdIndex = tablePosition.getColIndex($(tdObj));
         var trIndex = row;
         var trTdIndexArray = getTr_tdIndex($(rd.td.source).parents("tr").index(), $(rd.td.source).index(), rdLTdColspan, rdRTdColspan, rdTdRowspan);
         var trTdIndex = "";
         var actualTrtdArray = [];
         var actualCount = 0;
         var returnFlag = true;
         var i;
         var tdActualIndex,actualTdObj;
         if(rdTdRowspan==1){
             var trRdObj = $(tdObj).parents("tr");
             for(i=1;i<rdLTdColspan+rdRTdColspan;i++){//判断左侧的单元格可不可放置
                 var temptdIndexL = tdIndex-1+i;
                 tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexL);//显示列索引
                 if (tdActualIndex == -1) {//目标列被合并
                     returnFlag = false;
                 }else{
                     actualTdObj = $(trRdObj).find("td").eq(tdActualIndex-1);
                     if (actualTdObj.html()!== undefined) {
                         if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                             $.trim($(rd.obj).attr("id").split("_")[1])) {
                             returnFlag = false;
                             break;
                         }
                     } else {
                         returnFlag = false;
                     }
                 }
                 if(!returnFlag){
                     break;
                 }
             }
         }
        var tdTrObj = $(tdObj).parents("tr");
         //校验可放部分
        for(i=0;i<rdTdRowspan;i++){
            var temptdIndex = tdIndex;
            if($(tdTrObj).html()!==undefined&&returnFlag) {
                for(var j=1;j<=rdLTdColspan+rdRTdColspan;j++) {//卡列循环
                    tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, temptdIndex);//显示列索引
                    trTdIndex = (trIndex+1)+"_"+temptdIndex;
                    //var actualTdObj;
                    //console.log("temptdIndex:"+temptdIndex+",tdActualIndex:"+tdActualIndex);
                    if (tdActualIndex == -1) {//目标列被合并
                        returnFlag = false;
                        var k= trTdIndexArray.length;
                        while(k>0){
                            //console.log(trTdIndexArray[k-1]+"=="+trTdIndex);
                            if(trTdIndexArray[k-1]==trTdIndex){
                                returnFlag = true;
                            }
                            k--;
                        }
                        if(!returnFlag){
                            break;
                        }
                    }else if(tdActualIndex == -2){
                        returnFlag = false;
                        break;
                    } else {
                        actualTdObj = $(tdTrObj).find("td").eq(tdActualIndex-1);
                        //console.log('actualTdObj = $(tdTrObj).find("td").eq('+(tdActualIndex)+')"');
                        if (actualTdObj.html()!== undefined) {
                            if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                $.trim($(rd.obj).attr("id").split("_")[1])) {
                                returnFlag = false;
                                break;
                            }else{
                                actualTrtdArray[actualCount++]=trIndex+"_"+(tdActualIndex-1);
                            }
                        } else {
                            returnFlag = false;
                            break;
                        }
                    }
                    temptdIndex++;
                }
            }else{
                returnFlag = false;
                break;
            }
            tdTrObj = $(tdTrObj).next();
            trIndex++;
        }
         //显示阴影部分
         if(returnFlag){
             //console.log(returnFlag);
             for(i=0;i<actualTrtdArray.length;i++){
                 var rowTmp=actualTrtdArray[i].split("_")[0];
                 var colTmp=actualTrtdArray[i].split("_")[1];
                 $(tdObj).parents("#table2").find("tr").eq(rowTmp).find("td").eq(colTmp).addClass("leftBroder");
             }
         }
         return returnFlag;

    }
    /***
     * function:判断合并行的内容可不可放（拖动右边的）
     * @param targetCell
     * @param rd
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     * @returns {boolean}
     */
    function checkNextTrandTdRight(targetCell, rd,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        var tdIndex = tablePosition.getColIndex($(rd.obj).parents("td"));//真实td索引位置
        var trIndex = $(rd.obj).parents("tr").index();
        var trTdIndexArray = getTr_tdIndex($(rd.td.source).parents("tr").index(),$(rd.td.source).prev().index(),rdLTdColspan,rdRTdColspan,rdTdRowspan);
        var trTdIndex="";
        var returnFlag = true;
        var i,j,k;
        var tdActualIndex,actualTdObj;
        //var oldrdNextObj = $(rd.td.source).next().find(".drag");
        if(rdTdRowspan==1){
            var trRdObj = $(targetCell).parents("tr");
            for(i=1;i<=rdLTdColspan;i++){//判断左侧的单元格可不可放置
                var temptdIndexL = tdIndex-i;
                tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexL);//显示列索引
                if (tdActualIndex == -1) {//目标列被合并
                    returnFlag = false;
                }else{
                    actualTdObj = $(trRdObj).find("td").eq(tdActualIndex-1);
                    if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                        if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                            $.trim($(rd.obj).attr("id").split("_")[1])) {
                            returnFlag = false;
                            break;
                        }
                    } else {
                        returnFlag = false;
                    }
                }
                if(!returnFlag){
                    break;
                }
            }
            for(i=1;i<rdRTdColspan;i++){//判断右侧的单元格可不可放置
                var temptdIndexR = tdIndex+i-1;
                tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexR);//显示列索引
                if (tdActualIndex == -1) {//目标列被合并
                    returnFlag = false;
                }else{
                    actualTdObj = $(trRdObj).find("td").eq(tdActualIndex+1);
                    if (actualTdObj.html()!== undefined) {
                        if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                            $.trim($(rd.obj).attr("id").split("_")[1])) {
                            returnFlag = false;
                            break;
                        }
                    } else {
                        returnFlag = false;
                    }
                }
                if(!returnFlag){
                    break;
                }
            }
        }
        var nextTrobj = $(rd.obj).parents("tr").next();
        for(i=1;i<rdTdRowspan;i++){
            var temptdIndex = tdIndex;
            if($(nextTrobj).html()!==undefined&&returnFlag) {
                for(j=1;j<=rdRTdColspan;j++) {//卡列循环
                    tdActualIndex = tablePosition.getColIndexActualIndex(nextTrobj, temptdIndex);//显示列索引
                    trTdIndex = (trIndex+1)+"_"+(temptdIndex-1);
                    //var actualTdObj;
                    if (tdActualIndex == -1) {//目标列被合并
                        returnFlag = false;
                        k= trTdIndexArray.length;
                        while(k>0){
                            if(trTdIndexArray[k-1]==trTdIndex){
                                returnFlag = true;
                            }
                            k--;
                        }
                        if(!returnFlag){
                            break;
                        }
                    } else {
                        actualTdObj = $(nextTrobj).find("td").eq(tdActualIndex-1);
                        if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                            if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                $.trim($(rd.obj).attr("id").split("_")[1])) {
                                returnFlag = false;
                                break;
                            }
                        } else {
                            returnFlag = false;
                            break;
                        }
                    }
                    temptdIndex--;
                }
                if(returnFlag){
                    temptdIndex = tdIndex-1;
                    for (j = 1; j <= rdLTdColspan; j++) {//卡列循环
                        tdActualIndex = tablePosition.getColIndexActualIndex(nextTrobj, temptdIndex);//显示列索引
                        trTdIndex = (trIndex+1) + "_" + (temptdIndex-1);
                        //var actualTdObj;
                        if (tdActualIndex == -1) {//目标列被合并
                            returnFlag = false;
                            k = trTdIndexArray.length;
                            while (k > 0) {
                                if (trTdIndexArray[k - 1] == trTdIndex) {
                                    returnFlag = true;
                                }
                                k--;
                            }
                            if (!returnFlag) {
                                break;
                            }
                        } else {
                            actualTdObj = $(nextTrobj).find("td").eq(tdActualIndex - 1);
                            if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                                if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                    $.trim($(rd.obj).attr("id").split("_")[1])) {
                                    returnFlag = false;
                                    break;
                                }
                            } else {
                                returnFlag = false;
                                break;
                            }
                        }
                        temptdIndex--;
                    }
                }
            }else{
                returnFlag = false;
                break;
            }
            nextTrobj = $(nextTrobj).next();
            trIndex++;
        }
        return returnFlag;
    }
    /***
     * function:判断合并行的内容可不可放显示阴影（拖动右边的）
     * @param targetCell
     * @param rd
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     * @returns {boolean}
     */
     exports.checkNextTrandTdRightShow  = function(tdObj,row,cell,rd,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        var tdIndex = tablePosition.getColIndex($(tdObj));//真实td索引位置
        var trIndex = row;
        var trTdIndexArray = getTr_tdIndex($(rd.td.source).parents("tr").index(),$(rd.td.source).prev().index(),rdLTdColspan,rdRTdColspan,rdTdRowspan);
        var trTdIndex="";
        var actualTrtdArray =[];
        var actualCount = 0;
        var returnFlag = true;
        var i,j,k;
        var tdActualIndex,actualTdObj;
         if(rdTdRowspan==1){
             var trRdObj = $(tdObj).parents("tr");
             for(i=1;i<=rdLTdColspan;i++){//判断左侧的单元格可不可放置
                 var temptdIndexL = tdIndex-i;
                 tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexL);//显示列索引
                 if (tdActualIndex == -1) {//目标列被合并
                     returnFlag = false;
                 }else{
                     actualTdObj = $(trRdObj).find("td").eq(tdActualIndex-1);
                     if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                         if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                             $.trim($(rd.obj).attr("id").split("_")[1])) {
                             returnFlag = false;
                             break;
                         }
                     } else {
                         returnFlag = false;
                     }
                 }
                 if(!returnFlag){
                     break;
                 }
             }
             for(i=1;i<rdRTdColspan;i++){//判断右侧的单元格可不可放置
                 var temptdIndexR = tdIndex+i-1;
                 tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexR);//显示列索引
                 if (tdActualIndex == -1) {//目标列被合并
                     returnFlag = false;
                 }else{
                     actualTdObj = $(trRdObj).find("td").eq(tdActualIndex+1);
                     if (actualTdObj.html()!== undefined) {
                         if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                             $.trim($(rd.obj).attr("id").split("_")[1])) {
                             returnFlag = false;
                             break;
                         }
                     } else {
                         returnFlag = false;
                     }
                 }
                 if(!returnFlag){
                     break;
                 }
             }
         }
        var tdTrObj = $(tdObj).parents("tr");
        for(i=0;i<rdTdRowspan;i++){
            var temptdIndex = tdIndex;
            if($(tdTrObj).html()!==undefined&&returnFlag) {
                for(j=1;j<=rdRTdColspan;j++) {//卡列循环
                    tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, temptdIndex);//显示列索引
                    trTdIndex = (trIndex+1)+"_"+temptdIndex;
                    //var actualTdObj;
                    if (tdActualIndex == -1) {//目标列被合并
                        returnFlag = false;
                        k= trTdIndexArray.length;
                        while(k>0){
                            if(trTdIndexArray[k-1]==trTdIndex){
                                returnFlag = true;
                            }
                            k--;
                        }
                        if(!returnFlag){
                            break;
                        }
                    }else if(tdActualIndex == -2){ returnFlag = false;  break;}  else {
                        actualTdObj = $(tdTrObj).find("td").eq(tdActualIndex-1);
                        if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                            if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                $.trim($(rd.obj).attr("id").split("_")[1])) {
                                returnFlag = false;
                                break;
                            }else{
                                actualTrtdArray[actualCount++]=trIndex+"_"+(tdActualIndex-1);
                            }
                        } else {
                            returnFlag = false;
                            break;
                        }
                    }
                    temptdIndex++;
                }
                if(returnFlag){
                    temptdIndex = tdIndex-1;
                    for (j = 1; j <= rdLTdColspan; j++) {//卡列循环
                        tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, temptdIndex);//显示列索引
                        trTdIndex = (trIndex+1) + "_" + temptdIndex;
                        //var actualTdObj;
                        if (tdActualIndex == -1) {//目标列被合并
                            returnFlag = false;
                            k = trTdIndexArray.length;
                            while (k > 0) {
                                if (trTdIndexArray[k - 1] == trTdIndex) {
                                    returnFlag = true;
                                }
                                k--;
                            }
                            if (!returnFlag) {
                                break;
                            }
                        } else {
                            actualTdObj = $(tdTrObj).find("td").eq(tdActualIndex - 1);
                            if (actualTdObj.html()!== undefined&&tdActualIndex-1>=0) {
                                if ($(actualTdObj).find(".drag").length != 0 && $.trim($(actualTdObj).find(".drag").attr("id").split("_")[1]) !=
                                    $.trim($(rd.obj).attr("id").split("_")[1])) {
                                    returnFlag = false;
                                    break;
                                }else{
                                    actualTrtdArray[actualCount++]=trIndex+"_"+(tdActualIndex-1);
                                }
                            }else if(tdActualIndex == -2){ returnFlag = false;  break;}  else {
                                returnFlag = false;
                                break;
                            }
                        }
                        temptdIndex--;
                    }
                }
            }else{
                returnFlag = false;
                break;
            }
            tdTrObj = $(tdTrObj).next();
            trIndex++;
        }
         //显示阴影部分
         if(returnFlag){
             for(i=0;i<actualTrtdArray.length;i++){
                 var rowTmp=actualTrtdArray[i].split("_")[0];
                 var colTmp=actualTrtdArray[i].split("_")[1];
                 $(tdObj).parents("#table2").find("tr").eq(rowTmp).find("td").eq(colTmp).addClass("leftBroder");
             }
         }
         return returnFlag;
    }
    /***
     * 判断控件是否可以放下显示(组合控件)
     * @param tdObj
     * @param row
     * @param cell
     * @param rd
     * @param rdLTdColspan
     * @param rdRTdColspan
     * @param rdTdRowspan
     */
    exports.checkDragShow = function(tdObj,row,cell,rd){
        var tdIndex = tablePosition.getColIndex($(tdObj));//真实td索引位置
        var returnFlag = true;
        var trRdObj = $(tdObj).parents("tr");
        var temptdIndexL = tdIndex+1;
        var tdActualIndex = tablePosition.getColIndexActualIndex(trRdObj, temptdIndexL);//显示列索引
        if (tdActualIndex == -1) {//目标列被合并
            returnFlag = false;
        }else{
            var actualTdObj = $(trRdObj).find("td").eq(tdActualIndex-1);
            if (actualTdObj.html()!== undefined) {
                if ($(actualTdObj).find(".drag").length != 0) {
                    returnFlag = false;
                }
            } else {
                returnFlag = false;
            }
        }
        return returnFlag;
    }


    /*拖动组合左侧*/
    function dragGroupLeft(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        var pos = rd.getPosition();
        //是否存在下一个td并且内容为空
        var trNextRowFlag = checkNextTrandTdLeft(targetCell, rd,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        if(!trNextRowFlag){
            $(targetCell).empty();
            $(rd.td.source).html(rd.obj);  //原来位置 -- 赋值
            return;
        }
        var checkFlag = checkNextCell(targetCell,rd,rdLTdColspan,rdRTdColspan);
        var rdDragObj = $(targetCell).find(".drag");
        var rdNextObj = $(rd.td.source).next("td").find(".drag");
        if (!checkFlag&&rdNextObj.html()!==undefined) {
            //目标单元格清空
            if($.trim($(rd.obj).attr("id").split("_")[1])== $.trim($(rdNextObj).attr("id").split("_")[1])&&
                $(rdDragObj).attr("id")!=$(rdNextObj).attr("id")){
                $(targetCell).empty();
                $(rd.td.source).html(rd.obj);  //原来位置 -- 赋值
            }
        }else if((rdLTdColspan>0||rdRTdColspan>0)&&(rdLTdColspan==0||rdRTdColspan==0)) {
            var rdTdColspan = 0;
            if(rdLTdColspan>1) {
                rdTdColspan = rdLTdColspan;
            }else if(rdRTdColspan>1){
                rdTdColspan = rdRTdColspan;
            }
            var nextObj =  $(targetCell).next();
            for(var i=1;i<rdTdColspan;i++) {
                if($(nextObj).html()!==undefined&&($(nextObj).find(".drag").length==0||$(nextObj).find(".drag").attr("id")==$(rd.td.source).attr("id"))){
                    nextObj = $(nextObj).next();
                }else{
                    $(targetCell).empty();
                    $(rd.td.source).html(rd.obj);
                    break;
                }
            }
        }else{
            //获取获取dragInputGroupRight对象
            var nextTargetCell = [pos[0], pos[1], pos[2] + 1];
            var nextDragInput = $(rd.td.source).next("td").find(".drag")[0];
            //移动右侧单元格
            if(rdNextObj.html()!==undefined){
                var rdNextId = $.trim($(rdNextObj).attr("id").split("_")[1]);
            }
            var rdId = $.trim($(rdDragObj).attr("id").split("_")[1]);
            if(rdId==rdNextId&&$(rdNextObj).attr("id")!=$(rdDragObj).attr("id")) {
                rd.moveObject(
                    {
                        obj: nextDragInput,
                        target: nextTargetCell
                    }
                );
            }
        }
    }
    /*拖动组合右侧*/
    function dragGroupRight(targetCell, rd, empty,rdLTdColspan,rdRTdColspan,rdTdRowspan){
        //console.log("dragGroupRight");
        var trNextRowFlag = checkNextTrandTdRight(targetCell, rd,rdLTdColspan,rdRTdColspan,rdTdRowspan);
        if(!trNextRowFlag){
            $(targetCell).empty();
            $(rd.td.source).html(rd.obj);  //原来位置 -- 赋值
            return;
        }
        var pos = rd.getPosition();
        //是否存在上一个td并且内容为空
        var checkFlag = checkPrevCell(targetCell,rd,rdLTdColspan,rdRTdColspan);
        var rdDragObj = $(targetCell).find(".drag");
        var rdPrevObj = $(rd.td.source).prev("td").find(".drag");
        if (!checkFlag&&rdPrevObj.html()!==undefined) {
            //目标单元格清空
            if($.trim($(rd.obj).attr("id").split("_")[1])==$.trim($(rdPrevObj).attr("id").split("_")[1])&&
            $(rdDragObj).attr("id")!=$(rdPrevObj).attr("id")) {
                $(targetCell).empty();
                $(rd.td.source).html(rd.obj);  //原来位置 -- 赋值
            }
        }else if((rdLTdColspan>0||rdRTdColspan>0)&&(rdLTdColspan==0||rdRTdColspan==0)) {
            var rdTdColspan = 0;
            if(rdLTdColspan>1) {
                rdTdColspan = rdLTdColspan;
            }else if(rdRTdColspan>1){
                rdTdColspan = rdRTdColspan;
            }
            var nextObj =  $(targetCell).next();
            for(var i=1;i<rdTdColspan;i++) {
                if($(nextObj).html()!==undefined&&($(nextObj).find(".drag").length==0||$(nextObj).find(".drag").attr("id")==$(rd.td.source).attr("id"))){
                    nextObj = $(nextObj).next();
                }else{
                    $(targetCell).empty();
                    $(rd.td.source).html(rd.obj);
                    break;
                }
            }
        }else{
            var prevTargetCell = [pos[0], pos[1], pos[2] - 1];
            //获取获取dragInputGroupRight对象
            var prevDragInput = $(rd.td.source).prev("td").find(".drag")[0];
            var rdId = $.trim($(rdDragObj).attr("id").split("_")[1]);
            //移动左侧单元格
            if(rdPrevObj.html()!==undefined){
                var rdPrevId = $.trim($(rdPrevObj).attr("id").split("_")[1]);
            }
            if(rdId==rdPrevId&&$(rdPrevObj).attr("id")!=$(rdDragObj).attr("id")) {
                rd.moveObject(
                    {
                        obj: prevDragInput,
                        target: prevTargetCell
                    }
                );
            }
        }
    }

    /*判断目标单元格是否存在下一个单元格（并且单元格中无内容）*/
    function checkNextCell(targetCell,rd,rdLTdColspan,rdRTdColspan) {
        var nextCell = $(targetCell).next();
        var nextCellHtml = $.trim($(nextCell).html());
        var objId= 0;
        if($(rd.obj).attr("id")!==undefined) {
            objId = $.trim($(rd.obj).attr("id").split("_")[1]);
        }
        var returnStr = true;
        var tdTrObj = $(targetCell).parents("tr");
        var temptdIndex = tablePosition.getColIndex($(targetCell));//真实td索引位置
        for(var i=1;i<(rdLTdColspan+rdRTdColspan);i++){
            var tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, ++temptdIndex);//显示列索引
            if(tdActualIndex==-1){
                returnStr=false;
                break;
            }else if (nextCell.length == 0&&nextCellHtml!="&nbsp;") {
                returnStr=false;
                break;
            }else{
                //console.log("nextCell.length:"+nextCell.length);
                //判断下一单元格中是否为空
                var nextCellObj = $.trim($(nextCell).html());
                if (nextCellObj!==undefined&&nextCellObj != ""&&nextCellHtml!="&nbsp;") {
                    var nextId = $.trim($(nextCell).find(".drag").attr("id").split("_")[1]);
                    if(objId!=nextId){
                        returnStr=false;
                        break;
                    }

                }
            }
            nextCell = $(nextCell).next();
        }
        //console.log("returnStr:"+returnStr+",rdLTdColspan:"+rdLTdColspan+",rdRTdColspan:"+rdRTdColspan);
        return returnStr;
    }
    /*判断目标单元格是否存在上一个单元格（并且单元格中无内容）*/
    function checkPrevCell(targetCell,rd,rdLTdColspan,rdRTdColspan) {
        var prevCell = $(targetCell).prev();
        var prevCellHtml = $.trim($(prevCell).html());
        //var nextCell =  $(targetCell).next();
        var objId= 0;
        if($(rd.obj).attr("id")!==undefined) {
            objId = $.trim($(rd.obj).attr("id").split("_")[1]);
        }
        var returnStr = true;
       /* for(var i=1;i<rdRTdColspan;i++){
            var nextCellObj = $.trim($(nextCell).html());
            if (nextCellObj != ""||$(nextCell).html()===undefined) {
                returnStr=false;
            }
            nextCell = $(nextCell).next();
        }*/
        var tdTrObj = $(targetCell).parents("tr");
        var tdIndex = tablePosition.getColIndex($(targetCell));//真实td索引位置
        var temptdIndex = tdIndex;
        var i;
        var tdActualIndex,prevCellObj,prevId;
        for(i=1;i<rdRTdColspan;i++){
            tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, ++temptdIndex);//显示列索引
            if(tdActualIndex==-1){
                returnStr=false;
                break;
            }else  if (prevCell.length == 0&&prevCellHtml!="&nbsp;") {
                return false;
            } else {
                //判断下一单元格中是否为空
                prevCellObj = $.trim($(prevCell).html());
                if (prevCellObj!==undefined&&prevCellObj != ""&&prevCellHtml!="&nbsp;") {
                    prevId = $.trim($(prevCell).find(".drag").attr("id").split("_")[1]);
                    if(objId!=prevId) {
                        return false;
                    }
                }
            }
            prevCell = $(prevCell).next();
        }
        if(returnStr){
            temptdIndex = tdIndex;
            for(i=1;i<rdLTdColspan;i++){
                tdActualIndex = tablePosition.getColIndexActualIndex(tdTrObj, --temptdIndex);//显示列索引
                if(tdActualIndex==-1){
                    returnStr=false;
                    break;
                }else  if (prevCell.length == 0&&prevCellHtml!="&nbsp;") {
                    return false;
                } else {
                    //判断下一单元格中是否为空
                    prevCellObj = $.trim($(prevCell).html());
                    if (prevCellObj!==undefined&&prevCellObj != ""&&prevCellHtml!="&nbsp;") {
                        prevId = $.trim($(prevCell).find(".drag").attr("id").split("_")[1]);
                        if(objId!=prevId) {
                            return false;
                        }
                    }
                }
                prevCell = $(prevCell).prev();
            }
        }
        return returnStr;
    }
    /*获取方法名*/
    function getFunctionName(obj){
        var returnStr = "";
        if($(obj).find("div>.groupSelect").length==1){
            returnStr="dragSelect"
        }else if($(obj).find("div>.groupDate").length==1){
            returnStr ="dragDate";
        }else if($(obj).find("div>.baseSelect").length==1){
            returnStr="dragSelect";
        }else if($(obj).find("div>.baseDate").length==1){
            returnStr="dragDate";
        }else{
            returnStr ="default";
        }
        return returnStr;
    }
    //下拉框
    function dragSelect(obj) {
        //如果已经被加载样式 则不加载
        var $dragSelectDiv = $(obj).find(".rightinput > div");
        if ($dragSelectDiv.hasClass("dropdown")) {
            return;
        } else {
            $(obj).find("select").easyDropDown();
        }
    }
    //日期框
    function dragDate(obj) {
        //如果已经被加载样式 则不加载
        var $dragDateDiv = $(obj).find(".rightinput > input");
        if ($dragDateDiv.hasClass("datelogo")) {
            return;
        } else {
            $(obj).find(".rightinput").find("input").addClass("datelogo");
            var $dragSelectDiv = $(obj).find(".rightinput > div");
            $(obj).find(".rightinput").find("input").datetimepicker();
        }
    }
    //标题栏
    function dragBartopTool(targetCell, rd) {
        //将所有td合并成一列
        //获取当前最大列数
        var maxCols = $(".mainContent col").length;
        $(targetCell).attr("colspan", maxCols).siblings().remove();
    }
    function dragBartopTable(targetCell, rd){
        $(rd.td.source).removeAttr("colspan");
        var maxCols = $(".mainContent col").length;
        for(var i=0;i<maxCols-1;i++){
            $(rd.td.source).after('<td></td>');
        }
        $(targetCell).attr("colspan", maxCols).siblings().remove();
        rightClick.rightClick();//重新绑定td监听事件
    }
});
