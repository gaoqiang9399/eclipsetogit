$(function() {
	$("body").mCustomScrollbar({
		advanced:{
			//滚动条根据内容实时变化
			updateOnContentResize:true
		},
		callbacks: {
			//正在滚动的时候执行回调函数
			whileScrolling: function(){
				if ($(".changeval").length>0) {
					$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
				}
			}
		}
	});
	/**授信业务流程--获取下一业务节点**/
	if(wkfAppId != null && wkfAppId != ""){
		getNextBusPoint();
		/**显示流程**/
		showWkfFlow(wkfAppId);
	}
	// 客户分类展示处理
	$("#cusNameRate-span").text(rankTypeName);
	if (cusClassify == '1') {
		$(".cus-tag").addClass("btn-danger");
		$(".cus-tag").removeClass("btn-forestgreen");
		$(".cus-tag").removeClass("btn-lightgray");
		$(".cus-tag").removeClass("btn-dodgerblue");
	} else if (cusClassify == '2') {
		$(".cus-tag").addClass("btn-forestgreen");
		$(".cus-tag").removeClass("btn-danger");
		$(".cus-tag").removeClass("btn-lightgray");
		$(".cus-tag").removeClass("btn-dodgerblue");
	}else if (cusClassify == '4') {
		$(".cus-tag").addClass("btn-lightgray");
		$(".cus-tag").removeClass("btn-danger");
		$(".cus-tag").removeClass("btn-forestgreen");
		$(".cus-tag").removeClass("btn-dodgerblue");
	}else if (cusClassify == '5') {
		$(".cus-tag").addClass("btn-forestgreen");
		$(".cus-tag").removeClass("btn-danger");
		$(".cus-tag").removeClass("btn-lightgray");
		$(".cus-tag").removeClass("btn-dodgerblue");
	}else if (cusClassify == '3') {
        $(".cus-tag").addClass("btn-dodgerblue");
        $(".cus-tag").removeClass("btn-danger");
        $(".cus-tag").removeClass("btn-forestgreen");
        $(".cus-tag").removeClass("btn-lightgray");
    } else {
        $("#cusNameRate-span").text("潜在客户");
        $(".cus-tag").addClass("btn-lightgray");
        $(".cus-tag").removeClass("btn-danger");
        $(".cus-tag").removeClass("btn-forestgreen");
        $(".cus-tag").removeClass("btn-dodgerblue");
    }
	$(".block-tip").hide();
	$(".cus-add").show();
	// 展示客户模块
	MfCusDyForm.init(cusTableList);
	// 处理头像信息
	var data = headImg;
	if (ifUploadHead != "1") {
		data = "themes/factor/images/" + headImg;
	}
	data = encodeURIComponent(encodeURIComponent(data));
	document.getElementById('headImgShow').src = webPath+ "/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";

	// 多业务情况下
	if (moreApplyList) {
		$(".badge").text(moreApplyList.length);
		if (moreApplyList.length > 3) {
			$(".moreCnt").text(moreApplyList.length + 1);
		}
	}
	//处理银行卡号
	if ($("[name=MfCusBankAccManageAction]").length == "1") {
		dealBankNo();
	}
	LoadingAnimate.stop();
});

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

//重写dblUpdateVal，支持单字段编辑事同时更新相关字段
function dblUpdateVal(key,data){
	if(key=="careaCity"){
		data["careaProvice"] = $("input[name=careaProvice]").val();//存储最终的编号
	}
}

function addCusFormInfoCall() {
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
					$dynamicBlock.find(".content").html(top.htmlString);
				}
				if(top.action=="MfCusCorpBaseInfoAction"){
					refreshCustomerInfo();
				}
				if (top.action == "MfCusBankAccManageAction") {
					dealBankNo();
				}
			}
		} else {
			if (top.htmlStrFlag) {
				MfCusDyForm.setBlock(top.showType, top.title, top.action, top.htmlString,"1", top.isMust, null, null, null);
				dblclickflag();
				if(top.action=="MfCusCorpBaseInfoAction"){
					refreshCustomerInfo();
				}
			}
		}

	}
};


