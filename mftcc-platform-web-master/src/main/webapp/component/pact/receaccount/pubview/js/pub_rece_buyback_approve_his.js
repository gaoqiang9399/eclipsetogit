;
var pub_rece_buyback_approve_his = function(window, $) {
    var _receId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfPleRepoApply/getByReceIdAjax",
            data:{receId:pub_rece_buyback_approve_his.receId},
            success:function(data) {
                if (data.flag == "success") {
                    var repoAppId = data.repoAppId;
                    showWkfFlowVertical($("#wj-modeler-buyback"),repoAppId,"12","repo_approval");
                }else{
                    $("#receBuyback-spInfo-block").remove();
                }
            },error:function() {

            }
        });
	};
	return {
		init : _init,
        receId : _receId,
	};
}(window, jQuery);
