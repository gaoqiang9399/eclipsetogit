;
var MfBusPact_DynaDetail = function(window, $) {
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
					alert("终止业务失败_25193410004516867:" + this.url, 0);
				},
				success : function(data) {
					if (data.flag == 'success') {// 终止业务成功
						window.location.href = webPath+"/mfBusPact/getById?busEntrance=2&appId=" + appId;
					} else {
						window.top.alert(top.getMessage("FAILED_OPERATION", "终止业务"), 1);
					}
				}
			});
		});
	};

	/** 放款及放款之前都允许否决, 以权限控制此功能是否开放 */
	var _disagree2 = function() {
		alert(top.getMessage("CONFIRM_OPERATION", "拒绝业务"), 2, function() {
			$.ajax({
				url : webPath+"/mfBusPact/disagree2?appId=" + appId,
				data : {},
				type : 'post',
				dataType : 'json',
				async : true,// true异步, false同步
				error : function() {
					alert("拒绝业务失败_20171106161438001:" + this.url, 0);
				},
				success : function(data) {
					if (data.flag == 'success') {// 拒绝业务成功
						window.location.href = webPath+"/mfBusPact/getById?busEntrance=2&appId=" + appId;
					} else {
						window.top.alert(top.getMessage("FAILED_OPERATION", "拒绝业务"), 1);
					}
				}
			});
		});
	};
	// 文件打印
	var _filePrint = function() {
		var tempParm = "&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId +  "&pleId=" + pleId;
		top.window.openBigForm(encodeURI(webPath+"/mfTemplateBizConfig/getTemplateListPage?relNo=" + pactId + "&modelNo=" + pactModelNo + "&generatePhase=05|06|07|08" + tempParm), '文件打印', myclose);
	};
	// 文件打印
	var _repayPlanTrial = function() {
		var tempParm = "&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId=" + pleId;
		top.window.openBigForm(encodeURI(webPath+"/mfRepayPlanTrial/input?" + tempParm), '还款计划试算', myclose);
	};
    // 三方放款
    var _thirdPay = function() {
        var tempParm = "&appId=" + appId;
        top.window.openBigForm(encodeURI(webPath+"/mfRepayPlan/thirdPayRepayPlanList?" + tempParm), '三方放款', myclose);
    };

    // 尽调报告按钮初始化
    var _surveyReport_init = function() {
        // 查询当前是否已保存尽调报告
        $.ajax({
            url: webPath + "/mfTuningReport/getByAppIdAjax?appId=" + appId,
            data: {},
            type: 'post',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (data.uploadSize == 1) {// 保存过尽调报告
                    $("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式
                    _surveyReport_bindClick(appId);
                }
            }
        });
    }
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
        if (pactSts == '6' || pactSts == '5'|| pactSts == '2') {
            $("#MfBusPact_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
            $("#MfBusPact_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
            // // 放款及放款之前都允许否决, 以权限控制此功能是否开放
            // $("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", true);// 拒绝业务
            // $("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt-dont");// 拒绝业务
        }
		$("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", true);// 拒绝业务
		$("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt-dont");// 拒绝业务
		if(fincSts=="0" || fincSts=="1" ||fincSts=="4"){
			$("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", false);
			$("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt");
		}
        if(pactEndFlag=='0'){
            $("#MfBusPact_DynaDetail_pactEnd_button").attr("disabled", true);//提前解约
            $("#MfBusPact_DynaDetail_pactEnd_button").attr("class", "btn btn-opt-dont");//提前解约
        }
	};
	//结束放款。
	var _finishLoan = function(nodeNo) {
		alert(top.getMessage("CONFIRM_OPERATION", "结束放款"), 2, function() {
			$.ajax({
				url : webPath+"/mfBusApply/doCommitToNodeByNodeNoAjax",
				data : {appId:appId,nodeNo:nodeNo},
				type : 'post',
				dataType : 'json',
				async : true,// true异步, false同步
				error : function() {
					alert("结束放款业务失败_25193410004516867:" + this.url, 0);
				},
				success : function(data) {
					if (data.flag == 'success') {// 结束放款成功
						getNextBusPoint();
						$("#wj-modeler1").empty();
						showWkfFlow($("#wj-modeler1"),wkfAppId);
					} else {
						window.top.alert(top.getMessage("FAILED_OPERATION", "结束放款"), 1);
					}
				}
			});
		});
	};
    // 贷后检查按钮初始化
    var _loanAfterExamine_init = function() {
        // 查询当前合同是否存在已经走到还款阶段的业务数据
        var parmData = {'pactId': pactId};
        $.ajax({
            url : webPath+"/mfBusPact/checkCusInfoByAjax",
            data : {ajaxData: JSON.stringify(parmData)},
            type : 'post',
            dataType : 'json',
            async : true,
            success : function(data) {
                if (data.flag == "success") {
                    $("#loanAfterExamine").attr("disabled", false);
                    $("#loanAfterExamine").attr("class", "btn btn-opt");
                }else{
                    $("#loanAfterExamine").attr("disabled", true);
                    $("#loanAfterExamine").attr("class", "btn btn-opt-dont");
				}
            }
        });
    };

	return {
		filePrint : _filePrint,
		surveyReport_init : _surveyReport_init,
		disagree : _disagree,
		disagree2 : _disagree2,
		operatButtons_init : _operatButtons_init,
		repayPlanTrial : _repayPlanTrial,
		finishLoan:_finishLoan,
        riskReview_init: _riskReview_init,
        thirdPay: _thirdPay,
        loanAfterExamine_init: _loanAfterExamine_init
	};
}(window, jQuery);

