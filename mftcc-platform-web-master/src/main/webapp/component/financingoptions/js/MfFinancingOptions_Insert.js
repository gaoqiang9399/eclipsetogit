;
var MfFinancingOptions_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				// 滚动条根据内容实时变化
				updateOnContentResize : true
			}
		});
		// 初始化首付比例
		$("input[name=downPaymentRatio]").popupSelection( {
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : downPaymentRatio
		});
		// 初始化融资期限
		$("input[name=financingTerm]").popupSelection( {
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : financingTerm
		});
		// 初始化产品
		$("input[name=applicableProducts]").popupSelection( {
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			items : kindNo
		});
		// 初始化标签
		$("input[name=label]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 多选
			items : labelMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增标签', 'FINAN_LABEL', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
		// 初始化产品优势
		$("input[name=kindAdvantage]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : kindAdvantageMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增产品优势', 'KIND_ADVANTAGE', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
		// 初始化准入条件
		$("input[name=entryCriteria]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : entryCriteriaMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增准入条件', 'ENTRY_CRITERIA', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
		// 初始化申请流程
		$("input[name=applicationProcess]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : applicationProcessMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增申请流程', 'APPLICATION_PROCESS', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});

        //允许修改区域选择组件
        $("input[name=editArea]").popupSelection({
            ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
            searchOn : true,// 启用搜索
            multiple : true,// 单选
            ztree : true,
            ztreeSetting : setting,
            title : "销售地区",
            handle : BASE.getIconInTd($("input[name=editArea]")),
            changeCallback : function(elem) {
                BASE.removePlaceholder($("input[name=editArea]"));
               /* var businessAreaNo = elem.data("values").val();
                var nodes = elem.data("treeNode");
                var salesAreaName = "";
                var len = elem.data("treeNode").length;
                for (var i = 0; i < len; i++) {
                    salesAreaName += nodes[i].name + "|";
                }
                $("input[name=salesAreaName]").val(salesAreaName);*/
            }
        });
	
	};
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
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
		ajaxSave : _ajaxSave
	};
}(window, jQuery);
