var MfBusCreditApplyTimeResult=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		var htmlStr = getPrdctFlowConfigHtml(data.flowList);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
	};
	//产品流程配置
	var getPrdctFlowConfigHtml = function(flowList){
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content padding_left_20">'
		+'<div class="item-div">'
		+'<div class="item-title">'
		+'<span>授信签约阶段时效配置</span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-desc"><span class="content-desc">设置授信业务合同阶段的时效时间</span></div>'
		+'<div class="main-content-div">';
		$.each(flowList,function(i,kindFlow){
			var firstStr="";
			if(i==0){
				firstStr="first";
			}
			var checkspan = '<span class="checkbox-span curChecked" data-id="'+kindFlow.modelName+'" data-flowid="'+kindFlow.modelId+'" onclick="MfBusCreditApplyTimeResult.updateFlowUseFlag(this,\''+kindFlow.modelId+'\',\'1\');"><i class="i i-gouxuan1"></i></span>';
			var alinkStr ='<a href="javascript:void(0);" onclick="MfBusCreditApplyTimeResult.openProcessDesigner(\''+kindFlow.modelId+'\')" class="padding_left_15 pointer">配置</a>';
			
			if(kindFlow.sts=="0"){
				checkspan='<span class="checkbox-span" data-id="'+kindFlow.modelId+'" data-flowid="'+kindFlow.modelId+'" onclick="MfBusCreditApplyTimeResult.updateFlowUseFlag(this,\''+kindFlow.modelId+'\',\'0\');"><i class="i i-gouxuan1"></i></span>';
				alinkStr ='<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>';
			}
			htmlStr =htmlStr +'<div class="main-content '+firstStr+'">'
					+'<span class="item-checkbox">'
					+ checkspan
					+'<span>启用'+kindFlow.modelName+'签约时效</span>'
					+ alinkStr
					+'</span>'
				+'</div>';
		});
		
		htmlStr=htmlStr+'</div></div></div></div></div></div>';
		return htmlStr;
	};
	
	
	
	//启用禁用审批流程
	var _updateFlowUseFlag = function(obj,modelId,sts){
		var kindFlowId = $(obj).data("id");
		var flowId = $(obj).data("flowid");
		if(sts=="1"){//禁用
			$(obj).removeClass("curChecked");
            sts = "0";
		}else{//启用
			$(obj).addClass("curChecked");
            sts = "1";
		}
		$.ajax({
			url:webPath+"/mfCusCreditModel/updateAjax",
			type:'post',
			data:{kindFlowId:kindFlowId,modelId:modelId,sts:sts},
			success:function(data){
				if(data.flag=="success"){
					if(sts=="0"){//禁用
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'MfBusCreditApplyTimeResult.updateFlowUseFlag(this,\''+modelId+'\',\''+sts+'\');');
						//去掉a标签上的onclick事件
						$(obj).parents(".item-checkbox").find("a").addClass("link-disabled");
						$(obj).parents(".item-checkbox").find("a").removeAttr("onclick");
					}else{//启用
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'MfBusCreditApplyTimeResult.updateFlowUseFlag(this,\''+modelId+'\',\''+sts+'\');');
						var alinkStr ='<a href="javascript:void(0);" onclick="MfBusCreditApplyTimeResult.openProcessDesigner(\''+modelId+'\')" class="padding_left_15 pointer">配置</a>';
						$(obj).parents(".item-checkbox").find("a").remove();
						$(obj).parents(".item-checkbox").append(alinkStr);
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	
	//点击配置打开配置表单
	var _openProcessDesigner=function(modelId){
		var url = webPath+"/mfCusCreditModel/getListPage?modelId="+modelId;
        top.openBigForm(url,"时效设置");
	};

	var _updateApplyTime = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
        	var applyTime = $('input[name=applyTime]').val();
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url:url,
                type:"post",
				data:{ajaxData:dataParam,applyTime:applyTime},
                dataType:"json",
                success:function(data){
                    if(data.flag == "success"){
                       top.alert(data.msg, 1);
                        myclose();
                    }else if(data.flag == "error"){
                        layer.alert(data.msg,0);
                    }
                }
            })
		}

    }

	return{
		init:_init,
		openProcessDesigner:_openProcessDesigner,
		updateFlowUseFlag:_updateFlowUseFlag,
        updateApplyTime:_updateApplyTime
		
	};
	
}(window, jQuery);