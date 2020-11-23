;
var MfKindConfig=function(window, $){

    /****************************************产品配置--页面初始化 begin****************************************/
        //页面初始化函数
    var _init = function(){
            var htmlStr = getKindItemConfigHtml();
            if(htmlStr!=""){
                $(".config-div").append(htmlStr);
                //处理要件配置的横向滚动条
                $(".scroll-div").mCustomScrollbar({
                    horizontalScroll : true
                });
                scrollUpdate();
                rateTypeBindEvent();
                repayTypeBindEvent();
                normCalcTypeBindEvent();
                interestDerateBindEvent();
                festivalTypeBindEvent();
                lastTermBalanceTypeBindEvent();
                repayDateBindEvent();
                instCollectTypeBindEvent();
                feeCollectWayBindEvent();
                lsbqChargeIntstBindEvent();
                secondNormCalcTypeBindEvent();
                instCalcBaseBindEvent();
                preInstCollectTypeBindEvent();
                preRepayTypeBindEvent();
                preRepayInstCalcBindEvent();
                yearDaysBindEvent();
                rateDecimalDigitsBindEvent();
                icTypeBindEvent();
                returnPlanPointBindEvent();
                feePlanMergeBindEvent();
                multiplePlanMergeBindEvent();
                balanceDealTypeBindEvent();
                overCmpdRateInputtypeBindEvent();
                overIntstFlagBindEvent();
                cmpdRateTypeBindEvent();
                penaltyFincBindEvent();
                preRepayPenaltyBindEvent();
                feeItemDivDragInit();
            }else{
                $(".config-div").append('<div style="text-align: center;font-size: 20px;padding: 20px 0px;color: #666;">暂无产品</div>');
            }

        };

    //获取产品配置的html
    var getKindItemConfigHtml = function(){
        var htmlStr = "";
        $.each(mfSysKindList, function(i, mfSysKind) {
            //获取产品配置标题html
            var kindTitleHtml = getKindTitleHtml(mfSysKind.busModelName,mfSysKind.kindNo,mfSysKind.kindName);
            //获取产品配置内容的html
            var kindConfigContentHtml = getKindConfigContentHtml(mfSysKind);

            htmlStr = htmlStr+'<div id="kindItem'+mfSysKind.kindNo+'" class="list-item col-md-9">'
                +'<div class="title-div">'
                + kindTitleHtml
                +'</div>'
                +'<div class="content-div">'
                + kindConfigContentHtml
                +'</div>'
                +'</div>';
        });
        return htmlStr;
    };
    /**获取产品配置标题html**/
    var getKindTitleHtml= function(busModelName,kindNo,kindName){
        var htmlStr ='<ol class="breadcrumb pull-left padding_left_0" id="kindConfig'+kindNo+'">'
            +'<li class="active">'
            +'<span name="title">'+kindName+'</span>'
            +'<span class="active"> --'+busModelName+'</span>'
            +'</li>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.editKind(\''+kindNo+'\');" class="padding_left_15 pointer">配置</a>'
            +'</ol>';
        return htmlStr;
    };

    /**获取产品配置内容的html**/
    var getKindConfigContentHtml = function(mfSysKind){
        var kindNo = mfSysKind.kindNo;
        var htmlStr="";
        //获取产品下流程配置html
        var approvalWkfHtml =getApprovalWkfConfigHtml(approvalWkfMap[kindNo],kindNo);
        htmlStr = htmlStr+approvalWkfHtml;
        //获取产品下产品费用配置html
        var feeHtml = getFeeConfigHtml(feeMap[kindNo],kindNo);
        htmlStr = htmlStr+feeHtml;
        /*//获取产品下业务模板配置html
        var templateHtml =getTemplateConfigHtml(templateMap[kindNo],mfSysKind);
        htmlStr = htmlStr+templateHtml;*/
        //获取产品下业务配置html
        var busWkfHtml =getBusWkfConfigHtml(kindNodeMap[kindNo],kindNodeConfigMap[kindNo],mfSysKind);
        htmlStr = htmlStr+busWkfHtml;
        //获取产品下核算配置html
        var calcHtml =getCalcConfigHtml(mfSysKind);
        htmlStr = htmlStr+calcHtml;

        return htmlStr;
    };
    /****************************************页面信息初始化 end****************************************/


    /****************************************产品基本配置（新增/编辑） begin****************************************/

    /**新增产品基本信息**/
    var _addKind = function(){
        top.addFlag= false;
        window.parent.openBigForm(webPath+"/mfSysKind/input","新增产品",function(){
            if(top.addFlag){
            }
        });
    };
    /**编辑产品基本信息**/
    var _editKind = function(kindNo){
        top.addFlag= false;
        window.parent.openBigForm(webPath+"/mfSysKind/getById?kindNo="+kindNo,"编辑产品",function(){
            if(top.addFlag){

            }
        });
    };

    /****************************************产品基本配置（新增/编辑） end****************************************/


    /****************************************费用配置（新增/编辑/删除） begin****************************************/

    /**获取产品下的费用配置内容的html**/
    var getFeeConfigHtml = function(mfSysFeeItemList,kindNo){
        //内容
        var contentHtml = getFeeItemContentHtml(mfSysFeeItemList,kindNo);

        var htmlStr ='<div id="feeConfig'+kindNo+'" class="sub-content-div padding_left_15 fee-config" data-kindno="'+kindNo+'">'
            +'<div class="sub-title"><span>产品费用项设置</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.addFeeItem(\''+kindNo+'\');" class="padding_left_15 pointer">新增</a>'
            +'</div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
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
//			if(i<countDef){
            subHtmlStr = subHtmlStr + htmlTmp;
//			}else{
//				moreHtml = moreHtml + htmlTmp;
//			}
        });
        var moreStr = "";
        var optStr = "";
