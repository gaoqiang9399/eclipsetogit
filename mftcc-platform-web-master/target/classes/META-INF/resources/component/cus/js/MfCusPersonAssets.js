function init() {
    myCustomScrollbarForForm({
        obj: ".scroll-content",
        advanced: {
            updateOnContentResize: true
        }
    });
    changeMortgage($("select[name='isMortgage']"));
    /*var html = "<font color='#FF0000'>*</font>";
    $("select[name='houseType']").parents("tr").find("label").prepend(html);
    $("input[name='vehicleBrand']").parents("tr").find("label").prepend(html);	*/
    if (flag == "update") {//详情页面资产类型不允许更改
        changeAssets($("input[name='assetsType']"));
    } else {
        changeAssets($("select[name='assetsType']"));
    }
    $("input[name='assetsOwner']").click();
}

function changeAssets(obj) {
};


function saveCusPersonAssetsInfo(obj, saveType) {
    var flag = submitJsMethod($(obj).get(0), '');
    if (flag) {
        var checkFlag = "";
        //证件号码唯一性验证
        var idNum = $("input[name=cusNo]").val();
        var idNumTitle = $("input[name=cusNo]").attr("title");
        var idNumType = $("select[name=idType]").val();
        var relationId = $("input[name=cusNo]").val();
        var idNumResult = checkUniqueVal(idNum, idNumTitle, relationId, "MfCusPersonAssetsInfo", "01", saveType, "");
        checkFlag = idNumResult.split("&")[0];
        idNumResult = idNumResult.split("&")[1];
        if (checkFlag == "1") {
            window.top.alert(idNumResult, 2, function () {
                ajaxInsertCusForm(obj);
            });
        } else {
            ajaxInsertCusForm(obj);

        }
    }
}
function saveCusPersonAssetsInfoAndAdd(obj, saveType) {
    var flag = submitJsMethod($(obj).get(0), '');
    if (flag) {
        var checkFlag = "";
        //证件号码唯一性验证
        var idNum = $("input[name=cusNo]").val();
        var idNumTitle = $("input[name=cusNo]").attr("title");
        var idNumType = $("select[name=idType]").val();
        var relationId = $("input[name=cusNo]").val();
        var idNumResult = checkUniqueVal(idNum, idNumTitle, relationId, "MfCusPersonAssetsInfo", "01", saveType, "");
        checkFlag = idNumResult.split("&")[0];
        idNumResult = idNumResult.split("&")[1];
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusPersonAssetsInfo/input?cusNo="+cusNo;
        if (checkFlag == "1") {
            window.top.alert(idNumResult, 2, function () {
                ajaxInserAndAddCusForm(obj,inputUrl);
            });
        } else {
            ajaxInserAndAddCusForm(obj,inputUrl);
        }
    }
}

function updateCallBack() {
    top.addFlag = true;
    myclose_click();
};

function changeMortgage(obj) {
    var isMortgage = $(obj).val();
    if ("N" == isMortgage) {//没有按揭
        $("input[name='loanBalance']").parents("tr").hide();
        $("input[name='loanPeriod']").parents("tr").hide();
        $("input[name='loanBalance']").val("");
        $("input[name='loanPeriod']").val("");
    } else {//有按揭
        $("input[name='loanBalance']").parents("tr").show();
        $("input[name='loanPeriod']").parents("tr").show();
    }
};

//当资产类型改变的时候把资产性质与资产描述复制为空
function informationInit() {
    $("input[name='assetsOwner']").val($("input[name='cusName']").val());
    $("input[name='assetsName']").val('');
    $("textarea[name='remark']").val('');
    $("input[name='buyDate']").val('');
    $("input[name='buyValue']").val('');
    $("input[name='assetsValue']").val('');
}


var MfCusPersonAssets = function(window,$){
    var _init = function() {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        $("input[name='assetsOwner']").click();
        _bindDataSourceTo();
    }

    //跳转评估页面方法
    var _changeHouseFormShow = function (){
        $("#cusAssetsDiv").css('display','none');
        $("#saveBtn").css('display','none');

        $("#houseEvalDiv").css('display','block');
        $("#saveBtnHouseEval").css('display','block');
        $(top.window.document).find("#myModalLabel").text('房产评估');
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

    var _ajaxSave = function (obj,flag) {
        var relNo = $("input[name=assetsId]").val();
        var returnData = evalHouseInfo(obj,flag,relNo);
        if(returnData.flag == 'success'){
            _changeFormDisplay();
            $("input[name='assetsValue']").val(returnData.TotalPrice);
        }
    }

    function _bindDataSourceTo() {
        MfCusPersonAssets.bindDataSource1('2','relNameHidden');
    }

    function _bindDataSource1(type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_, ifAdd) {
        var obj = $('input[name=assetsOwner]');
        //表单编号

        var formId = $(obj).parents('form').find('input[name=formId]').val();
        if (formId_) {// 当formId_有值时，以formId_为准
            formId = formId_;
        }
        //绑定事件的input框
        var element = $(obj).attr('name');

        var title = '选择共有权人';
        var multipleFlag = true;//多选标志 true多选,false 单选
        var ajaxUrl = webPath + "/mfUserPermission/getPerDataSourceAjax?formId=" + formId + "&element=" + element + "&cusNo=" + cusNo + "&cusType=" + cusType;//请求数据URL;
        if (parmTitle != undefined) {
            title = parmTitle;
        }
        if (parmMultipleFlag != undefined) {
            multipleFlag = parmMultipleFlag;
        }
        if (cusBaseType != undefined) {//客户类型
            ajaxUrl += "&cusBaseType=" + cusBaseType;
        }
        $('input[name=relNameHidden]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: ajaxUrl,
            handle:BASE.getIconInTd($("input[name=assetsOwner]")),//其他触发控件
            valueElem: "input[name=assetsOwner]",//真实值选择器
            title: title,//标题
            labelShow: false,
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=assetsOwner]"));

                },
            tablehead: {//列表显示列配置
                "cusName": "客户名称",
                "idNum": "证件号码"
            },
            returnData: {//返回值配置
                disName: "cusName",//显示值
                value: "cusName"//真实值
            },
            addBtn: {//添加扩展按钮
                "title": "新增",
                "fun": function (hiddenInput, elem) {
                    top.window.openBigForm(webPath + "/mfCusCustomer/inputCoborr", "新增客户", function () {
                        $('input[name=assetsOwner]').popupList("initItems", $('input[name=assetsOwner]'), $('input[name=assetsOwner]').data("options"));});
                }
            }
        });

    }


    function _bindDataSourceDetail(){
        //客户名称新版选择组件
        $('input[name=assetsOwner]').popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCustomer/findByPageForSelectAjax?removeCusId="+cusNo+"&cusBaseType=2",//请求数据URL
            handle:BASE.getIconInTd($("input[name=assetsOwner]")),//其他触发控件
            valueElem:"input[name=assetsOwner]",//真实值选择器
            title: "选择个人客户",//标题
            changeCallback:function(elem){//回调方法
            },
            tablehead:{//列表显示列配置
                "cusNo":{"disName":"客户编号","align":"center"},
                "cusName":{"disName":"客户名称","align":"center"}
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusName"//真实值
            }
        });
    }


    return {
        init : _init,
        bindDataSource1 : _bindDataSource1,
        bindDataSourceTo : _bindDataSourceTo,
        bindDataSourceDetail : _bindDataSourceDetail,
        changeHouseFormShow:_changeHouseFormShow,
        changeFormDisplay:_changeFormDisplay,
        changeRengong:_changeRengong,
        ajaxSave:_ajaxSave
    };
}(window, jQuery);



