;
var MfBusAgencies = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {		  myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfBusAgencies/findByPageAjax",//列表数据查询的url
		    	tableId:"table" + tableId,//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30//加载默认行数(不填为系统默认行数)
		    });
	};
	
	var _insertInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//关联业务员
	    var $trenchOpName=$("[name=agenciesOpName]");
	    if($trenchOpName!=null&&$trenchOpName!='undefined'){
	    	$trenchOpName.bind("click",function(){
	    		selectUserCustomTitleDialog("选择业务员",function(userInfo){
	    			var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
	    			$('[name=agenciesOpNo]').val(users);
	    			$('[name=agenciesOpName]').val(userInfo.brName);
	    		});
	    	})
	    }

        var $trenchBrName=$("[name=agenciesBrName]");
        if($trenchBrName!=null&&$trenchBrName!='undefined'){
        $trenchBrName.bind("click",function(){
                selectOrgCheckDialog(function(orgInfo){
                    $("input[name=agenciesBrName]").val(orgInfo.brName);
                    $("input[name=agenciesBrNo]").val(orgInfo.brNo);
                });
            })
        }

	    dealWithCusType("5");
	};
	var _updateInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		//渠道商关联业务员
	    var $OpName=$("[name=agenciesOpName]");
	    if($OpName!=null&&$OpName!='undefined'){
	    	$OpName.bind("click",function(){
	    		selectUserCustomTitleDialog("选择业务员?"+$("[name=agenciesOpNo]").val(),function(userInfo){
	    			var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
	    			$('[name=agenciesOpNo]').val(users);
	    			$('[name=agenciesOpName]').val(userInfo.brName);
	    		});
	    	})
	    }
	    //处理客户类型
	    dealWithCusType("5");
	    
	};
	// 选择产品编号
	var _selectKindNo = function(){
		var kindNo = $("[name='supportProducts']").val();
		kindNo = kindNo.replace(/;/g,"@")
		selectKindMutiDialog(_selectKindCallBack,kindNo);
	}
	// 选择产品回调
	var _selectKindCallBack = function(sysKind) {
		var kindNo = sysKind.kindNo;
		var kindName = sysKind.kindName;
		$("[name='supportProducts']").val(kindNo.replace(/@/g,";"));
		$("[name='supportProductsName']").val(kindName.replace(/@/g,";"));
	};
	
	//新增时,授信余额=授信总额
	var _changeCreditBal = function(){
		var creditAmt = $("[name='creditAmt']").val();
		$("[name='creditBal']").val(creditAmt);
	}
	
	var _creditTermOnBlur = function(obj){
		var creditTerm = Number(obj.value);
		var beginDate = $("input[name=creditBeginDate]").val();
		var endDate = "";
		if(creditTerm != "" && beginDate != ""){
			endDate = creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m");
		}else{
			$("input[name=creditEndDate]").val("");
		}
		$("input[name=creditEndDate]").val(endDate);
	};
	
	var _beginDateOnBlur = function(){
		var creditTerm = Number($("input[name=creditTerm]").val());
		//var beginDate = obj.value;
		var beginDate = $("input[name=creditBeginDate]").val();
		if(beginDate != ""){
			if(creditTerm != ""){
				$("input[name=creditEndDate]").val(creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m"));
			}else{
				$("input[name=creditEndDate]").val(beginDate);
			}
		}
	};
	
	
	//开始日期选择后，默认带出结束日期(可根据系统参数显示结束日期)
	var _updateApplyEndDate = function(){
		var termType = "1";
		var term = $("input[name=creditTerm]").val();
		var beginDate = $("input[name=creditBeginDate]").val();
		if(term == '' || beginDate == ''){
			$("input[name=creditEndDate]").val("");
			return;
		}
        $.ajax({
            url:webPath+"/mfBusPact/getPactEndDateInfoMapAjax",
            data:{"beginDate":beginDate,"term":term,"termType":termType},
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    var endDate=data.endDate;
                    $("input[name=creditEndDate]").val(endDate);
                    //选择开始日后，清除结束日中的不能为空提示
                    $("input[name=creditEndDate]").parent().find(".error.required").remove();
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    };
	
	var _getCreditUsedAmount = function(){		
		var creditUsedAmount;
		var creditAmt = $("input[name=creditAmt]").val().replace(/,/g,'');
		var creditBal = $("input[name=creditBal]").val().replace(/,/g,'');
		creditUsedAmount = CalcUtil.subtract(creditAmt,creditBal);
		$("input[name=creditUsedAmount]").val(CalcUtil.formatMoney(creditUsedAmount,2));
	};
	
	var _depositBondApp = function(){
		var agenciesUid = cusNo;
		var url = webPath+'/mfBusAgencies/inputDepositBond?agenciesUid='+agenciesUid;
		var agenciesId1 = agenciesId;
		window.parent.openBigForm(url, '新增存出保证金', function() {
			window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
		});
	}; 
	
	var _agenciesConfig = function() {
		var agenciesUid = cusNo;
		var url = webPath+'/mfBusAgencies/inputAgenciesConfig?agenciesUid='+agenciesUid;
		var agenciesId1 = agenciesId;
		window.parent.openBigForm(url, '新增资金机构配置', function() {
			window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
		});
	};
	
	var _getEnableBondAmount = function(){
		var enableBondAmount;
		var realDepositBond = $("input[name=realDepositBond]").val().replace(/,/g,'');
		var largeMulti = $("input[name=largeMulti]").val().replace(/,/g,'');
		enableBondAmount = CalcUtil.multiply(realDepositBond,largeMulti);
		$("input[name=enableBondAmount]").val(CalcUtil.formatMoney(enableBondAmount,2));
	};
	
	var _insertDetailInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//console.log($("input[name=bondInterest]").parent().parent().parent().html());
		//$("input[name=bondInterest]").parents("tr").hide();
		$("#formRowCenter").show();
		$("#formApprovalRowCenter").hide();
		$("#formCloseCenter").hide();
		
		dealWithAgenciesUid(agenciesUid,"input");
	};
	
	var _insertConfigInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("#formRowCenter").show();
		$("#formCloseCenter").hide();
		
		$('select[name=agenciesDetailName]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			//inline: false, //下拉模式
			//valueClass : "show-text",// 自定义显示值样式
			/*addBtn:{//添加扩展按钮
				"title":"新增",
				"fun":function(hiddenInput, elem){
						$(elem).popupSelection("hideSelect", elem);
						BASE.openDialogForSelect('新增资金机构','AGENCIES_BANK', elem);
					}
				},*/
			changeCallback : function (obj, elem) {
				$("input[name=agenciesDetailName]").val(obj.data("text"));
				$("input[name=agenciesDetailUid]").val(obj.val());				
			}
		});	
		
		var td = '<tr><td colspan="4" height="200" style="border:1px solid #fff"></td></tr>';
		$("#insertForm").find("table").find("tbody").append(td);
	};
	
	var _updateDetailInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		if(approvalStatus == '01'){//未提交
			$("#formRowCenter").show();
			$("#formApprovalRowCenter").hide();
			$("#formCloseCenter").hide();
		}else{
			$("#formRowCenter").hide();
			$("#formApprovalRowCenter").hide();
			$("#formCloseCenter").show();
		}
		
		dealWithAgenciesUid(agenciesUid,"update");
	};
	
	var _updateConfigInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		$("#formRowCenter").show();
		$("#formCloseCenter").hide();
		
		$('select[name=agenciesDetailName]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			/*addBtn:{//添加扩展按钮
				"title":"新增",
				"fun":function(hiddenInput, elem){
						$(elem).popupSelection("hideSelect", elem);
						BASE.openDialogForSelect('新增资金机构','AGENCIES_BANK', elem);
					}
				},*/
			changeCallback : function (obj, elem) {
				$("input[name=agenciesDetailName]").val(obj.data("text"));
				$("input[name=agenciesDetailUid]").val(obj.val());				
			}
		});
		
		$(".pops-value").empty();
		$(".pops-value").text(agenciesDetailName);
		$("input[name=agenciesDetailName]").val(agenciesDetailName);
		
		var td = '<tr><td colspan="4" height="200" style="border:1px solid #fff"></td></tr>';
		$("#insertForm").find("table").find("tbody").append(td);
	};
	
	var _updateReleaseDetailInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		if(approvalStatus == '03'){//通过
			$("#formRowCenter").show();
			$("#formCloseReleaseCenter").hide();
		}else{
			$("#formRowCenter").hide();
			$("#formCloseReleaseCenter").show();
		}
	};
	
	var _viewPointInit = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		if ($('select[name=opinionType]').length > 0) {
			$('select[name=opinionType]').popupSelection({
				searchOn : true, // 启用搜索
				inline : true, // 下拉模式
				multiple : false, // 单选
				changeCallback : WkfApprove.opinionTypeChange
			});
		}
	};
