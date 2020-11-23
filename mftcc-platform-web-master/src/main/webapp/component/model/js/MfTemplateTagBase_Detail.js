;
var MfTemplateTagBase_Detail=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
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
	var _updateTemplateTagBase=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url:webPath+"/mfTemplateTagBase/updateAjax",
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
	return{
		init:_init,
		updateTemplateTagBase:_updateTemplateTagBase
	};
}(window,jQuery);