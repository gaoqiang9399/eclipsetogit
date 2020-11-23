var searchList = function(window,$){
	//完善客户资料
	var _updateCusFormStas = function(cusNo){
		top.openBigForm(webPath+'/mfCusTable/getPageUpdateStas?cusNo='+cusNo+"&relNo="+cusNo,'完善资料',false);
	};
	
	// 发起授信 - 客户授信操作
	var _getAppAuth = function(cusNo,cusType) {
		if(cusType=="2"){
			alert("功能正在调整，敬请稍后！", 3);
			return;
		}
		top.LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfCusCreditApply/checkWkfEndSts",
			type : 'post',
			dataType : 'json',
			data : {
				cusNo : cusNo
			},
			success : function(data) {
				top.LoadingAnimate.stop();
				var wkfAppId = data.wkfAppId;
				if(data.status == "0") {  //流程未结束
					top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId, '授信申请历史信息', function() {});
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
								alert(top.getMessage("NO_CREDIT_MODEL") , 0);
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
													alert(top.getMessage("CONFIRM_FIN_ADJUST"), 2,
															function() { // 调整 确定
																var url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=0";
																top.openBigForm(url, "调整授信申请", false, "80", "80");
															},
															function() { // 新增 取消
																var url =webPath+"/mfCusCreditApply/creditInitInput?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
                                                               // var url =webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
                                                                top.createShowDialog(url, "新增授信");
															});
												} else {
													// 新增
													var url = webPath+"/mfCusCreditApply/creditInitInput?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
													// top.openBigForm(url, "新增授信", false, "80", "80");
                                                    top.createShowDialog(url, "新增授信");

                                                }
											},
											error : function() {
												alert(top.getMessage("NO_CREDIT_MODEL"), 0);
											}
										});
							}
						},
						error : function(data) {
							top.LoadingAnimate.stop();
							alert(top.getMessage("ERROR_DATA_CREDIT","客户基本信息、财务报表信息和评级"), 0);
						}
					});
				}
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(top.getMessage("ERROR_DATA_CREDIT","授信流程"), 0);
			}
		});
	};
	
	//贷后检查
	var _loanAfterExamine = function (url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url,"贷后检查");
	};
	
	//验证文档是否上传
	var _valiDocIsUp = function (relNo){
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
	};
	
	//获取流程节点信息
	var _getTaskInfo = function(wkfAppId,appId,appSts){
		$.ajax({
			type:"post",
			async:false,
			url:webPath+"mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId,
			success:function(data){
				var url = data.url;
				var title=data.title;
				var nodeName = data.nodeName;
				var applyHtmlMake = '下一业务步骤：<a href="javascript:void(0)" class=\'font_size_20\' onclick="toNextBusPoint(\''+url+'\',\''+title+'\',\''+nodeName+'\',\''+appId+'\',\''+wkfAppId+'\')">'+title+'</a>';
				if(appSts == '2'){
					 applyHtmlMake = '<span>申请已提交，正在审批中</span></div>';
				}else if(appSts == '4'){
					 applyHtmlMake = '<span>申请已审批通过，请在合同视角中查看信息</span></div>';
				}else if(appSts == '5'){
					 applyHtmlMake = '<span>申请已被否决，业务结束</span></div>';
				}
				$("#"+appId).find(".forth").empty();
				$("#"+appId).find(".forth").append(applyHtmlMake);
			}
		});
	};
	
	//回调函数
	var _wkfCallBack = function(){
		searchList.getTaskInfo(top.wkfAppId,top.appId,top.appSts);
	};
	
	return{
		updateCusFormStas:_updateCusFormStas,
		getAppAuth:_getAppAuth,
		loanAfterExamine:_loanAfterExamine,
		valiDocIsUp:_valiDocIsUp,
		wkfCallBack:_wkfCallBack,
		getTaskInfo:_getTaskInfo
	};
}(window,jQuery);