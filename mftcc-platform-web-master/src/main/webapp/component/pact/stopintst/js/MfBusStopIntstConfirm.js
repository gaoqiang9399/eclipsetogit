var MfBusStopIntstConfirm = {
    //停息保存
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
            }
        });
    }
}