//		if(mfSysFeeItemList.length>countDef){
//			optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#morefeeItem'+kindNo+'">'
//					+'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
//					+'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
//					+'</div>';
//			moreStr = moreStr+'<div id="morefeeItem'+kindNo+'" class="more-div fee-more-div collapse">'+moreHtml+'</div>'+optStr;
//		}
        var htmlStr = '<div class="feeItem-div">'+subHtmlStr+'</div>'+moreStr;
        return htmlStr;
    };

    /**产品下费用项html**/
    var getFeeItemHtml = function(mfSysFeeItem){
        var htmlStr ="";
        var takeType = mfSysFeeItem.takeType;
        var spanStr='<span class="padding_left_10">'+mfSysFeeItem.feeType+mfSysFeeItem.ext1+'的'+mfSysFeeItem.ext3+'%</span>';
        if(takeType=="2"){
            spanStr = '<span class="padding_left_10">'+mfSysFeeItem.feeType+'固定金额(分摊)'+mfSysFeeItem.ext3+'元</span>';
        }
        if(takeType=="3"){
            spanStr = '<span class="padding_left_10">'+mfSysFeeItem.feeType+'固定金额(额定)'+mfSysFeeItem.ext3+'元</span>';
        }
        if(takeType=="4"){
            spanStr = '<span class="padding_left_10">'+mfSysFeeItem.feeType+'利息差额'+'</span>';
        }
        htmlStr =htmlStr +'<div class="item-div fee-move-div" data-id=\''+mfSysFeeItem.id+'\'>'
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
                feeItemDivDragInit();
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
                        feeItemDivDragInit();
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

    //费用拖动事件初始化
    var feeItemDivDragInit = function(){
        $(".feeItem-div").sortable({
            cursor: "move" ,
            items: ".fee-move-div",
            start : function(event, ui) {
            },
            update : function(event, ui) {
            }
        });
        // 拖动事件结束时触发
        $(".feeItem-div").on("sortupdate", function(event, ui) {
            var $feeConfig = $(this).parents(".fee-config");
            var kindNo = $feeConfig.data("kindno");
            var itemIds = [];
            $.each($(this).find(".fee-move-div"),function(index , item){
                itemIds[index] = $(item).data("id");
            });
            $.ajax({
                url : webPath+"/mfSysFeeItem/updateSortAjax",
                data : {
                    feeStdNo : kindNo,
                    itemIds : itemIds
                },
                type : 'post',
                dataType : 'json',
                traditional: true,
                success : function(data) {
                    if(data.flag=="success"){
                    }else{
                        alert(top.getMessage("FAILED_OPERATION","排序"),0);
                    }
                },
                error : function() {
                    alert(top.getMessage("FAILED_OPERATION","排序"),0);
                }
            });
        });

    };

    /****************************************费用配置（新增/编辑/删除） end****************************************/


    /****************************************业务模板配置（新增/配置/删除） begin****************************************/
    /**获取产品下业务模板配置html**/
    var getTemplateConfigHtml  = function(mfTemplateConfigList,mfSysKind){
        //内容
        var contentHtml = getTemplatemContentHtml(mfTemplateConfigList,mfSysKind.kindNo);

        var htmlStr='<div id="templateConfig'+mfSysKind.kindNo+'" class="sub-content-div padding_left_15 template-config">'
            +'<div class="sub-title"><span>产品模板设置</span><a href="javascript:void(0);" onclick="MfKindConfig.addTemplate(\''+mfSysKind.kindNo+'\',\''+mfSysKind.busModel+'\');" class="padding_left_15 pointer">新增</a></div>'
            +'<div class="sub-content padding_left_15">'
            +contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    /**获取产品下业务模板内容的html**/
    var getTemplatemContentHtml = function(mfTemplateConfigList,kindNo){
        //循环产品下的模板项
        var subHtmlStr ="";
        var moreHtml = "";
        $.each(mfTemplateConfigList,function(i,mfTemplateConfig){
            var htmlTmp = getTemplateItemHtml(mfTemplateConfig);
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
        return htmlStr;
    };

    /**产品下模板项html**/
    var getTemplateItemHtml = function(mfTemplateConfig){
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
    };

    //新增模板
    var _addTemplate = function(kindNo,busModel){
        top.addFlag=false;
        top.templateModelConfigList="";
        top.window.openBigForm(webPath+'/mfTemplateModelConfig/input?modelNo='+kindNo+"&busModel="+busModel,'新增模板',function(){
            if(top.addFlag){
                var htmlStr = getTemplatemContentHtml(top.templateModelConfigList);
                $("#templateConfig"+kindNo+" .sub-content").html(htmlStr);
            }
        });
    };

    //设置模板
    var _templateSet = function(id,fileName){
        //实时查询数据，新增模板后不用刷新代码就可以打开保存的新模板
        $.ajax({
            url:webPath+"/mfSysTemplate/getByIdAjax",
            type:'post',
            data:{templateNo:templateNo,templateNameEn:fileName},
            async:false,
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
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
                        saveUrl = "factor/component/model/updateTemplateName.jsp?templateNo="+templateNo+"&filename="+saveFileName+"&id="+id;
                    }
                    var poCntJson = {
                        fileName : fileName,
                        filePath : filePath,
                        saveFileName : saveFileName,
                        fileType : 0,
                        saveUrl :saveUrl
                    };
                    poCntJson.opName = data.opName;
                    poCntJson.orgNo = data.orgNo;
                    poCntJson.orgName = data.orgName;
                    poCntJson.markUrl=webPath+"/mfSysTemplate/labelSetBase?fileName="+fileName;
                    poCntJson.printBtn="0";//取消打印按钮
                    mfPageOffice.editOpen(poCntJson);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };
    //删除模板
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
                        var htmlStr = getTemplatemContentHtml(data.templateModelConfigList);
                        $("#templateConfig"+kindNo+" .sub-content").html(htmlStr);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };

    /****************************************业务模板配置（新增/配置/删除） end****************************************/

    /****************************************业务设置（进件节点、业务节点设置） begin************************************/
    /**获取产品下业务配置html**/
    var getBusWkfConfigHtml  = function(kindNodeList,nodeConfigMap,mfSysKind){
        //内容
        var contentHtml = getBusNodeHtml(kindNodeList,nodeConfigMap,mfSysKind.kindNo);

        var htmlStr='<div id="busConfig'+mfSysKind.kindNo+'" class="sub-content-div padding_left_15 bus-config">'
            +'<div class="sub-title"><span>业务设置</span><a href="javascript:void(0);" onclick="MfKindConfig.addBusNode(\''+mfSysKind.kindNo+'\',\''+mfSysKind.busModel+'\');" class="padding_left_15 pointer">节点定制</a></div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    //节点定制
    var _addBusNode = function(kindNo,busModel){
        top.itemId="";
        top.flag= false;
        window.parent.openBigForm(webPath+"/mfKindNode/getKindNodeList?busModel="+busModel+'&kindNo='+kindNo,"选择业务节点",function(){
            if(top.flag){
                //异步更新产品下设置的节点定制
                addBusNodeCallBack(top.itemId,kindNo,busModel);

            }
        },"790px","450px");
    };

    var addBusNodeCallBack = function(itemIds,kindNo,busModel){
        $.ajax({
            url : webPath+"/mfKindNode/insertAjax?kindNo="+kindNo+"&busModel="+busModel+"&nodeNo="+encodeURI(itemIds),
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    var kindNodeList = data.kindNodeMap[kindNo];
                    var nodeConfigMap = data.kindNodeConfigMap[kindNo]
                    var contentHtml = getBusNodeHtml(kindNodeList,nodeConfigMap,kindNo);
                    $("#busConfig"+kindNo).find(".sub-content").html(contentHtml);
                    $("#busConfig"+kindNo +" .scroll-div").mCustomScrollbar({
                        horizontalScroll : true
                    });
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

    //节点的配置
    var _kindNodeSet = function(kindNo,kindNodeId,nodeNo){
        top.flag= false;
        top.itemId="";
        window.parent.openBigForm(webPath+"/mfKindNode/getNodeSetList?kindNodeId="+kindNodeId+'&kindNo='+kindNo,"节点配置",function(){
            if(top.flag){
                kindNodeSetCallBack(top.itemId,kindNo,kindNodeId,nodeNo);
            }
        },"790px","450px");
    };
    var kindNodeSetCallBack = function(itemIds,kindNo,kindNodeId,nodeNo){
        $.ajax({
            url : webPath+"/mfKindNode/updateAjax?kindNo="+kindNo+"&kindNodeId="+kindNodeId+"&nodeNo="+encodeURI(itemIds),
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    var nodeConfigMap = data.nodeConfigMap;
                    var htmlTmp = "";
                    if(nodeConfigMap[nodeNo]["docList"]!=undefined){
                        htmlTmp = htmlTmp+getNodeDocConfigHtml(nodeConfigMap[nodeNo]["docList"],kindNo,nodeNo);
                    }
                    if(nodeConfigMap[nodeNo]["templateList"]!=undefined){
                        htmlTmp = htmlTmp+getNodeTemplateConfigHtml(nodeConfigMap[nodeNo]["templateList"],kindNo,nodeNo);
                    }
                    if(nodeConfigMap[nodeNo]["feeList"]!=undefined){
                        htmlTmp = htmlTmp+getNodeFeeConfigHtml(nodeConfigMap[nodeNo]["feeList"],kindNo,nodeNo);
                    }
                    if(nodeConfigMap[nodeNo]["interceptList"]!=undefined){
                        htmlTmp = htmlTmp+getNodeInterceptConfigHtml(nodeConfigMap[nodeNo]["interceptList"],kindNo,nodeNo);
                    }
                    $("#nodeConfig"+kindNo+nodeNo).find(".sub-content").html(htmlTmp);
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


    //获取业务节点配置的html
    var getBusNodeHtml = function(kindNodeList,nodeConfigMap,kindNo){
        //循环处理产品下的业务节点
        var htmlStr ="";
        var moreHtml = "";
        $.each(kindNodeList,function(i,kindNode){
            //节点要件配置html
            var htmlTmp = "";
            if(kindNode.docFlag=="1"){
                htmlTmp = htmlTmp+getNodeDocConfigHtml(nodeConfigMap[kindNode.nodeNo]["docList"],kindNo,kindNode.nodeNo);
            }
            if(kindNode.templateFlag=="1"){
                htmlTmp = htmlTmp+getNodeTemplateConfigHtml(nodeConfigMap[kindNode.nodeNo]["templateList"],kindNo,kindNode.nodeNo);
            }
            if(kindNode.feeFlag=="1"){
                htmlTmp = htmlTmp+getNodeFeeConfigHtml(nodeConfigMap[kindNode.nodeNo]["feeList"],kindNo,kindNode.nodeNo);
            }
            if(kindNode.interceptFlag=="1"){
                htmlTmp = htmlTmp+getNodeInterceptConfigHtml(nodeConfigMap[kindNode.nodeNo]["interceptList"],kindNo,kindNode.nodeNo);
            }

            var subNodeHtml = '<div id="nodeConfig'+kindNo+kindNode.nodeNo+ '" class="sub-content-div">'
                +'<div class="sub-title margin_bottom_10">'
                +'<span>'+kindNode.nodeName+'</span>'
                +'<a href="javascript:void(0);" onclick="MfKindConfig.kindNodeSet(\''+kindNo+'\',\''+kindNode.kindNodeId+'\',\''+kindNode.nodeNo+'\');" class="padding_left_15 pointer">配置</a>'
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
        if(kindNodeList.length>2){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreBusNode'+kindNo+'">'
                +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                +'</div>';
            moreStr = moreStr+'<div id="moreBusNode'+kindNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        htmlStr = htmlStr + moreStr;

        return htmlStr;
    };
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
                    window.top.alert(data.msg,1);
                }
            },error:function(){
                alert(top.getMessage("FAILED_UPDATE"),0);
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


    var _getEventObj = function(obj){
        eventObj = obj;
    };

    //文档模型选择后 回调函数
    function docTypeSelCall(doc) {
        var $itemDiv = $(eventObj).parents(".item-div");
        var $scrollDiv = $(eventObj).parents(".item-div").find(".scroll-div");

        $scrollDiv.find(".block-item").remove();
        var docNo = doc.docNo.split("@");
        var docName = doc.docName.split("@");
        var docType = doc.docType.split("@");
        var formNo = doc.formNo.split("@");

        var kindNo = $itemDiv.data("kindno");
        var scNo = $itemDiv.data("scno");
        var dataParmList = [];
        $.each(docName, function(i, docThis) {
            if (docNo[i]) {
                var entity = {};
                entity.dime1 = kindNo;
                entity.scNo = scNo;
                entity.docType = docType[i];
                entity.docSplitNo = docNo[i];
                entity.formId = formNo[i];
                entity.docName = docName[i];
                dataParmList.push(entity);
            }
        });

        $.ajax({
            url : webPath+"/docBizSceConfig/insertDocsAjax",
            data:{ajaxData : JSON.stringify(dataParmList)},
            type:'post',
            dataType:'json',
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag == "success"){
                    var docList = data.docBizSceConfigList;
                    var htmlStr = getNodeDocContentHtml(docList,kindNo,scNo);
                    $itemDiv.find(".add-item").before(htmlStr);
                    $scrollDiv.mCustomScrollbar("update");
                    if($itemDiv.find(".mCSB_scrollTools").is(":visible")){
                        $itemDiv.find(".add-item").css("display","none");
                        $itemDiv.find(".fixed-add-div").css("display","block");
                    }else{
                        $itemDiv.find(".add-item").css("display","inline-block");
                        $itemDiv.find(".fixed-add-div").css("display","none");
                    }
                    $scrollDiv.mCustomScrollbar("update");
                }
            },error:function(){
                alert(top.getMessage("ERROR"),0);
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };

    var scrollUpdate = function(){
        $(".scroll-div").each(function(index){
            var $thisScroll = $(this).find(".mCSB_scrollTools");
            if($thisScroll.is(":visible")){
                $(this).find(".add-item").hide();
                $(this).parents(".item-div").find(".fixed-add-div").css("display","inline-block");
            }
        });
        $(".scroll-div").mCustomScrollbar("update");
    };

    //节点模板设置html
    var getNodeTemplateConfigHtml = function(templateList,kindNo,nodeNo){
        var templateContentHtml = getNodeTemplateContentHtml(templateList,kindNo,nodeNo);
        var htmlStr = "";
        htmlStr = htmlStr+'<div id="templateConfig'+kindNo+nodeNo+'" data-node="'+nodeNo+'" class="sub-content-div padding_left_15 node-template-config  ">'
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
            //alert(templateConfig.templateSuffix);
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
            +'<div class="add-div" onclick="MfKindConfig.addNodeTemplate(\''+kindNo+'\',\''+nodeNo+'\');">'
            +'<i class="i i-jia2"></i>'
            +'</div>'
            +'</div>'
            +'</div>'
            +'<div class="fixed-add-div">'
            +'<div  class="add-div" onclick="MfKindConfig.addNodeTemplate(\''+kindNo+'\',\''+nodeNo+'\');">'
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
                    $("#templateConfig"+kindNo+nodeNo).find(".sub-content").html(contentHtml);
                    //处理滚动条
                    var $scrollDiv = $("#templateConfig"+kindNo+nodeNo).find(".scroll-div");
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


    //节点费用设置
    var getNodeFeeConfigHtml = function(feeItemList,kindNo,nodeNo){
        //内容
        var contentHtml = getNodeFeeItemContentHtml(feeItemList,kindNo,nodeNo);

        var htmlStr ='<div id="feeConfig'+kindNo+nodeNo+'" class="sub-content-div padding_left_15 fee-config">'
            +'<div class="sub-title"><span>费用项设置</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.addNodeFeeItem(\''+kindNo+'\',\''+nodeNo+'\');" class="padding_left_15 pointer">配置</a>'
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
            moreStr = moreStr+'<div id="morefeeItem'+kindNo+nodeNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
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
            spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+' '+kindNodeFee.ext6+'固定金额(分摊)'+kindNodeFee.ext3+'元，操作权限['+kindNodeFee.optPower+']</span>';
        }
        if(takeType=="3"){
            spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+' '+kindNodeFee.ext6+'固定金额(额定)'+kindNodeFee.ext3+'元，操作权限['+kindNodeFee.optPower+']</span>';
        }

        if(takeType=="4"){
            spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+' '+kindNodeFee.ext6+'利息差额'+'，操作权限['+kindNodeFee.optPower+']</span>';
        }
        htmlStr =htmlStr +'<div class="item-div">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="span-title">'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.editNodeFeeItem(\''+kindNodeFee.kindNodeFeeId+'\',\''+kindNodeFee.kindNo+'\',\''+kindNodeFee.nodeNo+'\');">'+kindNodeFee.itemName+'</a>'
            +'<i class="i i-lajitong item-delete" onclick="MfKindConfig.deleteNodeFeeItem(\''+kindNodeFee.kindNodeFeeId+'\',\''+kindNodeFee.kindNo+'\',\''+kindNodeFee.nodeNo+'\');"></i>'
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
                $("#feeConfig"+kindNo+nodeNo+" .sub-content").html(htmlStr);
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
                $("#feeConfig"+kindNo+nodeNo+" .sub-content").html(htmlStr);
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
                        $("#feeConfig"+kindNo+nodeNo+" .sub-content").html(htmlStr);

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

    //拦截项设置
    var  getNodeInterceptConfigHtml = function(interceptList,kindNo,nodeNo){
        var interceptContentHtml = getNodeInterceptContentHtml(interceptList,kindNo,nodeNo);
        var htmlStr = "";
        htmlStr = htmlStr+'<div id="interceptConfig'+kindNo+nodeNo+'" data-node="'+nodeNo+'" class="sub-content-div padding_left_15 node-intercept-config  ">'
            +'<div class="sub-title margin_bottom_10 margin_top_10">'
            +'<span>拦截项设置</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.addNodeIntercept(\''+kindNo+'\',\''+nodeNo+'\',5);" class="padding_left_15 pointer">准入</a>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.addNodeIntercept(\''+kindNo+'\',\''+nodeNo+'\',1);" class="padding_left_15 pointer">拦截</a>'
            +'</div>'
            +'<div class="sub-content margin_bottom_10 padding_left_15">'
            + interceptContentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    var getNodeInterceptContentHtml = function(interceptList,kindNo,nodeNo){
        //循环产品下节点的拦截项
        var subHtmlStr ="";
        var moreHtml = "";
        $.each(interceptList,function(i,kindNodeIntercept){
            var htmlTmp = getNodeInterceptItemHtml(kindNodeIntercept);
            if(i<countDef){
                subHtmlStr = subHtmlStr + htmlTmp;
            }else{
                moreHtml = moreHtml + htmlTmp;
            }
        });
        var moreStr = "";
        var optStr = "";
        if(interceptList.length>countDef){
            optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreInterceptItem'+kindNo+nodeNo+'">'
                +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
                +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
                +'</div>';
            moreStr = moreStr+'<div id="moreInterceptItem'+kindNo+nodeNo+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
        }
        var htmlStr = subHtmlStr+moreStr;
        return htmlStr;
    };


    /**产品下拦截项html**/
    var getNodeInterceptItemHtml = function(kindNodeIntercept){
        var htmlStr ="";
        var typeStr = "拦截项";
        htmlStr =htmlStr +'<div class="item-div">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="span-title">'
            +'<span>['+typeStr+']'+kindNodeIntercept.itemName+'</span>'
            +'<i class="i i-lajitong item-delete" onclick="MfKindConfig.deleteNodeIntercept(\''+kindNodeIntercept.kindNo+'\',\''+kindNodeIntercept.nodeNo+'\',\''+kindNodeIntercept.kindNodeInterceptId+'\');"></i>'
            +'</span>'
            +'<span class="padding_left_10">'+kindNodeIntercept.itemDesc+'</span>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    var _addNodeIntercept = function(kindNo,nodeNo,itemType){
        top.flag=false;
        top.itemNos="";
        var title = '5'==itemType ? "选择准入项" : "选择拦截项";
        window.parent.openBigForm(webPath+"/mfKindNodeIntercept/getRiskInterceptList?kindNo="+kindNo+"&nodeNo="+nodeNo, title, function(){
            if(top.flag){
                $.ajax({
                    url:webPath+"/mfKindNodeIntercept/insertAjax",
                    type:'post',
                    data:{kindNo:kindNo,nodeNo:nodeNo,ajaxData:top.itemNos},
                    beforeSend:function(){
                        LoadingAnimate.start();
                    },success:function(data){
                        if(data.flag=="success"){
                            var htmlStr =  getNodeInterceptContentHtml(data.interceptList,kindNo,nodeNo);
                            $("#interceptConfig"+kindNo+nodeNo).find(".sub-content").html(htmlStr);
                        }
                    },error:function(){
                        alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    },complete: function(){
                        LoadingAnimate.stop();
                    }
                });
                var htmlStr ="";

                $("#interceptConfig"+kindNo+nodeNo+" .sub-content").html(htmlStr);
            }
        });
    };

    var _deleteNodeIntercept = function(kindNo,nodeNo,kindNodeInterceptId){
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url:webPath+"/mfKindNodeIntercept/deleteAjax",
                type:'post',
                data:{kindNo:kindNo,nodeNo:nodeNo,kindNodeInterceptId:kindNodeInterceptId},
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag=="success"){
                        var htmlStr =  getNodeInterceptContentHtml(data.interceptList,kindNo,nodeNo);
                        $("#interceptConfig"+kindNo+nodeNo).find(".sub-content").html(htmlStr);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        });
    };
    /****************************************业务设置（进件节点、业务节点设置）end***************************************/


    /****************************************流程设置（业务审批、合同审批、放款审批流程设置）begin******************************/
    var getApprovalWkfConfigHtml = function(flowList,kindNo){
        //内容
        var contentHtml = getApprovalWkfContentHtml(flowList);

        var htmlStr='<div id="wkfConfig'+kindNo+'" class="sub-content-div padding_left_15">'
            +'<div class="sub-title"><span>流程设置</span></div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    var getApprovalWkfContentHtml = function(flowList){
        var htmlStr="";
        $.each(flowList,function(i,kindFlow){
            var checkspan = '<span class="checkbox-span curChecked" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfKindConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'1\');"><i class="i i-gouxuan1"></i></span>';
            var alinkStr ='<a href="javascript:void(0);" onclick="MfKindConfig.openProcessDesigner(\''+kindFlow.flowId+'\')" class="padding_left_15 pointer">配置</a>';

            if(kindFlow.useFlag=="0"){
                checkspan='<span class="checkbox-span" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfKindConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'0\');"><i class="i i-gouxuan1"></i></span>';
                alinkStr ='<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>';
            }
            htmlStr =htmlStr +'<div class="item-div">'
                +'<div class="item-title  margin_bottom_10">'
                +'<span class="item-checkbox">'
                + checkspan
                +'<span>启用'+kindFlow.flowApprovalName+'</span>'
                +'</span>'
                +'</div>'
                +'<div class="item-content margin_bottom_15 padding_left_15">'
                +'<div id="processItem'+kindFlow.kindFlowId+'" class="padding_left_15">'
                +'当前流程：<span id="'+kindFlow.flowApprovalNo+kindFlow.kindNo+'">'+kindFlow.flowRemark+'</span>'
                +alinkStr
                +'</div>'
                +'</div>'
                +'</div>';
        });

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
                    window.open(webPath+"/tech/wkf/modelerEditor.jsp?command=DesignProcess&objectId="+data.workflowId);
                }
            },error:function(){
                //alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
    //流程设计表单
    var _openDesignerForm=function(wkfId){
        $.ajax({
            url:webPath+"/mfSysKind/getWorkflowIdAjax",
            type:'post',
            data:{appWorkflowNo:wkfId},
            async:false,
            success:function(data){
                if(data.flag=="success"){
                    window.open(webPath+"/tech/wkf/modelerEditor.jsp?command=DesignProcess&objectId="+data.workflowId);
                }
            },error:function(){
                //alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    /****************************************流程设置（业务审批、合同审批、放款审批设置）end***********************************/


    /****************************************核算设置（基本设置和高级设置）begin******************************/

        //产品设置修改
    var updateKindCalcConfig = function(ajaxData,updateCallBack){
            $.ajax({
                url:webPath+"/mfSysKind/updateKindCalcConfigAjax",
                type:'post',
                data:{ajaxData:ajaxData},
                async:false,
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag="success"){
                        if(updateCallBack!==undefined&&typeof(updateCallBack) == "function"){
                            updateCallBack(data);
                        }
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },complete: function(){
                    LoadingAnimate.stop();
                }
            });
        };



    //利率类型
    var getRateTypeHtml = function(mfSysKind){
        var nameStr="";
        var rateTypeShow="";
        var inputStr="";
        var marginStr="";
        $.each(rateTypeList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.rateType==parmDic.optCode){
                rateTypeShow = parmDic.remark;
                nameStr = parmDic.optName;
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" checked="checked" name="rateType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio"  name="rateType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }

        });
        var htmlStr=""
        //获取
        if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
            htmlStr = '<div class="item-div rateType" data-kindno="'+mfSysKind.kindNo+'">'
                +'<div class="item-title margin_bottom_10">'
                +'<span class="color_black">利率类型:</span>'
                +'<span class="padding_left_15">'+mfSysKind.kindName+'的融资利率使用<span id="rateType'+mfSysKind.kindNo+'" >'+nameStr+'</span>格式，默认利率为<span class="fincRate'+mfSysKind.kindNo+'">'+mfSysKind.fincRate+'</span><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
                +'<span id="rateTypeShowName'+mfSysKind.kindNo+'" >,默认逾期利率上浮为</span><span class="overFltRateDef'+mfSysKind.kindNo+'">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName'+mfSysKind.kindNo+'Show">%</span>'
                +'</span>'
                +'</div>'
                + '<div class="item-content margin_bottom_20 padding_left_15">'
                + '<span>'
                + inputStr
                + '</span>'
                + '</div>'
                + '<div class="item-content margin_bottom_20 padding_left_15">'
                + '<span class="color_black">融资利率上限：<input type="text" class="maxFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.maxFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '<span class="color_black padding_left_20">融资利率下限：<input type="text" class="minFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.minFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '<span class="color_black padding_left_20">默认利率：<input type="text" class="fincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.fincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '<span class="hide" id="maxFincRateSpan'+mfSysKind.kindNo+'">'+mfSysKind.maxFincRate+'</span><span class="hide" id="minFincRateSpan'+mfSysKind.kindNo+'">'+mfSysKind.minFincRate+'</span>'
                + '</div>'
                + '<div class="item-content margin_bottom_20 padding_left_15">'
                + '<span class="color_black" id="overFlotRateDef'+mfSysKind.kindNo+'" ><span id="overFlotRateDefName'+mfSysKind.kindNo+'">默认逾期利率上浮：</span><input type="text" class="overFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.overFltRateDef+'"/><span class="overFltRateDef'+mfSysKind.kindNo+'Show">%</span></span>'
                + '</div>'
                + '</div>';
        }else{//默认利率
            htmlStr = '<div class="item-div rateType" data-kindno="'+mfSysKind.kindNo+'">'
                +'<div class="item-title margin_bottom_10">'
                +'<span class="color_black">利率类型:</span>'
                +'<span class="padding_left_15">'+mfSysKind.kindName+'的融资利率使用<span id="rateType'+mfSysKind.kindNo+'" >'+nameStr+'</span>格式，默认利率为<span class="fincRate'+mfSysKind.kindNo+'">'+mfSysKind.fincRate+'</span><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
                +'<span id="rateTypeShowName'+mfSysKind.kindNo+'" >,默认逾期利率为</span><span class="overFltRateDef'+mfSysKind.kindNo+'">'+mfSysKind.overFltRateDef+'</span><span id="overFltRateDefShowName'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span>'
                +'</span>'
                +'</div>'
                + '<div class="item-content margin_bottom_20 padding_left_15">'
                + '<span>'
                + inputStr
                + '</span>'
                + '</div>'
                + '<div class="item-content margin_bottom_20">'
                + '<span class="color_black ">融资利率上限：<input type="text" class="maxFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.maxFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '<span class="color_black padding_left_20">融资利率下限：<input type="text" class="minFincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.minFincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '<span class="color_black padding_left_20">默认利率：<input type="text" class="fincRate'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.fincRate+'"/><span class="rateType'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '</div>'
                + '<div class="item-content margin_bottom_20">'
                + '<span class="color_black "><span id="overFlotRateDefName'+mfSysKind.kindNo+'">默认逾期利率：</span><input type="text" class="overFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.overFltRateDef+'"/><span class="overFltRateDef'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                + '</div>'
                + '</div>';
        }
        return htmlStr;
    };
    //利率类型绑定事件
    var rateTypeBindEvent = function(){
        $(".rateType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            var rateTypeShow;
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.rateType=$(this).val();
            if(mfSysKind.rateType=="1"){
                rateTypeShow = "%";
            }else if(mfSysKind.rateType=="2"){
                rateTypeShow = "‰";
            }else if(mfSysKind.rateType=="3"){
                rateTypeShow = "‱";
            }else if(mfSysKind.rateType=="4"){
                rateTypeShow = "%";
            }else if(mfSysKind.rateType=="5"){
                rateTypeShow = "%";
            }
            mfSysKind.fincRate=0.00;
            mfSysKind.minFincRate=0.00;
            mfSysKind.maxFincRate=0.00;
            var ajaxData = JSON.stringify(mfSysKind);
            top.rateTypeName=$(this).data("name");
            top.kindNo =mfSysKind.kindNo;
            mfSysKind.overCmpdRateInputtype=$("input[name='overCmpdRateInputtype"+mfSysKind.kindNo+"']:checked").val();
            updateKindCalcConfig(ajaxData,function(data){
                $(".rateType"+top.kindNo+"Show").text(rateTypeShow);
                $("#rateType"+top.kindNo).text(top.rateTypeName);
                $(".fincRate"+top.kindNo).text(0.00);
                $(".fincRate"+top.kindNo).val(0.00);
                $(".minFincRate"+top.kindNo).val(0.00);
                $(".maxFincRate"+top.kindNo).val(0.00);
                $("#minFincRateSpan"+top.kindNo).text(0.00);
                $("#maxFincRateSpan"+top.kindNo).text(0.00);
                if(mfSysKind.overCmpdRateInputtype=="1"){//利率
                    $(".overFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
                    $("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
                    $("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
                    //复利利率
                    $(".cmpFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
                }else{//利率浮动
                    $(".overFltRateDef"+top.kindNo+"Show").text("%");
                    $("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
                    $("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
                    $(".overFltRateDef"+top.kindNo+"Show").text("%");
                    //复利利率
                    $(".cmpFltRateDef"+top.kindNo+"Show").text("%");
                }
                //逾期利率上浮
                $(".overFltRateDef"+top.kindNo).text(0.00);
                $(".overFltRateDef"+top.kindNo).val(0.00);
            });
        });

        $(".rateType input[type=text]").bind("change",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            var classAttr=$(this).attr("class");
            var oldFincRate = $("span.fincRate"+mfSysKind.kindNo).text();
            var oldMinFinsRate = $("#minFincRateSpan"+mfSysKind.kindNo).text();
            var oldMaxFinsRate = $("#maxFincRateSpan"+mfSysKind.kindNo).text();
            var maxFincRate,minFincRate,fincRate;
            if(classAttr=="fincRate"+mfSysKind.kindNo){//贷款利率
                mfSysKind.fincRate=$(this).val();
                maxFincRate = $(".maxFincRate"+mfSysKind.kindNo).val();
                minFincRate = $(".minFincRate"+mfSysKind.kindNo).val();
                if ($(this).val() * 1 > maxFincRate*1) {//默认融资利率 不能大于贷款利率上限
                    alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : '默认融资利率',
                        "timeTwo" : '融资利率上限'
                    }), 0);
                    $(this).val(oldFincRate);
                    return false;
                }
                if ($(this).val() * 1 < minFincRate*1) {//默认融资利率 不能小于贷款利率下限
                    alert(top.getMessage("NOT_SMALL_TIME", {
                        "timeOne" : '默认融资利率',
                        "timeTwo" : '融资利率下限'
                    }), 0);
                    $(this).val(oldFincRate);
                    return false;
                }

            }else if(classAttr=="overFltRateDef"+mfSysKind.kindNo){//逾期利率上浮
                mfSysKind.overFltRateDef=$(this).val();
            }else if(classAttr=="minFincRate"+mfSysKind.kindNo){//贷款利率下限
                mfSysKind.minFincRate=$(this).val();
                minFincRate = $(this).val();
                maxFincRate = $(".maxFincRate"+mfSysKind.kindNo).val();
                fincRate  = $("input.fincRate"+mfSysKind.kindNo).val();
                if ($(this).val() * 1 > maxFincRate*1) {//贷款利率下限 不能大于贷款利率上限
                    alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : '融资利率下限',
                        "timeTwo" : '融资利率上限'
                    }), 0);
                    $(this).val(oldMinFinsRate);
                    return false;
                }
                if (fincRate * 1 < minFincRate*1) {//默认融资利率 不能小于贷款利率下限
                    alert(top.getMessage("NOT_SMALL_TIME", {
                        "timeOne" : '默认融资利率',
                        "timeTwo" : '融资利率下限'
                    }), 0);
                    $(this).val(oldMinFinsRate);
                    return false;
                }

            }else if(classAttr=="maxFincRate"+mfSysKind.kindNo){//贷款利率上限
                mfSysKind.maxFincRate=$(this).val();
                maxFincRate = $(this).val();
                minFincRate = $(".minFincRate"+mfSysKind.kindNo).val();
                fincRate  = $("input.fincRate"+mfSysKind.kindNo).val();
                if ($(this).val() * 1 < minFincRate*1) {//贷款利率上限 不能小于贷款利率下限
                    alert(top.getMessage("NOT_SMALL_TIME", {
                        "timeOne" : '融资利率上限',
                        "timeTwo" : '融资利率下限'
                    }), 0);
                    $(this).val(oldMaxFinsRate);
                    return false;
                }
                if (fincRate * 1 > maxFincRate*1) {//默认融资利率 不能大于贷款利率上限
                    alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : '默认融资利率',
                        "timeTwo" : '融资利率上限'
                    }), 0);
                    $(this).val(oldMaxFinsRate);
                    return false;
                }

            }
            mfSysKind.rateType=$("input[name='rateType"+mfSysKind.kindNo+"']:checked").val();
            var ajaxData = JSON.stringify(mfSysKind);
            top.kindNo =mfSysKind.kindNo;
            updateKindCalcConfig(ajaxData,function(data){
                var mfSysKindTmp = data.mfSysKind;
                $(".fincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.fincRate);
                $(".overFltRateDef"+mfSysKindTmp.kindNo).text(mfSysKindTmp.overFltRateDef);
                $(".minFincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.minFincRate);
                $(".maxFincRate"+mfSysKindTmp.kindNo).text(mfSysKindTmp.maxFincRate);
                $("#minFincRateSpan"+mfSysKindTmp.kindNo).text(mfSysKindTmp.minFincRate);
                $("#maxFincRateSpan"+mfSysKindTmp.kindNo).text(mfSysKindTmp.maxFincRate);
            });
        });
    };


    //还款方式
    var getRepayTypeHtml = function(mfSysKind){
        var repayTypeList =repayTypeMap[mfSysKind.kindNo];
        var subStr = "";
        var repayTypeTip="";
        var repayTypeDefTip="";
        $.each(repayTypeList,function(i,mapObj){
            repayTypeTip=repayTypeTip+mapObj.repayTypeName+"、";
            if(mapObj.repayTypeDef=="0"){
                subStr=subStr+'<span class="option-div" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
            }else{//默认还款方式
                subStr=subStr+'<span class="option-div selected" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
                repayTypeDefTip=mapObj.repayTypeName;
            }
        });
        repayTypeTip = repayTypeTip.substring(0, repayTypeTip.length-1);
        //还款方式
        var htmlStr ='<div class="item-div repayType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">还款方式：</span>'
            +'<span class="padding_left_15">'+mfSysKind.kindName+'支持<span id="repayTypeTip'+mfSysKind.kindNo+'">'+repayTypeTip+'</span>，其中默认还款方式为：<span id="repayTypeDefTip'+mfSysKind.kindNo+'">'+repayTypeDefTip+'</span></span>'
            +'</div>'
            +'<div class="item-content padding_left_15">'
            +'<button class="btn btn-default btn-add">添加</button>'
            +'<span id="repayTypeOption'+mfSysKind.kindNo+'">'
            + subStr
            +'</span>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    //还款方式绑定事件
    var repayTypeBindEvent = function(){
        $(".repayType .btn-add").bind("click",function(){
            var kindNo = $(this).parents(".item-div").data("kindno");
            top.flag=false;
            top.itemId="";
            top.kindNo=kindNo;
            window.parent.openBigForm(webPath+"/mfSysKind/getRepayTypeForSelect?kindNo="+kindNo,"选择还款方式",function(){
                if(top.flag){
                    var itemId = top.itemId;
                    var mfSysKind={};
                    mfSysKind.kindNo=kindNo;
                    mfSysKind.repayType=itemId;
                    var ajaxData = JSON.stringify(mfSysKind);
                    updateKindCalcConfig(ajaxData,function(data){
                        var subStr = "";
                        var repayTypeTip="";
                        var repayTypeDefTip="";
                        $.each(data.repayTypeList,function(i,mapObj){
                            repayTypeTip=repayTypeTip+mapObj.repayTypeName+"、";
                            if(mapObj.repayTypeDef=="0"){
                                subStr=subStr+'<span class="option-div" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
                            }else{//默认还款方式
                                subStr=subStr+'<span class="option-div selected" data-repaytype="'+mapObj.repayType+'" data-name="'+mapObj.repayTypeName+'"><span>'+mapObj.repayTypeName+'</span><i class="i i-sanjiaoduihao"></i></span>';
                                repayTypeDefTip=mapObj.repayTypeName;
                            }
                        });
                        repayTypeTip = repayTypeTip.substring(0, repayTypeTip.length-1);
                        $("#repayTypeOption"+data.mfSysKind.kindNo).html(subStr);

                        $("#repayTypeTip"+kindNo).text(repayTypeTip);
                        $("#repayTypeDefTip"+kindNo).text(repayTypeDefTip);

                        $("#repayTypeOption"+data.mfSysKind.kindNo+" .option-div").bind("click",function(){
                            var $thisItemDiv = $(this).parents(".item-div");
                            $thisItemDiv.find(".option-div").removeClass("selected");
                            $(this).addClass("selected");
                            var mfSysKind={};
                            mfSysKind.kindNo=$thisItemDiv.data("kindno");
                            mfSysKind.repayTypeDef=$(this).data("repaytype");
                            var ajaxData = JSON.stringify(mfSysKind);
                            top.repayTypeDef=$(this).data("name");
                            top.kindNo=mfSysKind.kindNo;
                            updateKindCalcConfig(ajaxData,function(){
                                $("#repayTypeDefTip"+top.kindNo).text(top.repayTypeDef);
                            });
                        });
                    });
                }
            },"790px","450px");
        });

        $(".repayType .option-div").bind("click",function(){
            var $thisItemDiv = $(this).parents(".item-div");
            $thisItemDiv.find(".option-div").removeClass("selected");
            $(this).addClass("selected");
            var mfSysKind={};
            mfSysKind.kindNo=$thisItemDiv.data("kindno");
            mfSysKind.repayTypeDef=$(this).data("repaytype");
            var ajaxData = JSON.stringify(mfSysKind);

            top.repayTypeDef=$(this).data("name");
            top.kindNo=mfSysKind.kindNo;
            updateKindCalcConfig(ajaxData,function(){
                $("#repayTypeDefTip"+top.kindNo).text(top.repayTypeDef);
            });
        });


    };


    //利息计算方式
    var getNormCalcTypeHtml = function(mfSysKind){
        var checkStr2="";
        var checkStr3="";
        if(mfSysKind.normCalcType=="2"){
            checkStr2='checked="checked"';
        }else if(mfSysKind.normCalcType=="3"){
            checkStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div calcType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">利息计算方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="normCalcType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>按月计息'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="normCalcType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>按日计息'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //利息计算方式绑定事件
    var normCalcTypeBindEvent = function(){
        $(".calcType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.normCalcType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            top.normCalcType = $(this).val();
            updateKindCalcConfig(ajaxData,function(data){
                var kindNo = data.mfSysKind.kindNo;
                var normCalcType = top.normCalcType;
                if(normCalcType=="3"){
                    $("#calcConfig"+kindNo+" .secondCalcType").removeClass("show");
                    $("#calcConfig"+kindNo+" .secondCalcType").addClass("hide");

                }else{
                    $("#calcConfig"+kindNo+" .secondCalcType").removeClass("hide");
                    $("#calcConfig"+kindNo+" .secondCalcType").addClass("show");
                }
            });
        });
    };

    //按月结息时不足一月利息计算方式second_norm_calc_type
    var getSecondNormCalcTypeHtml = function(mfSysKind){
        var checkStr2="";
        var checkStr3="";
        var showOrHide="show";
        if(mfSysKind.normCalcType=="3"){//如果是按月计息，不足一月按月计息
            showOrHide="hide";
        }
        if(mfSysKind.secondNormCalcType=="2"){
            checkStr2='checked="checked"';
        }else if(mfSysKind.secondNormCalcType=="3"){
            checkStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div secondCalcType '+showOrHide+'" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">不足一月利息计算方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="secondCalcType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>按月计息'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="secondCalcType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>按日计息'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //按月结息时不足一月利息计算方式绑定事件
    var secondNormCalcTypeBindEvent = function(){
        $(".secondCalcType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.secondNormCalcType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };
    //利息计算基数
    var getInstCalcBaseHtml = function(mfSysKind){
        var checkStr1="";
        var checkStr2="";
        if(mfSysKind.instCalcBase=="1"){
            checkStr1='checked="checked"';
        }else if(mfSysKind.instCalcBase=="2"){
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div instCalcBase" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">利息计算基数：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="instCalcBase'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>贷款金额'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="instCalcBase'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>贷款余额'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //利息计算基数绑定事件
    var instCalcBaseBindEvent = function(){
        $(".instCalcBase input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.instCalcBase=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };


    //利息减免
    var getInterestDerateHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.interestDerateFlag=="0"){
            checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div instDerate" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>启用利息减免：</span>'
            +'<span class="padding_left_15" style="color:#666;">还款时，利息支持减免优惠，包括正常利息和罚息</span>'
            +'</span>'
            +'</div>'
            + '</div>';
        return htmlStr;
    };
    //利息减免绑定点击事件
    var interestDerateBindEvent = function(){
        $(".instDerate .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");

            if($(this).hasClass("curChecked")){//禁用
                mfSysKind.interestDerateFlag="0";
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.interestDerateFlag="1";
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式
    var getFestivalTypeHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.festivalType=="0"){
            checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div festivalType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>节假日（周末/宽限天）利息收取方式：</span>'
            +'<span class="padding_left_15" style="color:#666;">还款日与法定节假日（周末）重合时或在宽限期内逾期后逾期利息、复利不收取</span>'
            +'</span>'
            +'</div>'
            + '</div>';
        return htmlStr;
    };
    //还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式绑定点击事件
    var festivalTypeBindEvent = function(){
        $(".festivalType .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).hasClass("curChecked")){
                mfSysKind.festivalType="1";	//收取
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.festivalType="0";//不收取
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };
    //允许最后一期结余：0-不允许、1-允许  getLastTermBalanceTypeHtml
    var getLastTermBalanceTypeHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.lastTermBalanceType=="1"){
            checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div lastTermBalanceType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>允许最后一期结余：</span>'
            +'<span class="padding_left_15" style="color:#666;">还款时允许最后一期还款存在结余</span>'
            +'</span>'
            +'</div>'
            + '</div>';
        return htmlStr;
    };
    //允许最后一期结余：0-不允许、1-允许绑定点击事件 lastTermBalanceTypeBindEvent
    var lastTermBalanceTypeBindEvent = function(){
        $(".lastTermBalanceType .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).hasClass("curChecked")){
                mfSysKind.lastTermBalanceType="0";	//允许
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.lastTermBalanceType="1";//不允许
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };
    //====



    //还款日设置
    var getRepayDateSetHtml = function(mfSysKind){
        var checkStr1="",checkStr2="",checkStr3="",showOrHide='style="display:none;"';
        var repayDateSetTip="";
        if(mfSysKind.repayDateSet=="1"){
            checkStr1='checked="checked"';
            repayDateSetTip="在贷款发放日";
        }else if(mfSysKind.repayDateSet=="2"){
            checkStr2='checked="checked"';
            repayDateSetTip="在月末";
        }else if(mfSysKind.repayDateSet=="3"){
            checkStr3='checked="checked"';
            showOrHide = 'style="display:inline-block;"';
            repayDateSetTip='固定在每月的<span id=repayDateDefSpan'+mfSysKind.kindNo+'>'+mfSysKind.repayDateDef+'</span>日';
        }
        var htmlStr = '<div class="item-div repayDate" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">还款日设置：</span>'
            +'<span class="padding_left_15">'+mfSysKind.kindName+'<span id="repayDateSetTip'+mfSysKind.kindNo+'">'+repayDateSetTip+'</span>还款</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="repayDateSet'+mfSysKind.kindNo+'" value="1" '+checkStr1+' data-name="贷款发放日"/>贷款发放日'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="repayDateSet'+mfSysKind.kindNo+'" value="2" '+checkStr2+' data-name="月末"/>月末'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="repayDateSet'+mfSysKind.kindNo+'" value="3" '+checkStr3+' data-name="固定还款日"/>固定还款日'
            + '</span>'
            +'<span id="repayDateDef'+mfSysKind.kindNo+'" '+showOrHide+' class="padding_left_15 color_black">默认还款日：<input type="text" name="repayDateDef'+mfSysKind.kindNo+'" value="'+mfSysKind.repayDateDef+'"/></span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //还款日设置绑定事件
    var repayDateBindEvent = function(){
        $(".repayDate input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.repayDateSet=$(this).val();
            mfSysKind.repayDateDef="";
            var ajaxData = JSON.stringify(mfSysKind);
            top.repayDateSetName=$(this).data("name");
            updateKindCalcConfig(ajaxData,function(data){
                var kindObj = data.mfSysKind;
                if(kindObj.repayDateSet=="3"){
                    $("#repayDateDef"+kindObj.kindNo).css("display","inline-block");
                    var repayDateDefTmp = "";
                    if(kindObj.repayDateDef!=null){
                        repayDateDefTmp=kindObj.repayDateDef;
                    }
                    $("#repayDateSetTip"+kindObj.kindNo).html('固定在每月的<span id=repayDateDefSpan'+kindObj.kindNo+'>'+repayDateDefTmp+'</span>日');
                }else{
                    $("#repayDateDef"+kindObj.kindNo).css("display","none");
                    $("#repayDateSetTip"+kindObj.kindNo).text("在"+top.repayDateSetName);
                }
            });
        });
        $(".repayDate input[type=text]").bind("change",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.repayDateDef=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            top.kindNo = mfSysKind.kindNo;
            top.repayDateDef = $(this).val();
            updateKindCalcConfig(ajaxData,function(){
                $("#repayDateDefSpan"+top.kindNo).text(top.repayDateDef);
            });
        });


    };

    //还款时利随本清的利息收取
    var getLsbqChargeIntstHtml = function(mfSysKind){
        var checkStr1="",checkStr2="",checkStr3="",showOrHide='style="display:none;"';
        var lsbqChargeIntstTip="";
        if(mfSysKind.lsbqChargeIntst=="1"){
            checkStr1='checked="checked"';
            lsbqChargeIntstTip="在还款时分次收取部分利息";
        }else if(mfSysKind.lsbqChargeIntst=="2"){
            checkStr2='checked="checked"';
            lsbqChargeIntstTip="在还款时一次收取全部利息";
        }else if(mfSysKind.lsbqChargeIntst=="3"){
            checkStr3='checked="checked"';
            lsbqChargeIntstTip="按还款本金收取利息";
        }

        var htmlStr = '<div class="item-div lsbqChargeInts" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">利随本清的利息收取：</span>'
            +'<span class="padding_left_15">'+mfSysKind.kindName+'<span id="lsbqChargeIntstTip'+mfSysKind.kindNo+'">'+lsbqChargeIntstTip+'</span></span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="lsbqChargeIntst'+mfSysKind.kindNo+'" value="1" '+checkStr1+' data-name="分次收取部分利息"/><span id="lsbqChargeIntst_span'+mfSysKind.kindNo+'1">分次收取部分利息</span>'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="lsbqChargeIntst'+mfSysKind.kindNo+'" value="2" '+checkStr2+' data-name="一次收取全部利息"/><span id="lsbqChargeIntst_span'+mfSysKind.kindNo+'2">一次收取全部利息</span>'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="lsbqChargeIntst'+mfSysKind.kindNo+'" value="3" '+checkStr3+' data-name="按还款本金收取利息"/><span id="lsbqChargeIntst_span'+mfSysKind.kindNo+'3">按还款本金收取利息</span>'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    var lsbqChargeIntstBindEvent = function(){
        $(".lsbqChargeInts input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.lsbqChargeIntst=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData,function(data){
                var kindObj = data.mfSysKind;
                $("#lsbqChargeIntstTip"+mfSysKind.kindNo).text("在还款时"+$("#lsbqChargeIntst_span"+mfSysKind.kindNo+kindObj.lsbqChargeIntst).text());
            });
        });
    };

    //利息收息方式
    var getInstCollectTypeHtml = function(mfSysKind){
        var inputStr="";
        var marginStr="";
        $.each(instCollectTypeList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.interestCollectType==parmDic.optCode){
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" checked="checked" name="interestCollectType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio"  name="interestCollectType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }

        });
        var htmlStr = '<div class="item-div instCollect" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">利息收息方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + inputStr
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var instCollectTypeBindEvent = function(){
        $(".instCollect input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.interestCollectType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };
    //费用收取方式
    var getFeeCollectWayHtml = function(mfSysKind){
        var inputStr="";
        var marginStr="";
        $.each(feeCollectWayList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.feeCollectWay==parmDic.optCode){
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" checked="checked" name="feeCollectWay'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio"  name="feeCollectWay'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }

        });
        var htmlStr = '<div class="item-div feeCollectWay" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">费用收取方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + inputStr
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var feeCollectWayBindEvent = function(){
        $(".feeCollectWay input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.feeCollectWay=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };



    //预先支付利息收取方式
    var getPreInstCollectTypeHtml = function(mfSysKind){
        var checkStr1 = "",checkStr2 = "",checkStr3 = "";
        if(mfSysKind.preInstCollectType=="1"){
            checkStr1='checked="checked"';
        }else if(mfSysKind.preInstCollectType=="2"){
            checkStr2='checked="checked"';
        }else if(mfSysKind.preInstCollectType=="3"){
            checkStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div preInstCollect" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">预先支付利息收取方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="preInstCollectType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>合并第一期'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="preInstCollectType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>独立一期'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="preInstCollectType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>放款时收取'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var preInstCollectTypeBindEvent = function(){
        $(".preInstCollect input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.preInstCollectType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };


    //提前还款
    var getPreRepayTypeHtml = function(mfSysKind){
        var checkStr1 = "",checkStr2 = "",checkStr3 = "";
        if(mfSysKind.preRepayType=="1"){
            checkStr1='checked="checked"';
        }else if(mfSysKind.preRepayType=="2"){
            checkStr2='checked="checked"';
        }else if(mfSysKind.preRepayType=="3"){
            checkStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div preRepayType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">提前还款本金：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="preRepayType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>偿还部分剩余本金'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="preRepayType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>偿还全部剩余本金'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="preRepayType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>一次性偿还所有未还本金、利息'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    var preRepayTypeBindEvent = function(){
        $(".preRepayType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.preRepayType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };


    //提前还款利息计算
    var getPreRepayInstCalcHtml = function(mfSysKind){
        var preRepayInstCalcTip ="";
        if(mfSysKind.termInstMustBack=="1"){
            preRepayInstCalcTip="必须结清当期本金和利息方可进行提前还款";
        }else{
            if(mfSysKind.preRepayInstAccoutBase=="1"){
                preRepayInstCalcTip="提前还款时按借据余额计算利息";
            }else if(mfSysKind.preRepayInstAccoutBase=="2"){
                preRepayInstCalcTip="提前还款时按提前还款本金计算利息";
            }else if(mfSysKind.preRepayInstAccoutBase=="3"){
                preRepayInstCalcTip="提前还款时只还本金，分段计算利息";
            }
        }
        var inputStr ="";
        var marginStr="";
        var dataIdStr="";
        $.each(preRepayIntsTypeList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(parmDic.optCode=="0"){
                dataIdStr='data-id="termInstMustBack"';
            }else{
                dataIdStr="";
            }
            if(mfSysKind.preRepayInstAccoutBase==parmDic.optCode){
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" '+dataIdStr+' checked="checked" name="preRepayInstAccoutBase'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" '+dataIdStr+' name="preRepayInstAccoutBase'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }
        });
        var htmlStr = '<div class="item-div preRepayInstCalc" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="color_black">提前还款利息：</span>'
            +'<span class="padding_left_15">'+mfSysKind.kindName+'要求<span id="preRepayInstCalcTip'+mfSysKind.kindNo+'">'+preRepayInstCalcTip+'</span></span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            +'<span id="preRepayInstAccoutBase'+mfSysKind.kindNo+'" class="padding_left_10">'
            + inputStr
            +'</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //提前还款利息计算
    var preRepayInstCalcBindEvent = function(){
        $(".preRepayInstCalc input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).data("id")=="termInstMustBack"){
                mfSysKind.termInstMustBack="1";
                mfSysKind.preRepayInstAccoutBase=$(this).val();
                $("#preRepayInstCalcTip"+mfSysKind.kindNo).text("必须结清当期本金和利息方可进行提前还款");
            }else{
                mfSysKind.preRepayInstAccoutBase=$(this).val();
                mfSysKind.termInstMustBack="0";
                $("#preRepayInstCalcTip"+mfSysKind.kindNo).text("提前还款时"+$(this).data("name")+"计算利息");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };




    //一年天数设置
    var getYearDaysHtml = function(mfSysKind){
        var checkStr1="",checkStr2="";
        if(mfSysKind.yearDays=="360"){
            checkStr1='checked="checked"';
        }else if(mfSysKind.yearDays=="365"){
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div yearDays" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">年天数：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="yearDays'+mfSysKind.kindNo+'" value="360" '+checkStr1+'/>360天'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="yearDays'+mfSysKind.kindNo+'" value="365" '+checkStr2+'/>365天'
            +'</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //年天数设置绑定事件
    var yearDaysBindEvent = function(){
        $(".yearDays input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.yearDays=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //利率小数位数设置
    var getRateDecimalDigitsHtml = function(mfSysKind){
        var inputStr="";
        var marginStr="";
        $.each(rateDigitsList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.rateDecimalDigits==parmDic.optCode){
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" checked="checked" name="rateDecimalDigits'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio"  name="rateDecimalDigits'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }
        });

        var htmlStr = '<div class="item-div rateDecimalDigits" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">利率小数位数：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + inputStr
            +'</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //利率小数位数设置绑定事件
    var rateDecimalDigitsBindEvent = function(){
        $(".rateDecimalDigits input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.rateDecimalDigits=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //计息方式
    var getIcTypeHtml = function(mfSysKind){
        var inputStr ="";
        var marginStr ="";
        $.each(icTypeList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.icType==parmDic.optCode){
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio" checked="checked" name="icType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }else{
                inputStr = inputStr +'<input class="margin_right_5 '+marginStr+'" type="radio"  name="icType'+mfSysKind.kindNo+'" value="'+parmDic.optCode+'"  data-name="'+parmDic.optName+'"/>'+parmDic.optName;
            }

        });
        var htmlStr = '<div class="item-div icType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">计息方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + inputStr
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //计息方式绑定事件
    var icTypeBindEvent = function(){
        $(".icType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.icType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };


    //还款计划保留位数
    var getReturnPlanPointHtml = function(mfSysKind){
        var checkStr1="",checkStr2="",checkStr3="";
        if(mfSysKind.returnPlanPoint=="2"){
            checkStr1='checked="checked"';
        }else if(mfSysKind.returnPlanPoint=="1"){
            checkStr2='checked="checked"';
        }else if(mfSysKind.returnPlanPoint=="0"){
            checkStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div returnPlanPoint" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">还款计划保留位数：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="returnPlanPoint'+mfSysKind.kindNo+'" value="2" '+checkStr1+'/>保留两位'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="returnPlanPoint'+mfSysKind.kindNo+'" value="1" '+checkStr2+'/>保留一位'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="returnPlanPoint'+mfSysKind.kindNo+'" value="0" '+checkStr3+'/>不保留'
            + '</span>'
            + '</div>'
            + '</div>';

        return htmlStr;
    };
    //还款计划保留位数绑定事件
    var returnPlanPointBindEvent = function(){
        $(".returnPlanPoint input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.returnPlanPoint=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //还款计划小数舍入方式
    var getReturnPlanRoundHtml = function(mfSysKind){
        var htmlStr = '<div class="item-div returnPlanRound" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">还款计划小数舍入方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="returnPlanRound'+mfSysKind.kindNo+'" value="2" checked="checked"/>四舍五入'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };
    //费用计划是否与还款计划合并
    var getFeePlanMergeHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.feePlanMerge=="0"){
            checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div feePlanMerge" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>费用计划与还款计划合并</span>'
            +'</span>'
            +'</div>'
            + '</div>';
        return htmlStr;
    };
    //费用计划是否与还款计划合并绑定事件
    var feePlanMergeBindEvent = function(){
        $(".feePlanMerge .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).hasClass("curChecked")){//禁用
                mfSysKind.feePlanMerge="0";
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.feePlanMerge="1";
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //多次放款还款计划合并
    var getMultipleLoanPlanMergeHtml = function(mfSysKind){
        var checkspan = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.multipleLoanPlanMerge=="0"){
            checkspan='<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr = '<div class="item-div multiplePlanMerge" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="item-checkbox">'
            + checkspan
            +'<span>多次放款还款计划合并</span>'
            +'</span>'
            +'</div>'
            + '</div>';

        return htmlStr;
    };

    //费用计划是否与还款计划合并绑定事件
    var multiplePlanMergeBindEvent = function(){
        $(".multiplePlanMerge .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).hasClass("curChecked")){//禁用
                mfSysKind.multipleLoanPlanMerge="0";
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.multipleLoanPlanMerge="1";
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };
    //还款顺序
    var getRepaymentOrderHtml = function(mfSysKind){
        var htmlStr = '<div class="item-div">'
            +'<div class="item-title margin_bottom_20">'
            +'<span class="color_black">还款顺序：</span>'
            +'<span class="padding_left_15">'+repaymentOrderTypeStr+'</span>'
            +'</div>'
            + '</div>';

        return htmlStr;
    };
    //结余处理方式
    var getBalanceDealTypeHtml = function(mfSysKind){
        var checkStr1="",checkStr2="";
        if(mfSysKind.balanceDealType=="1"){
            checkStr1='checked="checked"';
        }else{
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div balanceDealType" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">结余处理方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="balanceDealType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>冲抵贷款'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="balanceDealType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>退款'
            + '</span>'
            + '</div>'
            + '</div>';

        return htmlStr;
    };

    //结余处理方式
    var balanceDealTypeBindEvent = function(){
        $(".balanceDealType input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.balanceDealType=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //逾期利率复利利率输入类型
    var getOverCmpdRateInputtypeHtml = function(mfSysKind){
        var checkStr1="",checkStr2="";
        if(mfSysKind.overCmpdRateInputtype=="0"){
            checkStr1='checked="checked"';
        }else{
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div overCmpdRateInputtype" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">逾期利率复利利率输入类型：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="overCmpdRateInputtype'+mfSysKind.kindNo+'" value="0" '+checkStr1+'/>利率浮动'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="overCmpdRateInputtype'+mfSysKind.kindNo+'" value="1" '+checkStr2+'/>利率'
            + '</span>'
            + '</div>'
            + '</div>';

        return htmlStr;
    };

    //逾期利率复利利率输入类型
    var overCmpdRateInputtypeBindEvent = function(){
        $(".overCmpdRateInputtype input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.overCmpdRateInputtype=$(this).val();
            top.kindNo =mfSysKind.kindNo;
            var rateTypeShow;
            mfSysKind.rateType=$("input[name='rateType"+mfSysKind.kindNo+"']:checked").val();
            if(mfSysKind.rateType=="1"){
                rateTypeShow = "%";
            }else if(mfSysKind.rateType=="2"){
                rateTypeShow = "‰";
            }else if(mfSysKind.rateType=="3"){
                rateTypeShow = "‱";
            }else if(mfSysKind.rateType=="4"){
                rateTypeShow = "%";
            }
            if(mfSysKind.overCmpdRateInputtype=="1"){//利率
                $(".overFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
                $("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
                $("#rateTypeShowName"+top.kindNo).text("默认逾期利率为");
                $("#overFltRateDefShowName"+top.kindNo+"Show").text(rateTypeShow);
                $("#overFlotRateDefName"+top.kindNo).text("默认逾期利率：");
                $("#cmpFltRateDefName"+top.kindNo).text("默认复利利率：");
                $(".cmpFltRateDef"+top.kindNo+"Show").text(rateTypeShow);
            }else{//利率浮动
                $(".overFltRateDef"+top.kindNo+"Show").text("%");
                $("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
                $("#rateTypeShowName"+top.kindNo).text("默认逾期利率上浮为");
                $("#overFltRateDefShowName"+top.kindNo+"Show").text("%");
                $("#overFlotRateDefName"+top.kindNo).text("默认逾期利率上浮：");
                $("#cmpFltRateDefName"+top.kindNo).text("默认复利利率上浮：");
                $(".cmpFltRateDef"+top.kindNo+"Show").text("%");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则）
    var getOverIntstFlagHtml = function(mfSysKind){
        var checkStr1="",checkStr2="";
        if(mfSysKind.overIntstFlag=="0"){
            checkStr1='checked="checked"';
        }else{
            checkStr2='checked="checked"';
        }
        var htmlStr = '<div class="item-div overIntstFlag" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">逾期（复利）利息计算方式：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            + '<span>'
            + '<input class="margin_right_5" type="radio" name="overIntstFlag'+mfSysKind.kindNo+'" value="0" '+checkStr1+'/>按照固定利率'
            + '<input class="margin_right_5 margin_left_10" type="radio" name="overIntstFlag'+mfSysKind.kindNo+'" value="1" '+checkStr2+'/>按阶梯规则（需配置规则）'
            + '</span>'
            + '</div>'
            + '</div>';
        return htmlStr;
    };

    //逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则） 点击事件
    var overIntstFlagBindEvent = function(){
        $(".overIntstFlag input[type=radio]").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.overIntstFlag=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
    };

    //复利利息是否收取 1 不收取 2 收取
    var getCmpdRateTypeHtml = function(mfSysKind){
        var rateTypeShow="";
        var inputStr="";
        var marginStr="";
        $.each(rateTypeList,function(i,parmDic){
            if(i>0){
                marginStr="margin_left_10";
            }
            if(mfSysKind.rateType==parmDic.optCode){
                rateTypeShow = parmDic.remark;
            }
        });
        var checkspan = '<span class="checkbox-span "><i class="i i-gouxuan1"></i></span>';
        if(mfSysKind.cmpdRateType=="1"){//收取
            checkspan='<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
        }
        var htmlStr=""
        if(mfSysKind.overCmpdRateInputtype=="0"){//默认利率浮动
            htmlStr = '<div class="item-div cmpdRateType" data-kindno="'+mfSysKind.kindNo+'">'
                +'<div class="item-title margin_bottom_20">'
                +'<span class="item-checkbox">'
                + checkspan
                +'<span>复利利息收取</span>'
                +'</span>'
                + '<span class="color_black padding_left_45"><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率上浮：</span><input type="text" class="cmpFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.cmpFltRateDef+'"/><span class="cmpFltRateDef'+mfSysKind.kindNo+'Show">%</span></span>'
                +'</div>'
                + '</div>';
        }else{
            htmlStr = '<div class="item-div cmpdRateType" data-kindno="'+mfSysKind.kindNo+'">'
                +'<div class="item-title margin_bottom_20">'
                +'<span class="item-checkbox">'
                + checkspan
                +'<span>复利利息收取</span>'
                +'</span>'
                + '<span class="color_black padding_left_45"><span id="cmpFltRateDefName'+mfSysKind.kindNo+'">默认复利利率：</span><input type="text" class="cmpFltRateDef'+mfSysKind.kindNo+'" maxlength="10" value="'+mfSysKind.cmpFltRateDef+'"/><span class="cmpFltRateDef'+mfSysKind.kindNo+'Show">'+rateTypeShow+'</span></span>'
                +'</div>'
                + '</div>';
        }

        return htmlStr;
    };
    //复利利息是否收取绑定事件
    var cmpdRateTypeBindEvent = function(){
        $(".cmpdRateType .checkbox-span").bind("click",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            if($(this).hasClass("curChecked")){//不收取
                mfSysKind.cmpdRateType="0";
                $(this).removeClass("curChecked");
            }else{
                mfSysKind.cmpdRateType="1";
                $(this).addClass("curChecked");
            }
            var ajaxData = JSON.stringify(mfSysKind);
            updateKindCalcConfig(ajaxData);
        });
        //复利利息上浮绑定事件
        $(".cmpdRateType input[type=text]").bind("change",function(){
            var mfSysKind={};
            mfSysKind.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysKind.cmpFltRateDef=$(this).val();
            top.cmpFltRateDef=$(this).val();
            var ajaxData = JSON.stringify(mfSysKind);
            top.kindNo =mfSysKind.kindNo;
            updateKindCalcConfig(ajaxData,function(){
                $(".cmpFltRateDef"+top.kindNo).text(top.cmpFltRateDef);
            });
        });
    };


    //逾期违约金
    var getPenaltyFincHtml = function(mfSysKind){
        var overPenaltyMain = mfSysKind.overPenaltyMain;
        var checkStr1 = "",checkStr2 = "",checkStr3 = "";
        var baseTypeStr1 = "",baseTypeStr2 = "",baseTypeStr3 = "",baseTypeStr4 = "";
        var cycleStr1 = "",cycleStr2 = "";
        var contentStr1 = 'style="display:none;"',contentSpanStr1 = 'style="display:none;"',contentStr2 = 'style="display:none;"',contentStr3 = 'style="display:none;"';
        if(overPenaltyMain.penaltyReceiveType=="1"){
            checkStr1='checked="checked"';
            contentStr1 = 'style="display:block;"';
            contentSpanStr1 = 'style="display:inline-block;"';
        }else if(overPenaltyMain.penaltyReceiveType=="2"){
            checkStr2='checked="checked"';
            contentStr2 = 'style="display:inline-block;"';
        }else if(overPenaltyMain.penaltyReceiveType=="3"){
            checkStr3='checked="checked"';
            contentStr3 = 'style="display:inline-block;"';
        }
        if(overPenaltyMain.penaltyCalcBaseType=="1"){
            baseTypeStr1='checked="checked"';
        }else if(overPenaltyMain.penaltyCalcBaseType=="2"){
            baseTypeStr2='checked="checked"';
        }else if(overPenaltyMain.penaltyCalcBaseType=="3"){
            baseTypeStr3='checked="checked"';
        }else if(overPenaltyMain.penaltyCalcBaseType=="4"){
            baseTypeStr4='checked="checked"';
        }
        if(overPenaltyMain.penaltyReceiveCycle=="1"){
            cycleStr1='checked="checked"';
        }else if(overPenaltyMain.penaltyReceiveCycle=="2"){
            cycleStr2='checked="checked"';
        }



        var htmlStr = '<div class="item-div penaltyFinc" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">逾期违约金：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            +'<input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>按比例'
            +'</span>'
            +'<span class="padding_left_15 penalty'+mfSysKind.kindNo+'" id="penalty1'+mfSysKind.kindNo+'" '+contentSpanStr1+'>'
            + '<input class="margin_right_5 margin_left_10 penaltyReceiveValue" type="text" name="peneceiveValue'+mfSysKind.kindNo+'" value="'+overPenaltyMain.penaltyReceiveValue+'"/>%'
            +'</span>'
            +'<div class="padding_left_15 margin_top_10 divPenalty'+mfSysKind.kindNo+'" id="divPenalty1'+mfSysKind.kindNo+'" '+contentStr1+'>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">计算基数</span>'
            +'<span class="padding_left_15">'
            + '<input class="margin_right_5 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType'+mfSysKind.kindNo+'" value="1" '+baseTypeStr1+'/>合同金额'
            + '<input class="margin_right_5 margin_left_10 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType'+mfSysKind.kindNo+'" value="2" '+baseTypeStr2+'/>借据金额'
            + '<input class="margin_right_5 margin_left_10 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType'+mfSysKind.kindNo+'" value="3" '+baseTypeStr3+'/>逾期本金'
            + '<input class="margin_right_5 margin_left_10 penaltyCalcBaseType" type="radio" name="penaltyCalcBaseType'+mfSysKind.kindNo+'" value="4" '+baseTypeStr4+'/>逾期金额'
            +'</span>'
            + '</div>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">计算周期</span>'
            +'<span class="padding_left_15">'
            + '<input class="margin_right_5 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle'+mfSysKind.kindNo+'" value="1" '+cycleStr1+'/>一次性'
            + '<input class="margin_right_5 margin_left_10 penaltyReceiveCycle" type="radio" name="penaltyReceiveCycle'+mfSysKind.kindNo+'" value="2" '+cycleStr2+'/>按逾期天数'
            +'</span>'
            + '</div>'
            + '</div>'
            + '</div>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            +'<input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>固定金额'
            +'</span>'
            +'<span class="padding_left_15 penalty'+mfSysKind.kindNo+'" id="penalty2'+mfSysKind.kindNo+'" '+contentStr2+'>'
            + '<input class="margin_right_5 margin_left_10 penaltyReceiveValue" type="text" name="peneceiveValue'+mfSysKind.kindNo+'" value="'+overPenaltyMain.penaltyReceiveValue+'"/>元'
            +'</span>'
            + '</div>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            + '<input class="margin_right_5 typeInput" type="radio" name="penaltyReceiveType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>根据逾期天数不同设置不同比例或金额(阶梯式收取)'
            +'</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.openPenaltyChild(\''+mfSysKind.kindNo+'\',1);" class="padding_left_15 pointer penalty'+mfSysKind.kindNo+'" id="penalty3'+mfSysKind.kindNo+'" '+contentStr3+'>配置</a>'
            + '</div>'
            + '</div>'
            +'</div>';

        return htmlStr;
    };
    //违约金设置绑定事件
    var penaltyFincBindEvent = function(){
        $(".penaltyFinc .typeInput").bind("click",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyReceiveType=$(this).val();
            mfSysPenaltyMain.penaltyType="1";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData,function(data){
                var mfSysPenaltyMain = data.mfSysPenaltyMain;
                $(".penalty"+mfSysPenaltyMain.kindNo).css("display","none");
                $(".divPenalty"+mfSysPenaltyMain.kindNo).css("display","none");

                $("#penalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","inline-block");
                if(mfSysPenaltyMain.penaltyReceiveType=="1"){//按比例
                    $("#divPenalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","block");
                }
            });
        });

        $(".penaltyFinc .penaltyReceiveValue").bind("change",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
            mfSysPenaltyMain.penaltyType="1";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData);
        });
        $(".penaltyFinc .penaltyCalcBaseType").bind("click",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyCalcBaseType=$(this).val();
            mfSysPenaltyMain.penaltyType="1";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData);
        });
        $(".penaltyFinc .penaltyReceiveCycle").bind("click",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyReceiveCycle=$(this).val();
            mfSysPenaltyMain.penaltyType="1";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData);
        });

    };

    //提前还款违约金
    var getAdvanceRepayHtml = function(mfSysKind){
        var prePenaltyMain = mfSysKind.prePenaltyMain;
        var checkStr1 = "",checkStr2 = "",checkStr3 = "";
        var baseTypeStr1 = "",baseTypeStr2 = "",baseTypeStr3 = "";
        var contentStr1 = 'style="display:none;"',contentSpanStr1 = 'style="display:none;"', contentStr2 = 'style="display:none;"',contentStr3 = 'style="display:none;"';
        if(prePenaltyMain.penaltyReceiveType=="1"){
            checkStr1='checked="checked"';
            contentStr1 = 'style="display:block;"';
            contentSpanStr1 = 'style="display:inline-block;"';
        }else if(prePenaltyMain.penaltyReceiveType=="2"){
            checkStr2='checked="checked"';
            contentStr2 = 'style="display:inline-block;"';
        }else if(prePenaltyMain.penaltyReceiveType=="3"){
            checkStr3='checked="checked"';
            contentStr3 = 'style="display:inline-block;"';
        }

        if(prePenaltyMain.penaltyCalcBaseType=="1"){
            baseTypeStr1='checked="checked"';
        }else if(prePenaltyMain.penaltyCalcBaseType=="2"){
            baseTypeStr2='checked="checked"';
        }else if(prePenaltyMain.penaltyCalcBaseType=="3"){
            baseTypeStr3='checked="checked"';
        }
        var htmlStr = '<div class="item-div preRepayPenalty" data-kindno="'+mfSysKind.kindNo+'">'
            +'<div class="item-title margin_bottom_10">'
            +'<span class="color_black">提前还款违约金：</span>'
            +'</div>'
            + '<div class="item-content margin_bottom_20 padding_left_15">'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            +'<input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="1" '+checkStr1+'/>按比例'
            +'</span>'
            +'<span class="padding_left_15 prePenalty'+mfSysKind.kindNo+'" id="prePenalty1'+mfSysKind.kindNo+'" '+contentSpanStr1+'>'
            + '<input class="margin_right_5 margin_left_10 penaltyReceiveValue" type="text" name="prePeneceiveValue'+mfSysKind.kindNo+'" value="'+prePenaltyMain.penaltyReceiveValue+'"/>%'
            +'</span>'
            +'<div class="padding_left_15 margin_top_10 divPrePenalty'+mfSysKind.kindNo+'" id="divPrePenalty1'+mfSysKind.kindNo+'" '+contentStr1+'>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">计算基数</span>'
            +'<span class="padding_left_15">'
            + '<input class="margin_right_5 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="1" '+baseTypeStr1+'/>合同金额'
            + '<input class="margin_right_5 margin_left_10 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="2" '+baseTypeStr2+'/>借据金额'
            + '<input class="margin_right_5 margin_left_10 penaltyCalcBaseType" type="radio" name="prePenaltyCalcBaseType'+mfSysKind.kindNo+'" value="3" '+baseTypeStr3+'/>提前还款本金'
            +'</span>'
            + '</div>'
            + '</div>'
            + '</div>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            +'<input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="2" '+checkStr2+'/>固定金额'
            +'</span>'
            +'<span class="padding_left_15 prePenalty'+mfSysKind.kindNo+'" id="prePenalty2'+mfSysKind.kindNo+'" '+contentStr2+'>'
            + '<input class="margin_right_5 margin_left_10 penaltyReceiveValue" type="text" name="prePeneceiveValue'+mfSysKind.kindNo+'" value="'+prePenaltyMain.penaltyReceiveValue+'"/>元'
            +'</span>'
            + '</div>'
            +'<div class="margin_bottom_10">'
            +'<span class="color_black">'
            + '<input class="margin_right_5 typeInput" type="radio" name="prePenaltyReceiveType'+mfSysKind.kindNo+'" value="3" '+checkStr3+'/>根据期限不同设置不同比例或金额(阶梯式收取)'
            +'</span>'
            +'<a href="javascript:void(0);" onclick="MfKindConfig.openPenaltyChild(\''+mfSysKind.kindNo+'\',2);" class="padding_left_15 pointer prePenalty'+mfSysKind.kindNo+'" id="prePenalty3'+mfSysKind.kindNo+'" '+contentStr3+'>配置</a>'
            + '</div>'
            + '</div>'
            + '</div>';

        return htmlStr;
    };
    //违约金设置绑定事件
    var preRepayPenaltyBindEvent = function(){
        $(".preRepayPenalty .typeInput").bind("click",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyReceiveType=$(this).val();
            mfSysPenaltyMain.penaltyType="2";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData,function(data){
                var mfSysPenaltyMain = data.mfSysPenaltyMain;
                $(".prePenalty"+mfSysPenaltyMain.kindNo).css("display","none");
                $(".divPrePenalty"+mfSysPenaltyMain.kindNo).css("display","none");

                $("#prePenalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","inline-block");
                if(mfSysPenaltyMain.penaltyReceiveType=="1"){//按比例
                    $("#divPrePenalty"+mfSysPenaltyMain.penaltyReceiveType+mfSysPenaltyMain.kindNo).css("display","block");
                }
            });
        });

        $(".preRepayPenalty .penaltyReceiveValue").bind("change",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyReceiveValue=$(this).val();
            mfSysPenaltyMain.penaltyType="2";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData);
        });
        $(".preRepayPenalty .penaltyCalcBaseType").bind("click",function(){
            var mfSysPenaltyMain={};
            mfSysPenaltyMain.kindNo=$(this).parents(".item-div").data("kindno");
            mfSysPenaltyMain.penaltyCalcBaseType=$(this).val();
            mfSysPenaltyMain.penaltyType="2";
            var ajaxData = JSON.stringify(mfSysPenaltyMain);
            updateMfSysPenalty(ajaxData);
        });
    };
    //违约金修改
    var updateMfSysPenalty = function(ajaxData,penaltyCallBack){
        $.ajax({
            url:webPath+"/mfSysKind/updateMfSysPenaltyAjax",
            type:'post',
            data:{ajaxData:ajaxData},
            async:false,
            beforeSend:function(){
                LoadingAnimate.start();
            },success:function(data){
                if(data.flag="success"){
                    if(penaltyCallBack!==undefined&&typeof(penaltyCallBack) == "function"){
                        penaltyCallBack(data);
                    }
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },complete: function(){
                LoadingAnimate.stop();
            }
        });
    };

    //违约金阶梯设置
    var _openPenaltyChild=function(kindNo,penaltyType){
        var penaltyTitle = penaltyType=='1'?'逾期违约金阶梯设置':'提前还款违约金阶梯设置';
        var url=webPath+"/mfSysPenaltyChild/getPenaltyChildList?kindNo="+kindNo+"&penaltyType="+penaltyType;
        window.parent.openBigForm(url,penaltyTitle,function(){
        });
    };

    //核算的高级设置
    var getCalcAdvancedSetHtml = function(mfSysKind){
        var contentHtml ="";
        //一年天数设置
        contentHtml= contentHtml + getYearDaysHtml(mfSysKind);
        //利率小数位数设置
        contentHtml= contentHtml + getRateDecimalDigitsHtml(mfSysKind);
        //计息方式
        contentHtml= contentHtml + getIcTypeHtml(mfSysKind);
        //还款计划保留位数
        contentHtml= contentHtml + getReturnPlanPointHtml(mfSysKind);
        //还款计划小数舍入方式
        contentHtml= contentHtml + getReturnPlanRoundHtml(mfSysKind);
        //费用计划是否与还款计划合并
        contentHtml= contentHtml + getFeePlanMergeHtml(mfSysKind);
        //多次放款还款计划合并
        contentHtml= contentHtml + getMultipleLoanPlanMergeHtml(mfSysKind);
        //还款顺序
        contentHtml= contentHtml + getRepaymentOrderHtml(mfSysKind);
        //结余处理方式
        contentHtml= contentHtml + getBalanceDealTypeHtml(mfSysKind);
        //逾期利率复利利率输入类型  0-利率浮动、1-利率 (0 表示表单输入的是利率浮动百分比，1 表示是利率值)
        contentHtml= contentHtml + getOverCmpdRateInputtypeHtml(mfSysKind);
        //逾期（复利）利息计算方式 0 按照固定利率 1 按阶梯规则（需配置规则）
        contentHtml= contentHtml + getOverIntstFlagHtml(mfSysKind);
        //复利利息是否收取  1 不收取 2 收取
        contentHtml= contentHtml + getCmpdRateTypeHtml(mfSysKind);
        //逾期违约金
        contentHtml= contentHtml + getPenaltyFincHtml(mfSysKind);
        //提前还款违约金
        contentHtml= contentHtml + getAdvanceRepayHtml(mfSysKind);
        var optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#advanceSet'+mfSysKind.kindNo+'">'
            +'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
            +'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
            +'</div>';
        var htmlStr = '<div id="advanceSet'+mfSysKind.kindNo+'" class="more-div collapse">'+contentHtml+'</div>'+optStr;
        return htmlStr;
    };

    var getCalcConfigHtml = function(mfSysKind){
        var contentHtml ="";
        //利率类型
        contentHtml = contentHtml+getRateTypeHtml(mfSysKind);
        //还款方式
        contentHtml = contentHtml+getRepayTypeHtml(mfSysKind);
        //利息计算方式
        contentHtml = contentHtml+getNormCalcTypeHtml(mfSysKind);
        //按月结息时不足一月利息计算方式
        contentHtml = contentHtml+getSecondNormCalcTypeHtml(mfSysKind);
        //利息计算基数
        contentHtml = contentHtml+getInstCalcBaseHtml(mfSysKind);
        //利息减免
        contentHtml = contentHtml+getInterestDerateHtml(mfSysKind);
        //节假日利息收取设置
        contentHtml = contentHtml+getFestivalTypeHtml(mfSysKind);
        //允许最后一期结余
        contentHtml = contentHtml+getLastTermBalanceTypeHtml(mfSysKind);
        //还款日设置
        contentHtml = contentHtml+getRepayDateSetHtml(mfSysKind);
        //利息收息方式
        contentHtml = contentHtml+getInstCollectTypeHtml(mfSysKind);
        //费用收取方式
        contentHtml = contentHtml+getFeeCollectWayHtml(mfSysKind);
        //利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
        contentHtml = contentHtml+getLsbqChargeIntstHtml(mfSysKind);
        //预先支付利息收取方式
        contentHtml = contentHtml+getPreInstCollectTypeHtml(mfSysKind);
        //提前还款
        contentHtml = contentHtml+getPreRepayTypeHtml(mfSysKind);
        //提前还款利息
        contentHtml = contentHtml+getPreRepayInstCalcHtml(mfSysKind);
        //核算高级设置
        contentHtml = contentHtml+getCalcAdvancedSetHtml(mfSysKind);

        var htmlStr='<div id="calcConfig'+mfSysKind.kindNo+'" class="sub-content-div padding_left_15">'
            +'<div class="sub-title"><span>核算设置</span></div>'
            +'<div class="sub-content padding_left_15">'
            + contentHtml
            +'</div>'
            +'</div>';
        return htmlStr;
    };



    /****************************************核算设置（基本设置和高级设置）end***********************************/
    return{
        init:_init,
        addKind:_addKind,
        editKind:_editKind,
        addFeeItem:_addFeeItem,
        editFeeItem:_editFeeItem,
        deleteFeeItem:_deleteFeeItem,
        addTemplate:_addTemplate,
        templateSet:_templateSet,
        deleteTemplate:_deleteTemplate,
        addBusNode:_addBusNode,
        kindNodeSet :_kindNodeSet,
        changeIsMust:_changeIsMust,
        deleteDocTypeItem:_deleteDocTypeItem,
        getDocTypeData:_getDocTypeData,
        getEventObj:_getEventObj,
        addNodeTemplate:_addNodeTemplate,
        deleteNodeTemplate:_deleteNodeTemplate,
        changeOptPower:_changeOptPower,
        addNodeFeeItem:_addNodeFeeItem,
        editNodeFeeItem:_editNodeFeeItem,
        deleteNodeFeeItem:_deleteNodeFeeItem,
        addNodeIntercept:_addNodeIntercept,
        deleteNodeIntercept:_deleteNodeIntercept,
        updateFlowUseFlag:_updateFlowUseFlag,
        openProcessDesigner:_openProcessDesigner,
        openPenaltyChild:_openPenaltyChild,


    };

}(window, jQuery);