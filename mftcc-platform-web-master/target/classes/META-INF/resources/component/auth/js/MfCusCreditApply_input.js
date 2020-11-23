var MfCusCreditApply_input = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		//_initKindInput();
		var creditType =$("[name=creditType]").val();
		if(creditType == "2"){
            // 初始化授信流程
            $("input[name=creditPactId]") .popupSelection({
                searchOn : true,// 启用搜索
                inline : true,// 下拉模式
                multiple : false,// 单选选
                items : ajaxData.creditContractMap,
                changeCallback : function( obj, elem) {
                    var creditPactId = $(obj).val();
                    $("[name='creditPactNo']").val($("[name='popscreditPactId']").parent().find(".pops-value").text());
                    $.ajax({
                        url : webPath + "/mfCusCreditContract/getByIdAjax",
                        data : {
                            id:creditPactId,
                        },
                        type : 'post',
                        dataType : 'json',
                        success : function(data) {
                            if (data.flag == "success") {
                                _adjAgenciesListInit(data.mfCusCreditContract.creditAppId);
                                _adjBreedListInit(data.mfCusCreditContract.creditAppId);
                                adaptationKindNo = data.adaptationKindNo;
                                $("[name='creditSum']").val(data.mfCusCreditContract.creditSumShowStr);
                                $("[name='beginDate']").val(data.mfCusCreditContract.beginDate);
                                $("[name='endDate']").val(data.mfCusCreditContract.endDate);
                                $("[name='creditTerm']").val(data.mfCusCreditContract.creditTerm);
                                $("input[name=creditAppId]").val(data.mfCusCreditContract.creditAppId);
                                $("input[name=adjCreditSum]").val($("input[name=creditSum]").val());
                                $("[name='authBal']").val(data.mfCusCreditContract.authBalShowStr);
                                if(creditModel == "3"){
                                    $("[name='isCeilingLoop']").val(data.mfCusCreditContract.isCeilingLoop);
                                    $("[name='isCeilingLoop']").attr("disabled","disabled");
                                    $("input[name=adjCreditTerm]").val($("input[name=creditTerm]").val());
                                    $("input[name=adjBeginDate]").val($("input[name=beginDate]").val());
                                    $("input[name=adjEndDate]").val($("input[name=endDate]").val());
                                    $("select[name=adjIsCeilingLoop]").val(data.mfCusCreditContract.isCeilingLoop);
                                    $("[name='kindNo']").val(data.mfCusCreditContract.kindName);
                                    $("[name='creditTermType']").val(data.mfCusCreditContract.creditTermType);
                                    $("[name='creditTermType']").attr("disabled","disabled");//授信期限不可改变

									//表单循环添加生成合作银行
                                    /*$("input[name='agenciesName']").attr("disabled","");
                                    $("input[name='agenciesName']").parent("td").unbind();
                                    var busJson = data.busJson.busAgencies;
                                    var agencies;
                                    var agenciesName = $("[name='agenciesNameNew']");
                                    for (index = 0;index<busJson.length;index++) {
                                        agencies = busJson[index];
                                        if(index==0){
                                            $("input[name='agenciesNameNew']").parent().find(".pops-value").html(agencies.name);
                                            $("input[name='agenciesNameNew']").val(agencies.name);
                                            $("input[name='agenciesId']").val(agencies.id);
                                            $("input[name='creditAmt']").val(agencies.amt);
                                            $("input[name='hidcreditAmt']").val(agencies.hidcreditAmt);
                                        }else{
                                            var tmp = "add_"+index;
                                            var agenciesInfo1 = "",agenciesInfo2 = "";
                                            var labelName ="合作银行";
                                            agenciesInfo2 ='<input  disabled="disabled" onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="合作银行" name="agenciesName_'+index+'" value='+agencies.name+'></input>';

                                            agenciesInfo2 =agenciesInfo2+'<input type="hidden"    onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="合作银行" name="agenciesId_'+index+'" value='+agencies.id+' ></input>';
                                            agenciesInfo2 =agenciesInfo2+'<input type="hidden"    onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="合作银行额度" name="hidcreditAmt_'+index+'"  value='+agencies.hidcreditAmt+'></input>';
                                            agenciesInfo1 = '<tr name='+tmp+' class="addPro">'+
                                                '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
                                                '<label class="control-label"><font color="#FF0000">*</font>'+labelName+'</label>'+
                                                '</div>'+
                                                '</td>'+
                                                '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
                                                '<div class="input-group">'+agenciesInfo2+'</div>'+
                                                '</td>'+
                                                '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
                                                '<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
                                                '</td>'+
                                                '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
                                                '<div class="input-group"><input  id="creditAmt_'+index+'"; value='+agencies.amt+' type="text" onkeydown="enterKey();"  onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);"  onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();MfCusCreditApply_input.checkAgenciesCreditAmt(this);" class="form-control" mustinput="1" datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
                                                '</td>'+
                                                '</tr>';
                                            var len=$(obj).parents("form").find(".addPro").length;
                                            if(len==0){
                                                agenciesName.parents("tr").after(agenciesInfo1 + agenciesInfo2);
                                            }else{
                                                agenciesName.parents("form").find(".addPro").eq(len-1).after(agenciesInfo1 + agenciesInfo2);
                                            }
                                        }
                                    }*/

                                }

                            } else {
                                alert(data.msg, 0);
                            }
                        },
                        error : function() {
                            alert("获取授信合同失败！", 0);
                        }
                    });
                }
            });
		}
		if(bankShowFlag==1){
			$("#mfBusAgenciesInfo").show();
			$("#busBreedInfo").show();
		}
		_initGuarantySelection();
        // 初始化授信流程
        $("input[name=templateCredit]") .popupSelection({
            searchOn : true,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 单选选
            items : ajaxData.templateCredit,
            changeCallback : function( obj, elem) {
                var templateCredit = $(obj).val();
                var creditId = $("[name='templateCredit']").val();
                var nodeNo = $("input[name=nodeNo]").val();
                var cusNo = $("[name='cusNo']").val();
                if(cusNo=="" || cusNo=="undifined"){
                    alert("请选择授信客户",0);
                }else{
                	//工程担保业务检查客户必填信息块是否已经完善，是否上传了财务报表信息
                	if(!_checkCusReportByKind(cusNo,creditId)){
                        alert("请完善客户必填信息块并上传财务报表",0);
                		return;
					}
                    _getCreditModelByTemplate();
                }
            }
        });
        if(initFlag !=1){
            _initAgenciesSelect();
           //不展示业务品种列表时初始化业务品种
            if(bankShowFlag==0){
                _breedInit();
            }
		}

        // //合作银行
        // $("input[name=agenciesId]").popupSelection({
        //     searchOn:true,//启用搜索
        //     inline:false,//下拉模式
        //     multiple:false,//多选选
        //     items:ajaxData.busAgencies,
        //     title:"合作银行",
        //     labelShow: false,
        //     changeCallback : function (obj, elem) {
        //     },
        // });

	};
	//初始化合作银行选择
	var _initAgenciesSelect =function () {
        $("input[name=agenciesName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListByKindNoAjax?kindNo="+adaptationKindNo,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=hidcreditAmt]").val(sltVal.creditBal);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
        $form.find("[name=agenciesName]").parents("tr").addClass("newPro");
        var newButton = '<div style="margin-left:-20px;margin-top:-33px;position:absolute;dispaly:none" id="newButton">'+
        '<button title="新增" onclick="MfCusCreditApply_input.addOneAgenciesLine(this);return false;" class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -17px;">'+
        '<i class="i i-jia3"></i>'+
        '</button>'+
        '</div>';
        $(".newPro").children("td").eq(0).append(newButton);
        //新增按钮鼠标移入移出事件
        $(document).on('mouseenter','.newPro',function(e){
        	$(this).find("#newButton").show();
        });
        $(document).on('mouseleave','.newPro',function(e){
        	$(this).find("#newButton").hide();
        });
        //删除按钮鼠标移入移出事件
        $(document).on('mouseenter','.addPro',function(e){
        	$(this).find("#delButton").show();
        });
        $(document).on('mouseleave','.addPro',function(e){
        	$(this).find("#delButton").hide();
        });
    }
    //选择授信流程后确认授信性质
    var _getCreditModelByTemplate = function(){
        var nodeNo = $("input[name=nodeNo]").val();
        var creditId = $("[name='templateCredit']").val();
        var cusNo = $("[name='cusNo']").val();
        $.ajax({
            url : webPath + "/mfCusCreditApply/getCreditConfigList",
            data : {
                creditId:creditId,
                cusNo:cusNo
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                        var showType = "1";
                        var creditModel = data.mfCusCreditConfig.creditModel;
                        var creditName = data.mfCusCreditConfig.creditName;
                      	adaptationKindNo = data.mfCusCreditConfig.adaptationKindNo;//适配产品编号
                        var url = webPath+"/mfCusCreditApply/inputForCreditView?cusNo=" + cusNo+ "&creditModel="+ creditModel + "&showType=" + showType+ "&templateCreditId=" + creditId+ "&creditName=" + creditName+"&adaptationKindNo="+adaptationKindNo;//客户授信
                        if(creditModel == "3" || creditModel == "4"){
                            url = webPath+"/mfCusCreditApply/inputForCreditAdj?cusNo=" + cusNo+ "&creditModel="+ creditModel  + "&creditId=" + creditId + "&creditName=" + creditName;//暂时只有客户授信
                        }
                        window.location.href = url;
                } else {
                    alert(data.msg, 0);
                }
            },
            error : function() {
                alert("根据授信流程ID获取授信流程信息失败！", 0);
            }
        });
    }
	//初始化申请机构选择组件
	var _initGuarantySelection = function(){



		$("input[name=guarantyAgencyNo]").popupSelection({//申请机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=guarantyAgency]"));
				$("input[name=guarantyAgency]").val($("input[name=guarantyAgencyNo]").parents("td").find(".pops-value").html());
			},
		});
	};
	//初始化产品输入框
	var _initKindInput=function(){
		//_setKindPopupSelection("kindNo");
		// $form.find("[name=kindNo]").parents("tr").addClass("newPro");
		// var newButton = '<div style="margin-left:-20px;margin-top:-33px;position:absolute;dispaly:none" id="newButton">'+
		// '<button title="新增" onclick="MfCusCreditApply_input.addOneProductTypeLine(this);return false;" class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -17px;">'+
		// '<i class="i i-jia3"></i>'+
		// '</button>'+
		// '</div>';
		// $(".newPro").children("td").eq(0).append(newButton);
		// //如果授信客户是核心企业，
		// if(baseType == "3"){
		// 	$form.find("[name=kindNo]").parents("tr").children("td").eq(0).find(".control-label").html("链属企业");
		// 	var $div = $form.find("[name=kindNo]").parent();
		// 	var $input = '<input type="text" title="链属企业" name="companyName" datatype="0" mustinput="0" class="form-control" maxlength="100" placeholder="请输入链属企业" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">';
		// 	$div.empty();
		// 	$div.append($input);
		// }
		// //新增按钮鼠标移入移出事件
		// $(document).on('mouseenter','.newPro',function(e){
		// 	$(this).find("#newButton").show();
		// });
		// $(document).on('mouseleave','.newPro',function(e){
		// 	$(this).find("#newButton").hide();
		// });
		//
		// //删除按钮鼠标移入移出事件
		// $(document).on('mouseenter','.addPro',function(e){
		// 	$(this).find("#delButton").show();
		// });
		// $(document).on('mouseleave','.addPro',function(e){
		// 	$(this).find("#delButton").hide();
		// });
	};
	//授信类型变化，根据授信类型切换授信申请表单
	var flag = "0";
	var _creditTypeChange=function(){
		var creditType=$("select[name=creditType]").val();
		if(creditModel == "1"){//客户授信
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/mfCusCreditApply/getCreditApplyFormHtmlAjax",
				data : {
					creditType:creditType,
					cusNo:cusNo,
					cusType:cusType
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						$("#operform").html(data.formHtml);
						if(creditType=="1"){
							_initKindInput();
						}
						if(creditType=="2"){
							var creditAppId =$("input[name=creditAppId]").val();
							MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppId,"");
						}
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}else if(creditModel == "2"){//立项授信
			if(creditType=="1"){
				$("input[name=projectNo]").parent().html("<input type='text' title='项目立项编号' name='projectNo' " +
						"datatype='0' mustinput='1' class='form-control Required' onblur='func_uior_valTypeImm(this);' " +
						"onmousedown='enterKey()' onkeydown='enterKey();'>");
			}else if(creditType=="2"){
				if(flag == "0"){
					//授信项目选择
					$("input[name=projectNo]").popupSelection({
						searchOn : true, // false-不启用搜索，true-启用搜索
						inline : true, // true-内联,false-弹出
						ajaxUrl : webPath+"/mfCusCreditContract/getCreditPactListAjax?creditModel=2&cusNo="+cusNo+"&projectType=1",
						multiple : false, // false-单选,true-复选
						changeCallback:function(elem){//回调方法
							BASE.removePlaceholder($("input[name=projectName]"));
							var projectNo = $("input[name=projectNo]").val();
							_getCreditAdjustFormHtml(projectNo);
						},
					});
					flag = "1";
				}else if(flag == "1"){
					$.ajax({
						url : webPath+"/mfCusCreditContract/getCreditPactListAjax",
						data : {
							creditModel:"2",
							cusNo:cusNo
						},
						type : "post",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if(creditType =="1"){//新增
								
							}else{
								$("input[name=popsprojectNo]").popupSelection("updateItems",data.items);
							}
						},
						error : function(data) {
							loadingAnimate.stop();
							window.top.alert(top.getMessage("ERROR_INSERT"), 0);
						}
					});
				}
			}
		}
	};
	//选择授信类型切换表单
	var _getCreditAdjustFormHtml = function(id){
		$.ajax({
			url : webPath+"/mfCusCreditApply/getProjectAdjustFormHtmlAjax",
			data : {credidPactId:id,creditModel:"2"},
			type : "post",
			dataType : "json",
			success : function(data) {
				//LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#operform").html(data.formHtml);
					$(".saveButton").attr("onclick","MfCusCreditAdjustApplyInsert.ajaxInsert('#operform')");
				}
			},
			error : function(data) {
				//loadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_INSERT"), 0);
			}
		});
	};
	
	//验证产品（或链属授信总额
	var _checkKindCreditSum=function(obj){
	}
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			//规则验证
			var ruleCusNo = $("input[name=cusNo]").val();
			var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'kindNo':adaptationKindNo};
			if(!rulesCall.validateRules(ruleParmsData,ruleCusNo)){
				return false;
			}
			var url = $(formObj).attr("action");
			var dataForm;
			var agenciesArrs = []; //授信银行
			var breedArrs = []; //授信业务品种
			var creditType=$(formObj).find("[name=creditType]").val();
			if(typeof(creditType) != "undefined" && "2" == creditType){//类型2为授信调整
				url = webPath+"/mfCusCreditAdjustApply/insertAjax";
			}
            var paramArray =  $(formObj).serializeArray();
            if($("input[name='calc13']")!=null&&$("input[name='calc13']").val()!=undefined){
                paramArray.push({name:"calc13",value:$("input[name='calc13']").val().replace(/,/g, "")});
                paramArray.push({name:"calc1",value:$("select[name='calc1']").val()});
                paramArray.push({name:"peopleNum",value:$("input[name='peopleNum']").val()});
            }
            if($("input[name='calc2']")!=null&&$("input[name='calc2']").val()!=undefined){
                paramArray.push({name:"calc2",value:$("input[name='calc2']").val().replace(/,/g, "")});
            }
			dataForm = JSON.stringify(paramArray);
            $("#agenciesList").find("tbody tr").each(
                    function(index) {
                        var entity = {};
                        $thisTr = $(this);
                        entity.bankNo = $thisTr.find("span[name=bankNo]").text();
                        entity.bankName = $thisTr.find("span[name=bankName]").text();
                        entity.areaNo = $thisTr.find("span[name=areaNo]").text();
                        entity.areaName = $thisTr.find("span[name=areaName]").text();
                        entity.agenciesId = $thisTr.find("span[name=agenciesId]").text();
                        entity.agenciesName = $thisTr.find("span[name=agenciesName]").text();
                        entity.address = $thisTr.find("span[name=address]").text();
                        entity.agenciesPhone = $thisTr.find("span[name=agenciesPhone]").text();
                        entity.creditAmt = $thisTr.find("span[name=bankCreditAmt]").text();
                        entity.putoutTerm = $thisTr.find("span[name=putoutTerm]").text();
                        entity.extendTerm = $thisTr.find("span[name=extendTerm]").text();
                        agenciesArrs.push(entity);
                });
            	$("#busBreedList").find("tbody tr").each(
                function(index) {
                    var entity = {};
                    $thisTr = $(this);
                    entity.breedNo = $thisTr.find("span[name=breedNo]").text();
                    entity.breedName = $thisTr.find("span[name=breedName]").text();
                    entity.creditAmt = $thisTr.find("span[name=breedCreditAmt]").text();
                    entity.breedAgenciesName = $thisTr.find("span[name=breedAgenciesName]").text();
                    entity.breedAgenciesId = $thisTr.find("span[name=breedAgenciesId]").text();
                    entity.agenciesCreditAmt = $thisTr.find("span[name=agenciesCreditAmt]").text();
                    breedArrs.push(entity);
                });
            	if(agenciesArrs.length<=0&&busModel!='12'){
                    window.top.alert("请添加合作银行后再进行保存!",0);
                    return ;
				}
            	if(breedArrs.length<=0&&busModel!='12'){
                    window.top.alert("请添加业务品种后再进行保存!",0);
                    return ;
				}
			dataObject = { ajaxData : dataForm, agenciesArrs: JSON.stringify(agenciesArrs),breedArrs: JSON.stringify(breedArrs), creditModel:creditModel,kindNo:adaptationKindNo};
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : dataObject,
				type : "post",
				dataType : "json",
                async : false,
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = data.wkfAppId;
						top.creditType=creditType;
						top.creditAppId=data.creditAppId;
                        top.cusNo = data.cusNo;
                        top.creditSave = "1";
                        myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	
	/**
	 * 授信期限文本框失焦时设置截止时间
	 */
	var _creditTermOnBlur = function(obj){
		var creditTerm = Number(obj.value);
		var beginDate = $("input[name=beginDate]").val();
		var endDate = "";
		if(creditTerm != "" && beginDate != ""){
			endDate = creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m");
		}else{
			$("input[name=endDate]").val("");
		}
		$("input[name=endDate]").val(endDate);
	};
	
	/**
	 * 开始时间文本框失焦时设置截止时间
	 */
	var _beginDateOnBlur = function(){
		var creditTerm = Number($("input[name=creditTerm]").val());
		//var beginDate = obj.value;
		var beginDate = $("input[name=beginDate]").val();
		if(beginDate != ""){
			if(creditTerm != ""){
                endDate = creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m");

                if (CREDIT_END_DATE_REDUCE) {
                    endDate = creditHandleUtil.getAddMonthRes(endDate, -1, "d");
                }
			}else{
				$("input[name=endDate]").val(endDate);
			}
		}
	};
	//动态删除一行产品
	var _delOneProductTypeLineForPro = function(obj,divName){
		//index--;
		var  id =  $(obj).parents("div").attr("id");
		$("tr[name="+divName+"]").remove();
		_checkKindCreditSumForPro();
		return false;
	};
	//动态删除一行合作银行
	var _delOneAgenciesLineForPro = function(obj,divName){
		var  id =  $(obj).parents("div").attr("id");
		$("tr[name="+divName+"]").remove();
		return false;
	};

	 //动态删除一行产品
	var _delOneProductTypeLineForProNew = function(obj,divName,a){
		//index--;
		a =  "newHope_" + a;
		var  id =  $(obj).parents("div").attr("id");

		   $("tr[name="+divName+"]").remove();
		   $("tr[id="+a+"]").remove();
			_checkKindCreditSumForPro();

		return false;
	};
    /**
     * 动态的条件合作银行信息
     */
    var _addOneAgenciesLine = function(obj){
        index++;
        var tmp = "add_"+index;
        var agenciesInfo1 = "",agenciesInfo2 = "";
        var labelName ="合作银行";
        agenciesInfo2 ='<input onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="合作银行" name="agenciesName_'+index+'"></input>';

        agenciesInfo2 =agenciesInfo2+'<input type="hidden"    onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);"  class="form-control" title="合作银行" name="agenciesId_'+index+'"></input>';
        agenciesInfo2 =agenciesInfo2+'<input type="hidden"    onkeydown="enterKey();" onmousedown="enterKey()" onblur="func_uior_valTypeImm(this);"  class="form-control" title="合作银行额度" name="hidcreditAmt_'+index+'"></input>';
        agenciesInfo1 = '<tr name='+tmp+' class="addPro">'+
            '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
            '<label class="control-label">'+labelName+'</label>'+
            '<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButton">'+
            '<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=MfCusCreditApply_input.delOneAgenciesLineForPro(this,"'+tmp+'"); title="删除">'+
            '<i class="i i-x5"></i>'+
            '</button>'+
            '</div>'+
            '</td>'+
            '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
            '<div class="input-group">'+agenciesInfo2+'</div>'+
            '</td>'+
            '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
            '<label class="control-label ">授信额度</label>'+
            '</td>'+
            '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
            '<div class="input-group"><input  id="creditAmt_'+index+'"; type="text" onkeydown="enterKey();"  onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);"  onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();MfCusCreditApply_input.checkAgenciesCreditAmt(this);" class="form-control"  datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
            '</td>'+
            '</tr>';
        var len=$(obj).parents("form").find(".addPro").length;
        if(len==0){
            $(obj).parents("tr").after(agenciesInfo1 + agenciesInfo2);
        }else{
            $(obj).parents("form").find(".addPro").eq(len-1).after(agenciesInfo1 + agenciesInfo2);
        }
        _setAgenciesSelection("agenciesName_"+index,"agenciesId_"+index,"hidcreditAmt_"+index);

    };
	/**
	 * 动态的条件产品信息
	 */
	var _addOneProductTypeLine = function(obj){
		index++;
		var tmp = "add_"+index;
		var porductInfo1 = "",porductInfo2 = "";
		var labelName ="产品";
		
		var popskindNoClass = $("select[name=popskindNo]").parents("td").attr("class");

		  if(baseType == "3"){
			 labelName="链属企业";
			 porductInfo2='<input type="text" title="链属企业" name="companyName_'+index+'" datatype="0" mustinput="1" class="form-control" maxlength="100" placeholder="请输入链属企业" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">';
		  }else{
			porductInfo2 ='<select onkeydown="enterKey();" onmousedown="enterKey()" onchange="creditHandleUtil.checkKindNo(this);" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="产品" name="kindNo_'+index+'">'+
			'<option selected="" value=""></option>';
			var optionStr = "";
			for(var i=0; i<mfSysKinds.length; i++){
				optionStr +='<option value="'+mfSysKinds[i].kindNo+'">'+mfSysKinds[i].kindName+'</option>';
			}
			porductInfo2 = porductInfo2+optionStr+'</select>';
		 };
		 porductInfo1 = '<tr name='+tmp+' class="addPro">'+
		 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label"><font color="#FF0000">*</font>'+labelName+'</label>'+
			'<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButton">'+
				'<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=MfCusCreditApply_input.delOneProductTypeLineForPro(this,"'+tmp+'"); title="删除">'+
					'<i class="i i-x5"></i>'+
				'</button>'+
			'</div>'+
		 '</td>'+
		 '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
			'<div class="input-group">'+porductInfo2+'</div>'+
		 '</td>'+
		 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
		 '</td>'+
		 '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
			'<div class="input-group"><input  id="kindNo_'+index+'"; type="text" onkeydown="enterKey();"  onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);"  onchange="MfCusCreditApply_input.checkCreditAmt(this)";  onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();MfCusCreditApply_input.checkKindCreditSumForPro(this)" class="form-control" mustinput="1" datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
		 '</td>'+
		 '</tr>';
		 var len=$(obj).parents("form").find(".addPro").length;
		 if(len==0){
			$(obj).parents("tr").after(porductInfo1 + porductInfo2);
		 }else{
			$(obj).parents("form").find(".addPro").eq(len-1).after(porductInfo1 + porductInfo2);
		 }
		 if(cusType != "101"){
			_setKindPopupSelection("kindNo_"+index);
		 }
		
	};
    //产品使用选择组件
    var _setAgenciesSelection = function(name,id,creditAmt) {
        $form.find("[name=" + name + "]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListAjax?kindNo="+adaptationKindNo,// 请求数据URL
            valueElem : "input[name="+id+"]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name="+id+"]").val(sltVal.agenciesId);
                $("input[name="+name+"]").val(sltVal.agenciesName);
                $("input[name="+creditAmt+"]").val(sltVal.creditBal);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "agenciesButtName" : "联系人",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });

    }
	//产品使用选择组件
	var _setKindPopupSelection = function(name) {
		var kinds = new Array();
		if(mfSysKinds == null){
		    return;
        }
		for (var i = 0; i < mfSysKinds.length; i++) {
			kinds.push({"id" : mfSysKinds[i].kindNo, "name" : mfSysKinds[i].kindName});
		}
		$form.find("[name=" + name + "]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			itemsCount : 8,
			multiple : false,//单选
			items: kinds,
			changeCallback : function(obj,elem) {
		        var  id =  obj.data("values").attr("name");
			    var popskindNoClass = $("select[name=popskindNo]").parents("td").attr("class");
			    if(id.indexOf("kindNo_") != -1){
					$("input[id="+id+"]").val("");
				}else{
					$("input[name=creditAmt]").val("");
					$("input[name=amountLand]").val("");
				}
			}
		});
	}
	
	// 获取产品信息
	var _getMysKind = function(thisKindNo){
		var mfSysKind = null;
		$.ajax({
			url : webPath+"/mfSysKind/getByIdAjaxNew",
			data : {kindNo:thisKindNo},
			type : "post",
			async: false,
			dataType : "json",
			success : function(data) {
				mfSysKind = data.mfSysKind;
			}
		 })
		 return mfSysKind;
	}
	
	
	//动态删除一行产品
	var _delOneProductTypeLine = function(obj,divName){
		$("tr[name="+divName+"]").remove();
		return false;
	};
	
	//关闭
	var _close = function(){
		myclose();
	};
	//设置传签部门
	var _setPassBrNo = function(sysOrg){
		$("input[name=passBrNo]").val(sysOrg.brNo);
		$("input[name=passBrName]").val(sysOrg.brName);
	};
	//业务类型改变修改立项申请表单
	var _changeProjectType = function(){
		if(creditModel=="2"){//立项
			var projectType = $("select[name=projectType]").val();
			$.ajax({
				url : webPath+"/mfCusCreditApply/getApplyFormHtmlByProjectTypeAjax",
				data : {projectType:projectType,cusNo:cusNo,creditModel:creditModel},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						$("#operform").html(data.formHtml);
						_initGuarantySelection();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
    var _initCus= function(){
        var nodeNo = $("input[name=nodeNo]").val();
        $("[name='cusName']").parent().find(".pops-value").remove();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCreditApply/getCreditUserList",//请求数据URL
            valueElem:"input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback:function(elem){//回调方法
				cusNo = elem.data("selectData").cusNo;
				$.ajax({
					url : webPath+"/mfCusCustomer/checkCusProSizeInfo",
					type : "post",
					async: false,
					data : {cusNo:cusNo},
					dataType : "json",
					success : function(data) {
						if (data.flag == "success") {
							if(typeof(nodeNo) != "undefined" && typeof(nodeNo) != null && nodeNo != ''){
								url +="&nodeNo="+nodeNo;
								var kindNo = $("select[name=kindNo]").val();
								var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': cusNo, 'cusNo': cusNo,'kindNo':kindNo};
								if(!rulesCall.validateRules(ruleParmsData,cusNo)){
									window.location.reload();
									return false;
								}
							}
							//查询是否重名
							$.ajax({
								url : webPath+"/mfCusCustomer/getCusByIdAjax",
								type : "post",
								async: false,
								data : {cusNo:$("input[name=cusNo]").val()},
								dataType : "json",
								success : function(data) {
									if (data.flag == "success") {
										if (data.listCus == "1") {
											dialog({
												id: 'cusMoreDialog',
												title: "重名客户",
												url: webPath + "/mfCusCustomer/getCusListByName?sign=credit&cusNo=" + $("input[name=cusNo]").val(),
												width: 500,
												height: 300,
												backdropOpacity: 0,
												onshow: function () {
													this.returnValue = null;
												},
												onclose: function () {
													if (this.returnValue) {
														//返回对象的属性:实体类MfCusCustomer中的所有属性
														if (typeof(callback) == "function") {
															callback(this.returnValue);
														} else {
															getCusInfoArtDialog(this.returnValue);
														}
													}
													var cusNo = $("input[name=cusNo]").val();
													if(cusNo ==null || cusNo==""){//取消
														$("input[name=cusName]").parent().find(".pops-value").click();
													}

												}
											}).showModal();
										}
									}else{

									}
								},
								error : function() {
									window.top.alert("根据客户号获取授信流程失败",1);
								}
							});
							// 修改授信流程
							$.ajax({
								url : webPath+"/mfCusCreditConfig/getListByCusNo",
								type : "post",
								async: false,
								data : {cusNo:$("input[name=cusNo]").val()},
								dataType : "json",
								success : function(data) {
									if (data.flag == "success") {
										$("[name=popstemplateCredit]").popupSelection("updateItems",data.creditFlowMap);
									}else{
										window.top.alert(data.msg,1);
									}
								},
								error : function() {
									window.top.alert("根据客户号获取授信流程失败",1);
								}
							});


						}else{
							window.top.alert(data.msg,0);
							$("input[name='cusNo']").val("");
							$("input[name='cusName']").parent().find(".pops-value").text("");
							return;
						}
					},
					error : function() {
						window.top.alert(top.getMessage("ERROR_DATA_CREDIT","客户信息"),0);
					}
				});

            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "cusTypeName":"客户类型",
                "idNum":"证件号码"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    }
    var _initProjectCus = function(){
        var nodeNo = $("input[name=nodeNo]").val();
        $('input[name=cusName]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfCusCustomer/findPerAndCoreResultByPageAjax",//请求数据URL
            valueElem:"input[name=cusNo]",//真实值选择器
            title: "选择客户",//标题
            changeCallback:function(elem){//回调方法
                var showType = "1";
                var cusNo = elem.data("values").val();
                var url = webPath+"/mfCusCreditApply/inputForCreditView?cusNo=" + cusNo+ "&creditModel=2" + "&showType=" + showType;//暂时只有客户授信
                if(typeof(nodeNo) != "undefined" && typeof(nodeNo) != null && nodeNo != ''){
                    url +="&nodeNo="+nodeNo;
                    var kindNo = $("select[name=kindNo]").val();
                    var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': cusNo, 'cusNo': cusNo,'kindNo':kindNo};
                    if(!rulesCall.validateRules(ruleParmsData,cusNo)){
                        window.location.reload();
                        return false;
                    }
                }
                window.location.href = url;
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "idNum":"证件号码"
            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusNo"//真实值
            }
        });
        $("input[name=cusName]").parent().find(".pops-value").click();
    }
	//客户可以单独新增授信产品时才会配置此方法
	var _checkKindCreditSumForPro=function(obj){
		var productAmtSum = Number($("input[name=creditAmt]").val().replace(/,/g,""));
		if(index != 0){
			for(var i = 1;i<=index;i++){
				if($form.find("[name=kindNo_"+i+"]").length>0){//对象存在
					productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
				}
			}
		}
		$("input[name=creditSum]").val(fmoney(productAmtSum, 2));
	}
	
	
	//当授信类型为调整时，动态添加产品额度和调整额度行
	var _getKindAmtAdjInfo=function(creditAppId,adjustAppId){
		//授信调整申请页面将已授信信息赋值给调整字段  adjustAppId为空串
		if(adjustAppId=="" || typeof(adjustAppId)=='undefined'){
			$("input[name=adjCreditSum]").val($("input[name=creditSum]").val());
			$("input[name=adjCreditTerm]").val($("input[name=creditTerm]").val());
			$("input[name=adjBeginDate]").val($("input[name=beginDate]").val());
			$("input[name=adjEndDate]").val($("input[name=endDate]").val());
			var isCeilingLoop=$("input[name=isCeilingLoop]").val();
			$("select[name=adjIsCeilingLoop]").find("option[text='"+isCeilingLoop+"']").attr("selected",true);
			$(".saveButton").attr("onclick","MfCusCreditAdjustApplyInsert.ajaxInsert('#operform')");
		}
		if(creditAppId != "" && adjustAppId != ""){
            $.ajax({
                url : webPath+"/mfCusCreditApply/getPorductCreditAjax",
                data : {
                    creditAppId:creditAppId,
                    adjustAppId:adjustAppId
                },
                type : "post",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        _addKindAmtAdjLine(data);
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    loadingAnimate.stop();
                    window.top.alert(top.getMessage("ERROR_INSERT"), 0);
                }
            });
        }
	};
	
	//动态添加授信调整产品信息及调整默认额度
	var _addKindAmtAdjLine=function(data){
		var creditAmt=0.00;
		var creditAdjAmt=0.00;
		$.each(data.mfCusPorductCreditList,function(index,obj){
			creditAmt=obj.creditAmt;
			creditAdjAmt=obj.creditAmt;
			$.each(data.mfCusPorductCreditAdjList,function(i,por){
				if(obj.kindNo==por.kindNo){
					creditAdjAmt=por.creditAmt;
				}
			});
			var optionStr = "";
			var labelName="产品";
			var inputName="kindNo";
			$.each(data.kindMap,function(i,parmDic){
				if(obj.kindNo == parmDic.optCode){
					optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+obj.kindName+'('+creditHandleUtil.numFormat(creditAmt)+')"><input type="hidden" value="'+obj.kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
					optionStr +='<input type="hidden" value="'+obj.kindName+'" name="kindName_'+index+'" readonly="readonly">';
				}
			});
			var porductInfo = '<tr><td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label ">'+labelName+'</label>'+
			'</td>'+
			'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
			'<div class="input-group">';
			optionStr +='</div>'+
			'</td>'+
			'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label ">调整额度</label>'+
			'</td>'+
			'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
			'<div class="input-group">'+
			'<input value="'+creditHandleUtil.numFormat(creditAdjAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();MfCusCreditApply_input.checkKindCreditSumAdjForPro(this)" class="form-control" mustinput="" datatype="12" id="adjCreditAmt_'+index+'" name="adjCreditAmt_'+index+'" title="授信额度" maxlength="14">'+
			'<span class="input-group-addon">元</span></div>'+
			'</td>'+
			'</tr>';
			$("input[name=creditAdjAmt]").parents("tr").after(porductInfo+optionStr);
			//金额格式化
			creditHandleUtil.moneyFormatting("adjCreditAmt_"+index)
		});
		$("input[name=creditAdjAmt]").parents("tr").hide();
	};
	
	//客户可以单独新增授信产品时才会配置此方法
	var _checkKindCreditSumAdjForPro=function(obj){
		var form=$(obj).parents("form");
		var productAmtSum = 0;
		$.each($(form).find("input[name^='adjCreditAmt']"),function(i,obj){
			productAmtSum = productAmtSum + Number($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
		});
		$("input[name=adjCreditSum]").val(fmoney(productAmtSum, 2));
	}
	
	//额度测算
	var _limitAmtCount = function(obj){
		var amountLand =$(obj).val();
		// 选择所有的name属性以'kindNo'开头的input元素
		var kindNoName = $(obj).parents('tr').find("[name^='kindNo']").attr("name");
		var kindNo = $("[name="+kindNoName+"]").val();
		$.ajax({
			url : webPath+"/mfCusCreditApply/creditlimitAmtCountAjax",
			data : {
				kindNo:kindNo,
				amountLand:amountLand
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					var countCreditAmt = data.creditAmt;//根据存栏量计算的授信金额
					var maxKindAmt = data.maxKindAmt;//产品最大额度
					if(CalcUtil.subtract(countCreditAmt,maxKindAmt)>0){
						window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"根据存栏量计算出的授信金额:","timeTwo":"产品额度上限:"+CalcUtil.formatMoney(data.maxKindAmt,2)}), 0);
					}else{
						var creditNameAmt = $(obj).parents('tr').next().find("input[name^='creditAmt']").attr("name");
						$("input[name="+creditNameAmt+"]").val(CalcUtil.formatMoney(data.creditAmt,2));
						
						var hidcreditNameAmt =    "hid" + creditNameAmt;
						$("input[name="+hidcreditNameAmt+"]").val(CalcUtil.formatMoney(data.creditAmt,2));
						_creditAmtCount(obj);	
					}
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				loadingAnimate.stop();
			}
		});
	}
	
	var _changeAmtCount = function(obj){
		var breedScale = $("input[name=breedScale]").val();
		$("input[name=creditAmt]").val(breedScale);
	}
	
	//保存方法
	/*var _insertAdjApply = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
		    if(!MfCusCreditAdjustApplyInsert.checkCreditAmt()){
		        return;
            }
			//规则验证
			var ruleCusNo = $("input[name=cusNo]").val();
			var ruleKindNo = $(formObj).find("[name=kindNo]").val();
			var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'kindNo':ruleKindNo};
			if(!rulesCall.validateRules(ruleParmsData,ruleCusNo)){
				return false;
			}
			var url = $(formObj).attr("action");
			var dataForm;
			var kindNos = [];
			var kindNames = [];
			var creditAmts = [];
			var productAmtSum = 0.00;
			var creditAmtSum = Number($("input[name=adjCreditSum]").val().replace(/,/g,""));
			var dataObject = {};
			var timeOne="产品";


            var agenciesId = $("input[name=agenciesId]").val();
            var agenciesName= $("input[name=agenciesNameNew]").val();
            var agenciesIds = [];
            var agenciesNames = [];
            if(agenciesId!=""&&agenciesId!=null){
                agenciesIds.push(agenciesId);
                agenciesNames.push(agenciesName);
                creditAmts.push($("input[name=creditAmt]").val().replace(/,/g,""));
            }
            if(index != 0){
                for(i = 1;i<=index;i++){
                    if($(formObj).find("[name=agenciesId_"+i+"]").length>0){   //对象存在
                        agenciesId = $(formObj).find("[name=agenciesId_"+i+"]").val();
                        if(agenciesId==""||agenciesId==null){
                            continue;
                        }
                        agenciesIds.push(agenciesId);
                        agenciesNames.push($(formObj).find("[name=agenciesName_"+i+"]").val());
                        creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
                    }
                }
            }


			var creditType=$(formObj).find("[name=creditType]").val();
			if(baseType == "3"){//核心企业
				timeOne="链属企业";
				//$("input[name=kindName]").val($("input[name=companyName]").val());
				dataForm = JSON.stringify($(formObj).serializeArray());
				$.each($(formObj).find("input[name^='adjCreditAmt']"),function(i,obj){
					kindNos.push(i+"");
					kindNames.push($("input[name=companyName_"+i+"]").val());
					creditAmts.push($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
					productAmtSum = productAmtSum + Number($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
				});
				/!*if(index != 0){
					for(var i = 1;i<=index;i++){
						if($("input[name=companyName_"+i+"]").length>0){   //对象存在
							kindNames.push($("input[name=companyName_"+i+"]").val());
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}*!/
				dataObject = { ajaxData : dataForm,
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts),
                                baseType:baseType

			         };
			}else{
				dataForm = JSON.stringify($(formObj).serializeArray());
				$.each($(formObj).find("input[name^='adjCreditAmt']"),function(i,obj){
					kindNos.push($("input[name=kindNo_"+i+"]").val());
					kindNames.push($("input[name=kindName_"+i+"]").val());
					creditAmts.push($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
					productAmtSum = productAmtSum + Number($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
				});
				dataObject = { ajaxData : dataForm,
								kindNos: JSON.stringify(kindNos),
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts),
                    			agenciesNames: JSON.stringify(agenciesNames),
                    			agenciesIds: JSON.stringify(agenciesIds),
                                baseType:baseType
					         };
			}
			if(kindNos != null && kindNos !='' && kindNos.length>0){
				var tmpKindNo = [];
				for(var i=0;i<kindNos.length;i++){
					if(tmpKindNo.indexOf(kindNos[i]) == -1){
						tmpKindNo.push(kindNos[i]);
					}else{
						window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"产品授信额度","content2":"重复"}), 0);
						return false;
					}
				}
			}
			
			if(creditAmtSum < productAmtSum){
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"额度总和","timeTwo":$("input[name=creditSum]").attr("title")}), 0);
				return;
			}
			LoadingAnimate.start();
			top.adjCreditFlag = false;
			$.ajax({
				url : webPath+"/mfCusCreditAdjustApply/insertAjax",
				data : dataObject,
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = data.wkfAppId;
						top.creditType=creditType;
						top.creditAppId=data.adjustAppId;
						top.adjCreditFlag=true;
						top.cusNo = cusNo;
                        top.creditSave = "1";
                        if(typeof (creditModel) != "undefined" && ("3" == creditModel || "4" == creditModel )){
                            myclose_click();
                        }else{
                            myclose_click();
                        }
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
		
	};*/

    //调整授信保存方法
    var _insertAdjApply = function(formObj){
        var flag = submitJsMethod($(formObj).get(0), '');
        if (flag) {
            if(!MfCusCreditAdjustApplyInsert.checkCreditAmt()){
                return;
            }
            //规则验证
            var ruleCusNo = $("input[name=cusNo]").val();
            var ruleKindNo = $(formObj).find("[name=kindNo]").val();
            var ruleParmsData = {'nodeNo':'CREDIT_APPLY', 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'kindNo':ruleKindNo};
            if(!rulesCall.validateRules(ruleParmsData,ruleCusNo)){
                return false;
            }
            var dataForm = JSON.stringify($(formObj).serializeArray());
            var agenciesArrs = []; //授信银行
            var breedArrs = []; //授信业务品种

            $("#agenciesList").find("tbody tr").each(
                function(index) {
                    var entity = {};
                    $thisTr = $(this);
                    entity.bankNo = $thisTr.find("span[name=bankNo]").text();
                    entity.bankName = $thisTr.find("span[name=bankName]").text();
                    entity.areaNo = $thisTr.find("span[name=areaNo]").text();
                    entity.areaName = $thisTr.find("span[name=areaName]").text();
                    entity.agenciesId = $thisTr.find("span[name=agenciesId]").text();
                    entity.agenciesName = $thisTr.find("span[name=agenciesName]").text();
                    entity.address = $thisTr.find("span[name=address]").text();
                    entity.agenciesPhone = $thisTr.find("span[name=agenciesPhone]").text();
                    entity.creditAmt = $thisTr.find("span[name=bankCreditAmt]").text();
                    entity.putoutTerm = $thisTr.find("span[name=putoutTerm]").text();
                    entity.extendTerm = $thisTr.find("span[name=extendTerm]").text();
                    agenciesArrs.push(entity);
                });
            $("#busBreedList").find("tbody tr").each(
                function(index) {
                    var entity = {};
                    $thisTr = $(this);
                    entity.breedNo = $thisTr.find("span[name=breedNo]").text();
                    entity.breedName = $thisTr.find("span[name=breedName]").text();
                    entity.creditAmt = $thisTr.find("span[name=breedCreditAmt]").text();
                    entity.breedAgenciesName = $thisTr.find("span[name=breedAgenciesName]").text();
                    entity.breedAgenciesId = $thisTr.find("span[name=breedAgenciesId]").text();
                    entity.agenciesCreditAmt = $thisTr.find("span[name=agenciesCreditAmt]").text();
                    breedArrs.push(entity);
                });
            if(agenciesArrs.length<=0&&busModel!='12'){
                window.top.alert("请添加合作银行后再进行保存!",0);
                return ;
            }
            if(breedArrs.length<=0&&busModel!='12'){
                window.top.alert("请添加业务品种后再进行保存!",0);
                return ;
            }
            dataObject = { ajaxData : dataForm, agenciesArrs: JSON.stringify(agenciesArrs),breedArrs: JSON.stringify(breedArrs), creditModel:creditModel,kindNo:adaptationKindNo};
            LoadingAnimate.start();
            top.adjCreditFlag = false;
            $.ajax({
                url : webPath+"/mfCusCreditAdjustApply/insertAjax",
                data : dataObject,
                type : "post",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.creditFlag=true;
                        top.wkfAppId = data.wkfAppId;
                        top.creditType=creditType;
                        top.creditAppId=data.adjustAppId;
                        top.adjCreditFlag=true;
                        top.cusNo = cusNo;
                        top.creditSave = "1";
                        if(typeof (creditModel) != "undefined" && ("3" == creditModel || "4" == creditModel )){
                            myclose_click();
                        }else{
                            myclose_click();
                        }
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    window.top.alert(top.getMessage("ERROR_INSERT"), 0);
                }
            });
        }

    };

	//追加产品的时候做授信产品额度控制
	var _checkCreditAmt = function(obj) {
		var limitCreditAmt = $(obj).val().replace(/,/g, "");
		var limitCreditAmtName = $(obj).attr("name");
		var limitCreditAmtNameNew = "hid" + limitCreditAmtName;
		var limitCreditAmtNew = $("input[name=" + limitCreditAmtNameNew + "]")
				.val().replace(/,/g, "");

		if (CalcUtil.subtract(limitCreditAmt, limitCreditAmtNew) > 0) {
			window.top.alert(top.getMessage("NOT_FORM_TIME", {
				"timeOne" : "修改的授信额度:",
				"timeTwo" : "测算出的授信额度:"
						+ CalcUtil.formatMoney(limitCreditAmtNew, 2)
			}), 0);
			$("input[name=" + limitCreditAmtName + "]").val("");
		} else {

			var name = $(obj).attr("id");
			var kindNo = $("input[name=" + name + "]").val();
			if (kindNo == "") {
				window.top.alert("请选择产品", 0);
				return false;
			}
			$.ajax({
				url : webPath + "/mfSysKind/getByIdAjaxNew",
				data : {
					kindNo : kindNo
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					var creditAmt = $(obj).val().replace(/,/g, "");
					if (data.mfSysKind.maxAmt < creditAmt) {
						window.top.alert("授信产品额度不可超过产品额度设置上限", 0);
						var creditAmtName = $(obj).attr("name");
						$("input[name=" + creditAmtName + "]").val("");
					}

				}

			})
		}
	};
    //追加合作银行的时候做额度控制
    var _checkAgenciesCreditAmt = function(obj) {
        var limitCreditAmt = $(obj).val().replace(/,/g, "");
        var limitCreditAmtName = $(obj).attr("name");
        var limitCreditAmtNameNew = "hid" + limitCreditAmtName;
        var limitCreditAmtNew = $("input[name=" + limitCreditAmtNameNew + "]")
            .val().replace(/,/g, "");
        //調整授信時取授信调整总额
        if(creditType=='2'){
            var creditSum =  $("input[name=adjCreditSum]").val().replace(/,/g, "");
		}else {
            var creditSum =  $("input[name=creditSum]").val().replace(/,/g, "");
		}

		if(creditSum==''){
            window.top.alert("请先输入授信总额", 0);
            $("input[name=" + limitCreditAmtName + "]").val("");
            return false;
		}
        if (CalcUtil.compare(limitCreditAmt, creditSum) ==1 ) {
            window.top.alert(top.getMessage("NOT_FORM_TIME", {
                "timeOne" : "合作银行授信额度:",
                "timeTwo" : "授信总额:"
                + CalcUtil.formatMoney(creditSum, 2)
            }), 0);
            $("input[name=" + limitCreditAmtName + "]").val("");
		}else{
            if (CalcUtil.compare(limitCreditAmt, limitCreditAmtNew) ==1) {
                window.top.alert(top.getMessage("NOT_FORM_TIME", {
                    "timeOne" : "修改的授信额度:",
                    "timeTwo" : "合作银行授信余额:"
                    + CalcUtil.formatMoney(limitCreditAmtNew, 2)
                }), 0);
                $("input[name=" + limitCreditAmtName + "]").val("");
            } else {

            }
		}

    };
	 //基础产品填写额度的时候做额度控制
	  var  _checkCreditAmtBase  = function(obj){
		  var   limitCreditAmt  =   $(obj).val().replace(/,/g,"");
		  var   limitCreditAmtName  =   $(obj).attr("name");
		  var   limitCreditAmtNameNew = "hid" + limitCreditAmtName;
		  var   limitCreditAmtNew = $("input[name="+limitCreditAmtNameNew+"]").val().replace(/,/g,"");
		  
		  if(CalcUtil.subtract(limitCreditAmt,limitCreditAmtNew)>0){
			  window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"修改的授信额度:","timeTwo":"测算出的授信额度:"+CalcUtil.formatMoney(limitCreditAmtNew,2)}), 0);
			  $("input[name="+limitCreditAmtName+"]").val("");
		  }else{
		   var kindNo = $("input[name=kindNo]").val();
		   $.ajax({
				url : webPath+"/mfSysKind/getByIdAjaxNew",
				data : {kindNo:kindNo},
				type : "post",
				dataType : "json",
				success : function(data) {
				var   creditAmt =   $(obj).val().replace(/,/g,"");
				if(data.mfSysKind.maxAmt < creditAmt){
				window.top.alert("授信产品额度不可超过产品额度设置上限", 0);
				var  creditAmtName  =   $(obj).attr("name");
				$("input[name="+creditAmtName+"]").val("");	
				}
			}
		     })
		   }
		 };
		 
		//当改变基础的产品额度时求出授信总额
	     var     _creditAmtCount  = function(obj){
	         var productAmtSum = Number($("input[name=creditAmt]").val().replace(/,/g,""));
	    	 var $form=$(obj).parents("form");
	    	 if(index != 0){
			    for(var i = 1;i<=index;i++){
			    	if($form.find("[name=kindNo_"+i+"]").length>0){//对象存在
						productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
					}
				}
				}
	    	 $("input[name=creditSum]").val(_formatMoney(productAmtSum, 2));
	     }
		
	      var    _formatMoney =   function (s, type) {
	    	     if (/[^0-9\.]/.test(s)){
	    	        return "0.00";
	    	     }
	    	     if (s == null || s == "null" || s == ""){
	    	    	 return "0.00";
	    	     }
	    	      s = s.toString().replace(/^(\d*)$/, "$1.");
	    	      s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
	    	      s = s.replace(".", ",");
	    	      var re = /(\d)(\d{3},)/;
	    	      while (re.test(s))
	    	      s = s.replace(re, "$1,$2");
	    	      s = s.replace(/,(\d\d)$/, ".$1");
	    	     if (type == 0) {
	    	          var a = s.split(".");
	    	         if (a[1] == "00") {
	    	             s = a[0];
	    	          }
	    	      }
	    	  return s;
	   }
	 //通过         
	   var    _checkCreditKind = function(obj){
		   var name = $(obj).attr("id");
		   _setKindPopupSelection(name);
	   }  
	   
	   
	  var    _checkMonthTotalRate  = function(obj){
		  var   monthTotalRate  =   $(obj).val();
		  var   monthTotalRateName  =   $(obj).attr("name");
		  var   monthTotalRateNameNew = "hid" + monthTotalRateName;
		  var   monthTotalRateNew = $("input[name="+monthTotalRateNameNew+"]").val();
		  
		  if(CalcUtil.subtract( monthTotalRate,monthTotalRateNew)<0){
			  window.top.alert("修改的产品综合费率不可小于原产品的综合费率:"+CalcUtil.formatMoney(monthTotalRateNew,2), 0);
			  $("input[name="+monthTotalRateName+"]").val("");
		  }else{
			 return true; 
		  }
	   }
    var _agenciesAdd = function(){
        var creditSum =  $("input[name=creditSum]").val();
        if(creditSum==""){
            window.top.alert("请先输入授信总额!",0);
            return ;
        }
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        $.ajax({
            url: webPath + "/mfCusCreditApply/agenciesInput",
            data:{cusNo:cusNo,creditTemplateId:creditTemplateId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#creditAgenciesInsert").html(data.formHtml);
                    $("#creditInsert").css("display","none");
                    $("#agenciesInsert").css("display","block");
                    //合作银行选择银行初始化
                    // MfCusCreditApply_input.bankInit();
                    // bankInitFlag=1;
                    _agenciesInitNew();

                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
//新增授信额度银行地区初始化
    var _agenciesInitNew = function(){
        var areaNo =  $("input[name=areaNo]").val();
        $("input[name=agenciesName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListAjax?kindNo="+adaptationKindNo+"&areaNo="+areaNo,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            searchplaceholder:"多个条件联和查询请用空格隔开",
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=address]").val(sltVal.address);
                $("input[name=agenciesPhone]").val(sltVal.agenciesButtPhone);
                $("input[name=bankCreditBal]").val(sltVal.creditBal);
                $("input[name=bankNo]").val(sltVal.bankNo);
                $("input[name=bankName]").val(sltVal.bankName);
                $("input[name=areaNo]").val(sltVal.areaNo);
                $("input[name=areaName]").val(sltVal.areaName);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "address" : "地址",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
    }
    var _breedAdd = function(){
        var creditSum =  $("input[name=creditSum]").val();
        if(creditSum==""){
            window.top.alert("请先输入授信总额!",0);
            return ;
        }
        //判断当前已经选择的银行
        var agenciesArr = [];
        $('#agenciesList').find("tr").each(function(i) {//循环封装参数
            var tdArr = $(this);
            var bankCreditAmtThis = tdArr.find("td").find("span[name='bankCreditAmt']").text();
            var agenciesIdThis = tdArr.find("td").find("span[name='agenciesId']").text();
            var agenciesNameThis = tdArr.find("td").find("span[name='agenciesName']").text();
            if(agenciesIdThis!=""){
                var agencies ={};
                agencies.bankCreditAmt=bankCreditAmtThis;
                agencies.agenciesId=agenciesIdThis;
                agencies.agenciesName=agenciesNameThis;
                agenciesArr.push(agencies);
            }
        })
        if(agenciesArr.length<=0){
            window.top.alert("请先选择合作银行!",0);
            return ;
        }
        $.ajax({
            url: webPath + "/mfCusCreditApply/breedInput",
            data:{kindNo:adaptationKindNo,agenciesArr:JSON.stringify(agenciesArr)},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#creditBreedInsert").html(data.formHtml);
                    $("#creditInsert").css("display","none");
                    $("#breedInsert").css("display","block");
                    _breedBankInit(data.agenciesArr);
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
    //业务品种界面合作银行初始化
    var _breedBankInit = function(agenciesArr){
        $.ajax({
            url:  webPath + "/mfCusCreditApply/breedBankInit",
            data:{agenciesArr:agenciesArr},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("input[name='breedAgenciesId']").popupSelection({
                        searchOn: false,//启用搜索
                        inline: true,//下拉模式
                        multiple: false,//多选
                        items : data.items,
                        changeCallback: function (obj,elem) {
                            $("input[name='breedAgenciesName']").val(obj.data("text"));
                            var breedAgenciesId = $("input[name='breedAgenciesId']").val()
                            var  agenciesList = data.items;
                            for (var index in agenciesList){
                            	if(breedAgenciesId==agenciesList[index].id){
                                    $("input[name='agenciesCreditAmt']").val(agenciesList[index].bankCreditAmt);
								}
                            }
                            _breedInit(breedAgenciesId);
                        }
                    })
                }
            }
        });
    }
    //新增授信额度银行初始化
    var _bankInit = function(){
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
		$("input[name='bankNo']").popupSelection({
			ajaxUrl: webPath + "/mfCusCreditApply/bankInit?cusNo=" + cusNo+"&creditTemplateId=" + creditTemplateId,
			searchOn: false,//启用搜索
			inline: true,//下拉模式
			multiple: false,//单选
			changeCallback: function (obj,elem) {
				$("input[name='bankName']").val(obj.data("text"));
				$("input[name='areaNo']").val("");
				$("input[name='areaNo']").parent().find(".pops-value").text("");
				$("input[name='areaName']").val("");
				$("input[name='agenciesId']").val("");
				$("input[name='agenciesName']").val("");
				_areaInit();
			}
		})

    }
    //新增业务品种初始化
    var _breedInit = function(breedAgenciesId){
	     //判断当前已经选择的业务品种
		var breedNoArr = [];
        $('#busBreedList').find("tr").each(function(i) {//循环封装参数
            var tdArr = $(this);
            var breedNoThis = tdArr.find("td").find("span[name='breedNo']").text();
            var agenciesIdThis = tdArr.find("td").find("span[name='breedAgenciesId']").text();
            if(breedNoThis!=""){
                var breedId ={};
                breedId.breedId=breedNoThis;
                breedId.agenciesId=agenciesIdThis;
                breedNoArr.push(breedId);
			}
        })
            $.ajax({
                url:  webPath + "/mfCusCreditApply/breedInit",
                data:{kindNo:adaptationKindNo,breedNoArr:JSON.stringify(breedNoArr),agenciesId:breedAgenciesId},
                type:'post',
                dataType:'json',
                success: function (data) {
                    if (data.flag == "success") {
                        if(breedInitFlag==0){
                            $("input[name='breedNo']").popupSelection({
                                searchOn: false,//启用搜索
                                inline: false,//下拉模式
                                multiple: true,//多选
                                items : data.items,
                                changeCallback: function (elem) {
                                    var breedNos = $("input[name='breedNo']").val();
                                    $.ajax({
                                        url: webPath + "/mfCusCreditApply/getNameByBreedNo",
                                        data:{breedNos:breedNos},
                                        type:'post',
                                        dataType:'json',
                                        success: function (data) {
                                            if (data.flag == "success") {
                                                $("input[name='breedName']").val(data.breedName)
                                            }
                                        }
                                    });
                                }
                            })
                            breedInitFlag=1;
						}else{
                            $("input[name=popsbreedNo]").popupSelection("updateItems",data.items);
						}

                    }
                }
            });
    }
    //新增授信额度银行地区初始化
    var _areaInit = function(){
        var bankNo =  $("input[name=bankNo]").val();
        if(bankNo==""){
            window.top.alert("请先选择银行!",0);
            return ;
        }
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        if(areaInitFlag==0){
            $("input[name='areaNo']").popupSelection({
                ajaxUrl: webPath + "/mfCusCreditApply/areaInit?cusNo=" + cusNo+"&creditTemplateId=" + creditTemplateId+"&bankNo=" + bankNo,
                searchOn: false,//启用搜索
                inline: true,//下拉模式
                multiple: false,//单选
                changeCallback: function (obj,elem) {
                    $("input[name='areaName']").val(obj.data("text"));
                    $("input[name='agenciesId']").val("");
                    $("input[name='agenciesName']").val("");
                    $("input[name=address]").val("");
                    $("input[name=agenciesPhone]").val("");
                    // $("input[name=bankCreditBal]").val("");
                    $("input[name=agenciesName]").parent().find(".pops-value").remove();
                    _agenciesInit();
                }
            })
            areaInitFlag=1;
        }else{
            $.ajax({
                url: webPath + "/mfCusCreditApply/areaInit",
                data:{cusNo:cusNo,creditTemplateId:creditTemplateId,bankNo},
                type:'post',
                dataType:'json',
                success: function (data) {
                    if (data.flag == "success") {
                         $("input[name=popsareaNo]").popupSelection("updateItems",data.items);
                    }
                }
            });
        }
    }
    //新增授信额度银行地区初始化
    var _agenciesInit = function(){
        var areaNo =  $("input[name=areaNo]").val();
        // if(areaNo==""){
        //     window.top.alert("请先选择地区!",0);
        //     return ;
        // }
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        $("input[name=agenciesName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListByKindNoAjax?kindNo="+adaptationKindNo+"&areaNo="+areaNo,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=address]").val(sltVal.address);
                $("input[name=agenciesPhone]").val(sltVal.agenciesButtPhone);
                $("input[name=bankCreditBal]").val(sltVal.creditBal);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "address" : "地址",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
    }
    //合作银行取消
    var  _agenciesCancle = function () {
        $("#creditInsert").css("display","block");
        $("#agenciesInsert").css("display","none");
        bankInitFlag = 0;//判断银行选择组件是否初始化
        areaInitFlag = 0;//判断银行区域选择组件是否初始化
        agenciesInitFlag = 0;//判断银行支行选择组件是否初始化
    }
    //业务品种取消
    var  _breedCancle = function () {
        $("#creditInsert").css("display","block");
        $("#breedInsert").css("display","none");
        breedInitFlag = 0;//判断业务品种选择组件是否初始化
    }
    //合作银行保存
    var _agenciessave = function (obj){
        var data = JSON.stringify($(obj).serializeArray());
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
        	//var bankCreditBal = $("input[name=bankCreditBal]").val().replace(/,/g, "");
        	var bankCreditAmt = $("input[name=bankCreditAmt]").val().replace(/,/g, "");
        	var creditSum = $("input[name=creditSum]").val().replace(/,/g, "");
        	var bankNo = $("input[name=bankNo]").val();
        	var bankName = $("input[name=bankName]").val();
        	var areaNo = $("input[name=areaNo]").val();
        	var areaName = $("input[name=areaName]").val();
        	var agenciesId = $("input[name=agenciesId]").val();
        	var agenciesName = $("input[name=agenciesName]").val();
        	var address = $("input[name=address]").val();
        	var putoutTerm = $("input[name=putoutTerm]").val();
        	var extendTerm = $("input[name=extendTerm]").val();
            var addressShort = address;
        	if(addressShort.length>20){
                addressShort = addressShort.substring(0, 20)+"...";
			}
        	var agenciesPhone = $("input[name=agenciesPhone]").val();
            var entity = {};
            entity.bankCreditAmt = bankCreditAmt;
            entity.bankName = bankName;
            entity.bankNo = bankNo;
            entity.areaNo = areaNo;
            entity.areaName = areaName;
            entity.agenciesId = agenciesId;
            entity.agenciesName = agenciesName;
            entity.address = address;
            entity.addressShort = addressShort;
            entity.agenciesPhone = agenciesPhone;
            entity.putoutTerm = putoutTerm;
            entity.extendTerm = extendTerm;
            if (CalcUtil.compare(bankCreditAmt, creditSum) ==1 ) {
                window.top.alert(top.getMessage("NOT_FORM_TIME", {
                    "timeOne" : "合作银行授信额度:",
                    "timeTwo" : "授信总额:"
                    + CalcUtil.formatMoney(creditSum, 2)
                }), 0);
            }else{

                _addAgenciesShow(entity);
                // if (CalcUtil.compare(bankCreditAmt, bankCreditBal) ==1) {
                //     window.top.alert(top.getMessage("NOT_FORM_TIME", {
                //         "timeOne" : "修改的授信额度:",
                //         "timeTwo" : "合作银行授信余额:"
                //         + CalcUtil.formatMoney(bankCreditBal, 2)
                //     }),2,function(){
                //         _addAgenciesShow(entity);
                //     });
                //
                // } else {
                //     _addAgenciesShow(entity);
                // }
            }
        }
    }
    //合作银行保存
    var _breedsave = function (obj){
        breedInitFlag=0;
        var data = JSON.stringify($(obj).serializeArray());
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var breedCreditAmt = $("input[name=breedCreditAmt]").val().replace(/,/g, "");
            //选择合作银行的授信额度
            var agenciesCreditAmt = $("input[name=agenciesCreditAmt]").val().replace(/,/g, "");
            var creditSum = $("input[name=creditSum]").val().replace(/,/g, "");
            var breedNo = $("input[name=breedNo]").val();
            var breedName = $("input[name=breedName]").val();
            var breedAgenciesName = $("input[name=breedAgenciesName]").val();
            var breedAgenciesId = $("input[name=breedAgenciesId]").val();
            var entity = {};
            entity.breedCreditAmt = breedCreditAmt;
            entity.agenciesCreditAmt = agenciesCreditAmt;
            entity.breedNo = breedNo;
            entity.breedName = breedName;
            entity.breedAgenciesId = breedAgenciesId;
            entity.breedAgenciesName = breedAgenciesName;
            if (CalcUtil.compare(breedCreditAmt, agenciesCreditAmt) ==1 ) {
                window.top.alert(top.getMessage("NOT_FORM_TIME", {
                    "timeOne" : "业务品种授信额度:",
                    "timeTwo" : "合作银行授信额度:"
                    + CalcUtil.formatMoney(agenciesCreditAmt, 2)
                }), 0);
            }else{
                if (CalcUtil.compare(breedCreditAmt, creditSum) ==1 ) {
                    window.top.alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : "业务品种授信额度:",
                        "timeTwo" : "授信总额:"
                        + CalcUtil.formatMoney(creditSum, 2)
                    }), 0);


                }else{
                    _addBreedShow(entity);
                }
            }
        }
    }
    var _addAgenciesShow = function(obj){
        var input = "<tr>\n" +
            "<td align=\"center\"><span style='width: 5%'  readonly name=\"bankName\">"+obj.bankName+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 10%'  readonly name=\"areaName\">"+obj.areaName+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 10%'  readonly name=\"agenciesName\">"+obj.agenciesName+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 15%'  readonly name=\"agenciesPhone\">"+obj.agenciesPhone+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 30%'  readonly name=\"address\" title =\" "+obj.address+ " \">"+obj.address+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 15%'  readonly name=\"bankCreditAmt\" >"+obj.bankCreditAmt+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 15%'  readonly name=\"putoutTerm\" >"+obj.putoutTerm+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 15%'  readonly name=\"extendTerm\" >"+obj.extendTerm+"</span></td>\n" +
            "<td align=\"center\"><span   hidden name=\"bankNo\" >"+obj.bankNo+"</span></td>\n" +
            "<td align=\"center\"><span   hidden name=\"areaNo\" >"+obj.areaNo+"</span></td>\n" +
            "<td align=\"center\"><span   hidden name=\"agenciesId\" >"+obj.agenciesId+"</span></td>\n" +
            "<td align=\"center\"><a href=\"javascript:void(0);\" onclick=\"MfCusCreditApply_input.deleteAgencies(this,'"+obj.agenciesId+"');return false;\" class=\"abatch\">删除</a></td>\n"+
			"</tr>";
        $("#agenciesList").find("tbody").append(input);
        $("#agenciesList").find(".tableMessage").html("");
        _agenciesCancle();
         bankInitFlag = 0;//判断银行选择组件是否初始化
         areaInitFlag = 0;//判断银行区域选择组件是否初始化
         agenciesInitFlag = 0;//判断银行支行选择组件是否初始化
	}
    var _addBreedShow = function(obj){
        var input = "<tr>\n" +
            "<td align=\"center\"><span style='width: 80%'  readonly name=\"breedAgenciesName\">"+obj.breedAgenciesName+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 10%'  readonly name=\"agenciesCreditAmt\">"+obj.agenciesCreditAmt+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 80%'  readonly name=\"breedName\">"+obj.breedName+"</span></td>\n" +
            "<td align=\"center\"><span style='width: 10%'  readonly name=\"breedCreditAmt\">"+obj.breedCreditAmt+"</span></td>\n" +
            "<td align=\"center\"><span   hidden name=\"breedNo\" >"+obj.breedNo+"</span></td>\n" +
            "<td align=\"center\"><span   hidden name=\"breedAgenciesId\" >"+obj.breedAgenciesId+"</span></td>\n" +
            "<td align=\"center\"><a href=\"javascript:void(0);\" onclick=\"MfCusCreditApply_input.deleteBreed(this,'"+obj.breedNo+"');return false;\" class=\"abatch\">删除</a></td>\n"+
            "</tr>";
        $("#busBreedList").find("tbody").append(input);
        $("#busBreedList").find(".tableMessage").html("");
        _breedCancle();
        breedInitFlag = 0;//判断业务品种选择组件是否初始化
    }

    var _deleteAgencies =  function (obj,agenciesId){
        $('#agenciesList').find("tr").each(function(i) {//循环封装参数
            var tdArr = $(this);
            var agenciesThis = tdArr.find("td").find("span[name='agenciesId']").text();
            if(agenciesId==agenciesThis){
                tdArr.remove();
                $('#busBreedList').find("tr").each(function(i) {//循环封装参数
                    var tdArr2 = $(this);
                    var breedAgenciesIdThis = tdArr2.find("td").find("span[name='breedAgenciesId']").text();
                    if(breedAgenciesIdThis==agenciesThis){
                        tdArr2.remove();
                    }
                })

            }
        })
    }
    var _deleteBreed =  function (obj,breedNo){
        $('#busBreedList').find("tr").each(function(i) {//循环封装参数
            var tdArr = $(this);
            var breedNoThis = tdArr.find("td").find("span[name='breedNo']").text();
            if(breedNo==breedNoThis){
                tdArr.remove();
            }
        })
    }

    //额度测算
    var _calcQuotaSts = function (){
        $("#cusCreditApplyDiv").css('display','none');
        $("#saveBtn").css('display','none');

        $("#quotaCalcDiv").css('display','block');
        $("#saveBtnCalc").css('display','block');
        $(top.window.document).find("#myModalLabel").text('额度测算');
    }

    var _changeFormDisplay = function () {
        $("#quotaCalcDiv").css('display','none');
        $("#saveBtnCalc").css('display','none');

        $("#cusCreditApplyDiv").css('display','block');
        $("#saveBtn").css('display','block');
        $(top.window.document).find("#myModalLabel").text(title);
    }

    var _calcQuotaAjax = function(obj) {
    	debugger;
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data: {ajaxData: dataParam},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        _changeFormDisplay();
                        window.top.alert(data.msg,1);
                        $("input[name='quotaCalc']").val(CalcUtil.formatMoney(data.quotaCalc,2));
                        $("input[name='creditSum']").val(CalcUtil.formatMoney(data.quotaCalc,2));
						if(typeof (data.bjAmtCalc) != 'undefined') {
							$("input[name='bjAmtCalc']").val(CalcUtil.formatMoney(data.bjAmtCalc,2));
						}
						if(typeof (data.houseAmtCalc) != 'undefined'){
							$("input[name='houseAmtCalc']").val(CalcUtil.formatMoney(data.houseAmtCalc,2));
						}
                        if(typeof (data.baseAmt) != 'undefined') {
                            $("input[name='baseAmtCalc']").val(CalcUtil.formatMoney(data.baseAmt,2));
                        }
                        if(typeof (data.guaranteeAmt) != 'undefined'){
                            $("input[name='guaranteeAmtCalc']").val(CalcUtil.formatMoney(data.guaranteeAmt,2));
                        }
                        if(typeof (data.financeLimit) != 'undefined'){
                            $("input[name='financeAmtCalc']").val(CalcUtil.formatMoney(data.financeLimit,2));
                        }
                        if(typeof (data.taxCalc) != 'undefined'){
                            $("input[name='taxAmtCalc']").val(CalcUtil.formatMoney(data.taxCalc,2));
                        }
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
        }
    };

    var _checkCusReportByKind = function(cusNo,creditId) {
        var ruleFlag = true;
        $.ajax({
            url : webPath+"/mfCusCustomer/checkCusReportByKind",
            type : "post",
            async: false,
            data : {cusNo:cusNo,creditId:creditId},
            dataType : "json",
            success : function(data) {
               ruleFlag = data;
            },
			error : function() {
				window.top.alert(top.getMessage("ERROR_DATA_CREDIT","客户信息"),0);
			}
		});
        return ruleFlag;
    };
	//调整授信初始化原合作银行列表
    var _adjAgenciesListInit = function (creditAppId) {
        var tableId = "tablecreditAgenciesList";
        $.ajax({
            url:webPath+"/mfCusCreditApply/getMfCusCreditAgenciesListAjax",
            type:'post',
            data : {tableId:tableId,creditAppId:creditAppId},
            dataType:'json',
            success:function(data){
                for(var i=0;i<data.mfCusAgenciesCreditList.length;i++){
                    var obj = data.mfCusAgenciesCreditList[i];
                    obj.bankCreditAmt = obj.creditAmt;
                    _addAgenciesShow(obj);
                }
            	//$("#agenciesList").empty().html(data.htmlStr);
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //调整授信初始化原业务品种列表
    var _adjBreedListInit = function (creditAppId) {
        var tableId = "tablecreditBreedList";
        $.ajax({
            url:webPath+"/mfCusCreditApply/getMfBusBreedListAjax",
            type:'post',
            data : {tableId:tableId,creditAppId:creditAppId},
            dataType:'json',
            success:function(data){
                for(var i=0;i<data.mfBusBreedCreditList.length;i++){
                	var obj = data.mfBusBreedCreditList[i];
                    obj.breedCreditAmt = obj.creditAmt;
                    _addBreedShow(obj);
                }
            	//$("#busBreedList").empty().html(data.htmlStr);
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };

    //校验期限
    var _checkCreditTerm = function () {
        var creditTerm = $("[name=creditTerm]").val();//申请期限
		//判断是否为整数
        var re =/^[1-9]+[0-9]*]*$/; //判断字符串是否为数字 ^[0-9]+.?[0-9]*$/
        if (!re.test(creditTerm)) {
            $("[name=creditTerm]").val("");
            window.top.alert("授信期限应为整数！",0);
        }
    };

	return{
		init:_init,
		close:_close,
		ajaxInsert:_ajaxInsert,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		addOneProductTypeLine:_addOneProductTypeLine,
		delOneProductTypeLineForPro:_delOneProductTypeLineForPro,
		checkKindCreditSum:_checkKindCreditSum,
		creditTypeChange:_creditTypeChange,
		setPassBrNo:_setPassBrNo,
		changeProjectType:_changeProjectType,
		initCus :_initCus,
		checkKindCreditSumForPro:_checkKindCreditSumForPro,
		addKindAmtAdjLine:_addKindAmtAdjLine,
		getKindAmtAdjInfo:_getKindAmtAdjInfo,
		checkKindCreditSumAdjForPro:_checkKindCreditSumAdjForPro,
		insertAdjApply:_insertAdjApply,
        checkCreditAmt:_checkCreditAmt,
		checkCreditAmtBase:_checkCreditAmtBase,
		creditAmtCount:_creditAmtCount,
		formatMoney:_formatMoney,
		limitAmtCount:_limitAmtCount,
		changeAmtCount:_changeAmtCount,
		checkCreditKind:_checkCreditKind,
		getMysKind:_getMysKind,
		delOneProductTypeLineForProNew:_delOneProductTypeLineForProNew,
		checkMonthTotalRate:_checkMonthTotalRate,
        initProjectCus:_initProjectCus,
        getCreditModelByTemplate:_getCreditModelByTemplate,
        addOneAgenciesLine:_addOneAgenciesLine,
        delOneAgenciesLineForPro:_delOneAgenciesLineForPro,
        checkAgenciesCreditAmt:_checkAgenciesCreditAmt,
        agenciesAdd:_agenciesAdd,
        breedAdd:_breedAdd,
        bankInit:_bankInit,
        breedInit:_breedInit,
        areaInit:_areaInit,
        agenciesCancle:_agenciesCancle,
        breedCancle:_breedCancle,
        agenciessave:_agenciessave,
        breedsave:_breedsave,
        deleteAgencies:_deleteAgencies,
        deleteBreed:_deleteBreed,
        changeFormDisplay : _changeFormDisplay,
        calcQuotaSts:_calcQuotaSts,
        calcQuotaAjax:_calcQuotaAjax,
        checkCreditTerm:_checkCreditTerm
	};
}(window,jQuery);