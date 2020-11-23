var MfCusCreditAdjustApplyInput = function(window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
		$("select[name=kindNo]").parents("tr").addClass("newPro");
		//如果授信客户是核心企业，
		if(cusType == "101"){
			$("select[name=kindNo]").parents("tr").children("td").eq(0).find(".control-label").html("链属企业");
			var $div = $("select[name=kindNo]").parent();
			var $input = '<input type="text" title="链属企业" name="companyName" datatype="0" mustinput="0" class="form-control" maxlength="100" placeholder="请输入链属企业" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">';
			$div.empty();
			$div.append($input);
		}
		
		//当调整授信申请进入时隐藏新增尽职报告
		$("input[name=reportTemplate]").parents("tr").hide();
		var newButton = '<div style="margin-left:-20px;margin-top:-33px;position:absolute;dispaly:none" id="newButton">'+
			  				'<button title="新增" onclick="mfCusCreditApplyInsert.addOneProductTypeLine(this);return false;" class="btn btn-link formAdd-btn">'+
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
		//未授信,授信类型默认为新增授信
		if(creditFlag=="0"){
			$("select[name=creditType]").attr("disabled","true");
		}else if(creditFlag=="1"&&termFlag=="1"){//已授信且在授信期限内，只能调整
			$("select[name=creditType]").attr("disabled","true");
		};
		if(creditModel=="2"){
			$("select[name=creditType]").attr("disabled","false");
		}
		if(cusType=="101"){
			
		}
	};
	//当授信类型为调整时，动态添加产品额度和调整额度行
	var _getKindAmtAdjInfo=function(creditAppId,adjustAppId){
		//授信调整申请页面将已授信信息赋值给调整字段  adjustAppId为空串
		if(adjustAppId==""){
			$("input[name=adjCreditSum]").val($("input[name=creditSum]").val());
			$("input[name=adjCreditTerm]").val($("input[name=creditTerm]").val());
			$("input[name=adjBeginDate]").val($("input[name=beginDate]").val());
			$("input[name=adjEndDate]").val($("input[name=endDate]").val());
			var isCeilingLoop=$("input[name=isCeilingLoop]").val();
			$("select[name=adjIsCeilingLoop]").find("option[text='"+isCeilingLoop+"']").attr("selected",true);
			$(".saveButton").attr("onclick","MfCusCreditAdjustApplyInsert.ajaxInsert('#operform')");
		}
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
			if(cusType=="101"){
				inputName="companyName";
				labelName="链属企业";
				optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+obj.kindName+'('+creditHandleUtil.numFormat(creditAmt)+')"><input type="hidden" value="'+obj.kindName+'" name="companyName_'+index+'" readonly="readonly">';
			}else{
				$.each(data.kindMap,function(i,parmDic){
					if(obj.kindNo == parmDic.optCode){
						optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+obj.kindName+'('+creditHandleUtil.numFormat(creditAmt)+')"><input type="hidden" value="'+obj.kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
						optionStr +='<input type="hidden" value="'+obj.kindName+'" name="kindName_'+index+'" readonly="readonly">';
					}
				});
			}
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
			'<input value="'+creditHandleUtil.numFormat(creditAdjAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();MfCusCreditAdjustApplyInsert.checkKindCreditSum(this)" class="form-control" mustinput="" datatype="12" id="adjCreditAmt_'+index+'" name="adjCreditAmt_'+index+'" title="授信额度" maxlength="14">'+
			'<span class="input-group-addon">元</span></div>'+
			'</td>'+
			'</tr>';
			$("input[name=creditAdjAmt]").parents("tr").after(porductInfo+optionStr);
			//金额格式化
			creditHandleUtil.moneyFormatting("adjCreditAmt_"+index)
		});
		$("input[name=creditAdjAmt]").parents("tr").hide();
	};
	//验证产品（或链属企业）额度不能大于授信总额
	var _checkKindCreditSum=function(obj){
		var productAmtSum = 0.00;
		var creditAmtSum = Number($("input[name=adjCreditSum]").val().replace(/,/g,""));
		var timeOne="";
		var $form=$(obj).parents("form");
		if(cusType == "101"){//核心企业
			timeOne="链属企业";
		}else{
			timeOne="产品";
		}
		$.each($form.find("input[name^='adjCreditAmt']"),function(i,obj){
			productAmtSum = productAmtSum + Number($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
		});
		if(creditAmtSum < productAmtSum){
			window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"调整额度总和","timeTwo":$("input[name=adjCreditSum]").attr("title")}), 0);
			return;
		}
		//调整授信额度不能小于已使用额度。待开发
		
	}
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm;
			var kindNos = [];
			var kindNames = [];
			var creditAmts = [];
			var productAmtSum = 0.00;
			var creditAmtSum = Number($("input[name=adjCreditSum]").val().replace(/,/g,""));
			var dataObject = {};
			var timeOne="产品";
			var creditType=$(formObj).find("[name=creditType]").val();
			if(cusType == "101"){
				timeOne="链属企业";
				//$("input[name=kindName]").val($("input[name=companyName]").val());
				dataForm = JSON.stringify($(formObj).serializeArray());
				$.each($(formObj).find("input[name^='adjCreditAmt']"),function(i,obj){
					kindNos.push(i+"");
					kindNames.push($("input[name=companyName_"+i+"]").val());
					creditAmts.push($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
					productAmtSum = productAmtSum + Number($("input[name=adjCreditAmt_"+i).val().replace(/,/g,""));
				});
				/*if(index != 0){
					for(var i = 1;i<=index;i++){
						if($("input[name=companyName_"+i+"]").length>0){   //对象存在
							kindNames.push($("input[name=companyName_"+i+"]").val());
							creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
							productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
						}
					}
				}*/
				dataObject = { ajaxData : dataForm,
								kindNames: JSON.stringify(kindNames),
								creditAmts: JSON.stringify(creditAmts)
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
								creditAmts: JSON.stringify(creditAmts)
					         };
			}
			if(creditAmtSum < productAmtSum){
				window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":timeOne+"额度总和","timeTwo":$("input[name=creditSum]").attr("title")}), 0);
				return;
			}
			LoadingAnimate.start();
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
						top.creditAppId=data.creditAppId;
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
		var creditTerm = Number($("input[name=adjCreditTerm]").val());
		var beginDate = $("input[name=adjBeginDate]").val();
		if(beginDate != ""){
			if(creditTerm != ""){
				$("input[name=adjEndDate]").val(creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m"));
			}else{
				$("input[name=adjEndDate]").val(beginDate);
			}
		}
	};
	//校验调整额度不能小于已使用的额度
	var _checkAdjCreditSum = function(){
		
	};
	return{
		init:_init,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		checkKindCreditSum:_checkKindCreditSum,
		addKindAmtAdjLine:_addKindAmtAdjLine,
		getKindAmtAdjInfo:_getKindAmtAdjInfo,
		ajaxInsert:_ajaxInsert,
		checkAdjCreditSum:_checkAdjCreditSum
	};
}(window,jQuery);