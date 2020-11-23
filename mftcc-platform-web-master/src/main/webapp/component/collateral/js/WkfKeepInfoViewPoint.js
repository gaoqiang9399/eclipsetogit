;
var WkfKeepInfoViewPoint=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});

        var pledgeNo=$("input[name=collateralId]").val();
        jQuery.ajax({
            url : webPath+"/keepInfo/getGoodsDetailList",
            type : "POST",
            dataType : "json",
            data:{tableId:"tablepledgebaseinfobillList",keepId:keepId},
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    var pledgeBillNoStr ="";
                    $("#goodsDetailList").html(data.tableData);
                    if($('.table_content #tab').find($('input[type=checkbox]'))>0){
                    }
                    $("#goodsDetailListdiv").show();
                    // $("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsApply.checkAllGoodsDetail()">全选</a>');
                    $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
                        $(this).bind("click",function(){
                            var val=this.value;

                        });
                    });
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
	};
	//进入审批页面
	var _getApprovaPage=function(){
		$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	 	/*if(validateCnt==0){
	 		validateAccIsModify();
	 	}*/
	}
	//返回详情页面
	var _approvalBack=function(){
		$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	};
	//出入库审批意见保存提交
	var _doSubmitAjax=function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
			//没有选择审批意见默认同意
			if(opinionType == undefined || opinionType == ''){
				opinionType = "1";
			}
			var operaType="batch";//批量出入库
			if(collateralId!=""){
				operaType="alone";//单独押品出入库
			}
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/keepInfo/submitUpdateAjax?keepId="+keepId+"&appId="+appId+"&operaType="+operaType+"&opinionType="+opinionType+"&appNo="+keepId,formObj,'keepApplySP');
		}
	};
	return{
		init:_init,
		doSubmitAjax:_doSubmitAjax,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
	};
}(window, jQuery);