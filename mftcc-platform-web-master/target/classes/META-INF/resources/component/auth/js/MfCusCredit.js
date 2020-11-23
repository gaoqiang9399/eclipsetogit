;
var MfCusCredit = function(window,$){
	var _init = function(){
		_showProjectCreditButton();
	};
	//发起授信/发起立项
	/**
	 * creditModel 1客户授信 2项目授信
	 */
	var _getAppAuth = function(creditModel){
		//规则验证
		var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': cusNo, 'cusNo': cusNo};
		if(!rulesCall.validateRules(ruleParmsData,cusNo)){
			return false;
		}
		$.ajax({
			url : webPath+"/mfCusCreditApply/checkWkfEndSts",
			type : 'post',
			dataType : 'json',
			data : {
				wkfAppId:wkfAppId,
				cusNo : cusNo,
				creditModel :creditModel
			},
			success : function(data) {
				if(data.status == "0") {  //流程未结束
					top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请历史信息', function() {});
				}else{  //流程结束
					$.ajax({
						url : webPath+"/mfCusCustomer/checkCusBus",
						data : {
							cusNo : cusNo,
							cusType : cusType
						},
						type : "post",
						dataType : "json",
						success : function(data) {
							data.fullFlag = "1";
							if (data.fullFlag == "0") {//
								alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
							} else {
								// 新增
								top.creditFlag=false;
								top.creditType="1";
								top.creditAppId="";
								top.creditModel=creditModel;
								var url = webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo+ "&creditModel=" + creditModel;
								var title = "授信申请";
								if(creditModel=="2"){//立项授信
									title = "立项申请";
								}
								top.openBigForm(url, title, _wkfCallBack);
							}
						},
						error : function(data) {
							alert(data.msg, 0);
						}
					});
				}
			},
			error : function(data) {
				alert(data.msg, 0);
			}
		});
	};
    //发起授信意向/发起临时额度
    /**
     *
     */
    var _getIntentionAppAuth = function(creditModel){
        //规则验证
        var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': cusNo, 'cusNo': cusNo};
        if(!rulesCall.validateRules(ruleParmsData,cusNo)){
            return false;
        }
        $.ajax({
            url : webPath+"/mfCusCreditApply/checkWkfEndSts",
            type : 'post',
            dataType : 'json',
            data : {
                wkfAppId:wkfAppId,
                cusNo : cusNo,
                creditModel :creditModel
            },
            success : function(data) {
                if(data.status == "0") {  //流程未结束
                    top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请历史信息', function() {});
                }else{  //流程结束
                    $.ajax({
                        url : webPath+"/mfCusCustomer/checkCusBus",
                        data : {
                            cusNo : cusNo,
                            cusType : cusType
                        },
                        type : "post",
                        dataType : "json",
                        success : function(data) {
                            data.fullFlag = "1";
                            if (data.fullFlag == "0") {//
                                alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
                            } else {
                                // 新增
                                top.creditFlag=false;
                                top.creditType="1";
                                top.creditAppId="";
                                top.creditModel=creditModel;
                                var url = webPath+"/mfCreditIntentionApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo+ "&creditModel=" + creditModel;
                                var title = "意向申请";
                                if(creditModel=="2"){//立项授信
                                    title = "立项申请";
                                }
                                top.openBigForm(url, title, _wkfCallBack);
                            }
                        },
                        error : function(data) {
                            alert(data.msg, 0);
                        }
                    });
                }
            },
            error : function(data) {
                alert(data.msg, 0);
            }
        });
    };
	//发起授信/发起立项（经销商版）
	var _getDealerAppAuth = function(creditModel){
		top.LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfCusCreditApply/checkWkfEndSts",
			type : 'post',
			dataType : 'json',
			data : {
				wkfAppId:wkfAppId,
				cusNo : cusNo,
				creditModel :creditModel
			},
			success : function(data) {
				top.LoadingAnimate.stop();
				if(data.status == "0") {  //流程未结束
					top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请历史信息', function() {});
				}else{  //流程结束
					top.LoadingAnimate.start();
					$.ajax({
						url : webPath+"/mfCusCustomer/checkCusBus",
						data : {
							cusNo : cusNo,
							cusType : cusType
						},
						type : "post",
						dataType : "json",
						success : function(data) {
							top.LoadingAnimate.stop();
							data.fullFlag = "1";
							if (data.fullFlag == "0") {//
								alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
							} else {
								// 新增
								top.creditFlag=false;
								top.creditType="1";
								top.creditAppId="";
								top.creditModel=creditModel;
								var url = webPath+"/mfCusCreditApply/dealerInput?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo+ "&creditModel=" + creditModel;
								top.openBigForm(url, "授信申请", _wkfCallBack);
							}
						},
						error : function(data) {
							top.LoadingAnimate.stop();
							alert(data.msg, 0);
						}
					});
				}
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		});
	};
	//获得下个节点提示信息
	var _getNextBusPoint = function(wkfAppId,creditModel){
		$.ajax({
			url : webPath+"/mfCusCreditApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var wkfFlag = data.wkfFlag;
				//业务结束或业务否决，不展示业务流程图和流程指引
				if (wkfFlag == '0') {
                    $("#showCusNext").removeClass("show");
                    $("#showCusNext").addClass("hide");
                    $("#showWkf").removeClass("show");
                    $("#showWkf").addClass("hide");
                    $(".bg-danger").removeClass("show");
                    $(".bg-danger").addClass("hide");
				} else if (wkfFlag == '1') {
					var url = data.url;
					var title = data.title;
					var nodeName = data.nodeName;
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
					$(".block-next").empty();
					$(".next-div").unbind();
                    var info = "";
                    //当前处于发回补充资料岗位,展示补充资料
                    if (data.approveNodeNo!=""&&data.approveNodeNo!=null
						&&data.approveNodeNo.indexOf("credit_supplement")!=-1){
                        info = "补充资料";
                    }
					if ((data.creditSts == "2") && (nodeName == "credit_approval"||nodeName == "credit_pact_approval" || nodeName == "credit_primary_approval" || nodeName == "primary_credit_pact_approval")) { // 审批环节
                        if(isCreditFlag==1){
                            $(".block-next").append("<span>当前授信已发起业务，请到签约视角下操作</span>");
                        }else{
                            if (info == ""){
                                info = "审批";
                            }
                            var approveInfo="业务在"+title+"阶段，";
                            var approveNodeName=data.approveNodeName;
                            var approvePartName=data.approvePartName;
                            if(approvePartName!=""&&approvePartName!=null){
                                approveInfo=approveInfo+"正在"+approveNodeName+"岗位的"+approvePartName+info;
                            }else{
                                approveInfo=approveInfo+"正在"+approveNodeName+"岗位"+info;
                            }
                            var busPointInfo="业务在"+ title + "阶段";
                            if(approveInfo.length>40){
                                //提示内容长度大于40时，截取展示并添加鼠标title提示。
                                var approveTitleInfo=approveInfo;
                                approveInfo=approveInfo.substring(0, 40)+"...";
                                busPointInfo = "<span title="+approveTitleInfo+">"+approveInfo+"</span>";
                            }else{
                                busPointInfo = "<span>"+approveInfo+"</span>";
                            }
                            $(".block-next").append(busPointInfo);
                        }

					}else if(data.creditSts == "8"){
                        $(".block-next").append( "<span id='point'>申请已被终止，业务结束</span>");
					}else if(data.creditSts == "4"){
                        $(".block-next").append( "<span id='point'>申请已被否决，业务结束</span>");
                    }else {
                        if(checkPmsBizFlag){
                        	//如果授信已发起首笔，请到业务详情下操作
							if(isCreditFlag==1){
                                $(".block-next").append("<span>当前授信已发起业务，请到签约视角下操作</span>");
							}else{
								if(data.ONLY_MNG_EDIT_CREDIT==1){
                                    $(".block-next").append("<span>非当前授信客户经理，无法操作</span>");
								}else{
                                    $(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
                                    $(".next-div").click(function() {
                                        _toNextBusPoint(url, title, nodeName,wkfAppId,creditModel);
                                    });
								}

							}

                        }else{

                        }
                    }
                    var dataShowWay = data.dataShowWay;
                    if(dataShowWay != undefined && dataShowWay == "0"){

                    }else{
                        $("#showCusNext").removeClass("hide");
                        $("#showCusNext").addClass("show");
                        $("#showWkf").removeClass("hide");
                        $("#showWkf").addClass("show");
                    }
				}
			}
		});
	}
	//跳转节点内容
	var _toNextBusPoint = function(url, title, nodeName,wkfAppId,creditModel){
		var tmpUrl = url.split("&")[0];
		var popFlag = tmpUrl.split("?")[1].split("=")[1];
		if (popFlag == "0") {// popFlag=1打开新窗口 popFlag=0反之
			if (nodeName == "credit_approval") {
				alert(top.getMessage("CONFIRM_OPERATION",title), 2, function() {
					LoadingAnimate.start();
					$.ajax({
						url : webPath+tmpUrl,
						type : 'post',
						dataType : 'json',
						data : {
							cusNo : cusNo,
							wkfAppId : wkfAppId
						},
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								if (data.node == "processaudit") {
									window.top.alert(data.msg, 3);
									//实时更新授信状态
									$.ajax({
										url : webPath+"/mfCusCreditApply/getCreditStsInfo",
										data : {
											wkfAppId : wkfAppId
										},
										type : 'post',
										dataType : 'json',
										success : function(data) {
											var status = data.status;
											var creditSum = data.creditSum;
											var applySum = data.applySum;
											//_getCreditSts(status,creditSum,applySum);
										},
										error : function() {
											alert(data.msg, 0);
										}
									});
									_getNextBusPoint(wkfAppId,creditModel);
									if(creditModel=="1"){//客户授信
										$("#wj-modeler1").empty();
										showWkfFlow($("#wj-modeler1"),wkfAppId);
									}else if(creditModel=="2"){//项目授信
										$("#prolect-wj-modeler").empty();
										showWkfFlow($("#prolect-wj-modeler"),wkfAppId);
									}
									
								} else {
									_getNextBusPoint(wkfAppId,creditModel);
									if(creditModel=="1"){//客户授信
										$("#wj-modeler1").empty();
										showWkfFlow($("#wj-modeler1"),wkfAppId);
									}else if(creditModel=="2"){//项目授信
										$("#prolect-wj-modeler").empty();
										showWkfFlow($("#prolect-wj-modeler"),wkfAppId);
									}
								}
							} else {
								alert(data.msg);
							}
						}
					});
				});
			}
		} else {
			top.creditFlag=false;
			if (nodeName == "collateral") { // 担保信息
				top.addCreditCollaFlag=false;
				top.creditAppId="";
				if(creditType=="2"){//调整登记担保信息，跳转到详情去新增
					// 先判断之前是否登记过担保信息
					$.ajax({
						url: webPath+"/mfBusCollateralRel/getCollateralInfoListAjax",
						type:"post",
						dataType:"json",
						data:{
							appId:adjustAppId,
							creditType:creditType
						},
						error:function(){
							alert('获取反担保信息失败', 0);
						},
						success:function(data){
							if(data.flag == "success"){
								if(data.skip == "1"){//已有担保信息
									//直接提交下一步流程
									$.ajax({
										url: webPath+"/mfCusCreditApply/doCommitWkf",
										type:"post",
										dataType:"json",
										data:{
											wkfAppId:wkfAppId,
											commitType:"collateral",
										},
										error:function(){
											alert('提交到下一个节点时发生异常', 0);
										},
										success:function(data){
											if(data.flag == "success"){
                                                if(data.hasOwnProperty("adjustAppId")){//是否含有授信调整申请编号
                                                    creditAppId=data.adjustAppId;
                                                }
                                                window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+creditAppId+"&creditAppId="+creditAppId+"&appId="+appId+"&entrance=credit&busEntrance=credit";

											}
										}
									});
								}else{//没有担保信息，弹层添加担保信息
									if(url.substr(0,1)=="/"){
										url =webPath + url; 
									}else{
										url =webPath + "/" + url;
									}
									top.window.openBigForm(url, title, _toCollateralDetail);
								}
							}else{
								alert('提交到下一个节点时发生异常', 0);
							}
						}
					});
					
				}else if(creditType=="1" || creditType=="9"){
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.window.openBigForm(url, title, _toCollateralDetail);
				}
			}else{
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                if (nodeName == "report"||nodeName == "credit_batch_printing"||nodeName == "credit_letter_intent"||nodeName == "first_apply") { // 尽职报告
                    //var url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=1";

					$.ajax({
						url: webPath+"/mfBusApply/checkIfEval",
						type:"post",
						dataType:"json",
						data:{
							relNo:creditAppId,
							type:"1",
						},
						success:function(data){
							if(data.flag == "success"){
								top.window.openBigForm(url, title, _wkfCallBackRefresh);
							}else{
								alert(top.getMessage("FIRST_OPERATION","详审评级"),3);
							}
						},error:function(){
							alert(top.getMessage("ERROR_SERVER"),0);
						}
					});

                }else {

                    top.window.openBigForm(url, title, _wkfCallBack);
				}


			}
		}
	};
	//节点保存回调
	var _wkfCallBack = function(){
		if(top.creditFlag){
			if (top.wkfAppId != undefined && top.wkfAppId != "") {
				wkfAppId = top.wkfAppId;
			}
			if (top.creditType != undefined && top.creditType != "") {
				creditType = top.creditType;
			}
			if (top.creditAppId != undefined && top.creditAppId != "") {
				creditAppId = top.creditAppId;
				//更新客户 授信终止操作按钮权限
					$.ajax({
						url: webPath + "/mfCusCreditApply/getByIdAjax",
						data: {
                            creditAppId: creditAppId
						},
						type: 'post',
						dataType: 'json',
						success: function (data) {
							if(data.flag=="success"){
								if(data.creditApply.creditSts=="2"){//申请中
                                    $("#cusCreditStop").removeClass("btn-opt");
                                    $("#cusCreditStop").addClass("btn-opt-dont");
                                    $("#cusCreditStop").attr("disabled",'false');
								}else{
                                    $("#cusCreditStop").removeClass("btn-opt-dont");// 去掉灰色样式
                                    $("#cusCreditStop").addClass("btn-opt");// 添加蓝色
                                    $("#cusCreditStop").removeAttr("disabled");// 添加蓝色
								}
							}
						}
					});
			}
			if(typeof (baseType) !="undefined" && baseType == "3"){//客户授信
				//实时更新客户授信总额
				$.ajax({
					url : webPath+"/mfCusCoreCompany/getDetailInfoAjax",
					data : {
						cusNo : cusNo,
						cusType:cusType
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if(data.flag == "success"){
							$("#MfCusCoreCompanyActionAjax_updateByOne_action").html(data.htmlStr);
							dblclickflag();
						}
					},
					error : function() {
						alert(data.msg, 0);
					}
				});
			}

			_showProjectCreditButton();
			// 申请信息回显
			_creditApplyFormHtml(creditAppId,'cusCreditApplyDetail');
            _wkfCallBackRefresh();
		}
        initRiskLevel();
	};
	//节点保存回调
	var _wkfCallBackRefresh = function(){
        // 回调函数
       //window.location.href = webPath + "/mfCusCreditApply/getCusCreditView?&cusNo=" + top.cusNo + "&creditAppId=" + top.creditAppId + "&busEntrance=credit" + "&entrance=credit";
        window.location.reload();
	};

	// 申请信息回显
	var _creditApplyFormHtml = function(showCreditAppId,formId){
		$.ajax({
			url : webPath+"/mfCusCreditApply/getCusCreditApplyDetailAjax",
			type : 'post',
			dataType : 'json',
			data : {
				creditAppId : showCreditAppId,
				formId :formId
			},
			success : function(data) {
				$("#cusCreditApplyDetailInfo").html(data.cusCreditApplyDetail);
			},
			error : function(data) {
				alert(data.msg, 0);
			}
		});	
	}
	
	//更新头部授信信息按钮样式
	var _getCreditSts = function(creditModel,status,creditSum,applySum){
        $(".creditBus").parent().removeClass("btn-lightgray");
        $(".creditBus").parent().removeClass("btn-dodgerblue");
        $(".creditBus").parent().addClass("cus-middle");
        if(status == "0" || status == "1"||status == "2"||status == "3"){//申请、审批中、审批完成
            $(".creditBus").html("授信中");
        }
        if(status == "5"){ //已签约
            $(".creditBus").parent().removeClass("cus-middle");
            $(".creditBus").parent().addClass("btn-dodgerblue");
            $(".creditBus").html(applySum+" 万");
        }
        if(status == "6"){ //已冻结
            $(".creditBus").parent().removeClass("cus-middle");
            $(".creditBus").parent().addClass("btn-danger");
            $(".creditBus").html(applySum+" 万");
        }
        if(status == "7"){ //已失效
            $(".creditBus").parent().removeClass("cus-middle");
            $(".creditBus").parent().addClass("btn-lightgray");
            $(".creditBus").html(applySum+" 万");
        }
        if(status == "8"){ //已终止
            $(".creditBus").parent().removeClass("cus-middle");
            $(".creditBus").parent().addClass("btn-danger");
            $(".creditBus").html("已终止");
        }
	};
	//跳转到押品详情
	var _toCollateralDetail = function(){
		if(top.addCreditCollaFlag){
			window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&creditAppId="+top.creditAppId+"&appId="+top.appId+"&entrance=credit&busEntrance=credit";
		}
		if(typeof(top.skipFlag) != "undefined" && top.skipFlag == "1"){//跳过担保节点
			_showProjectCreditButton();
			MfBusCollateralRel_AbstractInfo.init();
		}
	};
	//获得授信详情
	var _getCreditHisDataInfo = function(creditModel,wkfAppId){
		if(wkfAppId == null || wkfAppId == ""){
			return;
		}
		$.ajax({
			url : webPath+"/mfCusCreditApply/checkWkfEndSts",
			type : 'post',
			dataType : 'json',
			data : {
				wkfAppId:wkfAppId,
				cusNo : cusNo
			},
			success : function(data) {
				top.LoadingAnimate.stop();
				top.appId = "";
				top.creditAppId = "";
				top.toCollateralDetail = false;
				top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo+"&creditModel="+creditModel, '授信详情信息', function() {
					if(top.toCollateralDetail){
						window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&appId="+top.appId+"&entrance=credit";
					}
				});
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		});	
	};
    //获得授信申请详情
    var _getCreditIntentionHisDataInfo = function(){
        top.openBigForm(webPath+'/mfCreditIntentionApply/openHisData?cusNo='+cusNo, '授信详情信息', function() {

        });
	}
	//单独提交业务流程
	var _doCommitProcess = function(){
		$.ajax({
			url:webPath+"/mfCusCreditApply/commitBusProcessAjax?wkfAppId="+wkfAppId,
			success:function(data){
				if(data.flag=="success"){
					alert(data.msg,1);
					_getNextBusPoint();
					$("#wj-modeler1").empty();
					showWkfFlow($("#wj-modeler1"),wkfAppId);
				}else{								    
					alert(data.msg,0);
				}
			}
		});
	};
	
	var _showProjectCreditButton = function(){
		$.ajax({
			url : webPath+"/mfCusCreditApply/getCreditDataAjax",
			type : 'post',
			dataType : 'json',
			data : {
				cusNo : cusNo,
				creditType : "0",
				creditModel : creditModel,
				creditAppId:creditAppId
			},
			success : function(data) {
				//客户授信
                var cusCreditData = data.cusCreditData;
                if(typeof (cusCreditData) != "undefined"){
                    if(cusCreditData.flag == "success"){
                        var taskFlag = cusCreditData.taskFlag;
                        var creditApproveId = cusCreditData.creditApproveId;
                        var primaryAppId = cusCreditData.primaryAppId;
                        var primaryPactId = cusCreditData.primaryPactId;
                        var pactId = cusCreditData.pactId;

                        var status,creditModel;
                        if(taskFlag =="applying"){//业务处理中
                            status = cusCreditData.creditSts;
                            if(creditType == null || creditType == ""){
                                creditType = cusCreditData.creditType;
                            }
                            creditModel = cusCreditData.creditModel;//授信业务模式1客户授信2项目授信
                            if(creditAppId == null || creditAppId == ""){
                                creditAppId = cusCreditData.creditAppId;
                            }
                            var projectCreditUseHis = cusCreditData.cusCreditUseHis;
                            var wkfProjectAppId = cusCreditData.wkfAppId;
                            wkfAppId = wkfProjectAppId;
                            _getCreditSts(creditModel,status,null,projectCreditUseHis.applySum);
                            $("#cusCredit-button").attr("onclick","MfCusCredit.getCreditHisDataInfo('1','"+wkfProjectAppId+"')");
                            // 展示授信业务流程--获取下一业务节点
                            if(wkfProjectAppId != null && wkfProjectAppId != ""){
                                _getNextBusPoint(wkfProjectAppId,creditModel);
                                $("#wj-modeler1").empty();
                                showWkfFlow($("#wj-modeler1"),wkfProjectAppId);
                            };
                        }else if(taskFlag =="finish"){//业务流程完成
                            wkfProjectAppId = cusCreditData.wkfAppId;
                            wkfAppId = wkfProjectAppId;
                            status = cusCreditData.creditSts;
                            creditModel = cusCreditData.creditModel;
                            var cusCreditUseHis = cusCreditData.cusCreditUseHis;
                            $("#wj-modeler1").empty();
                            $("#showCusNext").removeClass("show");
                            $("#showCusNext").addClass("hide");
                            $("#showWkf").removeClass("show");
                            $("#showWkf").addClass("hide");
                            _getCreditSts(creditModel,status,null,cusCreditUseHis.applySum);
                            $("#cusCredit-button").attr("onclick","MfCusCredit.getCreditHisDataInfo('1','"+wkfProjectAppId+"')");
                            _setCreditApplyButton("1");
                        }
                        // 展示授信预审批历史
                        if(primaryAppId != null && primaryAppId != ""){
                            $("#wj-modeler2").empty();
                            showWkfFlowVertical($("#wj-modeler2"),primaryAppId,"1","credit_primary_approval");
                            $("#creditPrimaryApproval").show();
                        }
                        // 展示授信审批历史
                        if(creditApproveId != null && creditApproveId != ""){
                            $("#wj-modeler3").empty();
                            showWkfFlowVertical($("#wj-modeler3"),creditApproveId,"1","credit_approval");
                            $("#creditApproval").show();
                        }
                        // 展示授信清稿审批历史
                        if(primaryPactId != null && primaryPactId != ""){
                            $("#wj-modeler4").empty();
                            showWkfFlowVertical($("#wj-modeler4"),primaryPactId,"1","primary_credit_pact_approval");
                            $("#primaryCreditPactApproval").show();
                        }
                        // 展示授信合同审批历史
                        if(pactId != null && pactId != ""){
                            $("#wj-modeler5").empty();
                            showWkfFlowVertical($("#wj-modeler5"),pactId,"1","credit_pact_approval");
                            $("#creditPactApproval").show();
                        }
                	}
				}
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		});	
	};
	//设置发起授信调整、发起临额调整操作按钮是否可用
	var _setCreditApplyButton = function(type){
	    if(typeof (busEntrance) != "undefined" && busEntrance == "credit"){
            // 初始化授信调整，临额调整按钮
            initAdjustTemporaryBtn();
        }

	};
    //尽调调查历史
    var _investHistoryInit = function (){
        $.ajax({
            url:webPath+"/mfCusCreditApply/getInvestHistoryInit?creditAppId="+creditAppId,
            type:'post',
            dataType:'json',
            success:function(data){
                var html = data.htmlStr;
                if(html==''){
                    $('#investHistory-block').addClass('hidden');
                }else{
                    $('#investHistory-block').removeClass('hidden');
                    $("#mfInvestHistoryList").empty().html(html);
                }
            }
        });
    };
    //尽调调查历史
    var _investHistoryOpen = function (url){
        top.window.openBigForm(encodeURI(url),'尽调调查历史',function () {
            MfCusCredit.intentionInit();
        });
    };
    //授信意向初始化头部显示
    var _intentionInit = function (){
        $.ajax({
            url:webPath+"/mfCreditIntentionApply/getIntentionStatus?cusNo="+cusNo,
            type:'post',
            dataType:'json',
            success:function(data){
                var flag = data.flag;
                if(flag=='success'){
                	var intentionStatus = data.intentionStatus;
                	var message = data.message;
                    if (intentionStatus == '3'||intentionStatus == '6') {
                        $(".creditIntention").text(message);
                        $("#credit-intention-button").addClass("btn-danger");
                        $("#credit-intention-button").removeClass("btn-forestgreen");
                        $("#credit-intention-button").removeClass("btn-lightgray");
                        $("#credit-intention-button").removeClass("btn-dodgerblue");
                    } else if (intentionStatus == '2'||intentionStatus == '5') {
                        $(".creditIntention").text(message);
                        $("#credit-intention-button").addClass("btn-forestgreen");
                        $("#credit-intention-button").removeClass("btn-danger");
                        $("#credit-intention-button").removeClass("btn-lightgray");
                        $("#credit-intention-button").removeClass("btn-dodgerblue");
                    }else if (intentionStatus == '1'||intentionStatus == '4') {
                        $(".creditIntention").text(message);
                        $("#credit-intention-button").addClass("btn-dodgerblue");
                        $("#credit-intention-button").removeClass("btn-danger");
                        $("#credit-intention-button").removeClass("btn-lightgray");
                        $("#credit-intention-button").removeClass("btn-forestgreen");
                    } else {
                        $(".creditIntention").text(message);
                        $("#credit-intention-button").addClass("btn-lightgray");
                        $("#credit-intention-button").removeClass("btn-danger");
                        $("#credit-intention-button").removeClass("btn-dodgerblue");
                        $("#credit-intention-button").removeClass("btn-forestgreen");
                    }
                }else{

                }
            }
        });
    };
	return{
		init:_init,
		getAppAuth:_getAppAuth,
		getDealerAppAuth:_getDealerAppAuth,
		getCreditHisDataInfo:_getCreditHisDataInfo,
		creditApplyFormHtml:_creditApplyFormHtml,
        investHistoryInit:_investHistoryInit,
        investHistoryOpen:_investHistoryOpen,
        getIntentionAppAuth:_getIntentionAppAuth,
        getCreditIntentionHisDataInfo:_getCreditIntentionHisDataInfo,
        intentionInit:_intentionInit,
        getCreditSts:_getCreditSts
	};
}(window,jQuery);