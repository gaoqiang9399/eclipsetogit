var MfCusAssets = function(window, $) {
    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {


    }
    //返回关闭方法
    var _changeFormDisplay = function () {
        $("#houseEvalDiv").css('display','none');
        $("#saveBtnHouseEval").css('display','none');

        $("#houseEvalRDiv").css('display','none');
        $("#saveBtnRHouseEval").css('display','none');

        $("#cusAssetsDiv").css('display','block');
        $("#saveBtn").css('display','block');
        $(top.window.document).find("#myModalLabel").text(title);
    }

    //跳转人工方法
    var _changeRengong = function () {
        $("#houseEvalDiv").css('display','none');
        $("#saveBtnHouseEval").css('display','none');

        $("#cusAssetsDiv").css('display','none');
        $("#saveBtn").css('display','none');

        $("#houseEvalRDiv").css('display','block');
        $("#saveBtnRHouseEval").css('display','block');
    }

    //跳转评估页面方法
    var _changeHouseFormShow = function (){
        $("#cusAssetsDiv").css('display','none');
        $("#saveBtn").css('display','none');

        $("#houseEvalDiv").css('display','block');
        $("#saveBtnHouseEval").css('display','block');
        $(top.window.document).find("#myModalLabel").text('房产评估');
    }

    var _ajaxSave = function (obj,flag) {
        var relNo = $("input[name=assetsId]").val();
        var returnData = evalHouseInfo(obj,flag,relNo);
        if(returnData.flag == 'success'){
            _changeFormDisplay();
            $("input[name='evalVal']").val(returnData.TotalPrice);
        }
    }
    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        changeFormDisplay:_changeFormDisplay,
        changeHouseFormShow:_changeHouseFormShow,
        changeRengong:_changeRengong,
        ajaxSave:_ajaxSave
    };

}(window, jQuery);

//选择网点信息
function selectWebsiteInfo(){
    var cusNo = $("input[name=cusNo]").val();
    $("input[name=belongWebsiteName]").popupList({
        searchOn: true, //启用搜索
        multiple: false, //false单选，true多选，默认多选
        ajaxUrl : webPath+"/mfCusNetworkInfo/findByPageAjax?cusNo=" + cusNo,// 请求数据URL
        valueElem:"input[name=belongWebsiteName]",//真实值选择器
        title: "网点信息",//标题
        elemEdit: true,
        changeCallback:function(elem){//回调方法
            var sltVal = elem.data("selectData");
            $("input[name='belongWebsiteId']").val(sltVal.websiteId);
            $("input[name='belongWebsiteName']").val(sltVal.websiteName);
        },
        tablehead:{//列表显示列配置
            "websiteId":"网点ID",
            "websiteCode":"网点编号",
            "websiteName":"网点名称"
        },
        returnData:{//返回值配置
            disName:"websiteName",//显示值
            value:"websiteId"//真实值
        }
    });
    $('input[name=belongWebsiteName]').next().click();
};