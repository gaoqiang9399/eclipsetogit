var mfCreditIntentionApplyInsert = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//保存方法
	var _ajaxInsert = function(formObj, temporaryStorage){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm;
			var creditType=$(formObj).find("[name=creditType]").val();
			dataForm = JSON.stringify($(formObj).serializeArray());
			dataObject = { ajaxData : dataForm,
							creditModel:creditModel,
							baseType:baseType
						 };

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
                        window.top.alert(data.msg, 1,function(){
                            myclose_click();
                        });

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
	 var _initCusLinShi = function(){
        selectCusDialog(_selectCusBack,"","","6");
    }
    //选择客户回调
    var _selectCusBack=function(cus){
        var cusNo=cus.cusNo;
        $("input[name=cusNo]").val(cusNo);
        $("input[name=cusName]").val(cus.cusName);
        var url = webPath+"/mfCreditIntentionApply/input?cusNo="+cusNo+"&creditType="+creditType;
        window.location.href = url;
    };
    //保存方法
    var _ajaxForzenInsert = function(formObj, temporaryStorage){
        var flag = submitJsMethod($(formObj).get(0), '');
        if (flag) {
            var url = $(formObj).attr("action");
            var dataForm;
            var creditType=$(formObj).find("[name=creditType]").val();
            dataForm = JSON.stringify($(formObj).serializeArray());
            dataObject = { ajaxData : dataForm
            };

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
                        top.frozeThawFlag = true;
                        if(data.ifApprove == "1"){
                            top.ifApprove = true;
                        }
                        window.top.alert(data.msg, 3,function(){
                            myclose_click();
                        });
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
     * 授信金额文本框失焦时
     */
    var _creditSumOnBlur = function(){
        var creditSum = $("input[name=creditSum]").val();
        var jichuAmt = $("input[name=jichuAmt]").val();
        if(creditSum!=undefined&&jichuAmt!=undefined){
            creditSum = creditSum.replace(/,/g,"");//替换计数法中的逗号
            jichuAmt = jichuAmt.replace(/,/g,"");//替换计数法中的逗号
			var zong =CalcUtil.add(creditSum,jichuAmt);
            $("input[name=creditAmtZong]").val(zong);
		}
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
	

	//关闭
	var _close = function(){
		myclose();
	};
	var _detailReturn= function (){
        top.openBigForm(webPath+'/mfCreditIntentionApply/openHisData?cusNo='+cusNo, '授信详情信息', function() {
            MfCusCredit.intentionInit();
        });
	}
	return{
		init:_init,
		close:_close,
		ajaxInsert:_ajaxInsert,
		creditTermOnBlur:_creditTermOnBlur,
		beginDateOnBlur:_beginDateOnBlur,
		updateApplyEndDate:_updateApplyEndDate,
        detailReturn:_detailReturn,
        creditSumOnBlur:_creditSumOnBlur,
        ajaxForzenInsert:_ajaxForzenInsert,
        initCusLinShi:_initCusLinShi,
	};
}(window,jQuery);