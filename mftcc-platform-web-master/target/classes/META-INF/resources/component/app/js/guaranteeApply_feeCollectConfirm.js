;
var guaranteeApply_feeCollectConfirm = function(window,$){
	var _init = function(){
		var flag=0;
		$("input[name=depositInCollectMainId]").popupSelection({//主体选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=depositInCollectMain]"));
				$("input[name=depositInCollectMain]").val($("input[name=depositInCollectMainId]").parents("td").find(".pops-value").html());
				var depositInCollectMainId = $("input[name=depositInCollectMainId]").val();
				var depositInAccountId = $("input[name=depositInAccountId]").val();
				if(flag==0){
					$("input[name=depositInAccountId]").popupSelection({//账号选择
						searchOn : true, // false-不启用搜索，true-启用搜索
						inline : true, // true-内联,false-弹出
						ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+depositInCollectMainId,//+"&useType=8"
						//items:data.items,
						multiple : false, // false-单选,true-复选
					});
				}else{
					$.ajax({
				 		type:"post",
				 		url:webPath+"/mfCusBankAccManage/getBankAccData",
				 		data:{cusNo:depositInCollectMainId},//,useType:"8"
				 		async: false,
				 		beforeSend:function(){
				 			LoadingAnimate.start();
						},success:function(data){
							if(data.flag=="success"){
								$("input[name=popsdepositInAccountId]").popupSelection("updateItems",data.items);
							}
				 		},
				 		complete : function() {
				 			if (LoadingAnimate) {
				 				LoadingAnimate.stop();
							}
				 		}
				 	});
				}
			},
		});
		//账户选择
		$("input[name=depositInAccountId]").popupSelection({
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusBankAccManage/getBankAccData?cusNo="+$("input[name=depositInCollectMainId]").val(),//+"&useType=8"
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){
				
			},
		});
		//编辑借据列表 fPopUpCalendarDlg(this);return false
		$('#depositOutList tbody tr').each(function(){
			var trObj = $(this);
			var cusNoFund = trObj.find("input[name=cusNoFund]").val();
			$.ajax({
		 		type:"post",
		 		url:webPath+"/mfCusBankAccManage/getBankAccData",
		 		data:{cusNo:cusNoFund},//,useType:"9"
		 		async: false,
		 		beforeSend:function(){
		 			LoadingAnimate.start();
				},success:function(data){
					if(data.flag=="success"){
						var mfCusBankAccManageList = data.mfCusBankAccManageList;
						trObj.find("select[name=depositOutAccountId]").empty();
						$.each(mfCusBankAccManageList,function(i,obj){
							trObj.find("select[name=depositOutAccountId]").append("<option value='"+obj.id+"'>"+obj.accountNo+"</option>");
						});
						
						$.ajax({
					 		type:"post",
					 		url:webPath+"/mfBusFincApp/getFincAppListAjax",
					 		data:{pactId:pactId},
					 		async: false,
					 		beforeSend:function(){
					 			LoadingAnimate.start();
							},success:function(data){
								if(data.flag=="success"){
									$.each(data.mfBusFincAppList,function(i,obj){
										var depositOutAccountId = obj.depositOutAccountId;
										if(obj.fincId==trObj.find("input[name=fincId]").val()){
											trObj.find("select[name=depositOutAccountId]").find("option[value='"+depositOutAccountId+"']").attr("selected",true);
										}
									});
								}
					 		},
					 		complete : function() {
					 			if (LoadingAnimate) {
					 				LoadingAnimate.stop();
								}
					 		}
					 	});
					}
		 		},
		 		complete : function() {
		 			if (LoadingAnimate) {
		 				LoadingAnimate.stop();
					}
		 		}
		 	});
			
			trObj.find("input[name=depositOutTime]").on('click', function(){
				fPopUpCalendarDlg({min: null,choose:function(){
					_updateDepositOut(trObj);
				}})
			});
		});
		
		$('#depositOutList').on('blur', 'input', function(){
			var trObj = $(this).parents('tr');
			if(''!=$(this).val()){
				_updateDepositOut(trObj);
			}
		});
		$('#depositOutList').on('change', 'select', function(){
			var trObj = $(this).parents('tr');
			if(''!=$(this).val()){
				_updateDepositOut(trObj);
			}
		});
	};
	
	var _updateDepositOut =function(trObj){
		var dataParam = JSON.stringify(trObj.find('input,select').serializeArray());
		jQuery.ajax({
			url: webPath+'/mfBusFincApp/updateDepositOutByIdAjax',
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
// 					 alert(top.getMessage("SUCCEED_OPERATION"),1);
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"),0);
			}
		});
	};
	//生成借据的费用计划
	var _createFincFeePlan = function(obj,url){
		top.openBigForm(url, "生成借据的费用计划", function(){
			
		});
	};
	//收取借据的费用
	var _doFincFeeCollect = function(obj,url){
		top.openBigForm(url, "收取借据的费用", function(){
			
		});
	};
	return{
		init:_init,
		updateDepositOut:_updateDepositOut,
		createFincFeePlan:_createFincFeePlan,
		doFincFeeCollect:_doFincFeeCollect
	}
}(window,jQuery)