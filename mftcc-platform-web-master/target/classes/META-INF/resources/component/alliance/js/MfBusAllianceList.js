;
var MfBusAllianceList = function(window, $) {
    /**
     * 新增
     *
     * @param obj
     */
    var _allianceInsert = function(obj) {
        top.openBigForm(webPath + "/mfBusAlliance/input", "新增联保体", function() {
            updateTableData();
        });
    };

    /**
     * 查看详情
     *
     * @param obj
     */
    var _allianceView = function(obj,url) {
        top.addFlag = false;
        top.createShowDialog(url,"联保体信息",'90','90',function(){
            if(!top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    };

    //列表删除
    var _ajaxDelete = function(obj,url){
        // url  MfBusStore/deleteAjax;id-id;brNo-brNo;onClick-MfBusStore.ajaxDelete(this)
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+url,
                dataType:'json',
                type:'post',
                success : function(data){
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        updateTableData();//重新加载列表数据
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                }
            });
        });
    };

    //列表删除
    var _ajaxDismiss = function (obj, url) {
        alert("确认要解散联保体吗", 2, function () {
            var id;
            var param = url.split("?")[1].split("&");
            $.each(param, function (index, data) {
                if (data && data.split("=")[0] == "id") {
                    id = data.split("=")[1];
                }
            });

            var parmData = {
                'nodeNo': 'alliance_dismiss',
                'relNo': id,
                'id': id
            };

            $.ajax({
                url: webPath + "/riskForApp/getNodeRiskDataForBeginAjax",
                type: "post",
                data: {
                    ajaxData: JSON.stringify(parmData)
                },
                async: false,
                dataType: "json",
                success: function (data) {
                    if (data.exsitRefused == true) {// 存在业务拒绝
                        top.window.openBigForm(webPath + '/riskForApp/preventList?relNo=' + id, '风险拦截信息', function () {
                        });
                    } else {
                        $.ajax({
                            url: webPath + url,
                            dataType: 'json',
                            type: 'post',
                            success: function (data) {
                                if (data.flag == "success") {
                                    window.top.alert(data.msg, 1);
                                    updateTableData();//重新加载列表数据
                                } else {
                                    window.top.alert(data.msg, 0);
                                }
                            }
                        });
                    }
                },
                error: function () {
                    alert(top.getMessage("FAILED_OPERATION", "风险拦截"), 0);
                }
            });


        });
    };

    return {
        allianceInsert : _allianceInsert,
		allianceDelete:_ajaxDelete,
        allianceDismiss:_ajaxDismiss,
        allianceView:_allianceView
    };
}(window, jQuery);