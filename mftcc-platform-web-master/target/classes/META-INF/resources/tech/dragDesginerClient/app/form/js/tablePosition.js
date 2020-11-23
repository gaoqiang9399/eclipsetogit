/**
 * Created by jzh on 2015/2/4.
 * 表格定位核心js
 */
define(function(require, exports, module) {

    var commonFunction =  require("../../common/js/commonFunction.js");
    var formDataCtrl = require("./formDataCtrl.js");
    /**
     * function：拖动改变位置
     * parameter:formId(表单id),
     *           formActiveId(字段Id),
     *           pRow(拖动到的行),
     *           pCol(拖动到的列)
     */
    exports.dragChangePosition = function(formId,formActiveId,pRow,pCol) {
        var formAttribute = "{";
        //5.标签行次
        formAttribute+='"row":"'+pRow+'",';
        //6.标签列次
        formAttribute+='"labelCol":"'+ pCol+'",';
        //7.字段列次
        formAttribute+='"fieldCol":"0",';
        formAttribute+="}";
        $.post("./FormDataForToolBean/updateRowAndCol",
            {formId: formId,formActiveId: formActiveId,formAttribute:formAttribute},
            function (data) {
                //commonFunction.reloadFormOrTable();
                initframe();
            },"html");
    }
    /**
     * function：新增字段记录位置
     * parameter:formId(表单id),
     *           pRow(拖动到的行),
     *           pCol(拖动到的列)
     */
    exports.dragAddPosition = function(formId,pRow,pCol,pFieldType,dataType,pSelRange,labelColSpan,targetName,labelName,titleFlag){
        //1.row(行次)
        var row = pRow;
        //2.labelCol(标签列次)
        var labelCol = pCol;
        //3.fieldCol(字段列次)
        var fieldCol = pCol+1;
        if(targetName=="base"){
            fieldCol =pCol;
        }
        //4.fieldType(字段类型)
        var fieldType =pFieldType;
        //5.selRange(定义范围)
        var selRange = pSelRange;
        var formAttribute = formDataCtrl.getformAttributeDefault(row,labelCol,fieldCol,fieldType,dataType,selRange,labelColSpan,labelName,titleFlag);
        $.post("./FormDataForToolBean/insertFormElement",
            {formId: formId,formAttribute:formAttribute},
            function (data) {
                if($.trim(data)=="0000"){
                    //commonFunction.reloadFormOrTable();
                    initframe();
                }
            },"html");
    }
    /****
     * function:dragAddModelTool(添加到模版工具栏中)
     * @param formId
     * @param formActiveId
     */
    exports.dragAddModelTool = function(formId,formActiveId){
        $.post("FormDataForToolBean/insertFormModelElement",{formId:formId,formActiveId:formActiveId},
            function(data){
                if($.trim(data)=="0000"){
                    $.myAlert.Alert("添加成功！");
                    initframe();
                }else{
                    $.myAlert.Alert($.trim(data));
                }
            },"html");
    }
    /****
     * function:dragRmoveModelTool(从模版工具栏中移除)
     * @param formId
     * @param formActiveId
     */
    exports.dragRmoveModelTool = function(formId,fieldName){
        $.post("FormDataForToolBean/delFormModelElement",{formId:"model",fieldName:fieldName},
            function(data){
                if($.trim(data)=="0000"){
                    $.myAlert.Alert("移除成功！");
                    initframe();
                }else{
                    $.myAlert.Alert($.trim(data));
                }
            },"html");
    }
    exports.dragAddPositionForModelTool = function(formId,fieldName,rowNo,colNo){
        var formAttribute = "";
        $.post("FormDataForToolBean/getFormModelElement",{formId:formId,fieldName:fieldName,rowNo:rowNo,colNo:colNo},
            function(data){
                if($.trim(data)!=""){
                    formAttribute = JSON.stringify(data);
                    $.post("./FormDataForToolBean/insertFormElement",
                        {formId: formId,formAttribute:formAttribute},
                        function (data) {
                            if($.trim(data)=="0000"){
                                //commonFunction.reloadFormOrTable();
                                initframe();
                            }
                        },"html");
                }
            },"json");
    }


    /**
     * 方法：判断列是否完整
     * 参数：colObj(列对象),maxColLen(最大列长)
     * return： false(不完整)/true(完整)
     */
    exports.colComplete = function(colObj,maxColLen){
        var returnStr = false;
        //当前列所在行
        var rowObj = $(colObj).parent("tr");
        var tdCount = $(rowObj).children("td").length;
        if(tdCount==maxColLen){
            returnStr = true;
        }else{
            tdCount = getColCount(rowObj);
            if(tdCount==maxColLen){
                returnStr = true;
            }
        }
        return returnStr;
    }
    /**
     * function：获取完整的列的当前列索引（指定列）
     * parameter：1.trObj(行对象)  2.colIndex(索引)
     * return:返回数字类型（实际位置索引） 如果被合并返回(-1)
     */
    exports.getColIndexActualIndex = function(trObj,colIndex){
        var returnStr = 0;//返回值
        var rowTrIndex =  trObj.index()+1;//tr的索引值
        var maxTdLen = exports.getMaxTdLength();
        var arrayStr = traverseTable(trObj,maxTdLen);
        var colDel = [];
        var colDelIndex= 0;
        var i;
        for(i=0;i<arrayStr.length;i++){
            var rTmp = arrayStr[i].split(",");
            if(rTmp[0].split("_")[1]==rowTrIndex){
                colDel[colDelIndex++] = rTmp[1].split("_")[1];
            }
        }
        var tempCount = colIndex;
        for(i=0;i<colDel.length;i++){
            if(colDel[i]<colIndex){
                tempCount--;
            }else if(colDel[i]==colIndex){
                tempCount = -1;
                break;
            }
        }
        returnStr = tempCount;
        return returnStr;
    }
    /**
     * function：获取完整的列的当前列索引(当前列)
     * parameter：colObj(列对象)
     * return:返回数字类型
     */
    exports.getColIndex = function(colObj){
        var returnStr = 0;//返回值
        var rowObj = $(colObj).parent("tr");//tr对象
        var rowTrIndex =  rowObj.index()+1;//tr的索引值
        var colObjIndex = $(colObj).index()+1;
        var maxTdLen = exports.getMaxTdLength();
        var arrayStr = traverseTable(colObj,maxTdLen);
        var rowDel = [];
        var rowIndex = 0;
        var i;
        for(i=0;i<arrayStr.length;i++){
          var rTmp = arrayStr[i].split(",");
            if(rTmp[0].split("_")[1]==rowTrIndex){
                rowDel[rowIndex++] = rTmp[1].split("_")[1];
            }
        }
        var tempCount = 0;
        for(i=0;i<rowDel.length;i++){
            if(rowDel[i]<=colObjIndex+tempCount){
                tempCount++;
                //console.log(rowDel[i]);
            }
        }
        returnStr =colObjIndex+tempCount;
        return returnStr;
    }
    /**
     * function：获取完整的列的当前列索引
     * parameter：colObj(列对象)
     * return:返回数字类型
     */
    exports.getRowIndex = function(colObj){
        var rowObj = $(colObj).parent("tr");
        var returnStr = rowObj.index()+1;
        return returnStr;
    }
    /**
     * function:获取列合并数
     * parameter:当前对象
     * return:返回数值型
     */
    function getColSpan(obj){
        var returnStr = 1;
        if($(obj).attr("colspan")!==undefined){
            returnStr = parseInt($(obj).attr("colspan"));
        }
        return returnStr;
    }

    function getColCount(rowObj){
        var returnStr = 0;
        for(var i=0;i<rowObj.find("td").length;i++){
            returnStr+=getColSpan(rowObj.find("td").eq(i));
        }
        return returnStr;
    }

    /**
     * function:遍历table
     * parameter:table对象，
     *           maxColNum(最大列数)
     * return:被删除的Tb数组
     *
     * 备注：arrayStr
     * 格式：r_*,c_*
     */
    function traverseTable(obj,maxColNum){
        var arrayStr =[];
        var parentTbody = $(obj).parents("tbody");
        var parentTrNum = parentTbody.find("tr").length;
        //遍历tr
        for(var i=0;i<parentTrNum;i++){
            var trObj = $(parentTbody).find("tr").eq(i);
            var tdNum = trObj.find("td").length;
            //遍历td
            for(var j=0;j<tdNum;j++){
                var tdObj = $(trObj).find("td").eq(j);
                var delCount = tdIsDelcount(tdObj,arrayStr);
                arrayStr = getTdDel(tdObj,maxColNum,arrayStr,i+1,j+1+delCount);
            }
        }
      return arrayStr;
    }
    /**
     * function:获取被单元格合并删除的td
     * parameter:1.obj(td对象)
     *           2.maxColNum(最大列值)
     *           3.row行数
     *           4.col列数
     */
    function getTdDel(obj,maxColNum,arrayStr,row,col){
        //td没有被合并行
        var arr = 0;
        var colSpanObj = $(obj).attr("colspan");
        var rowSpanObj = $(obj).attr("rowspan");
        var i;

        if((rowSpanObj===undefined||rowSpanObj==1)&&colSpanObj!==undefined&&colSpanObj>1){//只有列合并
            for(i=1;i<colSpanObj;i++){
                arr = getArrLen(arrayStr);
                arrayStr[arr] = "r_"+row+",c_"+(col+i);
            }
        }else if((colSpanObj===undefined||colSpanObj==1)&&rowSpanObj!==undefined&&rowSpanObj>1){//只有行合并
            for(i=1;i<rowSpanObj;i++){
                arr = getArrLen(arrayStr);
                arrayStr[arr] = "r_"+(row+i)+",c_"+col;
            }
        }else if(colSpanObj!==undefined&&colSpanObj>1&&rowSpanObj!==undefined&&rowSpanObj>1){//行列都合并
            for(i=0;i<rowSpanObj;i++){
                var jTmp=1;
                if(i>0){
                    jTmp=0;
                }
                for(var j=jTmp;j<colSpanObj;j++){
                    arr = getArrLen(arrayStr);
                    arrayStr[arr] = "r_"+(row+i)+",c_"+(col+j);
                }
            }
        }
        return arrayStr;
    }
    /**
     * function:获取当前对象的td删除的个数
     * parameter:arrayStr删除数组
     */
    function tdIsDelcount(tdObj,arrayStr){
        var returnStr = 0;
        var rowNo = $(tdObj).parent("tr").index()+1;
        var colNo = $(tdObj).index()+1;
        var temp = 0;
        for(var i=0;i<arrayStr.length;i++){
            if(arrayStr[i].split(",")[0].split("_")[1]==rowNo){
                if(arrayStr[i].split(",")[1].split("_")[1]-1<=temp+colNo){
                    temp++;
                }
            }
        }
        returnStr = temp;
        return returnStr;
    }
    /**
     * function:计算字符串个数
     * parameter:字符串对象
     * return:字符串个数
     */
    function getArrLen(arrayStr){
        var returnStr = 0;
        if($(arrayStr).length>0){
            returnStr = $(arrayStr).length;
        }
        return returnStr;
    }
    /**
     * function:获取最大列
     * return:数值
     */
    exports.getMaxTdLength = function(){
        var returnStr = 0;
        if($("#table2").find("colgroup").find("col").length>0){
            returnStr =$("#table2").find("colgroup").find("col").length;
        }
        return returnStr;
    }
    /**
     * function：判断是否显示标题
     * return:false(不是)，true(是)
     */
    exports.isBartitle = function(){
        var returnStr =false;
        if($("#table2").find("tbody").find(".bartitle").length>0){
            returnStr = true;
        }
        return returnStr;
    }
    /**
     * function：判断left或right
     * parameter:td对象
     * return:left/right;
     */
    exports.isLeftOrRight = function(tdObj){
      return $.trim($(tdObj).find(".drag").attr("id").split("_")[0]);
    }
    /**
     * function：判断是否是组合
     * parameter:td对象
     * return:false(不是)，true(是)
     */
    exports.isCombinationYN = function(tdObj){
        var returnStr = false;
        var tdId = $.trim($(tdObj).find(".drag").attr("id").split("_")[1]);
        var tdAlign =  exports.isLeftOrRight(tdObj);
        if(tdAlign=="left"){
            var nextTdObj =$(tdObj).next("td");
            if($(nextTdObj).html()!==undefined&&isNotNull($(nextTdObj).html())==true){
                var nextTdId = $.trim($(nextTdObj).find(".drag").attr("id").split("_")[1]);
                if(tdId==nextTdId){
                    returnStr = true;
                }
            }
        }else if(tdAlign=="right"){
            var prevTdObj =$(tdObj).prev("td");
            if($(prevTdObj).html()!==undefined&&isNotNull($(prevTdObj).html())==true){
                var prevTdId = $.trim($(prevTdObj).find(".drag").attr("id").split("_")[1]);
                if(tdId==prevTdId){
                    returnStr = true;
                }
            }
        }
        return returnStr;
    }
    /**
     * function：判断是否是组合(或移置组合的left/right)
     * parameter:td对象
     * return:false(不是)，left/right(是)
     */
    exports.isCombination = function(tdObj){
        var returnStr = false;
        var tdId = $.trim($(tdObj).find(".drag").attr("id").split("_")[1]);
        var tdAlign =  exports.isLeftOrRight(tdObj);
        if(tdAlign=="left"){
            var nextTdObj =$(tdObj).next("td");
            if($(nextTdObj).html()!==undefined&&isNotNull($(nextTdObj).html())==true){
                var nextTdId = $.trim($(nextTdObj).find(".drag").attr("id").split("_")[1]);
                if(tdId==nextTdId){
                    returnStr = "left";
                }
            }
        }else if(tdAlign=="right"){
            var prevTdObj =$(tdObj).prev("td");
            if($(prevTdObj).html()!==undefined&&isNotNull($(prevTdObj).html())==true){
                var prevTdId = $.trim($(prevTdObj).find(".drag").attr("id").split("_")[1]);
                if(tdId==prevTdId){
                    returnStr = "right";
                }
            }
        }
        return returnStr;
    }
});
