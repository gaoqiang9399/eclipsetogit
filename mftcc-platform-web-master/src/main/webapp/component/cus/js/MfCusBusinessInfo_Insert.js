;
var MfCusBusinessInfo_Insert = function (window, $) {
    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        // 营业执照类型
        $("select[name=registeredType]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false//单选
        });
        $("input[name='wayClassDes']").bind("change",function(){
            _matchProjectSizeAjax();
        });
        $("input[name='assetSum']").bind("blur",function(){
            _matchProjectSizeAjax();
        });
        $("input[name='bussIncome']").bind("blur",function(){
            _matchProjectSizeAjax();
        });
        $("input[name='empCnt']").bind("blur",function(){
            _matchProjectSizeAjax();
        });
    };

    //新增保存
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var assetSum=$("input[name='assetSum']").val();
            if(assetSum > 0){
                ajaxInsertCusForm(obj);
            }else{
                alert("上一年末资产总额需大于0！", 0);
            }
        }
    };
    var _matchProjectSizeAjax = function (){
        var wayNo=$("input[name='wayClass']").val();
        var assetSum=$("input[name='assetSum']").val();
        var bussIncome=$("input[name='bussIncome']").val();
        var empCnt=$("input[name='empCnt']").val();
        $.ajax({
            url:webPath+"/mfCusCorpBaseInfo/getMatchProjectSizeAjax",
            data : {"wayNo":wayNo,"assetSum":assetSum,"bussIncome":bussIncome,"empCnt":empCnt},
            type : 'post',
            dataType : 'json',
            async:false,
            success : function(data) {
                if(data.proSizeNo!="") {
                    $("input[name='projSize']").val(data.proSizeNo);
                    $("input[name='projSize']").parent().find("input[class='form-control']").val(data.proSizeName);
                }
            },
            error : function() {
            }
        });

    }
    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init,
        ajaxSave: _ajaxSave,
        matchProjectSizeAjax:_matchProjectSizeAjax
    };

}(window, jQuery);

//分级加载areaProvice隐藏，areaCity显示
function selectAreaProviceCallBack(areaInfo){
    $("input[name=careaProvice]").val(areaInfo.disNo);
    $("input[name=careaCity]").val(areaInfo.disName);
    $("input[name=address]").val(areaInfo.disName);
    getIfLocal(areaInfo.disNo);
};
function getIfLocal(areaNo){
    $.ajax({
        url : webPath + "/mfCusPersBaseInfo/getIfLocalAjax",
        data : {
            areaNo:areaNo
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if (data.flag == "success") {
                $("[name='ifLoc']").val(data.ifLoc);
            }
        },
        error : function() {
        }
    });
}
//行业分类选择后的回调处理
function nmdWaycCallBack(nmdWayInfo){
    var oldWayClass = $("input[name=wayClass]").val();
    $("input[name=wayClassDes]").val(nmdWayInfo.wayName);
    $("input[name=wayClass]").val(nmdWayInfo.wayNo);
    $("[name=industryClass]").val(nmdWayInfo.industryClass);
    $("input[name=wayMaxClass]").val(nmdWayInfo.wayMaxClass);
    if(nmdWayInfo.wayNo!=oldWayClass){//行业分类改变时，修改企业规模
        MfCusBusinessInfo_Insert.matchProjectSizeAjax();
    }
};
