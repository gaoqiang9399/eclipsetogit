;
var MfSysTemplateEsignerType_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});

	};
	//保存标签信息
	var _ajaxSave=function(obj){
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
                        top.esignerTypeTableHtml = data.esignerTypeTableHtml;
                        top.esignerTypeFlag = true;
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
	var _deleteAjax=function(obj,url){
        $.ajax({
            url : url,
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    $("#MfSysTemplateEsignerTypeController").html(data.esignerTypeTableHtml);
                    alert(data.msg,3);
                } else {
                    alert(data.msg, 0);
                }
            },
            error : function() {
            }
        });
	};
	var _edit=function(obj,url){
        top.esignerTypeFlag = false;
        top.openBigForm(webPath + url,"编辑电签人员类型", function(){
            if(top.esignerTypeFlag){
                $("#MfSysTemplateEsignerTypeController").html(top.esignerTypeTableHtml);
            }
        });
	};
    //新增电签人员类型
    var _addMfSysTemplateEsignerType = function(){
        top.esignerTypeFlag = false;
        top.openBigForm(webPath+"/mfSysTemplateEsignerType/input?templateNo=" + templateNo,"新增电签人员类型", function(){
            if(top.esignerTypeFlag){
                $("#MfSysTemplateEsignerTypeController").html(top.esignerTypeTableHtml);
            }
        });
    }

    var _showIfElectricSign = function(){
        var ifElectricSign = $("[name='ifElectricSign']").val();
        if(ifElectricSign == "1"){
            $("[name='MfSysTemplateEsignerTypeController']").show();
        }else{
            $("[name='MfSysTemplateEsignerTypeController']").hide();
        }
    }
	return{
		init:_init,
        ajaxSave:_ajaxSave,
        addMfSysTemplateEsignerType:_addMfSysTemplateEsignerType,
        showIfElectricSign:_showIfElectricSign,
        deleteAjax:_deleteAjax,
        edit:_edit
	};
}(window,jQuery);