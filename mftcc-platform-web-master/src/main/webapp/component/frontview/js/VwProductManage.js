var VwProductManage = {
    //新增保存申请
    insertSave: function (formObj) {
        var url = $(formObj).attr("action");
        var dataForm = JSON.stringify($(formObj).serializeArray());
        top.LoadingAnimate.start();
        $.ajax({
            url: url,
            data: {
                ajaxData: dataForm,
            },
            type: "post",
            dataType: "json",
            success: function (data) {
                top.flag = true;
                top.LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    myclose_click()
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
    },
    getsyskindtable:function () {
        var url = "/component/frontview/VmGetStsKind_List.jsp";
        top.kindName = "";
        top.kindNo = "";
        openCreatShowDialog(url, "选择产品种类", 70, 70, function (data) {
            if (top.kindNo == "") {
                return false;
            } else {
                $("input[name='productName']").val(top.kindName);
                $("input[name='kindNo']").val(top.kindNo);
            }
        });
    },
    getsyskindinfo:function(parm) {
        parm = parm.split("?")[1];
        var parmArray = parm.split("&");
        var kindNo = parmArray[0].split("=")[1];
        var kindName = parmArray[1].split("=")[1];
        top.kindNo=kindNo;
        top.kindName =kindName;
        myclose_showDialogClick();
    }
}