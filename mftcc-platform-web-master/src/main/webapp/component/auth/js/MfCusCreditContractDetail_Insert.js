;
MfCusCreditContractDetail_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=agenciesUid]").popupSelection({// 中汇鑫德，资金机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfBusTrench/getTrenchData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=agenciesName]"));
				$("input[name=agenciesName]").val($("input[name=agenciesUid]").parents("td").find(".pops-value").html());
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
				url : webPath + url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.pactDetailFlag=true;
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
				$("input[name=creditSum]").val(authBalTmp);
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"资金机构的合作额度","timeTwo":"授信余额"}), 0);
				return;
			}
		}
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
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.pactDetailFlag=true;
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
	return{
		init:_init,
		ajaxInsert:_ajaxInsert,
		checkAgenciesCreditAmt:_checkAgenciesCreditAmt,
		submitPactDataRegular:_submitPactDataRegular,
		submitPactDataUpload:_submitPactDataUpload,
		ajaxUpdate:_ajaxUpdate,
		initPactDataUpload:_initPactDataUpload,
		submitPactDataRegularAjax:_submitPactDataRegularAjax
	}
}(window,jQuery);