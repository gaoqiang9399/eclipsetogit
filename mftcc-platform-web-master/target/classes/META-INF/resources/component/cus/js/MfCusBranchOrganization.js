/** 保存 */
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			ajaxInsertCusForm(obj);
		}
	};