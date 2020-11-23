;
var pub_accnt_repay_detail_list = function(window, $) {
    var _appId = '';
    var _tableId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusReceBaseInfo/getAccntRepayDetailListAjax",
            data:{receId:pub_accnt_repay_detail_list.receId,tableId:pub_accnt_repay_detail_list.tableId},
            success:function(data) {
                if (data.flag == "success") {
                    $("#rece_account_list").show();
                    $("#receAccountList").html(data.htmlStr);
                    $("#tablist").show();
                }else{
                }
            },error:function() {

            }
        });
	};
    //新增应收账款
    var _insertReceBaseInfo = function(){
        top.window.openBigForm(webPath+"/mfBusReceBaseInfo/input?appId="+appId,'账款登记',function() {
            if(top.addFlag){
                _init();
            }
        });
    };

    //删除应收账款信息
    var _deleteReceInfoAjax = function(obj,url){
        $.ajax({
            url: webPath+url,
            success: function (data) {
                if(data.flag=="success"){
                    _init();
                }else{
                    alert(data.msg,0);
                }
            },error:function() {
                alert(top.getMessage("FAILED_DELETE"),0);
            }
        });

    };
	return {
		init : _init,
        deleteReceInfoAjax : _deleteReceInfoAjax,
	};
}(window, jQuery);