// 获取业务信息
function getBusInfo(appId) {
	LoadingAnimate.start();
	if(completeFlag == '0'){
		window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
	}else{
		window.location.href = webPath+"/mfBusApply/getSummary?appId=" + appId+"&busEntrance=1&operable="+operable;
	}
};
// 获取押品信息
function getPleInfo() {
	LoadingAnimate.start();
	window.location.href = webPath+"/mfBusPledge/getPledgeById?cusNo="
			+ cusNo + "&appId=" + appId;
};
// 获取合同信息
function getPactInfo() {
	LoadingAnimate.start();
	if(busEntrance=='3'||busEntrance=='6'){
		window.location.href=webPath+"/mfBusPact/getPactFincById?fincId="+fincId+"&appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}else{
		window.location.href = webPath+"/mfBusPact/getById?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}
//	window.location.href = webPath+"/mfBusPact/getById?appId=" + appId;
};
function getBusDetail(obj, urlThis) {
	var parm = urlThis.split("?")[1];
	var parmArray = parm.split("&");
	var appIdThis = parmArray[0].split("=")[1];
	var appStsThis = parmArray[1].split("=")[1];
	if (appStsThis == "4") {// 表示申请审批通过
		window.location.href = webPath+"/mfBusPact/getById?appId="
				+ appIdThis;
	} else {
		window.location.href = webPath+"/mfBusApply/getSummary?appId="
				+ appIdThis;
	}
};
function addBlockInfo() {
	// 如果客户基础信息不存在，则先录入基本信息
	/*
	 * if(cusBaseFlag == '0'){ addBaseInfo(); return false; }
	 */
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
		type : "post",
		dataType : "json",
		success : function(data) {

			if (data.fullFlag == '1') {// 全部都填写了
				alert("客户资料已经全部完善", 0);
			} else if (data.fullFlag == '0') {
				top.addFlag = false;
				top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
				top.htmlString = null;
				top.action = null;
				top.title = null;
				top.name = null;
				top.cusNo = cusNo;
				top.showType = null;
				// top.baseInfo="0";标识 该表单信息是否是客户的基本信息
				top.openBigForm(
						webPath+'/mfCusTable/getListPage?cusNo='
								+ cusNo + '&dataFullFlag=0', '选择表单',
						addCusFormInfoCall);
			}
		},
		error : function() {

		}
	});

};

function getCusInfoById(obj, getUrl) {// 根据列表超链接获得信息详情，支持编辑
	var $dynamicBlock = $(obj).parents(".dynamic-block");
	var title = $dynamicBlock.attr("title");
	var action = $dynamicBlock.attr("name");
	top.action = action;
	top.showType = "2";
	top.addFlag = false;
	top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.getTableUrl = action + "_getListPage.action?cusNo=" + cusNo;
	top.openBigForm(getUrl, title, addCusFormInfoCall);
};

// 业务办理
function applyInsert() {
	// 判断该客户是否完善了基本信息
	// LoadingAnimate.start();
	$.ajax({
			url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
			success : function(data) {
				if (data.fullFlag == '1') {// 全部都填写了
				// top.createShowDialog(webPath+"/mfBusApply/inputQuery?cusNo="+cusNo+"&kindNo="+firstKindNo,"业务申请");
					window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="
							+ cusNo
							+ "&kindNo="
							+ firstKindNo
							+ "&query=cusbody";
				} else if (data.fullFlag == '0') {
					alert(top.getMessage("FIRST_COMPLETE_INFORMAATION","客户信息"),0);
				}
			},error : function() {
			}
		});

};

function addBaseInfo() {// 增加客户的基本信息
	top.addFlag = false;
	top.action = "MfCusCorpBaseInfoAction";
	top.showType = '1';
	top.baseInfo = "1";
	top.name = "MfCusCorpBaseInfoAction";
	top.openBigForm(webPath+"/mfCusCorpBaseInfo/input?cusNo=" + cusNo,
			"基本信息", closeCallBack);
};
function checkCusInfoIsFull() {// 验证客户信息是否已经填写完整
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.fullFlag == '1') {// 全部都填写了

			} else if (data.fullFlag == '0') {

			}
		},
		error : function() {

		}
	});
};

