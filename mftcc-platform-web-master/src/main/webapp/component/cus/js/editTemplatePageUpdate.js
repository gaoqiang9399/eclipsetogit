;
var editTemplatePageUpdate=function(window, $){
    //初始化产品基础配置信息
    var _init = function(){
        $.ajax({
            url : webPath + "/mfCusFormConfig/findTemplatePage",
            data : {
                cusType:cusType
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    var htmlStr = getPrdctTemplateContentHtml(data);
                    $(".nav-content-div").html(htmlStr);
                } else {
                    alert(data.msg, 0);
                }
            },
            error : function() {
                alert("获取模板列表失败", 0);
            }
        });

    };

    var  getPrdctTemplateContentHtml = function(data){
        var htmlStr="";
        htmlStr =htmlStr+'<div class="sub-content-div padding_left_5" style="position:relative;">'
            +'<div class="sub-title">'
            +'<span class="font_size_14">'+cusTypeName+'</span>'
            +'</div>'
            +'<div class="sub-content">'
            +'<div class="item-div templateNode'+cusType+'" data-nodeno="'+cusType+'" data-nodename="'+cusTypeName+'">'
            +'<div class="item-content padding_bottom_20">'
            +'<div class="main-content-div">';
        htmlStr =htmlStr + getNodeTemplateContentHtml(data);
        htmlStr =htmlStr+'</div></div></div></div></div>';
        return htmlStr;
    };

    var getNodeTemplateContentHtml = function(data){
        var htmlStr="";
        $.each(data.mfKindNodeTemplates,function(i,kindNodeTemplate){
            var inputStr="";
            var spanStr="";
            var imgClass = "i-word";
            if(kindNodeTemplate.templateSuffix=="2"){
                imgClass = "i-excel";
            }else if(kindNodeTemplate.templateSuffix=="3"){
                imgClass = "i-pdf-1";
            }else if(kindNodeTemplate.templateSuffix=="4"){
                imgClass = "i-icon_html";
            }
            if(kindNodeTemplate.ifMustInput == "1"){//必填
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="editTemplatePageUpdate.changeIfMustInput(this);" type="checkbox" checked="true" >必填</span>'
            }else{
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="editTemplatePageUpdate.changeIfMustInput(this);" type="checkbox">必填</span>'
            }
            if(kindNodeTemplate.optPower == "2"){//可签
                spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="editTemplatePageUpdate.changeOptPower(this);" type="checkbox" checked="true">可签</span>'
            }else{
                spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="editTemplatePageUpdate.changeOptPower(this);" type="checkbox"  >可签</span>'
            }
            htmlStr = htmlStr+'<div class="block-item" data-ifmustinput="'+kindNodeTemplate.ifMustInput+'" data-optpower= "'+kindNodeTemplate.optPower+'" data-templateno="'+kindNodeTemplate.templateNo+'">'
                +'<div class="checkbox-div">'
                + spanStr
                +'</div>'
                +'<div class="item">'
                +'<i class="i '+imgClass+' item-icon" ></i><span title="'+kindNodeTemplate.ext1+'">'+kindNodeTemplate.ext1+'</span>'
                +'</div>'
                +'<div class="hover-div pointer">'
                +'<div class="hover-info">'
                +'<a href="javascript:void(0);" onclick="editTemplatePageUpdate.templateSet(\''+kindNodeTemplate.ext2+'\',\''+kindNodeTemplate.ext3+'\');" class="pointer">设置模板</a>'
                +'</div>'
                +'</div>'
                +'<i class="i i-x5 item-del pointer" onclick="editTemplatePageUpdate.deleteNodeTemplate(this,\''+kindNodeTemplate.kindNodeTemplateId+'\');"></i>'
                +'</div>';
        });

        //添加按钮
        htmlStr = htmlStr+'<div class="add-item-div" onclick="editTemplatePageUpdate.addNodeTemplate(this);">'
            +'<div class="add-item">'
            +'<div class="item">'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;

    };

    var getTemplateContentHtml = function(nodeNo,mfSysTemplate,mfKindNodeTemplate){
        var htmlStr = "";
        var inputStr="";
        var spanStr="";
        var imgClass = "i-word";
        if(mfSysTemplate.templateSuffix=="2"){
            imgClass = "i-excel";
        }else if(mfSysTemplate.templateSuffix=="3"){
            imgClass = "i-pdf-1";
        }else if(mfSysTemplate.templateSuffix=="4"){
            imgClass = "i-icon_html";
        }
        if(mfKindNodeTemplate.ifMustInput == "1"){//必填
            spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="editTemplatePageUpdate.changeIfMustInput(this);" type="checkbox" checked="true" >必填</span>'
        }else{
            spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="editTemplatePageUpdate.changeIfMustInput(this);" type="checkbox">必填</span>'
        }
        if(mfKindNodeTemplate.optPower == "2"){//可签
            spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="editTemplatePageUpdate.changeOptPower(this);" type="checkbox" checked="true">可签</span>'
        }else{
            spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="editTemplatePageUpdate.changeOptPower(this);" type="checkbox"  >可签</span>'
        }
        htmlStr = htmlStr+'<div class="block-item" data-ifmustinput="'+mfKindNodeTemplate.ifMustInput+'" data-optpower= "'+mfKindNodeTemplate.optPower+'" data-templateno="'+mfSysTemplate.templateNo+'">'
            +'<div class="checkbox-div">'
            + spanStr
            +'</div>'
            +'<div class="item">'
            +'<i class="i '+imgClass+' item-icon" ></i><span title="'+mfSysTemplate.templateNameZh+'">'+mfSysTemplate.templateNameZh+'</span>'
            +'</div>'
            +'<div class="hover-div pointer">'
            +'<div class="hover-info">'
            +'<a href="javascript:void(0);" onclick="editTemplatePageUpdate.templateSet(\''+mfSysTemplate.templateNo+'\',\''+mfSysTemplate.templateNameEn+'\');" class="pointer">设置模板</a>'
            +'</div>'
            +'</div>'
            +'<i class="i i-x5 item-del pointer" onclick="editTemplatePageUpdate.deleteNodeTemplate(this,\''+mfKindNodeTemplate.kindNodeTemplateId+'\');"></i>'
            +'</div>';

        return htmlStr;
    }
    //新增节点模板
    var _addNodeTemplate = function(obj){
        top.addFlag=false;
        var nodeName = $(obj).parents(".item-div").data("nodename");
        top.mfSysTemplate="";
        top.mfKindNodeTemplate="";
        top.window.openBigForm(webPath+'/mfKindNodeTemplate/input?kindNo=cus_template&nodeNo='+cusType,'新增模板',function(){
            if(top.addFlag){
                var htmlStr = getTemplateContentHtml(cusType,top.mfSysTemplate,top.mfKindNodeTemplate);
                $(obj).before(htmlStr);
            }
        });
    };


    //设置模板标签
    var _templateSet = function(templateNo,fileName){
        //实时查询数据，新增模板后不用刷新代码就可以打开保存的新模板
        $.ajax({
            url:webPath+"/mfSysTemplate/getByIdAjax",
            data:{templateNo:templateNo,templateNameEn:fileName},
            success:function(data){
                if(data.flag=="success"){
                    var filePath = data.filePath;
                    fileName=data.mfTemplateModelConfig.templateNameEn;
                    var templateNo=data.mfTemplateModelConfig.templateNo;
                    var fileNameOnly = fileName.substr(0,fileName.lastIndexOf("."));
                    var saveFileName = "";
                    var saveUrl ="";
                    var fileNameEnd = fileName.substr(fileName.lastIndexOf("."));
                    if(fileNameOnly=="templateFile"){
                        saveFileName = id+fileNameEnd;
                        saveUrl = "factor/component/model/updateTemplateName.jsp?templateNo="+templateNo+"&filename="+saveFileName+"&id="+id;
                    }
                    var poCntJson = {
                        fileName : fileName,
                        filePath : filePath,
                        saveFileName : saveFileName,
                        fileType : 0,
                        saveUrl :saveUrl
                    };
                    poCntJson.markUrl=webPath+"/mfSysTemplate/labelSetBase?fileName="+fileName;
                    poCntJson.printBtn="0";//取消打印按钮
                    mfPageOffice.editOpen(poCntJson);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //删除节点上的模板配置
    var _deleteNodeTemplate = function(obj,kindNodeTemplateId){
        var $blockItem = $(obj).parents(".block-item");
        alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
            $.ajax({
                url : webPath+"/mfKindNodeTemplate/deleteAjax",
                data:{kindNodeTemplateId:kindNodeTemplateId},
                success:function(data){
                    if(data.flag == "success"){
                        $blockItem.hide("slow",function(){
                            $blockItem.remove();
                        });
                    }else{
                        window.top.alert(top.getMessage("FAILED_DELETE"),1);
                    }
                },error:function(){
                    alert(top.getMessage("FAILED_DELETE"),0);
                }
            });
        });
    };

    //可编辑权限设置
    var _changeOptPower = function(obj){
        var $blockItem = $(obj).parents(".block-item");
        var mfKindNodeTemplate ={};
        if ($(obj).is(":checked")) {
            mfKindNodeTemplate.optPower="2";
        } else {
            mfKindNodeTemplate.optPower="1";
        }
        mfKindNodeTemplate.kindNo = kindNo;
        mfKindNodeTemplate.nodeNo = $blockItem.parents(".item-div").data("nodeno");
        mfKindNodeTemplate.templateNo = $blockItem.data("templateno");
        var ajaxData = JSON.stringify(mfKindNodeTemplate);
        //更新必填状态
        $.ajax({
            url : webPath+"/mfKindNodeTemplate/updateAjax",
            data:{ajaxData:ajaxData},
            success:function(data){
                if(data.flag == "success"){
                    if ($(obj).is(":checked")) {
                        $blockItem .data("optpower", "2");
                    } else {
                        $blockItem.data("optpower", "1");
                    }
                }else{
                    window.top.alert(top.getMessage("FAILED_UPDATE"),1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_UPDATE"),0);
            }
        });
    };
    //是否必填设置
    var _changeIfMustInput = function(obj){
        var $blockItem = $(obj).parents(".block-item");
        var mfKindNodeTemplate ={};
        if ($(obj).is(":checked")) {
            mfKindNodeTemplate.ifMustInput="1";
        } else {
            mfKindNodeTemplate.ifMustInput="0";
        }
        mfKindNodeTemplate.kindNo = kindNo;
        mfKindNodeTemplate.nodeNo = cusType;
        mfKindNodeTemplate.templateNo = $blockItem.data("templateno");
        var ajaxData = JSON.stringify(mfKindNodeTemplate);
        //更新必填状态
        $.ajax({
            url : webPath+"/mfKindNodeTemplate/updateAjax",
            data:{ajaxData:ajaxData},
            success:function(data){
                if(data.flag == "success"){
                    if ($(obj).is(":checked")) {
                        $blockItem .data("ifmustinput", "1");
                    } else {
                        $blockItem.data("ifmustinput", "0");
                    }
                }else{
                    window.top.alert(top.getMessage("FAILED_UPDATE"),1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_UPDATE"),0);
            }
        });
    };
    return{
        init:_init,
        addNodeTemplate:_addNodeTemplate,
        changeOptPower:_changeOptPower,
        changeIfMustInput:_changeIfMustInput,
        templateSet:_templateSet,
        deleteNodeTemplate :_deleteNodeTemplate,
    };

}(window, jQuery);