/**
 * Created by jzh on 2015/1/28.
 */
define(function(require, exports, module) {
    var rightClick =require("./rightClick.js");
    var commonFunction = require("../../common/js/commonFunction.js");
    var console = require("./console.js");
    var tablePosition = require("./tablePosition.js");
    var validate = require("./validate.js");
    require("bootstrap/js/bootstrap.min.js");
    //当前行插入一行
    window.addCurrentRow = function(){
        var lineNum =  $(".rightClickTr").index()+1;
        if(tablePosition.isBartitle()){//判断是否存在标题
            lineNum-=1;
        }
        var formId = commonFunction.getFileName("form");
        if(formId!=null&&formId!=""&&formId!==undefined){
            $.post("FormDataForToolBean/insertLine",
                {formId:formId,lineNum:lineNum},
                function(data){
                    commonFunction.reloadFormOrTable();
                },"html");
        }
    }
    //删除当前行
    window.delCurrentRow = function(){
        $.myAlert.Confirm("是否删除当前行！","删除提示",function() {
            var lineNum = $(".rightClickTr").index() + 1;
            if (tablePosition.isBartitle()) {//判断是否存在标题
                lineNum -= 1;
            }
            var formId = commonFunction.getFileName("form");
            if (formId != null && formId != "" && formId !== undefined) {
                $.post("FormDataForToolBean/delLine",
                    {formId: formId, lineNum: lineNum},
                    function (data) {
                        commonFunction.reloadFormOrTable();
                    }, "html");
            }
        });
    }
    //当前列插入一列
    window.addCurrentCol = function(){
        var formId = commonFunction.getFileName("form");
        var colNum = tablePosition.getColIndex($(".rightClick"));
        $.post("FormDataForToolBean/insertColumn",
            {formId:formId,columnNum:colNum},
            function(){
                commonFunction.reloadFormOrTable();
        },"html");

    }
    //当前删除当前一列
    window.delCurrentCol = function(){
        var colNum = tablePosition.getColIndex($(".rightClick"));
        $.myAlert.Confirm("是否删除当前列！","删除提示",function() {
            var formId = commonFunction.getFileName("form");
            $.post("FormDataForToolBean/delColumn",
                {formId: formId, columnNum: colNum},
                function () {
                    commonFunction.reloadFormOrTable();
                }, "html");
            });
    }
    /*删除td中的内容*/
    window.delTdContent = function(){
        var rightClickObj = $(".rightClick");
        var formId = commonFunction.getFileName("form");
        if($(rightClickObj).find(".drag").html()!==undefined){
            var formActiveId = $.trim($(rightClickObj).find(".drag").attr("id").split("_")[1]);
            $.myAlert.Confirm("是否删除该字段！", "删除提示", function () {
                if ($(rightClickObj).find(".drag .dataType").length == 1) {//判断是否是组合组件
                    if ($(rightClickObj).find(".drag .leftObj").length == 1) {
                        $(rightClickObj).next("td").find(".drag").remove();
                        $(rightClickObj).find(".drag").remove();
                        $.myAlert.Alert("删除成功！");
                    } else {
                        $(rightClickObj).prev("td").find(".drag").remove();
                        $(rightClickObj).find(".drag").remove();
                        $.myAlert.Alert("删除成功！");
                    }
                } else {
                    $(rightClickObj).find(".drag").remove();
                }
                if (formActiveId !== undefined && formActiveId != "") {
                    $.post("./FormDataForToolBean/delFormElement",
                        {formId: formId, formActiveId: formActiveId},
                        function (data) {//返回结果
                            //myAlert.myAlert(data);
                            //commonFunction.reloadFormOrTable();
                            initframe();
                        }, "html");
                }
            });
        }else{
            $.myAlert.Alert("没有字段,不可删除！");
        }
    }
    /*设置为隐藏域*/
    window.setHidden= function(){
        var formId = commonFunction.getFileName("form");
        if($(".rightClick").find(".drag").html()!==undefined) {
            var formActiveId = $.trim($(".rightClick").find(".drag").attr("id").split("_")[1]);
            $.myAlert.Confirm("是否将该字段设为隐藏域！", "提示", function () {
                if (formActiveId !== undefined && formActiveId != "") {
                    $.post("./FormDataForToolBean/updateHide",
                        {formId: formId, formActiveId: formActiveId},
                        function (data) {//返回结果
                            if ($.trim(data) == "0000") {
                                commonFunction.reloadFormOrTable();
                            } else {
                                $.myAlert.Alert(data);
                            }
                        }, "html");
                }
                $(".rightClick").find(".drag").remove();
            });
        }else{
            $.myAlert.Alert("没有字段,不可隐藏！");
        }
    }
    window.addModelTool = function(){
        var formId = commonFunction.getFileName("form");
        if($(".rightClick").find(".drag").html()!==undefined) {
            var formActiveId = $.trim($(".rightClick").find(".drag").attr("id").split("_")[1]);
            $.myAlert.Confirm("是否将该字段加入自定义模板中！", "提示", function () {
                if (formActiveId !== undefined && formActiveId != "") {
                    tablePosition.dragAddModelTool(formId,formActiveId);
                }
                $(".rightClick").find(".drag").remove();
            });
        }else{
            $.myAlert.Alert("没有字段,不可加入模板！");
        }
    }
    window.removeSelectTd = function(){
        $(".clickTdSelect").removeClass("clickTdSelect");
        $(".clickTdSelectL").removeClass("clickTdSelectL");
        $(".clickTdSelectR").removeClass("clickTdSelectR");
    }
    /***
     * 设置已选中的单元格
     */
    window.setSelectTd = function (rdObj,isCombination){
        removeSelectTd();
        var tdObj = $(rdObj).parents("td");
        if(isCombination=="left"){
            $(tdObj).addClass("clickTdSelectL");
            $(tdObj.next()).addClass("clickTdSelectR");
        }else if(isCombination=="right"){
            $(tdObj).addClass("clickTdSelectR");
            $(tdObj.prev()).addClass("clickTdSelectL");
        }else{
            $(tdObj).addClass("clickTdSelect");
        }
    }
    /***
     * function：初始化自定义工具栏
     */
    window.initCustomTool = function(){
        $.post("FormDataForToolBean/getFormModelToolElement",{},
            function(data){
                    $("#collapseFour").find(".list-table").html(data);
            }, "html");
    }
    //删除工具栏的初始化
     window.delToolInit = function(){
        //列删除增加
        var trObj = $("#table2").find("tbody").find("tr");
        //var tableHight = $("#table2").height();
        var trLength =$(trObj).length;
        var firstTdLength = $(trObj).eq(0).find("td").length;
        var tdHeadLength =  $(".mainContent #table2").find("colgroup").find("col").length;
        var flagHeight = 0;
        var flagWidth = 0;
        var tdStrArr = {};
        var i;
        for(i=0;i<firstTdLength;i++){
            flagWidth+=$(trObj).eq(0).find("td").eq(i).width()+1;
        }
        for(i=0;i<trLength;i++){
            flagHeight+=$(trObj).eq(i).find("td").first().height()+1;
            tdStrArr[i]=$(trObj).eq(i).find("td").first().height()+1;
        }
        $("#delRowCtrl").css("margin-top",-$("#table2").height()-8);
        $("#delRowCtrl").css("height",flagHeight);
        var delColCtrlStr = "";
        var spanWidth = flagWidth/tdHeadLength;
        var colStrInt = 0;
        while(tdHeadLength-->0){
            delColCtrlStr += '<div class="delColCtrl" style="width:'+(spanWidth-1)+
            'px;"><span onclick="colCtrlFun('+colStrInt+',\'del\')" '+
            'class="fa fa-minus-circle delFlag" style="margin-left:'+(spanWidth/2-12)+'px;"></span>'+
            '<span onclick="colCtrlFun('+colStrInt+',\'add\')"'+
            ' class="fa fa-plus-circle"></span></div>';
            colStrInt++;
        }
        $("#delColCtrl").html(delColCtrlStr).show();
        $("#delColCtrl").css("width",flagWidth+"px");
        //行删除增加
        var delRowCtrlStr = "";
        var tdStrInt = 0;
        while(trLength-->0){
            delRowCtrlStr += '<div class="delRowCtrl" style="width:8px;height: '+tdStrArr[tdStrInt]+'px;">'+
            '<span onclick="rowCtrlFun('+tdStrInt+',\'del\')" class="fa fa-minus-circle" style="top:'+
            ((tdStrArr[tdStrInt]-30>0?tdStrArr[tdStrInt]-30:0)/2)+'px"></span>'+
            '<span onclick="rowCtrlFun('+tdStrInt+',\'add\')" class="fa fa-plus-circle" style="top:'+
            ((tdStrArr[tdStrInt]-30>0?tdStrArr[tdStrInt]-30:0)/2)+'px"></span></div>';
            tdStrInt++;
        }
        $("#delRowCtrl").html(delRowCtrlStr);
    }
    $(function(){
        $(".initFormSetting").hover(function(){
            $("#firstSelectedTd").css(background-color,"red");
        });
    });
    /*页面初始化*/
    window.initframe = function(){
        /*获取打开的页面的form名称
         * window.parent.window 获取父层内容
         * */
        //window.console.log("页面初始化");
         var formName = commonFunction.getFileName("form");
        if(formName!=false){
            $.post("./FormDataForToolBean/getFormAllElement", {formId: formName},
                function(data){
                    /*formName存在显示内容*/
                    if(data!=null&&$.trim(data)!=""&&$.trim(data)!="&nbsp;"){
                        var re2 = new RegExp('&nbsp',"g");
                        data =data.replace(re2,"");
                        $(".mainContent #table2").find("tbody").html(data);
                        var trObj = $(".mainContent #table2").find("tbody tr").first();
                        var trLength = $(".mainContent #table2").find("tbody tr").length;
                        var tdLength = $(trObj).first().find("td").length;
                        var tdNum = 0;
                        var thaed = "";
                        var i;
                        for(i=0;i<tdLength;i++){
                            var colspanNum =1;
                            if(trObj.children('td').eq(i).attr("colspan")!==undefined){
                                colspanNum = trObj.children('td').eq(i).attr("colspan");
                            }
                            tdNum+= parseInt(colspanNum);
                        }
                        //设置td的width属性
                        for(i=0;i<trLength;i++){
                            var tdObj = $(".mainContent #table2").find("tbody tr").eq(i);
                            tdLength = $(tdObj).find("td").length;
                            for(var j=0;j<tdLength;j++){
                                var tdWidth = $(tdObj).find("td").eq(j).find(".drag").attr("tdwidth");
                                if(tdWidth!==undefined&&tdWidth!=""){
                                    $(tdObj).find("td").eq(j).attr("width",tdWidth);
                                }
                            }
                             /*if(tablePosition.getColIndexActualIndex(tdObj,$(tdObj).find("td").first().index()+1)==1){
                                tdObj.find("td").first().css("border-left","3px solid #f3cc5a");
                             }*/
                        }
                         if(tdNum<6){
                        	$(".mainContent #table2").find("tbody tr").each(function(){
                        		for(i=0;i<6-tdNum;i++){
                        			$(this).append("<td></td>");
                        		}
                        	});
                        }
                        var appendStr = "<tr>";
                        for(i=0;i<(tdNum>6?tdNum:6);i++){
                            thaed+='<col>';
                            appendStr+="<td></td>"
                            /*if(i==0){
                            	// appendStr+="<td style=\"border-left:3px solid #f3cc5a;\"></td>"
                            }else{
                            	 appendStr+="<td></td>"
                            }*/
                        }
                        appendStr+="</tr>";
                    }
                    /*动态修改列个数*/
                    $(".mainContent #table2").find("colgroup").html(thaed);
                    //$(".mainContent #table2").find("tbody").find("tr").last().find("td").first().css("border-left","3px solid #f3cc5a");
                    $(".mainContent #table2").find("tbody").append(appendStr);
                    initCustomTool();
                    //$("select").easyDropDown({
                    //    cutOff: 10});
                    $("#table2 tbody td").each(function(){
                        if($(this).find(".drag>div").hasClass("leftborder1")){
                            $(this).addClass("tdleftborder");
                        }
                    });
                    if($("#table2").height()>$("body").height()-155){
                        $(".tableShow").css("height",($("body").height()-155)+"px");
                    }else{
                        $(".tableShow").css("height","auto");
                    }
                    rightClick.rightClick();//右键事件绑定
                    setTimeout(initForm,500);//表格初始化
                    $(".showHead").find("h1").text($.trim(window.parent.window.$("#designerName").val()));
                    //删除工具栏的初始化
                    delToolInit();
                    initChange_tab();
                    //初始化加载控制台
                    console.initConsole(formName);
                },"html");

        }
    }
});