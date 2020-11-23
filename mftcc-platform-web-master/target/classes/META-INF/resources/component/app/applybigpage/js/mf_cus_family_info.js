;
var mf_cus_family_info = function(window, $) {
	var _init = function() {
		// 循环初始化表单
		$("#forms_div").find("form[name='form_mf_cus_family_info']").each(function(index, element) {
			_formInit(element);
		});
	};

	// 初始化表单
	var _formInit = function(form) {
		_initRelative(form);// 与客户关系选择组件

		_addressSelectInit(form);// 阳光金控-联系地址选择
	};

	/** 阳光金控-联系地址选择 */
	var _addressSelectInit = function(form) {
		$(form).find("input[name=ext2]").popupSelection({
			items : areaData,
			searchOn : true,// 启用搜索
			multiple : false,// 单选
			valueClass : "show-text",// 自定义显示值样式
			ztree : true,
			title : "联系地址",
			handle : BASE.getIconInTd($(form).find("input[name=ext2]")),
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

	// 与客户关系选择组件
	var _initRelative = function(form) {
		// 与客户关系选择组件
		$(form).find("select[name=relative]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			addBtn : {
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增与客户关系', 'CUS_PERS_REL', elem);
				}
			},
			changeCallback : function(elem, obj) {
				// 如果是配偶关系自动带出性别。
				var relative = $(form).find("input[name='relative']").val();
				if (relative == "1") {
					$(form).find("select[name=popssex]").popupSelection("selectedById", ajaxData.baseSex);
				}

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