//可用保证金余额校验
	var _availableSpareAmt = function (obj) {
        var money = $(obj).val();
        var enableBondAmount = $("input[name ='enableBondAmount']").val();
        // 可用保证金余额 > 可用保证金
        if(parseFloat(money.replace(/,/g,'')) > parseFloat(enableBondAmount.replace(/,/g,''))){
            $(obj).val(enableBondAmount);
            alert("可用保证金余额不能大于可用保证金",0);
        }
    };
    // 资金机构分润
    var _agenciesShareProfit = function(url){
    	var cusNo = url.split("?")[1].split("=")[1];
    	jQuery.ajax({
            url:webPath+"/mfShareProfitConfig/checkShareProfitConfigAjax",
            data:{cusNo:cusNo},
            type:"post",
            success:function(data){
                if(data.flag == "success"){
                	if(data.mfShareProfitConfig.calcBase == "1"){//按客户数分润
                		top.openBigForm(webPath + "/mfBusAgencies/agenciesShareProfitByCus?cusNo=" + cusNo + "&calcBase=" + data.mfShareProfitConfig.calcBase,showName + "分润", function(){
                			updateTableData();
                		});	
                	}else{
                		top.openBigForm(webPath + url + "&calcBase=" + data.mfShareProfitConfig.calcBase,showName + "分润", function(){
                			updateTableData();
                		});	
                	}
                }else{
                	alert(data.msg,3);
                }
            },
            error:function(){
            	alert(top.getMessage("FAILED_OPERATION"),0);
            }
        });
    };

    /**
		上级资金机构选择
     */
    var _selectAgencies =function(callback){
        dialog({
            id:'agenciesDialog',
            title:'上级资金机构',
            url:webPath+"/mfBusAgencies/getSelectPage",
            width:900,
            height:400,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    if(typeof(callback)== "function"){
                        callback(this.returnValue);
                    }
                }
            }
        }).showModal();

	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		insertInit:_insertInit,
		updateInit:_updateInit,
		selectKindCallBack:_selectKindCallBack,
		selectKindNo:_selectKindNo,
		changeCreditBal:_changeCreditBal,
		creditTermOnBlur : _creditTermOnBlur,
		beginDateOnBlur : _beginDateOnBlur,
		getCreditUsedAmount : _getCreditUsedAmount,
		depositBondApp : _depositBondApp,
		getEnableBondAmount : _getEnableBondAmount,
		insertDetailInit : _insertDetailInit,
		updateDetailInit : _updateDetailInit,
		updateReleaseDetailInit : _updateReleaseDetailInit,
		viewPointInit : _viewPointInit,
		agenciesConfig : _agenciesConfig,
		insertConfigInit : _insertConfigInit,
		updateConfigInit : _updateConfigInit,
        availableSpareAmt : _availableSpareAmt,
        agenciesShareProfit:_agenciesShareProfit,
        updateApplyEndDate:_updateApplyEndDate,
        selectAgencies:_selectAgencies
	};
}(window, jQuery);
function finForm_input(url){//新增弹框
	top.addFlag = false;
	top.createShowDialog(url,"新增资金机构",'90','90',function(){
		if(top.addFlag){
			 updateTableData();//重新加载列表数据
   		}
	});
}
//删除
function deleteBanner(url){
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
	 	$.ajax({
 		url:url,
 		dataType:'json',
 		type:'post',
 		success : function(data){
 			if (data.flag == "success") {
				window.top.alert(data.msg, 1);
				updateTableData();//重新加载列表数据
			} else {
				window.top.alert(data.msg, 0);
			}
 		}
 	})
 });
	 
}
//新增页面js
function myInsertAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
						top.addFlag=true;
						$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myInsertDetailAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);	
					$("input[name=id]").val(data.id);
					$("#formRowCenter").hide();
					$("#formApprovalRowCenter").show();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myInsertConfigAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					$("input[name=id]").val(data.id);
					$("#formRowCenter").hide();
					$("#formCloseCenter").show();
				} else {
					window.top.alert(data.msg, 0);
				}
                myclose_click();
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myUpdateDetailAjax(dom){//更新方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					$("#formRowCenter").hide();
					$("#formApprovalRowCenter").show();
					$("#formCloseCenter").hide();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myUpdateConfigAjax(dom){//更新方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					$("#formRowCenter").hide();
					$("#formCloseCenter").show();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function approvelDetailAjax(dom){//更新方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url: webPath + "/mfBusAgencies/submitMfAgenciesProcess",
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.success) {
					window.top.alert(data.msg, 1);
					$("#formRowCenter").hide();
					$("#formApprovalRowCenter").hide();
					$("#formCloseCenter").show();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myUpdateReleaseDetailAjax(dom){//更新方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					window.top.alert(data.msg, 1);	
					//$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
					$("#formRowCenter").show();
					$("#formCloseReleaseCenter").hide();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

var approveModify = function(obj) {
	var flag = submitJsMethod($(obj).get(0), '');
	if (flag) {
		var url = $(obj).attr("action");
		var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		commitProcess(url + "?appNo=" + wkfAppId + "&opinionType=" + opinionType, obj, 'applySP');
	}
};

function ajaxTrDeleteDetail(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	var agenciesId1 = agenciesId;
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
	 	$.ajax({
 		url:url,
 		dataType:'json',
 		type:'post',
 		success : function(data){
 			if (data.flag == "success") {
				window.top.alert(data.msg, 1);
				window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
			} else {
				window.top.alert(data.msg, 0);
			}
 		}
 	})
 });
};

function ajaxTrDeleteConfig(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	var agenciesId1 = agenciesId;
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
	 	$.ajax({
 		url:url,
 		dataType:'json',
 		type:'post',
 		success : function(data){
 			if (data.flag == "success") {
				window.top.alert(data.msg, 1);
				window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
			} else {
				window.top.alert(data.msg, 0);
			}
 		}
 	})
 });
};

