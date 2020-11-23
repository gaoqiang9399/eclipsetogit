;
var ExamineApply4Risk=function(window,$){
	var showData = {
			dx:{//定性
				"indexName":"指标名称",
				"ctrl_btn":"打分选项"},
		};
	var _init=function(){
		//是否是查看 getFlag查看标志  true 为是
		if(getFlag===undefined){
			getFlag = false;
		}else if(getFlag=="apply"){
			getFlag = true;
		}
		if(getFlag==true){//查看时将选择财报 不显示
			$("#chosefinButton").hide();
		}
		var bodyWidth = $("body").width();
		var liLength= $(".content_ul li").length;
		$(".content_ul").css({width:(liLength)*bodyWidth+"px",left:"-200%"});
		for(var i=0;i<liLength;i++){
			if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
				$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
				continue;
			}
			$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
		}
		//选项卡添加切换事件
		_liClick();
		//初始化页面的表头
		_initThead("",showData);
		$(window).resize(function() {
			var bodyWidth = $("body").width();
			var bodyheight = $("body").height();
			var liLength= $(".content_ul li").length;
			$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
			for(var i=0;i<liLength;i++){
				if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
					$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
					continue;
				}
				$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
			}
		});
		//调整下拉事件
		$("select[name=restrictLevel]").attr("onfocus","this.defaultIndex=this.selectedIndex;");
		$("select[name=restrictLevel]").attr("onchange","this.selectedIndex=this.defaultIndex;");
		if(getFlag==true){
			//查看更改操作状态
			$(".showprogress ul li").addClass("success");
			$(".showprogress ul li").eq(0).addClass("selected");
			//检查模型配置了检查项
			if(dataMap.indexFlag=="1"){
				//初始化查看数据
				ExamCardInitData.initData(dataMap.listData);
				$.each(dataMap.examCardListData,function(iArgs,obj){
					if(dataMap["dxData"+obj.examineCardId]){
						var str="dxData"+obj.examineCardId;
						if(dataMap[str].score=="0"||dataMap[str].score==null){
							$(".showprogress ul").find("li[name=dx]").removeClass("success");
							$(".showprogress ul").find("li[name=dx]").click();
						}else{
							//只执行一次
							if(iArgs==0){
								$(".content_ul li").find("input[type=radio]").each(function(index){
									$(this).prop("checked",false);
									$(this).removeAttr("checked");
								});
							}
							var dxList = dataMap[str].scoreList;
							if(dxList!=null&&dxList!=undefined){
								for(var i=0;i<dxList.split("@").length-1;i++){
									var dxobj = dxList.split("@")[i];
									var name = dxobj.split(":")[0];
									var vuale = dxobj.split(":")[1];
									$(".content_ul li div[name=dx"+obj.examineCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
										$(this).prop("checked",true);
										$(this).attr("checked","checked");
									});
								}
							}
							$(".content_ul li div[name=dx"+obj.examineCardId+"]").find("input[type=radio]").attr("disabled","true");
							$("#editEval").show();
							$("#addEval").hide();
							$(".showprogress ul").find("li[name=evalapp]").click();
							
						}
					}
				});
				
			}else if(dataMap.indexFlag=="0"){//检查模型没有配置检查项
				$(".showprogress ul li[name=dx]").addClass("success");
				$(".showprogress ul li[name=dx]").hide();
				$(".showprogress ul").find("li[name=evalapp]").click();
			}
		}
		//加载完成 显示数据
		$(".eval-content").css({"display":"block"});
		//隐藏财务、定量、定性右上角总分
		$(".content_ul li").find(".li_title .scoreShow").hide();
		//隐藏页面中所有thead
		$("thead").hide();
		var check = $(".selected").attr("name");
		if(check == 'chosefin'){
			ExamineApplySave4Risk.examApplySaveAjax("#ecamHisInsertForm");
			$("div[id=gradeCard] div[id=moreBar]").remove();
			$("div[id=evalapp] div[id=moreBar]").remove();
		}
		if(check == 'dx'){
			$("div[id=evalapp] div[id=moreBar]").remove();
			myCustomScrollbarForForm({
				obj:"#gradeCard",
				advanced : {
					updateOnContentResize : true
				}
			});
		}
		if(check == 'evalapp'){
			$("div[id=gradeCard] div[id=moreBar]").remove();
			myCustomScrollbarForForm({
				obj:"#evalapp",
				advanced : {
					updateOnContentResize : true
				}
			});
		}
		$("#evalappButton").css("position","fixed");
	};
	//给选项卡添加事件
	var _liClick=function(){
		$(".showprogress ul li").click(function(){
			var $this = $(this);
			var name = $this.attr("name");
			var changeFlag =  false;
			if (name == "dx") {
				if (_isTrue($this, "chosefin")) {
					changeFlag = true;
				} else {
					changeFlag = false;
				}
			} else if (name == "evalapp") {
				if (_isTrue($this, "chosefin")) {
					changeFlag = true;
				} else {
					changeFlag = false;
				}
				if (_isTrue($this, "dx")) {
					changeFlag = true;
				} else {
					changeFlag = false;
				}
			} else {
				changeFlag = true;
			}
			if(changeFlag){
				//var resultFormId = "eval1001";
				//发起评级
				if(name=="evalapp"&&query!="query"){
					_initExamForm(resultFormId);
					$("div[name=evalapp]").find(".li_content").eq(0).mCustomScrollbar("update");
				}else{
					$("#bootstarpTag-div").find(".hidden-content").hide();
					$("#bootstarpTag-div").find("i").remove();
				}
				$(".selected").removeClass("selected");
				$(this).addClass("selected");
				var liIndex = $(this).index();
				//$(".content_ul").css({left:"-"+(liIndex*100)+"%"});
				$("#gradeCardButton").hide();
				$("#evalappButton").hide();
				$("#chosefinButton").hide();
				if(name=="dx"){
					name="gradeCard";
					$("#addEval").show();
					if(getFlag==true){
						$.each(dataMap.examCardListData,function(i,obj){
							if(dataMap["dxData"+obj.examineCardId]){
								var str="dxData"+obj.examineCardId;
								if(dataMap[str].score!="0"&&dataMap[str].score!=null){
									$("#editEval").show();
									$("#addEval").hide();
								}
							}
						});
					}
				}
				if(name=="evalapp"||name=="chosefin"){
					$("#addEval").hide();
					$("#editEval").hide();
				}
				$("#"+name+"Button").show();
				if(getFlag==true){//查看模型匹配操作按钮隐藏 不显示
					$("#chosefinButton").hide();
				}
			}else{
				alert(top.getMessage("FIRST_OPERATION","前面的步骤"),0);
			}
		});
	};
	//初始化表头
	var _initThead=function(obj,showData){
		if(dataMap.examCardListData){
			$.each(dataMap.examCardListData,function(i,obj){
				$.each(showData, function(index,dataObj) {
					var $liContent =  $(".eval-content .content_ul li").find("div[name="+index+obj.examineCardId+"]");
					var $table = $liContent.find("table");
					var $thTr = $('<tr></tr>');
					$.each(dataObj, function(key,dic) {
						var $th = $('<th name='+key+'>'+dic+'</th>');
						$thTr.append($th);
						if(key=="indexName"){
							$th.before('<th style="width:10%;" name="noneshow">&nbsp;</th>');
							$th.addClass("text_align_s");
						}
					});
					$table.find("thead").append($thTr);
				});
			});
			
		}
	}
	//判断选项卡内容是否已填写
	var _isTrue=function($this,str){
		var $obj = $this.parent().find("li[name="+str+"]");
		if($obj.length>0){
			if($obj.hasClass("success")){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	};
	var _initTemplateOptions=function(){
		jQuery.ajax({
			url:webPath+"/mfExamineTemplateConfig/getConfigMatchedListAjax",
			data:{relId:pactId,entFlag:"finc"},
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
				}else if(data.flag == "error"){
					window.top.alert(top.getMessage("NO_MATCH_MODEL",{"name1":"检查模型","name2":"检查模型"}),0);
				}
			},error:function(data){
				 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	var _initExamForm=function(formId){
		jQuery.ajax({
				url:webPath+"/mfExamineHis/getByIdAjaxForForm",
				data:{ajaxData:"",examHisId:examHisId,formId:formId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						$(".content_ul li div[name=evalapp]").find("#bootstarpTag-div").html(data.formHtml);
						//addGradeCard(data);
						if(query=="query"){//详情
							$("#evalapp").find("i").remove();//隐藏输入框中的小笔或的放大镜图标
							$("#evalapp").find("font").remove();//隐藏输入框中的小笔或的放大镜图标
							$("#evalapp").find("input,select,textarea").attr("readonly","true");
							$("#evalapp").find("input,select,textarea").removeAttr("onclick");
						}
						
					}else if(data.flag=="error"){
						alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	};
	var _editEval=function(){
		$("input[type=radio]").removeAttr("disabled");
		$("#editEval").hide();
		$("#addEval").show();
	};

	/** 文档加载 */
	var _template_init = function() {
		if(templateId==null||templateId==""){
			return;
		}
		$.ajax({
			url : webPath+"/mfExamineHis/getSaveTemplateInfoAjax",
			data : {
				templateId : templateId,
				examHisId:examHisId
			},
			type : 'post',
			dataType : 'json',
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},
			success : function(data) {
				if(data.flag=="success"){
					var htmlStr = getTemplateBizConfigHtml(data.docTemplateList);
					$("#bizConfigs").html(htmlStr);
					if(htmlStr=="" || htmlStr==null){
						$("#template_div").hide();
					}
				}else if(data.flag=="error"){
					$("#template_div").hide();
				}
			}
		});
	};

	/** 循环添加所有的业务文档 */
	var getTemplateBizConfigHtml = function(docTemplateList) {
		// 循环产品下的模板项
		var subHtmlStr = "";
		$.each(docTemplateList, function(i, docTemplate) {
			var htmlTmp = getTemplateItemHtml(docTemplate);
			subHtmlStr = subHtmlStr + htmlTmp;
		});
		var htmlStr = '<div class="item-div">' + subHtmlStr + '</div>';
		return htmlStr;
	};

	/** 业务文档html */
	var getTemplateItemHtml = function(docTemplate) {
		var imgClass = "item-word";
		var htmlStr = '<div id="' + docTemplate.id + '" class="block-item">';
		htmlStr += '     <div class="item-title ' + imgClass + '" onclick="ExamineApply.openDocTemplate(\'' + docTemplate.templateNo + '\',\'' + docTemplate.templateNameEn + '\');">';
		htmlStr += '       <span>' + docTemplate.templateNameZh + '</span>';
		if(docTemplate.saveFlag=="0"){
			htmlStr += '   <div class="color_theme"><i class="i i-jia3"></i>新增</div>';
		}
		htmlStr += '     </div>';
		htmlStr += '   </div>';
		return htmlStr;
	};
	var _openDocTemplate=function(templateNo,filename){
		$.ajax({
			url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo",
			type:"post",
			data:{"relNo":examHisId,templateNo:templateNo},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				var type = "add";
				var filepath = "";
				if(data.saveFlag !="0"){
					filepath = data.filePath;
					type = "detail";
				}
				var fileName = data.fileName;
				var saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+examHisId+"&filename="+filename+"&templateNo="+templateNo+"&userId="+userId;
				var poCntJson = {
						filePath : filepath,
						fileName : fileName,
						appId : appId,
						pactId : pactId,
						fincId : fincId,
						templateNo :templateNo,
						cusNo : cusNo,
						saveUrl :saveUrl,
						saveBtn : "1",
						callBackfun : 'ExamineApply.template_init',
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
		init:_init,
		initTemplateOptions:_initTemplateOptions,
		showData:showData,
		initThead:_initThead,
		initExamForm:_initExamForm,
		editEval:_editEval,
		template_init:_template_init,
		openDocTemplate:_openDocTemplate
	};
}(window,jQuery);