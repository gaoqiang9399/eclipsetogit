;
var MfBusReceBaseInfo_InputList = function(window, $) {
	var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
    };
	//应收账款采集
	var _insertReceBaseInfo = function(){
        top.addReceInfoFlag=false;
        top.window.openBigForm(webPath+"/mfBusReceBaseInfo/input?appId="+appId+"&cusNo="+cusNo,'账款登记',function() {
            if(top.addReceInfoFlag){
                $.ajax({
                    url:webPath+"/mfBusReceBaseInfo/getReceListAjax",
                    data:{appId:appId,tableId:"tablereceInfoCollectList"},
                    success:function(data) {
                        if (data.flag == "success") {
                            $("#receAccountList").html(data.htmlStr);
                        }else{
                        }
                    },error:function() {

                    }
                });
            }
        });
    };

    //删除应收账款信息
    var _deleteReceInfoAjax = function(obj,url){
        $.ajax({
            url: webPath+url,
            success: function (data) {
                if(data.flag=="success"){
                    $.ajax({
                        url:webPath+"/mfBusReceBaseInfo/getReceListAjax",
                        data:{appId:appId,tableId:"tablereceInfoCollectList"},
                        success:function(data) {
                            if (data.flag == "success") {
                                $("#receAccountList").html(data.htmlStr);
                            }else{
                            }
                        },error:function() {

                        }
                    });
                }else{
                    alert(data.msg,0);
                }
            },error:function() {
                alert(top.getMessage("FAILED_DELETE"),0);
            }
        });

    };
	var _doSubmit = function(){
        DIALOG.confirm(top.getMessage("CONFIRM_OPERATION","业务流程的下一步"),function(){
            $.ajax({
                url:webPath+"/mfBusApply/commitBusProcessAjax?appId="+appId,
                success:function(data){
                    if(data.flag=="success"){
                        top.collaFlag=true;
                        myclose_click();
                    }else{
                        alert(data.msg,0);
                    }
                }
            });
        });
    };
	var _doSkip = function(){
        DIALOG.confirm(top.getMessage("CONFIRM_OPERATION","跳过此节点"),function(){
            $.ajax({
                url:webPath+"/mfBusApply/commitBusProcessAjax?appId="+appId,
                success:function(data){
                    if(data.flag=="success"){
                        top.collaFlag=true;
                        top.skipFlag=true;
                        myclose_click();
                    }else{
                        alert(data.msg,0);
                    }
                }
            });
        });
    };
	return {
		init : _init,
        insertReceBaseInfo : _insertReceBaseInfo,
        deleteReceInfoAjax : _deleteReceInfoAjax,
        doSubmit : _doSubmit,
        doSkip : _doSkip,
	};
}(window, jQuery);
