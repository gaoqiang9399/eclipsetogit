;
var CollateralCommon = function(window, $){
    _init=function(){
        _changeRelation();
        _assureTypeDo();
    };

	var _freshPledgeBaseForm=function(busModel,appId,entrFlag){
		var tmpClassId = "";
		classId = $("#pledgeBaseInfoInsert").find("[name=classId]").val();
		var pledgeMethod = $("#pledgeBaseInfoInsert").find("[name=pledgeMethod]").val();
		var dataParam = JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
		$.ajax({
			url:webPath+"/pledgeBaseInfo/freshPledgeFormAjax",
			data:{classId:classId,ajaxData:dataParam,busModelNo:busModel,appId:appId,entrFlag:entrFlag,pledgeMethod:pledgeMethod,classFirstNo:classFirstNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				// debugger
				if(data.flag == "error"){
					alert(data.msg, 0);
				}else{
					tmpClassId = data.classId;
					$("#pledgeBaseInfoInsert").empty().html(data.formHtml);

                    $(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
                    $(".anchor-ctrl select").each(function(){handleAnchorFun(this);});

					$('select[name=pledgeMethod]').popupSelection({
						searchOn: false, //启用搜索
						inline: true, //下拉模式
						multiple: false, //单选
						changeCallback : function (obj, elem) {
							_refreshFormByVouType(busModel,appId,obj.val(),entrFlag);
						}
					});
                    if (!$("input[name='classId']").is(":hidden")) {
                        $("input[name=classId]").popupSelection({
                            searchOn: true,//启用搜索
                            inline: true,//下拉模式
                            multiple: false,//多选选
                            items: data.collClass,
                            changeCallback: function (obj, elem) {
                                classId = $("input[name=classId]").val();
                                $("input[name=classSecondName]").val(obj.data("text"));
                                /*if (tmpClassId != classId) {

                                }*/
                                //无需判断选择的押品类别和当前类型不一样才切换表单
                                tmpClassId = classId;
                                _freshPledgeBaseForm(busModel, appId, entrFlag);
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
					//客户新组件
					/*$("#pledgeBaseInfoInsert").find('input[name=cusName]').popupList({//随便填写一个隐藏域，防止这个字段不能填写的问题
						searchOn: true, //启用搜索
						multiple: false, //false单选，true多选，默认多选
						ajaxUrl:webPath+"/mfCusCustomer/findByPageForSelectAjax?removeCusId=&cusBaseType=1,2",//请求数据URL
				//		handle:BASE.getIconInTd($("input[name=cusName]")),//其他触发控件
						valueElem:"input[name=cusNo]",//真实值选择器
						title: "选择客户",//标题
						changeCallback:function(elem){//回调方法
							var cusNo = elem.data("values").val();
							$("input[name=cusName]").val(elem.data("text"));
							$("input[name=certificateName]").val(elem.data("text"));
							$.ajax({
								url:webPath+"/pledgeBaseInfo/getPledgeShowNoAjax",
								data:{cusNo:cusNo},
								type:"POST",
								dataType:"json",
								beforeSend:function(){},
								success:function(data){
									$("input[name=pledgeShowNo]").val(data.pledgeShowNo);
								},
								error:function(data){
									alert(top.getMessage("FAILED_OPERATION"," "), 0);
								}
							});
						},
						tablehead:{//列表显示列配置
							"cusNo":{"disName":"客户编号","align":"center"},
							"cusName":{"disName":"客户名称","align":"center"}
						},
						returnData:{//返回值配置
							disName:"cusName",//显示值
							value:"cusNo"//真实值
						}
					});*/
//					$("#pledgeBaseInfoInsert").find("input[name=cusNo]").popupSelection({
//						searchOn:true,//启用搜索
//						inline:true,//下拉模式
//						multiple:false,//多选选
//						items:data.cus,
//						ajaxUrl: false, //异步url
//						valueClass: false, //String类型，自定义显示值class
//						selectClass: false, //String类型，自定义选框class
//						addBtn: false, //false-不启用新增按钮,{"title":"按钮名称","fun":function(){//点击执行}}
//						labelEdit: false, //选项编辑回调函数function(d){console.log(d)};
//						labelShow: true, //是否在选择区域显示，已选择项
//						cellCount: 3, //每行显示选项个数
//						ztree: false, //是否使用ztree
//						ztreeSetting: false, //ztree配置
//						title: false, //标题
//						handle: false, //触发器
//						initFlag: false,
//						changeCallback : function (obj, elem) {
//							var cusNo = obj.val();
//							//$("input[name=cusNo]").val(cusNo);
//							$("input[name=cusName]").val(obj.data("text"));
//							if("yango" != data.companyId){
//								$("input[name=certificateName]").val(obj.data("text"));
//							}
//							$.ajax({
//								url:webPath+"/pledgeBaseInfo/getPledgeShowNoAjax",
//								data:{cusNo:cusNo},
//								type:"POST",
//								dataType:"json",
//								beforeSend:function(){},
//								success:function(data){
//									$("input[name=pledgeShowNo]").val(data.pledgeShowNo);
//								},
//								error:function(data){
//									alert(top.getMessage("FAILED_OPERATION"," "), 0);
//								}
//							});
//						}
//					});
					
					if(entrFlag!=undefined&&entrFlag!=""){
						$("input[name=cusName]").attr("readOnly","readOnly").unbind();
						$("input[name=cusName]").parent().find("div.pops-value").unbind();
						$("input[name=popscusNo]").remove();
						if(collateralType=="pledge"){
                            //添加押品放大镜
                            $("input[name=pledgeName]").after('<span class="input-group-addon">'+
                                '<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
                        }
					}
                    _initOnCardsCity();
                    //进件环节客户不允许选择客户,资产性质默认为客户押品且只读
                    if(typeof(entrance) != "undefined" &&entrance !="collateral"){
                        $("input[name=cusName]").removeAttr("onclick");
                        $("select[name=assetProperty]").prop("disabled",true);
                    }
                  /*  if(classId=="17062211213501712" || classId=="17061309580618115"){//房产评估按钮控制
                        $("#artificial").attr("style","display:block;");
                        $("#normal").attr("style","display:none;");
                        $("#valuationResults").attr("style","display:block;");
                    }else{
                        $("#artificial").attr("style","display:none;");
                        $("#normal").attr("style","display:block;");
                        $("#valuationResults").attr("style","display:none;");

					}*/
				}
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//发票面额变化
	var _invoiceAmtChange=function(){
		//发票面额
		var pleInvoiceAmount=$("input[name=pleInvoiceAmount]").val();
        //应收账款金额
        $("input[name=pleOriginalValue]").val(pleInvoiceAmount);
        $("input[name=pleOriginalValue]").focus();
        $("input[name=pleOriginalValue]").blur();
		//应收账款净值
		$("input[name=pleValue]").val(pleInvoiceAmount);
		$("input[name=pleValue]").focus();
		$("input[name=pleValue]").blur();

	};
	//担保方式处理
	var _assureTypeDo=function(){
		let pledgeMethod=$("[name=pledgeMethod]").val();
		if(pledgeMethod == "2"){
			if("12" != busModel){
				$("[name=assureType] option[value='4']").remove();
			}
		}
	};
	//应收账款净值变化
	var _receAmtChange=function(){
		//发票面额
		var pleInvoiceAmount=$("input[name=pleInvoiceAmount]").val();
		//应收账款净值
		var pleValue=$("input[name=pleValue]").val();
		pleValue= pleValue.replace(/,/g,"");
		pleInvoiceAmount= pleInvoiceAmount.replace(/,/g,"");
		if(parseFloat(pleInvoiceAmount)<parseFloat(pleValue)){
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=pleValue]").attr("title"),"timeTwo":$("input[name=pleInvoiceAmount]").attr("title")}),0);
			$("input[name=pleValue]").val($("input[name=pleInvoiceAmount]").val());
		}
	};
    //校验金额大小（转让账款金额<=应收账款金额<=票面金额）
    var  _checkAmt = function(obj){
		//票面金额
		var pleInvoiceAmount = $("input[name=pleInvoiceAmount]").val().replace(/,/g,"");
		if(pleInvoiceAmount==""){
			pleInvoiceAmount="0.00";
		}
		//应收账款金额（应收账款金额<=票面金额）
		var pleOriginalValue = $("input[name=pleOriginalValue]").val().replace(/,/g,"");
		if(pleOriginalValue==""){
			pleOriginalValue="0.00";
		}
		if(parseFloat(pleInvoiceAmount)<parseFloat(pleOriginalValue)){
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=pleOriginalValue]").attr("title"),"timeTwo":$("input[name=pleInvoiceAmount]").attr("title")}),0);
			$("input[name=pleOriginalValue]").val($("input[name=pleInvoiceAmount]").val());
		}

		//转让金额（转让金额<=应收账款金额）
		var pleValue=$("input[name=pleValue]").val().replace(/,/g,"");
		if(pleValue==""){
			pleValue="0.00";
		}
		if(parseFloat(pleOriginalValue)<parseFloat(pleValue)){
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=pleValue]").attr("title"),"timeTwo":$("input[name=pleOriginalValue]").attr("title")}),0);
			$("input[name=pleValue]").val($("input[name=pleOriginalValue]").val());
		}

    };

	//切换担保方式异步刷新表单
	var _refreshFormByVouType = function(busModel,appId,vouType,entrFlag){
		var dataParam = JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
		var pledgeNo = $("input[name='pledgeShowNo']").val();
		$.ajax({
			url:webPath+"/mfBusCollateralRel/refreshFormByVouTypeAjax",
			data:{vouType:vouType,ajaxData:dataParam,busModelNo:busModel,appId:appId,entrFlag:entrFlag,entrance:entrance},
			success:function(data){
				if(data.flag == "error"){
					alert(data.msg, 0);
				}else{
					$("#pledgeBaseInfoInsert").empty().html(data.formHtml);

					$("#formHouseEval").empty().html(data.houseEvalBaseHtml);
					$("#formHouseEvalMan").empty().html(data.houseEvalBaseManHtml);

                    $(".anchor-ctrl select").change(function(){handleAnchorFun(this);});
                    $(".anchor-ctrl select").each(function(){handleAnchorFun(this);});

					if(pledgeNo){//解决押品类别从保证切换至抵质押时，押品编号没有带出来问题
						$("input[name=pledgeShowNo]").val(pledgeNo);
					}else{
						$("input[name=pledgeShowNo]").val(data.pledgeShowNo);
					}
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
					$('select[name=pledgeMethod]').popupSelection({
						searchOn: true, //启用搜索
						inline: true, //下拉模式
						multiple: false, //单选
						changeCallback : function (obj, elem) {
							_refreshFormByVouType(busModel,appId,obj.val(),entrFlag);
						}
					});
                    // //控制人工按钮显示
                    // if(data.classId=="17062211213501712" || data.classId=="17061309580618115"){//房产评估按钮控制
                    //     classId=data.classId;
                    //     $("#artificial").attr("style","display:block;");
                    //     $("#normal").attr("style","display:none;");
                    //     $("#valuationResults").attr("style","display:block;");
                    // }else{
                    //     $("#artificial").attr("style","display:none;");
                    //     $("#normal").attr("style","display:block;");
                    //     $("#valuationResults").attr("style","display:none;");
                    //
                    // }
					if(data.vouType=="2"){
						$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfAssureInfo/insertAjax");
						var assureType = $("select[name=assureType]").val();
                        var options;
						if(assureType=="1"){
							options = $("select[name=assureCusType]").find("option");// debugger;
							makeOptionsJQ(options,data.corpCusTypeStr);
							$("select[name=assureCusType] option:first").prop("selected", 'selected'); 
							
							options = $("select[name=idType]").find("option");
							makeOptionsJQ(options,data.corpIdTypeStr);
							$("select[name=idType] option:first").prop("selected", 'selected');
						}else{
							options = $("select[name=assureCusType]").find("option");
							makeOptionsJQ(options,data.perCusTypeStr);
							$("select[name=assureCusType] option:first").prop("selected", 'selected'); 
							
							options = $("select[name=idType]").find("option");
							makeOptionsJQ(options,data.perIdTypeStr);
							$("select[name=idType] option:first").prop("selected", 'selected'); 
						}
                        _changeRelation();//更改反担保方式刷新保证人关系
						corpCusTypeStr = data.corpCusTypeStr;
						corpIdTypeStr = data.corpIdTypeStr;
						perCusTypeStr = data.perCusTypeStr;
						perIdTypeStr = data.perIdTypeStr;
                        IdentityCheck.assureInit();
                        $("select[name=assureCusType]").bind("change", function() {// 保证人类型change事件
						var cusTypeSet = CollateralCommon.getCusTypeSet($(this).val());// 根据客户类别查询业务身份(parm_dic.key_name = 'CUS_TYPE_SET')

                            if (cusTypeSet == '9') {// 担保公司，则保证人改为只读，必须从系统中选择。
                                $("input[name='assureName']").attr("readonly", true);
                            } else {
                                $("input[name='assureName']").attr("readonly", false);
                            }

							if (cusTypeSet == '9') {// 担保公司必须是全额担保
							$("input[name='assureAmt']").val(appAmt);
							$("input[name='assureAmt']").attr("readonly", true);
							} else {
								$("input[name='assureAmt']").val("");
								$("input[name='assureAmt']").attr("readonly", false);
							}
						});

                        if(entrFlag == "credit") {
                            $("input[name=cusName]").attr("readOnly", "readOnly").unbind();
                            $("input[name=cusName]").parent().find("div.pops-value").unbind();
                            $("input[name=popscusNo]").remove();
                        }
                        _assureTypeDo();
					}else if(vouType=='10'){
						//--待修改-haoy-- 重新绑定事件等操作
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
						if(appId == ""){
							$("#pledgeBaseInfoInsert").attr("action",webPath+"/pledgeBaseInfo/insertAjax");
						}else{
							$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfBusCollateralRel/insertCollateralAjax");
						}
                        $('select[name=classModel]').popupSelection({
                            searchOn: true, //启用搜索
                            inline: true, //下拉模式
                            multiple: false //单选
                        });
						$("input[name=classId]").popupSelection({
							searchOn:true,//启用搜索
							inline:true,//下拉模式
							multiple:false,//多选选
							items:data.collClass,
							changeCallback : function (obj, elem) {
								$("input[name=classSecondName]").val(obj.data("text"));
								_freshPledgeBaseForm(busModel,appId,entrFlag);
							}
						});
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
						if(entrFlag!=undefined && entrFlag!=""){
							$("input[name=cusName]").attr("readOnly","readOnly").unbind();
							$("input[name=cusName]").parent().find("div.pops-value").unbind();
							//所有权人名称默认带出本人，允许修改
//							if(busModel!='26'){//中茂押品所有权人允许修改
//								$("input[name=certificateName]").attr("readOnly","readOnly").removeAttr("onclick");
//							}
							$("input[name=popscusNo]").remove();
							//添加押品放大镜

                                $("input[name=pledgeName]").after('<span class="input-group-addon">'+
                                    '<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
						}
						if(entrance !="collateral"){
                            $("input[name=cusName]").removeAttr("onclick");
						}
					}
				}
			},
			error:function(a,b,c){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	
	};

	var _goAddHighGrtContract=function(cusNo){
		top.window.openBigForm(webPath+"/mfHighGuaranteeContract/input?cusNo="+cusNo,"新增最高额担保合同",function(){});
	}

	/** 根据客户类别查询业务身份 */
	var _getCusTypeSet = function (cusType) {
		var result;

		$.ajax({// 根据客户类别查询业务身份
			url : webPath+"/mfCusType/getCusTypeSetAjax",
			data : {
				"typeNo" : cusType
			},
			type : "POST",
			dataType : "json",
			async : false,
			beforeSend : function() {
			},
			success : function(data) {
				result = data.cusTypeSet;
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});

		return result;
	};

	//保证人弹出层数据
	var _selectAssureList = function(){
		var obj= $('input[name=assureName]').not(':disabled');
		var cusBaseType = $('select[name=assureType]').val();
        var cusType = $("select[name=assureCusType]").val();
        var isLong = $("select[name=isLong]").val();
        if(cusBaseType == "4"){//保证金担保
            cusBaseType =cusType.substring(0,1);
        }
        var assureType = $('select[name=assureType]').val();
        $("input[name=assureName]").parent().find('div').remove();
		var cusTypeSet = _getCusTypeSet(cusType);// 根据客户类别查询业务身份(parm_dic.key_name = 'CUS_TYPE_SET')
		if (cusTypeSet == '9') {// 客户分类是担保公司
			bindDataSource(obj, '9', 'assureNo', '选择担保公司', false, cusBaseType, cusType);
		} else if(assureType == "3"){//弹出联保体除了本客户的人员
            bindDataSource(obj, '7', 'idNum', '选择联保体', true, cusBaseType, cusType,false,function(data){
                var cusIdArr = '';
                var idNumArr = '';
                $.each(data,function(i,li){
                    cusIdArr = cusIdArr + li.cusId+"|";
                });
                $("input[name='assureNo']").val(cusIdArr);
            });
		}else {
			bindDataSource(obj, '4', 'idNum', '选择保证人', false, cusBaseType, cusType,false,function(data){
				$("input[name='assureNo']").val(data.cusNo);
				$("input[name='idNum']").val(data.idNum);
				$("select[name='idType']").val(data.idType);
				$("input[name='cusTel']").val(data.contactsTel);
				$("input[name='commAddress']").val(data.commAddress);
				$("select[name='marrige']").val(data.ext5);
				if(data.ext7!=''&&data.ext7!=null&&data.ext7.length==8){
                    $("select[name='isLong']").val("1");
                    $.ajax({
                        url:webPath+"/mfAssureInfo/getByIdDetailAjax",
                        data:{"id":data.ext6},
                        success : function(data) {
                            if (data.flag="success") {
                                var assureInfo =data.mfAssureInfo;
                                $("select[name='jointlySssure']").val(assureInfo.jointlySssure);
                                $("select[name='relation']").val(assureInfo.relation);
                                $("input[name='ext1']").val(assureInfo.ext1);
                                $("input[name='ext2']").val(assureInfo.ext2);
                                $("input[name='ext3']").val(assureInfo.ext3);
                                $("input[name='ext4']").val(assureInfo.ext4);
                                $("textarea[name='remark']").val(assureInfo.remark);
                                $(".anchor6-anchor").show();
                                $("input[name='longEndDate']").attr("disabled", false);
                                $("input[name='longEndDate']").val(assureInfo.longEndDate);
                                $("select[name='idType']").val(assureInfo.idType);
                                $("input[name='isXuanLong']").val("1");
                            }
                        },
                        error : function(data) {
                            alert(top.getMessage("FAILED_OPERATION", " "), 0);
                        }
                    });

				}

				$("input[name='idNum']").attr("readonly","readonly");
				$("select[name='idType']").attr("disabled",true);
				var assureType = $("[name=assureType]").val();
				if(assureType=="1"){
//					getCusCorpBaseInfo(data.cusNo);
                    $("select[name='idType']").val('B');
				}else if(assureType=="2"){
                    $("select[name='idType']").val('0');
					 if(data.cusNo=="legal"){
                        var cusThis = $("input[name='cusNo']").val();
                        getCorpBaseInfo(cusThis);
					}else{
                         if(data.cusNo!="assure"){
                             getCusPersBaseInfo(data.cusNo);
                         }
					}
				}
			},null,null,null,busModel);
		}

		isQuote="1";
	};
	var getCusPersBaseInfo  = function(cusno){
		$.ajax({
			url:webPath+"/mfCusPersBaseInfo/getCusPersBaseInfoAjax",
			data:{"cusNo":cusno},
			success : function(data) {
				var baseInfo = data.baseInfo;
				var jobInfo = data.jobInfo;
                if (baseInfo) {
                    $("input[name='cusTel']").val(baseInfo.cusTel);
                    $("[name='age']").val(baseInfo.age);

                    var brithday = baseInfo.brithday;
                    if (brithday != null && brithday.indexOf('-') >= 0) {
                        brithday = brithday.replace(/-/g, '');
                    }
                    if (brithday != null && isNaN(brithday)) {
                        alert("保证人出生日期存在错误！请联系管理员", 0);
                        return false;
                    }
                    $("[name='brithday']").val(baseInfo.brithday);// 出生日期 mf_cus_pers_base_info.brithday  这里应该赋值带-的,因为表单上字段是日期格式,保存时会校验

                    $("[name='commAddress']").val(baseInfo.commAddress);// 现住址 mf_cus_pers_base_info.comm_address
                    $("[name='regHomeAddre']").val(baseInfo.regHomeAddre);// 户籍地址 mf_cus_pers_base_info.reg_home_addre
                    $("[name='careaProvice']").val(baseInfo.careaCity);// 客户所属地区 mf_cus_pers_base_info.carea_city
                    $("[name='nationality']").val(baseInfo.nationality);// 民族 mf_cus_pers_base_info.nationality
                    $("[name='education']").val(baseInfo.education);// 学历 mf_cus_pers_base_info.education
                    $("[name='sex']").val(baseInfo.sex);// 性别 mf_cus_pers_base_info.sex
                    $("[name='sex']").attr("disabled","disabled");
                    $("[name='marrige']").val(baseInfo.marrige);// 婚姻状况 mf_cus_pers_base_info.marrige
                    $("[name='postalCode']").val(baseInfo.postalCode);// 邮编 mf_cus_pers_base_info.postal_code
                }
				if(jobInfo){
					$("[name='workUnit']").val(jobInfo.workUnit);
					$("[name='techTitle']").val(jobInfo.techTitle);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	};
    var getCorpBaseInfo  = function(cusno){
        $.ajax({
            url:webPath+"/mfCusCorpBaseInfo/getByIdAjax",
            data:{"cusNo":cusno},
            success : function(data) {
                var baseInfo = data.mfCusCorpBaseInfo;
                if (baseInfo) {
                    $("[name='commAddress']").val(baseInfo.apAddr);
                    $("[name='marrige']").val(baseInfo.legalMarrige);
                }
            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };
	var  getCusFamilyInfo =  function (id) {
        $.ajax({
            url:webPath+"/mfCusFamilyInfo/getByIdAjaxZm",
            data:{"id":id},
            success : function(data) {
                var formData  =  data.formData;
                if(formData){
                    $("input[name='cusTel']").val(formData.relTel);
                    $("[name='marrige']").val(formData.maritalStatus);
                    $("[name='education']").val(formData.education);
                    $("[name='brithday']").val(formData.brithday);
                    $("[name='sex']").val(formData.sex);
                    $("[name='age']").val(formData.age);
                }

            },
            error : function(data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };



	var getCusCorpBaseInfo  = function(cusno){
		$.ajax({
			url:"",
			data:{"cusNo":cusno},
			success : function(data) {
				
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}	
				
		});
	};

	var _changeRelation = function () {
        var pledgeMethod = $("input[name='pledgeMethod']").val();
        if(pledgeMethod === '2'){//保证
            var assureType = $("select[name='assureType']").val();
            var options = $("select[name=relation]").find("option"); //和被保人关系
            if(cusType === '101'){//企业客户
                if(assureType ==='1'){//企业法人担保
                    if (typeof(busModel) != "undefined" && busModel=="12") {
                        makeOptionsJQ(options,"42,43,44,45","");//关联关系,子公司,母公司,合作关系
					}else {
                        makeOptionsJQ(options,"31,32,33,99","");//合作企业/同一法人/同一股东/其他
					}
                }else if(assureType ==='2'){//自然人担保
                    if (typeof(busModel) != "undefined" && busModel=="12") {
                        makeOptionsJQ(options,"23,32,22,40,41","");//实际控制人,法人代表,股东,高管,项目负责人
                    }else {
                    	makeOptionsJQ(options,"21,22,23,99","");//企业法人/股东/实际控制人/其他
                    }
                }else if(assureType ==='4'){//保证金担保
                    let assureCusType = $("select[name='assureCusType']").val();
                    let assureCusBaseType = assureCusType.substring(0,1);
                    if(assureCusBaseType == "1"){
                        makeOptionsJQ(options,"31,32,33,99","");//合作企业/同一法人/同一股东/其他
					}else{
                        makeOptionsJQ(options,"21,22,23,99","");//企业法人/股东/实际控制人/其他
                    }
                }
            }else if(cusType === '202'){//个人客户
                if(assureType ==='1'){//企业法人担保
                    if (typeof(busModel) != "undefined" && busModel=="12") {
                        makeOptionsJQ(options,"42,43,44,45","");//关联关系,子公司,母公司,合作关系
                    }else {
                    	makeOptionsJQ(options,"21,22,23,99","");//企业法人/股东/实际控制人/其他
                    }
                }else if(assureType ==='2'){//自然人担保
                    if (typeof(busModel) != "undefined" && busModel=="12") {
                        makeOptionsJQ(options,"23,32,22,40,41","");//实际控制人,法人代表,股东,高管,项目负责人
                    }else {
                        makeOptionsJQ(options, "11,12,13,14,15,16,17,99", "");//配偶/子女/父母/亲戚/同事/兄弟/朋友/其他
                    }
                }else if(assureType ==='4'){//保证金担保
                    let assureCusType = $("select[name='assureCusType']").val();
                    let assureCusBaseType = assureCusType.substring(0,1);
                    if(assureCusBaseType == "1"){
                        makeOptionsJQ(options,"21,22,23,99","");//企业法人/股东/实际控制人/其他
                    }else{
                        makeOptionsJQ(options,"11,12,13,14,15,16,17,99","");//配偶/子女/父母/亲戚/同事/兄弟/朋友/其他
                    }
                }
            }
        }
    };

	//保证方法内容变更事件
	var _assureTypeChangeEvent = function(obj){
		var assureType = $(obj).val();
        var options;
		if(assureType=="1"){
            $("input[name=assureName]").attr("readOnly",false);
            $("#asBtn").css("display","block");
            $("input[name=idNum]").val("");
            $("input[name=idNum]").attr("readOnly",false);
            $("input[name=idNum]").attr("onblur","CollateralInsert.checkUniqueness();StringUtil.setBirthyAndSexByID(this,'sex','brithday','age');func_uior_valFormat_tips(this,'allidtype');");
			options = $("select[name=assureCusType]").find("option");
			makeOptionsJQ(options,corpCusTypeStr);
			$("select[name=assureCusType] option:first").prop("selected", 'selected'); 
			options = $("select[name=idType]").find("option");
			makeOptionsJQ(options,corpIdTypeStr);
			$("select[name=idType] option:first").prop("selected", 'selected'); 
		}
		/*联保体新增*/
		else if(assureType=="3"){
            options = $("select[name=assureCusType]").find("option");
            makeOptionsJQ(options,perCusTypeStr);
            $("select[name=assureCusType] option:first").prop("selected", 'selected');
            options = $("select[name=idType]").find("option");
            makeOptionsJQ(options,perIdTypeStr);
            $("select[name=idType] option:first").prop("selected", 'selected');

            if($("input[name=cusNo]").val()!=""){
                $("input[name=assureName]").attr("readOnly",true);
                $("#asBtn").css("display","none");
                $("input[name=idNum]").removeAttr('onblur');
                $("input[name=idNum]").attr("readOnly",true);
                $.ajax({
                    url:webPath+'/mfBusAlliance/getAllianceInfoByCusNo?cusNo='+$("input[name=cusNo]").val() + "&appId=" + appId,
                    dataType:'json',
                    type:'post',
                    success : function(data){
                        if (data.flag == "success") {

                            var assureNoArray = [];
                            var assureNameArray = [];
                            var idNumArray = [];
                            for(var i = 0 ;i < data.list.length;i++){
                                assureNoArray.push(data.list[i].cusId);
                                assureNameArray.push(data.list[i].cusName);
                                idNumArray.push(data.list[i].legalIdNum);
							}
                            $("input[name=assureName]").not(':disabled').val(assureNameArray.join('|'));
                            $("input[name=assureNo]").val(assureNoArray.join('|'));
                            $("input[name=idNum]").val(idNumArray.join('|'));
                            $("[name=idType]").val("0");

                        } else {
                            window.top.alert(data.msg, 0);
                            $("input[name=assureName]").attr("readOnly",true);
                        }
                    }
                });
			}

		}
		/*联保体新增结束*/
		/*保证金担保*/
       else if(assureType=="4"){
            $("input[name=assureName]").attr("readOnly",false);
            $("#asBtn").css("display","block");
            $("input[name=idNum]").val("");
            $("input[name=idNum]").attr("readOnly",false);
            $("input[name=idNum]").attr("onblur","CollateralInsert.checkUniqueness();StringUtil.setBirthyAndSexByID(this,'sex','brithday','age');func_uior_valFormat_tips(this,'allidtype');");
            options = $("select[name=assureCusType]").find("option");
            makeOptionsJQ(options,assureAmtCusTypeStr);
            $("select[name=assureCusType] option:first").prop("selected", 'selected');
            var assureAmtCusType = $("[name='assureCusType']").val();
            var assureAmtCusBaseType = assureAmtCusType.substring(0,1);
            var assureAmtIdTypeStr = perIdTypeStr;
            if(assureAmtCusBaseType == "1"){
                assureAmtIdTypeStr = corpIdTypeStr;
			}
            options = $("select[name=idType]").find("option");
            makeOptionsJQ(options,assureAmtIdTypeStr);
            $("select[name=idType] option:first").prop("selected", 'selected');
            $("[name='isLong']").val("");
        }
		else{
            $("input[name=assureName]").attr("readOnly",false);
            $("#asBtn").css("display","block");
            $("input[name=idNum]").val("");
            $("input[name=idNum]").attr("readOnly",false);
            $("input[name=idNum]").attr("onblur","CollateralInsert.checkUniqueness();StringUtil.setBirthyAndSexByID(this,'sex','brithday','age');func_uior_valFormat_tips(this,'allidtype');");
            options = $("select[name=assureCusType]").find("option");
			makeOptionsJQ(options,perCusTypeStr);
			$("select[name=assureCusType] option:first").prop("selected", 'selected');  
			options = $("select[name=idType]").find("option");
			makeOptionsJQ(options,perIdTypeStr);
			$("select[name=idType] option:first").prop("selected", 'selected');  
			
		}
		$("input[name=assureNo]").val("");
		$("input[name=assureName]").val("");
		$("input[name=assureName]").parent(".input-group").find(".pops-value").remove();
		$("input[name=assureName]").css("display","block");
		$("input[name='idNum']").removeAttr();
		//$("select[name='idType']").removeAttr("disabled");
		IdentityCheck.assureInit();
        _changeRelation();
	};
	//客户类型内容变更事件
	var _cusTypeChangeEvent = function(obj){
		var assureType = $(obj).val();
        var options;
		var assureAmtCusType = $("[name='assureCusType']").val();
		var assureAmtCusBaseType = assureAmtCusType.substring(0,1);
		var assureAmtIdTypeStr = perIdTypeStr;
		if(assureAmtCusBaseType == "1"){
			assureAmtIdTypeStr = corpIdTypeStr;
		}
		options = $("select[name=idType]").find("option");
		makeOptionsJQ(options,assureAmtIdTypeStr);
		$("select[name=idType] option:first").prop("selected", 'selected');

		$("input[name=assureNo]").val("");
		$("input[name=assureName]").val("");
		$("input[name=assureName]").parent(".input-group").find(".pops-value").remove();
		$("input[name=assureName]").css("display","block");
		$("input[name='idNum']").removeAttr();
		//$("select[name='idType']").removeAttr("disabled");
		IdentityCheck.assureInit();
        _changeRelation();
	};

	//分级加载areaProvice隐藏，areaCity显示
	var _selectAreaProviceCallBack = function(areaInfo){
		$("input[name=careaProvice]").val(areaInfo.disNo);
		$("input[name=careaCity]").val(areaInfo.disName);
		$("input[name=address]").val(areaInfo.disName);
	};
	var _initOnCardsCity =function(){
        //上牌城市选择组件
        $("input[name=onCardsCityNo]").popupSelection({
            ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
            searchOn : true,// 启用搜索
            multiple : false,// 单选
            ztree : true,
            ztreeSetting : setting,
            title : "上牌城市",
            handle : BASE.getIconInTd($("input[name=onCardsCityNo]")),
            changeCallback : function(elem) {
                BASE.removePlaceholder($("input[name=onCardsCityNo]"));
                var value = $("input[name=onCardsCityNo]").parent().find(".pops-label-alt").html();
                $("input[name=onCardsCity]").val(value);
            }
        });
	};
    //选择客户
    var _selectCusDialog=function(){
        selectCusDialog(_selectCusBack,"","","6");
    };
    //选择客户回调
    var _selectCusBack=function(cus){
        cusNo=cus.cusNo;
        $("input[name=cusNo]").val(cusNo);
        $("input[name=cusName]").val(cus.cusName);
        $("input[name=certificateName]").val(cus.cusName);
        $("input[name=certificateNo]").val(cus.cusNo);
        $.ajax({
            url:webPath+"/pledgeBaseInfo/getPledgeShowNoAjax",
            data:{cusNo:cusNo},
            type:"POST",
            dataType:"json",
            beforeSend:function(){},
            success:function(data){
                $("input[name=pledgeShowNo]").val(data.pledgeShowNo);
            },
            error:function(data){
                alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };
	return {
		init:_init,
		freshPledgeBaseForm:_freshPledgeBaseForm,
		invoiceAmtChange:_invoiceAmtChange,
		receAmtChange:_receAmtChange,
		refreshFormByVouType:_refreshFormByVouType,
		selectAssureList:_selectAssureList,
		assureTypeChangeEvent:_assureTypeChangeEvent,
		getCusTypeSet : _getCusTypeSet,
		selectAreaProviceCallBack : _selectAreaProviceCallBack,
        initOnCardsCity:_initOnCardsCity,
        checkAmt:_checkAmt,
        selectCusDialog:_selectCusDialog,
        changeRelation:_changeRelation,
        cusTypeChangeEvent:_cusTypeChangeEvent,
	};
}(window, jQuery);