var VwProductManage_List = function(window, $) {
    var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/vwProductManage/findByPageAjax", //列表数据查询的url
            tableId : "tableinfovwproduct", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
    };
    /**
     * 前端产品新增页面请求
     * @private
     */
    var _toApplyInsert = function(){
        top.window.openBigForm(webPath+"/vwProductManage/input","产品新增" );
    };
    //详情页面关闭
    var _register =function(){
        window.location.href=webPath+"/vwProductManage/getListPage";
    };
    var _deletevmproductmanage=function(parm){
        parm = parm.split("?")[1];
        var parmArray = parm.split("&");
        var productNo = parmArray[0].split("=")[1];
        top.LoadingAnimate.start();
        $.ajax({
            url: webPath+"/vwProductManage/deleteAjax",
            data: {
                productNo:productNo,
            },
            type: "post",
            dataType: "json",
            success: function (data) {
                top.flag = true;
                top.LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    VwProductManage_List.register();
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
    };
    var _update= function (formObj) {
        var url = $(formObj).attr("action");
        var dataForm = JSON.stringify($(formObj).serializeArray());
        top.LoadingAnimate.start();
        $.ajax({
            url: url,
            data: {
                ajaxData: dataForm,
            },
            type: "post",
            dataType: "json",
            success: function (data) {
                top.flag = true;
                top.LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    VwProductManage_List.register();
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
    }
    return {
        init : _init,
        toApplyInsert: _toApplyInsert,
        register:_register,
        deletevmproductmanage:_deletevmproductmanage,
        update:_update
    };
}(window, jQuery);