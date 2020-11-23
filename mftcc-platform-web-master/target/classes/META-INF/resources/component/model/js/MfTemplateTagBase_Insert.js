;
var MfTemplateTagBase_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		// _changeGroupFlag();
		$('[name=paramType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: true, //单选
			items:ajaxData.paramType
		});
		/* 标签分组 */
        $('[name=groupFlag]').popupSelection({
            searchOn: true, //启用搜索
            inline: true, //下拉模式
            multiple: false, //单选
            items:bookData.bookmarkClass
        });

	};
	//保存标签信息
	var _insertTemplateTagBase=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url:webPath+"/mfTemplateTagBase/insertAjax",
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						myclose_click();
						_init();
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", webPath+"/mfSysTemplate/insertAjax"));
				}
			}); 
		}
	};
	var _changeGroupFlag=function(){
		var groupFlag=$("[name=groupFlag]").val();
		$.ajax({
			url:webPath+"/mfTemplateTagBase/getMaxSortAjax",
			data:{groupFlag:groupFlag},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name=sort]").val(data.maxSort);
				}else{
					window.top.alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", webPath+"/mfSysTemplate/insertAjax"));
			}
		}); 
	};
	return{
		init:_init,
		insertTemplateTagBase:_insertTemplateTagBase,
		changeGroupFlag:_changeGroupFlag
	};
}(window,jQuery);