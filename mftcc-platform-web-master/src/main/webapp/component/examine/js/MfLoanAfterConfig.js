;
var MfLoanAfterConfig=function(window,$){
	var _init=function(){
		_showDocConfigHtml();
		_getTemplateConfigHtml();
		//处理要件配置的横向滚动条
		$(".scroll-div").mCustomScrollbar({
			horizontalScroll : true
		});
		_scrollUpdate();
		_showRepaymentDocConfig();
	};
	//新增贷后检查模型
	var _addExamineTemplateConfig=function(templateType){
		top.flag=false;
		top.templateId="";
		top.templateName="";
		top.docuTemplate="";
		top.remark="";
		top.examTempConfig="";
		top.window.openBigForm(webPath+"/mfExamineTemplateConfig/input?templateType="+templateType,"检查模型配置",function(){
			if(top.flag){

				if (templateType == "0"){
                    var scno="loanAfterExamine";
                    var docContentHtml='<div id="docConfig'+top.examTempConfig.templateId+'" data-node="'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15 node-doc-config  ">'
                        +'<div class="sub-title margin_bottom_10 margin_top_10">'
                        +'<span>要件设置</span>'
                        +'</div>'
                        +'<div class="sub-content margin_bottom_10 padding_left_15">'
                        +'<div class="item-div" data-tempserialid="'+top.examTempConfig.tempSerialId+'" data-scno="'+scno+'"><div class="scroll-div">'
                        +'<div class="add-item">'
                        +'<div class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'<div class="fixed-add-div">'
                        +'<div  class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>';
                    var addDocTemplateHtmlStr ='<div class="add-item">'
                        +'<div class="add-div" onclick="MfLoanAfterConfig.addTemplate('+top.examTempConfig.templateId+');">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'<div class="fixed-add-div">'
                        +'<div  class="add-div" onclick="MfLoanAfterConfig.addTemplate('+top.examTempConfig.templateId+');">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>';
                    var htmlStr='<div id="base'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15">'
                        +'<div class="sub-title">'
                        +'<span id="templateName'+top.examTempConfig.templateId+'">'
                        +'<a id="edit_'+top.examTempConfig.templateId+'" class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.editExamineTemplateConfig('+top.examTempConfig.templateId+')">'
                        +top.examTempConfig.templateName
                        +'</a>'
                        +'</span>'
                        +'<span id="remark'+top.examTempConfig.templateId+'" style="font-weight:normal">'+top.examTempConfig.remark+'</span>'
                        +'<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.setExamConfig('+top.examTempConfig.templateId+')">检查项</a>'
                        +'<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.examFormDesign('+top.examTempConfig.templateId+')">表单配置</a>'
                        +'</div>'
                        +'</div>'
                        +'<div id="uploadInfo'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15 bus-config max-width">'
                        +docContentHtml
                        +'</div>'
                        +'<div id="docTemplateInfo'+top.examTempConfig.templateId+'" class=" sub-content-div padding_left_15 template-config bus-config max-width" style="margin-left:17px">'
                        +'<div class="sub-title">'
                        +'<span>模板配置</span>'
                        +'</div>'
                        +'<div id="docTemplateContent'+top.examTempConfig.templateId+'" class="sub-content margin_bottom_10 padding_left_15">'
                        +addDocTemplateHtmlStr
                        +'</div>'
                        +'</div>';
                    $("#examine-content-div").append(htmlStr);
				} else{
                    var scno="loanAfterExamine";
                    var docContentHtml='<div id="docConfig'+top.examTempConfig.templateId+'" data-node="'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15 node-doc-config  ">'
                        +'<div class="sub-title margin_bottom_10 margin_top_10">'
                        +'<span>要件设置</span>'
                        +'</div>'
                        +'<div class="sub-content margin_bottom_10 padding_left_15">'
                        +'<div class="item-div" data-tempserialid="'+top.examTempConfig.tempSerialId+'" data-scno="'+scno+'"><div class="scroll-div">'
                        +'<div class="add-item">'
                        +'<div class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'<div class="fixed-add-div">'
                        +'<div  class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>';
                    var addDocTemplateHtmlStr ='<div class="add-item">'
                        +'<div class="add-div" onclick="MfLoanAfterConfig.addTemplate('+top.examTempConfig.templateId+');">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'<div class="fixed-add-div">'
                        +'<div  class="add-div" onclick="MfLoanAfterConfig.addTemplate('+top.examTempConfig.templateId+');">'
                        +'<i class="i i-jia2"></i>'
                        +'</div>'
                        +'</div>'
                        +'</div>';
                    var htmlStr='<div id="base'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15">'
                        +'<div class="sub-title">'
                        +'<span id="templateName'+top.examTempConfig.templateId+'">'
                        +'<a id="edit_'+top.examTempConfig.templateId+'" class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.editExamineTemplateConfig('+top.examTempConfig.templateId+')">'
                        +top.examTempConfig.templateName
                        +'</a>'
                        +'</span>'
                        +'<span id="remark'+top.examTempConfig.templateId+'" style="font-weight:normal">'+top.examTempConfig.remark+'</span>'
                        +'<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.examFormDesign('+top.examTempConfig.templateId+')">表单配置</a>'
                        +'</div>'
                        +'</div>'
                        +'<div id="uploadInfo'+top.examTempConfig.templateId+'" class="sub-content-div padding_left_15 bus-config max-width">'
                        +docContentHtml
                        +'</div>'
                        +'<div id="docTemplateInfo'+top.examTempConfig.templateId+'" class=" sub-content-div padding_left_15 template-config bus-config max-width" style="margin-left:17px">'
                        +'<div class="sub-title">'
                        +'<span>模板配置</span>'
                        +'</div>'
                        +'<div id="docTemplateContent'+top.examTempConfig.templateId+'" class="sub-content margin_bottom_10 padding_left_15">'
                        +addDocTemplateHtmlStr
                        +'</div>'
                        +'</div>';
                    $("#audita-content-div").append(htmlStr);
				}
			}
		});
	};

	//编辑贷后检查模板
	var _editExamineTemplateConfig=function(templateId){
		top.flag=false;
		top.deleteFlag=false;
		top.templateName="";
		top.remark="";
		window.parent.openBigForm(webPath+"/mfExamineTemplateConfig/getById?templateId="+templateId,"检查模型配置",function(){
			if(top.flag){
				$("#edit_"+templateId).html(top.templateName);
				$("#remark"+templateId).html(top.remark);
			}
			if(top.deleteFlag){
				$("#base"+templateId).remove();
				$("#uploadInfo"+templateId).remove();
				$("#docTemplateInfo"+templateId).remove();
			}
		});
	};
	//贷后结论表单配置
	var _examFormDesign=function(templateId){
		var url=webPath+"/mfExamineTemplateConfig/copyFormTemplateAjax";
		$.ajax({
			url:url,
			data:{templateId:templateId},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					var url = webPath+'/tech/dragDesginer/openForm.action?formId='+data.formTemplate;
					window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
				}else{
					alert(top.getMessage("ERROR_DATA_CREDIT","表单文件"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL",""),0);
			}
		});
	};
	//添加检查项配置页面
	var _setExamConfig=function(templateId){
		var url= webPath+"/mfExamineTemplateConfig/getExamineIndexConfigSetting?templateId="+templateId;
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	}
	
	var _getExamDocConfig=function(tempSerialId){
		window.parent.openBigForm(webPath+"/mfExamineTemplateConfig/getExamDocConfig?tempSerialId="+tempSerialId,"检查模型要件配置",function(){
		});
	};
	var _dcouTemplateSet=function(templateId,docuTemplate){
		var saveFileName = docuTemplate;
		var pathFileName = "/factor/component/model/docmodel/"+saveFileName;
		var savePath = "/factor/component/examine/docuTemplate/";
		var fileName = saveFileName.substr(0,saveFileName.lastIndexOf("."));
		if(fileName=="examDocuTemp"){
			var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
			saveFileName = fileName+"_"+templateId+fileNameEnd;
		}else{
			pathFileName = "/factor/component/examine/docuTemplate/"+saveFileName;
		}
		var returnUrl = window.location.href;
		var saveUrl = basePath+"component/examine/updateDcouTemplate.jsp?templateId="+templateId+"&filename="+saveFileName+"&returnUrl="+returnUrl;
		var poCntJson = {
			fileName : fileName,
			savePath : "" + savePath,
			saveFileName : saveFileName,
			fileType : 0
		};
		poCntJson.returnUrl = returnUrl;
		poCntJson.saveUrl = saveUrl;
		poCntJson.markUrl="${webPath}MfSysTemplateAction_labelSetBase.action?fileName=examDocuTemp.doc";
		poCntJson.printBtn="0";//取消打印按钮
		var poCnt = JSON.stringify(poCntJson);
		var url=webPath +"/pageoffice/pageOfficeFactor.do?method=pageOfficeEdit&poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
		window.open(url, '_self', '');
	};
	//编辑贷后风险级别
	var _editExamRiskLevel=function(configId){
		top.addFlag=true;
		top.examRiskLevelConfig="";
		window.parent.openBigForm(webPath+"/mfExamRiskLevelConfig/getById?configId="+configId,"贷后检查风险级别配置",function(){
			if(top.addFlag){
				$("#remark"+top.examRiskLevelConfig.configId).html(top.examRiskLevelConfig.remark);
				$("#opScore"+top.examRiskLevelConfig.configId).html("检查分值&nbsp;" +top.examRiskLevelConfig.opScore1+"--"+top.examRiskLevelConfig.opScore2);
			}
		});
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
	//展示模型要件配置信息
	var _showDocConfigHtml=function(){
		 $.each(mfExamineTemplateConfigListData,function(i,mfExamineTemplateConfig){
			 _getDocConfigHtml(mfExamineTemplateConfig,"docBizSceConfigList"+mfExamineTemplateConfig.templateId);
    	 });
        //展示风险模型配置要件配置信息
        $.each(mfExamineTemplateConfigRiskListData,function(i,mfExamineTemplateConfig){
            _getDocConfigHtml(mfExamineTemplateConfig,"docBizSceConfigList"+mfExamineTemplateConfig.templateId);
        });
	}
	var _getDocConfigHtml=function(mfExamineTemplateConfig,index){
		var docContentHtml = _getDocContentHtml(mfExamineTemplateConfig,docBizSceConfigData[index]);
		var htmlStr = "";
		var templateId=mfExamineTemplateConfig.templateId;
		htmlStr = htmlStr+'<div id="docConfig'+templateId+'" data-node="'+templateId+'" class="sub-content-div padding_left_15 node-doc-config  ">'
							+'<div class="sub-title margin_bottom_10 margin_top_10">'
							+'<span class="font-weight-normal">要件设置</span>'
							+'</div>'
							+'<div class="sub-content margin_bottom_10 padding_left_15">'
							+docContentHtml
							+'</div>'
						+'</div>';
		$("#uploadInfo"+templateId).append(htmlStr);
	};
	var _getDocContentHtml=function(mfExamineTemplateConfig,docBizSceConfigList){
		var htmlStr = "";
		var scno="loanAfterExamine";
		//循环该节点下的每一个要件docBizSceConfigList
		htmlStr =htmlStr + '<div class="item-div" data-tempserialid="'+mfExamineTemplateConfig.tempSerialId+'" data-scno="'+scno+'"><div class="scroll-div">';
		$.each(docBizSceConfigList,function(i,docConfig){
			var inputStr = "";
			var spanStr = "";
			if(docConfig.ifMustInput == "1"){
				inputStr = '<input type="checkbox" onclick="MfLoanAfterConfig.changeIsMust(this);" checked=true >';
				spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
			}else{
				inputStr = '<input type="checkbox" onclick="MfLoanAfterConfig.changeIsMust(this);">';
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
				+'<span class="i i-lajitong1 span-lajitong" onclick="MfLoanAfterConfig.deleteDocTypeItem(this);"></span>'
				+'</div>'
				+'</div>'
				+'</div>';
		});	
		htmlStr =htmlStr +'<div class="add-item">'
						+'<div class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
						+'<i class="i i-jia2"></i>'
						+'</div>'
						+'</div>'
			        +'</div>'
				        +'<div class="fixed-add-div">'
				        +'<div  class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getDocTypeData(this);">'
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
	
		docBizSceConfig.dime1 = $blockItem.parents(".item-div").data("tempserialid");
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
		var dime1 = $blockItem.data("tempserialid");
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
	var _getEventObj = function(obj){
		eventObj = obj;
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
		selectDocTypeDialog(_docTypeSelCall,docSplitNo,doctype);
	};
	//文档模型选择后 回调函数
	var _docTypeSelCall=function(doc){
		var $itemDiv = $(eventObj).parents(".item-div");
		var $scrollDiv = $(eventObj).parents(".item-div").find(".scroll-div");
		
		$scrollDiv.find(".block-item").remove();
		var docNo = doc.docNo.split("@");
		var docName = doc.docName.split("@");
		var docType = doc.docType.split("@");
		var formNo = doc.formNo.split("@");
		
		var tempSerialId = $itemDiv.data("tempserialid");
		var scNo = $itemDiv.data("scno");
		var htmlStr="";
		var dataParmList = [];
		$.each(docName, function(i, docThis) {
			if (docNo[i]) {
				var entity = {};
				entity.dime1 =tempSerialId;
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
					var mfExamineTemplateConfig = {};
					mfExamineTemplateConfig.tempSerialId = tempSerialId;
					var htmlStr = _getDocContentHtml(mfExamineTemplateConfig,docList);
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
	//要件配置 end
	//模板配置 start
	var _getTemplateConfigHtml=function(){
		 //内容
		 $.each(mfExamineTemplateConfigListData,function(i,mfExamineTemplateConfig){
            var index="mfExamineDocTemplateList"+mfExamineTemplateConfig.templateId;
            _getTemplateContentHtml(mfExamineTemplateConfig.templateId,examineDocTemplateData[index]);
        });
        //展示风险模型配置要件配置信息
        $.each(mfExamineTemplateConfigRiskListData,function(i,mfExamineTemplateConfig){
            var index="mfExamineDocTemplateList"+mfExamineTemplateConfig.templateId;
            _getTemplateContentHtml(mfExamineTemplateConfig.templateId,examineDocTemplateData[index]);
        });
	};
	var _getTemplateContentHtml=function(templateId,examineDocTemplateList){
		//循环产品下的模板项
		var subHtmlStr ="";
		var moreHtml = "";
		var htmlStr ="";
		var addHtmlStr ='<div class="add-item">'
			+'<div class="add-div" onclick="MfLoanAfterConfig.addTemplate('+templateId+');">'
			+'<i class="i i-jia2"></i>'
			+'</div>'
			+'</div>'
		    +'</div>'
		        +'<div class="fixed-add-div">'
		        +'<div  class="add-div" onclick="MfLoanAfterConfig.addTemplate('+templateId+');">'
		        +'<i class="i i-jia2"></i>'
		        +'</div>'
		        +'</div>'
		    +'</div>';
		if(examineDocTemplateList){
			$.each(examineDocTemplateList,function(i,examineDocTemplate){
				var htmlTmp = _getTemplateItemHtml(examineDocTemplate);
				if(i < countDef){
					subHtmlStr = subHtmlStr + htmlTmp;
				}else{
					moreHtml = moreHtml + htmlTmp;
				}
			});
			var moreStr = "";
			var optStr = "";
			if(examineDocTemplateList.length > countDef){
				optStr='<div class="item-opt collapsed" data-toggle="collapse" data-target="#moreTemplateItem'+templateId+'">'
						+'<span class="more-span "><a>更多<i class="i i-open-down item-icon"></i></a></span>'
						+'<span class="close-span "><a>收起<i class="i i-close-up item-icon"></i></a></span>'
						+'</div>';
				moreStr = moreStr+'<div id="moreTemplateItem'+templateId+'" class="more-div collapse">'+moreHtml+'</div>'+optStr;
			}
			htmlStr ='<div class="item-div">'+ subHtmlStr+addHtmlStr+moreStr+'</div>';
		}else{
			htmlStr=htmlStr+addHtmlStr;
		}
		
		$("#docTemplateContent"+templateId).html("");
		$("#docTemplateContent"+templateId).append(htmlStr);
	};
	/**产品下模板项html**/
	var _getTemplateItemHtml = function(examineDocTemplate){
		var imgClass = "item-word";
		var htmlStr ='<div id="'+examineDocTemplate.templateNo+'" class="block-item">'
			+'<div class="item-title '+imgClass+'" onclick="MfLoanAfterConfig.templateSet(\''+examineDocTemplate.id+'\',\''+examineDocTemplate.templateNameEn+'\');">'
			+'<span>'+examineDocTemplate.templateNameZh+'</span>'
			+'</div>'
			+'<i class="i i-x2" onclick="MfLoanAfterConfig.deleteTemplate(this,\''+examineDocTemplate.id+'\')"></i>'
			+'</div>';
		return htmlStr;
	};
	//设置模板
	var _templateSet = function(id,saveFileName){
		if(saveFileName=="templateFile"){
			var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
			saveFileName = id+fileNameEnd;
		}
		var poCntJson = {
			fileName : saveFileName,
			filePath : "/data/factor/model/docmodel/",
			printBtn : "0",//取消打印按钮
			markUrl : webPath+"/mfSysTemplate/labelSetBase?fileName="+saveFileName+"&pageOffice=pass",
			fileType : 0
		};
		mfPageOffice.openPageOffice(poCntJson);
	};
	
	//新增模板
	var _addTemplate = function(templateId){
		top.itemId="";
		top.flag= false;
		var templateNoStr="";
		$.each($("#docTemplateContent"+templateId).find(".block-item"),function(i,obj){
			templateNoStr=templateNoStr+$(obj).attr("id")+",";
		});
		window.parent.openBigForm(webPath+"/mfSysTemplate/getAllTemplateList?templateNoStr="+templateNoStr,"选择模板",function(){
			if(top.flag){
				//异步更新产品下设置的节点定制
				_addTemplateCallBack(top.itemId,templateId);
			}
		},"790px","450px");
	};
	var _addTemplateCallBack=function(itemId,templateId){
		$.ajax({
			url : webPath+"/mfExamineDocTemplate/insertAjax?examineModelId="+templateId+"&templateNo="+encodeURI(itemId),
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					var templateList = data.examineDocTemplateList;
					var contentHtml = _getTemplateContentHtml(templateId,templateList);
					//处理滚动条
					var $scrollDiv = $("#docTemplateContent"+templateId).find(".scroll-div");
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
	
	var _deleteTemplate = function(obj,id){
		var $blockItem = $(obj).parents(".block-item");
		alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
			$.ajax({
				url : webPath+"/mfExamineDocTemplate/deleteAjax",
				data:{id:id},
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
	//模板配置 end

	//更新参数启用禁用状态
	var _updateParamUseFlag = function(obj,keyName){
		var useflag = $(obj).data("useflag");
		if(useflag=="1"){//禁用
			$(obj).removeClass("curChecked");
			useflag = "0";
		}else{//启用
			$(obj).addClass("curChecked");
			useflag = "1";
		}
		$.ajax({
			url:webPath+"/mfExamineTemplateConfig/updateParamUseFlagAjax",
			data:{keyName:keyName,preRepayApplyFlag:useflag},
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				$(obj).data("useflag",useflag);
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	//更新核销设置参数
	var _updateCheckSetting = function(obj){
		var setVal="";
		var tmpFlag=$(obj).prop("checked");
		if(tmpFlag){
			setVal=$(obj).val();
		}
	
		
		
		if(setVal=="2"){
			//如果是逾期天数 /展示天数设置
			$("#overdaysSettingSpan").show();
			
		}else if(setVal=="1"){
			$("#overdaysSettingSpan").hide();
		}else{
			return ;
		}
		
		var overDays=$("#checkoffDatasourceOverDays").val();
		$.ajax({
			url:webPath+"/mfExamineTemplateConfig/updateCheckOffParamUseFlagAjax",
			data:{keyName:"CHECKOFF_DATASOURCE","checkoffDatasource":setVal,"checkoffDatasourceOverDays":overDays},
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	//展示还款要件配置
	var _showRepaymentDocConfig=function(){
		var docContentHtml="";
		var htmlStr = "";
		$.ajax({
			url:webPath+"/mfRepaymentConfig/getRepaymentDocConfigAjax",
			data:{},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					//正常还款
					var normalDocConfigList=data.normalDocConfigList;
					var docType="normalDoc";
					docContentHtml=_getRepaymentDocContentHtml(docType,normalDocConfigList);
					htmlStr = '<div id="docConfig'+docType+'" data-node="'+docType+'" class="sub-content-div padding_left_15 node-doc-config  ">'
					+'<div class="sub-title margin_bottom_10 margin_top_10">'
					+'<span class="font-weight-normal">要件设置</span>'
					+'</div>'
					+'<div class="sub-content margin_bottom_10 padding_left_15">'
					+docContentHtml
					+'</div>'
					+'</div>';
					$("#uploadInfo-"+docType).append(htmlStr);
					//提前还款
					var advanceDocConfigList=data.advanceDocConfigList;
					docType="advanceDoc";
					docContentHtml=_getRepaymentDocContentHtml(docType,advanceDocConfigList);
					htmlStr = '<div id="docConfig'+docType+'" data-node="'+docType+'" class="sub-content-div padding_left_15 node-doc-config  ">'
					+'<div class="sub-title margin_bottom_10 margin_top_10">'
					+'<span class="font-weight-normal">要件设置</span>'
					+'</div>'
					+'<div class="sub-content margin_bottom_10 padding_left_15">'
					+docContentHtml
					+'</div>'
					+'</div>';
					$("#uploadInfo-"+docType).append(htmlStr);
					
					//处理要件配置的横向滚动条
					$(".scroll-div").mCustomScrollbar({
						horizontalScroll : true
					});
					_scrollUpdate();
				}else{
					alert(top.getMessage("ERROR_DATA_CREDIT","表单文件"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL",""),0);
			}
		});
		
	};
	
	var _getRepaymentDocContentHtml=function(docType,docBizSceConfigList){
		var htmlStr = "";
		//循环该节点下的每一个要件docBizSceConfigList
		htmlStr =htmlStr + '<div class="item-div" data-tempserialid="'+docType+'" data-scno="'+docType+'"><div class="scroll-div">';
		$.each(docBizSceConfigList,function(i,docConfig){
			var inputStr = "";
			var spanStr = "";
			if(docConfig.ifMustInput == "1"){
				inputStr = '<input type="checkbox" onclick="MfLoanAfterConfig.changeIsMust(this);" checked=true >';
				spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
			}else{
				inputStr = '<input type="checkbox" onclick="MfLoanAfterConfig.changeIsMust(this);">';
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
				+'<span class="i i-lajitong1 span-lajitong" onclick="MfLoanAfterConfig.deleteDocTypeItem(this);"></span>'
				+'</div>'
				+'</div>'
				+'</div>';
		});	
		htmlStr =htmlStr +'<div class="add-item">'
						+'<div class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getRepaymentDocTypeData(this);">'
						+'<i class="i i-jia2"></i>'
						+'</div>'
						+'</div>'
			        +'</div>'
				        +'<div class="fixed-add-div">'
				        +'<div  class="add-div" onclick="MfLoanAfterConfig.getEventObj(this);MfLoanAfterConfig.getRepaymentDocTypeData(this);">'
				        +'<i class="i i-jia2"></i>'
				        +'</div>'
				        +'</div>'
			        +'</div>';
		return htmlStr;
	};
	//新增要件配置弹层
	var _getRepaymentDocTypeData =function(obj){
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
		selectDocTypeDialog(_repaymentdocTypeSelCall,docSplitNo,doctype);
	};
	
	//文档模型选择后 回调函数
	var _repaymentdocTypeSelCall=function(doc){
		var $itemDiv = $(eventObj).parents(".item-div");
		var $scrollDiv = $(eventObj).parents(".item-div").find(".scroll-div");
		
		$scrollDiv.find(".block-item").remove();
		var docNo = doc.docNo.split("@");
		var docName = doc.docName.split("@");
		var docType = doc.docType.split("@");
		var formNo = doc.formNo.split("@");
		
		var tempSerialId = $itemDiv.data("tempserialid");
		var scNo = $itemDiv.data("scno");
		var htmlStr="";
		var dataParmList = [];
		$.each(docName, function(i, docThis) {
			if (docNo[i]) {
				var entity = {};
				entity.dime1 =tempSerialId;
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
					var htmlStr = _getDocContentHtml(tempSerialId,docList);
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
	return{
		init:_init,
		addExamineTemplateConfig:_addExamineTemplateConfig,
		editExamineTemplateConfig:_editExamineTemplateConfig,
		examFormDesign:_examFormDesign,
		setExamConfig:_setExamConfig,
		getExamDocConfig:_getExamDocConfig,
		dcouTemplateSet:_dcouTemplateSet,
		editExamRiskLevel:_editExamRiskLevel,
		changeIsMust:_changeIsMust,
		deleteDocTypeItem:_deleteDocTypeItem,
		getEventObj:_getEventObj,
		getDocTypeData:_getDocTypeData,
		addTemplate:_addTemplate,
		deleteTemplate:_deleteTemplate,
		templateSet:_templateSet,
		updateParamUseFlag:_updateParamUseFlag,
		updateCheckSetting:_updateCheckSetting,
		getRepaymentDocTypeData:_getRepaymentDocTypeData
	};
}(window,jQuery);