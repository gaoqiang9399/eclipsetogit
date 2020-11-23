;
var pub_rece_finc_main_detail_info = function(window, $) {
    var _fincMainId = '';
    var _formId = '';
    var _init = function () {
        $.ajax({
            url:webPath+"/mfBusFincAppMain/getReceFincMainDetailFormAjax",
            data:{
                fincMainId:pub_rece_finc_main_detail_info.fincMainId,
                formId:pub_rece_finc_main_detail_info.formId
            },
            success:function(data) {
                if (data.flag == "success") {
                    var html = data.htmlStr;
                    $("#fincAppMainDetail").empty().html(html);
                }else{
                }
            },error:function() {

            }
        });
	};
	return {
		init : _init,
	};
}(window, jQuery);
