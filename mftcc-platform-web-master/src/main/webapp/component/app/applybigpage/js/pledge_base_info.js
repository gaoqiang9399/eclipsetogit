;
var pledge_base_info = function(window, $) {
	var collClass;// 押品类别数据

	var _init = function() {
		_collClassInit();// 加载押品类别数据

		// 循环初始化表单
		$("#forms_div").find("form[name='form_pledge_base_info']").each(function(index, element) {
			_formInit(element);
		});
	};

	// 初始化表单
	var _formInit = function(form) {
		var pledgeSeq = $("#forms_div").find("form[name='form_mf_bus_apply']").find("[name='pledgeSeq']").val();// 申请抵押顺位
		$(form).find('select[name=extOstr01]').val(pledgeSeq);// 押品抵押顺位默认申请的

		// 业务属性
		$(form).find('select[name=classModel]').popupSelection({
			searchOn : true, // 启用搜索
			inline : true, // 下拉模式
			multiple : false
		// 单选
		});

		// 担保类型，修改都需要变更表单
		$(form).find("[name='pledgeMethod']").each(function(index, element) {
			$(element).bind("change", function() {
				_changeForm(element);
			});
		});

		// 押品类别选择组件，押品类别修改需要变更表单
		$(form).find("input[name=classId]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 多选选
			items : collClass,
			changeCallback : function(obj, elem) {
				_changeForm(obj);
			}
		});

		// 楼盘
		$(form).find('[name=extOstr18]').bind("change", function() {
			$(form).find('[name=extOstr08]').val("");// 楼盘编号
			$(form).find('[name=extOstr19]').val("");// 楼栋
			$(form).find('[name=extOstr09]').val("");// 楼栋编号
			$(form).find('[name=extOstr20]').val("");// 房号
			$(form).find('[name=extOstr10]').val("");// 房号编号
		});

		// 共同借款人
		_certificateNameSelectInit(form);
	};

	/** 所有权人选择加载 */
	var _certificateNameSelectInit = function(form) {
		var data = getCertificateNameObj();// 获取页面共同借款人数据

		// 创建select
		var $select = $('<select multiple="multiple">');
		for ( var idx in data) {
			var $option = $('<option value="' + data[idx].id + '">' + data[idx].name + '</option>');
			$select.append($option);
		}
		$(form).find("[name='certificateName']").after($select);
		$(form).find("[name='certificateName']").hide();

		var placeholder = $(form).find("[name='certificateName']").val().replace(/\|/g, ', ');
		if (!placeholder) {
			placeholder = '请选择';
		}
		// 创建select控件
		$select.fSelect({
			placeholder : placeholder,
			numDisplayed : 5,// 标签值最多显示个数
			showSearch : false,
			changeCallback : function(vals) {// 回调
				var id = [];
				var name = [];
				for ( var idx in vals) {
					id.push(vals[idx].id);
					name.push(vals[idx].name);
				}

				$(form).find("[name='extOstr21']").val(id.join('|'));
				$(form).find("[name='certificateName']").val(name.join('|'));
			}
		});

		// 动态更新select控件选择项
		$(form).find("[name='certificateName']").parent().find(".fs-label-wrap").bind("click", function() {
			var $label = $(this);
			var $wrap = $label.parent(".fs-wrap");
			var $select = $wrap.find("select");
			var $fSelect = $select.data('fSelect');

			var data = getCertificateNameObj();// 获取页面共同借款人数据
			$select.empty();
			for ( var idx in data) {
				var $option = $('<option value="' + data[idx].id + '">' + data[idx].name + '</option>');
				$select.append($option);
			}

			var choices = $fSelect.buildOptions($select);
			$wrap.find('.fs-options').empty();
			$wrap.find('.fs-options').html(choices);
		});
	};

	// 所有权人数据源
	var getCertificateNameObj = function() {
		var data = applyBigPage.getCusRelationData();// 获取页面共同借款人数据

		// 申请人数据
		var idNum = $("#forms_div").find("form[name='form_mf_cus_pers_base_info']").find("[name='idNum']").val();// 证件号
		var cusName = $("#forms_div").find("form[name='form_mf_bus_apply']").find("[name='cusName']").val();// 姓名
		var cusObj = {"id" : idNum, "name" : cusName};
		data.push(cusObj);

		return data;
	};

	// 加载押品类别数据
	var _collClassInit = function() {
		var kindNo = $("form[name='form_mf_bus_apply']").find("[name='kindNo']").val();
		jQuery.ajax({
			url : "ApplyBigPageActionAjax_getCollClassAjax.action",
			data : {
				kindNo : kindNo
			},
			type : "POST",
			dataType : "json",
			async : false,
			success : function(data) {
				collClass = data.collClass;
			},
			error : function(data) {
				alert("押品类别加载失败", 0);
			}
		});
	};

	// 变更表单
	var _changeForm = function(obj) {
		var cusNo = $(obj).parents("form").find("[name='cusNo']").val();
		var cusName = $(obj).parents("form").find("[name='cusName']").val();
		var pledgeMethod = $(obj).parents("form").find("[name='pledgeMethod']").val();
		var classId = '';
		if ($(obj).attr("name") == 'popsclassId') {
			classId = $(obj).val();
		}
		var dataParam = '{"cusNo":"' + cusNo + '", "cusName":"' + cusName + '", "pledgeMethod":"' + pledgeMethod + '", "classId":"' + classId + '"}';
		jQuery.ajax({
			url : "ApplyBigPageActionAjax_pledgeChangeFormAjax.action",
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				var $form = $(obj).parents("form");
				$form.find("#formDiv").empty();// 清空现有元素
				$form.find("#formDiv").append(data.formHtml);// 替换表单内容
				_formInit($form);// 将此表单做一次初始化
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		formInit : _formInit,
		collClassInit : _collClassInit
	};
}(window, jQuery);