function closeMfAgenciesDetailInput(){
	//window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId='"+agenciesUid+"'&busEntrance='cus_agencies'";
	myclose_click();
};

function closeMfAgenciesConfigInput(){
	myclose_click();
};

var getAgenciesDetailById = function(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	var agenciesId1 = agenciesId;
	window.parent.openBigForm(url, '存出保证金详情', function() {
		window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
	});
};

var getMfBusAgenciesDetailWkfHis = function(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	window.parent.openBigForm(url, '审批详情', function() {
		
	});
};

var getAgenciesConfigById = function(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	var agenciesId1 = agenciesId;
	window.parent.openBigForm(url, '资金机构配置详情', function() {
		window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
	});
};

var getAgenciesDetailReleaseById = function(url){
	if (url.substr(0, 1) == "/") {
		url = webPath + url;
	} else {
		url = webPath + "/" + url;
	}
	var agenciesId1 = agenciesId;
	window.parent.openBigForm(url, '释放保证金', function() {
		window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
	});
};

$(".cancel").bind("click", function(event){
	$(top.window.document).find("#showDialog").remove();
});

var _submitMfAgenciesProcess = function(obj) {
	var agenciesId1 = agenciesId;
	var flag = submitJsMethod($(obj).get(0), '');
	if (flag) {
		LoadingAnimate.start();
		var url = webPath + "/mfBusAgencies/submitMfAgenciesProcess";
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			url : url,
			data : {
				ajaxData : dataParam
			},
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if (data.success) {
					window.top.alert(data.msg, 3);
					window.location.href = webPath + "/mfBusAgencies/getAgenciesView?agenciesId="+agenciesId1+"&busEntrance=cus_agencies";
				} else {
					window.top.alert(data.msg, 3);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
};

function myUpdateAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
						top.updateFlag=true;
						top.htmlStr=data.htmlStr;
						top.cusInfo=data.cusInfo;//更新之后要更新视角页面
						window.top.alert(data.msg, 1);
						$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
				} else {
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
}
//调视角详情页面
function getViewCommon(url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	window.location.href=url;
}
function dealWithAgenciesUid(agenciesUid,type){
	$.ajax({
		url:webPath+'/mfBusAgencies/getAgenciesBankNotShowAjax',
		data:'agenciesUid='+agenciesUid,
		dataType:'json',
		async:false,
		type:'POST',
		success:function(data){
			var agenciesBankNotShow = data.agenciesBankNotShow;
			for(var i = 0; i < agenciesBankNotShow.length; i++){
				var agenciesBankId = agenciesBankNotShow[i].id;
				$("[name=agenciesDetailName]").find("option[value="+agenciesBankId+"]").remove();
			}
		}
	});
	
	$('select[name=agenciesDetailName]').popupSelection({
		searchOn: true, //启用搜索
		inline: true, //下拉模式
		multiple: false, //单选
		changeCallback : function (obj, elem) {
			$("input[name=agenciesDetailName]").val(obj.data("text"));
			$("input[name=agenciesDetailUid]").val(obj.val());
			
			$.ajax({
				url:webPath+'/mfBusAgencies/getAgenciesBankInfoAjax',
				data:{'agenciesUid':agenciesUid,'agenciesDetailUid':obj.val()},
				dataType:'json',
				async:false,
				type:'POST',
				success:function(data){
					$("input[name=largeMulti]").val(data.mfBusAgenciesConfig.largeMulti);
					$("input[name=realCreditRate]").val(data.mfBusAgenciesConfig.realCreditRate);
					var enableBondAmount;
					var realDepositBond = $("input[name=realDepositBond]").val().replace(/,/g,'');
					var largeMulti = data.mfBusAgenciesConfig.largeMulti;
					enableBondAmount = CalcUtil.multiply(realDepositBond,largeMulti);
					$("input[name=enableBondAmount]").val(CalcUtil.formatMoney(enableBondAmount,2));
					$("input[name=creditRateMemo]").val(data.mfBusAgenciesConfig.creditRateMemo);
				}
			});
		}
	});
	
	if(type == 'update'){
		$(".pops-value").empty();
		$(".pops-value").text(agenciesDetailName);
		$("input[name=agenciesDetailName]").val(agenciesDetailName);
	}
}
//处理客户类型问题,以后逗号分隔，可以传入多个客户类型
function dealWithCusType(baseTypes){
	$.ajax({
		url:webPath+'/mfBusTrench/getCusTypeNotShowAjax',
		data:'baseTypes='+baseTypes,
		dataType:'json',
		async:false,
		type:'POST',
		success:function(data){
			var notShowCusTypes=data.cusTypeList;
			if(notShowCusTypes!=null){
				for(var i=0;i<notShowCusTypes.length;i++){
					var typeNo=notShowCusTypes[i].typeNo;
					$("[name=cusType]").find("option[value="+typeNo+"]").remove();
				}
			}
		}
	})
	$('[name=cusType]').popupSelection({
		searchOn: true, //启用搜索
		inline: true, //下拉模式
		multiple: false, //单选
		changeCallback:chooseCusType
	});
}
function chooseCusType(){
	$("[name=cusType]").change();
}
