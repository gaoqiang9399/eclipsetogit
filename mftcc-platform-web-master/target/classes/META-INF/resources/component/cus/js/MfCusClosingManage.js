var MfCusClosingManage = function (window, $) {
    var _init = function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                theme: "minimal-dark",
                updateOnContentResize: true
            }
        });
    }
    /** 保存 */
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

    var _getInput = function (obj, url) {
        top.openBigForm(url, "结案详情", function () {
            window.location.reload();
        });
    };

    var _insertClosing = function () {
        top.window.openBigForm(webPath + "/mfCusClosingManage/input", "结案申请",function () {
            window.location.reload();
        });
    };
    var _selectAppName = function (){
        var obj = $("input[name=appName]");
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusClosingManage/selectAppName",// 请求数据URL
            valueElem:"input[name=ext1]",//真实值选择器
            title: "选择项目",//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name=appName]"));
                BASE.removePlaceholder($("input[name=revouCase]"));
                var sltVal = elem.data("selectData");
                $("textarea[name=revouCase]").val(sltVal.revouCase);
                $("input[name=appName]").val(sltVal.appName);
                $("input[name=fincId]").val(sltVal.fincId);
                $("input[name=cusNo]").val(sltVal.cusNo);
                $("input[name=compensatoryId]").val(sltVal.compensatoryId);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名",
                "appName":"项目名称",
                "fincShowId":"借据编号"
            },
            returnData:{//返回值配置
                disName:"appName",//显示值
                value:"appName"//真实值
            }
        });
        $("input[name=appName]").next().click();
    };

    //审批提交
    function _doSubmit(obj){
        var flag=submitJsMethod($(obj).get(0), '');
        if(flag){
            var compensatoryId =  $("input[name=compensatoryId]").val();
            var id = $("input[name=id]").val();
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url: webPath+"/mfCusClosingManage/doCommitWkf",
                type:"post",
                dataType:"json",
                data:{
                    wkfId:id,
                    ajaxData:dataParam,
                    compensatoryId:compensatoryId
                },
                error:function(){
                    alert('提交到下一个节点时发生异常', 0);
                },
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg, 3);
                        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",webPath+"/sysTaskInfo/getListPage?entranceNo=1");
                        myclose_task();
                    }else{
                        alert(  top.getMessage("FAILED_SAVE"),0);
                    }
                }
            });
        }
    }

    var  _insertAjax  =   function (obj){
        var url = $(obj).attr("action");
        var flag=submitJsMethod($(obj).get(0), '');
        if(flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:'post',
                dataType:'json',
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    }else{
                        alert(  top.getMessage("FAILED_SAVE"),0);
                    }
                },error:function(){
                    LoadingAnimate.stop();
                    alert(  top.getMessage("FAILED_SAVE"),0);
                }
            });
        }else{
            alert(  top.getMessage("FAILED_SAVE"),0);
        }
    };

    return {
        init: _init,
        ajaxSave: _ajaxSave,
        getInput: _getInput,
        insertClosing : _insertClosing,
        selectAppName : _selectAppName,
        doSubmit : _doSubmit,
        insertAjax : _insertAjax
    };
}(window, jQuery);