// 客户要件信息
function cusDocInfo() {
	window.parent.openBigForm(webPath+'/docManage/pubUpload?relNo=' + cusNo
			+ '&cusNo=' + cusNo + '&scNo=' + scNo, '图文资料', function() {
	});
};
function cusTrack(type) {
	top.updateFlag = false;
	top.openBigForm(webPath+'/mfCusTrack/getListPage?cusNo=' + cusNo+ "&query=" + type,'客户跟进',function() {
		if (top.updateFlag) {
			$.ajax({
				url : webPath+"/mfCusTrack/getListTopAjax?cusNo="+ cusNo,
				success : function(data) {
					if (data.flag == "success") {
						$("#cusTrackBlock").empty();
						if (data.mfCusTrackList.length == 0) {
							$("#cusTrackBlock")
									.append('<div class="no-content"> 暂无数据</div>');
						} else {
							var cusTrackHtml = "";
							$.each(data.mfCusTrackList,function(i,cusTrack) {
								cusTrackHtml = cusTrackHtml
										+ '<div><p class="his-title">'
										+ '<span style="margin-left: 0px;" class="change-date">'
										+ cusTrack.regDate
										+ '</span>'
										+ '<span class="change-time">'
										+ cusTrack.regTime
										+ '</span>'
										+ '<span style="margin-left: 5px;">'
										+ cusTrack.opName
										+ '</span>'
										+ '</p>'
										+ '<p class="his-cont">'
										+ '<span>'
										+ cusTrack.trackContent
										+ '</span>'
										+ '</p></div>';

							});
							$("#cusTrackBlock").append(cusTrackHtml);
						}
					} else {

					}
				},error : function() {

				}
			});
		}
	});
};

// 客户分类
function cusTag() {
	top.updateFlag = false;
	top.classify = false;
	top.openBigForm(webPath+'/mfCusClassify/getByCusNo?cusNo=' + cusNo,
			'客户分类', function() {
				if (top.updateFlag) {
					if (top.cusClassify == '1') {
						$("#cusNameRate-span").text(top.cusTag);
						$(".cus-tag").addClass("btn-danger");
						$(".cus-tag").removeClass("btn-forestgreen");
						$(".cus-tag").removeClass("btn-lightgray");
						$(".cus-tag").removeClass("btn-dodgerblue");
					} else if (top.cusClassify == '2') {
						$("#cusNameRate-span").text(top.cusTag);
						$(".cus-tag").addClass("btn-forestgreen");
						$(".cus-tag").removeClass("btn-danger");
						$(".cus-tag").removeClass("btn-lightgray");
						$(".cus-tag").removeClass("btn-dodgerblue");
					} else if (top.cusClassify == '4') {
						$(".cus-tag").addClass("btn-lightgray");
						$(".cus-tag").removeClass("btn-danger");
						$(".cus-tag").removeClass("btn-forestgreen");
						$(".cus-tag").removeClass("btn-dodgerblue");
					}else if (top.cusClassify == '5') {
						$("#cusNameRate-span").text(top.cusTag);
						$(".cus-tag").addClass("btn-forestgreen");
						$(".cus-tag").removeClass("btn-danger");
						$(".cus-tag").removeClass("btn-lightgray");
						$(".cus-tag").removeClass("btn-dodgerblue");
					}else if (top.cusClassify == '3') {
                        $("#cusNameRate-span").text(top.cusTag);
                        $(".cus-tag").addClass("btn-dodgerblue");
                        $(".cus-tag").removeClass("btn-danger");
                        $(".cus-tag").removeClass("btn-forestgreen");
                        $(".cus-tag").removeClass("btn-lightgray");
                    } else {
                        $("#cusNameRate-span").text("潜在客户");
                        $(".cus-tag").addClass("btn-lightgray");
                        $(".cus-tag").removeClass("btn-danger");
                        $(".cus-tag").removeClass("btn-forestgreen");
                        $(".cus-tag").removeClass("btn-dodgerblue");
                    }
				}
			}, '80', '80');
};

