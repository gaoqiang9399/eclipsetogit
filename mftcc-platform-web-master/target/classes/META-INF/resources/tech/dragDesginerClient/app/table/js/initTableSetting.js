/**
 * Created by jzh on 2015/3/3.
 */

define(function(require, exports, module) {
    var commonFunction = require("../../common/js/commonFunction.js");
    var tablePage = require("./tablePage.js");
    /*页面初始化*/
    exports.initTableframe = function(){
        /*获取打开的页面的form名称
         * window.parent.window 获取父层内容
         * */
        var tableId = commonFunction.getFileName("table");
        if(tableId!=false){
            $.post("TableForToolBean/initTable", {tableId: tableId},
                function(data){
                    if(data!=null&&$.trim(data)!=""&&$.trim(data)!="&nbsp;") {
                        var re2 = new RegExp('&nbsp', "g");
                        data = data.replace(re2, "");
                        $(".mainContent #table2").find("tbody").html(data);
                        var tdObj = $(".mainContent #table2").find("tbody tr").first();
                        var tdLength = $(".mainContent #table2").find("tbody tr").first().find("td").length;
                        var thaed = "";
                        for(var i=0;i<tdLength;i++){
                            thaed+='<col>';
                           var tdWidth = $(tdObj).find("td").eq(i).find(".drag").attr("tdwidth");
                            if(tdWidth!==undefined){
                                $(tdObj).find("td").eq(i).attr("width",tdWidth);
                            }
                        }
                        /*动态修改列个数*/
                        $(".mainContent #table2").find("colgroup").html(thaed);
                    }
                    initTable();//表格初始化
                    initTrash();
                },"html");
            $.post("TableForToolBean/getTableSelfElement",{tableId:tableId},
                function(data) {
                    var type = data.type;
                    tablePage.initPage(type);
                },"json");
            $(".showHead").find("h1").text($.trim(window.parent.window.$("#designerName").val()));
        }
    }
});