;
var WkfMoveabledTransferViewPoint=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_getPledgeData();
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=transferPledge]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:true,//单选
						items:data.items,
					});
					var $obj=$("input[name=transferPledge]").parents("td").eq(0);
					$obj.find("[name=popstransferPledge]").remove();
					$obj.find("[class=pops-select]").remove();
					$obj.find("[class=pops-close]").remove();
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//打开货物明细列表
	var _goodsDetailList=function(){
		var pledgeNo=$("input[name=transferPledge]").val();
		dialog({
			id:"goodsDialog",
    		title:'货物明细',
    		url: webPath+'/pledgeBaseInfoBill/getListByPage?pledgeNo='+pledgeNo,
    		width:700,
    		height:400,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				//返回对象的属性:opNo,opName
    				if(typeof(callback)== "function"){
    					callback(this.returnValue);
    				}else{
    				}
    			}
    		}
    	}).showModal();
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
	var _doSubmit=function(obj){
		var opinionType = $("select[name=opinionType]").val();
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfMoveableTransferApply/submitUpdateAjax?opinionType"+opinionType+"&transferId="+transferId
			+"&appId="+appId,obj,'moveTransSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
		goodsDetailList:_goodsDetailList
	};
}(window,jQuery);