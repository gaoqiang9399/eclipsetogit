//获取客户信息
function getCusInfo(){
	top.LoadingAnimate.start();
	if(busEntrance == 'credit' || busEntrance == 'cus_credit' || busEntrance == 'cus_core_company'){
		if(typeof(baseType) != "undefined" && baseType == "3"){
			window.location.href=webPath+"/mfCusCoreCompany/getCoreCompanyView?coreCompanyUid="+cusNo+"&busEntrance=cus_core_company&baseType="+baseType;
		}else{
			window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance=cus_credit"+"&creditAppId="+creditAppId+"&relNo="+creditAppId;
		}
	}else{
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&fincId="+fincId+"&opType="+opType+"&busEntrance="+busEntrance+"&operable="+operable;
	}

}

//获取申请信息
function getBusInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfBusApply/getSummary?appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
}
//获取合同信息
function getPactInfo(){
	top.LoadingAnimate.start();
	if(busEntrance=='3'||busEntrance=='6' ||busEntrance=='finc'){
		window.location.href=webPath+"/mfBusPact/getPactFincById?fincId="+fincId+"&appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}else{
		window.location.href=webPath+"/mfBusPact/getById?appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}
}

//多业务大于3条时，弹层列表页面
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

//根据列表超链接获得信息详情，支持编辑
function getCollateralInfoById(obj,getUrl){
	var $dynamicBlock = $(obj).parents(".list-table");
	var id = $dynamicBlock.attr("id");
	top.collateralNo=$dynamicBlock.parent().find("span").attr("id");
	var title = $dynamicBlock.find("span").html();
	top.id = id;
	top.title=title;
	top.showType = "2";
	top.flag="edit";//编辑
	top.addFlag = false;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	//top.getTableUrl = action + "_getListPage.action?collateralNo="+collateralNo;
	top.openBigForm(getUrl,title,function(){
		if(top.addFlag){
			ShowPledgeDetailInfo.addCollateralFormInfoCall();
		}
	});
}
function deletePledgeBaseInfoBill(obj , url){
	var pledgeBillNo = url.split("?")[1].split("&")[0].split("=")[1];
	var pledgeNo = url.split("?")[1].split("&")[1].split("=")[1];
	var tableId = $(obj).parent().parent().parent().parent().attr("title");
	tableId = tableId.substring(5,tableId.length);
	$.ajax({
		url:webPath+"/pledgeBaseInfoBill/deleteAjax",
		data:{pledgeBillNo:pledgeBillNo,pledgeNo:pledgeNo,tableId:tableId},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				if(data.dataFullFlag == "1"){
					if(data.htmlStrFlag == "1"){
						var divTable = $(obj).parent().parent().parent().parent().parent();
						$(obj).parent().parent().parent().parent().remove();
						$(divTable).html(data.htmlStr);
					}
				}else if(data.dataFullFlag == "0"){
					$(obj).parent().parent().parent().parent().parent().parent().remove();
					$("#rotate-body"+pledgeNo).empty();
					$.each(data.mfCollateralTableList,function(i,collateralTable){
						ShowPledgeDetailInfo.setBlock(collateralTable,i,data.pledgeBaseInfo.classId);
					});
					ShowPledgeDetailInfo.cusAdd(pledgeNo);
				}
			}else{
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
			top.LoadingAnimate.stop();
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	
}
//单字段编辑的保存回调方法。
function oneCallback(data) {
	var name = data[0].name;
	var value = data[0].value;
	BASE.oneRefreshTable(name,value);
	if(name=="evalAmount"){
		MfBusCollateralRelDetail.updateCollateral();
	};
	if(name=="assureAmt"){
		MfBusCollateralRelDetail.updateCollateral();
	};
	if(name=="pledgeRate"){
		MfBusCollateralRelDetail.updateCollateral();
	};
	if(name=="mortRate"){
		MfBusCollateralRelDetail.updateCollateral();
	};
	$(".form-table").empty();
	MfBusCollateralRelDetail.showCollateralBaseInfo();
}


function validateAmount(){
	var num  = $("input[name=evalAmount]").val();
	if(num.length!=0){
		var reg = /^\-[,,0-9]*.?[0-9]*$/;
		if(reg.test(num)){
			alert("评估价值不能输入负数", 0);
			$("input[name=evalAmount]").val("");
		}
	}
}
function validateRate(){
	var num  = $("input[name=mortRate]").val();
	if(num.length!=0){
		var reg = /^\-[0-9]*.?[0-9]*$/;
		if(reg.test(num)){
			alert("抵质押率不能输入负值", 0);
			$("input[name=mortRate]").val("");
		}
	}
}
function validateMonth(){
	var num  = $("input[name=validTerm]").val();
	if(num.length!=0){
		var reg = /^\-[0-9]*$/;
		if(reg.test(num)){
			alert("评估有效期不能输入负值", 0);
			$("input[name=validTerm]").val("");
		}
	}
}

function calConfirmAmount(){
	var value = $("input[name=evalAmount]").val().replace(/,/g,"");
	if(value.length == 0){
		alert("请先输入评估价值",0);
		$("input[name=mortRate]").val("");
	}
	var rate = $("input[name=mortRate]").val();
	$("input[name=confirmAmount]").val(value*rate/100);
};

var MfBusCollateralRelDetailReadOnly = function(window, $){
	var _init=function(){
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			},
			callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
				whileScrolling: function(){
					if ($(".changeval").length>0) {
						$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
					}
				}
			}
		});
		
		// 质押、抵押时，显示入库，出库按钮
		if(vouType=='3' || vouType=='4'){
			$('#batchInStock').show();
			$('#batchOutStock').show();
		}else{
			$('#batchInStock').hide();
			$('#batchOutStock').hide();
		}
		
		_showCollateralBaseInfo();
		if(busModel!="5"&&busModel!="13"){
			if(evalFlag=="0"){
				$("#envalueAmt").html("未评估").attr("class","content-span unregistered");
				$("#receiptsAmount").html("未评估").attr("class","content-span unregistered");
			}
		}
		
		//放款审批通过之后才允许入库，放款审批完成前入库不能按钮不可编辑
		if(fincSts == "" || fincSts<4){
			$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
		}
		if(inBatchSts=="1"){
			$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			$("#batchInStock").find("span").html("入库审批中");
		}
		if(outBatchSts=="1"){
			$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			$("#batchOutStock").find("span").html("出库审批中");
		}
		//入库、出库审批中时展示审批历史流程图
		if(outBatchSts=="1"||inBatchSts=="1"){
			//获得审批历史信息
			_showApproveHis();
		}
		top.LoadingAnimate.stop(); 
	};
	//展示审批历史
	var _showApproveHis=function(){
		//获得审批历史信息
		showWkfFlowVertical($("#wj-modeler2"),keepId,"17","inout_stock_approval");
		$("#inOutStockApproveHis").show();
	};
	//展示所有已登记的押品基本信息
	var _showCollateralBaseInfo=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralListHtmlAjax?entrance="+entrance+"&cusNo="+cusNo,
			data:{appId:appId,relId:relId,collateralType:collateralType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=="success"){
					$.each(data.htmlMap,function(index,obj){
							var collateralId = index;
							var collateralDetailRel=data[index];
							var bean = data.beanMap[index];
							var vouType = data.vouTypeMap[index];
                        	var collateralName,pledgeMethod,collateralNameCapture,releaseCollateralStr,htmlStr;
							if(vouType!="2"){//抵质押信息
								collateralName = bean.pledgeName;
								pledgeMethod=bean.pledgeMethod;
								var keepStatusName=bean.extOstr01;
								collateralNameCapture = collateralName
								if(collateralName.length>20){
									collateralNameCapture = collateralName.substring(0,20)+"...";
								}
								var collateralType = bean.classNo;
								var keepStatus = bean.keepStatus;
								/*var releaseCollateralStr = '<button class="btn btn-link formAdd-btn" id="release'+collateralId+'" onclick="MfBusCollateralRelDetail.releaseCollateral(\''+collateralId+'\',\''+collateralName+'\');" title="删除">删除</button>';
								var inStockStr = '<button class="btn btn-link formAdd-btn" id="inStock'+collateralId+'" onclick="MfBusCollateralRelDetail.inStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+pledgeMethod+'\');" title="入库">入库</button>';
								var outStockStr = '<button class="btn btn-link formAdd-btn" id="outStock'+collateralId+'" onclick="MfBusCollateralRelDetail.outStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+pledgeMethod+'\');" title="出库">出库</button>';
								var replaceCollateral = '<button class="btn btn-link formAdd-btn" id="replace'+collateralId+'" onclick="MfBusCollateralRelDetail.replaceCollateral(\''+collateralId+'\');" title="置换">置换</button>';
								var lowestWorthAdjust = '<button class="btn btn-link formAdd-btn" id="worthAdjust'+collateralId+'" onclick="MfMoveableCommon.lowestWorthAdjust(\''+collateralId+'\');" title="最低监管价值调整">监管调整</button>';
								*/
								releaseCollateralStr = '';
								var inStockStr = ''; 
								var outStockStr  = '';
								var replaceCollateral = '';
								var lowestWorthAdjust = '';
									
								htmlStr ='<div class="dynamic-block" name="collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title" id="title'+collateralId+'">'+
								'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'('+keepStatusName+')'+'</span>'+
								releaseCollateralStr+inStockStr+outStockStr+replaceCollateral+lowestWorthAdjust+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBase'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
								$(".block-add").after(htmlStr);
								ShowPledgeDetailInfo.showPledgeInfo(collateralId,relId,bean.classId);
								/*var tableList = data.tableListMap[index];
								$.each(tableList,function(i,collateralTable){
									ShowPledgeDetailInfo.setBlock(collateralTable,i,bean.classId);
								});
								//信息块加完后，加号去掉
								if(data.allFullFlag=="0"){
									ShowPledgeDetailInfo.cusAdd(collateralId);
								}
								if(keepStatus=="0"||keepStatus=="1"||keepStatus>="5" || fincSts == "" || fincSts<4){//入库
									$("#inStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
								}
								//如果业务到贷后阶段了，但是又新增了押品时单个押品支持出库
								if(keepStatus=="0"&&fincSts>=5){
									$("#inStock"+collateralId).removeAttr("disabled")
									$("#inStock"+collateralId).attr("class","btn btn-link formAdd-btn");
								}
								if(keepStatus=="10"){
									$("#outStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
									$("#inStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
								}
								//押品入库前，不允许出库操作
								if(keepStatus<"5"){
									$("#outStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
								}
								if(collateralDetailRel.inSts=="1"){
									$("#inStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
									$("#inStock"+collateralId).html("入库审批中");
								}
								if(collateralDetailRel.outSts=="1"){
									$("#outStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
									$("#outStock"+collateralId).html("出库审批中");
								}*/
							}else if(vouType=="2"){//保证信息
								collateralName = bean.assureName;
								var htmlContent = data.htmlMap[index];
								pledgeMethod=vouType;
								collateralNameCapture = collateralName
								if(collateralName.length>20){
									collateralNameCapture = collateralName.substring(0,20)+"...";
								}
								//var releaseCollateralStr = '<button class="btn btn-link formAdd-btn" id="release'+collateralId+'" onclick="MfBusCollateralRelDetail.releaseCollateral(\''+collateralId+'\',\''+collateralName+'\');" title="删除">删除</button>';
								releaseCollateralStr = '';
								htmlStr ='<div class="dynamic-block" name="collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title" id="title'+collateralId+'">'+
								'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'</span>'+
								releaseCollateralStr+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBaseXin'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div id="collateralBaseXin'+collateralId+'">' +
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
								$(".block-add").after(htmlStr);
								htmlStr = '<div class="handle"><span class="sub-title" id="'+collateralId+'">保证信息</span></div>';
								htmlStr = '<div class="cover infoTilte form-table" id="mf_assure_info'+collateralId+'">'+htmlStr+
								'<form class="form-margin" action="'+webPath+'/MfAssureInfo/updateAjaxByOne.action" id="MfAssureInfoAjax_updateByOne">'+htmlContent+'</form></div>';
								$("#rotate-body"+collateralId).before(htmlStr);
								if(data.entrance != ""  &&  data.entrance  ==  "credit" ){
								var htmlTableContent = data.tablehtmlMap[index];
								var htmlTableContent1 =	'<div class="list-table-replan"  id="collateral'+collateralId+'">'  + '<div class="content margin_left_15 collapse in">'
								   +htmlTableContent+
								'</div>' +   '</div>'  +  '</div>'
								$("#collateralBase"+collateralId).after(htmlTableContent1);
								//$(htmlTableContent).after(htmlStr);
								}
							}
				
					});
					_switchShowHide();
					if(data.entrance != ""  &&  data.entrance  ==  "credit" ){
					var htmlTableContentCusNo = data.assureInfosStrXin;
					var htmlTableContentCusNo1 ='<div class="form-table">' +  '<div class="title"><span class="sub-title">该客户所担保的保证信息</span> </div>'  +	'<div class="list-table-replan">'  + '<div class="content margin_left_15 collapse in">'
					   +htmlTableContentCusNo+
					'</div>' +   '</div>' + '</div>'
					};
				}
				top.LoadingAnimate.stop();
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});

	};
	//对押品进行各项操作后更新押品基本信息
	var _updateInfo=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/updateInfoAjax",
			data:{appId:appId,relId:relId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag="success"){
					$.each(data.htmlMap,function(index,obj){
							var collateralId = index;
							$("#pledge_base_info"+collateralId).children("#PledgeBaseInfoActionAjax_updateByOne").html(data.htmlMap[index]);
					})
				}
			}
			})
					
	}
	//根据业务模式控制担保信息中功能操作的显示或隐藏
	var _switchShowHide=function(){
		//保理业务,应收账款质押融资 隐藏押品置换功能
		if(busModel=="13"||busModel=="5"){
			if((collaSts=="0"||collaSts=="1")||collaSts>="5"){
				$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			}
			$("button[id^='replace']").hide();
			//放款复核完成前，未进入贷后阶段。押品没有全部入库，不允许折让、争议处理、反转让操作
			if(busSts<="4"||instockAllflag=="0"){
				$("#rebatePledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#disputedDealPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#buyBackPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#rebateHistory").attr("onclick","");
				$("#disputedHistory").attr("onclick","");
				$("#repoAffirmInfo").attr("onclick","");
			}else{//贷后
				_controlOperate();
			}
		}
		//业务提交审批后
		if(appSts=="2"||appSts=="3"||appSts=="4"){
			//押品新增放开,不再受业务状态控制
			//$("#addCollateralInfo").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			$("button[id^='release']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
		}
		_switchDisabled();
		//应收账款是否质押或转让
		if(tranFlag=="1"){
			$("#recePledge").attr("class","btn btn-view button-bac32B5CB");
		}else{
			$("#recePledge").attr("onclick","");
		}
		//预付款没有还清，业务未完结 控制出库操作不可用
		if(authCycle == "1"){// 额度可循环
			if(isAllFincEnd != '1'){// 有未完结借据
				$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("button[id^='outStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
			}
		}else{//额度不可循环
			if(busSts!="7"){
				$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("button[id^='outStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
			}
		}
		//保理业务中 进行过反转让且已确认
		if(busModel=="13"){
			if(repoFlag=="1"&&repoSts=="4"){
				if(collaSts!="10"){
					$("#batchOutStock").removeAttr("disabled").attr("class","btn btn-opt");
					$("button[id^='outStock']").removeAttr("disabled").attr("class","btn btn-link formAdd-btn");
				}
			}
		}
		//是否全部入库
		if(instockAllflag=="1"){
			$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			$("button[id^='inStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
		}
		//是否全部出库
		if(outstockAllflag=="1"){
			$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
			$("button[id^='outStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
		}
		//动产质押业务
		if(busModel=="1"){
			//合同未签约。押品没有全部入库，不允许移库、提货等操作
			if(pactSts<4||instockAllflag=="0"){
				$("#transferApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#claimGoodsApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#moveableModify").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#moveableCompentstate").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#patrolInventory").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("#accountCheck").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("button[id^='worthAdjust']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
				$("button[id^='replace']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
				$("#checkInventory").attr("onclick","");
				$("#modifyHistory").attr("onclick","");
			}else{
				_controlOperateForMoveable();
			}
		}else{//不是动产质押业务,监管调整隐藏掉
			$("button[id^='worthAdjust']").hide();
		}
		//$("#modifyHistory").attr("class","btn btn-view btn-dodgerblue");
	};
	/**
	 * 动产质押
	 */
	var _controlOperateForMoveable=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralRelStsForMoveableAjax",
			data:{appId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=='success'){
					if(data.collaSts!="0"){
						$("#transferApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#claimGoodsApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#moveableModify").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#moveableCompentstate").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#patrolInventory").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#accountCheck").attr("disabled","true").attr("class","btn btn-opt set-disabled");
					}else if(data.collaSts=="0"){
						$("#transferApply").removeAttr("disabled").attr("class","btn btn-opt");
						$("#claimGoodsApply").removeAttr("disabled").attr("class","btn btn-opt");
						$("#moveableModify").removeAttr("disabled").attr("class","btn btn-opt");
						$("#moveableCompentstate").removeAttr("disabled").attr("class","btn btn-opt");
						$("#patrolInventory").removeAttr("disabled").attr("class","btn btn-opt");
						$("#accountCheck").removeAttr("disabled").attr("class","btn btn-opt");
						$("#claimGoodsAffirm").hide();
						$("#claimGoodsApply").show();
						$("#moveableCompentstate").show();
						$("#moveableCompentstateConfirm").hide();
					}
					if(data.collaSts=="3"){//提货确认
						$("#claimGoodsAffirm").show();
						$("#claimGoodsApply").hide();
					}
					if(data.collaSts=="5"){//跌价补偿确认
						$("#moveableCompentstateConfirm").show();
						$("#moveableCompentstate").hide();
					}
					if(collaSts=="10"){
						$("#transferApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#claimGoodsApply").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#moveableModify").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#moveableCompentstate").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#patrolInventory").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#accountCheck").attr("disabled","true").attr("class","btn btn-opt set-disabled");
					}
				}
			}
		});
	};
	/**
	 * 控制应收账款折让、争议、反转让功能是否可操作
	 */
	var _controlOperate=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralRelStsByIdAjax",
			data:{appId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=='success'){
					if(data.collaSts!="0"){
						$("#rebatePledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#disputedDealPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#buyBackPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#disputedDealPledge").show();
						$("#relieveDisputed").hide();
						$("#rebateAffirm").hide();
					}else if(data.collaSts=="0"){//没有折让、争议、反转让交易争议正在进行
						$("#rebatePledge").show();
						$("#disputedDealPledge").show();
						$("#buyBackPledge").show();
						$("#relieveDisputed").hide();
						$("#rebateAffirm").hide();
						$("#receRepoAffirm").hide();
						$("#rebatePledge").removeAttr("disabled").attr("class","btn btn-opt");
						$("#disputedDealPledge").removeAttr("disabled").attr("class","btn btn-opt");
						$("#buyBackPledge").removeAttr("disabled").attr("class","btn btn-opt");
					}
					if(data.collaSts=="3"){//反转让中
						$("#buyBackPledge").find("span").html("反转让中");
						if(busSts=="7"){//回购中，剩余预付款已还完，业务完结
							//$("#buyBackPledge").hide();
						}
					}else if(data.collaSts=="4"){//反转让已确认
						$("#receRepoAffirm").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#receRepoAffirm").find("span").html("反转让已确认");
						//$("#buyBackPledge").hide();
					}else if(data.collaSts=="1"){//折让中
						$("#rebatePledge").find("span").html("折让中");
					}else if(data.collaSts=="2"){//争议审批中
						$("#disputedDealPledge").find("span").html("争议处理中");
					}else if(data.collaSts=="5"){//争议审批完成，争议待解除
						$("#disputedDealPledge").hide();
						$("#relieveDisputed").show();
					}else if(data.collaSts=="6"){//折让审批完成，待折让确认
						$("#rebatePledge").hide();
						$("#rebateAffirm").show();
					}else if(data.collaSts=="7"){//反转让审批完成，待折让确认
						$("#buyBackPledge").hide();
						$("#receRepoAffirm").show();
					}
					if(busSts!="7"){//剩余预付款未还完
						//$("#buyBackPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
					}
					if(data.tranFlag=="1"){
						$("#recePledge").attr("class","btn btn-view button-bac32B5CB");
					}
					if(data.repoFlag=="1"){
						$("#repoAffirmInfo").attr("class","btn btn-view button-bacE14A47");
					}
					if(data.rebateFlag=="1"){
						$("#rebateHistory").attr("class","btn btn-view button-bacFCB865");
					}
					if(data.disputedFlag=="1"){
						$("#disputedHistory").attr("class","btn btn-view button-bacE14A47");
					}
					if(collaSts=="10"){
						$("#rebatePledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#disputedDealPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
						$("#buyBackPledge").attr("disabled","true").attr("class","btn btn-opt set-disabled");
					}
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//切换功能是否可操作
	var _switchDisabled=function(){
		//如果是授信担保详情或出入库审批页面展示时，去掉担保详情中操作按钮，保留删除
		/**
		 * operable参数无法使用，暂时用其他值判断
		 */
		var flag = false;
		if(busEntrance=='4' || busEntrance=='5' || busEntrance=='6'){
			flag=true;
		}
		if(entrance=="credit"|| flag){
			
			$("button[id^='inStock']").hide();
			$("button[id^='outStock']").hide();
			$("button[id^='replace']").hide();
			$("#batchInStock").hide();
			$("#batchOutStock").hide();
			
			//出入库审批页面展示详情时，去掉担保详情中操作按钮
			if(flag){
				$("button[id^='release']").hide();
			}
		}
	}
	//追加押品
	var _addCollateralInfo =function(){
		/*var url = webPath+"/mfBusCollateralRel/insertInput?cusNo="+cusNo+"&appId="+relId+"&entrFlag=collateral&entrance="+entrance;
		top.addCollateral=false;
		top.collateralId="";
		top.collateralName="";
		top.collateralType="";
		top.openBigForm(url,"新增担保信息",function(){
			if(top.addCollateral){
				var collateralId = top.collateralId;
				var collateralName = top.collateralName;
				var collateralType = top.collateralType;
				var vouType=top.vouType;
				jQuery.ajax({
					url:webPath+"/mfBusCollateralRel/getBaseCollateralHtmlAjax?entrance="+entrance+"&cusNo="+cusNo,
					data:{collateralId:collateralId,collateralType:collateralType,vouType:vouType},
					type:"POST",
					dataType:"json",
					beforeSend:function(){
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							var collateralNameCapture = collateralName
							if(collateralName.length>20){
								collateralNameCapture = collateralName.substring(0,20)+"...";
							}
							if(vouType!="2"){//抵质押信息
								var keepStsName=data.pledgeBaseInfo.extOstr01;
								var keepStatus=data.pledgeBaseInfo.keepStatus;
								var releaseCollateralStr = '<button class="btn btn-link formAdd-btn" id="release'+collateralId+'" onclick="MfBusCollateralRelDetail.releaseCollateral(\''+collateralId+'\',\''+collateralName+'\');" title="删除">删除</button>';
								var inStockStr = '<button class="btn btn-link formAdd-btn" id="inStock'+collateralId+'" onclick="MfBusCollateralRelDetail.inStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+vouType+'\');" title="入库">入库</button>';
								var outStockStr = '<button class="btn btn-link formAdd-btn" id="outStock'+collateralId+'" onclick="MfBusCollateralRelDetail.outStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+vouType+'\');" title="出库">出库</button>';
								var replaceCollateral = '<button class="btn btn-link formAdd-btn" id="replace'+collateralId+'" onclick="MfBusCollateralRelDetail.replaceCollateral(\''+collateralId+'\');" title="置换">置换</button>';
								var lowestWorthAdjust = '<button class="btn btn-link formAdd-btn" id="worthAdjust'+collateralId+'" onclick="MfMoveableCommon.lowestWorthAdjust(\''+collateralId+'\');" title="最低监管价值调整">监管调整</button>';
								var htmlStr ='<div class="dynamic-block" title="押品信息" name="collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title">'+
								'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'('+keepStsName+')'+'</span>'+
								releaseCollateralStr+inStockStr+outStockStr+replaceCollateral+lowestWorthAdjust+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBase'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
								$(".block-add").after(htmlStr);
								ShowPledgeDetailInfo.showPledgeInfo(collateralId,relId,data.classId);
								var tableList = data.collateralTableList;
								$.each(tableList,function(i,collateralTable){
									ShowPledgeDetailInfo.setBlock(collateralTable,i,data.classId);
								});
								ShowPledgeDetailInfo.cusAdd(collateralId);
								_updateCollateral();
								_switchShowHide();
								$("#inStock"+collateralId).removeAttr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
								//如果业务到贷后阶段了，但是又新增了押品时单个押品支持出库
								if(keepStatus=="0"&&fincSts>=5){
									$("#inStock"+collateralId).removeAttr("disabled")
									$("#inStock"+collateralId).attr("class","btn btn-link formAdd-btn");
								}
								//押品入库前，不允许出库操作
								if(keepStatus<"5"){
									$("#outStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
								}
								
							}else if(vouType=="2"){//保证信息
								var releaseCollateralStr = '<button class="btn btn-link formAdd-btn" id="release'+collateralId+'" onclick="MfBusCollateralRelDetail.releaseCollateral(\''+collateralId+'\',\''+collateralName+'\');" title="删除">删除</button>';
								var htmlStr ='<div class="dynamic-block" name="collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title" id="title'+collateralId+'">'+
								'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'</span>'+
								releaseCollateralStr+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBase'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
								$(".block-add").after(htmlStr);
								var htmlStr = '<div class="handle"><span class="sub-title" id="'+collateralId+'">保证信息</span></div>';
								htmlStr = '<div class="cover infoTilte form-table" id="mf_assure_info'+collateralId+'">'+htmlStr+
								'<form class="form-margin" action="/MfAssureInfo/updateAjaxByOne.action" id="MfAssureInfoAjax_updateByOne">'+data.htmlStr+'</form></div>';
								$("#rotate-body"+collateralId).before(htmlStr);
								var htmlStr ='<div class="dynamic-block" name="collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title" id="title'+collateralId+'">'+
								'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'</span>'+
								releaseCollateralStr+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBaseXin'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div id="collateralBaseXin'+collateralId+'">' +
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
								$(".block-add").after(htmlStr);
								
								var htmlStr = '<div class="handle"><span class="sub-title" id="'+collateralId+'">保证信息</span></div>';
								htmlStr = '<div class="cover infoTilte form-table" id="mf_assure_info'+collateralId+'">'+htmlStr+
								'<form class="form-margin" action="/MfAssureInfo/updateAjaxByOne.action" id="MfAssureInfoAjax_updateByOne">'+data.htmlStr+'</form></div>';
								$("#rotate-body"+collateralId).before(htmlStr);
								if(data.entrance != ""  &&  data.entrance  ==  "credit" ){
								var htmlTableContent = data.assureInfosStr;
								var htmlTableContent1 =	'<div class="list-table-replan"  id="collateral'+collateralId+'">'  + '<div class="content margin_left_15 collapse in">'
								   +htmlTableContent+
								'</div>' +   '</div>'  +  '</div>'
								$("#collateralBase"+collateralId).after(htmlTableContent1);
								}
								_updateCollateral();
								_switchShowHide();
							}
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		});*/
	};
	
	//刷新押品基本信息
	var _updateBaseCollateralInfo = function(collateralId){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getBaseCollateralHtmlAjax",
			data:{collateralId:collateralId},
			success:function(data){
				if(data.flag="success"){
					if(data.vouType!="2"){//抵质押信息
						$("#pledge_base_info"+collateralId+" .form-margin").html(data.htmlStr);
					}else if(vouType=="2"){//保证信息
						$("mf_assure_info"+collateralId+" .form-margin").before(data.htmlStr);
					}
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	
	//解除押品关联关系，删除押品关联关系
	var _releaseCollateral=function(collateralId,busCollateralId){
		alert(top.getMessage("CONFIRM_DELCOLLAERTALREL"),2,function(){
			jQuery.ajax({
				url:webPath+"/mfBusCollateralRel/releaseCollateralAjax",
				data:{collateralId:collateralId,relId:relId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){
				},success:function(data){
					if(data.flag=="success"){
						alert(top.getMessage("SUCCEED_DELCOLLAERTALREL"),1);
						$("div[name=collateralBase"+collateralId+"]").remove();
						_updateCollateral();
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	//更新担保金额和担保比例
	var _updateCollateral=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralByUpdateAjax",
			data:{busCollateralId:busCollateralId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=="success"){
					$("#pledgeNum").html(data.collateralRel.pledgeNum);
					$("#envalueAmt").html(data.collateralRel.collateralAmt);
					//担保比例控制
					if(data.collateralRel.collateralRate>100){
						$("#receiptsAmount").html(100);
					}else{
                        $("#receiptsAmount").html(data.collateralRel.collateralRate);
					}
					$("#envalueAmt").attr("class","content-span");
					$("#receiptsAmount").attr("class","content-span");
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//打开押品入库页面
	var _inStockKeepInfo=function(collateralId,collateralNameArgs,pledgeMethod){
		var collateralMethod = vouType;
		var collateralName = collateralNameArgs;
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getPledgeEvalFlagAjax",
			data:{collateralId:collateralId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				//pledgeEvalFlag 0未添加押品评估1已添加押品评估
				if(data.pledgeEvalFlag=='1'){
					top.inStock=false;
					top.isExamineInOutStock="0";
					top.window.openBigForm(webPath+'/keepInfo/getInsertPage?collateralId='+collateralId+'&collateralName='+collateralName+'&collateralMethod='+pledgeMethod+"&appId="+appId,"入库信息",function(){
						if(top.inStock){
							if(top.isExamineInOutStock == "1"){//需要审批
								$("#inStock"+collateralId).html("入库审批中");
								$("#batchInStock").find("span").html("入库审批中");
							}else{
								$("#inStock"+collateralId).html("入库");
								$("#batchInStock").find("span").html("入库");
							}
							$("#inStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
							$("#replace"+collateralId).removeAttr("disabled");
							$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
							//获得审批信息
							keepId=data.keepInfo.keepId;
							_showApproveHis();
						}
					});
				}else if(data.pledgeEvalFlag=='0'){
					alert(top.getMessage("FIRST_OPERATION","押品评估"),0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//检查押品是否全部入库，如果全部入库提交下一个流程节点
	var _checkCollateralInstockflag = function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getInstockFlagAndSubmitAjax",
			data:{appId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=="success"){
					alert(data.msg,1);
					$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//押品批量入库
	var _inStockCollateralBatch=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getPledgeEvalFlagAjax",
			data:{appId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				//pledgeEvalFlag 0未添加押品评估1已添加押品评估
				if(data.pledgeEvalFlag=='1'){
					top.inStockBatch=false;
					top.window.openBigForm(webPath+'/mfBusCollateralRel/inStockBatchInput?appId='+appId+"&fincId="+fincId,"批量入库",function(){
						if(top.inStockBatch){
							$("#batchInStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
							$("button[id^='inStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
							if(top.isExamineInOutStock == "1"){
								$("#batchInStock").find("span").html("入库审批中");
								$("button[id^='inStock']").html("入库审批中");
							}else{
								$("#batchInStock").find("span").html("入库");
								$("button[id^='inStock']").html("入库");
							}
							//获得审批历史信息
							keepId=data.keepInfo.keepId;
							_showApproveHis();
						}
					});
				}else if(data.pledgeEvalFlag=='0'){
					alert(top.getMessage("FIRST_OPERATION","押品评估"),0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//打开押品出库页面
	var _outStockKeepInfo =function(collateralId,collateralNameArgs,pledgeMethod){
		var collateralMethod = pledgeMethod;
		var collateralName = collateralNameArgs;
		jQuery.ajax({
			url:webPath+"/keepInfo/isDataExistsAjax",
			data:{collateralId:collateralId,keepType:'2'},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.result=='1'){//不存在
					top.outStockPage=false;
					top.isExamineInOutStock="0";
					top.window.openBigForm(webPath+'/keepInfo/getOutStockPage?collateralId='+collateralId+'&collateralName='+collateralName+'&collateralMethod='+pledgeMethod+"&appId="+appId,"出库信息",function(){
						if(top.outStockPage){
							if(top.isExamineInOutStock == "1"){//需要审批
								$("#outStock"+collateralId).html("出库审批中");
								$("#batchOutStock").find("span").html("出库审批中");
							}else{
								$("#outStock"+collateralId).html("出库");
								$("#batchOutStock").find("span").html("出库");
							}
							$("#outStock"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
							$("#replace"+collateralId).attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
							$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
							//获得审批历史信息
							keepId=data.keepInfo.keepId;
							_showApproveHis();
						}
					});
				}else if(data.result=='0'){//存在
					top.window.openBigForm(webPath+'/keepInfo/getViewPage?collateralId='+collateralId+'&keepType=2',"出库信息",function(){
						
					});
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//是否全部出库，如果全部出库，则更新担保信息为已出库
	var _checkAllOutStock=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getAllOutstockFlagAndUpdateAjax",
			data:{appId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				//全部出库
				if(data.outstockflag=="1"){
					_updateInfo();
					$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//押品批量出库
	var _outStockCollateralBatch=function(){
		top.outStockBatch=false;
		top.window.openBigForm(webPath+'/mfBusCollateralRel/outStockBatchInput?appId='+appId+"&authCycle="+authCycle+"&fincId="+fincId,"批量出库",function(){
			if(top.outStockBatch){
				$("#batchOutStock").attr("disabled","true").attr("class","btn btn-opt set-disabled");
				$("button[id^='outStock']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
				$("button[id^='replace']").attr("disabled","true").attr("class","btn btn-link formAdd-btn set-disabled");
				if(top.isExamineInOutStock == "1"){//需要审批
					$("#batchOutStock").find("span").html("出库审批中");
					$("button[id^='outStock']").html("出库审批中");
				}else{//不需要审批
					$("#batchOutStock").find("span").html("出库");
					$("button[id^='outStock']").html("出库");
				}
				//获得审批历史信息
				keepId=data.keepInfo.keepId;
				_showApproveHis();
			}
		});
	};
	//押品置换
	var _replaceCollateral=function(collateralId_old){
		var url = webPath+"/mfBusCollateralRel/replaceCollateralInput?appId="+appId+"&collateralId_old="+collateralId_old+"&entrance="+entrance+"&cusNo="+cusNo;
		top.repCollateral=false;
		top.collateralId="";
		top.collateralName="";
		top.collateralType="";
		top.openBigForm(url,"置换押品",function(){
			if(top.repCollateral){
				var collateralId = top.collateralId;
				var collateralName = top.collateralName;
				var collateralType = top.collateralType;
				jQuery.ajax({
					url:webPath+"/mfBusCollateralRel/getBaseCollateralHtmlAjax",
					data:{collateralId:collateralId,collateralType:collateralType,action:"PledgeBaseInfoAction"},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag="success"){
							var collateralNameCapture = collateralName
							if(collateralName.length>20){
								collateralNameCapture = collateralName.substring(0,20)+"...";
							}
							var releaseCollateralStr = '<button class="btn btn-link formAdd-btn" id="release'+collateralId+'" onclick="MfBusCollateralRelDetail.releaseCollateral(\''+collateralId+'\',\''+collateralName+'\');" title="删除">删除</button>';
							var inStockStr = '<button class="btn btn-link formAdd-btn" id="inStock'+collateralId+'" onclick="MfBusCollateralRelDetail.inStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+collateralType+'\');" title="入库">入库</button>';
							var outStockStr = '<button class="btn btn-link formAdd-btn" id="outStock'+collateralId+'" onclick="MfBusCollateralRelDetail.outStockKeepInfo(\''+collateralId+'\',\''+collateralName+'\',\''+collateralType+'\');" title="出库">出库</button>';
							var replaceCollateral = '<button class="btn btn-link formAdd-btn" id="replace'+collateralId+'" onclick="MfBusCollateralRelDetail.replaceCollateral(\''+collateralId+'\');" title="置换">置换</button>';
							var lowestWorthAdjust = '<button class="btn btn-link formAdd-btn" id="worthAdjust'+collateralId+'" onclick="MfMoveableCommon.lowestWorthAdjust(\''+collateralId+'\');" title="最低监管价值调整">监管调整</button>';
							var htmlStr ='<div class="dynamic-block" title="押品信息" name="collateralBase'+collateralId+'">'+
							'<div class="form-table"><div class="title">'+
							'<span class="formName" title="'+collateralName+'"><i class="i i-xing blockDian"></i>'+collateralNameCapture+'</span>'+
							releaseCollateralStr+inStockStr+outStockStr+replaceCollateral+lowestWorthAdjust+
							'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBase'+collateralId+'">'+
							'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
							'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
							'<div id="rotate-body'+collateralId+'\"></div></div></div></div></div>';
							$("div[name=collateralBase"+collateralId_old+"]").remove();
							$(".block-add").after(htmlStr);
							ShowPledgeDetailInfo.showPledgeInfo(collateralId,appId,data.classId);
							var tableList = data.collateralTableList;
							$.each(tableList,function(i,collateralTable){
								ShowPledgeDetailInfo.setBlock(collateralTable,i,data.classId);
							});
							ShowPledgeDetailInfo.cusAdd(collateralId);
							ShowPledgeDetailInfo.updateCollateral();
							_switchShowHide();
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		});
	};
	var _getPactInfo=function(){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfBusPact/getById?appId="+appId;
	};
	
	var _getCusInfo=function(){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&opType="+opType;
	};
	//应收账款折让
	var _rebatePledge=function(){
		top.rebate=false;
		top.window.openBigForm(webPath+'/mfReceivablesRebateApp/input?busPleId='+busCollateralId+"&appId="+appId,"应收账款折让申请",function(){
			if(top.rebate){
				_updateInfo();
				_controlOperate();
			}
		});
	};
	//折让确认
	var _rebateAffirm=function(){
		top.rebate=false;
		top.window.openBigForm(webPath+'/mfReceivablesRebateApp/inputAffirm?busPleId='+busCollateralId+"&appId="+appId,"应收账款折让确认",function(){
			if(top.rebate){
				_updateInfo();
				_controlOperate();
			}
		});
	}
	//应收账款争议处理
	var _disputedDealPledge=function(){
		top.disputedFlag=false;
		top.window.openBigForm(webPath+'/mfReceivablesDisputedApp/input?busPleId='+busCollateralId+"&appId="+appId,"应收账款争议申请",function(){
			if(top.disputedFlag){
				_updateInfo();
				_controlOperate();
			}
		});
	};
	//应收账款争议解除
	var _relieveDisputed=function(){
		top.relieveFlag=false;
		top.window.openBigForm(webPath+'/mfReceivablesDisputedApp/relieveInput?busPleId='+busCollateralId+"&appId="+appId,"应收账款争议解除",function(){
			if(top.relieveFlag){
				_updateInfo();
				_controlOperate();
			}
		});
	};
	//应收账款反转让
	var _buyBackPledge=function(){
		top.repoFlag=false;
		top.window.openBigForm(webPath+'/mfPleRepoApply/input?busPleId='+busCollateralId+"&appId="+appId,"应收账款反转让申请",function(){
			if(top.repoFlag){
				_updateInfo();
				_controlOperate();
			}
		});
	};
	//应收账款反转让确认
	var _receRepoAffirm=function(){
		top.repoAffirmFlag=false;
		top.window.openBigForm(webPath+'/mfPleRepoApply/inputAffirm?busPleId='+busCollateralId+"&appId="+appId,"应收账款反转让确认",function(){
			if(top.repoAffirmFlag){
				_updateInfo();
				_controlOperate();
			}
		});
	};
	var _setDisplayColor=function(){
		$("button[disabled=disabled]").attr("class","btn btn-link formAdd-btn set-disabled");
	};
	//质押证明详情
	var _getRecePledgeInfo=function(){
		top.window.openBigForm(webPath+'/mfReceivablesPledgeInfo/getById?busCollateralId='+busCollateralId+"&appId="+appId,"质押证明",function(){
		});
	};
	//转让证明详情
	var _getReceTranInfo=function(){
		top.window.openBigForm(webPath+'/mfReceivablesPledgeInfo/getById?busCollateralId='+busCollateralId+"&appId="+appId,"转让证明",function(){
		});
	};
	//折让历史
	var _getRebateHistory=function(){
		top.window.openBigForm(webPath+'/mfReceivablesRebateApp/getListPage?busPleId='+busCollateralId+"&appId="+appId,"折让历史",function(){
		});
	};
	//争议历史
	var _getDisputedHistory=function(){
		top.window.openBigForm(webPath+'/mfReceivablesDisputedApp/getListPage?busPleId='+busCollateralId+"&appId="+appId,"争议历史",function(){
		});
	};
	//调价历史
	var _getModifyHistory=function(){
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/getListPage?busPleId='+busCollateralId+"&appId="+appId,"价格变动",function(){
		});
	};
	//反转让确认信息
	var _getRepoAffirmInfo=function(){
		top.window.openBigForm(webPath+'/mfPleRepoApply/getById?busPleId='+busCollateralId+"&appId="+appId,"反转让确认信息",function(){
		});
	};
	//调价
	var _moveableModify=function(){
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/input?busPleId='+busCollateralId+"&appId="+appId,"调价申请",function(){
			_controlOperateForMoveable();
		});
	};
	//跌价补偿
	var _moveableCompentstate=function(){
		top.window.openBigForm(webPath+'/mfMoveableCompensation/input?busPleId='+busCollateralId+"&appId="+appId,"跌价补偿",function(){
			
		});
	};
	//跌价补偿确认
	var _moveableCompentstateConfirm=function(){
		top.window.openBigForm(webPath+'/mfMoveableCompensationConfirm/input?busPleId='+busCollateralId+"&appId="+appId,"跌价补偿确认",function(){
		});
	};
	//回购申请
	/*var _moveableBuyBack=function(){
		top.window.openBigForm(webPath+'/mfMoveableBuybackApply/input?busPleId='+busCollateralId+"&appId="+appId,"回购申请",function(){
		});
	};*/
	//回购申请确认
	var _moveableBuyBack=function(){
		top.window.openBigForm(webPath+'/mfMoveableBuybackApply/input?busPleId='+busCollateralId+"&appId="+appId,"回购申请确认",function(){
		});
	};
	return {
		init:_init,
		addCollateralInfo : _addCollateralInfo,
		inStockCollateralBatch:_inStockCollateralBatch,
		outStockCollateralBatch:_outStockCollateralBatch,
		showCollateralBaseInfo:_showCollateralBaseInfo,
		releaseCollateral:_releaseCollateral,
		inStockKeepInfo:_inStockKeepInfo,
		outStockKeepInfo:_outStockKeepInfo,
		replaceCollateral:_replaceCollateral,
		getPactInfo:_getPactInfo,
		getCusInfo:_getCusInfo,
		rebatePledge:_rebatePledge,
		disputedDealPledge:_disputedDealPledge,
		buyBackPledge:_buyBackPledge,
		receRepoAffirm:_receRepoAffirm,
		relieveDisputed:_relieveDisputed,
		getReceTranInfo:_getReceTranInfo,
		getRecePledgeInfo:_getRecePledgeInfo,
		getRebateHistory:_getRebateHistory,
		getDisputedHistory:_getDisputedHistory,
		getRepoAffirmInfo:_getRepoAffirmInfo,
		rebateAffirm:_rebateAffirm,
		moveableModify:_moveableModify,
		getModifyHistory:_getModifyHistory,
		moveableCompentstate:_moveableCompentstate,
		moveableCompentstateConfirm:_moveableCompentstateConfirm,
		moveableBuyBack:_moveableBuyBack,
		controlOperateForMoveable:_controlOperateForMoveable,
		updateCollateral:_updateCollateral,
		updateBaseCollateralInfo:_updateBaseCollateralInfo,
	};
}(window, jQuery);