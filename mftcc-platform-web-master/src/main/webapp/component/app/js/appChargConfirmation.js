
var ApplyChargConfirmation = function(window,$){
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
    };

    var _doSubmit = function (obj){
        alert("确定要进行提交下一步操作吗?",2,function(){
            var dataParam = JSON.stringify($(obj).serializeArray());
            var ajaxUrl = $(obj).attr("action");
            jQuery.ajax({
                url : ajaxUrl,
                data : {ajaxData : dataParam},
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                    LoadingAnimate.start();
                },success : function(data){
                    if(data.flag == "success"){
                        top.flag=true;
                        top.pactUpdateFlag=true;//表示是否是合同签约节点
                        top.pactSts = data.pactSts;
                        top.pactDetailInfo = data.pactDetailInfo;
                        //window.location.reload();//刷新页面
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        window.top.alert(data.msg,0);
                    }
                },
                error : function(data) {
                    if(data.flag == "error"){
                        window.top.alert("操作失败！",0);
                    }
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };
    return{
        init:_init,
        doSubmit:_doSubmit,
    };

}(window,jQuery);