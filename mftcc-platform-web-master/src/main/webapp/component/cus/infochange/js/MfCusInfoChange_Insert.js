;
var MfCusInfoChange_Insert = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		$('input[name=afterTheChangeShow]').css('color','red');
		if(flag == "0"){
			$('textarea[name=changeReason]').attr("disabled","disabled");
			$('textarea[name=changeReason]').parent().parent().parent().find("font").hide();
		}

        fieldUpdateList()
	};
	
	
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
                        top.updateflag = true;
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
    //展示客户字段修改列表信息
    function fieldUpdateList(){
        $.ajax({
            url:webPath+"/mfCusInfoChange/fieldUpdateList?changeId="+changeId+"&beforeValueShow="+beforeValueShow+"&aftervalueShow="+aftervalueShow+"&changeFieldName="+changeFieldName+"&fieldType="+fieldType,
            success:function(data){
                var html = data.htmlStr;
                $("#tablechangeInfo0001").empty().html(html);
            }
        });
    }


	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave
	};
}(window, jQuery);
