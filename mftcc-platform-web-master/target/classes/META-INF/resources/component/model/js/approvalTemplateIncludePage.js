;
var approvalTemplateIncludePage = function(window, $) {
	var _init = function() {
		template_init();
	};
	//打开文档
	var _openTemplateTmp = function(obj,url){
		var templateBizConfigId = url.split("=")[1];
		_openTemplate(templateBizConfigId);
	};
	/** 打开pageoffice */
	var _openTemplate = function(templateBizConfigId) {
		var url = webPath +"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
		var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
		$.ajax({
			url : url + "&" + temParm,
			data : {
				"returnUrl" : backUrl
			},
			type : 'post',
			dataType : 'json',
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			},
			success : function(data) {
				var poCntObj = $.parseJSON(data.poCnt);
				//回调方法
				poCntObj.callBackfun = "templateIncludePage.init";
				mfPageOffice.openPageOffice(poCntObj);
			}
		});
	};

	/** 文档加载 */
	var template_init = function() {
	    var paramMap = new Object();
	    if(typeof (fincId) != "undefined" && fincId != null && fincId != "" && (typeof (ifEsignHistory) == "undefined" || ifEsignHistory == 0)){
            paramMap.fincId = fincId;
        }
	    if(typeof (ifEsignHistory) != "undefined"){
            paramMap.ifEsignHistory = ifEsignHistory;
        }else{
            paramMap.ifEsignHistory = "0";
        }
		$.ajax({
			url :webPath + "/mfTemplateBizConfig/getBizConfigsForListAjax",
			data : {
				"nodeNo" : nodeNo,
                "approvalNodeNo" : approvalNodeNo,
				"temBizNo" : temBizNo,
				"querySaveFlag" : querySaveFlag_pl,
                "paramMap":JSON.stringify(paramMap)

			},
			type : 'post',
			dataType : 'json',
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", webPath + "/mfTemplateBizConfig/getApprovalBizConfigsForListAjax"));
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#bizConfigs").html(data.bizConfigs);
					$("#bizConfigs td[mytitle]:contains('...')").initMytitle();
					if(data.qrCodeShowFlag=="0"){
						$(".qrCode").remove();
					}
                    $("#template_div").show();
                    $("#buttonFile").css("display","inline-block");
                    $(".docType").each(function(){
                        var innerVal = $(this).html();
                        if(innerVal == "2"){
                            $(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/xls.png' height='20px' width='20px' style='margin-top:10px;'>");
                        }else if(innerVal == "1"){
                            $(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/word.png' height='20px' width='20px' style='margin-top:10px;'>");
                        }else if(innerVal == "3"){
                            $(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/pdf.png' height='20px' width='20px' style='margin-top:10px;'>");
                        }else if(innerVal == "4"){
                            $(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/html.png' height='20px' width='20px' style='margin-top:10px;'>");
                        }
                    });
				} else {
					$("#template_div").hide();
				}
			}
		});
	};

	/** 循环添加所有的业务文档 */
	var getTemplateBizConfigHtml = function(mfTemplateBizConfigList) {
		// 循环产品下的模板项
		var subHtmlStr = "";
		$.each(mfTemplateBizConfigList, function(i, bizConfig) {
			var htmlTmp = getTemplateItemHtml(bizConfig);
			subHtmlStr = subHtmlStr + htmlTmp;
		});
		var htmlStr = '<div class="item-div">' + subHtmlStr + '</div>';
		return htmlStr;
	};

	/** 业务文档html */
	var getTemplateItemHtml = function(bizConfig) {
		var imgClass = "item-word";
		if(bizConfig.templateSuffix=="2"){
			imgClass = "item-excel";
		}else if(bizConfig.templateSuffix=="3"){
			imgClass = "item-pdf";
		}
		//文件名过长时截取显示
		var templateNameZh = bizConfig.templateNameZh;
		if(templateNameZh.length>15){
			templateNameZh = templateNameZh.substring(0, 15)+ "...";
		}
		
		var htmlStr = '<div id="' + bizConfig.templateBizConfigId + '" class="block-item">';
		htmlStr += '<div class="item-title ' + imgClass + '" onclick="templateIncludePage.openTemplate(\'' + bizConfig.templateBizConfigId + '\');">';
		htmlStr += '<span title='+bizConfig.templateNameZh+'>' + templateNameZh + '</span>';
		if (!bizConfig.docFileName) {
			htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
		}
		htmlStr += '     </div>';
		// htmlStr += ' <i class="i i-x2" onclick="templateIncludePage.deleteTemplate(\'' + bizConfig.templateBizConfigId + '\', \'' + bizConfig.modelNo + '\')"></i>';
		htmlStr += '   </div>';
		return htmlStr;
	};

	//重置保存过模板的信息
	var _resetTemplateInfo = function(obj,urlArgs){
        var url = webPath + urlArgs +  "&ajaxData=&docFilePath=&docFileName=";
        var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
        $.ajax({
            url : url ,
            type : 'post',
            dataType : 'json',
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", url));
            },
            success : function(data) {
                if(data.flag == "success"){
                    window.top.alert("删除成功！", 1);
				}else{
                    window.top.alert("删除失败！", 1);
				}
            }
        });
	};

   var _previewTagValue = function(obj,url) {
       var parmValue = "";

       if(typeof (cusNo) != "undefined"){
           parmValue = parmValue + "&cusNo=" + cusNo;
       }else{
           parmValue = parmValue + "&cusNo=" + null;
       }
       if(typeof (appId) != "undefined"){
           parmValue = parmValue + "&appId=" + appId;
       }else{
           parmValue = parmValue + "&appId=" + null;
       }
       if(typeof (pactId) != "undefined"){
           parmValue = parmValue + "&pactId=" + pactId;
       }else{
           parmValue = parmValue + "&pactId=" + null;
       }
       if(typeof (fincId) != "undefined"){
           parmValue = parmValue + "&fincId=" + fincId;
       }else{
           parmValue = parmValue + "&fincId=" + null;
       }
        top.window.openBigForm(encodeURI(webPath+url+ parmValue), '标签预览页面', closeDialog);

    }

    var closeDialog =function(){
        $(top.window.document).find(".dhccModalDialog").eq($(top.window.document).find(".dhccModalDialog").length-1).find(".i-x5").click();
    }
    var _getCerCode = function(url) {
        $.ajax({
            url :webPath + url,
            data : {
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    window.location.href=webPath + "/mfBusPact/getPdfView?templateNo="+ data.templateNo+"&id="+data.id+"&pdfHash="+data.pdfHash+"&temBizNo="+data.temBizNo+"&nodeNo="+data.nodeNo+"&cusNo="+data.cusNo;
                }else{
                    window.top.alert(data.msg, 0);
                }

            },
            error : function() {
            },
        });
    }
    var _findListForDZQY = function(url) {
        top.window.openBigForm(webPath + url, '签约人员列表', closeDialog);
    }
    return {
        init : _init,
        openTemplate : _openTemplate,
        openTemplateTmp : _openTemplateTmp,
        getTemplateBizConfigHtml:getTemplateBizConfigHtml,
        resetTemplateInfo : _resetTemplateInfo,
        previewTagValue : _previewTagValue,
        getCerCode:_getCerCode,
        findListForDZQY:_findListForDZQY
    };
}(window, jQuery);
