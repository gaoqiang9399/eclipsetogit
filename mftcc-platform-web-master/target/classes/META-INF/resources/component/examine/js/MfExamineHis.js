function init(){
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			updateOnContentResize : true
		}
	});
	setTemplateOptions();
	$("input[name=examOpName]").val($("input[name=cusMngName]").val());
	$("input[name=examOpNo]").val($("input[name=cusMngNo]").val());
}
//文档模板和表单模板编辑页面初始化
function init_update(){
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			updateOnContentResize : true
		}
	});
	examHisId = $("input[name=examHisId]").val();
	if(templateType=="1"){
		var docuTemplate = $("input[name=docuTemplate]").val();
		var htmlStr = "<button type='button' class='btn btn-primary' onclick='openDocuTemplate();' style='border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8;'>检查模板</button>";
		var fileNameEnd = docuTemplate.substr(docuTemplate.lastIndexOf("."));
		if(docuTemplate==examHisId+fileNameEnd){
			htmlStr= "<button type='button' class='btn btn-primary' onclick='openDocuTemplate();' style='border-radius: 7px;padding: 6px 54px;background-color: #4bafc5;border-color: #4bafc5;'>检查模板</button>";
		}
		$("input[name=docuTemplate]").hide();
		$("input[name=docuTemplate]").parent().append(htmlStr);
		$("#save").hide();
	}else{
		$("#submit").hide();
	}
}
//给检查模板赋值
function setTemplateOptions(){
	var dataParam = JSON.stringify($("#ecamHisInsertForm").serializeArray());
	jQuery.ajax({
		url:webPath+"/mfExamineTemplateConfig/getConfigMatchedListAjax",
		data:{ajaxData:dataParam},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				var optionHtml = "<option value='' selected=''></option>";
				templateList = data.templateList;
				$.each(data.templateList, function(i, template) {
					if(templateId==template.templateId){
						optionHtml = optionHtml + "<option value='" + template.templateId
						+ "' selected='selected'>" + template.templateName + "</option>";
					}else{
						optionHtml = optionHtml + "<option value='" + template.templateId
						+ "'>" + template.templateName + "</option>";
					}
				});
				$("select[name=templateId]").html(optionHtml);
			}
		},error:function(data){
			 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
/*//检查模板变化时，动态获得检查使用的模板或表单
function getExcaTemplateType(){
	var templateId = $("select[name=templateId]").val();
	var trObj=$("select[name=templateId]").parents("tr");
	if(templateId!=""){
		$.each(templateList, function(i, template) {
			if(templateId==template.templateId){
				templateType = template.templateType;
				if(templateType=="1"){
					var docuTemplate = template.docuTemplate;
					$("input[name=docuTemplate]").val(docuTemplate);
					$("input[name=formTemplate]").val("");
					trObj.nextAll("tr").remove();
					var htmlStr = '<tr><td class="tdlable right" colspan="1" rowspan="1">'+
					'<label class="control-label "><font color="#FF0000">*</font>检查模板</label></td>'+
					'<td class="tdvalue right" colspan="1" rowspan="1"><div class="input-group">'+
					'<input type="text" title="检查人员" name="docuTemplateShow" datatype="0"  mustinput="" onblur="func_uior_valTypeImm(this);"'+
					'onmousedown="enterKey()" onkeydown="enterKey();" value="检查模板"></div></td><td></td><td></td></tr>';
					trObj.after(htmlStr);
					var htmlButton = "<button type='button' class='btn btn-primary' onclick='openDocuTemplate();' style='border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8;'>检查模板</button>";
					if(docuSaveFlag=="1"){
						$("input[name=docuTemplate]").val(docuTemplateNew);
						htmlButton= "<button type='button' class='btn btn-primary' onclick='openDocuTemplate();' style='border-radius: 7px;padding: 6px 54px;background-color: #4bafc5;border-color: #4bafc5;'>检查模板</button>";
					}
					$("input[name=docuTemplateShow]").hide();
					$("input[name=docuTemplateShow]").parent().append(htmlButton);
				}
				if(templateType=="2"){
					$("div[formtype=Bootstarp]").next().remove();
					var formTemplate = template.formTemplate;
					$("input[name=formTemplate]").val(formTemplate);
					$("input[name=docuTemplate]").val("");
					trObj.nextAll("tr").remove();
					jQuery.ajax({
						url:webPath+"/mfExamineHis/getEditFormTemplateAjax",
						data:{formTemplate:formTemplate},
						type:"POST",
						dataType:"json",
						beforeSend:function(){
						},success:function(data){
							if(data.flag == "success"){
								$("#ecamHisInsertForm").append(data.htmlStr);
								trObj.after($("#ecamHisInsertForm").find("table").eq(1).find("tbody").html());
								$("#ecamHisInsertForm").find("table").eq(1).remove();
							}
						},error:function(data){
							window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
				$("input[name=templateName]").val(template.templateName);
			}
		});
	}else{
		trObj.nextAll("tr").remove();
	}
}*/
//打开检查文档模板
function openDocuTemplate(){
	var saveFileName = $("input[name=docuTemplate]").val();
	if(docuSaveFlag=="1"){
		saveFileName = docuTemplateNew;
	}
	var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
	var fileNameTmp = examHisId+fileNameEnd;
	var pathFileName ="";
	var datatmp="";
	var flag = "0";//新增和编辑标识 0新增1编辑
	if(saveFileName==fileNameTmp){//编辑，保存后再次打开
		flag ="1";
		pathFileName = "/factor/component/examine/saveDocu/"+saveFileName;
	}else{//新增 第一次打开
		$.ajax({
			url: webPath+"/mfToPageOffice/getDataMapComm",
			type:"post",
			data:{"modelId":'',"filename":'examDocuTemp.doc',"cifno":cusNo,"pactno":pactId,"appno":appId,"loanNo":'',"traceNo":''},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				datatmp = JSON.stringify(data);
			}
		});
		pathFileName = "/factor/component/examine/docuTemplate/"+saveFileName;
	}
	var savePath = "/factor/component/examine/saveDocu/";
	saveFileName = examHisId+fileNameEnd;
	var examOpNo = $("input[name=examOpNo]").val();
	var beginDate = $("input[name=beginDate]").val();
	var endDate = $("input[name=endDate]").val();
	var templateId = $("select[name=templateId]").val();
	var returnUrl = window.location.href;
	/**
	 * 第一次打开模板拼接参数，当模板保存后，
	 * 返回的路径里面已经有这些参数，再次打开是不需要再次拼接参数。
	 * 
	 */
	if(returnUrl.indexOf("docuSaveFlag")==-1){
		returnUrl = returnUrl+"&templateId="+templateId+"&examOpNo="+examOpNo+"&beginDate="+beginDate+"&endDate="+endDate+"&saveFileName="+saveFileName+"&docuSaveFlag=0";
	}
	saveUrl = saveUrl+"?examHisId="+examHisId+"&filename="+saveFileName+"&returnUrl="+returnUrl+"&templateType="+templateType;
	var poCntJson = {
		pathFileName : "" + pathFileName,
		savePath : "" + savePath,
		saveFileName : saveFileName,
		fileType : 0
	};
	poCntJson.saveUrl = saveUrl;
	poCntJson.returnUrl = returnUrl;
	poCntJson.printBtn="0";//取消打印按钮
	var poCnt = JSON.stringify(poCntJson);
	var url=webPath + "/pageoffice/pageOfficeFactor.do?method=pageOfficeAddCommon&poCnt="+encodeURIComponent(poCnt)+"&datatmp="+encodeURIComponent(encodeURIComponent(datatmp));
	if(flag=="1"){//编辑
		url=webPath + "/pageoffice/pageOfficeFactor.do?method=pageOfficeEdit&poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
	}
	window.open(url, '_self', '');
}
//给检查人赋值
function getExamOpName(userInfo){
	$("input[name=examOpName]").val(userInfo.opName);
	$("input[name=examOpNo]").val(userInfo.opNo);
}
//新增保存
function ajaxInsertThis(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		if(docuSaveFlag!="1"&&templateType=="1"){
			window.top.alert(top.getMessage("FIRST_COMPLETE_INFORMAATION","检查模板内容"),0);
			return;
		}
		alert(top.getMessage("CONFIRM_OPERATION","贷后检查提交"),2,function(){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url:url+"?templateType="+templateType,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						$("input[name=examHisId]").val(data.examHisId);
						examHisId = data.examHisId;
						submitAjax();
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	}
}
//编辑保存
function ajaxUpdateThis(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
				url:webPath+"/mfExamineHis/updateAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
}
//提交检查到审批
function submitAjax(){
	var examHisId = $("input[name=examHisId]").val();
	LoadingAnimate.start();
	jQuery.ajax({
		url:webPath+"/mfExamineHis/submitAjax",
		data:{examHisId:examHisId},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			LoadingAnimate.stop();
			if(data.flag == "success"){
				window.top.alert(data.msg, 3);
				myclose_click();
			}
		},error:function(data){
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	/*if(examDetailFlag=="1"){
	}
	if(examDetailFlag=="0"){
		window.top.alert("请先完成检查内容登记！", 0);
	}*/
}
//保存检查表单内容
function saveFormTemplateAjax(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfExamineDetail/insertDetailAjax",
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					window.top.alert(data.msg, 1);
					examDetailFlag = "1";
					//$("#submit").show();
					//$("#save").hide();
				}
			},error:function(data){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}

//根据检查模板不同动态展示检查模板内容
function getExcaTemplate(){
	var templateId = $("select[name=templateId]").val();
	$.each(templateList, function(i, template) {
		if(templateId==template.templateId){
			templateType = template.templateType;
			if(templateType=="1"){
				var docuTemplate = template.docuTemplate;
				$("input[name=docuTemplate]").val(docuTemplate);
				$("div[formtype=Bootstarp]").eq(1).remove();
				$("#ecamHisInsertForm").append("<div class='rows'><label class='form-label right '>检查模板</label>" +
						"<div class='input-box left'><input type='text' title='检查人员' name='docuTemplateShow' datatype='0'" +
						" mustinput='' onblur='func_uior_valTypeImm(this);' onclick='openDocuTemplate();' " +
						"onmousedown='enterKey()' onkeydown='enterKey();' value='检查模板'>" +
						"</div></div>");
			}
			if(templateType=="2"){
				$("div[formtype=Bootstarp]").next().remove();
				var formTemplate = template.formTemplate;
				$("input[name=formTemplate]").val(formTemplate);
				jQuery.ajax({
					url:webPath+"/mfExamineHis/getFormTemplateAjax",
					data:{formTemplate:formTemplate},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							$("#ecamHisInsertForm").append(data.htmlStr);
						}
					},error:function(data){
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
			$("input[name=templateName]").val(template.templateName);
		}
	});
}