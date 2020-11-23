var MfFundPlan_Detail = function(window, $) {
	var _init = function(){
		//单位选择组件
		$("input[name=unit]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple : false,//单选
			items:resultMap,
			addBtn:{//添加扩展按钮
				"title":"新增",
				"fun":function(hiddenInput, elem){
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增单位','FUND_PLAN_UNIT', elem);
				}
			},
			changeCallback : function (obj, elem) { 
			}
		});
		if(opNo != regNo){
			$("font").remove();
			$("input[type='text']").attr("disabled","disabled");
			$("select").attr("disabled","disabled");
			$("textarea").attr("disabled","disabled");
			$(".pops-value").unbind("click");
			$(".pops-value").attr("style","min-height: 34px;cursor:default;");
		}
	};
	// 更新记录
	var _updateAjax = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						alert(data.msg,1);
						setTimeout("myclose_click()",1000);
					}
					if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					 LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	return {
		init:_init,
		updateAjax:_updateAjax
	};
}(window, jQuery);