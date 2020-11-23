;
var MfMakingMeetingSummary_Insert = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });

        // 初始化上会时间
        $("input[name=meetingTime]") .popupSelection({
            searchOn : true,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 单选选
            items : meetingTimeMap,
            changeCallback : function( obj, elem) {
                var meetingTime = $(obj).val();
                $.ajax({
                    url : webPath + "/mfBusApply/getMeetingTimeBusAjax",
                    data : { meetingTime : meetingTime },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        if (data.flag == "success") {
							$(".table_content").html(data.tableHtml);
                        }
                    },
                    error : function() {
                    }
                });
            }
        });
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
            var datas = [];
            $(".table_content").find("tbody tr")
                .each(
                    function(index) {
                        var entity = {};
                        $thisTr = $(this);
                        entity.cusNo = $thisTr.find("input[name=cusNo]").val();
                        entity.cusName = $thisTr.find("input[name=cusName]").val();
                        entity.appId = $thisTr.find("input[name=appId]").val();
                        entity.appName = $thisTr.find("input[name=appName]").val();
                        entity.approveRemark = $thisTr.find("input[name=approveRemark]").val();
                        datas.push(entity);
                    });
            var dataParam = JSON.stringify($(obj).serializeArray());
            var param =  {ajaxData : dataParam,ajaxDataList : JSON.stringify(datas)};

			$.ajax({
				url : url,
				data : param,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	return {
		init : _init,
		ajaxSave:_ajaxSave,
	};
}(window, jQuery);
