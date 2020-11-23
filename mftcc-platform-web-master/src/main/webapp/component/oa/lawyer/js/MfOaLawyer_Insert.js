;
var MfOaLawyer_Insert = function (window, $) {
    var _init = function () {
        $(".scroll-content").mCustomScrollbar({//滚动条的生成
            advanced: {
                theme: "minimal-dark",
                updateOnContentResize: true
            }
        });
    };

    var _myclose = function () {//关闭当前弹窗的方法
        myclose_click();//关闭弹窗
        window.location.href = webPath + "/mfOaLawyer/getListPage";//重新刷新列表
    };
    //demo新增
    var _ajaxSave = function (obj) {
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
                        alert(data.msg, 1);
                        myclose_click();//保存完成之后关闭弹窗，回到列表
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }, complete: function () {
                    LoadingAnimate.stop();
                }
            });
        }
    };

    //结束时间和开始时间校验
    var _judgeHireStartEndDate = function () {
        var startTime = $("input[name='hireStartTime']").val();
        var endTime = $("input[name='hireEndTime']").val();
        if (startTime != "" && endTime != "") {
            var startStr = startTime.split("-");
            var endStr = endTime.split("-");
            var startDate = new Date(startStr[0], startStr[1], startStr[2]);
            var endDate = new Date(endStr[0], endStr[1], endStr[2]);
            if (startDate.getTime() > endDate.getTime()) {
                window.top.alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":"结束日期","timeTwo":"开始日期"}),0,function(){
                    $("input[name='hireStartTime']").val("");
                    $("input[name='hireEndTime']").val("");
                });
            }
        }
    };


    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init,
        ajaxSave: _ajaxSave,
        myclose: _myclose,
        judgeHireStartEndDate: _judgeHireStartEndDate
    };
}(window, jQuery);
