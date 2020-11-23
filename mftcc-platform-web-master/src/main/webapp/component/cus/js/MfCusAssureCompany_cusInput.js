;
var MfCusAssureCompany_cusInput = function(window, $) {
	/** 初始化 */
	var _init = function() {
		var ajaxData = JSON.parse($('#ajaxData').val());

		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});

		_dealWithCusType("9");// 只加载担保公司

		// 关闭按钮
		$(".cancel").bind("click", function(event) {
			$(top.window.document).find("#showDialog").remove();
		});

		// 客户类别
		$('[name=cusType]').bind('change', function() {
			document.location.href = webPath+"/mfCusAssureCompany/cusInput?cusType=" + $(this).val();
		});
		$("input[name='idType']").popupSelection({
			searchOn:false,//不启用搜索
			inline:true,//下拉模式
			multiple:false,//多选
			items:idTypeMap
		});

	};

	/** 保存 */
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());

			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
					LoadingAnimate.start();
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.addFlag = true;
						window.top.alert(data.msg, 1);
						$(top.window.document).find("#showDialog .close").click();// 点击弹出框的“X”，触发list页面的回调函数,刷新List页面
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};

	/** 排除非担保公司选项 */
	//处理客户类型问题,以后逗号分隔，可以传入多个客户类型
	var _dealWithCusType = function(baseTypes) {
		$.ajax({
			url : webPath+'/mfBusTrench/getCusTypeNotShowAjax',
			data : 'baseTypes=' + baseTypes,
			dataType : 'json',
			async : false,
			type : 'POST',
			success : function(data) {
				var notShowCusTypes = data.cusTypeList;
				if (notShowCusTypes != null) {
					for ( var i = 0; i < notShowCusTypes.length; i++) {
						var typeNo = notShowCusTypes[i].typeNo;
						$("[name=cusType]").find("option[value=" + typeNo + "]").remove();
					}
				}
			}
		});
	};
//担保公司证件号拦截重复
	var _getAssureInfoByIdNum=function(){
		var idNum=$("[name=idNum]").val();
		if(idNum==""){
			return;
		}
		$.ajax({
			url:webPath+'/mfCusAssureCompany/getAssureInfoByIdNumAjax',
			data:{idNum:idNum},
			dataType:'json',
			async:false,
			type:'POST',
			success:function(data){
				if(data.flag="success"){
					if(data.existFlag=="1"){
						$("input[name=idNum]").val("");
						var assureInfo=data.assureInfo;
						var msg=assureInfo.opName+"登记的"+assureInfo.assureCompanyName+"的证件号码"
						alert(top.getMessage("CONFIRM_UPDATE_CUSTOMER",msg),2,function(){
							
						});
					}
				}else if(data.flag="error"){
					window.top.alert(data.msg,0);
				}
			}
		});
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave : _ajaxSave,
		getAssureInfoByIdNum:_getAssureInfoByIdNum
	};
}(window, jQuery);
