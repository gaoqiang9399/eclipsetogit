var mfCusCreditApplyDetail = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		if(creditType=="2"){
			var creditAppId =$("input[name=creditAppId]").val();
			var adjustAppId =$("input[name=adjustAppId]").val();
			//MfCusCreditAdjustApplyInsert.getKindAmtAdjInfo(creditAppId,adjustAppId);
            _initReportPopupSelection(JSON.parse(reportMatter));
		}else{
			//产品信息
			if(mfCusPorductCreditList.length == 0){
				$form.find("[name=kindNo]").parents("tr").remove();
			}
			for(var i=0; i<mfCusPorductCreditList.length; i++){
				index++;
				var optionStr = "";
				var labelName="产品";
				var inputName="kindNo";
				if(baseType=="3"){
					inputName="companyName";
					labelName="链属企业";
					optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="" name="companyName_'+index+'" readonly="readonly">';
				}else{
					$.each(mfSysKinds,function(num,obj){
						if(mfCusPorductCreditList[i].kindNo==obj.kindNo){
							optionStr += '<input type="text" class="form-control" readonly="readonly" value="'+mfCusPorductCreditList[i].kindName+'"><input type="hidden" value="'+mfCusPorductCreditList[i].kindNo+'" name="kindNo_'+index+'" readonly="readonly">';
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
				'<label class="control-label ">授信额度</label>'+
				'</td>'+
				'<td style="width:32%;" rowspan="1" colspan="2" class="tdvalue  half right">'+
				'<div class="input-group">'+
				'<input value="'+creditHandleUtil.numFormat(mfCusPorductCreditList[i].creditAmt)+'"  type="text" onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="toMoney(this)" onfocus="selectInput(this);" onblur="func_uior_valTypeImm(this);resetTimes();" readonly="readonly" class="form-control" mustinput="" datatype="12" id="creditAmt_'+index+'" name="creditAmt_'+index+'" title="授信额度">'+
				'<span class="input-group-addon">元</span></div>'+
				'</td>'+
				'</tr>';
				$("input[name=creditAmt]").parents("tr").after(porductInfo+optionStr);
				//金额格式化
				creditHandleUtil.moneyFormatting("creditAmt_"+index)
			};
			$("input[name=creditAmt]").parents("tr").remove();
			
		}
		
		$("[name=reportOpNo]").popupSelection({
			ajaxUrl : webPath+"/sysUser/getSysUserListAjax?opNoType=1",
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "列席人员",
			handle : BASE.getIconInTd($("input[name=reportOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=reportOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=reportOpName]").val(AcceptOpName);
			}
		});
        _tuningListInit('1');
        _tuningListInit('2');
        _tuningListInit('3');
        _tuningListInit('4');
        _tuningListInit('5');
	};
	
	//更新操作
	var _ajaxUpdate = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			$("input[name=kindName]").val($("select[name=kindNo] option:selected").text());
			var dataForm = JSON.stringify($(formObj).serializeArray());
			var kindNos = [];
			var kindNames = [];
			var creditAmts = [];
			var productAmtSum = Number($("input[name=creditAmt]").val());
			var creditAmtSum = Number($("input[name=creditSum]").val());
			if(index != 0){
				for(var i = 1;i<=index;i++){
					kindNos.push($("select[name=kindNo_"+i+"] option:selected").val());
					kindNames.push($("select[name=kindNo_"+i+"] option:selected").text());
					creditAmts.push($("input[name=creditAmt_"+i).val());
					productAmtSum = productAmtSum + Number($("input[name=creditAmt_"+i).val());
				}
			}
			if(creditAmtSum < productAmtSum){
				window.top.alert("产品额度不能超过授信总额", 0);
				return;
			}
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					kindNos: JSON.stringify(kindNos),
					kindNames: JSON.stringify(kindNames),
					creditAmts: JSON.stringify(creditAmts)
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = data.wkfAppId;
						//window.top.alert(data.msg, 1);
						myclose_click()
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					loadingAnimate.stop();
					alert(data.msg, 0);
				}
			});
		}
	};
	//关闭
	var _close = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
        var flag1 = true;
        var supdataForm;
        if (creditType=='2'&&nodeNo=='report'){
            flag1 = submitJsMethod($('#supplementform').get(0), '');
            supdataForm = JSON.stringify($('#supplementform').serializeArray());
		}
        //校验押品是否关联合作银行
		var dataForm = JSON.stringify($(formObj).serializeArray());
        if(!checkAgenciesPledgeRel(dataForm)){
            return;
        }

    if(flag&&flag1){
        alert("确定已经确认报告流程，提交下一步",2,function(){
            $.ajax({
                url: webPath+"/mfCusCreditApply/doCommitWkf",
                type:"post",
                dataType:"json",
                data:{
                    wkfAppId:wkfAppId,
                    commitType:"REPORT",
                    ajaxData:dataForm,
                    formData:supdataForm
                },
                error:function(){
                    alert('提交到下一个节点时发生异常', 0);
                },
                success:function(data){
                    if(data.flag == "success"){
                        top.creditFlag=true;
                        top.creditAppId=creditAppId;
                        top.creditType=creditType;
                        top.creditType=wkfAppId;
                        top.cusNo=cusNo;
                        myclose_click();
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                }
            });
        });
    }
    /*//表示尽职报告调用使用
    if(openType == "1"){
    }else{
        //myclose_click();
        myclose();
    }*/
};

    //校验押品是否关联合作银行
    var checkAgenciesPledgeRel = function(dataParam){
        var ruleFlag = true;
        $.ajax({
            url : webPath+"/mfBusAgenciesPledgeRel/checkAgenciesPledgeRelAjax",
            type : "post",
            async: false,
            data : {ajaxData: dataParam,creditAppId:creditAppId},
            dataType : "json",
            success : function(data) {
                if (data.flag == "error") {// 存在
                    window.top.alert(data.msg, 0);
                    /*alert(top.getMessage("该业务退费金额小于缴款金额"), 2, function() {
                    },function(){
                    });*/
                    ruleFlag = false;
                }else {
                }
            },
            error : function() {
            }
        });
        return ruleFlag;
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
        var beginDate = $("input[name=beginDate]").val();
        var endDate = beginDate;
        if (beginDate != "") {
            if (creditTerm != "") {
                endDate = creditHandleUtil.getAddMonthRes(beginDate, creditTerm, "m");
            }

            $("input[name=endDate]").val(endDate);
        }
    };


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
                        $("input[name='quotaCalc']").val(data.quotaCalc);
                        if(typeof (data.bjAmtCalc) != 'undefined'){
                            $("input[name='bjAmtCalc']").val(data.bjAmtCalc);
                        }
                        if(typeof (data.houseAmtCalc) != 'undefined'){
                            $("input[name='houseAmtCalc']").val(data.houseAmtCalc);
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
    var _saveOnlyAjax = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        var flag1 = true;
        var supdataForm;
        if (creditType=='2'&&nodeNo=='report'){
            flag1 = submitJsMethod($('#supplementform').get(0), '');
            supdataForm = JSON.stringify($('#supplementform').serializeArray());
        }
        //if(flag&&flag1){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data: {ajaxData: dataParam,appId:appId,formData:supdataForm},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        window.location.href = webPath + "/mfCusCreditApply/getOrderDescFirst?&wkfAppId=" + wkfAppId + "&creditAppId=" + creditAppId ;
                        window.top.alert(data.msg,1);
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
        //}
    };

    //初始化补充报告涉及事项选择组件
    var _initReportPopupSelection = function (data) {
        $("input[name=reportMatter]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//下拉模式
            multiple: true,//多选选
            title: "补充报告涉及事项",
            items: data,
            labelShow: false,
            changeCallback: function (obj, elem) {

            },
        });
    };

    //尽调下列表新增
    var _creditTuningAdd = function (delType) {
        $.ajax({
            url: webPath + "/mfCusCreditTuningDetail/input",
            data:{creditAppId:creditAppId,delType:delType},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#creditTuningInsertForm").html(data.formHtml);
                    $("#creditInsert").css("display","none");
                    $("#creditTuningInsert").css("display","block");
                } else {
                    alert("数据查询出错")
                }
            }
        });
    };
    //尽调下列表保存
    var _tuningSave = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var delType = $("input[name=delType]").val();
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data: {ajaxData: dataParam},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        _tuningListInit(delType);
                        _tuningCancle();
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
        }
    };
    //尽调下列表取消
    var  _tuningCancle = function () {
        $("#creditInsert").css("display","block");
        $("#creditTuningInsert").css("display","none");
    };
    //尽调下列表初始化
    var _tuningListInit = function (delType) {
        var tableId = "tablecredittuningList"+delType;
        $.ajax({
            url:webPath+"/mfCusCreditTuningDetail/findByPageAjax",
            type:'post',
            data : {tableId:tableId,delType:delType,creditAppId:creditAppId},
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $("#tuningList"+delType).empty().html(data.tableHtml);
                }else if(data.flag == "error"){
                    alert(data.msg,0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
    var _tuningDelete = function(obj, url) {
        alert(top.getMessage("CONFIRM_DELETE"), 2, function() {
            var ajaxParam = {};
            url=webPath+url;
            $.post(url, ajaxParam, function(data) {
                if (data.flag == "success") {
                    window.top.alert(data.msg, 1);
                    _tuningListInit(data.delType);
                }else {
                    alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
                }
            })
        })
    };
    var _glqysqcybChange =function () {
        var glqysqcyb = $("select[name='glqysqcyb']").val();
        if(glqysqcyb==1){
            $("select[name='relCorpApply']").val("0");
        }
    }
	return{
		init:_init,
		close:_close,
		ajaxUpdate:_ajaxUpdate,
        beginDateOnBlur:_beginDateOnBlur,
        creditTermOnBlur:_creditTermOnBlur,
        changeFormDisplay : _changeFormDisplay,
        calcQuotaSts:_calcQuotaSts,
        calcQuotaAjax:_calcQuotaAjax,
        saveOnlyAjax:_saveOnlyAjax,
        initReportPopupSelection:_initReportPopupSelection,
        creditTuningAdd:_creditTuningAdd,
        tuningSave:_tuningSave,
        tuningCancle:_tuningCancle,
        tuningListInit:_tuningListInit,
        tuningDelete:_tuningDelete,
        glqysqcybChange:_glqysqcybChange
	};
}(window,jQuery);