function cusTagHis() {
	top.openBigForm(webPath+'/mfCusClassify/getListPage?cusNo=' + cusNo,
			'客户分类', function() {
		if (top.updateFlag) {
			if (top.cusClassify == '1') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			} else if (top.cusClassify == '2') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			}else if (top.cusClassify == '4') {
				$(".cus-tag").addClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-dodgerblue");
			}else if (top.cusClassify == '5') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			} else if (top.cusClassify == '3') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-dodgerblue");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
            } else {
                $("#cusNameRate-span").text("潜在客户");
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }
		}
			});
};

//查看本次授信申请信息
function getCreditHisDataInfo(){
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
			top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId, '授信申请历史信息', function() {});
		},
		error : function(data) {
			top.LoadingAnimate.stop();
			alert(data.msg, 0);
		}
	});	
}

// 发起授信 - 客户授信操作
function getAppAuth() {
	top.LoadingAnimate.start();
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
			if(data.status == "0") {  //流程未结束
				top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId, '授信申请历史信息', function() {});
			}else{  //流程结束
				top.LoadingAnimate.start();
				$.ajax({
					url : webPath+"/mfCusCustomer/checkCusBus",
					data : {
						cusNo : cusNo
					},
					type : "post",
					dataType : "json",
					success : function(data) {
						top.LoadingAnimate.stop();
						if (data.fullFlag == "0") {
							alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
						} else {
							// 检查客户企业是否授信过，是的话调整目前最新的授信
							$.ajax({
										url : webPath+"/mfCusCreditApply/checkIsCredit",
										type : "POST",
										dataType : "json",
										data : {
											cusNo : cusNo,
											cusType : cusType
										},
										success : function(data) {
											if (data.flag == '1') { // 已有授信
												alert("您当前已有授信，是否要调整授信?", 2,
														function() { // 调整 确定
															var url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=0";
															top.openBigForm(url, "调整授信申请", wkfCallBack, "80", "80");
														},
														function() { // 新增 取消
															var url = webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
															top.openBigForm(url, "新增授信", wkfCallBack, "80", "80");
														});
											} else {
												// 新增
												var url = webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
												top.openBigForm(url, "新增授信", wkfCallBack, "80", "80");
											}
										},
										error : function() {
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
			}
		},
		error : function(data) {
			top.LoadingAnimate.stop();
			alert(data.msg, 0);
		}
	});
}

// 获取下一个业务节点
function getNextBusPoint() {
	$.ajax({
		url : webPath+"/mfCusCreditApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			var wkfFlag = data.wkfFlag;
			if (wkfFlag == '0') {
				$(".bg-danger").removeClass("show");
				$(".bg-danger").addClass("hide");
			} else if (wkfFlag == '1') {
				var url = data.url;
				var title = data.title;
				var nodeName = data.nodeName;
				$(".block-next").empty();
				$(".next-div").unbind();
				if ((data.creditSts == "2" || data.creditSts == "3") && nodeName == "credit_approval") { // 审批环节
					$(".block-next").append("<span>业务在" + title + "阶段</span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
				} else {
					$(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
					$(".next-div").click(function() {
						toNextBusPoint(url, title, nodeName, wkfAppId);
					});
				}
			}
		}
	});
}

// 跳转至下一业务节点
function toNextBusPoint(urlArgs, title, nodeName) {
	var tmpUrl = urlArgs.split("&")[0];
	var popFlag = tmpUrl.split("?")[1].split("=")[1];
	if (popFlag == "0") {// popFlag=1打开新窗口 popFlag=0反之
		if (nodeName == "credit_approval") {
			alert(top.getMessage("CONFIRM_OPERATION",title), 2, function() {
//			alert("确定进行“" + title + "”操作?", 2, function() {
				LoadingAnimate.start();
				$.ajax({
					url : tmpUrl,
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
										getCreditSts(status,creditSum,applySum);
									},
									error : function() {
										alert(data.msg, 0);
									}
								});
								getNextBusPoint();
								showWkfFlow(wkfAppId);
							} else {
								getNextBusPoint();
								showWkfFlow(wkfAppId);
							}
						} else {
							alert(data.msg);
						}
					}
				});
			});
		}
	} else {
        var url;
		if (nodeName == "node2456438285") { // 担保信息
			top.window.openBigForm(urlArgs, title, wkfCallBackPledge, "80", "80");
		}
		if (nodeName == "node6951342387") {// 授信协议
			url = webPath+"/mfCusCreditApply/protocolPrint?wkfAppId=" + wkfAppId;
			top.window.openBigForm(url, title, wkfCallBack, "80", "80");
		}
		if (nodeName == "node7269622672") { // 尽职报告
			url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=1";
			top.openBigForm(url, "授信尽调报告", wkfCallBack);
		}
	}
}
// 回调函数
function wkfCallBack() {
	if (top.wkfAppId != undefined && top.wkfAppId != "") {
		wkfAppId = top.wkfAppId;
	}
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
			getCreditSts(status,creditSum,applySum);
		},
		error : function() {
			alert(data.msg, 0);
		}
	});
	getNextBusPoint();
	showWkfFlow(wkfAppId);
}

