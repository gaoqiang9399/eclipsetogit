;
var MfCusCredit_cus = function(window,$){
	var _init = function(){
		_showProjectCreditButton();
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
//更新头部授信信息按钮样式
    var _getCreditSts = function(creditModel,status,creditSum,applySum){
        //授信业务模式1客户授信2项目授信
        if(creditModel=="1"){
            $(".creditBus").parent().removeClass("btn-lightgray");
            $(".creditBus").parent().removeClass("btn-dodgerblue");
            $(".creditBus").parent().addClass("cus-middle");
            if(status == "0"||status == "1"||status == "2"||status == "3"){//申请、审批中、审批完成
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
                $(".creditBus").html("已失效");
            }
            if(status == "8"){ //已终止
                $(".creditBus").parent().removeClass("cus-middle");
                $(".creditBus").parent().addClass("btn-danger");
                $(".creditBus").html("已终止");
            }
        }
        //授信业务模式1客户授信2项目授信
        if(creditModel=="2"){
            $(".projectCredit-span").parent().removeClass("btn-lightgray");
            $(".projectCredit-span").parent().removeClass("btn-dodgerblue");
            $(".projectCredit-span").parent().addClass("cus-middle");
            if(status == "0"||status == "1"||status == "2"||status == "3"){//申请、审批中、审批完成
                $(".projectCredit-span").html("立项中");
            }
            if(status == "5"){ //已签约
                $(".projectCredit-span").parent().removeClass("cus-middle");
                $(".projectCredit-span").parent().addClass("btn-dodgerblue");
                $(".projectCredit-span").html(applySum+" 万");
            }
            if(status == "6"){ //已冻结
                $(".projectCredit-span").parent().removeClass("cus-middle");
                $(".projectCredit-span").parent().addClass("btn-danger");
                $(".projectCredit-span").html(applySum+" 万");
            }
            if(status == "7"){ //已失效
                $(".projectCredit-span").parent().removeClass("cus-middle");
                $(".projectCredit-span").parent().addClass("btn-lightgray");
                $(".projectCredit-span").html("已失效");
            }
            if(status == "8"){ //已终止
                $(".projectCredit-span").parent().removeClass("cus-middle");
                $(".projectCredit-span").parent().addClass("btn-danger");
                $(".projectCredit-span").html("已终止");
            }
        }

    };
	var _showProjectCreditButton = function(){
		$.ajax({
			url : webPath+"/mfCusCreditApply/getCusCreditDataAjax",
			type : 'post',
			dataType : 'json',
			data : {
				cusNo : cusNo
			},
			success : function(data) {
                //项目授信
                var projectCreditData = data.projectCreditData;
                var taskFlag,status,creditModel,wkfProjectAppId,projectCreditUseHis;

                if(typeof (projectCreditData) != "undefined"){
                    var flag = projectCreditData.flag;
                    if(flag == "success"){
                        taskFlag = projectCreditData.taskFlag;
                        if(taskFlag =="applying" || taskFlag =="finish") {//业务处理中
                            status = projectCreditData.creditSts;
                            creditModel = projectCreditData.creditModel;//授信业务模式1客户授信2项目授信
                            wkfProjectAppId = projectCreditData.wkfAppId;
                            projectCreditUseHis = projectCreditData.projectCreditUseHis;
                            _getCreditSts(creditModel, status, null, projectCreditUseHis.applySum);
                            $("#projectCredit-button").attr("onclick", "MfCusCredit_cus.getCreditHisDataInfo('2','" + wkfProjectAppId + "')");
                        }
                    }
                }
                //客户授信
                var cusCreditData = data.cusCreditData;
                if(typeof (cusCreditData) != "undefined"){
                    if(cusCreditData.flag == "success"){
                        taskFlag = cusCreditData.taskFlag;
                        if(taskFlag =="applying" || taskFlag =="finish") {//业务处理中
                            status = cusCreditData.creditSts;
                            creditModel = cusCreditData.creditModel;//授信业务模式1客户授信2项目授信
                            projectCreditUseHis = cusCreditData.cusCreditUseHis;
                            wkfProjectAppId = cusCreditData.wkfAppId;
                            _getCreditSts(creditModel, status, null, projectCreditUseHis.applySum);
                            $("#cusCredit-button").attr("onclick", "MfCusCredit_cus.getCreditHisDataInfo('1','" + wkfProjectAppId + "')");
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

	return{
		init:_init,
		getCreditHisDataInfo:_getCreditHisDataInfo,
        getCreditIntentionHisDataInfo:_getCreditIntentionHisDataInfo,
	};
}(window,jQuery);