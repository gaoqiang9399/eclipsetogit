var MfCusInfoDw = function (window, $) {
    //初始化客户查询历史列表
    var _init = function () {
        myCustomScrollbar({
            obj: "#content",//页面内容绑定的id
            url: webPath + "/mfCusInfoDw/findByPageAjax",//列表数据查询的url
            tableId: "tablecusInfoDw",//列表数据查询的table编号
            tableType: "thirdTableTag",//table所需解析标签的种类
            pageSize: 30//加载默认行数(不填为系统默认行数)
        });
    }

    //打开查询客户信息表单
    var _openCusInfoForm = function () {
        top.window.openBigForm(webPath + '/mfCusInfoDw/queryCusInfoInit', '客户信息查询', function () {
            _init();
        })
    }


    //客户列表
    var _selectCusName = function () {
        $("input[name=cusName]").parent().find('div').remove();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusMaintain/selectCusInfo",//请求数据URL
            valueElem: "input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=cusNo]").val(tmpData.cusNo);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "idNum": "证件号码",
                "cusTel": "手机号码"
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    };

    //选择合同
    var _selectpactNo = function () {
        var cusNo = $("input[name=cusNo]").val();
        //先选择客户才能选择合同
        if(cusNo==null || cusNo==''){
            return false;
        }
        $("input[name=pactNo]").parent().find('div').remove();
        $('input[name=pactNo]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusInfoDw/selectPactNo?cusNo=" + cusNo,//请求数据URL
            valueElem: "input[name=appId]",//真实值选择器
            title: "选择客户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=appId]").val(tmpData.appId);
                $("input[name=appName]").val(tmpData.appName);
            },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "pactNo": "合同号",
            },
            returnData: {//返回值配置
                disName: "pactNo",//显示值
                value: "appId"//真实值
            }
        });
        $("input[name=pactNo]").parent().find(".pops-value").click();
    }

    //查询合同信息并下载
    var _queryAndDw = function () {
        var flag = submitJsMethod($("#queryCusInfo").get(0), '');
        if (flag) {
            var dataParam = JSON.stringify($("#queryCusInfo").serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url: "/mfCusInfoDw/queryAndDw",
                data: {ajaxData: dataParam},
                success: function (data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        var filePath = data.filePath;
                        var fileName = data.fileName;
                        //下载
                        window.top.location.href = encodeURI(webPath + "/mfCusInfoDw/cusInfoDownload?filePath=" + filePath + "&fileName=" + fileName);
                        window.top.alert(data.msg, 1);
                        myclose_click();

                    } else {
                        LoadingAnimate.stop();
                        window.top.alert(data, msg, 1);
                    }

                }
            })
        }
    }

    //列表下载
    var _cusListDw = function (obj, url) {
        if (url != null) {
            var resObj = StringUtil.urlParamsToObj(url);
            var filePath = resObj.storagePage;
            var fileName = resObj.fileName;
            window.top.location.href = encodeURI(webPath + "/mfCusInfoDw/cusInfoDownload?filePath=" + filePath + "&fileName=" + fileName);
        }
    }


    return {
        init: _init,
        openCusInfoForm: _openCusInfoForm,
        selectCusName: _selectCusName,
        selectpactNo: _selectpactNo,
        queryAndDw: _queryAndDw,
        cusListDw: _cusListDw
    }

}(window, jQuery);