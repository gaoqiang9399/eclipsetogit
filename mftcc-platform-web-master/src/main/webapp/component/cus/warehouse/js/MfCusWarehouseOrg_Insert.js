;
var MfCusWarehouseOrg_Insert = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});

        // 初始化客户类型
        $("input[name=cusType]").popupSelection( {
            searchOn : true,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 单选
            items : cusTypeArray ,
            changeCallback : function(elem) {
                var typeNo = elem.data("values").val();
                window.location.href = "/mfCusWarehouseOrg/input?typeNo=" + typeNo;
            }
        });
	};

    //新增仓储机构
    var _ajaxInsert = function(dom){//新增方法
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
                        myclose_click();
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("ERROR_REQUEST_URL", url));
                }
            });
        }
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        ajaxInsert:_ajaxInsert,
	};
}(window, jQuery);