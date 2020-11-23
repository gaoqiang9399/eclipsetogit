/*table util 列表控制访法*/
define(function(require, exports, module) {
    var tableAttributeSetting = require("./tableAttributeSetting.js");
    /*
    * 作用：标签添加是新增列
    * 参数：rd(rd对象),parentsDiv(父层div)
    */
    exports.addColm = function(rd,parentsDiv){
        var cloneDisplayObj = $(rd.obj).find(".cloneDisplayObj");
        if(cloneDisplayObj.length != 0){
            var reallyObj = $(rd.obj).find(".reallyObj");
            rd.obj.innerHTML = $.trim(reallyObj.html());
        }
        var tableDarg = $(".mainContent #table2 tbody");
        var lastTdDiv = tableDarg.find("tr td").last().find("div").length;
        if(parentsDiv.parents(".left-nav").length==1){
            //判断目标内容是否为空
            if(lastTdDiv==0){
            }else{
                //新增列
                $(".mainContent #table2 tbody").find("tr").last("td").hover(function(){
                    //console.log("进");
                    $(this).find(".toolsHover").css("display","block");
                },function(){
                    $(this).find(".toolsHover").css("display","none");
                    //console.log("出");
                });
            }
        }
    }
    /*
    * 作用：鼠标单击
    * 参数：parentsDiv（父层div）
    * */
    exports.mosueClicks = function(parentsDiv){
        if(parentsDiv.parents(".mainContent").length==1){
            /*tableSetting is show*/
            if( $("#tableSetting").css("display")=="block"){
                if(parentsDiv.hasClass("drag")){
                    $("#leibm").val(parentsDiv.find(".leftborder2").text().trim());
                }
            }
        }
    }
    /*
    *作用：删除属性
    */
    window.toolsHoverDelete = function(el){
        $.myAlert.Confirm("确定删除该字段吗？","",function(){deleteAttributeConfirm(el)});
    }
    function deleteAttributeConfirm(el){
        //e.preventDefault();
        var item = $(el);
        if(item.parent().find(".drag").html()!==undefined){
            var tableListId = item.parent().find(".drag").attr("id");
            if(item.parents("tr").find("td").length==1){
                item.parents("td").html("");
            }else{
                item.parent("td").remove();
                $(".mainContent #table2 colgroup").find("col").last().remove();
                var count =   $(".mainContent #table2 colgroup").find("col").length;
                //$(".mainContent #table2 tbody td .drag").css("width",$(document).width()*0.85*0.9/count-1.5+"px");
            }
            tableAttributeSetting.deleteTableElement(tableListId);
        }
    }
    /**
     * function:获取类型
     * parameter:dataType类型
     */
    exports.getTypeFromDateType = function(dataType){
        var returnStr = "";
        var dataTypes = ["label-0","date-1","select-6","ckeckbox-2","link-4","button-5","cut-3","other-0"];
        for(var i=0;i<dataTypes.length;i++){
            if(dataType==dataTypes[i].split("-")[0]){
                returnStr = dataTypes[i].split("-")[1];
            }
        }
        return returnStr;
    }
    exports.setDragWidth = function(obj){
        if($(obj).parents("#table2").length>0){
            $(obj).css("opacity","0.7");
            $(obj).css("filter","alpha(opacity=70)");
            var trObj = $(obj).parents("tr");
            var tdLength = $(trObj).find("td").length;
            var percentWidth = 0;
            var pxWidth = 0;
            var percentTdCount = 0;
            var i;
            var tmpWidth;
            for(i=0;i<tdLength;i++){
                tmpWidth = $(trObj).find("td").eq(i).find(".drag").attr("tdwidth");
                if(tmpWidth!==undefined&&tmpWidth.indexOf("%")>-1){
                    percentWidth+=parseInt(tmpWidth.split("%")[0]);
                    percentTdCount++;
                }else if(tmpWidth!==undefined&&tmpWidth.indexOf("%")>-1){
                	pxWidth+=parseInt(tmpWidth.split("px")[0]);
                }
            }
            var tableWidth=  $("#table2").width();
            percentWidth = tableWidth*parseFloat(percentWidth)/100;
            var pWidth = tableWidth-percentWidth;
            //console.log("tableWidth:"+tableWidth+"-percentWidth:"+percentWidth+"-pWidth:"+pWidth);
            for(i=0;i<tdLength;i++){
                tmpWidth = $(trObj).find("td").eq(i).find(".drag").attr("tdwidth");
                if(tmpWidth!==undefined&&tmpWidth.indexOf("%")>-1) {
                    tmpWidth = parseFloat(tmpWidth.split("%")[0])/100;
                    $(trObj).find("td").eq(i).attr("width", (tmpWidth * tableWidth).toFixed(2) + "px");
                    $(trObj).find("td").eq(i).find(".drag").css("width", (tmpWidth * tableWidth).toFixed(2) + "px");
                }else if(tmpWidth!==undefined&&tmpWidth.indexOf("px")>-1){
                	$(trObj).find("td").eq(i).attr("width", tmpWidth);
                    $(trObj).find("td").eq(i).find(".drag").css("width",tmpWidth );
            	}else{
                    $(trObj).find("td").eq(i).find(".drag").css("width", (pWidth - pxWidth)/(tdLength-percentTdCount).toFixed(2) + "px");
                }
            }
        }
    }
    exports.resetDragWidth = function(){
        var trObj = $("#table2").find("tbody").find("tr");
        var tdLength = $(trObj).find("td").length;
        for (var i = 0; i < tdLength; i++) {
            var tmpWidth = $(trObj).find("td").eq(i).find(".drag").attr("tdwidth");
            $(trObj).find("td").eq(i).find(".drag").css("width", "");
            $(trObj).find("td").eq(i).find(".drag").css("opacity", "");
            $(trObj).find("td").eq(i).find(".drag").css("filter", "");
            $(trObj).find("td").eq(i).removeAttr("width");
            if (tmpWidth !== undefined) {
                //console.log(tmpWidth);
                $(trObj).find("td").eq(i).attr("width",tmpWidth);
            }
        }
    }
});