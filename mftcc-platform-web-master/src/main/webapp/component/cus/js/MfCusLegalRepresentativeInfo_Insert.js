;
var MfCusLegalRepresentativeInfo_Insert = function (window, $) {
    /**
     * 在此处开始定义内部处理函数。
     */
    var _init = function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
       // fa ren证件类型选择组件
        $("select[name=legalIdType]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//单选
            changeCallback : function (obj, elem) {
                var blur = $("input[name=legalIdNum]").attr("onblur");
                var newAddBlur = blur.substring(0,blur.indexOf("func_uior_valFormat_tips"));
                $("input[name=legalIdNum]").attr("onblur",newAddBlur);
                var idTypeValue = obj.data('values').val();  //当前选中的值
                $("select[name=sex]").val('');
                $("input[name=legalIdNum]").val('');
                $("input[name=legalIdNum]").parent().find('.error').remove();
                $("input[name=brithday]").val('');
                if(idTypeValue == "0"){ //身份证
                    var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
                    $("input[name=legalIdNum]").attr("onblur",blurAdd);
                }
            }
        });

        // fa ren pei ou证件类型选择组件
        $("select[name=apMateIdType]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//单选
            changeCallback : function (obj, elem) {
                var blur = $("input[name=apMateIdNo]").attr("onblur");
                var newAddBlur = blur.substring(0,blur.indexOf("func_uior_valFormat_tips"));
                $("input[name=apMateIdNo]").attr("onblur",newAddBlur);
                var idTypeValue = obj.data('values').val();  //当前选中的值
                $("select[name=sex]").val('');
                $("input[name=apMateIdNo]").val('');
                $("input[name=apMateIdNo]").parent().find('.error').remove();
                $("input[name=brithday]").val('');
                if(idTypeValue == "0"){ //身份证
                    var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
                    $("input[name=apMateIdNo]").attr("onblur",blurAdd);
                }
            }
        });
    };

    //新增保存
    var _ajaxSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

    /**
     * 在return方法中声明公开接口。
     */
    return {
        init: _init,
        ajaxSave: _ajaxSave,
    };

}(window, jQuery);

