var MfBusRecourseApplyDetail = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=guarantyAgencyNo]").popupSelection({//申请机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=guarantyAgency]"));
				$("input[name=guarantyAgency]").val($("input[name=guarantyAgencyNo]").parents("td").find(".pops-value").html());
				$("input[name=recourseCompany]").val($("input[name=guarantyAgency]").val());
			},
		});	

	};
	 //打开发起追偿页面
	 var _getRecourseType = function(){
		 $.ajax({
			url : webPath+'/mfBusRecourseApply/getRecourseType',
			data : {
				"fincId" : fincId
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					if(data.appSts=="3"){//追偿审批完成,隐藏代称按钮,展示代偿确认按钮
						$("#recourse").attr("style","display:none;");
						$("#recourseConfirm").attr("style","display:show();");
					}else if(data.appSts=="1" || data.appSts=="2"){
						$("#recourse").attr("style","display:show();");
						$("#recourseConfirm").attr("style","display:none;");
						$("#recourse").text("追偿中");
						$("#recourse").attr('disabled',true);
						$("#recourse").attr("class", "btn btn-opt-dont");
					}else{
						$("#recourse").attr("style","display:show();");
						$("#recourseConfirm").attr("style","display:none;");
					}
					recourseId = data.recourseId;
				} 
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	 }
	 
		
		//更新操作
	var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url:webPath+"/mfBusRecourseApply/updateAjax",
                data:{
                    ajaxData : dataParam
                },
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        alert(top.getMessage("ERROR_INSERT"),0);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            });
        }
	};


    //更新操作
    var _ajaxInsertRec = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url:webPath+"/mfBusRecourseApply/insertAjaxRec",
                data:{
                    ajaxData : dataParam
                },
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else{
                        alert(top.getMessage("ERROR_INSERT"),0);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            });
        }
    };
	
	//追偿历史列表信息
	//正常初始化
	var _pubMfBusRecourseList = function (){
		$.ajax({
			url:webPath+"/mfBusRecourseConfirm/getRecourseHistoryListAjax?fincId="+fincId+"&tableId="+recourseTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				if(html==''){
				}else{
					$('#mfRecocurseHistoryList-block').removeClass('hidden');
					$("#mfRecourseHistoryList").empty().html(html);
				}
			}
		});
	};

	 var _getRecourseApply = function(){
		top.flag=false;
		top.window.openBigForm(webPath+"/mfBusRecourseApply/input?compensatoryId="+compensatoryId+"&fincId="+fincId,'追偿申请',function(){
			_getRecourseType();
		});
	 }

    var _getRecourseApplyRec = function(){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusRecourseApply/inputRec?compensatoryId="+compensatoryId+"&fincId="+fincId,'追偿登记',function(){
            _getRecourseStatus();
        });
    }
	 
	 var _getRecourseConfirm = function(){
		top.flag=false;
		top.window.openBigForm(webPath+"/mfBusRecourseConfirm/input?fincId="+fincId+"&recourseId="+recourseId,'追偿收回',function(){
			_recourseConfirmBack();
            _getRecourseStatus();
		});
	 }

   var _recourseConfirmBack =function(){
       _getRecourseType();
       _getRecourseStatus();
   };

	 var _getRecourseConfirmInfo = function(){
			top.flag=false;
			top.window.openBigForm(webPath+"/mfBusRecourseConfirm/inputInfo?fincId="+fincId+"&recourseId="+recourseId,'追偿信息',function(){
			});
		 }

	//追偿确认点亮图标，添加点击事件
	var _getRecourseStatus = function () {
        $.ajax({
            url: webPath + '/mfBusRecourseConfirm/getByRecourseId',
            data: {"fincId": fincId },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    //点亮追偿图标
                    $("#recourse_info").addClass("btn-dodgerblue");
                    //添加追偿点击事件
                    $("#recourse_info").click(function () {
                        _getRecourseConfirmInfo();
                    })
                }
            }
        })
    };

	//计算实际追偿总额，违约金=代偿金额×1‰×逾期天数，最大不能超过代偿金额的24%。
 	var _calcActualAmt = function (obj) {
        var toalrecourse = 0.00;
        //申请追偿金额
 		var recourseFee = $("input[name='recourseFee']").val();
		var domNname = $(obj).attr("name");
		var penaltyAmt = 0.00;
		var actualAmount = 0.00;
        var compensatoryAlreadyTerm = $("input[name='compensatoryAlreadyTerm']").val();
        if("actualAmount" == domNname){
            actualAmount = $(obj).val();
            //实际追偿金额不能大于申请追偿金额
            if(parseFloat(actualAmount.replace(/,/g,'')) > parseFloat(recourseFee.replace(/,/g,''))){
                $(obj).val(0.00);
            	alert("实际追偿金额不能大于申请追偿金额",0);
            	return;
			}
            var temval = CalcUtil.multiply(actualAmount.replace(/,/g,''),compensatoryAlreadyTerm);
            penaltyAmt = CalcUtil.multiply(temval,0.001);
            var maxMoneyTip =  CalcUtil.multiply(actualAmount.replace(/,/g,''),0.24);
            if(parseFloat(penaltyAmt) > parseFloat(maxMoneyTip)){
                $("input[name='penaltyAmt']").val(0.00);
                alert("违约金最大不能超过实际追偿金额的24%,"+maxMoneyTip+"元",0);
                return ;
            }else{
                $("input[name='penaltyAmt']").val(CalcUtil.formatMoney(penaltyAmt,2));
            }
		}
        if("penaltyAmt" == domNname){
            penaltyAmt =  $(obj).val();
            actualAmount = $("input[name='actualAmount']").val();
            var limitMoney = CalcUtil.multiply(actualAmount.replace(/,/g,''),0.24);
            penaltyAmt = penaltyAmt.replace(/,/g,'');
            if(parseFloat(penaltyAmt) > parseFloat(limitMoney)){
                $("input[name='penaltyAmt']").val(0.00);
                alert("违约金最大不能超过实际追偿金额的24%,"+limitMoney+"元",0);
                return ;
			}
        }
        toalrecourse = CalcUtil.add(actualAmount.replace(/,/g,''),penaltyAmt);
        $("input[name='actualAmt']").val(CalcUtil.formatMoney(toalrecourse,2));
    };
    var _getDetailPage = function (obj, url) {
        top.LoadingAnimate.start();
        top.window.openBigForm(url, '追偿详情', function() {
            window.location.reload();
        });
    };

    return{
		init:_init,
		ajaxInsert:_ajaxInsert,
		getRecourseType:_getRecourseType,
		getRecourseApply:_getRecourseApply,
		getRecourseConfirm:_getRecourseConfirm,
		getRecourseConfirmInfo:_getRecourseConfirmInfo,
		pubMfBusRecourseList:_pubMfBusRecourseList,
        getRecourseStatus : _getRecourseStatus,
        calcActualAmt : _calcActualAmt,
        getRecourseApplyRec:_getRecourseApplyRec,
        ajaxInsertRec:_ajaxInsertRec,
        recourseConfirmBack : _recourseConfirmBack,
        getDetailPage : _getDetailPage
	};
}(window,jQuery);