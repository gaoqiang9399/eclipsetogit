var sysRoleManagement = function (window, $) {
    var _init = function () {
        myCustomScrollbar({
            obj: "#content",//页面内容绑定的id
            url: webPath + "/sysOrg/roleInfoList",//列表数据查询的url
            tableId: "tableroleInfoList",//列表数据查询的table编号
            tableType: "thirdTableTag",//table所需解析标签的种类
            pageSize: 30,//加载默认行数(不填为系统默认行数)
            topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
        });
    }


   var _openCusInfoForm=function() {
        var opNoType = "";
        top.window.openBigForm(webPath + '/pmsConfigure/configureNew?roleNo=&opNoType=' + opNoType, '新增角色', function () {
            _init();
        });
    };


    var _modifyRole=function (obj, url) {
        if (url != null) {
            var resObj = StringUtil.urlParamsToObj(url);
            var roleNo=resObj.roleNo;
            var opNoType=resObj.opNoType;
            top.window.openBigForm(webPath + "/pmsConfigure/configureNew?roleNo="+roleNo+"&opNoType=" + opNoType, '新增角色', function () {
                _init();
            });
        }
    }
    
    return {
        init: _init,
        openCusInfoForm:_openCusInfoForm,
        modifyRole:_modifyRole
    }
}(window, jQuery);