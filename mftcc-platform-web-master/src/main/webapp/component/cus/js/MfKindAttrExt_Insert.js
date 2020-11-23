;
var MfKindAttrExt_Insert = function(window, $) {
    //产品名称赋值
	var _selectKind = function(obj){
		var kindName = $(obj).find("option:selected").text();
		$(obj).parents("form").find("input[name='kindName']").val(kindName);
	};

    //新增方法
    var _insertAjax = function(dom){
        var flag = submitJsMethod($(dom).get(0), '');
        if(flag){
            var url = $(dom).attr("action");
            var dataParam = JSON.stringify($(dom).serializeArray());
            LoadingAnimate.start();
            $.ajax({
                url:url,
                data : {
                    ajaxData : dataParam
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.kindExtFormAddFlag = true;
                        window.top.alert(data.msg, 1);
                        _closeThisPage();
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("ERROR_REQUEST_URL", url));
                }
            });
        };
    }

    var _closeThisPage = function (){
        $(top.window.document).find("#showDialog .close").click();
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
        selectKind:_selectKind,
        insertAjax:_insertAjax,
        closeThisPage:_closeThisPage
	};
	
}(window, jQuery);

