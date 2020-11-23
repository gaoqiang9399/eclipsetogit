;
var MfPubFlowConfig=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		$(".tabCont").html("").addClass("hide");
		var htmlStr = getPubFlowConfigHtml(data.dicList,data.flowMap);
		$(".nav-content-div").html(htmlStr);
	};
	
	var getPubFlowConfigHtml = function(dicList,flowMap){
		var htmlStr='<div class="content-div">';
		$.each(dicList,function(i,parmDic){//遍历业务模式
			htmlStr=htmlStr+'<div class="sub-content-div padding_left_15">'
			+'<div class="sub-title"><span><i class="i i-shuxian"></i>'+parmDic.optName+'</span></div>'
			+'<div class="sub-content">';
			htmlStr=htmlStr + getFlowContentHtml(flowMap[parmDic.optCode]);
			htmlStr=htmlStr +'</div></div>';
		});
		htmlStr=htmlStr+'</div>';
		return htmlStr;
	};
	
	
	//产品流程配置
	var getFlowContentHtml = function(flowList){
		var htmlStr="";
		$.each(flowList,function(i,kindFlow){
			var checkspan = '<span class="checkbox-span curChecked" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfPubBaseConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'1\');"><i class="i i-gouxuan1"></i></span>';
			var alinkStr ='<a href="javascript:void(0);" onclick="MfPubFlowConfig.openProcessDesigner(\''+kindFlow.flowId+'\')" class="padding_left_15 pointer">配置</a>';
			
			if(kindFlow.useFlag=="0"){
				checkspan='<span class="checkbox-span" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfPubBaseConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'0\');"><i class="i i-gouxuan1"></i></span>';
				alinkStr ='<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>';
			}
			htmlStr =htmlStr +'<div class="item-div">'
				+'<div class="item-title  margin_bottom_10">'
					+'<span class="item-checkbox">'
					+ checkspan
					+'<span>启用'+kindFlow.flowApprovalName+'</span>'
					+ alinkStr
					+'</span>'
				+'</div>'
				+'<div class="item-content padding_left_5">'
					+'<div id="processItem'+kindFlow.kindFlowId+'" class="padding_left_15">'
					+'基础流程：<span class="process-item-desc" id="'+kindFlow.flowApprovalNo+kindFlow.kindNo+'">'+kindFlow.flowRemark+'</span>'
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
			success:function(data){
				if(data.flag=="success"){
					if(useflag=="0"){//禁用
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'MfPubFlowConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
						//去掉a标签上的onclick事件
						$(obj).parents(".item-checkbox").find("a").addClass("link-disabled");
						$(obj).parents(".item-checkbox").find("a").removeAttr("onclick");
					}else{//启用
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'MfPubFlowConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
						var alinkStr ='<a href="javascript:void(0);" onclick="MfPubFlowConfig.openProcessDesigner(\''+flowId+'\')" class="padding_left_15 pointer">配置</a>';
						$(obj).parents(".item-checkbox").find("a").remove();
						$(obj).parents(".item-checkbox").append(alinkStr);
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
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
	//流程设计表单
	var _openDesignerForm=function(wkfId){
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
	
	
	return{
		init:_init,
		openDesignerForm:_openDesignerForm,
		openProcessDesigner:_openProcessDesigner,
		updateFlowUseFlag:_updateFlowUseFlag
		
	};
	
}(window, jQuery);