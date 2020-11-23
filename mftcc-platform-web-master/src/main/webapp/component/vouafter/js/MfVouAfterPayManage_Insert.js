;
var MfVouAfterPayManage_Insert = function(window, $) {
    var _init = function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
    };
    var _selectPact = function () {
        $("input[name='pactNo']").popupList({
            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfVouAfterPayManage/getPactListForVouPay",//请求数据URL
            title: "选择合同",//标题
            searchplaceholder: "合同编号/客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.pactId);
            },
            tablehead:{//列表显示列配置
                "pactNo":"合同编号",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"pactNo"//真实值
            },
        });
        $("input[name='pactNo']").next().click();
    };

    var _getForm = function(pactId){
        $.ajax({
            type : "POST",
            url : webPath+"/mfVouAfterPayManage/getPactInfoAjax?pactId=" + pactId,
            data:{},
            dataType : "json",
            success : function(data) {
                if (data.flag == "success") {
                    var mfBusPact = data.mfBusPact;
                    $("input[name=cusNo]").val(mfBusPact.cusNo);
                    $("input[name=cusName]").val(mfBusPact.cusName);
                    $("input[name=pactNo]").val(mfBusPact.pactNo);
                    $("input[name=pactId]").val(mfBusPact.pactId);
                    $("input[name=kindNo]").val(mfBusPact.kindNo);
                    $("input[name=kindName]").val(mfBusPact.kindName);
                    $("input[name=actualBankChargeAmt]").val(data.bankChargeAmt);
                    $("input[name=actualBankDepositAmt]").val(data.bankBond);
                }
            }
        });

    }

	return {
		init : _init,
        selectPact:_selectPact,
        getForm:_getForm
    };
}(window, jQuery);
