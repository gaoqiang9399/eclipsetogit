;
var MfKindTemplateConfig=function(window, $){
    //初始化产品基础配置信息
    var _init = function(data){
        var htmlStr = getPrdctTemplateConfigHtml(data);
        $(".nav-content-div").html(htmlStr);
        $(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
        $(".vertical-bar").css("height",$(".nav-content-div").height()-40);
    };
    //产品模板配置
    var getPrdctTemplateConfigHtml = function (data) {
        var mfSysKind = data.mfSysKind;
        var nodeList = data.nodeList;
        var nodeConfigMap = data.nodeConfigMap;
        var htmlStr = "";
        htmlStr = htmlStr + '<div class="content-div templateConfig special-config"><div class="sub-content-div padding_left_15">'
            + '<div class="vertical-bar"><i class="i i-yuan"></i></div>'
            + '<div class="sub-title">'
            + '<span class="font_size_16">模板配置</span>'
            + '<button onclick="MfSysKindConfig.addBusNode(\'' + mfSysKind.busModel + '\',\'template\');" class="margin_left_15  btn add-node pointer">节点定制</button>'
            + '</div>'
            + '<div class="sub-content padding_left_45 margin_top_30">';
        htmlStr = htmlStr + getPrdctTemplateContentHtml(nodeList, nodeConfigMap, mfSysKind);
        htmlStr = htmlStr + '</div></div></div>';
        return htmlStr;
    };

    var getPrdctTemplateContentHtml = function (nodeList, nodeConfigMap, mfSysKind) {
        var htmlStr = "";
        $.each(nodeList, function (i, kindNode) {
            /*htmlStr =htmlStr+'<div class="sub-content-div padding_left_5" style="position:relative;">'
            +'<div class="horizontal-bar"><i class="i i-yuan"></i></div>'
            +'<div class="sub-title">'
            +'<span class="font_size_14">'+kindNode.nodeName+'</span>'
            +'</div>'
            +'<div class="sub-content">'
            +'<div class="item-div templateNode'+kindNode.nodeNo+'" data-nodeno="'+kindNode.nodeNo+'" data-nodename="'+kindNode.nodeName+'">'
            +'<div class="item-content padding_bottom_20">'
            +'<div class="main-content-div">';
            var templateList = nodeConfigMap[kindNode.nodeNo]["templateList"];
            htmlStr =htmlStr + getNodeTemplateContentHtml(templateList,kindNode.nodeNo);
            htmlStr =htmlStr+'</div></div></div></div></div>';*/
            // 重构div
            var docList = nodeConfigMap[kindNode.nodeNo]["templateList"];
            var nodeDocContenHtml = getNodeTemplateContentHtml(docList, kindNode.nodeNo);

            var box1 = $("<div></div>").addClass("sub-content-div padding_left_5")
                .css({"position": "relative"});
            var $li = $("<i></i>").addClass('i i-yuan');

            var box2 = $("<div></div>").addClass("horizontal-bar").append($li);
            var $subTile = $("<div></div>").addClass('sub-title');
            var $nodeName = $("<span></span>").addClass("font_size_14").text(kindNode.nodeName);
            $subTile.append($nodeName);
            //获取该节点的节点编号
            var nodeNo = kindNode.nodeNo;
            //获取节点名称
            var nodeName = kindNode.nodeName;
            //该节点下是否存在子流程  0不存在  1存在
            var isExistDoc = nodeConfigMap[kindNode.nodeNo]["isExistDoc"];
            //该节点是主流程节点还是子流程节点  0是主流程  1是子流程
            var mainApprove = nodeConfigMap[kindNode.nodeNo]["mainApprove"];
            var box5,box7;
            if (isExistDoc == "1") {
                var approveDocMap = nodeConfigMap[kindNode.nodeNo]["approveDocMap"];
                var $button = $("<button></button>").addClass("margin_left_15  btn add-node pointer").text(nodeName + "节点定制");
                $button.attr("onclick", "MfSysKindConfig.addBusNode('" + mfSysKind.busModel + "','template','" + nodeNo + "','" + mainApprove + "');");
                $nodeName.append($button);


                var boxApprove1 = $("<div></div>").addClass("boxApprove-1").css({
                    "position": "absolute",
                    "top": "12px",
                    "left": "-23px",
                    "width": "1px",

                    "border-left": "1px dashed  #A5B5C8",
                    "font-size": "20px"
                });

                box1.append(box2);
                box1.append($subTile).append(boxApprove1);

                var nodeListApprove = approveDocMap["nodeList"];
                var nodeConfigMapApprove = approveDocMap["nodeConfigMap"];

                //要件的总行数
                var docNumTotal = approveDocMap["docNumTotal"];
                //要件的节点数
                var nodeListNum = approveDocMap["nodeListNum"];
                //需要增加的行数
                var addNum = docNumTotal - nodeListNum;

                var myheight = nodeListNum * 204 + addNum * 90 + 90;

                boxApprove1.height(myheight);

                var htmlStrNew = getPrdctTemplateContentHtml(nodeListApprove, nodeConfigMapApprove, mfSysKind);
                box5 = $("<div></div>").addClass("font_size_14 docNode" + nodeNo).attr("data-scno", nodeNo);
                box7 = $("<div></div>").addClass("main-content-div   lp").css({"margin-left": "30px"}).append(htmlStrNew);

            } else {
                box5 = $("<div></div>").addClass("item-div docNode" + nodeNo).attr("data-scno", nodeNo);
                box7 = $("<div></div>").addClass("main-content-div").append(nodeDocContenHtml);
            }
            var box4 = $("<div></div>").addClass("sub-content");

            var box6 = $("<div></div>").addClass("item-content padding_bottom_20");
            //div内部存放标签之后  包括本身的是outerHTML
            box6.html(box7[0].outerHTML);
            box5.html(box6[0].outerHTML);
            box4.html(box5[0].outerHTML);
            //div追加div
            box1.append(box2);
            box1.append($subTile).append(box4);

            htmlStr = htmlStr + box1[0].outerHTML;


        });
        return htmlStr;
    };

    var getNodeTemplateContentHtml = function(templateList,nodeNo){
        var htmlStr="";
        $.each(templateList,function(i,kindNodeTemplate){
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
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfKindTemplateConfig.changeIfMustInput(this);" type="checkbox" checked="true" >必填</span>'
            }else{
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfKindTemplateConfig.changeIfMustInput(this);" type="checkbox">必填</span>'
            }
            if(kindNodeTemplate.optPower == "2"){//可签
                spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="MfKindTemplateConfig.changeOptPower(this);" type="checkbox" checked="true">可签</span>'
            }else{
                spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="MfKindTemplateConfig.changeOptPower(this);" type="checkbox"  >可签</span>'
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
                +'<a href="javascript:void(0);" onclick="MfKindTemplateConfig.templateSet(\''+kindNodeTemplate.templateNo+'\',\''+kindNodeTemplate.ext3+'\');" class="pointer">设置模板</a>'
                +'</div>'
                +'</div>'
                +'<i class="i i-x5 item-del pointer" onclick="MfKindTemplateConfig.deleteNodeTemplate(this,\''+nodeNo+'\',\''+kindNodeTemplate.kindNodeTemplateId+'\');"></i>'
                +'</div>';
        });
        //添加按钮
        htmlStr = htmlStr+'<div class="add-item-div" onclick="MfKindTemplateConfig.addNodeTemplate(this,\''+nodeNo+'\');">'
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
            spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfKindTemplateConfig.changeIfMustInput(this);" type="checkbox" checked="true" >必填</span>'
        }else{
            spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfKindTemplateConfig.changeIfMustInput(this);" type="checkbox">必填</span>'
        }
        if(mfKindNodeTemplate.optPower == "2"){//可签
            spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="MfKindTemplateConfig.changeOptPower(this);" type="checkbox" checked="true">可签</span>'
        }else{
            spanStr=spanStr+'<span style="background: #5EB3C7;margin-left: 1px;"><input class="pointer" onclick="MfKindTemplateConfig.changeOptPower(this);" type="checkbox"  >可签</span>'
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
            +'<a href="javascript:void(0);" onclick="MfKindTemplateConfig.templateSet(\''+mfSysTemplate.templateNo+'\',\''+mfSysTemplate.templateNameEn+'\');" class="pointer">设置模板</a>'
            +'</div>'
            +'</div>'
            +'<i class="i i-x5 item-del pointer" onclick="MfKindTemplateConfig.deleteNodeTemplate(this,\''+nodeNo+'\',\''+mfKindNodeTemplate.kindNodeTemplateId+'\');"></i>'
            +'</div>';

        return htmlStr;
    }
    //新增节点模板
    var _addNodeTemplate = function(obj,nodeNo){
        top.addFlag=false;
        var nodeName = $(obj).parents(".item-div").data("nodename");
        top.mfSysTemplate="";
        top.mfKindNodeTemplate="";
        top.window.openBigForm(webPath+'/mfKindNodeTemplate/input?kindNo='+kindNo+'&nodeNo='+nodeNo,'新增模板',function(){
            if(top.addFlag){
                var htmlStr = getTemplateContentHtml(nodeNo,top.mfSysTemplate,top.mfKindNodeTemplate);
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
                    fileName=data.mfSysTemplate.templateNameEn;
                    var templateNo=data.mfSysTemplate.templateNo;
                    var fileNameOnly = fileName.substr(0,fileName.lastIndexOf("."));
                    var saveFileName = "";
                    var saveUrl ="";
                    var fileNameEnd = fileName.substr(fileName.lastIndexOf("."));
                    if(fileNameOnly=="templateFile"){
                        saveFileName = id+fileNameEnd;
                        saveUrl = "factor/component/model/updateTemplateName.jsp?templateNo="+templateNo+"&filename="+saveFileName;
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
    var _deleteNodeTemplate = function(obj,nodeNo,kindNodeTemplateId){
        var $blockItem = $(obj).parents(".block-item");
        alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
            $.ajax({
                url : webPath+"/mfKindNodeTemplate/deleteAjax",
                data:{kindNo:kindNo,nodeNo:nodeNo,kindNodeTemplateId:kindNodeTemplateId},
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