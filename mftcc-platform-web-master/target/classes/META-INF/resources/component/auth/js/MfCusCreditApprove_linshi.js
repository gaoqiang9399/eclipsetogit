var MfCusCreditApprove_linshi = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('select[name=opinionType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
	};
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
            if(baseType=="3"){
                inputName="companyName";
                labelName="链属企业";
                optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+obj.kindName+'(历史授信额度：'+creditHandleUtil.numFormat(creditAmt)+')"><input type="hidden" value="'+obj.kindName+'" name="companyName_'+index+'" readonly="readonly">';
            }else{
                $.each(data.kindMap,function(i,parmDic){
                    if(obj.kindNo == parmDic.optCode){
                        optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+obj.kindName+'(历史授信额度：'+creditHandleUtil.numFormat(creditAmt)+')"><input type="hidden" value="'+obj.kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
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
                '<input value="'+creditHandleUtil.numFormat(creditAdjAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();MfCusCreditAdjustApplyInsert.checkKindCreditSum(this)" class="form-control" mustinput="" datatype="12" id="adjCreditAmt_'+index+'" name="adjCreditAmt_'+index+'" title="授信额度" maxlength="14" disabled="disabled">'+
                '<span class="input-group-addon">元</span></div>'+
                '</td>'+
                '</tr>';
            $("input[name=creditAdjAmt]").parents("tr").after(porductInfo+optionStr);
            //金额格式化
            creditHandleUtil.moneyFormatting("adjCreditAmt_"+index)
        });
        $("input[name=creditAdjAmt]").parents("tr").hide();
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
		if(flag){
			var opinionType = $("[name=opinionType]").val();
			commitProcess(webPath+"/mfCreditIntentionApply/submitApproveAjax?appNo="+creditId+"&opinionType="+opinionType+"&creditId="+creditId,obj,'creditSP');
		}
	};

    //审批提交
    var _doSubmitForPrimary = function(obj){
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
                kindNames.push($("input[name=kindNo_"+i+"]").val());
                creditAmts.push($("input[name=creditAmt_"+i).val().replace(/,/g,""));
                productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val());
            }
            $("input[name=kindNames]").val(JSON.stringify(kindNames));
            $("input[name=creditAmts]").val(JSON.stringify(creditAmts));
        }
        if(creditAmtSum < productAmtSum){
            window.top.alert("产品额度不能超过授信总额", 0);
            return;
        }
        if(flag){
            var opinionType = $("[name=opinionType]").val();
            commitProcess(webPath+"/mfCusCreditApproveInfo/submitApproveForPrimaryAjax?appNo="+creditApproveId+"&opinionType="+opinionType+"&creditApproveId="+creditApproveId+"&primaryAppId="+primaryAppId,obj,'creditSP');
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
        doSubmitForPrimary:_doSubmitForPrimary,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
	};
}(window,jQuery);