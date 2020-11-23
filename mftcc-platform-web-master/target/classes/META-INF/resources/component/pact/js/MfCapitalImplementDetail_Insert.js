;
var MfCapitalImplementDetail_Insert = function(window, $) {
    // 初始化
    var _init = function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
    };
    var _myclose = function () {
        if (typeof entrance == "undefined") {
            entrance = '';
        }
        if(entrance!='loanAfter'){
            myclose_task();
        }else{
            myclose_click();
        }
    }
    //登记资金落实
    var _capitalInput = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url,"资金落实详情",function(){
            _myclose();
            var url=webPath+"/mfBusFincApp/getCapitalImplementListPage";
            window.location.href=url;
        });
    }
    //资金落实详情
    var _capitalDetail = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url,"登记资金落实",function(){
            _myclose();
        });
    }
    var _ajaxSave = function (obj) {
        if (typeof entrance == "undefined") {
            entrance = '';
        }
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url: url,
                data: {ajaxData: dataParam},
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                    LoadingAnimate.start();
                }, success: function (data) {
                    if (data.flag == "success") {
                        if(entrance!='loanAfter'){
                            window.top.alert(data.msg,3,function(){
                                var url=webPath+"/sysTaskInfo/getListPage?pasMaxNo=7";
                                $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
                                setTimeout(function() {
                                    $(top.window.document).find("#taskShowDialog").remove();
                                }, 100);
                            });
                        }else{
                            var url=webPath+"/mfBusFincApp/getCapitalImplementListPage";
                            myclose_click();
                            window.location.href=url;

                        }
                    } else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }, complete: function () {
                    LoadingAnimate.stop();

                }
            });
        }
    };
   //待办任务完成后的处理
    var _closePop = function (){
        var dm = document.getElementById("b1_iframe").contentWindow;
        if($(dm.document).find(".task_select").length>0){
            var $task = $(dm.document).find(".task_select");
            var data = $task.data("info");
            $task.removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
                $(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
                    $(this).empty();
                    document.getElementById("b1_iframe").contentWindow.mcSelector.mCustomScrollbar("update");
                });
            });
            document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
        }
        //同步更新待办任务的条数
        if($("#task_count").length>0){
            var curCount = $("#count_input").val();
            curCount = curCount*1-1;
            if(curCount>=0 && curCount<100){
                if(curCount==0){
                    $("#task_count").text("");
                }else{
                    $("#task_count").text(curCount);
                }
            }else if(curCount<0){
                $("#task_count").text("");
            }
            $("#count_input").val(curCount);
        }
    };
    var  _getDetailPage=function(obj, url) {
        if (url.substr(0, 1) == "/") {
            url = webPath + url;
        } else {
            url = webPath + "/" + url;
        }
        top.openBigForm(url, "客户详情", function () {
        });
    };
    var  _getPactDetailPage=function(obj, url) {
        if (url.substr(0, 1) == "/") {
            url = webPath + url;
        } else {
            url = webPath + "/" + url;
        }
        top.openBigForm(url, "合同详情", function () {
        });
    };
    return {
        init : _init,
        closePop:_closePop,
        myclose:_myclose,
        capitalInput:_capitalInput,
        capitalDetail:_capitalDetail,
        ajaxSave : _ajaxSave,
        getDetailPage:_getDetailPage,
        getPactDetailPage:_getPactDetailPage
    };
}(window, jQuery);