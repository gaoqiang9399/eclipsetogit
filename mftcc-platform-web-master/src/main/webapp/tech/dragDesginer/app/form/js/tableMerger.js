define(function(require, exports, module) {
    require("table/js/redips-table.js");
    var rightClick = require("./rightClick.js");
    var commonFunction = require("../../common/js/commonFunction.js");
    var tablePosition = require("./tablePosition.js")
    var redips = {},
        rt = REDIPS.table;
// REDIPS.table initialization
    redips.init = function () {
        // define reference to the REDIPS.table object
        rt.onmousedown('table2', true);
        // show cellIndex (it is nice for debugging)
        //rt.cell_index(true);
        // define background color for marked cell
        rt.color.cell = '#9BB3DA';
    };
// function merges table cells
    redips.merge = function () {
        // first merge cells horizontally and leave cells marked
        REDIPS.table.merge('h', false);
        // and then merge cells vertically and clear cells (second parameter is true by default)
        REDIPS.table.merge('v');
    };
// function splits table cells if colspan/rowspan is greater then 1
// mode is 'h' or 'v' (cells should be marked before)
    redips.split = function (mode) {
        REDIPS.table.split(mode);
    };
// insert/delete table row
    redips.row = function (type) {
        REDIPS.table.row('table2', type);

    };
// insert/delete table column
    redips.column = function (type) {
        REDIPS.table.column('table2', type);
    };
    /*合并行列*/
    window.merge = function(){
        redips.merge();
    }
    /*拆分*/
    window.split = function(obj){
        redips.split(obj);
        rightClick.rightClick();
    }
    /*追加行or删除行*/
    window.crtlRow = function(objStr){
        var formId = commonFunction.getFileName("form");
        if(formId!=false) {
            if(objStr=="insert") {
                appendRow("table2");
            }else if(objStr=="delete"){
                redips.row(objStr);
            }
            rightClick.rightClick();
            delToolInit();
        }else{
            $.myAlert.Alert("请打开表单！");
        }
    }
    /*追加一行*/
    function appendRow(objStr){
        objStr="#"+objStr;
        var tdLength = $(objStr).find("colgroup").find("col").length;
        var tmpStr = "<tr>";
        for(var i=0;i<tdLength;i++){
            tmpStr+="<td></td>";
        }
        tmpStr+="</tr>";
        $(objStr).find("tbody").find("tr").last().after(tmpStr);
    }
    /*追加一列*/
    function appendCol(objStr){
        objStr="#"+objStr;
        $(objStr).find("colgroup").append('<col>');
        $(objStr).find("tbody").find("tr").append('<td></td>');
        var tableWidth = $(document).width() * 0.85 * 0.9;//85%为table占屏幕宽度 90% 占table的宽度
        var tdLength = $("#table2").find("colgroup").find("col").length;
        var pTdWidth = tableWidth/(tdLength*20);
        var i=1;
        var cleraInt = setInterval(function(){
            $("#table2").find("tbody").find("tr").first().find("td").last().attr("width",(pTdWidth*++i).toFixed(1)+"px");
            if(i==20){
                clearInterval(cleraInt);
            }
        },1);
    }
    /*追加行列*/
    exports.appendRowCol = function(objStr){
        appendRow(objStr);
        appendCol(objStr);
    }
    /*移除行列*/
    exports.removeRowCol = function(objStr){
        objStr="#"+objStr;
        var trObj = $(objStr).find("tbody").find("tr").last();
        var tdLength = $(trObj).find("td").length;
        var flag = false;
        var i;
        for(i=0;i<=tdLength;i++){
            if($(trObj).find("td").eq(i).text().length>0){
                flag = true;
                break;
            }
        }
        if(!flag){
            trObj.remove();
        }
        flag = false;
        var trLength = $(objStr).find("tbody").find("tr").length;
        var trAllObj =  $(objStr).find("tbody").find("tr");
        for(i=0;i<=trLength;i++){
            if($(trAllObj).eq(i).find("td").last().text().length>0){
                flag = true;
                break;
            }
        }
        if(!flag){
            var tableWidth = $(document).width() * 0.85 * 0.9;//85%为table占屏幕宽度 90% 占table的宽度
            trLength = $("#table2").find("tbody").find("tr").length;
            tdLength= $("#table2").find("colgroup").find("col").length;
            var pTdWidth = tableWidth/(tdLength*20);
            var ctrlInt = 20;
            var cleraInt = setInterval(function(){
                $("#table2").find("tbody").find("tr").first().find("td").last().attr("width",(pTdWidth*--ctrlInt).toFixed(1)+"px");
                if(ctrlInt==1){
                    clearInterval(cleraInt);
                    $(objStr).find("colgroup").find("col").last().remove();
                    for(var i=0;i<=trLength;i++){
                        $(trAllObj).eq(i).find("td").last().remove();
                        //console.log("i:"+i);
                    }
                }
            },1);
        }
        flag = false;
    }
    /**
     * function:设置拖拽div具体宽度
     */
    exports.setDragWidth = function(obj){
        if($(obj).parents("#table2").length>0) {
            $(obj).css("opacity", "0.7");
            $(obj).css("filter", "alpha(opacity=70)");
            var tdLength = $(obj).parents("table").find("colgroup").find("col").length;
            var trLength = $(obj).parents("tbody").find("tr").length;
            $(obj).parents("tbody").find("tr").find("td").removeAttr("width");
            //var tableWidth = $(document).width() * 0.85 * 0.9;
            var tableWidth = $("#table2").width();
            var pWidth = tableWidth / tdLength;
            for(var i=0;i<trLength;i++){
                tdLength = $(obj).parents("tbody").find("tr").eq(i).find("td").length;
                for(var j=0;j<tdLength;j++){
                    var tdColspan= $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).attr("colspan");
                    /*if(tdColspan!==undefined&&tdColspan>1){
                        $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).find(".drag").css("width", tdColspan*pWidth+"px");
                    }else{
                        $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).find(".drag").css("width", pWidth+"px");
                    }*/
                    if(tdColspan!==undefined&&tdColspan>1){
                        $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).find(".drag").css("width", $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).width()+"px");
                    }else{
                        $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).find(".drag").css("width", $(obj).parents("tbody").find("tr").eq(i).find("td").eq(j).width()+"px");
                    }
                }
            }
            return pWidth;
        }else{
            return false;
        }
    }
    /****
     * function:追加组合另一半
     * @param obj,pWidth
     */
    exports.setAppendOther = function(obj,pWidth){
            var tdObj = $(obj).parents("td"),
                isCombination = tablePosition.isCombination(tdObj),
                leftColspan = 0,
                rightColspan = 0,
                isLeftOrRight = tablePosition.isLeftOrRight(tdObj);
        var leftWidth,topWidth;
            if (isCombination == false) {
                if ($(obj).find("textarea").length < 1) {
                    $(obj).find(".dataType").css({width: pWidth * rightColspan + "px", height: "34px"});
                    //$(obj).find(".dataType").css("padding-top", "4px");
                }
                if(isLeftOrRight=="left"){
                    leftColspan=$(tdObj).attr("colspan") !== undefined ? parseInt($(tdObj).attr("colspan")) : 1;
                }else if(isLeftOrRight=="right"){
                    rightColspan = $(tdObj).attr("colspan") !== undefined ? parseInt($(tdObj).attr("colspan")) : 1;
                }
            } else if (isCombination == "left") {
                leftColspan = $(tdObj).attr("colspan") !== undefined ? parseInt($(tdObj).attr("colspan")) : 1;
                rightColspan = $(tdObj).next("td").attr("colspan") !== undefined ? parseInt($(tdObj).next("td").attr("colspan")) : 1;
                leftWidth = $(obj).parents("td").offset().left;
                topWidth = $(obj).parents("td").offset().top+$(obj).parents("td").height()/2-14;
                $(obj).css("position","fixed");
                $(obj).css("left",leftWidth+"px");
                $(obj).css("top",topWidth+"px");
                $(obj).find(".dataType").after($(tdObj).next("td").find(".drag").html());
                $(tdObj).next("td").find(".drag").addClass("displayNone");
            } else if (isCombination == "right") {
                leftColspan = $(tdObj).prev("td").attr("colspan") !== undefined ? parseInt($(tdObj).prev("td").attr("colspan")) : 1;
                rightColspan = $(tdObj).attr("colspan") !== undefined ? parseInt($(tdObj).attr("colspan")) : 1;
                leftWidth = $(obj).parents("td").offset().left-pWidth;
                topWidth = $(obj).parents("td").offset().top+$(obj).parents("td").height()/2-14;
                $(obj).css("position","fixed");
                $(obj).css("left",leftWidth+"px");
                $(obj).css("top",topWidth+"px");
                $(obj).find(".dataType").before($(tdObj).prev("td").find(".drag").html());
                $(tdObj).prev("td").find(".drag").addClass("displayNone");
            }
            if (pWidth != false) {
                //console.log("pWidth:false");
                $(obj).css("width", pWidth * (leftColspan + rightColspan) + "px");
                if(isCombination == false){
                    if(isLeftOrRight=="left"){
                        $(obj).find(".dataType").css("width", pWidth * leftColspan + "px");
                    }else if(isLeftOrRight=="right"){
                        $(obj).find(".dataType").css("width", pWidth * rightColspan + "px");
                    }
                }else {
                    $(obj).find(".dataType").eq(0).css("width", (pWidth-1) * leftColspan + "px");
                    $(obj).find(".dataType").eq(1).css({width: (pWidth-1) * rightColspan + "px", height: "36px"});
                }
                //$(obj).find(".dataType").eq(1).css("padding-top", "4px");
                if ($(obj).find("textarea").length > 0) {
                    $(obj).find("textarea").css("height", "24px");
                }
            //return isCombination;
        }
    }
    /****
     * function:移除组合另一半
     * @param obj
     */
    exports.removeAppendOther = function(rd,isCombination,pWidth,droppedFlag){
        var obj=rd.obj;
        if($(rd.td.source).parents("#table2").length>0) {
            var tdcolspn = $(rd.td.source).attr("colspan")!==undefined?parseInt($(rd.td.source).attr("colspan")):1;
            $(obj).find(".dataType").css("height", "");
            $(obj).find(".dataType").css("width", "");
            if (isCombination == false) {
                //console.log("removeAppendOther:false");
                return;
            } else if (isCombination == "left") {
                $(obj).find(".dataType").eq(1).remove();
                $(obj).css("width",(tdcolspn*pWidth)+"px");
            } else if (isCombination == "right") {
                $(obj).find(".dataType").eq(0).remove();
                $(obj).css("width",(tdcolspn*pWidth)+"px");
            }
            if ($(obj).find("textarea").length > 0) {
                $(obj).find("textarea").css("height", "");
            }
            if(droppedFlag==false){
                $(".displayNone").removeClass("displayNone");
            }else {
                setTimeout(function () {
                    $(".displayNone").removeClass("displayNone");
                }, 100);
            }
        }
    }
    /**
     * function:重置拖拽div具体宽度
     */
    exports.resetDragWidth = function(obj){
        $(obj).css("opacity", "");
        var tbody = $("#table2").find("tbody");
        var trLength = $(tbody).find("tr").length;
        //设置td的width属性
        for(var i=0;i<trLength;i++){
            var tdObj = $(".mainContent #table2").find("tbody tr").eq(i);
            var tdLength = $(tdObj).find("td").length;
            for(var j=0;j<tdLength;j++){
                var tdWidth = $(tdObj).find("td").eq(j).find(".drag").attr("tdwidth");
                if(tdWidth!==undefined&&tdWidth!=""){
                    $(tdObj).find("td").eq(j).attr("width",tdWidth);
                }
                $(tdObj).find("td").eq(j).find(".drag").css("width","");
            }
        }
    }
    /*追加列or删除列*/
    window.crtlCol = function(obj){
        var formId = commonFunction.getFileName("form");
        if(formId!=false) {
            if (obj == "insert") {
                $("#table2 colgroup").append('<col>');
            } else if (obj == "delete") {
                if ($("#table2 colgroup").find("col").length != 1) {
                    $("#table2 colgroup").find("col").last().remove();
                }
            }
            redips.column(obj);
            rightClick.rightClick();
            delToolInit();
        }else{
            $.myAlert.Alert("请打开表单！");
        }
    }
    /*防止报错方法 开始*/
    window.enterKey = function(e){
        return false;
    }
    window.textareaInputCount= function(e){
        return false;
    }
    window.showCharsInfo= function(e){
        return false;
    }
    window.func_uior_valTypeImm= function(e){
        return false;
    }
    window.hideCharsInfo= function(e){
        return false;
    }
    /*结束*/
    $(function(){
        redips.init();
    });
});