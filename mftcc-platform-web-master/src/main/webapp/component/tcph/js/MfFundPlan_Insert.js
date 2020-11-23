var MfFundPlan_Insert = function(window, $) {
	var _init = function() {
		// 单位选择组件
		$("input[name=unit]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			items : resultMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增单位', 'FUND_PLAN_UNIT', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
		$("input[name=mainNo]").popupSelection({//申请机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=mainName]"));
				$("input[name=mainName]").val($("input[name=mainNo]").parents("td").find(".pops-value").html());
			},
		});	
	};
	// 新增记录
	var _insertAjax = function(obj, isAdd) {
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
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						alert(data.msg, 1);
						if (isAdd == "0") {
							setTimeout("myclose_click()", 1000);
						} else {
							$("input[name='project']").val("");
							$("input[name='fundPlanAmt']").val("");
							$("select[name='fundPlanSts']").val("1");
							$("textarea[name='state']").val("");
						}
					}
					if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		}
	};
	//更新按钮
	var _updateAjax = function(obj){
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			url:webPath+"/mfFundPlan/updateAjax",
			data:{
				ajaxData : dataParam,
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					myclose_click();
				}else{
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};
	var _myclose = function(){
		myclose_click();
	};
	var _callback = function(date){
		var arr = date.split(",");
		$("input[name=date]").val(arr[1]);
	};
	return {
		init : _init,
		insertAjax : _insertAjax,
		updateAjax:_updateAjax,
		myclose : _myclose,
		callback:_callback
	};
}(window, jQuery);