// 回调函数
function wkfCallBackPledge() {
	window.location.href = webPath+"/mfBusPledge/getPledgeById?cusNo=" + cusNo + "&opType=CREDIT_APPLY";
}

//获取授信状态及授信额度
function getCreditSts(status,creditSum,applySum){
	if(status == "1"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().addClass("btn-danger");
		$(".creditBus").html("申请中");
	}
	if(status == "2"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().addClass("btn-danger");
		$(".creditBus").html("审批中");			
	}
	if(status == "3"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().addClass("btn-danger");
		$(".creditBus").html("审批通过");
	}
	if(status == "5"){ //已签约
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().addClass("btn-dodgerblue");
		$(".creditBus").html(applySum);
	}
}

/*外部评级*/
function getManEvalApp() {
		LoadingAnimate.start();
		$.ajax({
			url : webPath+"/appEval/getUnfinishedAppEval",
			data : {
				cusNo : cusNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if (data.status == "0") {
					alert("请先添加财务报表！", 0);
					return;
				}
				if (data.flag == "1") {//未提交
					top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="
							+ data.evalAppNo, "评级申请", function() {
					});
				}else if(data.flag == "2"){//审批阶段查看详情
					top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo
							+ "&appSts=2&useType=1", "评级信息", function() {
					});
				}else{//新增或审批完成
					top.openBigForm(webPath+"/appEval/initManEavl?cusNo="
							+ cusNo, "评级申请", function() {
					});
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};

function getInitatEcalApp() {
	LoadingAnimate.start();
	$.ajax({
		url : webPath+"/appEval/getUnfinishedAppEval",
		data : {
			cusNo : cusNo
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop();
			if (data.status == "0") {
				alert("请先添加财务报表！", 0);
				return;
			}
			if (data.flag == "1") {//未提交
				top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="
						+ data.evalAppNo, "评级申请", function() {
				});
			}else if(data.flag == "2"){//审批阶段查看详情
				top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo
						+ "&appSts=2&useType=1", "评级信息", function() {
				});
			}else{//新增或审批完成
				top.openBigForm(webPath+"/appEval/initiateApp?cusNo="
						+ cusNo, "评级申请", function() {
				});
			}
		},
		error : function() {
			LoadingAnimate.stop();
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

// 获取评级信息
function getEvalDetailResult(parm) {
	if (parm == '1') {
		top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo
				+ "&appSts=4&useType=1", "评级信息", function() {
		});
	} else {
		return false;
	}
};

// 上传头像
function uploadHeadImg() {
	window.parent.openBigForm('MfCusCustomer_uploadHeadImg.action?relNo='
			+ cusNo + '&cusNo=' + cusNo, '客户头像', showNewHeadImg);
};
function showNewHeadImg() {
	var data;
	$
			.ajax({
				url : webPath+"/mfCusCustomer/getIfUploadHeadImg",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "1") {
						data = encodeURIComponent(encodeURIComponent(data.headImg));
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					} else {
						data = "themes/factor/images/" + data.headImg;
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
	delFile();
};
function getPfsDialog() {// 获得财务报表信息
	top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo=' + cusNo,'财务报表', function() {});
};

// 删除文件
function delFile() {
	var srcPath = "/tmp/";
	$.ajax({
		url : webPath+"/uploadFile/delFile",
		data : {
			srcPath : srcPath
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {

		},
		error : function() {
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

function cusRelation() {
	top.openBigForm(webPath+'/mfCusRelation/getListPage?cusNo=' + cusNo,'关联关系', function() {});
};
function cusRelationIn() {
	window.parent.openBigForm(webPath+'/mfCusRelationType/input?cusNo='+ cusNo, '新增关联关系', function() {});
};
// 打开修改客户基本信息页面
function updateCustomerInfo() {
	var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("cus-baseInfo-edit");
	if(checkPmsBizFlag){
		top.updateFlag = false;
		top.cusName = "";
		top.contactsName = "";
		top.contactsTel = "";
		top.idNum = "";
		top.commAddress = "";
		top.window.openBigForm(webPath+'/mfCusCustomer/toUpdate?cusNo=' + cusNo, '修改客户信息',refreshCustomerInfo);
	}
}
// 刷新客户登记信息以及基本信息
function refreshCustomerInfo() {
	if (top.updateFlag) {
		$("div[id=cusNameShow]").html(top.cusName!=null&&top.cusName!=''?top.cusName:"<span class='unregistered'>未登记</span>");
		$("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
		$("span[id=contactsTel]").html(top.contactsTel!=null&&top.contactsTel!=''?top.contactsTel:"<span class='unregistered'>未登记</span>");
		$("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
		$("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
		$("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
		// 刷新基本信息
		var BaseInfoFlag = $("[name=MfCusCorpBaseInfoAction]").length;
		if (BaseInfoFlag == "1") {
			$("#MfCusCorpBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
		}
	}
};

function selectAreaCallBack(areaInfo) {
	$("input[name=careaCity]").val(areaInfo.disName);
	$("input[name=careaProvice]").val(areaInfo.disNo);
};

// 填充申请信息
function setApplyInfo(data) {
	var mfBusApplyObj = data.mfBusApply;
	var mfBusApplyListTmp = data.mfBusApplyList;
	var appName = mfBusApplyObj.appName;
	if (appName.length > 8) {
		appName = appName.substring(0, 8) + "...";
	}
	var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_20">'
			+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''
			+ mfBusApplyObj.appId + '\');">'
			+ '<i class="i i-applyinfo font-icon"></i>'
			+ '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
	// 如果业务笔数大于3笔
	if (data.mfBusApplyList.length > 3) {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix" style="padding-top: 45px;">'
				+ '<span>客户共有 <div class="badge more-apply-count">'
				+ data.mfBusApplyList.length + 1
				+ '</div> 笔在履行业务<a>查看</a></span>' + '</div>' + '</div>';
	} else {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column">';

		// 如果业务笔数为1条
		if (data.mfBusApplyList.length == 0) {
			htmlStr = htmlStr
					+ '<button class="btn btn-link content-title" title="'
					+ mfBusApplyObj.appName + '">' + mfBusApplyObj.appName
					+ '</button>';
		} else {

			var tmpStr = '';
			$
					.each(
							data.mfBusApplyList,
							function(i, appObj) {
								var appObjName = appObj.appName;
								var len = mfBusApplyObj.appName.length;
								if (appObjName.length > len) {
									appObjName = appObjName.substring(0, len)
											+ "...";
								}
								var unit = "天";
								if (appObj.termType == "1") {
									unit = "月";
								}
								tmpStr = tmpStr
										+ '<li class="more-content-li" onclick="switchBus(\''
										+ appObj.appId
										+ '\');">'
										+ '<p class="more-title-p"><span>'
										+ appObj.appName
										+ '</span></p>'
										+ '<p class="more-content-p"><span class="more-span">总金额 '
										+ appObj.appAmt
										+ '元</span><span class="more-span">期限 '
										+ appObj.term + unit
										+ '</span><span class="more-span">利率 '
										+ appObj.fincRate + '%</span></p>'
										+ '</li>';
							});

			htmlStr = htmlStr
					+ '<div class="btn-group">'
					+ '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
					+ mfBusApplyObj.appName
					+ '">'
					+ appName
					+ '</button>'
					+ '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
					+ '<span class="badge">' + data.mfBusApplyList.length
					+ '</span>' + '</button>' + '<ul class="dropdown-menu">'
					+ tmpStr + '</ul>' + '</div>';
		}
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan"  onclick="getBusInfo(\''
				+ mfBusApplyObj.appId
				+ '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';

		var unitStr = "天";
		if (mfBusApplyObj.termType == "1") {
			unitStr = "月";
		}
		htmlStr = htmlStr
				+ '<p>'
				+ '<span class="content-span"><i class="i i-rmb"></i>'
				+ mfBusApplyObj.fincAmt
				+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span"><i class="i i-richengshezhi"></i>'
				+ mfBusApplyObj.term + '</span>' + '<span class="unit-span">'
				+ unitStr + '</span>'
				+ '<span class="content-span"><i class="i i-tongji1"></i>'
				+ mfBusApplyObj.fincRate
				+ '</span><span class="unit-span">%</span>' + '</p>' + '</div>';
	}
	$("#busInfo").empty().html(htmlStr);
};
// 填充合同信息
function setPactInfo(data) {
	var mfBusPactObj = data.mfBusPact;
	var mfBusApplyListTmp = data.mfBusApplyList;
	var appName = mfBusPactObj.appName;
	if (appName.length > 8) {
		appName = appName.substring(0, 8) + "...";
	}
	var htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_20">'
			+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getBusInfo('
			+ mfBusPactObj.pactId + ');">'
			+ '<i class="i i-pact font-icon"></i>'
			+ '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
	// 如果业务笔数大于3笔
	if (data.mfBusApplyList.length > 3) {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix" style="padding-top: 45px;">'
				+ '<span>客户共有 <div class="badge more-apply-count">'
				+ data.mfBusApplyList.length + 1
				+ '</div> 笔在履行业务<a>查看</a></span>' + '</div>' + '</div>';
	} else {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column">';

		// 如果业务笔数为1条
		if (data.mfBusApplyList.length == 0) {
			htmlStr = htmlStr
					+ '<button class="btn btn-link content-title" title="'
					+ mfBusPactObj.appName + '">' + appName + '</button>';
		} else {

			var tmpStr = '';
			$
					.each(
							data.mfBusApplyList,
							function(i, appObj) {
								var appObjName = appObj.appName;
								var len = mfBusPactObj.appName.length;
								if (appObjName.length > len) {
									appObjName = appObjName.substring(0, len)
											+ "...";
								}
								var unit = "天";
								if (appObj.termType == "1") {
									unit = "月";
								}
								tmpStr = tmpStr
										+ '<li class="more-content-li" onclick="switchBus(\''
										+ appObj.appId
										+ '\');">'
										+ '<p class="more-title-p"><span>'
										+ appObj.appName
										+ '</span></p>'
										+ '<p class="more-content-p"><span class="more-span">总金额 '
										+ appObj.appAmt
										+ '元</span><span class="more-span">期限 '
										+ appObj.term + unit
										+ '</span><span class="more-span">利率 '
										+ appObj.fincRate + '%</span></p>'
										+ '</li>';
							});
			htmlStr = htmlStr
					+ '<div class="btn-group">'
					+ '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
					+ mfBusPactObj.appName
					+ '">'
					+ appName
					+ '</button>'
					+ '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
					+ '<span class="badge">' + data.mfBusApplyList.length
					+ '</span>' + '</button>' + '<ul class="dropdown-menu">'
					+ tmpStr + '</ul>' + '</div>';
		}
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan"  onclick="getBusInfo('
				+ mfBusPactObj.pactId
				+ ');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';

		var unitStr = "天";
		if (mfBusPactObj.termType == "1") {
			unitStr = "月";
		}
		htmlStr = htmlStr
				+ '<p>'
				+ '<span class="content-span"><i class="i i-rmb"></i>'
				+ mfBusPactObj.fincAmt
				+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span"><i class="i i-richengshezhi"></i>'
				+ mfBusPactObj.term + '</span>' + '<span class="unit-span">'
				+ unitStr + '</span>'
				+ '<span class="content-span"><i class="i i-tongji1"></i>'
				+ mfBusPactObj.fincRate
				+ '</span><span class="unit-span">%</span>' + '</p>' + '</div>';
	}
	$("#busInfo").empty().html(htmlStr);
};

function setPleInfo(pleInfo) {
    var htmlStr;
	if (pleInfo.pleId != null) {
		var pleObjName = pleInfo.pledgeName;
		if (pleObjName.length > 8) {
			pleObjName = pleObjName.substring(0, 8) + "...";
		}
		htmlStr = '<div class="col-xs-3 col-md-3 column padding_top_10">'
				+ '<button type="button" class="btn btn-font-pledge padding_left_15" onclick="getPleInfo();">'
				+ '<i class="i i-pledge font-icon"></i>'
				+ '<div class="font-text">押品信息</div>'
				+ '</button>'
				+ '</div>'
				+ '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column margin_top_20">'
				+ '<button type="button" id="apply-name" class="btn btn-link content-title"  title="'
				+ pleInfo.pledgeName
				+ '">'
				+ pleObjName
				+ '</button>'
				+ '</div>'
				+ '<div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan" onclick="getPleInfo();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>'
				+ '</div>'
				+ '<div class="row clearfix">'
				+ '<p>'
				+ '<span class="content-span" id="envalueAmt"><i class="i i-rmb" ></i>'
				+ pleInfo.envalueAmt
				+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span" id="receiptsAmount"><i class="i i-danju" ></i>'
				+ pleInfo.receiptsAmount
				+ '</span><span class="unit-span">张单据</span>' + '</p>'
				+ '</div>';

		$("#pleInfo").empty().html(htmlStr);
		$("#pleInfo").removeClass("no-content").addClass("has-content");
	} else {
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-none no-link padding_left_15">'
				+ '<i class="i i-pledge font-icon"></i>'
				+ '<div class="font-text">押品信息</div>' + '</button>' + '</div>'
				+ '<div class="col-xs-9 col-md-9 column no-content-tip">'
				+ '<span>暂无押品</span>' + '</div>';
		$("#pleInfo").empty().html(htmlStr);
		$("#pleInfo").removeClass("has-content").addClass("no-content");
	}
};

// 切换业务
function switchBus(appId) {
	// 获取业务以及押品信息，替换原页面相应模块中的内容
	$.ajax({
		type : "post",
		dataType : 'json',
		url : webPath+"/mfBusApply/getSwitchBusInfoAjax",
		data : {
			appId : appId
		},
		async : false,
		success : function(data) {
			if (data.flag == "success") {
				if (data.showFlag == "pact") {
					setPactInfo(data);
				} else {
					setApplyInfo(data);
				}
			} else {
				alert(data.msg, 0);
			}

		},
		error : function() {
			alert(top.getMessage("ERROR_REQUEST_URL", ""), 0);
		}
	});
};

// 多业务大于3条时，弹层列表页面
function getMultiBusList() {
	top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo=" + cusNo,"多笔业务", function() {});
};

//重新认证同盾的报告
var reportApply = function (){
	alert(top.getMessage("CONFIRM_OPERATION","认证"),2,function(){
		var reportData = tdReport("1");
		if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
			if(reportData.errorCode == "00000"){
				alert(reportData.errorMsg, 0);
				return ;
			}
			var TD_data = reportData.data;
			TD_data = $.parseJSON(TD_data);
			$.showTDReport(TD_data);
		}
	});
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