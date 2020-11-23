;
var TemplateConfig=function(window,$){
	var _init=function(){
		_showSysTemplateConfigAjax();
	};
	var _showSysTemplateConfigAjax=function(){
		$.ajax({
			url :webPath+"/mfSysTemplate/getSysTemplateConfigListAjax",
			success:function(data){
				if(data.flag == "success"){
					getSysTemplateConfigHtml(data.mfSysTemplateList,data.templateTypeDicList);
				}else if(data.flag == "error"){
					window.top.alert(data.msg,0);
				}
			},error:function(){
				window.top.alert(top.getMessage("ERROR"),0);
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};	
	//节点模板设置html
	var getSysTemplateConfigHtml = function(templateList,templateTypeDicList){
		var templateContentHtml = getSysTemplateContentHtml(templateList,templateTypeDicList);
		$("#sysTemplateConfig-div").html(templateContentHtml);
		$("span[name='mfSysTemplateUseFlag']").useFlagRcswitcher({
			name:"useFlag",onText:"启用",offText:"停用"});
		//调用时间轴
		navLine.createNavLine();
	};
	
	
	var getSysTemplateContentHtml = function(templateList,templateTypeDicList){
		var htmlStr = "";
		//循环该节点下的每一个要件templateList
		$.each(templateTypeDicList,function(i,parmDic){
			var groupHtml='<div id="group'+parmDic.optCode+'" class="sub-content-div node-template-config padding_left_15 bus-config">';
			htmlStr =htmlStr +groupHtml+_getGroupHtml(templateList,parmDic.optCode,parmDic.optName)+"</div>"
		});
		return htmlStr;
	};
	
	var _getSysTemplateItemHtml=function(mfSysTemplate){
		var templateNo=mfSysTemplate.templateNo;
		var templateNameEn=mfSysTemplate.templateNameEn;
		var templateType=mfSysTemplate.templateType;
		var htmlStr = "";
		var designHtml = '<td><a href="javascript:void(0);" onclick="TemplateConfig.templateSet(\''+templateNo+'\',\''+templateNameEn+'\')">设计模板</a></td>';
		var iClass = "i i-word";
		if(mfSysTemplate.templateSuffix=="2"){
			iClass = "i i-excel";
		}else if(mfSysTemplate.templateSuffix=="3"){
			iClass = "i i-pdf-1";
		}else if(mfSysTemplate.templateSuffix=="4"){
            iClass = "i i-icon_html";
            designHtml = '<td style="font-size: 12px;color: #666">设计模板</td>';
        }
		var inputStr = "";
		var spanStr = "";
		if(mfSysTemplate.editableFlag == "1"){
			inputStr = '<input type="checkbox" onclick="MfKindConfig.changeOptPower(this);" checked=true >';
			spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>签</span>";
		}else{
			inputStr = '<input type="checkbox" onclick="MfKindConfig.changeOptPower(this);">';
		}
		htmlStr =htmlStr +'<div id="template'+mfSysTemplate.templateNo+'" class="template-block-item" data-optpower= "'+mfSysTemplate.editableFlag+'" data-templateno="'+mfSysTemplate.templateNo+'">'
		+'<div class="">'
		+'<table class="table-templateList"><tbody>'
		+'<tr><td rowspan="2" class="td-rowspan"><i class="'+iClass+'"></i></td><td class="td-templateName" colspan="5"><span>'+mfSysTemplate.templateNameZh+'</span></td></tr>'
		+'<tr class="tr-operator">'
		+'<td><span name="mfSysTemplateUseFlag"><a href="'+webPath+'/mfSysTemplate/updateUseFlagAjax?templateNo='+templateNo+'">'+mfSysTemplate.useFlag+'</a></span></td>'
		+'<td><a href="javascript:void(0);" onclick="TemplateConfig.editTemplateInfo(\''+templateNo+'\',\''+templateType+'\')">编辑</a></td>'
		+ designHtml
		+'<td><a href="javascript:void(0);" onclick="TemplateConfig.templateTagSet(\''+templateNo+'\')">标签配置</a></td>'
		+'<td><a href="javascript:void(0);" onclick="TemplateConfig.deleteTemplate(\''+templateNo+'\',\''+templateType+'\')">删除</a></td></tr>'
		+'</tbody></table>'
		/*+'<span>'+mfSysTemplate.templateNameZh+'</span>'*/
		//+ spanStr
		+'</div>'
		/*+'<div class="hover-div">'
		+'<div class="hover-info">'
		+'<label style="cursor: pointer;font-weight: normal;">'
		+inputStr+'是否可签'
		+'</label>'
		+'</div>'
		+'<div class="hover-info">'
		+'<label style="">'
		+'设置模板'
		+'</label>'
		+'</div>'
		+'<div class="hover-info">'
		+'<i class="i i-x2" onclick="MfKindConfig.deleteTemplate(\''+mfSysTemplate.templateNo+'\',\''+mfSysTemplate.templateNo+'\')"></i>'
		+'<span class="i i-x2 span-lajitong" onclick="MfKindConfig.deleteNodeTemplate(this,\''+templateNo+'\',\''+templateNo+'\',\''+templateNo+'\')"></span>'
		+'</div>'
		+'</div>'*/
		+'</div>';
		return htmlStr;
	};
	//新增系统模板
	var _addSysTemplate = function() {
		top.saveFlag = false;
		top.templateType = "";
		var url = webPath +"/mfSysTemplate/addTemplate";
		top.openBigForm(url, '新增模板', function(){
			if(top.saveFlag){
				_addSysTemplateCallBack(top.templateType);
			}
		});
	};
	
	//新增模板后回调
	var _addSysTemplateCallBack=function(templateType){
		$.ajax({
			url : webPath + "/mfSysTemplate/getSysTemplateConfigListAjax",
			data:{templateType:templateType},
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					var templateList = data.mfSysTemplateList;
					var contentHtml = getContentByTemplateGroupHtml(templateList,data.tempMap);
					$("#group"+templateType).html(contentHtml);
					$("span[name='mfSysTemplateUseFlag']").useFlagRcswitcher({
						name:"useFlag",onText:"启用",offText:"停用"});
					//$("span[name='mfcollateralclassname']").collRcswitcher({name:"useFlag",onText:"启用",offText:"停用"});
				}else if(data.flag == "error"){
					window.top.alert(data.msg,0);
				}
			},error:function(){
				window.top.alert(top.getMessage("ERROR"),0);
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	var getContentByTemplateGroupHtml = function(templateList,groupMap){
		var htmlStr = "";
		//循环该节点下的每一个要件templateList
		$.each(groupMap,function(key,value){
			htmlStr =htmlStr +_getGroupHtml(templateList,key,value);
		});
		return htmlStr;
	};
	//获得分组内的内容
	var _getGroupHtml=function(templateList,key,value){
		var htmlStr ="";
		var subHtmlStr ="";
		var countDef = 2;
		var moreHtml = "";
		var num=0;
		var showNum=0;
		var moreNum=0;
		var groupTitleHtml='<div class="title-div title-templateList"><ol class="breadcrumb pull-left padding_left_0" id="title'+key+'">'
		+'<li class="active"><i class="i i-shuxian shuxian"></i><span name="title">'+value+'</span></li></ol></div>';
		htmlStr =htmlStr +groupTitleHtml+ '<div class="sub-content margin_bottom_10 padding_left_15 margin_top_10">';
		$.each(templateList,function(i,mfSysTemplate){
			if(mfSysTemplate.templateType==key){
				var htmlTmp = _getSysTemplateItemHtml(mfSysTemplate);
				if(num < 6){
					//subHtmlStr = subHtmlStr + htmlTmp;
					//每行展示固定数量的模板信息块
					if(showNum < countDef){
						subHtmlStr = subHtmlStr + htmlTmp;
						showNum++;
					}else{
						subHtmlStr = subHtmlStr+"</br>";
						subHtmlStr = subHtmlStr + htmlTmp;
						showNum=1;
					}
				}else{
					//每行展示固定数量的模板信息块
					if(moreNum < countDef){
						moreHtml = moreHtml + htmlTmp;
						moreNum++;
					}else{
						moreHtml = moreHtml+"</br>";
						moreHtml = moreHtml + htmlTmp;
						moreNum=1;
					}
				}
				num++;
			}
		});	
		var moreStr = "";
		var optStr = "";
		if(templateList.length > countDef){
			optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreTemplateItem'+key+'" style="width:25px">'
			+'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
			+'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
			+'</div>';
			moreStr = moreStr+'<div id="moreTemplateItem'+key+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
		}
		htmlStr=htmlStr+ subHtmlStr+moreStr+"</div>";
		return htmlStr;
	};
	//编辑模板基础信息
	var _editTemplateInfo = function(templateNo,templateType) {
		top.editFlag = false;
		var url = webPath+"/mfSysTemplate/getById?templateNo=" + templateNo;
		top.openBigForm(url, '编辑模板', function() {
			if (top.editFlag) {
				_addSysTemplateCallBack(templateType);
			}
		});
	};
	//设计模板
	var _templateSet=function(templateNo,templateNameEn){
		var saveFileName = templateNameEn;
		var fileNameTemp = saveFileName;
		var fileName = saveFileName.substr(0,saveFileName.lastIndexOf("."));
		var filePath = "";
		var saveUrl = "";
		if(fileName=="templateFile"){
			var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
			saveFileName = fileName+"_"+templateNo+fileNameEnd;
			saveUrl = "factor/component/model/updateTemplateName.jsp?templateNo="+templateNo+"&filename="+saveFileName;
		}else{
			saveFileName = fileNameTemp;
		}
		$.ajax({
			url: webPath + "/mfTemplateModelRel/getIfSaveTemplateInfo",
			type:"post",
			data:{templateNo:templateNo},
			async: false,
			dataType:"json",
			success:function(data){
				fileNameTemp = data.fileName;
				filePath = data.filePath;
			},error:function(){alert('error');},
		});
		var poCntJson = {
			filePath : filePath,
			fileName : fileNameTemp,
			saveFileName : saveFileName,
			printBtn : "0",//取消打印按钮
			saveUrl : saveUrl,
			fileType : 0
		};
		if(pageOfficeVersion == "0"){
            poCntJson.markUrl=webPath+"/mfSysTemplate/labelSetBase?fileName="+fileNameTemp;
		}else if(pageOfficeVersion == "1"){
            poCntJson.markUrl=pageOfficePath+"/officeService/pageOffice/labelSetBase?fileName="+fileNameTemp;
		}
		mfPageOffice.editOpen(poCntJson);
	};
	//模板标签设置
	var _templateTagSet=function(templateNo){
		top.saveFlag = false;
		var url = webPath + "/mfTemplateTagSet/baseTagSet?templateNo=" + templateNo;
		top.openBigForm(url, '模板标签配置', function() {
			if (top.saveFlag) {

			}
		});
	};
	//删除模板
	var _deleteTemplate=function(templateNo,templateType){
		alert(top.getMessage("CONFIRM_OPERATION", "删除"), 2, function() {
			$.ajax({
				url:webPath + "/mfSysTemplate/deleteAjax?templateNo="+templateNo,
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag=="success"){
						_addSysTemplateCallBack(templateType);
					}else{
						window.top.alert(data.msg,0);
					}
				}
			});
		});
	};
	return{
		init:_init,
		addSysTemplate:_addSysTemplate,
		editTemplateInfo:_editTemplateInfo,
		templateSet:_templateSet,
		templateTagSet:_templateTagSet,
		deleteTemplate:_deleteTemplate
	};
}(window,jQuery);