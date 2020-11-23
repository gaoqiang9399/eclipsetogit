$(function() {
	/**滚动条**/
	top.infIntegrity = null;
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
	
	/**授信业务流程--获取下一业务节点**//*
	if(wkfAppId != null && wkfAppId != ""){
		getNextBusPoint();
		if(operable=="operable"){
			showWkfFlow($("#wj-modeler1"),wkfAppId);
		}
	}
	*//**评级申请流程展示**//*
	if(evalAppNo != null && evalAppNo != ""){
		showWkfFlow($("#wj-modeler2"),evalAppNo);
		//评级审批历史
//		showWkfFlow($("#wj-modeler3"),evalAppNo);
		showWkfFlowVertical($("#wj-modeler3"),evalAppNo,"2","eval_approval");
	}
	*//**授信申请流程展示**//*
	if(creditApproveId != null && creditApproveId != ""){
		showWkfFlow($("#wj-modeler2"),creditApproveId);
		//评级审批历史
		showWkfFlow($("#wj-modeler3"),creditApproveId);
		showWkfFlowVertical($("#wj-modeler3"),creditApproveId,"1");
	}*/

	/**处理头像信息**/
	var data = headImg;
	if (ifUploadHead != "1") {
		data = "themes/factor/images/" + headImg;
	}
	data = encodeURIComponent(encodeURIComponent(data));
	document.getElementById('headImgShow').src = webPath+ "/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
	/**客户分类展示处理,普通客户，优质客户，黑名单**/
	//initCusTrackInfo();
	/**客户信息完整度**/
	MfCusDyForm.initCusIntegrity(cusInfIntegrity);
	/**展示客户信息模块**/
	//cusTableList：后台传过来的mf_cus_form_config的信息
	//初始化关联关系图标
	getRelation(relation);
	/**客户动态表单处理**/
	MfCusDyForm.init(cusTableList);



	//处理银行卡号
	if ($("[name=MfCusBankAccManageAction]").length == "1") {
		dealBankNo();
	}
	top.LoadingAnimate.stop();
	
	//根据权限判断是否展示客户信息模块的新增按钮
	/*$.ajax({
		url : "MfCusInfoDetailActionAjax_getUserPmsBizsAjax.action",
		data : "",
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(data) {
			if(data.flag=="success"){
				var pmBizNo=data.pmBizNo;
				//隐藏社会关系详情新增按钮
				if(pmBizNo.indexOf("cus-list-add-MfCusFamilyInfoAction")<0){
					$("div[name='MfCusFamilyInfoAction'] .list-table .title .formAdd-btn").hide();
				}
				//隐藏账户管理详情新增按钮
				if(pmBizNo.indexOf("cus-list-add-MfCusBankAccManageAction")<0){
					$("div[name='MfCusBankAccManageAction'] .list-table .title .formAdd-btn").hide();
				}
			}
		},
		error : function() {
		}
	});*/
	updateGroupName();
	//MfCreditQueryRecordInfo.init();//征信查询按钮初始化
	//渠道业务系统客户详情隐藏信息块上新增操作按钮
	//$(".list-table .title .formAdd-btn").hide();
	//$("#rotate-body").hide();
});
//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){
	if(key=="careaCity"){
		data["careaProvice"] = $("input[name=careaProvice]").val();//存储最终的编号
	}else if(key=="wayClassName"){
		data["wayClass"] = $("input[name=wayClass]").val();
		data["wayMaxClass"] = $("input[name=wayMaxClass]").val();
	}else if(key=="beginDate" || key=="endDate"){
		data["loanTerm"] = $(".loanTerm").text();
	}else if(key=="recommenderName"){//推荐者
		data["recommenderNo"] = $("input[name=recommenderNo]").val();//推荐者的编号
	}else if(key=="address"){
		if($("input[name=carea]").length>0){
			data["carea"] = $("input[name=carea]").val();//存储最终的编号
		}
		if($("input[name=careaCountry]").length>0){
			data["careaCountry"] = $("input[name=careaCountry]").val();//存储最终的编号
		}
	}else if(key=="investigateOpName"){//调查人(权益分析)
		data["investigateOpNo"] = $("input[name=investigateOpNo]").val();//存储最终的编号
	}else if(key=="investigationPeople"){//调查人(现场调查)
		data["investigationPeopleNo"] = $("input[name=investigationPeopleNo]").val();//存储最终的编号
	}	
}
//证件类型变化是修改证件号码验证规则
function idTypeChange(obj){
	//证件号码格式验证
	var idType = $(obj).val();
	var $idNum = $("input[name=legalIdNum]")[0];
	if(idType=="0"){//身份证样式格式
		//如果是身份证，添加校验
		changeValidateType($idNum, "idnum");
	}else{
		changeValidateType($idNum, "");
	}
	$(obj).parent().parent().find("i").click();
}
//初始化关联关系图标
function getRelation(relation){
	var level;
	if(relation){
		level = "L2";
	}else{
		level = "L1";
	}
	$(".btn-relation").attr("level", level);
}
//初始化客户分类信息
function initCusTrackInfo(){
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
	} else if (cusClassify == '4') {
		$(".cus-tag").addClass("btn-lightgray");
		$(".cus-tag").removeClass("btn-danger");
		$(".cus-tag").removeClass("btn-forestgreen");
		$(".cus-tag").removeClass("btn-dodgerblue");
	} else if (top.cusClassify == '5') {
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
};

//
function getInfList(){
	top.openBigForm(webPath+'/mfCusCustomer/getCusInfIntegrityList?cusNo='+cusNo,'客户信息完整度详情',myclose);
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
}




