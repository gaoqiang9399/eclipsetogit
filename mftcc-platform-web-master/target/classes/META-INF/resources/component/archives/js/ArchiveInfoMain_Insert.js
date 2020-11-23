;
var ArchiveInfoMain_Insert = function(window, $) {
    //资料合同选择项目
    var _selectBusForDoc = function () {
        $("[name='appName']").parent().find(".pops-value").remove();
        $("input[name='appName']").popupList({
//            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusApply/getMfBusApplyListForArchive",//请求数据URL
            title: "选择项目",//标题
            searchplaceholder: "客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getDocTemplateBack(sltVal.appId);//跳转到单笔下合作银行资料合同归档界面
            },
            tablehead:{//列表显示列配置
                "pactNo":"委托合同",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"appId"//真实值
            },
        });
        $("input[name=appName]").parent().find(".pops-value").click();
//        $("input[name='appName']").next().click();
    };


    //资料合同选择授信
    var _selectCreditForDoc = function () {
        $("[name='creditAppNo']").parent().find(".pops-value").remove();
        $("input[name='creditAppNo']").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCreditApply/findListForArchiveAjax",//请求数据URL
            title: "选择授信",//标题
            searchplaceholder: "客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                creditAppId = sltVal.creditAppId;
                $("input[name=agenciesId]").val("");
                $("input[name=agenciesName]").val("");
                $("input[name=cusName]").val("");
            },
            tablehead:{//列表显示列配置
                "creditAppNo":"授信编号",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"creditAppNo",//显示值
                value:"creditAppId"//真实值
            },
        });
        $("input[name=creditAppNo]").parent().find(".pops-value").click();
    };

    //资料合同选择授信合作银行
    var _selectAgencyForDoc = function () {
        $("[name='agenciesName']").parent().find(".pops-value").remove();
        $("input[name='agenciesName']").popupList({
            searchOn: false, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/archiveInfoMain/breedBankInit?creditAppId="+creditAppId,//请求数据URL
            title: "选择合作银行",//标题
            searchplaceholder: "银行名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                agenciesId = sltVal.agenciesId;
                _getDocTemplateBack(creditAppId,agenciesId);//跳转到授信合作银行资料合同归档界面
            },
            tablehead:{//列表显示列配置
                "agenciesName":"合作银行",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"agenciesName",//显示值
                value:"agenciesId"//真实值
            },
        });
        $("input[name=agenciesName]").parent().find(".pops-value").click();
    };

    //资料合同选择业务/授信后的回调
    var _getDocTemplateBack = function(relationId,agenciesId){
        var archivePactStatus = $("[name=archivePactStatus]").val();
        if(archivePactStatus == '01'){//授信
            window.location.href = webPath+"/archiveInfoMain/inputCredit?creditAppId=" + relationId+"&archivePactStatus="+archivePactStatus+"&agenciesId="+agenciesId;
        }else if(archivePactStatus == '02'){//业务
            window.location.href = webPath+"/archiveInfoMain/inputBusApply?appId=" + relationId+"&archivePactStatus="+archivePactStatus;
        }
    };

    //凭证选择授信
    var _selectCreditForVoucher = function () {
        $("[name='creditAppNo']").parent().find(".pops-value").remove();
        $("input[name='creditAppNo']").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCreditApply/findListForArchiveAjax",//请求数据URL
            title: "选择授信",//标题
            searchplaceholder: "客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getVoucherBack(sltVal.creditAppId);
            },
            tablehead:{//列表显示列配置
                "creditAppNo":"授信编号",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"creditAppNo",//显示值
                value:"creditAppId"//真实值
            },
        });
        $("input[name=creditAppNo]").parent().find(".pops-value").click();
    };

    //选择凭证后的回调--授信
    var _getVoucherBack = function(creditAppId){
        var archivePactStatus = $("[name=archivePactStatus]").val();
        if(archivePactStatus == '01'){//授信
            window.location.href = webPath+"/archiveInfoMain/inputVoucherForCredit?creditAppId=" + creditAppId+"&archivePactStatus="+archivePactStatus;
        }
    };

    //选择归档项目下的其他资料类别
   var _selectArchivePaperDialog=function(){
        selectArchivePaperDialog(_selectArchivePaperBack);
    };

    //选择其他资料回调
    var _selectArchivePaperBack=function(res){
        var docSplitName=res.docSplitName;
        var docSplitNo =res.docSplitNo;
        var isPaper =res.isPaper;

        $("input[name=docSplitName]").val(docSplitName);
        $("input[name=docSplitNo]").val(docSplitNo);
        $("input[name=isPaper]").val(isPaper);
    };

    //原始凭证选择项目(0911放弃)
    var _selectPact = function () {
        $("[name='appName']").parent().find(".pops-value").remove();
        $("input[name='appName']").popupList({
            async:false,
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusApply/getMfBusApplyListForArchive",//请求数据URL
            title: "选择项目",//标题
            searchplaceholder: "客户名称",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                _getForm(sltVal.appId);
            },
            tablehead:{//列表显示列配置
                "pactNo":"委托合同",
                "cusName":"客户名称"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"appId"//真实值
            },
        });
        $("input[name=appName]").parent().find(".pops-value").click();
    };

    //原始凭证选择项目
    var _getForm = function(appId){
        window.location.href = webPath+"/archiveInfoMain/inputVoucherForBus?appId=" + appId;
    }

    //20200911修改，凭证归档是在同一个列表选择银行授信和业务
    var _selectCreditAndApply = function (type) {//type-01 原始凭证  02 他项凭证
        type = type;
        selectCreditAndApplyDialog(_selectCreditAndApplyBack,type);
    };

    var _selectCreditAndApplyBack=function(res){
        var archivePactStatus = res.archivePactStatus;
        var type = res.type;
        if(type == "02"){//他项凭证
            if(archivePactStatus == '01'){//授信
                window.location.href = webPath+"/archiveInfoMain/inputVoucherForCredit?creditAppId=" + res.creditAppId+"&archivePactStatus="+archivePactStatus;
            }else if(archivePactStatus == '02'){
                window.location.href = webPath+"/archiveInfoMain/inputVoucherForBus?appId=" + res.appId;
            }
        }else{
            $("[name=archivePactStatus]").val(archivePactStatus);
            $("[name=cusName]").val(res.cusName);
            $("[name=busiNo]").val(res.busiNo);
            if(archivePactStatus == '01'){//授信
                $("[name=creditAppId]").val(res.creditAppId);
            }else if(archivePactStatus == '02'){
                $("[name=appId]").val(res.appId);
            }
        }
    };

	return {
        selectPact:_selectPact,
        getForm:_getForm,
        selectCreditAndApply:_selectCreditAndApply,
        selectCreditAndApplyBack:_selectCreditAndApplyBack,
        selectBusForDoc:_selectBusForDoc,
        selectCreditForDoc:_selectCreditForDoc,
        selectAgencyForDoc:_selectAgencyForDoc,
        selectCreditForVoucher:_selectCreditForVoucher,
        selectArchivePaperDialog:_selectArchivePaperDialog,
        selectArchivePaperBack:_selectArchivePaperBack,
        getVoucherBack:_getVoucherBack,
        getDocTemplateBack:_getDocTemplateBack
    };
}(window, jQuery);
function selectCreditAndApplyDialog(callback,type){
    var url = "/archiveInfoMain/getCreditAndApplyList?type="+type;
    dialog({
        id:'selectCreditOrApplyDialog',
        title:"选择合同号",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};

function selectArchivePaperDialog(callback){
    var url = "/archiveInfoMain/getArchivePaperList";
    dialog({
        id:'archivePaperDialog',
        title:"选择归档类别",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};