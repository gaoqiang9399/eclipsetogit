;
var MfInputCusManage = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    };

    //展示查询结果
    var _inputCusManageAjax=function() {
        var flag = submitJsMethod($("#inputCusManageForm").get(0), '');
        if(flag) {
            var dataParam = JSON.stringify($("#inputCusManageForm").serializeArray());
            $.ajax({
                url: webPath + "/mfBusApply/inputCusManageAjax",
                data: {ajaxData: dataParam},
                success: function (data) {
                    if (data.flag == "success") {
                        if (data.queryType == "1"){//客户查询
                            $("#queryCusList").empty().html(data.tableHtml);
                            $("#query_cus").show();
                            $("#query_bus").hide();
                        }else if (data.queryType == "2"){
                            $("#queryCusList").empty().html(data.cusTableHtml);
                            $("#queryBusList").empty().html(data.tableHtml);
                            $("#query_cus").show();
                            $("#query_bus").show();
                        }
                    } else if (data.flag == "error") {
                        $("#query_cus").hide();
                        $("#query_bus").hide();
                        window.top.alert("无查询结果数据！请检查查询内容。", 3);
                    }
                }
            });
        }
    }
    return{
        init:_init,
        inputCusManageAjax:_inputCusManageAjax
    };
}(window,jQuery);