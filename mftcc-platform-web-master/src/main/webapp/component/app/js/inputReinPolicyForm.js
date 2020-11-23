;
var inputReinPolicyForm = function(window, $) {
	var _init = function () {	
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".container",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindClose();
		_bindInsertAjax("#MfBusNextUser");		
//		_initCus();
//		_initNextUser();
		_nextOpNameSelect();// 分单, 初始化选择下一岗位人员
	};

	/** 分单, 初始化选择下一岗位人员 */
	var _nextOpNameSelect = function() {
		$("input[name=nextOpNo]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			items : nextOpArray,// 数据
			multiple : false// false单选，true多选，默认多选
		});
	};

	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	
	
	var  _initCus= function(){
		//客户经理新版选择组件
		$('input[name=manageOpName3]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/sysUser/findByPageForSelectAjax",//请求数据URL
			handle:BASE.getIconInTd($("input[name=manageOpName3]")),//其他触发控件
			valueElem:"input[name=manageOpNo3]",//真实值选择器
			title: "选择协办人员",//标题
			tablehead:{//列表显示列配置
				"opName":"协办人员编号",
				"opNo":"协办人员名称"
			},
			returnData:{//返回值配置
				disName:"opName",//显示值
				value:"opNo"//真实值
			}
		});
	};
	
	var  _initNextUser= function(){
		//客户经理新版选择组件
		$('input[name=opName]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/sysUser/findByPageForSelectAjax",//请求数据URL
//			handle:BASE.getIconInTd($("input[name=opName]")),//其他触发控件
			valueElem:"input[name=opNo]",//真实值选择器
			title: "选择下一岗位操作员",//标题
			tablehead:{//列表显示列配置
				"opName":"操作员编号",
				"opNo":"操作员名称"
			},
			returnData:{//返回值配置
				disName:"opName",//显示值
				value:"opNo"//真实值
			}
		});
	};
	
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click",function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						appId : inputReinPolicyForm.appId
					},
					type : 'post',
					dataType : 'json',
					async:false,
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg,3);
							 top.flag = true;
							 top.reinsurance_policy= true;
							 top.appSts = data.appSts;
							 myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}				
		});
	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,	
	};
}(window, jQuery);