$(function(){
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
	
	getNextBusPoint();
	//判断贷后跟踪审批页面跳转过来合同审批历史及放款审批历史不展示
	if(examHis == 'examHis'){
		$("#pactSpInfo-block").hide();
		$("#fincSpInfo-block").hide();
		$("#fiveClass-block").hide();
	}
	//判断有无法律诉讼
	if(hasLawsuit==1){
		$("#has-lawsuit").addClass("btn-dodgerblue");
	}
	//如果进行过催收
	if(hasRecallFlag=="1"){
		$("#recallbase").removeClass("hide");
		if(recallingFlag=="0"){
			$("#recallspan").text("催收完成");
			$("#recallbase").removeClass("btn-danger");
			$("#recallbase").addClass("btn-forestgreen");
		}
	}
	getFiveclassSts();
	if(fiveFlag=="fiveFlag"){//bug修改：有此标识，只显示五级分类
		$("#pactSpInfo-block").hide();
		$("#fincSpInfo-block").hide();
		$("#spInfo-block").hide();
	}
	top.LoadingAnimate.stop(); 

	MfBusPact_DynaDetail.surveyReport_init();// 尽调报告按钮初始化
	MfBusPact_DynaDetail.operatButtons_init();// 操作按钮状态控制初始化
    MfBusPact_DynaDetail.riskReview_init();//风险审查按钮刷新
    MfBusPact_DynaDetail.loanAfterExamine_init();//贷后检查按钮刷新
    //调用贷后检查初始化方法，处理是否已存在贷后检查
    BusExamine.init();

});


