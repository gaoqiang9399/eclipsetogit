;
var MfRiskLevelAdjust_Insert = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		// 处置方案选择组件
		$("input[name=manageType]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : true,// 多选
			items : managePlan
		});

        $("input[name='operateType']").popupSelection({
            searchOn:false,//不启用搜索
            inline:true,//下拉模式
            multiple:false,//单选
            items:adjustType
        });
		if(manageSts == "1" || manageSts == "2" || manageSts == "3" ){
			
		}else{
			$("input[name=manageType]").parent().find(".pops-close").remove();
		}
		if(comeFrom == "1" && mfBusFincAppListSize > 0){
			$("#tablist").show();
		}
	};
	
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam,
					manageSts:manageSts
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
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
	
	var _getCusById = function(url){
		top.window.openBigForm(webPath + url,'客户详情',function(){
		});
	}
	var _getPactById = function(url){
		top.window.openBigForm(webPath + url,'项目详情',function(){
		});
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		getCusById:_getCusById,
		getPactById:_getPactById
	};
}(window, jQuery);
