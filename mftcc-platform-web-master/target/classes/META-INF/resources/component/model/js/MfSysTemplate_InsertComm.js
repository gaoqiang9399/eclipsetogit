;
var MfSysTemplate_InsertComm = function(window,$){
	var _init = function(){
		$('input[name=templateSource]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			searchOn:true,
			items:ajaxData.tempJsonArray,
			changeCallback : function (obj, elem) {
				_templateSourceBack($("input[name=templateSource]").val(),obj.data("text"));
			}
		});
	};
	var _templateSourceBack=function(templateSource,templateName){
		var templateNameZh=$("input[name=templateNameZh]").val();
        $.ajax({
			url:webPath+"/mfSysTemplate/getSysTemplateHtmlAjax",
			data:{templateNo:templateSource},
			type:'post',
			dataType:'json',
			success:function(data){
                if(data.flag == "success"){
                    $("#operform").html(data.htmlStr);
                    if(data.templateSuffix == '4'){
                        $(".opt_divNew").css("display","block");
                        if(data.templateSource == '00000'){
                            $(".anchor2-anchor").show();
                            $(".anchor2-anchor input[name='upload']").removeAttr("disabled");
                        }else{
                            $(".anchor2-anchor").hide();
						}
					}else{
                        $(".opt_divNew").css("display","none");
                        $(".anchor2-anchor").hide();
					}
                    $("[name='templateNo']").val(templateNo);
                    $('input[name=templateSource]').popupSelection({
						inline: true, //下拉模式
						multiple: false, //单选
						searchOn:true,
						items:ajaxData.tempJsonArray,
						changeCallback : function (obj, elem) {
							_templateSourceBack($("input[name=templateSource]").val(),obj.data("text"));
						}
					});
					//$("input[name=templateSource]").parents("td").find(".pops-value").html(templateName);
					$("input[name=templateNameZh]").val(templateNameZh);
					
				}else{
				 window.top.alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", "${webPath}/mfSysTemplate/insertAjax"));
			}
		}); 
		
	};
	return{ 
		init:_init,
	};
	 
}(window,jQuery);