function setBlockTitle(showType,title,name){
	var htmlStr = "";
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form action='"+name+"Ajax_updateAjaxByOne.action' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "2"){
		var tableStr = "";
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='list-table'>"
		+ "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content margin_left_15 collapse in' id='"+name+"' name='"+name+"'>"+tableStr+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "3"){
		var formInfo = "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"	
		 + "<div class='content collapse in' style='margin-top:15px;' id='"+name+"' name='"+name+"'></div>";
		htmlStr = htmlStr + "<div class='dynamic-block'><div class='form-table'>"
				+ formInfo +  "</div></div>";
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
                var formStr;
				if(showType == "1" ){
					setBlockTitle(showType,title,name);
					formStr = $html.find("form").prop("outerHTML");
					$(".content[name='"+name+"']").html(formStr);
				}else if(showType == "2"){
					if($html.find("table #tab tr").length>0){
						setBlockTitle(showType,title,name);
						var tableStr = $html.find("table").prop("outerHTML");
						$(".content[name='"+name+"']").html(tableStr);
					}
				}else if(showType == "3"){
					setBlockTitle(showType,title,name);
					formStr = $html.find(".bigForm_content_form").prop("outerHTML");
					$(".content[name='"+name+"']").html(formStr);
				}
				
				//adjustheight();
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	//刷新合同详情信息
	function  refreshPactDetailInfo(divId,htmlStr){
		var html = '<form id="MfCusCorpBaseInfoActionAjax_updateAjaxByOne_action" name="operform" action="/factor/mfCusCorpBaseInfo/updateAjaxByOne" method="post">'+htmlStr+'</form>';
		$("#"+divId).html(html);
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
	
	//处理下一步提示信息
	function dealNextTip(url,title,nodeName){
		$.ajax({
			url:webPath+"/mfBusPact/getPactFincDetailInfoAjax?appId="+appId+"&pactId="+pactId,
			type:'post',
			dataType:'json',
			success:function(data){
				pactSts = data.pactSts;
				fincSts = data.fincSts;
				if(fincSts== '2' || pactSts == '2' || pactSts == '3' ||transferSts=='1' || nodeName=="pledge_approval"||nodeName=="certidInfo_appr"){
					var approveInfo="";
					var approveNodeName=null;
					var approvePartName=null;
					$.ajax({
						url:webPath+"/mfBusPact/getBussFlowApproveInfoAjax?pactId="+pactId,
						type:'post',
						dataType:'json',
						success:function(data){
							approveNodeName=data.approveNodeName;
							approvePartName=data.approvePartName;
                            var info = "审批";
                            //当前处于发回补充资料岗位,展示补充资料
                            if(data.nodeNo != null){
                                if (data.nodeNo.indexOf("supplement_data")!=-1){
                                    info = "补充资料";
                                }
                            }
							if(approvePartName!=""&&approvePartName!=null){
								approveInfo="正在"+approveNodeName+"岗位的"+approvePartName+info;
							}else{
								approveInfo="正在"+approveNodeName+"岗位"+info;
							}
							var busPointInfo="";
							if(approveInfo.length>40){
								//提示内容长度大于40时，截取展示并添加鼠标title提示。
								var approveTitleInfo=approveInfo;
								approveInfo=approveInfo.substring(0, 40)+"...";
								busPointInfo = "<span title="+approveTitleInfo+">"+approveInfo+"</span>";
							}else{
								busPointInfo = "<span>"+approveInfo+"</span>";
							}
							$(".block-next").append(busPointInfo);
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}
					});
				}else if(pactSts == '5' || fincSts == '3'){
					$(".block-next").append("<span>业务被否决，流程终止</span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
				}else if(pactSts == '6' && fincSts == '5'){
                    var busPointInfo = "<span>流程通过，请在贷后阶段中查看信息</span>";
                    $(".block-next").append(busPointInfo);
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
                }else if((pactSts=="4" && nodeName=="contract_approval")||(fincSts=="4" && nodeName=="putout_approval")||(transferSts=="2" && nodeName=="rece_approval")){//如果审批状态是通过，但是当前节点还是在合同审批阶段的话，需要手动触发一下业务流程的提交
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("manual_submit");
					if(checkPmsBizFlag){
						$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>业务提交>></a></span>");
						$(".block-next").click(function(){
							alert(top.getMessage("CONFIRM_OPERATION","业务提交"),2,function(){
								doCommitProcess();
							});
						}); 
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}else{
						$(".block-next").append("<span>暂时没有权限操作业务提交</span>");
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}
                }else if(nodeName=="account_confirm"){//如果是保理业务，融资申请阶段提示进行账款转让
                    $(".block-next").append("<span>当前业务处于费用确认,请联系财务人员操作</span>");
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
				}else if(nodeName=="guarantee_registration"){//如果是工程担保，保函登记在保后处理
                    $(".block-next").append("<span>当前业务处于保函登记,请在保后保函管理处操作</span>");
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
                }else if((pactSts=="4" && nodeName=="expense_confirm")){//如果是保理业务，融资申请阶段提示进行账款转让
                    $(".block-next").append("<span>当前业务处于费用开票,请联系财务人员操作</span>");
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
                }else if(nodeName=="charge-fee-approve"){//缴款通知书提交不允许操作
                    $(".block-next").append("<span>当前业务处于缴款通知书审批中，审批人员为"+data.approveName+"</span>");
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
                }else{
					$.ajax({
						url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
						type:'post',
						dataType:'json',
						success:function(data){
							url = data.url;
                            if(typeof (fincId)!="undefined" && fincId!=null && fincId!=""){
                                url = url+"&fincId="+fincId;
                            }
							var title=data.title;
							nodeName = data.nodeName;
							var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
							if(checkPmsBizFlag){
								if(data.assign){
									$(".block-next").append("<span>暂时没有权限操作该节点</span>");
									$(".next-div").removeClass("hide");
									$(".next-div").addClass("show");
								}else if(data.isShow){
									$(".block-next").append("<span>"+title+"</span>");
									$(".next-div").removeClass("hide");
									$(".next-div").addClass("show");
									
								}else {
                                    if(data.ONLY_MNG_EDIT_APP==1){
                                        $(".block-next").append("<span>非当前业务客户经理，无法操作</span>");
                                    }else{
                                        $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+"&gt;&gt;</a></span>");
                                        $(".next-div").removeClass("hide");
                                        $(".next-div").addClass("show");
                                        $(".next-div").click(function(){
                                            toNextBusPoint(webPath+url,title,nodeName);
                                        });
                                    }
								}
							}else{
								$(".block-next").append("<span>暂时没有权限操作该节点</span>");
								$(".next-div").removeClass("hide");
								$(".next-div").addClass("show");
							}
						}
					});
				}
			}
		});
	}

	function getNextBusPoint(){
		$(".block-next").empty();
		$(".block-next").unbind();
        $(".next-div").unbind();
		$.ajax({
			url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.wkfFlag!="0"){//流程未结束
                    var info = "";
                    //当前处于合同审批的发回补充资料岗位,展示补充资料
                    if (data.mfBusApply.nodeNo.indexOf("supplement_data")!=-1){
                        info = "补充资料";
                    }
					if (data.sysTaskInfo) {// 当前操作员有审批权限, 直接打开审批页面
                        $(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>" + data.approvalTask.description +info+ ">></a></span>");
                        $(".block-next").click(function() {
                            window.top.createTaskShowDialog(data.sysTaskInfo.pasUrl, data.title, "100", "100" , null, refreshNextBusPointAndWkfFlow );
                        });
                        $(".next-div").removeClass("hide");
                        $(".next-div").addClass("show");
                    } else {
                        var url = data.url;
                        if(typeof (fincId)!="undefined" && fincId!=null && fincId!=""){
                            url = url+"&fincId="+fincId;
						}
                        var title = data.title;
                        var nodeName = data.nodeName;
						// var tmpUrl=url.split("&")[0];
						// var popFlag = tmpUrl.split("?")[1].split("=")[1];
                        var result = data.result;
                        $(".block-next").empty();
                        $(".next-div").unbind();

                        dealNextTip(url, title, nodeName);

                        //放款申请节点、放款审批节点、还款计划节点，展示合同放款状态
                        if (nodeName == "putout_apply" || nodeName == "putout_approval" || nodeName == "review_finc") {
                            $('#putoutSts-div').show();
                        }
                    }
				}
				else if(data.wkfFlag=="0"){//流程结束
				    //流程结束时在合同签约详情页面也展示相关流程，因此注释掉
					// $(".next-div").removeClass("show");
					// $(".next-div").addClass("hide");
					// $(".app-process").addClass("hide");
                    $(".block-next").append("<span id='point'>流程结束</span>");
                    $(".next-div").removeClass("hide");
                    $(".next-div").addClass("show");
                    if (data.flowFlag!=undefined && data.flowFlag=="0"){
						$(".next-div").removeClass("show");
                        $(".next-div").addClass("hide");
                        $(".app-process").addClass("hide");
					}
				}
			}
		});
	}

	function refreshNextBusPointAndWkfFlow() {
		getNextBusPoint();
        pubPactDetailInfo.init();
        putoutHisList.init();
		$("#wj-modeler1").empty();
		showWkfFlow($("#wj-modeler1"),wkfAppId);
        if (fincSts != '0' && fincSts != '1' && fincSts != '' && fincProcessId != '') {
        	//业务走到放款审批阶段，刷新放款审批历史的信息
            $("#wj-modeler4").empty();
            showWkfFlowVertical($("#wj-modeler4"), fincChidId, "3","putout_approval");
        }else if(pactSts != '0' && pactSts != '1' && pactProcessId != ''){
            //业务走到合同审批阶段，刷新合同审批历史的信息
            $("#wj-modeler3").empty();
            showWkfFlowVertical($("#wj-modeler3"), pactId, "4","contract_approval");
		}else{
            //业务走到业务审批阶段，刷新业务审批历史的信息
            $("#wj-modeler2").empty();
            showWkfFlowVertical($("#wj-modeler2"),appId,"5","apply_approval");
		}
		//拒绝放款和终止业务按钮控制
        operationButton()
	}

	//跳转至下一业务节点
	function toNextBusPoint(url,title,nodeName){
		top.flag=false;//表示是否进行业务操作
		top.putoutFlag=false;//表示是否是放款申请节点
		top.putoutReviewFlag=false;//表示是否是放款复核节点
		top.getInfoFlag = false;//业务操作后表示是否需要获得信息
		top.pactUpdateFlag=false;//表示是否是合同签约节点
		top.repayReviewFlag=false;//表示是否是还款复核节点
		top.pactDetailInfo = "";
		top.putoutSaveFlag=false;//表示放款标识
		var tmpUrl=url.split("&")[0];
		var popFlag = tmpUrl.split("?")[1].split("=")[1];
		if(popFlag=="0"){
			alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
				if(!valiDocIsUp(pactId)){
					return false;
				} 
				/*if(nodeName=="putout_approval"){//放款审批提交
					url=url+"&fincId="+fincId;
				}*/
				$.ajax({
					url:url,
					type:'post',
					async:false,
	    			dataType:'json',
	    			async:false,
	    			beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){ 
						if(data.flag=="success"){
//							approvalSubFlag="0";
							if(data.node=="processaudit"){ 
								if(data.processType == 'pact'){
									pactSts = data.pactSts;
								}
								if(data.processType == 'finc'){
									fincSts = data.fincSts;
								}
//								approvalSubFlag="1";
								window.top.alert(data.msg,3);
							}else if(data.node=="repaying"){
								window.top.alert(data.msg,3);
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
								$("#repay").show();
								wkfAppId = data.wkfAppId;
							}else if(data.node=="repayed"){
								window.top.alert(data.msg,3);
								wkfAppId = data.wkfAppId;
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
							}else if(data.node == "join_zh"){
								window.top.alert(data.msg,3);
							}
							getNextBusPoint();
							$("#wj-modeler1").empty();
//								showWkfFlow(wkfAppId);
							showWkfFlow($("#wj-modeler1"),wkfAppId);
							initDocNodes();//重新初始化要件
						}else{
                            window.top.alert(data.msg,0);
						}
                        LoadingAnimate.stop();
					},complete: function(){
   						LoadingAnimate.stop();
   					}
				});
			});
		}else{
			//还款计划节点验证是否启用资金方
			if(nodeName=="review_finc" || nodeName=="review_finc_batch"){
				$.ajax({
					type: "post",
					dataType: 'json',
					url: webPath+"/mfBusAppKind/getByAppIdAjax",
					data:{appId:appId},
					async: false,
					success: function(data) {
						if(data.flag=="success"){
							var isFund=data.busAppKind.isFund;
							if(isFund=="1"){
								window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"已启用自动处理,","content2":"再手动操作"}), 3);
							}else if(isFund=="0"){
								if(nodeName=="review_finc_batch"){
                                    top.window.openBigForm(url,title,wkfCallBackForBatch);
                                }else{
                                    top.window.openBigForm(url,title,wkfCallBack);
                                }
							}
						}else{
                            if(nodeName=="review_finc_batch"){
                                top.window.openBigForm(url,title,wkfCallBackForBatch);
                            }else{
                                top.window.openBigForm(url,title,wkfCallBack);
                            }
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
					}
				});
			}else{
				top.window.openBigForm(url,title,wkfCallBack);
			}
		}
	}

	//批量放款申请还款计划保存后回调处理
    // 直接跳转至贷后的借据视角
	function wkfCallBackForBatch(){
        if(top.flag) {
            //放款复核后，处理还款节点以及显示收款计划
            if (top.putoutReviewFlag) {// 成功时到贷后
                window.location.href = webPath+'/mfBusPact/getPactFincById?fincId='+top.fincId+'&appId='+appId+'&busEntrance=finc';
            } else {// 失败时更新页面
                fincId = null;// 避免再次放款申请时还是旧借据号
                wkfCallBack();
			}
        }
	}
		
	//业务流程环节处理后的回调处理
	function wkfCallBack(){
		if(top.flag){
            var tableHtml;
			//放款复核后，处理还款节点以及显示收款计划
			if(top.putoutReviewFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				$("#repay").show();
				tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
								+'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
								+'</div>'
								+'<div class="list-table">'
								+'<div class="content">'
								+ top.tableHtml
								+'</div>'
								+'</div>';
				$("#fincAppDetail").after(tableHtml);
				putoutHisList.init();
			}
			if(top.getInfoFlag){
				fincSts = top.fincSts;
				if(top.showType != null){
					setBlock(top.showType,top.title,top.name,top.getInfoUrl);
				}
			}
			if(top.pactUpdateFlag){
				pactSts = top.pactSts;
//				refreshPactDetailInfo("pactDetailInfo",top.pactDetailInfo);
				pubPactDetailInfo.init();
                //MfBusCoborrList.showCocoborrPactList();
				if(pactSts=="4"){
					$('.btn-file-archive').removeClass('hidden');
				}
			}
			if( top.repayReviewFlag){//还款复核成功后处理
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
					+'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
					+'</div>'
					+'<div class="list-table">'
					+'<div class="content">'
					+ top.tableHtml
					+'</div>'
					+'</div>';
				$("#fincAppDetail").after(tableHtml);
			}
			//放款申请保存后，刷新放款历史
			if(top.putoutSaveFlag){
				putoutHisList.init();
			}
			if(top.transferFlag){
				transferSts="1";
			}
			//铁甲网的GPS登记信息
			if(top.gpsUpdateFlag){
				getGpsDetailInfo("gpsDetailInfo",top.gpsDetailInfo);
			}
			
			//GPS登记信息回调展示列表
			if(top.gpsListUpdateFlag){
				$("#gpsListInfo").empty().html(top.gpsListInfo);
				$("#gpsListDiv").show();
			}
			//刷新流程放到最后    之前可能会处理一下状态
			if(top.putoutFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
			}else{
				getNextBusPoint();
				$("#wj-modeler1").empty();
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				initDocNodes();//重新初始化要件
			}
			//处理拒绝放款和终止业务流程中按钮操作问题
			operationButton();
		}
	};
	//跳转至客户的详情页面
	function getCusInfo(cusNo){ 
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&operable="+operable+"&busEntrance="+busEntrance+"&fincId="+fincId;
	}

	//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等
	function getInfoForView(typeThis,id){
		top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+typeThis+'&cusNo='+id,'客户信息',function(){});
	}
	 
		
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
					window.top.alert(data.msg,0);
				}
				flag = data.flag;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
		return flag;
	}
	
	//查看产品信息
	function getKindInfo(){
		top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',function(){});
	}
