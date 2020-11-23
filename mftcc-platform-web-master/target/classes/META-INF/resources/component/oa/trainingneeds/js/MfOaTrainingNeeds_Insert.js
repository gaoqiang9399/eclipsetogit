;
var MfOaTrainingNeeds_Insert = function(window, $) {
	var _init = function() {
		$("input[name=trainingNeedsId]").val(trainingNeedsId);
		top.trainingNeedsId=trainingNeedsId;
		// 单位选择组件
		$("input[name=trainingNeedsType]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			items : resultMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增培训类型', 'TRAINING_NEEDS_TYPE', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {ajaxData:dataParam},
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
	var _myclose = function(){
		 myclose_click();//关闭弹窗
		window.location.href = webPath+"/mfOaTrainingNeeds/getListPage";
	};
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		myclose:_myclose
	};
}(window, jQuery);
