var mfCusCreditApplyInsert = function(window,$){
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_initKindInput();
		/*//当调整授信申请进入时隐藏新增尽职报告
		$("input[name=reportTemplate]").parents("tr").hide();
		*/
		var creditType =$("[name=creditType]").val();
		if(creditType=="2"){
			var creditAppId =$("input[name=creditAppId]").val();
			MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppId, $('[name=adjustAppId]').val());
		}
		_initGuarantySelection();
	};

        //更新操作(惠农贷)
        var _insertAjax = function(obj){
            var datas = [];
            var cuSum = 0.00;
            $.each($("#mfBusFundDetailList").find("tbody tr"), function (index) {
                var entity = {};
                $thisTr = $(this);
                if($thisTr.find(':checkbox').get(0).checked==true){
                    entity.fundSimpleName = $thisTr.find("input[name=fundSimpleName]").val();
                    entity.id = $thisTr.find("input[name=id]").val();
                    entity.applyPurchaseQuantity = $thisTr.find("input[name=applyPurchaseQuantity]").val();
                    entity.redeemQuantity = $thisTr.find("input[name=redeemQuantity]").val();
                    entity.applyPurchaseAmount = $thisTr.find(".applyPurchaseAmount").html().replace(/,/g, "");
                    entity.redeemAmount = $thisTr.find(".redeemAmount").html().replace(/,/g, "");
                    datas.push(entity);
                    if ($thisTr.find(':checkbox').get(0).checked == true) {
                        cuSum = Number(cuSum) + Number(entity.applyPurchaseAmount);
                    }
                }
            });
            var creditSum = Number($("input[name=creditSum]").val().replace(/,/g,""));
            if(datas == false){
                alert("请先添加质押基金",1);
                return;
            }
            if(parseFloat(creditSum) > cuSum){
                alert("授信额度不能大于质押基金总额的80%",1);
                return;
			}
            var flag = submitJsMethod($(obj).get(0), '');
            if(flag){
                var dataParam = JSON.stringify($(obj).serializeArray());
                LoadingAnimate.start();
                $.ajax({
                    url:webPath+"/mfCusCreditApply/insertAjaxHND",
                    data:{
                        ajaxData : dataParam,
                        ajaxDataList : JSON.stringify(datas)
                    },
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        LoadingAnimate.stop();
                        if(data.flag == "success"){
                            window.top.alert(data.msg,3);
                            myclose_click();
                        }else{
                            alert(data.msg,0);
                        }
                    },error:function(){
                        alert(top.getMessage("ERROR_INSERT"),0);
                    }
                });
            }
        };
	/**
	 * 回调修改链属企业的值
	 */
	var _changeCompanyName = function(index1,cusInfo){
		if(index1 == '0'){
			$("[name=companyName]").val(cusInfo.cusName);
			$("[name=companyNo]").val(cusInfo.cusNo);
		}else{
			$("[name=companyName_"+index1+"]").val(cusInfo.cusName);
			$("[name=companyNo_"+index1+"]").val(cusInfo.cusNo);
		}
		var selectVal = cusInfo.cusNo;
		var companyNos = new Array();
		var companyNames = new Array();
		companyNos.push($("[name=companyNo]").val());
		companyNames.push($("[name=companyName]").val());
		if(index != 0){
			for(var i = 1;i<=index;i++){
				var companyNo = $("[name=companyNo_" + i + "]").val();
				var companyName = $("[name=companyName_" + i + "]").val();
				if(companyNo != ""){
					companyNos.push(companyNo);
					companyNames.push(companyName);
				}
			}
		}
		companyNos.splice(companyNos.indexOf(selectVal), 1);  //删除数组中某一项
		if($.inArray(selectVal, companyNos)>=0){
			alert(top.getMessage("FIRST_SELECT_FIELD","链属企业，因为你选择的链属企业重复"), 2);
		}
	};
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
		_setKindPopupSelection("kindNo");
		$form.find("[name=kindNo]").parents("tr").addClass("newPro");
		var newButton = '<div style="margin-left:-20px;margin-top:-33px;position:absolute;dispaly:none" id="newButton">'+
		'<button title="新增" onclick="mfCusCreditApplyInsert.addOneProductTypeLine(this);return false;" class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -17px;">'+
		'<i class="i i-jia3"></i>'+
		'</button>'+
		'</div>';
		$(".newPro").children("td").eq(0).append(newButton);
		//如果授信客户是核心企业，
		if(baseType == "3"){
			var kindNoType = $form.find("[name=kindNo]").attr("type");
			if(kindNoType!="hidden"){
                $form.find("[name=kindNo]").parents("tr").children("td").eq(0).find(".control-label").html("链属企业");
                var $div = $form.find("[name=kindNo]").parent();
                var $input = '<input type="text" title="链属企业" name="companyName" datatype="0" mustinput="0" class="form-control" maxlength="100" placeholder="请输入链属企业" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" readonly="readonly" onclick="selectCompanyName(\'0\',mfCusCreditApplyInsert.changeCompanyName);"><input type="text"  name="companyNo"  style="display:none;">';
                $div.empty();
                $div.append($input);
			}
		}
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
							MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppId,$('[name=adjustAppId]').val());
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
	
	//验证产品（或链属企业）额度不能大于授信总额
	var _checkKindCreditSum=function(obj){
		// var productAmtSum = Number($("input[name=creditAmt]").val().replace(/,/g,""));
		// var creditAmtSum = Number($("input[name=creditSum]").val().replace(/,/g,""));
		// var timeOne="";
		// var $form=$(obj).parents("form");
        // var i;
		// if(baseType == "3"){//核心企业
		// 	timeOne=$form.find("[name=companyName]").attr("title");
		// 	if(index != 0){
		// 		for(i = 1;i<=index;i++){
		// 			if($("input[name=companyName_"+i+"]").length>0){//对象存在
		// 				productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
		// 			}
		// 		}
		// 	}
		// }else{
		// 	timeOne=$form.find("[name=popskindNo]").attr("title");
		// 	if(index != 0){
		// 		for(i = 1;i<=index;i++){
		// 			if($form.find("[name=kindNo_"+i+"]").length>0){//对象存在
		// 				productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
		// 			}
		// 		}
		// 	}
		// }
		// if(creditAmtSum>0&&productAmtSum>0){
		// 	if(creditAmtSum < productAmtSum){
		// 		window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"额度总和","timeTwo":$("input[name=creditSum]").attr("title")}), 0);
		// 		return;
		// 	}
		// }
	}
	
	//保存方法
	var _ajaxInsert = function(formObj, temporaryStorage){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm;
			var kindNo = null;
			var kindName= null;
			var kindNos = [];
			var kindNames = [];
			var creditAmts = [];
			var productAmtSum = 0.00;
            var creditAmtTmp = $("input[name=creditAmt]").val();
            if(creditAmtTmp!="undefined"&&creditAmtTmp!=undefined){
                productAmtSum = Number(creditAmtTmp.replace(/,/g,""));
            }
			var creditAmtSum = Number($("input[name=creditSum]").val().replace(/,/g,""));
			var dataObject = {};
			var timeOne="";
			var creditType=$(formObj).find("[name=creditType]").val();
            var i;
			if(typeof(baseType) != "undefined" && baseType== "3"){//核心企业
				timeOne=$(formObj).find("[name=companyName]").attr("title");
				kindName = $("input[name=companyName]").val();
				kindNo = $("input[name=companyNo]").val();
                var creditAmt = 0.00;
                creditAmtTmp = $("input[name=creditAmt]").val();
                if(creditAmtTmp!="undefined"&&creditAmtTmp!=undefined){
                    creditAmt = creditAmtTmp.replace(/,/g,"");
                }
				if(kindName!=""&&kindName!=null){
					kindNos.push(kindNo);
					kindNames.push(kindName);
					creditAmts.push(creditAmt);
				}
				dataForm = JSON.stringify($(formObj).serializeArray());
				if(index != 0){
					for(i = 1;i<=index;i++){
						if($("input[name=companyName_"+i+"]").length>0){   //对象存在
							kindName =  $("input[name=companyName_"+i+"]").val();
							if(kindName==""||kindName==null){
								continue;
							}
							kindNos.push($("input[name=companyNo_"+i+"]").val());
							kindNames.push($("input[name=companyName_"+i+"]").val());
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}
				dataObject = { ajaxData : dataForm,
								kindNames: JSON.stringify(kindNames),
								kindNos: JSON.stringify(kindNos),
								creditAmts: JSON.stringify(creditAmts),
								creditModel:creditModel,
								baseType:baseType
			         };
			}else{
				timeOne=$(formObj).find("[name=popskindNo]").attr("title");
				kindNo = $(formObj).find("[name=kindNo]").val();
				kindName= $(formObj).find("[name=kindNo]").parents("tr").find(".pops-value").html();
				$("input[name=kindName]").val(kindName);
				if(kindNo!=""&&kindNo!=null){
					kindNos.push(kindNo);
					kindNames.push(kindName);
					creditAmts.push($("input[name=creditAmt]").val().replace(/,/g,""))
				}
				dataForm = JSON.stringify($(formObj).serializeArray());
				if(index != 0){
					for(i = 1;i<=index;i++){
						if($(formObj).find("[name=kindNo_"+i+"]").length>0){   //对象存在
							kindNo = $(formObj).find("[name=kindNo_"+i+"]").val();
							if(kindNo==""||kindNo==null){
								continue;
							}
							kindName=$(formObj).find("[name=kindNo_"+i+"]").parents("tr").find(".pops-value").html();
							kindNos.push(kindNo);
							kindNames.push(kindName);
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}
				dataObject = { ajaxData : dataForm,
								kindNos: JSON.stringify(kindNos),
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts),
								creditModel:creditModel,
								baseType:baseType
					         };
			}
			if(kindNos != null && kindNos !='' && kindNos.length>0){
				var tmpKindNo = [];
				for(i=0;i<kindNos.length;i++){
					if(tmpKindNo.indexOf(kindNos[i]) == -1){
						tmpKindNo.push(kindNos[i]);
					}else{
						if("3" == baseType){//核心企业
							window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"链属企业","content2":"重复"}), 0);
						}else{
							window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"产品","content2":"重复"}), 0);
						}
						return false;
					}
				}
			}
			
			if(creditAmtSum < productAmtSum){
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"额度总和","timeTwo":$("input[name=creditSum]").attr("title")}), 0);
				return;
			}

            if (temporaryStorage) {// 是否是暂存
                dataObject['temporaryStorage'] = temporaryStorage;
            }
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : dataObject,
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = data.wkfAppId;
						top.creditType=creditType;
						top.creditAppId=data.creditAppId;
						myclose_click();
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
	
	/**
	 * 授信期限文本框失焦时设置截止时间
	 */
	var _creditTermOnBlur = function(obj){
		var creditTerm = Number(obj.value);
		var beginDate = $("input[name=beginDate]").val();
		var endDate = "";
		if(creditTerm != "" && beginDate != ""){
			endDate = creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m");

            if (CREDIT_END_DATE_REDUCE) {
                endDate = creditHandleUtil.getAddMonthRes(endDate, -1, "d");
            }
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
        var beginDate = $("input[name=beginDate]").val();
        var endDate = beginDate;
        if (beginDate != "") {
            if (creditTerm != "") {
                endDate = creditHandleUtil.getAddMonthRes(beginDate, creditTerm, "m");

                if (CREDIT_END_DATE_REDUCE) {
                    endDate = creditHandleUtil.getAddMonthRes(endDate, -1, "d");
                }
            }

            $("input[name=endDate]").val(endDate);
        }
	};
	
	//开始日期选择后，默认带出结束日期(可根据系统参数显示结束日期)
	var _updateApplyEndDate = function(){
		var termType = "1";
		var term = $("input[name=creditTerm]").val();
		var beginDate = $("input[name=beginDate]").val();
		if(term == '' || beginDate == ''){
			$("input[name=endDate]").val("");
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
                    $("input[name=endDate]").val(endDate);
                    //选择开始日后，清除结束日中的不能为空提示
                    $("input[name=endDate]").parent().find(".error.required").remove();
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    };
	
	/**
	 * 动态的条件产品信息
	 */
	var _addOneProductTypeLine = function(obj){
		index++;
		var tmp = "add_"+index;
		var porductInfo1 = "",porductInfo2 = "";
		var labelName ="产品";
		if(baseType == "3"){
			labelName="链属企业";
			porductInfo2='<input type="text" title="链属企业" name="companyName_'+index+'" datatype="0" mustinput="0" class="form-control" maxlength="100" placeholder="请输入链属企业" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" readonly="readonly" onclick="selectCompanyName(\''+index+'\',mfCusCreditApplyInsert.changeCompanyName);"><input type="text"  name="companyNo_'+index+'"  style="display:none;">';
		}else{
			porductInfo2 ='<select onkeydown="enterKey();" onmousedown="enterKey()" onchange="creditHandleUtil.checkKindNo(this);" onblur="func_uior_valTypeImm(this);" mustinput="" class="form-control" title="产品" name="kindNo_'+index+'">'+
			'<option selected="" value=""></option>';
			var optionStr = "";
			for(var i=0; i<mfSysKinds.length; i++){
				optionStr +='<option value="'+mfSysKinds[i].kindNo+'">'+mfSysKinds[i].kindName+'</option>';
			}
			porductInfo2 = porductInfo2+optionStr+'</select>';
		};
		porductInfo1 = '<tr name='+tmp+' class="addPro">'+
		'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label">'+labelName+'</label>'+
			'<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButton">'+
				'<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=mfCusCreditApplyInsert.delOneProductTypeLine(this,"'+tmp+'"); title="删除">'+
					'<i class="i i-x5"></i>'+
				'</button>'+
			'</div>'+
		'</td>'+
		'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
			'<div class="input-group">'+porductInfo2+'</div>'+
		'</td>'+
		'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
			'<label class="control-label ">授信额度</label>'+
		'</td>'+
		'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
			'<div class="input-group"><input type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();mfCusCreditApplyInsert.checkKindCreditSum(this)" class="form-control" mustinput="" datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
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
	var _setKindPopupSelection = function(name) {
		var kinds = new Array();
		for (var i = 0; i < mfSysKinds.length; i++) {
			kinds.push({"id" : mfSysKinds[i].kindNo, "name" : mfSysKinds[i].kindName});
		}
		$form.find("[name=" + name + "]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			itemsCount : 8,
			multiple : false,//单选
			items: kinds
		});
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
	var _fuZhi  = function(){
		$("input[name=creditSum]").val($("input[name=creditAmt]").val());
	};
	return{
		init:_init,
		close:_close,
        insertAjax:_insertAjax,
		ajaxInsert:_ajaxInsert,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		addOneProductTypeLine:_addOneProductTypeLine,
		delOneProductTypeLine:_delOneProductTypeLine,
		checkKindCreditSum:_checkKindCreditSum,
		creditTypeChange:_creditTypeChange,
		setPassBrNo:_setPassBrNo,
		changeProjectType:_changeProjectType,
		fuZhi:_fuZhi,
		changeCompanyName:_changeCompanyName,
		updateApplyEndDate:_updateApplyEndDate
	};
}(window,jQuery);