var MfCusCreditPactApprove = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//如果是补充资料节点时隐藏意见类型所在行
        if(nodeNo.indexOf("credit_supplement")!=-1){
            $("[name=opinionType]").parents("tr").hide();
        }
		//产品信息
		if(creditType=="2"){
//			var creditAppId =$("input[name=creditAppId]").val();
//			var adjustAppId =$("input[name=adjustAppId]").val();
			MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppIdPro,adjAppIdPro);
		}else{
			if("credit_supplement_data".indexOf(nodeNo) != -1){
				var optionStr,labelName,inputName,i,j,kindNoNew,kindNameNew,porductInfo2,m,porductInfo,indexNew;
				for(i=0; i<mfCusPorductCreditList.length; i++){
					n++;
					index++;
					if(index != 1){
					optionStr = "";
					labelName="产品";
					inputName="kindNo";
					tmp = "add_"+index;
					if(cusType=="101"){
						inputName="companyName";
						labelName="链属企业";
						optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="" name="companyName_'+index+'" readonly="readonly">';
					}else{
					for(j=0;j<mfSysKinds.length;j++){
						if(mfCusPorductCreditList[i].kindNo == mfSysKinds[j].kindNo){
						    kindNoNew  =  	mfCusPorductCreditList[i].kindNo  ;
						    kindNameNew =  mfCusPorductCreditList[i].kindName;
						  }
					    }	
					
					
					optionStr += '<select  onchange="MfCusCreditPactApprove.turnKindNo(this);" onkeydown="enterKey();" onmousedown="enterKey()"  onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="产品" name="kindNo_'+index+'">'+
					   '<option selected="" value='+kindNoNew+'>'+kindNameNew+'</option>';
					   porductInfo2 = "";
					   for(m=0; m<mfSysKinds.length; m++){
						   porductInfo2 +='<option value="'+mfSysKinds[m].kindNo+'">'+mfSysKinds[m].kindName+'</option>';
					   }
					   optionStr = optionStr+porductInfo2+'</select>';
				    }
					
					porductInfo = '<tr name='+tmp+'><td name= style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					'<label class="control-label "><font color="#FF0000">*</font>'+labelName+'</label>'+

                        '<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButtonNewHope">'+

                        '<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=MfCusCreditPactApprove.delOneProductTypeLineForProNew(this,"'+tmp+'","'+index+'"); title="删除">'+
                        '<i class="i i-x5"></i>'+
                        '</button>'+

	               '</div>'+

			    	'</td>'+
					'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
					'<div class="input-group">';
					optionStr +='</div>'+
					'</td>'+

					'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					'<label class="control-label "><font color="#FF0000">*</font>存栏量</label>'+
				    '</td>'+
				    '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
					'<div class="input-group"><input  id="kindNo_'+index+'"  value="'+mfCusPorductCreditList[i].amountLand+'" type="text" title="存栏量" name="amountLand_'+index+'" datatype="1" mustinput="1" class="form-control" maxlength="14" onchange="MfCusCreditPactApprove.limitAmtCount(this);" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div>'+
				    '</td>' +
				    '</tr>'+

				    '<tr id = "newHope_'+index+'">' +

				    '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					'<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
					'</td>'+
					'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
					'<div class="input-group">'+
					'<input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valFormat_tips(this, "nonnegative");func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.creditAmtCount(this);MfCusCreditPactApprove.checkCreditAmt(this)"; class="form-control" mustinput="1" datatype="12" id="kindNo_'+index+'" name="creditAmt_'+index+'" title="授信额度" maxlength = "14">'+
					'<span class="input-group-addon">元</span></div>'+
					'</td>'+

					'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					  '<label class="control-label "><font color="#FF0000">*</font>产品综合费率</label>'+
				    '</td>'+
				    '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
					  '<div class="input-group"><input  value="'+mfCusPorductCreditList[i].monthTotalRate+'"  type="text" title="产品综合费率" name="monthTotalRate_'+index+'" datatype="17" mustinput="1"  class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.checkMonthTotalRateApply(this)";  onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"><span class="input-group-addon">‰</span></div>'+
				    '</td>' +

					 '<td style="width:32%;" rowspan="1" colspan="3" class="tdvalue  half right">'+
					   '<div class="input-group"><input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'" type="hidden" title="测算额度" name="hidcreditAmt_'+index+'" datatype="12" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
					 '</td>' +

					'</tr>';

					if(index == 1){
						$("input[name=creditAmt]").parents("tr").after(porductInfo+optionStr);
					}else{
						//上一个产品额度的下标
						indexNew = index - 1 ;
						$("input[name=creditAmt_"+indexNew+"]").parents("tr").after(porductInfo+optionStr);
					}


					//金额格式化
					creditHandleUtil.moneyFormatting("creditAmt_"+index);
					}else{
						optionStr = "";
						labelName="产品";
						inputName="kindNo";
						if(cusType=="101"){
							inputName="companyName";
							labelName="链属企业";
							optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="" name="companyName_'+index+'" readonly="readonly">';
						}else{
						for(j=0;j<mfSysKinds.length;j++){
							if(mfCusPorductCreditList[i].kindNo == mfSysKinds[j].kindNo){
							    kindNoNew  =  	mfCusPorductCreditList[i].kindNo  ;
							    kindNameNew =  mfCusPorductCreditList[i].kindName;
							  }
						    }


						optionStr += '<select onchange="MfCusCreditPactApprove.turnKindNo(this);" onkeydown="enterKey();" onmousedown="enterKey()"  onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="产品" name="kindNo_'+index+'">'+
						   '<option selected="" value='+kindNoNew+'>'+kindNameNew+'</option>';
						   porductInfo2 = "";
						   for(m=0; m<mfSysKinds.length; m++){
							   porductInfo2 +='<option value="'+mfSysKinds[m].kindNo+'">'+mfSysKinds[m].kindName+'</option>';
						   }
						   optionStr = optionStr+porductInfo2+'</select>';
					    }

						porductInfo = '<tr><td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
						'<label class="control-label "><font color="#FF0000">*</font>'+labelName+'</label>'+
						'</td>'+
						'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
						'<div class="input-group">';
						optionStr +='</div>'+
						'</td>'+

						'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
						'<label class="control-label "><font color="#FF0000">*</font>存栏量</label>'+
					    '</td>'+
					    '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
						'<div class="input-group"><input  id="kindNo_'+index+'"  value="'+mfCusPorductCreditList[i].amountLand+'" type="text" title="存栏量" name="amountLand_'+index+'" datatype="1" mustinput="1" class="form-control" maxlength="14" onchange="MfCusCreditPactApprove.limitAmtCount(this);" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div>'+
					    '</td>' +
					    '</tr>'+

					    '<tr id = "newHope'+index+'">' +

					    '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
						'<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
						'</td>'+
						'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
						'<div class="input-group">'+
						'<input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valFormat_tips(this, "nonnegative");func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.creditAmtCount(this);MfCusCreditPactApprove.checkCreditAmt(this)"; class="form-control" mustinput="1" datatype="12" id="kindNo_'+index+'" name="creditAmt_'+index+'" title="授信额度" maxlength = "14">'+
						'<span class="input-group-addon">元</span></div>'+
						'</td>'+

						'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
						  '<label class="control-label "><font color="#FF0000">*</font>产品综合费率</label>'+
					    '</td>'+
					    '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
						  '<div class="input-group"><input  value="'+mfCusPorductCreditList[i].monthTotalRate+'"  type="text" title="产品综合费率" name="monthTotalRate_'+index+'" datatype="17" mustinput="1"  class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.checkMonthTotalRateApply(this)";  onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"><span class="input-group-addon">‰</span></div>'+
					    '</td>' +

						 '<td style="width:32%;" rowspan="1" colspan="3" class="tdvalue  half right">'+
						   '<div class="input-group"><input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'" type="hidden" title="测算额度" name="hidcreditAmt_'+index+'" datatype="12" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
						 '</td>' +

						'</tr>';

						if(index == 1){
							$("input[name=creditAmt]").parents("tr").after(porductInfo+optionStr);
						}else{
							//上一个产品额度的下标
							indexNew = index - 1 ;
							$("input[name=creditAmt_"+indexNew+"]").parents("tr").after(porductInfo+optionStr);
						}


						//金额格式化
						creditHandleUtil.moneyFormatting("creditAmt_"+index);
					}
				}

				$("input[name=creditAmt]").parents("tr").hide();


				$("select[name=kindNo_1]").parents("tr").addClass("newPro");

				var newButton = '<div style="margin-left:-20px;margin-top:-33px;position:absolute;dispaly:none" id="newButton">'+
				'<button title="新增" onclick="MfCusCreditPactApprove.addOneProductTypeLineNew(this);return false;" class="btn btn-link formAdd-btn" style="margin-top: 10px; margin-left: -17px;">'+
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


		 }else{

				for(i=0; i<mfCusPorductCreditList.length; i++){
					index++;
					optionStr = "";
					labelName="产品";
					inputName="kindNo";
					if(baseType=="3"){
						inputName="companyName";
						labelName="链属企业";
						optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="" name="companyName_'+index+'" readonly="readonly">';
					}else{
						for(j=0;j<mfSysKinds.length;j++){
							if(mfCusPorductCreditList[i].kindNo == mfSysKinds[j].kindNo){
								optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="'+mfCusPorductCreditList[i].kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
							}
						}
					}
					porductInfo = '<tr><td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					'<label class="control-label ">'+labelName+'</label>'+
					'</td>'+
					'<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
					'<div class="input-group">';
					optionStr +='</div>'+
					'</td>'+
					'<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
					'<label class="control-label ">授信额度</label>'+
					'</td>'+
					'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
					'<div class="input-group">'+
					'<input  value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'" readonly="readonly" type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();" class="form-control" mustinput="" datatype="12" id="creditAmt_'+index+'" name="creditAmt_'+index+'" readonly="readonly" title="授信额度">'+
					'<span class="input-group-addon">元</span></div>'+
					'</td>'+
					'</tr>';
					$("input[name=creditAmt]").parents("tr").after(porductInfo+optionStr);
					//金额格式化
					creditHandleUtil.moneyFormatting("creditAmt_"+index);
				};

		  }

	 	$("input[name=creditAmt]").parents("tr").remove();
		}
		
		$("[name=meetRaterOpNo]").popupSelection({
			ajaxUrl : webPath+"/sysUser/getSysUserListAjax?opNoType=1",
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "出席评委",
			handle : BASE.getIconInTd($("input[name=meetRaterOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=meetRaterOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=meetRaterOpName]").val(AcceptOpName);
			}
		});
		
		$("[name=meetAttendOpNo]").popupSelection({
			ajaxUrl : webPath+"/sysUser/getSysUserListAjax?opNoType=1",
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "列席人员",
			handle : BASE.getIconInTd($("input[name=meetAttendOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=meetAttendOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=meetAttendOpName]").val(AcceptOpName);
			}
		});
	};
	
	
	/**
	 * 动态的条件产品信息
	 */
	var _addOneProductTypeLineNew = function(obj){
		n++;
		index++;
		var tmp = "add_"+index;
		var porductInfo3 = "",porductInfo4 = "";
		var labelName ="产品";
		porductInfo4 ='<select onkeydown="enterKey();" onmousedown="enterKey()" onchange="creditHandleUtil.checkKindNo(this);" onblur="func_uior_valTypeImm(this);" mustinput="1" class="form-control" title="产品" name="kindNo_'+index+'">'+
			'<option selected="" value=""></option>';
			var optionStr = "";
			for(var i=0; i<mfSysKinds.length; i++){
				optionStr +='<option value="'+mfSysKinds[i].kindNo+'">'+mfSysKinds[i].kindName+'</option>';
			 }
			 porductInfo4 = porductInfo4+optionStr+'</select>';
			 porductInfo3 = '<tr name='+tmp+' class="addPro">'+
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label"><font color="#FF0000">*</font>'+labelName+'</label>'+
				'<div style="margin-left:-20px;margin-top:-33px;position:absolute;display:block;" id="delButtonNewHope">'+
					
				    '<button style="color:red;margin-top: 10px; margin-left: -17px;" class="btn btn-link list-add" onclick=MfCusCreditPactApprove.delOneProductTypeLineForProNew(this,"'+tmp+'","'+index+'"); title="删除">'+
						'<i class="i i-x5"></i>'+
					'</button>'+
				
				 '</div>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
				'<div class="input-group">'+porductInfo4+'</div>'+
			 '</td>'+
			 
			 
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>存栏量</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group"><input id="kindNo_'+index+'" type="text" title="存栏量" name="amountLand_'+index+'" datatype="1" mustinput="1" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.limitAmtCount(this)" onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div>'+
			 '</td>' +
			 '</tr>'+
			 
			 '<tr id = "newHope_'+index+'">' +
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>授信额度</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="1" class="tdvalue  half right">'+
				'<div class="input-group"><input  id="kindNo_'+index+'"; type="text" onkeydown="enterKey();"  onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);"  onchange="MfCusCreditPactApprove.creditAmtCount(this);MfCusCreditPactApprove.checkCreditAmt(this)";  onblur="func_uior_valFormat_tips(this, \'nonnegative\');func_uior_valTypeImm(this);resetTimes();MfCusCreditPactApprove.checkKindCreditSumForPro(this)" class="form-control" mustinput="1" datatype="12" name="creditAmt_'+index+'" title="授信额度" maxlength="14"><span class="input-group-addon">元</span></div>'+
			 '</td>'+
			  /*'<td ></td><td ></td>'+*/
			 
			 '<td style="width:18%;" rowspan="1" colspan="1" class="tdlable right">'+
				'<label class="control-label "><font color="#FF0000">*</font>产品综合费率</label>'+
			 '</td>'+
			 '<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group"><input type="text" title="产品综合费率" name="monthTotalRate_'+index+'" datatype="17" mustinput="1"  class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onchange="MfCusCreditPactApprove.checkMonthTotalRate(this)";  onfocus="selectInput(this);" onmousedown="enterKey()" onkeydown="enterKey();"><span class="input-group-addon">‰</span></div>'+
			 '</td>' +
			 
			 '<td style="width:32%;" rowspan="1" colspan="3" class="tdvalue  half right">'+
			   '<div class="input-group"><input type="hidden" title="测算额度" name="hidcreditAmt_'+index+'" datatype="12" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
			 '</td>' +
			 
			 
			 '<td style="width:32%;" rowspan="1" colspan="4" class="tdvalue  half right">'+
			   '<div class="input-group"><input type="hidden" title="授信产品综合费率" name="hidmonthTotalRate_'+index+'" datatype="17" mustinput="" class="form-control" maxlength="14" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" ></div>'+
			 '</td>' +
			 
			 '</tr>';
			
			 var len=$("input[name=creditAmt]").parents("form").find(".addPro").length;
			 
			 
		    var indexNew  ; 
			 
		    for(indexNew = n; indexNew >=1; indexNew -- ){
		    	if($("input[name=creditAmt_"+indexNew+"]").length>0){
		    		break;
		    	}
		    }
			 
			 if(len==0){
				 $("input[name=creditAmt_"+indexNew+"]").parents("tr").after(porductInfo3 + porductInfo4);
			 }else{
				 $("input[name=creditAmt_"+indexNew+"]").parents("form").find(".addPro").eq(len-1).next("tr").after(porductInfo3 + porductInfo4);
			 }
			 /*n = n+m;
			 m = 0;*/
			 if(cusType != "101"){
				_setKindPopupSelection("kindNo_"+index);
			 }
	 };
	
	
	    //动态删除一行产品
		var _delOneProductTypeLineForProNew = function(obj,divName,a){
			//index--;
			/*if(a==n){
				n--;
				m++;
			}*/
			a =  "newHope_" + a;
			var  id =  $(obj).parents("div").attr("id");
			
			   $("tr[name="+divName+"]").remove();
			   $("tr[id="+a+"]").remove();
				_checkKindCreditSumForPro();
			
			return false;
		};
		
		//客户可以单独新增授信产品时才会配置此方法
		var _checkKindCreditSumForPro=function(obj){
			var productAmtSum = 0;
			if(index != 0){
				for(var i = 1;i<=index;i++){
					if($("[name=kindNo_"+i+"]").length>0){//对象存在
						productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val().replace(/,/g,""));
					}
				}
			}
			$("input[name=creditSum]").val(fmoney(productAmtSum, 2));
		}
		
		
	
	//追加产品的时候做授信产品额度控制
	var  _checkCreditAmt  = function(obj){
		
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
	   var kindNo = $("[name="+name+"]").val();
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
	//求出授信总额
     var     _creditAmtCount  = function(obj){
         var productAmtSum = 0;
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
	//  //审批页面
	// var _getApprovaPage = function(){
	//  	$("#infoDiv").css("display","none");
	//  	$("#approvalBtn").css("display","none");
	//  	$("#approvalDiv").css("display","block");
	//  	$("#submitBtn").css("display","block");
	//  }
	//  //返回详情页面
	//  var _approvalBack = function(){
	//  	$("#infoDiv").css("display","block");
	//  	$("#approvalBtn").css("display","block");
	//  	$("#approvalDiv").css("display","none");
	//  	$("#submitBtn").css("display","none");
	//  }
	
	 //审批提交
	var _doSubmit = function(obj){
		//var opinionType = $("input[name=approveResult]").val();
		var approvalOpinion  = $("textarea[name=approveRemark]").val();
		var id = $("input[name=id]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		var kindNames = [];
		var creditAmts = [];
		var amountLands = [];
		var monthTotalRates = [];
		var productAmtSum = Number($("input[name=creditAmt]").val());
		var creditAmtSum = Number($("input[name=creditSum]").val());
		if(index != 0){
			for(var i = 1;i<=index;i++){
			  if($(obj).find("[name=kindNo_"+i+"]").length>0){   //对象存在
				kindNames.push($("[name=kindNo_"+i+"]").val());
				creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
				amountLands.push($("input[name=amountLand_"+i).val());
				monthTotalRates.push($("input[name=monthTotalRate_"+i+"]").val());
				productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val());
			  }
			}
			$("input[name=kindNames]").val(JSON.stringify(kindNames));
			$("input[name=creditAmts]").val(JSON.stringify(creditAmts));
			$("input[name=amountLands]").val(JSON.stringify(amountLands));
		}
		if(creditAmtSum < productAmtSum){
			window.top.alert("产品额度不能超过授信总额", 0);
			return;
		}
		if(flag){
			var opinionType = $("[name=opinionType]").val();
            if(isPrimary=="1"){//授信合同清稿审批
                commitProcess(webPath+"/mfCusCreditPactWkf/submitApproveForPrimaryAjax?opinionType="+opinionType+"&pactId="+pactId+"&primaryPactId="+primaryPactId+"&appNo="+primaryPactId,obj,'applySP');
            }else{
            	var  kindNoNews = JSON.stringify(kindNames);
            	var  creditAmtNews = JSON.stringify(creditAmts);
            	amountLands  = JSON.stringify(amountLands);
            	monthTotalRates  = JSON.stringify(monthTotalRates);
            	
                commitProcess(webPath+"/mfCusCreditPactWkf/submitApproveAjax?appNo="+pactId+"&opinionType="+opinionType+"&pactId="+pactId+"&kindNoNews="+kindNoNews+"&creditAmtNews="+creditAmtNews+"&amountLands="+amountLands+"&monthTotalRates="+monthTotalRates,obj,'creditSP');
            }
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
	
	//产品使用选择组件
	var _setKindPopupSelection = function(name) {
		var kinds = new Array();
		for (var i = 0; i < mfSysKinds.length; i++) {
			kinds.push({"id" : mfSysKinds[i].kindNo, "name" : mfSysKinds[i].kindName});
		}
		$("select[name=" + name + "]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			itemsCount : 8,
			multiple : false,//单选
			items: kinds,
			changeCallback : function(obj,elem) {
				 var  id =  obj.data("values").attr("name");
				    if(id.indexOf("kindNo_") != -1){
						$("input[id="+id+"]").val("");
					}else{
						$("input[name=creditAmt]").val("");
						$("input[name=amountLand]").val("");

					}
			}
		});
	};
	
	var   _turnKindNo  =  function  (obj){
		var  id =	$(obj).attr("name");
	    if(id.indexOf("kindNo_") != -1){
		    //存栏量赋值为空
	    	$("input[id="+id+"]").val("");
			var thisKindNo = $(obj).val(); 
			var mfSysKind = _getMysKind(thisKindNo);
			var thisIdIndex = id.substr(7);
			$("input[name=monthTotalRate_"+thisIdIndex+"]").val(CalcUtil.formatMoney(mfSysKind.monthTotalRate,2));	 
		}
	};
	
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
	};
	
	
	 //核对修改的产品不可小于原产品的综合费率
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
	   };
	
	//申请处已有的产品产品费率进行改变的时候进行判断不可小于产品中所定的产品综合费率
	 var   _checkMonthTotalRateApply  = function(obj){
		 var   monthTotalRateName  =   $(obj).attr("name"); 
		 var   thisNameIndex  =  monthTotalRateName.substr(15);
		 var   thisKindNo  =  $("[name = kindNo_"+thisNameIndex+"]").val();
		 var   mfSysKind = _getMysKind(thisKindNo);
		 var   monthTotalRate  =   $(obj).val();
		 var   monthTotalRateNew  =	CalcUtil.formatMoney(mfSysKind.monthTotalRate,2);
		
		 if(CalcUtil.subtract( monthTotalRate,monthTotalRateNew)<0){
			  window.top.alert("修改的产品综合费率不可小于原产品的综合费率:"+CalcUtil.formatMoney(monthTotalRateNew,2), 0);
			  $("input[name="+monthTotalRateName+"]").val("");
		  }else{
			 return true; 
		  }
	 };
	 
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
		};
	
	return{
		init:_init,
		doSubmit:_doSubmit,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		// approvalBack:_approvalBack,
		// getApprovaPage:_getApprovaPage,
		creditAmtCount:_creditAmtCount,
		formatMoney:_formatMoney,
		checkCreditAmt:_checkCreditAmt,
		addOneProductTypeLineNew:_addOneProductTypeLineNew,
		delOneProductTypeLineForProNew:_delOneProductTypeLineForProNew,
		checkKindCreditSumForPro:_checkKindCreditSumForPro,
		setKindPopupSelection:_setKindPopupSelection,
		turnKindNo:_turnKindNo,
		limitAmtCount:_limitAmtCount,
		checkMonthTotalRateApply:_checkMonthTotalRateApply,
		getMysKind:_getMysKind,
		checkMonthTotalRate:_checkMonthTotalRate
	};
}(window,jQuery);