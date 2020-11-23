;
var mf_cus_pers_base_info = function(window, $) {
	var _init = function() {
		// 循环初始化表单
		_formInit($("#forms_div").find("form[name='form_mf_cus_pers_base_info']"));
	};

	// 初始化表单
	var _formInit = function(form) {
		StringUtil.setBirthyAndSexByID($(form).find("[name='idNum']"), 'sex', 'brithday', 'age');
		$(form).find("[name='idNum']").bind("change", function() {// 证件号
			StringUtil.setBirthyAndSexByID($(this), 'sex', 'brithday', 'age');
		});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		formInit : _formInit,
	};
}(window, jQuery);
