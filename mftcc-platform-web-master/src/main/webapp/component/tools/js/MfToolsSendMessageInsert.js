;
var MfToolsSendMessageInsert = function(window, $) {
    var _init = function () {
        $("#list-div").height($("#list-div").parent().parent().height()-300);
        $("#tablist span[mytitle]:contains('...')").initMytitle();
        $("#tablist p[mytitle]:contains('...')").initMytitle();
        var len = $("#list-div tbody tr").length;
        $(".pageCount").text(len);
        if (len >= 10) {
            var time = 10;//每页显示条数
            var times = parseInt(len / time);
            var j = 1;
            $(".loadCount").text("10");
            for ( var i = 10; i < len; i++) { //10以後的先隐藏
                $("tbody tr:eq(" + i + ")").hide();
            }
            var nScrollHeight = 0;
            var nScrollTop = 0;
            var nDivHeight = $(".notice-div").height();
        } else {
            $(".loadCount").text(len);
        }
        $("textarea[name=sendMsg]").css("width", "80%");
        var textareaDiv = $("textarea[name=sendMsg]").parent();
        textareaDiv
            .append("<span style='position: absolute;top: 65%;right: 10px;' class=\"divc\"></span>");
        $("#list-div")
            .mCustomScrollbar(
                {
                    advanced : {
                        theme : "minimal-dark",
                        updateOnContentResize : true
                    },
                    callbacks : {
                        onUpdate : function(){
                            $(window).resize(function() {
                                $("#list-div").height($("#list-div").parent().parent().height()-300);
                            });
                        },
                        whileScrolling : function() {
                            nScrollHeight = $(this)[0].scrollHeight; //是notice-div的高度
                            nScrollTop = $(this)[0].scrollTop;//从顶端到现在滚动的高度
                            var paddingBottom = parseInt($(this).css(
                                'padding-bottom'));//.notice-div的内边距
                            var paddingTop = parseInt($(this).css(
                                'padding-top'));
                            if (paddingBottom + paddingTop + nScrollTop
                                + nDivHeight >= nScrollHeight) {//判斷滾動條到達底部
                                console.log(paddingBottom);
                                console.log(paddingTop);
                                $(".fa-3x").show();
                                setTimeout(function() {
                                    $(".fa-3x").hide();
                                }, 1000);
                                var i;
                                if (j == times) {
                                    for ( i = time; i < len; i++) {
                                        $("tbody tr:eq(" + i + ")")
                                            .show();
                                        $(".loadCount").text(len);
                                    }
                                } else if (j < times) {
                                    for ( i = time * j; i < time
                                    * (j + 1); i++) {
                                        $("tbody tr:eq(" + i + ")")
                                            .show();
                                    }
                                    j++;
                                    $(".loadCount").text(time * j);
                                }

                                //滚动分页结束
                            }
                        }
                    }
                });
        //处理暂无数据的情况
        if ($('#list-div tbody tr').length == 0) {
            $(".footer_loader").hide();
        }

        /*if(!$("input[name=msgTel]").attr("readonly")){
            $("input[name=msgTel]").bind("click", function(event) {
                //bindDataSource(this,'2','coborrNum');;
                cusUserSelect(this,'1','msgTel');
            });
        }*/
        _bindInsertAjax("#MfToolsSendMessageInsert");
        _bindCloseAjax();
    };

    var _bindInsertAjax = function(obj){
        $(".insertAjax").bind("click",function(){
            var msgTel = $("input[name=msgTel]").val();
            var sendMsg = $("textarea[name=sendMsg]").val();
            if (msgTel == "") {
                alert(top.getMessage("FIRST_SELECT_FIELD", "手机号码"), 0);
                return false;
            }
            if (sendMsg == "") {
                alert(top.getMessage("NOT_FORM_EMPTY", "短信内容"), 0);
                return false;
            }
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : {
                    ajaxData : dataParam
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        $("input[name=msgTel]").val("");
                        $("input[name=sendMsg]").val("");
                        window.location.reload();
                        window.top.alert(data.msg, 1);
                    } else if (data.flag == "failed") {
                        $("input[name=msgTel]").val("");
                        $("input[name=sendMsg]").val("");
                        window.location.reload();
                        if(data.msg=="无法送额度"){
                            window.top.alert(top.getMessage("NO_SEND_MONEY"), 2,function(){
                                payUrl = "/servicemanage/recharge/pay.html";
                                //top.openBigForm(payUrl,"账户充值");
                                window.location.href=payUrl;
                            });
                        }
                        window.top.alert(data.msg, 0);
                    } else {
                        alert(top.getMessage("FAILED_MSG_SEND"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                }
            });
        });
    };
    var _bindCloseAjax = function(){
        $(".cancel").bind("click", function(event){
            myclose_showDialog();
        });
    };
    var  _changeReceiver = function(obj) {
        $("input[name=msgTel]").unbind("click");
        var sendMsgType = $(obj).val();
        $(".pops-label").remove();
        $("input[name=msgTel]").val("");
        $(".list-checkbox").prop("checked", false);
        $(".pops-bg").remove();
        $(".pops-value").remove();
        $("input[name=msgTel]").show();
        /*if (2 == sendMsgType) { //员工

            $("input[name=msgTel]").bind("click", function(event) {
                //bindDataSource(this,'2','coborrNum');;
                cusUserSelect(this,'2','msgTel');
            });
        } else { // 客户
            $("input[name=msgTel]").bind("click", function(event) {
                cusUserSelect(this,'1','msgTel');
            });
        }*/
    };

    var _getSysUsetTel = function(sysUserTelInfo) {
        $("input[name=msgTel]").val(sysUserTelInfo.cusTel);
    };

    var _getCusPhone = function(cusPhoneInfo) {
        $("input[name=msgTel]").val(cusPhoneInfo.cusTel);
    };

    var _userSelect = function(obj, type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_,ifAdd){
        //表单编号
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        if (formId_) {// 当formId_有值时，以formId_为准
            formId = formId_;
        }
        //绑定事件的input框
        var element = $(obj).attr('name');
        if(type == '2'){//选择员工
            var title = '选择员工';
            var multipleFlag = true;//多选标志 true多选,false 单选
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: multipleFlag, //false单选，true多选，默认多选
                ajaxUrl : webPath+"/mfUserPermission/getOpDataSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag,// 请求数据URL
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: "选择员工",//标题
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                    var sltVal = elem.data("selectData");
                    var msgTel = '';
                    for(let i in sltVal){
                        msgTel = msgTel + sltVal[i].opName+"("+sltVal[i].mobile+")"+";";
                    }
                    $("input[name='msgTel']").val(msgTel);
                },
                tablehead:{//列表显示列配置
                    "opName":"员工名称",
                    "opNo":"员工编号",
                    "brName":"部门",
                    "mobile":"电话"
                },
                returnData:{//返回值配置
                    disName:"opName",//显示值
                    value:"mobile"//真实值
                }
            });
        }
        $('input[name='+element+']').next().click();
    }

    var _cusSelect = function(obj, type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_,ifAdd){
        //表单编号
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        if (formId_) {// 当formId_有值时，以formId_为准
            formId = formId_;
        }
        //绑定事件的input框
        var element = $(obj).attr('name');
        var title = '选择客户';
        var multipleFlag = true;//多选标志 true多选,false 单选
        if(typeof(cusNo) == "undefined"){
            var  cusNo = $("input[name=cusNo]").val();
        }
        var ajaxUrl =webPath+"/mfUserPermission/getPerDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo + "&cusType=" + cusType;//请求数据URL;
        if(parmTitle != undefined){
            title = parmTitle;
        }
        if(parmMultipleFlag != undefined){
            multipleFlag = parmMultipleFlag;
        }
        if(cusBaseType != undefined){//客户类型
            ajaxUrl+="&cusBaseType="+cusBaseType;
        }
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: multipleFlag, //false单选，true多选，默认多选
            ajaxUrl:ajaxUrl,
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: title,//标题
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                var sltVal = elem.data("selectData");
                var msgTel = '';
                for(let i in sltVal) {
                    msgTel = msgTel + sltVal[i].cusName + "(" + sltVal[i].cusTel + ")" + ";";
                }
                $("input[name='msgTel']").val(msgTel);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":"证件号码",
                "cusTel":"电话"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusTel"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    }

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init : _init,
        changeReceiver : _changeReceiver,
        cusSelect : _cusSelect,
        userSelect : _userSelect
    };
}(window, jQuery);