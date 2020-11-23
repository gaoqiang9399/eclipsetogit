var MfCusApplyPersonSurvey=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		//工程区域
		$("input[name=engineeringArea]").popupSelection({
				ajaxUrl : webPath+"/nmdArea/getAreaListAllAjax",
				searchOn : true,//启用搜索
				multiple : false,//单选
				valueClass : "show-text",//自定义显示值样式
				ztree : true,
				ztreeSetting : setting,
				title : "工程区域",
				handle : BASE.getIconInTd($("input[name=engineeringArea]")),
				changeCallback : function (elem) {
					var node = elem.data("treeNode");
					var parNode =  node.getParentNode();
					var address=node.name;
					while(parNode) {
						address=parNode.name+address;
						parNode=parNode.getParentNode();
					}
					$("input[name=engineeringArea]").val(address);
					var $careaProviceObj = $("input[name=engineeringArea]").parent(".input-group").find(".pops-label-alt");
					$careaProviceObj.removeClass("pops-label-alt");
					$careaProviceObj.html(address);
			}
		});
	};
	
	//新增保存
	var  _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0),'');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	
	return{
		init:_init,
		ajaxSave:_ajaxSave
	};
}(window, jQuery);
