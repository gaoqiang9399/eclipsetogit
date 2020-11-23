;
var MfBusApply_DynaDetail = function(window, $) {
	// 终止业务
	var _disagree = function() {
		alert(top.getMessage("CONFIRM_OPERATION", "终止业务"), 2, function() {
			$.ajax({
				url : webPath+"/mfBusApply/disagree?appId=" + appId,
				data : {},
				type : 'post',
				dataType : 'json',
				async : true,// true异步, false同步
				error : function() {
					alert("终止业务失败_25193410004516864:" + this.url, 0);
				},
				success : function(data) {
					if (data.flag == 'success') {// 终止业务成功
						window.location.href = webPath+'/mfBusApply/getSummary?busEntrance=1&appId=' + data.appId;
					} else {
						window.top.alert(top.getMessage("FAILED_OPERATION", "终止业务"), 1);
					}
				}
			});
		});
	};

	// 文件打印
	var _filePrint = function() {
		var tempParm = "&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId" + pleId;
		top.window.openBigForm(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo=" + appId + "&modelNo=" + modelNo + "&generatePhase=" + encodeURI("01|02|03|04") + tempParm, '文件打印', myclose);
	};

	// 尽调报告按钮初始化
	var _surveyReport_init = function() {
		// 查询当前是否已保存尽调报告
        $.ajax({
            url : webPath+"/mfTuningReport/getByAppIdAjax?appId=" + appId,
            data : {},
            type : 'post',
            dataType : 'json',
            async : true,
            success : function(data) {
                if (data.uploadSize == 1) {// 保存过尽调报告
                    $("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式
                    _surveyReport_bindClick(appId);
                }
            }
        });

		// $.ajax({
		// 	url : webPath+"/mfTemplateBizConfig/getMfTemplateBizConfig?appId=" + appId,
		// 	data : {},
		// 	type : 'post',
		// 	dataType : 'json',
		// 	async : true,
		// 	success : function(data) {
		// 		if (data.uploadSize > 0) {// 保存过尽调报告
		// 			$("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
		// 			$("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式
        //
		// 			if (data.tbcList.length == 1) {
		// 				// 尽职调查阶段只有一个文档，直接打开
		// 				_surveyReport_bindClick(data.tbcList[0].templateBizConfigId);// 尽调报告绑定click事件
		// 			}
        //
		// 			if (data.tbcList.length > 1) {
		// 				// 尽职调查阶段有多个文档，打开选择窗口，选择打开
		// 				$("#surveyReport").bind("click", function() {
		// 					var tempParm = "&nodeNo=resp_investigation&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId" + pleId;
		// 					top.window.openBigForm(webPath+"/mfTemplateBizConfig/printSurveyReportList?relNo=" + appId + "&modelNo=" + modelNo + "&generatePhase=" + encodeURI("01|02|03|04") + tempParm, '调查报告', myclose);
		// 				});
		// 			}
        //
		// 		}
		// 	}
		// });
	};
// 尽调报告绑定click事件
    var _surveyReport_bindClick = function(appId) {
    	let showName = "尽调报告";
    	if(typeof(busModel) != "undefined" && busModel == "12"){
            showName = "项目申报表";
		}
        $("#surveyReport").bind("click", function() {
            top.openBigForm(webPath + "/mfTuningReport/getReportDtail?appId=" + appId + "&type=2", showName, function () {
                window.location.reload();
            });
        });
    };
	// // 尽调报告绑定click事件
	// var _surveyReport_bindClick = function(templateBizConfigId) {
	// 	$("#surveyReport").bind("click", function() {
	// 		var temParm = '&cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=';// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
	// 		var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId + temParm;
	// 		var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
	// 		// 处理跳转office的url
	// 		$.ajax({
	// 			url : url,
	// 			data : {
	// 				"returnUrl" : backUrl,
	// 				"functionPoint" : "jdbg"
	// 			},
	// 			type : 'post',
	// 			dataType : 'json',
	// 			async : true,
	// 			beforeSend : function() {
	// 				LoadingAnimate.start();
	// 			},
	// 			complete : function() {
	// 				LoadingAnimate.stop();
	// 			},
	// 			error : function() {
	// 				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
	// 			},
	// 			success : function(data) {
	// 				var poCntObj = $.parseJSON(data.poCnt);
	// 				mfPageOffice.openPageOffice(poCntObj);
	// 			}
	// 		});
	// 	});
	// };

    // 风险审查按钮初始化
    var _riskReview_init = function() {
        // 查询当前是否已保存风险报告
        $.ajax({
            url : webPath+"/mfTemplateBizConfig/getRiskReviewMfTemplateBizConfig?appId=" + appId,
            data : {},
            type : 'post',
            dataType : 'json',
            async : true,
            success : function(data) {
                if (data.uploadSize > 0) {// 保存过风险报告
                    $("#riskReview").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#riskReview").addClass("btn-forestgreen");// 添加绿色样式

                    if (data.mfTemplateBizConfigList.length == 1) {
                        // 风险报告阶段只有一个文档，直接打开
                        _riskReview_bindClick(data.mfTemplateBizConfigList[0].templateBizConfigId);// 风险审查绑定click事件
                    }

                    if (data.mfTemplateBizConfigList.length > 1) {
                        // 风险报告阶段有多个文档，打开选择窗口，选择打开
                        $("#riskReview").bind("click", function() {
                            var tempParm = "&nodeNo=resp_investigation&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId" + pleId;
                            top.window.openBigForm(webPath+"/mfTemplateBizConfig/printRiskReviewList?relNo=" + appId + "&modelNo=" + modelNo + "&generatePhase=" + encodeURI("01|02|03|04") + tempParm, '风险审查', myclose);
                        });
                    }

                }
            }
        });
    };
    // 风险审查绑定click事件
    var _riskReview_bindClick = function(templateBizConfigId) {
		
        $("#riskReview").bind("click", function() {
            var temParm = '&cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=';// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
            var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId + temParm;
            var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
            // 处理跳转office的url
            $.ajax({
                url : url,
                data : {
                    "returnUrl" : backUrl,
                    "functionPoint" : "jdbg"
                },
                type : 'post',
                dataType : 'json',
                async : true,
                beforeSend : function() {
                    LoadingAnimate.start();
                },
                complete : function() {
                    LoadingAnimate.stop();
                },
                error : function() {
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                },
                success : function(data) {
                    var poCntObj = $.parseJSON(data.poCnt);
                    mfPageOffice.openPageOffice(poCntObj);
                }
            });
        });
    };
	/** 操作按钮状态控制初始化 */
	var _operatButtons_init = function() {
        /*if (appSts != '0' && appSts != '1') {
            $("#MfBusApply_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
            $("#MfBusApply_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
        }*/
        // 查询当前业务状态
        $.ajax({
            url : webPath+"/mfBusApply/getApplyDetailInfoAjax?appId=" + appId,
            data : {},
            type : 'post',
            dataType : 'json',
            async : true,
            success : function(data) {
            	//如果业务处于审批中或流程结束了，终止业务不能操作
                if (data.mfBusApply.nodeNo == 'apply_approval'||data.wkfFlag=="0") {
                    $("#MfBusApply_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
                    $("#MfBusApply_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
                }
            }
        });
	};

	//房屋评估
	var _eval = function (){
		top.window.openBigForm(webPath+"/mfPledgeEvalInfo/input?appId=" + appId ,'房屋评估', myclose);
	};
	var _carEval=function(){
		top.window.openBigForm(webPath+"/mfPledgeCarEval/input?appId=" + appId +"&cusNo="+cusNo+"&from=zm_nfa",'车辆评估', myclose);
	};
	var _houseEval=function(){
		top.window.openBigForm(webPath+"/mfPledgeHouseEval/input?appId=" + appId +"&cusNo="+cusNo,'房屋评估', myclose);
	};
	var _carGps=function(){
		top.window.openBigForm(webPath+"/mfPledgeCarGps/input?appId=" + appId +"&cusNo="+cusNo,'车辆GPS查询', myclose);
	};
	var _honeypot=function(){
		top.window.openBigForm(webPath+"/thirdJxlHoneypotRecord/input?appId=" + appId +"&cusNo="+cusNo,'蜜罐报告', myclose);
	};
	var _honeybee=function(){
		top.window.openBigForm(webPath+"/thirdJxlBeeRecord/input?appId=" + appId +"&cusNo="+cusNo+"&from=zm_nfa",'蜜蜂报告', myclose);
	};
	var _che300Eval=function(){
		top.window.openBigForm(webPath+"/thirdChe300EvalRecord/input?appId=" + appId +"&cusNo="+cusNo+"&from=zm_nfa",'商用车询价', myclose);
	};
	
	var _getRiskReport = function(){
		top.window.openBigForm(webPath+"/mfBusApply/riskReport?appId=" + appId +"&cusNo="+cusNo+"&query=query",'风控查询', myclose);
	};

    var _getRiskManagement = function(){
        top.window.openBigForm(webPath+"/apiReturnRecord/riskManagement?appId=" + appId +"&cusNo="+cusNo+"&query=query",'风控查询', myclose);
    };
    //历史数据对比
    var _analysisTable = function(){
    	top.window.openBigForm(webPath + "/mfAnalysisTable/analysisTable?appId=" + appId ,'历史数据对比', myclose);
    };

	var _analysisTable_init = function (){
    	var url =webPath + "/mfAnalysisTable/getAnalysisTable?appId=" + appId;
        $.ajax({
            url : url,
            type : 'post',
            dataType : 'json',
            async : true,
            beforeSend : function() {
                LoadingAnimate.start();
            },
            complete : function() {
                LoadingAnimate.stop();
            },
            success : function(data) {
              if(data.errorCode=='success'){
					var flag = data.flag;
					if(flag=='0'){
					}else{
                        $("#analysisTable").removeClass("btn-lightgray");// 去掉灰色样式
                        $("#analysisTable").addClass("btn-forestgreen");// 添加绿色样式
					}
			  }
            }
        });
	};

	//选择共同借款人
    var _selectCocoborrMuti = function (){
        selectCocoborrMutiDialog(cusNo,appId,function (cusInfo) {
			$("input[name=coborrNo]").val(cusInfo.coborrNo);
			$("input[name=coborrName]").val(cusInfo.coborrName);
        });
    };

	return {
		filePrint : _filePrint,
		surveyReport_init : _surveyReport_init,
		disagree : _disagree,
		eval : _eval,
		carEval : _carEval,
		operatButtons_init : _operatButtons_init,
		houseEval : _houseEval,
		carGps : _carGps,
		honeypot : _honeypot,
		honeybee : _honeybee,
		che300Eval : _che300Eval,
		getRiskReport:_getRiskReport,
        getRiskManagement:_getRiskManagement,
        riskReview_init: _riskReview_init,
        analysisTable:_analysisTable,
        analysisTable_init:_analysisTable_init,
        selectCocoborrMuti:_selectCocoborrMuti
	};
}(window, jQuery);

$(function(){
	var nodeName;

	/**滚动条**/
	$("body").mCustomScrollbar({
		advanced:{
			updateOnContentResize:true
		},
		callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
			whileScrolling: function(){
				if ($(".changeval").length>0) {
					$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
				}
			}
		}
	});
	/**显示流程**/
	
	getNextBusPoint();
	dblclickflag();
    selectRiskFlag();
	MfBusApply_DynaDetail.surveyReport_init();// 尽调报告按钮初始化
	MfBusApply_DynaDetail.operatButtons_init();// 操作按钮状态控制初始化
	MfCreditQueryRecordInfo.init();//征信查询按钮初始化
	MfThirdMftccHighcourt.init();//法执情况按钮初始化
	top.LoadingAnimate.stop();
    MfBusApply_DynaDetail.riskReview_init();// 风险审查按钮初始化

});

function setBlockTitle(showType,title,name){
	var htmlStr = "";
	var _name = name;
	if(name!=null&&name!=""){
			_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
			_name =_name.replace("Action","");
	}
	/**
	 * 系统添加了映射地址，拼接的URL地址需要添加webPath，如果不添加拼接的URL地址不正确导致方法无法访问（404） 2018年5月4日17:49:11
	 */
	_name = webPath +"/"+_name;
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		htmlStr = htmlStr + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form  action='"+_name+"/updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "2"){
		var tableStr = "";
		htmlStr = htmlStr + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
		+ "<div class='list-table'>"
		+ "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"+tableStr+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}
}

function setBlock(showType,title,name,getUrl){
	$.ajax({
		url:getUrl,
		type:'post',
		dataType:'html',
		success:function(data){
			var $html = $(data);
			if(showType == "1"){
				setBlockTitle(showType,title,name);
				var formStr = $html.find("form").prop("outerHTML");
				$(".content[name='"+name+"']").html(formStr);
			}else if(showType == "2"){  
				if($html.find("table #tab tr").length>0){
					setBlockTitle(showType,title,name);
					var tableStr = $html.find("table").prop("outerHTML");
					$(".content[name='"+name+"']").html(tableStr);
				}
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

//单独提交业务流程
function doCommitProcess(){
	$.ajax({
		url:webPath+"/mfBusApply/commitBusProcessAjax?appId="+appId,
		success:function(data){
			if(data.flag=="success"){
				alert(data.msg,1);
				getNextBusPoint();
				$("#wj-modeler1").empty();
				showWkfFlow($("#wj-modeler1"),wkfAppId);
			}else{								    
				alert(data.msg,0);
			}
		}
	});
}

function getNextBusPoint(){
	$(".block-next").empty();
	$(".block-next").unbind();
	var busPointInfo = '';
	$.ajax({
		url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.wkfFlag!="0"){
				appSts = data.mfBusApply.appSts;
                var signSts = data.mfBusApply.signSts;
				var approveInfo="";
				var approveNodeName=null;
				var approvePartName=null;
				var info = "";
				//当前处于业务审批的发回补充资料岗位,展示补充资料
				if (data.mfBusApply.nodeNo == "supplement_data"){
                    info = "补充资料";
				}
				if(appSts == '1' || appSts == '6'){
                    if(signSts == "11") {
                        $(".block-next").append("<span>请先进行复议操作</span>");
                        $(".next-div").removeClass("hide");
                        $(".next-div").addClass("show");
                    }else{
                        if (data.sysTaskInfo) {// 当前操作员有审批权限, 直接打开审批页面
                            $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>" + data.approvalTask.description + info+">></a></span>");
                            $(".block-next").click(function() {
                            	window.top.createTaskShowDialog(data.sysTaskInfo.pasUrl, data.title, "100", "100" , null, refreshNextBusPointAndWkfFlow );
                            });
                            $(".next-div").removeClass("hide");
                            $(".next-div").addClass("show");
                        } else {
                        	if(info==""){
                                info = "审批";
							}
                            approveNodeName=data.mfBusApply.approveNodeName;
                            approvePartName=data.mfBusApply.approvePartName;
                            if(approvePartName!=""&&approvePartName!=null){
                                var nodeNameArr = approveNodeName.split(",");
                                var partNameArr = approvePartName.split(",");
                                var tipStr = "";
                                for(var i=0;i<nodeNameArr.length;i++){//如果该岗位有多个操作员则合并一起
                                    if(i==0){
                                        tipStr = tipStr + nodeNameArr[i] +"岗位的【" + partNameArr[i];
                                    }else{
                                        tipStr = tipStr +"，"+ partNameArr[i];
                                    }
                                }
                                //tipStr = tipStr.substring(0, tipStr.length-1);
                                approveInfo="正在"+tipStr+"】"+info;
                            }else{
                                approveInfo="正在"+approveNodeName+"岗位"+info;
                            }
                            if(approveInfo.length>40){
                                //提示内容长度大于40时，截取展示并添加鼠标title提示。
                                var approveTitleInfo=approveInfo;
                                approveInfo=approveInfo.substring(0, 40)+"...";
                                busPointInfo = "<span title="+approveTitleInfo+">申请已提交，"+approveInfo+"</span>";
                            }else{
                                busPointInfo = "<span>申请已提交，"+approveInfo+"</span>";
                            }
                            $(".block-next").append(busPointInfo);
                            $(".next-div").removeClass("hide");
                            $(".next-div").addClass("show");
                        }
                    }
				}else if(appSts=="2" && (data.nodeName=="apply_approval" || data.nodeName=="primary_apply_approval")){//如果审批状态是通过，但是当前节点还是在业务审批阶段的话，需要手动触发一下业务流程的提交
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("manual_submit");
					if(checkPmsBizFlag){
                        if(data.ONLY_MNG_EDIT_APP==1){
                            $(".block-next").append("<span>非当前业务客户经理，无法操作</span>");
                        }else{
                            $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>业务提交>></a></span>");
                            $(".block-next").click(function(){
                                alert(top.getMessage("CONFIRM_OPERATION","业务提交"),2,function(){
                                    doCommitProcess();
                                });
                            });
                        }
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}else{
						$(".block-next").append("<span>暂时没有权限操作业务提交</span>");
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}
				}else if(appSts == '4'){//审批通过
					busPointInfo = "<span>申请已审批通过，请在签约阶段中查看信息</span>";
					$(".block-next").append(busPointInfo);
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
				}else if(appSts == '5'){//申请被否决
					busPointInfo = "<span>申请已被否决，业务结束</span>";
					$(".block-next").append(busPointInfo);
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
				}else{
					$.ajax({
						url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
						type:'post',
						dataType:'json',
						success:function(data){
							var url = data.url;
							var title=data.title;
							nodeName = data.nodeName;
							if(data.wkfFlag!="0"){
								var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
								if(checkPmsBizFlag){
									//分单特殊处理
									if(data.assign){
										$(".block-next").append("<span>暂时没有权限操作该节点</span>");
										$(".next-div").removeClass("hide");
										$(".next-div").addClass("show");
									}else if(data.isShow){
										$(".block-next").append("<span>"+title+"</span>");
										$(".next-div").removeClass("hide");
										$(".next-div").addClass("show");
										
									}else{
                                        if(data.ONLY_MNG_EDIT_APP==1){
                                            $(".block-next").append("<span>非当前业务客户经理，无法操作</span>");
                                        }else{

                                            $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+">></a></span>");
                                            $(".block-next").click(function(){
                                                if(nodeName=="credit_resp" || nodeName=="resp_investigation" ){
                                                    $.ajax({
                                                        url: webPath+"/mfBusApply/checkIfEval",
                                                        type:"post",
                                                        dataType:"json",
                                                        data:{
                                                            relNo:appId,
                                                            type:"2"
                                                        },
                                                        success:function(data){
                                                            if(data.flag == "success"){
                                                                toNextBusPoint(url, title, nodeName);
                                                            }else{
                                                                alert(top.getMessage("FIRST_OPERATION","详审评级"),3);
                                                            }
                                                        },error:function(){
                                                            alert(top.getMessage("ERROR_SERVER"),0);
                                                        }
                                                    });
                                                }else{
                                                    toNextBusPoint(url, title, nodeName);
                                                }
                                            });
                                        }
										$(".next-div").removeClass("hide");
										$(".next-div").addClass("show");
										if(nodeName!="investigation"){
											//调查资料
											var vouType = '${vouType}';
											if(vouType!="1" && pleFlag=="1"){
												$(".bus-investigate").show();
											}
										}
									}
								}else{
									$(".block-next").append("<span>暂时没有权限操作该节点</span>");
									$(".next-div").removeClass("hide");
									$(".next-div").addClass("show");
								}
							}
						}
					});
				}
			}else{
                $(".next-div").addClass("hide");
                $(".app-process").addClass("hide");
			}
            MfBusApply_DynaDetail.operatButtons_init();
		}
	});
};
//吉时与项目刷新风控块
function   refreshNextRisk(){
    $.ajax({
        url : webPath+"/MfApplyExaminationApproval/getRiskApproveBaseListAppId",
        data : {
            appId:appId
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.flag == "success"){
                $("#mfRiskApproveBaseListDiv").show();
                $("#mfRiskApproveBaseList").html(data.tableHtml);
                dblclickflag();
            }
        },
        error : function(data) {
            alert(data.msg, 0);
        }
    });
}


//吉时与项目刷新电核块
function   refreshNextCall(){
    $.ajax({
        url : webPath+"/MfApplyExaminationApproval/getCallApproveBaseListAppId",
        data : {
            appId:appId
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.flag == "success"){
                $("#mfCallApproveBaseListDiv").show();
                $("#mfCallApproveBaseList").html(data.tableHtml);
                dblclickflag();
            }
        },
        error : function(data) {
            alert(data.msg, 0);
        }
    });
}




function refreshNextBusPointAndWkfFlow() {
    getNextBusPoint();
    pubApplyDetailInfo.init();
	//刷新审批状态
    MfBusApply_DynaDetail.operatButtons_init();
   /* refreshNextCall();方法已经注释掉不用了
    refreshNextRisk();*/
   $("#wj-modeler1").empty();
   showWkfFlow($("#wj-modeler1"),wkfAppId);
   //刷新业务审批历史
   $("#wj-modeler2").empty();
   showWkfFlowVertical($("#wj-modeler2"),appId,"5","apply_approval");
}

//跳转至下一业务节点
function toNextBusPoint(url,title,nodeName){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
//	if(riskLevel == "99"){//riskLevel为99标书拒绝级业务
//		var pointInfo = '<div style="height: 120px;padding: 20px;width: 300px;"><div style="height: 40px;">该业务风险过高，无法进行下一步</div><div><a  href="javaScript:void(0);" onclick="busRisk();">查看风险>></a></div></div>';
//		dialog({
//			title:'风险提示',
//			id:"riskInfoDialog",
//			backdropOpacity:0,
//			content:pointInfo
//		}).showModal();
//		return false;
//	}
			top.flag=false;//表示是否进行业务操作
			top.submitFlag=false;
			top.pleFlag = false;
			top.tuningReport = false;
			top.relCorpflag = "";//关联企业标志
			top.getInfoFlag = false;//业务操作后表示是否需要获得信息
			top.toCoreCompanyFlag = false;//是否跳转到核心企业列表
			var tmpUrl=url.split("&")[0];
			var popFlag = tmpUrl.split("?")[1].split("=")[1];
			if(popFlag=="0"){
				alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
					if(!valiDocIsUp(cusNo)){
						return;
					} 
					$.ajax({
						url:url,
						success:function(data){
							if(data.flag=="success"){
								if(data.appSts){
									appSts = data.appSts;
								}
								if(data.node=="processaudit"){
									//审批提醒弹窗，不自动关闭
									getNextBusPoint();
								}else{
									getNextBusPoint();
									$("#wj-modeler1").empty();
									showWkfFlow($("#wj-modeler1"),wkfAppId);
								}
								//initDocNodes();//重新初始化要件
							}else{								    
								//alert(data.msg,0);
							}
						}
					});
				});
			} else{
				if(nodeName=="investigation"){
					url = url+"&scNo="+investigateScNo;
					top.window.openBigForm(url,title,function(){
						if(top.investigation){
							$.ajax({
								url:webPath+"/docManage/getDocNodesAjax?"+docParm,
								type:"POST",
								dataType:"json",
								success:function(data){
									query =data.query;
									zTreeNodesDoc = data.zTreeNodes;
									var zTreeObj = $.fn.zTree.init($("#uploadTree"), setting, zTreeNodesDoc);
								},error:function(){
									
								}
							});
							wkfCallBack();
						}						
					});
				}
				//押品登记节点，选择押品类别
				if(nodeName=="pledge_reg"||nodeName=="account_info_collect" ||nodeName=="lease_info_collect"){
					top.collaFlag = false;
					top.vouType=vouType;
                    top.skipFlag=false;//是否跳过应收账款采集
					if(nodeName=="account_info_collect"){
						top.collaType="account";
					}else if(nodeName=="lease_info_collect"){
						top.collaType="lease";
					}else{
						top.collaType="pledge";
					}
					
					top.window.openBigForm(url,title,goToCollaDetailInfo);
				}else{
					top.window.openBigForm(url,title,wkfCallBack);
				}
			}
//			}
//		});
}