function addCusFormInfoCall() {
	var action = new Array("MfCusEquityInfoAction","MfCusFamilyInfoAction","MfCusGuaranteeOuterAction","MfCusHighInfoAction","MfCusKeyManAction","MfCusLegalEquityInfoAction","MfCusLegalMemberAction","MfCusShareholderAction","MfCusFamilyInfoAction");		
	if (top.addFlag) {
		MfCusDyForm.initCusIntegrity(top.infIntegrity);
		var cusRelation = action.indexOf(top.action);//判断唯一表中是否存入与关联关系有关的信息
		if(cusRelation!="-1"){
			var Relation = true;
			getRelation(Relation);
		}
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
						$dynamicBlock.find(".content").html(top.htmlString+"<input id='updateByOneUrl' type='hidden' value='/mfCusBankAccManage/updateByOneAjax.action'></input>");
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
				if(top.action=="MfCusCorpBaseInfoAction"||top.action=="MfCusPersBaseInfoAction"){
					refreshCustomerInfo();
				}
			}
		}
		if (top.action == "MfCusBankAccManageAction") {
			dealBankNo();
		}		
	}
};

// 获取业务信息
function getBusInfo(appId) {
	LoadingAnimate.start();
	busEntrance="t1";
	if(completeFlag == '0'){
		window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
	}else{
		window.location.href = webPath+"/mfBusApply/getTrenchApplySummary?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}
};
// 获取合同信息
function getPactInfo(appId) {
	LoadingAnimate.start();
	if(busEntrance=='t3'){
		window.location.href=webPath+"/mfBusPact/getTrenchFincInfoById?fincId="+fincId+"&appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}else{
		busEntrance="t2";
		window.location.href = webPath+"/mfBusPact/getTrenchPactDetailById?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}
};
//获取债权信息
function getCreditorInfo(creditId) {
	LoadingAnimate.start();
	window.location.href = webPath+"/p2pCreditorPoolManage/getById?creditId=" + creditId;
	
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
				top.baseInfo = null;
				// top.baseInfo="0";标识 该表单信息是否是客户的基本信息
				top.openBigForm(webPath+'/mfCusTable/getListPage?cusNo='+ cusNo + '&dataFullFlag=0', '选择表单',addCusFormInfoCall);
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
	top.baseInfo = null;
	top.getTableUrl = action + "_getListPage.action?cusNo=" + cusNo;
	top.openBigForm(getUrl, title, addCusFormInfoCall);
};

// 业务办理
function applyInsert() {
	if(!valiDocIsUp(cusNo)){
		return false;
	} 
	if(firstKindNo == '' || firstKindNo == null){
		alert(top.getMessage("FIRST_CHECK_PRODUCT"),0);
		return ;
	}
	// 判断该客户是否完善了基本信息
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
		success : function(data) {
			if (data.fullFlag == '1') {// 全部都填写了
				// 准入拦截
				var parmData = {'nodeNo':'cus_apply', 'relNo': cusNo, 'cusNo': cusNo};
				$.ajax({
					url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
					type : "post",
					data : {ajaxData: JSON.stringify(parmData)}, 
					dataType : "json",
					success : function(data) {
						if (data.exsitRefused == true) {// 存在业务拒绝
							top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
						}else if(data.exsitFX == true){//存在风险项
							alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"新增业务"}), 2, function() {
								window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
							});
						}else {
							// top.createShowDialog("MfBusApplyAction_inputQuery.action?cusNo="+cusNo+"&kindNo="+firstKindNo,"业务申请");
							//传appId时是为了在业务新增页面取消时返回到原来的页面
							window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
						}
					},
					error : function() {
					}
				});
			} else if (data.fullFlag == '0') {
				alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
			}
		}
	});

};

function addBaseInfo() {// 增加客户的基本信息
	top.addFlag = false;
	top.action = "MfCusCorpBaseInfoAction";
	top.showType = '1';
	top.baseInfo = "1";
	top.name = "MfCusCorpBaseInfoAction";
	top.openBigForm(webPath+"/mfCusCorpBaseInfo/input?cusNo=" + cusNo,"基本信息", closeCallBack);
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
	window.parent.openBigForm(webPath+'/docManage/pubUpload?relNo=' + cusNo + '&cusNo=' + cusNo + '&scNo=' + scNo, '图文资料', function() {
	});
};

//客户跟进
function cusTrack(type) {
	top.updateFlag = false;
	top.openBigForm(webPath+'/mfCusTrack/getListPage?cusNo=' + cusNo+ "&query=" + type,'客户跟进',function(){
		if (top.updateFlag){
			getCusTrackTopList();
		}
	});
};


