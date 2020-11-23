var MfCusConfig=function(window, $){
	var _init=function(){
		$.each($("#evalScoreGradeConfig").find("table[id=tablist]").find("tr"),function(index,data){
			var $obj = $(data);
			var opScore1 = $obj.find("td").eq(2).html();
			var opScore2 = $obj.find("td").eq(4).html();
			var opScore1Html="";
			var opScore2Html="";
			if(opScore1!=undefined){
				opScore1Html="<span>"+opScore1+"</span><input name='opScore1' value='"+opScore1+"' type='hidden'>";
				$obj.find("td").eq(2).html(opScore1Html);
			}
			if(opScore2!=undefined){
				opScore2Html="<span>"+opScore2+"</span><input name='opScore2' value='"+opScore2+"' type='hidden'>";
				$obj.find("td").eq(4).html(opScore2Html);
			}
		});
	};
	//新增客户类型
	var _addCusType=function (){
		var url = webPath+"/mfCusType/input";
		top.flag=false;
		top.optName="";
		window.parent.openBigForm(url,"新增客户类别",function(){
			if(top.flag){
                var htmlStr = '<div class="item-title margin_bottom_10" id="typeNo'+top.typeNo+'"><span style="min-width:63px;display:inline-block;">['+top.coop+'] </span><span class="color_theme">'+ top.typeName+'</span> 信息采集与信息展示 '+
                    '<a href="#" onclick="MfCusConfig.editCusType('+top.typeNo+');" class="config-font">编辑</a>'+
                    '<a href= "/mfCusFormConfig/getAllFormConList?optCode='+top.typeNo+'" class="config-font">表单配置</a>'+
                    '<a class=\"config-font\" href=\"JavaScript:newDocModel(\'/mfCusFormConfig/getEditDocPage?optCode=' + top.typeNo +'\');\">要件配置</a>'+
                    '<a class=\"config-font\" href=\"JavaScript:newTemplateModel(\'/mfCusFormConfig/getEditTemplatePage?optCode=' + top.typeNo +'\');\">模板配置</a></div>';
                if(top.useFlag == "0"){
                    htmlStr =
                        '<div class="item-title margin_bottom_10"><span style="min-width:63px;display:inline-block;">['+top.coop+'] </span><span class="color_theme">'+ top.typeName+'</span> 信息采集与信息展示 '+
                        '<span class="config-font"'+
                        '>编辑</span>'+
                        '<span class="config-font"'+
                        '>表单配置</span>'+
                        '<span class="config-font"'+
                        '>要件配置</span>'+
                        '<span class="config-font"'+
                        '>模板配置</span></div>';
                }
				$("#content_table_nmd").append(htmlStr);
			}
		});
	};
	
	var _editCusType = function(typeNo){
		top.flag=false;
		window.parent.openBigForm(webPath + "/mfCusType/getById?typeNo="+typeNo,"编辑客户类别",function(){
			if(top.flag){
                if(top.useFlag == "0"){
                   var htmlStr =  '<span class="config-font"'+
                       '>编辑</span>'+
                       '<span class="config-font"'+
                       '>表单配置</span>'+
                       '<span class="config-font"'+
                       '>要件配置</span>'
                    '<span class="config-font"'+
                    '>模板配置</span>';
                    $("#typeNo" + typeNo + " a").remove();
                    $("#typeNo" + typeNo).append(htmlStr);
                }
			}
		});
	};
	
	//新增评级场景
	var _addEvalScenceConfig=function (gradeType){
		top.evalScenceConfig="";
		window.parent.openBigForm(webPath+"/evalScenceConfig/input?gradeType="+gradeType,"评级模型配置",_refreshScenceConfig);
	};
    //新增业务评级场景
	var _addEvalScenceConfigXY=function (gradeType){
		top.evalScenceConfig="";
		window.parent.openBigForm(webPath+"/evalScenceConfig/input?gradeType="+gradeType,"业务评级模型配置",_refreshScenceConfigXY);
	};
    //新增风险评级场景
    var _addEvalScenceConfigFX=function (gradeType){
        top.evalScenceConfig="";
        window.parent.openBigForm(webPath+"/evalScenceConfig/input?gradeType="+gradeType,"风险检查模型配置",_refreshScenceConfigFX);
    };
    //新增风险评级场景
    var _addEvalScenceConfigEC=function (gradeType){
        top.evalScenceConfig="";
        window.parent.openBigForm(webPath+"/evalScenceConfig/input?gradeType="+gradeType,"额度测算模型配置",_refreshScenceConfigEC);
    };
	//编辑评级场景
	var _getById=function(evalScenceNo){
		var url = webPath+"/evalScenceConfig/getById?evalScenceNo="+evalScenceNo;
		top.evalScenceConfig="";
		top.editFlag=false;
		window.parent.openBigForm(url,"评级模型配置",function(){
			if(top.editFlag){
				var evalScenceName = top.evalScenceConfig.evalScenceName;
				$("#"+evalScenceNo).find("a").html(evalScenceName);
			}
		});
	};
	//评级场景配置
	var _getEvalScenceConfig=function(evalScenceNo,evalIndexTypeRel){
		var url= webPath+"/evalScenceConfig/getEvalScenceConfigSetting?evalScenceNo="+evalScenceNo;
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	};
	//删除模型
	var _deleteScence = function(obj,url){
		var argument = url.split("\?")[1];
		var evalScenceNo = argument.split("&")[0].split("=")[1];
		$.ajax({
			url:webPath+"/evalScenceConfig/getScenceIsUsedAjax",
			data:{evalScenceNo:evalScenceNo},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					ajaxTrDelete(obj,url);
				}else{
					window.top.alert(data.msg,0);
				}
			},error:function(){
				 window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		}); 
	};
	//刷新评级模型配置列表信息
	var _refreshScenceConfig=function(){
		if(top.evalScenceConfig!=""){
			var evalScenceNo = top.evalScenceConfig.evalScenceNo;
			var evalScenceName = top.evalScenceConfig.evalScenceName;
			var evalIndexTypeRel=top.evalScenceConfig.evalIndexTypeRel;
//					var htmlStr= '<p class="p-content"><span id="'+evalScenceNo+'">'+evalScenceName+'</span>评级指标与评级模型'
//					+'<a href="javascript:void(0);" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" class="config-font">配置</a>';
//					//$("#ScenceContent_table_div").find("p").eq(0).before(htmlStr);
			
			var htmlStr='<div class="item-title margin_bottom_10"><span id="'+evalScenceNo+'" style="margin-left: 0px;">'
				+'<a  onclick="MfCusConfig.getById(\''+evalScenceNo+'\');return false;" href="javascript:void(0);">'
				+''+evalScenceName+'</a></span>&nbsp;评级指标与评级模型&nbsp;<a class="config-font" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" href="javascript:void(0);">配置</a>'
				+'</div>';
			
			$("#ScenceContent_table_div").append(htmlStr);
		}
	};
    //刷新风险评级模型配置列表信息
    var _refreshScenceConfigFX=function(){
        if(top.evalScenceConfig!=""){
            var evalScenceNo = top.evalScenceConfig.evalScenceNo;
            var evalScenceName = top.evalScenceConfig.evalScenceName;
            var evalIndexTypeRel=top.evalScenceConfig.evalIndexTypeRel;
//					var htmlStr= '<p class="p-content"><span id="'+evalScenceNo+'">'+evalScenceName+'</span>评级指标与评级模型'
//					+'<a href="javascript:void(0);" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" class="config-font">配置</a>';
//					//$("#ScenceContent_table_div").find("p").eq(0).before(htmlStr);

            var htmlStr='<div class="item-title margin_bottom_10"><span id="'+evalScenceNo+'" style="margin-left: 0px;">'
                +'<a  onclick="MfCusConfig.getById(\''+evalScenceNo+'\');return false;" href="javascript:void(0);">'
                +''+evalScenceName+'</a></span>&nbsp;客户授信与业务风险检查模型&nbsp;<a class="config-font" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" href="javascript:void(0);">配置</a>'
                +'</div>';

            $("#ScenceContent_table_divFX").append(htmlStr);
        }
    };
    var _refreshScenceConfigXY=function(){
        if(top.evalScenceConfig!=""){
            var evalScenceNo = top.evalScenceConfig.evalScenceNo;
            var evalScenceName = top.evalScenceConfig.evalScenceName;
            var evalIndexTypeRel=top.evalScenceConfig.evalIndexTypeRel;
            var evalClass=top.evalScenceConfig.evalClass;
            var htmlStr='<div class="item-title margin_bottom_10"><span id="'+evalScenceNo+'" style="margin-left: 0px;">'
                +'<a  onclick="MfCusConfig.getById(\''+evalScenceNo+'\');return false;" href="javascript:void(0);">'
                +''+evalScenceName+'</a></span>&nbsp;评级指标与评级模型&nbsp;<a class="config-font" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" href="javascript:void(0);">配置</a>'
                +'</div>';
            if(evalClass == '1'){
                $("#ScenceContent_table_div_XY").append(htmlStr);
			}else{
                $("#ScenceContent_table_div_ZY").append(htmlStr);
            }
        }
    };
    //刷新风险评级模型配置列表信息
    var _refreshScenceConfigEC=function(){
        if(top.evalScenceConfig!=""){
            var evalScenceNo = top.evalScenceConfig.evalScenceNo;
            var evalScenceName = top.evalScenceConfig.evalScenceName;
            var evalIndexTypeRel=top.evalScenceConfig.evalIndexTypeRel;
//					var htmlStr= '<p class="p-content"><span id="'+evalScenceNo+'">'+evalScenceName+'</span>评级指标与评级模型'
//					+'<a href="javascript:void(0);" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" class="config-font">配置</a>';
//					//$("#ScenceContent_table_div").find("p").eq(0).before(htmlStr);

            var htmlStr='<div class="item-title margin_bottom_10"><span id="'+evalScenceNo+'" style="margin-left: 0px;">'
                +'<a  onclick="MfCusConfig.getById(\''+evalScenceNo+'\');return false;" href="javascript:void(0);">'
                +''+evalScenceName+'</a></span>&nbsp;客户额度测算模型&nbsp;<a class="config-font" onclick="MfCusConfig.getEvalScenceConfig(\''+evalScenceNo+'\',\''+evalIndexTypeRel+'\');return false;" href="javascript:void(0);">配置</a>'
                +'</div>';

            $("#ScenceContent_table_divEC").append(htmlStr);
        }
    };
	//修改保存分数等级配置
	var _updateScoreGradeConFig=function(obj,url){
		var configNo = url.split("?")[1].split("&")[0].split("=")[1];
		var opScore1 = "";
		var opScore2 = "";
		$.each($("#evalScoreGradeConfig").find("table[id=tablist]").find("tr"),function(index,data){
			var $obj = $(data);
			if($obj.find("td").html()==configNo){
				opScore1 = $obj.find("input[name=opScore1]").val();
				opScore2=$obj.find("input[name=opScore2]").val();
			}
		});
		LoadingAnimate.start();
		$.ajax({
			url:webPath+"/evalScoreGradeConfig/updateBytableAjax",
			data:{configNo:configNo,opScore1:opScore1,opScore2:opScore2},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("#ScoreGradeContent_table_div").html(data.tableHtml);
					
					LoadingAnimate.stop();
				}else{
					LoadingAnimate.stop();
					window.top.alert(data.msg,0);
				}
			},error:function(){
				LoadingAnimate.stop();
				 window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	};
	//编辑评级级别描述
	var _editAssess = function(obj,url){
		var configNo = url.split("?")[1].split("&")[0].split("=")[1];
		var formId = "eval4001";
		//如果是编辑等级配置信息，使用eval0007，如果使用等级描述使用eval4001
		if(obj=="evalInfo"){
			formId = "eval0007";
		}
		top.gradeConfig="";
		window.parent.openBigForm(webPath+"/evalScoreGradeConfig/getById?configNo="+configNo+"&formId="+formId,"分数等级配置",function(){
			if(obj=="evalAssess"&&top.gradeConfig!=""){
				$("#evalAssess"+configNo).html(top.gradeConfig.evalAssess);
			}
			if(obj=="evalInfo"&&top.gradeConfig!=""){
				$("#opScore"+configNo).html("评级分值"+top.gradeConfig.opScore1+"--"+top.gradeConfig.opScore2);
				$("#creditAmt"+configNo).html("授信额度"+top.gradeConfig.creditAmt+"万");
			}
		});
	};

    //编辑评级级别描述
    var _editAssessNew = function(obj,url){
        var formId = "eval0007";
        var cusType = url.split("?")[1].split("&")[1].split("=")[1];
        //如果是编辑等级配置信息，使用eval0007，如果使用等级描述使用eval4001
        top.gradeConfig="";
        window.parent.openBigForm(webPath+url+"&formId="+formId,"分数等级配置",function(){
            window.location.href=webPath+"/evalScoreGradeConfig/getListPage1?cusType="+cusType;
        });
    };


	//新增客户分类
	var _addCusClassIfy = function(){
		var url = webPath+"/mfCusClassify/configInput";
		top.flag="";
		top.optName="";
		top.optCode="";
		top.remark="";
		window.parent.openBigForm(url,"客户分类配置",function(){
			if(top.flag!=""){
				var htmlStr = '<div class="item-title margin_bottom_10"><a id ="a_classIfy'+top.optCode+'" href="javascript:void(0);"'+
						'onclick="MfCusConfig.editCusClassify(\''+top.optCode+'\');return false;">'+
						top.optName+'</a><span style="margin-left: 6px;" id= "classIfy'+top.optCode+'">'+top.remark+'</span></div>';
				$("#cusClassifyContent_table_div").append(htmlStr);
			}
		});
	};
	//编辑客户分类
	var _editCusClassify=function(optCode){
		var url = webPath+"/mfCusClassify/configEdit?optCode="+optCode;
		top.flag="";
		top.remark="";
		top.optName="";
		top.optCode="";
		window.parent.openBigForm(url,"客户分类配置",function(){
			if(top.flag!=""){
				$("#a_classIfy"+top.optCode).html(top.optName);
				$("#classIfy"+top.optCode).html(top.remark);
			}
		});
	};
	//添加授信模型
	var _addCusCreditModel = function(){
		var url=webPath+"/mfCusCreditModel/input";
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		
		/*top.flag="";
		top.creditModel="";
		window.parent.openBigForm(url,"授信模型配置",function(){
			if(top.flag!=""){
				var modelId = top.creditModel.modelId;
				var htmlStr = '<p class="p-content"><a id ="creditModelName'+modelId+'" href="javascript:void(0);"'+
				'onclick="MfCusConfig.editCusCreditModel(\''+modelId+'\');return false;"'+
				'class="config-font">'+top.creditModel.modelName+'</a><span style="margin-left: 6px;" id= "creditRemark'+modelId+'">'+top.creditModel.remark+'</span></p>';
				$("#cusCreditContent_table_div").append(htmlStr);
			}
		});*/
	};
	var _editCusCreditModel=function(modelId){
		var url=webPath+"/mfCusCreditModel/getById?modelId="+modelId;
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		/*top.flag="";
		top.creditModel="";
		window.parent.openBigForm(url,"授信模型配置",function(){
			if(top.flag!=""){
				var modelId = top.creditModel.modelId;
				$("#creditModelName"+modelId).html(top.creditModel.modelName);
				$("#creditRemark"+modelId).html(top.creditModel.remark);
			}
		});*/
	};
	//财务报表模板导出
	var _exportExcel=function(){
		$.ajax({
			url:webPath+"/cusFinMain/exportExcelAjax",
			dataType:"json",
			type:"POST",
			success:function(data){
				if(data.flag == "success"){
					if(data.exportFlag == "success"){
						window.top.location.href = webPath+"/docUpLoad/getFileDownload_new.action?path="+data.path;
					}else{
						alert(data.msg,0);
					}
				}else{
					alert("error",0);
				}
			},error:function(){
				alert("error",0);
			}
		});
	};
	//财务报表预览
	var _getReportPreview=function(reportType){
		top.openBigForm(webPath+"/cusFinParm/getPageReportPreview?reportType="+reportType,"报表预览",function(){});
	};
	//财务报表配置
	var _getFinParm=function(reportType){
		top.openBigForm(webPath+"/cusFinParm/getListPageNew?reportType="+reportType,"财务报表配置",function(){});
	};

    //报表预览
    var _getReportView = function(reportTypeId){
        top.openBigForm(webPath+"/mfCusReportItem/toReportItemView?reportTypeId="+reportTypeId, '预览',function(){});
    }
	//财务报表配置
	var _getReportSet=function(reportTypeId){
		top.openBigForm(webPath+"/mfCusReportItem/toReportItemSet?reportTypeId="+reportTypeId,"财务报表配置",function(){});
	};
	//打开财务指标列表
	var _getCusFinFormByPage=function(){
		var url=webPath+"/cusFinForm/getListPage";
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	};
	//新增财务指标
	var _addCusFinForm = function(){
		var url=webPath+"/cusFinForm/input";
		top.addFlag = false;
		top.cusFinForm="";
   		top.window.openBigForm(url,'指标配置',function(){
   			if(top.addFlag){
   				var formName = top.cusFinForm.formName;
   				var formNo = top.cusFinForm.formNo;
   				var accRule = top.cusFinForm.accRule;
   				var htmlStr = '<div class="item-title margin_bottom_10" id="cusFinformName\''+formNo+'\'">'+formName+'<a href="javascript:void(0);"'+
				'onclick="MfCusConfig.editCusFinForm(\''+formNo+'\',\''+accRule+'\')" class="config-font"> 配置</a></div>'+
				'<div id= "cusFinFormDesc'+formNo+'" class="item-title margin_bottom_10">'+top.cusFinForm.formDesc+'</div>';
   				$("#moreCusFinForm").before(htmlStr);
   			}
   		});
	};
	//编辑财务指标
	var _editCusFinForm = function(formNo,accRule){
		var url=webPath+"/cusFinForm/getById?formNo="+formNo+"&accRule="+accRule;
		top.updateFlag = false;
		top.cusFinForm="";
   		top.window.openBigForm(url,'指标配置',function(){
   			if(top.updateFlag){
   				var formName = top.cusFinForm.formName;
   				var formNo = top.cusFinForm.formNo;
   				var accRule = top.cusFinForm.accRule;
   				var htmlStr = formName+'<a href="javascript:void(0);"'+
						'onclick="MfCusConfig.editCusFinForm(\''+formNo+'\',\''+accRule+'\')" class="config-font">配置</a>';
   				$("#cusFinformName"+formNo).html(htmlStr);
				$("#cusFinFormDesc"+formNo).html(top.cusFinForm.formDesc);
   			}
   		});
	};

    //查看企业客户分数等级列表
    var _getCompany = function(){
    	var  cusType = "1";
    	window.location.href=webPath+"/evalScoreGradeConfig/getListPage1?cusType="+cusType;
    };

    //查看个人客户分数等级列表
    var _getPerson = function(){
        var  cusType = "2";
        window.location.href=webPath+"/evalScoreGradeConfig/getListPage1?cusType="+cusType;
    };
    //授信配置
    var _openCreditConfig = function (creditId, creditModel, configType) {
        window.location.href = webPath + "/mfCusFormConfig/getCreditConfig?creditId=" + creditId + "&creditModel=" + creditModel + "&configType=" + configType;
    };
    var _creditConfigAdd = function () {
        top.creditConfigAddFlag = false;
        top.window.openBigForm(webPath + "/mfCusCreditConfig/input", '授信流程新增', function () {
            if (top.creditConfigAddFlag) {
                var html = '<div class="item-div">' +
                    '<div class="item-title  margin_bottom_10">' +
                    '<span>' + top.creditName + '</span>' +
                    '<span>' + top.remark + '</span>' +
                    '<a href="javascript:void(0);"' +
                    'onclick="MfCusConfig.openCreditConfig(\'' + top.creditId + '\',\'credit_' + top.creditModel + '\',\'base\')"' +
                    'class="padding_left_15 pointer">配置</a></div></div>';
                var oldHtml = $("#credit-flow-content-div .sub-content-div").html();
                $("#credit-flow-content-div .sub-content-div").html(oldHtml + html);
            }
        });
    }
	return{
		addEvalScenceConfig:_addEvalScenceConfig,
		getById:_getById,
		getEvalScenceConfig:_getEvalScenceConfig,
		deleteScence:_deleteScence,
		refreshScenceConfig:_refreshScenceConfig,
		updateScoreGradeConFig:_updateScoreGradeConFig,
		editAssess:_editAssess,
		init:_init,
		addCusType:_addCusType,
		editCusType:_editCusType,
		addCusClassIfy:_addCusClassIfy,
		editCusClassify:_editCusClassify,
		addCusCreditModel:_addCusCreditModel,
		editCusCreditModel:_editCusCreditModel,
		getReportPreview:_getReportPreview,
		getFinParm:_getFinParm,
        getReportView:_getReportView,
        getReportSet:_getReportSet,
		exportExcel:_exportExcel,
		getCusFinFormByPage:_getCusFinFormByPage,
		addCusFinForm:_addCusFinForm,
		editCusFinForm:_editCusFinForm,
        getCompany:_getCompany,
        getPerson:_getPerson,
        editAssessNew:_editAssessNew,
        openCreditConfig:_openCreditConfig,
        addEvalScenceConfigFX:_addEvalScenceConfigFX,
        refreshScenceConfigFX:_refreshScenceConfigFX,
        addEvalScenceConfigEC:_addEvalScenceConfigEC,
        refreshScenceConfigEC:_refreshScenceConfigEC,
        creditConfigAdd: _creditConfigAdd,
        refreshScenceConfigXY: _refreshScenceConfigXY,
        addEvalScenceConfigXY: _addEvalScenceConfigXY

	};
}(window, jQuery);