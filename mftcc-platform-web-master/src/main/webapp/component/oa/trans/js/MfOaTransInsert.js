;
var OaTransInsert = function(window, $) {
    var _init = function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        _bindClose();
        _bindInsertAjax("#OaTransInsert");
        $("#tableOaTransList").empty();//将选择之后显示的列表清空
    };
    //选择移交人回调函数
    var _transPersonCallBack = function(userInfo){
        var cusMngNo = userInfo.opNo;
        $("input[name=transOpNo]").val(userInfo.opNo);
        $("input[name=transOpName]").val(userInfo.opName);
        $.ajax({
            url :webPath+"/mfOaTrans/getCusAndAppNumAjax?cusMngNo="+cusMngNo,
            success : function(data) {
                if (data.flag == "success") {
                    var html = "该操作员共有 "+data.cusCount+"个客户"+data.busCount+"个项目";
                    $(".trans").empty();
                    $(".trans").append(html);
                    $("input[name=transContent]").val("");
                    $("input[name=transContentNo]").val("");
                    $("textarea[name=transReason]").val("");
                    $("input[name=recOpName]").val("");
                    $("input[name=recOpNo]").val("");
                    $("select[name=transType]").val("1");
                    $("input[name=transContent]").css("display","block");
                    $("input[name=transContent]").parents("tr").find("label").text("移交客户");
                    $("input[name=transContent]").next(".pops-value").remove();
                    $("input[name=transContent]").attr('onclick','OaTransInsert.getCusInfo(this);');
                    $(".pops-bg.tranType1").remove();
                    $(".pops-bg.tranType2").remove();
                } else {
                    window.top.alert(data.msg, 0);
                }
            },
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //选择接收人回调函数
    var _recPersonCallBack = function(userInfo){
        $("input[name=recOpNo]").val(userInfo.opNo);
        $("input[name=recOpName]").val(userInfo.opName);
        var transOpNo =$("input[name=transOpNo]").val();
        var recOpNo =$("input[name=recOpNo]").val();
        if(transOpNo==recOpNo){//如果移交人和接收人相同，清空重选
            $("input[name=recOpNo]").val("");
            $("input[name=recOpName]").val("");
            $("input[name=recOpName]").next().text("");
            alert(top.getMessage("ERROR_SAME_TRANSER"),0);
            return false;
        }
    };

    //获取移交人的所有客户
    var _getCusInfo = function (obj){
        var cusMngNo = $("input[name=transOpNo]").val();
        if(cusMngNo==null||cusMngNo==""){
            alert(top.getMessage("FIRST_SELECT_FIELD", "移交人"),0);
            return false;
        }
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        var element = $(obj).attr('name');
        var ajaxUrl =webPath+"/mfOaTrans/getCustomerListAjax?cusMngNo="+cusMngNo;//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            allCheckShow: true, //false不显示，true显示
            ajaxUrl:ajaxUrl,
            bgClass:"tranType1",
            title: "移交客户",//标题
            searchplaceholder: "客户名称/证件号码/联系电话",//明确支持的过滤条件
            valueElem:"input[name=transContentNo]",//真实值选择器
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                //移交内容变更时重新获取选中的列表数据
                $("#tableOaTransList").empty();//将选择之后显示的列表清空
                _oaTransListData();
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":{"disName":"证件号码","align":"center"},
                "contactsTel":{"disName":"联系电话","align":"center"},
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    };

    var _getBusInfo = function (obj){
        var cusMngNo = $("input[name=transOpNo]").val();
        if(cusMngNo==null||cusMngNo==""){
            alert(top.getMessage("ERROR_SAME_TRANSER","移交人"),0);
            return false;
        }
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        var element = $(obj).attr('name');
        var ajaxUrl =webPath+"/mfOaTrans/getBusTransListAjax?cusMngNo="+cusMngNo;//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            allCheckShow: true, //false不显示，true显示
            ajaxUrl:ajaxUrl,
//			 valueClass:"show-text",
            bgClass:"tranType2",
            title: "移交项目",//标题
            searchplaceholder: "客户名称/合同编号",//明确支持的过滤条件
            valueElem:"input[name=transContentNo]",//真实值选择器
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                //移交内容变更时重新获取选中的列表数据
                $("#tableOaTransList").empty();//将选择之后显示的列表清空
                _oaTransListData();
            },
            tablehead:{//列表显示列配置
                "cusName":{"disName":"客户名称","width":"20%"},
                "pactNo":{"disName":"合同编号","width":"20%"},
                "kindName":{"disName":"产品名称","width":"20%"},
                "appAmt":{"disName":"合同金额(元)","align":"right","width":"20%","dataType":"money"},
                "termShow":{"disName":"合同期限","align":"center","width":"10%"},
            },
            returnData:{//返回值配置
                disName:"appName",//显示值
                value:"appId"//真实值
            }
        });
        $('input[name='+element+']').next().click();


    };
    //移交
    var _changeTransContenet=function(obj){
        //移交类型
        var transType=$("select[name=transType]").val();
        //清空transContentNo中的
        $("input[name=transContentNo]").val("");
        $("input[name=transContent]").val("");
        $("input[name=transContent]").css("display","block");
        $("input[name=transContent]").next(".pops-value").remove();
        $("#tableOaTransList").empty();//将选择之后显示的列表清空
        if(transType=="1"){
            $("input[name=transContent]").parents("tr").find("label").text("移交客户");
            $("input[name=transContent]").attr('onclick','OaTransInsert.getCusInfo(this);');
            $(".pops-bg.tranType2").remove();
        }else if(transType=="2"){
            $("input[name='transContent']").parents("tr").find("label").text("移交项目");
            $("input[name=transContent]").attr('onclick','OaTransInsert.getBusInfo(this);');
            $(".pops-bg.tranType1").remove();
        }else if(transType=="3"){
            $("input[name='transContent']").parents("tr").find("label").text("移交授信客户");
            $("input[name=transContent]").attr('onclick','OaTransInsert.getCreditCusInfo(this);');

            $(".pops-bg.tranType1").remove();
            $(".pops-bg.tranType2").remove();
            var transOpNo = $("input[name='transOpNo']").val();
            if(transOpNo!=''&&transOpNo!=null&&transOpNo!=undefined){
                _getTips(transOpNo);
            }
        }else if(transType == "4"){
            $("input[name=transContent]").parents("tr").find("label").text("移交客户");
            $("input[name=transContent]").attr('onclick','OaTransInsert.initCus(this);');

            $(".pops-bg.tranType2").remove();
            $(".pops-bg.tranType3").remove();
        }
    };


    var _bindClose = function () {
        $(".cancel").bind("click", function(event){
            //window.location.href=contextPath + "/component/oa/MfOaEntrance.jsp";
            if(view=='bus'){
                window.location.href= webPath+"/mfQueryEntrance/queryEntrance?menuNo=5&entranceNo=5";
            }else{
                window.location.href=webPath+"/mfOa/getEntrance";
            }
        });
    };

    //移交按钮绑定移交函数
    var _bindInsertAjax = function (obj) {
        $(".insertAjax").bind("click", function(event){
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                var transContent = $("input[name=transContent]").val();
                if(transContent==null||transContent==""){
                    var transType=$("select[name=transType]").val();
                    var transMsg = "";
                    if(transType=="1"){
                        transMsg = "移交客户";
                    }else if(transType=="2"){
                        transMsg = "移交项目";
                    }
                    window.top.alert(top.getMessage("FIRST_SELECT_FIELD", transMsg), 0);
                    return false;
                }
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                $.ajax({
                    url : url,
                    data : {ajaxData : dataParam},
                    success : function(data) {
                        if (data.flag == "success") {
                            window.top.alert(data.msg, 3, function () {
                                LoadingAnimate.start();// alert之后有页面跳转有延迟, 还能继续操作, 添加等待层避免
                                // if (view == 'bus') {
                                //     window.location.href = webPath + "/mfQueryEntrance/queryEntrance?menuNo=5&entranceNo=5";
                                // } else {
                                    window.location.href = webPath+"/mfOaTrans/getListPage";
                                // }
                            });
                        } else {
                            window.top.alert(data.msg, 0);
                        }
                    },error : function() {
                        alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    }
                });
            }
        });
    };

    //获取移交人的所有客户
    var _getCreditCusInfo = function (obj){
        var cusMngNo = $("input[name=transOpNo]").val();
        if(cusMngNo==null||cusMngNo==""){
            alert(top.getMessage("FIRST_SELECT_FIELD", "移交人"),0);
            return false;
        }
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        var element = $(obj).attr('name');
        var ajaxUrl =webPath+"/mfOaTrans/getCreditCusListAjax?cusMngNo="+cusMngNo+"&ext2="+"3";//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            allCheckShow: true, //false不显示，true显示
            ajaxUrl:ajaxUrl,
            bgClass:"tranType1",
            title: "移交授信客户",//标题
            searchplaceholder: "客户名称/证件号码/联系电话",//明确支持的过滤条件
            valueElem:"input[name=transContentNo]",//真实值选择器
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                //移交内容变更时重新获取选中的列表数据
                $("#tableOaTransList").empty();//将选择之后显示的列表清空
                _oaTransListData();
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":{"disName":"证件号码","align":"center"},
                "contactsTel":{"disName":"联系电话","align":"center"},
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    };

    var _initCus = function(obj){
        var cusMngNo = $("input[name=transOpNo]").val();
        if(cusMngNo==null||cusMngNo==""){
            alert(top.getMessage("FIRST_SELECT_FIELD", "移交人"),0);
            return false;
        }
        var element = $(obj).attr('name');
        var ajaxUrl =webPath+"/mfOaTrans/getCustomerListAjax?cusMngNo="+cusMngNo+"&ext2=7";//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: true, //false单选，true多选，默认多选
            allCheckShow: true, //false不显示，true显示
            ajaxUrl:ajaxUrl,
            bgClass:"tranType1",
            title: "移交客户",//标题
            searchplaceholder: "客户名称/证件号码/联系电话",//明确支持的过滤条件
            valueElem:"input[name=transContentNo]",//真实值选择器
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                $("#tableOaTransList").empty();//将选择之后显示的列表清空
                _oaTransListData();
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":{"disName":"证件号码","align":"center"},
                "commAddress":{"disName":"户籍地址","align":"center"},
                "contactsTel":{"disName":"联系电话","align":"center"},
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    }
    //获取某一个操作员下面有多少授信数据
    var _getTips = function(transOpNo){
        $.ajax({
            url :webPath+"/mfOaTrans/getCusAndAppNumAjax?cusMngNo="+transOpNo,
            success : function(data) {
                if (data.flag == "success") {
                    var html = "该操作员共有 "+data.cusCount+"个客户，添加"+data.creditCount+"个授信";
                    $(".trans").empty();
                    $(".trans").append(html);
                } else {
                    window.top.alert(data.msg, 0);
                }
            },
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    }
    //获取移交内容数据的页面列表展示
    var _oaTransListData = function () {
        var transContentNo = $("input[name=transContentNo]").val();
        var transType = $("select[name=transType]").val();
        var dataParam = {"transContentNo":transContentNo,"transType":transType};
        $.ajax({
            url:"/mfOaTrans/oaTransListData",
            data:{ajaxData:JSON.stringify(dataParam)},
            success:function(data){
                var html = data.htmlStr;
                $("#tableOaTransList").empty().html(html);
            }
        });
    }

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        recPersonCallBack:_recPersonCallBack,
        transPersonCallBack:_transPersonCallBack,
        changeTransContenet:_changeTransContenet,
        getBusInfo:_getBusInfo,
        getCusInfo:_getCusInfo,
        getCreditCusInfo:_getCreditCusInfo,
        initCus : _initCus
    };
}(window, jQuery);