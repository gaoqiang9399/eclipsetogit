/*table page 分页展现*/
define(function(require, exports, module) {
    var commonFunction = require("../../common/js/commonFunction.js");
    exports.initPage = function(type){
        if(type=="1"){
            $("#pageShow").css("display","block");
        }else if(type=="3"){
            $("#pageNoNumberShow").css("display","block");
        }else if(type=="4"){
            $("#pageShowNoGoSHow").css("display","block");
        }else if(type=="5"){
            $("#pageHidenNoGoSHow").css("display","block");
        }
    }
    function updateType(type){
        var tableId = commonFunction.getFileName("table");
        $.post("TableForToolBean/updateTableForType",{tableId:tableId,type:type},
        function(data){
            if($.trim(data)=="0000"){
            }
        },"html");
    }
    //页面加载执行
    $(function(){
        /*分页展现*/
        $("#pageShowWindows").click(function(){
            var fileName = commonFunction.getFileName();
            if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else{
                $("#pageNoNumberShow").slideUp("slow");
                $("#pageShowNoGoSHow").slideUp("slow");
                $("#pageHidenNoGoSHow").slideUp("slow");
                if($("#pageShow").css("display")=="none"){
                    updateType("1");
                }
                $("#pageShow").slideDown();
            }
        });
        $("#pageNoFilp").click(function(){
            var fileName = commonFunction.getFileName();
            if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else {
                if ($("#pageShow").css("display") == "block" ||
                    $("#pageNoNumberShow").css("display") == "block" ||
                    $("#pageShowNoGoSHow").css("display") == "block" ||
                    $("#pageHidenNoGoSHow").css("display") == "block") {
                    updateType("2");
                }
                $("#pageShow").slideUp("slow");
                $("#pageNoNumberShow").slideUp("slow");
                $("#pageShowNoGoSHow").slideUp("slow");
                $("#pageHidenNoGoSHow").slideUp("slow");
            }
        });
        $("#pageNoNumber").click(function(){
            var fileName = commonFunction.getFileName();
            if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else {
                $("#pageShow").slideUp("slow");
                $("#pageShowNoGoSHow").slideUp("slow");
                $("#pageHidenNoGoSHow").slideUp("slow");
                if ($("#pageNoNumberShow").css("display") == "none") {
                    updateType("3");
                }
                $("#pageNoNumberShow").slideDown();
            }
        });
        $("#pageShowNoGo").click(function(){
            var fileName = commonFunction.getFileName();
            if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else {
                $("#pageShow").slideUp("slow");
                $("#pageNoNumberShow").slideUp("slow");
                $("#pageHidenNoGoSHow").slideUp("slow");
                if ($("#pageShowNoGoSHow").css("display") == "none") {
                    updateType("4");
                }
                $("#pageShowNoGoSHow").slideDown();
            }
        });
        $("#pageHideNoGo").click(function(){
            var fileName = commonFunction.getFileName();
            if(fileName==false){
                $.myAlert.Alert("请打开一个列表！");
            }else {
                $("#pageShow").slideUp("slow");
                $("#pageNoNumberShow").slideUp("slow");
                $("#pageShowNoGoSHow").slideUp("slow");
                if ($("#pageHidenNoGoSHow").css("display") == "none") {
                    updateType("5");
                }
                $("#pageHidenNoGoSHow").slideDown();
            }
        });
    });
});