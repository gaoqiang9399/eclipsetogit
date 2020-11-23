;
var MfKindNodeTemplate = function(window, $) {
    var _init = function() {
        myCustomScrollbarForForm({
            obj : ".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        _templateNameZh_init();
    };

    // 模板名称, 选择组件初始化
    var _templateNameZh_init = function() {
        var modelNo = $("input[name=kindNo]").val();
        $.ajax({
            url : webPath+'/mfSysTemplate/getSysTemplateListNoFilterAjax?modelNo=' + modelNo,
            success : function(data) {
                var items = new Array();
                for (var idx in data.mfSysTemplateList) {
                    var sysTemplate = data.mfSysTemplateList[idx];
                    var item = new Object();
                    item["id"] = sysTemplate.templateNo;
                    item["name"] = sysTemplate.templateNameZh;
                    items.push(item);
                }
                $("[name='templateNameZh']").data("itemsData", data.mfSysTemplateList);

                $("[name='templateNameZh']").popupSelection({
                    inline : true,
                    multiple : false,
                    searchOn:true,
                    labelShow : false,
                    itemsCount:10,
                    title : true,
                    items : items,
                    addBtn:{//添加扩展按钮
                        "title":"新增",
                        "fun":function(hiddenInput, elem){
                            $(elem).popupSelection("hideSelect", elem);
                            addSysTemplateFun(function(data){
                                $("[name=templateNameZh]").val(data.templateNameZh);
                                $("[name=templateNameZh]").parent(".input-group").find(".pops-value").text(data.templateNameZh);
                                $("[name=templateNo]").val(data.templateNo);
                                $("[name=templateType]").val(data.templateType);
                                $("[name=templateTypeName]").val(data.templateTypeName);
                                $(elem).popupSelection("addItem",{"id":data.templateNo,"name":data.templateNameZh});
                                if($("[name=templateNameZh]").parent(".input-group").find('.error.required').length>0){
                                    $("[name=templateNameZh]").parent(".input-group").find('.error.required').remove();
                                }
                            });
                        }
                    },
                    changeCallback: function(obj, elem) {
                        var templateNo = $(obj).val();
                        $.ajax({
                            url : webPath+'/mfSysTemplate/getByIdAjax?templateNo=' + templateNo,
                            success : function(data) {
                                $("[name='templateNo']").val(data.mfSysTemplate.templateNo);
                                $("[name='templateNameEn']").val(data.mfSysTemplate.templateNameEn);
                                $("[name='templateNameZh']").val(data.mfSysTemplate.templateNameZh);
                                $("[name='templateType']").val(data.mfSysTemplate.templateType);
                                $("[name='templateTypeName']").val(data.mfSysTemplate.templateTypeName);
                            }
                        });
                    }
                });
            },error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //新增基础模板
    var addSysTemplateFun = function(callback){
        dialog({
            id:"addSysTemplateDialog",
            title:"新增基础模板",
            url: webPath+'/mfSysTemplate/input',
            width:1000,
            height:500,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    if(typeof(callback)== "function"){
                        callback(this.returnValue);
                    }else{
                    }
                }
            }
        }).showModal();
    };

    //新增模板保存方法（产品设置页面使用）
    var _addKindNodeNo = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var templateNo = $("input[name=templateNo]").val();
            if(templateNo==""){
                //新增基础模板时，基础模板默认文件名为templateFile.doc
                $("input[name=templateNameEn]").val("templateFile.doc");
            }
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data : {ajaxData:dataParam},
                success : function(data) {
                    if (data.flag == "success") {
                        top.addFlag = true;
                        top.mfSysTemplate = data.mfSysTemplate;
                        top.mfKindNodeTemplate = data.mfKindNodeTemplate;
                        myclose_click();
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                }
            });
        }

    };

    return {
        init : _init,
        addKindNodeNo:_addKindNodeNo,
    };
}(window, jQuery);