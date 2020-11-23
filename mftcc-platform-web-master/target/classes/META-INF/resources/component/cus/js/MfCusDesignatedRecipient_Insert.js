;
var MfCusDesignatedRecipient_Insert = function (window, $) {
    var _init = function () {

        // myCustomScrollbarForForm({
        //     obj: ".scroll-content",
        //     advanced: {
        //         theme: "minimal-dark",
        //         updateOnContentResize: true
        //     }
        // });

        myCustomScrollbarForForm({
            obj: ".scroll-content"
        });

        // 客户名称新版选择组件
        $('input[name=tmpHidden]').popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/mfCusCustomer/findByPageForSelectAjax?removeCusId=" + cusNo + "&cusBaseType=2",//请求数据URL
            handle: BASE.getIconInTd($("input[name=recipientName]")),//其他触发控件
            // valueElem: "input[name=recipientName]",//真实值选择器
            title: "选择个人客户",//标题
            changeCallback: function (elem) {//回调方法
                var sltVal = elem.data("selectData");
                $('input[name=recipientName]').val(sltVal.cusName);
                $('input[name=recipientIdNum]').val(sltVal.idNum);
                // $('input[name=recipientName]').parent().find(".error").remove();
            },
            tablehead: {//列表显示列配置
                "cusNo": {"disName": "客户编号", "align": "center"},
                "cusName": {"disName": "客户名称", "align": "center"}
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "cusNo"//真实值
            }
        });
    };

    return {
        init: _init
    };
}(window, jQuery);
