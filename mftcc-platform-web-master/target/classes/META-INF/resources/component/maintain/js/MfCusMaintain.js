;
var MfCusMaintain = function (window, $) {
    var _init = function () {
    };

//客户列表
    var _selectLegalPerson = function () {
        $("input[name=cusName]").parent().find('div').remove();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusMaintain/selectCusInfo",//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "idNum": "证件号码"
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    };


//客户姓名修改
    var _cusUpdateName = function (obj) {
        //客户姓名修改
        _submit(obj);
    };


//客户证件号码列表
    var _selectCusIdNum = function () {
        $("input[name=idNum]").parent().find('div').remove();
        $('input[name=idNum]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusMaintain/selectCusInfo",//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                _selectCusBack(tmpData);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "idNum": "证件号码",
            },
            returnData: {//返回值配置
                disName: "idNum",//显示值
                value: "cusNo"//真实值
            }
        });
        $("input[name=idNum]").parent().find(".pops-value").click();
    };


//客户证件号码修改
    var _cusUpdateIdNum = function (obj) {
        var url = $(obj).attr("action");
        //表单为空验证，空为false
        var flag = submitJsMethod($(obj).get(0), '');
        //证件号码唯一性检验
        var unVal = $("[name=ext1]").val();
        var unValMsg = {};
        var unValCheckResult = doCheckUniqueIdNum(unVal);
        var checkFlag = unValCheckResult.split("&")[0];
        if (checkFlag == "1") {
            unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
            window.top.alert(unValMsg.idNumResultMsg, 0);
            return false;
        }
        //证件号提交修改
        _submit(obj);

    };


//客户手机号码列表
    var _selectCusTel = function () {
        $("input[name=cusName]").parent().find('div').remove();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusMaintain/selectCusInfo",//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户手机号码",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                _selectCusBack1(tmpData);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "cusTel": "个人手机号码",
                "contactsTel": "企业手机号码",
            },
            returnData: {//返回值配置
                disName: "cusName",
                value: "cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    }


//选择客户回调
    var _selectCusBack1 = function (cus) {
        $("input[name=cusNo]").val(cus.cusNo);
        $("input[name=cusBaseType]").val(cus.cusBaseType);
        var cusBaseType = cus.cusBaseType;
        if (cusBaseType == "2") {
            $("input[name=cusTel]").val(cus.cusTel);
        } else {
            $("input[name=cusTel]").val(cus.contactsTel);
        }


    };

//客户手机号修改
    var _cusUpdateCusTel = function (obj) {
        //表单为空验证，空为false
        var saveType = "update"
        //手机号码唯一性验证
        var relationId = $("input[name=cusNo]").val();
        var $tel = $("input[name=ext1]");
        var unVal = $tel.val();
        if (unVal != null && unVal != "") {
            var columnTitle = $tel.attr("title");
            var unValCheckResult = checkUniqueVal(unVal, columnTitle, relationId, "MfCusCustomer", "20", saveType, "");
            var checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
            var telResultMsg = unValCheckResult.split("&")[1];
            if (checkFlag == "1") {
                window.top.alert(telResultMsg + " 是否继续保存?", 2, function () {
                    _submit(obj);
                });
            } else {
                _submit(obj);
            }
        }
    };

    //选择客户回调
    var _selectCusBack = function (cus) {
        $("input[name=cusNo]").val(cus.cusNo);
        $("input[name=idType]").val(cus.idType);
    };
    //提交修改
    var _submit = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        var url = $(obj).attr("action");
        if (flag) {
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url: url,
                data: {ajaxData: dataParam},
                success: function (data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        myclose_click();
                    } else {
                        LoadingAnimate.stop();
                        window.top.alert(data, msg, 1);
                    }

                }
            })
        }
    };

    return {
        init: _init,
        selectLegalPerson: _selectLegalPerson,
        cusUpdateName: _cusUpdateName,
        selectCusIdNum: _selectCusIdNum,
        cusUpdateIdNum: _cusUpdateIdNum,
        selectCusTel: _selectCusTel,
        cusUpdateCusTel: _cusUpdateCusTel
    };
}(window, jQuery);







