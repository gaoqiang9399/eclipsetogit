;
var cusEval = function(window,$){
	var _init = function(){
		/*if(typeof(cusLevelName)!=="undefined"){
			if(cusLevelName != null&&cusLevelName != ""){
				$("#cusEvalRating-span").text(cusLevelName);
				$("#cusEvalRating-button").attr("onclick","cusEval.getEvalDetailResult('1');");
				if(cusLevelName.indexOf("A") != -1){
					$("#cusEvalRating-button").attr("class","btn btn-view btn-forestgreen");
				}else if(cusLevelName.indexOf("B") != -1){
					$("#cusEvalRating-button").attr("class","btn btn-view cus-middle");
				}else if(cusLevelName.indexOf("C") != -1){
					$("#cusEvalRating-button").attr("class","btn btn-view btn-danger");
				}
			}else{
				$("#cusEvalRating-span").text("未评估");
				$("#cusEvalRating-button").attr("onclick","cusEval.getEvalDetailResult('0');");
				$("#cusEvalRating-button").attr("class","btn btn-view btn-lightgray");//灰的
			}
		}*/
		_initEval();
	};
	//初始化评级
	var _initEval = function(){
        var param = {};
        param.cusNo = cusNo;
        param.useType = "1";
        param.evalClass = "1";
        param.gradeType = "4";
        param.relNo = cusNo;
        param = JSON.stringify(param);
		$.ajax({
			url : webPath+"/appEval/getCurrAppEvalDataAjax",
			data : {
                param : param,
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					var appEval = data.appEval;
					// var evalStage = appEval.evalStage;
					// var evalAppNo = appEval.evalAppNo;
					if(appEval!=null){
                        var gradeType = appEval.gradeType;
                        var evalClass = appEval.evalClass;
                        var relNo = appEval.cusNo;
                        var cusLevelName = data.cusLevelName;
                        if(cusLevelName != null&&cusLevelName != ""){
                            $("#cusEvalRating-span").text(cusLevelName);
                            $("#cusEvalRating-button").attr("onclick","pubInitEvalInfo.getEvalDetailResult('1','"+relNo+"','"+gradeType+"','"+evalClass+"');");
                            if(cusLevelName.indexOf("A") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view btn-forestgreen");
                            }else if(cusLevelName.indexOf("B") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view cus-middle");
                            }else if(cusLevelName.indexOf("C") != -1  || cusLevelName.indexOf("D") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view btn-danger");
                            } else if(cusLevelName.indexOf("优秀") != -1 || cusLevelName.indexOf("较好") != -1 || cusLevelName.indexOf("良好") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view btn-forestgreen");
                            }else if(cusLevelName.indexOf("一般") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view cus-middle");
                            }else if(cusLevelName.indexOf("差") != -1){
                                $("#cusEvalRating-button").attr("class","btn btn-view btn-danger");
                            }
                        }else{
                            $("#cusEvalRating-span").text("未评估");
                            $("#cusEvalRating-button").attr("onclick","cusEval.getEvalDetailResult('0');");
                            $("#cusEvalRating-button").attr("class","btn btn-view btn-lightgray");//灰的
                        }
					}

					// /**评级申请流程展示**/
					// if(evalStage=="4"){//评级审批中
					// 	$("#evalApprovalHisModeler").empty();
					// 	showWkfFlowVertical($("#evalApprovalHisModeler"),evalAppNo,"2","eval_approval");
					// 	$("#evalApprovalHis-block").removeClass("hide");
					// 	$("#evalApprovalHis-block").addClass("show");
					// 	showWkfFlow($("#eval-wj-modeler2"),evalAppNo);
					// }
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", "/appEval/getCurrAppEvalDataAjax"));
			}
		});
        $.ajax({
            url : webPath+"/cusFinMain/queryCusFinDataAjax",
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
	};
	//发起评级时评分卡所需字段的模块必填
	var _getInitatEcalApp =function (relNo,gradeType,evalClass){
		$.ajax({
			url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
			success : function(data) {
				if (data.fullFlag == '1') {// 全部都填写了
					_getInitatEcalAppY("1",relNo,gradeType,evalClass);
				}
				else if (data.fullFlag == '0') {
					alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
				}
			}
		});
	}
	var _getInitatEcalAppY = function(useType,relNo,gradeType,evalClass){
		LoadingAnimate.start();
		var param = {};
        param.cusNo = cusNo;
        param.useType = "1";
        param.relNo = relNo;
        var showInfo = "评级";
		if(useType != null && useType != ""){
            switch(useType){
                case "1"://客户评级
                    param.gradeType = gradeType;
                    param.evalClass = evalClass;
                    break;
                case "2"://业务检查
                    param.appId = appId;
                    param.relNo = appId;
                    param.useType = "2";
                    param.gradeType = "2";
                    showInfo = "风险检查";
                    break;
                case "3"://授信检查
                    param.creditAppId = creditAppId;
                    param.relNo = creditAppId;
                    param.useType = "3";
                    param.gradeType = "3";
                    showInfo = "风险检查";
                    break;
                case "4"://额度测算
                    param.useType = "4";
                    showInfo = "授信测算";
                    break;
                default:
                    break;

            }
        }
        param = JSON.stringify(param);
		$.ajax({
			url : webPath+"/appEval/getUnfinishedAppEvalUseType",
			data : {
                param : param
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if (data.status == "0") {
					alert(data.msg, 0);
					return;
				}
				top.creditFlag = false;
				if (data.flag == "1") {//未提交
					top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&param=" + encodeURIComponent(param), showInfo + "申请", function() {
                        if(useType == "1") {
                            if(gradeType=='4' && evalClass=='2'){
                                pubInitEvalInfo.initEval(top.evalCusNo,useType,top.evalRelNo,gradeType,evalClass,top.evalSpanId,top.evalButtonId);
							}else {
                                _initEval();
							}
                        }
                        if(useType == "3" && top.creditFlag) {
                            $("[name='riskLevelName']").val(top.cusLevelName);
                        }

					});
				}else if(data.flag == "2"){//审批阶段查看详情
					top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=" +useType+"&evalAppNo="+ data.evalAppNo, showInfo + "信息", function() {});
				}else{//新增或审批完成
					var timeLimit = null;
					if(null!=data.timeLimit){
                        timeLimit = data.timeLimit;
					}

					if(typeof data.ifCreditEval != 'undefined' && data.ifCreditEval=='0'){
                        alert(data.warnMsg, 3);
					}else{
                        top.openBigForm(webPath+"/appEval/initiateApp?param="+ encodeURIComponent(param)+ "&timeLimit="+timeLimit, showInfo + "申请", function() {
                            if(useType == "1") {
                                if(gradeType=='4' && evalClass=='2'){
                                    pubInitEvalInfo.initEval(top.evalCusNo,useType,top.evalRelNo,gradeType,evalClass,top.evalSpanId,top.evalButtonId);
                                }else {
                                    _initEval();
                                }
                            }
                            if(useType == "3" && top.creditFlag) {
                                $("[name='riskLevelName']").val(top.cusLevelName);
                            }
                        });
                    }
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", ""));
			}
		});
	};
	
	/*外部评级,客户类型设定为2，让其避开企业上传财务报表*/
	var _getManEvalApp =function () {
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/appEval/getUnfinishedAppEval",
				data : {
					cusNo : cusNo,
					cusBaseType:'2'
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
						alert("有一个未提交的外部评级",1);
						//top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&cusNo=" + cusNo, "评级申请", function() {});
					}else if(data.flag == "2"){//审批阶段查看详情*/						
						top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=1", "评级信息", function() {});
					}else{//新增或审批完成
						var timeLimit = null;
						if(null!=data.timeLimit){
                     	   timeLimit = data.timeLimit;
						}
						
						top.openBigForm(webPath+"/appEval/initManEavl?cusNo="+ cusNo+ "&timeLimit="+timeLimit, "评级申请",function () {});
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		};
	
	
	//评级详情
	var _getEvalDetailResult = function(parm){
		if (parm == '1') {
			top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=4&useType=1", "评级信息", function() {});
		} else {
			return false;
		}
	};
	
	//判断评分卡所需信息模块是否完善
	var _checkCusInfoMustIsFull = function(){
		var params = {
				"cusNo" : cusNo
			};
		$.ajax({
			url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
			success : function(data) {
				if (data.fullFlag == '1') {// 全部都填写了
					
				}
				else if (data.fullFlag == '0') {
					alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
				}
			}
		});
	}
	
	return{
		init:_init,
		getInitatEcalApp:_getInitatEcalApp,
		getEvalDetailResult:_getEvalDetailResult,
        getInitatEcalAppY:_getInitatEcalAppY,
		getManEvalApp:_getManEvalApp
	};
}(window,jQuery);