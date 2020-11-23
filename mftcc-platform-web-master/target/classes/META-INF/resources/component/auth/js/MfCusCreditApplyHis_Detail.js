var mfCusCreditApplyHisDetail = function(window,$){
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('select[name=approveResult]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false //单选
		});	
		//产品信息
		if(mfCusPorductCreditList.length == 0){
			$("select[name=kindNo]").parents("tr").remove();
		}
		for(var i=1; i<mfCusPorductCreditList.length; i++){
			index++;
			var kindText = ["汽车行业产品","应收账款融资产品一","应收账款融资产品","保理产品","测试","SDF","标准产品2","标准产品1","动产质押产品","保兑仓标准产品"];
			var kindVal = ["1000","1002","1003","1004","1016","1018","20160521","20160522","kind16080415305155811","kind16092911513974110"];
			var porductInfo = '<tr>'+
									'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
										'<label class="control-label ">产品</label>'+
									'</td>'+
									'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
										'<div class="input-group">'+
											'<select onkeydown="enterKey();" onmousedown="enterKey()" onchange="creditHandleUtil.checkKindNo(this);" onblur="func_uior_valTypeImm(this);" mustinput="" class="form-control" title="产品" name="kindNo_'+index+'">';
												var optionStr = "";
												for(var j=0;j<kindText.length;j++){
													if(mfCusPorductCreditList[i].kindNo == kindVal[j]){
														optionStr += '<option value="'+mfCusPorductCreditList[i].kindNo+'" selected>'+mfCusPorductCreditList[i].kindName+'</option>';
													}else{
														optionStr += '<option value="'+kindVal[j]+'">'+kindText[j]+'</option>';
													}
												}				
								optionStr +='</select>'+
										'</div>'+
									'</td>'+
									'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
										'<label class="control-label ">授信额度(元)</label>'+
									'</td>'+
									'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
										'<div class="input-group">'+
											'<input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();" class="form-control" mustinput="" datatype="12" name="creditAmt_'+index+'" title="授信额度(元)">'+
										'</div>'+
									'</td>'+
								'</tr>';
			$("select[name=kindNo]").parents("tr").after(porductInfo+optionStr);
	 	};
	};
	 //审批页面
	var _getApprovaPage = function(){
	 	$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	 }
	 //返回详情页面
	 var _approvalBack = function(){
	 	$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	 }
	
	 //审批提交
	var _doSubmit = function(obj){
		//var opinionType = $("input[name=approveResult]").val();
		var approvalOpinion  = $("textarea[name=approveRemark]").val();
		var id = $("input[name=id]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		var kindNames = [];
		var creditAmts = [];
		var productAmtSum = Number($("input[name=creditAmt]").val());
		var creditAmtSum = Number($("input[name=creditSum]").val());
		if(index != 0){
			for(var i = 1;i<=index;i++){
				kindNames.push($("select[name=kindNo_"+i+"] option:selected").text());
				creditAmts.push($("input[name=creditAmt_"+i).val());
				productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val());
			}
			$("input[name=kindNames]").val(kindNames.join(","));
			$("input[name=creditAmts]").val(creditAmts.join(","));
		}
		if(creditAmtSum < productAmtSum){
			window.top.alert("产品额度不能超过授信总额", 0);
			return;
		}
		if(flag){
			var opinionType = $("select[name=opinionType]").val();
			commitProcess(webPath+"/mfCusCreditApply/submitApproveAjax?opinionType="+opinionType+"&id="+id,obj,'creditSP');
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
	var _beginDateOnBlur = function(obj){
		var creditTerm = Number($("input[name=creditTerm]").val());
		var beginDate = obj.value;
		if(beginDate != ""){
			if(creditTerm != ""){
				$("input[name=endDate]").val(creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m"));
			}else{
				$("input[name=endDate]").val(beginDate);
			}
		}
	};
	
	return{
		init:_init,
		doSubmit:_doSubmit,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage
	};
}(window,jQuery);