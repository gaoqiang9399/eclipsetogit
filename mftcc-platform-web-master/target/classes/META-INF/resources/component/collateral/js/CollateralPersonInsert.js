var CollateralPersonInsert = function(window, $){

    //保证人弹出层数据
    var _selectAssurePersonList = function(){

        var obj= $('input[name=assureName]');
        var cusBaseType = $('select[name=assureType]').val();
        $("input[name=assureName]").parent().find('div').remove();
        var cusType = $("select[name=assureCusType]").val();
        var cusTypeSet = _getCusTypeSet1(cusType);// 根据客户类别查询业务身份(parm_dic.key_name = 'CUS_TYPE_SET')
        if (cusTypeSet == '9') {// 客户分类是担保公司
            bindDataSource1(obj, '9', 'idNum', '选择担保公司', false, cusBaseType, cusType);
        } else {
            bindDataSource1(obj, '4', 'idNum', '选择保证人', false, cusBaseType, cusType, false, function (data) {
                alert("");
                $("input[name='assureNo']").val(data.cusNo);
                $("input[name='idNum']").val(data.idNum);
                $("select[name='idType']").val(data.idType);
                $("input[name='idNum']").attr("readonly", "readonly");
                //$("select[name='idType']").attr("disabled","disabled");
                var assureType = $("[name=assureType]").val();
                if (assureType == "1") {
//					getCusCorpBaseInfo(data.cusNo);
                } else if (assureType == "2") {
                    if (data.cusNo != "assure") {
                        getCusPersBaseInfo1(data.cusNo);
                    }
                }
            });
        }

        isQuote="1";
    };

   var getCusPersBaseInfo1  = function(cusno){

        $.ajax({
            url:webPath+"/mfCusPersBaseInfo/getCusPersBaseInfoAjax",
            data:{"cusNo":cusno},
            success : function(data) {
                var baseInfo = data.baseInfo;
                var jobInfo = data.jobInfo;
                if(baseInfo){
                    $("input[name='cusTel']").val(baseInfo.cusTel);
                    $("[name='marrige']").val(baseInfo.marrige);
                    $("[name='nationality']").val(baseInfo.nationality);
                    $("[name='education']").val(baseInfo.education);
                    $("[name='brithday']").val(baseInfo.brithday);
                    $("input[name='careaProvice']").val(baseInfo.careaProvice);
//					if(baseInfo.careaCity){
//						$("input[name='careaProvice']").parent().find(".pops-value.show-text").text(baseInfo.careaCity);
//					}
                    $("[name='careaCity']").val(baseInfo.careaCity);
                    $("[name='regHomeAddre']").val(baseInfo.regHomeAddre);
                    $("[name='commAddress']").val(baseInfo.commAddress);
                    $("[name='postalCode']").val(baseInfo.postalCode);
                    $("[name='sex']").val(baseInfo.sex);
                    $("[name='age']").val(baseInfo.age);
                }
                if(jobInfo){
                    $("[name='workUnit']").val(jobInfo.workUnit);
                    $("[name='techTitle']").val(jobInfo.techTitle);
                }
            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };


    /** 根据客户类别查询业务身份 */
    var _getCusTypeSet1 = function (cusType) {
        var result;

        $.ajax({// 根据客户类别查询业务身份
            url : webPath+"/mfCusType/getCusTypeSetAjax",
            data : {
                "typeNo" : cusType
            },
            type : "POST",
            dataType : "json",
            async : false,
            beforeSend : function() {
            },
            success : function(data) {
                result = data.cusTypeSet;
            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });

        return result;
    };

    /**
     *
     * @param obj 当前input框  this
     * @param type 类型  1：操作员，2共同借款人,3:渠道商
     * @param hide 存放真实编号的隐藏域name
     * @param parmTitle 标题
     * @param parmMultipleFlag false单选，true多选，默认多选
     * @param cusBaseType 客户大类 1-企业客户 2-个人客户
     * @param cusType
     * @param ifFilterFlag 是否过滤当前操作员1-是 0-否
     * @param formId_ 详情页面单字段编辑是悬浮框，取不到formId，所以用参数方式处理
     * @param ifAdd 共借人新增按钮是否隐藏
     */
    function bindDataSource1(obj, type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_,ifAdd){
        //表单编号
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        if (formId_) {// 当formId_有值时，以formId_为准
            formId = formId_;
        }
        //绑定事件的input框
        var element = $(obj).attr('name');

        var ajaxUrl;
        var title;
        var multipleFlag;
        if(type == '1'){
            if (!ifFilterFlag) {
                ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
            }
            multipleFlag = false;//多选标志 true多选,false 单选
            if(parmMultipleFlag != undefined){
                multipleFlag = parmMultipleFlag;
            }
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: multipleFlag, //false单选，true多选，默认多选
                ajaxUrl : webPath+"/mfUserPermission/getOpDataSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag,// 请求数据URL
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: "选择人员",//标题
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                },
                tablehead:{//列表显示列配置
                    "opName":"人员名称",
                    "opNo":"人员编号"
                },
                returnData:{//返回值配置
                    disName:"opName",//显示值
                    value:"opNo"//真实值
                }
            });
        }
        else if(type == '3'){// 渠道商
            title = '选择渠道商';
            if(parmTitle != undefined){
                title = parmTitle;
            }
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: false, //false单选，true多选，默认多选
                ajaxUrl:webPath+"/mfUserPermission/getChannelSourceAjax?formId="+formId+"&element="+element,//请求数据URL
//			valueElem:"input[name='channelSourceNo']",//真实值选择器
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: title,//标题
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                },
                tablehead:{//列表显示列配置
                    "trenchUid":"渠道商",
                    "trenchName":"渠道商编号"
                },
                returnData:{//返回值配置
                    disName:"trenchName",//显示值
                    value:"trenchUid"//真实值
                }
            });
        }else if(type == '4'){// 保证人
            ajaxUrl =webPath+"/mfUserPermission/getAssureDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo + "&cusType=" + cusType+"&cusBaseType="+cusBaseType;//请求数据URL;
            $("input[name=assureNameHidden]").popupList({
                searchOn: true, //启用搜索
                multiple: parmMultipleFlag, //false单选，true多选，默认多选
                ajaxUrl:  ajaxUrl,
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: parmTitle,//标题
                labelShow:false,
                elemEdit:true,
                changeCallback:function(elem){
                    //回调方法
                    var personvalue = elem.data("selectData");
                    $('input[name=assureName]').not(':disabled').val(personvalue.cusName);
                    $('.hidden-content').find('div[class=pops-value]').hide();
                    if(typeof(callback)== "function"){
                        callback(personvalue);
                    }
                },
                tablehead:{//列表显示列配置
                    "cusName":"客户名称",
                    "idNum":"证件号码"
                },
                returnData:{//返回值配置
                    disName:"cusName",//显示值
                    value:"idNum"//真实值
                }
            });
        } else if (type == '9') {// 担保公司
            ajaxUrl = webPath+"/mfCusAssureCompany/getAssureCompanyAjax?cusType=" + cusType + "&cusBaseType=" + cusBaseType;// 请求数据URL;
            $(obj).popupList({
                searchOn : true, // 启用搜索
                multiple : parmMultipleFlag, // false单选，true多选，默认多选
                ajaxUrl : ajaxUrl,
                valueElem : "input[name=" + hide + "]",// 真实值选择器
                title : parmTitle,// 标题
                labelShow : false,
                changeCallback : function(elem) {// 回调方法
                    BASE.removePlaceholder($("input[name=" + element + "]"));
                    var sltVal = elem.data("selectData");
                    $("input[name='assureNo']").val(sltVal.cusNo);
                    $("select[name='idType']").val(sltVal.idType);
                },
                tablehead : {// 列表显示列配置
                    "cusName" : "客户名称",
                    "idNum" : "证件号码"
                },
                returnData : {// 返回值配置
                    disName : "cusName",// 显示值
                    value : "idNum"// 真实值
                }
            });
        }else if (type == 'A') {// 集团客户
            ajaxUrl = webPath+"/mfCusGroup/getCusGroupAjax";// 请求数据URL;
            $(obj).popupList({
                searchOn : true, // 启用搜索
                multiple : parmMultipleFlag, // false单选，true多选，默认多选
                ajaxUrl : ajaxUrl,
                valueElem : "input[name=" + hide + "]",// 真实值选择器
                title : parmTitle,// 标题
                labelShow : false,
                changeCallback : function(elem) {// 回调方法
                    BASE.removePlaceholder($("input[name=" + element + "]"));
                    var sltVal = elem.data("selectData");
                    $("input[name='groupNo']").val(sltVal.idNum);

                },
                tablehead : {// 列表显示列配置
                    "groupName" : "集团名称",
                    "idNum" : "集团代码"
                },
                returnData : {// 返回值配置
                    disName : "groupName",// 显示值
                    value : "idNum"// 真实值
                }
            });
        }else if(type == '5'){// 核心企业
            title = '选择核心企业';
            if(parmTitle != undefined){
                title = parmTitle;
            }
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: false, //false单选，true多选，默认多选
                ajaxUrl:webPath+"/mfUserPermission/getCoreCompanySourceAjax?formId="+formId+"&element="+element,//请求数据URL
//			valueElem:"input[name='channelSourceNo']",//真实值选择器
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: title,//标题
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                },
                tablehead:{//列表显示列配置
                    "coreCompanyName":"核心企业",
                    "coreCompanyUid":"核心企业编号"
                },
                returnData:{//返回值配置
                    disName:"coreCompanyName",//显示值
                    value:"coreCompanyUid"//真实值
                }
            });
        }else if(type=='18'){
            var fincId = $(obj).parents('form').find('input[name=fincId]').val();
            if (!ifFilterFlag) {
                ifFilterFlag = '';// 如果未传值则给空默认值，避免undefined字符串
            }
            multipleFlag = false;//多选标志 true多选,false 单选
            if(parmMultipleFlag != undefined){
                multipleFlag = parmMultipleFlag;
            }
            $(obj).popupList({
                searchOn: true, //启用搜索
                multiple: multipleFlag, //false单选，true多选，默认多选
                ajaxUrl : webPath+"/mfUserPermission/getAmountSourceAjax?formId=" + formId + "&element=" + element + "&ifFilterFlag=" + ifFilterFlag+"&fincId="+fincId,// 请求数据URL
                valueElem:"input[name="+hide+"]",//真实值选择器
                title: "选择追偿期数",//标题
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name="+element+"]"));
                    var sltVal = elem.data("selectData");
                    $("input[name='planId']").val(sltVal.planId);
                },
                tablehead:{//列表显示列配置
                    "termNum":"期号",
                    "compensatoryFeeSum":"代偿总额",
                    "planId":"流水号"
                },
                returnData:{//返回值配置
                    disName:"compensatoryFeeSum",//显示值
                    value:"compensatoryFeeSum"//真实值
                }
            });
        }
        else{

            title = '选择共同借款人';
            multipleFlag = true;//多选标志 true多选,false 单选
            ajaxUrl =webPath+"/mfUserPermission/getPerDataSourceAjax?formId="+formId+"&element="+element+"&cusNo="+cusNo + "&cusType=" + cusType;//请求数据URL;
            if(parmTitle != undefined){
                title = parmTitle;
            }
            if(parmMultipleFlag != undefined){
                multipleFlag = parmMultipleFlag;
            }
            if(cusBaseType != undefined){//客户类型
                ajaxUrl+="&cusBaseType="+cusBaseType;
            }
            if(ifAdd){

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
                        $("input[name='assureNo']").val(sltVal.cusNo);
                        $("input[name='consignerNo']").val(sltVal.cusNo);
                        $("input[name='consignerName']").val(sltVal.cusName);
                        $("input[name='recommenderNo']").val(sltVal.cusNo);
                        $("input[name='recommenderName']").val(sltVal.cusName);
                        $("input[name='certificateNum']").val($("input[name='"+hide+"']").val());

                    },
                    tablehead:{//列表显示列配置
                        "cusName":"客户名称",
                        "idNum":"证件号码"
                    },
                    returnData:{//返回值配置
                        disName:"cusName",//显示值
                        value:"idNum"//真实值
                    }
                });
            }else{
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
                        $("input[name='assureNo']").val(sltVal.cusNo);
                        $("input[name='consignerNo']").val(sltVal.cusNo);
                        $("input[name='consignerName']").val(sltVal.cusName);
                        $("input[name='recommenderNo']").val(sltVal.cusNo);
                        $("input[name='recommenderName']").val(sltVal.cusName);
                        $("input[name='certificateNum']").val($("input[name='"+hide+"']").val());

                    },
                    tablehead:{//列表显示列配置
                        "cusName":"客户名称",
                        "idNum":"证件号码"
                    },
                    returnData:{//返回值配置
                        disName:"cusName",//显示值
                        value:"idNum"//真实值
                    },
                    addBtn:{//添加扩展按钮
                        "title":"新增",
                        "fun":function(hiddenInput, elem){
                            top.window.openBigForm(webPath+"/mfCusCustomer/inputCoborr","新增客户", function(){
                                $(obj).popupList("initItems",$(obj),$(obj).data("options"));
                            });
                        }
                    }
                });
            }
        }
        //判断是否是保证人类型
        if(type=='4'){
            $('input[name=assureNameHidden]').next().click();
        }else{
            $('input[name='+element+']').next().click();
        }

    }
    return {
        selectAssurePersonList:_selectAssurePersonList,
        getCusTypeSet1 : _getCusTypeSet1,
    };

}(window, jQuery);