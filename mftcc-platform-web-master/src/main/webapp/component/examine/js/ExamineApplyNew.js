;
var ExamineApply=function(window,$){
	var showData = {
			dx:{//定性
				"indexName":"指标名称",
				"ctrl_btn":"打分选项"},
		};
	var _init=function(){
		//中关村固定主体为客户
        $("select[name='pasMinNo']").val("305");
        $("select[name='pasMinNo']").attr("disabled","disabled");

		//是否是查看 getFlag查看标志  true 为是
		if(getFlag===undefined){
			getFlag = false;
		}else if(getFlag=="apply"||getFlag=="detail"){
			getFlag = true;
            $("select[name='templateId']").attr("disabled","");
            $("select[name='templateId']").parent("td").unbind();
		}
		//控制贷后检查进入和待办任务进入展示不同的关闭按钮
		if(entrance!='loanAfter'&&entrance==''){
			$("#chosefinButton").hide();
			$("#chosefinTaskButton").show();
		}

		if(getFlag==true){//查看时将选择财报 不显示
			$("#chosefinButton").hide();
		}
		var bodyWidth = $("body").width();
		var liLength= $(".content_ul li").length;
		$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
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
				$(".showprogress ul li[name=dx]").show();
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
            $(".hidden-content").hide();
		}
		//加载完成 显示数据
		$(".eval-content").css({"display":"block"});
		//隐藏财务、定量、定性右上角总分
		$(".content_ul li").find(".li_title .scoreShow").hide();
		//隐藏页面中所有thead
		$("thead").hide();
		var check = $(".selected").attr("name");
        $("div[id=gradeCard] div[id=moreBar]").remove();
        //$("div[id=gradeCard]")[0].hasMoreBar=false;
        $("div[id=evalapp] div[id=moreBar]").remove();
        $("div[id=evalapp]")[0].hasMoreBar=false;
        $("div[id=chosefin] div[id=moreBar]").remove();
        $("div[id=chosefin]")[0].hasMoreBar=false;
		if(check == 'chosefin'){
            myCustomScrollbarForForm({
                obj:"#chosefin",
                advanced : {
                    theme : "minimal-dark",
                    updateOnContentResize : true
                },
                updateImmediate:true
            });

		}else{
			$("select[name='templateId']").attr("disabled","");
			$("select[name='templateId']").parent("td").unbind();
		}
		if(check == 'dx'){
			myCustomScrollbarForForm({
				obj:"#gradeCard",
				advanced : {
					updateOnContentResize : true
				},
                updateImmediate:true
			});
		}
		if(check == 'evalapp'){
			myCustomScrollbarForForm({
				obj:"#evalapp",
				advanced : {
					updateOnContentResize : true
				},
                updateImmediate:true
			});
		}
		$("#evalappButton").css("position","fixed");
        if(getFlag==true){
            $(".hidden-content").hide();//隐藏表单隐藏域
            //非新增页面初始化时切换到结论登记
            $(".showprogress ul li[name=dx]").hide();
            $(".showprogress ul").find("li[name=evalapp]").click();
        }
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

				if($("div[name=dx]").length>0){
                    if (_isTrue($this, "dx")) {
                        changeFlag = true;
                    } else {
                        changeFlag = false;
                    }
				}

			} else {
				changeFlag = true;
			}
			if(changeFlag){
				//var resultFormId = "eval1001";
				//发起评级
				if(name=="evalapp"){
					_initExamForm(resultFormId);
					$("div[name=evalapp]").find(".li_content").eq(0).mCustomScrollbar("update");
				}else{
					$("#bootstarpTag-div").find(".hidden-content").hide();
					$("#bootstarpTag-div").find("i").remove();
				}
				$(".selected").removeClass("selected");
				$(this).addClass("selected");
				var liIndex = $(this).index();
				$(".content_ul").animate({left:"-"+(liIndex*100)+"%"});
				$("#gradeCardButton").hide();
				$("#evalappButton").hide();
				$("#chosefinButton").hide();
				$("#chosefinTaskButton").hide();
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
				//重置滚动条
                $("div[id=gradeCard] div[id=moreBar]").remove();
                $("div[id=evalapp] div[id=moreBar]").remove();
                $("div[id=chosefin] div[id=moreBar]").remove();
                if(name == 'chosefin'){
                    myCustomScrollbarForForm({
                        obj:"#chosefin",
                        advanced : {
                            theme : "minimal-dark",
                            updateOnContentResize : true
                        },
                        updateImmediate:true
                    });
                    if(queryVal=='query'){//当页面是查看时将隐藏域隐藏掉
                    	$(".bootstarpTag").find(".hidden-content").hide();
					}
                }else if(name == 'dx'){
                    myCustomScrollbarForForm({
                        obj:"#gradeCard",
                        advanced : {
                            updateOnContentResize : true
                        },
                        updateImmediate:true
                    });
                }else if(name == 'evalapp'){
                    myCustomScrollbarForForm({
                        obj:"#evalapp",
                        advanced : {
                            updateOnContentResize : true
                        },
                        updateImmediate:true
                    });
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
	var _initTemplateOptions=function(templateType){
		jQuery.ajax({
			url:webPath+"/mfExamineTemplateConfig/getConfigMatchedListAjax",
			data:{relId:pactId,entFlag:"finc",templateType:templateType},
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
				url:webPath+"/mfExamineHis/getByIdAjaxForFormNew",
				data:{ajaxData:"",examHisId:examHisId,formId:formId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						$(".content_ul li div[name=evalapp]").find("#bootstarpTag-div").html(data.formHtml);
						$("input[name=adjRiskLevel]").val(4);
						$("input[name=riskLevel]").val(4);
						//addGradeCard(data);
						if(queryVal=="query"){//详情
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
				var filename = data.fileName;
				var saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+examHisId+"&filename="+filename+"&templateNo="+templateNo+"&userId="+userId;
				var poCntJson = {
						filePath : filepath,
						fileName : filename,
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

    //-------------------------------------财务报表模块功能 START-----------------------------------------------------------//
//获得财务报表信息
    function _getPfsDialog() {
    	if( cusNo == 'undefined' || cusNo == ''){
            cusNo = $("input[name=cusNo]").val();
		}
        top.isUpload = false;
        top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo='+cusNo,'财务报表', function() {
            if(top.isUpload){  //财务报表上传成标志
                $.ajax({
                    url : webPath+"/cusFinMain/queryCusFinDataAjax",
                    data : {
                        cusNo : cusNo
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        getFinDataHtml(data);
                    }
                });
            }
        });
    };

    function getFinDataHtml(data){
        MfCusDyForm.initCusIntegrity(top.infIntegrity);
        if(data.cusFinMainList.length > 0){
            $("div[name=MfCusFinMainAction]").remove();
            var subjectDataFlag=BussNodePmsBiz.checkPmsBiz('cus-edit-SubjectData');
            var comparisonStr = "";
            // if(BussNodePmsBiz.checkPmsBiz('cus-multi-period-comparison')){
            //     comparisonStr = "<span style='margin-top: -28px;margin-left: 145px;' ><a href='javascript:void(0);' onclick='multiPeriodComparisonView();'>多期对比</a></span>";
            comparisonStr = "<button  class=' btn btn-link formEdit-btn' onclick='multiPeriodComparisonView()'; '>多期对比</button>";
            // }
            var finDiv = '<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block">'+
                '<div class="list-table">'+
                '<div class="title">';

            if(data.cusType=="2") {  //个人客户时
                finDiv +=  '<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表</span>'+
                    '<button title="新增" onclick="getPersonPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>';
            }else{
                finDiv +=  '<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>'+
                    '<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>';
            }
            finDiv +=    comparisonStr+
                '<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
                '<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
                '</button>'+
                '</div>'+
                '<div id="CusFinMainAction" class="content collapse in">'+
                '<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
                '<colgroup style="width: 10%"></colgroup>'+
                '<colgroup style="width: 10%"></colgroup>'+
                '<colgroup style="width: 10%"></colgroup>'+
                '<colgroup style="width: 10%"></colgroup>'+
                '<colgroup style="width: 10%"></colgroup>'+
                '<thead>'+
                '<tr>'+
                '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">选择</th>'+
                '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>';
            if(data.cusType=="2"){  //个人客户时
                finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">名下企业名称</th>';
            }
            finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
                '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
                '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>';
            if(subjectDataFlag) {
                finDiv += '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">科目余额表</th>';
            }
            if(data.cusType=="1"){  //个人客户时
                finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">口径</th>';
            }
            finDiv += '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
                '</tr>'+
                '</thead>'+
                '<tbody id="tab">';
            var htmlStr = "";
            $.each(data.cusFinMainList,function(i,cusFinMain){
                var viewStr ="";
                var delStr="";
                var confirStr ="";
                if(data.cusType=="2") {  //个人客户时
                    viewStr = webPath + "/cusFinMain/inputReportView?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo + "&accRule=1&relationCorpNo=" + cusFinMain.corpNo;
                    delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&relationCorpNo=" + cusFinMain.corpNo;
                    confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&subjectDataFlag="+subjectDataFlag+"&relationCorpNo=" + cusFinMain.corpNo;
                }else{
                    viewStr = webPath + "/cusFinMain/inputReportView?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo + "&accRule=1";
                    delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo;
                    confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&subjectDataFlag="+subjectDataFlag;
                }
                var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    subjectStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
                var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
                var caliberStr = "";
                if(data.cusType=="1") {
                    if(cusFinMain.caliber=='1'){
                        caliberStr =  '<span class="listOpStyle">税务</span>&nbsp;&nbsp;&nbsp;&nbsp;';
                    }
                    if(cusFinMain.caliber=='2'){
                        caliberStr =  '<span class="listOpStyle">管理</span>&nbsp;&nbsp;&nbsp;&nbsp;';
                    }
                }
                if(cusFinMain.reportSts != 2){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        subjectStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }

                if(cusFinMain.isUsed != 1){
                    opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
                }
                if(cusFinMain.isUsed == 1){
                    opStr = opStr + '<span class="listOpStyle">删除</span>';
                }

                if(cusFinMain.assetsDataId == null||cusFinMain.assetsDataId == ""){
                    if(data.cusType=="2") {  //个人客户时
                        zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                    }else{
                        zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                    }
                    if(cusFinMain.reportSts == 2){
                        zcStr = "上传";
                    }
                }
                if(cusFinMain.incomeDataId==null || cusFinMain.incomeDataId==""){
                    if(data.cusType=="2") {  //个人客户时
                        proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                    }else{
                        proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                    }
                    if(cusFinMain.reportSts == 2){
                        proStr = "上传";
                    }
                }
                if(cusFinMain.cashDataId==null || cusFinMain.cashDataId==""){
                    if(data.cusType=="2") {  //个人客户时
                        cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                    }else{
                        cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                    }
                    if(cusFinMain.reportSts == 2){
                        cashStr = "上传";
                    }
                }
                if(subjectDataFlag){
                    if(cusFinMain.balanceDataId==null || cusFinMain.balanceDataId==""){
                        if(data.cusType=="2") {  //个人客户时
                            subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                        }else{
                            subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                        }
                        if(cusFinMain.reportSts == 2){
                            subjectStr = "上传";
                        }
                    }
                }
                htmlStr += '<tr>';
                if(data.cusType=="1"){
                    htmlStr +=  '<td align="center" width="10%"><input type="checkbox" onclick="selectThis(this)" name="accountId" value="'+cusFinMain.accountId+'" /></td>';
                }
                htmlStr +=  '<td align="center" width="10%">'+
                    '<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.weeks+'</a>'+
                    '</td>';
                if(data.cusType=="2"){
                    htmlStr +=    '<td align="center" width="15%">'+ cusFinMain.corpName+'</td>';
                }
                htmlStr +=   '<td align="center" width="15%">'+
                    zcStr+
                    '</td>'+
                    '<td align="center" width="15%">'+
                    proStr+
                    '</td>'+
                    '<td align="center" width="15%">'+
                    cashStr+
                    '</td>';
                if(subjectDataFlag) {
                    htmlStr += '<td align="center" width="15%">' +
                        subjectStr +
                        '</td>';
                }
                htmlStr += '<td align="center" width="5%">'+caliberStr+'</td>';
                htmlStr += '<td align="center" width="15%">'+
                    opStr+
                    '</td>'+
                    '</tr>';
            });
            finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>';
            $(".block-add").after(finDiv);
        }
    }

	return{
		init:_init,
		initTemplateOptions:_initTemplateOptions,
		showData:showData,
		initThead:_initThead,
		initExamForm:_initExamForm,
		editEval:_editEval,
		template_init:_template_init,
		openDocTemplate:_openDocTemplate,
        getPfsDialog:_getPfsDialog
	};
}(window,jQuery);