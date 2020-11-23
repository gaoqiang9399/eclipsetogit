;
var MfVouAfterTrack_InitBusInfo = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
	};
	var _selectCusInfo = function(cusInfo){
		$("[name='cusName']").val(cusInfo.cusName);
		$("[name='cusNo']").val(cusInfo.cusNo);
        $.ajax({
            url : webPath + "/mfBusPact/getVouAfterTrackByCusNoAjax",
            data : { cusNo : cusInfo.cusNo },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                	let popspactId = $("input[name=popspactId]");
                	if(popspactId.length > 0){
                        $("[name=popspactId]").popupSelection("updateItems",data.pactInfoMap);
					}else{
                        // 合同选择组件
                        $("input[name=pactId]").popupSelection({
                            searchOn : true,// 启用搜索
                            inline : true,// 下拉模式
                            multiple : false,// 单选选
                            items : data.pactInfoMap,
                            changeCallback : function(obj, elem) {
                                var pactId = $(obj).val();
                                window.location.href = webPath+"/mfVouAfterTrack/input?pactId=" + pactId;
                            }
                        });
                    }
                }
            },
            error : function() {
            }
        });
	};
	return {
		init : _init,
        selectCusInfo:_selectCusInfo,
	};
}(window, jQuery);
