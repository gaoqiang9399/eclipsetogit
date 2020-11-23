define(function(require, exports, module) {
    var commonFunction = require("../../common/js/commonFunction.js");
    /*右键操作*/
    function rightClick(e,obj){
        var objParent = $(obj).parents("#table2");
        var formId = commonFunction.getFileName("form");
        if(formId!=false){
            if($(objParent).length==1){
                var x = e.pageX;
                var y = e.pageY;
                $("#rightPrompt").css({display:"block",top:y+"px",left:x+"px"});
                $(".rightClick").removeClass("rightClick");
                $(".rightClickTr").removeClass("rightClickTr");
                obj.parent().addClass("rightClickTr");
                obj.addClass("rightClick");
            }
        }else{
            $.myAlert.Alert("请打开表单！");
        }
    }
    /*合并列方法
    * 向右合并
    * */
    function colSpanRight(){
        var parent = $(".rightClick").parent();
        var index = getObjIndex($(".rightClick"),".rightClick","td");
        var len = $(parent).find("td").length;
        var colNum = 0;
        if(len>index+1){
            //当前列已经合并
            if($(".rightClick").attr("colspan")!==undefined){
                colNum+=new Number($(".rightClick").attr("colspan"));
                if($(parent).find("td").eq(index+1).attr("colspan")!==undefined) {//要合并的列也合并过
                    colNum += new Number($(parent).find("td").eq(index + 1).attr("colspan"));
                }else{
                    colNum+=1;
                }
            }else{//当前列没有合并
                if($(parent).find("td").eq(index+1).attr("colspan")!==undefined) {//要合并的列也合并过
                    colNum += new Number($(parent).find("td").eq(index + 1).attr("colspan"))+1;
                }else{
                    colNum+=2;
                }
            }
            $(parent).find("td").eq(index+1).remove();
            $(".rightClick").attr("colspan",colNum);
            $.myAlert.Alert("合并成功");
        }else{
            $.myAlert.Alert("右侧没有可合并的列");
        }
        //隐藏右键工具条
        displayNone();
    }
    /*合并列方法
     * 向左合并
     * */
    function colSpanLeft(){
        var parent = $(".rightClick").parent();
        var index = getObjIndex($(".rightClick"),".rightClick","td");
        var colNum = 0;
        if(index>0){
            if($(".rightClick").attr("colspan")!==undefined){
                colNum+=new Number($(".rightClick").attr("colspan"));
                if($(parent).find("td").eq(index-1).attr("colspan")!==undefined){
                    colNum += new Number($(parent).find("td").eq(index - 1).attr("colspan"));
                }else{
                    colNum+=1;
                }
            }else{
                if($(parent).find("td").eq(index-1).attr("colspan")!==undefined){
                    colNum += new Number($(parent).find("td").eq(index - 1).attr("colspan"))+1;
                }else{
                    colNum+=2;
                }
            }
            $(parent).find("td").eq(index-1).remove();
            $(".rightClick").attr("colspan",colNum);
            $.myAlert.Alert("合并成功！");
        }else{
            $.myAlert.Alert("左侧没有可合并的列！");
        }
        //隐藏右键工具条
        displayNone();
    }
    /*拆分列*/
    function colSplit(){
        var parent = $(".rightClick").parent();
        var index = getObjIndex($(".rightClick"),".rightClick","td");
        var colspan = $(".rightClick").attr("colspan");
        if(colspan=== undefined){
            myAlert.myAlert("该单元格没有合并过,不可拆分！");
        }else if(colspan>=2){
            $(".rightClick").removeAttr("colspan");
            for(var i=0;i<=colspan-2;i++){
                $(".rightClick").after('<td></td>');
                exports.objContextmenu($(parent).find("td").eq(index+1));
            }
            $.myAlert.Alert("拆分成功！");
        }else{
            $.myAlert.Alert("未知错误！");
        }
        //隐藏右键工具条
        displayNone();
    }
    /*合并行方法
    * 向下合并行
    * */
    function rowSpanDown(){
        var parent = $(".rightClick").parent();
        var trParent = $(".rightClickTr").parent("tbody");
        var index = getObjIndex($(".rightClick"),".rightClick","td");
        var trIndex = getObjIndex($(".rightClickTr"),".rightClickTr","tr");
        var len = $(trParent).find("tr").length;
        var colspan = $(".rightClick").attr("colspan");
        var rowspan = $(".rightClick").attr("rowspan");
        var rowNum = 0;
        var  tdTmp;
        //console.log("len:"+len+"  trIndex:"+trIndex);
        if(len>trIndex+1){
            for(var i=0;i<len;i++){
            }
            if(rowspan===undefined){//该对象没有合并过行
                tdTmp = $(trParent).find("tr").eq(trIndex+1).find("td").eq(index);
                if($(tdTmp).attr("rowspan")===undefined){
                    rowNum+=2;
                    $(tdTmp).remove();
                }else{
                    rowNum+=new Number($(tdTmp).attr("rowspan"))+1;
                    $(tdTmp).remove();
                }
            }else{
                rowNum+=new Number(rowspan);
                var rtIndexTmp =  trIndex+new Number(rowspan);
                tdTmp = $(trParent).find("tr").eq(rtIndexTmp).find("td").eq(index);
                if($(tdTmp).attr("rowspan")===undefined){
                    rowNum+=1;
                    $(tdTmp).remove();
                }else{
                    rowNum+=new Number($(tdTmp).attr("rowspan"));
                    $(tdTmp).remove();
                }
            }
            $(".rightClick").attr("rowspan",rowNum);
        }else{
            $.myAlert.Alert("向下不可合并已经是最后一个了！");
        }
        //隐藏右键工具条
        displayNone();
    }
    /*合并行方法
     * 向上合并行
     * */
    function rowSpanUp(){
        var parent = $(".rightClick").parent();
        var index = getObjIndex($(".rightClick"),".rightClick");
        //隐藏右键工具条
        displayNone();
    }

    /*拆分列*/
    function rowSplit(){
        var parent = $(".rightClick").parent();
        var index = getObjIndex($(".rightClick"),".rightClick");
        var len = $(parent).find("td").length;
        var colspan = $(".rightClick").attr("colspan");
        if(colspan=== undefined){
            $.myAlert.Alert("该单元格没有合并过,不可拆分！");
        }else if(colspan>=2){
            $(".rightClick").removeAttr("colspan");
            for(var i=0;i<=colspan-2;i++){
                $(parent).append('<td></td>');
                exports.objContextmenu($(parent).find("td:last"));
            }
            $.myAlert.Alert("拆分成功！");
        }else{
            $.myAlert.Alert("未知错误！");
        }
        //隐藏右键工具条
        displayNone();
    }
    /*获取对象的在父层index位置*/
    function getObjIndex(obj,classStr,type){
        var objParent = obj.parent();
        var objBrotherLen = $(objParent).find(type).length;
        var index = 0;
        var className = classStr.replace(".","");
        for(var i=0;i<objBrotherLen;i++){
            if(objParent.find(type).eq(i).attr("class")==className){
                index=i;
            }
        }
        return index;
    }
    /*隐藏右键工具条*/
    function displayNone(){
        $("#rightPrompt").css({display:"none"});
    }
    /*右键td绑定事件*/
    exports.objContextmenu = function (obj){
        $(obj).on("contextmenu", function (e) {
            rightClick(e,$(this));
            return false;
        });
    }
    /*右键重新绑定td*/
    exports.rightClick = function(){
        $("td").off("contextmenu");//td右击解绑
        $("td").on("contextmenu", function (e) {
            rightClick(e,$(this));
            return false;
        });
    }
    $(function(){
        $("td").on("contextmenu", function (e) {
            rightClick(e,$(this));
            return false
        });
        $(document).on("contextmenu", function (e) {
            return false
        });
        /*合并列-右侧*/
        $("#colSpanRight").click(function(){
            colSpanRight();
            $(".rightClick").removeClass("rightClick");
        });
        /*合并列-左侧*/
        $("#colSpanLeft").click(function(){
            colSpanLeft();
            $(".rightClick").removeClass("rightClick");
        });
        /*列拆分
        * 将合并两个个或多个的列拆分为原始状态
        * */
        $("#colSplit").click(function(){
            colSplit();
            $(".rightClick").removeClass("rightClick");
        });
        /*合并行-向下*/
        $("#rowSpanDown").click(function(){
            rowSpanDown();
            $(".rightClick").removeClass("rightClick");
        });
        /*合并行-向上*/
        $("#rowSpanUp").click(function(){
            rowSpanUp();
            $(".rightClick").removeClass("rightClick");
        });
        /*行拆分-将合并过个行进行拆分*/
        $("#rowSplit").click(function(){
            rowSplit();
            $(".rightClick").removeClass("rightClick");
        });
        /*鼠标点击其他位置 隐藏右键工具条*/
        $(document).click(function(e){
            if( $("#rightPrompt").css("display")=="block"){
                //隐藏右键工具条
                displayNone();
                $(".rightClick").removeClass("rightClick");
            }
        });
    });
});