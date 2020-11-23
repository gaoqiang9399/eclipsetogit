;
var MfCreditBaseConfig = function (window, $) {
    //初始化产品基础配置信息
    var _init = function (data) {
        var htmlStr = getPrdctBaseConfigHtml(data);
        $(".nav-content-div").html(htmlStr);
        $(".config-div").mCustomScrollbar("scrollTo", "top"); // 滚动到顶部（垂直滚动条）
        initBindEvent(data);
        //初始化选择组件
        initPopUpSelection(data);
    };
    //产品基础配置Html
    var getPrdctBaseConfigHtml = function (data) {
        var mfCusCreditConfig = data.mfCusCreditConfig;
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="content-div baseConfig"><div class="sub-content-div padding_left_15">'
            + '<div class="sub-content padding_left_20 margin_top_15">';

        //产品名称
        htmlStr = htmlStr + getPrdctNameConfigHtml(mfCusCreditConfig);
        //产品描述
        htmlStr = htmlStr + getPrdctDescConfigHtml(mfCusCreditConfig);
        //客户类别
        htmlStr = htmlStr + getPrdctCusTypeConfigHtml(mfCusCreditConfig, data.cusSubTypeList);
        //业务模式
        htmlStr = htmlStr + getPrdctBusModelConfigHtml(mfCusCreditConfig);
        //开办部门
        htmlStr = htmlStr + getPrdctDepartmentConfigHtml(mfCusCreditConfig);
        //开办角色
        htmlStr = htmlStr + getPrdctRoleConfigHtml(mfCusCreditConfig);
        //适配产品
        htmlStr = htmlStr + getPrdctKindConfigHtml(mfCusCreditConfig);
        if(creditModel == "credit_3"){
            //适配授信流程
            htmlStr = htmlStr + getAdaKindConfigHtml(mfCusCreditConfig);
        }
        //是否启用
        htmlStr = htmlStr + getPrdctAuthCycleConfigHtml(mfCusCreditConfig);

        htmlStr = htmlStr + '</div></div></div>';
        return htmlStr;
    };

    //初始化属性上的绑定事件
    var initBindEvent = function (data) {
        //产品名称绑定事件
        // kindNameBindEvent();
        kindDescBindEvent();
        //客户类别绑定事件
        cusTypeBindEvent();
        //业务模式绑定编辑事件
        busModelBindEvent(data);
        //开办部门绑定事件
        brNoBindEvent(data);
        //开办角色绑定事件
        roleNoBindEvent();
        //适配产品绑定事件
        adaptationKindNoBindEvent();
        if(creditModel == "credit_3") {
            //适配授信流程绑定事件
            adaptationCreditKindBindEvent();
        }
        //是否启用循环绑定事件
        authCycleBindEvent();
    };

    var initBrNoPopupSelection = function (data) {
        //开办部门 ztree 选择设置
        var ztreeSetting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "s", "N": ""}
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
        }
        //部门新组件
        $("input[name=startupBrNo]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            ztree: true,
            parentSelect: true,//选择父节点
            ztreeSetting: ztreeSetting,
            title: "开办部门",
            items: data.org,
            changeCallback: function (obj, elem) {
                var startupBrNo = $("input[name=startupBrNo]").val();
                var mfCusCreditConfig = {};
                mfCusCreditConfig.creditId = creditId;
                mfCusCreditConfig.startupBrNo = startupBrNo;
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    var startupBrName = mfCusCreditConfigTmp.startupBrName;
                    if(startupBrName == null || startupBrName == "null" || startupBrName == ""){
                        startupBrName = "未选择";
                    }
                    if (startupBrName.length > 50) {
                        startupBrName = startupBrName.substring(0, 50) + "...";
                    }
                    $(".startupBrNo .span-text").text(startupBrName);
                    $(".startupBrNo .span-text").attr("title", mfCusCreditConfigTmp.startupBrName);
                });

            },
        });
    };
    //初始化开办角色选择组件
    var initRolePopupSelection = function (data) {
        //角色新组件
        $("input[name=startupRoleNo]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            title: "开办角色",
            items: data.role,
            labelShow: false,
            changeCallback: function (obj, elem) {
                var mfCusCreditConfig = {};
                mfCusCreditConfig.creditId = creditId;
                mfCusCreditConfig.startupRoleNo = $("input[name=startupRoleNo]").val();
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    var startupRoleName = mfCusCreditConfigTmp.startupRoleName;
                    if(startupRoleName == null || startupRoleName == "null" || startupRoleName == ""){
                        startupRoleName = "未选择";
                    }
                    if (startupRoleName.length > 50) {
                        startupRoleName = startupRoleName.substring(0, 50) + "...";
                    }
                    $(".startupRoleNo .span-text").text(startupRoleName);
                    $(".startupRoleNo .span-text").attr("title", mfCusCreditConfigTmp.startupRoleName);
                });
            },
        });
    };
    //初始化适配产品选择组件
    var initKindPopupSelection = function (data) {
        //角色新组件
        $("input[name=adaptationKindNo]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            title: "适配产品",
            items: data.kind,
            labelShow: false,
            changeCallback: function (obj, elem) {
                var mfCusCreditConfig = {};
                mfCusCreditConfig.creditId = creditId;
                mfCusCreditConfig.adaptationKindNo = $("input[name=adaptationKindNo]").val();
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    var adaptationKindName = mfCusCreditConfigTmp.adaptationKindName;
                    if(adaptationKindName == null || adaptationKindName == "null" || adaptationKindName == ""){
                        adaptationKindName = "未选择";
                    }
                    if (adaptationKindName.length > 50) {
                        adaptationKindName = adaptationKindName.substring(0, 50) + "...";
                    }
                    $(".adaptationKindNo .span-text").text(adaptationKindName);
                    $(".adaptationKindNo .span-text").attr("title", mfCusCreditConfigTmp.adaptationKindName);
                });
            },
        });
    };

    //初始化业务模式选择组件
    var initBusModelPopupSelection = function (data) {
        var count = 0;
        $("input[name=busModel]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: false,//单选
            title: "业务模式",
            items: data.busModelList,
            labelShow: false,
            changeCallback: function (obj, elem) {
                var mfCusCreditConfig = {};
                mfCusCreditConfig.creditId = creditId;
                mfCusCreditConfig.busModel = $("input[name=busModel]").val();
                //因会触发两次，因异步会造成数据错误，控制第二次再更新数据库
                if(count == 1){
                    var ajaxData = JSON.stringify(mfCusCreditConfig);
                    MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                        var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                        var busModelName = mfCusCreditConfigTmp.busModelName;
                        if(busModelName == null || busModelName == "null" || busModelName == ""){
                            busModelName = "未选择";
                        }
                        if (busModelName.length > 50) {
                           ame.substring(0, 50) + "...";
                        }
                        console.log(busModelName)
                        $(".busModel .span-text").text(busModelName);
                        $(".busModel .span-text").attr("title", busModelName);
                    });
                    count = 0;
                }else{
                    count ++;
                }


            },
        });
    };

    //初始化适配授信流程选择组件
    var initCreditKindPopupSelection = function (data) {
        //角色新组件
        $("input[name=adaptationCreditId]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            title: "适配授信流程",
            items: data.apaCreditKind,
            labelShow: false,
            changeCallback: function (obj, elem) {
                var mfCusCreditConfig = {};
                mfCusCreditConfig.creditId = creditId;
                mfCusCreditConfig.adaptationCreditId = $("input[name=adaptationCreditId]").val();
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    var adaptationCreditIdName = mfCusCreditConfigTmp.adaptationCreditIdName;
                    if(adaptationCreditIdName == null || adaptationCreditIdName == "null" || adaptationCreditIdName == ""){
                        adaptationCreditIdName = "未选择";
                    }
                    if (adaptationCreditIdName.length > 50) {
                        adaptationCreditIdName = adaptationCreditIdName.substring(0, 50) + "...";
                    }
                    $(".adaptationCreditId .span-text").text(adaptationCreditIdName);
                    $(".adaptationCreditId .span-text").attr("title", mfCusCreditConfigTmp.adaptationCreditIdName);
                });
            },
        });
    };


    function zTreeBeforeCheck(treeId, treeNode) {
        return !treeNode.isParent;//当是父节点 返回false 不让选取
    }

    //初始化选择组件
    var initPopUpSelection = function (data) {
        //开办部门选择组件初始化
        initBrNoPopupSelection(data);
        //开办角色选择组件初始化
        initRolePopupSelection(data);
        //适配产品选择组件初始化
        initKindPopupSelection(data);
        //适配业务模式选择组件初始化
        initBusModelPopupSelection(data);
        if(creditModel == "credit_3") {
            //适配授信流程选择组件初始化
            initCreditKindPopupSelection(data);
        }
        $(".pops-value").hide();
    };

    //产品名称
    var getPrdctNameConfigHtml = function (mfCusCreditConfig) {
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div">'
            + '<div class="item-title">'
            + '<span >授信流程名称 </span>'
            + '</div>'
            + '<div class="item-content">'
            + '<div class="main-content-div margin_bottom_5">'
            + '<span class="kindName">'
            + '<span class="span-read"><span>' + mfCusCreditConfig.creditName + '</span></span>'
            + '<span class="span-edit">'
            + '<input title="授信流程名称" name="creditName" mustinput="1" class="Required" onblur="func_uior_valTypeImm(this);"  type="text" value="' + mfCusCreditConfig.creditName + '">'
            + '</span>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var kindNameBindEvent = function () {
        $(".kindName .span-read").dblclick(function () {
            $(".kindName .span-read").css("display", "none");
            $(".kindName .span-edit").css("display", "inline-block");
            $("input[name=kindName]").css("width", $(this).width());
            $("input[name=kindName]").focus();

        });
        $("input[name=kindName]").blur(function () {
            var mfCusCreditConfig = {};
            mfCusCreditConfig.kindNo = kindNo;
            mfCusCreditConfig.kindName = $("input[name=kindName]").val();
            if ($("input[name=kindName]").val() != "") {
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    $(".kindName .span-text").text(mfCusCreditConfigTmp.kindName);
                    $(".nav-div .nav-title").text(mfCusCreditConfigTmp.kindName + "--" + mfCusCreditConfigTmp.busModelName);
                    $(".kindName .span-read").css("display", "inline-block");
                    $(".kindName .span-edit").css("display", "none");
                });
            } else {
                alert(top.getMessage("NOT_FORM_EMPTY", "产品名称"), 0);
            }
        });
    };


    //产品描述
    var getPrdctDescConfigHtml = function (mfCusCreditConfig) {
        var htmlStr = "";
        var remark = mfCusCreditConfig.remark;
        var spanText = mfCusCreditConfig.remark;
        if (remark == null) {
            spanText = '<span class="unregistered">未选择</span>';
            remark = "";
        }
        htmlStr = htmlStr + '<div class="item-div">'
            + '<div class="item-title">'
            + '<span >授信流程描述 </span>'
            + '</div>'
            + '<div class="item-content">'
            + '<div class="main-content-div margin_bottom_5">'
            + '<span class="kindDesc">'
            + '<span class="span-read"><span class="span-text">' + spanText + '</span></span>'
            + '<span class="span-edit">'
            + '<input title="授信流程描述" name="remark" mustinput="1" class="Required" onblur="func_uior_valTypeImm(this);"  type="text" value="' + remark + '">'
            + '</span>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var kindDescBindEvent = function () {
        $(".kindDesc .span-read").dblclick(function () {
            $(".kindDesc .span-read").css("display", "none");
            $(".kindDesc .span-edit").css("display", "inline-block");
            $("input[name=remark]").css("width", $(this).width());
            $("input[name=remark]").focus();

        });
        $("input[name=remark]").blur(function () {
            var mfCusCreditConfig = {};
            mfCusCreditConfig.creditId = creditId;
            mfCusCreditConfig.remark = $("input[name=remark]").val();
            if ($("input[name=remark]").val() != "") {
                var ajaxData = JSON.stringify(mfCusCreditConfig);
                MfSysCreditConfig11.updateCreditConfig(ajaxData, function (data) {
                    var mfCusCreditConfigTmp = data.mfCusCreditConfig;
                    $(".kindDesc .span-text").text(mfCusCreditConfigTmp.remark);
                    $(".kindDesc .span-read").css("display", "inline-block");
                    $(".kindDesc .span-edit").css("display", "none");
                });
            } else {
                alert(top.getMessage("NOT_FORM_EMPTY", "产品描述"), 0);
            }
        });
    };


    //客户类别
    var getPrdctCusTypeConfigHtml = function (mfCusCreditConfig, cusSubTypeList) {
        var subHtmlStr = '<div class="main-content-desc"><span class="content-desc">客户类别</span></div>'
            + '<div class="main-content-div">'
        $.each(cusSubTypeList, function (i, parmDic) {
            var curChecked = "";
            if (mfCusCreditConfig.startupCusType.indexOf(parmDic.optCode + "|") != -1) {
                curChecked = "curChecked";
            } else if (mfCusCreditConfig.startupCusType == parmDic.optCode) {
                curChecked = "curChecked";
            }
            subHtmlStr = subHtmlStr + '<span class="item-checkbox margin_right_25">'
                + '<span class="checkbox-span ' + curChecked + ' margin_right_5" data-custype="' + parmDic.optCode + '"><i class="i i-gouxuan1"></i></span>'
                + '<span>' + parmDic.optName + '</span>'
                + '</span>';
        });
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div startupCusType" data-oldval="' + mfCusCreditConfig.startupCusType + '">'
            + '<div class="item-title"><span>客户类别 </span></div>'
            + '<div class="item-content">'
            + subHtmlStr
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    //客户类别绑定事件
    var cusTypeBindEvent = function () {
        $(".startupCusType .checkbox-span").bind("click", function () {
            if ($(this).hasClass("curChecked")) {
                $(this).removeClass("curChecked");
            } else {
                $(this).addClass("curChecked");
            }
            var tmpStr = "";
            $.each($(".startupCusType .checkbox-span.curChecked"), function (index, item) {
                tmpStr = tmpStr + $(item).data("custype") + "|";
            });
            $(".startupCusType").data("oldval", tmpStr);
            var mfCusCreditConfig = {};
            mfCusCreditConfig.creditId = creditId;
            mfCusCreditConfig.startupCusType = tmpStr;
            var ajaxData = JSON.stringify(mfCusCreditConfig);
            MfSysCreditConfig11.updateCreditConfig(ajaxData);
        });
    };

    //业务模式
    var getPrdctBusModelConfigHtml = function (mfCusCreditConfig) {
        var busModelName = mfCusCreditConfig.busModelName;
        var busModel = mfCusCreditConfig.busModel;
        if(busModelName == null || busModelName == "null" || busModelName == ""|| busModelName.indexOf("null") != -1){
            busModelName = "未选择";
            busModel = "";
        }
        if (busModelName.length > 50) {
            busModelName = busModelName.substring(0, 50) + "...";
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div busModel">'
            + '<div class="item-title"><span>业务模式</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span class="span-read">'
            + '<span class="span-text" title="' + busModelName + '">' + busModelName + '</span>'
            + '<input  name="busModel" title="业务模式" type="hidden" value="' + busModel + '"/>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //业务模式编辑绑定点击事件
    var busModelBindEvent = function (data) {
        $(".busModel .span-read").dblclick(function () {
            $(this).find(".pops-value").click();
        });
    };


    //开办部门
    var getPrdctDepartmentConfigHtml = function (mfCusCreditConfig) {
        var startupBrName = mfCusCreditConfig.startupBrName;
        if(startupBrName == null || startupBrName == "null" || startupBrName == ""|| startupBrName.indexOf("null") != -1){
            startupBrName = "未选择";
        }
        if (startupBrName.length > 50) {
            startupBrName = startupBrName.substring(0, 50) + "...";
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div startupBrNo">'
            + '<div class="item-title"><span>开办部门</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span class="span-read">'
            + '<span class="span-text" title="' + startupBrName + '">' + startupBrName + '</span>'
            + '<input  name="startupBrNo" type="hidden" value="' + mfCusCreditConfig.startupBrNo + '"/>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //开办部门编辑绑定点击事件
    var brNoBindEvent = function (data) {
        $(".startupBrNo .span-read").dblclick(function () {
            $(this).find(".pops-value").click();
        });
    };
    //开办角色
    var getPrdctRoleConfigHtml = function (mfCusCreditConfig) {
        var startupRoleName = mfCusCreditConfig.startupRoleName;
        if(startupRoleName == null || startupRoleName == "null" || startupRoleName == "" || startupRoleName.indexOf("null") != -1){
            startupRoleName = "未选择";
        }
        if (startupRoleName.length > 50) {
            startupRoleName = startupRoleName.substring(0, 50) + "...";
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div startupRoleNo">'
            + '<div class="item-title"><span>开办角色</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span class="span-read">'
            + '<span class="span-text" title="' + startupRoleName + '">' + startupRoleName + '</span>'
            + '<input  name="startupRoleNo" title="开办角色" type="hidden" value="' + mfCusCreditConfig.startupRoleNo + '"/>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    //开办角色编辑绑定点击事件
    var roleNoBindEvent = function () {
        $(".startupRoleNo .span-read").dblclick(function () {
            $(this).find(".pops-value").click();
        });
    };
    //适配产品
    var getPrdctKindConfigHtml = function (mfCusCreditConfig) {
        var adaptationKindName = mfCusCreditConfig.adaptationKindName;
        if(adaptationKindName == null || adaptationKindName == "null" || adaptationKindName == "" || adaptationKindName.indexOf("null") != -1){
            adaptationKindName = "未选择";
        }
        if (adaptationKindName.length > 50) {
            adaptationKindName = adaptationKindName.substring(0, 50) + "...";
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div adaptationKindNo">'
            + '<div class="item-title"><span>适配产品</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span class="span-read">'
            + '<span class="span-text" title="' + adaptationKindName + '">' + adaptationKindName + '</span>'
            + '<input  name="adaptationKindNo" title="适配产品" type="hidden" value="' + mfCusCreditConfig.adaptationKindNo + '"/>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    //适配产品编辑绑定点击事件
    var adaptationKindNoBindEvent = function () {
        $(".adaptationKindNo .span-read").dblclick(function () {
            $(this).find(".pops-value").click();
        });
    };
    //适配授信流程
    var getAdaKindConfigHtml = function (mfCusCreditConfig) {
        var adaptationCreditIdName = mfCusCreditConfig.adaptationCreditIdName;
        if(adaptationCreditIdName == null || adaptationCreditIdName == "null" || adaptationCreditIdName == "" || adaptationCreditIdName.indexOf("null") != -1){
            adaptationCreditIdName = "未选择";
        }
        if (adaptationCreditIdName.length > 50) {
            adaptationCreditIdName = adaptationCreditIdName.substring(0, 50) + "...";
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div adaptationCreditId">'
            + '<div class="item-title"><span>适配授信流程</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span class="span-read">'
            + '<span class="span-text" title="' + adaptationCreditIdName + '">' + adaptationCreditIdName + '</span>'
            + '<input  name="adaptationCreditId" title="适配授信流程" type="hidden" value="' + mfCusCreditConfig.adaptationCreditId + '"/>'
            + '</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    //适配授信流程编辑绑定点击事件
    var adaptationCreditKindBindEvent = function () {
        $(".adaptationCreditId .span-read").dblclick(function () {
            $(this).find(".pops-value").click();
        });
    };


    //是否启用
    var getPrdctAuthCycleConfigHtml = function (mfCusCreditConfig) {
        var checkStr0 = "";
        var checkStr1 = "";
        if (mfCusCreditConfig.ifUse == "1") {
            checkStr1 = 'checked="checked"';
        } else {
            checkStr0 = 'checked="checked"';
        }
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="item-div ifUse">'
            + '<div class="item-title"><span>是否启用</span></div>'
            + '<div class="item-content">'
            + '<div class="main-content-div">'
            + '<span id="ifUse1" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifUse" value="1" ' + checkStr1 + '>是</span>'
            + '<span id="ifUse0" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifUse" value="0" ' + checkStr0 + '>否</span>'
            + '</div>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //是否启用
    var authCycleBindEvent = function () {
        $(".ifUse input[type=radio]").bind("click", function () {
            var mfCusCreditConfig = {};
            mfCusCreditConfig.creditId = creditId;
            mfCusCreditConfig.ifUse = $(this).val();
            var ajaxData = JSON.stringify(mfCusCreditConfig);
            MfSysCreditConfig11.updateCreditConfig(ajaxData);
        });
    };

    return {
        init: _init,
    };
}(window, jQuery);