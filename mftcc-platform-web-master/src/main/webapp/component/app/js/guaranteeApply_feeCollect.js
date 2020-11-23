;
var guaranteeApply_feeCollect = function(window,$){
	var _init = function(){
		// var flag=0;
		// $("input[name=depositInCollectMainId]").popupSelection({//授信项目选择
		// 	searchOn : true, // false-不启用搜索，true-启用搜索
		// 	inline : true, // true-内联,false-弹出
		// 	ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
		// 	multiple : false, // false-单选,true-复选
		// 	changeCallback:function(elem){//回调方法
		// 		BASE.removePlaceholder($("input[name=depositInCollectMain]"));
		// 		$("input[name=depositInCollectMain]").val($("input[name=depositInCollectMainId]").parents("td").find(".pops-value").html());
		// 		var depositInCollectMainId = $("input[name=depositInCollectMainId]").val();
		// 		var depositInAccountId = $("input[name=depositInAccountId]").val();
		// 		if(flag==0){
		// 			$("input[name=depositInAccountId]").popupSelection({//账号选择
		// 				searchOn : true, // false-不启用搜索，true-启用搜索
		// 				inline : true, // true-内联,false-弹出
		// 				ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+depositInCollectMainId,//+"&useType=8"
		// 				//items:data.items,
		// 				multiple : false, // false-单选,true-复选
		// 			});
		// 			flag=1;
		// 		}else{
		// 			$.ajax({
		// 		 		type:"post",
		// 		 		url:webPath+"/mfCusBankAccManage/getBankAccData",
		// 		 		data:{cusNo:depositInCollectMainId},//,useType:"8"
		// 		 		async: false,
		// 		 		beforeSend:function(){
		// 		 			LoadingAnimate.start();
		// 				},success:function(data){
		// 					$("input[name=popsdepositInAccountId]").popupSelection("updateItems",data.items);
		// 		 		},
		// 		 		complete : function() {
		// 		 			if (LoadingAnimate) {
		// 		 				LoadingAnimate.stop();
		// 					}
		// 		 		}
		// 		 	});
		// 		}
		// 	},
		// });
	};
	
	var _advanceCollectFee = function(obj,url){
		top.openBigForm(url, "账号信息", function(){
			
		});
	};

    var _submitForm = function(obj) {
        var dataParam = JSON.stringify($(obj).serializeArray());
        $.ajax({
            url : webPath+'/mfBusAppFee/getByAppIdAjax',
            data : { ajaxData : dataParam },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                LoadingAnimate.stop();
                if (data.flag == "error") {
                    window.top.alert(data.msg, 0);
                }else{
                    var url = $(obj).attr("action");
                    var flag = submitJsMethod($(obj).get(0), '');
                    if (flag) {
                        LoadingAnimate.start();
                        $.ajax({
                            url : url,
                            data : { ajaxData : dataParam },
                            type : 'post',
                            dataType : 'json',
                            success : function(data) {
                                LoadingAnimate.stop();
                                if (data.flag == "success") {
                                    window.top.alert(data.msg, 3);
                                    top.flag = true;
                                    myclose_click();
                                }else if (data.flag == "finish") {
                                    top.flag = true;
                                    myclose_click();
                                } else {
                                    alert(top.getMessage("FAILED_SAVE"), 0);
                                }
                            },
                            error : function() {
                                LoadingAnimate.stop();
                                alert(top.getMessage("FAILED_SAVE"), 0);
                            }
                        });
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }

                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });

    };
	return{
		init:_init,
		advanceCollectFee:_advanceCollectFee,
        submitForm:_submitForm
	}
}(window,jQuery)