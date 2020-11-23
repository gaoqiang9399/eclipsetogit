;
var MfOaMattersReported_Insert=function(window, $){
	var _init=function(){
		$("input[name=mattersReportedId]").val(mattersReportedId);
		top.mattersReportedId=mattersReportedId;
		
		// 事項添加选择组件
		$("input[name=itemCategory]").popupSelection({
			searchOn : false,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			items : resultMap,
			addBtn : {// 添加扩展按钮
				"title" : "新增",
				"fun" : function(hiddenInput, elem) {
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增事项类型', 'ITEM_CATEGORY', elem);
				}
			},
			changeCallback : function(obj, elem) {
			}
		});
	};
	_myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 window.location.href = webPath+"/mfOaHumanResources/getListPage";//重新刷新列表
		 };
	//新增
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam,mattersReportedId:mattersReportedId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						alert(data.msg,1);
						window.top.alert(data.msg, 3);
						myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		}
	};
	return{
		init:_init,
		ajaxSave:_ajaxSave,
		myclose:_myclose
	};
}(window, jQuery);