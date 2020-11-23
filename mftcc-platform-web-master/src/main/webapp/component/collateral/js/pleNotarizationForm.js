
var pleNotarization = function(window, $) {
	
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		var groupNameLabel = $("input[name=groupName]").parents(".rows")
				.find(".form-label");
		var groupNameLabelText = $(groupNameLabel).text();
		$(groupNameLabel).empty().append(
				"<span class='required'>*</span>" + groupNameLabelText);
		$("input[name=groupName]").attr("mustinput", "1");		
	};
	
	var _getLegalIdType = function(){
		var legalIdType = $("select[name =legalIdType]").val();
		if (legalIdType == "0") {
			$("input[name=legalIdNum]").attr("alt", "idnum");
		} else {
			$("input[name=legalIdNum]").attr("alt", "tmp");
		}
		$("input[name=legalIdNum]").val("");
	};
	function ifGroupCustomer(obj) {
		var ifGroupType = $(obj).val();
		if (ifGroupType == 0) {//非集团客户
			$("input[name=groupName]").attr("mustinput", "0");
			$("input[name=groupName]").parents(".rows").hide();
		} else {//集团客户
			$("input[name=groupName]").attr("mustinput", "1");
			$("input[name=groupName]").parents(".rows").show();
		}
	}

	
	var _getCusMngNameDialog = function (){
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};
	
	//从集团客户放大镜赋值给表单属性
	var _getGroInfoArtDialog = function(groupInfo) {
		$("input[name=groupName]").val(groupInfo.groupName);
		$("input[name=groupNo]").val(groupInfo.groupNo);
		$("input[name='groupName']").attr("readonly", true);
	};
	
	var _selectAreaCallBack = function(areaInfo) {
		$("input[name=careaProvice]").val(areaInfo.disName);
	};
	var _insertFairInfo = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg,3);
						top.flag = true;
						var url = webPath+'/mfBusPact/getById?pactId='+pactId;
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	return {
		init : _init,
		getLegalIdType:_getLegalIdType,
		insertFairInfo :_insertFairInfo,
		getCusMngNameDialog :_getCusMngNameDialog,
		getGroInfoArtDialog:_getGroInfoArtDialog,
		selectAreaCallBack:_selectAreaCallBack,
	};
}(window, jQuery);