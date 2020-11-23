;
var MfRiskLevelManage_Detail = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		$(".ls_list").show();
	};
	
	// 获取详情信息
	var _getById = function(url) {
		top.window.openBigForm(webPath + url + "&entryFlag=1",'风险处置详情',function(){
			$.ajax({
				url : webPath + "/mfRiskLevelManage/getByRiskId",
				data :{riskId:riskId},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					$("#manageContent").html(data.tableHtml);
					$(".ls_list").show();
				},
				error : function() {
				}
			});
		});
	}

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById:_getById
	};
}(window, jQuery);
