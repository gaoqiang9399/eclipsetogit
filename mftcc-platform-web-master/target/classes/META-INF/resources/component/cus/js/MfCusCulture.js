var MfCusCulture = function (window, $) {
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

    //获取苗种厂家银行账号
    var _getCwBankCusAccMangeAjax = function () {
        var userType = "03";
        $("input[name=seedFactory]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/cwCusBankAccManage/getCwBankCusAccMangeAjax?useType=" + userType, // 请求数据URL
            valueElem: "input[name='seedFactory']",//真实值选择器
            title: "银行账户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=popsseedFactory]").val(tmpData.accountName);
                //$("input[name=collectAccName]").val(tmpData.accountName);
                //$("input[name=collectBank]").val(tmpData.bank);
            },
            tablehead: {//列表显示列配置
                "accountNo": "账号",
                "accountName": "账号名称",
                "bank": "开户行"
            },
            returnData: {//返回值配置
                disName: "accountName",//显示值
                value: "accountName"//真实值
            }
        });
        $("input[name=seedFactory]").next().click();
    };

    //获取冷藏厂家银行账号
    var _getReCwBankCusAccMangeAjax = function () {
        var userType = "02";
        $("input[name=recycleFirm]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/cwCusBankAccManage/getCwBankCusAccMangeAjax?useType=" + userType, // 请求数据URL
            valueElem: "input[name='recycleFirm']",//真实值选择器
            title: "银行账户",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=popsrecycleFirm]").val(tmpData.accountName);
                //$("input[name=collectAccName]").val(tmpData.accountName);
                //$("input[name=collectBank]").val(tmpData.bank);
            },
            tablehead: {//列表显示列配置
                "accountNo": "账号",
                "accountName": "账号名称",
                "bank": "开户行"
            },
            returnData: {//返回值配置
                disName: "accountName",//显示值
                value: "accountName"//真实值
            }
        });
        $("input[name=recycleFirm]").next().click();
    };


    return {
        init: _init,
        ajaxSave: _ajaxSave,
        getCwBankCusAccMangeAjax: _getCwBankCusAccMangeAjax,
        getReCwBankCusAccMangeAjax: _getReCwBankCusAccMangeAjax
    };
}(window, jQuery);