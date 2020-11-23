;
var viewPointApprove = function(window, $) {
	
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
//		_initCusFund();
		_changeInvest();
	};
	

	var _doSubmit = function(obj){
		var opinionType = $("select[name=opinionType]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfLoanApply/updateLoanApproveAjax?opinionType"+opinionType+"&appId="+appId,obj,'applySP');
		}
	};
	
	var _changeInvest = function(){
		var investMethod = $("select[name=investMethod]").val();//出资方式
		if(investMethod == '1'){
			$("select[name=investMethod]").parent().parent().next().next().hide();
			$("select[name=investMethod]").parent().parent().next().hide();
		}else{
			$("select[name=investMethod]").parent().parent().next().show();
			$("select[name=investMethod]").parent().parent().next().next().show();
		}
	};
	
	var _initCusFund = function(){
		//角色新组件
		$("input[name=cusNoFund]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				items:dataJson,
				addBtn:{//添加扩展按钮
					"title":"新增资金机构",
					"fun":function(d){
						top.window.openBigForm(webPath+'/mfCusCustomer/fundForm','新增资金机构',function(){
						});
					}
				}
//				labelEdit:function(d){
//					top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo='+d.roleNo,'角色配置',function(){
//					});
//				}
				
		});
	};
	
	 //审批页面
	var _getApprovaPage = function(){
	 	$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	 };
	 //返回详情页面
	 var _approvalBack = function(){
	 	$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	 };
	
	return {
		init : _init,
		changeInvest : _changeInvest,
		doSubmit :_doSubmit,
		initCusFund :_initCusFund,
		getApprovaPage:_getApprovaPage,
		approvalBack:_approvalBack
		
	};
}(window, jQuery);
