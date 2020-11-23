var MfBusStopIntstApply = {
    init(){
        $('input[name=fincShowId]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusStopIntstApply/getFincListAjax",//请求数据URL
            valueElem:"input[name=fincId]",//真实值选择器
            title: "选择借据",//标题
            searchplaceholder: "客户名称/申请号",//查询输入框的悬浮内容
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name=fincShowId]"));
                var sltVal = elem.data("selectData");
                $('input[name=cusName]').val(sltVal.cusName);
                $('input[name=kindName]').val(sltVal.kindName);
                $('input[name=appId]').val(sltVal.appId);
                $('input[name=pactId]').val(sltVal.pactId);
                $('input[name=pactNo]').val(sltVal.pactNo);
                $('input[name=kindNo]').val(sltVal.kindNo);
                $('input[name=loanAmt]').val(sltVal.putoutAmt);
                $('input[name=appName]').val(sltVal.appName);
                $('input[name=loanBal]').val(sltVal.loanBal);
                $('input[name=cusNo]').val(sltVal.cusNo);
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "pactNo":"合同编号",
                "appName":"融资申请号",
                "fincShowId":"借据号"
            },
            returnData:{//返回值配置
                disName:"fincShowId",//显示值
                value:"fincId"//真实值
            }
        });
    },
    //新增保存申请
    insertSave:function(formObj){
        var url = $(formObj).attr("action");
        var dataForm = JSON.stringify($(formObj).serializeArray());
        LoadingAnimate.start();
        $.ajax({
            url : url,
            data : {
                ajaxData : dataForm,
            },
            type : "post",
            dataType : "json",
            success : function(data) {
                top.flag=true;
                LoadingAnimate.stop();
                if (data.flag == "success") {

                    alert(data.msg, 3,function(){
                        myclose_click();
                    });


                } else {
                    LoadingAnimate.stop();
                    window.top.alert(data.msg, 0);
                }
            }
        });
    },
    cusChange:function(){
        //初始化借据号选择组件

    },
//选择用户
    getusertinfo:function () {
        var url="/component/archives/MfArchiveuserinfo.jsp";
        top.applyUserNo="";
        top.applyUserName="";
        openCreatShowDialog(url,"选择人员",70,70,function(data){
            if(top.applyUserNo==""){
                return
            }else {
                $("input[name='applyName']").val(top.applyUserName);
                $("input[name='applyNo']").val(top.applyUserNo);
            }
        });
    },  //回调选择的数据
    getbusappinfo:function(){
        var url="/component/pact/stopintst/MfBusStopIntstApply_getbusappinfo.jsp";
        top.fincId="";
        top.cusNo="";
        top.cusName="";
        top.appId="";
        top.appName="";
        top.pactId="";
        top.pactNo="";
        top.loanBal="";
        top.intstEndDate="";
        top.fincShowId="";
        top.lastReturnDate="";
        openCreatShowDialog(url,"选择合同",70,70,function(data){
            if(top.fincId==""){
                return
            }else {
                $("input[name='fincId']").val(top.fincId);
                $("input[name='cusNo']").val(top.cusNo);
                $("input[name='cusName']").val(top.cusName);
                $("input[name='appId']").val(top.appId);
                $("input[name='appName']").val(top.appName);
                $("input[name='pactId']").val(top.pactId);
                $("input[name='pactNo']").val(top.pactNo);
                $("input[name='loanBal']").val(top.loanBal);
                $("input[name='intstEndDate']").val(top.intstEndDate);
                $("input[name='lastReturnDate']").val(top.lastReturnDate);
                $("input[name='fincShowId']").val(top.fincShowId);
            }
        });
    },//获取选择数据
    choseCredit:function(parm){
    parm=parm.split("?")[1];
    var parmArray=parm.split("&");
    var fincId = parmArray[0].split("=")[1];
    var cusNo=parmArray[1].split("=")[1];
    var cusName=parmArray[2].split("=")[1];
    var appId=parmArray[3].split("=")[1];
    var appName=parmArray[4].split("=")[1];
    var pactId=parmArray[5].split("=")[1];
    var pactNo=parmArray[6].split("=")[1];
    var loanBal=parmArray[7].split("=")[1];
    var intstEndDate=parmArray[8].split("=")[1];
    var fincShowId=parmArray[9].split("=")[1];
    var lastReturnDate=parmArray[10].split("=")[1];
    top.fincId = fincId;
    top.cusNo=cusNo;
    top.cusName=cusName;
    top.appId=appId;
    top.appName=appName;
    top.pactId=pactId;
    top.pactNo=pactNo;
    top.loanBal=loanBal;
    top.intstEndDate=intstEndDate;
    top.fincShowId=fincShowId;
    top.lastReturnDate=lastReturnDate;
    myclose_showDialogClick();
},
    stopPlanDate:function(){
        fPopUpCalendarDlg({min:today});
        /*var Date=$("input[name='lastReturnDate']").val();
        var enddate=$("input[name='intstEndDate']").val();
        if(Date==""&&enddate == "" ){
            return  false;
        }
        else if(Date!=""){
            var dateString =Date;
            var timestring=enddate;
            var pattern = /(\d{4})(\d{2})(\d{2})/;
            var patterns=/(\d{4})(\d{2})(\d{2})/;
            var formatedDates=timestring.replace(patterns, '$1-$2-$3');
            var formatedDate = dateString.replace(pattern, '$1-$2-$3');
            if(formatedDate>formatedDates){
                fPopUpCalendarDlg({min:formatedDate});
            }else{
                fPopUpCalendarDlg({min:formatedDates});
            }

        }else{
            var dateString =enddate;
            var pattern = /(\d{4})(\d{2})(\d{2})/;
            var formatedDate = dateString.replace(pattern, '$1-$2-$3');
            fPopUpCalendarDlg({min:formatedDate});
        }*/
    }
}
function usertinfo(parm) {
    parm=parm.split("?")[1];
    var parmArray=parm.split("&");
    var applyUserName = parmArray[0].split("=")[1];
    var applyUserNo=parmArray[1].split("=")[1];
    top.applyUserName = applyUserName;
    top.applyUserNo=applyUserNo;
    myclose_showDialogClick();
};