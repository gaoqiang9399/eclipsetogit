;
MfCusCreditChildContract_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=agenciesUid]").popupSelection({//资金机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfBusTrench/getTrenchData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=agenciesName]"));
                $("input[name=agenciesName]").val($("input[name=agenciesUid]").parents("td").find(".pops-value").html());
				var agenciesUid = $("input[name=agenciesUid]").val();
				var creditAppId = $("input[name=creditAppId]").val();
                $.ajax({
                    url : "/mfCusCreditChildContract/getCusNoFundProjectPactInfo",
                    data : {cusNoFund:agenciesUid,creditAppId:creditAppId},
                    type : "post",
                    dataType : "json",
                    success : function(data) {
                        //LoadingAnimate.stop();
                        if (data.flag == "success") {
							var mfCusCreditChildContract = data.mfCusCreditChildContract;
                            $("input[name=agenciesBal]").val(mfCusCreditChildContract.authBal);
                        }
                    },
                    error : function(data) {
                        //loadingAnimate.stop();
                        window.top.alert(top.getMessage("ERROR_INSERT"), 0);
                    }
                });
			},
		});
	};
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId,creditType:creditType},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						window.top.alert(data.msg, 1);
						myclose_click();
					} else if (data.flag == "error"){
						window.top.alert(data.msg, 0);
						myclose_click();
					}else if (data.flag == "overAmt"){
						window.top.alert(data.msg, 3);
					}else if (data.flag == "finish") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						myclose_click();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	var _checkAgenciesCreditAmt=function(){
		//授信合同余额
		var authBalTmp = $("input[name=authBal]").val();
		var authBal = Number(authBalTmp.replace(/,/g,""));
		//资金机构合作额度
		var creditSum = Number($("input[name=creditSum]").val().replace(/,/g,""));
		if(authBal>0&&creditSum>0){
			if(authBal < creditSum){
				$("input[mame=creditSum]").val(authBalTmp);
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"资金机构的合作额度","timeTwo":"授信余额"}), 0);
				return;
			}
		}
		//_checkCusNoFundAdjustAmtAjax();
	}

    var _checkAddAmt=function(){
        //变更的资金机构合作额度
        var addAmt = $("input[name=addAmt]").val();
        //项目追加的额度 正数表示增加 负数表示减少
        var addBal = $("input[name=addAmtShow]").val();
        //资金机构合作余额
        var agenciesBal = $("input[name=agenciesBal]").val();
        addBal = Number(addBal.replace(/,/g,""));
        agenciesBal = Number(agenciesBal.replace(/,/g,""));
        addAmt = Number(addAmt.replace(/,/g,""));
        //增加额度
        if(addAmt>0&&agenciesBal>0){
            if(addBal < addAmt){
                $("input[mame=addAmt]").val($("input[name=addBal]").val());
                window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"增加的合作额度","timeTwo":"项目追加余额"}), 0);
                return;
            }
        }else if(addAmt<0&&agenciesBal>0){//减少额度
        	var balTmp = addAmt + agenciesBal;
        	if(balTmp<0){
                $("input[mame=addAmt]").val($("input[name=agenciesBal]").val());
                window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"减少的合作额度","timeTwo":"合作余额"}), 0);
                return;
			}
            if(addAmt<addBal){
                $("input[mame=addAmt]").val($("input[name=agenciesBal]").val());
                window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"减少的合作额度","timeTwo":"项目减少的额度"}), 0);
                return;
            }
		}
        //_checkCusNoFundAdjustAmtAjax();
    }

	var _initPactDataUpload=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//法务上传资料提交
	var _submitPactDataUpload = function(){
		$.ajax({
			url: webPath+"/mfCusCreditApply/doCommitWkf",
			type:"post",
			dataType:"json",
			data:{
				wkfAppId:wkfAppId,
			},
			error:function(){
				alert('提交到下一个节点时发生异常', 0);
			},
			success:function(data){
				if(data.flag == "success"){
					top.creditFlag=true;
					myclose_click();
				}
			}
		});
	};
	//法务上传资料提交
	var _submitPactDataRegular = function(){
		_submitPactDataUpload();
	};
	
	//保存方法
	var _ajaxUpdate = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId,creditType:creditType},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						window.top.alert(data.msg, 1);
						myclose_click();
					} else if (data.flag == "error"){
						window.top.alert(data.msg, 0);
						myclose_click();
					}else if (data.flag == "overAmt"){
						window.top.alert(data.msg, 3);
					}else if (data.flag == "finish") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						myclose_click();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	
	//保存方法
	var _submitPactDataRegularAjax = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						window.top.alert(data.msg, 1);
						myclose_click();
					} else if (data.flag == "error"){
						window.top.alert(data.msg, 0);
						myclose_click();
					}else if (data.flag == "overAmt"){
						window.top.alert(data.msg, 3);
					}else if (data.flag == "finish") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						myclose_click();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	
	var _checkCusNoFundAdjustAmtAjax = function(){
		var creditSum = $("input[name=creditSum]").val();
		creditSum = creditSum.replace(/,/g, "");
		var cusNoFund = $("input[name=agenciesUid]").val();
		var creditAppId = $("input[name=creditAppId]").val();
		$.ajax({
			url : webPath+"/mfCusCreditChildContract/checkCusNoFundAdjustAmtAjax",
			data : {creditAppId:creditAppId,cusNoFund:cusNoFund,adjustAmt:parseFloat(creditSum)},
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.checkBool == false) {
					$("input[name=creditSum]").val(null);
					window.top.alert(data.msg, 0);
				}else{
					
				}
			},
			error : function(data) {
				window.top.alert(top.getMessage("ERROR_INSERT"), 0);
			}
		});
	};
	
	//追加合作资金机构保存
	var _saveAddChildPactInfoAjax = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,creditType:creditType},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.childPactFlag=true;
						top.htmlStr = data.htmlStr;
						window.top.alert(data.msg, 1);
						myclose_click();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	return{
		init:_init,
		ajaxInsert:_ajaxInsert,
		checkAgenciesCreditAmt:_checkAgenciesCreditAmt,
		submitPactDataRegular:_submitPactDataRegular,
		submitPactDataUpload:_submitPactDataUpload,
		ajaxUpdate:_ajaxUpdate,
		initPactDataUpload:_initPactDataUpload,
		submitPactDataRegularAjax:_submitPactDataRegularAjax,
		checkCusNoFundAdjustAmtAjax:_checkCusNoFundAdjustAmtAjax,
		saveAddChildPactInfoAjax:_saveAddChildPactInfoAjax,
        checkAddAmt:_checkAddAmt
	}
}(window,jQuery);