// 客户分类
function cusTag() {
	top.updateFlag = false;
	top.classify = false;
	top.openBigForm(webPath+'/mfCusClassify/getByCusNo?cusNo=' + cusNo,'客户分类', function() {
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
				$("#cusNameRate-span").text(top.cusTag);
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
	}, '90', '90');
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
			}  else if (top.cusClassify == '4') {
				$("#cusNameRate-span").text(top.cusTag);
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
			top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请信息', function() {});
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
						if (data.fullFlag == "0") {
							alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
						} else {
							// 新增
							top.creditFlag=false;
							top.creditType="1";
							top.creditAppId="";
							var url = webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
							top.openBigForm(url, "授信申请", wkfCallBack);
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

//单独提交业务流程
function doCommitProcess(){
	$.ajax({
		url:webPath+"/mfCusCreditApply/commitBusProcessAjax?wkfAppId="+wkfAppId,
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

// 获取下一个业务节点 获取当前业务节点的参数信息
function getNextBusPoint() {
	$.ajax({
		url : webPath+"/mfCusCreditApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			var wkfFlag = data.wkfFlag;
			//业务结束或业务否决，不展示业务流程图和流程指引
			if (wkfFlag == '0'||data.creditSts=="4") {
				$(".bg-danger").removeClass("show");
				$(".bg-danger").addClass("hide");
			} else if (wkfFlag == '1') {
				var url = data.url;
				var title = data.title;
				var nodeName = data.nodeName;
				$(".block-next").empty();
				$(".next-div").unbind();
				if (data.creditSts == "2" && nodeName == "credit_approval") { // 审批环节
					var approveInfo="业务在"+title+"阶段，";
					var approveNodeName=data.approveNodeName;
					var approvePartName=data.approvePartName;
					if(approvePartName!=""&&approvePartName!=null){
						approveInfo=approveInfo+"正在"+approveNodeName+"岗位的"+approvePartName+"审批";
					}else{
						approveInfo=approveInfo+"正在"+approveNodeName+"岗位审批";
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
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
					$("#showWkf").removeClass("hide");
					$("#showWkf").addClass("show");
				}else if(data.creditSts == "3" && nodeName == "credit_approval"){//审批通过，但是提交下一业务节点时失败，需要在这里手动提交业务流程
						$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>业务提交>></a></span>");
						$(".next-div").click(function(){
							alert(top.getMessage("CONFIRM_OPERATION","业务提交"),2,function(){
								doCommitProcess();
							});
						}); 
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
						$("#showWkf").removeClass("hide");
						$("#showWkf").addClass("show");
				}else {
					$(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
					$("#showWkf").removeClass("hide");
					$("#showWkf").addClass("show");
					$(".next-div").click(function() {
						toNextBusPoint(url, title, nodeName);
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
//								showWkfFlow(wkfAppId);
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							} else {
								getNextBusPoint();
//								showWkfFlow(wkfAppId);
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
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
				//直接提交下一步流程
				$.ajax({
					url:webPath+"/mfCusCreditApply/doCommitWkf",
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
							window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+creditAppId+"&appId="+appId+"&entrance=credit";
						}
					}
				});
			}else if(creditType=="1"){
				top.window.openBigForm(url, title, toCollateralDetail);
			}
		}
		if (nodeName == "protocolPrint") {// 授信协议
			var url = webPath+"/mfCusCreditApply/protocolPrint?wkfAppId=" + wkfAppId;
			top.window.openBigForm(url, title, wkfCallBack);
		}
		if (nodeName == "report") { // 尽职报告
			//var url = "MfCusCreditApplyAction_getOrderDescFirst.action?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=1";
			top.openBigForm(url+"&wkfAppId="+wkfAppId, "授信尽调报告", wkfCallBack);
		}
	}
}
//登记过押品信息后跳转押品详情
function toCollateralDetail(){
	if(top.addCreditCollaFlag){
		window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&appId="+appId+"&entrance=credit";
	}
}
// 回调函数
function wkfCallBack() {
	if(top.creditFlag){
		if (top.wkfAppId != undefined && top.wkfAppId != "") {
			wkfAppId = top.wkfAppId;
		}
		if (top.creditType != undefined && top.creditType != "") {
			creditType = top.creditType;
		}
		if (top.creditAppId != undefined && top.creditAppId != "") {
			creditAppId = top.creditAppId;
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
		$("#wj-modeler1").empty();
		showWkfFlow($("#wj-modeler1"),wkfAppId);
	}
}
//提交审批
function processSubmitAjax(){
	if(top.creditFlag){
			wkfCallBack();
			alert(top.getMessage("CONFIRM_OPERATION","提交到审批"),2,function(){
				LoadingAnimate.start();
				$.ajax({
					url : webPath+"/mfCusCreditApply/processSubmitAjax",
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
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							} else {
								getNextBusPoint();
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							}
						} else {
							alert(data.msg);
						}
					}
				});
			});
	}
}
//获取授信状态及授信额度
function getCreditSts(status,creditSum,applySum){
	if(status == "1"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		$(".creditBus").html("授信中");
	}
	if(status == "2"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		$(".creditBus").html("授信中");			
	}
	if(status == "3"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		//$(".creditBus").html("审批通过");
		$(".creditBus").html("授信中");
	}
	if(status == "5"){ //已签约
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("cus-middle");
		$(".creditBus").parent().addClass("btn-dodgerblue");
		$(".creditBus").html(applySum+" 万");
		$("#showWkf").removeClass("show");
		$("#showWkf").addClass("hide");
	}
}

/*外部评级*/
function getManEvalApp() {
	LoadingAnimate.start();
	$.ajax({
		url : webPath+"/appEval/getUnfinishedAppEval",
		data : {
			cusNo : cusNo,
			cusBaseType:cusBaseType
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop();
			if (data.status == "0") {
				alert(data.msg, 0);
				return;
			}
			if (data.flag == "1") {//未提交
				top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&cusNo=" + cusNo, "评级申请", function() {});
			}else if(data.flag == "2"){//审批阶段查看详情
				top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=1", "评级信息", function() {});
			}else{//新增或审批完成
				top.openBigForm(webPath+"/appEval/initiateApp?cusNo="+ cusNo, "评级申请", function() {});
			}
		},
		error : function() {
			LoadingAnimate.stop();
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

//评级申请
function getInitatEcalApp() {
	LoadingAnimate.start();
	$.ajax({
		url : webPath+"/appEval/getUnfinishedAppEval",
		data : {
			cusNo : cusNo,
			cusBaseType:cusBaseType
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop();
			if (data.status == "0") {
				alert(data.msg, 0);
				return;
			}
			top.submitFlag=false;//是否提交保存
			top.approveFlag=false;//是否走审批
			if (data.flag == "1") {//未提交
				top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&cusNo=" + cusNo, "评级申请", function() {
					showEvalLevelResult();
				});
			}else if(data.flag == "2"){//审批阶段查看详情
				top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=1", "评级信息", function() {});
			}else{//新增或审批完成
				top.openBigForm(webPath+"/appEval/initManEavl?cusNo="+ cusNo, "评级申请", function() {
					showEvalLevelResult();
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
		top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=4&useType=1", "评级信息", function() {});
	} else {
		return false;
	}
};
//评级不走审批时提交评级回显
function showEvalLevelResult(){
	if(top.submitFlag&&!top.approveFlag){
		$.ajax({
			url : webPath+"/mfCusCustomer/getCusByIdAjax",
			data : {
				cusNo : cusNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if(data.flag=="success"){
					var cusLevelName=data.cusInfo.cusLevelName;
					$("#cusEvalLevel").html('<i class="i i-eval1"></i>'+cusLevelName);
					$("#cusEvalLevel").attr("onclick","getEvalDetailResult('1');");
					if(cusLevelName.indexOf("A")!=-1){
						$("#cusEvalLevel").attr("class","btn btn-forestgreen btn-view");
					}else if(cusLevelName.indexOf("B")!=-1){
						$("#cusEvalLevel").attr("class","btn cus-middle btn-view");
					}else if(cusLevelName.indexOf("C")!=-1){
						$("#cusEvalLevel").attr("class","btn btn-danger btn-view");
					}
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	}
};

// 上传头像
function uploadHeadImg() {
	window.parent.openBigForm(webPath+'/mfCusCustomer/uploadHeadImg?relNo='+ cusNo + '&cusNo=' + cusNo, '客户头像', showNewHeadImg);
};
function showNewHeadImg() {
	var data;
	$.ajax({
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
//-------------------------------------财务报表模块功能 START-----------------------------------------------------------//
//获得财务报表信息
function getPfsDialog() {
	top.isUpload = false;
	top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo='+cusNo,'财务报表', function() {
		if(top.isUpload){  //财务报表上传成标志			
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/cusFinMainActionAjax_queryCusFinDataAjax.action",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					getFinDataHtml(data);
				},
				error : function() {
					LoadingAnimate.stop();
				}
			});
		}
	});
};
//获得跟名下企业财务报表信息
function getPersonPfsDialog() {
	top.isUpload = false;
	top.openBigForm(webPath+'/cusFinMain/getListPageForPerson?cusNo='+cusNo,'财务报表', function() {
		if(top.isUpload){  //财务报表上传成标志			
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/cusFinMain/queryCusFinDataAjax",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					getPersonFinDataHtml(data);
				},
				error : function() {
					LoadingAnimate.stop();
				}
			});
		}
	});
};
//组装财务报表信息块
function getFinDataHtml(data){
	MfCusDyForm.initCusIntegrity(top.infIntegrity);
	if(data.cusFinMainList.length > 0){
		$("div[name=MfCusFinMainAction]").remove();
		var finDiv = '<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block">'+
		'<div class="list-table">'+
			'<div class="title">'+
				'<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>'+
				'<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
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
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
						'</tr>'+
					'</thead>'+
					'<tbody id="tab">';
		var htmlStr = "";
		$.each(data.cusFinMainList,function(i,cusFinMain){
			var viewStr = "CusFinMainAction_inputReportView.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1";
			var confirStr = "CusFinMainActionAjax_updateReportConfirmAjax.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
			var delStr = "CusFinMainActionAjax_deleteAjax.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
			var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            if(reportConfirmFlag != 2){
                if(cusFinMain.finRptSts != 1){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }
            }
            if(cusFinMain.ifShowDel == "1"){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.ifShowDel == "0"){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }
			if(!cusFinMain.finCapFlag){
				zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					zcStr = "上传";
				}
			}
			if(!cusFinMain.finProFlag){
				proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					proStr = "上传";
				}
			}
			if(!cusFinMain.finCashFlag){
				cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					cashStr = "上传";
				}
			}
			htmlStr += '<tr>'+
							'<td align="center" width="10%">'+
								'<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
							'</td>'+
							'<td align="center" width="15%">'+
								zcStr+
							'</td>'+
							'<td align="center" width="15%">'+
								proStr+
							'</td>'+
							'<td align="center" width="15%">'+
								cashStr+
							'</td>'+
							'<td align="center" width="20%">'+
								opStr+
							'</td>'+
						'</tr>';
		});
		finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>'; 
		$(".block-add").after(finDiv);
	}
}
//组装名下企业财务报表信息块
function getPersonFinDataHtml(data){
	MfCusDyForm.initCusIntegrity(top.infIntegrity);
	if(data.cusFinMainList.length > 0){
		$("div[name=MfCusFinMainAction]").remove();
		var finDiv = '<div name="MfCusFinMainAction" title="名下企业财务报表" class="dynamic-block">'+
		'<div class="list-table">'+
			'<div class="title">'+
				'<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表</span>'+
				'<button title="新增" onclick="getPersonPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
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
					'<colgroup style="width: 10%"></colgroup>'+
					'<thead>'+
						'<tr>'+
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">名下企业名称4</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
						'</tr>'+
					'</thead>'+
					'<tbody id="tab">';
		var htmlStr = "";
		$.each(data.cusFinMainList,function(i,cusFinMain){
			var viewStr = "CusFinMainAction_inputReportView.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1"+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var confirStr = "CusFinMainActionAjax_updateReportConfirmAjax.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var delStr = "CusFinMainActionAjax_deleteAjax.action?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            if(reportConfirmFlag != 2){
                if(cusFinMain.finRptSts != 1){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }
            }
            if(cusFinMain.ifShowDel == "1"){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.ifShowDel == "0"){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }
			if(!cusFinMain.finCapFlag){
				zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					zcStr = "上传";
				}
			}
			if(!cusFinMain.finProFlag){
				proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					proStr = "上传";
				}
			}
			if(!cusFinMain.finCashFlag){
				cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					cashStr = "上传";
				}
			}
			htmlStr += '<tr>'+
							'<td align="center" width="10%">'+
								'<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
							'</td>'+
							'<td align="center" width="15%">'+
								cusFinMain.relationCorpName+
							'</td>'+
							'<td align="center" width="15%">'+
								zcStr+
							'</td>'+
							'<td align="center" width="15%">'+
								proStr+
							'</td>'+
							'<td align="center" width="15%">'+
								cashStr+
							'</td>'+
							'<td align="center" width="20%">'+
								opStr+
							'</td>'+
						'</tr>';
		});
		finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>'; 
		$(".block-add").after(finDiv);
	}
}
//打开财务报表查看页面
function reportView(obj,url){
	top.openBigForm(url,'财务报表',false);
};
//数据确认
function confirmFinMain(obj,url){
	LoadingAnimate.start();
	var parm = "?"+url.split("?")[1];
	$.ajax({
		type:"post",
		url:webPath+"/cusFinMain/checkFinDataAjax"+parm,
		dataType:"json",
		success:function(data){
			if(data.flag=="success"){
				if(data.checkFlag == "success"){
					LoadingAnimate.stop();
					alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
						LoadingAnimate.start();
						doCofrimData(obj,url);
					});
				}else{
					LoadingAnimate.stop();
					alert(top.getMessage("CONFIRM_FIN_VERIFY"),2,function(){
						LoadingAnimate.start();
						doCofrimData(obj,url);
					});
					
				}
			}else if(data.flag=="error"){
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_FIN_VERIFY"),0);
			}
		},error:function(){
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
		}
	});
}

//执行确认操作
function doCofrimData(obj,url){
	var tdObj = $(obj).parent();
	var tdOp = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="listOpStyle">删除</span>';
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(data){
			LoadingAnimate.stop();
			if(data.flag=="success"){
				$(tdObj).empty();
				$(tdOp).appendTo(tdObj);
				$(".color_theme").css("color","gray");
				$(tdObj).parent().find('td:not(:first)').find('.abatch').css("color","gray");
				$(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('href');//去掉a标签中的href属性
				$(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('onclick');//去掉a标签中的onclick事件
			}else if(data.flag=="error"){
				window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
			}
		},error:function(){
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
		}
	});
}
//-------------------------------------财务报表模块功能 END-----------------------------------------------------------//

// 删除文件
function delFile() {
	var srcPath = "/tmp/";
	$.ajax({
		url : webPath+"/ploadFile/delFile",
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
	top.openBigForm(webPath+'/mfCusRelation/forDetail?cusNo=' + cusNo,
			'关联关系', function(){});
};
function cusRelationIn() {
	top.relation = false;
	window.parent.openBigForm(webPath+'/mfCusRelationType/input?cusNo='+ cusNo, '新增关联关系', function() {
		if(top.relation){
			getRelation(top.relation);
		}
	});
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
		top.cusSubTypeName=$(".type-name-div").text();
		top.window.openBigForm(webPath+'/mfCusCustomer/toUpdate?cusNo=' + cusNo, '修改客户信息',refreshCustomerInfo);
	}
}
// 刷新客户登记信息以及基本信息
function refreshCustomerInfo() {
	if (top.updateFlag) {
        var BaseInfoFlag;
		if(top.cusBaseType=="2"){
			$("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsTel]").html(top.cusTel!=null&&top.cusTel!=''?top.cusTel:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
			$("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
			$("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
			$(".cus.head-title").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
			// 刷新基本信息
			BaseInfoFlag = $("[name=MfCusPersBaseInfoAction]").length;
			if (BaseInfoFlag == "1") {
				$("#MfCusPersBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
			}
		}else{
			$("div[id=cusNameShow]").html(top.cusName!=null&&top.cusName!=''?top.cusName:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsTel]").html(top.contactsTel!=null&&top.contactsTel!=''?top.contactsTel:"<span class='unregistered'>未登记</span>");
			$("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
			$("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
			$("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
		
			// 刷新基本信息
			BaseInfoFlag = $("[name=MfCusCorpBaseInfoAction]").length;
			if (BaseInfoFlag == "1") {
				$("#MfCusCorpBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
			}
		}
		$(".cus-type-font").empty().html('<div class="type-name-div">'+top.cusSubTypeName+'</div>');
		
		updateGroupName();
	}
};

function selectAreaCallBack(areaInfo) {
	$("input[name=careaCity]").val(areaInfo.disName);
	$("input[name=careaProvice]").val(areaInfo.disNo);
};

function selectAddressCallBack(areaInfo) {
	$("input[name=commAddress]").val(areaInfo.disName);
	$("input[name=careaCountry]").val(areaInfo.disNo);
};

function selectWayClassCallBack(waycls) {
	$("input[name=wayClassName]").val(waycls.disName);
	$("input[name=wayClass]").val(waycls.disNo);
	$("input[name=wayMaxClass]").val(waycls.disNo);
};

function selectBankAreaCallBack(areaInfo) {
	$("input[name=bankArea]").val(areaInfo.disName);
};

//填充押品信息
function setPleInfo(data) {
	$("#pleInfo").addClass("hide");
	$("#pleInfono").addClass("hide");
	$("#pleInfo").removeClass("show");
	$("#pleInfono").removeClass("show");
	if (data.mfBusPledge.id != null) {
		// 填充业务信息
		setCollateralInfo(data.mfBusPledge);
		MfBusCollateralRel_AbstractInfo.collateralRelId = data.mfBusPledge.busCollateralId;
		$("#pleInfo").removeClass("hide");
		$("#pleInfo").addClass("show");
		if(data.busModel!="5"){
			if(data.evalFlag=="0"){
				$("#pledgeInfo").html("没有登记评估信息");
			}
		}
	}else{
		$("#pleInfono").removeClass("hide");
		$("#pleInfono").addClass("show");
		if(busModel=="13"||busModel=="5"){
			$("#title").html("应收账款");
			$("#noPledge").html("暂无应收账款");
			$("#titleLi").attr("class","i i-rece font-icon");
		}
	}
};


//填充业务信息
function setBusInfo(data) {
	var busInfoObj = data.mfbusInfo;
	var mfBusApplyListTmp = data.mfBusApplyList;
	var appName = busInfoObj.appName;
	if (appName.length > 8) {
		appName = appName.substring(0, 8) + "...";
	}
	var htmlStr="";
	var busId = data.mfbusInfo.appId;
	if(data.showFlag=="apply"){
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''+ busId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
	}else if(data.showFlag=="pact"){
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\');">'
				+ '<i class="i i-pact font-icon"></i>'
				+ '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
	}
	// 如果业务笔数大于3笔
	if (data.mfBusApplyList.length > 3){
			htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix padding_top_20">'
					+ '<span>客户共有 <a  class="moreCnt more-apply-count pointer" onclick="getMultiBusList();">'
					+ (data.mfBusApplyList.length + 1)
					+'</a> 笔在履行业务'
					+ '</span>'
				+ '</div>'
			+ '</div>';	
	} else {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column">';
		if(frompage=="pledge"){//如果主体页面是押品页面，业务不展示多笔业务的情况
			if(data.showFlag=="apply"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}else if(data.showFlag=="pact"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}
		}else{
			// 如果业务笔数为1条
			if (data.mfBusApplyList.length == 0) {
				if(data.showFlag=="apply"){
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}else if(data.showFlag=="pact"){
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}
			} else {
				var tmpStr = '';
				$.each(data.mfBusApplyList,function(i, appObj) {
					var appObjName = appObj.appName;
					var len = busInfoObj.appName.length;
					if (appObjName.length > len) {
						appObjName = appObjName.substring(0, len)+ "...";
					}
					tmpStr = tmpStr+ '<li class="more-content-li" onclick="switchBus(\''+ appObj.appId+ '\');">'
							+ '<p class="more-title-p"><span>'+ appObjName+ '</span></p>'
							+ '<p class="more-content-p"><span class="more-span">总金额 '+ appObj.appAmt+ '元</span><span class="more-span">期限 '
							+ appObj.termShow + '</span><span class="more-span">利率 '+ appObj.fincRate + '%</span></p>'
							+ '</li>';
				});
				htmlStr = htmlStr
						+ '<div class="btn-group">'
						+ '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
						+ busInfoObj.appName + '">'+ appName
						+ '</button>'
						+ '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
						+ '<span class="badge">' + data.mfBusApplyList.length+ '</span>' 
						+ '</button>' 
						+ '<ul class="dropdown-menu">'+ tmpStr + '</ul>' + '</div>';
			}
		}
		if(data.showFlag == "apply"){
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getBusInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}else if(data.showFlag == "pact"){
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan" style="margin-top: -5px;" onclick="getPactInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}

		var unitStr = "天";
		if (busInfoObj.termType == "1") {
			unitStr = "个月";
		}
		htmlStr = htmlStr + '<p>'
				+ '<span class="content-span"><i class="i i-rmb"></i>'+ busInfoObj.fincAmt+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ busInfoObj.term + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
				+ '<span class="content-span"><i class="i i-tongji1"></i>'+ busInfoObj.fincRate+ '</span><span class="unit-span">%</span>'
				+ '</p>' 
				+ '</div>';
	}
	$("#busInfo .cont-row").html(htmlStr);
};
	
function selectConAreaCallBack(areaInfo) {
	$("input[name=address]").val(areaInfo.disName);
	$("input[name=careaCountry]").val(areaInfo.disNo);
	$("input[name=carea]").val(areaInfo.disNo);
};
// 切换业务
function switchBus(appId) {
	// 获取业务以及押品信息，替换原页面相应模块中的内容
	$.ajax({
		type : "post",
		dataType : 'json',
		url : webPath+"/mfBusApply/getSwitchBusInfoAjax",
		data : {appId : appId},
		async : false,
		success : function(data) {
			
			if (data.flag == "success") {
				setBusInfo(data);
				setPleInfo(data);
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
function getMultiBusList(flag){
	if('apply'==flag){
		top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中业务",function(){});
	}else if('pact'==flag){
		top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行合同",function(){});
	}else if('finc'==flag){
		top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行借据",function(){});
	}else if('assure'==flag){
		top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"担保项目",function(){});
	}
};
//cusNoTmp临时变量，cusNo进到oneCallback就没有了
var cusNoTmp=cusNo;
//单字段编辑的保存回调方法。
function oneCallback(data,disVal) {
	var name = data[0].name;
	var value = data[0].value;
	var $_form = this;
	var formAction = $_form.attr("action");
	// 如果修改的是XXX表单，则进行XXX相关的业务处理。
	if (formAction === "") {
		
	} else if (formAction === "MfCusCorpBaseInfoActionAjax_updateAjaxByOne.action") {
		if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
			//如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
			$.post(webPath+"/mfCusCorpBaseInfo/getByIdAjax", {cusNo: cusNoTmp}, function(data) {
				if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
					var proj=data.mfCusCorpBaseInfo.projSize;
					var proJectName="";
					if("1"==proj){
						proJectName="大型企业";
					}
					if("2"==proj){
						proJectName="中型企业";
					}
					if("3"==proj){
						proJectName="小型企业";
					}
					if("4"==proj){
						proJectName="微型企业";
					}
					$(".projSize").html(proJectName);
				}
			});
		}
	} else if (formAction === "/mfCusBankAccManage/updateByOneAjax") {
		if (name=="accountNo"){
			getBankByCardNumbe(name,data[1].value);
		}
	} else if (formAction === "MfCusProfitLossAnalyseActionAjax_updateAjaxByOne.action") {
		if(name=="incomeTotal"||name=="variableCostTotal"||name=="fixFeePay"||name=="otherIncome"||name=="otherOftenFeePay"){//损益分析
			var incomeTotal = $('input[name=incomeTotal]', $_form).val();
			var variableCostTotal = $('input[name=variableCostTotal]', $_form).val();
			var grossProfit = CalcUtil.subtract(incomeTotal.replace(/,/g,''),variableCostTotal.replace(/,/g,''));
			//修改毛利
			$('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(grossProfit,2));
			var fixFeePay = $('input[name=fixFeePay]', $_form).val();
			var netProfit = CalcUtil.subtract(grossProfit,fixFeePay.replace(/,/g,''));
			//修改净利润
			$('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(netProfit,2));
			var otherIncome = $('input[name=otherIncome]', $_form).val();
			var profitSum = CalcUtil.add(netProfit,otherIncome.replace(/,/g,''));
			var otherOftenFeePay = $('input[name=otherOftenFeePay]', $_form).val();
			var disposableIncome = CalcUtil.subtract(profitSum,otherOftenFeePay.replace(/,/g,''));
			//修改可支配收入
			$('input[name=otherOftenFeePay]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(disposableIncome,2));
			BASE.oneRefreshTable("incomeTotal",incomeTotal);
			BASE.oneRefreshTable("variableCostTotal",variableCostTotal);
			BASE.oneRefreshTable("grossProfit",CalcUtil.formatMoney(grossProfit,2));
			BASE.oneRefreshTable("fixFeePay",fixFeePay);
			BASE.oneRefreshTable("netProfit",CalcUtil.formatMoney(netProfit,2));
			BASE.oneRefreshTable("otherIncome",otherIncome);
			BASE.oneRefreshTable("otherOftenFeePay",otherOftenFeePay);
			BASE.oneRefreshTable("disposableIncome",CalcUtil.formatMoney(disposableIncome,2));
		}
	}else if (formAction === "MfCusDebtArmourInfoActionAjax_updateAjaxByOne.action") {//负债信息(铁甲网)
		if(name=="overAmt"||name=="loanAmt"||name=="pettyLoanAmt"||name=="creditCardDebtAmt"
			||name=="externalGuaranteeAmt"||name=="otherDebtAmt"){
			var overAmt = $('input[name=overAmt]', $_form).val().replace(/,/g,'');
			var loanAmt = $('input[name=loanAmt]', $_form).val().replace(/,/g,'');
			var pettyLoanAmt = $('input[name=pettyLoanAmt]', $_form).val().replace(/,/g,'');
			var creditCardDebtAmt = $('input[name=creditCardDebtAmt]', $_form).val().replace(/,/g,'');
			var externalGuaranteeAmt = $('input[name=externalGuaranteeAmt]', $_form).val().replace(/,/g,'');
			var otherDebtAmt = $('input[name=otherDebtAmt]', $_form).val().replace(/,/g,'');
			//求和 
			var sumAmt1 = CalcUtil.add(overAmt,loanAmt);
			var sumAmt2 = CalcUtil.add(pettyLoanAmt,creditCardDebtAmt);
			var sumAmt3 = CalcUtil.add(externalGuaranteeAmt,otherDebtAmt);
			var sumAmt11 = CalcUtil.add(sumAmt1,sumAmt2);
			var sumAmt = CalcUtil.add(sumAmt11,sumAmt3);
			$('input[name=otherDebtAmt]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(sumAmt,2));
		}
	} else {
		/**
		 *  基本不会重复的，或者基本完全通用的，可以不加条件来处理 
		 *  有重复的，需要把两个都独立出去。
		 */
		//mf_cus_customer mf_cus_register mf_cus_corp_base_info
		if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
			$("span[id="+name+"]", $_form).html(value);
		}
		if(name=="debtType"||name=="useType"||name=="highCusType"||name=="education"||name=="highCusType"||name=="education"||name=="sellArea"||name=="sellWayclass"||name=="isLegal"||"relative"==name||name=="assetsType"||name=="saleArea"||name=="corpKind"||name=="workKind"||name=="goodsRule"||name=="changeType"||name=="corpNature"||name=="registeredType"||name=="idCardInfo"||name=="regBookInfo"||name=="conEnvironment"||name=="conPaymentt"){
			BASE.oneRefreshTable(name,disVal);
		}else if(name=="careaCity"){
			var careaProvice = $("input[name=careaProvice]", $_form).val();	
			var cusNo = $("input[name=cusNo]", $_form).val();
			$.ajax({		
				url : webPath+"/mfCusCorpBaseInfo/updateCareaAjax",
				data : {
					ajaxData : careaProvice ,cusNo:cusNoTmp
				},
				type : 'post',
				dataType : 'json',
				async:false,
				success : function(data) {					
				}
			});		
		}else if(name=="initialCapital"||name=="durateDisposableIncomeTotal"||name=="durateOutNotOftenPay"||name=="assetDepreciate"||name=="durateAssetInject"){//权益分析
			var initialCapital = $('input[name=initialCapital]').val();//启动资本
			var durateDisposableIncomeTotal = $('input[name=durateDisposableIncomeTotal]', $_form).val();//期间可支配收入合计
			var durateOutNotOftenPay = $('input[name=durateOutNotOftenPay]', $_form).val();//期间表外非经常性支出
			var assetDepreciate = $('input[name=assetDepreciate]', $_form).val();//资产折旧
			var durateAssetInject = $('input[name=durateAssetInject]', $_form).val();//期间注资
			
			var incomeValue = CalcUtil.add(initialCapital.replace(/,/g,''),durateDisposableIncomeTotal.replace(/,/g,''));
			var incomeTotalValue = CalcUtil.add(incomeValue,durateAssetInject.replace(/,/g,''));
			var payValue = CalcUtil.add(durateOutNotOftenPay.replace(/,/g,''),assetDepreciate.replace(/,/g,''));
			var ownerAssetValue = CalcUtil.subtract(incomeTotalValue,payValue);
			//修改所有者权益
			$('input[name=durateAssetInject]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(ownerAssetValue,2));
		}else if(name=="ifGroup"){
			updateGroupName();
		}else{
			if ($_form.attr("id") === 'listForm') {
				BASE.oneRefreshTable(name,value);
			}
		}
	}
	
}
function getBankByCardNumbe(name,bankId) {
		$.ajax({
			url:webPath+"/mfCusBankAccManage/getByIdForOneAjax",
			data:{id:bankId},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
                    var index;
					//$("#MfCusBankAccManageAction").html(data.htmlStr);
					if(name=="accountNo"){
						refreshTable(name,data.mfCusBankAccManage);
						index=	$("th[name=bankAuthSts]").index();
						$(".listshow-tr").prev().find("td").eq(index).html(data.bankAuthSts);
					}else if (name=="useType"){
						index=	$("th[name=useType]").index();
						$(".listshow-tr").prev().find("td").eq(index).html(data.userType);
					}
					$("input[name=bank]").val(data.mfCusBankAccManage.bank);
					$("input[name=bankNumbei]").val(data.mfCusBankAccManage.bankNumbei);
					$("input[name=bank]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bank);
					$("input[name=bankNumbei]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bankNumbei);
					
				}else{
					$("input[name=bank]").val("");
					$("input[name=bankNumbei]").val("");
				}	
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
};
//
function refreshTable(name,data){
	var index;
	if(name=="accountNo"){
		 index = $("th[name=accountNo]").index();
		$(".listshow-tr").prev().find("td").eq(index).find('a').html(data.accountNo);
		BASE.oneRefreshTable("bank",data.bank);
		dealBankNo();
	}else if (name=="useType"){
		var useType = 	$("select[name=useType]").parent().parent().find("div[class=fieldShow]").html();
		BASE.oneRefreshTable(name,useType);
	}
}
//联网核查跳转到增值服务页面
function addService(){
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
				headImgShowSrc = webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
				if(customer.cusType=="202"){//个人客户
					/*var headImg=customer.headImg;
					var ifUploadHead = customer.ifUploadHead;
					var data = headImg;
					if(ifUploadHead!="1"){
						data = "themes/factor/images/"+headImg;
					}
					data = encodeURIComponent(encodeURIComponent(data));
					var ipandport=window.document.location.href;

					var port=window.location.href;
					var src=webPath+"/UploadFileAction_viewUploadImageDetail.action?srcPath="+data+"&fileName=user2.jpg";*/
					url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}else{
					url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType+"&contactsName="+customer.contactsName+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));

				}
				window.location.href=url;
			}else{
				LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		}
	});
}

//风险拦截
function cusRisk(){
	if(!(dialog.get('riskInfoDialog') == null)){
		dialog.get('riskInfoDialog').close();
	}
	top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
};

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
	top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo+'&appId='+appId, '认证报告',function(){},"75","90");
}


//个人负债表单更新开始日期和结束日期，联动计算期限
function getLoanTerm(datas){
	var beginDate =  $("input[name=beginDate]").val().replace(/\-/g, ""); 
	var endDate = $("input[name=endDate]").val().replace(/\-/g, ""); 
	var key = $(".changeval .inputText").attr("name");
	if(key=="beginDate"){
		beginDate = datas.replace(/\-/g, "");
	}else if(key =="endDate"){
		endDate = datas.replace(/\-/g, "");
	}
	if(beginDate!="" && endDate!=""){
		UtilDwr.getMonthsAndDays(beginDate,endDate,function(data){
			var loanTerm="";
			if(data[0]>0){
				loanTerm=loanTerm+data[0]+"月";
			}
			if(data[1]>0){
				loanTerm=loanTerm+data[1]+"天";
			}
			$(".loanTerm").text(loanTerm);
		});
		
	}
	return true;
}
// 更新是否集团客户
function updateGroupName(){
	if($("select[name='ifGroup']").size() > 0){
		var value = $("select[name='ifGroup']").val();
		if(value == "1"){
			$("input[name='groupName']").parent().parent().parent().show();
		}else{
			$("input[name='groupName']").parent().parent().parent().hide();
		}
	}
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

//重新认证同盾的报告
var reportApply = function (){
	alert(top.getMessage("CONFIRM_OPERATION","认证"),2,function(){
		var reportData = tdReport("1");
		if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
			if(reportData.errorCode == "00000"){
				alert(reportData.errorMsg, 0);
				return;
			}
			var TD_data = reportData.data;
			TD_data = $.parseJSON(TD_data);
			$.showTDReport(TD_data);
		}
	});
};

//阳光银行放款渠道是否允许自助放款设置
var channelSelfPutoutSet = function(){
	top.window.openBigForm(webPath+'/mfCusSelfPutoutConfigApply/input?cusNo='+cusNo, '自助放款设置',function(){
		
	});
};