//回调函数
function wkfCallBack(){
	if(top.flag){
		if(top.pleFlag){//押品信息回调处理
			setPleInfo(top.pleInfo);
			$(".bus-investigate").show();
		}
		if(top.relCorpflag!=""){//关联企业信息回调处理
			setRelateCorpInfo(top.relCorpflag,top.cusInfo.cusNo,top.cusInfo.cusName);
		}
		if(top.getInfoFlag){
			
			if(top.showType != null){
				setBlock(top.showType,top.title,top.name,top.getInfoUrl);
			}
		}
		//用章申请刷新展示
		if(top.sealConfirm){
			$("#busSealConfirmAddBase").parent().parent().show();
			$("#busSealConfirmAddBase").empty().html(top.formbusSealConfirmAddBase);
		    if(top.opinionType == 'refuse'){
				window.top.alert(top.sealMsg, 3);
		    }
		}
		if(top.riskFlag){
			pubApplyHeadInfo.init();
			top.riskFlag = false;
		}
		if(top.tuningReport){//尽调报告
			appSts = top.appSts;
			if(top.refsh){//需要刷新
				refreshApplyDetailInfo('appreportDetailForm',top.appreportDetail);
//				oneCallback(eval("("+top.applyInfo+")"));
				pubApplyDetailInfo.init();
				//头部信息刷新
				pubApplyHeadInfo.init();
				if(applyProcessId!="" && applyProcessId!=null){
					//业务视角登记的客户表单在审批提交后不允许再操作（新增、删除、单子段编辑）
					$(".bus-cus-info .formAdd-btn").hide();//新增
					$(".bus-cus-info .delBtn").html("删除");//删除
					busSubmitCnt="1";
				}
                if(top.appId!="" && top.appId!="undefined") {//业务申请
                  var  url = webPath + "/mfBusApply/findCoborrNo?appId=" + top.appId
                }else{
                 var   url = webPath + "/mfBusPact/findCoborrNo?appId=" + top.appId
				}
                    $.ajax({
                        url:url,
                        type:'post',
                        dataType:'json',
                        success:function(data){
                            if(typeof(sign)!="undefined"&&sign=="detail"){
                                var html = data.tableHtml;
                                $("#applyCusBorrowerList").empty().html(html);
                            }
                        }
                    });

				top.refsh = false;
				//尽调报告按钮刷新
				MfBusApply_DynaDetail.surveyReport_init();
				//风险审查按钮刷新
                MfBusApply_DynaDetail.riskReview_init();

			}
			top.tuningReport = false;
		}
		if(top.reinsurance_policy){//分单
			appSts = top.appSts;
			top.reinsurance_policy = false;
		}
		if(top.appSts){
			appSts = top.appSts;
		}
		if(top.toCoreCompanyFlag){
			window.location.href=webPath+"/mfCusCoreCompany/getListPage";
			$outerWest.find("li").eq("4").addClass("menu-active");
		}
		getNextBusPoint();
		$("#wj-modeler1").empty();
		
		showWkfFlow($("#wj-modeler1"),wkfAppId);
		initDocNodes();//重新初始化要件
	}
	
}
var $outerWest = $("#a_iframe", window.top.document).parent().parent().parent().next();//左侧菜单
function openPleDyForm(url,title){
	$.ajax({
		url:webPath+"/mfBusPledge/getPleDyFormInfo",
		data:{appId:appId},
		type:'post',
		dataType:'json',
		success:function(data){
			top.collaFlag=false;
			var formid_new = data.formid_new;
			url = url+'&formid_new='+formid_new;
			top.window.openBigForm(url,title,goToCollaDetailInfo);
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
}


function  refreshApplyDetailInfo(divId,htmlStr){
	var html = '<form  id="MfBusApplyActionAjax_updateAjaxByOne_action" name="operform" action="/factor/MfBusApplyActionAjax_updateAjaxByOne_action" method="post">'+htmlStr+'</form>';
	$("#"+divId).empty().html(html);
	$("#InterviewInformationDiv").show();
};
//获取押品信息
function goToCollaDetailInfo(){
	if(top.collaFlag){
		if(top.vouType=="1"||top.vouTypeShort=="1"){
			getNextBusPoint();
			$("#wj-modeler1").empty();
			showWkfFlow($("#wj-modeler1"),wkfAppId);
		}else{
			top.LoadingAnimate.start();
			//如果是担保跳过成功，刷新流程指引
            if(top.skipFlag){
                getNextBusPoint();
                $("#wj-modeler1").empty();
                showWkfFlow($("#wj-modeler1"),wkfAppId);
                top.LoadingAnimate.stop();
            }else{
                var collaType=top.collaType;
                if(typeof(collaType)=='undefined' ||collaType=='' || collaType==null){
                    collaType='pledge';
                }
                if(collaType=='account'){
                    /*if(top.skipFlag){
                        getNextBusPoint();
                        $("#wj-modeler1").empty();
                        showWkfFlow($("#wj-modeler1"),wkfAppId);
                        top.LoadingAnimate.stop();
                    }else{
                        window.location.href=webPath+"/mfBusReceBaseInfo/getReceDetail?busEntrance="+busEntrance+"&cusNo="+cusNo+"&appId="+appId;
                    }*/
                    window.location.href=webPath+"/mfBusReceBaseInfo/getReceDetail?busEntrance="+busEntrance+"&cusNo="+cusNo+"&appId="+appId;
                    // window.location.href=webPath+"/mfRecievables/getCollateralInfo?busEntrance=1&cusNo="+cusNo+"&appId="+appId+"&relId="+appId+"&entrance="+entrance+"&collateralType="+collaType;
                }else if(collaType=='lease'){
                    window.location.href=webPath+"/mfLeases/getCollateralInfo?busEntrance=1&cusNo="+cusNo+"&appId="+appId+"&relId="+appId+"&entrance="+entrance+"&collateralType="+collaType;
                }else{
                    window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?busEntrance=apply&cusNo="+cusNo+"&appId="+appId+"&relId="+appId+"&entrance="+entrance+"&collateralType="+collaType;
                }
			}


			
		}
	}
};
//选择押品类别
function selectPledgeClass(url,title){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	$.ajax({
		url:webPath+"/mfBusPledge/getIfRegisterPledge",
		data:{appId:appId},
		type:'post',
		dataType:'json',
		success:function(data){
			//没有登记押品信息，打开押品类别；登记过，打开押品清单登记页面
			if(data.flag == "0"){
				top.openBigForm(webPath+'/mfPledgeClass/getAllPledgeClassList?cusNo='+cusNo+'&appId='+appId,'押品类别',wkfCallBack);
			}else{
				top.window.openBigForm(url,title,wkfCallBack);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
}
//关联企业信息回调处理
function setRelateCorpInfo(flag,cusno,cusname){
	var htmlStr = "";
	if(flag =="wareHouseFlag"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="cuswarehouse">'
		+'<i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="getInfoForView(\'103\',\''+cusno+'\',\'仓储机构\');">'+cusname+'</a>保管货物 </span>'
		+'</span>';
	}else if(flag=="coreFlag"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="cuscore">'
		+'<i class="i i-qiYe"></i><span>由核心企业<a href="javascript:void(0);"  onclick="getInfoForView(\'108\',\''+cusno+'\',\'核心企业\');">'+cusname+'</a>推荐 </span>'
		+'</span>';
	}else if(flag=="fundOrgFlag"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="fundorg">'
		+'<i class="i i-fundorg"></i><span>由资金机构<a href="javascript:void(0);"  onclick="getInfoForView(\'109\',\''+cusno+'\',\'资金机构\');">'+cusname+'</a>放款 </span>'
		+'</span>';
	}
	$(".btn-special").append(htmlStr);
}


function setPleInfo(pleInfo){
	$("#pleInfo").empty();
	var htmlStr ='<button type="button" class="btn btn-font-qiehuan pull-right" onclick="getPleInfo();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
		+'<div class="col-xs-3 col-md-3 column">'
		+'<button type="button" class="btn btn-font-pledge padding_left_15" onclick="getPleInfo();">'
		+'<i class="i i-pledge font-icon"></i>'
		+'</button>'
		+'<div class="font-text">押品信息</div>'
		+'</div>'
		+'<div class="col-xs-9 col-md-9 column">'
		+'<button class="btn btn-link content-title"  title="'+pleInfo.pledgeName+'" onclick="getPleInfo();">'
		+ pleInfo.pledgeName
		+'</button>'
		+'<p>'
		+'<span class="content-span" id="envalueAmt"><i class="i i-rmb" ></i>'+pleInfo.envalueAmt+'</span><span class="unit-span">万</span>'
		+'<span class="content-span" id="receiptsAmount"><i class="i i-danju" ></i>'+pleInfo.receiptsAmount+'</span><span class="unit-span">张单据</span>'
		+'</p>'
		+'</div>'
	$("#pleInfo").html(htmlStr);
	
};

//业务详情页面，切换至客户详情页面
function getCusInfo(cusNoThis){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfCusCustomer/getById?busEntrance="+busEntrance+"&cusNo="+cusNoThis+"&appId="+appId+"&operable="+operable;
}


//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等
function getInfoForView(custype,id,title){
	top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+custype+'&cusNo='+id,title,function(){});
}

//风险拦截
function busRisk(){
	if(!(dialog.get('riskInfoDialog') == null)){
		dialog.get('riskInfoDialog').close();
	}
	top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息',function(){});
};


//验证文档是否上传
function valiDocIsUp(relNo){
	var flag = true;
	$.ajax({
		type: "post",
		dataType: 'json',
		url: webPath+"/docBizManage/valiWkfDocIsUp",
		data:{relNo:relNo},
		async: false,
		success: function(data) {
			if(!data.flag){
				//window.top.alert(data.msg,0);
			}
			flag = data.flag;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
		}
	});
	return flag;
}

/*//费用信息编辑
function getFeeById(obj,url){
	var $obj = $(obj);
	top.obj = $obj.parents(".dynamic-block");
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.window.openBigForm(url,'修改费用项',closeCallBack1);
	};
	
	function closeCallBack1(){
	var $obj = $(top.obj);
	if(top.htmlStrFlag){
		$obj.find(".content").empty();
		$obj.find(".content").html(top.htmlString);
		//adjustheight();
	}
};
*/
//查看产品信息
function getKindInfo(){
	top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',function(){});
}

//多业务大于3条时，弹层列表页面
function getMultiBusList(flag){
	if('apply'==flag){
		top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中项目",function(){});
	}else if('pact'==flag){
		top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"已放款",function(){});
	}else if('finc'==flag){
		top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在保项目",function(){});
	}else if('assure'==flag){
		top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"担保项目",function(){});
    } else if ('fincFinish' == flag) {
        top.openBigForm(webPath + "/mfBusFincApp/getMultiBusFinishList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已解除", function () {
        });
    } else if ('repay' == flag) {
        top.openBigForm(webPath + "/mfRepayHistory/getMfRepayHistoryList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已还款", function () {
        });
    }
}
//切换业务（上一笔、下一笔）
function switchBus(appid,pactid){
	top.LoadingAnimate.start();
	if(pactid==null || pactid ==""){
		window.location.href=webPath+"/mfBusApply/getSummary?appId="+appid;
	}else{
		window.location.href=webPath+"/mfBusPact/getById?appId="+appid;
	}
}

//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){
	if(key=="fincRate" || key=="overFloat" || key=="cmpdFloat"){//当申请利率或者逾期利率上浮发生变化时，逾期利率会联动计算
		var fincRate = $("#applyDetailForm .fieldReal").find("[name=fincRate]").val();
		var overFloat = $("#applyDetailForm .fieldReal").find("[name=overFloat]").val();
		var cmpdFloat = $("#applyDetailForm .fieldReal").find("[name=cmpdFloat]").val();
		// var key = $(".changeval .inputText").attr("name");
		if(key=="fincRate"){
			fincRate = $(".changeval .inputText").val();
		}else if(key =="overFloat"){
			overFloat = $(".changeval .inputText").val();
		}else if(key =="cmpdFloat"){
            cmpdFloat = $(".changeval .inputText").val();
        }
        $.ajax({
			url : webPath+"/mfBusApply/getOverOrCmpdRateAjax",
			data : {appId: appId,fincRate:fincRate,overFloat:overFloat,cmpdFloat:cmpdFloat},
			async : false,
			success : function(data) {
				if(data.flag=="success"){
					$(".fieldShow.overRate").text(data.overRate);
					$(".fieldShow.cmpdRate").text(data.cmpdRate);
				}
			},
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", ""), 0);
			}
		});
        data["fincRate"] = $(".fieldShow.fincRate").text();
        data["overRate"] = $(".fieldShow.overRate").text();
        data["cmpdRate"] = $(".fieldShow.cmpdRate").text();
	}
    if(key =="fincUseSmShow"){//征信用途
        data["fincUseSm"] = $("input[name=fincUseSm]").val();//存储最终的征信用途编号
    }
    if(key =="fincUseSmDes"){//明细投向
		if($("div[class='fieldShow fincUse']") != undefined)
		{
            var tempdetailTo = $("input[name = fincUseSmDes]").val();//明细投向
            var index = tempdetailTo.indexOf('-');
            var detailTo = tempdetailTo.substring(0,index);
            $("div[class='fieldShow fincUse']").text(detailTo);
		}
    }
}

//单字段编辑的保存回调方法。
function oneCallback(data) {
	var $_form = this;
	var formAction = $_form.attr("action");
	if(formAction == webPath+"/mfCusPersonIncomeSurvey/updateAjaxByOne") {//收入流水调查(铁甲网)
		$.ajax({
			url :webPath+"/mfCusPersonIncomeSurvey/getByAppIdAjax",
			type:"post",
			data:{relNo:appId},
			success:function(data){
				if(data!=''){
					var incomeSurvey = data.incomeSurvey;
					$('input[name=incomeMonthAmtTwo]').parents("tr").find("td").eq(2).find('.fieldShow').html(incomeSurvey.incomeMonthTwo);
					$('input[name=incomeMonthAmtThree]').parents("tr").find("td").eq(0).find('.fieldShow').html(incomeSurvey.incomeMonthThree);
					$('input[name=incomeMonthAmtFour]').parents("tr").find("td").eq(2).find('.fieldShow').html(incomeSurvey.incomeMonthFour);
					$('input[name=incomeMonthAmtFive]').parents("tr").find("td").eq(0).find('.fieldShow').html(incomeSurvey.incomeMonthFive);
					$('input[name=incomeMonthAmtSix]').parents("tr").find("td").eq(2).find('.fieldShow').html(incomeSurvey.incomeMonthSix);
					$('input[name=incomeMonthAmtSix]', $_form).parents("tr").next().find("td").eq(0).find('.fieldShow').html(CalcUtil.formatMoney(incomeSurvey.incomeAverageAmt,2));
					$('input[name=incomeMonthAmtSix]', $_form).parents("tr").next().find("td").eq(1).find('.fieldShow').html(CalcUtil.formatMoney(incomeSurvey.incomeSumAmt,2));
				}
			}
		});
	}else{
        var dataParam;
		for(var i in data){
			var name = data[i].name;
			var value = data[i].value;
			if(name=="appAmt"){
				value = value.replace(/,/g,"");
				$("span[id="+name+"]").html(parseFloat(value)/10000);
			}
			if(name=="term"){
				$("span[id="+name+"]").html(value);
			}
			if(name=="fincRate"){
				$("span[id="+name+"]").html(value);
			}
			if(name=="channelSource"){
				dataParam = JSON.stringify($("#applyDetailForm").serializeArray());
				$.ajax({
					url :webPath+"/mfBusApply/updateAjaxByOne",
					type:"post",
					data:{ajaxData:dataParam},
					success:function(data){
					}
				});
			}
			if(name=="coborrName"){//共借人
                dataParam = JSON.stringify($("#applyDetailForm").serializeArray());
                $.ajax({
                    url :webPath+"/mfBusApply/updateAjaxByCoborr",
                    type:"post",
                    data:{ajaxData:dataParam},
                    success:function(data){
                      if(data.flag =="success"){
                          $("#applyCusBorrowerList").empty().html(data.tableData);
					  }
                    }
                });
			}
		}
	}
}
function addedService(){
	LoadingAnimate.start();
	$.ajax({
		url :webPath+"/mfCusCustomer/toAddServicePage",
		type:"post",
		data:{"cusNo":cusNo,"showType":"0"},
		success:function(dataArgs){
			LoadingAnimate.stop();
			if(dataArgs.flag=="success"){
				var url = dataArgs.url;
				var customer = dataArgs.mfCusCustomer;
				var data =customer.headImg;
				if (customer.ifUploadHead != "1") {
					data = "themes/factor/images/" + customer.headImg;
				}
				data = encodeURIComponent(encodeURIComponent(data));
				headImgShowSrc = basePath+webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
				if(customer.cusType=="202"){//个人客户
					//url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=622899911919911&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR";
					url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}else{
					//url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType;
					url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType+"&contactsName="+customer.contactsName+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}
				window.location.href=url;
			}else{
				LoadingAnimate.stop();
				//alert(data.msg, 0);
			}
		}
	});
}
//打开客户基本认证报告页面
function openCustomerCerReport(){
	//查询是否同盾的报告
	var reportData = tdReport("0");
	if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
		if(reportData.errorCode == "00000"){
			alert(reportData.errorMsg, 0);
			return ;
		}
		var TD_data = reportData.data;
		TD_data = $.parseJSON(TD_data);
		$.showTDReport(TD_data);
		return ;
	}
	top.updateFlag = false;
	top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo+"&appId="+appId, '认证报告',function(){},"75","90");
};
//查看风控审批是否通过
function selectRiskFlag(){
    LoadingAnimate.start();
    $.ajax({
        url :webPath+"/apiReturnRecord/getApprovalFlag",
        type:"post",
        data:{appId:appId},
        success:function(data){
            LoadingAnimate.stop();
            if(data.flag=="SelectError"){
                $("#riskQueryManagement").removeClass("btn-forestgreen").addClass("btn-danger").find("span").html("自动审批失败");
            }
        },error : function(data) {
            LoadingAnimate.stop();
            alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}

//处理银行卡号
function dealBankNo(){
	$(".bankNo").each(function(i, item) {
		var itemBankNo = $(item).text();
		var itemBankNoHtml = $(item).html();
		if(/\S{5}/.test(itemBankNo)){
			 $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
		}
	});
};

function addCusFormInfoCall(){
	var action = new Array("MfCusEquityInfoAction","MfCusFamilyInfoAction","MfCusGuaranteeOuterAction","MfCusHighInfoAction","MfCusKeyManAction","MfCusLegalEquityInfoAction","MfCusLegalMemberAction","MfCusShareholderAction","MfCusFamilyInfoAction");		
	if (top.addFlag) {
		$("#rotate-body").find(".rotate-div[name=" + top.action + "]").remove();
		if ($(".dynamic-block[name=" + top.action + "]").length) {
			var $dynamicBlock = $(".dynamic-block[name=" + top.action + "]");
			$dynamicBlock.find(".formDel-btn").remove();
			$dynamicBlock.show();
			if (top.htmlStrFlag) {
				if (top.showType == "1") {
					$dynamicBlock.find(".content form").empty();
					$dynamicBlock.find(".content form").html(top.htmlString);
					$dynamicBlock.find(".formAdd-btn").remove();
				} else {
					$dynamicBlock.find(".content").empty();
					if (top.action == "MfCusBankAccManageAction") {
						$dynamicBlock.find(".content").html(top.htmlString+"<input id='updateByOneUrl' type='hidden' value='${webPath}/mfCusBankAccManage/updateByOneAjax'></input>");
					}else{
						$dynamicBlock.find(".content").html(top.htmlString);
					}	
					
				}
				if(top.action=="MfCusCorpBaseInfoAction"){
					refreshCustomerInfo();
				}
			}
			$("table td[mytitle]:contains('...')").initMytitle();
			
		} else {
			if (top.htmlStrFlag) {
				MfCusDyForm.setBlock(top.showType, top.title, top.action, top.htmlString,"1", top.isMust, top.tableName, null, null,top.sort);
				dblclickflag();
			}
		}
		if (top.action == "MfCusBankAccManageAction") {
			dealBankNo();
		}		
	}
};

function applyEsignHistory(){
    top.window.openBigForm(webPath+"/mfEsignHistory/getListPage?appId=" + appId +"&cusNo="+cusNo,'电签记录查询', myclose);
}

function applyOldBusInfo(){
    top.window.openBigForm(webPath+"/mfBusApply/getSummary?appId=" + appIdOld +"&busEntrance=1&subStringNub=",'原业务信息', myclose);
}
//客户反欺诈报告查询
function getCusAntiFraudReport(){
	top.updateFlag = false;
	//1、首先判断客户是否第一次查询
	$.post('/servicemanage/history/person/reports.json',{itemType:itemType,itemNo:itemNo,idCardNum:idCardNum},function(result){
		if(result.data.length>0){//不是初次查询时，直接展示最新的一条查询结果
			top.window.openBigForm(webPath+'/mfCusCustomer/getCusAntiFraudReport?cusNo='+cusNo+'&firstFlag=0'+'&idNum='+idCardNum, '反欺诈报告',function(){
			});
		}else{
			alert(top.getMessage("CONFIRM_QUERY_COST","9.00"),2,function(){
				top.window.openBigForm(webPath+'/mfCusCustomer/getCusAntiFraudReport?firstFlag=1'+'&cusNo='+cusNo, '反欺诈报告',function(){});
			});
		}
	},'json');
	
}

//同盾认证报告 ,submitFlag-重新提交查询 0-否 1-是
var tdReport = function (submitFlag){
	var resultMap = null;
	$.ajax({
		 type : "post",  
         url : webPath+"/mfTongDunReportAuth/getTDReportAjax?cusNo="+cusNo+"&appId="+appId+"&submitFlag="+submitFlag,  
         async : false,  
         success : function(data){ 
        	 resultMap = data;
         }  
    });
	return resultMap;
};

//工程区域回调
function selectEngineeringAreaBack(areaInfo){
	$("input[name=engineeringArea]").val(areaInfo.disName);
}

//查询银行借款
function  getBorrowBank(){
	$("input[name=cusNameFund]").popupList({
		searchOn: true, //启用搜索
		multiple: false, //false单选，true多选，默认多选
		ajaxUrl : webPath+"/mfBusAgencies/getMfBusPageListAjax", // 请求数据URL
		valueElem:"input[name='cusNoFund']",//真实值选择器
		title: "选择机构",//标题
		changeCallback:function(elem){//回调方法
			var tmpData = elem.data("selectData");
			$("input[name=cusNoFund]").val(tmpData.agenciesDetailUid);
			$("input[name=cusNameFund]").val(tmpData.agenciesDetailName);
		},
		tablehead:{//列表显示列配置
			"agenciesDetailName":"名称",
			"agenciesDetailUid":"机构编号"
		},
		returnData:{//返回值配置
			disName:"agenciesDetailName",//显示值
			value:"agenciesDetailUid"//真实值
		}
	});
	$("input[name=cusNameFund]").next().click();
 }
