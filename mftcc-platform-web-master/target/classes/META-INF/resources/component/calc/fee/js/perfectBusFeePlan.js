;
var perfectBusFeePlan= function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//加载收费主体选择组件
		var flag=0;
		if(itemNo=="3"){//如果时存出保证金时，费用主体取资金机构
			$("input[name=feeMainNo]").popupSelection({//申请机构选择
				searchOn : true, // false-不启用搜索，true-启用搜索
				inline : true, // true-内联,false-弹出
				ajaxUrl : webPath+"/mfBusTrench/getTrenchData",
				multiple : false, // false-单选,true-复选
				changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name=feeMainName]"));
					$("input[name=feeMainName]").val($("input[name=feeMainNo]").parents("td").find(".pops-value").html());
					var feeMainNo = $("input[name=feeMainNo]").val();
					if(flag==0){
						$("input[name=feeAccountId]").popupSelection({//账号选择
							searchOn : true, // false-不启用搜索，true-启用搜索
							inline : true, // true-内联,false-弹出
							ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+feeMainNo,//+"&useType=7"
							//items:data.items,
							multiple : false, // false-单选,true-复选
						});
						flag=1;
					}else{
						$.ajax({
					 		type:"post",
					 		url:webPath+"/mfCusBankAccManage/getBankAccData",
					 		data:{cusNo:feeMainNo},//,useType:"7"
					 		async: false,
					 		beforeSend:function(){
					 			LoadingAnimate.start();
							},success:function(data){
								$("input[name=popsfeeAccountId]").popupSelection("updateItems",data.items);
					 		},
					 		complete : function() {
					 			if (LoadingAnimate) {
					 				LoadingAnimate.stop();
								}
					 		}
					 	});
					}
				}
			});
		}else{
			$("input[name=feeMainNo]").popupSelection({//申请机构选择
				searchOn : true, // false-不启用搜索，true-启用搜索
				inline : true, // true-内联,false-弹出
				ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
				multiple : false, // false-单选,true-复选
				changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name=feeMainName]"));
					$("input[name=feeMainName]").val($("input[name=feeMainNo]").parents("td").find(".pops-value").html());
					var feeMainNo = $("input[name=feeMainNo]").val();
					if(flag==0){
						$("input[name=feeAccountId]").popupSelection({//账号选择
							searchOn : true, // false-不启用搜索，true-启用搜索
							inline : true, // true-内联,false-弹出
							ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+feeMainNo,//+"&useType=7"
							//items:data.items,
							multiple : false, // false-单选,true-复选
						});
						flag=1;
					}else{
						$.ajax({
					 		type:"post",
					 		url:webPath+"/mfCusBankAccManage/getBankAccData",
					 		data:{cusNo:feeMainNo},//,useType:"7"
					 		async: false,
					 		beforeSend:function(){
					 			LoadingAnimate.start();
							},success:function(data){
								$("input[name=popsfeeAccountId]").popupSelection("updateItems",data.items);
					 		},
					 		complete : function() {
					 			if (LoadingAnimate) {
					 				LoadingAnimate.stop();
								}
					 		}
					 	});
					}
				}
			});
		}
		//加载收费账号选择组件
		var feeMainNo = $("input[name=feeMainNo]").val();
		if(feeMainNo!=""||feeMainNo!=null){
			$("input[name=feeAccountId]").popupSelection({//账号选择
				searchOn : true, // false-不启用搜索，true-启用搜索
				inline : true, // true-内联,false-弹出
				ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+$("input[name=feeMainNo]").val(),//+"&useType=7"
				//items:data.items,
				multiple : false, // false-单选,true-复选
			});
			flag=1;
		}
	};
	
	//保存费用信息
	var _ajaxUpdateThis = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						top.saveFlag = true;
						top.feeAccountNo = $("input[name=feeAccountId]").parent().find(".pops-value").html();
						top.mfBusFeePlan = data.mfBusFeePlan;
						window.top.alert(data.msg,1);
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
	return{
		init:_init,
		ajaxUpdateThis:_ajaxUpdateThis
	};
}(window,jQuery);