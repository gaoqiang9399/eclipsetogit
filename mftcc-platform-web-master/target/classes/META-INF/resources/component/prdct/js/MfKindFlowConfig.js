;
var MfKindFlowConfig=function(window, $){
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
		+'<span>审批流程设置</span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-desc"><span class="content-desc">流程设置主要是设置融资审批、合同审批、放款审批流程的启用与否以及审批流程图</span></div>'
		+'<div class="main-content-div">';
		$.each(flowList,function(i,kindFlow){
			var firstStr="";
			if(i==0){
				firstStr="first";
			}
			var checkspan = '<span class="checkbox-span curChecked" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfKindFlowConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'1\');"><i class="i i-gouxuan1"></i></span>';
			var alinkStr ='<a href="javascript:void(0);" onclick="MfKindFlowConfig.openProcessDesigner(\''+kindFlow.flowId+'\')" class="padding_left_15 pointer">配置</a>';
			
			if(kindFlow.useFlag=="0"){
				checkspan='<span class="checkbox-span" data-id="'+kindFlow.kindFlowId+'" data-flowid="'+kindFlow.flowId+'" onclick="MfKindFlowConfig.updateFlowUseFlag(this,\''+kindFlow.kindNo+'\',\'0\');"><i class="i i-gouxuan1"></i></span>';
				alinkStr ='<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>';
			}
			htmlStr =htmlStr +'<div class="main-content '+firstStr+'">'
					+'<span class="item-checkbox">'
					+ checkspan
					+'<span>启用'+kindFlow.flowApprovalName+'</span>'
					+ alinkStr
					+'</span>'
					+'<div class="content-desc margin_top_5">'
					+'<div id="processItem'+kindFlow.kindFlowId+'" class="padding_left_25">'
					+'<span class="content-desc font_size_12" id="'+kindFlow.flowApprovalNo+kindFlow.kindNo+'">当前流程：'+kindFlow.flowRemark+'</span>'
					+'</div>'
					+'</div>'
				+'</div>';
		});
		
		htmlStr=htmlStr+'</div></div></div></div></div></div>';
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
						$(obj).attr("onclick",'MfKindFlowConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
						//去掉a标签上的onclick事件
						$(obj).parents(".item-checkbox").find("a").addClass("link-disabled");
						$(obj).parents(".item-checkbox").find("a").removeAttr("onclick");
					}else{//启用
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'MfKindFlowConfig.updateFlowUseFlag(this,\''+kindNo+'\',\''+useflag+'\');');
						var alinkStr ='<a href="javascript:void(0);" onclick="MfKindFlowConfig.openProcessDesigner(\''+flowId+'\')" class="padding_left_15 pointer">配置</a>';
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
	
	
	return{
		init:_init,
		openDesignerForm:_openDesignerForm,
		openProcessDesigner:_openProcessDesigner,
		updateFlowUseFlag:_updateFlowUseFlag
		
	};
	
}(window, jQuery);