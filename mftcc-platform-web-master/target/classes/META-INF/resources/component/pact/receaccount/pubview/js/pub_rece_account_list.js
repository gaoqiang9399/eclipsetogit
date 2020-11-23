;
var pub_rece_account_list = function(window, $) {
    var _appId = '';
    var _cusNo = '';
    var _tableId = '';
    var _query = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusReceBaseInfo/getReceListAjax",
            data:{appId:pub_rece_account_list.appId,tableId:pub_rece_account_list.tableId},
            success:function(data) {
                if (data.flag == "success") {
                    $("#rece_account_list").show();
                    $("#receAccountList").html(data.htmlStr);
                    if(pub_rece_account_list.query=="query"){
                        $("#rece_account_list .delBtn").html("删除");
                    }
                }else{
                }
            },error:function() {

            }
        });
	};
    //新增应收账款
    var _insertReceBaseInfo = function(){
        top.addReceInfoFlag=false;
        top.window.openBigForm(webPath+"/mfBusReceBaseInfo/input?appId="+pub_rece_account_list.appId+"&cusNo="+pub_rece_account_list.cusNo,'账款登记',function() {
            if(top.addReceInfoFlag){
                _init();
                pub_rece_account_head_info.appId=pub_rece_account_list.appId;
                pub_rece_account_head_info.init();
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
                    pub_rece_account_head_info.init();
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
        insertReceBaseInfo : _insertReceBaseInfo,
        deleteReceInfoAjax : _deleteReceInfoAjax,
        cusNo:_cusNo,
        appId:_appId,
        tableId:_tableId,
        query:_query,
	};
}(window, jQuery);