//查看产品信息
function getKindInfoNew(appId){
    top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',function(){});
}
	//查看编辑费用详情
	function getFeeById(obj,url){
		top.window.openBigForm(url,"费用标准",function(){});	
	}
	//法律诉讼:新建
	function lawsuit(){
		top.openBigForm(webPath+"/mfLawsuit/input?pactNo="+pactId, "新增案件",function(){});
	};
	//查看已发起的法律诉讼
	function getLawsuitList(){
		top.openBigForm(webPath+"/mfLawsuit/getByPact?pactNo="+pactId, "案件详情",function(){});
	};
	//批量进行五级分类
	function batchFiveclass(){
		window.location.href=webPath+"/mfPactFiveclass/batchFiveclass";
	};
	//查看五级分类信息
	function getFiveclass(){
		top.openBigForm(webPath+"/mfPactFiveclass/getFiveclass?fiveclassId="+fiveclassId, "五级分类详情",function(){});
	};

	// 查看五级分类信息
	function fiveclassView() {
		top.openBigForm(webPath + "/mfPactFiveclass/fiveclassView?fincId=" + fincId, "五级分类详情", function () { });
	};

	//客户经理调整五级分类
	function fiveclassUpdate(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/getById?fiveclassId='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//公司认定五级分类
	function fiveclassFinal(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/getFiveclassById?fiveclassId='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//查看审批历史
	function fiveclassApprovalHis(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/fiveclassApprovalHis?appNo='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//新增五级分类
	function fiveclassInsert(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/input?pactId='+pactId,"五级分类",getFiveclassSts);
	}
	//加载五级分类状态
	function getFiveclassSts(){
		$.ajax({
			url: webPath+"/mfPactFiveclass/getFiveclassStsAjax",
			type:"post",
			data:{"pactId":pactId},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				if(data.flag == "success"){
					var fiveclassSts = data.mfPactFiveclass.fiveclassSts
					fiveclassId = data.mfPactFiveclass.fiveclassId;
					if(fiveclassSts == 0 || fiveclassSts == 1 || fiveclassSts == 4 || fiveclassSts == 5){//0系统初分 1不需要审批 5公司已认定
						$("#fiveclass").addClass("btn-dodgerblue");
						$("#fiveclassUpdate").show();
						$("#fiveclassInsert").hide();
						var fiveclass = data.mfPactFiveclass.fiveclass;
						if(fiveclass == 1){
							fiveclass = "正常";
						}else if(fiveclass == 2){
							fiveclass = "关注";
						}else if(fiveclass == 3){
							fiveclass = "次级";
						}else if(fiveclass == 4){
							fiveclass = "可疑";
						}else if(fiveclass == 5){
							fiveclass = "损失";
						}
						//$("#fiveclass").attr("onclick","getFiveclass()");
						$("#fiveclass").attr("onclick","fiveclassView()");
						$("#fiveclass").text(fiveclass);
					}else if(fiveclassSts == 2){
						$("#fiveclass").text("五级分类审批中");
						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未审批完毕，查看审批历史
						$("#fiveclass").removeClass("btn-dodgerblue");
						$("#fiveclassUpdate").attr("onclick","fiveclassApprovalHis()");
						$("#fiveclassInsert").attr("onclick","fiveclassApprovalHis()");
					}
					//暂不需要公司认定
//					else if(fiveclassSts == 4){
//						$("#fiveclass").text("五级分类公司认定中");
//						$("#fiveclassInsert").attr("onclick","fiveclassFinal()");//修改五级分类操作按钮的调用方法为公司认定
//						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未认定完毕，查看审批历史
//						$("#fiveclass").removeClass("btn-dodgerblue");
//					}
				}else{
					//$("#fiveclass").attr("onclick","");
				}
			}
		});
	}
	
	//多业务大于3条时，弹层列表页面
	function getMultiBusList(flag){
		if('apply'==flag){
			top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中业务",function(){});
		}else if('pact'==flag){
			top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在保项目",function(){});
		}else if('finc'==flag){
			top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行借据",function(){});
		}else if('assure'==flag){
			top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"担保项目",function(){});
        } else if ('fincFinish' == flag) {
            top.openBigForm(webPath + "/mfBusFincApp/getMultiBusFinishList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已完成", function () {
            });
        } else if ('repay' == flag) {
            top.openBigForm(webPath + "/mfRepayHistory/getMfRepayHistoryList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已还历史", function () {
            });
        } else if ('earlyWarning' == flag) {
            top.openBigForm(webPath + "/mfVouAfterTrack/getMultiBusList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "预警项目", function () {
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
	
	//单字段编辑的保存回调方法。
	function oneCallback(data) {
		var beginDate = $("input[name=beginDate]").val();
		var name = data[0].name;
		if(name=="beginDate"){
			var intTerm = parseInt(term);
			var str = "";
            var d;
			if(1==termType){ //融资期限类型为月 
				d = new Date(beginDate);
				d.setMonth(d.getMonth()+intTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			}else{ //融资期限类型为日 
				d = new Date(beginDate);
			 	d.setDate(d.getDate()+intTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
			}
			$(".endDate").html(str);
		}
	}
	
//催收登记
function recallRegist(){
	top.flag = false;
	top.window.openBigForm(webPath+'/recallBase/input?pactId='+pactId+'&cusNo='+cusNo+'&fincId='+fincId,"催收登记",function(){
		if(top.flag){
			if(top.formType=="assign"){
				$("#recallspan").text("待催收");
				$("#recallbase").removeClass("btn-forestgreen");
				$("#recallbase").addClass("btn-danger");
				$("#recallbase").removeClass("hide");
			}else if(top.formType=="regist"){
				if($("#recallbase").hasClass("btn-danger")&&$("#recallbase").hasClass("hide")){
					$("#recallspan").text("催收完成");
					$("#recallbase").removeClass("btn-danger");
					$("#recallbase").addClass("btn-forestgreen");
					$("#recallbase").removeClass("hide");
				}
			}
		}
		
	});
};

//催收详情信息
function getRecallInfo(){
	top.window.openBigForm(webPath+'/recallBase/getRecallInfo?pactId='+pactId+'&cusNo='+cusNo+'&fincId='+fincId,"催收详情",function(){});
}

//文件归档
function fileArchive(){
	var archivePactStatus = "01";
	if(pactSts=="7"){
		archivePactStatus="02";
	}
	var dataParam = '?optType=01&archivePactStatus='+archivePactStatus+'&cusNo='+cusNo+'&appId='+appId+'&pactId='+pactId+'&pleId='+pleId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件归档",function(){});
}
//文件封档
function fileSeal(){
	var dataParam = '?optType=02&cusNo='+cusNo+'&appId='+appId+'&pactId='+pactId+'&pleId='+pleId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件封档",function(){});
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
	top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo, '认证报告',function(){},"75","90");
}
function loanEsignHistory(){
    top.window.openBigForm(webPath+"/mfEsignHistory/getListPage?appId=" + appId +"&cusNo="+cusNo,'电签记录查询', myclose);
}
//GPS详情信息
function  getGpsDetailInfo(divId,htmlStr){
	var html = '<form method="post" theme="simple" id="gpsDetailForm" name="operform" action="'+webPath+'/mfBusGpsReg/updateAjaxByOne">'+htmlStr+'</form>';
	$("#"+divId).html(html);
};

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


//处理拒绝放款和终止业务流程中按钮操作问题
function operationButton() {
	var fincId='';
	if(top.fincSts !=undefined){
		 fincId=top.name;
	}
	$.ajax({
		type: "post",
		url:webPath+"/mfBusPact/operationSts?pactId="+pactId+"&fincId="+fincId,
		success:function (data) {
			var pactSts=data.pactSts;
			var fincSts=data.fincSts
			if (pactSts == '6' || pactSts == '5'|| pactSts == '2') {
				$("#MfBusPact_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
				$("#MfBusPact_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
			}else  {
                $("#MfBusPact_DynaDetail_disagree_button").attr("disabled", false);// 终止业务
                $("#MfBusPact_DynaDetail_disagree_button").attr("class", "btn btn-opt");// 终止业务
            }

			if(fincSts=="0" || fincSts=="1"||fincSts=="4"){
				$("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", false);
				$("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt");
			}
			if(fincSts !=""){
                putoutHisList.init();
			}
		}
	})
}

function changeCollectBankId(bankAcc){
    $("input[name='collectAccName']").val(bankAcc.accountName);
    $("input[name='collectBank']").val(bankAcc.bank);
    $("input[name='collectAccount']").val(bankAcc.accountNo);
    $("input[name='collectNumbei']").val(bankAcc.bankNumbei);
    $("input[name='collectAccId']").val(bankAcc.id);
    $("input[name='collectBankArea']").val(bankAcc.bankArea);
    $("span[field-name='collectBank']").find(".fieldShow").html(bankAcc.bank);
    $("span[field-name='collectAccName']").find(".fieldShow").html(bankAcc.accountName);
}
function selectCollectBankAccDialog(){
    selectBankAccDialog(changeCollectBankId,cusNo,"选择收款账号");
}
//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data) {
    if (key == "collectAccount") {
        data["collectBank"] = $("input[name=collectBank]").val();
        data["collectAccName"] = $("input[name=collectAccName]").val();
        data["collectNumbei"] = $("input[name=collectNumbei]").val();
        data["collectAccId"] = $("input[name=collectAccId]").val();
        data["collectBankArea"] = $("input[name=collectBankArea]").val();
    }
}