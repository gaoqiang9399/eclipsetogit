;
var CollateralInsert = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		//将本业务登记过的押品信息查询出来然后可选关联
        $('input[name=associateId]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfBusCollateralDetailRel/getByAppId?appId="+appId,//请求数据URL
            valueElem:"input[name=associateId]",//真实值选择器
            changeCallback:function(elem) {//回调方法
                var associateId = elem.data("associateId").val();
                var pledgeName = elem.data("pledgeName").val();
            },
            tablehead:{//列表显示列配置
                "pledgeName" : "押品名称",
                "busCollateralId" : "关联编号"
            },
            returnData:{//返回值配置
                disName:"busCollateralId",//显示值
                value:"busCollateralId"//真实值
            }

		})

		//担保方式
		$("select[name=pledgeMethod]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			changeCallback : function (obj, elem) {
				CollateralCommon.refreshFormByVouType(busModel,appId,obj.val(),entrFlag);
			}
		});
		if(vouType=="2"){
			//var option = $("select[name=classNo]").find("option");
			//makeOptionsJQ(option, data.classNo, data.pledgeMethod);
			$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfAssureInfo/insertAjax");
			var assureType = $("select[name=assureType]").val();
            var options;
			if(assureType=="1"){
				options = $("select[name=assureCusType]").find("option");
				makeOptionsJQ(options,corpCusTypeStr);
				$("select[name=assureCusType] option:first").prop("selected", 'selected');
				if($("select[name=idType]").find("option").length==0){

				}else{
                    options = $("select[name=idType]").not(':disabled').find("option");
                    makeOptionsJQ(options,corpIdTypeStr);
                    $("select[name=idType] option:first").prop("selected", 'selected');
				}
			}else{
				options = $("select[name=assureCusType]").find("option");
				makeOptionsJQ(options,perCusTypeStr);
				$("select[name=assureCusType] option:first").prop("selected", 'selected'); 
				
				options = $("select[name=idType]").find("option");
				makeOptionsJQ(options,perIdTypeStr);
				$("select[name=idType] option:first").prop("selected", 'selected'); 
			}
			
			//客户所属区域选择组件
//			if (!$("input[name='careaProvice']").is(":hidden")){
//				$("input[name=careaProvice]").popupSelection({
//						ajaxUrl : webPath+"/nmdArea/getAreaListAllAjax",
//						searchOn : true,//启用搜索
//						multiple : false,//单选
//						valueClass : "show-text",//自定义显示值样式
//						ztree : true,
//						ztreeSetting : setting,
//						title : "客户所属地区",
//						handle : BASE.getIconInTd($("input[name=careaProvice]")),
//						changeCallback : function (elem) {
//						var areaNo=elem.data("values").val();
//							var node = elem.data("treeNode");
//							var parNode =  node.getParentNode();
//							var address=node.name;
//							while(parNode) {
//								address=parNode.name+address;
//								parNode=parNode.getParentNode();
//							}
//							BASE.removePlaceholder($("input[name=regHomeAddre]"));
//							$("input[name=regHomeAddre]").val(address);
//							$("input[name=careaCity]").val(address);
//							var $careaProviceObj = $("input[name=careaProvice]").parent(".input-group").find(".pops-label-alt");
//							$careaProviceObj.removeClass("pops-label-alt");
//							$careaProviceObj.html(address);
//					}
//				});
//			}
            if(entrFlag == "credit") {
                $("input[name=cusName]").attr("readOnly", "readOnly").unbind();
                $("input[name=cusName]").parent().find("div.pops-value").unbind();
                $("input[name=popscusNo]").remove();
            }
		}else if(vouType=='10'){//担保方式：最高额担保合同
			//修改表单提交action等
            $("#pledgeBaseInfoInsert").attr("action",webPath+"/mfHighGuaranteeContract/insertCollateralAjax");
			//绑定点击事件：弹层选择最高额担保合同
            $("input[name=showId]").popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
                searchOn: true, //启用搜索
                multiple: false, //false单选，true多选，默认多选
                ajaxUrl:webPath+"/mfHighGuaranteeContract/findByPageForSelectAjax?cusNo="+cusNo,//请求数据URL
                valueElem:"input[name=highGrtContractId]",//真实值选择器
                title: "选择最高额担保合同",//标题
            	addBtn:{"title":"新增","fun":function(){_goAddHighGrtContract(cusNo);}},
                changeCallback:function(elem){//回调方法
                    $("input[name=showId]").val(elem.data("text"));
                    var highGrtContractId = $("input[name=highGrtContractId]").val();
                    if(highGrtContractId!=''){
                    	//异步获取合同信息，并回显值
						$.ajax({
							url:webPath+"/mfHighGuaranteeContract/getByIdAjax",
							type:"post",
							data:{highGrtContractId:highGrtContractId},
							dataType:"json",
							success:function (data) {
								if(data.flag=='success'){
                                    var highGrtContract = data.highGrtContract;
                                    var endDate = highGrtContract.endDate;
                                    if(endDate!=''&&endDate.length==8){
                                        endDate = new Date(endDate.substr(0,4)+"/"+endDate.substr(4,2)+"/"+endDate.substr(6,2));
                                        var today = new Date();
                                        if(endDate<today){
                                            alert('该合同已过期，请重新选择');
                                            $("input[name=showId]").val('');
                                            $("input[name=highGrtContractId]").val('');
                                            return;
                                        }
                                        $("input[name=endDate]").val(endDate.format("yyyy-MM-dd"));
                                    }
                                    $("input[name=name]").val(highGrtContract.name);
                                    $("input[name=typeName]").val(highGrtContract.typeName);
                                    $("input[name=type]").val(highGrtContract.type);
                                    $("input[name=amt]").val(highGrtContract.amt);
                                    $("input[name=term]").val(highGrtContract.term);
                                    var startDate = highGrtContract.startDate;
                                    if(startDate!=''&&startDate.length==8){
                                        startDate = new Date(startDate.substr(0,4)+"/"+startDate.substr(4,2)+"/"+startDate.substr(6,2));
                                        $("input[name=startDate]").val(startDate.format("yyyy-MM-dd"));
                                    }
								}else{
									alert(data.msg,0);
                                    $("input[name=showId]").val('');
                                    $("input[name=highGrtContractId]").val('');
								}
                            },
							error:function(data){
                                alert(top.getMessage("FAILED_OPERATION"," "), 0);
                                $("input[name=showId]").val('');
                                $("input[name=highGrtContractId]").val('');
							}
						});
					}
                },
                tablehead:{//列表显示列配置
                    "showId":{"disName":"展示合同号","align":"left"},
                    "cusName":{"disName":"客户名称","align":"left"},
                    "name":{"disName":"合同名称","align":"left"}
                },
                returnData:{//返回值配置
                    disName:"showId",//显示值
                    value:"highGrtContractId"//真实值
                }
            });
		}else{
			$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfBusCollateralRel/insertCollateralAjax");
			//押品新组件
            if (!$("input[name='classId']").is(":hidden")){
                $("input[name=classId]").popupSelection({
                    searchOn:true,//启用搜索
                    inline:true,//下拉模式
                    multiple:false,//多选选
                    items:ajaxData.collClass,
                    changeCallback : function (obj, elem) {
                        $("input[name=classSecondName]").val(obj.data("text"));
                        CollateralCommon.freshPledgeBaseForm(busModel,appId,entrFlag);
                    }
                });
            }
			//人民币状态
			$('select[name=extDic02]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false //单选
			});
			//支付方式
			$('select[name=extDic03]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false //单选
			});
			if(collateralType=="pledge"){
				$("input[name=pledgeName]").after('<span class="input-group-addon">'+
					'<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
            }
			$("input[name=cusName]").attr("readOnly","readOnly").unbind();
			$("input[name=cusName]").parent().find("div.pops-value").unbind();
			$("input[name=popscusNo]").remove();
		}
		//支持跳过时，跳过按钮显示
		if(supportSkipFlag=="1"){
			$(".skipButton").show();
		}else{
			$(".skipButton").hide();
		}
		
		//切换保证方式时,清空保证人名称和证件号码
		$('select[name=assureType]').change(function(){
			$('input[name=assureName]').parent().find(".pops-value").remove();
			$('input[name=assureName]').val('');
			$('input[name=idNum]').val('');
			$('input[name=assureName]').show();
			if(changeCusRelFlag == '1'){//切换关系标志
				$('input[name=relation]').val('');
				$('input[name=relation]').parent().find(".pops-value").html("");
				var assureTypeVal = $('select[name=assureType]').val();
				changeRelation(assureTypeVal);
			}
		});
		
		//关系下拉框
