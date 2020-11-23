;
var MfBusExtensionCommon=function(window, $){
	//发起展期申请
	var _ExtensionApply=function(){
		//该借据不符合发起展期申请的条件或正在展期未结束的业务，展期申请按钮置灰不可编辑
	 	if(top.conditFlag=="0"||top.existFlag=="1"){
			return;
		}
		top.saveFlag=false;
		top.wkfAppId="";
		top.window.openBigForm(webPath+"/mfBusExtensionApply/input?fincId="+fincId,'展期申请',function(){
			//展期申请保存后，回显展期业务流程图和展期申请信息
			if(top.saveFlag){
				_showExtenWkfFlowInfo();
				$("#ExtenAppDetail-div").show();
			}
		});
	};
	var _showExtenWkfFlowInfo=function(){
		$.ajax({
			url : webPath+"/mfBusExtensionApply/getExtensionInfoByFincIdAjax?fincId="+fincId,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				//该借据存在正在展期未结束的业务
				if(data.existFlag=="1"){
					var extensionApply=data.extensionApply;
					wkfAppId=extensionApply.wkfAppId;
                    extensionApplyId = extensionApply.extensionApplyId;
                    var extenBusStage = extensionApply.extenBusStage;
					$("#exten-wj-modeler").empty();
					showWkfFlow($("#exten-wj-modeler"),extensionApply.wkfAppId);
					_getNextBusPoint(extensionApply.wkfAppId);
					pubExtenAppDetail.init();
					//借据展期中，正常还款和提前还款不能操作
					$("#repay").attr("disabled",true);
					$("#repay").removeClass("btn-opt");
					$("#repay").addClass("btn-opt-dont");
					$("#prerepay").attr("disabled",true);
					$("#prerepay").removeClass("btn-opt");
					$("#prerepay").addClass("btn-opt-dont");
					$("#extensionApply").attr("disabled",true);
					$("#extensionApply").removeClass("btn-opt");
					$("#extensionApply").addClass("btn-opt-dont");
                    //不在审批中,终止本次展期业务可以进行点击
                    if (extenBusStage != "4") {
                        if ($("#extensionDisagreeButton").hasClass("more-li-div")) {
                            $("#extensionDisagreeButton").parent("li").attr("disabled", false);
                            $("#extensionDisagreeButton").parent("li").removeClass("btn-opt-dont");
                            $("#extensionDisagreeButton").parent("li").addClass("btn-opt");
                            $("#extensionDisagreeButton").parent("li").css("cursor", "pointer");
                        } else {
                            //先把展期申请按钮处理为可以进行使用
                            $("#extensionDisagreeButton").attr("disabled", false);
                            $("#extensionDisagreeButton").removeClass("btn-opt-dont");
                            $("#extensionDisagreeButton").addClass("btn-opt");
                        }
                    } else {
                        if ($("#extensionDisagreeButton").hasClass("more-li-div")) {
                            $("#extensionDisagreeButton").parent("li").attr("disabled", true);
                            $("#extensionDisagreeButton").parent("li").removeClass("btn-opt");
                            $("#extensionDisagreeButton").parent("li").addClass("btn-opt-dont");
                            $("#extensionDisagreeButton").parent("li").css("cursor", "default");
                        } else {
                            $("#extensionDisagreeButton").attr("disabled", true);
                            $("#extensionDisagreeButton").removeClass("btn-opt");
                            $("#extensionDisagreeButton").addClass("btn-opt-dont");
                        }
                    }
				}else{
					$("#showExtenWkf").removeClass("show");
					$("#showExtenWkf").addClass("hide");
					$(".bg-danger").removeClass("show");
					$(".bg-danger").addClass("hide");
					$("#ExtenAppDetail-div").hide();
                    if ($("#extensionDisagreeButton").hasClass("more-li-div")) {
                        $("#extensionDisagreeButton").parent("li").attr("disabled", true);
                        $("#extensionDisagreeButton").parent("li").removeClass("btn-opt");
                        $("#extensionDisagreeButton").parent("li").addClass("btn-opt-dont");
                        $("#extensionDisagreeButton").parent("li").css("cursor", "default");
                    } else {
                        $("#extensionDisagreeButton").attr("disabled", true);
                        $("#extensionDisagreeButton").removeClass("btn-opt");
                        $("#extensionDisagreeButton").addClass("btn-opt-dont");
                    }
                    if(busModel!=undefined&&busModel=='12'){

					}else {
                        pubFincAppDetail.init();
					}
					pubMfRepayPlanList.init();
				}
                //  展期前利息收取按钮是否能够操作 0能收取 1不能收取
                if(data.extenLiXiOperateFlag == "1"){// 不能操作
                    $("#LiXiRepay").attr("disabled",true);
                    $("#LiXiRepay").removeClass("btn-opt");
                    $("#LiXiRepay").addClass("btn-opt-dont");
                }else{
                    //展期前利息收取按钮 按钮相关控制 LiXiRepay 能操作
                    $("#LiXiRepay").attr("disabled",false);
                    $("#LiXiRepay").removeClass("btn-opt-dont");
                    $("#LiXiRepay").addClass("btn-opt");
                }
				top.existFlag=data.existFlag;
				//该借据不符合发起展期申请的条件，展期申请按钮置灰不可编辑
				top.conditFlag=data.conditFlag;
				top.extensionTerm = data.extensionTerm;//需要获得剩余期限
				top.extensionMsg = data.extensionMsg;//展期申请提示信息
				//控制展期申请按钮的可用与否
                if ($("#extensionApplyButton").hasClass("more-li-div")) {
                    $("#extensionApplyButton").parent("li").attr("disabled", false);
                    $("#extensionApplyButton").parent("li").removeClass("btn-opt-dont");
                    $("#extensionApplyButton").parent("li").addClass("btn-opt");
                    $("#extensionApplyButton").parent("li").css("cursor", "pointer");

                } else {
                    //先把展期申请按钮处理为可以进行使用
                    $("#extensionApplyButton").attr("disabled", false);
                    $("#extensionApplyButton").removeClass("btn-opt-dont");
                    $("#extensionApplyButton").addClass("btn-opt");
                }


				if($("#extensionApplyButton").hasClass("more-li-div")){
					if(top.conditFlag=="0"||top.existFlag=="1" || pactSts == "7"){
                        /*$("#extensionApplyButton").parent("li.btn-opt").removeAttr("onclick");
                        $("#extensionApplyButton").css("color","#989FA0");
                        $("#extensionApplyButton").parent("li.btn-opt").css("cursor","default");*/

                        $("#extensionApplyButton").parent("li").attr("disabled", true);
                        $("#extensionApplyButton").parent("li").removeClass("btn-opt");
                        $("#extensionApplyButton").parent("li").addClass("btn-opt-dont");
                        $("#extensionApplyButton").parent("li").css("cursor", "default");
					}
				}else{
					if(top.conditFlag=="0"||top.existFlag=="1" || pactSts == "7"){
						$("#extensionApplyButton").attr("disabled",true);
						$("#extensionApplyButton").attr("class", "btn btn-opt-dont");
					}
				}
                pub_extension_list.init(fincId, 'tablepubviewExtensionListBase');
			}
		});
	};
	//获得下个节点提示
	var _getNextBusPoint=function(wkfAppId){
        $(".block-next").empty();
        $(".block-next").unbind();
        $(".next-div").unbind();
		$.ajax({
			url : webPath+"/mfBusExtensionApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var wkfFlag = data.wkfFlag;
				var extenAppSts = data.extenAppSts;
				if (wkfFlag == '0') {
					$(".bg-danger").removeClass("show");
					$(".bg-danger").addClass("hide");
				} else if (wkfFlag == '1'&& extenAppSts!="5") {
					var nodeName = data.nodeName;
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
					if(checkPmsBizFlag){
						var url = data.url;
						var title = data.title;
						$(".block-next").empty();
						$(".next-div").unbind();
						if (data.extenAppSts == "1" || data.extenAppSts == "3"|| data.extenAppSts == "4") { // 审批环节
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
							$("#showExtenWkf").removeClass("hide");
							$("#showExtenWkf").addClass("show");
						} else {
							$(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
							$("#showExtenWkf").removeClass("hide");
							$("#showExtenWkf").addClass("show");
							$(".next-div").click(function() {
								_toNextBusPoint(url, title, nodeName);
							});
						}
					}else{
						$(".block-next").append("<span>暂时没有权限操作该节点</span>");
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
						$("#showExtenWkf").removeClass("hide");
						$("#showExtenWkf").addClass("show");
					}
				}
			}
		});
	};
	var _toNextBusPoint=function(url, title, nodeName){
		url = webPath + url;
		var tmpUrl = url.split("&")[0];
		var popFlag = tmpUrl.split("?")[1].split("=")[1];
		top.extenFlag=false;
		top.addCollateral=false;
		top.skipFlag="0";//跳过标识 0不跳过1跳过
		if (nodeName == "extension_pledge") { // 担保信息
			url=url+"&appId="+appId;
			top.window.openBigForm(url, title, _goToCollaDetailInfo);
		}else{
			top.window.openBigForm(url, title, _wkfCallBack);
		}
	};
	var _wkfCallBack=function(){
		if(top.extenFlag){
			_showExtenWkfFlowInfo();
		}
		//
		
	};
	//获取押品信息
	var _goToCollaDetailInfo=function(){
		if(top.addCollateral){
			//原业务押品和展期业务建立关联关系,并提交下一节点
			$.ajax({
				url : webPath+"/mfBusExtensionApply/setExtensionPledgeRelationAjax",
				data : {
					wkfAppId : wkfAppId,
					skipFlag:top.skipFlag,
					pledgeId:top.pledgeId
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					//担保登记跳过，不再跳转担保详情页面
					if(top.skipFlag=="0"){
						if(data.flag="success"){
							top.LoadingAnimate.start();
                            window.location.href = webPath + "/mfBusCollateralRel/getCollateralInfo?busEntrance=3&cusNo=" + cusNo + "&appId=" + appId + "&relId=" + appId + "&fincId=" + fincId + "&entrance=" + entrance + "&extensionApplyId=" + extensionApplyId;
						}
					}else{
						_showExtenWkfFlowInfo();
					}
				},
				error : function() {
					alert(data.msg, 0);
				}
			});
		}
	};
	return{
		ExtensionApply:_ExtensionApply,
		toNextBusPoint:_toNextBusPoint,
        showExtenWkfFlowInfo: _showExtenWkfFlowInfo,
        getNextBusPoint: _getNextBusPoint
	};
}(window, jQuery);