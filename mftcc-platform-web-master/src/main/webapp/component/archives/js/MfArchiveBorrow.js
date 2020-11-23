var MfArchiveBorrow = {
    //新增保存申请
    insertSave:function(formObj){
        var url = $(formObj).attr("action");
        var dataForm = JSON.stringify($(formObj).serializeArray());
        top.LoadingAnimate.start();
        $.ajax({
            url : url,
            data : {
                ajaxData : dataForm,
            },
            type : "post",
            dataType : "json",
            success : function(data) {
                top.flag=true;
                top.LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    myclose_click();
                } else {
                    window.top.alert(data.msg, 0);
                }
            },
            error : function(data) {
                top.loadingAnimate.stop();
                window.top.alert(data.msg, 0);
            }
        });
    },
    //回调选择的数据
    archivesinsert:function(){
    var url="/component/archives/MfArchiveInfoMain.jsp";
        top.appName="";
        top.archiveMainNo="";
    openCreatShowDialog(url,"选择合同",70,70,function(data){
        if(top.archiveMainNo==""){
            return
        }else {
            $("input[name='archiveName']").val(top.appName);
            $("input[name='archiveMainNo']").val(top.archiveMainNo);
        }
    });
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
                $("input[name='applyUserName']").val(top.applyUserName);
                $("input[name='applyUserNo']").val(top.applyUserNo);
                $("input[name='revertName']").val(top.applyUserName);
                $("input[name='revertNo']").val(top.applyUserNo);
                $("input[name='applyUserName']").attr("readOnly",true);
                $("input[name='revertName']").attr("readOnly",true);
            }
        });
    }
}
//获取选择数据
function choseCredit(parm){
    parm=parm.split("?")[1];
    var parmArray=parm.split("&");
    var appName = parmArray[0].split("=")[1];
    var archiveMainNo=parmArray[1].split("=")[1];
    top.appName = appName;
    top.archiveMainNo=archiveMainNo;
    myclose_showDialogClick();
};
//获取人员信息
function usertinfo(parm) {
    parm=parm.split("?")[1];
    var parmArray=parm.split("&");
    var applyUserName = parmArray[0].split("=")[1];
    var applyUserNo=parmArray[1].split("=")[1];
    top.applyUserName = applyUserName;
    top.applyUserNo=applyUserNo;
    myclose_showDialogClick();
};
function bacthCusTes(){
    var tranceNos = getCheckedNos();
    top.cwBackData = tranceNos;
    myclose_showDialogClick();
};
//登记页面关闭
function register(){
    window.location.href=webPath+"/mfArchiveBorrow/getListPage";
}
function insertregister(obj){
    var url = $(obj).attr("action");
    var flag=submitJsMethod($(obj).get(0), '');
    if(flag){
        window.top.alert("是否确认登记",2,function(){
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:'post',
                dataType:'json',
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        top.alert(data.msg, 1);
                        register();
                    }else{
                        if(data.flag=="error"){
                            top.alert(data.msg, 1);
                        }else {
                            alert(  top.getMessage("FAILED_SAVE"),0);
                        }
                    }
                }
            });
        });
    }
}
function insertrevert(obj){
    var url = $(obj).attr("action");
    var flag=submitJsMethod($(obj).get(0), '');
    if(flag){
        window.top.alert("是否确认归还",2,function(){
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data:{ajaxData:dataParam},
                type:'post',
                dataType:'json',
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        top.alert(data.msg, 1);
                        register();
                    }else{
                        if(data.flag=="error"){
                            top.alert(data.msg, 1);
                        }else {
                            alert(  top.getMessage("FAILED_SAVE"),0);
                        }
                    }
                }
            });
        });
    }
}