//		$("select[name=relation]").popupSelection({
//			searchOn : false,//启用搜索
//			inline : true,//下拉模式
//			multiple : false,//多选
//			items:ajaxData.cusRelComType,
//			title:"关系",
//			labelShow: false,
//			changeCallback : function (obj, elem) {
//			},
//		});

		$("select[name=assureCusType]").bind("change", function() {// 保证人类型change事件
			var cusTypeSet = CollateralCommon.getCusTypeSet($(this).val());// 根据客户类别查询业务身份(parm_dic.key_name = 'CUS_TYPE_SET')
            if(typeof(funcEntryFlag)!='undefined'&&funcEntryFlag=='highGrtContract_addSub'){
            	//说明此脚本是从最高额担保合同添加明细记录页面加载的
                //主要控制担保金额可编辑。因此时尚未关联业务，故需要手动输入担保公司的担保金额。
                if (cusTypeSet == '9') {// 担保公司，则保证人改为只读，必须从系统中选择。
                    $("input[name='assureName']").attr("readonly", true);
                } else {
                    $("input[name='assureName']").attr("readonly", false);
                }
			}else{
                if (cusTypeSet == '9') {// 担保公司必须是全额担保
                    $("input[name='assureAmt']").val(appAmt);
                    $("input[name='assureAmt']").attr("readonly", true);
                } else {
                    $("input[name='assureAmt']").val("");
                    $("input[name='assureAmt']").attr("readonly", false);
                }
			}
		});
	};

	var _goAddHighGrtContract=function(cusNo){
		top.window.openBigForm(webPath+"/mfHighGuaranteeContract/input?cusNo="+cusNo,"新增最高额担保合同",function(){});
	};

	//切换关系下拉框内容
	var changeRelation = function(assureTypeVal){
		if(assureTypeVal=='1'){//企业
			$("select[name=popsrelation]").popupSelection("updateItems",ajaxData.cusRelComType);
		}else{//个人
			$("select[name=popsrelation]").popupSelection("updateItems",ajaxData.cusRelPersType);
		}
	};
	
	var _insertCollateralBase=function(obj,skipFlag){

		//跳过担保登记，直接提交不验证
		if(skipFlag=="1"){
            if(entrance == "credit" || entrFlag == "credit"){
                _submitCreditProcessAjax();
            }else{
                _submitBussProcessAjax();
            }
		}else{
            var assureNo=$("[name=assureNo]").val();//证件号唯一校验
            if(""==assureNo){
                var unValMsg = {};
                var unVal = $("[name=idNum]").val();
                var unValCheckResult = doCheckUniqueIdNum(unVal);
                var checkFlag = unValCheckResult.split("&")[0];
                if (checkFlag == "1") {
                    unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
                    window.top.alert("该客户已存在，请选择已有客户作为保证人", 1);
                    return false;
                }
            }
			var flag = submitJsMethod($(obj).get(0), '');
            $("select[name='idType']").attr("disabled",false);
            var assureTypeFalg = $("select[name=assureType]").val();
            var assureAmt = $("input[name=assureAmt]").val();
            if("3" == assureTypeFalg){//联保体校验
                $.ajax({
                    url : webPath+"/mfBusAlliance/getAllianceByCusNo",
                    data : {cusNo : cusNo,assureAmt : assureAmt},
                    type : "POST",
                    dataType : "json",
                    success : function(data) {
                        if (data.flag == "success") {
                            //余额充足,直接提交
                            _insertBase(obj,skipFlag);
                        } else if (data.flag == "error") {
                            alert(data.msg, 0);
                        }
                    },
                    error : function(data) {
                        alert(top.getMessage("FAILED_OPERATION"," "), 0);
                    }
                });
            }else if (flag) {
            	_insertBase(obj,skipFlag);

			}
		}
	};
	var _insertBase=function(obj,skipFlag){
		if(typeof(collateralType)=='undefined' || collateralType==''){
			collateralType = 'pledge';
		}
		
		if(entrFlag == "business"){
			var assureNo = $("input[name=assureNo]").val();
			if(assureNo == ''){
				assureNo = "newAssure";
			}
			var parmData = {'nodeNo':nodeNo, 'assureNo': assureNo, 'appId': appId,'relNo':appId};
			$.ajax({
				url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
				type : "post",
				data : {ajaxData: JSON.stringify(parmData)}, 
				dataType : "json",
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				},
				success : function(data) {
					if (data.exsitRefused == true) {// 存在业务拒绝
						top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息',function(){});
					}else {
                        var url = $(obj).attr("action");
                        var dataParam = JSON.stringify($(obj).serializeArray());
                        var param = {ajaxData : dataParam,appId:appId,entrFlag:entrFlag,isQuote:isQuote,skipFlag:skipFlag,extensionApplyId:extensionApplyId,'collateralType':collateralType,'nodeNo':nodeNo}
                        jQuery.ajax({
                            url : url,
                            data : param,
                            type : "POST",
                            dataType : "json",
                            success : function(data) {
                                if (data.flag == "success") {
                                    if(top.collaFlag!=undefined){
                                        top.collaFlag=true;
                                    }
                                    if(top.addCollateral!=undefined){
                                        top.addCollateral=true;
                                        top.collateralId =data.pledgeNo;
                                        top.collateralName =data.pledgeName;
                                        top.classId=data.classId;
                                        top.vouType=data.pledgeMethod;
                                    }
                                    if(top.addCreditCollaFlag!=undefined){
                                        top.addCreditCollaFlag=true;
                                        top.creditAppId=appId;
                                    }
                                    if(top.extenFlag!=undefined){
                                        top.skipFlag=skipFlag;
                                        top.extenFlag=true;
                                        top.addCollateral=true;
                                        top.pledgeId=data.pledgeNo;
                                    }
                                    if(data.classId=="17062211213501712" || data.classId=="17061309580618115"){
                                        if(evalState=="1" && skipFlag=="0"){
                                            _onlineEvalStorage(data.pledgeNo)//在线估值结果存储
                                        }
                                        if(evalState=="2" && skipFlag=="0"){
                                            _assessmentApply(data.pledgeNo);//在线估值未出值已转人工
                                        }
                                        if(skipFlag=="2"){
                                            _assessmentApply(data.pledgeNo);//人工申请
                                        }
                                    }
                                    alert(data.msg, 1);
                                    myclose_click();
                                } else if (data.flag == "error") {
                                    alert(data.msg, 0);
                                }
                            },
                            error : function(data) {
                                alert(top.getMessage("FAILED_OPERATION"," "), 0);
                            }
                        });
                    }
				}
			});
		}else{
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			var param =  {ajaxData : dataParam,appId:appId,entrFlag:entrFlag,isQuote:isQuote,skipFlag:skipFlag,extensionApplyId:extensionApplyId,'collateralType':collateralType};
			jQuery.ajax({
				url : url,
				data : param,
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					if (data.flag == "success") {
						if(top.collaFlag!=undefined){
							top.collaFlag=true;
						}
						if(top.addCollateral!=undefined){
							top.addCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId=data.classId;
							top.vouType=data.pledgeMethod;
						}
						if(top.addCreditCollaFlag!=undefined){
							top.addCreditCollaFlag=true;
							top.creditAppId=appId;
						}
						if(top.extenFlag!=undefined){
							top.skipFlag=skipFlag;
							top.extenFlag=true;
							top.addCollateral=true;
							top.pledgeId=data.pledgeNo;
						}
                        if(data.classId=="17062211213501712" || data.classId=="17061309580618115"){
                            if(evalState=="1" && skipFlag=="0"){
                                _onlineEvalStorage(data.pledgeNo)//在线估值结果存储
                            }
                            if(evalState=="2" && skipFlag=="0"){
                                _assessmentApply(data.pledgeNo);//在线估值未出值已转人工
                            }
                            if(skipFlag=="2"){
                                _assessmentApply(data.pledgeNo);//人工申请
                            }
                        }
						alert(data.msg, 1);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	var _replaceCollateral=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					collateralId_old:collateralId_old,
					appId:appId,
					entrFlag:entrFlag,
					isQuote:isQuote
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(top.repCollateral!=undefined){
							top.repCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId =data.classId;
						}
						alert(top.getMessage("SUCCEED_REPLACE"), 1);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	//选择押品类型
	var _selectCollateralTypeData=function(_setCollateralType){
		var vouType = $("input[name=vouType]").val();
		if(vouType==undefined){//如果取不到，就查询全部押品类别
			vouType = "";
		}
		if(vouType=="5"){
			vouType ="4";
		}
		selectCollateralTypeDataDialog(_setCollateralType, vouType);
	};
	//选择押品类型回调设置表单中押品类型相关字段
	var _setCollateralType=function(data){
		if(entrance=="credit"){//如果是授信登记押品，担保方式隐藏
			$("input[name=pledgeMethod]").val(data.vouType);
		}
		$("input[name=classId]").val(data.classId);
		$("input[name=collateralTypeName]").val(data.className);
	};
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		var pledgeMethod = $("input[name=pledgeMethod]").val();
		selectCollateralDataDialog(_setCollateralData,cusNo,appId,pledgeMethod);
	};
	//选择客户押品回调设置押品相关字段。
	var _setCollateralData=function(data){
		var pledgeNo = data.pledgeNo;
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/getAddPledgeBaseHtmlAjax",
			data : {pledgeNo:pledgeNo},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#pledgeBaseInfoInsert").find("table").remove();
					$("#pledgeBaseInfoInsert").find(".hidden-content").remove();
					$("#pledgeBaseInfoInsert").html(data.htmlStr);
					isQuote="1";
					$("input[name=classId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//下拉模式
						multiple:false,//多选选
						items:data.collClass,
					});
					$("input[name=classId]").parents("td").find(".pops-select").remove();

                    if(collateralType=="pledge"){
                        //添加押品放大镜
						$("input[name=pledgeName]").after('<span class="input-group-addon">'+
							'<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
                    }
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//信用担保方式情况下，跳过担保登记业务节点直接进行下一步
	var _submitBussProcessAjax = function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralRel/submitBussProcessAjax",
			data : {appId:appId,extensionApplyId:extensionApplyId},
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					if(top.collaFlag!=undefined){
						top.collaFlag=true;
						top.vouType=vouType;
						top.vouTypeShort=vouTypeShort;
                        top.skipFlag=true;
					}

					if(top.extenFlag!=undefined){
						top.skipFlag=1;
						top.extenFlag=true;
						top.addCollateral=true;
					}
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete : function(){
				LoadingAnimate.stop();
			}
		});
	};
	var _submitCreditProcessAjax = function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralRel/submitCreditProcessAjax",
			data : {appId:appId},
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					top.skipFlag=1;
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete : function(){
				LoadingAnimate.stop();
			}
		});
    };

	/** 根据存单编号查询存单信息 <span class='btn block-view' onclick='CollateralInsert.depositQuery()'>存单查询</span> */
    var _depositQuery = function (thiz) {
        var pleCertificateOwner = $("[name='pleCertificateOwner']").val();
        if (!pleCertificateOwner) {
            alert(top.getMessage("NOT_FORM_EMPTY", "存单编号"), 0);
            return;
		}
        jQuery.ajax({
            url: webPath + "/mfBusCollateralRel/depositQuery",
            data: {'pleCertificateOwner': pleCertificateOwner},
            beforeSend: function () {
                LoadingAnimate.start();
            }, success: function (data) {
                if (data.flag == "success") {
                    $("[name='extOstr03']").val(data.sys25);// 户名
                    $("[name='pleOriginalValue']").val(data.sys41);// 存入金额(可用余额)
                    $("[name='extNum01']").val(data.sys85);// 存款利率(利率 活期或定期利率)
                    $("[name='extOstr02']").val(data.sys105j);// 存期
                    $("[name='pleValueDate']").val(data.sys1053);// 存入日(开户日期)
                    $("[name='pleExpiryDate']").val(data.sys1054);// 到期日期

                    var sys40; // 账面余额
                    var sys42; // 定期余额

                    var sys2; // 开户机构
                    var sys5; // 交易日期
                    var sys12; // 错误码 成功-0000
                    var sys13; // 错误信息
                    var sys20; // 介质属性 1-存折类, 2-存单类, 3-磁条卡类, 4-I卡类, 5-复合卡类, 6-其他
                    var sys22; // 冻结状态
                    var sys27; // 地址
                    var sys28; // 客户号
                    var sys46; // 交易日期
                    var sys62; // 证件类型及号码 证件类型见名词解释 查询账号信息时返回 第一位证件类型 后面为证件号码
                    var sys66; // 账户状态 1-正常, 0-开户待确认, 3-挂失结清, 4-开户更正, 5-临时销户, *-销户, #-计息结束
                    var sys67; // 客户类型 查询成功时返回: 1-个人, 2-对公, 3-内部账
                    var sys69; // 介质状态 0-正常, 1-挂失, 2-挂失换证, 3-正常换证, 4-已经部提, 5:撤销, *-销户, 7-(批量开户)未激活, 6-损坏更换
                    var sys70; // 账户类型 标明账户所属类型（普通活期、结算账户、卡、内部账户等等）
					var sys72; // 止付状态 0-正常, 1-全部止付, 2-只进不出, 3-部分止付
                    var sys83; // 机构名称
                } else if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", "查询存单信息"), 0);
            }, complete: function () {
                LoadingAnimate.stop();
            }
        });
    };

    //检查保证人证件号唯一性
    var _checkUniqueness=function () {
    	var assureNo=$("[name=assureNo]").val();
    	if(""==assureNo){
            var unValMsg = {};
            var unVal = $("[name=idNum]").val();
            var unValCheckResult = doCheckUniqueIdNum(unVal);
            var checkFlag = unValCheckResult.split("&")[0];
            if (checkFlag == "1") {
                unValMsg.idNumResultMsg = unValCheckResult.split("&")[1];
                window.top.alert("该客户已存在，请选择已有客户作为保证人", 1);
            }
		}
    }

	//房产人工估值申请
    var _assessmentApply=function (pledgeNo) {
    	if(evalState=="2"){//在线评估未出数据已转人工
    		var cityCode=$("input[name=extOstr12]").val();
            var pledgeRate=$("input[name=pledgeRate]").val();
            $.ajax({
                url : webPath+"/pledgeBaseInfo/artificialApplyInsert",
                data:{cityCode:cityCode,pledgeNo:pledgeNo,evalTaskNum:evalTaskNum,pledgeRate:pledgeRate},
                type : "POST",
                dataType : "json",
                success : function(data) {
                    alert(data.msg,1);
				},error:function(){
                    alert(top.getMessage("FAILED_OPERATION","在线估值转人工保存"),0);
                }
            })
		}else {
            var flag = submitJsMethod($("#pledgeBaseInfoInsert").get(0), '');
            if(flag){
                var dataParam =JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
                $.ajax({
                    url : webPath+"/pledgeBaseInfo/assessment",
                    data:{ajaxData:dataParam,pledgeNo:pledgeNo,artificial:"1"},
                    type : "POST",
                    dataType : "json",
                    success : function(data) {
                    	if("0"==data.evalState){
                            alert(data.msg,0);
						}else{
                            alert(data.msg,1);
						}
                    },error:function(){
                        alert(top.getMessage("FAILED_OPERATION","人工申请估值"),0);
                    }
                })
            }
		}
    }

	//房产在线估值结果保存
    var _onlineEvalStorage=function (pledgeNo) {
        var dataParam =JSON.stringify($("#onlineDlevalinfo0002").serializeArray());
        $.ajax({
            url : webPath+"/evalInfo/resultStorage",
            data:{ajaxData:dataParam,pledgeNo:pledgeNo},
            type : "POST",
            dataType : "json",
            success : function(data) {
                alert(data.msg,1);
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","在线估值保存"),0);
            }
        })
    }
    //跳转评估页面方法
    var _changeHouseFormShow = function (){
        $("#pledgeBaseInfoDiv").css('display','none');
        $("#normal").css('display','none');

        $("#houseEvalDiv").css('display','block');
        $("#saveBtnHouseEval").css('display','block');
        $(top.window.document).find("#myModalLabel").text('房产评估');
    }
    //返回关闭方法
    var _changeFormDisplay = function () {
        $("#houseEvalDiv").css('display','none');
        $("#saveBtnHouseEval").css('display','none');

        $("#houseEvalRDiv").css('display','none');
        $("#saveBtnRHouseEval").css('display','none');

        $("#pledgeBaseInfoDiv").css('display','block');
        $("#normal").css('display','block');
        $(top.window.document).find("#myModalLabel").text(title);
    }

    //跳转人工方法
    var _changeRengong = function () {
        $("#houseEvalDiv").css('display','none');
        $("#saveBtnHouseEval").css('display','none');

        $("#pledgeBaseInfoDiv").css('display','none');
        $("#normal").css('display','none');

        $("#houseEvalRDiv").css('display','block');
        $("#saveBtnRHouseEval").css('display','block');
    }

    var _ajaxSaveHouseEval = function (obj,flag) {
        var relNo = $("input[name=pledgeNo]").val();
        var returnData = evalHouseInfo(obj,flag,relNo);
        if(returnData.flag == 'success'){
            _changeFormDisplay();
            $("input[name='pleOriginalValue']").val(returnData.TotalPrice);
            $("input[name='valuationPrice']").val(returnData.UnitPrice);
        }
    };
	//行政区域回调
    function _selectHouseCityCallBack(areaInfo){
        $("input[name=cityCode]").val(areaInfo.disNo);
        $("input[name=cityName]").val(areaInfo.disName);
    };

    return {
		init:_init,
		insertCollateralBase:_insertCollateralBase,
		replaceCollateral:_replaceCollateral,
		selectCollateralTypeData:_selectCollateralTypeData,
		setCollateralType:_setCollateralType,
		selectCollateralData:_selectCollateralData,
		setCollateralData:_setCollateralData,
        depositQuery: _depositQuery,
        checkUniqueness:_checkUniqueness,
        assessmentApply:_assessmentApply,
        onlineEvalStorage:_onlineEvalStorage,
        changeHouseFormShow:_changeHouseFormShow,
        changeFormDisplay:_changeFormDisplay,
        changeRengong:_changeRengong,
        ajaxSaveHouseEval:_ajaxSaveHouseEval,
        selectHouseCityCallBack:_selectHouseCityCallBack

	};
}(window, jQuery);
