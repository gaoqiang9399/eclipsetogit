;
var mf_cus_bank_acc_manage = function(window, $) {
	var _init = function() {
		// 循环初始化表单
		$("#forms_div").find("form[name='form_mf_cus_bank_acc_manage']").each(function(index, element) {
			_formInit(element);
		});
	};

	// 初始化表单
	var _formInit = function(form) {
		// 银行区域选择组件
		_initBankArea(form);

	};

	// 银行区域选择组件
	var _initBankArea = function(form) {
		$(form).find("input[name=ext1]").popupSelection({
			ajaxUrl : "NmdAreaActionAjax_getAllExceptDirectCityAjax.action",
			searchOn : true,// 启用搜索
			multiple : false,// 单选
			valueClass : "show-text",// 自定义显示值样式
			ztree : true,
			// ztreeSetting : setting,
			title : "银行城市",
			handle : BASE.getIconInTd($(form).find("input[name=ext1]")),
			changeCallback : function(elem) {
				var node = elem.data("treeNode");
				var id = node.id;
				var name = node.name;
				$(form).find("input[name=bankArea]").val(name);
				$(form).find("input[name=ext1]").val(id);
				var $careaProviceObj = $(form).find("input[name=careaProvice]").parent(".input-group").find(".pops-label-alt");
				$careaProviceObj.removeClass("pops-label-alt");
				$careaProviceObj.html(name);
			}
		});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		formInit : _formInit
	};
}(window, jQuery);

// ---------- 兼容已有基础表单，下面未做js封包 ----------

