;
var ExtensionBussConfig=function(window,$){
    var _init=function(){
        _extensionConfig();
        //处理要件配置的横向滚动条
        $(".scroll-div").mCustomScrollbar({
            horizontalScroll : true
        });
        _scrollUpdate();
    };
    //要件配置 start
    var _scrollUpdate=function(){
        $(".scroll-div").each(function(index){
            var $thisScroll = $(this).find(".mCSB_scrollTools");
            if($thisScroll.is(":visible")){
                $(this).find(".add-item").hide();
                $(this).parents(".item-div").find(".fixed-add-div").css("display","inline-block");
            }
        });
        $(".scroll-div").mCustomScrollbar("update");
    }
    /*************************展期业务设置***********************/
    var _extensionConfig=function(){
        var htmlStr="";
        //展期业务审批流程设置
        var approveWkfHtml=_getApprovalWkfConfigHtml();
        htmlStr=htmlStr+approveWkfHtml;
        /*//展期业务使用的费用设置
        var extemFeeHtml=_getFeeConfigHtml();
        htmlStr=htmlStr+extemFeeHtml;*/
        /*//展期业务使用的模板
        var extenTemplateHtml=getExtenTemplateConfigHtml();
        htmlStr=htmlStr+extenTemplateHtml;*/
        //展期业务节点配置
        var extensionNodeConfigHtml=_getBusWkfConfigHtml();
        htmlStr=htmlStr+extensionNodeConfigHtml;
        $("#extensionConfig_div").append(htmlStr);
    };
    /****************************************展期业务审批流程配置（启用/禁用，流程配置） begin****************************************/
    /**
     * 获得展期业务审批配置html
     */
    var _getApprovalWkfConfigHtml = function(){
        //内容
        var contentHtml = _getApprovalWkfContentHtml();
        var htmlStr='<div id="wkfConfig" class="sub-content-div padding_left_15">'
            +'<div class="sub-title"><span>流程设置</span></div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
        $("#extensionConfig_div").append(htmlStr);
    };

    var _getApprovalWkfContentHtml = function(){
        var htmlStr="";
        var checkspan = '<span class="checkbox-span curChecked" data-id="'+extenApproveFlow.kindFlowId+'" data-flowid="'+extenApproveFlow.flowId+'" onclick="MfKindConfig.updateFlowUseFlag(this,\''+extenApproveFlow.kindNo+'\',\'1\');"><i class="i i-gouxuan1"></i></span>';
        var alinkStr ='<a href="javascript:void(0);" onclick="MfKindConfig.openProcessDesigner(\''+extenApproveFlow.flowId+'\')" class="padding_left_15 pointer">配置</a>';

        if(extenApproveFlow.useFlag=="0"){
            checkspan='<span class="checkbox-span" data-id="'+extenApproveFlow.kindFlowId+'" data-flowid="'+extenApproveFlow.flowId+'" onclick="MfKindConfig.updateFlowUseFlag(this,\''+extenApproveFlow.kindNo+'\',\'0\');"><i class="i i-gouxuan1"></i></span>';
            alinkStr ='<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>';
        }
        htmlStr =htmlStr +'<div class="item-div">'
            +'<div class="item-title  margin_bottom_10">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>启用'+extenApproveFlow.flowApprovalName+'</span>'
            +'</span>'
            +'</div>'
            +'<div class="item-content margin_bottom_15 padding_left_15">'
            +'<div id="processItem'+extenApproveFlow.kindFlowId+'" class="padding_left_15">'
            +'当前流程：<span id="'+extenApproveFlow.flowApprovalNo+extenApproveFlow.kindNo+'">'+extenApproveFlow.flowRemark+'</span>'
            +alinkStr
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    //启用禁用审批流程
    var _updateFlowUseFlag = function(obj,kindNo,useflag){
        var kindFlowId = $(obj).data("id");
        var flowId = $(obj).data("flowid");
        if(useflag=="1"){//禁用
            $(obj).removeClass("curChecked");
            useflag = "0";
        }else{//启用
            $(obj).addClass("curChecked");
            useflag = "1";
        }
        $.ajax({
            url:webPath+"/mfKindFlow/updateUseFlagAjax",
            type:'post',
            data:{kindFlowId:kindFlowId,kindNo:kindNo,useFlag:useflag},
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag=="success"){
                    if(useflag=="0"){
                        $(obj).removeAttr("onclick");
                        $(obj).attr("onclick",'MfKindConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
                        //去掉a标签上的onclick事件
                        $("#processItem"+kindFlowId+" a").addClass("link-disabled");
                        $("#processItem"+kindFlowId+" a").removeAttr("onclick");
                    }else{
                        $(obj).removeAttr("onclick");
                        $(obj).attr("onclick",'MfKindConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
                        var alinkStr ='<a href="javascript:void(0);" onclick="MfKindConfig.openProcessDesigner(\''+flowId+'\')" class="padding_left_15 pointer">配置</a>';
                        $("#processItem"+kindFlowId+" a").remove();
                        $("#processItem"+kindFlowId).append(alinkStr);
                    }
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };

    //在流程设置中打开流程
    var _openProcessDesigner=function(wkfId){
        $.ajax({
            url:webPath+"/mfSysKind/getWorkflowIdAjax",
            type:'post',
            data:{appWorkflowNo:wkfId},
            async:false,
            success:function(data){
                if(data.flag=="success"){
                    window.open("tech/wkf/modelerEditor.jsp?command=DesignProcess&objectId="+data.workflowId);
                }
            },error:function(){
                //alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
    /****************************************展期业务审批流程配置（启用/禁用，流程配置）end****************************************/

    /****************************************展期业务 费用配置（新增/编辑/删除） begin****************************************/
    /**获取展期业务下的费用配置内容的html**/
    var _getFeeConfigHtml = function(){
        var kindNo="extension_buss";
        //内容
        var contentHtml = getFeeItemContentHtml(mfSysFeeItemList,kindNo);

        var htmlStr ='<div id="feeConfig'+kindNo+'" class="sub-content-div padding_left_15 fee-config">'
            +'<div class="sub-title"><span>产品费用项设置</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.addFeeItem(\''+kindNo+'\');" class="padding_left_15 pointer">新增</a>'
            +'</div>'
            +'<div class="sub-content padding_left_15">'
            +contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    var getFeeItemContentHtml = function(mfSysFeeItemList,kindNo){
        //循环产品下的费用项
        var subHtmlStr ="";
        var moreHtml = "";
        $.each(mfSysFeeItemList,function(i,mfSysFeeItem){
            var htmlTmp = getFeeItemHtml(mfSysFeeItem);
            if(i<countDef){
                subHtmlStr = subHtmlStr + htmlTmp;
            }else{
                moreHtml = moreHtml + htmlTmp;
            }
        });
        var moreStr = "";
        var optStr = "";
        if(mfSysFeeItemList.length>countDef){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#morefeeItem'+kindNo+'">'
                +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                +'</div>';
            moreStr = moreStr+'<div id="morefeeItem'+kindNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        var htmlStr = subHtmlStr+moreStr;
        return htmlStr;
    };

    /**产品下费用项html**/
    var getFeeItemHtml = function(mfSysFeeItem){
        var htmlStr ="";
        var takeType = mfSysFeeItem.takeType;
        var spanStr='<span class="padding_left_10">'+mfSysFeeItem.feeType+mfSysFeeItem.ext1+'的'+mfSysFeeItem.ext3+'%</span>';
        if(takeType=="2"){
            spanStr = '<span class="padding_left_10">'+mfSysFeeItem.feeType+'固定金额'+mfSysFeeItem.ext3+'元</span>';
        }

        htmlStr =htmlStr +'<div class="item-div">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="span-title">'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.editFeeItem(this,\''+mfSysFeeItem.id+'\');">'+mfSysFeeItem.itemName+'</a>'
            +'<i class="i i-lajitong item-delete" onclick="MfKindConfig.deleteFeeItem(\''+mfSysFeeItem.feeStdNo+'\',\''+mfSysFeeItem.id+'\');"></i>'
            +'</span>'
            + spanStr
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    /**新增费用项**/
    var _addFeeItem = function(kindNo){
        top.addFlag=false;
        top.mfSysFeeItemList="";
        window.parent.openBigForm(webPath+"/mfSysFeeItem/input?feeStdNo="+kindNo,"新增费用项",function(){
            if(top.addFlag){
                var htmlStr = getFeeItemContentHtml(top.mfSysFeeItemList);
                $("#feeConfig"+kindNo+" .sub-content").html(htmlStr);
            }
        });

    };
    /**编辑费用项**/
    var _editFeeItem = function(obj,id){
        top.addFlag=false;
        window.parent.openBigForm(webPath+"/mfSysFeeItem/getById?id="+id,"编辑费用项",function(){
            if(top.addFlag){
                var $itemDiv = $(obj).parents(".item-div");
                var sysKindFeeItem;
                $.ajax({
                    url:webPath+'/mfSysFeeItem/getByIdUpdatedAjax',
                    data:'id='+id,
                    dataType:'json',
                    type:'post',
                    async:false,
                    success:function(data){
                        if(data.flag=='success'){
                            sysKindFeeItem=data.bean;
                        }
                    }
                })
                var htmlStr = getFeeItemHtml(sysKindFeeItem);
                $itemDiv.before(htmlStr);
                $itemDiv.remove();
            }
        });

    };
    /**删除费用项**/
    var _deleteFeeItem = function(kindNo,id){
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+"/mfSysFeeItem/deleteAjax",
                data:{id:id,feeStdNo:kindNo},
                type:'post',
                dataType:'json',
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag == "success"){
                        var htmlStr = getFeeItemContentHtml(data.mfSysFeeItemList,kindNo);
                        $("#feeConfig"+kindNo+" .sub-content").html(htmlStr);
                    }else{
                        window.top.alert(top.getMessage("ERROR_INSERT"),1);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_INSERT"),0);
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });

    };

    /****************************************展期业务 费用配置（新增/编辑/删除） end****************************************/

    /****************************************业务模板配置（新增/配置/删除） begin****************************************/
    /**获取展期业务模板配置html  废弃**/
    /*var getExtenTemplateConfigHtml  = function(){
        var kindNo="extension_buss";
        var busModel="99";
        //内容
        var contentHtml = getExtenTemplatemContentHtml(templateModelConfigList,kindNo);

        var htmlStr='<div id="templateConfig'+kindNo+'" class="sub-content-div padding_left_15 template-config">'
                /!*+'<div class="sub-title"><span>展期模板设置</span><a href="javascript:void(0);" onclick="ExtensionBussConfig.addExtenTemplate(\''+kindNo+'\',\''+busModel+'\');" class="padding_left_15 pointer">新增</a></div>'*!/
                +'<div class="sub-content padding_left_15">'
                +contentHtml
                +'</div>'
                +'</div>';
        return htmlStr;
    };*/

    /**获取产品下业务模板内容的html 废弃**/
    var getExtenTemplatemContentHtml = function(mfTemplateConfigList,kindNo){
        /*//循环产品下的模板项
        var subHtmlStr ="";
        var moreHtml = "";
        $.each(mfTemplateConfigList,function(i,mfTemplateConfig){
            var htmlTmp = getExtenTemplateItemHtml(mfTemplateConfig);
            if(i < countDef){
                subHtmlStr = subHtmlStr + htmlTmp;
            }else{
                moreHtml = moreHtml + htmlTmp;
            }
        });
        var moreStr = "";
        var optStr = "";
        if(mfTemplateConfigList.length > countDef){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreTemplateItem'+kindNo+'">'
                    +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                    +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                    +'</div>';
            moreStr = moreStr+'<div id="moreTemplateItem'+kindNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        var htmlStr ='<div class="item-div">'+ subHtmlStr+moreStr+'</div>';
        return htmlStr;*/
    };

    /**产品下模板项html 废弃**/
    /*var getExtenTemplateItemHtml = function(mfTemplateConfig){
        var imgClass = "item-word";
        if(mfTemplateConfig.templateSuffix=="2"){
            imgClass = "item-excel";
        }else if(mfTemplateConfig.templateSuffix=="3"){
            imgClass = "item-pdf";
        }
        var htmlStr ='<div id="'+mfTemplateConfig.id+'" class="block-item">'
            +'<div class="item-title '+imgClass+'" onclick="MfKindConfig.templateSet(\''+mfTemplateConfig.id+'\',\''+mfTemplateConfig.templateNameEn+'\');">'
            +'<span>'+mfTemplateConfig.templateNameZh+'</span>'
            +'</div>'
            +'<i class="i i-x2" onclick="MfKindConfig.deleteTemplate(\''+mfTemplateConfig.id+'\',\''+mfTemplateConfig.modelNo+'\')"></i>'
            +'</div>';
        return htmlStr;
    };*/

    /*//新增模板  废弃
    var _addExtenTemplate = function(kindNo,busModel){
        top.addFlag=false;
        top.templateModelConfigList="";
        top.window.openBigForm(webPath+'/mfTemplateModelConfig/input?modelNo='+kindNo+"&busModel="+busModel,'新增模板',function(){
            if(top.addFlag){
                var htmlStr = getExtenTemplatemContentHtml(top.templateModelConfigList);
                $("#templateConfig"+kindNo+" .sub-content").html(htmlStr);
            }
        });
    };*/

    //设置模板
    var _templateSet = function(id,fileName){
        //实时查询数据，新增模板后不用刷新代码就可以打开保存的新模板
        $.ajax({
            url:webPath+"/mfTemplateModelConfig/getModelConfigByIdAjax",
            type:'post',
            data:{id:id},
            async:false,
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag=="success"){
                    var savePath = "/data/factor/model/docmodel/";
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
                        filePath : savePath,
                        saveFileName : saveFileName,
                        fileType : 0,
                        saveUrl :saveUrl
                    };
                    poCntJson.markUrl=basePath+webPath+"/mfSysTemplate/labelSetBase?fileName="+fileName;
                    poCntJson.printBtn="0";//取消打印按钮
                    mfPageOffice.openPageOffice(poCntJson);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };
    /*//删除模板 废弃
    var _deleteTemplate =function(id,kindNo){
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+"/mfTemplateModelConfig/deleteAjax",
                type:'post',
                data:{id:id,modelNo:kindNo},
                async:false,
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag=="success"){
                        var htmlStr = getExtenTemplatemContentHtml(data.templateModelConfigList);
                        $("#templateConfig"+kindNo+" .sub-content").html(htmlStr);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };*/

    /****************************************业务模板配置（新增/配置/删除） end****************************************/

    /****************************展期业务节点费用配置 开始********************************/
    /**获取产品下业务配置html**/
    var _getBusWkfConfigHtml  = function(){
        //内容
        var contentHtml = _getBusNodeHtml(extensionNodeList,jsonNodeConfig);
        var htmlStr='<div id="busConfig" class="sub-content-div padding_left_15 bus-config">'
            +'<div class="sub-title"><span>业务设置</span></div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    //获取展期业务节点配置的html
    var _getBusNodeHtml = function(extensionNodeList,nodeConfigMap){
        //循环处理产品下的业务节点
        var htmlStr ="";
        var moreHtml = "";
        var kindNo="extension_buss";
        $.each(extensionNodeList,function(i,extensionNode){
            //节点要件配置html
            var htmlTmp = "";
            var nodeNo=extensionNode.nodeNo;
            //htmlTmp = htmlTmp+getNodeFeeConfigHtml(nodeConfigMap[nodeNo]["feeList"],kindNo,nodeNo);
            htmlTmp = htmlTmp+getNodeDocConfigHtml(nodeConfigMap[nodeNo]["docList"],kindNo,nodeNo);
            htmlTmp = htmlTmp+getNodeTemplateConfigHtml(nodeConfigMap[nodeNo]["templateList"],kindNo,nodeNo);

            var subNodeHtml = '<div id="nodeConfig'+kindNo+nodeNo+ '" class="sub-content-div">'
                +'<div class="sub-title margin_bottom_10">'
                +'<span>'+extensionNode.nodeName+'</span>'
                +'</div>'
                +'<div class="sub-content margin_bottom_10">'
                + htmlTmp
                +'</div>'
                +'</div>';
            if(i<2){
                htmlStr = htmlStr + subNodeHtml;
            }else{
                moreHtml = moreHtml + subNodeHtml;
            }
        });
        var moreStr = "";
        var optStr = "";
        if(extensionNodeList.length>2){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreBusNode'+kindNo+'">'
                +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                +'</div>';
            moreStr = moreStr+'<div id="moreBusNode'+kindNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        htmlStr = htmlStr + moreStr;

        return htmlStr;
    };

    //节点费用设置
    var getNodeFeeConfigHtml = function(feeItemList,kindNo,nodeNo){
        //内容
        var contentHtml = getNodeFeeItemContentHtml(feeItemList,kindNo,nodeNo);

        var htmlStr ='<div id="feeConfig'+nodeNo+'" class="sub-content-div padding_left_15 fee-config">'
            +'<div class="sub-title"><span>费用项设置</span>'
            +'<a href="javascript:void(0);" onclick="ExtensionBussConfig.addNodeFeeItem(\''+kindNo+'\',\''+nodeNo+'\');" class="padding_left_15 pointer">配置</a>'
            +'</div>'
            +'<div class="sub-content padding_left_15">'
            +contentHtml
            +'</div>'
            +'</div>';

        return htmlStr;
    };

    var getNodeFeeItemContentHtml = function(feeItemList,kindNo,nodeNo){
        //循环产品下的费用项
        var subHtmlStr ="";
        var moreHtml = "";
        $.each(feeItemList,function(i,mfSysFeeItem){
            var htmlTmp = getNodeFeeItemHtml(mfSysFeeItem);
            if(i<countDef){
                subHtmlStr = subHtmlStr + htmlTmp;
            }else{
                moreHtml = moreHtml + htmlTmp;
            }
        });
        var moreStr = "";
        var optStr = "";
        if(feeItemList.length>countDef){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#morefeeItem'+kindNo+nodeNo+'">'
                +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                +'</div>';
            moreStr = moreStr+'<div id="morefeeItem'+nodeNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        var htmlStr = subHtmlStr+moreStr;
        return htmlStr;
    };

    /**产品下节点费用项html**/
    var getNodeFeeItemHtml = function(kindNodeFee){
        var htmlStr ="";
        var takeType = kindNodeFee.ext1;//收取类型：1-百分比 2-固额
        var spanStr='<span class="padding_left_10">'+kindNodeFee.ext5+' '+kindNodeFee.ext6+' '+kindNodeFee.ext7+'的'+kindNodeFee.ext3+'%，操作权限['+kindNodeFee.optPower+']</span>';
        if(takeType=="2"){
            spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+' '+kindNodeFee.ext6+'固定金额'+kindNodeFee.ext3+'元，操作权限['+kindNodeFee.optPower+']</span>';
        }
        htmlStr =htmlStr +'<div class="item-div">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="span-title">'
            +'<a href="javascript:void(0);" onclick="ExtensionBussConfig.editNodeFeeItem(\''+kindNodeFee.kindNodeFeeId+'\',\''+kindNodeFee.kindNo+'\',\''+kindNodeFee.nodeNo+'\');">'+kindNodeFee.itemName+'</a>'
            +'<i class="i i-lajitong item-delete" onclick="ExtensionBussConfig.deleteNodeFeeItem(\''+kindNodeFee.kindNodeFeeId+'\',\''+kindNodeFee.kindNo+'\',\''+kindNodeFee.nodeNo+'\');"></i>'
            +'</span>'
            + spanStr
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    //节点费用项新增
    var _addNodeFeeItem = function(kindNo,nodeNo){
        top.flag=false;
        top.feeList="";
        window.parent.openBigForm(webPath+"/mfKindNodeFee/input?kindNo="+kindNo+"&nodeNo="+nodeNo,"配置费用项",function(){
            if(top.flag){
                var htmlStr = getNodeFeeItemContentHtml(top.feeList,kindNo,nodeNo);
                $("#feeConfig"+nodeNo+" .sub-content").html(htmlStr);
            }
        });
    };

    //节点费用项编辑
    var _editNodeFeeItem = function(kindNodeFeeId,kindNo,nodeNo){
        top.flag=false;
        top.feeList="";
        window.parent.openBigForm(webPath+"/mfKindNodeFee/getById?kindNodeFeeId="+kindNodeFeeId+"&kindNo="+kindNo+"&nodeNo="+nodeNo,"编辑费用项",function(){
            if(top.flag){
                var htmlStr = getNodeFeeItemContentHtml(top.feeList,kindNo,nodeNo);
                $("#feeConfig"+nodeNo+" .sub-content").html(htmlStr);
            }
        });
    };

    //节点费用删除
    var _deleteNodeFeeItem = function(kindNodeFeeId,kindNo,nodeNo){
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+"/mfKindNodeFee/deleteAjax",
                type:'post',
                data:{kindNodeFeeId:kindNodeFeeId,kindNo:kindNo,nodeNo:nodeNo},
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag=="success"){
                        alert(data.msg,1);
                        var htmlStr = getNodeFeeItemContentHtml(data.feeList,kindNo,nodeNo);
                        $("#feeConfig"+nodeNo+" .sub-content").html(htmlStr);

                    }else{
                        alert(data.msg,0);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };
    /****************************展期业务节点费用配置 结束********************************/

    /****************************展期业务节点要件配置 开始********************************/
        //节点要件配置html
    var getNodeDocConfigHtml = function(docBizSceConfigList,kindNo,nodeNo){
            var docContentHtml = getNodeDocContentHtml(docBizSceConfigList,kindNo,nodeNo);
            var htmlStr = "";
            htmlStr = htmlStr+'<div id="docConfig'+kindNo+nodeNo+'" data-node="'+nodeNo+'" class="sub-content-div padding_left_15 node-doc-config  ">'
                +'<div class="sub-title margin_bottom_10 margin_top_10">'
                +'<span>要件设置</span>'
                +'</div>'
                +'<div class="sub-content margin_bottom_10 padding_left_15">'
                +docContentHtml
                +'</div>'
                +'</div>';
            return htmlStr;
        };

    var getNodeDocContentHtml = function(docBizSceConfigList,kindNo,nodeNo){
        var htmlStr = "";
        //循环该节点下的每一个要件docBizSceConfigList
        htmlStr =htmlStr + '<div class="item-div" data-kindno="'+kindNo+'" data-scno="'+nodeNo+'"><div class="scroll-div">';
        $.each(docBizSceConfigList,function(i,docConfig){
            var inputStr = "";
            var spanStr = "";
            if(docConfig.ifMustInput == "1"){
                inputStr = '<input type="checkbox" onclick="MfKindConfig.changeIsMust(this);" checked=true >';
                spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
            }else{
                inputStr = '<input type="checkbox" onclick="MfKindConfig.changeIsMust(this);">';
            }
            htmlStr =htmlStr +'<div class="block-item" data-doctype="'+docConfig.docType+'" data-docno="'+docConfig.docSplitNo+'" data-formno="'+docConfig.formId+'" data-ismust="'+docConfig.ifMustInput+'">'
                +'<div class="item">'
                +'<span>'+docConfig.docSplitName+'</span>'
                +spanStr
                +'</div>'
                +'<div class="hover-div">'
                +'<div class="hover-info">'
                +'<label style="cursor: pointer;font-weight: normal;">'
                +inputStr+'是否必填'
                +'</label>'
                +'</div>'
                +'<div class="hover-info">'
                +'<span class="i i-lajitong1 span-lajitong" onclick="MfKindConfig.deleteDocTypeItem(this);"></span>'
                +'</div>'
                +'</div>'
                +'</div>';
        });
        htmlStr =htmlStr +'<div class="add-item">'
            +'<div class="add-div" onclick="MfKindConfig.getEventObj(this);MfKindConfig.getDocTypeData(this);">'
            +'<i class="i i-jia2"></i>'
            +'</div>'
            +'</div>'
            +'</div>'
            +'<div class="fixed-add-div">'
            +'<div  class="add-div" onclick="MfKindConfig.getEventObj(this);MfKindConfig.getDocTypeData(this);">'
            +'<i class="i i-jia2"></i>'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    //更新要件必填状态
    var _changeIsMust = function(obj) {
        var $blockItem = $(obj).parents(".block-item");
        var docBizSceConfig ={};
        var btspan = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
        if ($(obj).is(":checked")) {
            $blockItem .find(".item").append(btspan);
            $blockItem .data("ismust", "1");
            docBizSceConfig.ifMustInput="1";

        } else {
            $blockItem.find(".btspan0").remove();
            $blockItem.find(".btspan1").remove();
            $blockItem.data("ismust", "0");
            docBizSceConfig.ifMustInput="0";
        }

        docBizSceConfig.dime1 = $blockItem.parents(".item-div").data("kindno");
        docBizSceConfig.scNo = $blockItem.parents(".item-div").data("scno");
        docBizSceConfig.docType = $blockItem.data("doctype");
        docBizSceConfig.docSplitNo = $blockItem.data("docno");
        var ajaxData = JSON.stringify(docBizSceConfig);
        //更新必填状态
        $.ajax({
            url : webPath+"/docBizSceConfig/updateAjax",
            data:{ajaxData:ajaxData},
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                }else{
                    window.top.alert(top.getMessage("FAILED_DELETE"),1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_DELETE"),0);
            },complete: function(){
                LoadingAnimate.stop();
            }
        });

    };

    //删除要件配置
    var _deleteDocTypeItem=function(obj) {
        var $blockItem =$(obj).parents(".block-item");
        //异步删除配置的文档信息
        var dime1 = $blockItem.data("kindno");
        var scNo = $blockItem.data("scno");
        var docType = $blockItem.data("doctype");
        var docSplitNo = $blockItem.data("docno");
        alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
            $.ajax({
                url : webPath+"/docBizSceConfig/deleteDocAjax",
                data:{dime1:dime1,scNo:scNo,docType:docType,docSplitNo:docSplitNo},
                type:'post',
                dataType:'json',
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag == "success"){
                        $blockItem.hide("slow",function(){
                            $blockItem.remove();
                        });
                        $(obj).parents(".scroll-div").mCustomScrollbar("update");
                    }else{
                        window.top.alert(top.getMessage("FAILED_DELETE"),1);
                    }
                },error:function(){
                    alert(top.getMessage("FAILED_DELETE"),0);
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };
    //新增要件配置弹层
    var _getDocTypeData =function(obj){
        var doctype="";
        var docSplitNo = "";
        var length = $(obj).parents(".item-div").find(".block-item").length;
        $(obj).parents(".item-div").find(".block-item").each(function(i,o){
            if(i == length-1){
                docSplitNo = docSplitNo + $(this).data("docno");
                doctype = doctype + $(this).data("doctype");
            }else{
                docSplitNo = docSplitNo + $(this).data("docno")+"@";
                doctype = doctype + $(this).data("doctype")+"@";
            }
        });
        selectDocTypeDialog(docTypeSelCall,docSplitNo,doctype);
    };
    /****************************展期业务节点要件配置 结束********************************/

    /****************************展期业务节点模板配置 开始********************************/
        //节点模板设置html
    var getNodeTemplateConfigHtml = function(templateList,kindNo,nodeNo){
            var templateContentHtml = getNodeTemplateContentHtml(templateList,kindNo,nodeNo);
            var htmlStr = "";
            htmlStr = htmlStr+'<div id="templateConfig'+nodeNo+'" data-node="'+nodeNo+'" class="sub-content-div padding_left_15 node-template-config  ">'
                +'<div class="sub-title margin_bottom_10 margin_top_10">'
                +'<span>模板使用设置</span>'
                +'</div>'
                +'<div class="sub-content margin_bottom_10 padding_left_15">'
                + templateContentHtml
                +'</div>'
                +'</div>';
            return htmlStr;
        };


    var getNodeTemplateContentHtml = function(templateList,kindNo,nodeNo){
        var htmlStr = "";
        //循环该节点下的每一个要件templateList
        htmlStr =htmlStr + '<div class="item-div" data-kindno="'+kindNo+'" data-nodeno="'+nodeNo+'"><div class="scroll-div">';
        $.each(templateList,function(i,templateConfig){
            var imgClass = "item-word";
            if(templateConfig.templateSuffix=="2"){
                imgClass = "item-excel";
            }else if(templateConfig.templateSuffix=="3"){
                imgClass = "item-pdf";
            }
            var inputStr = "";
            var spanStr = "";
            if(templateConfig.optPower == "2"){
                inputStr = '<input type="checkbox" onclick="MfKindConfig.changeOptPower(this);" checked=true >';
                spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>签</span>";
            }else{
                inputStr = '<input type="checkbox" onclick="MfKindConfig.changeOptPower(this);">';
            }
            htmlStr =htmlStr +'<div class="block-item" data-optpower= "'+templateConfig.optPower+'" data-templateno="'+templateConfig.templateNo+'">'
                +'<div class="'+imgClass+'">'
                +'<span>'+templateConfig.ext1+'</span>'
                + spanStr
                +'</div>'
                +'<div class="hover-div">'
                +'<div class="hover-info">'
                +'<label style="cursor: pointer;font-weight: normal;">'
                +inputStr+'是否可签'
                +'</label>'
                +'</div>'
                +'<div class="hover-info">'
                +'<span class="i i-lajitong1 span-lajitong" onclick="MfKindConfig.deleteNodeTemplate(this,\''+kindNo+'\',\''+nodeNo+'\',\''+templateConfig.kindNodeTemplateId+'\')"></span>'
                +'</div>'
                +'</div>'
                +'</div>';
        });
        htmlStr =htmlStr +'<div class="add-item">'
            +'<div class="add-div" onclick="ExtensionBussConfig.addNodeTemplate(\''+kindNo+'\',\''+nodeNo+'\');">'
            +'<i class="i i-jia2"></i>'
            +'</div>'
            +'</div>'
            +'</div>'
            +'<div class="fixed-add-div">'
            +'<div  class="add-div" onclick="ExtensionBussConfig.addNodeTemplate(\''+kindNo+'\',\''+nodeNo+'\');">'
            +'<i class="i i-jia2"></i>'
            +'</div>'
            +'</div>'
            +'</div>';

        return htmlStr;
    };

    //业务节点配置模板
    var _addNodeTemplate = function(kindNo,nodeNo){
        top.itemId="";
        top.flag= false;
        window.parent.openBigForm(webPath+"/mfKindNodeTemplate/getTemplateForNodeList?kindNo="+kindNo+"&nodeNo="+nodeNo,"选择模板",function(){
            if(top.flag){
                //异步更新产品下设置的节点定制
                addNodeTemplateCallBack(top.itemId,kindNo,nodeNo);

            }
        },"790px","450px");
    };
    var addNodeTemplateCallBack = function(itemIds,kindNo,nodeNo){
        $.ajax({
            url : webPath+"/mfKindNodeTemplate/insertAjax?kindNo="+kindNo+"&nodeNo="+nodeNo+"&templateNo="+encodeURI(itemIds),
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    var templateList = data.templateList;
                    var contentHtml = getNodeTemplateContentHtml(templateList,kindNo,nodeNo);
                    $("#templateConfig"+nodeNo).find(".sub-content").html(contentHtml);
                    //处理滚动条
                    var $scrollDiv = $("#templateConfig"+nodeNo).find(".scroll-div");
//					$scrollDiv.width($scrollDiv.width()-130);
                    $scrollDiv.mCustomScrollbar({
                        horizontalScroll : true
                    });
                    $scrollDiv.mCustomScrollbar("update");
                    var $thisScroll = $scrollDiv.find(".mCSB_scrollTools");
                    if($thisScroll.is(":visible")){
                        $scrollDiv.find(".add-item").hide();
                        $scrollDiv.parents(".item-div").find(".fixed-add-div").css("display","inline-block");
                    }
                    $scrollDiv.mCustomScrollbar("update");
                }else{
                    window.top.alert(top.getMessage("FAILED_DELETE"),1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_DELETE"),0);
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };

    var _deleteNodeTemplate = function(obj,kindNo,nodeNo,kindNodeTemplateId){
        var $blockItem = $(obj).parents(".block-item");
        alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
            $.ajax({
                url : webPath+"/mfKindNodeTemplate/deleteAjax",
                data:{kindNo:kindNo,nodeNo:nodeNo,kindNodeTemplateId:kindNodeTemplateId},
                type:'post',
                dataType:'json',
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag == "success"){
                        $blockItem.hide("slow",function(){
                            $blockItem.remove();
                        });
                        var $scrollDiv = $(obj).parents(".scroll-div");
                        $scrollDiv.mCustomScrollbar("destroy");
                        $scrollDiv.mCustomScrollbar({
                            horizontalScroll : true
                        });
                        var $thisScroll = $scrollDiv.find(".mCSB_scrollTools");
                        if(!$thisScroll.is(":visible")){
                            $scrollDiv.find(".add-item").show();
                            $scrollDiv.parents(".item-div").find(".fixed-add-div").css("display","none");
                        }
                        $scrollDiv.mCustomScrollbar("update");
                    }else{
                        window.top.alert(top.getMessage("FAILED_DELETE"),1);
                    }
                },error:function(){
                    alert(top.getMessage("FAILED_DELETE"),0);
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };

    var _changeOptPower = function(obj){
        var $blockItem = $(obj).parents(".block-item");
        var mfKindNodeTemplate ={};
        var btspan = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>签</span>";
        if ($(obj).is(":checked")) {
            $blockItem .find(".item-word").append(btspan);
            $blockItem .data("optpower", "2");
            mfKindNodeTemplate.optPower="2";

        } else {
            $blockItem.find(".btspan0").remove();
            $blockItem.find(".btspan1").remove();
            $blockItem.data("optpower", "1");
            mfKindNodeTemplate.optPower="1";
        }

        mfKindNodeTemplate.kindNo = $blockItem.parents(".item-div").data("kindno");
        mfKindNodeTemplate.nodeNo = $blockItem.parents(".item-div").data("nodeno");
        mfKindNodeTemplate.templateNo = $blockItem.data("templateno");
        var ajaxData = JSON.stringify(mfKindNodeTemplate);
        //更新必填状态
        $.ajax({
            url : webPath+"/mfKindNodeTemplate/updateAjax",
            data:{ajaxData:ajaxData},
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                }else{
                    window.top.alert(top.getMessage("FAILED_DELETE"),1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_DELETE"),0);
            },complete: function(){
                LoadingAnimate.stop();
            }
        });


    };
    /****************************展期业务节点模板配置 结束********************************/
    return{
        init:_init,
        changeIsMust:_changeIsMust,
        deleteDocTypeItem:_deleteDocTypeItem,
        getDocTypeData:_getDocTypeData,
        //deleteTemplate:_deleteTemplate,
        templateSet:_templateSet,
        updateFlowUseFlag:_updateFlowUseFlag,
        openProcessDesigner:_openProcessDesigner,
        addNodeFeeItem:_addNodeFeeItem,
        editNodeFeeItem:_editNodeFeeItem,
        deleteNodeFeeItem:_deleteNodeFeeItem,
        addNodeTemplate:_addNodeTemplate,
        //addExtenTemplate:_addExtenTemplate
    };
}(window,jQuery);