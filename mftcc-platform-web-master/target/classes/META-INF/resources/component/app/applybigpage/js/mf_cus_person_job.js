;
var mf_cus_person_job = function(window, $) {
	var _init = function() {
		// 循环初始化表单
		$("#forms_div").find("form[name='form_mf_cus_person_job']").each(function(index, element) {
			_formInit(element);
		});
	};

	// 初始化表单
	var _formInit = function(form) {
		// 与客户关系选择组件
		_addressSelectInit(form);
	};

	/** 阳光金控-单位地址选择 */
	var _addressSelectInit = function(form) {
		$(form).find("input[name=ext1]").popupSelection({
			items : areaData,
			searchOn : true,// 启用搜索
			multiple : false,// 单选
			valueClass : "show-text",// 自定义显示值样式
			ztree : true,
			title : "单位地址",
			handle : BASE.getIconInTd($(form).find("input[name=ext1]")),
			changeCallback : function(elem) {
				var areaNo = elem.data("values").val();
				var node = elem.data("treeNode");
				var parNode = node.getParentNode();
				var address = node.name;
				while (parNode) {
					address = parNode.name + address;
					parNode = parNode.getParentNode();
				}

				var $careaProviceObj = $(elem).parent(".input-group").find(".pops-label-alt");
				$careaProviceObj.removeClass("pops-label-alt");
				$careaProviceObj.html(address);
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

