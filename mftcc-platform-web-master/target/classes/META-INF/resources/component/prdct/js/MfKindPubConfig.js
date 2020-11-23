;
var MfKindPubConfig=function(window, $){
	//产品设置 列表页面初始化方法
	var _init = function(){
		MfConfigNavLine.createPubConfigNavLine(configTypeList);
		$(".config-div").mCustomScrollbar({
			advanced: {
				updateOnContentResize: true,
			}
		});
	};
	//获取产品的公共配置信息
	var _getPubConfigByType = function(configType){
		$.ajax({
			url:webPath+"/mfSysKind/getKindPubConfigAjax",
			data:{configType:configType},
			success:function(data){
				if(data.flag=='success'){
					setKindPubConfigHtml(data,configType);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	};
	
	//获取产品公共设置的html
	var setKindPubConfigHtml = function(data,configType){
		if("approval"==configType){//基础审批流程配置
			MfPubFlowConfig.init(data);
		}else if("calc"==configType){//核算配置
			MfPubCalcConfig.init(data);
		}else if("form"==configType){//表单配置
//			MfPubFormConfig.init(data);
		}else if("fee"==configType){//费用配置
//			MfPubFeeConfig.init(data);
		}else if("template"==configType){//模板配置
			TemplateConfig.init();
		}
	};
	

	
	var _toPubConfig = function(obj){
		window.location.href = webPath+"/mfSysKind/toPubConfig";
	};
	
	return{
		init:_init,
		getPubConfigByType:_getPubConfigByType,
		toPubConfig:_toPubConfig,
	};
}(window, jQuery);