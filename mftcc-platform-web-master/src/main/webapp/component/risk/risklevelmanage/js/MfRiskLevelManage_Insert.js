;
var MfRiskLevelManage_Insert = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		$("select[name='idType']").attr("disabled","true");
		$("select[name='cusType']").attr("disabled","true");
		$("input[name='cusName']").click(function(){
			selectCusDialog(function(mfCusCustomer){
				$("input[name='cusNo']").val(mfCusCustomer.cusNo);
				$("input[name='cusName']").val(mfCusCustomer.cusName);
				$("select[name='idType']").val(mfCusCustomer.idType);
				$("input[name='idNum']").val(mfCusCustomer.idNum);
				$("select[name='cusType']").val(mfCusCustomer.cusType);
			},"","选择客户","1");
		});
		// 处置方案选择组件
		$("input[name=manageType]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : managePlan
		});
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
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

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave
	};
}(window, jQuery);
