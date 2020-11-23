;
var inputDisagreeBuss=function(window,$){
	
	var _init=function(){
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		if(busFlag=="disagreeFinc"){
			$("textarea[name=disagreeReason]").parents("tr").find("label").html("<font color='#FF0000'>*</font>拒绝原因");
			$("input[name=disagreeOpName]").parents("tr").find("label").html("拒绝人");
			$("input[name=disagreeDate]").parents("tr").find("label").html("拒绝时间");
		}
	};
	//打开登记终止信息表单
	var _inputDisagree=function(){
		top.stageFlag="";
		top.window.openBigForm(webPath+"/mfBusApply/inputDisagreeBuss?appId="+appId+"&pactId="+pactId+"&fincId="+fincIdTmp+"&busEntrance="+busEntrance+"&busFlag=disagreeBuss", '终止业务', function(){
			if(top.disagreeFlag){
				$("#MfBusApply_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
				$("#MfBusApply_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
				getNextBusPoint();
				$("#wj-modeler1").empty();
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				if(busEntrance=="apply"){//业务详情
					pubApplyDetailInfo.init();
				}
				if(busEntrance=="pact"){//合同详情
					$("#MfBusPact_DynaDetail_disagree_button").attr("disabled", true);// 终止业务
					$("#MfBusPact_DynaDetail_disagree_button").attr("class", "btn btn-opt-dont");// 终止业务
					if(pactId!=""&&pactId!=null){
						pubPactDetailInfo.init();
					}
					if(fincId!=""&&fincId!=null){
						putoutHisList.init();
					}
					//业务阶段处于放款审批中，且当前登录操作员存在该任务.处理任务数量
					if(top.stageFlag=="fincApprove"){
						var taskCount = parent.$("#task_count").text();
						if(taskCount!=""&&taskCount!=null){
							var curCount = parent.$("#count_input").val();
							curCount = curCount*1-1;
							if(curCount>=0 && curCount<100){
								if(curCount==0){
									parent.$("#task_count").text("");
								}else{
									parent.$("#task_count").text(curCount);
								}
							}
                            if(curCount<0){
                                curCount=0;
                                parent.$("#task_count").text("");
                            }
							parent.$("#count_input").val(curCount);
						}
					}
				}
			}
		});
	};
	
	//跳转拒绝放款表单
	var _inputDisagreeFinc=function(){
		top.disagreeFlag=false;
		top.window.openBigForm(webPath+"/mfBusApply/inputDisagreeBuss?appId="+appId+"&pactId="+pactId+"&fincId="+fincIdTmp+"&busFlag=disagreeFinc", '拒绝放款', function(){
			if(top.disagreeFlag){
				$("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", true);// 拒绝业务
				$("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt-dont");// 拒绝业务
				getNextBusPoint();
				$("#wj-modeler1").empty();
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				if(pactId!=""&&pactId!=null){
					pubPactDetailInfo.init();
				}
				if(fincId!=""&&fincId!=null){
					putoutHisList.init();
					
				}
			}
		});
	};
	//跳转拒绝放款表单
	var _inputDisagreeFincList=function(obj,url){
		top.disagreeFlag=false;
		top.window.openBigForm(webPath + url +"&busFlag=disagreeFinc", '拒绝放款', function(){
			if(top.disagreeFlag){
				$("#MfBusPact_DynaDetail_disagree2_button").attr("disabled", true);// 拒绝业务
				$("#MfBusPact_DynaDetail_disagree2_button").attr("class", "btn btn-opt-dont");// 拒绝业务
				getNextBusPoint();
				$("#wj-modeler1").empty();
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				if(pactId!=""&&pactId!=null){
					pubPactDetailInfo.init();
				}
                putoutHisList.init();
			}
		});
	};


    //跳转拒绝本次展期表单
    var _extensionDisagreeBuss = function () {

        if ($("#extensionDisagreeButton").parent("li").attr("disabled") == "disabled") {
            return;
        }

        top.stageFlag = "";
        top.data = "";
        top.window.openBigForm(webPath + "/mfBusExtensionApply/inputDisagreeBussExtension?appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&extensionApplyId=" + extensionApplyId + "&busFlag=disagreeBussExtension", '终止本次展期', function () {
            if (top.disagreeFlag) {

                if ($("#extensionDisagreeButton").hasClass("more-li-div")) {

                    $("#extensionDisagreeButton").parent("li").attr("disabled", true);
                    $("#extensionDisagreeButton").parent("li").removeClass("btn-opt");
                    $("#extensionDisagreeButton").parent("li").addClass("btn-opt-dont");
                    $("#extensionDisagreeButton").parent("li").css("cursor", "default");

                } else {
                    //先把展期申请按钮处理为可以进行使用
                    $("#extensionDisagreeButton").attr("disabled", true);
                    $("#extensionDisagreeButton").removeClass("btn-opt");
                    $("#extensionDisagreeButton").addClass("btn-opt-dont");
                }


                var busExtensionApply = top.data.busExtensionApply;
                wkfAppId = busExtensionApply.wkfAppId;
                $("#exten-wj-modeler").empty();
                showWkfFlow($("#exten-wj-modeler"), busExtensionApply.wkfAppId);
                MfBusExtensionCommon.getNextBusPoint(busExtensionApply.wkfAppId);
                MfBusExtensionCommon.showExtenWkfFlowInfo();

                $("#showExtenWkf").removeClass("show");
                $("#showExtenWkf").addClass("hide");

                $(".bg-danger").removeClass("show");
                $(".bg-danger").addClass("hide");
                pubExtenAppDetail.init();
                //借据展期否决之后，正常还款和提前还款可以操作
                $("#repay").attr("disabled", false);
                $("#repay").removeClass("btn-opt-dont");
                $("#repay").addClass("btn-opt");
                $("#prerepay").attr("disabled", false);
                $("#prerepay").removeClass("btn-opt-dont");
                $("#prerepay").addClass("btn-opt");
                $("#extensionApply").attr("disabled", false);
                $("#extensionApply").removeClass("btn-opt-dont");
                $("#extensionApply").addClass("btn-opt");
                if (operateflag == "1" || canRepay == "0") {//还款操作不能点击
                    $("#repay").attr("disabled", true);
                    $("#repay").removeClass("btn-opt");
                    $("#repay").addClass("btn-opt-dont");
                    $("#prerepay").attr("disabled", true);
                    $("#prerepay").removeClass("btn-opt");
                    $("#prerepay").addClass("btn-opt-dont");
                } else {
                    $("#repay").attr("disabled", false);
                    $("#repay").removeClass("btn-opt-dont");
                    $("#repay").addClass("btn-opt");
                    $("#prerepay").attr("disabled", false);
                    $("#prerepay").removeClass("btn-opt-dont");
                    $("#prerepay").addClass("btn-opt");
                }
                if (fincSts == "5") {//还款操作能点击
                    $("#repay").attr("disabled", false);
                    $("#repay").removeClass("btn-opt-dont");
                    $("#repay").addClass("btn-opt");
                    $("#prerepay").attr("disabled", false);
                    $("#prerepay").removeClass("btn-opt-dont");
                    $("#prerepay").addClass("btn-opt");

                }
                if (fincSts == "7") {//业务完结,贷后检查不可操作

                    $("#repay").attr("disabled", true);
                    $("#repay").addClass("btn-opt-dont");
                    $("#repay").removeClass("btn-opt");

                }
                if (canRepay == "0") {//还款操作不能点击
                    $("#repay").attr("disabled", true);
                    $("#repay").removeClass("btn-opt");
                    $("#repay").addClass("btn-opt-dont");

                    $("#prerepay").attr("disabled", true);
                    $("#prerepay").removeClass("btn-opt");
                    $("#prerepay").addClass("btn-opt-dont");

                }

            }
        });
    }

    //业务验证规则
    var valiAmtRules = function(dataParam){
        var ruleFlag = true;
        $.ajax({
            url : webPath+"/mfBusApply/valiAmtRulesAjax",
            type : "post",
            async: false,
            data : {ajaxData: dataParam},
            dataType : "json",
            success : function(data) {
                if (data.flag == "error") {// 存在业务拒绝
                    window.top.alert("该业务退费金额小于缴款金额", 0);
                    /*alert(top.getMessage("该业务退费金额小于缴款金额"), 2, function() {
                    },function(){
                    });*/
                    ruleFlag = false;
                }else {
                }
            },
            error : function() {
            }
        });
        return ruleFlag;
    };

	//终止信息保存
	var _insertDisagreeInfo=function(obj){

        var dataParam = JSON.stringify($(obj).serializeArray());

        if(typeof(busEntrance) != "undefined" && "apply" == busEntrance){

        }else {
            if(!valiAmtRules(dataParam)){
            }
        }
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var title = "终止业务";
				if(busFlag=="disagreeFinc"){
					title = "拒绝放款";
				}
                if (busFlag == "disagreeBussExtension") {
                    title = "终止本次展期";
                }
				alert(top.getMessage("CONFIRM_OPERATION", title), 2, function() {
					var url=webPath+"/mfBusApply/insertDisagreeInfoAjax";
					if(busFlag=="disagreeFinc"){
						url=webPath+"/mfBusFincApp/disagreeFincAjax";
					}
                    if (busFlag == "disagreeBussExtension") {
                        url = webPath + "/mfBusExtensionApply/disagreeExtensionAjax";
                    }
					$.ajax({
						url:url,
						data:{ajaxData : dataParam},
						success:function(data){
							if(data.flag=="success"){
								top.disagreeFlag=true;
								top.stageFlag=data.stageFlag;
                                if (top.stageFlag == "extension") {
                                    top.data = data;
                                }
							}else if(data.flag=="error"){
								window.top.alert(data.msg,0);
							}
							myclose_click();
						},
						error:function(data){
							alert(top.getMessage("FAILED_SAVE"), 0);
						}
					});
				});
			};
	};

    //打开登记提前解约表单
    var _inputPactPreEnd=function(){
        top.endFlag=false;
        top.window.openBigForm(webPath+"/mfBusPact/inputPactPreEnd?appId="+appId+"&pactId="+pactId, '提前解约', function(){
            if(top.endFlag){
                window.location.href = webPath+"/mfBusPact/getListPage";
            }
        });
    };
    var _insertPactPreEnd = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            alert(top.getMessage("CONFIRM_OPERATION", "提前解约"), 2, function() {
                var dataParam = JSON.stringify($(obj).serializeArray());
                var url = $(obj).attr("action");
                $.ajax({
                    url:url,
                    data:{ajaxData : dataParam},
                    success:function(data){
                        if(data.flag=="success"){
                            top.endFlag=true;
                        }else if(data.flag=="error"){
                            window.top.alert(data.msg,0);
                        }
                        myclose_click();
                    },error:function(data){
                        alert(top.getMessage("FAILED_OPERATION","解约"), 0);
                    }
                });
            });
        };
    };
	return{
		init:_init,
		inputDisagree:_inputDisagree,
		insertDisagreeInfo:_insertDisagreeInfo,
		inputDisagreeFinc:_inputDisagreeFinc,
        inputPactPreEnd:_inputPactPreEnd,
        insertPactPreEnd:_insertPactPreEnd,
        inputDisagreeFincList: _inputDisagreeFincList,
        extensionDisagreeBuss: _extensionDisagreeBuss
	};
}(window,jQuery);