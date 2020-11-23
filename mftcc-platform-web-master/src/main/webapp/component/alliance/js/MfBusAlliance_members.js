;

//成员列表
var memberList = function(window, $) {
    //正常初始化
    var _init = function (){
        $.ajax({
            url: webPath+'/mfBusAlliance/getMemberListAjax?allianceId='+allianceId+"&tableId="+allianceCusFormId,
            type:'post',
            dataType:'json',
            success:function(data){
                var $html = $(data.tableData);
                $(".content[name=putoutHis]").html(data.tableData);
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //列表删除
    var _ajaxDelete = function(obj,url){
        // url  MfBusStore/deleteAjax;id-id;brNo-brNo;onClick-MfBusStore.ajaxDelete(this)
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+url,
                dataType:'json',
                type:'post',
                success : function(data){
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        _init();//重新加载列表数据
                        allianceDetailInfo.init();
                    } else {

                        window.top.alert(data.msg, 0);
                    }
                }
            });
        });
    };
    var _addAllianceCustomer=function(){
        top.openBigForm(webPath + "/mfBusAlliance/alliancecustomerinput?allianceId="+allianceId, "新增联保体成员", function() {
            _init();
        });
    };
    var _insertAllianceCustomerAjax=function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : dataParam
                },
                type : "POST",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.alert(data.msg, 1);
                        myclose_click();
                    }
                    if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }
            });
        }
    };
    var _myclose = function(){
        myclose_click();
    };
    //查找个人用户
    var _selectPersonalCus=function(obj, type, hide, parmTitle, parmMultipleFlag, cusBaseType, cusType, ifFilterFlag, callback, formId_,ifAdd){
        //表单编号
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        if (formId_) {// 当formId_有值时，以formId_为准
            formId = formId_;
        }
        //绑定事件的input框
        var element = $(obj).attr('name');
        var title = '选择共同借款人';
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

        //if($('input[name=areaCode]').val()!=''){
            ajaxUrl=webPath+"/mfBusAlliance/findCustomerByPageAjax"
        //}
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: multipleFlag, //false单选，true多选，默认多选
            ajaxUrl:ajaxUrl,
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: title,//标题
            labelShow:false,
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":"证件号码"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $('input[name='+element+']').next().click();
    };
    //设置组长
    var _allianceCustomerSetLeader = function(obj,url){
        alert("确定要设为组长吗？一个联保体只可以有一个组长",2,function(){
            $.ajax({
                url:webPath+url,
                dataType:'json',
                type:'post',
                success : function(data){
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        _init();//重新加载列表数据
                        allianceDetailInfo.init();
                        console.log($("span #contactsName").length);
                        $("span #contactsName").empty().html($(obj).parent().siblings()[0].innerHTML)
                        $("span #contactsTel").empty().html($(obj).parent().siblings()[2].innerHTML);
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                }
            });
        });
    };

    return {
        init : _init,
        allianceCustomerDelete:_ajaxDelete,
        addAllianceCustomer:_addAllianceCustomer,
        insertAllianceCustomerAjax:_insertAllianceCustomerAjax,
        myclose : _myclose,
        selectPersonalCus:_selectPersonalCus,
        allianceCustomerSetLeader:_allianceCustomerSetLeader};
}(window, jQuery);
function oneCallback(data, disVal){
    var name = data[0].name;
    var value = data[0].value;
    var $_form = this;
    var formAction = $_form.attr("action");
    if ($_form.attr("id") === 'listForm') {
        BASE.oneRefreshTable(name,disVal);
    }
}



