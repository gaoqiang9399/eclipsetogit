;
var MfCusCustomerShare = function(window, $) {
    var opNo;
    var cusMngNo;
    var _init = function(){
        _getCusCustomerShareList();
        $(function () {
            myCustomScrollbarForForm({
                obj:".container",
                advanced : {
                    theme : "minimal-dark",
                    updateOnContentResize : true
                }
            });
        })
    };

    //获取客户共享列表
    var _openCusCustomerShare = function(){
        top.window.openBigForm(webPath+"/mfCusCustomerShare/getListPage?cusNo="+cusNo,"客户共享",function(){
            _changeShareStatus(cusNo);
        });
    };

    //新增客户共享页面
    var _openInputPage=function () {
        if(opNo === cusMngNo){
            top.window.openBigForm(webPath+"/mfCusCustomerShare/input?cusNo="+cusNo,"新增客户共享",function(){
                _getCusCustomerShareList();
            });
        }else{
            window.top.alert('您不是此客户项目经理，不能共享此客户！', 0);
        }
    };

    //新增客户共享信息
    var _insert=function (dom) {
        var flag = submitJsMethod($(dom).get(0), '');
        if(flag){
            var url = $(dom).attr("action");
            var dataParam = JSON.stringify($(dom).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data : {
                    ajaxData : dataParam
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        alert(data.msg, 1);
                        $(top.window.document).find(".dhccModalDialog").eq($(top.window.document).find(".dhccModalDialog").length-1).find(".i-x5").click();
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("ERROR_REQUEST_URL", url));
                }
            });
        }
    };

    //获取共享状态
    var _changeShareStatus=function (cusNo){
        $.ajax({
            url: webPath + "/mfCusCustomerShare/getCusCustomerShareStatus",
            data: {cusNo: cusNo},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    if(data.status === '1'){
                        $("#cusCustomerShare").removeClass("btn-lightgray");// 去掉灰色样式
                        $("#cusCustomerShare").addClass("btn-dodgerblue");// 添加蓝色
                    }else if(data.status === '0'){
                        $("#cusCustomerShare").removeClass("btn-dodgerblue");// 去掉蓝
                        $("#cusCustomerShare").addClass("btn-lightgray");// 添加灰色
                    }
                }
            }
        });
    };

    //获取客户共享列表
    var _getCusCustomerShareList=function (){
        $.ajax({
            url: webPath + "/mfCusCustomerShare/getCusCustomerShareList",
            data: {cusNo: cusNo},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    $("#cusCustomerShare").html(data.tableHtml);
                    cusMngNo = data.cusMngNo;
                    opNo = data.opNo;

                }
            }
        });
    };

    //删除共享
    var _deleteById=function (obj,url) {
        alert("是否删除共享项目经理信息？", 2, function() {
            $.ajax({
                url: webPath + url,
                dataType: "json",
                beforeSend: function () {
                }, success: function (data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        $(obj).parent().parent().remove();
                        alert(data.msg, 1);
                    }else if (data.flag == "error"){
                        alert(data.msg, 0);
                    }
                }
            });
        });
    };



    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        openCusCustomerShare:_openCusCustomerShare,
        openInputPage:_openInputPage,
        insert:_insert,
        changeShareStatus:_changeShareStatus,
        deleteById:_deleteById
    };
}(window, jQuery);