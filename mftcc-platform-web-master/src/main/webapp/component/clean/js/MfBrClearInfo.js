;
var MfBrClealInfo = function(window,$){
    var _init = function(){

    };
    //选择部门
    var _selectbr=function(callback){
        dialog({
            id:"orgSelectDialog",
            title:'选择部门',
            url:webPath+'/sysOrg/sysOrgSelect',
            width:525,
            height:495,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    if(typeof(callback)== "function"){
                        callback(this.returnValue);
                    }else{
                    }
                }
            }
        }).showModal();
        $("#tablebrclear0001").empty();
    };

    //拿到部门编号
    var _setSysOrgInfo=function(sysOrg){
        $("input[name=upOneName]").val(sysOrg.brName);
        $("input[name=brNo]").val(sysOrg.brNo);
        //清空操作员编号和姓名
        $("input[name=opNo]").val("");
        $(".pops-value").empty();
        _clearList();
    };

    //展示客户业务列表信息
    _clearList=function(){
        var dataParam =JSON.stringify($("#clearBrForm").serializeArray());
        $.ajax({
            url:"/mfDataClean/cusBusinessInfo",
            data:{ajaxData:dataParam},
            success:function(data){
                var html = data.htmlStr;
                $("#tablebrclear0001").empty().html(html);
            }
        });
    };

    //根据部门选择操作员
    var _selectOpByBr=function() {
        var brNo=$("input[name=brNo]").val();
        $("input[name=opName]").parent().find('div').remove();
        $('input[name=opName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfDataClean/selectOpByBr?brNo="+brNo,//请求数据URL
            valueElem:"input[name=opNo]",//真实值选择器
            title: "选择操作员",//标题
            changeCallback:function(elem){//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=opNo]").val(tmpData.opNo);
                _clearList();
            },
            tablehead:{//列表显示列配置
                "opName":"操作员",
                "brName":"部门"
            },
            returnData:{//返回值配置
                disName:"opName",//显示值
                value:"opNo"//真实值
            }
        });
        $("input[name=opName]").parent().find(".pops-value").click();
    }

    var _Clear=function () {
        var dataParam =JSON.stringify($("#clearBrForm").serializeArray());
        LoadingAnimate.start();
        $.ajax({
            url:"/mfDataClean/dataBrClear",
            data:{ajaxData:dataParam},
            success:function (data) {
                LoadingAnimate.stop();
                var noClearName=data.noClearName;
                if(noClearName!=null) {
                    alert("请重新选择部门或者操作员。  原因："+noClearName,0);
                }
                var cleanFlag=data.cleanFlag;
                if(cleanFlag=="1"){
                    window.top.alert(data.msg,1);
                    myclose_click();
                }else{
                    LoadingAnimate.stop();
                    window.top.alert(data,msg,0);
                }

            }
        })
    }
    //部门清理
    var _dataBrClear=function () {
        var flag = submitJsMethod($("#clearBrForm").get(0), '');
        if(flag){
            alert(top.getMessage("CONFIRM_CLEAN"),2,function(){
                _Clear();

            });
        }
    }


    return{
        init:_init,
        selectOpByBr:_selectOpByBr,
        selectbr:_selectbr,
        setSysOrgInfo:_setSysOrgInfo,
        dataBrClear:_dataBrClear
    };
}(window,jQuery);
