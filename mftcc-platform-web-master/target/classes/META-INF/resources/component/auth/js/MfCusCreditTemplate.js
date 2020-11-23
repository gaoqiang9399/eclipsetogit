var MfCusCreditTemplate= function(window,$){
	var _init=function(){
		_template_init();
	}
	/** 文档加载 */
	var _template_init = function() {
		$.ajax({
			url : webPath+"/mfCusCreditApply/getCreditTemplateDataAjax",
			type : 'post',
			dataType : 'json',
			data : {
				creditAppId : appId,
				tempType:tempType,
				relId:relId,
			},
			success : function(data) {
				var saveFlag=data.flag;
				var htmlStr = getTemplateBizConfigHtml(data.mfCusCreditModel,saveFlag);
				$("#bizConfigs").html(htmlStr);
			}
		});
	};

	/** 循环添加所有的业务文档 */
	var getTemplateBizConfigHtml = function(mfCusCreditModel,saveFlag) {
		// 循环产品下的模板项
		var subHtmlStr = "";
		var htmlTmp = getTemplateItemHtml(mfCusCreditModel,saveFlag);
		subHtmlStr = subHtmlStr + htmlTmp;
		var htmlStr = '<div class="item-div">' + subHtmlStr + '</div>';
		return htmlStr;
	};

	/** 业务文档html */
	var getTemplateItemHtml = function(mfCusCreditModel,saveFlag) {
		var htmlStr = '';
		var imgClass = "item-word";
		var templateName=mfCusCreditModel.reportTemplateName;
		if(tempType =="PROTOCOL"){
			templateName=mfCusCreditModel.protocolTemplateName;
			htmlStr = '<div id="' + appId + '" class="block-item">';
			htmlStr += '     <div class="item-title ' + imgClass + '" onclick="MfCusCreditTemplate.openDocTemplate(\''+tempType+'\');">';
			htmlStr += '       <span>' + templateName + '</span>';
			if(saveFlag=="0"){
				htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
			}
			htmlStr += '     </div>';
			htmlStr += '   </div>';
		}else if(tempType =="REPORT"){
			htmlStr = '<div id="' + appId + '" class="block-item">';
			htmlStr += '     <div class="item-title ' + imgClass + '" onclick="MfCusCreditTemplate.openDocTemplate(\''+tempType+'\');">';
			htmlStr += '       <span>' + templateName + '</span>';
			if(saveFlag=="0"){
				htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
			}
			htmlStr += '     </div>';
			htmlStr += '   </div>';
		}else if(tempType =="ALL"){
			tempType="REPORT";
			htmlStr = '<div id="' + appId +tempType+ '" class="block-item">';
			htmlStr += '     <div class="item-title ' + imgClass + '" onclick="MfCusCreditTemplate.openDocTemplate(\''+tempType+'\');">';
			htmlStr += '       <span>' + templateName + '</span>';
			if(saveFlag=="0"){
				htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
			}
			htmlStr += '     </div>';
			htmlStr += '   </div>';
			tempType="PROTOCOL";
			templateName=mfCusCreditModel.protocolTemplateName;
			htmlStr += '<div id="' + appId +tempType+ '" class="block-item">';
			htmlStr += '     <div class="item-title ' + imgClass + '" onclick="MfCusCreditTemplate.openDocTemplate(\''+tempType+'\');">';
			htmlStr += '       <span>' + templateName + '</span>';
			if(saveFlag=="0"){
				htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
			}
			htmlStr += '     </div>';
			htmlStr += '   </div>';
		}
		return htmlStr;
	};
	//调用PageOffice
	var _openDocTemplate = function(tempType){
		if(tempType =="PROTOCOL"){
			relId=pactId;
		}else if(tempType =="REPORT"){
			relId=appId;
		}
		$.ajax({
				url: webPath+"/mfCusCreditApply/getIfSaveModleInfo",
				type:"post",
				dataType:"json",
				data:{
					relId:relId,
					creditAppId:appId,
					tempType:tempType
				},
				error:function(){
					alert('error');
				},
				success:function(data){
					var filepath = "";
					var type="add";
					if(data.flag != "0"){
						filepath = "/component/model/docword/";
						type="detail";
					}
					var returnUrl = window.location.href;
					returnUrl = encodeURIComponent(returnUrl);
					var filename = data.filename;
					var templateNo=data.templateNo;
					var saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+relId+"&filename="+filename+"&templateNo="+templateNo+"&userId="+userId;
					var poCntJson = {
							filePath : filepath,
							fileName : filename,
							appId : "",
							pactId : "",
							fincId : "",
							templateNo :"",
							cusNo : cusNo,
							saveUrl :saveUrl,
							saveBtn : "1",
							callBackfun : 'MfCusCreditTemplate.init',
							fileType : 0
					};
					if(type == "detail"){
						poCntJson.saveBtn = "0";// 取消保存按钮
						poCntJson.callBackfun = "";
					};
					mfPageOffice.openPageOffice(poCntJson);
				}
			});
	};
	return{
		template_init:_template_init,
		openDocTemplate:_openDocTemplate,
		init:_init
	};
}(window,jQuery);